package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ocursosuplemento database table.
 * 
 */
@Entity
public class Ocursosuplemento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idocursosuplemento")
    private Integer idocursosuplemento;
    @JoinColumn(name = "coprodutos_idcoprodutos", referencedColumnName = "idcoprodutos")
    @ManyToOne(optional = false)
    private Coprodutos produtoSuplemento;
    @JoinColumn(name = "valorcoprodutos_idvalorcoprodutos", referencedColumnName = "idvalorcoprodutos")
    @ManyToOne(optional = false)
    private Valorcoprodutos valorcoprodutos;
    @JoinColumn(name = "coprodutos_idcoprodutos1", referencedColumnName = "idcoprodutos")
    @ManyToOne(optional = false)
    private Coprodutos produtoOrigem;
    
    public Ocursosuplemento() {
    }

    public Ocursosuplemento(Integer idocursosuplemento) {
        this.idocursosuplemento = idocursosuplemento;
    }

    public Integer getIdocursosuplemento() {
        return idocursosuplemento;
    }

    public void setIdocursosuplemento(Integer idocursosuplemento) {
        this.idocursosuplemento = idocursosuplemento;
    }

	public Coprodutos getProdutoSuplemento() {
		return produtoSuplemento;
	}

	public void setProdutoSuplemento(Coprodutos produtoSuplemento) {
		this.produtoSuplemento = produtoSuplemento;
	}

	public Valorcoprodutos getValorcoprodutos() {
		return valorcoprodutos;
	}

	public void setValorcoprodutos(Valorcoprodutos valorcoprodutos) {
		this.valorcoprodutos = valorcoprodutos;
	}

	public Coprodutos getProdutoOrigem() {
		return produtoOrigem;
	}

	public void setProdutoOrigem(Coprodutos produtoOrigem) {
		this.produtoOrigem = produtoOrigem;
	}
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idocursosuplemento != null ? idocursosuplemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ocursosuplemento)) {
            return false;
        }
        Ocursosuplemento other = (Ocursosuplemento) object;
        if ((this.idocursosuplemento == null && other.idocursosuplemento != null) || (this.idocursosuplemento != null && !this.idocursosuplemento.equals(other.idocursosuplemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Ocursosuplemento[ idocursosuplemento=" + idocursosuplemento + " ]";
    }

	

}