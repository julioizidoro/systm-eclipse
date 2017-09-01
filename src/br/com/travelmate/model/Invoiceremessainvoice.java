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

/**
 *
 * @author jizid
 */
@Entity
@Table(name = "invoiceremessainvoice")
public class Invoiceremessainvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinvoiceremessainvoice")
    private Integer idinvoiceremessainvoice;
    @JoinColumn(name = "invoiceremessa_idinvoiceremessa", referencedColumnName = "idinvoiceremessa")
    @ManyToOne(optional = false)
    private Invoiceremessa invoiceremessa;
    @JoinColumn(name = "invoices_idinvoices", referencedColumnName = "idinvoices")
    @OneToOne(optional = false)
    private Invoice invoice;

    public Invoiceremessainvoice() {
    }

    public Invoiceremessainvoice(Integer idinvoiceremessainvoice) {
        this.idinvoiceremessainvoice = idinvoiceremessainvoice;
    }

    public Integer getIdinvoiceremessainvoice() {
        return idinvoiceremessainvoice;
    }

    public void setIdinvoiceremessainvoice(Integer idinvoiceremessainvoice) {
        this.idinvoiceremessainvoice = idinvoiceremessainvoice;
    }

    public Invoiceremessa getInvoiceremessa() {
        return invoiceremessa;
    }

    public void setInvoiceremessa(Invoiceremessa invoiceremessa) {
        this.invoiceremessa = invoiceremessa;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinvoiceremessainvoice != null ? idinvoiceremessainvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Invoiceremessainvoice)) {
            return false;
        }
        Invoiceremessainvoice other = (Invoiceremessainvoice) object;
        if ((this.idinvoiceremessainvoice == null && other.idinvoiceremessainvoice != null) || (this.idinvoiceremessainvoice != null && !this.idinvoiceremessainvoice.equals(other.idinvoiceremessainvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Invoiceremessainvoice[ idinvoiceremessainvoice=" + idinvoiceremessainvoice + " ]";
    }
    
}

