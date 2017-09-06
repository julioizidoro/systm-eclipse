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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "produtosorcamentogrupo")
public class Produtosorcamentogrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutosorcamentogrupo")
    private Integer idprodutosorcamentogrupo;
    @JoinColumn(name = "produtosorcamentoindice_idprodutosorcamentoindice", referencedColumnName = "idprodutosorcamentoindice")
    @ManyToOne(optional = false)
    private Produtosorcamentoindice produtosorcamentoindice;
    @JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @OneToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Produtosorcamentogrupo() {
    }

    public Produtosorcamentogrupo(Integer idprodutosorcamentogrupo) {
        this.idprodutosorcamentogrupo = idprodutosorcamentogrupo;
    }

    public Integer getIdprodutosorcamentogrupo() {
        return idprodutosorcamentogrupo;
    }

    public void setIdprodutosorcamentogrupo(Integer idprodutosorcamentogrupo) {
        this.idprodutosorcamentogrupo = idprodutosorcamentogrupo;
    }

    public Produtosorcamentoindice getProdutosorcamentoindice() {
        return produtosorcamentoindice;
    }

    public void setProdutosorcamentoindice(Produtosorcamentoindice produtosorcamentoindice) {
        this.produtosorcamentoindice = produtosorcamentoindice;
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
        hash += (idprodutosorcamentogrupo != null ? idprodutosorcamentogrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtosorcamentogrupo)) {
            return false;
        }
        Produtosorcamentogrupo other = (Produtosorcamentogrupo) object;
        if ((this.idprodutosorcamentogrupo == null && other.idprodutosorcamentogrupo != null) || (this.idprodutosorcamentogrupo != null && !this.idprodutosorcamentogrupo.equals(other.idprodutosorcamentogrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Produtosorcamentogrupo[ idprodutosorcamentogrupo=" + idprodutosorcamentogrupo + " ]";
    }
    
}
