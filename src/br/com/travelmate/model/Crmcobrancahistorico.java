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
@Table(name = "crmcobrancahistorico")
public class Crmcobrancahistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcrmcobrancahistorico")
    private Integer idcrmcobrancahistorico;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 30)
    @Column(name = "tipocontato")
    private String tipocontato;
    @Lob
    @Size(max = 16777215)
    @Column(name = "historico")
    private String historico;
    @Column(name = "proximocontato")
    @Temporal(TemporalType.DATE)
    private Date proximocontato;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Crmcobrancahistorico() {
    }

    public Crmcobrancahistorico(Integer idcrmcobrancahistorico) {
        this.idcrmcobrancahistorico = idcrmcobrancahistorico;
    }

    public Integer getIdcrmcobrancahistorico() {
        return idcrmcobrancahistorico;
    }

    public void setIdcrmcobrancahistorico(Integer idcrmcobrancahistorico) {
        this.idcrmcobrancahistorico = idcrmcobrancahistorico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipocontato() {
        return tipocontato;
    }

    public void setTipocontato(String tipocontato) {
        this.tipocontato = tipocontato;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Date getProximocontato() {
        return proximocontato;
    }

    public void setProximocontato(Date proximocontato) {
        this.proximocontato = proximocontato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        hash += (idcrmcobrancahistorico != null ? idcrmcobrancahistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Crmcobrancahistorico)) {
            return false;
        }
        Crmcobrancahistorico other = (Crmcobrancahistorico) object;
        if ((this.idcrmcobrancahistorico == null && other.idcrmcobrancahistorico != null) || (this.idcrmcobrancahistorico != null && !this.idcrmcobrancahistorico.equals(other.idcrmcobrancahistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Crmcobrancahistorico[ idcrmcobrancahistorico=" + idcrmcobrancahistorico + " ]";
    }
    
}

