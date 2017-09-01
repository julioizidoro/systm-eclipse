package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "avisodocsusuario")
public class Avisodocsusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idavisodocsusuario")
    private Integer idavisodocsusuario;
    @Column(name = "visualizou")
    private Boolean visualizou;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @JoinColumn(name = "avisodocs_idavisodocs", referencedColumnName = "idavisodocs")
    @ManyToOne(optional = false)
    private Avisodocs avisodocs;

    public Avisodocsusuario() {
    }

    public Avisodocsusuario(Integer idavisodocsusuario) {
        this.idavisodocsusuario = idavisodocsusuario;
    }

    public Integer getIdavisodocsusuario() {
        return idavisodocsusuario;
    }

    public void setIdavisodocsusuario(Integer idavisodocsusuario) {
        this.idavisodocsusuario = idavisodocsusuario;
    }

    public Boolean getVisualizou() {
        return visualizou;
    }

    public void setVisualizou(Boolean visualizou) {
        this.visualizou = visualizou;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Avisodocs getAvisodocs() {
        return avisodocs;
    }

    public void setAvisodocs(Avisodocs avisodocs) {
        this.avisodocs = avisodocs;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idavisodocsusuario != null ? idavisodocsusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Avisodocsusuario)) {
            return false;
        }
        Avisodocsusuario other = (Avisodocsusuario) object;
        if ((this.idavisodocsusuario == null && other.idavisodocsusuario != null) || (this.idavisodocsusuario != null && !this.idavisodocsusuario.equals(other.idavisodocsusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Avisodocsusuario[ idavisodocsusuario=" + idavisodocsusuario + " ]";
    }
    
}

