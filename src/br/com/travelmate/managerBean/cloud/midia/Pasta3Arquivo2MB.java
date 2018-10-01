package br.com.travelmate.managerBean.cloud.midia;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.Pasta3Facade;
import br.com.travelmate.facade.Pasta4Facade;
import br.com.travelmate.facade.Pasta5Facade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class Pasta3Arquivo2MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pasta2 pasta2;
	private Arquivo2 arquivo2;
	private List<Arquivo2> listaArquivo2;
	private Departamento departamento;
	private Pasta1 pasta1;
	private List<Arquivo3> listaArquivo3;
	private List<Pasta3> listaPasta3;
	private Pasta3 pasta3;
	private Arquivo3 arquivo3;
	private List<ArquivoSubPastaBean> listaArquivoSub;
	private ArquivoSubPastaBean arquivoBean;
	private boolean semPastasAquivos = false;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaArquivoLista = false;
	private boolean tabelaArquivoQuadro = true;
	private List<Arquivo2> listaArq2 = new ArrayList<>();
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
			pasta2 = (Pasta2) session.getAttribute("pasta2");
			session.removeAttribute("pasta2");
			if (pasta2 != null) {
				pasta1 = pasta2.getPasta1();
				departamento = pasta1.getDepartamento();
			}
			if (departamento != null && pasta1 != null && pasta2 != null) {
				gerarListaCloudSubPastaArquivo();
				gerarCloudSubSubPastas();
			}else{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			urlArquivo = "https://docs.systm.com.br";
			// Verifica se contém Pastas e Arquivos na tela
			semConteudo();
			verificarExibicao();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	
	public boolean isSemPastasAquivos() {
		return semPastasAquivos;
	}

	public void setSemPastasAquivos(boolean semPastasAquivos) {
		this.semPastasAquivos = semPastasAquivos;
	}

	public ArquivoSubPastaBean getArquivoBean() {
		return arquivoBean;
	}

	public void setArquivoBean(ArquivoSubPastaBean arquivoBean) {
		this.arquivoBean = arquivoBean;
	}

	public List<ArquivoSubPastaBean> getListaArquivoSub() {
		return listaArquivoSub;
	}

	public void setListaArquivoSub(List<ArquivoSubPastaBean> listaArquivoSub) {
		this.listaArquivoSub = listaArquivoSub;
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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	

	public boolean isTabelaArquivoLista() {
		return tabelaArquivoLista;
	}

	public void setTabelaArquivoLista(boolean tabelaArquivoLista) {
		this.tabelaArquivoLista = tabelaArquivoLista;
	}

	public boolean isTabelaArquivoQuadro() {
		return tabelaArquivoQuadro;
	}

	public void setTabelaArquivoQuadro(boolean tabelaArquivoQuadro) {
		this.tabelaArquivoQuadro = tabelaArquivoQuadro;
	}
	
	public List<Arquivo2> getListaArq2() {
		return listaArq2;
	}

	public void setListaArq2(List<Arquivo2> listaArq2) {
		this.listaArq2 = listaArq2;
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

	public ArquivoBean getArquivosBean() {
		return arquivosBean;
	}

	public void setArquivosBean(ArquivoBean arquivosBean) {
		this.arquivosBean = arquivosBean;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public void gerarListaCloudSubPastaArquivo() {
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		String sql = "";
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			int iddepartamento = pasta1.getDepartamento().getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo2 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2();
			} else {
				sql = "Select c from Arquivo2 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2() + " and c.datainicio <='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			sql = sql + " order by c.nome";
			listaArquivo2 = arquivo2Facade.listar(sql);
			if (listaArquivo2 == null) {
				listaArquivo2 = new ArrayList<Arquivo2>();
			} else {
				listaArquivoSub = new ArrayList<ArquivoSubPastaBean>();
			}
			for (int i = 0; i < listaArquivo2.size(); i++) {
				arquivoBean = new ArquivoSubPastaBean();
				arquivoBean.setArquivo1(listaArquivo2.get(i));
				if ((i + 1) < listaArquivo2.size()) {
					arquivoBean.setArquivo2(listaArquivo2.get(i + 1));
					i++;
					if ((i + 1) < listaArquivo2.size()) {
						arquivoBean.setArquivo3(listaArquivo2.get(i + 1));
						i++;
					} else {
						arquivoBean.setArquivo3(null);
					}
	
				} else {
					arquivoBean.setArquivo2(null);
					arquivoBean.setArquivo3(null);
				}
	
				listaArquivoSub.add(arquivoBean);
			}
		}
	}

	public void gerarCloudSubSubPastas() {
		Pasta3Facade pasta3Facade = new Pasta3Facade();
		String sql = "Select c from Pasta3 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.restrito=0 ";
		}
		sql = sql +  " order by c.nome";
		listaPasta3 = pasta3Facade.listar(sql);
		if (listaPasta3 == null) {
			listaPasta3 = new ArrayList<Pasta3>();
		}
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

	public String voltarConsDepartamento() {
		return "consDepartamentos";
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

	public Integer gerarTotalArquivosSubPastas(Pasta3 pasta3) {
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		Integer numeroTotal = 0;
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		}else{
			int iddepartamento = pasta1.getDepartamento().getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo3 c where c.pasta3.idpasta3=" + pasta3.getIdpasta3();
			} else {
				sql = "Select c from Arquivo3 c where c.pasta3.idpasta3=" + pasta3.getIdpasta3() + " and c.datainicio <='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			listaArquivo3 = arquivo3Facade.listar(sql);
			if (listaArquivo3 == null || listaArquivo3.isEmpty()) {
				listaArquivo3 = new ArrayList<>();
				numeroTotal = listaArquivo3.size();
				return numeroTotal;
			} else {
				numeroTotal = listaArquivo3.size();
				return numeroTotal;
			}
		}
	}

	public List<Arquivo3> gerar3UltimosArquivo3(Pasta3 pasta3) {
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		List<Arquivo3> listaArquivo3 = null;
		if (listaArquivo3 == null) {
			listaArquivo3 = new ArrayList<Arquivo3>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaArquivo3;
		}else{
			int idDepartamento = departamento.getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == idDepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select csspa from Arquivo3 csspa where csspa.pasta3.idpasta3=" + pasta3.getIdpasta3();
			} else {
				sql = "Select csspa from Arquivo3 csspa  where csspa.pasta3.idpasta3=" + pasta3.getIdpasta3() + " and csspa.datainicio <='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and (csspa.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or csspa.datavalidade is null) "
						;
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and csspa.restrito=0 ";
			}
			sql = sql + " order by csspa.idarquivo3 DESC ";
			List<Arquivo3> listaArquivos3 = arquivo3Facade.listar(sql);
			if (listaArquivos3 == null) {
				listaArquivos3 = new ArrayList<Arquivo3>();
				return listaArquivos3;
			} else {
				for (int i = 0; i < listaArquivos3.size(); i++) {
					if (listaArquivo3.size() < 3) {
						listaArquivo3.add(listaArquivos3.get(i));
					}
				}
				return listaArquivo3;
			}
		}
	}

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

	public String pasta4Arquivo3(Pasta3 pasta3) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta3", pasta3);
		return "consPasta4Arquivo3";
	}

	public String cadastroPasta3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pasta2", pasta2);
		RequestContext.getCurrentInstance().openDialog("cadPasta3", options, null);
		return "";
	}

	public String cadastroArquivo2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta2", pasta2);
		RequestContext.getCurrentInstance().openDialog("cadArquivo2", options, null);
		return "";
	}

	public void retornoDialogNovaPasta(SelectEvent event) {
		Pasta3 pasta3 = (Pasta3) event.getObject();
		if (pasta3.getIdpasta3() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarCloudSubSubPastas();
		semConteudo();
	}

	@SuppressWarnings("unchecked")
	public void retornoDialogNovoArquivo(SelectEvent event) {
		List<GerarAvisosDocsBean> listaAviso =  (List<GerarAvisosDocsBean>) event.getObject();
		if (listaAviso.size() > 0) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			new AvisoArquivoBean(listaAviso);
		}
		gerarListaCloudSubPastaArquivo();
		semConteudo();
	}

	// Verifica se contém Pastas e Arquivos na tela
	public void semConteudo() {
		if ((listaPasta3 == null || listaPasta3.isEmpty()) && (listaArquivo2 == null || listaArquivo2.isEmpty())) {
			semPastasAquivos = true;
		} else {
			semPastasAquivos = false;

		}
	}

	public boolean excluirArquivoFTP(Arquivo2 arquivo2) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("docs", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey(arquivo2.getNomeftp());
			if(s3.delete(objectSummary)) {
				Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
				return true;
			}else {
				Mensagem.lancarMensagemInfo("Falha ao excluir", "");
				return false;
			}
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivoSubPasta(Arquivo2 arquivo2) {
		excluirArquivoFTP(arquivo2);
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		arquivo2Facade.excluir(arquivo2.getIdarquivo2());
		gerarCloudSubSubPastas();
		gerarListaCloudSubPastaArquivo();
		return "";
	}

	public String alterarNomeArquivo(Arquivo2 arquivo2) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("arquivo2", arquivo2);
		RequestContext.getCurrentInstance().openDialog("editarArquivo2", options, null);
		return "";
	}

	public void retornoDialogAlteracaoNomeArquivo(SelectEvent event) {
		Arquivo2 arquivo2 = (Arquivo2) event.getObject();
		if (arquivo2.getIdarquivo2() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarCloudSubSubPastas();
		gerarListaCloudSubPastaArquivo();
		semConteudo();
	}

	public void excluirPasta(Pasta3 pasta3) {
		Pasta3Facade pasta3Facade = new Pasta3Facade();
		excluirIntens5(pasta3);
		excluirItens4(pasta3);
		excluirItens3(pasta3);
		pasta3Facade.excluir(pasta3.getIdpasta3());
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		gerarCloudSubSubPastas();
	}
	
	public void excluirIntens5(Pasta3 pasta3){
		Pasta5Facade pasta5Facade = new Pasta5Facade();
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		List<Arquivo5> listaArquivo5 = arquivo5Facade.listar("Select c from Arquivo5 c Where c.pasta3.idpasta3="+ pasta3.getIdpasta3());
		if (listaArquivo5 != null) {
			for (int i = 0; i < listaArquivo5.size(); i++) {
				excluirTodosArquivoFTP(listaArquivo5.get(i).getNomeftp());
				arquivo5Facade.excluir(listaArquivo5.get(i).getIdarquivo5());
			}
		}
		List<Pasta5> listaPasta5 = pasta5Facade.listar("Select c From Pasta5 c Where c.pasta3.idpasta3=" + pasta3.getIdpasta3());
		if (listaPasta5 != null) {
			for (int i = 0; i < listaPasta5.size(); i++) {
				pasta5Facade.excluir(listaPasta5.get(i).getIdpasta5());
			}
		}
	}
	
	public void excluirItens4(Pasta3 pasta3){
		Pasta4Facade pasta4Facade = new Pasta4Facade();
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		List<Arquivo4> listaArquivo4 = arquivo4Facade
				.listar("Select c from Arquivo4 c where c.pasta3.idpasta3=" + pasta3.getIdpasta3());
		if (listaArquivo4 != null) {
			for (int i = 0; i < listaArquivo4.size(); i++) {
				excluirTodosArquivoFTP(listaArquivo4.get(i).getNomeftp());
				arquivo4Facade.excluir(listaArquivo4.get(i).getIdarquivo4());
			}
		}
		List<Pasta4> listaPasta4 = pasta4Facade
				.listar("Select c from Pasta4 c where c.pasta3.idpasta3=" + pasta3.getIdpasta3());
		if (listaPasta4 != null) {
			for (int i = 0; i < listaPasta4.size(); i++) {
				pasta4Facade.excluir(listaPasta4.get(i).getIdpasta4());
			}
		}
	}
	
	public void excluirItens3(Pasta3 pasta3){
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		List<Arquivo3> listaArquivo3 = arquivo3Facade
				.listar("Select c from Arquivo3 c where c.pasta3.idpasta3=" + pasta3.getIdpasta3());
		if (listaArquivo3 != null) {
			for (int i = 0; i < listaArquivo3.size(); i++) {
				excluirTodosArquivoFTP(listaArquivo3.get(i).getNomeftp());
				arquivo3Facade.excluir(listaArquivo3.get(i).getIdarquivo3());
			}
		}
	}
	
	public boolean excluirTodosArquivoFTP(String nomeArquivo) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("docs", caminho);
		S3ObjectSummary objectSummary = new S3ObjectSummary();
		objectSummary.setKey(nomeArquivo);
		if (s3.delete(objectSummary)) {
			Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
			return true;
		} else {
			Mensagem.lancarMensagemInfo("Falha ao excluir", "");
			return false;
		}
	}


	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (departamento != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento.getIddepartamento()) {
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

	public boolean verificarArquivo1(ArquivoSubPastaBean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getArquivo1() == null) {
				return acesso;
			} else {
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
				return acesso;
			}
		}
		return acesso;
	}

	public boolean verificarArquivo2(ArquivoSubPastaBean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getArquivo2() == null) {
				return acesso;
			} else {
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
				return acesso;
			}
		}
		return acesso;
	}

	public boolean verificarArquivo3(ArquivoSubPastaBean arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getArquivo3() == null) {
				return acesso;
			} else {
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
				return acesso;
			}
		}
		return acesso;
	}

//	public boolean verificarArquivo4(ArquivoSubPastaBean arquivo) {
//		Boolean acesso = false;
//		if (arquivo.getArquivo4() == null) {
//			return acesso;
//		} else {
//			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
//					.getIddepartamento()) {
//				acesso = true;
//			} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
//				acesso = true;
//				if (departamento.getIddepartamento() == 1) {
//					acesso = false;
//				}
//			}
//			return acesso;
//		}
//	}

	public String editarPasta3(Pasta3 pasta3) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta3", pasta3);
		RequestContext.getCurrentInstance().openDialog("cadPasta3", options, null);
		return "";
	}  

	public Integer gerarTotalPasta4(Pasta3 pasta3) {
		Pasta4Facade pasta4Facade = new Pasta4Facade();
		Integer numeroTotal = 0;
		List<Pasta4> listaPasta4 = pasta4Facade
				.listar("Select c from Pasta4 c where c.pasta3.idpasta3=" + pasta3.getIdpasta3());
		if (listaPasta4 == null) {
			listaPasta4 = new ArrayList<Pasta4>();
			numeroTotal = listaPasta4.size();
			return numeroTotal;
		} else {
			numeroTotal = listaPasta4.size();
			return numeroTotal;
		}
	}
	
	
	public String trocarCorLinhaTabela(Arquivo2 arquivo2){
		String cor = "background:#FFFFFF;";
		if (arquivo2 != null) {
			if ((arquivo2.getIdarquivo2() % 2) == 0) {
				cor = "background:#FFFFFF;";
			}else{
				cor = "background:#F3F3F3";
			}	
		}
		return cor;
	}
	
	
	public void listarArquivoLista(){
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
	}
	
	public void listarArquivoQuadro(){
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
	}

	
	public boolean verificarArquivoLista(Arquivo2 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getIdarquivo2() != null) {
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
	
	
	public void salvarModoExibicao(String nomeExibicao){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			usuarioLogadoMB.getUsuario().setModoexibicao(nomeExibicao);
			usuarioFacade.salvar(usuarioLogadoMB.getUsuario());
		}
	}
	
	
	public void verificarExibicao(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (usuarioLogadoMB.getUsuario().getModoexibicao() == null) {
				tabelaArquivoQuadro = true;
				tabelaArquivoLista = false;
			}else{
				if (usuarioLogadoMB.getUsuario().getModoexibicao().equalsIgnoreCase("I")) {
					tabelaArquivoQuadro = true;
					tabelaArquivoLista = false;
				}else{
					tabelaArquivoLista = true;
					tabelaArquivoQuadro = false;
				}
			}
		}
	}
	
	
	public String pesquisar(){
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
		return "listarArquivos";
	}  
	
	
	public void gerarListaArquivos(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo2(pasta2.getArquivo2List(), departamento);
				gerarListaArquivo3(pasta2.getArquivo3List(), departamento);
				gerarListaArquivo4(pasta2.getArquivo4List(), departamento);
				gerarListaArquivo5(pasta2.getArquivo5List(), departamento);
		
			}
		}
	}   
		

		
		public void gerarListaArquivo2(List<Arquivo2> lista, Departamento departamento){
			for (int i = 0; i < lista.size(); i++) {
				arquivosVencidosBean = null;
				Date dataAtual = new Date();
				if (lista.get(i).getDatavalidade() != null) {
					Date dataArquivo = lista.get(i).getDatavalidade();
					if (dataArquivo.after(dataAtual) || dataArquivo.equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}
							}
						}
					}
				}else{
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}
							}
						}
					}else{
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							}
						}
					}
				}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
			}   
		}
		
		public void gerarListaArquivo3(List<Arquivo3> lista, Departamento departamento){
			for (int i = 0; i < lista.size(); i++) {
				arquivosVencidosBean = null;
				Date dataAtual = new Date();
				if (lista.get(i).getDatavalidade() != null) {	
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}
							}
						}
					}
				}else{
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}
							}
						}
					}else{
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							}
						}
					}
				}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
			} 
		}
		
		public void gerarListaArquivo4(List<Arquivo4> lista, Departamento departamento){
			for (int i = 0; i < lista.size(); i++) {
				arquivosVencidosBean = null;
				Date dataAtual = new Date();
				if (lista.get(i).getDatavalidade() != null) {	
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}
							}
						}
					}
				}else{
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Uniade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}
							}
						}
					}else{
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}
						}
					}
				}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
			} 
		}
		
		public void gerarListaArquivo5(List<Arquivo5> lista, Departamento departamento){
			for (int i = 0; i < lista.size(); i++) {
				arquivosVencidosBean = null;
				Date dataAtual = new Date();
				if (lista.get(i).getDatavalidade() != null) {	
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}
							}
						}
					}
				}else{
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}
							}
						}
					}else{
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
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
		
		
		public ArquivosVencidosBean adicionarArquivo2(Arquivo2 arquivo2, Departamento departamento){
			ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
			if (arquivo2.getDatavalidade() == null) {
				arquivoBean.setNome(arquivo2.getNome());
				arquivoBean.setPasta("2");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo2.getPasta1().getNome() + "\\" 
						+ arquivo2.getPasta2().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo2.getIdarquivo2());
				arquivoBean.setNomeFtp(arquivo2.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}else{
				arquivoBean.setNome(arquivo2.getNome());
				arquivoBean.setPasta("2");
				arquivoBean.setData(arquivo2.getDatavalidade());
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo2.getPasta1().getNome() + "\\" 
						+ arquivo2.getPasta2().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo2.getIdarquivo2());
				arquivoBean.setNomeFtp(arquivo2.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}
			return arquivoBean;
		}
		
		
		public ArquivosVencidosBean adicionarArquivo3(Arquivo3 arquivo3, Departamento departamento){
			ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
			if (arquivo3.getDatavalidade() == null) {
				arquivoBean.setNome(arquivo3.getNome());
				arquivoBean.setPasta("3");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo3.getPasta1().getNome() + "\\" 
						+ arquivo3.getPasta2().getNome() + "\\" + arquivo3.getPasta3().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo3.getIdarquivo3());
				arquivoBean.setNomeFtp(arquivo3.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}else{
				arquivoBean.setNome(arquivo3.getNome());
				arquivoBean.setData(arquivo3.getDatavalidade());
				arquivoBean.setPasta("3");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo3.getPasta1().getNome() + "\\" 
						+ arquivo3.getPasta2().getNome() + "\\" + arquivo3.getPasta3().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo3.getIdarquivo3());
				arquivoBean.setNomeFtp(arquivo3.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}
			return arquivoBean;
		}
		
		
		public ArquivosVencidosBean adicionarArquivo4(Arquivo4 arquivo4, Departamento departamento){
			ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
			if (arquivo4.getDatavalidade() == null) {
				arquivoBean.setNome(arquivo4.getNome());
				arquivoBean.setData(arquivo4.getDatavalidade());
				arquivoBean.setPasta("4");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo4.getPasta1().getNome() + "\\" 
						+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
						+ arquivo4.getPasta4().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
				arquivoBean.setNomeFtp(arquivo4.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}else{
				arquivoBean.setNome(arquivo4.getNome());
				arquivoBean.setData(arquivo4.getDatavalidade());
				arquivoBean.setPasta("4");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo4.getPasta1().getNome() + "\\" 
						+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
						+ arquivo4.getPasta4().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
				arquivoBean.setNomeFtp(arquivo4.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}
			return arquivoBean;
		}
		
		
		public ArquivosVencidosBean adicionarArquivo5(Arquivo5 arquivo5, Departamento departamento){
			ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
			if (arquivo5.getDatavalidade() == null) {
				arquivoBean.setNome(arquivo5.getNome());
				arquivoBean.setData(arquivo5.getDatavalidade());
				arquivoBean.setPasta("5");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo5.getPasta1().getNome() + "\\" 
						+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\" +
						arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
				arquivoBean.setNomeFtp(arquivo5.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}else{
				arquivoBean.setNome(arquivo5.getNome());
				arquivoBean.setData(arquivo5.getDatavalidade());
				arquivoBean.setPasta("5");
				arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo5.getPasta1().getNome() + "\\" 
						+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\" +
						arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
				arquivoBean.setNomeFtp(arquivo5.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}
			return arquivoBean;
		}
		
		
		public void limparPesquisa(){
			nomeArquivo = "";
			tipoArquivo = "TODAS";
			listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
		}
	
		
		public String vincularArquivoFornecedor(Arquivo2 arquivo2){
			String urlDocs = "consPasta3Arquivo2";
			arquivosBean = new ArquivoBean();
			arquivosBean.setDatainicio(arquivo2.getDatainicio());
			arquivosBean.setDatavalidade(arquivo2.getDatavalidade());
			arquivosBean.setNome(arquivo2.getNome());
			arquivosBean.setNomeftp(arquivo2.getNomeftp());
			arquivosBean.setRestrito(arquivo2.isRestrito());
			arquivosBean.setTipo("Documento");
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("arquivoBean", arquivosBean);
			session.setAttribute("pasta2", pasta2);
			session.setAttribute("urlDocs", urlDocs);
			session.setAttribute("arquivo2", arquivo2);
			return "vincularFonecedorDocs";
		}
		
		
		
		public boolean acessoVinculoFornecedor(Arquivo2 arquivo2){
			Boolean acesso = false;
			if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
				if (arquivo2 != null) {
					if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						acesso = true;
					}
				}
			}
			return acesso;
		}
		
		
		public String verificarvinculoFornecedor(Arquivo2 arquivo2){
			String cor = "";
			if (arquivo2 != null) {
				if (arquivo2.isCopiado()) {
					cor = "color:#6495ED;";
				}
			}
			return cor;
		}
		
		
		public String verificarvinculoFornecedorLista(Arquivo2 arquivo2){
			String cor = "../../resources/img/vincularUnidade.png";
			if (arquivo2 != null) {
				if (arquivo2.isCopiado()) {
					cor = "../../resources/img/iconeVinculadoFornecedor.png";
				}
			}
			return cor;
		}
	
}
