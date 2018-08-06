/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.com.travelmate.managerBean.financeiro.faturafranquia;
 
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;  

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class ImprimirRelatorioFaturaMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L; 
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private int mes;
	private int ano; 
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private boolean habilitarunidade;

	@PostConstruct()
	public void init() {
		mes = Formatacao.getMesData(new Date())+1;
		ano = Formatacao.getAnoData(new Date());
		unidadenegocio =new Unidadenegocio();
		gerarListaUnidadeNegocio();
		if(usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
			habilitarunidade=false;
		}else{
			habilitarunidade=true;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	} 
	
	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isHabilitarunidade() {
		return habilitarunidade;
	}

	public void setHabilitarunidade(boolean habilitarunidade) {
		this.habilitarunidade = habilitarunidade;
	}

	public String gerarFatura(){
		if(mes>0 && ano>0){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("mes", mes);
			session.setAttribute("ano", ano);
			session.setAttribute("unidadenegocio", unidadenegocio);
			return "relatorioFatura";
		}
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";    
	}
	
	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
}
