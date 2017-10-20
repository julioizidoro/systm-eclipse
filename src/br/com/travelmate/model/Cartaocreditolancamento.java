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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "cartaocreditolancamento")
public class Cartaocreditolancamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcartaocreditolancamento")
    private Integer idcartaocreditolancamento;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 200)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorinformado")
    private Float valorinformado;
    @Column(name = "valorlancado")
    private Float valorlancado;
    @Column(name = "valorcambio")
    private Float valorcambio;
    @Size(max = 100)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "conferido")
    private Boolean conferido;
    @Column(name = "lancado")
    private Boolean lancado;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "planoconta_idplanoconta", referencedColumnName = "idplanoconta")
    @ManyToOne(optional = false)
    private Planoconta planoconta;
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas;
    @JoinColumn(name = "cartaocredito_idcartaocredito", referencedColumnName = "idcartaocredito")
    @ManyToOne(optional = false)
    private Cartaocredito cartaocredito;
    @Size(max = 100)
    @Column(name = "estabelecimento")
    private String estabelecimento;
    @Size(max = 8)
    @Column(name = "numeroparcelas")
    private String numeroparcelas;
    @Column(name = "valorrecorrente")
    private boolean valorrecorrente;
    @Column(name = "habilitarmoeda")
    private boolean habilitarmoeda;

    public Cartaocreditolancamento() {
    }

    public Cartaocreditolancamento(Integer idcartaocreditolancamento) {
        this.idcartaocreditolancamento = idcartaocreditolancamento;
    }

    public Integer getIdcartaocreditolancamento() {
        return idcartaocreditolancamento;
    }

    public void setIdcartaocreditolancamento(Integer idcartaocreditolancamento) {
        this.idcartaocreditolancamento = idcartaocreditolancamento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValorinformado() {
        return valorinformado;
    }

    public void setValorinformado(Float valorinformado) {
        this.valorinformado = valorinformado;
    }

    public Float getValorlancado() {
        return valorlancado;
    }

    public void setValorlancado(Float valorlancado) {
        this.valorlancado = valorlancado;
    }

    public Float getValorcambio() {
        return valorcambio;
    }

    public void setValorcambio(Float valorcambio) {
        this.valorcambio = valorcambio;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getConferido() {
        return conferido;
    }

    public void setConferido(Boolean conferido) {
        this.conferido = conferido;
    }

    public Boolean getLancado() {
        return lancado;
    }

    public void setLancado(Boolean lancado) {
        this.lancado = lancado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public Moedas getMoedas() {
        return moedas;
    }

    public void setMoedas(Moedas moedas) {
        this.moedas = moedas;
    }

    public Cartaocredito getCartaocredito() {
		return cartaocredito;
	}

	public void setCartaocredito(Cartaocredito cartaocredito) {
		this.cartaocredito = cartaocredito;
	}

	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getNumeroparcelas() {
		return numeroparcelas;
	}

	public void setNumeroparcelas(String numeroparcelas) {
		this.numeroparcelas = numeroparcelas;
	}

	public boolean isValorrecorrente() {
		return valorrecorrente;
	}

	public void setValorrecorrente(boolean valorrecorrente) {
		this.valorrecorrente = valorrecorrente;
	}

	public boolean isHabilitarmoeda() {
		return habilitarmoeda;
	}

	public void setHabilitarmoeda(boolean habilitarmoeda) {
		this.habilitarmoeda = habilitarmoeda;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcartaocreditolancamento != null ? idcartaocreditolancamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cartaocreditolancamento)) {
            return false;
        }
        Cartaocreditolancamento other = (Cartaocreditolancamento) object;
        if ((this.idcartaocreditolancamento == null && other.idcartaocreditolancamento != null) || (this.idcartaocreditolancamento != null && !this.idcartaocreditolancamento.equals(other.idcartaocreditolancamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cartaocreditolancamento[ idcartaocreditolancamento=" + idcartaocreditolancamento + " ]";
    }
    
}

