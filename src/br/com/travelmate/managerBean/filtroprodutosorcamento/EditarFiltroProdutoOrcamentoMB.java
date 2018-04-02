package br.com.travelmate.managerBean.filtroprodutosorcamento;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.model.Filtroorcamentoproduto;

@Named
@ViewScoped
public class EditarFiltroProdutoOrcamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Filtroorcamentoproduto> listaOrcamentoProduto;
	private Filtroorcamentoproduto filtroorcamentoproduto;
	
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		filtroorcamentoproduto = (Filtroorcamentoproduto) session.getAttribute("filtroorcamentoproduto");
		session.removeAttribute("filtroorcamentoproduto");
	}



	public List<Filtroorcamentoproduto> getListaOrcamentoProduto() {
		return listaOrcamentoProduto;
	}



	public void setListaOrcamentoProduto(List<Filtroorcamentoproduto> listaOrcamentoProduto) {
		this.listaOrcamentoProduto = listaOrcamentoProduto;
	}



	public Filtroorcamentoproduto getFiltroorcamentoproduto() {
		return filtroorcamentoproduto;
	}



	public void setFiltroorcamentoproduto(Filtroorcamentoproduto filtroorcamentoproduto) {
		this.filtroorcamentoproduto = filtroorcamentoproduto;
	}
	
	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	
	public void salvar() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		filtroorcamentoproduto = filtroOrcamentoProdutoFacade.salvar(filtroorcamentoproduto);
		RequestContext.getCurrentInstance().closeDialog(filtroorcamentoproduto);
	}
	
	
	
	
	

}
