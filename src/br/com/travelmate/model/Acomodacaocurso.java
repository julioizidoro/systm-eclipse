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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "acomodacaocurso")
public class Acomodacaocurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idacomodacaocurso")
    private Integer idacomodacaocurso;
    @JoinColumn(name = "acomodacao_idacomodacao", referencedColumnName = "idacomodacao")
    @OneToOne(optional = false)
    private Acomodacao acomodacao;
    @JoinColumn(name = "cursos_idcursos", referencedColumnName = "idcursos")
    @OneToOne(optional = false)
    private Curso curso;
	
    
    public Acomodacaocurso() {
		
	}


	public Integer getIdacomodacaocurso() {
		return idacomodacaocurso;
	}


	public void setIdacomodacaocurso(Integer idacomodacaocurso) {
		this.idacomodacaocurso = idacomodacaocurso;
	}


	public Acomodacao getAcomodacao() {
		return acomodacao;
	}


	public void setAcomodacao(Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idacomodacaocurso == null) ? 0 : idacomodacaocurso.hashCode());
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
		Acomodacaocurso other = (Acomodacaocurso) obj;
		if (idacomodacaocurso == null) {
			if (other.idacomodacaocurso != null)
				return false;
		} else if (!idacomodacaocurso.equals(other.idacomodacaocurso))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Acomodacaocurso [idacomodacaocurso=" + idacomodacaocurso + "]";
	}
    
    
    
    
    

}
