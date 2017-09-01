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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.Video1Facade;
import br.com.travelmate.facade.Video2Facade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.facade.VideoPasta1Facade;
import br.com.travelmate.facade.VideoPasta2Facade;
import br.com.travelmate.facade.VideoPasta3Facade;
import br.com.travelmate.facade.VideoPasta4Facade;
import br.com.travelmate.facade.VideoPasta5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Midias;
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
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class Pasta1VideoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Departamento departamento;
	private List<String> listaConteudo;
	private List<String> listaLink;
	private List<Midias> listaMidia;
	private boolean semPastas = false;
	private boolean pesquisar;
	private String tituloPagina;
	private String tipoArquivo;
	private String nomeArquivo;
	private List<Videopasta1> listaVideoPasta1;
	private List<Video1> listaVideo1;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			departamento = (Departamento) session.getAttribute("departamento");
			session.removeAttribute("departamento");
			if (departamento != null) {
				gerarVideoPastas1();

				// Verifica se a pastas para ser mostradas na tela
				semConteudo();
			}else {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
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

	public List<Videopasta1> getListaVideoPasta1() {
		return listaVideoPasta1;
	}

	public void setListaVideoPasta1(List<Videopasta1> listaVideoPasta1) {
		this.listaVideoPasta1 = listaVideoPasta1;
	}

	public List<Video1> getListaVideo1() {
		return listaVideo1;
	}

	public void setListaVideo1(List<Video1> listaVideo1) {
		this.listaVideo1 = listaVideo1;
	}

	public void gerarVideoPastas1() {
		VideoPasta1Facade videoPasta1Facade = new VideoPasta1Facade();
		String sql = "Select c from Videopasta1 c where c.departamento.iddepartamento="
				+ departamento.getIddepartamento();

		listaVideoPasta1 = videoPasta1Facade.listar(sql);
		if (listaVideoPasta1 == null) {
			listaVideoPasta1 = new ArrayList<Videopasta1>();
		}
	}

	public String voltarConsDepartamento() {
		return "consDepartamentos1Videos";
	}

	public String consPasta2Video1(Videopasta1 videopasta1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("videopasta1", videopasta1);
		return "consPastas2Videos1";
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

	public Integer gerarNVideo1(Videopasta1 videopasta1) {
		Video1Facade video1Facade = new Video1Facade();
		Integer numeroTotal = 0;
		String sql = "";
		List<Video1> listaVideo1 = new ArrayList<>();
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return numeroTotal;
		} else {
			sql = "Select c from Video1 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.ativo=true";
			}
			listaVideo1 = video1Facade.listar(sql);
			if (listaVideo1 == null) {
				listaVideo1 = new ArrayList<Video1>();
				numeroTotal = listaVideo1.size();
				return numeroTotal;
			} else {
				numeroTotal = listaVideo1.size();
				return numeroTotal;
			}
		}
	}

	public Integer gerarTotalPasta2(Videopasta1 videopasta1) {
		VideoPasta2Facade videoPasta2Facade = new VideoPasta2Facade();
		Integer numeroTotal = 0;
		String sql = "Select v from Videopasta2 v where v.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1();
		List<Videopasta2> listaVideoPasta2 = videoPasta2Facade.listar(sql);
		if (listaVideoPasta2 == null) {
			listaVideoPasta2 = new ArrayList<>();
			numeroTotal = listaVideoPasta2.size();
			return numeroTotal;
		} else {
			numeroTotal = listaVideoPasta2.size();
			return numeroTotal;
		}
	}

	public String retornaIconeArquivo(String nomeArquivo) {
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
		} else if (extensao.equalsIgnoreCase(".ai")) {
			return "../../resources/img/icone_ai.png";
		} else if (extensao.equalsIgnoreCase(".psd")) {
			return "../../resources/img/icone_psd.png";
		}
		return "";
	}

	public List<Video1> gerarTotalVideo2(Videopasta1 videopasta1) {
		Video1Facade video1Facade = new Video1Facade();
		List<Video1> listaVideo1 = null;
		if (listaVideo1 == null) {
			listaVideo1 = new ArrayList<Video1>();
		}
		String sql = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return listaVideo1;
		} else {
			sql = "Select v from Video1 v  where v.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and v.ativo=true";
			}
			List<Video1> listaVideos1 = video1Facade.listar(sql);
			if (listaVideos1 == null || listaVideos1.isEmpty()) {
				listaVideos1 = new ArrayList<Video1>();
				return listaVideos1;
			} else {
				for (int i = 0; i < listaVideos1.size(); i++) {
					if (listaVideo1.size() < 3) {
						listaVideo1.add(listaVideos1.get(i));
					}
				}
				return listaVideo1;
			}
		}
	}

	public String cadastroPasta1Videos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("departamento", departamento);
		RequestContext.getCurrentInstance().openDialog("cadPastasVideos1", options, null);
		return "";
	}

	public void retornoDialogNovo(SelectEvent event) {
		Videopasta1 videopasta1 = (Videopasta1) event.getObject();
		if (videopasta1.getIdvideopasta1() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			listaVideoPasta1.add(videopasta1);
		}
		semConteudo();
	}

	// Verifica se a pastas para ser mostradas na tela
	public void semConteudo() {
		if (listaVideoPasta1 == null || listaVideoPasta1.isEmpty()) {
			semPastas = true;
		} else {
			semPastas = false;
		}
	}

	public void excluirPasta(Videopasta1 videopasta1) {
		VideoPasta1Facade videoPasta1Facade = new VideoPasta1Facade();
		excluirIntens5(videopasta1);
		excluirItens4(videopasta1);
		excluirItens3(videopasta1);
		excluirItens2(videopasta1);
		excluirItens1(videopasta1);
		videoPasta1Facade.excluir(videopasta1.getIdvideopasta1());
		gerarVideoPastas1();
		Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
	}
	
	public void excluirIntens5(Videopasta1 videopasta1){
		VideoPasta5Facade videoPasta5Facade = new VideoPasta5Facade();
		Video5Facade video5Facade = new Video5Facade();
		List<Video5> listaVideo5 = video5Facade.listar("Select c from Video5 c Where c.videopasta1.idvideopasta1="+ videopasta1.getIdvideopasta1());
		if (listaVideo5 != null) {
			for (int i = 0; i < listaVideo5.size(); i++) {
				video5Facade.excluir(listaVideo5.get(i).getIdvideo5());
			}
		}
		List<Videopasta5> listaVideoPasta5 = videoPasta5Facade.listar("Select c From Videopasta5 c Where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1());
		if (listaVideoPasta5 != null) {
			for (int i = 0; i < listaVideoPasta5.size(); i++) {
				videoPasta5Facade.excluir(listaVideoPasta5.get(i).getIdvideopasta5());
			}
		}
	}
	
	public void excluirItens4(Videopasta1 videopasta1){
		Video4Facade video4Facade = new Video4Facade();
		VideoPasta4Facade videoPasta4Facade = new VideoPasta4Facade();
		List<Video4> listaVideo4 = video4Facade
				.listar("Select c from Video4 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1());
		if (listaVideo4 != null) {
			for (int i = 0; i < listaVideo4.size(); i++) {
				video4Facade.excluir(listaVideo4.get(i).getIdvideo4());
			}
		}
		List<Videopasta4> listaPastaVideo4 = videoPasta4Facade
				.listar("Select c from Videopasta4 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1());
		if (listaPastaVideo4 != null) {
			for (int i = 0; i < listaPastaVideo4.size(); i++) {
				videoPasta4Facade.excluir(listaPastaVideo4.get(i).getIdvideopasta4());
			}
		}
	}
	
	public void excluirItens3(Videopasta1 videopasta1){
		Video3Facade video3Facade = new Video3Facade();
		VideoPasta3Facade videoPasta3Facade = new VideoPasta3Facade();
		
		List<Video3> listaVideo3 = video3Facade
				.listar("Select c from Video3 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1());
		if (listaVideo3 != null) {
			for (int i = 0; i < listaVideo3.size(); i++) {
				video3Facade.excluir(listaVideo3.get(i).getIdvideo3());
			}
		}
		
		List<Videopasta3> listaVideoPasta3 = videoPasta3Facade.listar("Select c From Videopasta3 c Where c.videopasta1.idvideopasta1="+ videopasta1.getIdvideopasta1());
		if (listaVideoPasta3 != null) {
			for (int i = 0; i < listaVideoPasta3.size(); i++) {
				videoPasta3Facade.excluir(listaVideoPasta3.get(i).getIdvideopasta3());
			}
		}
		
	}
	
	public void excluirItens2(Videopasta1 videopasta1){
		Video2Facade video2Facade = new Video2Facade();
		VideoPasta2Facade videoPasta2Facade = new VideoPasta2Facade();
		List<Video2> listaVideo2 = video2Facade
				.listar("Select c from Video2 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1());
		if (listaVideo2 != null) {
			for (int i = 0; i < listaVideo2.size(); i++) {
			video2Facade.excluir(listaVideo2.get(i).getIdvideo2());
			}	
		}
		
		List<Videopasta2> listaVideoPasta2 = videoPasta2Facade.listar("Select c From Videopasta2 c Where c.videopasta1.idvideopasta1="+ videopasta1.getIdvideopasta1());
		if (listaVideoPasta2 != null) {
			for (int i = 0; i < listaVideoPasta2.size(); i++) {
				videoPasta2Facade.excluir(listaVideoPasta2.get(i).getIdvideopasta2());
			}
		}
	}
	
	public void excluirItens1(Videopasta1 videopasta1){
		Video1Facade video1Facade = new Video1Facade();
		List<Video1> listaVideo1 = video1Facade
				.listar("Select c from Video1 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1());
		if (listaVideo1 != null) {
			for (int i = 0; i < listaVideo1.size(); i++) {
			video1Facade.excluir(listaVideo1.get(i).getIdvideo1());
			}	
		}
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
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

	public void retornoDialogAlteracao() {
		gerarVideoPastas1();
	}

	public String editarVideoPasta1(Videopasta1 videopasta1) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("departamento", departamento);
		session.setAttribute("videopasta1", videopasta1);
		RequestContext.getCurrentInstance().openDialog("cadPastasVideos1", options, null);
		return "";
	}

}
