package br.com.travelmate.managerBean.ficha.voluntariado;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.model.Dadospais;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class FichaVoluntariadoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private Voluntariado voluntariado;
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
	private boolean habilitarObservacao = false;
	private Seguroviagem seguroviagem;
	private String moedaNacional;
	

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		voluntariado = (Voluntariado) session.getAttribute("voluntariado");
		if (voluntariado != null) {
			vendas = voluntariado.getVendas();
			valorTotalMoeda = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
			
			if (voluntariado.getCartaoVTM().equalsIgnoreCase("Sim")) {
				habilitarCartãoVtm = true;
			}else{
				habilitarCartãoVtm = false;
			}
			SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
			seguroviagem = seguroViagemFacade.consultarSeguroCurso(voluntariado.getVendas().getIdvendas());
			if (seguroviagem != null && seguroviagem.getIdseguroViagem() != null) {
				habilitarSeguroViagem = true;
			}else {
				habilitarSeguroViagem = false;
			}
		}
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		moedaNacional = vendas.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + " de "+ Formatacao.getMes() + " de " + Formatacao.getAnoData(dataHoje);
		if (vendas.getFormapagamento() != null) {
			totalPagamento = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
		}
		if (vendas.getFormapagamento() != null && (vendas.getFormapagamento().getObservacoes() != null && vendas.getFormapagamento().getObservacoes().length() > 0)) {
			habilitarObservacao = true;
		}
	}



	public String getContrato() {
		return contrato;
	}



	public void setContrato(String contrato) {
		this.contrato = contrato;
	}





	public Voluntariado getVoluntariado() {
		return voluntariado;
	}



	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
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



	public boolean isHabilitarObservacao() {
		return habilitarObservacao;
	}



	public void setHabilitarObservacao(boolean habilitarObservacao) {
		this.habilitarObservacao = habilitarObservacao;
	}
	
	
	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}



	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}



	public String getMoedaNacional() {
		return moedaNacional;
	}



	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}



	public String verificacaoSeguroCancelamento() {
		if (seguroviagem != null) {
			if (seguroviagem.isSegurocancelamento()) {
				return "Sim";
			}else {
				return "Não";
			}
		}
		return "";	
	}

}
