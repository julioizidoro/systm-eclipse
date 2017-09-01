package br.com.travelmate.managerBean.banco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.model.Banco; 

@Named
@ViewScoped
public class BancoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome=""; 
	private List<Banco> listaBanco; 
	
	@PostConstruct
	public void init() { 
		gerarlistaBanco(); 
	}
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	} 
 
	public List<Banco> getListaBanco() {
		return listaBanco;
	}


	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}


	public String novoBanco(){
        return "cadBanco";
    }
 
	public void gerarlistaBanco(){
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco==null){
        	listaBanco = new ArrayList<Banco>();
        }
    }
	 
	
	public String editarBanco(Banco banco){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("banco", banco);
        return "cadBanco";
    }
	
	public void pesquisar(){  
		 BancoFacade bancoFacade = new BancoFacade();
		 String sql="select b from Banco b where b.nome like '%"+nome+"%' order by b.nome";
	     listaBanco = bancoFacade.listar(sql);
	}
	
	public void limpar(){ 
		nome ="";
		gerarlistaBanco();
	}
}
