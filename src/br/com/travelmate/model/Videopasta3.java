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
@Table(name = "videopasta3")
public class Videopasta3 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idvideopasta3")
	private Integer idvideopasta3;
	@Size(max = 100)
	@Column(name = "descricao")
	private String descricao;
	@JoinColumn(name = "videopasta2_idvideopasta2", referencedColumnName = "idvideopasta2")
	@ManyToOne(optional = false)
	private Videopasta2 videopasta2;
	@JoinColumn(name = "videopasta1_idvideopasta1", referencedColumnName = "idvideopasta1")
	@ManyToOne(optional = false)
	private Videopasta1 videopasta1;
	
	
	public Videopasta3() {
	}


	public Integer getIdvideopasta3() {
		return idvideopasta3;
	}


	public void setIdvideopasta3(Integer idvideopasta3) {
		this.idvideopasta3 = idvideopasta3;
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


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideopasta3 != null ? idvideopasta3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Videopasta3)) {
            return false;
        }
        Videopasta3 other = (Videopasta3) object;
        if ((this.idvideopasta3 == null && other.idvideopasta3 != null) || (this.idvideopasta3 != null && !this.idvideopasta3.equals(other.idvideopasta3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Videopasta3[ idvideopasta3=" + idvideopasta3 + " ]";
    }

}
