package br.com.travelmate.managerBean.cartaocredito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.model.Cartaocredito; 

@Named
@ViewScoped
public class CartaoCreditoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome=""; 
	private List<Cartaocredito> listaCartaoCredito; 
	
	@PostConstruct
	public void init() { 
		gerarlistaCartaoCredito(); 
	}
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}  

	public List<Cartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	} 

	public void setListaCartaoCredito(List<Cartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	} 

	public String novo(){
        return "cadCartaoCredito";
    }
 
	public void gerarlistaCartaoCredito(){
		CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
        listaCartaoCredito = cartaoCreditoFacade.listar();
        if (listaCartaoCredito==null){
        	listaCartaoCredito = new ArrayList<Cartaocredito>();
        }
    }
	 
	
	public String editar(Cartaocredito cartaocredito){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cartaocredito", cartaocredito);
        return "cadCartaoCredito";
    }
	
	public void pesquisar(){  
		 CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
		 String sql="select c from Cartaocredito c where c.nome like '%"+nome+"%' order by c.nome";
	     listaCartaoCredito = cartaoCreditoFacade.listar(sql);
	}
	
	public void limpar(){ 
		nome ="";
		gerarlistaCartaoCredito();
	}
}
