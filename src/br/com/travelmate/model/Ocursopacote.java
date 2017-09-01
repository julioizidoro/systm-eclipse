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
 * @author wolverine
 */
@Entity
@Table(name = "ocursopacote")
public class Ocursopacote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idocursopacote")
    private Integer idocursopacote;
    @JoinColumn(name = "ocurso_idocurso", referencedColumnName = "idocurso")
    @OneToOne(optional = false)
    private Ocurso ocurso;
    @JoinColumn(name = "cursospacote_idcursospacote", referencedColumnName = "idcursospacote")
    @OneToOne(optional = false)
    private Cursospacote cursospacote;

    public Ocursopacote() {
    }

    public Ocursopacote(Integer idocursopacote) {
        this.idocursopacote = idocursopacote;
    }

    public Integer getIdocursopacote() {
        return idocursopacote;
    }

    public void setIdocursopacote(Integer idocursopacote) {
        this.idocursopacote = idocursopacote;
    }

    public Ocurso getOcurso() {
        return ocurso;
    }

    public void setOcurso(Ocurso ocurso) {
        this.ocurso = ocurso;
    }

    public Cursospacote getCursospacote() {
        return cursospacote;
    }

    public void setCursospacote(Cursospacote cursospacote) {
        this.cursospacote = cursospacote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idocursopacote != null ? idocursopacote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ocursopacote)) {
            return false;
        }
        Ocursopacote other = (Ocursopacote) object;
        if ((this.idocursopacote == null && other.idocursopacote != null) || (this.idocursopacote != null && !this.idocursopacote.equals(other.idocursopacote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Ocursopacote[ idocursopacote=" + idocursopacote + " ]";
    }
    
}

