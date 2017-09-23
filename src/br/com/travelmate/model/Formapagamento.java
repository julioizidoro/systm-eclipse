/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "formapagamento")
public class Formapagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idformaPagamento")
    private Integer idformaPagamento;
    @Size(max = 10)
    @Column(name = "condicao")
    private String condicao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorOrcamento")
    private Float valorOrcamento;
    @Size(max = 3)
    @Column(name = "possuiJuros")
    private String possuiJuros;
    @Column(name = "valorJuros")
    private Float valorJuros;
    @Column(name = "valorTotal")
    private Float valorTotal;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "valordesconto")
    private Float valordesconto;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "formapagamento")
    private List<Parcelamentopagamento> parcelamentopagamentoList;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;
    

    public Formapagamento() {
    }

    public Formapagamento(Integer idformaPagamento) {
        this.idformaPagamento = idformaPagamento;
    }

    public Integer getIdformaPagamento() {
        return idformaPagamento;
    }

    public void setIdformaPagamento(Integer idformaPagamento) {
        this.idformaPagamento = idformaPagamento;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public Float getValorOrcamento() {
        return valorOrcamento;
    }

    public void setValorOrcamento(Float valorOrcamento) {
        this.valorOrcamento = valorOrcamento;
    }

    public String getPossuiJuros() {
        return possuiJuros;
    }

    public void setPossuiJuros(String possuiJuros) {
        this.possuiJuros = possuiJuros;
    }

    public Float getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(Float valorJuros) {
        this.valorJuros = valorJuros;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Float getValordesconto() {
        return valordesconto;
    }

    public void setValordesconto(Float valordesconto) {
        this.valordesconto = valordesconto;
    }

    public List<Parcelamentopagamento> getParcelamentopagamentoList() {
        return parcelamentopagamentoList;
    }

    public void setParcelamentopagamentoList(List<Parcelamentopagamento> parcelamentopagamentoList) {
        this.parcelamentopagamentoList = parcelamentopagamentoList;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idformaPagamento != null ? idformaPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Formapagamento)) {
            return false;
        }
        Formapagamento other = (Formapagamento) object;
        if ((this.idformaPagamento == null && other.idformaPagamento != null) || (this.idformaPagamento != null && !this.idformaPagamento.equals(other.idformaPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Formapagamento[ idformaPagamento=" + idformaPagamento + " ]";
    }
    
}
