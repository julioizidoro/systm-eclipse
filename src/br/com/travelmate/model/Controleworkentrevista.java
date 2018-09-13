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
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controleworkentrevista")
public class Controleworkentrevista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleworkentrevista")
    private Integer idcontroleworkentrevista;
    @Size(max = 15)
    @Column(name = "situacaoempreagador")
    private String situacaoempreagador;
    @Size(max = 15)
    @Column(name = "situacaosponsor")
    private String situacaosponsor;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 8)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "controlework_idcontroleWork", referencedColumnName = "idcontroleWork")
    @ManyToOne(optional = false)
    private Controlework controlework;
    @JoinColumn(name = "workempregador_idworkempregador", referencedColumnName = "idworkempregador")
    @ManyToOne(optional = false)
    private Workempregador workempregador;
    @JoinColumn(name = "worksponsor_idworksponsor", referencedColumnName = "idworksponsor")
    @ManyToOne(optional = false)
    private Worksponsor worksponsor;

    public Controleworkentrevista() {
    }

    public Controleworkentrevista(Integer idcontroleworkentrevista) {
        this.idcontroleworkentrevista = idcontroleworkentrevista;
    }

    public Integer getIdcontroleworkentrevista() {
        return idcontroleworkentrevista;
    }

    public void setIdcontroleworkentrevista(Integer idcontroleworkentrevista) {
        this.idcontroleworkentrevista = idcontroleworkentrevista;
    }

    public String getSituacaoempreagador() {
        return situacaoempreagador;
    }

    public void setSituacaoempreagador(String situacaoempreagador) {
        this.situacaoempreagador = situacaoempreagador;
    }

    public String getSituacaosponsor() {
        return situacaosponsor;
    }

    public void setSituacaosponsor(String situacaosponsor) {
        this.situacaosponsor = situacaosponsor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public Worksponsor getWorksponsor() {
        return worksponsor;
    }

    public void setWorksponsor(Worksponsor worksponsor) {
        this.worksponsor = worksponsor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleworkentrevista != null ? idcontroleworkentrevista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controleworkentrevista)) {
            return false;
        }
        Controleworkentrevista other = (Controleworkentrevista) object;
        if ((this.idcontroleworkentrevista == null && other.idcontroleworkentrevista != null) || (this.idcontroleworkentrevista != null && !this.idcontroleworkentrevista.equals(other.idcontroleworkentrevista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleworkentrevista[ idcontroleworkentrevista=" + idcontroleworkentrevista + " ]";
    }
    
}

