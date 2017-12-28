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
@Table(name = "pacotetransfer")
@NamedQueries({
    @NamedQuery(name = "Pacotetransfer.findAll", query = "SELECT p FROM Pacotetransfer p")})
public class Pacotetransfer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpacotetransfer")
    private Integer idpacotetransfer;
    @Size(max = 100)
    @Column(name = "de")
    private String de;
    @Size(max = 100)
    @Column(name = "para")
    private String para;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 15)
    @Column(name = "tipo")
    private String tipo;
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
    @Size(max = 100)
    @Column(name = "deout")
    private String deout;
    @Size(max = 100)
    @Column(name = "paraout")
    private String paraout;
    @Column(name = "dataout")
    @Temporal(TemporalType.DATE)
    private Date dataout;
    @Column(name = "datapagamentoparceiro")
    @Temporal(TemporalType.DATE)
    private Date datapagamentoparceiro;

    public Pacotetransfer() {
    	tarifa = 0f;
    	taxa = 0f;
    	valorcambio = 0f;
    }

    public Pacotetransfer(Integer idpacotetransfer) {
        this.idpacotetransfer = idpacotetransfer;
    }

    public Integer getIdpacotetransfer() {
        return idpacotetransfer;
    }

    public void setIdpacotetransfer(Integer idpacotetransfer) {
        this.idpacotetransfer = idpacotetransfer;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

	public String getDeout() {
		return deout;
	}

	public void setDeout(String deout) {
		this.deout = deout;
	}

	public String getParaout() {
		return paraout;
	}

	public void setParaout(String paraout) {
		this.paraout = paraout;
	}

	public Date getDataout() {
		return dataout;
	}

	public void setDataout(Date dataout) {
		this.dataout = dataout;
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
        hash += (idpacotetransfer != null ? idpacotetransfer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pacotetransfer)) {
            return false;
        }
        Pacotetransfer other = (Pacotetransfer) object;
        if ((this.idpacotetransfer == null && other.idpacotetransfer != null) || (this.idpacotetransfer != null && !this.idpacotetransfer.equals(other.idpacotetransfer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Pacotetransfer[ idpacotetransfer=" + idpacotetransfer + " ]";
    }
    
}
