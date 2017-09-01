/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.relatorios;

import java.util.Date;

/**
 *
 * @author Wolverine
 */
public class ConciliacaoBean {
    
    private Date dataCompensacao;
    private String planoContas;
    private String descricao;
    private Float valorEntrada;
    private Float valorSaida;
    private Float saldo;

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
    }

    public String getPlanoContas() {
        return planoContas;
    }

    public void setPlanoContas(String planoContas) {
        this.planoContas = planoContas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Float valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Float getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(Float valorSaida) {
        this.valorSaida = valorSaida;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }
    
    
    
}
