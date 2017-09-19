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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */

@Entity
@Table(name = "produtos")
public class Produtos implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idprodutos")
	private Integer idprodutos;
	@Size(max = 50)
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "comissaogerente")
	private Double comissaogerente;
	@Column(name = "idgerente")
	private Integer idgerente;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "mostrar")
	private boolean mostrar;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "produtos", fetch = FetchType.LAZY)  
	private List<Fornecedorcidade> fornecedorcidadeList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "produtos")
	private List<Tipoarquivoproduto> tipoarquivoprodutoList;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "produtos")
	private List<Paisproduto> paisprodutoList;

	public Produtos() {
	}

	public Produtos(Integer idprodutos) {
		this.idprodutos = idprodutos;
	}

	public Integer getIdprodutos() {
		return idprodutos;
	}

	public void setIdprodutos(Integer idprodutos) {
		this.idprodutos = idprodutos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getComissaogerente() {
		return comissaogerente;
	}

	public void setComissaogerente(Double comissaogerente) {
		this.comissaogerente = comissaogerente;
	}

	public Integer getIdgerente() {
		return idgerente;
	}

	public void setIdgerente(Integer idgerente) {
		this.idgerente = idgerente;
	}

	public List<Paisproduto> getPaisprodutoList() {
		return paisprodutoList;
	}

	public void setPaisprodutoList(List<Paisproduto> paisprodutoList) {
		this.paisprodutoList = paisprodutoList;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isMostrar() {
		return mostrar;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	public List<Tipoarquivoproduto> getTipoarquivoprodutoList() {
		return tipoarquivoprodutoList;
	}

	public void setTipoarquivoprodutoList(List<Tipoarquivoproduto> tipoarquivoprodutoList) {
		this.tipoarquivoprodutoList = tipoarquivoprodutoList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idprodutos != null ? idprodutos.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Produtos)) {
			return false;
		}
		Produtos other = (Produtos) object;
		if ((this.idprodutos == null && other.idprodutos != null)
				|| (this.idprodutos != null && !this.idprodutos.equals(other.idprodutos))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Produtos[ idprodutos=" + idprodutos + " ]";
	}

	public List<Fornecedorcidade> getFornecedorcidadeList() {
		return fornecedorcidadeList;
	}

	public void setFornecedorcidadeList(List<Fornecedorcidade> fornecedorcidadeList) {
		this.fornecedorcidadeList = fornecedorcidadeList;
	}
}
