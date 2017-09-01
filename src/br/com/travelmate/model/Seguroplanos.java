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
 * @author wolverine
 */
@Entity
@Table(name = "seguroplanos")
public class Seguroplanos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idseguroplanos")
    private Integer idseguroplanos;
    @Size(max = 50)
    @Column(name = "nome")
    private String nome;
    @Column(name = "ativo")
    private Boolean ativo;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;

    public Seguroplanos() {
    }

    public Seguroplanos(Integer idseguroplanos) {
        this.idseguroplanos = idseguroplanos;
    }

    public Integer getIdseguroplanos() {
        return idseguroplanos;
    }

    public void setIdseguroplanos(Integer idseguroplanos) {
        this.idseguroplanos = idseguroplanos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idseguroplanos != null ? idseguroplanos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seguroplanos)) {
            return false;
        }
        Seguroplanos other = (Seguroplanos) object;
        if ((this.idseguroplanos == null && other.idseguroplanos != null) || (this.idseguroplanos != null && !this.idseguroplanos.equals(other.idseguroplanos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Seguroplanos[ idseguroplanos=" + idseguroplanos + " ]";
    }
    
}

