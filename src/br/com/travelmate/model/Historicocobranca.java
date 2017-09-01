/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Wolverine
 */
@Entity
@Table(name = "historicocobranca")
@NamedQueries({
    @NamedQuery(name = "Historicocobranca.findAll", query = "SELECT h FROM Historicocobranca h")})
public class Historicocobranca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhistoricocobranca")
    private Integer idhistoricocobranca;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 100)
    @Column(name = "contato")
    private String contato;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "assunto")
    private String assunto;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @JoinColumn(name = "cobranca_idcobranca", referencedColumnName = "idcobranca")
    @ManyToOne(optional = false)
    private Cobranca cobranca;

    public Historicocobranca() {
    }

    public Historicocobranca(Integer idhistoricocobranca) {
        this.idhistoricocobranca = idhistoricocobranca;
    }

    public Integer getIdhistoricocobranca() {
        return idhistoricocobranca;
    }

    public void setIdhistoricocobranca(Integer idhistoricocobranca) {
        this.idhistoricocobranca = idhistoricocobranca;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistoricocobranca != null ? idhistoricocobranca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Historicocobranca)) {
            return false;
        }
        Historicocobranca other = (Historicocobranca) object;
        if ((this.idhistoricocobranca == null && other.idhistoricocobranca != null) || (this.idhistoricocobranca != null && !this.idhistoricocobranca.equals(other.idhistoricocobranca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Historicocobranca[ idhistoricocobranca=" + idhistoricocobranca + " ]";
    }
    
}
