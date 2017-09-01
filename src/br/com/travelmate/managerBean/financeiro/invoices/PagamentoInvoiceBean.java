/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.invoices;

import java.util.Date;

/**
 *
 * @author Wolverine
 */
public class PagamentoInvoiceBean {
    
	private int escala;
    private String fornecedor;
    private String pais;
    private String cidade;
    private String cliente;
    private String cpf;
    private String status;
    private String moeda;
    private Float valornet;
    private Float credito;
    private Date previsaoPagamento;
    private Date inicioCurso;
    private float valorReais;
    private String clientedevedor;
    
    public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}

	public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public Float getValornet() {
        return valornet;
    }

    public void setValornet(Float valornet) {
        this.valornet = valornet;
    }

    public Float getCredito() {
        return credito;
    }

    public void setCredito(Float credito) {
        this.credito = credito;
    }

    public Date getPrevisaoPagamento() {
        return previsaoPagamento;
    }

    public void setPrevisaoPagamento(Date previsaoPagamento) {
        this.previsaoPagamento = previsaoPagamento;
    }

    public float getValorReais() {
        return valorReais;
    }

    public void setValorReais(float valorReais) {
        this.valorReais = valorReais;
    }

	public Date getInicioCurso() {
		return inicioCurso;
	}

	public void setInicioCurso(Date inicioCurso) {
		this.inicioCurso = inicioCurso;
	}

	public String getClientedevedor() {
		return clientedevedor;
	}

	public void setClientedevedor(String clientedevedor) {
		this.clientedevedor = clientedevedor;
	}

	
    
    
}
