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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vendasembarque")
public class Vendasembarque implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idvendasembarque")
	private Integer idvendasembarque;
	@Column(name = "dataida")
	@Temporal(TemporalType.DATE)
	private Date dataida;
	@Column(name = "datavolta")
	@Temporal(TemporalType.DATE)
	private Date datavolta;
	@JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;

	public Vendasembarque()  {
		
	}

	public Integer getIdvendasembarque() {
		return idvendasembarque;
	}

	public void setIdvendasembarque(Integer idvendasembarque) {
		this.idvendasembarque = idvendasembarque;
	}

	public Date getDataida() {
		return dataida;
	}

	public void setDataida(Date dataida) {
		this.dataida = dataida;
	}

	public Date getDatavolta() {
		return datavolta;
	}

	public void setDatavolta(Date datavolta) {
		this.datavolta = datavolta;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	
	

}
