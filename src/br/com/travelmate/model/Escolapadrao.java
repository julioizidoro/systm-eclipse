package br.com.travelmate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "escolapadrao")
public class Escolapadrao implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idescolapadrao")
    private Integer idescolapadrao;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descricaoescola")
    private String descricaoescola;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descricaopais")
    private String descricaopais;
	
    public Escolapadrao() {
		
	}

	public Integer getIdescolapadrao() {
		return idescolapadrao;
	}

	public void setIdescolapadrao(Integer idescolapadrao) {
		this.idescolapadrao = idescolapadrao;
	}

	public String getDescricaoescola() {
		return descricaoescola;
	}

	public void setDescricaoescola(String descricaoescola) {
		this.descricaoescola = descricaoescola;
	}

	public String getDescricaopais() {
		return descricaopais;
	}

	public void setDescricaopais(String descricaopais) {
		this.descricaopais = descricaopais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idescolapadrao == null) ? 0 : idescolapadrao.hashCode());
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
		Escolapadrao other = (Escolapadrao) obj;
		if (idescolapadrao == null) {
			if (other.idescolapadrao != null)
				return false;
		} else if (!idescolapadrao.equals(other.idescolapadrao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Escolapadrao [idescolapadrao=" + idescolapadrao + "]";
	}
    
    
    
    
	

}
