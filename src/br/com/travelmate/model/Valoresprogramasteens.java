/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "valoresprogramasteens")
@NamedQueries({
    @NamedQuery(name = "Valoresprogramasteens.findREFRESH", query = "SELECT v FROM Valoresprogramasteens v")})
public class Valoresprogramasteens implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvaloresprogramasteens")
    private Integer idvaloresprogramasteens;
    @Size(max = 50)
    @Column(name = "inicio")
    private String inicio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valornet")
    private Float valornet;
    @Column(name = "valorgross")
    private Float valorgross;
    @Column(name = "comissaoexpress")
    private Float comissaoexpress;
    @Column(name = "comissaopremium")
    private Float comissaopremium;
    @Size(max = 30)
    @Column(name = "situacao")
    private String situacao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "valoresprogramasteens")
    private List<Programasteens> programasteensList;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;

    public Valoresprogramasteens() {
    }

    public Valoresprogramasteens(Integer idvaloresprogramasteens) {
        this.idvaloresprogramasteens = idvaloresprogramasteens;
    }

    public Integer getIdvaloresprogramasteens() {
        return idvaloresprogramasteens;
    }

    public void setIdvaloresprogramasteens(Integer idvaloresprogramasteens) {
        this.idvaloresprogramasteens = idvaloresprogramasteens;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Float getValornet() {
        return valornet;
    }

    public void setValornet(Float valornet) {
        this.valornet = valornet;
    }

    public Float getValorgross() {
        return valorgross;
    }

    public void setValorgross(Float valorgross) {
        this.valorgross = valorgross;
    }

    public Float getComissaoexpress() {
        return comissaoexpress;
    }

    public void setComissaoexpress(Float comissaoexpress) {
        this.comissaoexpress = comissaoexpress;
    }

    public Float getComissaopremium() {
        return comissaopremium;
    }

    public void setComissaopremium(Float comissaopremium) {
        this.comissaopremium = comissaopremium;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<Programasteens> getProgramasteensList() {
        return programasteensList;
    }

    public void setProgramasteensList(List<Programasteens> programasteensList) {
        this.programasteensList = programasteensList;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    public Moedas getMoedas() {
        return moedas;
    }

    public void setMoedas(Moedas moedas) {
        this.moedas = moedas;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvaloresprogramasteens != null ? idvaloresprogramasteens.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Valoresprogramasteens)) {
            return false;
        }
        Valoresprogramasteens other = (Valoresprogramasteens) object;
        if ((this.idvaloresprogramasteens == null && other.idvaloresprogramasteens != null) || (this.idvaloresprogramasteens != null && !this.idvaloresprogramasteens.equals(other.idvaloresprogramasteens))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Valoresprogramasteens[ idvaloresprogramasteens=" + idvaloresprogramasteens + " ]";
    }
    
}
