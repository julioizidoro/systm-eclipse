package br.com.travelmate.managerBean.coprodutos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Valorcoprodutos;

@Named
@ViewScoped
public class CoSuplementosMB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Valorcoprodutos> listavalorprodutos;
	private Coprodutos coprodutos;
	
	
	@PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        coprodutos = (Coprodutos) session.getAttribute("coprodutos");
        session.removeAttribute("coprodutos");
        gerarListaSuplementos();
     }
	
	
	
	 public List<Valorcoprodutos> getListavalorprodutos() {
		return listavalorprodutos;
	}



	public void setListavalorprodutos(List<Valorcoprodutos> listavalorprodutos) {
		this.listavalorprodutos = listavalorprodutos;
	}



	public Coprodutos getCoprodutos() {
		return coprodutos;
	}

	public void setCoprodutos(Coprodutos coprodutos) {
		this.coprodutos = coprodutos;
	}

	public void gerarListaSuplementos(){
		String sql ="Select v from Valorcoprodutos v where v.coprodutos.idcoprodutos=" + coprodutos.getIdcoprodutos() + " and v.produtosuplemento<>'valor'";
        ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
        listavalorprodutos = valorCoProdutosFacade.listar(sql);
        if (listavalorprodutos==null){
        	listavalorprodutos = new ArrayList<Valorcoprodutos>();
        }
   }
	
	 public String excluir(Valorcoprodutos valorcoprodutos) {
		 ValorCoProdutosFacade valorCoProdutosFacade= new ValorCoProdutosFacade();
		 valorCoProdutosFacade.excluir(valorcoprodutos.getIdvalorcoprodutos());
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
	        gerarListaSuplementos();
	        return "";
	    }

}
