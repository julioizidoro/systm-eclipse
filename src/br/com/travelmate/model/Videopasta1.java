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
@Table(name = "videopasta1")
public class Videopasta1 implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideopasta1")
    private Integer idvideopasta1;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;
    

    

	public Integer getIdvideopasta1() {
		return idvideopasta1;
	}

	public void setIdvideopasta1(Integer idvideopasta1) {
		this.idvideopasta1 = idvideopasta1;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideopasta1 != null ? idvideopasta1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Videopasta1)) {
            return false;
        }
        Videopasta1 other = (Videopasta1) object;
        if ((this.idvideopasta1 == null && other.idvideopasta1 != null) || (this.idvideopasta1 != null && !this.idvideopasta1.equals(other.idvideopasta1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Videopasta1[ idvideopasta1=" + idvideopasta1 + " ]";
    }
}
