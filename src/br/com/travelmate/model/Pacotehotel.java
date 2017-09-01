/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pacotehotel")
@NamedQueries({ @NamedQuery(name = "Pacotehotel.findAll", query = "SELECT p FROM Pacotehotel p") })
public class Pacotehotel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idpacoteHotel")
	private Integer idpacoteHotel;
	@Size(max = 50)
	@Column(name = "cidade")
	private String cidade;
	@Size(max = 50)
	@Column(name = "pais")
	private String pais;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "valormoedanacional")
	private Float valormoedanacional;
	@Column(name = "datacheckin")
	@Temporal(TemporalType.DATE)
	private Date datacheckin;
	@Size(max = 5)
	@Column(name = "horacheckin")
	private String horacheckin;
	@Column(name = "datacheckout")
	@Temporal(TemporalType.DATE)
	private Date datacheckout;
	@Size(max = 5)
	@Column(name = "horacheckout")
	private String horacheckout;
	@Size(max = 10)
	@Column(name = "tipoquarto")
	private String tipoquarto;
	@Size(max = 10)
	@Column(name = "categoria")
	private String categoria;
	@Size(max = 20)
	@Column(name = "regime")
	private String regime;
	@Lob
	@Size(max = 16777215)
	@Column(name = "descritivo")
	private String descritivo;
	@Column(name = "tarifa")
	private Float tarifa;
	@Column(name = "taxa")
	private Float taxa;
	@JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
	@ManyToOne(optional = false)
	private Cambio cambio;
	@JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
	@ManyToOne(optional = false)
	private Fornecedorcidade fornecedorcidade;
	@JoinColumn(name = "pacotetrecho_idpacotetrecho", referencedColumnName = "idpacotetrecho")
	@ManyToOne(optional = false)
	private Pacotetrecho pacotetrecho;
	@Column(name = "valorcambio")
	private Float valorcambio;
	@Column(name = "comissaofornecedor")
	private Float comissaofornecedor;
	@Column(name = "datapagamentoparceiro")
	@Temporal(TemporalType.DATE)
	private Date datapagamentoparceiro;

	public Pacotehotel() {
	}

	public Pacotehotel(Integer idpacoteHotel) {
		this.idpacoteHotel = idpacoteHotel;
	}

	public Integer getIdpacoteHotel() {
		return idpacoteHotel;
	}

	public void setIdpacoteHotel(Integer idpacoteHotel) {
		this.idpacoteHotel = idpacoteHotel;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Float getValormoedanacional() {
		return valormoedanacional;
	}

	public void setValormoedanacional(Float valormoedanacional) {
		this.valormoedanacional = valormoedanacional;
	}

	public Date getDatacheckin() {
		return datacheckin;
	}

	public void setDatacheckin(Date datacheckin) {
		this.datacheckin = datacheckin;
	}

	public String getHoracheckin() {
		return horacheckin;
	}

	public void setHoracheckin(String horacheckin) {
		this.horacheckin = horacheckin;
	}

	public Date getDatacheckout() {
		return datacheckout;
	}

	public void setDatacheckout(Date datacheckout) {
		this.datacheckout = datacheckout;
	}

	public String getHoracheckout() {
		return horacheckout;
	}

	public void setHoracheckout(String horacheckout) {
		this.horacheckout = horacheckout;
	}

	public String getTipoquarto() {
		return tipoquarto;
	}

	public void setTipoquarto(String tipoquarto) {
		this.tipoquarto = tipoquarto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public String getDescritivo() {
		return descritivo;
	}

	public void setDescritivo(String descritivo) {
		this.descritivo = descritivo;
	}

	public Float getTarifa() {
		return tarifa;
	}

	public void setTarifa(Float tarifa) {
		this.tarifa = tarifa;
	}

	public Float getTaxa() {
		return taxa;
	}

	public void setTaxa(Float taxa) {
		this.taxa = taxa;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public Pacotetrecho getPacotetrecho() {
		return pacotetrecho;
	}

	public void setPacotetrecho(Pacotetrecho pacotetrecho) {
		this.pacotetrecho = pacotetrecho;
	}

	public Float getValorcambio() {
		return valorcambio;
	}

	public void setValorcambio(Float valorcambio) {
		this.valorcambio = valorcambio;
	}

	public Float getComissaofornecedor() {
		return comissaofornecedor;
	}

	public void setComissaofornecedor(Float comissaofornecedor) {
		this.comissaofornecedor = comissaofornecedor;
	}

	public Date getDatapagamentoparceiro() {
		return datapagamentoparceiro;
	}

	public void setDatapagamentoparceiro(Date datapagamentoparceiro) {
		this.datapagamentoparceiro = datapagamentoparceiro;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idpacoteHotel != null ? idpacoteHotel.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Pacotehotel)) {
			return false;
		}
		Pacotehotel other = (Pacotehotel) object;
		if ((this.idpacoteHotel == null && other.idpacoteHotel != null)
				|| (this.idpacoteHotel != null && !this.idpacoteHotel.equals(other.idpacoteHotel))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Pacotehotel[ idpacoteHotel=" + idpacoteHotel + " ]";
	}

}
