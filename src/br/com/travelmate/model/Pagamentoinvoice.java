package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pagamentoinvoice")
public class Pagamentoinvoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpagamentoinvoice")
    private Integer idpagamentoinvoice;
    @Column(name = "datapagamento")
    @Temporal(TemporalType.DATE)
    private Date datapagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorpago")
    private Float valorpago;
    @Column(name = "taxa")
    private Float taxa;
    @Column(name = "iof")
    private Float iof;
    @Column(name = "cambiopagamento")
    private Float cambiopagamento;
    @Column(name = "ganhoperda")
    private Float ganhoperda;
    @Size(max = 20)
    @Column(name = "banco")
    private String banco;
    @Size(max = 50)
    @Column(name = "outros")
    private String outros;
    @Column(name = "datacomprovante")
    @Temporal(TemporalType.DATE)
    private Date datacomprovante;
    @Size(max = 100)
    @Column(name = "nomearquivo")
    private String nomearquivo;
    @Column(name = "tipopagamento")
    private String tipopagamento;
	@JoinColumn(name = "invoices_idinvoices", referencedColumnName = "idinvoices")
    @ManyToOne(optional = false)
    private Invoice invoice;

    public Pagamentoinvoice() {
    }

    public Pagamentoinvoice(Integer idpagamentoinvoice) {
        this.idpagamentoinvoice = idpagamentoinvoice;
    }

    public Integer getIdpagamentoinvoice() {
        return idpagamentoinvoice;
    }

    public void setIdpagamentoinvoice(Integer idpagamentoinvoice) {
        this.idpagamentoinvoice = idpagamentoinvoice;
    }

    public Date getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(Date datapagamento) {
        this.datapagamento = datapagamento;
    }

    public Float getValorpago() {
        return valorpago;
    }

    public void setValorpago(Float valorpago) {
        this.valorpago = valorpago;
    }

    public Float getTaxa() {
        return taxa;
    }

    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }

    public Float getIof() {
		return iof;
	}

	public void setIof(Float iof) {
		this.iof = iof;
	}

	public Invoice getInvoices() {
		return invoice;
	}

	public Float getCambiopagamento() {
        return cambiopagamento;
    }

    public void setCambiopagamento(Float cambiopagamento) {
        this.cambiopagamento = cambiopagamento;
    }

    public Float getGanhoperda() {
        return ganhoperda;
    }

    public void setGanhoperda(Float ganhoperda) {
        this.ganhoperda = ganhoperda;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    public Date getDatacomprovante() {
        return datacomprovante;
    }

    public void setDatacomprovante(Date datacomprovante) {
        this.datacomprovante = datacomprovante;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

   
    public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getTipopagamento() {
		return tipopagamento;
	}

	public void setTipopagamento(String tipopagamento) {
		this.tipopagamento = tipopagamento;
	}


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpagamentoinvoice != null ? idpagamentoinvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pagamentoinvoice)) {
            return false;
        }
        Pagamentoinvoice other = (Pagamentoinvoice) object;
        if ((this.idpagamentoinvoice == null && other.idpagamentoinvoice != null) || (this.idpagamentoinvoice != null && !this.idpagamentoinvoice.equals(other.idpagamentoinvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pagamentoinvoice[ idpagamentoinvoice=" + idpagamentoinvoice + " ]";
    }
    
}
