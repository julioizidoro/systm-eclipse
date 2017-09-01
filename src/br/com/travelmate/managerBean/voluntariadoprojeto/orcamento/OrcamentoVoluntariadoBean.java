/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.voluntariadoprojeto.orcamento;

import java.util.Date;
import java.util.List;

import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosExtrasBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.model.Voluntariadoprojetoacomodacao;
import br.com.travelmate.model.Voluntariadoprojetocurso;
import br.com.travelmate.model.Voluntariadoprojetovalor;

/**
 *
 * @author Kamila
 */
public class OrcamentoVoluntariadoBean {

	private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado;
	private Cliente cliente;
	private Voluntariadoprojetovalor voluntariadoprojetovalor;
	private Float valor;
	private Float valorRS; 
	private int numeroSemanasAdicionais; 
	private String totalnumerosemanas; 
	private Float valorSemanaAdc;
	private Float valorSemanaAdcRS;   
	private boolean possuiCurso;
	private boolean possuiAcomodacao;
	private boolean possuiSemanaAdicional;
	private boolean possuiTransfer;
	private Date datainicial;
	private Date datatermino;
	private Voluntariadoprojetocurso voluntariadoprojetocurso;
	private Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao;
	private Cambio cambio;
	private float valorcambio;
	private boolean possuiSeguroViagem;
	private Seguroviagem seguroviagem;
	private List<ProdutosExtrasBean> listaOutrosProdutos; 
	private List<Ocursodesconto> listaDesconto;
	private Valoresseguro valorSeguro;
	private float valorUtilitarioRS = 0.0f;
	private float valorTotalSeguroDolar = 0.0f;
	private float total = 0.0f;
	private float totalRS = 0.0f;
	private Orcamentovoluntariadoformapagamento orcamentovoluntariadoformapagamento;
	private Produtosorcamento taxatm;
	private float valortaxatm;
	private float valortaxatmRS;
	
	public OrcamentoVoluntariadoBean() {
		super();
	}

	public Orcamentoprojetovoluntariado getOrcamentoprojetovoluntariado() {
		return orcamentoprojetovoluntariado;
	}

	public void setOrcamentoprojetovoluntariado(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
		this.orcamentoprojetovoluntariado = orcamentoprojetovoluntariado;
	}

	public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
		return voluntariadoprojetovalor;
	}

	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Float getValorRS() {
		return valorRS;
	}

	public void setValorRS(Float valorRS) {
		this.valorRS = valorRS;
	}

	public Float getValorSemanaAdc() {
		return valorSemanaAdc;
	}

	public void setValorSemanaAdc(Float valorSemanaAdc) {
		this.valorSemanaAdc = valorSemanaAdc;
	}

	public Float getValorSemanaAdcRS() {
		return valorSemanaAdcRS;
	}

	public void setValorSemanaAdcRS(Float valorSemanaAdcRS) {
		this.valorSemanaAdcRS = valorSemanaAdcRS;
	}

	public int getNumeroSemanasAdicionais() {
		return numeroSemanasAdicionais;
	}

	public void setNumeroSemanasAdicionais(int numeroSemanasAdicionais) {
		this.numeroSemanasAdicionais = numeroSemanasAdicionais;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isPossuiCurso() {
		return possuiCurso;
	}

	public void setPossuiCurso(boolean possuiCurso) {
		this.possuiCurso = possuiCurso;
	}

	public boolean isPossuiAcomodacao() {
		return possuiAcomodacao;
	}

	public void setPossuiAcomodacao(boolean possuiAcomodacao) {
		this.possuiAcomodacao = possuiAcomodacao;
	}

	public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public Date getDatatermino() {
		return datatermino;
	}

	public void setDatatermino(Date datatermino) {
		this.datatermino = datatermino;
	}

	public Voluntariadoprojetocurso getVoluntariadoprojetocurso() {
		return voluntariadoprojetocurso;
	}

	public void setVoluntariadoprojetocurso(Voluntariadoprojetocurso voluntariadoprojetocurso) {
		this.voluntariadoprojetocurso = voluntariadoprojetocurso;
	}

	public Voluntariadoprojetoacomodacao getVoluntariadoprojetoacomodacao() {
		return voluntariadoprojetoacomodacao;
	}

	public void setVoluntariadoprojetoacomodacao(Voluntariadoprojetoacomodacao voluntariadoprojetoacomodacao) {
		this.voluntariadoprojetoacomodacao = voluntariadoprojetoacomodacao;
	}

	public String getTotalnumerosemanas() {
		return totalnumerosemanas;
	}

	public void setTotalnumerosemanas(String totalnumerosemanas) {
		this.totalnumerosemanas = totalnumerosemanas;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public float getValorcambio() {
		return valorcambio;
	}

	public void setValorcambio(float valorcambio) {
		this.valorcambio = valorcambio;
	}

	public boolean isPossuiSemanaAdicional() {
		return possuiSemanaAdicional;
	}

	public void setPossuiSemanaAdicional(boolean possuiSemanaAdicional) {
		this.possuiSemanaAdicional = possuiSemanaAdicional;
	}

	public boolean isPossuiTransfer() {
		return possuiTransfer;
	}

	public void setPossuiTransfer(boolean possuiTransfer) {
		this.possuiTransfer = possuiTransfer;
	}

	public boolean isPossuiSeguroViagem() {
		return possuiSeguroViagem;
	}

	public void setPossuiSeguroViagem(boolean possuiSeguroViagem) {
		this.possuiSeguroViagem = possuiSeguroViagem;
	}

	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}

	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}

	public List<ProdutosExtrasBean> getListaOutrosProdutos() {
		return listaOutrosProdutos;
	}

	public void setListaOutrosProdutos(List<ProdutosExtrasBean> listaOutrosProdutos) {
		this.listaOutrosProdutos = listaOutrosProdutos;
	}

	public float getValortaxatmRS() {
		return valortaxatmRS;
	}

	public void setValortaxatmRS(float valortaxatmRS) {
		this.valortaxatmRS = valortaxatmRS;
	}

	public Produtosorcamento getTaxatm() {
		return taxatm;
	}

	public void setTaxatm(Produtosorcamento taxatm) {
		this.taxatm = taxatm;
	}

	public float getValortaxatm() {
		return valortaxatm;
	}

	public void setValortaxatm(float valortaxatm) {
		this.valortaxatm = valortaxatm;
	}

	public List<Ocursodesconto> getListaDesconto() {
		return listaDesconto;
	}

	public void setListaDesconto(List<Ocursodesconto> listaDesconto) {
		this.listaDesconto = listaDesconto;
	}

	public Valoresseguro getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(Valoresseguro valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public float getValorUtilitarioRS() {
		return valorUtilitarioRS;
	}

	public void setValorUtilitarioRS(float valorUtilitarioRS) {
		this.valorUtilitarioRS = valorUtilitarioRS;
	}

	public float getValorTotalSeguroDolar() {
		return valorTotalSeguroDolar;
	}

	public void setValorTotalSeguroDolar(float valorTotalSeguroDolar) {
		this.valorTotalSeguroDolar = valorTotalSeguroDolar;
	}

	public Orcamentovoluntariadoformapagamento getOrcamentovoluntariadoformapagamento() {
		return orcamentovoluntariadoformapagamento;
	}

	public void setOrcamentovoluntariadoformapagamento(
			Orcamentovoluntariadoformapagamento orcamentovoluntariadoformapagamento) {
		this.orcamentovoluntariadoformapagamento = orcamentovoluntariadoformapagamento;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getTotalRS() {
		return totalRS;
	}

	public void setTotalRS(float totalRS) {
		this.totalRS = totalRS;
	}

	
}
