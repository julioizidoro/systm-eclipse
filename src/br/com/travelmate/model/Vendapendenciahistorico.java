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
 * @author wolverine
 */
@Entity
@Table(name = "vendapendenciahistorico")
public class Vendapendenciahistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvendapendenciahistorico")
    private Integer idvendapendenciahistorico;
    @Column(name = "datahistorico")
    @Temporal(TemporalType.DATE)
    private Date datahistorico;
    @Size(max = 45)
    @Column(name = "contato")
    private String contato;
    @Lob
    @Size(max = 16777215)
    @Column(name = "assunto")
    private String assunto;
    @JoinColumn(name = "vendapendencia_idvendapendencia", referencedColumnName = "idvendapendencia")
    @ManyToOne(optional = false)
    private Vendapendencia vendapendencia;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Vendapendenciahistorico() {
    }

    public Vendapendenciahistorico(Integer idvendapendenciahistorico) {
        this.idvendapendenciahistorico = idvendapendenciahistorico;
    }

    public Integer getIdvendapendenciahistorico() {
        return idvendapendenciahistorico;
    }

    public void setIdvendapendenciahistorico(Integer idvendapendenciahistorico) {
        this.idvendapendenciahistorico = idvendapendenciahistorico;
    }

    public Date getDatahistorico() {
        return datahistorico;
    }

    public void setDatahistorico(Date datahistorico) {
        this.datahistorico = datahistorico;
    }


    public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Vendapendencia getVendapendencia() {
        return vendapendencia;
    }

    public void setVendapendencia(Vendapendencia vendapendencia) {
        this.vendapendencia = vendapendencia;
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
        hash += (idvendapendenciahistorico != null ? idvendapendenciahistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vendapendenciahistorico)) {
            return false;
        }
        Vendapendenciahistorico other = (Vendapendenciahistorico) object;
        if ((this.idvendapendenciahistorico == null && other.idvendapendenciahistorico != null) || (this.idvendapendenciahistorico != null && !this.idvendapendenciahistorico.equals(other.idvendapendenciahistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Vendapendenciahistorico[ idvendapendenciahistorico=" + idvendapendenciahistorico + " ]";
    }
    
}

