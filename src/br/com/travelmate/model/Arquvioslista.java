package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "arquvioslista")
public class Arquvioslista implements Serializable{
	
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "idarquvioslista")
	    private Integer idarquvioslista;
		@JoinColumn(name = "arquivos_idarquivos", referencedColumnName = "idarquivos")
	    @ManyToOne(optional = false)
	    private Arquivos arquivos;
	    @JoinColumn(name = "arquivoslistamodelo_idarquivoslistamodelo", referencedColumnName = "idarquivoslistamodelo")
	    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	    private Arquivoslistamodelo arquivoslistamodelo;
	    @Transient
	    private String imgSituacao;
	    @Transient
	    private String corArquivo;

	public Arquvioslista() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdarquvioslista() {
		return idarquvioslista;
	}

	public void setIdarquvioslista(Integer idarquvioslista) {
		this.idarquvioslista = idarquvioslista;
	}

	public Arquivos getArquivos() {
		return arquivos;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	}

	public Arquivoslistamodelo getArquivoslistamodelo() {
		return arquivoslistamodelo;
	}

	public void setArquivoslistamodelo(Arquivoslistamodelo arquivoslistamodelo) {
		this.arquivoslistamodelo = arquivoslistamodelo;
	}

	public String getImgSituacao() {
		return imgSituacao;
	}

	public void setImgSituacao(String imgSituacao) {
		this.imgSituacao = imgSituacao;
	}

	public String getCorArquivo() {
		return corArquivo;
	}

	public void setCorArquivo(String corArquivo) {
		this.corArquivo = corArquivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idarquvioslista == null) ? 0 : idarquvioslista.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arquvioslista other = (Arquvioslista) obj;
		if (idarquvioslista == null) {
			if (other.idarquvioslista != null)
				return false;
		} else if (!idarquvioslista.equals(other.idarquvioslista))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "arquvioslista [idarquvioslista=" + idarquvioslista + "]";
	}
	
	

}
