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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "condicaocancelamento")
public class Condicaocancelamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcondicaocancelamento")
    private Integer idcondicaocancelamento;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;

    public Condicaocancelamento() {
    }

    public Condicaocancelamento(Integer idcondicaocancelamento) {
        this.idcondicaocancelamento = idcondicaocancelamento;
    }

    public Integer getIdcondicaocancelamento() {
        return idcondicaocancelamento;
    }

    public void setIdcondicaocancelamento(Integer idcondicaocancelamento) {
        this.idcondicaocancelamento = idcondicaocancelamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash += (idcondicaocancelamento != null ? idcondicaocancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Condicaocancelamento)) {
            return false;
        }
        Condicaocancelamento other = (Condicaocancelamento) object;
        if ((this.idcondicaocancelamento == null && other.idcondicaocancelamento != null) || (this.idcondicaocancelamento != null && !this.idcondicaocancelamento.equals(other.idcondicaocancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Condicaocancelamento[ idcondicaocancelamento=" + idcondicaocancelamento + " ]";
    }
    
}

