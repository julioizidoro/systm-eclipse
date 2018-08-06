/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "fornecedorcidadeidioma")
public class Fornecedorcidadeidioma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidadeidioma")
    private Integer idfornecedorcidadeidioma;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "idioma_ididioma", referencedColumnName = "ididioma")
    @ManyToOne(optional = false)
    private Idioma idioma;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "habilitada")
    private boolean habilitada;
    @Column(name = "acomodacaoindependente")
    private boolean acomodacaoindependente;
    @Column(name = "maioridade")
    private int maioridade;
    @Column(name = "acomodacao")
    private boolean acomodacao;
    // Transient utilizados para relatório de fornecedor cidade idioma
    @Transient
    private String possuiAcomodacao;
    @Transient
    private String anoTarifario;
    @Transient
    private String tipoOrcamento;
    
    
    public Fornecedorcidadeidioma() {
    	habilitada=true;
    }

    public Fornecedorcidadeidioma(Integer idfornecedorcidadeidioma) {
        this.idfornecedorcidadeidioma = idfornecedorcidadeidioma;
    }

    public Integer getIdfornecedorcidadeidioma() {
        return idfornecedorcidadeidioma;
    }

    public void setIdfornecedorcidadeidioma(Integer idfornecedorcidadeidioma) {
        this.idfornecedorcidadeidioma = idfornecedorcidadeidioma;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	
	
	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}

	public boolean isAcomodacaoindependente() {
		return acomodacaoindependente;
	}

	public void setAcomodacaoindependente(boolean acomodacaoindependente) {
		this.acomodacaoindependente = acomodacaoindependente;
	}

	public int getMaioridade() {
		return maioridade;
	}

	public void setMaioridade(int maioridade) {
		this.maioridade = maioridade;
	}

	public boolean isAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(boolean acomodacao) {
		this.acomodacao = acomodacao;
	}

	public String getPossuiAcomodacao() {
		return possuiAcomodacao;
	}

	public void setPossuiAcomodacao(String possuiAcomodacao) {
		this.possuiAcomodacao = possuiAcomodacao;
	}

	public String getAnoTarifario() {
		return anoTarifario;
	}

	public void setAnoTarifario(String anoTarifario) {
		this.anoTarifario = anoTarifario;
	}

	public String getTipoOrcamento() {
		return tipoOrcamento;
	}

	public void setTipoOrcamento(String tipoOrcamento) {
		this.tipoOrcamento = tipoOrcamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidadeidioma != null ? idfornecedorcidadeidioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorcidadeidioma)) {
            return false;
        }
        Fornecedorcidadeidioma other = (Fornecedorcidadeidioma) object;
        if ((this.idfornecedorcidadeidioma == null && other.idfornecedorcidadeidioma != null) || (this.idfornecedorcidadeidioma != null && !this.idfornecedorcidadeidioma.equals(other.idfornecedorcidadeidioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcidadeidioma[ idfornecedorcidadeidioma=" + idfornecedorcidadeidioma + " ]";
    }

    
}
