package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author jizid
 */
@Entity
@Table(name = "invoiceremessa")
public class Invoiceremessa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinvoiceremessa")
    private Integer idinvoiceremessa;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 8)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioCurso;
    @JoinColumn(name = "usuario_idusuario1", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuairoFinanceiro;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "invoiceremessa")
    private List<Invoiceremessainvoice> invoiceremessainvoiceList;
    @Column(name = "finalizada")
    private boolean finalizada;

    public Invoiceremessa() {
    	setFinalizada(false);
    }

    public Invoiceremessa(Integer idinvoiceremessa) {
        this.idinvoiceremessa = idinvoiceremessa;
    }

    public Integer getIdinvoiceremessa() {
        return idinvoiceremessa;
    }

    public void setIdinvoiceremessa(Integer idinvoiceremessa) {
        this.idinvoiceremessa = idinvoiceremessa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    

    public Usuario getUsuarioCurso() {
		return usuarioCurso;
	}

	public void setUsuarioCurso(Usuario usuarioCurso) {
		this.usuarioCurso = usuarioCurso;
	}

	public Usuario getUsuairoFinanceiro() {
		return usuairoFinanceiro;
	}

	public void setUsuairoFinanceiro(Usuario usuairoFinanceiro) {
		this.usuairoFinanceiro = usuairoFinanceiro;
	}

	public List<Invoiceremessainvoice> getInvoiceremessainvoiceList() {
        return invoiceremessainvoiceList;
    }

    public void setInvoiceremessainvoiceList(List<Invoiceremessainvoice> invoiceremessainvoiceList) {
        this.invoiceremessainvoiceList = invoiceremessainvoiceList;
    }

    public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idinvoiceremessa != null ? idinvoiceremessa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Invoiceremessa)) {
            return false;
        }
        Invoiceremessa other = (Invoiceremessa) object;
        if ((this.idinvoiceremessa == null && other.idinvoiceremessa != null) || (this.idinvoiceremessa != null && !this.idinvoiceremessa.equals(other.idinvoiceremessa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Invoiceremessa[ idinvoiceremessa=" + idinvoiceremessa + " ]";
    }
    
}

