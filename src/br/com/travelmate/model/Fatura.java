package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.persistence.Transient;

import br.com.travelmate.managerBean.financeiro.faturafranquia.GerarFaturaBean;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "fatura")
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfatura")
    private Integer idfatura;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldodebito")
    private Float saldodebito;
    @Column(name = "valorpago")
    private Float valorpago;
    @Column(name = "saldofinalanterior")
    private Float saldofinalanterior;
    @Column(name = "saldolancamentos")  
    private Float saldolancamentos;
    @Column(name = "valorpagar")  
    private Float valorpagar;
    @Column(name = "saldodevedor")
    private Float saldodevedor; 
    @Column(name = "gerartaxapublicidade")
    private boolean gerartaxapublicidade;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fatura")
    private List<Faturafaturafraquias> faturafaturafraquiasList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fatura")
    private List<Faturafaturaajuste> faturafaturaajusteList;
    @JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Transient 
    private List<GerarFaturaBean> listaFaturaItensAberta;  

    public Fatura() {
    	setSaldofinalanterior(0.0f);
    	setSaldodebito(0.0f);
    	setValorpago(0.0f);
    	setSaldolancamentos(0.0f);
    	setValorpagar(0.0f);
    	setSaldodevedor(0.0f);
    }

    public Fatura(Integer idfatura) {
        this.idfatura = idfatura;
    }

    public Integer getIdfatura() {
        return idfatura;
    }

    public void setIdfatura(Integer idfatura) {
        this.idfatura = idfatura;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    } 
    
    public Float getSaldodebito() {
		return saldodebito;
	}

	public void setSaldodebito(Float saldodebito) {
		this.saldodebito = saldodebito;
	}

	public Float getValorpago() {
		return valorpago;
	}

	public void setValorpago(Float valorpago) {
		this.valorpago = valorpago;
	}

	public Float getSaldofinalanterior() {
		return saldofinalanterior;
	}

	public void setSaldofinalanterior(Float saldofinalanterior) {
		this.saldofinalanterior = saldofinalanterior;
	}

	public Float getSaldolancamentos() {
		return saldolancamentos;
	}

	public void setSaldolancamentos(Float saldolancamentos) {
		this.saldolancamentos = saldolancamentos;
	}

	public Float getValorpagar() {
		return valorpagar;
	}

	public void setValorpagar(Float valorpagar) {
		this.valorpagar = valorpagar;
	}

	public Float getSaldodevedor() {
		return saldodevedor;
	}

	public void setSaldodevedor(Float saldodevedor) {
		this.saldodevedor = saldodevedor;
	}

	public List<Faturafaturafraquias> getFaturafaturafraquiasList() {
        return faturafaturafraquiasList;
    }

    public void setFaturafaturafraquiasList(List<Faturafaturafraquias> faturafaturafraquiasList) {
        this.faturafaturafraquiasList = faturafaturafraquiasList;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<GerarFaturaBean> getListaFaturaItensAberta() {
		return listaFaturaItensAberta;
	}

	public void setListaFaturaItensAberta(List<GerarFaturaBean> listaFaturaItensAberta) {
		this.listaFaturaItensAberta = listaFaturaItensAberta;
	}

	public List<Faturafaturaajuste> getFaturafaturaajusteList() {
		return faturafaturaajusteList;
	}

	public void setFaturafaturaajusteList(List<Faturafaturaajuste> faturafaturaajusteList) {
		this.faturafaturaajusteList = faturafaturaajusteList;
	}

	public boolean isGerartaxapublicidade() {
		return gerartaxapublicidade;
	}

	public void setGerartaxapublicidade(boolean gerartaxapublicidade) {
		this.gerartaxapublicidade = gerartaxapublicidade;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idfatura != null ? idfatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fatura)) {
            return false;
        }
        Fatura other = (Fatura) object;
        if ((this.idfatura == null && other.idfatura != null) || (this.idfatura != null && !this.idfatura.equals(other.idfatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fatura[ idfatura=" + idfatura + " ]";
    }
    
}

