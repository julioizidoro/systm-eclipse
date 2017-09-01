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
public class PerdaGanhoCambialBean {
    
    private String cliente;
    private float cambioVenda;
    private Date dataPagamento;;
    private String moeda;
    private float valorPago;
    private float cambioPagamento;
    private float taxa;
    private float iof;
    private float ganhoPerda;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public float getCambioVenda() {
        return cambioVenda;
    }

    public void setCambioVenda(float cambioVenda) {
        this.cambioVenda = cambioVenda;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public float getCambioPagamento() {
        return cambioPagamento;
    }

    public void setCambioPagamento(float cambioPagamento) {
        this.cambioPagamento = cambioPagamento;
    }

    public float getTaxa() {
        return taxa;
    }

    public void setTaxa(float taxa) {
        this.taxa = taxa;
    }

    public float getGanhoPerda() {
        return ganhoPerda;
    }

    public void setGanhoPerda(float ganhoPerda) {
        this.ganhoPerda = ganhoPerda;
    }

    public float getIof() {
        return iof;
    }

    public void setIof(float iof) {
        this.iof = iof;
    }
    
    
    
}
