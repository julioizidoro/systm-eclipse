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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "fornecedorarquivo")
public class Fornecedorarquivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorarquivo")
    private Integer idfornecedorarquivo;
    @Size(max = 200)
    @Column(name = "nomearquivo")
    private String nomearquivo;
    @Column(name = "datavalidade")
    @Temporal(TemporalType.DATE)
    private Date datavalidade;
    @JoinColumn(name = "fornecedorarquivotipo_idfornecedorarquivotipo", referencedColumnName = "idfornecedorarquivotipo")
    @ManyToOne(optional = false)
    private Fornecedorarquivotipo fornecedorarquivotipo;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;

    public Fornecedorarquivo() {
    }

    public Fornecedorarquivo(Integer idfornecedorarquivo) {
        this.idfornecedorarquivo = idfornecedorarquivo;
    }

    public Integer getIdfornecedorarquivo() {
        return idfornecedorarquivo;
    }

    public void setIdfornecedorarquivo(Integer idfornecedorarquivo) {
        this.idfornecedorarquivo = idfornecedorarquivo;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public Date getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(Date datavalidade) {
        this.datavalidade = datavalidade;
    }

    public Fornecedorarquivotipo getFornecedorarquivotipo() {
        return fornecedorarquivotipo;
    }

    public void setFornecedorarquivotipo(Fornecedorarquivotipo fornecedorarquivotipo) {
        this.fornecedorarquivotipo = fornecedorarquivotipo;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorarquivo != null ? idfornecedorarquivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorarquivo)) {
            return false;
        }
        Fornecedorarquivo other = (Fornecedorarquivo) object;
        if ((this.idfornecedorarquivo == null && other.idfornecedorarquivo != null) || (this.idfornecedorarquivo != null && !this.idfornecedorarquivo.equals(other.idfornecedorarquivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorarquivo[ idfornecedorarquivo=" + idfornecedorarquivo + " ]";
    }
    
}

