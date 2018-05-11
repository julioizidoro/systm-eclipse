package br.com.travelmate.managerBean.ficha.worktravel;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Dadospais;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class FichaWorkTravelMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private Worktravel worktravel;
	private Vendas vendas;
	private Date dataHoje;
	private String dataExtanso;
	private int diaSemana;
	private float valorTotalMoeda = 0.0f;
	private boolean habilitarSegundoCurso = false;
	private Dadospais dadospais;
	private boolean habilitarDadosPais = false;
	private boolean habilitarCartãoVtm = true;
	private boolean habilitarSeguroViagem = false;
	private boolean habilitarSeguroObrigatorio = true;
	private float totalPagamento = 0.0f;
	

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		worktravel = (Worktravel) session.getAttribute("worktravel");
		session.removeAttribute("worktravel");
		if (worktravel != null) {
			vendas = worktravel.getVendas();
			valorTotalMoeda = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
			if (worktravel.getCartaoVTM() != null && worktravel.getCartaoVTM().equalsIgnoreCase("Sim")) {
				habilitarCartãoVtm = true;
			}else{
				habilitarCartãoVtm = false;
			}
		}
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + " de "+ Formatacao.getMes() + " de " + Formatacao.getAnoData(dataHoje); 
		if (vendas.getFormapagamento() != null) {
			totalPagamento = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
		}
	}



	public String getContrato() {
		return contrato;
	}



	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	

	public Worktravel getWorktravel() {
		return worktravel;
	}



	public void setWorktravel(Worktravel worktravel) {
		this.worktravel = worktravel;
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



	public Dadospais getDadospais() {
		return dadospais;
	}



	public void setDadospais(Dadospais dadospais) {
		this.dadospais = dadospais;
	}



	public boolean isHabilitarDadosPais() {
		return habilitarDadosPais;
	}



	public void setHabilitarDadosPais(boolean habilitarDadosPais) {
		this.habilitarDadosPais = habilitarDadosPais;
	}



	public boolean isHabilitarCartãoVtm() {
		return habilitarCartãoVtm;
	}



	public void setHabilitarCartãoVtm(boolean habilitarCartãoVtm) {
		this.habilitarCartãoVtm = habilitarCartãoVtm;
	}



	public boolean isHabilitarSeguroViagem() {
		return habilitarSeguroViagem;
	}



	public void setHabilitarSeguroViagem(boolean habilitarSeguroViagem) {
		this.habilitarSeguroViagem = habilitarSeguroViagem;
	}



	public boolean isHabilitarSeguroObrigatorio() {
		return habilitarSeguroObrigatorio;
	}



	public void setHabilitarSeguroObrigatorio(boolean habilitarSeguroObrigatorio) {
		this.habilitarSeguroObrigatorio = habilitarSeguroObrigatorio;
	}
	
	
	public float getTotalPagamento() {
		return totalPagamento;
	}



	public void setTotalPagamento(float totalPagamento) {
		this.totalPagamento = totalPagamento;
	}



	public String retornarCondicao(boolean verificacao) {
		if (verificacao) {
			return "X";
		}
		return "";
	}
	
	
	
	

}
