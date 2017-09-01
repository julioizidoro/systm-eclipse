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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "copromocao")
public class Copromocao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcopromocao")
    private Integer idcopromocao;
    @Column(name = "datavalidade")
    @Temporal(TemporalType.DATE)
    private Date datavalidade;
    @Column(name = "numerosemanainicial")
    private Integer numerosemanainicial;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "promocao")
    private String promocao;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;

    public Copromocao() {
    }

    public Copromocao(Integer idcopromocao) {
        this.idcopromocao = idcopromocao;
    }

    public Integer getIdcopromocao() {
        return idcopromocao;
    }

    public void setIdcopromocao(Integer idcopromocao) {
        this.idcopromocao = idcopromocao;
    }

    public Date getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(Date datavalidade) {
        this.datavalidade = datavalidade;
    }

    public Integer getNumerosemanainicial() {
        return numerosemanainicial;
    }

    public void setNumerosemanainicial(Integer numerosemanainicial) {
        this.numerosemanainicial = numerosemanainicial;
    }

    public Integer getNumerosemanafinal() {
        return numerosemanafinal;
    }

    public void setNumerosemanafinal(Integer numerosemanafinal) {
        this.numerosemanafinal = numerosemanafinal;
    }

    public String getPromocao() {
        return promocao;
    }

    public void setPromocao(String promocao) {
        this.promocao = promocao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        hash += (idcopromocao != null ? idcopromocao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Copromocao)) {
            return false;
        }
        Copromocao other = (Copromocao) object;
        if ((this.idcopromocao == null && other.idcopromocao != null) || (this.idcopromocao != null && !this.idcopromocao.equals(other.idcopromocao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Copromocao[ idcopromocao=" + idcopromocao + " ]";
    }
    
}
