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
@Table(name = "vendaspacote")
public class Vendaspacote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvendaspacote")
    private Integer idvendaspacote;
    @JoinColumn(name = "cursospacote_idcursospacote", referencedColumnName = "idcursospacote")
    @OneToOne(optional = false)
    private Cursospacote cursospacote;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @OneToOne(optional = false)
    private Vendas vendas;

    public Vendaspacote() {
    }

    public Vendaspacote(Integer idvendaspacote) {
        this.idvendaspacote = idvendaspacote;
    }

    public Integer getIdvendaspacote() {
        return idvendaspacote;
    }

    public void setIdvendaspacote(Integer idvendaspacote) {
        this.idvendaspacote = idvendaspacote;
    }

    public Cursospacote getCursospacote() {
        return cursospacote;
    }

    public void setCursospacote(Cursospacote cursospacote) {
        this.cursospacote = cursospacote;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendaspacote != null ? idvendaspacote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vendaspacote)) {
            return false;
        }
        Vendaspacote other = (Vendaspacote) object;
        if ((this.idvendaspacote == null && other.idvendaspacote != null) || (this.idvendaspacote != null && !this.idvendaspacote.equals(other.idvendaspacote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Vendaspacote[ idvendaspacote=" + idvendaspacote + " ]";
    }
    
}

