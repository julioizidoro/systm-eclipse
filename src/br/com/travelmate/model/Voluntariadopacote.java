package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.*;  

@Entity
@Table(name = "voluntariadopacote")
public class Voluntariadopacote implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idvoluntariadopacote")
	private Integer idvoluntariadopacote; 
	@JoinColumn(name = "voluntariadoprojetovalor_idvoluntariadoprojetovalor", referencedColumnName = "idvoluntariadoprojetovalor")
	@ManyToOne(optional = false)
	private Voluntariadoprojetovalor voluntariadoprojetovalor;
	@JoinColumn(name = "cursospacote_idcursospacote", referencedColumnName = "idcursospacote")
	@ManyToOne(optional = false)
	private Cursospacote cursospacote;
 
	public Voluntariadopacote() {
	}

	 
	public Integer getIdvoluntariadopacote() {
		return idvoluntariadopacote;
	}


	public void setIdvoluntariadopacote(Integer idvoluntariadopacote) {
		this.idvoluntariadopacote = idvoluntariadopacote;
	}


	public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
		return voluntariadoprojetovalor;
	}


	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}


	public Cursospacote getCursospacote() {
		return cursospacote;
	}


	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idvoluntariadopacote != null ? idvoluntariadopacote.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Voluntariadopacote)) {
			return false;
		}
		Voluntariadopacote other = (Voluntariadopacote) object;
		if ((this.idvoluntariadopacote == null && other.idvoluntariadopacote != null)
				|| (this.idvoluntariadopacote != null && !this.idvoluntariadopacote.equals(other.idvoluntariadopacote))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Voluntariadopacote[ idvoluntariadopacote=" + idvoluntariadopacote + " ]";
	}

}
