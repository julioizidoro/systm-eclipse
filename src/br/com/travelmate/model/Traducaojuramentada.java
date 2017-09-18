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
 * @author wolverine
 */
@Entity
@Table(name = "traducaojuramentada")
public class Traducaojuramentada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtraducaojuramentada")
    private Integer idtraducaojuramentada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valortraducao")
    private Float valortraducao;
    @Column(name = "assessoriatm")
    private Float assessoriatm;
    @Column(name = "comissaofranquia")
    private Float comissaofranquia;
    @Column(name = "valortotal")
    private Float valortotal;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;

    public Traducaojuramentada() {
    }

    public Traducaojuramentada(Integer idtraducaojuramentada) {
        this.idtraducaojuramentada = idtraducaojuramentada;
    }

    public Integer getIdtraducaojuramentada() {
        return idtraducaojuramentada;
    }

    public void setIdtraducaojuramentada(Integer idtraducaojuramentada) {
        this.idtraducaojuramentada = idtraducaojuramentada;
    }

    public Float getValortraducao() {
        return valortraducao;
    }

    public void setValortraducao(Float valortraducao) {
        this.valortraducao = valortraducao;
    }

    public Float getAssessoriatm() {
        return assessoriatm;
    }

    public void setAssessoriatm(Float assessoriatm) {
        this.assessoriatm = assessoriatm;
    }

    public Float getComissaofranquia() {
        return comissaofranquia;
    }

    public void setComissaofranquia(Float comissaofranquia) {
        this.comissaofranquia = comissaofranquia;
    }

    public Float getValortotal() {
        return valortotal;
    }

    public void setValortotal(Float valortotal) {
        this.valortotal = valortotal;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtraducaojuramentada != null ? idtraducaojuramentada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traducaojuramentada)) {
            return false;
        }
        Traducaojuramentada other = (Traducaojuramentada) object;
        if ((this.idtraducaojuramentada == null && other.idtraducaojuramentada != null) || (this.idtraducaojuramentada != null && !this.idtraducaojuramentada.equals(other.idtraducaojuramentada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Traducaojuramentada[ idtraducaojuramentada=" + idtraducaojuramentada + " ]";
    }
    
}

