package br.com.travelmate.managerBean.financeiro.vendamotivopendencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.VendaMotivoPendenciaFacade;
import br.com.travelmate.model.Vendamotivopendencia;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class VendaMotivoPendenciaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vendamotivopendencia> listaVendaMotivoPencencia;
	
	
	@PostConstruct
	public void init(){
		gerarListaMotivoPendencia();
	}
	
	
	
	
	public List<Vendamotivopendencia> getListaVendaMotivoPencencia() {
		return listaVendaMotivoPencencia;
	}




	public void setListaVendaMotivoPencencia(List<Vendamotivopendencia> listaVendaMotivoPencencia) {
		this.listaVendaMotivoPencencia = listaVendaMotivoPencencia;
	}




	public void gerarListaMotivoPendencia(){
		VendaMotivoPendenciaFacade vendaMotivoPendenciaFacade = new VendaMotivoPendenciaFacade();
		listaVendaMotivoPencencia = vendaMotivoPendenciaFacade.lista("Select v From Vendamotivopendencia v");
		if (listaVendaMotivoPencencia == null) {
			listaVendaMotivoPencencia = new ArrayList<>();
		}
		
	}
	
	
	public String editar(Vendamotivopendencia vendamotivopendencia) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendamotivopendencia", vendamotivopendencia);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadVendaMotivoPendencia", options, null);
		return "";
	}

	public String cadastrar() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadVendaMotivoPendencia", options, null);
		return "";
	}


	public void returnDialogNovo(SelectEvent event) {
		Vendamotivopendencia vendamotivopendencia = (Vendamotivopendencia) event.getObject();
		if (vendamotivopendencia.getIdvendamotivopendencia() != null) {
			listaVendaMotivoPencencia.add(vendamotivopendencia);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
	}
	
	
	public void returnDialogEdicao(SelectEvent event) {
		Vendamotivopendencia vendamotivopendencia = (Vendamotivopendencia) event.getObject();
		if (vendamotivopendencia.getIdvendamotivopendencia() != null) {
			gerarListaMotivoPendencia();
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} 
	}

}
