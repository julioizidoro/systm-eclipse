package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "vendapendencia")
public class Vendapendencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvendapendencia")
    private Integer idvendapendencia;
    @Column(name = "dataproximocontato")
    @Temporal(TemporalType.DATE)
    private Date dataproximocontato;
    @Lob
    @Size(max = 16777215)
    @Column(name = "relato")
    private String relato;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "vendamotivopendencia_idvendamotivopendencia", referencedColumnName = "idvendamotivopendencia")
    @ManyToOne(optional = false)
    private Vendamotivopendencia vendamotivopendencia;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "vendapendencia")
    private List<Vendapendenciahistorico> vendapendenciahistoricoList;

    public Vendapendencia() {
    }

    public Vendapendencia(Integer idvendapendencia) {
        this.idvendapendencia = idvendapendencia;
    }

    public Integer getIdvendapendencia() {
        return idvendapendencia;
    }

    public void setIdvendapendencia(Integer idvendapendencia) {
        this.idvendapendencia = idvendapendencia;
    }

    public Date getDataproximocontato() {
        return dataproximocontato;
    }

    public void setDataproximocontato(Date dataproximocontato) {
        this.dataproximocontato = dataproximocontato;
    }

    public String getRelato() {
        return relato;
    }

    public void setRelato(String relato) {
        this.relato = relato;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Vendamotivopendencia getVendamotivopendencia() {
        return vendamotivopendencia;
    }

    public void setVendamotivopendencia(Vendamotivopendencia vendamotivopendencia) {
        this.vendamotivopendencia = vendamotivopendencia;
    }

    public List<Vendapendenciahistorico> getVendapendenciahistoricoList() {
        return vendapendenciahistoricoList;
    }

    public void setVendapendenciahistoricoList(List<Vendapendenciahistorico> vendapendenciahistoricoList) {
        this.vendapendenciahistoricoList = vendapendenciahistoricoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendapendencia != null ? idvendapendencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendapendencia)) {
            return false;
        }
        Vendapendencia other = (Vendapendencia) object;
        if ((this.idvendapendencia == null && other.idvendapendencia != null) || (this.idvendapendencia != null && !this.idvendapendencia.equals(other.idvendapendencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Vendapendencia[ idvendapendencia=" + idvendapendencia + " ]";
    }
    
}

