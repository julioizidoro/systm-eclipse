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
@Table(name = "metasfaturamentomensal")
public class Metasfaturamentomensal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmetasfaturamentoemensal")
    private Integer idmetasfaturamentoemensal;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valormeta")
    private Float valormeta;
    @Column(name = "valoralcancado")
    private Float valoralcancado;
    @Column(name = "percentualalcancado")
    private Float percentualalcancado;
    @Column(name = "valormetasemana")
    private Float valormetasemana;
    @JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;

    public Metasfaturamentomensal() {
    }

    public Metasfaturamentomensal(Integer idmetasfaturamentoemensal) {
        this.idmetasfaturamentoemensal = idmetasfaturamentoemensal;
    }

    public Integer getIdmetasfaturamentoemensal() {
        return idmetasfaturamentoemensal;
    }

    public void setIdmetasfaturamentoemensal(Integer idmetasfaturamentoemensal) {
        this.idmetasfaturamentoemensal = idmetasfaturamentoemensal;
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

    public Float getValoralcancado() {
        return valoralcancado;
    }

    public void setValoralcancado(Float valoralcancado) {
        this.valoralcancado = valoralcancado;
    }

    public Float getPercentualalcancado() {
        return percentualalcancado;
    }

    public void setPercentualalcancado(Float percentualalcancado) {
        this.percentualalcancado = percentualalcancado;
    }

    public Float getValormetasemana() {
        return valormetasemana;
    }

    public void setValormetasemana(Float valormetasemana) {
        this.valormetasemana = valormetasemana;
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
        hash += (idmetasfaturamentoemensal != null ? idmetasfaturamentoemensal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Metasfaturamentomensal)) {
            return false;
        }
        Metasfaturamentomensal other = (Metasfaturamentomensal) object;
        if ((this.idmetasfaturamentoemensal == null && other.idmetasfaturamentoemensal != null) || (this.idmetasfaturamentoemensal != null && !this.idmetasfaturamentoemensal.equals(other.idmetasfaturamentoemensal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Metasfaturamentomensal[ idmetasfaturamentoemensal=" + idmetasfaturamentoemensal + " ]";
    }
    
}

