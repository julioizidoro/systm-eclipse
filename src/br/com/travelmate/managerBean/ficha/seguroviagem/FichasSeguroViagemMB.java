package br.com.travelmate.managerBean.ficha.seguroviagem;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;


@Named
@ViewScoped
public class FichasSeguroViagemMB implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private Seguroviagem seguroviagem;
	private Vendas vendas;
	private Date dataHoje;
	private String dataExtanso;
	private int diaSemana;
	private float valorTotalMoeda = 0.0f;
	private boolean habilitarSegundoCurso = false;
	

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		seguroviagem = (Seguroviagem) session.getAttribute("seguroviagem");
		if (seguroviagem != null) {
			vendas = seguroviagem.getVendas();
		}
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + Formatacao.getMes() + " " + Formatacao.getAnoData(dataHoje); 
	}



	public String getContrato() {
		return contrato;
	}



	public void setContrato(String contrato) {
		this.contrato = contrato;
	}






	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}



	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}



	public Date getDataHoje() {
		return dataHoje;
	}



	public void setDataHoje(Date dataHoje) {
		this.dataHoje = dataHoje;
	}



	public String getDataExtanso() {
		return dataExtanso;
	}



	public void setDataExtanso(String dataExtanso) {
		this.dataExtanso = dataExtanso;
	}



	public float getValorTotalMoeda() {
		return valorTotalMoeda;
	}



	public void setValorTotalMoeda(float valorTotalMoeda) {
		this.valorTotalMoeda = valorTotalMoeda;
	}



	public boolean isHabilitarSegundoCurso() {
		return habilitarSegundoCurso;
	}



	public void setHabilitarSegundoCurso(boolean habilitarSegundoCurso) {
		this.habilitarSegundoCurso = habilitarSegundoCurso;
	}
	
	
	public String verificacaoSeguroCancelamento(boolean seguroCancelamento) {
		if (seguroCancelamento) {
			return "Sim";
		}else {
			return "Não";
		}
	}


}
