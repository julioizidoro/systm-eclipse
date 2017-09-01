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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "eventoscontasreceber")
public class Eventocontasreceber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ideventoscontasreceber")
    private Integer ideventoscontasreceber;
    @Size(max = 50)
    @Column(name = "tipoevento")
    private String tipoevento;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 8)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @ManyToOne(optional = false)
    private Contasreceber contasreceber;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;

    public Eventocontasreceber() {
    }

    public Eventocontasreceber(Integer ideventoscontasreceber) {
        this.ideventoscontasreceber = ideventoscontasreceber;
    }

    public Integer getIdeventoscontasreceber() {
        return ideventoscontasreceber;
    }

    public void setIdeventoscontasreceber(Integer ideventoscontasreceber) {
        this.ideventoscontasreceber = ideventoscontasreceber;
    }

    public String getTipoevento() {
        return tipoevento;
    }

    public void setTipoevento(String tipoevento) {
        this.tipoevento = tipoevento;
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

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideventoscontasreceber != null ? ideventoscontasreceber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Eventocontasreceber)) {
            return false;
        }
        Eventocontasreceber other = (Eventocontasreceber) object;
        if ((this.ideventoscontasreceber == null && other.ideventoscontasreceber != null) || (this.ideventoscontasreceber != null && !this.ideventoscontasreceber.equals(other.ideventoscontasreceber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Eventoscontasreceber[ ideventoscontasreceber=" + ideventoscontasreceber + " ]";
    }
    
}
