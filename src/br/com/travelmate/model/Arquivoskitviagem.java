package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "arquivoskitviagem")
public class Arquivoskitviagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivoskitviagem")
    private Integer idarquivoskitviagem;
    @Column(name = "completo")
    private Boolean completo;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;

    public Arquivoskitviagem() {
    }

    public Arquivoskitviagem(Integer idarquivoskitviagem) {
        this.idarquivoskitviagem = idarquivoskitviagem;
    }

    public Integer getIdarquivoskitviagem() {
        return idarquivoskitviagem;
    }

    public void setIdarquivoskitviagem(Integer idarquivoskitviagem) {
        this.idarquivoskitviagem = idarquivoskitviagem;
    }

    public Boolean getCompleto() {
        return completo;
    }

    public void setCompleto(Boolean completo) {
        this.completo = completo;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarquivoskitviagem != null ? idarquivoskitviagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Arquivoskitviagem)) {
            return false;
        }
        Arquivoskitviagem other = (Arquivoskitviagem) object;
        if ((this.idarquivoskitviagem == null && other.idarquivoskitviagem != null) || (this.idarquivoskitviagem != null && !this.idarquivoskitviagem.equals(other.idarquivoskitviagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Arquivoskitviagem[ idarquivoskitviagem=" + idarquivoskitviagem + " ]";
    }
    
}

