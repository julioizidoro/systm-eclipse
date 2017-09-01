package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Promocaoacomodacao;

@Named
@ViewScoped
public class ConsCidadeAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Promocaoacomodacao promocaoacomodacao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		promocaoacomodacao = (Promocaoacomodacao) session.getAttribute("promocaoacomodacao");
		session.removeAttribute("promocaoacomodacao"); 
	}

	
	
	public Promocaoacomodacao getPromocaoacomodacao() {
		return promocaoacomodacao;
	}



	public void setPromocaoacomodacao(Promocaoacomodacao promocaoacomodacao) {
		this.promocaoacomodacao = promocaoacomodacao;
	}



	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	

}
