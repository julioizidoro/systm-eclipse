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
import javax.validation.constraints.Size;

@Entity
@Table(name = "tmstar")
public class Tmstar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idtmstar")
	private Integer idtmstar;
	@Size(max = 100)
	@Column(name = "nomeimagem")
	private String nomeimagem;
	@Size(max = 100)
	@Column(name = "nomepdf")
	private String nomepdf;
	@JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
    @ManyToOne(optional = false)
    private Pais pais;
	
	
	public Tmstar() {
	}


	public Integer getIdtmstar() {
		return idtmstar;
	}


	public void setIdtmstar(Integer idtmstar) {
		this.idtmstar = idtmstar;
	}


	public String getNomeimagem() {
		return nomeimagem;
	}


	public void setNomeimagem(String nomeimagem) {
		this.nomeimagem = nomeimagem;
	}


	public String getNomepdf() {
		return nomepdf;
	}


	public void setNomepdf(String nomepdf) {
		this.nomepdf = nomepdf;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idtmstar != null ? idtmstar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tmstar)) {
            return false;
        }
        Tmstar other = (Tmstar) object;
        if ((this.idtmstar == null && other.idtmstar != null) || (this.idtmstar != null && !this.idtmstar.equals(other.idtmstar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Tmstar[ idtmstar=" + idtmstar + " ]";
    }

}
