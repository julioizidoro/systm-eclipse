/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import br.com.travelmate.bean.BolinhasBean;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "contasreceber")
public class Contasreceber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontasreceber")
    private Integer idcontasreceber;
    @Size(max = 30)
    @Column(name = "numerodocumento")
    private String numerodocumento;
    @Size(max = 8)
    @Column(name = "numeroparcelas")
    private String numeroparcelas;
    @Column(name = "datavencimento")
    @Temporal(TemporalType.DATE)
    private Date datavencimento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorparcela")
    private Float valorparcela;
    @Column(name = "juros")
    private Float juros;
    @Column(name = "desagio")
    private Float desagio;
    @Column(name = "datapagamento")
    @Temporal(TemporalType.DATE)
    private Date datapagamento;
    @Column(name = "valorpago")
    private Float valorpago;
    @Size(max = 50)
    @Column(name = "tipodocumento")
    private String tipodocumento;
    @Size(max = 8)
    @Column(name = "nossonumero")
    private String nossonumero;
    @Size(max = 3)
    @Column(name = "boletogerado")
    private String boletogerado;
    @Size(max = 200)
    @Column(name = "motivocancelamento")
    private String motivocancelamento;
    @Column(name = "dataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Column(name = "dataenvio")
    @Temporal(TemporalType.DATE)
    private Date dataenvio;
    @Column(name = "dataRetorno")
    @Temporal(TemporalType.DATE)
    private Date dataRetorno;
    @Column(name = "boletoenviado")
    private Boolean boletoenviado;
    @Column(name = "dataalterada")
    private Boolean dataalterada;
    @Column(name = "boletocancelado")
    private Boolean boletocancelado;
    @Column(name = "idconciliacao")
    private int idconciliacao;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "parcelaboleto")
    private int parcelaboleto;
    @Column(name = "codigoocorrencia")
    private String codigoocorrencia;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;
    @JoinColumn(name = "planoconta_idplanoconta", referencedColumnName = "idplanoconta")
    @ManyToOne(optional = false)
    private Planoconta planoconta;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Column(name = "datacancelamento")
    @Temporal(TemporalType.DATE)
    private Date datacancelamento;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "contasreceber", fetch=FetchType.LAZY)
    private Crmcobrancaconta crmcobrancaconta;
    @Transient
    private boolean selecionado;
    @Transient
    private BolinhasBean bolinhas;
    @Column(name = "datanovovencimento")
    @Temporal(TemporalType.DATE)
    private Date datanovovencimento;
    @Column(name = "idparcelamentopagamento")
    private Integer idparcelamentopagamento;
    

    public Contasreceber() {
    	setCodigoocorrencia("00");
    	setBoletogerado("NAO");
    }

    public Contasreceber(Integer idcontasreceber) {
        this.idcontasreceber = idcontasreceber;
    }

    public Integer getIdcontasreceber() {
        return idcontasreceber;
    }

    public void setIdcontasreceber(Integer idcontasreceber) {
        this.idcontasreceber = idcontasreceber;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getNumeroparcelas() {
        return numeroparcelas;
    }

    public void setNumeroparcelas(String numeroparcelas) {
        this.numeroparcelas = numeroparcelas;
    }

    public Date getDatavencimento() {
        return datavencimento;
    }

    public void setDatavencimento(Date datavencimento) {
        this.datavencimento = datavencimento;
    }

    public Float getValorparcela() {
        return valorparcela;
    }

    public void setValorparcela(Float valorparcela) {
        this.valorparcela = valorparcela;
    }

    public Float getJuros() {
        return juros;
    }

    public void setJuros(Float juros) {
        this.juros = juros;
    }

    public Float getDesagio() {
        return desagio;
    }

    public void setDesagio(Float desagio) {
        this.desagio = desagio;
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

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNossonumero() {
        return nossonumero;
    }

    public void setNossonumero(String nossonumero) {
        this.nossonumero = nossonumero;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public String getBoletogerado() {
		return boletogerado;
	}

	public void setBoletogerado(String boletogerado) {
		this.boletogerado = boletogerado;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Boolean getBoletoenviado() {
        return boletoenviado;
    }

    public void setBoletoenviado(Boolean boletoenviado) {
        this.boletoenviado = boletoenviado;
    }

    public Date getDataenvio() {
		return dataenvio;
	}

	public void setDataenvio(Date dataenvio) {
		this.dataenvio = dataenvio;
	}

	public Boolean getDataalterada() {
        return dataalterada;
    }

    public void setDataalterada(Boolean dataalterada) {
        this.dataalterada = dataalterada;
    }

    public Boolean getBoletocancelado() {
        return boletocancelado;
    }

    public void setBoletocancelado(Boolean boletocancelado) {
        this.boletocancelado = boletocancelado;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public int getIdconciliacao() {
        return idconciliacao;
    }

    public void setIdconciliacao(int idconciliacao) {
        this.idconciliacao = idconciliacao;
    }

    public BolinhasBean getBolinhas() {
		return bolinhas;
	}

	public void setBolinhas(BolinhasBean bolinhas) {
		this.bolinhas = bolinhas;
	}

	public String getMotivocancelamento() {
		return motivocancelamento;
	}

	public void setMotivocancelamento(String motivocancelamento) {
		this.motivocancelamento = motivocancelamento;
	}

	public int getParcelaboleto() {
		return parcelaboleto;
	}

	public void setParcelaboleto(int parcelaboleto) {
		this.parcelaboleto = parcelaboleto;
	}

	public String getCodigoocorrencia() {
		return codigoocorrencia;
	}

	public void setCodigoocorrencia(String codigoocorrencia) {
		this.codigoocorrencia = codigoocorrencia;
	}

	public Date getDatacancelamento() {
		return datacancelamento;
	}

	public void setDatacancelamento(Date datacancelamento) {
		this.datacancelamento = datacancelamento;
	}

	public Crmcobrancaconta getCrmcobrancaconta() {
		return crmcobrancaconta;
	}

	public void setCrmcobrancaconta(Crmcobrancaconta crmcobrancaconta) {
		this.crmcobrancaconta = crmcobrancaconta;
	}

	public Date getDatanovovencimento() {
		return datanovovencimento;
	}

	public void setDatanovovencimento(Date datanovovencimento) {
		this.datanovovencimento = datanovovencimento;
	}


	public Integer getIdparcelamentopagamento() {
		return idparcelamentopagamento;
	}

	public void setIdparcelamentopagamento(Integer idparcelamentopagamento) {
		this.idparcelamentopagamento = idparcelamentopagamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontasreceber != null ? idcontasreceber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Contasreceber)) {
            return false;
        }
        Contasreceber other = (Contasreceber) object;
        if ((this.idcontasreceber == null && other.idcontasreceber != null) || (this.idcontasreceber != null && !this.idcontasreceber.equals(other.idcontasreceber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Contasreceber[ idcontasreceber=" + idcontasreceber + " ]";
    }
    
}
