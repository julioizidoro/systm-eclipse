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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "fornecedorcidade")
public class Fornecedorcidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidade")
    private Integer idfornecedorcidade;
    @Size(max = 50)
    @Column(name = "classificacao")
    private String classificacao;
    @Column(name = "peso")
    private Integer peso;
    @JoinColumn(name = "cidade_idcidade", referencedColumnName = "idcidade")
    @ManyToOne(optional = false)
    private Cidade cidade;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedorcidade", fetch=FetchType.LAZY)
    private List<Valoresseguro> valoresseguroList;
    @Lob
    @Size(max = 16777215)
    @Column(name = "texto")
    private String texto;
    @Size(max = 50)
    @Column(name = "imagem")
    private String imagem;
    @Column(name = "numestrelas")
    private int numestrelas;
    @Column(name = "toptmstar")
    private boolean toptmstar;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "fornecedorcidade")
    private Fornecedorcidadeguia fornecedorcidadeguiaList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedorcidade")
    private List<Fornecedorcidadedepoimento> fornecedorcidadedepoimentoList;
    @Column(name = "ativo")
    private boolean ativo;
    @Column(name = "work")
    private boolean work;
    @Transient
    private boolean selecionado;
    @Transient
    private boolean excluir;
    @Transient 
    private Fornecedorcidadedocs fornecedorcidadedocs;
   
    public Fornecedorcidade() {
    	setExcluir(true);
    	setAtivo(true);
    	setWork(true);
    }

    public Fornecedorcidade(Integer idfornecedorcidade) {
        this.idfornecedorcidade = idfornecedorcidade;
    }

    public Integer getIdfornecedorcidade() {
        return idfornecedorcidade;
    }

    public void setIdfornecedorcidade(Integer idfornecedorcidade) {
        this.idfornecedorcidade = idfornecedorcidade;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Fornecedorcidadeguia getFornecedorcidadeguiaList() {
		return fornecedorcidadeguiaList;
	}

	public void setFornecedorcidadeguiaList(Fornecedorcidadeguia fornecedorcidadeguiaList) {
		this.fornecedorcidadeguiaList = fornecedorcidadeguiaList;
	}

	public List<Fornecedorcidadedepoimento> getFornecedorcidadedepoimentoList() {
		return fornecedorcidadedepoimentoList;
	}

	public void setFornecedorcidadedepoimentoList(List<Fornecedorcidadedepoimento> fornecedorcidadedepoimentoList) {
		this.fornecedorcidadedepoimentoList = fornecedorcidadedepoimentoList;
	}

	public int getNumestrelas() {
		return numestrelas;
	}

	public void setNumestrelas(int numestrelas) {
		this.numestrelas = numestrelas;
	}

	public boolean isToptmstar() {
		return toptmstar;
	}

	public void setToptmstar(boolean toptmstar) {
		this.toptmstar = toptmstar;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public boolean isExcluir() {
		return excluir;
	}

	public void setExcluir(boolean excluir) {
		this.excluir = excluir;
	}

	public Fornecedorcidadedocs getFornecedorcidadedocs() {
		return fornecedorcidadedocs;
	}

	public void setFornecedorcidadedocs(Fornecedorcidadedocs fornecedorcidadedocs) {
		this.fornecedorcidadedocs = fornecedorcidadedocs;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isWork() {
		return work;
	}

	public void setWork(boolean work) {
		this.work = work;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidade != null ? idfornecedorcidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Fornecedorcidade)) {
            return false;
        }
        Fornecedorcidade other = (Fornecedorcidade) object;
        if ((this.idfornecedorcidade == null && other.idfornecedorcidade != null) || (this.idfornecedorcidade != null && !this.idfornecedorcidade.equals(other.idfornecedorcidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorcidade[ idfornecedorcidade=" + idfornecedorcidade + " ]";
    }

	public List<Valoresseguro> getValoresseguroList() {
		return valoresseguroList;
	}

	public void setValoresseguroList(List<Valoresseguro> valoresseguroList) {
		this.valoresseguroList = valoresseguroList;
	}
   
}
