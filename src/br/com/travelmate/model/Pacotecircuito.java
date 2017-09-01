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
@Table(name = "pacotecircuito")
@NamedQueries({
    @NamedQuery(name = "Pacotecircuito.findAll", query = "SELECT p FROM Pacotecircuito p")})
public class Pacotecircuito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotecircuito")
    private Integer idpacotecircuito;
    @Size(max = 100)
    @Column(name = "de")
    private String de;
    @Size(max = 100)
    @Column(name = "para")
    private String para;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Column(name = "datatertminio")
    @Temporal(TemporalType.DATE)
    private Date datatertminio;
    @Column(name = "adultos")
    private Integer adultos;
    @Column(name = "criancas")
    private Integer criancas;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descritivo")
    private String descritivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tarifa")
    private Float tarifa;
    @Column(name = "taxas")
    private Float taxas;
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
    @Column(name = "datapagamentoparceiro")
    @Temporal(TemporalType.DATE)
    private Date datapagamentoparceiro;

    public Pacotecircuito() {
    }

    public Pacotecircuito(Integer idpacotecircuito) {
        this.idpacotecircuito = idpacotecircuito;
    }

	public Integer getIdpacotecircuito() {
		return idpacotecircuito;
	}

	public void setIdpacotecircuito(Integer idpacotecircuito) {
		this.idpacotecircuito = idpacotecircuito;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatatertminio() {
		return datatertminio;
	}

	public void setDatatertminio(Date datatertminio) {
		this.datatertminio = datatertminio;
	}

	public Integer getAdultos() {
		return adultos;
	}

	public void setAdultos(Integer adultos) {
		this.adultos = adultos;
	}

	public Integer getCriancas() {
		return criancas;
	}

	public void setCriancas(Integer criancas) {
		this.criancas = criancas;
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
		return taxas;
	}

	public void setTaxa(Float taxa) {
		this.taxas = taxa;
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

	public Date getDatapagamentoparceiro() {
		return datapagamentoparceiro;
	}

	public void setDatapagamentoparceiro(Date datapagamentoparceiro) {
		this.datapagamentoparceiro = datapagamentoparceiro;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpacotecircuito != null ? idpacotecircuito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pacotecircuito)) {
            return false;
        }
        Pacotecircuito other = (Pacotecircuito) object;
        if ((this.idpacotecircuito == null && other.idpacotecircuito != null) || (this.idpacotecircuito != null && !this.idpacotecircuito.equals(other.idpacotecircuito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotecircuito[ idpacotecircuito=" + idpacotecircuito + " ]";
    }
    
}
