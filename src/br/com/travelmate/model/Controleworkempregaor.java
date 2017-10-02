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
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controleworkempregaor")
public class Controleworkempregaor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleworkempregaor")
    private Integer idcontroleworkempregaor;
    @Size(max = 50)
    @Column(name = "colocacao")
    private String colocacao;
    @JoinColumn(name = "controlework_idcontroleWork", referencedColumnName = "idcontroleWork")
    @OneToOne(optional = false)
    private Controlework controlework;
    @JoinColumn(name = "workempregador_idworkempregador", referencedColumnName = "idworkempregador")
    @ManyToOne(optional = false)
    private Workempregador workempregador;

    public Controleworkempregaor() {
    }

    public Controleworkempregaor(Integer idcontroleworkempregaor) {
        this.idcontroleworkempregaor = idcontroleworkempregaor;
    }

    public Integer getIdcontroleworkempregaor() {
        return idcontroleworkempregaor;
    }

    public void setIdcontroleworkempregaor(Integer idcontroleworkempregaor) {
        this.idcontroleworkempregaor = idcontroleworkempregaor;
    }

    public String getColocacao() {
        return colocacao;
    }

    public void setColocacao(String colocacao) {
        this.colocacao = colocacao;
    }

    public Controlework getControlework() {
        return controlework;
    }

    public void setControlework(Controlework controlework) {
        this.controlework = controlework;
    }

    public Workempregador getWorkempregador() {
        return workempregador;
    }

    public void setWorkempregador(Workempregador workempregador) {
        this.workempregador = workempregador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleworkempregaor != null ? idcontroleworkempregaor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controleworkempregaor)) {
            return false;
        }
        Controleworkempregaor other = (Controleworkempregaor) object;
        if ((this.idcontroleworkempregaor == null && other.idcontroleworkempregaor != null) || (this.idcontroleworkempregaor != null && !this.idcontroleworkempregaor.equals(other.idcontroleworkempregaor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleworkempregaor[ idcontroleworkempregaor=" + idcontroleworkempregaor + " ]";
    }
    
}
