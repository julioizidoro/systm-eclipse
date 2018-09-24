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
import javax.validation.constraints.Size;

@Entity
@Table(name = "fonecedorapplication")
public class Fornecedorapplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idfonecedorapplication")
	private Integer idfornecedorapplication;
	@Size(max = 100)
	@Column(name = "nomearquivo")
	private String nomearquivo;
	@JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
	@ManyToOne(optional = false)
	private Fornecedor fornecedor;
	@JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
	@ManyToOne(optional = false)
	private Pais pais;
	@JoinColumn(name = "produtosorcamento_idprodutosorcamento", referencedColumnName = "idprodutosorcamento")
	@ManyToOne(optional = false)
	private Produtosorcamento produtosorcamento;

	public Fornecedorapplication() {
	}

	public Integer getIdfornecedorapplication() {
		return idfornecedorapplication;
	}

	public void setIdfornecedorapplication(Integer idfornecedorapplication) {
		this.idfornecedorapplication = idfornecedorapplication;
	}

	public String getNomearquivo() {
		return nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idfornecedorapplication != null ? idfornecedorapplication.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Fornecedorapplication)) {
			return false;
		}
		Fornecedorapplication other = (Fornecedorapplication) object;
		if ((this.idfornecedorapplication == null && other.idfornecedorapplication != null)
				|| (this.idfornecedorapplication != null
						&& !this.idfornecedorapplication.equals(other.idfornecedorapplication))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Fornecedorapplication[ idfornecedorapplication=" + idfornecedorapplication
				+ " ]";
	}

}
