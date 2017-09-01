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

@Entity
@Table(name = "mtp")
public class Mtp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idmtp")
	private Integer idmtp;
	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	private Date data;
	@Size(max = 10)
	@Column(name = "host")
	private String host;
	@Size(max = 100)
	@Column(name = "hora")
	private String hora;
	@Column(name = "nota")
	private String nota;
	@Column(name = "instituicao")
	private String instituicao;
	@JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
	@ManyToOne(optional = false)
	private Departamento departamento;
	@JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
	@ManyToOne(optional = false)
	private Pais pais;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "nomearquivo")
	private String nomearquivo;

	public Mtp() {
	}

	public Integer getIdmtp() {
		return idmtp;
	}

	public void setIdmtp(Integer idmtp) {
		this.idmtp = idmtp;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNomearquivo() {
		return nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idmtp != null ? idmtp.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Mtp)) {
			return false;
		}
		Mtp other = (Mtp) object;
		if ((this.idmtp == null && other.idmtp != null) || (this.idmtp != null && !this.idmtp.equals(other.idmtp))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "model.Mtp[ idmtp=" + idmtp + " ]";
	}

}
