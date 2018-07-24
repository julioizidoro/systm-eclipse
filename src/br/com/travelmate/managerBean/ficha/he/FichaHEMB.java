package br.com.travelmate.managerBean.ficha.he;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.DadosPaisFacade;
import br.com.travelmate.facade.HeParceirosFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Dadospais;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Heparceiros;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class FichaHEMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private He he;
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
	private boolean habilitarInformacoes = false;
	private Questionariohe questionariohe;
	private List<Heparceiros> listaHeParceiros; 
	

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		he = (He) session.getAttribute("he");
		session.removeAttribute("he");
		if (he != null) {
			vendas = he.getVendas();
			if (vendas.getFormapagamento() != null) {
				valorTotalMoeda = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
			}
		}
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + " de "+ Formatacao.getMes() + " de " + Formatacao.getAnoData(dataHoje);
		if (vendas.getFormapagamento() != null) {
			totalPagamento = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
		}
		if (vendas.getFormapagamento() != null && (vendas.getFormapagamento().getObservacoes() != null && vendas.getFormapagamento().getObservacoes().length() > 0)) {
			habilitarObservacao = true;
		}
		if (he.getQuestionario() > 0) {
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			questionariohe = questionarioHeFacade.consultar(he.getQuestionario());
			if (questionariohe != null && questionariohe.getIdquestionariohe() != null) {
				habilitarInformacoes = true;
			}
		}
		HeParceirosFacade heParceirosFacade = new HeParceirosFacade();
		listaHeParceiros = heParceirosFacade.listar("Select h From Heparceiros h WHERE h.he.idhe=" + he.getIdhe());
	}



	public String getContrato() {
		return contrato;
	}



	public void setContrato(String contrato) {
		this.contrato = contrato;
	}





	public He getHe() {
		return he;
	}



	public void setHe(He he) {
		this.he = he;
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



	public int getDiaSemana() {
		return diaSemana;
	}



	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}



	public boolean isHabilitarInformacoes() {
		return habilitarInformacoes;
	}



	public void setHabilitarInformacoes(boolean habilitarInformacoes) {
		this.habilitarInformacoes = habilitarInformacoes;
	}



	public Questionariohe getQuestionariohe() {
		return questionariohe;
	}



	public void setQuestionariohe(Questionariohe questionariohe) {
		this.questionariohe = questionariohe;
	}



	public List<Heparceiros> getListaHeParceiros() {
		return listaHeParceiros;
	}



	public void setListaHeParceiros(List<Heparceiros> listaHeParceiros) {
		this.listaHeParceiros = listaHeParceiros;
	}

}
