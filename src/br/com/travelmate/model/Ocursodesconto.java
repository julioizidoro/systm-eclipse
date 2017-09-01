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
 * @author Kamila
 */
@Entity
@Table(name = "ocursodesconto")
@NamedQueries({
    @NamedQuery(name = "Ocursodesconto.findAll", query = "SELECT o FROM Ocursodesconto o")})
public class Ocursodesconto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idocursodesconto")
    private Integer idocursodesconto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valormoedaestrangeira")
    private Float valormoedaestrangeira;
    @Column(name = "valormoedanacional")
    private Float valormoedanacional;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @JoinColumn(name = "ocurso_idocurso", referencedColumnName = "idocurso")
    @ManyToOne(optional = false)
    private Ocurso ocurso;
    @Transient
    private boolean selecionado;
    @Transient
    private boolean taxaTmSelecionado;

    public Ocursodesconto() {
    }

    public Ocursodesconto(Integer idocursodesconto) {
        this.idocursodesconto = idocursodesconto;
    }

    public Integer getIdocursodesconto() {
        return idocursodesconto;
    }

    public void setIdocursodesconto(Integer idocursodesconto) {
        this.idocursodesconto = idocursodesconto;
    }

    public Float getValormoedaestrangeira() {
        return valormoedaestrangeira;
    }

    public void setValormoedaestrangeira(Float valormoedaestrangeira) {
        this.valormoedaestrangeira = valormoedaestrangeira;
    }

    public Float getValormoedanacional() {
        return valormoedanacional;
    }

    public void setValormoedanacional(Float valormoedanacional) {
        this.valormoedanacional = valormoedanacional;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    public Ocurso getOcurso() {
        return ocurso;
    }

    public void setOcurso(Ocurso ocurso) {
        this.ocurso = ocurso;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isTaxaTmSelecionado() {
        return taxaTmSelecionado;
    }

    public void setTaxaTmSelecionado(boolean taxaTmSelecionado) {
        this.taxaTmSelecionado = taxaTmSelecionado;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idocursodesconto != null ? idocursodesconto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ocursodesconto)) {
            return false;
        }
        Ocursodesconto other = (Ocursodesconto) object;
        if ((this.idocursodesconto == null && other.idocursodesconto != null) || (this.idocursodesconto != null && !this.idocursodesconto.equals(other.idocursodesconto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Ocursodesconto[ idocursodesconto=" + idocursodesconto + " ]";
    }
    
}
