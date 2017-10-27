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
 * @author Kamila
 */
@Entity
@Table(name = "leadposvenda")
public class Leadposvenda implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idleadposvenda")
	private Integer idleadposvenda;
	@Column(name = "dataembarque")
	@Temporal(TemporalType.DATE)
	private Date dataembarque;
	@Column(name = "datachegada")
	@Temporal(TemporalType.DATE)
	private Date datachegada;
	@JoinColumn(name = "lead_idlead", referencedColumnName = "idlead")
	@ManyToOne(optional = false)
	private Lead lead;
	@JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	@ManyToOne(optional = false)
	private Vendas vendas;

	public Leadposvenda() {
	}

	public Integer getIdleadposvenda() {
		return idleadposvenda;
	}

	public void setIdleadposvenda(Integer idleadposvenda) {
		this.idleadposvenda = idleadposvenda;
	}

	public Date getDataembarque() {
		return dataembarque;
	}

	public void setDataembarque(Date dataembarque) {
		this.dataembarque = dataembarque;
	}

	public Date getDatachegada() {
		return datachegada;
	}

	public void setDatachegada(Date datachegada) {
		this.datachegada = datachegada;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idleadposvenda != null ? idleadposvenda.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Leadposvenda)) {
			return false;
		}
		Leadposvenda other = (Leadposvenda) object;
		if ((this.idleadposvenda == null && other.idleadposvenda != null)
				|| (this.idleadposvenda != null && !this.idleadposvenda.equals(other.idleadposvenda))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Leadposvenda[ idleadposvenda=" + idleadposvenda + " ]";
	}

}
