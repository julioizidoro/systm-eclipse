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
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.Pasta2Facade;
import br.com.travelmate.facade.Pasta3Facade;
import br.com.travelmate.facade.Pasta4Facade;
import br.com.travelmate.facade.Pasta5Facade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class Pastas2Arquivo1MB implements Serializable {

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
	private ArquivoPastaBean arquivoPastaBean;
	private List<ArquivoPastaBean> listaArquivoBean;
	private boolean semPastaArquivo = false;
	private Ftpdados ftpDados;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaArquivoLista = false;
	private boolean tabelaArquivoQuadro = true;
	private boolean pesquisar;
	private String tituloPagina;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private String tipoArquivo;
	private String nomeArquivo;
	private ArquivoBean arquivoBean;
	private String urlArquivo = "";

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			pasta1 = (Pasta1) session.getAttribute("pasta1");
			session.removeAttribute("pasta1");
			if (pasta1 != null) {
				departamento = pasta1.getDepartamento();
			}
			if (pasta1!=null && departamento != null){
				if (pasta1.getDepartamento()!=null){
					gerarCloudFiles();
					gerarCloudSubPastas();
					ftpDados = new Ftpdados();
					FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
					try {
						ftpDados = ftpDadosFacade.getFTPDados();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					if (ftpDados != null) {
						urlArquivo = ftpDados.getProtocolo() + "://" + ftpDados.getHost() +  ":" + ftpDados.getWww() + "/cloud/departamentos";
					}
					// Verificar se existe pastas ou arquivos na tela
					semConteudo();
					verificarExibicao();
				}
			}else{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public boolean isSemPastaArquivo() {
		return semPastaArquivo;
	}

	public void setSemPastaArquivo(boolean semPastaArquivo) {
		this.semPastaArquivo = semPastaArquivo;
	}

	public List<ArquivoPastaBean> getListaArquivoBean() {
		return listaArquivoBean;
	}

	public void setListaArquivoBean(List<ArquivoPastaBean> listaArquivoBean) {
		this.listaArquivoBean = listaArquivoBean;
	}

	public ArquivoPastaBean getArquivoPastaBean() {
		return arquivoPastaBean;
	}

	public void setArquivoPastaBean(ArquivoPastaBean arquivoPastaBean) {
		this.arquivoPastaBean = arquivoPastaBean;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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

	public ArquivoBean getArquivoBean() {
		return arquivoBean;
	}

	public void setArquivoBean(ArquivoBean arquivoBean) {
		this.arquivoBean = arquivoBean;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public void gerarCloudFiles() {
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		int idDepartamento = pasta1.getDepartamento().getIddepartamento();
		String sql = "";
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == idDepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo1 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1();
			} else {
				sql = "Select c from Arquivo1 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1() + " and c.datainicio<='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			sql = sql + " order by c.nome";
			listaArquivo1 = arquivo1Facade.listar(sql);
			if (listaArquivo1 == null) {
				listaArquivo1 = new ArrayList<Arquivo1>();
			} else {
				listaArquivoBean = new ArrayList<ArquivoPastaBean>();
			}
			for (int i = 0; i < listaArquivo1.size(); i++) {
				arquivoPastaBean = new ArquivoPastaBean();
				arquivoPastaBean.setArquivo1(listaArquivo1.get(i));
				if ((i + 1) < listaArquivo1.size()) {
					arquivoPastaBean.setArquivo2(listaArquivo1.get(i + 1));
					i++;
					if ((i + 1) < listaArquivo1.size()) {
						arquivoPastaBean.setArquivo3(listaArquivo1.get(i + 1));
						i++;
					} else {
						arquivoPastaBean.setArquivo3(null);
					}
	
				} else {
					arquivoPastaBean.setArquivo2(null);
					arquivoPastaBean.setArquivo3(null);
				}
	
				listaArquivoBean.add(arquivoPastaBean);
			}
		}
	}

	public void gerarCloudSubPastas() {
		Pasta2Facade pasta2Facade = new Pasta2Facade();
		String sql = "Select c from Pasta2 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.restrito=0 ";
		}
		sql = sql +  " order by c.nome"; 
		listaPasta2 = pasta2Facade.listar(sql);
		if (listaPasta2 == null) {
			listaPasta2 = new ArrayList<Pasta2>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
		}
	}

	public String pegarNomeArquivo(Arquivo1 arquivo1) {
		if (arquivo1 != null) {
			String nome = arquivo1.getNome();
			return nome;
		}
		return "";
	}

	public String voltarConsPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1";
	}

	public String alterarNomeArquivo(Arquivo1 arquivo1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("arquivo1", arquivo1);
		RequestContext.getCurrentInstance().openDialog("editarArquivo1", options, null);
		return "";
	}

	public String identificarExtensao(String nomeArquivo) {
		String extensao = "";
		for (int i = 0; i < nomeArquivo.length(); i++) {
			if (nomeArquivo.charAt(nomeArquivo.length() - i) != '.') {
				extensao = nomeArquivo.charAt(nomeArquivo.length() - i) + extensao;
			} else {
				i = nomeArquivo.length() + 100;
			}
		}
		if (extensao.length() > 4) {
			extensao = "pdf";
		}
		if (extensao.equalsIgnoreCase("jpg") || extensao.equalsIgnoreCase("jpeg") || extensao.equalsIgnoreCase("png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase("pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase("docx") || extensao.equalsIgnoreCase("doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase("xls") || extensao.equalsIgnoreCase("xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (extensao.equalsIgnoreCase("ai")) {
			return "../../resources/img/icone_ai.png";
		} else if (extensao.equalsIgnoreCase("psd")) {
			return "../../resources/img/icone_psd.png";
		}
		return "";
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
	
	


	public Integer gerarTotalArquivosSubPastas(Pasta2 pasta2) {
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		Integer numeroTotal = 0;
		int idDepartamento = pasta1.getDepartamento().getIddepartamento();
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		}else{
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == idDepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo2 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2();
			} else {
				sql = "Select c from Arquivo2 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2() + " and c.datainicio<='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			listaArquivo2 = arquivo2Facade.listar(sql);
			if (listaArquivo2 == null) {
				listaArquivo2 = new ArrayList<>();
				numeroTotal = listaArquivo2.size();
				return numeroTotal;
			} else {
				numeroTotal = listaArquivo2.size();
				return numeroTotal;
			}
		}
	}

	public Integer gerarTotalSubPastas(Pasta2 pasta2) {
		Pasta3Facade pasta3Facade = new Pasta3Facade();
		Integer numeroTotal = 0;
		String sql = "Select c from Pasta3 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.restrito=0 ";
		}
		List<Pasta3> listaPasta3 = pasta3Facade.listar(sql);
		if (listaPasta3 == null) {
			listaPasta3 = new ArrayList<>();
			numeroTotal = listaPasta3.size();
			return numeroTotal;
		} else {
			numeroTotal = listaPasta3.size();
			return numeroTotal;
		}
	}

	public List<Arquivo2> gerar3UltimosArquivosSubPasta(Pasta2 pasta2) {
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		List<Arquivo2> listaArquivo2 = null;
		if (listaArquivo2 == null) {
			listaArquivo2 = new ArrayList<Arquivo2>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaArquivo2;
		}else{
			int idDepartamento = departamento.getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == idDepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select cspa from Arquivo2 cspa Where cspa.pasta2.idpasta2=" + pasta2.getIdpasta2();
			} else {
				sql = "Select cspa from Arquivo2 cspa Where  cspa.pasta2.idpasta2=" + pasta2.getIdpasta2()
						+ " and cspa.datainicio <='" + Formatacao.ConvercaoDataSql(new Date()) + "'"
						+ "  and (cspa.datavalidade>='" + Formatacao.ConvercaoDataSql(new Date())
						+ "' or cspa.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and cspa.restrito=0 ";
			}
			sql = sql + "  order by cspa.idarquivo2 DESC";
			List<Arquivo2> listaArquivos2 = arquivo2Facade.listar(sql);
			if (listaArquivos2 == null || listaArquivos2.isEmpty()) {
				listaArquivos2 = new ArrayList<Arquivo2>();
				return listaArquivos2;
			} else {
				for (int i = 0; i < listaArquivos2.size(); i++) {
					if (listaArquivo2.size() < 3) {
						listaArquivo2.add(listaArquivos2.get(i));
					}
				}  
				return listaArquivo2;
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

	public String pasta3Arquivo2(Pasta2 pasta2) {
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

	public String cadastroPasta2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pasta1", pasta1);
		RequestContext.getCurrentInstance().openDialog("cadPasta2", options, null);
		return "";
	}

	public String cadastroArquivo1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta1", pasta1);
		RequestContext.getCurrentInstance().openDialog("cadArquivo1", options, null);
		return "";
	}

	public void retornoDialogNovaPasta(SelectEvent event) {
		Pasta2 pasta2 = (Pasta2) event.getObject();
		if (pasta2.getIdpasta2() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarCloudSubPastas();
		semConteudo();
	}

	public void retornoDialogAlteracaoNomeArquivo(SelectEvent event) {
		Arquivo1 arquivo1 = (Arquivo1) event.getObject();
		if (arquivo1.getIdArquivo1() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarCloudSubPastas();
		gerarCloudFiles();
		semConteudo();
	}

    @SuppressWarnings("unchecked")
	public void retornoDialogNovoArquivo(SelectEvent event) {
		List<GerarAvisosDocsBean> lisaAvisos =  (List<GerarAvisosDocsBean>) event.getObject();
		if (lisaAvisos.size() > 0) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			new AvisoArquivoBean(lisaAvisos);
		}
		gerarCloudFiles();
		semConteudo();
	}
	
	public String gerarCaminhoArquivo(Arquivo1 arquivo){
		String caminho = arquivo.getPasta1().getDepartamento().getNome() + "\\" + arquivo.getPasta1().getNome() + "\\" + arquivo.getNome();
		return caminho;
	}

	// Verificar se existe pastas ou arquivos na tela
	public void semConteudo() {
		if ((listaArquivo1 == null || listaArquivo1.isEmpty()) && (listaPasta2 == null || listaPasta2.isEmpty())) {
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;

		}
	}

	public boolean excluirArquivoFTP(Arquivo1 arquivo1) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = arquivo1.getNomeftp();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/cloud/departamentos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo(Arquivo1 arquivo1) {
		excluirArquivoFTP(arquivo1);
		Arquivo1Facade cloudFilesFacade = new Arquivo1Facade();
		cloudFilesFacade.excluir(arquivo1.getIdArquivo1());
		gerarCloudSubPastas();
		gerarCloudFiles();
		return "";
	}

	public void excluirPasta(Pasta2 pasta2) {
		Pasta2Facade pasta2Facade = new Pasta2Facade();
		excluirIntens5(pasta2);
		excluirItens4(pasta2);
		excluirItens3(pasta2);
		excluirItens2(pasta2);
		pasta2Facade.excluir(pasta2.getIdpasta2());
		gerarCloudSubPastas();
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
	}
	
	public void excluirIntens5(Pasta2 pasta2){
		Pasta5Facade pasta5Facade = new Pasta5Facade();
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		List<Arquivo5> listaArquivo5 = arquivo5Facade.listar("Select c from Arquivo5 c Where c.pasta2.idpasta2="+ pasta2.getIdpasta2());
		if (listaArquivo5 != null) {
			for (int i = 0; i < listaArquivo5.size(); i++) {
				excluirArquivoSubFTP(listaArquivo5.get(i).getNomeftp());
				arquivo5Facade.excluir(listaArquivo5.get(i).getIdarquivo5());
			}
		}
		List<Pasta5> listaPasta5 = pasta5Facade.listar("Select c From Pasta5 c Where c.pasta2.idpasta2=" + pasta2.getIdpasta2());
		if (listaPasta5 != null) {
			for (int i = 0; i < listaPasta5.size(); i++) {
				pasta5Facade.excluir(listaPasta5.get(i).getIdpasta5());
			}
		}
	}
	
	public void excluirItens4(Pasta2 pasta2){
		Pasta4Facade pasta4Facade = new Pasta4Facade();
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		List<Arquivo4> listaArquivo4 = arquivo4Facade
				.listar("Select c from Arquivo4 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2());
		if (listaArquivo4 != null) {
			for (int i = 0; i < listaArquivo4.size(); i++) {
				excluirArquivoSubFTP(listaArquivo4.get(i).getNomeftp());
				arquivo4Facade.excluir(listaArquivo4.get(i).getIdarquivo4());
			}
		}
		List<Pasta4> listaPasta4 = pasta4Facade
				.listar("Select c from Pasta4 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2());
		if (listaPasta4 != null) {
			for (int i = 0; i < listaPasta4.size(); i++) {
				pasta4Facade.excluir(listaPasta4.get(i).getIdpasta4());
			}
		}
	}
	
	public void excluirItens3(Pasta2 pasta2){
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		Pasta3Facade pasta3Facade = new Pasta3Facade();
		List<Arquivo3> listaArquivo3 = arquivo3Facade
				.listar("Select c from Arquivo3 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2());
		if (listaArquivo3 != null) {
			for (int i = 0; i < listaArquivo3.size(); i++) {
				excluirArquivoSubFTP(listaArquivo3.get(i).getNomeftp());
				arquivo3Facade.excluir(listaArquivo3.get(i).getIdarquivo3());
			}
		}
		
		List<Pasta3> listaPasta3 = pasta3Facade.listar("Select c From Pasta3 c Where c.pasta2.idpasta2="+ pasta2.getIdpasta2());
		if (listaPasta3 != null) {
			for (int i = 0; i < listaPasta3.size(); i++) {
				pasta3Facade.excluir(listaPasta3.get(i).getIdpasta3());
			}
		}
		
	}
	
	public void excluirItens2(Pasta2 pasta2){
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		List<Arquivo2> listaArquivo2 = arquivo2Facade
				.listar("Select c from Arquivo2 c where c.pasta2.idpasta2=" + pasta2.getIdpasta2());
		if (listaArquivo2 != null) {
			for (int i = 0; i < listaArquivo2.size(); i++) {
				excluirArquivoSubFTP(listaArquivo2.get(i).getNomeftp());
				arquivo2Facade.excluir(listaArquivo2.get(i).getIdarquivo2());
			}	
		}
	}
	
	public boolean excluirArquivoSubFTP(String nomeArquivo) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			msg = ftp.excluirArquivo(nomeArquivo, "/cloud/departamentos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pasta5Arquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
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

	public boolean verificarArquivo1(ArquivoPastaBean arquivo) {
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

	public boolean verificarArquivo2(ArquivoPastaBean arquivo) {
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

	public boolean verificarArquivo3(ArquivoPastaBean arquivo) {
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

//	public boolean verificarArquivo4(ArquivoPastaBean arquivo) {
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

	public String editarPasta2(Pasta2 pasta2) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta1", pasta1);
		RequestContext.getCurrentInstance().openDialog("cadPasta2", options, null);
		return "";
	}
	
	
	public void listarArquivoLista(){
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
		salvarModoExibicao("L");
	}
	
	public void listarArquivoQuadro(){
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
		salvarModoExibicao("I");
	}

	
	public boolean verificarArquivoLista(Arquivo1 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (arquivo.getIdarquivo1() != null) {
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
	
	
	public String trocarCorLinhaTabela(Arquivo1 arquivo1){
		String cor = "background:#FFFFFF;";
		if (arquivo1 != null) {
			if ((arquivo1.getIdarquivo1() % 2) == 0) {
				cor = "background:#FFFFFF;";
			}else{
				cor = "background:#F3F3F3";
			}	
		}
		return cor;
	}
	
	
	public void salvarModoExibicao(String nomeExibicao){
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
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
		return "listarArquivos";
	}  
	
	
	public void gerarListaArquivos(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo1(pasta1.getArquivo1List(), departamento);
				gerarListaArquivo2(pasta1.getArquivo2List(), departamento);
				gerarListaArquivo3(pasta1.getArquivo3List(), departamento);
				gerarListaArquivo4(pasta1.getArquivo4List(), departamento);
				gerarListaArquivo5(pasta1.getArquivo5List(), departamento);
		
			}
		}
	}   
		
		
		public void gerarListaArquivo1(List<Arquivo1> lista, Departamento departamento) {
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
										arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
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
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}
							}
						}
					}else{
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							}
						}
					}
				}
				if (arquivosVencidosBean != null) {
					listaArquivoVencidoBean.add(arquivosVencidosBean);
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
		
		
		public ArquivosVencidosBean adicionarArquivo1(Arquivo1 arquivo1, Departamento departamento){
			ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
			if (arquivo1.getDatavalidade() == null) {
				arquivoBean.setNome(arquivo1.getNome());
				arquivoBean.setPasta("1");
				arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo1.getPasta1().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo1.getIdArquivo1());
				arquivoBean.setNomeFtp(arquivo1.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}else{
				arquivoBean.setNome(arquivo1.getNome());
				arquivoBean.setData(arquivo1.getDatavalidade());
				arquivoBean.setPasta("1");
				arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo1.getPasta1().getNome() + "\\");
				arquivoBean.setnArquivo(arquivo1.getIdArquivo1());
				arquivoBean.setNomeFtp(arquivo1.getNomeftp());
				arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
			}
			return arquivoBean;
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
		
		
		public String vincularArquivoFornecedor(Arquivo1 arquivo1){
			String urlDocs = "consPasta2Arquivo1";
			arquivoBean = new ArquivoBean();
			arquivoBean.setDatainicio(arquivo1.getDatainicio());
			arquivoBean.setDatavalidade(arquivo1.getDatavalidade());
			arquivoBean.setNome(arquivo1.getNome());
			arquivoBean.setNomeftp(arquivo1.getNomeftp());
			arquivoBean.setRestrito(arquivo1.isRestrito());
			arquivoBean.setTipo("Documento");
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("arquivoBean", arquivoBean);
			session.setAttribute("pasta1", pasta1);
			session.setAttribute("urlDocs", urlDocs);
			session.setAttribute("arquivo1", arquivo1);
			return "vincularFonecedorDocs";
		}
		
		
		public boolean acessoVinculoFornecedor(Arquivo1 arquivo1){
			Boolean acesso = false;
			if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
				if (arquivo1 != null) {
					if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						acesso = true;
					}
				}
			}
			return acesso;
		}
		
		
		public String verificarvinculoFornecedor(Arquivo1 arquivo1){
			String cor = "";
			if (arquivo1 != null) {
				if (arquivo1.isCopiado()) {
					cor = "color:#6495ED;";
				}
			}
			return cor;
		}
		
		
		public String verificarvinculoFornecedorLista(Arquivo1 arquivo1){
			String cor = "../../resources/img/vincularUnidade.png";
			if (arquivo1 != null) {
				if (arquivo1.isCopiado()) {
					cor = "../../resources/img/iconeVinculadoFornecedor.png";
				}
			}
			return cor;
		}

}
