package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name ="relatoriocliente")
public class Relatoriocliente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idrelatoriocliente")
	private Integer idrelatoriocliente;
	@Column(name = "nome")
	private String nome;
	@Column(name = "dataNascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@Column(name = "foneResidencial")
	private String foneResidencial;
	@Column(name = "foneCelular")
	private String foneCelular;
	@Column(name = "email")
	private String email;
	@Column(name = "unidade")
	private String unidade;
	@Column(name = "programa")
	private String programa;
	@Column(name = "pais")
	private String pais;
	@Column(name = "cidade")
	private String cidade;
	
	public Relatoriocliente() {
		
	}

	

	public Integer getIdrelatoriocliente() {
		return idrelatoriocliente;
	}



	public void setIdrelatoriocliente(Integer idrelatoriocliente) {
		this.idrelatoriocliente = idrelatoriocliente;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getFoneResidencial() {
		return foneResidencial;
	}

	public void setFoneResidencial(String foneResidencial) {
		this.foneResidencial = foneResidencial;
	}

	public String getFoneCelular() {
		return foneCelular;
	}

	public void setFoneCelular(String foneCelular) {
		this.foneCelular = foneCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idrelatoriocliente == null) ? 0 : idrelatoriocliente.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relatoriocliente other = (Relatoriocliente) obj;
		if (idrelatoriocliente == null) {
			if (other.idrelatoriocliente != null)
				return false;
		} else if (!idrelatoriocliente.equals(other.idrelatoriocliente))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Relatoriocliente [nome=" + nome + "]";
	}
	
	
	
	
}
