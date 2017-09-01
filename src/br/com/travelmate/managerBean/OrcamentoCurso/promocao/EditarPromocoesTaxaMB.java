package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import br.com.travelmate.facade.PromocaoTaxaCursoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Promocaotaxacurso;

@Named
@ViewScoped
public class EditarPromocoesTaxaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Promocaotaxacurso promocaotaxacurso;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		promocaotaxacurso = (Promocaotaxacurso) session.getAttribute("promocaotaxacurso");
		session.removeAttribute("promocaotaxacurso");
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Promocaotaxacurso getPromocaotaxacurso() {
		return promocaotaxacurso;
	}

	public void setPromocaotaxacurso(Promocaotaxacurso promocaotaxacurso) {
		this.promocaotaxacurso = promocaotaxacurso;
	}

	public String confirmar() {
		PromocaoTaxaCursoFacade promocaoTaxaCursoFacade = new PromocaoTaxaCursoFacade();
		promocaotaxacurso = promocaoTaxaCursoFacade.salvar(promocaotaxacurso);
		return "consPromocoesTaxas";
	}

	public String cancelar() {
		return "consPromocoesTaxas";
	}
}
