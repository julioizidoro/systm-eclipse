/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.formaPagamento;

/**
 *
 * @author Wolverine
 */
public class FormaPagamentoBean {
    
    private String titulo;
    private double percentualEtnrada;
    private float valorentrada;
    private int numeroParcelas;
    private double percentualSaldo;
    private float valorSaldo;
    private float valorParcela;
    private String obs1;
    private String obs2;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPercentualEtnrada() {
        return percentualEtnrada;
    }

    public void setPercentualEtnrada(double percentualEtnrada) {
        this.percentualEtnrada = percentualEtnrada;
    }

    public float getValorentrada() {
        return valorentrada;
    }

    public void setValorentrada(float valorentrada) {
        this.valorentrada = valorentrada;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public double getPercentualSaldo() {
        return percentualSaldo;
    }

    public void setPercentualSaldo(double percentualSaldo) {
        this.percentualSaldo = percentualSaldo;
    }

    public float getValorSaldo() {
        return valorSaldo;
    }

    public void setValorSaldo(float valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    public float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getObs1() {
        return obs1;
    }

    public void setObs1(String obs1) {
        this.obs1 = obs1;
    }

    public String getObs2() {
        return obs2;
    }

    public void setObs2(String obs2) {
        this.obs2 = obs2;
    }
    
    
    
    
}
