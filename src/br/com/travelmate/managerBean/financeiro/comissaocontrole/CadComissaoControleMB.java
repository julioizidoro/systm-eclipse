package br.com.travelmate.managerBean.financeiro.comissaocontrole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ComissaoControleFacade;
import br.com.travelmate.model.Comissaocontrole;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadComissaoControleMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private Comissaocontrole comissaocontrole;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		comissaocontrole = (Comissaocontrole) session.getAttribute("comissaocontrole");
		session.removeAttribute("comissaocontrole");
		listaUnidade = GerarListas.listarUnidade();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (comissaocontrole == null) {
			comissaocontrole = new Comissaocontrole();
		}else{
			unidadenegocio = comissaocontrole.getUnidadenegocio();
		}
	}



	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}



	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}



	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}



	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}



	public Comissaocontrole getComissaocontrole() {
		return comissaocontrole;
	}



	public void setComissaocontrole(Comissaocontrole comissaocontrole) {
		this.comissaocontrole = comissaocontrole;
	}
	
	
	public boolean validarDados(){
		if (unidadenegocio == null) {
			Mensagem.lancarMensagemInfo("Selecione Unidade", "");
			return false;
		}
		
		if (comissaocontrole.getMes() == null) {
			Mensagem.lancarMensagemInfo("Selecione o mês", "");
			return false;
		}
		
		if (comissaocontrole.getAno() == null) {
			Mensagem.lancarMensagemInfo("Escolha o ano", "");
			return false;
		}
		return true;
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Comissaocontrole());
	}
	
	
	public void salvar(){
		ComissaoControleFacade comissaoControleFacade = new ComissaoControleFacade();
		if (validarDados()) {
			comissaocontrole.setUnidadenegocio(unidadenegocio);
			comissaocontrole = comissaoControleFacade.salvar(comissaocontrole);
			RequestContext.getCurrentInstance().closeDialog(comissaocontrole);
		}
	}

}
