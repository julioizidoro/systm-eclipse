package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "avisodocs")

public class Avisodocs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idavisodocs")
    private Integer idavisodocs;
    @Size(max = 200)
    @Column(name = "nomearquivo")
    private String nomearquivo;
    @Size(max = 200)
    @Column(name = "caminhoarquivo")
    private String caminhoarquivo;
    @Size(max = 200)
    @Column(name = "nomesalvo")
    private String nomesalvo;
    

    public Avisodocs() {
    }

    public Avisodocs(Integer idavisodocs) {
        this.idavisodocs = idavisodocs;
    }

    

    public Integer getIdavisodocs() {
		return idavisodocs;
	}

	public void setIdavisodocs(Integer idavisodocs) {
		this.idavisodocs = idavisodocs;
	}

	public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public String getCaminhoarquivo() {
        return caminhoarquivo;
    }

    public void setCaminhoarquivo(String caminhoarquivo) {
        this.caminhoarquivo = caminhoarquivo;
    }

    public String getNomesalvo() {
        return nomesalvo;
    }

    public void setNomesalvo(String nomesalvo) {
        this.nomesalvo = nomesalvo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idavisodocs != null ? idavisodocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Avisodocs)) {
            return false;
        }
        Avisodocs other = (Avisodocs) object;
        if ((this.idavisodocs == null && other.idavisodocs != null) || (this.idavisodocs != null && !this.idavisodocs.equals(other.idavisodocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Avisodocs[ idavisodocs=" + idavisodocs + " ]";
    }
    
}

