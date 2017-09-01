package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "remessaarquivo")
public class Remessaarquivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idremessaarquivo")
    private Integer idremessaarquivo;
    @Column(name = "dataenvio")
    @Temporal(TemporalType.DATE)
    private Date dataenvio;
    @Size(max = 20)
    @Column(name = "nomearquivo")
    private String nomearquivo;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "remessaarquivo")
    private List<Remessacontas> remessacontasList;

    public Remessaarquivo() {
    }

    public Remessaarquivo(Integer idremessaarquivo) {
        this.idremessaarquivo = idremessaarquivo;
    }

    public Integer getIdremessaarquivo() {
        return idremessaarquivo;
    }

    public void setIdremessaarquivo(Integer idremessaarquivo) {
        this.idremessaarquivo = idremessaarquivo;
    }

    public Date getDataenvio() {
        return dataenvio;
    }

    public void setDataenvio(Date dataenvio) {
        this.dataenvio = dataenvio;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Remessacontas> getRemessacontasList() {
        return remessacontasList;
    }

    public void setRemessacontasList(List<Remessacontas> remessacontasList) {
        this.remessacontasList = remessacontasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idremessaarquivo != null ? idremessaarquivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Remessaarquivo)) {
            return false;
        }
        Remessaarquivo other = (Remessaarquivo) object;
        if ((this.idremessaarquivo == null && other.idremessaarquivo != null) || (this.idremessaarquivo != null && !this.idremessaarquivo.equals(other.idremessaarquivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Remessaarquivo[ idremessaarquivo=" + idremessaarquivo + " ]";
    }
    
}

