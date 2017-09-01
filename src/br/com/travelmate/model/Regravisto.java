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
@Table(name = "regravisto")
public class Regravisto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idregravisto")
    private Integer idregravisto;
    @Size(max = 50)
    @Column(name = "tipovisto")
    private String tipovisto;
    @Column(name = "numerodias")
    private Integer numerodias;
    @JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
    @ManyToOne(optional = false)
    private Pais pais;

    public Regravisto() {
    }

    public Regravisto(Integer idregravisto) {
        this.idregravisto = idregravisto;
    }

    public Integer getIdregravisto() {
        return idregravisto;
    }

    public void setIdregravisto(Integer idregravisto) {
        this.idregravisto = idregravisto;
    }

    public String getTipovisto() {
        return tipovisto;
    }

    public void setTipovisto(String tipovisto) {
        this.tipovisto = tipovisto;
    }

    public Integer getNumerodias() {
        return numerodias;
    }

    public void setNumerodias(Integer numerodias) {
        this.numerodias = numerodias;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregravisto != null ? idregravisto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Regravisto)) {
            return false;
        }
        Regravisto other = (Regravisto) object;
        if ((this.idregravisto == null && other.idregravisto != null) || (this.idregravisto != null && !this.idregravisto.equals(other.idregravisto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Regravisto[ idregravisto=" + idregravisto + " ]";
    }
    
}

