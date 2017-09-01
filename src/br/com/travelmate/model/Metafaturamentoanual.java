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
@Table(name = "metafaturamentoanual")
public class Metafaturamentoanual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmetafaturamentoanual")
    private Integer idmetafaturamentoanual;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valormeta")
    private Float valormeta;
    @Column(name = "metaalcancada")
    private Float metaalcancada;
    @Column(name = "percentualalcancado")
    private Float percentualalcancado;
    @JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;

    public Metafaturamentoanual() {
    }

    public Metafaturamentoanual(Integer idmetafaturamentoanual) {
        this.idmetafaturamentoanual = idmetafaturamentoanual;
    }

    public Integer getIdmetafaturamentoanual() {
        return idmetafaturamentoanual;
    }

    public void setIdmetafaturamentoanual(Integer idmetafaturamentoanual) {
        this.idmetafaturamentoanual = idmetafaturamentoanual;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Float getValormeta() {
        return valormeta;
    }

    public void setValormeta(Float valormeta) {
        this.valormeta = valormeta;
    }

    public Float getMetaalcancada() {
        return metaalcancada;
    }

    public void setMetaalcancada(Float metaalcancada) {
        this.metaalcancada = metaalcancada;
    }

    public Float getPercentualalcancado() {
        return percentualalcancado;
    }

    public void setPercentualalcancado(Float percentualalcancado) {
        this.percentualalcancado = percentualalcancado;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmetafaturamentoanual != null ? idmetafaturamentoanual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Metafaturamentoanual)) {
            return false;
        }
        Metafaturamentoanual other = (Metafaturamentoanual) object;
        if ((this.idmetafaturamentoanual == null && other.idmetafaturamentoanual != null) || (this.idmetafaturamentoanual != null && !this.idmetafaturamentoanual.equals(other.idmetafaturamentoanual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Metafaturamentoanual[ idmetafaturamentoanual=" + idmetafaturamentoanual + " ]";
    }
    
}

