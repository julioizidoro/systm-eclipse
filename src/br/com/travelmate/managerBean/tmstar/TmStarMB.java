package br.com.travelmate.managerBean.tmstar;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.TmStarFacade;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Tmstar;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class TmStarMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tmstar tmstar;
	private List<Tmstar> listaTmStar;
	private Ftpdados ftpdados;
	private String urlArquivo;

	@PostConstruct
	public void init() {
		gerarListaTmStar();
		pegarFtpDados();
	}

	public Tmstar getTmstar() {
		return tmstar;
	}

	public void setTmstar(Tmstar tmstar) {
		this.tmstar = tmstar;
	}

	public List<Tmstar> getListaTmStar() {
		return listaTmStar;
	}

	public void setListaTmStar(List<Tmstar> listaTmStar) {
		this.listaTmStar = listaTmStar;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public void gerarListaTmStar() {
		TmStarFacade tmStarFacade = new TmStarFacade();
		listaTmStar = tmStarFacade.lista();
		if (listaTmStar == null || listaTmStar.isEmpty()) {
			listaTmStar = new ArrayList<Tmstar>();
		}
	}

	public void editarPais(Tmstar tmstar) {
		if (tmstar != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 280);
			session.setAttribute("tmstar", tmstar);
			RequestContext.getCurrentInstance().openDialog("editarPaisTmStar", options, null);
		}
	}

	public void retornoDialogEdicao(SelectEvent event) {
		Tmstar tmstar = (Tmstar) event.getObject();
		if (tmstar.getIdtmstar() != null) {
			gerarListaTmStar();
			Mensagem.lancarMensagemInfo("Alteração do pais feita com sucesso", "");
		}
	}

	public void uploadPDF(Tmstar tmstar) {
		if (tmstar != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			session.setAttribute("tmstar", tmstar);
			RequestContext.getCurrentInstance().openDialog("uplaodPDFTmStar", options, null);
		}
	}

	public void retornoDialogPDF(SelectEvent event) {
		Tmstar tmstar = (Tmstar) event.getObject();
		if (tmstar.getIdtmstar() != null) {
			gerarListaTmStar();
			Mensagem.lancarMensagemInfo("PDF salvo com sucesso", "");
		}
	}

	public void uploadImagem(Tmstar tmstar) {
		if (tmstar != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			session.setAttribute("tmstar", tmstar);
			RequestContext.getCurrentInstance().openDialog("uploadImagemTmStar", options, null);
		}
	}

	public void retornoDialogImagem(SelectEvent event) {
		Tmstar tmstar = (Tmstar) event.getObject();
		if (tmstar.getIdtmstar() != null) {
			gerarListaTmStar();
			Mensagem.lancarMensagemInfo("Imagem salva com sucesso", "");
		}
	}

	public boolean habilitarImagem(Tmstar tmstar) {
		if (tmstar.getNomeimagem() == null || tmstar.getNomeimagem().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean habilitarPDF(Tmstar tmstar) {
		if (tmstar.getNomepdf() == null || tmstar.getNomepdf().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void pegarFtpDados() {
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
			if (ftpdados != null) {
				urlArquivo = ftpdados.getProtocolo() + "://" + ftpdados.getHost() + ":" + ftpdados.getWww();
			}
		} catch (SQLException e) {
			  
		}
	}

	public String retornaNomePais(int posicao) {
		String nomePais = listaTmStar.get(posicao).getPais().getNome();
		return nomePais;
	}

	public int retornarIdTmStar(int posicao) {
		int idTmStar = listaTmStar.get(posicao).getIdtmstar();
		return idTmStar;
	}

}
