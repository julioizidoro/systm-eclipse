package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext; 
import br.com.travelmate.model.Promocaobrindecurso; 

@Named
@ViewScoped
public class ConsCidadeBrindesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Promocaobrindecurso promocaobrindecurso;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		promocaobrindecurso = (Promocaobrindecurso) session.getAttribute("promocaobrindecurso");
		session.removeAttribute("promocaotaxacurso"); 
	}
 
	public Promocaobrindecurso getPromocaobrindecurso() {
		return promocaobrindecurso;
	}
 
	public void setPromocaobrindecurso(Promocaobrindecurso promocaobrindecurso) {
		this.promocaobrindecurso = promocaobrindecurso;
	} 
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
