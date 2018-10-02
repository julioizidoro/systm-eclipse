package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.FornecedorCidadeDocsFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadedocs;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class DocumentosFornecedorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Fornecedorcidade fornecedorCidade;
	private Ftpdados ftpDados;
	private List<DocsVideosFornecedorBean> listaVideos;
	private List<DocsVideosFornecedorBean> listaAquivo;
	private DocsVideosFornecedorBean docsVideosFornecedorBean;
	private Fornecedordocs fornecedordocs;
	private Fornecedorcidadedocs fornecedorcidadedocs;
	private List<Fornecedorcidadedocs> listaFornecedorCidadeDocs;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			fornecedorCidade = (Fornecedorcidade) session.getAttribute("fornecedorCidade");
			session.removeAttribute("fornecedorCidade");
			ftpDados = new Ftpdados();
			FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			gerarListaFornecedorCidadeArquivo();
			gerarListaFornecedorCidadeVideo();
			try {
				ftpDados = ftpDadosFacade.getFTPDados();
			} catch (SQLException e) {
				  
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public List<DocsVideosFornecedorBean> getListaVideos() {
		return listaVideos;
	}

	public void setListaVideos(List<DocsVideosFornecedorBean> listaVideos) {
		this.listaVideos = listaVideos;
	}

	public List<DocsVideosFornecedorBean> getListaAquivo() {
		return listaAquivo;
	}

	public void setListaAquivo(List<DocsVideosFornecedorBean> listaAquivo) {
		this.listaAquivo = listaAquivo;
	}

	public Fornecedordocs getFornecedordocs() {
		return fornecedordocs;
	}

	public void setFornecedordocs(Fornecedordocs fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}

	public Fornecedorcidadedocs getFornecedorcidadedocs() {
		return fornecedorcidadedocs;
	}

	public void setFornecedorcidadedocs(Fornecedorcidadedocs fornecedorcidadedocs) {
		this.fornecedorcidadedocs = fornecedorcidadedocs;
	}

	public List<Fornecedorcidadedocs> getListaFornecedorCidadeDocs() {
		return listaFornecedorCidadeDocs;
	}

	public void setListaFornecedorCidadeDocs(List<Fornecedorcidadedocs> listaFornecedorCidadeDocs) {
		this.listaFornecedorCidadeDocs = listaFornecedorCidadeDocs;
	}

	public DocsVideosFornecedorBean getDocsVideosFornecedorBean() {
		return docsVideosFornecedorBean;
	}

	public void setDocsVideosFornecedorBean(DocsVideosFornecedorBean docsVideosFornecedorBean) {
		this.docsVideosFornecedorBean = docsVideosFornecedorBean;
	}

	public String voltar() {
		return "consEscolasCadastradas";
	}

	public void gerarListaFornecedorCidadeArquivo() {
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		String sql = "Select f From Fornecedorcidadedocs f Where f.fornecedorcidade.idfornecedorcidade="
				+ fornecedorCidade.getIdfornecedorcidade() + " and f.fornecedordocs.tipo='documento'";
		if (usuarioLogadoMB != null) {
			if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialdocsfornecedor()) {
				sql = sql + " and f.fornecedordocs.datainicio<='" + Formatacao.ConvercaoDataSql(new Date())
						+ "' and (f.fornecedordocs.datavalidade>='" + Formatacao.ConvercaoDataSql(new Date())
						+ "' or f.fornecedordocs.datavalidade is null)";
				sql = sql + " and f.fornecedordocs.restrito=0 ";
			}
		}
		listaFornecedorCidadeDocs = fornecedorCidadeDocsFacade.listar(sql);
		if (listaFornecedorCidadeDocs == null) {
			listaFornecedorCidadeDocs = new ArrayList<>();
		}
		popularListaDocs();
	}

	public void gerarListaFornecedorCidadeVideo() {
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		String sql = "Select f From Fornecedorcidadedocs f Where f.fornecedorcidade.idfornecedorcidade="
				+ fornecedorCidade.getIdfornecedorcidade() + " and f.fornecedordocs.tipo='Video'";
		if (usuarioLogadoMB != null) {
			if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialdocsfornecedor()) {
				sql = sql + " and f.fornecedordocs.datainicio<='" + Formatacao.ConvercaoDataSql(new Date())
						+ "' and (f.fornecedordocs.datavalidade>='" + Formatacao.ConvercaoDataSql(new Date())
						+ "' or f.fornecedordocs.datavalidade is null)";
				sql = sql + " and f.fornecedordocs.restrito=0 ";
			}
		}
		listaFornecedorCidadeDocs = fornecedorCidadeDocsFacade.listar(sql);
		if (listaFornecedorCidadeDocs == null) {
			listaFornecedorCidadeDocs = new ArrayList<>();
		}
		popularListaVideos();
	}

	public void popularListaDocs() {
		listaAquivo = new ArrayList<>();
		listaVideos = new ArrayList<>();
		for (int i = 0; i < listaFornecedorCidadeDocs.size(); i++) {
			docsVideosFornecedorBean = new DocsVideosFornecedorBean();
			docsVideosFornecedorBean.setFornecedordocs1(listaFornecedorCidadeDocs.get(i).getFornecedordocs());
			if ((i + 1) < listaFornecedorCidadeDocs.size()) {
				if (listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs().getTipo().equalsIgnoreCase("Documento")) {
					docsVideosFornecedorBean
							.setFornecedordocs2(listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs());
					i++;
					if ((i + 1) < listaFornecedorCidadeDocs.size()) {
						if (listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs().getTipo()
								.equalsIgnoreCase("Documento")) {
							docsVideosFornecedorBean
									.setFornecedordocs3(listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs());
							i++;
						} else {
							docsVideosFornecedorBean.setFornecedordocs3(null);
						}
					} else {
						docsVideosFornecedorBean.setFornecedordocs3(null);
					}
				} else {
					docsVideosFornecedorBean.setFornecedordocs2(null);
					docsVideosFornecedorBean.setFornecedordocs3(null);
				}

			} else {
				docsVideosFornecedorBean.setFornecedordocs2(null);
				docsVideosFornecedorBean.setFornecedordocs3(null);
			}

			listaAquivo.add(docsVideosFornecedorBean);

		}
	}

	public void popularListaVideos() {
		listaVideos = new ArrayList<>();
		for (int i = 0; i < listaFornecedorCidadeDocs.size(); i++) {
			docsVideosFornecedorBean = new DocsVideosFornecedorBean();
			docsVideosFornecedorBean.setFornecedordocs1(listaFornecedorCidadeDocs.get(i).getFornecedordocs());
			if ((i + 1) < listaFornecedorCidadeDocs.size()) {
				if (listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs().getTipo().equalsIgnoreCase("Video")) {
					docsVideosFornecedorBean
							.setFornecedordocs2(listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs());
					i++;
					if ((i + 1) < listaFornecedorCidadeDocs.size()) {
						if (listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs().getTipo()
								.equalsIgnoreCase("Video")) {
							docsVideosFornecedorBean
									.setFornecedordocs3(listaFornecedorCidadeDocs.get(i + 1).getFornecedordocs());
							i++;
						} else {
							docsVideosFornecedorBean.setFornecedordocs3(null);
						}
					} else {
						docsVideosFornecedorBean.setFornecedordocs3(null);
					}
				} else {
					docsVideosFornecedorBean.setFornecedordocs2(null);
					docsVideosFornecedorBean.setFornecedordocs3(null);
				}

			} else {
				docsVideosFornecedorBean.setFornecedordocs2(null);
				docsVideosFornecedorBean.setFornecedordocs3(null);
			}

			listaVideos.add(docsVideosFornecedorBean);
		}
	}

	public boolean verificarDocsVideo(Fornecedordocs fornecedordocs) {
		if (fornecedordocs == null) {
			return false;
		} else {
			return true;
		}
	}

	public String retornaIconeDocumento(String nomeArquivo) {
		if (nomeArquivo == "") {
			return "";
		}
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length() > 5) {
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg") || extensao.equalsIgnoreCase(".jpeg")
				|| extensao.equalsIgnoreCase(".png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase(".pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase(".docx") || extensao.equalsIgnoreCase(".doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase(".xls") || extensao.equalsIgnoreCase(".xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (extensao.equalsIgnoreCase(".txt")) {
			return "../../resources/img/icone_txt.png";
		} else if (extensao.equalsIgnoreCase(".cdr")) {
			return "../../resources/img/icone_cdr.png";
		} else if (extensao.equalsIgnoreCase(".eps")) {
			return "../../resources/img/icone_eps.png";
		} else if (extensao.equalsIgnoreCase(".bmp")) {
			return "../../resources/img/icone_bmp.png";
		} else if (extensao.equalsIgnoreCase(".pptx")) {
			return "../../resources/img/icone_pptx.png";
		} else if (extensao.equalsIgnoreCase(".wma")) {
			return "../../resources/img/iconewma.png";
		} else if (extensao.equalsIgnoreCase(".ppsx")) {
			return "../../resources/img/icone_ppsx.png";
		}
		return "";
	}

	public void editarDocumentoFornecedor(Fornecedordocs fornecedordocs) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		session.setAttribute("fornecedordocs", fornecedordocs);
		options.put("contentWidth", 480);
		RequestContext.getCurrentInstance().openDialog("editarDocumentosFornecedor", options, null);
	}

	public void retornoDialogEditar(SelectEvent event) {
		Fornecedordocs fornecedordocs = (Fornecedordocs) event.getObject();
		if (fornecedordocs.getIdfornecedordocs() != null) {
			Mensagem.lancarMensagemInfo("Salvou com sucesso", "");
			gerarListaFornecedorCidadeArquivo();
			gerarListaFornecedorCidadeVideo();
		}
	}

	public void excluir(Fornecedordocs fornecedordocs) {
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		if (fornecedordocs != null) {
			String sql = "Select f From Fornecedorcidadedocs f Where f.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade() + " and f.fornecedordocs.idfornecedordocs="
					+ fornecedordocs.getIdfornecedordocs();
			Fornecedorcidadedocs fornecedorcidadedocs = fornecedorCidadeDocsFacade.consultar(sql);
			if (fornecedorcidadedocs != null) {
				fornecedorCidadeDocsFacade.excluir(fornecedorcidadedocs.getIdfornecedorcidadedocs());
			}
			Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
		}
		gerarListaFornecedorCidadeArquivo();
		gerarListaFornecedorCidadeVideo();
	}

}
