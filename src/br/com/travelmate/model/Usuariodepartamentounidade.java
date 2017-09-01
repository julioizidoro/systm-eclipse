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
 * @author Kamila
 */
@Entity
@Table(name = "usuariodepartamentounidade")
public class Usuariodepartamentounidade implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idusuariodepartamentounidade")
	private Integer idusuariodepartamentounidade;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Usuario usuario;
	@JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
	@ManyToOne(optional = false)
	private Unidadenegocio unidadenegocio;
	@JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
	@ManyToOne(optional = false)
	private Departamento departamento;
	@Column(name="tipo")
	private String tipo;
	
	public Usuariodepartamentounidade() {
	}

	public Integer getIdusuariodepartamentounidade() {
		return idusuariodepartamentounidade;
	}

	public void setIdusuariodepartamentounidade(Integer idusuariodepartamentounidade) {
		this.idusuariodepartamentounidade = idusuariodepartamentounidade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	 public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idusuariodepartamentounidade != null ? idusuariodepartamentounidade.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        if (!(object instanceof Usuariodepartamentounidade)) {
	            return false;
	        }
	        Usuariodepartamentounidade other = (Usuariodepartamentounidade) object;
	        if ((this.idusuariodepartamentounidade == null && other.idusuariodepartamentounidade != null) || (this.idusuariodepartamentounidade != null && !this.idusuariodepartamentounidade.equals(other.idusuariodepartamentounidade))) {
	            return false;
	        }
	        return true;
	    }

	@Override
	public String toString() {
		return "br.com.travelmate.model.Usuariodepartamentounidade[ idusuariodepartamentounidade="
				+ idusuariodepartamentounidade + " ]";
	}

}
