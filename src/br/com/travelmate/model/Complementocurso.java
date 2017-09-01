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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "complementocurso")
public class Complementocurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomplementocurso")
    private Integer idcomplementocurso;
    @Size(max = 5)
    @Column(name = "cargahoraria")
    private String cargahoraria;
    @Size(max = 50)
    @Column(name = "tipocargahoraria")
    private String tipocargahoraria;
    @Size(max = 50)
    @Column(name = "turno")
    private String turno;
    @JoinColumn(name = "coprodutos_idcoprodutos", referencedColumnName = "idcoprodutos", updatable=true)
    @OneToOne(optional = false)
    private Coprodutos coprodutos;

    public Complementocurso() {
    }

    public Complementocurso(Integer idcomplementocurso) {
        this.idcomplementocurso = idcomplementocurso;
    }

    public Integer getIdcomplementocurso() {
        return idcomplementocurso;
    }

    public void setIdcomplementocurso(Integer idcomplementocurso) {
        this.idcomplementocurso = idcomplementocurso;
    }

    public String getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(String cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public String getTipocargahoraria() {
        return tipocargahoraria;
    }

    public void setTipocargahoraria(String tipocargahoraria) {
        this.tipocargahoraria = tipocargahoraria;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Coprodutos getCoprodutos() {
        return coprodutos;
    }

    public void setCoprodutos(Coprodutos coprodutos) {
        this.coprodutos = coprodutos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomplementocurso != null ? idcomplementocurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Complementocurso)) {
            return false;
        }
        Complementocurso other = (Complementocurso) object;
        if ((this.idcomplementocurso == null && other.idcomplementocurso != null) || (this.idcomplementocurso != null && !this.idcomplementocurso.equals(other.idcomplementocurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Complementocurso[ idcomplementocurso=" + idcomplementocurso + " ]";
    }
    
}

