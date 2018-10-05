package br.com.travelmate.managerBean.videos;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
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

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.facade.VideoPasta5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Departamento;

import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Video2;
import br.com.travelmate.model.Video3;
import br.com.travelmate.model.Video4;
import br.com.travelmate.model.Video5;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class Pastas4Videos3MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Videopasta1 videopasta1;
	private List<Videopasta4> listaVideoPasta4;
		private boolean semPastaArquivo = false;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaArquivoLista = false;
	private boolean tabelaArquivoQuadro = true;
	private boolean pesquisar;
	private String tituloPagina;
	private String tipoArquivo;
	private String nomeArquivo;
	private List<Video3> listaVideo3;
	private Videopasta2 videopasta2;
	private Videopasta3 videopasta3;
	private List<Video3Bean> listaVideo3Bean;
	private Video3Bean video3Bean;
	private ArquivoBean arquivoBean;
	private String urlArquivo = "";

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			videopasta3 = (Videopasta3) session.getAttribute("videopasta3");
			session.removeAttribute("videopasta3");
			if (videopasta3 != null) {
				videopasta1 = videopasta3.getVideopasta1();
				videopasta2 = videopasta3.getVideopasta2();
				departamento = videopasta1.getDepartamento();
				gerarPastaVideos4();
				gerarVideos3();
				// Verificar se existe pastas ou arquivos na tela
				semConteudo();
				verificarExibicao();
			}else{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
				} catch (IOException e) {
					  
				}
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
			} catch (IOException e) {
				  
			}
		}
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}

	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}

	public List<Videopasta4> getListaVideoPasta4() {
		return listaVideoPasta4;
	}

	public void setListaVideoPasta4(List<Videopasta4> listaVideoPasta4) {
		this.listaVideoPasta4 = listaVideoPasta4;
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

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public List<Video3> getListaVideo3() {
		return listaVideo3;
	}

	public void setListaVideo3(List<Video3> listaVideo3) {
		this.listaVideo3 = listaVideo3;
	}

	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}

	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}

	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}

	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}
	
	

	public List<Video3Bean> getListaVideo3Bean() {
		return listaVideo3Bean;
	}

	public void setListaVideo3Bean(List<Video3Bean> listaVideo3Bean) {
		this.listaVideo3Bean = listaVideo3Bean;
	}

	public Video3Bean getVideo3Bean() {
		return video3Bean;
	}

	public void setVideo3Bean(Video3Bean video3Bean) {
		this.video3Bean = video3Bean;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public String cadastroPastaVideos4() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta3", videopasta3);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos4", options, null);
		return "";
	}

	public String cadastroVideos3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		session.setAttribute("videopasta3", videopasta3);
		RequestContext.getCurrentInstance().openDialog("cadVideo3", options, null);
		return "";
	}

	public String consPastas5Videos4(Videopasta4 videopasta4) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("videopasta4", videopasta4);
		return "consPastas5Videos4";
	}

	public void gerarVideos3() {
		Video3Facade video3Facade = new Video3Facade();
		String sql = "Select c from Video3 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2()
				+ " and c.videopasta3.idvideopasta3=" + +videopasta3.getIdvideopasta3();
		
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.ativo=true";
		}
		sql = sql + " order by c.nome";
		listaVideo3 = video3Facade.listar(sql);
		if (listaVideo3 == null) {
			listaVideo3 = new ArrayList<Video3>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
			listaVideo3Bean = new ArrayList<>();
		}
//		for (int i = 0; i < listaVideo3.size(); i++) {
//			video3Bean = new Video3Bean();
//			video3Bean.setVideo1(listaVideo3.get(i));
//			if ((i + 1) < listaVideo3.size()) {
//				video3Bean.setVideo2(listaVideo3.get(i + 1));
//				i++;
//				if ((i + 1) < listaVideo3.size()) {
//					video3Bean.setVideo3(listaVideo3.get(i + 1));
//					i++;
//				} else {
//					video3Bean.setVideo3(null);
//				}
//
//			} else {
//				video3Bean.setVideo2(null);
//				video3Bean.setVideo3(null);
//			}
//
//			listaVideo3Bean.add(video3Bean);
//		}
	}

	public void gerarPastaVideos4() {
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		String sql = "Select c from Videopasta4 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2()
				+ " and c.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3();

		listaVideoPasta4 = videoPasta4Facade.listar(sql);
		if (listaVideoPasta4 == null) {
			listaVideoPasta4 = new ArrayList<Videopasta4>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
		}
	}

	public String pegarNomeArquivo(Video2 video2) {
		if (video2 != null) {
			String nome = video2.getDescricao();
			return nome;
		}
		return "";
	}

	public String voltarConsVideoPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1Video";
	}

	public String voltarConsVideoPasta2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("videopasta1", videopasta1);
		return "consPastas2Videos1";
	}

	public String voltarConsVideoPasta3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("videopasta2", videopasta2);
		return "consPastas3Videos2";
	}

	public String alterarDescricaoVideo(Video3 video3) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video3", video3);
		session.setAttribute("editar", true);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo3", options, null);
		return "";
	}
	 
	public String descricaoVideo(Video3 video3) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video3", video3);
		session.setAttribute("editar", false);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo3", options, null);
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

	public String retornaIconeArquivo(String nomeArquivo) {
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

	public Integer gerarTotalNVideo4(Videopasta4 videopasta4) {
		Video4Facade video4Facade = new Video4Facade();
		Integer numeroTotal = 0;
		String sql = "";
		List<Video4> listaVideo4 = new ArrayList<>();
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		} else {
			sql = "Select c from Video4 c where c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.ativo=true";
			}
			listaVideo4 = video4Facade.listar(sql);
			if (listaVideo4 == null) {
				listaVideo4 = new ArrayList<>();
				numeroTotal = listaVideo4.size();
				return numeroTotal;
			} else {
				numeroTotal = listaVideo4.size();
				return numeroTotal;
			}
		}
	}

	public Integer gerarTotalPasta5(Videopasta4 videopasta4) {
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		Integer numeroTotal = 0;
		String sql = "Select c from Videopasta5 c where c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4();
		List<Videopasta5> listaVideoPasta5 = videoPasta5Facade.listar(sql);
		if (listaVideoPasta5 == null) {
			listaVideoPasta5 = new ArrayList<>();
			numeroTotal = listaVideoPasta5.size();
			return numeroTotal;
		} else {
			numeroTotal = listaVideoPasta5.size();
			return numeroTotal;
		}
	}

	public List<Video4> gerar3UltimosVideo4(Videopasta4 videopasta4) {
		Video4Facade video4Facade = new Video4Facade();
		List<Video4> listaVideo4 = null;
		if (listaVideo4 == null) {
			listaVideo4 = new ArrayList<Video4>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaVideo4;
		} else {
			sql = "Select v from Video4 v where v.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2()
					+ " and v.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
					+ " and v.videopasta3.idvideopasta3=" + videopasta3.getIdvideopasta3()
					+ " and v.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and v.ativo=true";
			}
			List<Video4> listaVideos4 = video4Facade.listar(sql);
			if (listaVideos4 == null || listaVideos4.isEmpty()) {
				listaVideos4 = new ArrayList<Video4>();
				return listaVideos4;
			} else {
				for (int i = 0; i < listaVideos4.size(); i++) {
					if (listaVideo4.size() < 3) {
						listaVideo4.add(listaVideos4.get(i));
					}
				}
				return listaVideo4;
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

	public String voltarConsDepartamento() {
		return "consDepartamentos1Videos";
	}

	public void retornoDialogNovaPasta(SelectEvent event) {
		Videopasta4 videopasta4 = (Videopasta4) event.getObject();
		if (videopasta4.getIdvideopasta4() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			listaVideoPasta4.add(videopasta4);
		}
		semConteudo();
	}

	public void retornoDialogAlteracaoDescricao(SelectEvent event) {
		Video3 video3 = (Video3) event.getObject();
		if (video3.getIdvideo3() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarVideos3();
		semConteudo();
	}

	public void retornoDialogNovoVideo(SelectEvent event) {
		Video3 video3 = (Video3) event.getObject();
		if (video3.getIdvideo3() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarVideos3();
		}
		semConteudo();
	}

	// Verificar se existe pastas ou arquivos na tela
	public void semConteudo() {
		if ((listaVideo3 == null || listaVideo3.isEmpty())
				&& (listaVideoPasta4 == null || listaVideoPasta4.isEmpty())) {
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;

		}
	}

	public boolean excluirArquivoFTP(Video3 video3) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("treinamento", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey(video3.getHost());
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

	public String excluirArquivo(Video3 video3) {
		excluirArquivoFTP(video3);
		Video3Facade video3Facade = new Video3Facade();
		video3Facade.excluir(video3.getIdvideo3());
		gerarPastaVideos4();
		gerarVideos3();
		return "";
	}

	public void excluirPasta(Videopasta4 videopasta4) {
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		excluirItens5(videopasta4);
		excluirItens4(videopasta4);
		videoPasta4Facade.excluir(videopasta4.getIdvideopasta4());
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		gerarPastaVideos4();
	}
	
	public void excluirItens5(Videopasta4 videopasta4){
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		Video5Facade video5Facade = new Video5Facade();
		List<Video5> listaVideo5 = video5Facade.listar("Select c from Video5 c Where c.videopasta4.idvideopasta4="+ videopasta4.getIdvideopasta4());
		if (listaVideo5 != null) {
			for (int i = 0; i < listaVideo5.size(); i++) {
				excluirTodosArquivoFTP(listaVideo5.get(i).getHost());
				video5Facade.excluir(listaVideo5.get(i).getIdvideo5());
			}
		}
		List<Videopasta5> listaVideoPasta5 = videoPasta5Facade.listar("Select c From Videopasta5 c Where c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4());
		if (listaVideoPasta5 != null) {
			for (int i = 0; i < listaVideoPasta5.size(); i++) {
				videoPasta5Facade.excluir(listaVideoPasta5.get(i).getIdvideopasta5());
			}
		}
	}
	
	public void excluirItens4(Videopasta4 videopasta4){
		Video4Facade video4Facade = new Video4Facade();
		List<Video4> listaVideo4 = video4Facade
				.listar("Select c from Video4 c where c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4());
		if (listaVideo4 != null) {
			for (int i = 0; i < listaVideo4.size(); i++) {
				excluirTodosArquivoFTP(listaVideo4.get(i).getHost());
				video4Facade.excluir(listaVideo4.get(i).getIdvideo4());
			}
		}
		
	}
	
	

	public boolean excluirTodosArquivoFTP(String nomeArquivo) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("treinamento", caminho);
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

	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
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

	public boolean verificarArquivo1(Video3Bean video3Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video3Bean.getVideo1() == null) {
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
	}

	public boolean verificarArquivo2(Video3Bean video3Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video3Bean.getVideo2() == null) {
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
	}

	public boolean verificarArquivo3(Video3Bean video3Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video3Bean.getVideo3() == null) {
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
	}
	
	
	public boolean verificarArquivoLista(Video3 video3) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video3 == null) {
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
	}

	public String editarVideoPasta4(Videopasta4 videopasta4) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta3", videopasta3);
		session.setAttribute("videopasta4", videopasta4);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos4", options, null);
		return "";
	}

	public void listarArquivoLista() {
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
		salvarModoExibicao("L");
	}

	public void listarArquivoQuadro() {
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
		salvarModoExibicao("I");
	}

	public boolean verificarArquivoLista(Arquivo1 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getIdarquivo1() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public String trocarCorLinhaTabela(Arquivo1 arquivo1) {
		String cor = "background:#FFFFFF;";
		if (arquivo1 != null) {
			if ((arquivo1.getIdarquivo1() % 2) == 0) {
				cor = "background:#FFFFFF;";
			} else {
				cor = "background:#F3F3F3";
			}
		}
		return cor;
	}

	public void salvarModoExibicao(String nomeExibicao) {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
			usuario.setModoexibicao(nomeExibicao);
			usuarioFacade.salvar(usuario);
			usuarioLogadoMB.setUsuario(usuario);
		}
	}

	public void verificarExibicao() {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
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
	
	
	public boolean verificarVideo(Video3 video3){
		if (video3 == null) {
			return false;
		}else{
			return true;
		}
	}
	
	
	public String vincularArquivoFornecedor(Video3 video3){
		String urlDocs = "consPastas4Videos3";
		arquivoBean = new ArquivoBean();
		arquivoBean.setDatainicio(new Date());
		arquivoBean.setDatavalidade(null);
		arquivoBean.setNome(video3.getNome());
		arquivoBean.setNomeftp(video3.getHost());
		arquivoBean.setRestrito(!video3.isAtivo());
		arquivoBean.setTipo("Video");
		arquivoBean.setDescricao(video3.getDescricao());
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivoBean);
		session.setAttribute("videopasta3", videopasta3);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("video3", video3);
		return "vincularFonecedorDocs";
	}
	
	
	public boolean acessoVinculoFornecedor(Video3 video3){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video3 == null) {
				return acesso;
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					return acesso;
				}else{
					acesso = true;
					return acesso;
				}
			}
		}
	}
	
	
	public String verificarvinculoFornecedor(Video3 video3){
		String cor = "";
		if (video3 != null) {
			if (video3.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Video3 video3){
		String cor = "../../resources/img/vincularUnidade.png";
		if (video3 != null) {
			if (video3.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}
}
