/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.model.Valorcoprodutos;

/**
 *
 * @author Wolverine
 */
public class ProdutosOrcamentoBean {

	private Valorcoprodutos valorcoprodutos;
	private Float valorOrigianl;
	private Float valorOriginalRS;
	private Float valorOriginalAcOpcional;
	private Float valorRSacOpcional;
	private double numeroSemanasAcOpcional;
	private Float valorPromocional;
	private Float valorPromocionalRS;
	private boolean selecionadoOpcional;
	private boolean selecionado;
	private double numeroSemanas;
	private int idproduto;
	private int linhaSuplementoAcomodacao;
	private int linhaObrigatorioAcomodacao;
	private String descricaopromocao;
	private boolean promocao;
	private boolean somarvalortotal=false;
	private String descricaobrinde;

	public int getLinhaObrigatorioAcomodacao() {
		return linhaObrigatorioAcomodacao;
	}

	public void setLinhaObrigatorioAcomodacao(int linhaObrigatorioAcomodacao) {
		this.linhaObrigatorioAcomodacao = linhaObrigatorioAcomodacao;
	}

	public ProdutosOrcamentoBean() {
		super();
		setLinhaSuplementoAcomodacao(-1);
		setLinhaObrigatorioAcomodacao(-1);
	}

	public Valorcoprodutos getValorcoprodutos() {
		return valorcoprodutos;
	}

	public void setValorcoprodutos(Valorcoprodutos valorcoprodutos) {
		this.valorcoprodutos = valorcoprodutos;
	}

	public Float getValorOrigianl() {
		return valorOrigianl;
	}

	public void setValorOrigianl(Float valorOrigianl) {
		this.valorOrigianl = valorOrigianl;
	}

	public Float getValorOriginalRS() {
		return valorOriginalRS;
	}

	public void setValorOriginalRS(Float valorOriginalRS) {
		this.valorOriginalRS = valorOriginalRS;
	}

	public Float getValorPromocionalRS() {
		return valorPromocionalRS;
	}

	public void setValorPromocionalRS(Float valorPromocionalRS) {
		this.valorPromocionalRS = valorPromocionalRS;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public Float getValorPromocional() {
		return valorPromocional;
	}

	public void setValorPromocional(Float valorPromocional) {
		this.valorPromocional = valorPromocional;
	}

	public boolean isSelecionadoOpcional() {
		return selecionadoOpcional;
	}

	public void setSelecionadoOpcional(boolean selecionadoOpcional) {
		this.selecionadoOpcional = selecionadoOpcional;
	}

	public double getNumeroSemanas() {
		return numeroSemanas;
	}

	public int getLinhaSuplementoAcomodacao() {
		return linhaSuplementoAcomodacao;
	}

	public void setLinhaSuplementoAcomodacao(int linhaSuplementoAcomodacao) {
		this.linhaSuplementoAcomodacao = linhaSuplementoAcomodacao;
	}

	public void setNumeroSemanas(double numeroSemanas) {
		this.numeroSemanas = numeroSemanas;
	}

	public int getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	public Float getValorOriginalAcOpcional() {
		return valorOriginalAcOpcional;
	}

	public void setValorOriginalAcOpcional(Float valorOriginalAcOpcional) {
		this.valorOriginalAcOpcional = valorOriginalAcOpcional;
	}

	public double getNumeroSemanasAcOpcional() {
		return numeroSemanasAcOpcional;
	}

	public void setNumeroSemanasAcOpcional(double numeroSemanasAcOpcional) {
		this.numeroSemanasAcOpcional = numeroSemanasAcOpcional;
	}

	public Float getValorRSacOpcional() {
		return valorRSacOpcional;
	}

	public void setValorRSacOpcional(Float valorRSacOpcional) {
		this.valorRSacOpcional = valorRSacOpcional;
	}

	public String getDescricaopromocao() {
		return descricaopromocao;
	}

	public void setDescricaopromocao(String descricaopromocao) {
		this.descricaopromocao = descricaopromocao;
	}

	public boolean isPromocao() {
		return promocao;
	}

	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
	}

	public String getDescricaobrinde() {
		return descricaobrinde;
	}

	public void setDescricaobrinde(String descricaobrinde) {
		this.descricaobrinde = descricaobrinde;
	}

	public boolean isSomarvalortotal() {
		return somarvalortotal;
	}

	public void setSomarvalortotal(boolean somarvalortotal) {
		this.somarvalortotal = somarvalortotal;
	}

}
