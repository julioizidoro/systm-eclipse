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
 * @author wolverine
 */
@Entity
@Table(name = "produtosorcamentoindice")
public class Produtosorcamentoindice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)   
    @Column(name = "idprodutosorcamentoindice")
    private Integer idProdutosorcamentoindice;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;

    public Produtosorcamentoindice() {
    }

    public Produtosorcamentoindice(Integer idProdutosorcamentoindice) {
        this.idProdutosorcamentoindice = idProdutosorcamentoindice;
    }

    public Integer getIdProdutosorcamentoindice() {
        return idProdutosorcamentoindice;
    }

    public void setIdProdutosorcamentoindice(Integer idProdutosorcamentoindice) {
        this.idProdutosorcamentoindice = idProdutosorcamentoindice;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProdutosorcamentoindice != null ? idProdutosorcamentoindice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtosorcamentoindice)) {
            return false;
        }
        Produtosorcamentoindice other = (Produtosorcamentoindice) object;
        if ((this.idProdutosorcamentoindice == null && other.idProdutosorcamentoindice != null) || (this.idProdutosorcamentoindice != null && !this.idProdutosorcamentoindice.equals(other.idProdutosorcamentoindice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Produtosorcamentoindice[ idProdutosorcamentoindice=" + idProdutosorcamentoindice + " ]";
    }
    
}

