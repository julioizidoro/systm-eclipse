package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Promocaotaxacurso;

@Named
@ViewScoped
public class ConsCidadeTaxasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Promocaotaxacurso promocaotaxacurso;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		promocaotaxacurso = (Promocaotaxacurso) session.getAttribute("promocaotaxacurso");
		session.removeAttribute("promocaotaxacurso"); 
	}

	public Promocaotaxacurso getPromocaotaxacurso() {
		return promocaotaxacurso;
	}

	public void setPromocaotaxacurso(Promocaotaxacurso promocaotaxacurso) {
		this.promocaotaxacurso = promocaotaxacurso;
	}

	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	

}
