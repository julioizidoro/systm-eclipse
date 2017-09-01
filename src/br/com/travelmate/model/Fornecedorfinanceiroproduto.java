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

/**
 *
 * @author wolverine
 */     
@Entity
@Table(name = "fornecedorfinanceiroproduto")    
public class Fornecedorfinanceiroproduto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idfornecedorfinanceiroproduto")
	private Integer idfornecedorfinanceiroproduto;
	@Column(name = "produto")
	private String produto;
	@JoinColumn(name = "fornecedorfinanceiro_idfornecedorfinanceiro", referencedColumnName = "idfornecedorfinanceiro", updatable = true)
	@ManyToOne(optional = false)
	private Fornecedorfinanceiro fornecedorfinanceiro;

	public Fornecedorfinanceiroproduto() {
	}

	public Integer getIdfornecedorfinanceiroproduto() {
		return idfornecedorfinanceiroproduto;
	}

	public void setIdfornecedorfinanceiroproduto(Integer idfornecedorfinanceiroproduto) {
		this.idfornecedorfinanceiroproduto = idfornecedorfinanceiroproduto;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	} 

	public Fornecedorfinanceiro getFornecedorfinanceiro() {
		return fornecedorfinanceiro;
	}

	public void setFornecedorfinanceiro(Fornecedorfinanceiro fornecedorfinanceiro) {
		this.fornecedorfinanceiro = fornecedorfinanceiro;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idfornecedorfinanceiroproduto != null ? idfornecedorfinanceiroproduto.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Fornecedorfinanceiroproduto)) {
			return false;
		}
		Fornecedorfinanceiroproduto other = (Fornecedorfinanceiroproduto) object;
		if ((this.idfornecedorfinanceiroproduto == null && other.idfornecedorfinanceiroproduto != null)
				|| (this.idfornecedorfinanceiroproduto != null
						&& !this.idfornecedorfinanceiroproduto.equals(other.idfornecedorfinanceiroproduto))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Fornecedorfinanceiroproduto[ idfornecedorfinanceiroproduto="
				+ idfornecedorfinanceiroproduto + " ]";
	}

}