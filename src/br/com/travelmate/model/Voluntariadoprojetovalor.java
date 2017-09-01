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
@Table(name = "voluntariadoprojetovalor")

public class Voluntariadoprojetovalor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvoluntariadoprojetovalor")
    private Integer idvoluntariadoprojetovalor;
    @Column(name = "datainicial")
    @Temporal(TemporalType.DATE)
    private Date datainicial;
    @Column(name = "datafinal")
    @Temporal(TemporalType.DATE)
    private Date datafinal;
    @Column(name = "numerosemanainicial")
    private Integer numerosemanainicial;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Column(name = "valorsemanaadicional")
    private Float valorsemanaadicional;
    @JoinColumn(name = "voluntariadoprojeto_idvoluntariadoprojeto", referencedColumnName = "idvoluntariadoprojeto")
    @ManyToOne(optional = false)
    private Voluntariadoprojeto voluntariadoprojeto;

    public Voluntariadoprojetovalor() {
    }

    public Voluntariadoprojetovalor(Integer idvoluntariadoprojetovalor) {
        this.idvoluntariadoprojetovalor = idvoluntariadoprojetovalor;
    }

    public Integer getIdvoluntariadoprojetovalor() {
        return idvoluntariadoprojetovalor;
    }

    public void setIdvoluntariadoprojetovalor(Integer idvoluntariadoprojetovalor) {
        this.idvoluntariadoprojetovalor = idvoluntariadoprojetovalor;
    }
 
    public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public Date getDatafinal() {
		return datafinal;
	}

	public void setDatafinal(Date datafinal) {
		this.datafinal = datafinal;
	}

	public Integer getNumerosemanainicial() {
		return numerosemanainicial;
	}

	public void setNumerosemanainicial(Integer numerosemanainicial) {
		this.numerosemanainicial = numerosemanainicial;
	}

	public Integer getNumerosemanafinal() {
		return numerosemanafinal;
	}

	public void setNumerosemanafinal(Integer numerosemanafinal) {
		this.numerosemanafinal = numerosemanafinal;
	}

	public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getValorsemanaadicional() {
        return valorsemanaadicional;
    }

    public void setValorsemanaadicional(Float valorsemanaadicional) {
        this.valorsemanaadicional = valorsemanaadicional;
    }

    public Voluntariadoprojeto getVoluntariadoprojeto() {
        return voluntariadoprojeto;
    }

    public void setVoluntariadoprojeto(Voluntariadoprojeto voluntariadoprojeto) {
        this.voluntariadoprojeto = voluntariadoprojeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoluntariadoprojetovalor != null ? idvoluntariadoprojetovalor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Voluntariadoprojetovalor)) {
            return false;
        }
        Voluntariadoprojetovalor other = (Voluntariadoprojetovalor) object;
        if ((this.idvoluntariadoprojetovalor == null && other.idvoluntariadoprojetovalor != null) || (this.idvoluntariadoprojetovalor != null && !this.idvoluntariadoprojetovalor.equals(other.idvoluntariadoprojetovalor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Voluntariadoprojetovalor[ idvoluntariadoprojetovalor=" + idvoluntariadoprojetovalor + " ]";
    }
    
}

