package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
 
import br.com.travelmate.facade.PromocaoBrindeCursoFacade; 
import br.com.travelmate.model.Promocaobrindecurso; 

@Named
@ViewScoped
public class EditarPromocoesBrindesMB implements Serializable {

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
		session.removeAttribute("promocaobrindecurso");
	}

	public Promocaobrindecurso getPromocaobrindecurso() {
		return promocaobrindecurso;
	}

	public void setPromocaobrindecurso(Promocaobrindecurso promocaobrindecurso) {
		this.promocaobrindecurso = promocaobrindecurso;
	}

	public String confirmar() {
		PromocaoBrindeCursoFacade promocaoBrindeCursoFacade = new PromocaoBrindeCursoFacade();
		promocaobrindecurso = promocaoBrindeCursoFacade.salvar(promocaobrindecurso);
		return "consPromocoesBrindes";
	}

	public String cancelar() {
		return "consPromocoesBrindes";
	}

}
