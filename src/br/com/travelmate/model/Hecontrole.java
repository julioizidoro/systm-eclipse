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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
/**
 * @author julioizidoro
 *
 */
@Entity
@Table(name = "hecontrole")
public class Hecontrole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhecontrole")
    private Integer idhecontrole;
    @Column(name = "tipovenda")
    private String tipovenda;
    @Column(name = "situacaoaplicacao")
    private String situacaoaplicacao;
    @Column(name = "datafichafinalizada")
    @Temporal(TemporalType.DATE)
    private Date datafichafinalizada;
    @Column(name = "dataaplicacaoenviada")
    @Temporal(TemporalType.DATE)
    private Date dataaplicacaoenviada;
    @Size(max = 7)
    @Column(name = "iniciocurso")
    private String iniciocurso;
    @Size(max = 100)
    @Column(name = "parceiro01")
    private String parceiro01;
    @Size(max = 100)
    @Column(name = "parceiro02")
    private String parceiro02;
    @Size(max = 100)
    @Column(name = "parceiro03")
    private String parceiro03;
    @Size(max = 3)
    @Column(name = "pathway")
    private String pathway;
    @Column(name = "impresso")
    private boolean impresso;
    @Column(name = "valorcomissao")
    private Float valorcomissao;  
    @Column(name = "dataembarque")
    @Temporal(TemporalType.DATE)
    private Date dataembarque;
    @Column(name = "comissaosolicitada")
    @Temporal(TemporalType.DATE)
    private Date comissaosolicitada;
    @Column(name = "comissaorecebida")
    @Temporal(TemporalType.DATE)
    private Date comissaorecebida;
    @Column(name = "vistoemitido")
    private boolean vistoemitido;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    
    public Hecontrole() {
    	
    }

	public Integer getIdhecontrole() {
		return idhecontrole;
	}

	public void setIdhecontrole(Integer idhecontrole) {
		this.idhecontrole = idhecontrole;
	}

	public String getTipovenda() {
		return tipovenda;
	}

	public void setTipovenda(String tipovenda) {
		this.tipovenda = tipovenda;
	}

	public String getSituacaoaplicacao() {
		return situacaoaplicacao;
	}

	public void setSituacaoaplicacao(String situacaoaplicacao) {
		this.situacaoaplicacao = situacaoaplicacao;
	}

	public Date getDatafichafinalizada() {
		return datafichafinalizada;
	}

	public void setDatafichafinalizada(Date datafichafinalizada) {
		this.datafichafinalizada = datafichafinalizada;
	}

	public Date getDataaplicacaoenviada() {
		return dataaplicacaoenviada;
	}

	public void setDataaplicacaoenviada(Date dataaplicacaoenviada) {
		this.dataaplicacaoenviada = dataaplicacaoenviada;
	}

	public String getIniciocurso() {
		return iniciocurso;
	}

	public void setIniciocurso(String iniciocurso) {
		this.iniciocurso = iniciocurso;
	}

	public String getParceiro01() {
		return parceiro01;
	}

	public void setParceiro01(String parceiro01) {
		this.parceiro01 = parceiro01;
	}

	public String getParceiro02() {
		return parceiro02;
	}

	public void setParceiro02(String parceiro02) {
		this.parceiro02 = parceiro02;
	}

	public String getParceiro03() {
		return parceiro03;
	}

	public void setParceiro03(String parceiro03) {
		this.parceiro03 = parceiro03;
	}

	public String getPathway() {
		return pathway;
	}

	public void setPathway(String pathway) {
		this.pathway = pathway;
	}

	public boolean isImpresso() {
		return impresso;
	}

	public void setImpresso(boolean impresso) {
		this.impresso = impresso;
	}

	public Float getValorcomissao() {
		return valorcomissao;
	}

	public void setValorcomissao(Float valorcomissao) {
		this.valorcomissao = valorcomissao;
	}

	public Date getDataembarque() {
		return dataembarque;
	}

	public void setDataembarque(Date dataembarque) {
		this.dataembarque = dataembarque;
	}

	public Date getComissaosolicitada() {
		return comissaosolicitada;
	}

	public void setComissaosolicitada(Date comissaosolicitada) {
		this.comissaosolicitada = comissaosolicitada;
	}

	public Date getComissaorecebida() {
		return comissaorecebida;
	}

	public void setComissaorecebida(Date comissaorecebida) {
		this.comissaorecebida = comissaorecebida;
	}

	public boolean isVistoemitido() {
		return vistoemitido;
	}

	public void setVistoemitido(boolean vistoemitido) {
		this.vistoemitido = vistoemitido;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idhecontrole == null) ? 0 : idhecontrole.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hecontrole other = (Hecontrole) obj;
		if (idhecontrole == null) {
			if (other.idhecontrole != null)
				return false;
		} else if (!idhecontrole.equals(other.idhecontrole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hecontrole [idhecontrole=" + idhecontrole + "]";
	}
    
}
