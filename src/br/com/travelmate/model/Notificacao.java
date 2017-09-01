package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotificacao")
    private Integer idnotificacao;
    @Size(max = 50)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 100)
    @Column(name = "unidade")
    private String unidade;
    @Size(max = 100)
    @Column(name = "cliente")
    private String cliente;
    @Size(max = 100)
    @Column(name = "fornecedor")
    private String fornecedor;
    @Size(max = 100)
    @Column(name = "datainicio")
    private String datainicio;
    @Size(max = 100)
    @Column(name = "consultor")
    private String consultor;
    @Size(max = 30)
    @Column(name = "tipovenda")
    private String tipovenda;
    @Size(max = 30)
    @Column(name = "imagem")
    private String imagem;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorvenda")
    private Float valorvenda;
    @Column(name = "cambio")
    private Float cambio;
    @Size(max = 5)
    @Column(name = "moeda")
    private String moeda;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "limpar")
    private Boolean limpar;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;

    public Notificacao() {
    }

    public Notificacao(Integer idnotificacao) {
        this.idnotificacao = idnotificacao;
    }

    public Integer getIdnotificacao() {
        return idnotificacao;
    }

    public void setIdnotificacao(Integer idnotificacao) {
        this.idnotificacao = idnotificacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getConsultor() {
        return consultor;
    }

    public void setConsultor(String consultor) {
        this.consultor = consultor;
    }

    public String getTipovenda() {
        return tipovenda;
    }

    public void setTipovenda(String tipovenda) {
        this.tipovenda = tipovenda;
    }

    public Float getValorvenda() {
        return valorvenda;
    }

    public void setValorvenda(Float valorvenda) {
        this.valorvenda = valorvenda;
    }

    public Float getCambio() {
        return cambio;
    }

    public void setCambio(Float cambio) {
        this.cambio = cambio;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public Boolean getLimpar() {
        return limpar;
    }

    public void setLimpar(Boolean limpar) {
        this.limpar = limpar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data= data;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotificacao != null ? idnotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.idnotificacao == null && other.idnotificacao != null) || (this.idnotificacao != null && !this.idnotificacao.equals(other.idnotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Notificacao[ idnotificacao=" + idnotificacao + " ]";
    }
    
}

