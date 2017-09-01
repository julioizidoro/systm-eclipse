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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "produtostrainee")
public class Produtostrainee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutostrainee")
    private Integer idprodutostrainee;
    @Column(name = "numerosemanas")
    private Integer numerosemanas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorapplication")
    private Float valorapplication;
    @Column(name = "valorprograma")
    private Float valorprograma;
    @Column(name = "valorvisto")
    private Float valorvisto;
    @Column(name = "valorseguro")
    private Float valorseguro;
    @Column(name = "valortotal")
    private Float valortotal;
    @Size(max = 100)
    @Column(name = "tipoestagio")
    private String tipoestagio;
    @JoinColumn(name = "produtoapplication", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtoapplication;
    @JoinColumn(name = "produtoprograma", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtoprograma;
    @JoinColumn(name = "produtovisto", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtovisto;
    @JoinColumn(name = "produtoseguro", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtoseguro;
    @JoinColumn(name = "valorestrainee_idvalorestrainee", referencedColumnName = "idvalorestrainee")
    @OneToOne(optional = false)
    private Valorestrainee valorestrainee;

    public Produtostrainee() {
    }

    public Produtostrainee(Integer idprodutostrainee) {
        this.idprodutostrainee = idprodutostrainee;
    }

    public Integer getIdprodutostrainee() {
        return idprodutostrainee;
    }

    public void setIdprodutostrainee(Integer idprodutostrainee) {
        this.idprodutostrainee = idprodutostrainee;
    }

    public Integer getNumerosemanas() {
        return numerosemanas;
    }

    public void setNumerosemanas(Integer numerosemanas) {
        this.numerosemanas = numerosemanas;
    }

    public Float getValorapplication() {
        return valorapplication;
    }

    public void setValorapplication(Float valorapplication) {
        this.valorapplication = valorapplication;
    }

    public Float getValorprograma() {
        return valorprograma;
    }

    public void setValorprograma(Float valorprograma) {
        this.valorprograma = valorprograma;
    }

    public Float getValorvisto() {
        return valorvisto;
    }

    public void setValorvisto(Float valorvisto) {
        this.valorvisto = valorvisto;
    }

    public Float getValorseguro() {
        return valorseguro;
    }

    public void setValorseguro(Float valorseguro) {
        this.valorseguro = valorseguro;
    }

    public Float getValortotal() {
        return valortotal;
    }

    public void setValortotal(Float valortotal) {
        this.valortotal = valortotal;
    }

    public String getTipoestagio() {
        return tipoestagio;
    }

    public void setTipoestagio(String tipoestagio) {
        this.tipoestagio = tipoestagio;
    }

    public Produtosorcamento getProdutoapplication() {
		return produtoapplication;
	}

	public void setProdutoapplication(Produtosorcamento produtoapplication) {
		this.produtoapplication = produtoapplication;
	}

	public Produtosorcamento getProdutoprograma() {
		return produtoprograma;
	}

	public void setProdutoprograma(Produtosorcamento produtoprograma) {
		this.produtoprograma = produtoprograma;
	}

	public Produtosorcamento getProdutovisto() {
		return produtovisto;
	}

	public void setProdutovisto(Produtosorcamento produtovisto) {
		this.produtovisto = produtovisto;
	}

	public Produtosorcamento getProdutoseguro() {
		return produtoseguro;
	}

	public void setProdutoseguro(Produtosorcamento produtoseguro) {
		this.produtoseguro = produtoseguro;
	}

	public Valorestrainee getValorestrainee() {
        return valorestrainee;
    }

    public void setValorestrainee(Valorestrainee valorestrainee) {
        this.valorestrainee = valorestrainee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutostrainee != null ? idprodutostrainee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtostrainee)) {
            return false;
        }
        Produtostrainee other = (Produtostrainee) object;
        if ((this.idprodutostrainee == null && other.idprodutostrainee != null) || (this.idprodutostrainee != null && !this.idprodutostrainee.equals(other.idprodutostrainee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Produtostrainee[ idprodutostrainee=" + idprodutostrainee + " ]";
    }
    
}
