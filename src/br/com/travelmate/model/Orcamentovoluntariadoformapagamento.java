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
@Table(name = "orcamentovoluntariadoformapagamento")
public class Orcamentovoluntariadoformapagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentovoluntariadoformapagamento")
    private Integer idorcamentovoluntariadoformapagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "avista")
    private Float avista;
    @Column(name = "percentualentradaboleto")
    private Float percentualentradaboleto;
    @Column(name = "valorentradaboleto")
    private Float valorentradaboleto;
    @Column(name = "numparcelasboleto")
    private int numparcelasboleto;
    @Column(name = "percentualsaldoboleto")
    private Float percentualsaldoboleto;
    @Column(name = "valorsaldoboleto")
    private Float valorsaldoboleto;
    @Column(name = "valorparcelaboleto")
    private Float valorparcelaboleto;
    @Column(name = "percentualentradacartao")
    private Float percentualentradacartao;
    @Column(name = "valorentradacartao")
    private Float valorentradacartao;
    @Column(name = "numparcelascartao")
    private int numparcelascartao;
    @Column(name = "percentualsaldocartao")
    private Float percentualsaldocartao;
    @Column(name = "valorsaldocartao")
    private Float valorsaldocartao;
    @Column(name = "valorparcelacartao")
    private Float valorparcelacartao;
    @Column(name = "percentualentradafinanciamento")
    private Float percentualentradafinanciamento;
    @Column(name = "valorentradafinanciamento")
    private Float valorentradafinanciamento;
    @Column(name = "nparcelasfinanciamento")
    private int nparcelasfinanciamento;
    @Column(name = "percentualsaldofinanciamento")
    private Float percentualsaldofinanciamento;
    @Column(name = "valorsaldofinanciamento")
    private Float valorsaldofinanciamento;
    @Column(name = "valorparcelafinanciamento")
    private Float valorparcelafinanciamento;
    @JoinColumn(name = "orcamentoprojetovoluntariado_idorcamentoprojetovoluntariado", referencedColumnName = "idorcamentoprojetovoluntariado")
    @ManyToOne(optional = false)
    private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado;
    @Transient
    private boolean selecionado;

    public Orcamentovoluntariadoformapagamento() {
    }

    public Orcamentovoluntariadoformapagamento(Integer idorcamentovoluntariadoformapagamento) {
        this.idorcamentovoluntariadoformapagamento = idorcamentovoluntariadoformapagamento;
    }

    public Integer getIdorcamentovoluntariadoformapagamento() {
        return idorcamentovoluntariadoformapagamento;
    }

    public void setIdorcamentovoluntariadoformapagamento(Integer idorcamentovoluntariadoformapagamento) {
        this.idorcamentovoluntariadoformapagamento = idorcamentovoluntariadoformapagamento;
    }

    public Float getAvista() {
        return avista;
    }

    public void setAvista(Float avista) {
        this.avista = avista;
    }

    public Float getPercentualentradaboleto() {
        return percentualentradaboleto;
    }

    public void setPercentualentradaboleto(Float percentualentradaboleto) {
        this.percentualentradaboleto = percentualentradaboleto;
    }
 

    public Float getPercentualsaldoboleto() {
        return percentualsaldoboleto;
    }

    public void setPercentualsaldoboleto(Float percentualsaldoboleto) {
        this.percentualsaldoboleto = percentualsaldoboleto;
    }

    public Float getValorsaldoboleto() {
        return valorsaldoboleto;
    }

    public void setValorsaldoboleto(Float valorsaldoboleto) {
        this.valorsaldoboleto = valorsaldoboleto;
    }

    public Float getValorparcelaboleto() {
        return valorparcelaboleto;
    }

    public void setValorparcelaboleto(Float valorparcelaboleto) {
        this.valorparcelaboleto = valorparcelaboleto;
    }

    public Float getPercentualentradacartao() {
        return percentualentradacartao;
    }

    public void setPercentualentradacartao(Float percentualentradacartao) {
        this.percentualentradacartao = percentualentradacartao;
    }

    public Float getValorentradacartao() {
        return valorentradacartao;
    }

    public void setValorentradacartao(Float valorentradacartao) {
        this.valorentradacartao = valorentradacartao;
    } 

    public Float getPercentualsaldocartao() {
        return percentualsaldocartao;
    }

    public void setPercentualsaldocartao(Float percentualsaldocartao) {
        this.percentualsaldocartao = percentualsaldocartao;
    }

    public Float getValorsaldocartao() {
        return valorsaldocartao;
    }

    public void setValorsaldocartao(Float valorsaldocartao) {
        this.valorsaldocartao = valorsaldocartao;
    }

    public Float getValorparcelacartao() {
        return valorparcelacartao;
    }

    public void setValorparcelacartao(Float valorparcelacartao) {
        this.valorparcelacartao = valorparcelacartao;
    }

    public Float getPercentualentradafinanciamento() {
        return percentualentradafinanciamento;
    }

    public void setPercentualentradafinanciamento(Float percentualentradafinanciamento) {
        this.percentualentradafinanciamento = percentualentradafinanciamento;
    }

    public Float getValorentradafinanciamento() {
        return valorentradafinanciamento;
    }

    public void setValorentradafinanciamento(Float valorentradafinanciamento) {
        this.valorentradafinanciamento = valorentradafinanciamento;
    } 
    
    public Float getPercentualsaldofinanciamento() {
        return percentualsaldofinanciamento;
    }

    public void setPercentualsaldofinanciamento(Float percentualsaldofinanciamento) {
        this.percentualsaldofinanciamento = percentualsaldofinanciamento;
    }

    public Float getValorsaldofinanciamento() {
        return valorsaldofinanciamento;
    }

    public void setValorsaldofinanciamento(Float valorsaldofinanciamento) {
        this.valorsaldofinanciamento = valorsaldofinanciamento;
    }

    public Float getValorparcelafinanciamento() {
        return valorparcelafinanciamento;
    }

    public void setValorparcelafinanciamento(Float valorparcelafinanciamento) {
        this.valorparcelafinanciamento = valorparcelafinanciamento;
    }

    public Orcamentoprojetovoluntariado getOrcamentoprojetovoluntariado() {
        return orcamentoprojetovoluntariado;
    }

    public void setOrcamentoprojetovoluntariado(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
        this.orcamentoprojetovoluntariado = orcamentoprojetovoluntariado;
    }

    public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public Float getValorentradaboleto() {
		return valorentradaboleto;
	}

	public void setValorentradaboleto(Float valorentradaboleto) {
		this.valorentradaboleto = valorentradaboleto;
	}

	public int getNumparcelasboleto() {
		return numparcelasboleto;
	}

	public void setNumparcelasboleto(int numparcelasboleto) {
		this.numparcelasboleto = numparcelasboleto;
	}

	public int getNumparcelascartao() {
		return numparcelascartao;
	}

	public void setNumparcelascartao(int numparcelascartao) {
		this.numparcelascartao = numparcelascartao;
	}

	public int getNparcelasfinanciamento() {
		return nparcelasfinanciamento;
	}

	public void setNparcelasfinanciamento(int nparcelasfinanciamento) {
		this.nparcelasfinanciamento = nparcelasfinanciamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentovoluntariadoformapagamento != null ? idorcamentovoluntariadoformapagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentovoluntariadoformapagamento)) {
            return false;
        }
        Orcamentovoluntariadoformapagamento other = (Orcamentovoluntariadoformapagamento) object;
        if ((this.idorcamentovoluntariadoformapagamento == null && other.idorcamentovoluntariadoformapagamento != null) || (this.idorcamentovoluntariadoformapagamento != null && !this.idorcamentovoluntariadoformapagamento.equals(other.idorcamentovoluntariadoformapagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentovoluntariadoformapagamento[ idorcamentovoluntariadoformapagamento=" + idorcamentovoluntariadoformapagamento + " ]";
    }
    
}

