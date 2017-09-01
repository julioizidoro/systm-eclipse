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

@Entity
@Table(name = "recinternacional")
public class Recinternacional implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idrecinternacional")
	private Integer idrecinternacional;
	@Column(name = "observacao")
	private String observacao;
	@Column(name = "datarecebimento")
	@Temporal(TemporalType.DATE)
	private Date datarecebimento;
	@Column(name = "dataenvio")
	@Temporal(TemporalType.DATE)
	private Date dataenvio;
	@Column(name = "datavencimento")
	@Temporal(TemporalType.DATE)
	private Date datavencimento;
	@Column(name = "valorreais")
	private float valorreais;
	@Column(name = "valormeodaestrangeira")
	private float valormeodaestrangeira;
	@Column(name = "tarifa")
	private float tarifa;
	@Column(name = "valor")
	private float valor;
	@JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
	@ManyToOne(optional = false)
	private Fornecedor fornecedor;
	@JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	@ManyToOne(optional = false)
	private Vendas vendas;
	@JoinColumn(name = "motivorecinternacional_idmotivorecinternacional", referencedColumnName = "idmotivorecinternacional")
	@ManyToOne(optional = false)
	private Motivorecinternacional motivorecinternacional;
	@JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
	@ManyToOne(optional = false)
	private Banco banco;
	@JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
	@ManyToOne(optional = false)
	private Moedas moedas;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false)
	private Usuario usuario;
	@Column(name = "relatorio")
	private boolean relatorio;
	
	
	
	public Recinternacional() {
	}



	public Integer getIdrecinternacional() {
		return idrecinternacional;
	}



	public void setIdrecinternacional(Integer idrecinternacional) {
		this.idrecinternacional = idrecinternacional;
	}



	public String getObservacao() {
		return observacao;
	}



	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}



	public Date getDatarecebimento() {
		return datarecebimento;
	}



	public void setDatarecebimento(Date datarecebimento) {
		this.datarecebimento = datarecebimento;
	}



	public Date getDataenvio() {
		return dataenvio;
	}



	public void setDataenvio(Date dataenvio) {
		this.dataenvio = dataenvio;
	}



	public Date getDatavencimento() {
		return datavencimento;
	}



	public void setDatavencimento(Date datavencimento) {
		this.datavencimento = datavencimento;
	}



	public float getValorreais() {
		return valorreais;
	}



	public void setValorreais(float valorreais) {
		this.valorreais = valorreais;
	}



	public float getValormeodaestrangeira() {
		return valormeodaestrangeira;
	}



	public void setValormeodaestrangeira(float valormeodaestrangeira) {
		this.valormeodaestrangeira = valormeodaestrangeira;
	}



	public float getTarifa() {
		return tarifa;
	}



	public void setTarifa(float tarifa) {
		this.tarifa = tarifa;
	}



	public float getValor() {
		return valor;
	}



	public void setValor(float valor) {
		this.valor = valor;
	}



	public Fornecedor getFornecedor() {
		return fornecedor;
	}



	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}



	public Motivorecinternacional getMotivorecinternacional() {
		return motivorecinternacional;
	}



	public void setMotivorecinternacional(Motivorecinternacional motivorecinternacional) {
		this.motivorecinternacional = motivorecinternacional;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
	}



	public Moedas getMoedas() {
		return moedas;
	}



	public void setMoedas(Moedas moedas) {
		this.moedas = moedas;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isRelatorio() {
		return relatorio;
	}



	public void setRelatorio(boolean relatorio) {
		this.relatorio = relatorio;
	}



	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idrecinternacional != null ? idrecinternacional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recinternacional)) {
            return false;
        }
        Recinternacional other = (Recinternacional) object;
        if ((this.idrecinternacional == null && other.idrecinternacional != null) || (this.idrecinternacional != null && !this.idrecinternacional.equals(other.idrecinternacional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Recinternacional[ idrecinternacional=" + idrecinternacional + " ]";
    }
	
	
	

}
