/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable; 

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedor")
    private Integer idfornecedor;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "logo")
    private String logo;
    @Column(name = "habilitarorcamento")
    private boolean habilitarorcamento;
    @Column(name = "site")
    private String site;
    @Column(name = "anotarifario")
    private int anotarifario;
    @Column(name = "anotarifariocurso")
    private int anotarifariocurso;
    @Transient
    private boolean selecionado;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "fornecedor")
    private Fornecedorfinanceiro fornecedorfinanceiro;
    

    
    public Fornecedor() {
    }

    public Fornecedor(Integer idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    public Integer getIdfornecedor() {
        return idfornecedor;
    }

    public void setIdfornecedor(Integer idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    

    public int getAnotarifariocurso() {
		return anotarifariocurso;
	}

	public void setAnotarifariocurso(int anotarifariocurso) {
		this.anotarifariocurso = anotarifariocurso;
	}

	public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
    
    public boolean isHabilitarorcamento() {
		return habilitarorcamento;
	}

	public void setHabilitarorcamento(boolean habilitarorcamento) {
		this.habilitarorcamento = habilitarorcamento;
	}

	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public int getAnotarifario() {
		return anotarifario;
	}

	public void setAnotarifario(int anotarifario) {
		this.anotarifario = anotarifario;
	}

	public Fornecedorfinanceiro getFornecedorfinanceiro() {
		return fornecedorfinanceiro;
	}

	public void setFornecedorfinanceiro(Fornecedorfinanceiro fornecedorfinanceiro) {
		this.fornecedorfinanceiro = fornecedorfinanceiro;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedor != null ? idfornecedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        if ((this.idfornecedor == null && other.idfornecedor != null) || (this.idfornecedor != null && !this.idfornecedor.equals(other.idfornecedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedor[ idfornecedor=" + idfornecedor + " ]";
    }


}
