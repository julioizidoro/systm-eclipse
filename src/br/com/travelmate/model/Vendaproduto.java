package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "vendaproduto")
public class Vendaproduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idvendaproduto")
    private Integer idvendaproduto;
    @Size(max = 7)
    @Column(name = "mesano")
    private String mesano;
    @Column(name = "intercambio")
    private Integer intercambio;
    @Column(name = "turismo")
    private Integer turismo;
    @Column(name = "produto")
    private Integer produto;
    @JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;

    public Vendaproduto() {
    }

    public Vendaproduto(Integer idvendaproduto) {
        this.idvendaproduto = idvendaproduto;
    }

    public Integer getIdvendaproduto() {
        return idvendaproduto;
    }

    public void setIdvendaproduto(Integer idvendaproduto) {
        this.idvendaproduto = idvendaproduto;
    }

    public String getMesano() {
        return mesano;
    }

    public void setMesano(String mesano) {
        this.mesano = mesano;
    }

    public Integer getIntercambio() {
        return intercambio;
    }

    public void setIntercambio(Integer intercambio) {
        this.intercambio = intercambio;
    }

    public Integer getTurismo() {
        return turismo;
    }

    public void setTurismo(Integer turismo) {
        this.turismo = turismo;
    }

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
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
        hash += (idvendaproduto != null ? idvendaproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vendaproduto)) {
            return false;
        }
        Vendaproduto other = (Vendaproduto) object;
        if ((this.idvendaproduto == null && other.idvendaproduto != null) || (this.idvendaproduto != null && !this.idvendaproduto.equals(other.idvendaproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Vendaproduto[ idvendaproduto=" + idvendaproduto + " ]";
    }
    
}

