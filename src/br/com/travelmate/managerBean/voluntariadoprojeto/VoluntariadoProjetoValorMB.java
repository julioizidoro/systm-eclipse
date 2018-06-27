/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.voluntariadoprojeto;

import br.com.travelmate.facade.VoluntariadoProjetoValorFacade;
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.model.Voluntariadoprojetovalor;

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

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class VoluntariadoProjetoValorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Voluntariadoprojeto voluntariadoprojeto;
	private List<Voluntariadoprojetovalor> listaValores;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		voluntariadoprojeto = (Voluntariadoprojeto) session.getAttribute("voluntariadoprojeto");
		session.removeAttribute("voluntariadoprojeto");
		if (voluntariadoprojeto != null) {
			gerarListaValores();
		} else {
			listaValores = new ArrayList<>();
		}
	}

	public Voluntariadoprojeto getVoluntariadoprojeto() {
		return voluntariadoprojeto;
	}

	public void setVoluntariadoprojeto(Voluntariadoprojeto voluntariadoprojeto) {
		this.voluntariadoprojeto = voluntariadoprojeto;
	}

	public List<Voluntariadoprojetovalor> getListaValores() {
		return listaValores;
	}

	public void setListaValores(List<Voluntariadoprojetovalor> listaValores) {
		this.listaValores = listaValores;
	}

	public void gerarListaValores() {
		String sql = "Select v from Voluntariadoprojetovalor v where v.voluntariadoprojeto.idvoluntariadoprojeto="
				+ voluntariadoprojeto.getIdvoluntariadoprojeto() + " order by v.datainicial";
		VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
		listaValores = voluntariadoProjetoValorFacade.listar(sql);
		if (listaValores == null) {
			listaValores = new ArrayList<>();
		}
	}

	public String cadValoresProdutos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariadoprojeto", voluntariadoprojeto);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 440);
		RequestContext.getCurrentInstance().openDialog("cadVoluntariadoProjetoValor", options, null);
		return "";
	} 

	public String voltar() {   
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorcidade", voluntariadoprojeto.getFornecedorcidade());
		return "consVoluntariadoProjeto";
	}

	public String editar(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("voluntariadoprojetovalor", voluntariadoprojetovalor);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 440);
		RequestContext.getCurrentInstance().openDialog("cadVoluntariadoProjetoValor", options, null);
		return "";
	}

	public void retornoDialogoNovo(SelectEvent event) {
		Voluntariadoprojetovalor voluntariadoprojetovalor = (Voluntariadoprojetovalor) event.getObject();
		listaValores.add(voluntariadoprojetovalor);
	}

	public void retornoDialogoEditar() {
		gerarListaValores();

	}
}
