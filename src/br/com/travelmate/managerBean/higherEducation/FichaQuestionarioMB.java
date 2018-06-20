package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Questionariohe;

@Named
@ViewScoped
public class FichaQuestionarioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private Date dataHoje;
	private String dataExtanso;
	private float valorTotalMoeda = 0.0f;
	private int aulasSemana;
	private float totalPagamento = 0.0f;
	private int aulasSemana2;
	private Questionariohe questionariohe;
	private boolean habilitarTesteEnem = false;
	private boolean habilitarCidade = false;
	

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		questionariohe = (Questionariohe) session.getAttribute("questionariohe");
		session.removeAttribute("questionariohe");
		if (questionariohe.getResultadotesteonline() != null && questionariohe.getResultadotesteonline().equalsIgnoreCase("Sim")) {
			habilitarTesteEnem = true;
		}
		if (questionariohe.getPais1() != null && questionariohe.getPais1().equalsIgnoreCase("Portugal")) {
			habilitarCidade = true;
		}
	}



	public String getContrato() {
		return contrato;
	}



	public void setContrato(String contrato) {
		this.contrato = contrato;
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
	
	
	public int getAulasSemana() {
		return aulasSemana;
	}



	public void setAulasSemana(int aulasSemana) {
		this.aulasSemana = aulasSemana;
	}



	public float getTotalPagamento() {
		return totalPagamento;
	}



	public void setTotalPagamento(float totalPagamento) {
		this.totalPagamento = totalPagamento;
	}



	public int getAulasSemana2() {
		return aulasSemana2;
	}



	public void setAulasSemana2(int aulasSemana2) {
		this.aulasSemana2 = aulasSemana2;
	}



	public Questionariohe getQuestionariohe() {
		return questionariohe;
	}



	public void setQuestionariohe(Questionariohe questionariohe) {
		this.questionariohe = questionariohe;
	}



	public boolean isHabilitarTesteEnem() {
		return habilitarTesteEnem;
	}



	public void setHabilitarTesteEnem(boolean habilitarTesteEnem) {
		this.habilitarTesteEnem = habilitarTesteEnem;
	}



	public boolean isHabilitarCidade() {
		return habilitarCidade;
	}



	public void setHabilitarCidade(boolean habilitarCidade) {
		this.habilitarCidade = habilitarCidade;
	}



}
