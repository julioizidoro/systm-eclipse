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
@Table(name = "notificacaouploadusuario")
public class Notificacaouploadusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotificacaouploadusuario")
    private Integer idnotificacaouploadusuario;
    @JoinColumn(name = "usuario_upload", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioUpload;
    @JoinColumn(name = "usuario_notificar", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioNotificar;

    public Notificacaouploadusuario() {
    }

    public Notificacaouploadusuario(Integer idnotificacaouploadusuario) {
        this.idnotificacaouploadusuario = idnotificacaouploadusuario;
    }

    public Integer getIdnotificacaouploadusuario() {
        return idnotificacaouploadusuario;
    }

    public void setIdnotificacaouploadusuario(Integer idnotificacaouploadusuario) {
        this.idnotificacaouploadusuario = idnotificacaouploadusuario;
    }

    

    public Usuario getUsuarioUpload() {
		return usuarioUpload;
	}

	public void setUsuarioUpload(Usuario usuarioUpload) {
		this.usuarioUpload = usuarioUpload;
	}

	public Usuario getUsuarioNotificar() {
		return usuarioNotificar;
	}

	public void setUsuarioNotificar(Usuario usuarioNotificar) {
		this.usuarioNotificar = usuarioNotificar;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotificacaouploadusuario != null ? idnotificacaouploadusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notificacaouploadusuario)) {
            return false;
        }
        Notificacaouploadusuario other = (Notificacaouploadusuario) object;
        if ((this.idnotificacaouploadusuario == null && other.idnotificacaouploadusuario != null) || (this.idnotificacaouploadusuario != null && !this.idnotificacaouploadusuario.equals(other.idnotificacaouploadusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Notificacaouploadusuario[ idnotificacaouploadusuario=" + idnotificacaouploadusuario + " ]";
    }
    
}

