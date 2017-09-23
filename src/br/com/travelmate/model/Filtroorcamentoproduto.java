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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "filtroorcamentoproduto")
public class Filtroorcamentoproduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfiltroOrcamentoProduto")
    private Integer idfiltroOrcamentoProduto;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @Column(name = "listar")
    private String listar;
    @Column(name = "hefichafinal")
    private boolean hefichafinal;
    @Transient
    private boolean selecionado;

    public Filtroorcamentoproduto() {
    }

    public Filtroorcamentoproduto(Integer idfiltroOrcamentoProduto) {
        this.idfiltroOrcamentoProduto = idfiltroOrcamentoProduto;
    }

    public Integer getIdfiltroOrcamentoProduto() {
        return idfiltroOrcamentoProduto;
    }

    public void setIdfiltroOrcamentoProduto(Integer idfiltroOrcamentoProduto) {
        this.idfiltroOrcamentoProduto = idfiltroOrcamentoProduto;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }
    
    

    public String getListar() {
		return listar;
	}

	public void setListar(String listar) {
		this.listar = listar;
	}

	public boolean isHefichafinal() {
		return hefichafinal;
	}

	public void setHefichafinal(boolean hefichafinal) {
		this.hefichafinal = hefichafinal;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfiltroOrcamentoProduto != null ? idfiltroOrcamentoProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Filtroorcamentoproduto)) {
            return false;
        }
        Filtroorcamentoproduto other = (Filtroorcamentoproduto) object;
        if ((this.idfiltroOrcamentoProduto == null && other.idfiltroOrcamentoProduto != null) || (this.idfiltroOrcamentoProduto != null && !this.idfiltroOrcamentoProduto.equals(other.idfiltroOrcamentoProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Filtroorcamentoproduto[ idfiltroOrcamentoProduto=" + idfiltroOrcamentoProduto + " ]";
    }
    
}
