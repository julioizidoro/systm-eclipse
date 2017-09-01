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
 * @author Wolverine
 */
@Entity
@Table(name = "tipocontato")
public class Tipocontato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipocontato")
    private Integer idtipocontato;
    @Size(max = 50)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "tipocontato")
    private List<Leadhistorico> leadhistoricoList; 
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "tipocontato")
    private List<Lead> leadList;

    public Tipocontato() {
    }

    public Tipocontato(Integer idtipocontato) {
        this.idtipocontato = idtipocontato;
    }

    public Integer getIdtipocontato() {
        return idtipocontato;
    }

    public void setIdtipocontato(Integer idtipocontato) {
        this.idtipocontato = idtipocontato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Leadhistorico> getLeadhistoricoList() {
        return leadhistoricoList;
    }

    public void setLeadhistoricoList(List<Leadhistorico> leadhistoricoList) {
        this.leadhistoricoList = leadhistoricoList;
    }
 
    public List<Lead> getLeadList() {
        return leadList;
    }

    public void setLeadList(List<Lead> leadList) {
        this.leadList = leadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocontato != null ? idtipocontato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tipocontato)) {
            return false;
        }
        Tipocontato other = (Tipocontato) object;
        if ((this.idtipocontato == null && other.idtipocontato != null) || (this.idtipocontato != null && !this.idtipocontato.equals(other.idtipocontato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Tipocontato[ idtipocontato=" + idtipocontato + " ]";
    }
    
}

