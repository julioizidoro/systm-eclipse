package br.com.travelmate.managerBean.videos;

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

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.Video1Facade;
import br.com.travelmate.facade.Video2Facade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.facade.VideoPasta2Facade;
import br.com.travelmate.facade.VideoPasta3Facade;
import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.facade.VideoPasta5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Video1;
import br.com.travelmate.model.Video2;
import br.com.travelmate.model.Video3;
import br.com.travelmate.model.Video4;
import br.com.travelmate.model.Video5;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;


@Named
@ViewScoped
public class Pastas2Videos1MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Videopasta1 videopasta1;
	private List<Videopasta2> listaVideoPasta2;
	private Ftpdados ftpDados;
	private boolean semPastaArquivo = false;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaArquivoLista = false;
	private boolean tabelaArquivoQuadro = true;
	private boolean pesquisar;
	private String tituloPagina;
	private String tipoArquivo;
	private String nomeArquivo;
	private List<Video1> listaVideo1;
	private Video1Bean video1Bean;
	private List<Video1Bean> listaVideo1Bean;
	private ArquivoBean arquivoBean; 
	private String urlArquivo = "";
	
	
	@PostConstruct
	public void init(){ 
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			departamento = (Departamento) session.getAttribute("departamento");
			videopasta1 = (Videopasta1) session.getAttribute("videopasta1");
			if (videopasta1 != null) {
				departamento = videopasta1.getDepartamento();
			}   
			session.removeAttribute("departamento");
			session.removeAttribute("videopasta1");
			if (videopasta1!=null && departamento != null){
				if (videopasta1.getDepartamento()!=null){
					gerarPastaVideos2();
					gerarVideos1();
					ftpDados = new Ftpdados();
					FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
					try {
						ftpDados = ftpDadosFacade.getFTPDados();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					if (ftpDados != null) {
						urlArquivo = ftpDados.getProtocolo() + "://" + ftpDados.getHost() +  ":"+ ftpDados.getWww() +"/videos";
					}
					// Verificar se existe pastas ou arquivos na tela
					semConteudo();
					verificarExibicao();
				}
			}else{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
			} catch (IOException e) {
				e.printStackTrace();
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







	public List<Videopasta2> getListaVideoPasta2() {
		return listaVideoPasta2;
	}







	public void setListaVideoPasta2(List<Videopasta2> listaVideoPasta2) {
		this.listaVideoPasta2 = listaVideoPasta2;
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









	public List<Video1> getListaVideo1() {
		return listaVideo1;
	}







	public void setListaVideo1(List<Video1> listaVideo1) {
		this.listaVideo1 = listaVideo1;
	}

	






	public Video1Bean getVideo1Bean() {
		return video1Bean;
	}







	public void setVideo1Bean(Video1Bean video1Bean) {
		this.video1Bean = video1Bean;
	}







	public List<Video1Bean> getListaVideo1Bean() {
		return listaVideo1Bean;
	}







	public void setListaVideo1Bean(List<Video1Bean> listaVideo1Bean) {
		this.listaVideo1Bean = listaVideo1Bean;
	}







	public String getUrlArquivo() {
		return urlArquivo;
	}







	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}







	public String cadastroPastaVideos2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta1", videopasta1);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos2", options, null);
		return "";
	}
	
	
	public String cadastroVideos1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		session.setAttribute("videopasta1", videopasta1);
		RequestContext.getCurrentInstance().openDialog("cadVideo1", options, null);
		return "";
	}
	
	
	public String consPastas3Videos2(Videopasta2 videopasta2) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("videopasta2", videopasta2);
		session.setAttribute("departamento", departamento);
		return "consPastas3Videos2";
	}
	
	
	public void gerarVideos1() {
		Video1Facade video1Facade = new Video1Facade();
		String sql = "Select c from Video1 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.ativo=true";
		}
		sql = sql + " order by c.nome";
		listaVideo1 = video1Facade.listar(sql);
		if (listaVideo1 == null) {
			listaVideo1 = new ArrayList<Video1>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
			listaVideo1Bean = new ArrayList<>();
		}
//		for (int i = 0; i < listaVideo1.size(); i++) {
//			video1Bean = new Video1Bean();
//			video1Bean.setVideo1(listaVideo1.get(i));
//			if ((i + 1) < listaVideo1.size()) {
//				video1Bean.setVideo2(listaVideo1.get(i + 1));
//				i++;
//				if ((i + 1) < listaVideo1.size()) {
//					video1Bean.setVideo3(listaVideo1.get(i + 1));
//					i++;
//				} else {
//					video1Bean.setVideo3(null);
//				}
//
//			} else {
//				video1Bean.setVideo2(null);
//				video1Bean.setVideo3(null);
//			}
//
//			listaVideo1Bean.add(video1Bean);
//		}
	}

	public void gerarPastaVideos2() {
		VideoPasta2Facade videoPasta2Facade = new VideoPasta2Facade();
		String sql = "Select c from Videopasta2 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1();
		
		listaVideoPasta2 = videoPasta2Facade.listar(sql);
		if (listaVideoPasta2 == null) {
			listaVideoPasta2 = new ArrayList<Videopasta2>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
		}
	}

	public String pegarNomeArquivo(Video1 video1) {
		if (video1 != null) {
			String nome = video1.getDescricao();
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

	public String alterarDescricaoVideo(Video1 video1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video1", video1);
		session.setAttribute("editar", true);
		RequestContext.getCurrentInstance().openDialog("consDescritivo1Video", options, null);
		return "";
	}
	
	public String descricaoVideo(Video1 video1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video1", video1);
		session.setAttribute("editar", false);
		RequestContext.getCurrentInstance().openDialog("consDescritivo1Video", options, null);
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
		}else if(extensao.equalsIgnoreCase(".ppsx")){
			return "../../resources/img/icone_ppsx.png";
		}  
		return "";
	}

	public Integer gerarTotalNVideo2(Videopasta2 videopasta2) {
		Video2Facade video2Facade = new Video2Facade();
		Integer numeroTotal = 0;
		String sql = "";
		List<Video2> listaVideo2 = new ArrayList<>();
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		}else{
			sql = "Select c from Video2 c where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.ativo=true";
			}
			listaVideo2 = video2Facade.listar(sql);
			if (listaVideo2 == null) {
				listaVideo2 = new ArrayList<>();
				numeroTotal = listaVideo2.size();
				return numeroTotal;
			} else {
				numeroTotal = listaVideo2.size();
				return numeroTotal;
			}
		}
	}

	public Integer gerarTotalPasta3(Videopasta2 videopasta2) {
		VideoPasta3Facade videoPasta3Facade = new VideoPasta3Facade();
		Integer numeroTotal = 0;
		String sql = "Select c from Videopasta3 c where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2();
		List<Videopasta3> listaVideoPasta3 = videoPasta3Facade.listar(sql);
		if (listaVideoPasta3 == null) {
			listaVideoPasta3 = new ArrayList<>();
			numeroTotal = listaVideoPasta3.size();
			return numeroTotal;
		} else {
			numeroTotal = listaVideoPasta3.size();
			return numeroTotal;
		}
	}

	public List<Video2> gerar3UltimosVideo2(Videopasta2 videopasta2) {
		Video2Facade video2Facade = new Video2Facade();
		List<Video2> listaVideo2 = null;
		if (listaVideo2 == null) {
			listaVideo2 = new ArrayList<Video2>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaVideo2;
		}else{
				sql = "Select v from Video2 v where v.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2() +
						" and v.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and v.ativo=true";
			}
			List<Video2> listaVideos2 = video2Facade.listar(sql);
			if (listaVideos2 == null || listaVideos2.isEmpty()) {
				listaVideos2 = new ArrayList<Video2>();
				return listaVideos2;
			} else {
				for (int i = 0; i < listaVideos2.size(); i++) {
					if (listaVideo2.size() < 3) {
						listaVideo2.add(listaVideos2.get(i));
					}
				}
				return listaVideo2;
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
		Videopasta2 videopasta2 = (Videopasta2) event.getObject();
		if (videopasta2.getIdvideopasta2() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			listaVideoPasta2.add(videopasta2);
		}
		semConteudo();
	}

	public void retornoDialogAlteracaoDescricao(SelectEvent event) {
		Video1 video1 = (Video1) event.getObject();
		if (video1.getIdvideo1() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		semConteudo();
	}

	public void retornoDialogNovoVideo(SelectEvent event) {
		Video1 video1 = (Video1) event.getObject();
		if (video1.getIdvideo1() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarVideos1();
		}
		semConteudo();
	}
	
	

	// Verificar se existe pastas ou arquivos na tela
	public void semConteudo() {
		if ((listaVideo1 == null || listaVideo1.isEmpty()) && (listaVideoPasta2 == null || listaVideoPasta2.isEmpty())) {
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;

		}
	}
	
	public boolean excluirArquivoFTP(Video1 video1) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("treinamento", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey(video1.getHost());
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

	public String excluirArquivo(Video1 video1) {
		excluirArquivoFTP(video1);
		Video1Facade video1Facade = new Video1Facade();
		video1Facade.excluir(video1.getIdvideo1());
		gerarPastaVideos2();
		gerarVideos1();
		return "";
	}

	public void excluirPasta(Videopasta2 videopasta2) {
		VideoPasta2Facade videoPasta2Facade = new VideoPasta2Facade();
		excluirIntens5(videopasta2);
		excluirItens4(videopasta2);
		excluirItens3(videopasta2);
		excluirItens2(videopasta2);
		videoPasta2Facade.excluir(videopasta2.getIdvideopasta2());
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
		gerarPastaVideos2();
	}
	
	public void excluirIntens5(Videopasta2 videopasta2){
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		Video5Facade video5Facade = new Video5Facade();
		List<Video5> listaVideo5 = video5Facade.listar("Select c from Video5 c Where c.videopasta2.idvideopasta2="+ videopasta2.getIdvideopasta2());
		if (listaVideo5 != null) {
			for (int i = 0; i < listaVideo5.size(); i++) {
				excluirVideosSubFTP(listaVideo5.get(i).getHost());
				video5Facade.excluir(listaVideo5.get(i).getIdvideo5());
			}
		}
		List<Videopasta5> listaVideoPasta5 = videoPasta5Facade.listar("Select c From Videopasta5 c Where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2());
		if (listaVideoPasta5 != null) {
			for (int i = 0; i < listaVideoPasta5.size(); i++) {
				videoPasta5Facade.excluir(listaVideoPasta5.get(i).getIdvideopasta5());
			}
		}
	}
	
	public void excluirItens4(Videopasta2 videopasta2){
		Video4Facade video4Facade = new Video4Facade();
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		List<Video4> listaVideo4 = video4Facade
				.listar("Select c from Video4 c where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2());
		if (listaVideo4 != null) {
			for (int i = 0; i < listaVideo4.size(); i++) {
				excluirVideosSubFTP(listaVideo4.get(i).getHost());
				video4Facade.excluir(listaVideo4.get(i).getIdvideo4());
			}
		}
		List<Videopasta4> listaPastaVideo4 = videoPasta4Facade
				.listar("Select c from Videopasta4 c where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2());
		if (listaPastaVideo4 != null) {
			for (int i = 0; i < listaPastaVideo4.size(); i++) {
				videoPasta4Facade.excluir(listaPastaVideo4.get(i).getIdvideopasta4());
			}
		}
	}
	
	public void excluirItens3(Videopasta2 videopasta2){
		Video3Facade video3Facade = new Video3Facade();
		VideoPasta3Facade videoPasta3Facade = new VideoPasta3Facade();
		
		List<Video3> listaVideo3 = video3Facade
				.listar("Select c from Video3 c where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2());
		if (listaVideo3 != null) {
			for (int i = 0; i < listaVideo3.size(); i++) {
				excluirVideosSubFTP(listaVideo3.get(i).getHost());
				video3Facade.excluir(listaVideo3.get(i).getIdvideo3());
			}
		}
		
		List<Videopasta3> listaVideoPasta3 = videoPasta3Facade.listar("Select c From Videopasta3 c Where c.videopasta2.idvideopasta2="+ videopasta2.getIdvideopasta2());
		if (listaVideoPasta3 != null) {
			for (int i = 0; i < listaVideoPasta3.size(); i++) {
				videoPasta3Facade.excluir(listaVideoPasta3.get(i).getIdvideopasta3());
			}
		}
		
	}
	
	public void excluirItens2(Videopasta2 videopasta2){
		Video2Facade video2Facade = new Video2Facade();
		List<Video2> listaVideo2 = video2Facade
				.listar("Select c from Video2 c where c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2());
		if (listaVideo2 != null) {
			for (int i = 0; i < listaVideo2.size(); i++) {
				excluirVideosSubFTP(listaVideo2.get(i).getHost());
				video2Facade.excluir(listaVideo2.get(i).getIdvideo2());
			}	
		}
	}

	public boolean excluirVideosSubFTP(String nomeArquivo) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas2Videos1MB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(Pastas2Videos1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			msg = ftp.excluirArquivo(nomeArquivo, "/videos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Videos1MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		return false;
	}

	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		}else{
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

	public boolean verificarArquivo1(Video1Bean video1Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video1Bean.getVideo1() == null) {
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

	public boolean verificarArquivo2(Video1Bean video1Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video1Bean.getVideo2() == null) {
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

	public boolean verificarArquivo3(Video1Bean video1Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video1Bean.getVideo3() == null) {
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
	
	
	public boolean verificarArquivoLista(Video1 video1) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video1 == null) {
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



	public String editarVideoPasta2(Videopasta2 videopasta2) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("videopasta2", videopasta2);
		session.setAttribute("videopasta1", videopasta1);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos2", options, null);
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
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
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
		}else{
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
			usuario.setModoexibicao(nomeExibicao);
			usuarioFacade.salvar(usuario);
			usuarioLogadoMB.setUsuario(usuario);
		}
	}
	
	
	public void verificarExibicao(){
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		}else{
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
	
	
	public boolean verificarVideo(Video1 video1){
		if (video1 == null) {
			return false;
		}else{
			return true;
		}
	}
	
	
	public String vincularArquivoFornecedor(Video1 video1){
		String urlDocs = "consPastas2Videos1";
		arquivoBean = new ArquivoBean();
		arquivoBean.setDatainicio(new Date());
		arquivoBean.setDatavalidade(null);
		arquivoBean.setNome(video1.getNome());
		arquivoBean.setNomeftp(video1.getHost());
		arquivoBean.setRestrito(!video1.isAtivo());
		arquivoBean.setTipo("Video");
		arquivoBean.setDescricao(video1.getDescricao());
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivoBean);
		session.setAttribute("videopasta1", videopasta1);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("video1", video1);
		return "vincularFonecedorDocs";
	}
	
	
	public boolean acessoVinculoFornecedor(Video1 video1){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video1 == null) {
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
	
	
	public String verificarvinculoFornecedor(Video1 video1){
		String cor = "";
		if (video1 != null) {
			if (video1.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Video1 video1){
		String cor = "../../resources/img/vincularUnidade.png";
		if (video1 != null) {
			if (video1.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}
	

}
