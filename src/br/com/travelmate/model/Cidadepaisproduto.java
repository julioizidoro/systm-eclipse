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
@Table(name = "cidadepaisproduto")
public class Cidadepaisproduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcidadepaisproduto")
    private Integer idcidadepaisproduto;
    @JoinColumn(name = "cidade_idcidade", referencedColumnName = "idcidade", updatable=true)
    @ManyToOne(optional = false)
    private Cidade cidade;
    @JoinColumn(name = "paisproduto_idpaisproduto", referencedColumnName = "idpaisproduto", updatable=true)
    @ManyToOne(optional = false)
    private Paisproduto paisproduto;

    public Cidadepaisproduto() {
    }

    public Cidadepaisproduto(Integer idcidadepaisproduto) {
        this.idcidadepaisproduto = idcidadepaisproduto;
    }

    public Integer getIdcidadepaisproduto() {
        return idcidadepaisproduto;
    }

    public void setIdcidadepaisproduto(Integer idcidadepaisproduto) {
        this.idcidadepaisproduto = idcidadepaisproduto;
    }

    

    

    public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Paisproduto getPaisproduto() {
		return paisproduto;
	}

	public void setPaisproduto(Paisproduto paisproduto) {
		this.paisproduto = paisproduto;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcidadepaisproduto != null ? idcidadepaisproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cidadepaisproduto)) {
            return false;
        }
        Cidadepaisproduto other = (Cidadepaisproduto) object;
        if ((this.idcidadepaisproduto == null && other.idcidadepaisproduto != null) || (this.idcidadepaisproduto != null && !this.idcidadepaisproduto.equals(other.idcidadepaisproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Cidadepaisproduto[ idcidadepaisproduto=" + idcidadepaisproduto + " ]";
    }
    
}
