package br.com.travelmate.managerBean.cloud.midia;

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

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.Pasta1Facade;
import br.com.travelmate.facade.Pasta2Facade;
import br.com.travelmate.facade.Pasta3Facade;
import br.com.travelmate.facade.Pasta4Facade;
import br.com.travelmate.facade.Pasta5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Midias;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class Pasta1MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Pasta1 pasta1;
	private List<Pasta1> listaPasta1;
	private Arquivo1 arquivo1;
	private List<Arquivo1> listaArquivo1;
	private List<String> listaConteudo;
	private List<String> listaLink;
	private List<Midias> listaMidia;
	private boolean semPastas = false;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean pesquisar;
	private String tituloPagina;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private String tipoArquivo;
	private String nomeArquivo;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			departamento = (Departamento) session.getAttribute("departamento");
			pasta1 = (Pasta1) session.getAttribute("pasta1");
			session.removeAttribute("pasta1");
			if (departamento!= null) {
				gerarCloudPastas();
				if (pasta1 == null) {
					pasta1 = new Pasta1();
				}
	
				// Verifica se a pastas para ser mostradas na tela
				semConteudo(); 
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isSemPastas() {
		return semPastas;
	}

	public void setSemPastas(boolean semPastas) {
		this.semPastas = semPastas;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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
	 * @return the listaPasta1
	 */
	public List<Pasta1> getListaPasta1() {
		return listaPasta1;
	}

	/**
	 * @param listaPasta1
	 *            the listaPasta1 to set
	 */
	public void setListaPasta1(List<Pasta1> listaPasta1) {
		this.listaPasta1 = listaPasta1;
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

	public List<String> getListaConteudo() {
		return listaConteudo;
	}

	public void setListaConteudo(List<String> listaConteudo) {
		this.listaConteudo = listaConteudo;
	}

	public List<String> getListaLink() {
		return listaLink;
	}

	public void setListaLink(List<String> listaLink) {
		this.listaLink = listaLink;
	}

	public List<Midias> getListaMidia() {
		return listaMidia;
	}

	public void setListaMidia(List<Midias> listaMidia) {
		this.listaMidia = listaMidia;
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

	public void gerarCloudPastas() {
			Pasta1Facade pasta1Facade = new Pasta1Facade();
			String sql = "Select c from Pasta1 c where c.departamento.iddepartamento=" + departamento.getIddepartamento();
					
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			sql = sql +  " order by c.nome"; 
			listaPasta1 = pasta1Facade.listar(sql);
			if (listaPasta1 == null) {
				listaPasta1 = new ArrayList<Pasta1>();
			}
	}

	public void gerarCloudFilesRelatorio() {
		Arquivo1Facade cloudFilesFacade = new Arquivo1Facade();
		listaArquivo1 = cloudFilesFacade.listar("Select c from Arquivo1 c where c.pasta1.idpasta1=" + 1);
		if (listaArquivo1 == null) {
			listaArquivo1 = new ArrayList<Arquivo1>();
		}
	}

	public String voltarConsDepartamento() {
		return "consDepartamentos";
	}

	public String cloudPasta2Arquivo1(Pasta1 pasta1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("pasta1", pasta1);
		return "consPasta2Arquivo1";
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

	public Integer gerarTotalArquivosSubPastas(Pasta1 pasta1) {
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		Integer numeroTotal = 0;
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		}else{
			int iddepartamento = pasta1.getDepartamento().getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				sql = "Select c from Arquivo1 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1();
			} else {
				sql = "Select c from Arquivo1 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1() + " and c.datainicio <='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and (c.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or c.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
			listaArquivo1 = arquivo1Facade
					.listar(sql);
			if (listaArquivo1 == null) {
				listaArquivo1 = new ArrayList<Arquivo1>();
				numeroTotal = listaArquivo1.size();
				return numeroTotal;
			} else {
				numeroTotal = listaArquivo1.size();
				return numeroTotal;
			}
		}
	}

	public Integer gerarTotalSubPastas(Pasta1 pasta1) {
		Pasta2Facade pasta2Facade = new Pasta2Facade();
		Integer numeroTotal = 0;
		String sql = "Select c from Pasta2 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.restrito=0 ";
		}
		List<Pasta2> listaPasta2 = pasta2Facade.listar(sql);
		if (listaPasta2 == null) {
			listaPasta2 = new ArrayList<>();
			numeroTotal = listaPasta2.size();
			return numeroTotal;
		} else {
			numeroTotal = listaPasta2.size();
			return numeroTotal;
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

	public List<Arquivo1> gerarTotalArquivos(Pasta1 pasta1) {
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		List<Arquivo1> listaArquivo1 = null;
		if (listaArquivo1 == null) {
			listaArquivo1 = new ArrayList<Arquivo1>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaArquivo1;
		}else{
			int iddepartamento = pasta1.getDepartamento().getIddepartamento();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == iddepartamento) {
				sql = "Select cf from Arquivo1  cf Where "
						+ " cf.pasta1.idpasta1=" + pasta1.getIdpasta1();
			} else {
				sql = "Select cf from Arquivo1 cf Where "
						+ " cf.pasta1.idpasta1=" + pasta1.getIdpasta1() + " and cf.datainicio<='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "'" + "  and (cf.datavalidade>='"
						+ Formatacao.ConvercaoDataSql(new Date()) + "' or cf.datavalidade is null)";
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and cf.restrito=0 ";
			}
			sql = sql +  " order by cf.idarquivo1 DESC";
			List<Arquivo1> listaArquivos1 = arquivo1Facade.listar(sql);
			if (listaArquivos1 == null || listaArquivos1.isEmpty()) {
				listaArquivos1 = new ArrayList<Arquivo1>();
				return listaArquivos1;
			} else {
				for (int i = 0; i < listaArquivos1.size(); i++) {
					if (listaArquivo1.size() < 3) {
						listaArquivo1.add(listaArquivos1.get(i));
					}
				}
				return listaArquivo1;
			}	
		}
	}

	public String cadastroPasta() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("departamento", departamento);
		RequestContext.getCurrentInstance().openDialog("cadPasta1", options, null);
		return "";
	}

	public void retornoDialogNovo(SelectEvent event) {
		Pasta1 pasta1 = (Pasta1) event.getObject();
		if (pasta1.getIdpasta1() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarCloudPastas();
		semConteudo();
	}

	// Verifica se a pastas para ser mostradas na tela
	public void semConteudo() {
		if (listaPasta1 == null || listaPasta1.isEmpty()) {
			semPastas = true;
		} else {
			semPastas = false;
		}
	}

	public void excluirPasta(Pasta1 pasta1) {
		Pasta1Facade pasta1Facade = new Pasta1Facade();
		excluirIntens5(pasta1);
		excluirItens4(pasta1);
		excluirItens3(pasta1);
		excluirItens2(pasta1);
		excluirItens1(pasta1);
		pasta1Facade.excluir(pasta1.getIdpasta1());
		gerarCloudPastas();
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
	}
	
	public void excluirIntens5(Pasta1 pasta1){
		Pasta5Facade pasta5Facade = new Pasta5Facade();
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		List<Arquivo5> listaArquivo5 = arquivo5Facade.listar("Select c from Arquivo5 c Where c.pasta1.idpasta1="+ pasta1.getIdpasta1());
		if (listaArquivo5 != null) {
			for (int i = 0; i < listaArquivo5.size(); i++) {
				excluirArquivoFTP(listaArquivo5.get(i).getNomeftp());
				arquivo5Facade.excluir(listaArquivo5.get(i).getIdarquivo5());
			}
		}
		List<Pasta5> listaPasta5 = pasta5Facade.listar("Select c From Pasta5 c Where c.pasta1.idpasta1=" + pasta1.getIdpasta1());
		if (listaPasta5 != null) {
			for (int i = 0; i < listaPasta5.size(); i++) {
				pasta5Facade.excluir(listaPasta5.get(i).getIdpasta5());
			}
		}
	}
	
	public void excluirItens4(Pasta1 pasta1){
		Pasta4Facade pasta4Facade = new Pasta4Facade();
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		List<Arquivo4> listaArquivo4 = arquivo4Facade
				.listar("Select c from Arquivo4 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1());
		if (listaArquivo4 != null) {
			for (int i = 0; i < listaArquivo4.size(); i++) {
				excluirArquivoFTP(listaArquivo4.get(i).getNomeftp());
				arquivo4Facade.excluir(listaArquivo4.get(i).getIdarquivo4());
			}
		}
		List<Pasta4> listaPasta4 = pasta4Facade
				.listar("Select c from Pasta4 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1());
		if (listaPasta4 != null) {
			for (int i = 0; i < listaPasta4.size(); i++) {
				pasta4Facade.excluir(listaPasta4.get(i).getIdpasta4());
			}
		}
	}
	
	public void excluirItens3(Pasta1 pasta1){
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		Pasta3Facade pasta3Facade = new Pasta3Facade();
		List<Arquivo3> listaArquivo3 = arquivo3Facade
				.listar("Select c from Arquivo3 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1());
		if (listaArquivo3 != null) {
			for (int i = 0; i < listaArquivo3.size(); i++) {
				excluirArquivoFTP(listaArquivo3.get(i).getNomeftp());
				arquivo3Facade.excluir(listaArquivo3.get(i).getIdarquivo3());
			}
		}
		
		List<Pasta3> listaPasta3 = pasta3Facade.listar("Select c From Pasta3 c Where c.pasta1.idpasta1="+ pasta1.getIdpasta1());
		if (listaPasta3 != null) {
			for (int i = 0; i < listaPasta3.size(); i++) {
				pasta3Facade.excluir(listaPasta3.get(i).getIdpasta3());
			}
		}
		
	}
	
	public void excluirItens2(Pasta1 pasta1){
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		Pasta2Facade pasta2Facade = new Pasta2Facade();
		List<Arquivo2> listaArquivo2 = arquivo2Facade
				.listar("Select c from Arquivo2 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1());
		if (listaArquivo2 != null) {
			for (int i = 0; i < listaArquivo2.size(); i++) {
				excluirArquivoFTP(listaArquivo2.get(i).getNomeftp());
				arquivo2Facade.excluir(listaArquivo2.get(i).getIdarquivo2());
			}	
		}
		
		List<Pasta2> listaPasta2 = pasta2Facade.listar("Select c From Pasta2 c Where c.pasta1.idpasta1="+ pasta1.getIdpasta1());
		if (listaPasta2 != null) {
			for (int i = 0; i < listaPasta2.size(); i++) {
				pasta2Facade.excluir(listaPasta2.get(i).getIdpasta2());
			}
		}
	}
	
	public void excluirItens1(Pasta1 pasta1){
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		List<Arquivo1> listaArquivo1 = arquivo1Facade
				.listar("Select c from Arquivo1 c where c.pasta1.idpasta1=" + pasta1.getIdpasta1());
		if (listaArquivo1 != null) {
			for (int i = 0; i < listaArquivo1.size(); i++) {
				excluirArquivoFTP(listaArquivo1.get(i).getNomeftp());
				arquivo1Facade.excluir(listaArquivo1.get(i).getIdArquivo1());
			}	
		}
	}
	
	
	public boolean excluirArquivoFTP(String nomeArquivo) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("docs", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey(nomeArquivo);
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
 
	public void retornoDialogAlteracao() {
		gerarCloudPastas();
	}

	public String editarPasta1(Pasta1 pasta1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		RequestContext.getCurrentInstance().openDialog("cadPasta1", options, null);
		return "";
	}
	
	
	public String pesquisar(){
		boolean arquivosNovos = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tituloPagina = "Pesquisar Arquivos";
		pesquisar = true;
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
		gerarListaArquivos(departamento.getPasta1List(), departamento);
		session.setAttribute("departamento", departamento);
		session.setAttribute("arquivosNovos", arquivosNovos);
		session.setAttribute("listaArquivoVencidoBean", listaArquivoVencidoBean);
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		return "listarArquivos";
	}  
	
	
	public void gerarListaArquivos(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo1(((Pasta1) obj).getArquivo1List(), departamento);
				gerarListaArquivo2(((Pasta1) obj).getArquivo2List(), departamento);
				gerarListaArquivo3(((Pasta1) obj).getArquivo3List(), departamento);
				gerarListaArquivo4(((Pasta1) obj).getArquivo4List(), departamento);
				gerarListaArquivo5(((Pasta1) obj).getArquivo5List(), departamento);
		
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
							if(!lista.get(i).isRestrito()){
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
}
