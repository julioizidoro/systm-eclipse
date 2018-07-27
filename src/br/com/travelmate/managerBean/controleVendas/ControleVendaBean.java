package br.com.travelmate.managerBean.controleVendas;

import java.util.Date;

import br.com.travelmate.model.Vendas;

public class ControleVendaBean {

	
	private Vendas vendas;
	private Date dataInicio;
	private Date dataInscricao;
	
	
	public Vendas getVendas() {
		return vendas;
	}
	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataInscricao() {
		return dataInscricao;
	}
	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	
	
	
	
	
}
