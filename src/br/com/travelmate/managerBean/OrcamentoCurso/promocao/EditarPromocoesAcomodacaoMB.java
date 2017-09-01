package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable; 
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
 
import br.com.travelmate.facade.PromocaoAcomodacaoFacade; 
import br.com.travelmate.model.Promocaoacomodacao; 

@Named
@ViewScoped
public class EditarPromocoesAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Promocaoacomodacao promocaoAcomodacao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		promocaoAcomodacao = (Promocaoacomodacao) session.getAttribute("promocaoAcomodacao");
		session.removeAttribute("promocaoAcomodacao");
	}

	public Promocaoacomodacao getPromocaoAcomodacao() {
		return promocaoAcomodacao;
	}

	public void setPromocaoAcomodacao(Promocaoacomodacao promocaoAcomodacao) {
		this.promocaoAcomodacao = promocaoAcomodacao;
	}

	public String confirmar() {
		PromocaoAcomodacaoFacade promocaoAcomodacaoFacade = new PromocaoAcomodacaoFacade();
		promocaoAcomodacao = promocaoAcomodacaoFacade.salvar(promocaoAcomodacao);
		return "consPromocoesAcomodacao";
	}

	public String cancelar() {
		return "consPromocoesAcomodacao";
	}

}
