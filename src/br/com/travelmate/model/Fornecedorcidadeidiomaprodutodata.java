package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "fornecedorcidadeidiomaprodutodata")

public class Fornecedorcidadeidiomaprodutodata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidadeidiomaprodutodata")
    private Integer idfornecedorcidadeidiomaprodutodata;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Column(name = "numerosemanas") 
    private int numerosemanas;
    @JoinColumn(name = "fornecedorcidadeidiomaproduto_idfornecedorcidadeidiomaproduto", referencedColumnName = "idfornecedorcidadeidiomaproduto")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;

    public Fornecedorcidadeidiomaprodutodata() {
    }

    public Fornecedorcidadeidiomaprodutodata(Integer idfornecedorcidadeidiomaprodutodata) {
        this.idfornecedorcidadeidiomaprodutodata = idfornecedorcidadeidiomaprodutodata;
    }

    public Integer getIdfornecedorcidadeidiomaprodutodata() {
        return idfornecedorcidadeidiomaprodutodata;
    }

    public void setIdfornecedorcidadeidiomaprodutodata(Integer idfornecedorcidadeidiomaprodutodata) {
        this.idfornecedorcidadeidiomaprodutodata = idfornecedorcidadeidiomaprodutodata;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public Fornecedorcidadeidiomaproduto getFornecedorcidadeidiomaproduto() {
        return fornecedorcidadeidiomaproduto;
    }

    public void setFornecedorcidadeidiomaproduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
        this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
    }

    public int getNumerosemanas() {
		return numerosemanas;
	}

	public void setNumerosemanas(int numerosemanas) {
		this.numerosemanas = numerosemanas;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidadeidiomaprodutodata != null ? idfornecedorcidadeidiomaprodutodata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedorcidadeidiomaprodutodata)) {
            return false;
        }
        Fornecedorcidadeidiomaprodutodata other = (Fornecedorcidadeidiomaprodutodata) object;
        if ((this.idfornecedorcidadeidiomaprodutodata == null && other.idfornecedorcidadeidiomaprodutodata != null) || (this.idfornecedorcidadeidiomaprodutodata != null && !this.idfornecedorcidadeidiomaprodutodata.equals(other.idfornecedorcidadeidiomaprodutodata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcidadeidiomaprodutodata[ idfornecedorcidadeidiomaprodutodata=" + idfornecedorcidadeidiomaprodutodata + " ]";
    }
    
}

