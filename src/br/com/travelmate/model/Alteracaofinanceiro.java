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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "alteracaofinanceiro")
@NamedQueries({
    @NamedQuery(name = "Alteracaofinanceiro.findAll", query = "SELECT a FROM Alteracaofinanceiro a")})
public class Alteracaofinanceiro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalteracaofinanceiro")
    private Integer idalteracaofinanceiro;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "situacao")
    private Boolean situacao;
    
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade= CascadeType.REFRESH)
    private Usuario usuario;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;

    public Alteracaofinanceiro() {
    }

    public Alteracaofinanceiro(Integer idalteracaofinanceiro) {
        this.idalteracaofinanceiro = idalteracaofinanceiro;
    }

    public Integer getIdalteracaofinanceiro() {
        return idalteracaofinanceiro;
    }

    public void setIdalteracaofinanceiro(Integer idalteracaofinanceiro) {
        this.idalteracaofinanceiro = idalteracaofinanceiro;
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

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalteracaofinanceiro != null ? idalteracaofinanceiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Alteracaofinanceiro)) {
            return false;
        }
        Alteracaofinanceiro other = (Alteracaofinanceiro) object;
        if ((this.idalteracaofinanceiro == null && other.idalteracaofinanceiro != null) || (this.idalteracaofinanceiro != null && !this.idalteracaofinanceiro.equals(other.idalteracaofinanceiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Alteracaofinanceiro[ idalteracaofinanceiro=" + idalteracaofinanceiro + " ]";
    }
    
}

