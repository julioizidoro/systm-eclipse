/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "pacotetrem")
@NamedQueries({
    @NamedQuery(name = "Pacotetrem.findAll", query = "SELECT p FROM Pacotetrem p")})
public class Pacotetrem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotetrem")
    private Integer idpacotetrem;
    @Size(max = 100)
    @Column(name = "de")
    private String de;
    @Size(max = 100)
    @Column(name = "para")
    private String para;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 5)
    @Column(name = "horasaida")
    private String horasaida;
    @Size(max = 5)
    @Column(name = "horachegada")
    private String horachegada;
    @Size(max = 20)
    @Column(name = "classe")
    private String classe;
    @Column(name = "adt")
    private Integer adt;
    @Column(name = "chd")
    private Integer chd;
    @Column(name = "inf")
    private Integer inf;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tarifa")
    private Float tarifa;
    @Column(name = "taxa")
    private Float taxa;
    @JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
    @ManyToOne(optional = false)
    private Cambio cambio;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "pacotetrecho_idpacotetrecho", referencedColumnName = "idpacotetrecho")
    @ManyToOne(optional = false)
    private Pacotetrecho pacotetrecho;
    @Column(name = "valorcambio")
    private Float valorcambio;
    @Column(name = "datapagamentoparceiro")
    @Temporal(TemporalType.DATE)
    private Date datapagamentoparceiro;

    public Pacotetrem() {
    }

    public Pacotetrem(Integer idpacotetrem) {
        this.idpacotetrem = idpacotetrem;
    }

    public Integer getIdpacotetrem() {
        return idpacotetrem;
    }

    public void setIdpacotetrem(Integer idpacotetrem) {
        this.idpacotetrem = idpacotetrem;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorasaida() {
        return horasaida;
    }

    public void setHorasaida(String horasaida) {
        this.horasaida = horasaida;
    }

    public String getHorachegada() {
        return horachegada;
    }

    public void setHorachegada(String horachegada) {
        this.horachegada = horachegada;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getAdt() {
        return adt;
    }

    public void setAdt(Integer adt) {
        this.adt = adt;
    }

    public Integer getChd() {
        return chd;
    }

    public void setChd(Integer chd) {
        this.chd = chd;
    }

    public Integer getInf() {
        return inf;
    }

    public void setInf(Integer inf) {
        this.inf = inf;
    }

   
    public Float getTarifa() {
		return tarifa;
	}

	public void setTarifa(Float tarifa) {
		this.tarifa = tarifa;
	}

	public Float getTaxa() {
		return taxa;
	}

	public void setTaxa(Float taxa) {
		this.taxa = taxa;
	}

	public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public Pacotetrecho getPacotetrecho() {
        return pacotetrecho;
    }

    public void setPacotetrecho(Pacotetrecho pacotetrecho) {
        this.pacotetrecho = pacotetrecho;
    }

    public Float getValorcambio() {
		return valorcambio;
	}

	public void setValorcambio(Float valorcambio) {
		this.valorcambio = valorcambio;
	}

	public Date getDatapagamentoparceiro() {
		return datapagamentoparceiro;
	}

	public void setDatapagamentoparceiro(Date datapagamentoparceiro) {
		this.datapagamentoparceiro = datapagamentoparceiro;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpacotetrem != null ? idpacotetrem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pacotetrem)) {
            return false;
        }
        Pacotetrem other = (Pacotetrem) object;
        if ((this.idpacotetrem == null && other.idpacotetrem != null) || (this.idpacotetrem != null && !this.idpacotetrem.equals(other.idpacotetrem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotetrem[ idpacotetrem=" + idpacotetrem + " ]";
    }
    
}
