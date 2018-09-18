package br.com.travelmate.managerBean.cloud.midia;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class Arquivo5MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Arquivo1 arquivo1;
	private Pasta1 pasta1;
	private List<Arquivo1> listaArquivo1;
	private Departamento departamento;
	private List<Pasta2> listaPasta2;
	private Pasta2 pasta2;
	private List<Arquivo2> listaArquivo2;
	private Arquivo2 arquivo2;
	private Pasta3 pasta3;
	private Arquivo3 arquivo3;
	private List<Pasta3> listaPasta3;
	private List<Arquivo3> listaArquivo3;
	private List<Arquivo5Bean> listaArquivo5Bean;
	private Arquivo5Bean arquivo5Bean;
	private boolean semArquivos = false;
	private Ftpdados ftpDados;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Pasta4 pasta4;
	private List<Pasta4> listaPasta4;
	private List<Arquivo4> listaArquivo4;
	private Pasta5 pasta5;
	private List<Arquivo5> listaArquivo5;
	private boolean tabelaArquivoQuadro = true;
	private boolean tabelaArquivoLista = false;
	private boolean pesquisar;
	private String tituloPagina;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private String tipoArquivo;
	private String nomeArquivo;
	private ArquivoBean arquivosBean;
	private String urlArquivo = "";

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			pasta5 = (Pasta5) session.getAttribute("pasta5");
			session.removeAttribute("pasta5");
			if (pasta5 != null) {
				pasta4 = pasta5.getPasta4();
				pasta3 = pasta5.getPasta3();
				pasta2 = pasta5.getPasta2();
				pasta1 = pasta5.getPasta1();
				departamento = pasta1.getDepartamento();
			}
			if (departamento != null && pasta1 != null && pasta2 != null && pasta3 != null && pasta4 != null
					&& pasta5 != null) {
				gerarListaCloudArquivo5();
			} else {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ftpDados = new Ftpdados();
			FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			try {
				ftpDados = ftpDadosFacade.getFTPDados();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (ftpDados != null) {
				urlArquivo = "http://docs.systm.com.br";
			}
			// Verificar se contém arquivos na tela
			semConteudo();
			verificarExibicao();
		}
	}

	public List<Arquivo4> getListaArquivo4() {
		return listaArquivo4;
	}

	public void setListaArquivo4(List<Arquivo4> listaArquivo4) {
		this.listaArquivo4 = listaArquivo4;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public boolean isSemArquivos() {
		return semArquivos;
	}

	public void setSemArquivos(boolean semArquivos) {
		this.semArquivos = semArquivos;
	}

	public List<Arquivo5Bean> getListaArquivo5Bean() {
		return listaArquivo5Bean;
	}

	public void setListaArquivo5Bean(List<Arquivo5Bean> listaArquivo5Bean) {
		this.listaArquivo5Bean = listaArquivo5Bean;
	}

	public Arquivo5Bean getArquivo5Bean() {
		return arquivo5Bean;
	}

	public void setArquivo5Bean(Arquivo5Bean arquivo5Bean) {
		this.arquivo5Bean = arquivo5Bean;
	}

	public Pasta5 getPasta5() {
		return pasta5;
	}

	public void setPasta5(Pasta5 pasta5) {
		this.pasta5 = pasta5;
	}

	/**
	 * @return the arquivo1
	 */
	public Arquivo1 getArquivo1() {
		return arquivo1;
	}

	/**
	 * @param arquivo1
	 *            the arquivo1 to set
	 */
	public void setArquivo1(Arquivo1 arquivo1) {
		this.arquivo1 = arquivo1;
	}

	/**
	 * @return the pasta1
	 */
	public Pasta1 getPasta1() {
		return pasta1;
	}

	/**
	 * @param pasta1
	 *            the pasta1 to set
	 */
	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}

	/**
	 * @return the listaArquivo1
	 */
	public List<Arquivo1> getListaArquivo1() {
		return listaArquivo1;
	}

	/**
	 * @param listaArquivo1
	 *            the listaArquivo1 to set
	 */
	public void setListaArquivo1(List<Arquivo1> listaArquivo1) {
		this.listaArquivo1 = listaArquivo1;
	}

	/**
	 * @return the listaPasta2
	 */
	public List<Pasta2> getListaPasta2() {
		return listaPasta2;
	}

	/**
	 * @param listaPasta2
	 *            the listaPasta2 to set
	 */
	public void setListaPasta2(List<Pasta2> listaPasta2) {
		this.listaPasta2 = listaPasta2;
	}

	/**
	 * @return the pasta2
	 */
	public Pasta2 getPasta2() {
		return pasta2;
	}

	/**
	 * @param pasta2
	 *            the pasta2 to set
	 */
	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}

	/**
	 * @return the listaArquivo2
	 */
	public List<Arquivo2> getListaArquivo2() {
		return listaArquivo2;
	}

	/**
	 * @param listaArquivo2
	 *            the listaArquivo2 to set
	 */
	public void setListaArquivo2(List<Arquivo2> listaArquivo2) {
		this.listaArquivo2 = listaArquivo2;
	}

	/**
	 * @return the arquivo2
	 */
	public Arquivo2 getArquivo2() {
		return arquivo2;
	}

	/**
	 * @param arquivo2
	 *            the arquivo2 to set
	 */
	public void setArquivo2(Arquivo2 arquivo2) {
		this.arquivo2 = arquivo2;
	}

	/**
	 * @return the pasta3
	 */
	public Pasta3 getPasta3() {
		return pasta3;
	}

	/**
	 * @param pasta3
	 *            the pasta3 to set
	 */
	public void setPasta3(Pasta3 pasta3) {
		this.pasta3 = pasta3;
	}

	/**
	 * @return the arquivo3
	 */
	public Arquivo3 getArquivo3() {
		return arquivo3;
	}

	/**
	 * @param arquivo3
	 *            the arquivo3 to set
	 */
	public void setArquivo3(Arquivo3 arquivo3) {
		this.arquivo3 = arquivo3;
	}

	/**
	 * @return the listaPasta3
	 */
	public List<Pasta3> getListaPasta3() {
		return listaPasta3;
	}

	/**
	 * @param listaPasta3
	 *            the listaPasta3 to set
	 */
	public void setListaPasta3(List<Pasta3> listaPasta3) {
		this.listaPasta3 = listaPasta3;
	}

	/**
	 * @return the listaArquivo3
	 */
	public List<Arquivo3> getListaArquivo3() {
		return listaArquivo3;
	}

	/**
	 * @param listaArquivo3
	 *            the listaArquivo3 to set
	 */
	public void setListaArquivo3(List<Arquivo3> listaArquivo3) {
		this.listaArquivo3 = listaArquivo3;
	}

	/**
	 * @return the usuarioLogadoMB
	 */
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	/**
	 * @param usuarioLogadoMB
	 *            the usuarioLogadoMB to set
	 */
	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Pasta4 getPasta4() {
		return pasta4;
	}

	public void setPasta4(Pasta4 pasta4) {
		this.pasta4 = pasta4;
	}

	public List<Pasta4> getListaPasta4() {
		return listaPasta4;
	}

	public void setListaPasta4(List<Pasta4> listaPasta4) {
		this.listaPasta4 = listaPasta4;
	}

	public List<Arquivo5> getListaArquivo5() {
		return listaArquivo5;
	}

	public void setListaArquivo5(List<Arquivo5> listaArquivo5) {
		this.listaArquivo5 = listaArquivo5;
	}

	public boolean isTabelaArquivoQuadro() {
		return tabelaArquivoQuadro;
	}

	public void setTabelaArquivoQuadro(boolean tabelaArquivoQuadro) {
		this.tabelaArquivoQuadro = tabelaArquivoQuadro;
	}

	public boolean isTabelaArquivoLista() {
		return tabelaArquivoLista;
	}

	public void setTabelaArquivoLista(boolean tabelaArquivoLista) {
		this.tabelaArquivoLista = tabelaArquivoLista;
	}

	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public String getTituloPagina() {
		return tituloPagina;
	}

	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}

	public ArquivosVencidosBean getArquivosVencidosBean() {
		return arquivosVencidosBean;
	}

	public void setArquivosVencidosBean(ArquivosVencidosBean arquivosVencidosBean) {
		this.arquivosVencidosBean = arquivosVencidosBean;
	}

	public List<ArquivosVencidosBean> getListaArquivoVencidoBean() {
		return listaArquivoVencidoBean;
	}

	public void setListaArquivoVencidoBean(List<ArquivosVencidosBean> listaArquivoVencidoBean) {
		this.listaArquivoVencidoBean = listaArquivoVencidoBean;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public String voltarConsPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1";
	}

	public String voltarConsPasta2Arquivo1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta1", pasta1);
		return "consPasta2Arquivo1";
	}

	public String voltarConsSubPasta3Arquivo2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta2", pasta2);
		return "consPasta3Arquivo2";
	}

	public String voltarConsDepartamento() {
		return "consDepartamentos";
	}

	public void gerarListaCloudArquivo5() {
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			int iddepartamento = pasta1.getDepartamento().getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo5 c where c.pasta5.idpasta5=" + pasta5.getIdpasta5();
			} else {
				sql = "Select c from Arquivo5 c where c.pasta5.idpasta5=" + pasta5.getIdpasta5()
						+ " and c.datainicio<='" + Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			sql = sql + " order by c.nome";
			listaArquivo5 = arquivo5Facade.listar(sql);
			if (listaArquivo5 == null) {
				listaArquivo5 = new ArrayList<Arquivo5>();
			} else {
				listaArquivo5Bean = new ArrayList<Arquivo5Bean>();
			}
			for (int i = 0; i < listaArquivo5.size(); i++) {
				arquivo5Bean = new Arquivo5Bean();
				arquivo5Bean.setArquivo1(listaArquivo5.get(i));
				if ((i + 1) < listaArquivo5.size()) {
					arquivo5Bean.setArquivo2(listaArquivo5.get(i + 1));
					i++;
					if ((i + 1) < listaArquivo5.size()) {
						arquivo5Bean.setArquivo3(listaArquivo5.get(i + 1));
						i++;
					} else {
						arquivo5Bean.setArquivo3(null);
					}

				} else {
					arquivo5Bean.setArquivo2(null);
					arquivo5Bean.setArquivo3(null);
				}

				listaArquivo5Bean.add(arquivo5Bean);
			}
		}
	}
	
	
	public String retornaIconeArquivo(String nomeArquivo){
		if (nomeArquivo.equalsIgnoreCase("jpg") || nomeArquivo.equalsIgnoreCase("jpeg")
				|| nomeArquivo.equalsIgnoreCase("png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (nomeArquivo.equalsIgnoreCase("pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (nomeArquivo.equalsIgnoreCase("docx") || nomeArquivo.equalsIgnoreCase("doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (nomeArquivo.equalsIgnoreCase("xls") || nomeArquivo.equalsIgnoreCase("xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (nomeArquivo.equalsIgnoreCase("txt")) {
			return "../../resources/img/icone_txt.png";
		} else if (nomeArquivo.equalsIgnoreCase("cdr")) {
			return "../../resources/img/icone_cdr.png";
		} else if (nomeArquivo.equalsIgnoreCase("eps")) {
			return "../../resources/img/icone_eps.png";
		} else if (nomeArquivo.equalsIgnoreCase("bmp")) {
			return "../../resources/img/icone_bmp.png";
		} else if (nomeArquivo.equalsIgnoreCase("pptx")) {
			return "../../resources/img/icone_pptx.png";
		} else if (nomeArquivo.equalsIgnoreCase("wma")) {
			return "../../resources/img/iconewma.png";
		}else if(nomeArquivo.equalsIgnoreCase("ppsx")){
			return "../../resources/img/icone_ppsx.png";
		}else if(nomeArquivo.equalsIgnoreCase("gif")){
			return "../../resources/img/iconegif.png";
		} else{
			return "../../resources/img/iconeDefault.png";
		}
	}

	public String cadastroArquivo5() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta5", pasta5);
		RequestContext.getCurrentInstance().openDialog("cadArquivo5", options, null);
		return "";
	}

	public void retornoDialogNovoArquivo(SelectEvent event) {
	    @SuppressWarnings("unchecked")
		List<GerarAvisosDocsBean> listaAviso = (List<GerarAvisosDocsBean>) event.getObject();
		if (listaAviso.size() > 0) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			new AvisoArquivoBean(listaAviso);
		}
		gerarListaCloudArquivo5();
		semConteudo();
	}

	// Verificar se contém arquivos na tela
	public void semConteudo() {
		if ((listaArquivo5 == null || listaArquivo5.isEmpty())) {
			semArquivos = true;
		} else {
			semArquivos = false;
		}
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo5(Arquivo5 arquivo5) {
		excluirArquivoFTP(arquivo5);
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		arquivo5Facade.excluir(arquivo5.getIdarquivo5());
		gerarListaCloudArquivo5();
		return "";
	}

	public String alterarNomeArquivo(Arquivo5 arquivo5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("arquivo5", arquivo5);
		RequestContext.getCurrentInstance().openDialog("editarArquivo5", options, null);
		return "";
	}

	public void retornoDialogAlteracaoNomeArquivo(SelectEvent event) {
		Arquivo5 arquivo5 = (Arquivo5) event.getObject();
		if (arquivo5.getIdarquivo5() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarListaCloudArquivo5();
		semConteudo();
	}

	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (departamento != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	public boolean verificarArquivo1(Arquivo5Bean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getArquivo1() != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	public boolean verificarArquivo2(Arquivo5Bean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getArquivo2() != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	public boolean verificarArquivo3(Arquivo5Bean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getArquivo3() != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	// public boolean verificarArquivo4(Arquivo5Bean arquivo){
	// Boolean acesso = false;
	// if (arquivo.getArquivo4() == null) {
	// return acesso;
	// }else{
	// if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() ==
	// departamento.getIddepartamento()) {
	// acesso = true;
	// }else if
	// (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9)
	// {
	// acesso = true;
	// if (departamento.getIddepartamento()==1){
	// acesso =false;
	// }
	// }
	// return acesso;
	// }
	// }

	public String posicaoInteracaoUiRepeat(String posicao) {
		String classe = "";
		if (posicao.equalsIgnoreCase("0") || posicao.equalsIgnoreCase("4") || posicao.equalsIgnoreCase("8")
				|| posicao.equalsIgnoreCase("12") || posicao.equalsIgnoreCase("16") || posicao.equalsIgnoreCase("20")
				|| posicao.equalsIgnoreCase("24") || posicao.equalsIgnoreCase("28") || posicao.equalsIgnoreCase("32")
				|| posicao.equalsIgnoreCase("36") || posicao.equalsIgnoreCase("40")) {
			classe = "panel-stat3 bg-danger";
			return classe;
		} else if (posicao.equalsIgnoreCase("1") || posicao.equalsIgnoreCase("5") || posicao.equalsIgnoreCase("9")
				|| posicao.equalsIgnoreCase("13") || posicao.equalsIgnoreCase("17") || posicao.equalsIgnoreCase("21")
				|| posicao.equalsIgnoreCase("25") || posicao.equalsIgnoreCase("29") || posicao.equalsIgnoreCase("33")
				|| posicao.equalsIgnoreCase("37") || posicao.equalsIgnoreCase("41")) {
			classe = "panel-stat3 bg-info";
			return classe;
		} else if (posicao.equalsIgnoreCase("2") || posicao.equalsIgnoreCase("6") || posicao.equalsIgnoreCase("10")
				|| posicao.equalsIgnoreCase("14") || posicao.equalsIgnoreCase("18") || posicao.equalsIgnoreCase("22")
				|| posicao.equalsIgnoreCase("26") || posicao.equalsIgnoreCase("30") || posicao.equalsIgnoreCase("34")
				|| posicao.equalsIgnoreCase("38") || posicao.equalsIgnoreCase("42")) {
			classe = "panel-stat3 bg-warning";
			return classe;
		} else {
			classe = "panel-stat3 bg-success";
			return classe;
		}
	}
  
	public boolean excluirArquivoFTP(Arquivo5 arquivo5) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("docs", caminho);
		S3ObjectSummary objectSummary = new S3ObjectSummary();
		objectSummary.setKey(arquivo5.getNomeftp());
		if (s3.delete(objectSummary)) {
			Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
			return true;
		} else {
			Mensagem.lancarMensagemInfo("Falha ao excluir", "");
			return false;
		}
	}

	public String voltarConsSubPasta4Arquivo3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta3", pasta3);
		return "consPasta4Arquivo3";
	}

	public String voltarConsSubPasta5Arquivo4() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta4", pasta4);
		return "consPasta5Arquivo4";
	}

	public void listarArquivoLista() {
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
	}

	public void listarArquivoQuadro() {
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
	}

	public boolean verificarArquivoLista(Arquivo5 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getIdarquivo5() != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	public String trocarCorLinhaTabela(Arquivo5 arquivo5) {
		String cor = "background:#FFFFFF;";
		if (arquivo5 != null) {
			if ((arquivo5.getIdarquivo5() % 2) == 0) {
				cor = "background:#FFFFFF;";
			} else {
				cor = "background:#F3F3F3";
			}
		}
		return cor;
	}

	public void salvarModoExibicao(String nomeExibicao) {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
			usuario.setModoexibicao(nomeExibicao);
			usuarioFacade.salvar(usuario);
			usuarioLogadoMB.setUsuario(usuario);
		}
	}

	public void verificarExibicao() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (usuarioLogadoMB.getUsuario().getModoexibicao() == null) {
				tabelaArquivoQuadro = true;
				tabelaArquivoLista = false;
			} else {
				if (usuarioLogadoMB.getUsuario().getModoexibicao().equalsIgnoreCase("I")) {
					tabelaArquivoQuadro = true;
					tabelaArquivoLista = false;
				} else {
					tabelaArquivoLista = true;
					tabelaArquivoQuadro = false;
				}
			}
		}

	}

	public String pesquisar() {
		boolean arquivosNovos = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tituloPagina = "Pesquisar Arquivos";
		pesquisar = true;
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
		gerarListaArquivos(departamento.getPasta1List(), departamento);
		session.setAttribute("arquivosNovos", arquivosNovos);
		session.setAttribute("listaArquivoVencidoBean", listaArquivoVencidoBean);
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta3", pasta3);
		session.setAttribute("pasta4", pasta4);
		session.setAttribute("pasta5", pasta5);
		return "listarArquivos";
	}

	public void gerarListaArquivos(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo5(pasta5.getArquivo5List(), departamento);

			}
		}
	}

	public void gerarListaArquivo5(List<Arquivo5> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						}
					}
				}
			}
			if (arquivosVencidosBean != null) {
				listaArquivoVencidoBean.add(arquivosVencidosBean);
			}
		}
	}

	public ArquivosVencidosBean adicionarArquivo5(Arquivo5 arquivo5, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo5.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo5.getPasta1().getNome() + "\\"
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\"
					+ arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo5.getPasta1().getNome() + "\\"
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\"
					+ arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public void limparPesquisa() {
		nomeArquivo = "";
		tipoArquivo = "TODAS";
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
	}
	
	
	public String vincularArquivoFornecedor(Arquivo5 arquivo5){
		String urlDocs = "consArquivo5";
		arquivosBean = new ArquivoBean();
		arquivosBean.setDatainicio(arquivo5.getDatainicio());
		arquivosBean.setDatavalidade(arquivo5.getDatavalidade());
		arquivosBean.setNome(arquivo5.getNome());
		arquivosBean.setNomeftp(arquivo5.getNomeftp());
		arquivosBean.setRestrito(arquivo5.isRestrito());
		arquivosBean.setTipo("Documento");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivosBean);
		session.setAttribute("pasta5", pasta5);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("arquivo5", arquivo5);
		return "vincularFonecedorDocs";
	}
	
	
	
	public boolean acessoVinculoFornecedor(Arquivo5 arquivo5){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo5 != null) {
				if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					acesso = true;
				}
			}
		}
		return acesso;
	}
	
	
	public String verificarvinculoFornecedor(Arquivo5 arquivo5){
		String cor = "";
		if (arquivo5 != null) {
			if (arquivo5.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Arquivo5 arquivo5){
		String cor = "../../resources/img/vincularUnidade.png";
		if (arquivo5 != null) {
			if (arquivo5.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}

}
