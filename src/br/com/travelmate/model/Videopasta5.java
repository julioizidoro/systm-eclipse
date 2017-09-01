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
@Table(name = "videopasta5")
public class Videopasta5 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idvideopasta5")
	private Integer idvideopasta5;
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
	@JoinColumn(name = "videopasta4_idvideopasta4", referencedColumnName = "idvideopasta4")
	@ManyToOne(optional = false)
	private Videopasta4 videopasta4;
	
	
	public Videopasta5() {
	}


	public Integer getIdvideopasta5() {
		return idvideopasta5;
	}


	public void setIdvideopasta5(Integer idvideopasta5) {
		this.idvideopasta5 = idvideopasta5;
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


	public Videopasta4 getVideopasta4() {
		return videopasta4;
	}


	public void setVideopasta4(Videopasta4 videopasta4) {
		this.videopasta4 = videopasta4;
	}
	
	
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideopasta5 != null ? idvideopasta5.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Videopasta5)) {
            return false;
        }
        Videopasta5 other = (Videopasta5) object;
        if ((this.idvideopasta5 == null && other.idvideopasta5 != null) || (this.idvideopasta5 != null && !this.idvideopasta5.equals(other.idvideopasta5))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Videopasta5[ idvideopasta5=" + idvideopasta5 + " ]";
    }

}
