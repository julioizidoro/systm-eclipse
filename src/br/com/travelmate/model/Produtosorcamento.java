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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "produtosorcamento")
public class Produtosorcamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutosOrcamento")
    private Integer idprodutosOrcamento;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "produtosorcamento")
    private List<Filtroorcamentoproduto> filtroorcamentoprodutoList;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "tipoproduto")
    private String tipoproduto;
    @Size(max = 50)
    @Column(name = "descricaooutralingua")
    private String descricaooutralingua;
    @Column(name = "vincular")
    private Boolean vincular;
    @Column(name = "tipoorcamento")
    private String tipoorcamento;
    @Column(name = "pathway")
    private boolean pathway;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "produtosorcamento")
    private List<Produtoremessa> produtoremessaList;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "produtosorcamento")
    private Produtosorcamentogrupo produtosorcamentogrupo;
    @Transient
    private boolean selecionado;
    @Column(name = "novo")
    private boolean novo;
    @Transient
    private Float valormaximo;
    

    public Produtosorcamento() {
    }

    public Produtosorcamento(Integer idprodutosOrcamento) {
        this.idprodutosOrcamento = idprodutosOrcamento;
    }

    public Integer getIdprodutosOrcamento() {
        return idprodutosOrcamento;
    }

    public void setIdprodutosOrcamento(Integer idprodutosOrcamento) {
        this.idprodutosOrcamento = idprodutosOrcamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Filtroorcamentoproduto> getFiltroorcamentoprodutoList() {
        return filtroorcamentoprodutoList;
    }

    public void setFiltroorcamentoprodutoList(List<Filtroorcamentoproduto> filtroorcamentoprodutoList) {
        this.filtroorcamentoprodutoList = filtroorcamentoprodutoList;
    }

    public String getTipoproduto() {
		return tipoproduto;
	}

	public void setTipoproduto(String tipoproduto) {
		this.tipoproduto = tipoproduto;
	}

	public String getTipoorcamento() {
		return tipoorcamento;
	}

	public void setTipoorcamento(String tipoorcamento) {
		this.tipoorcamento = tipoorcamento;
	}
	public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricaooutralingua() {
        return descricaooutralingua;
    }

    public void setDescricaooutralingua(String descricaooutralingua) {
        this.descricaooutralingua = descricaooutralingua;
    }

    public Boolean getVincular() {
        return vincular;
    }

    public void setVincular(Boolean vincular) {
        this.vincular = vincular;
    }

    public List<Produtoremessa> getProdutoremessaList() {
        return produtoremessaList;
    }

    public void setProdutoremessaList(List<Produtoremessa> produtoremessaList) {
        this.produtoremessaList = produtoremessaList;
    }  
    
	public boolean isPathway() {
		return pathway;
	}

	public void setPathway(boolean pathway) {
		this.pathway = pathway;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public boolean isNovo() {
		return novo;
	}

	public void setNovo(boolean novo) {
		this.novo = novo;
	}

	public Produtosorcamentogrupo getProdutosorcamentogrupo() {
		return produtosorcamentogrupo;
	}

	public void setProdutosorcamentogrupo(Produtosorcamentogrupo produtosorcamentogrupo) {
		this.produtosorcamentogrupo = produtosorcamentogrupo;
	}

	public Float getValormaximo() {
		return valormaximo;
	}

	public void setValormaximo(Float valormaximo) {
		this.valormaximo = valormaximo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutosOrcamento != null ? idprodutosOrcamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtosorcamento)) {
            return false;
        }
        Produtosorcamento other = (Produtosorcamento) object;
        if ((this.idprodutosOrcamento == null && other.idprodutosOrcamento != null) || (this.idprodutosOrcamento != null && !this.idprodutosOrcamento.equals(other.idprodutosOrcamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return "br.com.travelmate.model.Produtosorcamento[ idprodutosOrcamento=" + idprodutosOrcamento + " ]";
    }

    
}
