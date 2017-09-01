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
 * @author wolverine
 */
@Entity
@Table(name = "fornecedorcidadedocs")
public class Fornecedorcidadedocs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidadedocs")
    private Integer idfornecedorcidadedocs;
    @JoinColumn(name = "fornecedordocs_idfornecedordocs", referencedColumnName = "idfornecedordocs")
    @ManyToOne(optional = false)
    private Fornecedordocs fornecedordocs;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @Transient
    private boolean selecionado;

    public Fornecedorcidadedocs() {
    }

    public Fornecedorcidadedocs(Integer idfornecedorcidadedocs) {
        this.idfornecedorcidadedocs = idfornecedorcidadedocs;
    }

    public Integer getIdfornecedorcidadedocs() {
        return idfornecedorcidadedocs;
    }

    public void setIdfornecedorcidadedocs(Integer idfornecedorcidadedocs) {
        this.idfornecedorcidadedocs = idfornecedorcidadedocs;
    }

    public Fornecedordocs getFornecedordocs() {
        return fornecedordocs;
    }

    public void setFornecedordocs(Fornecedordocs fornecedordocs) {
        this.fornecedordocs = fornecedordocs;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidadedocs != null ? idfornecedorcidadedocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorcidadedocs)) {
            return false;
        }
        Fornecedorcidadedocs other = (Fornecedorcidadedocs) object;
        if ((this.idfornecedorcidadedocs == null && other.idfornecedorcidadedocs != null) || (this.idfornecedorcidadedocs != null && !this.idfornecedorcidadedocs.equals(other.idfornecedorcidadedocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcidadedocs[ idfornecedorcidadedocs=" + idfornecedorcidadedocs + " ]";
    }
    
}

