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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controleaupair")
public class Controleaupair implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleAuPair")
    private Integer idcontroleAuPair;
    @Column(name = "dataembarque")
    @Temporal(TemporalType.DATE)
    private Date dataembarque;
    @Size(max = 100)
    @Column(name = "estado")
    private String estado;
    @Size(max = 50)
    @Column(name = "statusprocesso")
    private String statusprocesso;
    @Column(name = "dataonline")
    @Temporal(TemporalType.DATE)
    private Date dataonline;
    @Column(name = "datamatch")
    @Temporal(TemporalType.DATE)
    private Date datamatch;
    @Column(name = "dataretormo")
    @Temporal(TemporalType.DATE)
    private Date dataretormo;
    @Column(name = "dataminimaretorno")
    @Temporal(TemporalType.DATE)
    private Date dataminimaretorno;
    @Column(name = "comissaopaga")
    private boolean comissaopaga;
    @Column(name = "valorcomissao")
    private Float valorcomissao;
    @Column(name = "datadescontocomissao")
    @Temporal(TemporalType.DATE)
    private Date datadescontocomissao;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Column(name = "datapagamento")
    @Temporal(TemporalType.DATE)
    private Date datapagamento;
    @Column(name = "descontocomissao")
    private Float descontocomissao;  
    @Column(name = "childtraining")
    private boolean childtraining;

    public Controleaupair() {
    }

    public Controleaupair(Integer idcontroleAuPair) {
        this.idcontroleAuPair = idcontroleAuPair;
    }

    public Integer getIdcontroleAuPair() {
        return idcontroleAuPair;
    }

    public void setIdcontroleAuPair(Integer idcontroleAuPair) {
        this.idcontroleAuPair = idcontroleAuPair;
    }

    public Date getDataembarque() {
        return dataembarque;
    }

    public void setDataembarque(Date dataembarque) {
        this.dataembarque = dataembarque;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatusprocesso() {
        return statusprocesso;
    }

    public void setStatusprocesso(String statusprocesso) {
        this.statusprocesso = statusprocesso;
    }

    public Date getDataonline() {
        return dataonline;
    }

    public void setDataonline(Date dataonline) {
        this.dataonline = dataonline;
    }

    public Date getDatamatch() {
        return datamatch;
    }

    public void setDatamatch(Date datamatch) {
        this.datamatch = datamatch;
    }

    public Date getDataretormo() {
        return dataretormo;
    }

    public void setDataretormo(Date dataretormo) {
        this.dataretormo = dataretormo;
    }

    public Date getDataminimaretorno() {
        return dataminimaretorno;
    }

    public void setDataminimaretorno(Date dataminimaretorno) {
        this.dataminimaretorno = dataminimaretorno;
    } 

    public boolean isComissaopaga() {
		return comissaopaga;
	}

	public void setComissaopaga(boolean comissaopaga) {
		this.comissaopaga = comissaopaga;
	}

	public Float getValorcomissao() {
        return valorcomissao;
    }

    public void setValorcomissao(Float valorcomissao) {
        this.valorcomissao = valorcomissao;
    }

    public Date getDatadescontocomissao() {
        return datadescontocomissao;
    }

    public void setDatadescontocomissao(Date datadescontocomissao) {
        this.datadescontocomissao = datadescontocomissao;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Date getDatapagamento() {
		return datapagamento;
	}

	public void setDatapagamento(Date datapagamento) {
		this.datapagamento = datapagamento;
	}

	public Float getDescontocomissao() {
		return descontocomissao;
	}

	public void setDescontocomissao(Float descontocomissao) {
		this.descontocomissao = descontocomissao;
	}

	public boolean isChildtraining() {
		return childtraining;
	}

	public void setChildtraining(boolean childtraining) {
		this.childtraining = childtraining;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleAuPair != null ? idcontroleAuPair.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controleaupair)) {
            return false;
        }
        Controleaupair other = (Controleaupair) object;
        if ((this.idcontroleAuPair == null && other.idcontroleAuPair != null) || (this.idcontroleAuPair != null && !this.idcontroleAuPair.equals(other.idcontroleAuPair))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleaupair[ idcontroleAuPair=" + idcontroleAuPair + " ]";
    }
    
}
