package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "motivorecinternacional")
public class Motivorecinternacional implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idmotivorecinternacional")
	private Integer idmotivorecinternacional;
	@Size(max = 100)
	@Column(name = "descricao")
	private String descricao;
	
	
	public Motivorecinternacional() {
	}


	public Integer getIdmotivorecinternacional() {
		return idmotivorecinternacional;
	}
 

	public void setIdmotivorecinternacional(Integer idmotivorecinternacional) {
		this.idmotivorecinternacional = idmotivorecinternacional;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idmotivorecinternacional != null ? idmotivorecinternacional.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        if (!(object instanceof Motivorecinternacional)) {
	            return false;
	        }
	        Motivorecinternacional other = (Motivorecinternacional) object;
	        if ((this.idmotivorecinternacional == null && other.idmotivorecinternacional != null) || (this.idmotivorecinternacional != null && !this.idmotivorecinternacional.equals(other.idmotivorecinternacional))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "br.com.travelmate.model.Motivorecinternacional[ idmotivorecinternacional=" + idmotivorecinternacional + " ]";
	    }
	    
	
	
	
	

}
