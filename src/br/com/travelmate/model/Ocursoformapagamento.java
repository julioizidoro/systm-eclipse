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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "ocursoformapagamento")
public class Ocursoformapagamento implements Serializable {
    public String getObservacao2() {
		return observacao2;
	}

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idocursoformapagamento")
    private Integer idocursoformapagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentualEntrada")
    private Double percentualEntrada;
    @Column(name = "valorEntrada")
    private Float valorEntrada;
    @Column(name = "percentualSaldo")
    private Double percentualSaldo;
    @Column(name = "valorSaldo")
    private Float valorSaldo;
    @Column(name = "valorparcela")
    private Float valorparcela;
    @Column(name = "numeroparcela")
    private int numeroparcela;
    @Column(name = "selecionado")
    private boolean selecionado;
    @Column(name = "observacao1")
    private String observacao1;
    @Column(name = "observacao2")
    private String observacao2;
    @Column(name = "titulo")
    private String titulo;
    @JoinColumn(name = "ocurso_idocurso", referencedColumnName = "idocurso")
    @ManyToOne(optional = false)
    private Ocurso ocurso;
    @Column(name = "vista")
    private Float vista;
    @Column(name = "parcelasboleto")
    private Integer parcelasboleto;
    @Column(name = "entradaboletovalor")
    private Float entradaboletovalor;
    @Column(name = "entradaboeltopercentual")
    private Float entradaboeltopercentual;
    @Column(name = "saldoboletovalor")
    private Float saldoboletovalor;
    @Column(name = "saldoboletopercentual")
    private Float saldoboletopercentual;
    @Column(name = "parcelaboletovalor")
    private Float parcelaboletovalor;
    @Column(name = "parcelascartao")
    private Integer parcelascartao;
    @Column(name = "entradacartaovalor")
    private Float entradacartaovalor;
    @Column(name = "entradacartaopercentual")
    private Float entradacartaopercentual;
    @Column(name = "saldocartaovalor")
    private Float saldocartaovalor;
    @Column(name = "saldocartaopercentual")
    private Float saldocartaopercentual;
    @Column(name = "parcelacartaovalor")
    private Float parcelacartaovalor;
    @Column(name = "parcelasfinanciamento")
    private Integer parcelasfinanciamento;
    @Column(name = "entradafinanciamentovalor")
    private Float entradafinanciamentovalor;
    @Column(name = "entradafinanciamentopercentual")
    private Float entradafinanciamentopercentual;
    @Column(name = "saldofinanciamentovalor")
    private Float saldofinanciamentovalor;
    @Column(name = "saldofinanciamentopercentual")
    private Float saldofinanciamentopercentual;
    @Column(name = "parcelafinanciamentovalor")
    private Float parcelafinanciamentovalor;
    

    public Ocursoformapagamento() {
    }

    public Ocursoformapagamento(Integer idocursoformapagamento) {
        this.idocursoformapagamento = idocursoformapagamento;
    }

    public Integer getIdocursoformapagamento() {
        return idocursoformapagamento;
    }

    
	public Double getPercentualEntrada() {
		return percentualEntrada;
	}

	public void setPercentualEntrada(Double percentualEntrada) {
		this.percentualEntrada = percentualEntrada;
	}

	public Float getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(Float valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public Double getPercentualSaldo() {
		return percentualSaldo;
	}

	public void setPercentualSaldo(Double percentualSaldo) {
		this.percentualSaldo = percentualSaldo;
	}

	public Float getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Float valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public Float getValorparcela() {
		return valorparcela;
	}

	public void setValorparcela(Float valorparcela) {
		this.valorparcela = valorparcela;
	}

	public int getNumeroparcela() {
		return numeroparcela;
	}

	public void setNumeroparcela(int numeroparcela) {
		this.numeroparcela = numeroparcela;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getObservacao1() {
		return observacao1;
	}

	public void setObservacao1(String observacao1) {
		this.observacao1 = observacao1;
	}

	public String isObservacao2() {
		return observacao2;
	}

	public void setObservacao2(String observacao2) {
		this.observacao2 = observacao2;
	}

	
	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public void setIdocursoformapagamento(Integer idocursoformapagamento) {
		this.idocursoformapagamento = idocursoformapagamento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idocursoformapagamento != null ? idocursoformapagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ocursoformapagamento)) {
            return false;
        }
        Ocursoformapagamento other = (Ocursoformapagamento) object;
        if ((this.idocursoformapagamento == null && other.idocursoformapagamento != null) || (this.idocursoformapagamento != null && !this.idocursoformapagamento.equals(other.idocursoformapagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Ocursoformapagamento[ idocursoformapagamento=" + idocursoformapagamento + " ]";
    }

	public Float getVista() {
		return vista;
	}

	public void setVista(Float vista) {
		this.vista = vista;
	}

	public Integer getParcelasboleto() {
		return parcelasboleto;
	}

	public void setParcelasboleto(Integer parcelasboleto) {
		this.parcelasboleto = parcelasboleto;
	}

	public Float getEntradaboletovalor() {
		return entradaboletovalor;
	}

	public void setEntradaboletovalor(Float entradaboletovalor) {
		this.entradaboletovalor = entradaboletovalor;
	}

	public Float getEntradaboeltopercentual() {
		return entradaboeltopercentual;
	}

	public void setEntradaboeltopercentual(Float entradaboeltopercentual) {
		this.entradaboeltopercentual = entradaboeltopercentual;
	}

	public Float getSaldoboletovalor() {
		return saldoboletovalor;
	}

	public void setSaldoboletovalor(Float saldoboletovalor) {
		this.saldoboletovalor = saldoboletovalor;
	}

	public Float getSaldoboletopercentual() {
		return saldoboletopercentual;
	}

	public void setSaldoboletopercentual(Float saldoboletopercentual) {
		this.saldoboletopercentual = saldoboletopercentual;
	}

	public Integer getParcelascartao() {
		return parcelascartao;
	}

	public void setParcelascartao(Integer parcelascartao) {
		this.parcelascartao = parcelascartao;
	}

	public Float getEntradacartaovalor() {
		return entradacartaovalor;
	}

	public void setEntradacartaovalor(Float entradacartaovalor) {
		this.entradacartaovalor = entradacartaovalor;
	}

	public Float getEntradacartaopercentual() {
		return entradacartaopercentual;
	}

	public void setEntradacartaopercentual(Float entradacartaopercentual) {
		this.entradacartaopercentual = entradacartaopercentual;
	}

	public Float getSaldocartaovalor() {
		return saldocartaovalor;
	}

	public void setSaldocartaovalor(Float saldocartaovalor) {
		this.saldocartaovalor = saldocartaovalor;
	}

	public Float getSaldocartaopercentual() {
		return saldocartaopercentual;
	}

	public void setSaldocartaopercentual(Float saldocartaopercentual) {
		this.saldocartaopercentual = saldocartaopercentual;
	}

	public Integer getParcelasfinanciamento() {
		return parcelasfinanciamento;
	}

	public void setParcelasfinanciamento(Integer parcelasfinanciamento) {
		this.parcelasfinanciamento = parcelasfinanciamento;
	}

	public Float getEntradafinanciamentovalor() {
		return entradafinanciamentovalor;
	}

	public void setEntradafinanciamentovalor(Float entradafinanciamentovalor) {
		this.entradafinanciamentovalor = entradafinanciamentovalor;
	}

	public Float getEntradafinanciamentopercentual() {
		return entradafinanciamentopercentual;
	}

	public void setEntradafinanciamentopercentual(Float entradafinanciamentopercentual) {
		this.entradafinanciamentopercentual = entradafinanciamentopercentual;
	}

	public Float getSaldofinanciamentovalor() {
		return saldofinanciamentovalor;
	}

	public void setSaldofinanciamentovalor(Float saldofinanciamentovalor) {
		this.saldofinanciamentovalor = saldofinanciamentovalor;
	}

	public Float getSaldofinanciamentopercentual() {
		return saldofinanciamentopercentual;
	}

	public void setSaldofinanciamentopercentual(Float saldofinanciamentopercentual) {
		this.saldofinanciamentopercentual = saldofinanciamentopercentual;
	}

	public Float getParcelaboletovalor() {
		return parcelaboletovalor;
	}

	public void setParcelaboletovalor(Float parcelaboletovalor) {
		this.parcelaboletovalor = parcelaboletovalor;
	}

	public Float getParcelacartaovalor() {
		return parcelacartaovalor;
	}

	public void setParcelacartaovalor(Float parcelacartaovalor) {
		this.parcelacartaovalor = parcelacartaovalor;
	}

	public Float getParcelafinanciamentovalor() {
		return parcelafinanciamentovalor;
	}

	public void setParcelafinanciamentovalor(Float parcelafinanciamentovalor) {
		this.parcelafinanciamentovalor = parcelafinanciamentovalor;
	}
    
    
    
}
