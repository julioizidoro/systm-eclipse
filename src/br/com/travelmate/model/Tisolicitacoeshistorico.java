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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
    @NamedQuery(name = "Tisolicitacoeshistorico.findAll", query = "SELECT t FROM Tisolicitacoeshistorico t")})
public class Tisolicitacoeshistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtisolicitacoeshistorico")
    private Integer idtisolicitacoeshistorico;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
