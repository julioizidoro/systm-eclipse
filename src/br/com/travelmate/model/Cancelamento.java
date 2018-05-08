package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "cancelamento")
public class Cancelamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcancelamento")
    private Integer idcancelamento;
    @Column(name = "datasolicitacao")
    @Temporal(TemporalType.DATE)
    private Date datasolicitacao;    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalrecebido")
    private Float totalrecebido;
    @Column(name = "totalrecebidomatriz")
    private Float totalrecebidomatriz;
    @Column(name = "totalrecebidoloja")
    private Float totalrecebidoloja;
    @Column(name = "multacliente")
    private Float multacliente;
    @Column(name = "totalreembolso")
    private Float totalreembolso;
    @Column(name = "datareembolso")
    @Temporal(TemporalType.DATE)
    private Date datareembolso;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "idvendacredito")
    private Integer idvendacredito;
    @Column(name = "estornoloja")
    private Float estornoloja;
    @Column(name = "multafornecedor")
    private Float multafornecedor;
    @Column(name = "saldocancelamento")
    private Float saldocancelamento;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "condicaocancelamento_idcondicaocancelamento", referencedColumnName = "idcondicaocancelamento")
    @ManyToOne(optional = false)
    private Condicaocancelamento condicaocancelamento;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "valorcredito")
    private Float valorcredito;
    @Column(name = "valorreembolso")
    private Float valorreembolso;
    @Column(name = "formapagamento")
    private String formapagamento;
    @Column(name = "hora")
    private String hora;
    @Column(name = "uploadtermo")
    private boolean uploadtermo;
    @Transient
    private String nomeArquivo;
    @Transient
    private String imagemTermo;
    @Transient
    private boolean semrecibo;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "cancelamento")
	private Cancelamentocredito cancelamentocredito;
    

    public Cancelamento() {
    	semrecibo = true;
    	imagemTermo = "../../resources/img/semrecibo.png";
    }

    public Cancelamento(Integer idcancelamento) {
        this.idcancelamento = idcancelamento;
    }

    public Integer getIdcancelamento() {
        return idcancelamento;
    }

    public void setIdcancelamento(Integer idcancelamento) {
        this.idcancelamento = idcancelamento;
    }

    public Date getDatasolicitacao() {
        return datasolicitacao;
    }

    public void setDatasolicitacao(Date datasolicitacao) {
        this.datasolicitacao = datasolicitacao;
    }

    public Float getTotalrecebido() {
        return totalrecebido;
    }

    public void setTotalrecebido(Float totalrecebido) {
        this.totalrecebido = totalrecebido;
    }

    public Float getTotalrecebidomatriz() {
		return totalrecebidomatriz;
	}

	public void setTotalrecebidomatriz(Float totalrecebidomatriz) {
		this.totalrecebidomatriz = totalrecebidomatriz;
	}

	public Float getTotalrecebidoloja() {
		return totalrecebidoloja;
	}

	public void setTotalrecebidoloja(Float totalrecebidoloja) {
		this.totalrecebidoloja = totalrecebidoloja;
	}

	public Float getMultacliente() {
        return multacliente;
    }

    public void setMultacliente(Float multacliente) {
        this.multacliente = multacliente;
    }


    public Float getTotalreembolso() {
        return totalreembolso;
    }

    public void setTotalreembolso(Float totalreembolso) {
        this.totalreembolso = totalreembolso;
    }


    public Date getDatareembolso() {
        return datareembolso;
    }

    public void setDatareembolso(Date datareembolso) {
        this.datareembolso = datareembolso;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getIdvendacredito() {
        return idvendacredito;
    }

    public void setIdvendacredito(Integer idvendacredito) {
        this.idvendacredito = idvendacredito;
    }

    public Float getEstornoloja() {
        return estornoloja;
    }

    public void setEstornoloja(Float estornoloja) {
        this.estornoloja = estornoloja;
    }

    public Float getMultafornecedor() {
        return multafornecedor;
    }

    public void setMultafornecedor(Float multafornecedor) {
        this.multafornecedor = multafornecedor;
    }

    public Float getSaldocancelamento() {
        return saldocancelamento;
    }

    public void setSaldocancelamento(Float saldocancelamento) {
        this.saldocancelamento = saldocancelamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Condicaocancelamento getCondicaocancelamento() {
		return condicaocancelamento;
	}

	public void setCondicaocancelamento(Condicaocancelamento condicaocancelamento) {
		this.condicaocancelamento = condicaocancelamento;
	}

	public Float getValorcredito() {
		return valorcredito;
	}

	public void setValorcredito(Float valorcredito) {
		this.valorcredito = valorcredito;
	}

	public Float getValorreembolso() {
		return valorreembolso;
	}

	public void setValorreembolso(Float valorreembolso) {
		this.valorreembolso = valorreembolso;
	}

	public String getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(String formapagamento) {
		this.formapagamento = formapagamento;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public boolean isUploadtermo() {
		return uploadtermo;
	}

	public void setUploadtermo(boolean uploadtermo) {
		this.uploadtermo = uploadtermo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getImagemTermo() {
		return imagemTermo;
	}

	public void setImagemTermo(String imagemTermo) {
		this.imagemTermo = imagemTermo;
	}

	public boolean isSemrecibo() {
		return semrecibo;
	}

	public void setSemrecibo(boolean semrecibo) {
		this.semrecibo = semrecibo;
	}

	public Cancelamentocredito getCancelamentocredito() {
		return cancelamentocredito;
	}

	public void setCancelamentocredito(Cancelamentocredito cancelamentocredito) {
		this.cancelamentocredito = cancelamentocredito;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcancelamento != null ? idcancelamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancelamento)) {
            return false;
        }
        Cancelamento other = (Cancelamento) object;
        if ((this.idcancelamento == null && other.idcancelamento != null) || (this.idcancelamento != null && !this.idcancelamento.equals(other.idcancelamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cancelamento[ idcancelamento=" + idcancelamento + " ]";
    }
    
}
