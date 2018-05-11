package br.com.travelmate.managerBean.ficha.curso;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.DadosPaisFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Dadospais;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class FichaCursoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private Curso curso;
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
	private Seguroviagem seguroviagem;
	private int aulasSemana;
	private float totalPagamento = 0.0f;
	

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		curso = (Curso) session.getAttribute("curso");
		session.removeAttribute("curso");
		if (curso != null) {
			vendas = curso.getVendas();
			valorTotalMoeda = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
			if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("S")) {
				habilitarSegundoCurso = true;
			}else{
				habilitarSegundoCurso = false;
			}
			int idadeCliente = Formatacao.calcularIdade(curso.getVendas().getCliente().getDataNascimento());
			if (idadeCliente < 18) {
				DadosPaisFacade dadosPaisFacade = new DadosPaisFacade();
				dadospais = dadosPaisFacade.consultarVendas(curso.getVendas().getIdvendas());
				habilitarDadosPais = true;
			}else{
				habilitarDadosPais = false;
			}
			if (curso.getCaratoVTM().equalsIgnoreCase("Sim")) {
				habilitarCartãoVtm = true;
			}else{
				habilitarCartãoVtm = false;
			}
			if (curso.getPossuiSeguroGovernamental().equalsIgnoreCase("S")) {
				habilitarSeguroObrigatorio = true;
			}else{
				habilitarSeguroObrigatorio = false;
			}
			SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
			seguroviagem = seguroViagemFacade.consultarSeguroCurso(curso.getVendas().getIdvendas());
			if (seguroviagem != null && seguroviagem.getIdseguroViagem() != null) {
				habilitarSeguroViagem = true;
			}else {
				habilitarSeguroViagem = false;
			}
		}
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + Formatacao.getMes() + " " + Formatacao.getAnoData(dataHoje); 
		aulasSemana = curso.getAulassemana().intValue();
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



	public Curso getCurso() {
		return curso;
	}



	public void setCurso(Curso curso) {
		this.curso = curso;
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



	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}



	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
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



	public String verificacaoSeguroCancelamento(boolean seguroCancelamento) {
		if (seguroCancelamento) {
			return "SIM";
		}else {
			return "NÃO";
		}
	}

}
