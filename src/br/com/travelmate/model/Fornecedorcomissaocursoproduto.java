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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "fornecedorcomissaocursoproduto")
@NamedQueries({
    @NamedQuery(name = "Fornecedorcomissaocursoproduto.findAll", query = "SELECT f FROM Fornecedorcomissaocursoproduto f")})
public class Fornecedorcomissaocursoproduto implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "premium")
    private Double premium;
    @Column(name = "express")
    private Double express;
    @Column(name = "matriz")
    private Double matriz;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcomissaocursoproduto")
    private Integer idfornecedorcomissaocursoproduto;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @JoinColumn(name = "fornecedorcomissaocurso_idfornecedorcomissao", referencedColumnName = "idfornecedorcomissao")
    @ManyToOne(optional = false)
    private Fornecedorcomissaocurso fornecedorcomissaocurso;

    public Fornecedorcomissaocursoproduto() {
    }

    public Fornecedorcomissaocursoproduto(Integer idfornecedorcomissaocursoproduto) {
        this.idfornecedorcomissaocursoproduto = idfornecedorcomissaocursoproduto;
    }

    public Integer getIdfornecedorcomissaocursoproduto() {
        return idfornecedorcomissaocursoproduto;
    }

    public void setIdfornecedorcomissaocursoproduto(Integer idfornecedorcomissaocursoproduto) {
        this.idfornecedorcomissaocursoproduto = idfornecedorcomissaocursoproduto;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    public Fornecedorcomissaocurso getFornecedorcomissaocurso() {
        return fornecedorcomissaocurso;
    }

    public void setFornecedorcomissaocurso(Fornecedorcomissaocurso fornecedorcomissaocurso) {
        this.fornecedorcomissaocurso = fornecedorcomissaocurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcomissaocursoproduto != null ? idfornecedorcomissaocursoproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorcomissaocursoproduto)) {
            return false;
        }
        Fornecedorcomissaocursoproduto other = (Fornecedorcomissaocursoproduto) object;
        if ((this.idfornecedorcomissaocursoproduto == null && other.idfornecedorcomissaocursoproduto != null) || (this.idfornecedorcomissaocursoproduto != null && !this.idfornecedorcomissaocursoproduto.equals(other.idfornecedorcomissaocursoproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcomissaocursoproduto[ idfornecedorcomissaocursoproduto=" + idfornecedorcomissaocursoproduto + " ]";
    }

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public Double getExpress() {
		return express;
	}

	public void setExpress(Double express) {
		this.express = express;
	}

	public Double getMatriz() {
		return matriz;
	}

	public void setMatriz(Double matriz) {
		this.matriz = matriz;
	}

    
    
}
