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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "terceiros")
public class Terceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idterceiros")
    private Integer idterceiros;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;

    public Terceiros() {
    }

    public Terceiros(Integer idterceiros) {
        this.idterceiros = idterceiros;
    }

    public Integer getIdterceiros() {
        return idterceiros;
    }

    public void setIdterceiros(Integer idterceiros) {
        this.idterceiros = idterceiros;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idterceiros != null ? idterceiros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Terceiros)) {
            return false;
        }
        Terceiros other = (Terceiros) object;
        if ((this.idterceiros == null && other.idterceiros != null) || (this.idterceiros != null && !this.idterceiros.equals(other.idterceiros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Terceiros[ idterceiros=" + idterceiros + " ]";
    }
    
}
