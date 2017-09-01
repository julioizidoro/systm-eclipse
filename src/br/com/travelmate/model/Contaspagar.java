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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "contaspagar")
public class Contaspagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontaspagar")
    private Integer idcontaspagar;
    @Column(name = "dataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Column(name = "datavencimento")
    @Temporal(TemporalType.DATE)
    private Date datavencimento;
    @Size(max = 200)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorsaida")
    private Float valorsaida;
    @Column(name = "valorentrada")
    private Float valorentrada;
    @Size(max = 7)
    @Column(name = "competencia")
    private String competencia;
    @Column(name = "datacompensacao")
    @Temporal(TemporalType.DATE)
    private Date datacompensacao;
    @JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @JoinColumn(name = "planoconta_idplanoconta", referencedColumnName = "idplanoconta")
    @ManyToOne(optional = false)
    private Planoconta planoconta;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;
    @Column(name = "idcontasreceber")
    private int idcontasreceber;
    @Transient
    private Boolean conciliada;
    @Column(name = "conciliacao")
    private String conciliacao;
    @Transient
    private boolean selecionado;
    

    public Contaspagar() {
    	conciliada = false;
    }

    public Contaspagar(Integer idcontaspagar) {
        this.idcontaspagar = idcontaspagar;
    }

    public Integer getIdcontaspagar() {
        return idcontaspagar;
    }

    public void setIdcontaspagar(Integer idcontaspagar) {
        this.idcontaspagar = idcontaspagar;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    
    public Date getDatavencimento() {
        return datavencimento;
    }

    public void setDatavencimento(Date datavencimento) {
        this.datavencimento = datavencimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValorsaida() {
        return valorsaida;
    }

    public void setValorsaida(Float valorsaida) {
        this.valorsaida = valorsaida;
    }

    public Float getValorentrada() {
        return valorentrada;
    }

    public void setValorentrada(Float valorentrada) {
        this.valorentrada = valorentrada;
    }


    

    public int getIdcontasreceber() {
		return idcontasreceber;
	}

	public void setIdcontasreceber(int idcontasreceber) {
		this.idcontasreceber = idcontasreceber;
	}

	public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public Date getDatacompensacao() {
        return datacompensacao;
    }

    public void setDatacompensacao(Date datacompensacao) {
        this.datacompensacao = datacompensacao;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    
    

    public String getConciliacao() {
		return conciliacao;
	}

	public void setConciliacao(String conciliacao) {
		this.conciliacao = conciliacao;
	}

	public Boolean getConciliada() {
		return conciliada;
	}

	public void setConciliada(Boolean conciliada) {
		this.conciliada = conciliada;
	}
	
	public Boolean getSelecionado() {
        return selecionado;
    }
	
	public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
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
        hash += (idcontaspagar != null ? idcontaspagar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Contaspagar)) {
            return false;
        }
        Contaspagar other = (Contaspagar) object;
        if ((this.idcontaspagar == null && other.idcontaspagar != null) || (this.idcontaspagar != null && !this.idcontaspagar.equals(other.idcontaspagar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Contaspagar[ idcontaspagar=" + idcontaspagar + " ]";
    }
    
}
