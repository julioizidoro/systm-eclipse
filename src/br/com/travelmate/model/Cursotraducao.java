/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.model;

import java.io.Serializable; 
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn; 
import javax.persistence.OneToOne;
import javax.persistence.Table; 

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "cursotraducao")
public class Cursotraducao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcursotraducao")
    private Integer idcursotraducao;
    @Column(name = "nivelidioma")
    private String nivelidioma; 
    @Column(name = "paisnascimento")
    private String paisnascimento; 
    @Column(name = "turno1")
    private String turno1; 
    @Column(name = "turno2")
    private String turno2; 
    @Column(name = "tipoacomodacao")
    private String tipoacomodacao; 
    @Column(name = "tipoquarto")
    private String tipoquarto; 
    @Column(name = "refeicao")
    private String refeicao; 
    @Column(name = "banheiro")
    private String banheiro; 
    @Column(name = "idiomaestudar")
    private String idiomaestudar; 
    @JoinColumn(name = "curso_idcurso", referencedColumnName = "idcursos")
    @OneToOne(optional = false)
    private Curso curso; 
     
	
	public Cursotraducao() { 
	}

	public Integer getIdcursotraducao() {
		return idcursotraducao;
	}

	public void setIdcursotraducao(Integer idcursotraducao) {
		this.idcursotraducao = idcursotraducao;
	} 

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getNivelidioma() {
		return nivelidioma;
	}

	public void setNivelidioma(String nivelidioma) {
		this.nivelidioma = nivelidioma;
	}

	public String getPaisnascimento() {
		return paisnascimento;
	}

	public void setPaisnascimento(String paisnascimento) {
		this.paisnascimento = paisnascimento;
	}

	public String getTurno1() {
		return turno1;
	}

	public void setTurno1(String turno1) {
		this.turno1 = turno1;
	}

	public String getTurno2() {
		return turno2;
	}

	public void setTurno2(String turno2) {
		this.turno2 = turno2;
	}

	public String getTipoacomodacao() {
		return tipoacomodacao;
	}

	public void setTipoacomodacao(String tipoacomodacao) {
		this.tipoacomodacao = tipoacomodacao;
	}

	public String getTipoquarto() {
		return tipoquarto;
	}

	public String getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(String refeicao) {
		this.refeicao = refeicao;
	}

	public void setTipoquarto(String tipoquarto) {
		this.tipoquarto = tipoquarto;
	}

	public String getBanheiro() {
		return banheiro;
	}

	public void setBanheiro(String banheiro) {
		this.banheiro = banheiro;
	}

	public String getIdiomaestudar() {
		return idiomaestudar;
	}

	public void setIdiomaestudar(String idiomaestudar) {
		this.idiomaestudar = idiomaestudar;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcursotraducao != null ? idcursotraducao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cursotraducao)) {
            return false;
        }
        Cursotraducao other = (Cursotraducao) object;
        if ((this.idcursotraducao == null && other.idcursotraducao != null) || (this.idcursotraducao != null && !this.idcursotraducao.equals(other.idcursotraducao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cursotraducao[ idcursotraducao=" + idcursotraducao + " ]";
    }
    
}
