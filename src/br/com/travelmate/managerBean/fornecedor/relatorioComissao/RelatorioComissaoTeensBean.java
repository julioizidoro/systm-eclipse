/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.fornecedor.relatorioComissao;

/**
 *
 * @author wolverine
 */
public class RelatorioComissaoTeensBean {
    
    private String pais;
    private String cidade;
    private String programa;
    private String  moeda;
    private Float comissaoExpress;
    private Float comissaoPremium;

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

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public Float getComissaoExpress() {
        return comissaoExpress;
    }

    public void setComissaoExpress(Float comissaoExpress) {
        this.comissaoExpress = comissaoExpress;
    }

    public Float getComissaoPremium() {
        return comissaoPremium;
    }

    public void setComissaoPremium(Float comissaoPremium) {
        this.comissaoPremium = comissaoPremium;
    }
    
    
    
}
