package br.com.travelmate.bean;

import java.util.Date;

import br.com.travelmate.model.He;
import br.com.travelmate.model.Questionariohe;

public class ListaHeBean {
	 
	private int idVenda;
	private String situacao;
	private String nomecliente;
	private String fornecedor;
	private String unidade;
	private String consultor;
	private String status;
	private Date data;
	private Questionariohe questionariohe;
	private He he; 
	private boolean autorizado;
	private boolean desistencia;
	
	public int getIdVenda() {
		return idVenda;
	}
	
	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getNomecliente() {
		return nomecliente;
	}
	
	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}
	
	public String getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public String getUnidade() {
		return unidade;
	}
	
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	public String getConsultor() {
		return consultor;
	}
	
	public void setConsultor(String consultor) {
		this.consultor = consultor;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Questionariohe getQuestionariohe() {
		return questionariohe;
	}
	
	public void setQuestionariohe(Questionariohe questionariohe) {
		this.questionariohe = questionariohe;
	}
	
	public He getHe() {
		return he;
	}
	
	public void setHe(He he) {
		this.he = he;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public boolean isDesistencia() {
		return desistencia;
	}

	public void setDesistencia(boolean desistencia) {
		this.desistencia = desistencia;
	} 
	
}
