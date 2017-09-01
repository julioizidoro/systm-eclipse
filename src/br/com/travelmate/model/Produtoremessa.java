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
import br.com.travelmate.model.Produtos;




/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "produtoremessa")
public class Produtoremessa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutoremessa")
    private Integer idprodutoremessa;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Produtoremessa() {
    }

    public Produtoremessa(Integer idprodutoremessa) {
        this.idprodutoremessa = idprodutoremessa;
    }

    public Integer getIdprodutoremessa() {
        return idprodutoremessa;
    }

    public void setIdprodutoremessa(Integer idprodutoremessa) {
        this.idprodutoremessa = idprodutoremessa;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutoremessa != null ? idprodutoremessa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtoremessa)) {
            return false;
        }
        Produtoremessa other = (Produtoremessa) object;
        if ((this.idprodutoremessa == null && other.idprodutoremessa != null) || (this.idprodutoremessa != null && !this.idprodutoremessa.equals(other.idprodutoremessa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Produtoremessa[ idprodutoremessa=" + idprodutoremessa + " ]";
    }
    
}
