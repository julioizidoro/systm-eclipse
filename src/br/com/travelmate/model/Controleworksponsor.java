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

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controleworksponsor")
public class Controleworksponsor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleworksponsor")
    private Integer idcontroleworksponsor;
    @JoinColumn(name = "controlework_idcontroleWork", referencedColumnName = "idcontroleWork")
    @OneToOne(optional = false)
    private Controlework controlework;
    @JoinColumn(name = "worksponsor_idworksponsor", referencedColumnName = "idworksponsor")
    @ManyToOne(optional = false)
    private Worksponsor worksponsor;

    public Controleworksponsor() {
    }

    public Controleworksponsor(Integer idcontroleworksponsor) {
        this.idcontroleworksponsor = idcontroleworksponsor;
    }

    public Integer getIdcontroleworksponsor() {
        return idcontroleworksponsor;
    }

    public void setIdcontroleworksponsor(Integer idcontroleworksponsor) {
        this.idcontroleworksponsor = idcontroleworksponsor;
    }

    public Controlework getControlework() {
        return controlework;
    }

    public void setControlework(Controlework controlework) {
        this.controlework = controlework;
    }

    public Worksponsor getWorksponsor() {
        return worksponsor;
    }

    public void setWorksponsor(Worksponsor worksponsor) {
        this.worksponsor = worksponsor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleworksponsor != null ? idcontroleworksponsor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controleworksponsor)) {
            return false;
        }
        Controleworksponsor other = (Controleworksponsor) object;
        if ((this.idcontroleworksponsor == null && other.idcontroleworksponsor != null) || (this.idcontroleworksponsor != null && !this.idcontroleworksponsor.equals(other.idcontroleworksponsor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleworksponsor[ idcontroleworksponsor=" + idcontroleworksponsor + " ]";
    }
    
}

