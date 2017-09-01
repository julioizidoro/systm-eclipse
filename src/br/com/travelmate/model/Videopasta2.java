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
@Table(name = "videopasta2")
public class Videopasta2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideopasta2")
    private Integer idvideopasta2;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "videopasta1_idvideopasta1", referencedColumnName = "idvideopasta1")
    @ManyToOne(optional = false)
    private Videopasta1 videopasta1;
    
    
    public Videopasta2() {
	}


	public Integer getIdvideopasta2() {
		return idvideopasta2;
	}


	public void setIdvideopasta2(Integer idvideopasta2) {
		this.idvideopasta2 = idvideopasta2;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
    
	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}


	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideopasta2 != null ? idvideopasta2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Videopasta2)) {
            return false;
        }
        Videopasta2 other = (Videopasta2) object;
        if ((this.idvideopasta2 == null && other.idvideopasta2 != null) || (this.idvideopasta2 != null && !this.idvideopasta2.equals(other.idvideopasta2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Videopasta2[ idvideopasta2=" + idvideopasta2 + " ]";
    }

}
