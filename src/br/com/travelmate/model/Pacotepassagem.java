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
@Table(name = "pacotepassagem")
@NamedQueries({
    @NamedQuery(name = "Pacotepassagem.findAll", query = "SELECT p FROM Pacotepassagem p")})
public class Pacotepassagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotepassagem")
    private Integer idpacotepassagem;
    @Size(max = 50)
    @Column(name = "localizador")
    private String localizador;
    @Size(max = 100)
    @Column(name = "ciaAerea")
    private String ciaAerea;
    @Column(name = "dataEmbarque")
    @Temporal(TemporalType.DATE)
    private Date dataEmbarque;
    @Column(name = "dataVolta")
    @Temporal(TemporalType.DATE)
    private Date dataVolta;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacoes")
    private String observacoes;
    @Size(max = 100)
    @Column(name = "intinerario")
    private String intinerario;
    @Column(name = "dataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Column(name = "adttarifa")
    private Float adttarifa;
    @Column(name = "adttaxas")
    private Float adttaxas;
    @Column(name = "adttaxaemissao")
    private Float adttaxaemissao;
    @Column(name = "adtcomissao")
    private Float adtcomissao;
    @Column(name = "chdtarifa")
    private Float chdtarifa;
    @Column(name = "chdtaxas")
    private Float chdtaxas;
    @Column(name = "chdtaxaemissao")
    private Float chdtaxaemissao;
    @Column(name = "chdcomissao")
    private Float chdcomissao;
    @Column(name = "inftarifa")
    private Float inftarifa;
    @Column(name = "inftaxas")
    private Float inftaxas;
    @Column(name = "inftaxaemissao")
    private Float inftaxaemissao;
    @Column(name = "infcomissao")
    private Float infcomissao;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "pacotetrecho_idpacotetrecho", referencedColumnName = "idpacotetrecho")
    @ManyToOne(optional = false)
    private Pacotetrecho pacotetrecho;
    @Column(name = "comissaofornecedor")
    private Float comissaofornecedor;
    @Column(name = "datapagamentoparceiro")
    @Temporal(TemporalType.DATE)
    private Date datapagamentoparceiro;

    public Pacotepassagem() {
    }

    public Pacotepassagem(Integer idpacotepassagem) {
        this.idpacotepassagem = idpacotepassagem;
    }

    public Integer getIdpacotepassagem() {
        return idpacotepassagem;
    }

    public void setIdpacotepassagem(Integer idpacotepassagem) {
        this.idpacotepassagem = idpacotepassagem;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public String getCiaAerea() {
        return ciaAerea;
    }

    public void setCiaAerea(String ciaAerea) {
        this.ciaAerea = ciaAerea;
    }

    public Date getDataEmbarque() {
        return dataEmbarque;
    }

    public void setDataEmbarque(Date dataEmbarque) {
        this.dataEmbarque = dataEmbarque;
    }

    public Date getDataVolta() {
        return dataVolta;
    }

    public void setDataVolta(Date dataVolta) {
        this.dataVolta = dataVolta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getIntinerario() {
        return intinerario;
    }

    public void setIntinerario(String intinerario) {
        this.intinerario = intinerario;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Float getAdttarifa() {
        return adttarifa;
    }

    public void setAdttarifa(Float adttarifa) {
        this.adttarifa = adttarifa;
    }

    public Float getAdttaxas() {
        return adttaxas;
    }

    public void setAdttaxas(Float adttaxas) {
        this.adttaxas = adttaxas;
    }

    public Float getAdttaxaemissao() {
        return adttaxaemissao;
    }

    public void setAdttaxaemissao(Float adttaxaemissao) {
        this.adttaxaemissao = adttaxaemissao;
    }

    public Float getAdtcomissao() {
        return adtcomissao;
    }

    public void setAdtcomissao(Float adtcomissao) {
        this.adtcomissao = adtcomissao;
    }

    public Float getChdtarifa() {
        return chdtarifa;
    }

    public void setChdtarifa(Float chdtarifa) {
        this.chdtarifa = chdtarifa;
    }

    public Float getChdtaxas() {
        return chdtaxas;
    }

    public void setChdtaxas(Float chdtaxas) {
        this.chdtaxas = chdtaxas;
    }

    public Float getChdtaxaemissao() {
        return chdtaxaemissao;
    }

    public void setChdtaxaemissao(Float chdtaxaemissao) {
        this.chdtaxaemissao = chdtaxaemissao;
    }

    public Float getChdcomissao() {
        return chdcomissao;
    }

    public void setChdcomissao(Float chdcomissao) {
        this.chdcomissao = chdcomissao;
    }

    public Float getInftarifa() {
        return inftarifa;
    }

    public void setInftarifa(Float inftarifa) {
        this.inftarifa = inftarifa;
    }

    public Float getInftaxas() {
        return inftaxas;
    }

    public void setInftaxas(Float inftaxas) {
        this.inftaxas = inftaxas;
    }

    public Float getInftaxaemissao() {
        return inftaxaemissao;
    }

    public void setInftaxaemissao(Float inftaxaemissao) {
        this.inftaxaemissao = inftaxaemissao;
    }

    public Float getInfcomissao() {
        return infcomissao;
    }

    public void setInfcomissao(Float infcomissao) {
        this.infcomissao = infcomissao;
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
        hash += (idpacotepassagem != null ? idpacotepassagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pacotepassagem)) {
            return false;
        }
        Pacotepassagem other = (Pacotepassagem) object;
        if ((this.idpacotepassagem == null && other.idpacotepassagem != null) || (this.idpacotepassagem != null && !this.idpacotepassagem.equals(other.idpacotepassagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotepassagem[ idpacotepassagem=" + idpacotepassagem + " ]";
    }
    
}
