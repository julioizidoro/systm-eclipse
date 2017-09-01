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
import javax.persistence.Transient;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "orcamentoprojetovoluntariado")
public class Orcamentoprojetovoluntariado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentoprojetovoluntariado")
    private Integer idorcamentoprojetovoluntariado;
    @Column(name = "dataorcamento")
    @Temporal(TemporalType.DATE)
    private Date dataorcamento;
    @Column(name = "datainicial")
    @Temporal(TemporalType.DATE)
    private Date datainicial;
    @Column(name = "datafinal")
    @Temporal(TemporalType.DATE)
    private Date datafinal;
    @Column(name = "nsemanas")
    private String nsemanas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorcambio")
    private Float valorcambio;
    @Column(name = "possuiacomodacao")
    private Boolean possuiacomodacao;
    @Column(name = "possuitransfer")
    private Boolean possuitransfer;
    @Column(name = "valorsemanaadicional")
    private Float valorsemanaadicional;
    @Column(name = "valorsemanaadicionalrs")
    private Float valorsemanaadicionalrs;
    @Column(name = "valorprojeto")
    private Float valorprojeto;
    @Column(name = "valorprojetors")
    private Float valorprojetors;
    @Column(name = "valortotal")
    private Float valortotal;
    @Column(name = "valortotalrs")
    private Float valortotalrs;
    @Column(name = "obs")
    private String obs;
    @Column(name = "nsemanaadicional")
    private int nsemanaadicional;
    @Column(name = "enviadoemail")
    private boolean enviadoemail;
    @JoinColumn(name = "voluntariadoprojetovalor_idvoluntariadoprojetovalor", referencedColumnName = "idvoluntariadoprojetovalor")
    @ManyToOne(optional = false)
    private Voluntariadoprojetovalor voluntariadoprojetovalor;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "orcamentoprojetovoluntariado")
    private List<Orcamentovoluntariadoformapagamento> orcamentovoluntariadoformapagamentoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "orcamentoprojetovoluntariado")
    private List<Orcamentovoluntariadodesconto> orcamentovoluntariadodescontoList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "orcamentoprojetovoluntariado")
    private List<Orcamentovoluntariadoseguro> orcamentovoluntariadoseguroList;
    @Transient 
    private boolean selecionado;

    public Orcamentoprojetovoluntariado() {
    }

    public Orcamentoprojetovoluntariado(Integer idorcamentoprojetovoluntariado) {
        this.idorcamentoprojetovoluntariado = idorcamentoprojetovoluntariado;
    }

    public Integer getIdorcamentoprojetovoluntariado() {
        return idorcamentoprojetovoluntariado;
    }

    public void setIdorcamentoprojetovoluntariado(Integer idorcamentoprojetovoluntariado) {
        this.idorcamentoprojetovoluntariado = idorcamentoprojetovoluntariado;
    }

    public Date getDataorcamento() {
		return dataorcamento;
	}

	public void setDataorcamento(Date dataorcamento) {
		this.dataorcamento = dataorcamento;
	}

	public Date getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(Date datainicial) {
        this.datainicial = datainicial;
    }

    public Date getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(Date datafinal) {
        this.datafinal = datafinal;
    }

    public String getNsemanas() {
        return nsemanas;
    }

    public void setNsemanas(String nsemanas) {
        this.nsemanas = nsemanas;
    }

    public Float getValorcambio() {
        return valorcambio;
    }

    public void setValorcambio(Float valorcambio) {
        this.valorcambio = valorcambio;
    }

    public Boolean getPossuiacomodacao() {
        return possuiacomodacao;
    }

    public void setPossuiacomodacao(Boolean possuiacomodacao) {
        this.possuiacomodacao = possuiacomodacao;
    }

    public Boolean getPossuitransfer() {
        return possuitransfer;
    }

    public void setPossuitransfer(Boolean possuitransfer) {
        this.possuitransfer = possuitransfer;
    }

    public Float getValorsemanaadicional() {
        return valorsemanaadicional;
    }

    public void setValorsemanaadicional(Float valorsemanaadicional) {
        this.valorsemanaadicional = valorsemanaadicional;
    }

    public Float getValorsemanaadicionalrs() {
        return valorsemanaadicionalrs;
    }

    public void setValorsemanaadicionalrs(Float valorsemanaadicionalrs) {
        this.valorsemanaadicionalrs = valorsemanaadicionalrs;
    }

    public Float getValorprojeto() {
        return valorprojeto;
    }

    public void setValorprojeto(Float valorprojeto) {
        this.valorprojeto = valorprojeto;
    }

    public Float getValorprojetors() {
        return valorprojetors;
    }

    public void setValorprojetors(Float valorprojetors) {
        this.valorprojetors = valorprojetors;
    }

    public Float getValortotal() {
        return valortotal;
    }

    public void setValortotal(Float valortotal) {
        this.valortotal = valortotal;
    }

    public Float getValortotalrs() {
        return valortotalrs;
    }

    public void setValortotalrs(Float valortotalrs) {
        this.valortotalrs = valortotalrs;
    }

    public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
        return voluntariadoprojetovalor;
    }
   
    public List<Orcamentovoluntariadoformapagamento> getOrcamentovoluntariadoformapagamentoList() {
		return orcamentovoluntariadoformapagamentoList;
	}

	public void setOrcamentovoluntariadoformapagamentoList(
			List<Orcamentovoluntariadoformapagamento> orcamentovoluntariadoformapagamentoList) {
		this.orcamentovoluntariadoformapagamentoList = orcamentovoluntariadoformapagamentoList;
	}

	public List<Orcamentovoluntariadodesconto> getOrcamentovoluntariadodescontoList() {
		return orcamentovoluntariadodescontoList;
	}

	public void setOrcamentovoluntariadodescontoList(
			List<Orcamentovoluntariadodesconto> orcamentovoluntariadodescontoList) {
		this.orcamentovoluntariadodescontoList = orcamentovoluntariadodescontoList;
	}

	public List<Orcamentovoluntariadoseguro> getOrcamentovoluntariadoseguroList() {
		return orcamentovoluntariadoseguroList;
	}

	public void setOrcamentovoluntariadoseguroList(List<Orcamentovoluntariadoseguro> orcamentovoluntariadoseguroList) {
		this.orcamentovoluntariadoseguroList = orcamentovoluntariadoseguroList;
	}

	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public int getNsemanaadicional() {
		return nsemanaadicional;
	}

	public void setNsemanaadicional(int nsemanaadicional) {
		this.nsemanaadicional = nsemanaadicional;
	}

	public boolean isEnviadoemail() {
		return enviadoemail;
	}

	public void setEnviadoemail(boolean enviadoemail) {
		this.enviadoemail = enviadoemail;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentoprojetovoluntariado != null ? idorcamentoprojetovoluntariado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) { 
        if (!(object instanceof Orcamentoprojetovoluntariado)) {
            return false;
        }
        Orcamentoprojetovoluntariado other = (Orcamentoprojetovoluntariado) object;
        if ((this.idorcamentoprojetovoluntariado == null && other.idorcamentoprojetovoluntariado != null) || (this.idorcamentoprojetovoluntariado != null && !this.idorcamentoprojetovoluntariado.equals(other.idorcamentoprojetovoluntariado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentoprojetovoluntariado[ idorcamentoprojetovoluntariado=" + idorcamentoprojetovoluntariado + " ]";
    }
    
}
