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

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "treinamentorespostacerta")
public class Treinamentorespostacerta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtreinamentorespostacerta")
    private Integer idtreinamentorespostacerta;
    @JoinColumn(name = "treinamentorespostas_idtreinamentorespostas", referencedColumnName = "idtreinamentorespostas")
    @ManyToOne(optional = false)
    private Treinamentorespostas treinamentorespostas;
    @Column(name = "respcerta1")
    private String respcerta1;
    @Column(name = "respcerta2")
    private String respcerta2;
    @Column(name = "respcerta3")
    private String respcerta3;
    @Column(name = "respcerta4")
    private String respcerta4;
    @Column(name = "respcerta5")
    private String respcerta5;
    @Column(name = "respcerta6")
    private String respcerta6;

    public Treinamentorespostacerta() {
    }

    public Treinamentorespostacerta(Integer idtreinamentorespostacerta) {
        this.idtreinamentorespostacerta = idtreinamentorespostacerta;
    }

    public Integer getIdtreinamentorespostacerta() {
        return idtreinamentorespostacerta;
    }

    public void setIdtreinamentorespostacerta(Integer idtreinamentorespostacerta) {
        this.idtreinamentorespostacerta = idtreinamentorespostacerta;
    }

    public Treinamentorespostas getTreinamentorespostas() {
        return treinamentorespostas;
    }

    public void setTreinamentorespostas(Treinamentorespostas treinamentorespostas) {
        this.treinamentorespostas = treinamentorespostas;
    }

    public String getRespcerta1() {
		return respcerta1;
	}

	public void setRespcerta1(String respcerta1) {
		this.respcerta1 = respcerta1;
	}

	public String getRespcerta2() {
		return respcerta2;
	}

	public void setRespcerta2(String respcerta2) {
		this.respcerta2 = respcerta2;
	}

	public String getRespcerta3() {
		return respcerta3;
	}

	public void setRespcerta3(String respcerta3) {
		this.respcerta3 = respcerta3;
	}

	public String getRespcerta4() {
		return respcerta4;
	}

	public void setRespcerta4(String respcerta4) {
		this.respcerta4 = respcerta4;
	}

	public String getRespcerta5() {
		return respcerta5;
	}

	public void setRespcerta5(String respcerta5) {
		this.respcerta5 = respcerta5;
	}

	public String getRespcerta6() {
		return respcerta6;
	}

	public void setRespcerta6(String respcerta6) {
		this.respcerta6 = respcerta6;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idtreinamentorespostacerta != null ? idtreinamentorespostacerta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Treinamentorespostacerta)) {
            return false;
        }
        Treinamentorespostacerta other = (Treinamentorespostacerta) object;
        if ((this.idtreinamentorespostacerta == null && other.idtreinamentorespostacerta != null) || (this.idtreinamentorespostacerta != null && !this.idtreinamentorespostacerta.equals(other.idtreinamentorespostacerta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Treinamentorespostacerta[ idtreinamentorespostacerta=" + idtreinamentorespostacerta + " ]";
    }
    
}
