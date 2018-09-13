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
@Table(name = "leadcontrole")
public class Leadcontrole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idleadcontrole")
	private Integer idleadcontrole;
	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	private Date data;
	@Size(max = 8)
	@Column(name = "hora")
	private String hora;
	@Column(name = "numeroleads")
	private String numeroleads;
	
	
	public Leadcontrole() {
	}


	public Integer getIdleadcontrole() {
		return idleadcontrole;
	}


	public void setIdleadcontrole(Integer idleadcontrole) {
		this.idleadcontrole = idleadcontrole;
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


	public String getNumeroleads() {
		return numeroleads;
	}


	public void setNumeroleads(String numeroleads) {
		this.numeroleads = numeroleads;
	}
	
	
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idleadcontrole != null ? idleadcontrole.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Leadcontrole)) {
			return false;
		}
		Leadcontrole other = (Leadcontrole) object;
		if ((this.idleadcontrole == null && other.idleadcontrole != null)
				|| (this.idleadcontrole != null && !this.idleadcontrole.equals(other.idleadcontrole))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Leadcontrole[ idleadcontrole=" + idleadcontrole + " ]";
	}

}
