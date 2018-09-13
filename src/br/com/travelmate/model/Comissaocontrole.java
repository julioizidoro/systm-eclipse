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
import javax.persistence.Table;

@Entity
@Table(name = "comissaocontrole")
public class Comissaocontrole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcomissaocontrole")
	private Integer idcomissaocontrole;
	@Column(name = "mes")
	private Integer mes;
	@Column(name = "ano")
	private Integer ano;
	@Column(name = "situacao")
	private boolean situacao;
	@JoinColumn(name = "unidadenegocio_idunidadenegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
	
	
	
	public Comissaocontrole() {
	}



	public Integer getIdcomissaocontrole() {
		return idcomissaocontrole;
	}



	public void setIdcomissaocontrole(Integer idcomissaocontrole) {
		this.idcomissaocontrole = idcomissaocontrole;
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



	public boolean isSituacao() {
		return situacao;
	}



	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}



	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}



	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}
	
	
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomissaocontrole != null ? idcomissaocontrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Comissaocontrole)) {
            return false;
        }
        Comissaocontrole other = (Comissaocontrole) object;
        if ((this.idcomissaocontrole == null && other.idcomissaocontrole != null) || (this.idcomissaocontrole != null && !this.idcomissaocontrole.equals(other.idcomissaocontrole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Comissaocontrole[ idcomissaocontrole=" + idcomissaocontrole + " ]";
    }

}
