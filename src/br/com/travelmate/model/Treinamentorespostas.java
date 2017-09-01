package br.com.travelmate.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "treinamentorespostas")
public class Treinamentorespostas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtreinamentorespostas")
    private Integer idtreinamentorespostas;
    @Column(name = "resposta11")
    private String resposta11;
    @Column(name = "resposta12")
    private String resposta12;
    @Column(name = "resposta13")
    private String resposta13;
    @Column(name = "resposta21")
    private String resposta21;
    @Column(name = "resposta22")
    private String resposta22;
    @Column(name = "resposta23")
    private String resposta23;
    @Column(name = "resposta31")
    private String resposta31;
    @Column(name = "resposta32")
    private String resposta32;
    @Column(name = "resposta33")
    private String resposta33;
    @Column(name = "resposta41")
    private String resposta41;
    @Column(name = "resposta42")
    private String resposta42;
    @Column(name = "resposta43")
    private String resposta43;
    @Column(name = "resposta51")
    private String resposta51;
    @Column(name = "resposta52")
    private String resposta52;
    @Column(name = "resposta53")
    private String resposta53;
    @Column(name = "resposta61")
    private String resposta61;
    @Column(name = "resposta62")
    private String resposta62;
    @Column(name = "resposta63")
    private String resposta63;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "treinamentorespostas")
    private List<Treinamentorespostacerta> treinamentorespostacertaList;
    @JoinColumn(name = "treinamentoperguntas_idtreinamentoperguntas", referencedColumnName = "idtreinamentoperguntas")
    @ManyToOne(optional = false)
    private Treinamentoperguntas treinamentoperguntas;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "treinamentorespostas")
    private List<Treinamentorespostausuario> treinamentorespostausuarioList;

    public Treinamentorespostas() {
    }

    public Treinamentorespostas(Integer idtreinamentorespostas) {
        this.idtreinamentorespostas = idtreinamentorespostas;
    }

    public Integer getIdtreinamentorespostas() {
        return idtreinamentorespostas;
    }

    public void setIdtreinamentorespostas(Integer idtreinamentorespostas) {
        this.idtreinamentorespostas = idtreinamentorespostas;
    }  
    
    public String getResposta11() {
		return resposta11;
	}

	public void setResposta11(String resposta11) {
		this.resposta11 = resposta11;
	}

	public String getResposta12() {
		return resposta12;
	}

	public void setResposta12(String resposta12) {
		this.resposta12 = resposta12;
	}

	public String getResposta13() {
		return resposta13;
	}

	public void setResposta13(String resposta13) {
		this.resposta13 = resposta13;
	}

	public String getResposta21() {
		return resposta21;
	}

	public void setResposta21(String resposta21) {
		this.resposta21 = resposta21;
	}

	public String getResposta22() {
		return resposta22;
	}

	public void setResposta22(String resposta22) {
		this.resposta22 = resposta22;
	}

	public String getResposta23() {
		return resposta23;
	}

	public void setResposta23(String resposta23) {
		this.resposta23 = resposta23;
	}

	public String getResposta31() {
		return resposta31;
	}

	public void setResposta31(String resposta31) {
		this.resposta31 = resposta31;
	}

	public String getResposta32() {
		return resposta32;
	}

	public void setResposta32(String resposta32) {
		this.resposta32 = resposta32;
	}

	public String getResposta33() {
		return resposta33;
	}

	public void setResposta33(String resposta33) {
		this.resposta33 = resposta33;
	}

	public String getResposta41() {
		return resposta41;
	}

	public void setResposta41(String resposta41) {
		this.resposta41 = resposta41;
	}

	public String getResposta42() {
		return resposta42;
	}

	public void setResposta42(String resposta42) {
		this.resposta42 = resposta42;
	}

	public String getResposta43() {
		return resposta43;
	}

	public void setResposta43(String resposta43) {
		this.resposta43 = resposta43;
	}

	public String getResposta51() {
		return resposta51;
	}

	public void setResposta51(String resposta51) {
		this.resposta51 = resposta51;
	}

	public String getResposta52() {
		return resposta52;
	}

	public void setResposta52(String resposta52) {
		this.resposta52 = resposta52;
	}

	public String getResposta53() {
		return resposta53;
	}

	public void setResposta53(String resposta53) {
		this.resposta53 = resposta53;
	}

	public String getResposta61() {
		return resposta61;
	}

	public void setResposta61(String resposta61) {
		this.resposta61 = resposta61;
	}

	public String getResposta62() {
		return resposta62;
	}

	public void setResposta62(String resposta62) {
		this.resposta62 = resposta62;
	}

	public String getResposta63() {
		return resposta63;
	}

	public void setResposta63(String resposta63) {
		this.resposta63 = resposta63;
	}

	public List<Treinamentorespostacerta> getTreinamentorespostacertaList() {
        return treinamentorespostacertaList;
    }

    public void setTreinamentorespostacertaList(List<Treinamentorespostacerta> treinamentorespostacertaList) {
        this.treinamentorespostacertaList = treinamentorespostacertaList;
    }

    public Treinamentoperguntas getTreinamentoperguntas() {
        return treinamentoperguntas;
    }

    public void setTreinamentoperguntas(Treinamentoperguntas treinamentoperguntas) {
        this.treinamentoperguntas = treinamentoperguntas;
    }

    public List<Treinamentorespostausuario> getTreinamentorespostausuarioList() {
        return treinamentorespostausuarioList;
    }

    public void setTreinamentorespostausuarioList(List<Treinamentorespostausuario> treinamentorespostausuarioList) {
        this.treinamentorespostausuarioList = treinamentorespostausuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtreinamentorespostas != null ? idtreinamentorespostas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Treinamentorespostas)) {
            return false;
        }
        Treinamentorespostas other = (Treinamentorespostas) object;
        if ((this.idtreinamentorespostas == null && other.idtreinamentorespostas != null) || (this.idtreinamentorespostas != null && !this.idtreinamentorespostas.equals(other.idtreinamentorespostas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Treinamentorespostas[ idtreinamentorespostas=" + idtreinamentorespostas + " ]";
    }
    
}

