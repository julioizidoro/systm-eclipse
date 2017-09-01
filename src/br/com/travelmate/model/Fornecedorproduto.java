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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "fornecedorproduto")
public class Fornecedorproduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorProduto")
    private Integer idfornecedorProduto;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;

    public Fornecedorproduto() {
    }

    public Fornecedorproduto(Integer idfornecedorProduto) {
        this.idfornecedorProduto = idfornecedorProduto;
    }

    public Integer getIdfornecedorProduto() {
        return idfornecedorProduto;
    }

    public void setIdfornecedorProduto(Integer idfornecedorProduto) {
        this.idfornecedorProduto = idfornecedorProduto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorProduto != null ? idfornecedorProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorproduto)) {
            return false;
        }
        Fornecedorproduto other = (Fornecedorproduto) object;
        if ((this.idfornecedorProduto == null && other.idfornecedorProduto != null) || (this.idfornecedorProduto != null && !this.idfornecedorProduto.equals(other.idfornecedorProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorproduto[ idfornecedorProduto=" + idfornecedorProduto + " ]";
    }
    
}

