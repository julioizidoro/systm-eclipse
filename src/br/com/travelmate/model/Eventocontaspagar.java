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
@Table(name = "eventocontaspagar")
public class Eventocontaspagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ideventocontaspagar")
    private Integer ideventocontaspagar;
    @Size(max = 50)
    @Column(name = "tipoevento")
    private String tipoevento;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 10)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "contaspagar_idcontaspagar", referencedColumnName = "idcontaspagar")
    @ManyToOne(optional = false)
    private Contaspagar contaspagar;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;

    public Eventocontaspagar() {
    }

    public Eventocontaspagar(Integer ideventocontaspagar) {
        this.ideventocontaspagar = ideventocontaspagar;
    }

    public Integer getIdeventocontaspagar() {
        return ideventocontaspagar;
    }

    public void setIdeventocontaspagar(Integer ideventocontaspagar) {
        this.ideventocontaspagar = ideventocontaspagar;
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

    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
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
        hash += (ideventocontaspagar != null ? ideventocontaspagar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Eventocontaspagar)) {
            return false;
        }
        Eventocontaspagar other = (Eventocontaspagar) object;
        if ((this.ideventocontaspagar == null && other.ideventocontaspagar != null) || (this.ideventocontaspagar != null && !this.ideventocontaspagar.equals(other.ideventocontaspagar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Eventocontaspagar[ ideventocontaspagar=" + ideventocontaspagar + " ]";
    }
    
}
