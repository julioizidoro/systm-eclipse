package br.com.travelmate.managerBean.fornecedordocs;

import java.util.Date;

public class ArquivoBean {
	
	private String nome;
	private String nomeftp;
	private Date datavalidade;
	private Date datainicio;
	private boolean restrito;
	private String tipo;
	private String descricao;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeftp() {
		return nomeftp;
	}
	public void setNomeftp(String nomeftp) {
		this.nomeftp = nomeftp;
	}
	public Date getDatavalidade() {
		return datavalidade;
	}
	public void setDatavalidade(Date datavalidade) {
		this.datavalidade = datavalidade;
	}
	public Date getDatainicio() {
		return datainicio;
	}
	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}
	public boolean isRestrito() {
		return restrito;
	}
	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	

}
