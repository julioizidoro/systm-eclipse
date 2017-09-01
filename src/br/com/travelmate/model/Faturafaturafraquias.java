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
@Table(name = "faturafaturafraquias")
public class Faturafaturafraquias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfaturafaturafraquias")
    private Integer idfaturafaturafraquias;
    @JoinColumn(name = "fatura_idfatura", referencedColumnName = "idfatura")
    @ManyToOne(optional = false)
    private Fatura fatura;
    @JoinColumn(name = "faturafranquias_idfaturafranquias", referencedColumnName = "idfaturafranquias")
    @ManyToOne(optional = false)
    private Faturafranquias faturafranquias;

    public Faturafaturafraquias() {
    }

    public Faturafaturafraquias(Integer idfaturafaturafraquias) {
        this.idfaturafaturafraquias = idfaturafaturafraquias;
    }

    public Integer getIdfaturafaturafraquias() {
        return idfaturafaturafraquias;
    }

    public void setIdfaturafaturafraquias(Integer idfaturafaturafraquias) {
        this.idfaturafaturafraquias = idfaturafaturafraquias;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public Faturafranquias getFaturafranquias() {
        return faturafranquias;
    }

    public void setFaturafranquias(Faturafranquias faturafranquias) {
        this.faturafranquias = faturafranquias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfaturafaturafraquias != null ? idfaturafaturafraquias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faturafaturafraquias)) {
            return false;
        }
        Faturafaturafraquias other = (Faturafaturafraquias) object;
        if ((this.idfaturafaturafraquias == null && other.idfaturafaturafraquias != null) || (this.idfaturafaturafraquias != null && !this.idfaturafaturafraquias.equals(other.idfaturafaturafraquias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Faturafaturafraquias[ idfaturafaturafraquias=" + idfaturafaturafraquias + " ]";
    }
    
}

