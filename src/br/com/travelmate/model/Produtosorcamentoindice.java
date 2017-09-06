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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "produtosorcamentoindice")
public class Produtosorcamentoindice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)   
    @Column(name = "idprodutosorcamentoindice")
    private Integer idprodutosorcamentoindice;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtosorcamentoindice")
    private List<Produtosorcamentogrupo> produtosorcamentogrupoList;

    public Produtosorcamentoindice() {
    	
    } 
    
    public Integer getIdprodutosorcamentoindice() {
		return idprodutosorcamentoindice;
	} 

	public void setIdprodutosorcamentoindice(Integer idprodutosorcamentoindice) {
		this.idprodutosorcamentoindice = idprodutosorcamentoindice;
	} 

	public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	public List<Produtosorcamentogrupo> getProdutosorcamentogrupoList() {
		return produtosorcamentogrupoList;
	}

	public void setProdutosorcamentogrupoList(List<Produtosorcamentogrupo> produtosorcamentogrupoList) {
		this.produtosorcamentogrupoList = produtosorcamentogrupoList;
	}  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutosorcamentoindice != null ? idprodutosorcamentoindice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtosorcamentoindice)) {
            return false;
        }
        Produtosorcamentoindice other = (Produtosorcamentoindice) object;
        if ((this.idprodutosorcamentoindice == null && other.idprodutosorcamentoindice != null) || (this.idprodutosorcamentoindice != null && !this.idprodutosorcamentoindice.equals(other.idprodutosorcamentoindice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Produtosorcamentoindice[ idprodutosorcamentoindice=" + idprodutosorcamentoindice + " ]";
    }

}

