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
@Table(name = "leadsituacao")
public class Leadsituacao implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idleadsituacao")
	private Integer idleadsituacao;
	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	private Date data;
	@Size(max = 12)
	@Column(name = "hora")
	private String hora;
	@Column(name = "situacaoanterior")
	private Integer situacaoanterior;
	@Column(name = "situacaoatual")
	private Integer situacaoatual;
	@JoinColumn(name = "lead_idlead", referencedColumnName = "idlead")
	@ManyToOne(optional = false)
	private Lead lead;

	public Leadsituacao() {

	}

	public Integer getIdleadsituacao() {
		return idleadsituacao;
	}

	public void setIdleadsituacao(Integer idleadsituacao) {
		this.idleadsituacao = idleadsituacao;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
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

	public Integer getSituacaoanterior() {
		return situacaoanterior;
	}

	public void setSituacaoanterior(Integer situacaoanterior) {
		this.situacaoanterior = situacaoanterior;
	}

	public Integer getSituacaoatual() {
		return situacaoatual;
	}

	public void setSituacaoatual(Integer situacaoatual) {
		this.situacaoatual = situacaoatual;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idleadsituacao != null ? idleadsituacao.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Leadsituacao)) {
			return false;
		}
		Leadsituacao other = (Leadsituacao) object;
		if ((this.idleadsituacao == null && other.idleadsituacao != null)
				|| (this.idleadsituacao != null && !this.idleadsituacao.equals(other.idleadsituacao))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Leadsituacao[ idleadsituacao=" + idleadsituacao + " ]";
	}

}
