package br.com.travelmate.managerBean.vendas.relatoriometas;

import br.com.travelmate.model.Unidadenegocio;

public class RelatorioMetas {
	
	private Unidadenegocio unidade;
	private Float valorBruto01;
	private Float valorLiquido01;
	private int numeroProdutos01;
	private Float valorBruto02;
	private Float valorLiquido02;
	private int numeroProdutos02;
	private Float percentualBruto;
	private Float percentualLiquido;
	
	public RelatorioMetas(Unidadenegocio unidade) {
		this.unidade = unidade;
		this.valorBruto01 = 0.0f;
		this.valorLiquido01 = 0.0f;
		this.numeroProdutos01 =  0;
		this.valorBruto02 =  0.0f;
		this.valorLiquido02 =  0.0f;
		this.numeroProdutos02 =  0;
		this.percentualBruto =  0.0f;
		this.percentualLiquido =  0.0f;
	}

	public Unidadenegocio getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
	}

	public Float getValorBruto01() {
		return valorBruto01;
	}

	public void setValorBruto01(Float valorBruto01) {
		this.valorBruto01 = valorBruto01;
	}

	public Float getValorLiquido01() {
		return valorLiquido01;
	}

	public void setValorLiquido01(Float valorLiquido01) {
		this.valorLiquido01 = valorLiquido01;
	}

	public int getNumeroProdutos01() {
		return numeroProdutos01;
	}

	public void setNumeroProdutos01(int numeroProdutos01) {
		this.numeroProdutos01 = numeroProdutos01;
	}

	public Float getValorBruto02() {
		return valorBruto02;
	}

	public void setValorBruto02(Float valorBruto02) {
		this.valorBruto02 = valorBruto02;
	}

	public Float getValorLiquido02() {
		return valorLiquido02;
	}

	public void setValorLiquido02(Float valorLiquido02) {
		this.valorLiquido02 = valorLiquido02;
	}

	public int getNumeroProdutos02() {
		return numeroProdutos02;
	}

	public void setNumeroProdutos02(int numeroProdutos02) {
		this.numeroProdutos02 = numeroProdutos02;
	}

	public Float getPercentualBruto() {
		return percentualBruto;
	}

	public void setPercentualBruto(Float percentualBruto) {
		this.percentualBruto = percentualBruto;
	}

	public Float getPercentualLiquido() {
		return percentualLiquido;
	}

	public void setPercentualLiquido(Float percentualLiquido) {
		this.percentualLiquido = percentualLiquido;
	}
	
	
	
	
	
	

}
