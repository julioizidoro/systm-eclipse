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
@Table(name = "versaousuario")
public class Versaousuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idversaousuario")
    private Integer idversaousuario;
    @JoinColumn(name = "versoes_idversoes", referencedColumnName = "idversoes")
    @ManyToOne(optional = false)
    private Versoes versoes;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Versaousuario() {
    }

    public Versaousuario(Integer idversaousuario) {
        this.idversaousuario = idversaousuario;
    }

    public Integer getIdversaousuario() {
        return idversaousuario;
    }

    public void setIdversaousuario(Integer idversaousuario) {
        this.idversaousuario = idversaousuario;
    }

    public Versoes getVersoes() {
        return versoes;
    }

    public void setVersoes(Versoes versoes) {
        this.versoes = versoes;
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
        hash += (idversaousuario != null ? idversaousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Versaousuario)) {
            return false;
        }
        Versaousuario other = (Versaousuario) object;
        if ((this.idversaousuario == null && other.idversaousuario != null) || (this.idversaousuario != null && !this.idversaousuario.equals(other.idversaousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Versaousuario[ idversaousuario=" + idversaousuario + " ]";
    }
    
}

