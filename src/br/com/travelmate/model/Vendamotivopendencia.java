package br.com.travelmate.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "vendamotivopendencia")
public class Vendamotivopendencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvendamotivopendencia")
    private Integer idvendamotivopendencia;
    @Size(max = 40)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendamotivopendencia")
    private List<Vendapendencia> vendapendenciaList;

    public Vendamotivopendencia() {
    }

    public Vendamotivopendencia(Integer idvendamotivopendencia) {
        this.idvendamotivopendencia = idvendamotivopendencia;
    }

    public Integer getIdvendamotivopendencia() {
        return idvendamotivopendencia;
    }

    public void setIdvendamotivopendencia(Integer idvendamotivopendencia) {
        this.idvendamotivopendencia = idvendamotivopendencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Vendapendencia> getVendapendenciaList() {
        return vendapendenciaList;
    }

    public void setVendapendenciaList(List<Vendapendencia> vendapendenciaList) {
        this.vendapendenciaList = vendapendenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendamotivopendencia != null ? idvendamotivopendencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendamotivopendencia)) {
            return false;
        }
        Vendamotivopendencia other = (Vendamotivopendencia) object;
        if ((this.idvendamotivopendencia == null && other.idvendamotivopendencia != null) || (this.idvendamotivopendencia != null && !this.idvendamotivopendencia.equals(other.idvendamotivopendencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Vendamotivopendencia[ idvendamotivopendencia=" + idvendamotivopendencia + " ]";
    }
    
}

