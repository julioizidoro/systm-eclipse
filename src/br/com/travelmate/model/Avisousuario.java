package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "avisousuario")
public class Avisousuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idavisousuario")
    private Integer idavisousuario;
    @Column(name = "visto")
    private Boolean visto;
    @JoinColumn(name = "avisos_idavisos", referencedColumnName = "idavisos")
    @ManyToOne(optional = false)
    private Avisos avisos;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;

    public Avisousuario() {
    }

    public Avisousuario(Integer idavisousuario) {
        this.idavisousuario = idavisousuario;
    }

    public Integer getIdavisousuario() {
        return idavisousuario;
    }

    public void setIdavisousuario(Integer idavisousuario) {
        this.idavisousuario = idavisousuario;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public Avisos getAvisos() {
        return avisos;
    }

    public void setAvisos(Avisos avisos) {
        this.avisos = avisos;
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
        hash += (idavisousuario != null ? idavisousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Avisousuario)) {
            return false;
        }
        Avisousuario other = (Avisousuario) object;
        if ((this.idavisousuario == null && other.idavisousuario != null) || (this.idavisousuario != null && !this.idavisousuario.equals(other.idavisousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Avisousuario[ idavisousuario=" + idavisousuario + " ]";
    }
    
}

