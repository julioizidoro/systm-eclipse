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
	@Table(name = "fornecedorpais")
	public class Fornecedorpais implements Serializable {
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "idfornecedorPais")
	    private Integer idfornecedorPais;
	    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
	    @ManyToOne(optional = false)
	    private Fornecedor fornecedor;
	    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
	    @ManyToOne(optional = false)
	    private Moedas moedas;
	    @JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
	    @ManyToOne(optional = false)
	    private Pais pais;

	    public Fornecedorpais() {
	    }

	    public Fornecedorpais(Integer idfornecedorPais) {
	        this.idfornecedorPais = idfornecedorPais;
	    }

	    public Integer getIdfornecedorPais() {
	        return idfornecedorPais;
	    }

	    public void setIdfornecedorPais(Integer idfornecedorPais) {
	        this.idfornecedorPais = idfornecedorPais;
	    }

	    public Fornecedor getFornecedor() {
	        return fornecedor;
	    }

	    public void setFornecedor(Fornecedor fornecedor) {
	        this.fornecedor = fornecedor;
	    }

	    public Moedas getMoedas() {
	        return moedas;
	    }

	    public void setMoedas(Moedas moedas) {
	        this.moedas = moedas;
	    }

	    public Pais getPais() {
	        return pais;
	    }

	    public void setPais(Pais pais) {
	        this.pais = pais;
	    }

	    @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idfornecedorPais != null ? idfornecedorPais.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        if (!(object instanceof Fornecedorpais)) {
	            return false;
	        }
	        Fornecedorpais other = (Fornecedorpais) object;
	        if ((this.idfornecedorPais == null && other.idfornecedorPais != null) || (this.idfornecedorPais != null && !this.idfornecedorPais.equals(other.idfornecedorPais))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "br.com.travelmate.model.Fornecedorpais[ idfornecedorPais=" + idfornecedorPais + " ]";
	    }
	    
	}

