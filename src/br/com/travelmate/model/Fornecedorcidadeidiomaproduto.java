package br.com.travelmate.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the fornecedorcidadeidiomaproduto database table.
 * 
 */
@Entity
@Table(name="fornecedorcidadeidiomaproduto")
public class Fornecedorcidadeidiomaproduto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorcidadeidiomaproduto")
    private Integer idfornecedorcidadeidiomaproduto;
	@JoinColumn(name = "fornecedorcidadeidioma_idfornecedorcidadeidioma", referencedColumnName = "idfornecedorcidadeidioma")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidioma fornecedorcidadeidioma;
	@JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedorcidadeidiomaproduto")
    private List<Fornecedorcidadeidiomaprodutodata> fornecedorcidadeidiomaprodutodataList;
	
	

	public Fornecedorcidadeidiomaproduto() {
	}

	public Integer getIdfornecedorcidadeidiomaproduto() {
		return idfornecedorcidadeidiomaproduto;
	}

	public void setIdfornecedorcidadeidiomaproduto(Integer idfornecedorcidadeidiomaproduto) {
		this.idfornecedorcidadeidiomaproduto = idfornecedorcidadeidiomaproduto;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public List<Fornecedorcidadeidiomaprodutodata> getFornecedorcidadeidiomaprodutodataList() {
		return fornecedorcidadeidiomaprodutodataList;
	}

	public void setFornecedorcidadeidiomaprodutodataList(
			List<Fornecedorcidadeidiomaprodutodata> fornecedorcidadeidiomaprodutodataList) {
		this.fornecedorcidadeidiomaprodutodataList = fornecedorcidadeidiomaprodutodataList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorcidadeidiomaproduto != null ? idfornecedorcidadeidiomaproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorcidadeidioma)) {
            return false;
        }
        Fornecedorcidadeidiomaproduto other = (Fornecedorcidadeidiomaproduto) object;
        if ((this.idfornecedorcidadeidiomaproduto == null && other.idfornecedorcidadeidiomaproduto != null) || (this.idfornecedorcidadeidiomaproduto != null && !this.idfornecedorcidadeidiomaproduto.equals(other.idfornecedorcidadeidiomaproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+this.idfornecedorcidadeidiomaproduto;
    }
}