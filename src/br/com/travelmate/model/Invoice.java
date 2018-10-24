/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinvoices")
    private Integer idinvoices;
    @Column(name = "dataPagamentoInvoice")
    @Temporal(TemporalType.DATE)
    private Date dataPagamentoInvoice;
    @Column(name = "valorPagamentoInvoice")
    private Float valorPagamentoInvoice;
    @Column(name = "valorcredito")
    private Float valorcredito;
    @Column(name = "obscredito")
    private String obscredito;
    @Column(name = "prioridade")
    private String prioridade;
    @Column(name = "controle")
    private int controle;
    @Column(name = "datarecebimentocomprovante")
    @Temporal(TemporalType.DATE)
    private Date datarecebimentocomprovante;
    @Column(name = "possuicredito")
    private boolean possuicredito;
    @Column(name = "escala")
    private int escala;
    @Transient
    private boolean selecionado;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "invoice")
    private List<Pagamentoinvoice> pagamentoinvoiceList;
    @Transient
    private boolean pagamentoRateio=true;
    @Column(name = "valorPago")
    private float valorPago;
    @Column(name = "dataPrevistaPagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPrevistaPagamento;
    @Column(name = "valorPrevistoInvoice")
    private Float valorPrevistoInvoice;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "situacao")
    private String situacao;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacaofinanceiro")
    private String observacaofinanceiro;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacaodepartamento")
    private String observacaodepartamento;
    @Column(name = "paga")
    private Boolean paga;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "invoice")
    private Invoiceremessainvoice invoiceremessainvoice;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;
    
    @JoinColumn(name = "tipoarquivoproduto_idtipoarquivoproduto", referencedColumnName = "idtipoarquivoproduto")
    @ManyToOne(optional = false)
    private Tipoarquivoproduto tipoarquivoproduto;
    public Tipoarquivoproduto getTipoarquivoproduto() {
		return tipoarquivoproduto;
	}

	public void setTipoarquivoproduto(Tipoarquivoproduto tipoarquivoproduto) {
		this.tipoarquivoproduto = tipoarquivoproduto;
	}

	@Transient
    private boolean clientedevedor;
    
    
    
    public Invoice() {
    	setTipo("Programa");
    	setValorcredito(0.0f);
    	setValorPagamentoInvoice(0.0f);
    	setValorPago(0.0f);
    	setValorPrevistoInvoice(0.0f);
    }

    public Invoice(Integer idinvoices) {
        this.idinvoices = idinvoices;
    }

    public Integer getIdinvoices() {
        return idinvoices;
    }

	public List<Pagamentoinvoice> getPagamentoinvoiceList() {
		return pagamentoinvoiceList;
	}

	public void setPagamentoinvoiceList(List<Pagamentoinvoice> pagamentoinvoiceList) {
		this.pagamentoinvoiceList = pagamentoinvoiceList;
	}

	public Date getDatarecebimentocomprovante() {
        return datarecebimentocomprovante;
    }

    public void setDatarecebimentocomprovante(Date datarecebimentocomprovante) {
        this.datarecebimentocomprovante = datarecebimentocomprovante;
    }

    public void setIdinvoices(Integer idinvoices) {
        this.idinvoices = idinvoices;
    }

    public int getControle() {
        return controle;
    }

    public void setControle(int controle) {
        this.controle = controle;
    }

    
    public boolean isPossuicredito() {
		return possuicredito;
	}

	public void setPossuicredito(boolean possuicredito) {
		this.possuicredito = possuicredito;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDataPagamentoInvoice() {
        return dataPagamentoInvoice;
    }

    public Float getValorcredito() {
        return valorcredito;
    }

    public void setValorcredito(Float valorcredito) {
        this.valorcredito = valorcredito;
    }

    public String getObscredito() {
        return obscredito;
    }

    public void setObscredito(String obscredito) {
        this.obscredito = obscredito;
    }

    public void setDataPagamentoInvoice(Date dataPagamentoInvoice) {
        this.dataPagamentoInvoice = dataPagamentoInvoice;
    }

    public Float getValorPagamentoInvoice() {
        return valorPagamentoInvoice;
    }

    public void setValorPagamentoInvoice(Float valorPagamentoInvoice) {
        this.valorPagamentoInvoice = valorPagamentoInvoice;
    }

    
    public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public boolean isPagamentoRateio() {
		return pagamentoRateio;
	}

	public void setPagamentoRateio(boolean pagamentoRateio) {
		this.pagamentoRateio = pagamentoRateio;
	}

	public float getValorPago() {
		return valorPago;
	}

	public void setValorPago(float valorPago) {
		this.valorPago = valorPago;
	}

	public Date getDataPrevistaPagamento() {
		return dataPrevistaPagamento;
	}

	public void setDataPrevistaPagamento(Date dataPrevistaPagamento) {
		this.dataPrevistaPagamento = dataPrevistaPagamento;
	}

	public Float getValorPrevistoInvoice() {
		return valorPrevistoInvoice;
	}

	public void setValorPrevistoInvoice(Float valorPrevistoInvoice) {
		this.valorPrevistoInvoice = valorPrevistoInvoice;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	

	public String getObservacaofinanceiro() {
		return observacaofinanceiro;
	}

	public void setObservacaofinanceiro(String observacaofinanceiro) {
		this.observacaofinanceiro = observacaofinanceiro;
	}

	public String getObservacaodepartamento() {
		return observacaodepartamento;
	}

	public void setObservacaodepartamento(String observacaodepartamento) {
		this.observacaodepartamento = observacaodepartamento;
	}

	public Boolean getPaga() {
		return paga;
	}

	public void setPaga(Boolean paga) {
		this.paga = paga;
	}

	public Invoiceremessainvoice getInvoiceremessainvoice() {
		return invoiceremessainvoice;
	}

	public void setInvoiceremessainvoice(Invoiceremessainvoice invoiceremessainvoice) {
		this.invoiceremessainvoice = invoiceremessainvoice;
	}

	public boolean isClientedevedor() {
		return clientedevedor;
	}

	public void setClientedevedor(boolean clientedevedor) {
		this.clientedevedor = clientedevedor;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idinvoices != null ? idinvoices.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.idinvoices == null && other.idinvoices != null) || (this.idinvoices != null && !this.idinvoices.equals(other.idinvoices))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Invoices[ idinvoices=" + idinvoices + " ]";
    }
    
}
