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
 * @author wolverine
 */
@Entity
@Table(name = "leadencaminhado")
public class Leadencaminhado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idleadencaminhado")
    private Integer idleadencaminhado;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 8)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "lead_idlead", referencedColumnName = "idlead")
    @ManyToOne(optional = false)
    private Lead lead;
    @JoinColumn(name = "usuario_idusuariode", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuariode;
    @JoinColumn(name = "usuario_idusuariopara", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuariopara;

    public Leadencaminhado() {
    }

    public Leadencaminhado(Integer idleadencaminhado) {
        this.idleadencaminhado = idleadencaminhado;
    }

    public Integer getIdleadencaminhado() {
        return idleadencaminhado;
    }

    public void setIdleadencaminhado(Integer idleadencaminhado) {
        this.idleadencaminhado = idleadencaminhado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    

    public Usuario getUsuariode() {
		return usuariode;
	}

	public void setUsuariode(Usuario usuariode) {
		this.usuariode = usuariode;
	}

	public Usuario getUsuariopara() {
		return usuariopara;
	}

	public void setUsuariopara(Usuario usuariopara) {
		this.usuariopara = usuariopara;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idleadencaminhado != null ? idleadencaminhado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Leadencaminhado)) {
            return false;
        }
        Leadencaminhado other = (Leadencaminhado) object;
        if ((this.idleadencaminhado == null && other.idleadencaminhado != null) || (this.idleadencaminhado != null && !this.idleadencaminhado.equals(other.idleadencaminhado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Leadencaminhado[ idleadencaminhado=" + idleadencaminhado + " ]";
    }
    
}
