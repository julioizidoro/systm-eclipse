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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "faturafranquias")
public class Faturafranquias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfaturafranquias")
    private Integer idfaturafranquias;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pagomatriz")
    private Float pagomatriz;
    @Column(name = "liquidopagar")
    private Float liquidopagar;
    @Column(name = "totalprodutos")
    private Float totalprodutos;
    @Column(name = "percentualcomissao")
    private Float percentualcomissao;
    @Column(name = "fatura")
    private boolean fatura;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "apagarfatura")
    private boolean apagarfatura;
    @JoinColumn(name = "vendascomissao_idvendascomissao", referencedColumnName = "idvendascomissao", updatable=true)
    @OneToOne(optional = false)
    private Vendascomissao vendascomissao;
    @Transient
    private boolean selecionado;

    public Faturafranquias() {
    }

    public Faturafranquias(Integer idfaturafranquias) {
        this.idfaturafranquias = idfaturafranquias;
    }

    public Integer getIdfaturafranquias() {
        return idfaturafranquias;
    }

    public void setIdfaturafranquias(Integer idfaturafranquias) {
        this.idfaturafranquias = idfaturafranquias;
    }

    public Float getPagomatriz() {
        return pagomatriz;
    }

    public void setPagomatriz(Float pagomatriz) {
        this.pagomatriz = pagomatriz;
    }

    public Float getLiquidopagar() {
        return liquidopagar;
    }

    public void setLiquidopagar(Float liquidopagar) {
        this.liquidopagar = liquidopagar;
    }

    public Vendascomissao getVendascomissao() {
        return vendascomissao;
    }

    public void setVendascomissao(Vendascomissao vendascomissao) {
        this.vendascomissao = vendascomissao;
    }

    public Float getTotalprodutos() {
		return totalprodutos;
	}

	public void setTotalprodutos(Float totalprodutos) {
		this.totalprodutos = totalprodutos;
	}

	public Float getPercentualcomissao() {
		return percentualcomissao;
	}

	public void setPercentualcomissao(Float percentualcomissao) {
		this.percentualcomissao = percentualcomissao;
	}

	public boolean isFatura() {
		return fatura;
	}

	public void setFatura(boolean fatura) {
		this.fatura = fatura;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public boolean isApagarfatura() {
		return apagarfatura;
	}

	public void setApagarfatura(boolean apagarfatura) {
		this.apagarfatura = apagarfatura;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfaturafranquias != null ? idfaturafranquias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faturafranquias)) {
            return false;
        }
        Faturafranquias other = (Faturafranquias) object;
        if ((this.idfaturafranquias == null && other.idfaturafranquias != null) || (this.idfaturafranquias != null && !this.idfaturafranquias.equals(other.idfaturafranquias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Faturafranquias[ idfaturafranquias=" + idfaturafranquias + " ]";
    }
    
}
