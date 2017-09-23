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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "fornecedorcomissaocurso")
public class Fornecedorcomissaocurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcomissao")
    private Integer idfornecedorcomissao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percmatriz")
    private Double percmatriz;
    @Column(name = "percloja")
    private Double percloja;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedorcomissaocurso")
    private List<Fornecedorcomissaocursoproduto> fornecedorcomissaocursoprodutoList;
    @JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
    @ManyToOne(optional = false)
    private Pais pais;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;

    public Fornecedorcomissaocurso() {
    }

    public Fornecedorcomissaocurso(Integer idfornecedorcomissao) {
        this.idfornecedorcomissao = idfornecedorcomissao;
    }

    public Integer getIdfornecedorcomissao() {
        return idfornecedorcomissao;
    }

    public void setIdfornecedorcomissao(Integer idfornecedorcomissao) {
        this.idfornecedorcomissao = idfornecedorcomissao;
    }

    public Double getPercmatriz() {
        return percmatriz;
    }

    public void setPercmatriz(Double percmatriz) {
        this.percmatriz = percmatriz;
    }

    public Double getPercloja() {
        return percloja;
    }

    public void setPercloja(Double percloja) {
        this.percloja = percloja;
    }

    public List<Fornecedorcomissaocursoproduto> getFornecedorcomissaocursoprodutoList() {
        return fornecedorcomissaocursoprodutoList;
    }

    public void setFornecedorcomissaocursoprodutoList(List<Fornecedorcomissaocursoproduto> fornecedorcomissaocursoprodutoList) {
        this.fornecedorcomissaocursoprodutoList = fornecedorcomissaocursoprodutoList;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcomissao != null ? idfornecedorcomissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorcomissaocurso)) {
            return false;
        }
        Fornecedorcomissaocurso other = (Fornecedorcomissaocurso) object;
        if ((this.idfornecedorcomissao == null && other.idfornecedorcomissao != null) || (this.idfornecedorcomissao != null && !this.idfornecedorcomissao.equals(other.idfornecedorcomissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcomissaocurso[ idfornecedorcomissao=" + idfornecedorcomissao + " ]";
    }
    
}
