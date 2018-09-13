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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "leadresponsavel")
public class Leadresponsavel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idleadresponsavel")
    private Integer idleadresponsavel;
    @JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @OneToOne(optional = false)
    private Usuario usuario;

    public Leadresponsavel() {
    }

    public Leadresponsavel(Integer idleadresponsavel) {
        this.idleadresponsavel = idleadresponsavel;
    }

    public Integer getIdleadresponsavel() {
        return idleadresponsavel;
    }

    public void setIdleadresponsavel(Integer idleadresponsavel) {
        this.idleadresponsavel = idleadresponsavel;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idleadresponsavel != null ? idleadresponsavel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Leadresponsavel)) {
            return false;
        }
        Leadresponsavel other = (Leadresponsavel) object;
        if ((this.idleadresponsavel == null && other.idleadresponsavel != null) || (this.idleadresponsavel != null && !this.idleadresponsavel.equals(other.idleadresponsavel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Leadresponsavel[ idleadresponsavel=" + idleadresponsavel + " ]";
    }
    
}

