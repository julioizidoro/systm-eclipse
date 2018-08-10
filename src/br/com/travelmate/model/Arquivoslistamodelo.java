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
import javax.validation.constraints.Size;

@Entity
@Table(name = "arquivoslistamodelo")
public class Arquivoslistamodelo implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivoslistamodelo")
    private Integer idarquivoslistamodelo;
    @Column(name = "ordem")
    private Integer ordem;
    @Size(max = 100)
    @Column(name = "classe")
    private String classe;
    @JoinColumn(name = "tipoarquivoproduto_idtipoarquivoproduto", referencedColumnName = "idtipoarquivoproduto")
    @ManyToOne(optional = false)
    private Tipoarquivoproduto tipoarquivoproduto;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Fornecedorcidade fornecedorcidade;
    

	public Arquivoslistamodelo() {
		
	}


	public Integer getIdarquivoslistamodelo() {
		return idarquivoslistamodelo;
	}


	public void setIdarquivoslistamodelo(Integer idarquivoslistamodelo) {
		this.idarquivoslistamodelo = idarquivoslistamodelo;
	}


	public Integer getOrdem() {
		return ordem;
	}


	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}


	public String getClasse() {
		return classe;
	}


	public void setClasse(String classe) {
		this.classe = classe;
	}


	public Tipoarquivoproduto getTipoarquivoproduto() {
		return tipoarquivoproduto;
	}


	public void setTipoarquivoproduto(Tipoarquivoproduto tipoarquivoproduto) {
		this.tipoarquivoproduto = tipoarquivoproduto;
	}


	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}


	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idarquivoslistamodelo == null) ? 0 : idarquivoslistamodelo.hashCode());
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
		Arquivoslistamodelo other = (Arquivoslistamodelo) obj;
		if (idarquivoslistamodelo == null) {
			if (other.idarquivoslistamodelo != null)
				return false;
		} else if (!idarquivoslistamodelo.equals(other.idarquivoslistamodelo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Arquivoslistamodelo [idarquivoslistamodelo=" + idarquivoslistamodelo + "]";
	}
	
	

}
