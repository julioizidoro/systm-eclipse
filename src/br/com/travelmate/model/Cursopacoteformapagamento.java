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

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "cursopacoteformapagamento")
public class Cursopacoteformapagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcursopacoteformapagamento")
    private Integer idcursopacoteformapagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "entradaboleto")
    private Float entradaboleto;
    @Column(name = "valorentradaboleto")
    private Float valorentradaboleto;
    @Column(name = "numeroparcelasboleto")
    private Integer numeroparcelasboleto;
    @Column(name = "saldoboleto")
    private Float saldoboleto;
    @Column(name = "valorsaldoboleto")
    private Float valorsaldoboleto;
    @Column(name = "valorparcelaboleto")
    private Float valorparcelaboleto;
    @Column(name = "entradacartao")
    private Float entradacartao;
    @Column(name = "valorentradacartao")
    private Float valorentradacartao;
    @Column(name = "numeroparcelascartao")
    private Integer numeroparcelascartao;
    @Column(name = "saldocartao")
    private Float saldocartao;
    @Column(name = "valorsaldocartao")
    private Float valorsaldocartao;
    @Column(name = "valorparcelacartao")
    private Float valorparcelacartao;
    @Column(name = "entradafinanciamento")
    private Float entradafinanciamento;
    @Column(name = "valorentradafinanciamento")
    private Float valorentradafinanciamento;
    @Column(name = "nparcelasfinanciamento")
    private Float nparcelasfinanciamento;
    @Column(name = "saldofinanciamento")
    private Float saldofinanciamento;
    @Column(name = "valorsaldofinanciamento")
    private Float valorsaldofinanciamento;
    @Column(name = "valorparcelafinanciamento")
    private Float valorparcelafinanciamento;
    @JoinColumn(name = "cursospacote_idcursospacote", referencedColumnName = "idcursospacote")
    @ManyToOne(optional = false)
    private Cursospacote cursospacote;

    public Cursopacoteformapagamento() {
        entradaboleto = 0.0f;
        valorentradaboleto = 0.0f;
        saldoboleto = 0.0f;
        valorsaldoboleto = 0.0f;
        valorparcelaboleto = 0.0f;
        entradacartao = 0.0f;
        valorentradacartao = 0.0f;
        saldocartao = 0.0f;
        valorsaldocartao = 0.0f;
        valorparcelacartao = 0.0f;
        entradafinanciamento = 0.0f;
        valorentradafinanciamento = 0.0f;
        nparcelasfinanciamento = 0.0f;
        saldofinanciamento = 0.0f;
        valorsaldofinanciamento = 0.0f;
        valorparcelafinanciamento = 0.0f;
    	
    }

    public Cursopacoteformapagamento(Integer idcursopacoteformapagamento) {
        this.idcursopacoteformapagamento = idcursopacoteformapagamento;
    }

    public Integer getIdcursopacoteformapagamento() {
        return idcursopacoteformapagamento;
    }

    public void setIdcursopacoteformapagamento(Integer idcursopacoteformapagamento) {
        this.idcursopacoteformapagamento = idcursopacoteformapagamento;
    }

    public Float getEntradaboleto() {
        return entradaboleto;
    }

    public void setEntradaboleto(Float entradaboleto) {
        this.entradaboleto = entradaboleto;
    }

    public Float getValorentradaboleto() {
        return valorentradaboleto;
    }

    public void setValorentradaboleto(Float valorentradaboleto) {
        this.valorentradaboleto = valorentradaboleto;
    }

    public Integer getNumeroparcelasboleto() {
        return numeroparcelasboleto;
    }

    public void setNumeroparcelasboleto(Integer numeroparcelasboleto) {
        this.numeroparcelasboleto = numeroparcelasboleto;
    }

    public Float getSaldoboleto() {
        return saldoboleto;
    }

    public void setSaldoboleto(Float saldoboleto) {
        this.saldoboleto = saldoboleto;
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

    public Float getEntradacartao() {
        return entradacartao;
    }

    public void setEntradacartao(Float entradacartao) {
        this.entradacartao = entradacartao;
    }

    public Float getValorentradacartao() {
        return valorentradacartao;
    }

    public void setValorentradacartao(Float valorentradacartao) {
        this.valorentradacartao = valorentradacartao;
    }

    public Integer getNumeroparcelascartao() {
        return numeroparcelascartao;
    }

    public void setNumeroparcelascartao(Integer numeroparcelascartao) {
        this.numeroparcelascartao = numeroparcelascartao;
    }

    public Float getSaldocartao() {
        return saldocartao;
    }

    public void setSaldocartao(Float saldocartao) {
        this.saldocartao = saldocartao;
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

    public Float getEntradafinanciamento() {
        return entradafinanciamento;
    }

    public void setEntradafinanciamento(Float entradafinanciamento) {
        this.entradafinanciamento = entradafinanciamento;
    }

    public Float getValorentradafinanciamento() {
        return valorentradafinanciamento;
    }

    public void setValorentradafinanciamento(Float valorentradafinanciamento) {
        this.valorentradafinanciamento = valorentradafinanciamento;
    }

    public Float getNparcelasfinanciamento() {
        return nparcelasfinanciamento;
    }

    public void setNparcelasfinanciamento(Float nparcelasfinanciamento) {
        this.nparcelasfinanciamento = nparcelasfinanciamento;
    }

    
    public Float getSaldofinanciamento() {
		return saldofinanciamento;
	}

	public void setSaldofinanciamento(Float saldofinanciamento) {
		this.saldofinanciamento = saldofinanciamento;
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

    public Cursospacote getCursospacote() {
        return cursospacote;
    }

    public void setCursospacote(Cursospacote cursospacote) {
        this.cursospacote = cursospacote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcursopacoteformapagamento != null ? idcursopacoteformapagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cursopacoteformapagamento)) {
            return false;
        }
        Cursopacoteformapagamento other = (Cursopacoteformapagamento) object;
        if ((this.idcursopacoteformapagamento == null && other.idcursopacoteformapagamento != null) || (this.idcursopacoteformapagamento != null && !this.idcursopacoteformapagamento.equals(other.idcursopacoteformapagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cursopacoteformapagamento[ idcursopacoteformapagamento=" + idcursopacoteformapagamento + " ]";
    }
    
}
