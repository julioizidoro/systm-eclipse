package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "parametrosfinanceiro")
public class Parametrosfinanceiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idparametrosfinanceiro")
    private Integer idparametrosfinanceiro;
    @Column(name = "datacobranca")
    @Temporal(TemporalType.DATE)
    private Date datacobranca;

    public Parametrosfinanceiro() {
    }

    public Parametrosfinanceiro(Integer idparametrosfinanceiro) {
        this.idparametrosfinanceiro = idparametrosfinanceiro;
    }

    public Integer getIdparametrosfinanceiro() {
        return idparametrosfinanceiro;
    }

    public void setIdparametrosfinanceiro(Integer idparametrosfinanceiro) {
        this.idparametrosfinanceiro = idparametrosfinanceiro;
    }

    public Date getDatacobranca() {
        return datacobranca;
    }

    public void setDatacobranca(Date datacobranca) {
        this.datacobranca = datacobranca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametrosfinanceiro != null ? idparametrosfinanceiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Parametrosfinanceiro)) {
            return false;
        }
        Parametrosfinanceiro other = (Parametrosfinanceiro) object;
        if ((this.idparametrosfinanceiro == null && other.idparametrosfinanceiro != null) || (this.idparametrosfinanceiro != null && !this.idparametrosfinanceiro.equals(other.idparametrosfinanceiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Parametrosfinanceiro[ idparametrosfinanceiro=" + idparametrosfinanceiro + " ]";
    }
    
}

