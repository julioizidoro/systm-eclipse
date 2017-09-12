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
@Table(name = "tisolicitacoeshistorico")
public class Tisolicitacoeshistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtisolicitacoeshistorico")
    private Integer idtisolicitacoeshistorico;
    @Size(max = 45)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "tisolicitacoes_idtisolicitacoes", referencedColumnName = "idtisolicitacoes")
    @ManyToOne(optional = false)
    private Tisolicitacoes tisolicitacoes;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Tisolicitacoeshistorico() {
    }

    public Tisolicitacoeshistorico(Integer idtisolicitacoeshistorico) {
        this.idtisolicitacoeshistorico = idtisolicitacoeshistorico;
    }

    public Integer getIdtisolicitacoeshistorico() {
        return idtisolicitacoeshistorico;
    }

    public void setIdtisolicitacoeshistorico(Integer idtisolicitacoeshistorico) {
        this.idtisolicitacoeshistorico = idtisolicitacoeshistorico;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Tisolicitacoes getTisolicitacoes() {
        return tisolicitacoes;
    }

    public void setTisolicitacoes(Tisolicitacoes tisolicitacoes) {
        this.tisolicitacoes = tisolicitacoes;
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
        hash += (idtisolicitacoeshistorico != null ? idtisolicitacoeshistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tisolicitacoeshistorico)) {
            return false;
        }
        Tisolicitacoeshistorico other = (Tisolicitacoeshistorico) object;
        if ((this.idtisolicitacoeshistorico == null && other.idtisolicitacoeshistorico != null) || (this.idtisolicitacoeshistorico != null && !this.idtisolicitacoeshistorico.equals(other.idtisolicitacoeshistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Tisolicitacoeshistorico[ idtisolicitacoeshistorico=" + idtisolicitacoeshistorico + " ]";
    }
    
}

