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
@Table(name = "videopasta4")
public class Videopasta4 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idvideopasta4")
	private Integer idvideopasta4;
	@Size(max = 100)
	@Column(name = "descricao")
	private String descricao;
	@JoinColumn(name = "videopasta2_idvideopasta2", referencedColumnName = "idvideopasta2")
	@ManyToOne(optional = false)
	private Videopasta2 videopasta2;
	@JoinColumn(name = "videopasta1_idvideopasta1", referencedColumnName = "idvideopasta1")
	@ManyToOne(optional = false)
	private Videopasta1 videopasta1;
	@JoinColumn(name = "videopasta3_idvideopasta3", referencedColumnName = "idvideopasta3")
	@ManyToOne(optional = false)
	private Videopasta3 videopasta3;
	
	
	public Videopasta4() {
	}


	


	public Integer getIdvideopasta4() {
		return idvideopasta4;
	}





	public void setIdvideopasta4(Integer idvideopasta4) {
		this.idvideopasta4 = idvideopasta4;
	}





	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}


	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}
	
	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}


	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}
	
	
	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}


	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideopasta4 != null ? idvideopasta4.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Videopasta4)) {
            return false;
        }
        Videopasta4 other = (Videopasta4) object;
        if ((this.idvideopasta4 == null && other.idvideopasta4 != null) || (this.idvideopasta4 != null && !this.idvideopasta4.equals(other.idvideopasta4))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Videopasta4[ idvideopasta4=" + idvideopasta4 + " ]";
    }

}
