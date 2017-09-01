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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "leadhistorico")
public class Leadhistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idleadhistorico")
    private Integer idleadhistorico;
    @Column(name = "datahistorico")
    @Temporal(TemporalType.DATE)
    private Date datahistorico;
    @Lob
    @Size(max = 16777215)
    @NotNull
    @Column(name = "historico")
    private String historico;
    @Column(name = "dataproximocontato")
    @Temporal(TemporalType.DATE)
    private Date dataproximocontato;
    @Size(max = 12)
    @Column(name = "horaporximocontato")
    private String horaporximocontato;
    @Size(max = 1)
    @Column(name = "tipoorcamento")
    private String tipoorcamento;
    @Column(name = "idorcamento")
    private Integer idorcamento;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente; 
    @JoinColumn(name = "tipocontato_idtipocontato", referencedColumnName = "idtipocontato")
    @ManyToOne(optional = false)
    private Tipocontato tipocontato;


    public Leadhistorico() {
    	setTipoorcamento("s");
    	setIdorcamento(0);
    	setHistorico("");
    }

    public Leadhistorico(Integer idleadhistorico) {
        this.idleadhistorico = idleadhistorico;
    }

    public Integer getIdleadhistorico() {
        return idleadhistorico;
    }

    public void setIdleadhistorico(Integer idleadhistorico) {
        this.idleadhistorico = idleadhistorico;
    }

    public Date getDatahistorico() {
        return datahistorico;
    }

    public void setDatahistorico(Date datahistorico) {
        this.datahistorico = datahistorico;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Date getDataproximocontato() {
        return dataproximocontato;
    }

    public void setDataproximocontato(Date dataproximocontato) {
        this.dataproximocontato = dataproximocontato;
    }

    public String getHoraporximocontato() {
        return horaporximocontato;
    }

    public void setHoraporximocontato(String horaporximocontato) {
        this.horaporximocontato = horaporximocontato;
    }
 
    public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Tipocontato getTipocontato() {
        return tipocontato;
    }

    public void setTipocontato(Tipocontato tipocontato) {
        this.tipocontato = tipocontato;
    }
 
    public String getTipoorcamento() {
		return tipoorcamento;
	}

	public void setTipoorcamento(String tipoorcamento) {
		this.tipoorcamento = tipoorcamento;
	}

	public Integer getIdorcamento() {
		return idorcamento;
	}

	public void setIdorcamento(Integer idorcamento) {
		this.idorcamento = idorcamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idleadhistorico != null ? idleadhistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Leadhistorico)) {
            return false;
        }
        Leadhistorico other = (Leadhistorico) object;
        if ((this.idleadhistorico == null && other.idleadhistorico != null) || (this.idleadhistorico != null && !this.idleadhistorico.equals(other.idleadhistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Leadhistorico[ idleadhistorico=" + idleadhistorico + " ]";
    }
    
}

