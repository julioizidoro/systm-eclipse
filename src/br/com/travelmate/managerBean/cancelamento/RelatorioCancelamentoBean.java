/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.cancelamento;

/**
 *
 * @author Wolverine
 */
public class RelatorioCancelamentoBean {
    
    private String idVenda;
    private String cliente;
    private String produto;
    private String multaCliente;
    private String multaEscola;
    private String saldoCancelamento;
    private String escola;

    public String getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(String idVenda) {
        this.idVenda = idVenda;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getMultaCliente() {
        return multaCliente;
    }

    public void setMultaCliente(String multaCliente) {
        this.multaCliente = multaCliente;
    }

    public String getMultaEscola() {
        return multaEscola;
    }

    public void setMultaEscola(String multaEscola) {
        this.multaEscola = multaEscola;
    }

    public String getSaldoCancelamento() {
        return saldoCancelamento;
    }

    public void setSaldoCancelamento(String saldoCancelamento) {
        this.saldoCancelamento = saldoCancelamento;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }
    
    
    
}
