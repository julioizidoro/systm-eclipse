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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "controleseguro")
public class Controleseguro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleSeguro")
    private Integer idcontroleSeguro;
    @Size(max = 3)
    @Column(name = "envioVoucher")
    private String envioVoucher;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacao")
    private String observacao;
    @Size(max = 50)
    @Column(name = "finalizado")
    private String finalizado;
    @Size(max = 20)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "dataemissao")
    @Temporal(TemporalType.DATE)
    private Date dataemissao;
    @JoinColumn(name = "seguroviagem_idseguroViagem", referencedColumnName = "idseguroViagem")
    @OneToOne(optional = false)
    private Seguroviagem seguroviagem;
    @Transient
    private boolean selecionado;

    public Controleseguro() {
    }

    public Controleseguro(Integer idcontroleSeguro) {
        this.idcontroleSeguro = idcontroleSeguro;
    }

    public Integer getIdcontroleSeguro() {
        return idcontroleSeguro;
    }

    public void setIdcontroleSeguro(Integer idcontroleSeguro) {
        this.idcontroleSeguro = idcontroleSeguro;
    }

    public String getEnvioVoucher() {
        return envioVoucher;
    }

    public void setEnvioVoucher(String envioVoucher) {
        this.envioVoucher = envioVoucher;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }


    public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}

	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}

	public Date getDataemissao() {
		return dataemissao;
	}

	public void setDataemissao(Date dataemissao) {
		this.dataemissao = dataemissao;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleSeguro != null ? idcontroleSeguro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controleseguro)) {
            return false;
        }
        Controleseguro other = (Controleseguro) object;
        if ((this.idcontroleSeguro == null && other.idcontroleSeguro != null) || (this.idcontroleSeguro != null && !this.idcontroleSeguro.equals(other.idcontroleSeguro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleseguro[ idcontroleSeguro=" + idcontroleSeguro + " ]";
    }
    
}
