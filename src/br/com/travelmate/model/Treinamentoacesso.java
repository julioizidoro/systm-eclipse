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

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "treinamentoacesso")
public class Treinamentoacesso implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idtreinamentoacesso")
	private Integer idtreinamentoacesso;
	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	private Date data;
	@Column(name = "hora")
	private String hora;
	@JoinColumn(name = "mtp_idmtp", referencedColumnName = "idmtp")
	@ManyToOne(optional = false)
	private Mtp mtp;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false)
	private Usuario usuario;

	public Treinamentoacesso() {
	}

	public Integer getIdtreinamentoacesso() {
		return idtreinamentoacesso;
	}

	public void setIdtreinamentoacesso(Integer idtreinamentoacesso) {
		this.idtreinamentoacesso = idtreinamentoacesso;
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

	public Mtp getMtp() {
		return mtp;
	}

	public void setMtp(Mtp mtp) {
		this.mtp = mtp;
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
		hash += (idtreinamentoacesso != null ? idtreinamentoacesso.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Treinamentoacesso)) {
			return false;
		}
		Treinamentoacesso other = (Treinamentoacesso) object;
		if ((this.idtreinamentoacesso == null && other.idtreinamentoacesso != null)
				|| (this.idtreinamentoacesso != null && !this.idtreinamentoacesso.equals(other.idtreinamentoacesso))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Treinamentoacesso[ idtreinamentoacesso=" + idtreinamentoacesso + " ]";
	}

}
