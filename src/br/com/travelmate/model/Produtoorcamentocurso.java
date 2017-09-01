/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "produtoorcamentocurso")
public class Produtoorcamentocurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutoOrcamentoCurso")
    private Integer idprodutoOrcamentoCurso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorMoedaEstrangeira")
    private Float valorMoedaEstrangeira;
    @Column(name = "valorMoedaNacional")
    private Float valorMoedaNacional;
    @Column(name = "orcamentoCurso_idorcamentoCurso")
    private int orcamentocurso;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosOrcamento;
    @Column(name = "somarvalortotal")
    private boolean somarvalortotal;

    public Produtoorcamentocurso() {
    }

    public Produtoorcamentocurso(Integer idprodutoOrcamentoCurso) {
        this.idprodutoOrcamentoCurso = idprodutoOrcamentoCurso;
    }

    public Integer getIdprodutoOrcamentoCurso() {
        return idprodutoOrcamentoCurso;
    }

    public int getOrcamentocurso() {
        return orcamentocurso;
    }

    public void setOrcamentocurso(int orcamentocurso) {
        this.orcamentocurso = orcamentocurso;
    }
    public void setIdprodutoOrcamentoCurso(Integer idprodutoOrcamentoCurso) {
        this.idprodutoOrcamentoCurso = idprodutoOrcamentoCurso;
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


    public Produtosorcamento getProdutosOrcamento() {
		return produtosOrcamento;
	}

	public void setProdutosOrcamento(Produtosorcamento produtosOrcamento) {
		this.produtosOrcamento = produtosOrcamento;
	}

	
	
	public boolean isSomarvalortotal() {
		return somarvalortotal;
	}

	public void setSomarvalortotal(boolean somarvalortotal) {
		this.somarvalortotal = somarvalortotal;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutoOrcamentoCurso != null ? idprodutoOrcamentoCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtoorcamentocurso)) {
            return false;
        }
        Produtoorcamentocurso other = (Produtoorcamentocurso) object;
        if ((this.idprodutoOrcamentoCurso == null && other.idprodutoOrcamentoCurso != null) || (this.idprodutoOrcamentoCurso != null && !this.idprodutoOrcamentoCurso.equals(other.idprodutoOrcamentoCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Produtoorcamentocurso[ idprodutoOrcamentoCurso=" + idprodutoOrcamentoCurso + " ]";
    }
    
}
