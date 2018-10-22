package br.com.travelmate.managerBean.curso.controle;

import java.util.Date;

import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Unidadenegocio;

public class ControleVistosBean {

	
	private Date dataEmbarque;
	private String situacao;
	private String nomeCliente;
	private Unidadenegocio Unidade;
	private Pais pais;
	private String tipoArquivo;
	private String escola;
	private Date dataInicio;
	private Controlecurso controlecurso;
	private String cor;
	
	
	public ControleVistosBean() {
	}


	
	public Date getDataEmbarque() {
		return dataEmbarque;
	}


	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public Unidadenegocio getUnidade() {
		return Unidade;
	}


	public void setUnidade(Unidadenegocio unidade) {
		Unidade = unidade;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public String getTipoArquivo() {
		return tipoArquivo;
	}


	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}


	public String getEscola() {
		return escola;
	}


	public void setEscola(String escola) {
		this.escola = escola;
	}


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}



	public Controlecurso getControlecurso() {
		return controlecurso;
	}



	public void setControlecurso(Controlecurso controlecurso) {
		this.controlecurso = controlecurso;
	}



	public String getCor() {
		return cor;
	}



	public void setCor(String cor) {
		this.cor = cor;
	}


	
	
	
	
	
}
