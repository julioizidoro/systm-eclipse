package br.com.travelmate.managerBean.vendas.relatoriometas;

import java.util.Date;

import br.com.travelmate.model.Produtos;

public class RelatorioMetasBean {
	
	private Date dataPriemiroInicio;
	private Date dataPriemiroFinal;
	private Date dataSegundoInicio;
	private Date dataSegundoFinal;
	private Produtos produto;
	
	
	public Date getDataPriemiroInicio() {
		return dataPriemiroInicio;
	}
	public void setDataPriemiroInicio(Date dataPriemiroInicio) {
		this.dataPriemiroInicio = dataPriemiroInicio;
	}
	public Date getDataPriemiroFinal() {
		return dataPriemiroFinal;
	}
	public void setDataPriemiroFinal(Date dataPriemiroFinal) {
		this.dataPriemiroFinal = dataPriemiroFinal;
	}
	public Date getDataSegundoInicio() {
		return dataSegundoInicio;
	}
	public void setDataSegundoInicio(Date dataSegundoInicio) {
		this.dataSegundoInicio = dataSegundoInicio;
	}
	public Date getDataSegundoFinal() {
		return dataSegundoFinal;
	}
	public void setDataSegundoFinal(Date dataSegundoFinal) {
		this.dataSegundoFinal = dataSegundoFinal;
	}
	public Produtos getProduto() {
		return produto;
	}
	public void setProduto(Produtos produto) {
		this.produto = produto;
	}
	
	
	

}
