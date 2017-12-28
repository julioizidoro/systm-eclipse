package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the pacoteseguro database table.
 * 
 */
@Entity
@Table(name = "pacoteseguro")
@NamedQueries({ @NamedQuery(name = "Pacoteseguro.findAll", query = "SELECT p FROM Pacoteseguro p") })
public class Pacoteseguro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idpacoteseguro")
	private Integer idpacoteseguro;
	@Column(name = "numerosegurados")
	private Integer numerosegurados;
	@Column(name = "totalvalornet")
	private Float totalvalornet;
	@Column(name = "valornet")
	private Float valornet;
	@JoinColumn(name = "pacotes_idpacotes", referencedColumnName = "idpacotes")
	@ManyToOne(optional = false)
	private Pacotes pacotes;
	@JoinColumn(name = "seguroviagem_idseguroViagem", referencedColumnName = "idseguroViagem")
	@ManyToOne(optional = false)
	private Seguroviagem seguroviagem;

	public Pacoteseguro() {
		totalvalornet = 0f;
		valornet = 0f;
	}

	public Integer getIdpacoteseguro() {
		return idpacoteseguro;
	}

	public void setIdpacoteseguro(Integer idpacoteseguro) {
		this.idpacoteseguro = idpacoteseguro;
	}

	public Integer getNumerosegurados() {
		return numerosegurados;
	}

	public void setNumerosegurados(Integer numerosegurados) {
		this.numerosegurados = numerosegurados;
	}

	public Float getTotalvalornet() {
		return totalvalornet;
	}

	public void setTotalvalornet(Float totalvalornet) {
		this.totalvalornet = totalvalornet;
	}

	public Float getValornet() {
		return valornet;
	}

	public void setValornet(Float valornet) {
		this.valornet = valornet;
	}

	public Pacotes getPacotes() {
		return pacotes;
	}

	public void setPacotes(Pacotes pacotes) {
		this.pacotes = pacotes;
	}

	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}

	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Pacoteseguro)) {
			return false;
		}
		Pacoteseguro other = (Pacoteseguro) object;
		if ((this.idpacoteseguro == null && other.idpacoteseguro != null)
				|| (this.idpacoteseguro != null && !this.idpacoteseguro.equals(other.idpacoteseguro))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Pacoteseguro[ idpacoteseguro=" + idpacoteseguro + " ]";
	}

}