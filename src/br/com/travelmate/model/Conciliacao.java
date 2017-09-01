/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "conciliacao")
@NamedQueries({
    @NamedQuery(name = "Conciliacao.findAll", query = "SELECT c FROM Conciliacao c")})
public class Conciliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconciliacai")
    private Integer idconciliacai;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorentrada")
    private Float valorentrada;
    @Column(name = "valorsaida")
    private Float valorsaida;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;
    @JoinColumn(name = "planoconta_idplanoconta", referencedColumnName = "idplanoconta")
    @ManyToOne(optional = false)
    private Planoconta planoconta;
    @JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;

    public Conciliacao() {
    }

    public Conciliacao(Integer idconciliacai) {
        this.idconciliacai = idconciliacai;
    }

    public Integer getIdconciliacai() {
        return idconciliacai;
    }

    public void setIdconciliacai(Integer idconciliacai) {
        this.idconciliacai = idconciliacai;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValorentrada() {
        return valorentrada;
    }

    public void setValorentrada(Float valorentrada) {
        this.valorentrada = valorentrada;
    }

    public Float getValorsaida() {
        return valorsaida;
    }

    public void setValorsaida(Float valorsaida) {
        this.valorsaida = valorsaida;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconciliacai != null ? idconciliacai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Conciliacao)) {
            return false;
        }
        Conciliacao other = (Conciliacao) object;
        if ((this.idconciliacai == null && other.idconciliacai != null) || (this.idconciliacai != null && !this.idconciliacai.equals(other.idconciliacai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.Interface.Conciliacao[ idconciliacai=" + idconciliacai + " ]";
    }
    
}
