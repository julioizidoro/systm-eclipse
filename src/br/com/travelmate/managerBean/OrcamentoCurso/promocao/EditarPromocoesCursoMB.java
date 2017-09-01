package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
 
import br.com.travelmate.facade.PromocaoCursoFacade; 
import br.com.travelmate.model.Promocaocurso; 

@Named
@ViewScoped
public class EditarPromocoesCursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Promocaocurso promocaocurso;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		promocaocurso = (Promocaocurso) session.getAttribute("promocaocurso");
		session.removeAttribute("promocaocurso");
	}

	public Promocaocurso getPromocaocurso() {
		return promocaocurso;
	}

	public void setPromocaocurso(Promocaocurso promocaocurso) {
		this.promocaocurso = promocaocurso;
	}

	public String confirmar() {
		PromocaoCursoFacade promocaoCursoFacade = new PromocaoCursoFacade();
		promocaocurso = promocaoCursoFacade.salvar(promocaocurso);
		return "consPromocoes";
	}

	public String cancelar() {
		return "consPromocoes";
	}

}
