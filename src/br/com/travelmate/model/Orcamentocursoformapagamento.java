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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "orcamentocursoformapagamento")
@NamedQueries({
    @NamedQuery(name = "Orcamentocursoformapagamento.findAll", query = "SELECT o FROM Orcamentocursoformapagamento o")})
public class Orcamentocursoformapagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentoCursoFormaPagamento")
    private Integer idorcamentoCursoFormaPagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "aVista")
    private Float aVista;
    @Column(name = "percentualEntrada2")
    private Double percentualEntrada2;
    @Column(name = "valorEntrada2")
    private Float valorEntrada2;
    @Column(name = "percentualSaldo2")
    private Double percentualSaldo2;
    @Column(name = "valorSaldo2")
    private Float valorSaldo2;
    @Column(name = "percentualEntrada3")
    private Double percentualEntrada3;
    @Column(name = "valorEntrada3")
    private Float valorEntrada3;
    @Column(name = "percentualSaldo3")
    private Double percentualSaldo3;
    @Column(name = "valorSaldo3")
    private Float valorSaldo3;
    @Column(name = "numeroParcelasFinanciamento")
    private Integer numeroParcelasFinanciamento;
    @Column(name = "finaciamento")
    private Float finaciamento;
    @Column(name = "numeroParcelas02")
    private Integer numeroParcelas02;
    @Column(name = "valorParcela02")
    private Float valorParcela02;
    @Column(name = "numeroParcelas03")
    private Integer numeroParcelas03;
    @Column(name = "valorParcela03")
    private Float valorParcela03;
    @Column(name = "percentualEntrada4")
    private Double percentualEntrada4;
    @Column(name = "percentualSaldo4")
    private Double percentualSaldo4;
    @Column(name = "valorEntrada4")
    private Float valorEntrada4;
    @Column(name = "valorSaldo4")
    private Float valorSaldo4;
    @JoinColumn(name = "orcamentoCurso_idorcamentoCurso", referencedColumnName = "idorcamentoCurso")
    @ManyToOne(optional = false)
    private Orcamentocurso orcamentocurso;
    @Transient
    private boolean selecionado2;
    @Transient
    private boolean selecionado3;
    @Transient
    private boolean selecionado4;
    

    public Orcamentocursoformapagamento() {
    }

    public Orcamentocursoformapagamento(Integer idorcamentoCursoFormaPagamento) {
        this.idorcamentoCursoFormaPagamento = idorcamentoCursoFormaPagamento;
    }

    public Integer getIdorcamentoCursoFormaPagamento() {
        return idorcamentoCursoFormaPagamento;
    }

    public void setIdorcamentoCursoFormaPagamento(Integer idorcamentoCursoFormaPagamento) {
        this.idorcamentoCursoFormaPagamento = idorcamentoCursoFormaPagamento;
    }

    public Float getAVista() {
        return aVista;
    }

    public void setAVista(Float aVista) {
        this.aVista = aVista;
    }

    public Double getPercentualEntrada2() {
        return percentualEntrada2;
    }

    public void setPercentualEntrada2(Double percentualEntrada2) {
        this.percentualEntrada2 = percentualEntrada2;
    }

    public Float getValorEntrada2() {
        return valorEntrada2;
    }

    public void setValorEntrada2(Float valorEntrada2) {
        this.valorEntrada2 = valorEntrada2;
    }

    public Double getPercentualSaldo2() {
        return percentualSaldo2;
    }

    public void setPercentualSaldo2(Double percentualSaldo2) {
        this.percentualSaldo2 = percentualSaldo2;
    }

    public Float getValorSaldo2() {
        return valorSaldo2;
    }

    public void setValorSaldo2(Float valorSaldo2) {
        this.valorSaldo2 = valorSaldo2;
    }

    public Double getPercentualEntrada3() {
        return percentualEntrada3;
    }

    public void setPercentualEntrada3(Double percentualEntrada3) {
        this.percentualEntrada3 = percentualEntrada3;
    }

    public Float getValorEntrada3() {
        return valorEntrada3;
    }

    public void setValorEntrada3(Float valorEntrada3) {
        this.valorEntrada3 = valorEntrada3;
    }

    public Double getPercentualSaldo3() {
        return percentualSaldo3;
    }

    public void setPercentualSaldo3(Double percentualSaldo3) {
        this.percentualSaldo3 = percentualSaldo3;
    }

    public Float getValorSaldo3() {
        return valorSaldo3;
    }

    public void setValorSaldo3(Float valorSaldo3) {
        this.valorSaldo3 = valorSaldo3;
    }

    public Integer getNumeroParcelasFinanciamento() {
        return numeroParcelasFinanciamento;
    }

    public void setNumeroParcelasFinanciamento(Integer numeroParcelasFinanciamento) {
        this.numeroParcelasFinanciamento = numeroParcelasFinanciamento;
    }

    public Float getFinaciamento() {
        return finaciamento;
    }

    public void setFinaciamento(Float finaciamento) {
        this.finaciamento = finaciamento;
    }

    public Integer getNumeroParcelas02() {
        return numeroParcelas02;
    }

    public void setNumeroParcelas02(Integer numeroParcelas02) {
        this.numeroParcelas02 = numeroParcelas02;
    }

    public Float getValorParcela02() {
        return valorParcela02;
    }

    public void setValorParcela02(Float valorParcela02) {
        this.valorParcela02 = valorParcela02;
    }

    public Integer getNumeroParcelas03() {
        return numeroParcelas03;
    }

    public void setNumeroParcelas03(Integer numeroParcelas03) {
        this.numeroParcelas03 = numeroParcelas03;
    }

    public Float getValorParcela03() {
        return valorParcela03;
    }

    public void setValorParcela03(Float valorParcela03) {
        this.valorParcela03 = valorParcela03;
    }

    public Orcamentocurso getOrcamentocurso() {
        return orcamentocurso;
    }

    public void setOrcamentocurso(Orcamentocurso orcamentocurso) {
        this.orcamentocurso = orcamentocurso;
    }

    public boolean isSelecionado2() {
		return selecionado2;
	}

	public void setSelecionado2(boolean selecionado2) {
		this.selecionado2 = selecionado2;
	}

	public boolean isSelecionado3() {
		return selecionado3;
	}

	public void setSelecionado3(boolean selecionado3) {
		this.selecionado3 = selecionado3;
	}

	public boolean isSelecionado4() {
		return selecionado4;
	}

	public void setSelecionado4(boolean selecionado4) {
		this.selecionado4 = selecionado4;
	}
	
	

	public Float getaVista() {
		return aVista;
	}

	public void setaVista(Float aVista) {
		this.aVista = aVista;
	}

	public Double getPercentualEntrada4() {
		return percentualEntrada4;
	}

	public void setPercentualEntrada4(Double percentualEntrada4) {
		this.percentualEntrada4 = percentualEntrada4;
	}
	
	

	public Double getPercentualSaldo4() {
		return percentualSaldo4;
	}

	public void setPercentualSaldo4(Double percentualSaldo4) {
		this.percentualSaldo4 = percentualSaldo4;
	}

	public Float getValorEntrada4() {
		return valorEntrada4;
	}

	public void setValorEntrada4(Float valorEntrada4) {
		this.valorEntrada4 = valorEntrada4;
	}

	public Float getValorSaldo4() {
		return valorSaldo4;
	}

	public void setValorSaldo4(Float valorSaldo4) {
		this.valorSaldo4 = valorSaldo4;
	}
	
	

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentoCursoFormaPagamento != null ? idorcamentoCursoFormaPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentocursoformapagamento)) {
            return false;
        }
        Orcamentocursoformapagamento other = (Orcamentocursoformapagamento) object;
        if ((this.idorcamentoCursoFormaPagamento == null && other.idorcamentoCursoFormaPagamento != null) || (this.idorcamentoCursoFormaPagamento != null && !this.idorcamentoCursoFormaPagamento.equals(other.idorcamentoCursoFormaPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentocursoformapagamento[ idorcamentoCursoFormaPagamento=" + idorcamentoCursoFormaPagamento + " ]";
    }
    
}
