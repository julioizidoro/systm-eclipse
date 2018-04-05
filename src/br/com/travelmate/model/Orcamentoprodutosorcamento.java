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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "orcamentoprodutosorcamento")
public class Orcamentoprodutosorcamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentoProdutosOrcamento")
    private Integer idorcamentoProdutosOrcamento;
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorMoedaEstrangeira")
    private Float valorMoedaEstrangeira;
    @Column(name = "valorMoedaNacional")
    private Float valorMoedaNacional;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "orcamento_idorcamento", referencedColumnName = "idorcamento")
    @ManyToOne(optional = false)
    private Orcamento orcamento;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
    @Transient
    private boolean podeExcluir;
    @Transient
    private int linha;

    public Orcamentoprodutosorcamento() {
    		podeExcluir=true;
    		linha = 0;
    }

    public Orcamentoprodutosorcamento(Integer idorcamentoProdutosOrcamento) {
        this.idorcamentoProdutosOrcamento = idorcamentoProdutosOrcamento;
    }

    public Integer getIdorcamentoProdutosOrcamento() {
        return idorcamentoProdutosOrcamento;
    }

    public void setIdorcamentoProdutosOrcamento(Integer idorcamentoProdutosOrcamento) {
        this.idorcamentoProdutosOrcamento = idorcamentoProdutosOrcamento;
    }

    public Float getValorMoedaEstrangeira() {
        return valorMoedaEstrangeira;
    }

    public void setValorMoedaEstrangeira(Float valorMoedaEstrangeira) {
        this.valorMoedaEstrangeira = valorMoedaEstrangeira;
    }

    public Float getValorMoedaNacional() {
        return valorMoedaNacional;
    }

    public void setValorMoedaNacional(Float valorMoedaNacional) {
        this.valorMoedaNacional = valorMoedaNacional;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isPodeExcluir() {
		return podeExcluir;
	}

	public void setPodeExcluir(boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentoProdutosOrcamento != null ? idorcamentoProdutosOrcamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentoprodutosorcamento)) {
            return false;
        }
        Orcamentoprodutosorcamento other = (Orcamentoprodutosorcamento) object;
        if ((this.idorcamentoProdutosOrcamento == null && other.idorcamentoProdutosOrcamento != null) || (this.idorcamentoProdutosOrcamento != null && !this.idorcamentoProdutosOrcamento.equals(other.idorcamentoProdutosOrcamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentoprodutosorcamento[ idorcamentoProdutosOrcamento=" + idorcamentoProdutosOrcamento + " ]";
    }
    
}
