package br.com.travelmate.managerBean.financeiro.contasReceber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.EventoContasReceberFacade;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Eventocontasreceber;

@Named
@ViewScoped
public class VisualizarEventosMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Eventocontasreceber> listaEventos;
	private Contasreceber contasreceber;
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		contasreceber  = (Contasreceber) session.getAttribute("contasreceber");
		session.removeAttribute("contasreceber");
		gerarListaEventos();
	}


	public List<Eventocontasreceber> getListaEventos() {
		return listaEventos;
	}


	public void setListaEventos(List<Eventocontasreceber> listaEventos) {
		this.listaEventos = listaEventos;
	}
	
	
	
	
	public Contasreceber getContasreceber() {
		return contasreceber;
	}


	public void setContasreceber(Contasreceber contasreceber) {
		this.contasreceber = contasreceber;
	}


	public void gerarListaEventos() {
		EventoContasReceberFacade eventoContasReceberFacade = new EventoContasReceberFacade();
		listaEventos = eventoContasReceberFacade.listar(contasreceber.getIdcontasreceber());
		if (listaEventos == null) {
			listaEventos = new ArrayList<Eventocontasreceber>();
		}
	}

}
