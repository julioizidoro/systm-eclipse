/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila Rodrigues
 */
@Entity
@Table(name = "fornecedorinformacao")
@NamedQueries({
    @NamedQuery(name = "Fornecedorinformacao.findAll", query = "SELECT f FROM Fornecedorinformacao f")})
public class Fornecedorinformacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorinformacao")
    private Integer idfornecedorinformacao;
    @Size(max = 15)
    @Column(name = "latitude")
    private String latitude;
    @Size(max = 15)
    @Column(name = "longitude")
    private String longitude;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;

    public Fornecedorinformacao() {
    }

    public Fornecedorinformacao(Integer idfornecedorinformacao) {
        this.idfornecedorinformacao = idfornecedorinformacao;
    }

    public Integer getIdfornecedorinformacao() {
        return idfornecedorinformacao;
    }

    public void setIdfornecedorinformacao(Integer idfornecedorinformacao) {
        this.idfornecedorinformacao = idfornecedorinformacao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorinformacao != null ? idfornecedorinformacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorinformacao)) {
            return false;
        }
        Fornecedorinformacao other = (Fornecedorinformacao) object;
        if ((this.idfornecedorinformacao == null && other.idfornecedorinformacao != null) || (this.idfornecedorinformacao != null && !this.idfornecedorinformacao.equals(other.idfornecedorinformacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorinformacao[ idfornecedorinformacao=" + idfornecedorinformacao + " ]";
    }
    
}
