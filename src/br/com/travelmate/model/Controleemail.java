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
import javax.persistence.Lob;
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
@Table(name = "controleemail")
public class Controleemail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleemail")
    private Integer idcontroleemail;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 10)
    @Column(name = "hora")
    private String hora;
    @Size(max = 20)
    @Column(name = "assunto")
    private String assunto;
    @Lob
    @Size(max = 16777215)
    @Column(name = "destinatarios")
    private String destinatarios;
    @Lob
    @Size(max = 16777215)
    @Column(name = "corpo")
    private String corpo;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Controleemail() {
    }

    public Controleemail(Integer idcontroleemail) {
        this.idcontroleemail = idcontroleemail;
    }

    public Integer getIdcontroleemail() {
        return idcontroleemail;
    }

    public void setIdcontroleemail(Integer idcontroleemail) {
        this.idcontroleemail = idcontroleemail;
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

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
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
        hash += (idcontroleemail != null ? idcontroleemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controleemail)) {
            return false;
        }
        Controleemail other = (Controleemail) object;
        if ((this.idcontroleemail == null && other.idcontroleemail != null) || (this.idcontroleemail != null && !this.idcontroleemail.equals(other.idcontroleemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controleemail[ idcontroleemail=" + idcontroleemail + " ]";
    }
    
}

