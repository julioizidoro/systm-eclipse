package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "heparceiros")
public class Heparceiros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idheparceiros")
    private Integer idheparceiros;
    @Column(name = "pathway")
    private boolean pathway;
    @Size(max = 100)
    @Column(name = "pathwaycurso")
    private String pathwaycurso;
    @Column(name = "numerosemanas")
    private Integer numerosemanas;
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;  
    @Column(name = "datatermino")
    @Temporal(TemporalType.DATE)
    private Date datatermino;
    @Size(max = 100)
    @Column(name = "hecurso")
    private String hecurso;
    @Size(max = 20)
    @Column(name = "hecodigo")
    private String hecodigo;
    @Size(max = 7)
    @Column(name = "mesano")
    private String mesano;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "he_idhe", referencedColumnName = "idhe")
    @ManyToOne(optional = false)
    private He he;
    @Transient
    private String temPathWay;
    
    
	public Heparceiros() {
		
	}


	public Integer getIdheparceiros() {
		return idheparceiros;
	}


	public void setIdheparceiros(Integer idheparceiros) {
		this.idheparceiros = idheparceiros;
	}


	public boolean isPathway() {
		return pathway;
	}


	public void setPathway(boolean pathway) {
		this.pathway = pathway;
	}


	public String getPathwaycurso() {
		return pathwaycurso;
	}


	public void setPathwaycurso(String pathwaycurso) {
		this.pathwaycurso = pathwaycurso;
	}


	public Integer getNumerosemanas() {
		return numerosemanas;
	}


	public void setNumerosemanas(Integer numerosemanas) {
		this.numerosemanas = numerosemanas;
	}


	public Date getDatainicio() {
		return datainicio;
	}


	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}


	public Date getDatatermino() {
		return datatermino;
	}


	public void setDatatermino(Date datatermino) {
		this.datatermino = datatermino;
	}


	public String getHecurso() {
		return hecurso;
	}


	public void setHecurso(String hecurso) {
		this.hecurso = hecurso;
	}


	public String getHecodigo() {
		return hecodigo;
	}


	public void setHecodigo(String hecodigo) {
		this.hecodigo = hecodigo;
	}


	public String getMesano() {
		return mesano;
	}


	public void setMesano(String mesano) {
		this.mesano = mesano;
	}


	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}


	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}


	public He getHe() {
		return he;
	}


	public void setHe(He he) {
		this.he = he;
	}


	public String getTemPathWay() {
		return temPathWay;
	}


	public void setTemPathWay(String temPathWay) {
		this.temPathWay = temPathWay;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idheparceiros != null ? idheparceiros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Heparceiros)) {
            return false;
        }
        Heparceiros other = (Heparceiros) object;
        if ((this.idheparceiros == null && other.idheparceiros != null) || (this.idheparceiros != null && !this.idheparceiros.equals(other.idheparceiros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Heparceiros[ idheparceiros=" + idheparceiros + " ]";
    }
	
}
    