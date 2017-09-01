package br.com.travelmate.managerBean.trainee.valoresTrainee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ProdutosTraineeFacade;
import br.com.travelmate.model.Produtostrainee;
import br.com.travelmate.model.Valorestrainee;

@Named
@ViewScoped
public class ProdutosTraineeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Valorestrainee valorestrainee;
	private List<Produtostrainee> listaProdutosTrainee;
	
	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        valorestrainee = (Valorestrainee) session.getAttribute("valorestrainee");
        session.removeAttribute("valorestrainee");
		carregarProdutos();
	}

	public Valorestrainee getValorestrainee() {
		return valorestrainee;
	}

	public void setValorestrainee(Valorestrainee valorestrainee) {
		this.valorestrainee = valorestrainee;
	}

	public List<Produtostrainee> getListaProdutosTrainee() {
		return listaProdutosTrainee;
	}

	public void setListaProdutosTrainee(List<Produtostrainee> listaProdutosTrainee) {
		this.listaProdutosTrainee = listaProdutosTrainee;
	}

	public void carregarProdutos() {
		ProdutosTraineeFacade produtosTraineeFacade = new ProdutosTraineeFacade();
		String sql;
		sql = "select p from Produtostrainee p where p.valorestrainee.idvalorestrainee="+valorestrainee.getIdvalorestrainee();
		listaProdutosTrainee= produtosTraineeFacade.listar(sql);
		if(listaProdutosTrainee==null){
			listaProdutosTrainee =  new ArrayList<Produtostrainee>();
		}
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String editar(Produtostrainee produtostrainee){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("produtosTrainee", produtostrainee);
		return "cadProdutosTrainee";
	}
}
