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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "paisproduto")
@NamedQueries({ @NamedQuery(name = "Paisproduto.findAll", query = "SELECT p FROM Paisproduto p") })
public class Paisproduto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idpaisproduto")
	private Integer idpaisproduto;
	@Lob
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
	@JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos", updatable=true)
	@ManyToOne(optional = false)
	private Produtos produtos;
	@JoinColumn(name = "pais_idpais", referencedColumnName = "idpais", updatable=true)
	@ManyToOne(optional = false)
	private Pais pais;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "paisproduto") 
	private List<Cidadepaisproduto> cidadepaisList;

	public Paisproduto() {
	}

	public Paisproduto(Integer idpaisproduto) {
		this.idpaisproduto = idpaisproduto;
	}

	public Integer getIdpaisproduto() {
		return idpaisproduto;
	}

	public void setIdpaisproduto(Integer idpaisproduto) {
		this.idpaisproduto = idpaisproduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Cidadepaisproduto> getCidadepaisList() {
		return cidadepaisList;
	}

	public void setCidadepaisList(List<Cidadepaisproduto> cidadepaisList) {
		this.cidadepaisList = cidadepaisList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idpaisproduto != null ? idpaisproduto.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Paisproduto)) {
			return false;
		}
		Paisproduto other = (Paisproduto) object;
		if ((this.idpaisproduto == null && other.idpaisproduto != null)
				|| (this.idpaisproduto != null && !this.idpaisproduto.equals(other.idpaisproduto))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Paisproduto[ idpaisproduto=" + idpaisproduto + " ]";
	}

}
