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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "planoconta")
public class Planoconta implements Serializable {
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "planoconta")
    private List<Conciliacao> conciliacaoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "planoconta")
    private List<Contaspagar> contaspagarList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplanoconta")
    private Integer idplanoconta;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "planoconta")
    private List<Contasreceber> contasreceberList;

    public Planoconta() {
    }

    public Planoconta(Integer idplanoconta) {
        this.idplanoconta = idplanoconta;
    }

    public Integer getIdplanoconta() {
        return idplanoconta;
    }

    public void setIdplanoconta(Integer idplanoconta) {
        this.idplanoconta = idplanoconta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Contasreceber> getContasreceberList() {
        return contasreceberList;
    }

    public void setContasreceberList(List<Contasreceber> contasreceberList) {
        this.contasreceberList = contasreceberList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplanoconta != null ? idplanoconta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Planoconta)) {
            return false;
        }
        Planoconta other = (Planoconta) object;
        if ((this.idplanoconta == null && other.idplanoconta != null) || (this.idplanoconta != null && !this.idplanoconta.equals(other.idplanoconta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Planoconta[ idplanoconta=" + idplanoconta + " ]";
    }

    public List<Contaspagar> getContaspagarList() {
        return contaspagarList;
    }

    public void setContaspagarList(List<Contaspagar> contaspagarList) {
        this.contaspagarList = contaspagarList;
    }

    public List<Conciliacao> getConciliacaoList() {
        return conciliacaoList;
    }

    public void setConciliacaoList(List<Conciliacao> conciliacaoList) {
        this.conciliacaoList = conciliacaoList;
    }

    
}
