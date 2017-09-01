package br.com.travelmate.managerBean.financeiro.planoContas;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.model.Planoconta;

@Named
@ViewScoped
public class CadPlanoContaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Planoconta planoconta;
	private int numero;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		planoconta = (Planoconta) session.getAttribute("planoconta");
		session.removeAttribute("planoconta");
		if (planoconta==null){
			PlanoContaFacade planoContaFacade = new PlanoContaFacade();
			numero = planoContaFacade.gerarNumeroConta();
			planoconta = new Planoconta();
		}else {
			numero = planoconta.getIdplanoconta();
		}
	}


	public Planoconta getPlanoconta() {
		return planoconta;
	}


	public void setPlanoconta(Planoconta planoconta) {
		this.planoconta = planoconta;
	}
	
	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String salvar(){
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		planoContaFacade.salvar(planoconta);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void salvarPlanoConta(){
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		planoContaFacade.salvar(planoconta);
	}

}
