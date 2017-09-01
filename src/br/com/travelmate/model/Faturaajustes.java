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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "faturaajustes")
public class Faturaajustes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFaturaajustes")
    private Integer idFaturaajustes; 
    @Size(max = 100)
    @Column(name = "item")   
    private String item;
    @Column(name = "datalancamento")
    @Temporal(TemporalType.DATE)
    private Date datalancamento;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 1)
    @Column(name = "creditodebito")
    private String creditodebito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @JoinColumn(name = "unidadenegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "faturaajustes")
    private List<Faturafaturaajuste> faturafaturaajusteList;

    public Faturaajustes() {
    }

    public Faturaajustes(Integer idFaturaajustes) {
        this.idFaturaajustes = idFaturaajustes;
    }

    public Integer getIdFaturaajustes() {
        return idFaturaajustes;
    }

    public void setIdFaturaajustes(Integer idFaturaajustes) {
        this.idFaturaajustes = idFaturaajustes;
    }
 
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Date getDatalancamento() {
        return datalancamento;
    }

    public void setDatalancamento(Date datalancamento) {
        this.datalancamento = datalancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCreditodebito() {
        return creditodebito;
    }

    public void setCreditodebito(String creditodebito) {
        this.creditodebito = creditodebito;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
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

    public List<Faturafaturaajuste> getFaturafaturaajusteList() {
        return faturafaturaajusteList;
    }

    public void setFaturafaturaajusteList(List<Faturafaturaajuste> faturafaturaajusteList) {
        this.faturafaturaajusteList = faturafaturaajusteList;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idFaturaajustes != null ? idFaturaajustes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faturaajustes)) {
            return false;
        }
        Faturaajustes other = (Faturaajustes) object;
        if ((this.idFaturaajustes == null && other.idFaturaajustes != null) || (this.idFaturaajustes != null && !this.idFaturaajustes.equals(other.idFaturaajustes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Faturaajustes[ idFaturaajustes=" + idFaturaajustes + " ]";
    }
    
}
