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
@Table(name = "faturafaturaajuste")
public class Faturafaturaajuste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFaturafaturaajuste")
    private Integer idFaturafaturaajuste;
    @JoinColumn(name = "fatura_idfatura", referencedColumnName = "idfatura")
    @ManyToOne(optional = false)
    private Fatura fatura;
    @JoinColumn(name = "Faturaajustes_idFaturaajustes", referencedColumnName = "idFaturaajustes")
    @ManyToOne(optional = false)
    private Faturaajustes faturaajustes;

    public Faturafaturaajuste() {
    }

    public Faturafaturaajuste(Integer idFaturafaturaajuste) {
        this.idFaturafaturaajuste = idFaturafaturaajuste;
    }

    public Integer getIdFaturafaturaajuste() {
        return idFaturafaturaajuste;
    }

    public void setIdFaturafaturaajuste(Integer idFaturafaturaajuste) {
        this.idFaturafaturaajuste = idFaturafaturaajuste;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public Faturaajustes getFaturaajustes() {
        return faturaajustes;
    }

    public void setFaturaajustes(Faturaajustes faturaajustes) {
        this.faturaajustes = faturaajustes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFaturafaturaajuste != null ? idFaturafaturaajuste.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faturafaturaajuste)) {
            return false;
        }
        Faturafaturaajuste other = (Faturafaturaajuste) object;
        if ((this.idFaturafaturaajuste == null && other.idFaturafaturaajuste != null) || (this.idFaturafaturaajuste != null && !this.idFaturafaturaajuste.equals(other.idFaturafaturaajuste))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Faturafaturaajuste[ idFaturafaturaajuste=" + idFaturafaturaajuste + " ]";
    }
    
}

