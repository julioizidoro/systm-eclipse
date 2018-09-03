/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "orcamento")
public class Orcamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamento")
    private Integer idorcamento;
    @Column(name = "dataCambio")
    @Temporal(TemporalType.DATE)
    private Date dataCambio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorCambio")
    private Float valorCambio;
    @Size(max = 3)
    @Column(name = "cambioAlterado")
    private String cambioAlterado;
    @Column(name = "totalMoedaEstrangeira")
    private Float totalMoedaEstrangeira;
    @Column(name = "totalMoedaNacional")
    private Float totalMoedaNacional;
    @Column(name = "valorbrasil")
    private Float valorbrasil;
    @Column(name = "taxatm")
    private Float taxatm;
    @JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
    @ManyToOne(optional = false)
    private Cambio cambio;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "orcamento")
    private List<Orcamentoprodutosorcamento> orcamentoprodutosorcamentoList;
    
    
    public Orcamento() {
    }

    public Orcamento(Integer idorcamento) {
        this.idorcamento = idorcamento;
    }

    public Integer getIdorcamento() {
        return idorcamento;
    }

    public void setIdorcamento(Integer idorcamento) {
        this.idorcamento = idorcamento;
    }

    public Date getDataCambio() {
        return dataCambio;
    }

    public void setDataCambio(Date dataCambio) {
        this.dataCambio = dataCambio;
    }

    public Float getValorCambio() {
        return valorCambio;
    }

    public void setValorCambio(Float valorCambio) {
        this.valorCambio = valorCambio;
    }

    public String getCambioAlterado() {
        return cambioAlterado;
    }

    public void setCambioAlterado(String cambioAlterado) {
        this.cambioAlterado = cambioAlterado;
    }

    public Float getTotalMoedaEstrangeira() {
        return totalMoedaEstrangeira;
    }

    public void setTotalMoedaEstrangeira(Float totalMoedaEstrangeira) {
        this.totalMoedaEstrangeira = totalMoedaEstrangeira;
    }

    public Float getTotalMoedaNacional() {
        return totalMoedaNacional;
    }

    public void setTotalMoedaNacional(Float totalMoedaNacional) {
        this.totalMoedaNacional = totalMoedaNacional;
    }

    public Float getTaxatm() {
        return taxatm;
    }

    public void setTaxatm(Float taxatm) {
        this.taxatm = taxatm;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Float getValorbrasil() {
		return valorbrasil;
	}

	public void setValorbrasil(Float valorbrasil) {
		this.valorbrasil = valorbrasil;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamento != null ? idorcamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamento)) {
            return false;
        }
        Orcamento other = (Orcamento) object;
        if ((this.idorcamento == null && other.idorcamento != null) || (this.idorcamento != null && !this.idorcamento.equals(other.idorcamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamento[ idorcamento=" + idorcamento + " ]";
    }

    public List<Orcamentoprodutosorcamento> getOrcamentoprodutosorcamentoList() {
        return orcamentoprodutosorcamentoList;
    }

    public void setOrcamentoprodutosorcamentoList(List<Orcamentoprodutosorcamento> orcamentoprodutosorcamentoList) {
        this.orcamentoprodutosorcamentoList = orcamentoprodutosorcamentoList;
    }
    
}
