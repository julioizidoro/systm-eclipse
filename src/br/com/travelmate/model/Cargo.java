package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.*; 
 
import java.util.List;

@Entity
@Table(name = "cargo")
public class Cargo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcargo")
	private Integer idcargo;
	@Column(name = "nome")
	private String nome;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "cargo")
	private List<Usuario> usuarioList;
 
	public Cargo() {
	}

	public Integer getIdcargo() {
		return idcargo;
	}

	public void setIdcargo(Integer idcargo) {
		this.idcargo = idcargo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcargo != null ? idcargo.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Cargo)) {
			return false;
		}
		Cargo other = (Cargo) object;
		if ((this.idcargo == null && other.idcargo != null)
				|| (this.idcargo != null && !this.idcargo.equals(other.idcargo))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.Interface.Cargo[ idcargo=" + idcargo + " ]";
	}

}
