package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "cancelamentocredito")
public class Cancelamentocredito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcancelamentocredito")
    private Integer idcancelamentocredito;
	@JoinColumn(name = "cancelamento_idcancelamento", referencedColumnName = "idcancelamento")
	@OneToOne(optional = false)
	private Cancelamento cancelamento;
	@JoinColumn(name = "credito_idcredito", referencedColumnName = "idcredito")
	@ManyToOne(optional = false)
	private Credito credito;
	
	
	public Cancelamentocredito() {
	}


	public Integer getIdcancelamentocredito() {
		return idcancelamentocredito;
	}


	public void setIdcancelamentocredito(Integer idcancelamentocredito) {
		this.idcancelamentocredito = idcancelamentocredito;
	}


	public Cancelamento getCancelamento() {
		return cancelamento;
	}


	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}


	public Credito getCredito() {
		return credito;
	}


	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcancelamentocredito != null ? idcancelamentocredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Cancelamentocredito)) {
            return false;
        }
        Cancelamentocredito other = (Cancelamentocredito) object;
        if ((this.idcancelamentocredito == null && other.idcancelamentocredito != null) || (this.idcancelamentocredito != null && !this.idcancelamentocredito.equals(other.idcancelamentocredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cancelamentocredito[ idcancelamentocredito=" + idcancelamentocredito + " ]";
    }
	

}
