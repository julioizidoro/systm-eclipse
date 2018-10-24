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
import javax.persistence.Transient;
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
    @Column(name = "situacaoaplicacao")
    private String situacaoaplicacao;
    @Column(name = "datafichafinalizada")
    @Temporal(TemporalType.DATE)
    private Date datafichafinalizada;
    @Column(name = "dataaplicacaoenviada")
    @Temporal(TemporalType.DATE)
    private Date dataaplicacaoenviada;
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
    @Column(name = "observacoes")
    private String observacoes;
    @JoinColumn(name = "he_idhe", referencedColumnName = "idhe")
    @ManyToOne(optional = false)
    private He he;
    @Transient
    private String parceiro;
    @Transient
    private String pathway;
    @Transient
    private String tipo;
    @Transient
    private String iconeFormImpresso;
    @Transient
    private String acomodacao;
    @Transient
    private String pais;
    @Transient
    private String cidade;
    @Transient
    private Date inicioPrograma;
    
    public Hecontrole() {
    	
    }

	public Integer getIdhecontrole() {
		return idhecontrole;
	}

	public void setIdhecontrole(Integer idhecontrole) {
		this.idhecontrole = idhecontrole;
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

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public He getHe() {
		return he;
	}

	public void setHe(He he) {
		this.he = he;
	}

	public String getParceiro() {
		return parceiro;
	}

	public void setParceiro(String parceiro) {
		this.parceiro = parceiro;
	}

	public String getPathway() {
		return pathway;
	}

	public void setPathway(String pathway) {
		this.pathway = pathway;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIconeFormImpresso() {
		return iconeFormImpresso;
	}

	public void setIconeFormImpresso(String iconeFormImpresso) {
		this.iconeFormImpresso = iconeFormImpresso;
	}

	public String getAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(String acomodacao) {
		this.acomodacao = acomodacao;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Date getInicioPrograma() {
		return inicioPrograma;
	}

	public void setInicioPrograma(Date inicioPrograma) {
		this.inicioPrograma = inicioPrograma;
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
