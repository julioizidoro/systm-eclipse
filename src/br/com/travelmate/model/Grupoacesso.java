/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "grupoacesso")
public class Grupoacesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgrupoAcesso")
    private Integer idgrupoAcesso;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "acesso_idacesso", referencedColumnName = "idacesso")
    @ManyToOne(optional = false)
    private Acesso acesso;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "grupoacesso")
    private List<Usuario> usuarioList;

    public Grupoacesso() {
    }

    public Grupoacesso(Integer idgrupoAcesso) {
        this.idgrupoAcesso = idgrupoAcesso;
    }

    public Integer getIdgrupoAcesso() {
        return idgrupoAcesso;
    }

    public void setIdgrupoAcesso(Integer idgrupoAcesso) {
        this.idgrupoAcesso = idgrupoAcesso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgrupoAcesso != null ? idgrupoAcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Grupoacesso)) {
            return false;
        }
        Grupoacesso other = (Grupoacesso) object;
        if ((this.idgrupoAcesso == null && other.idgrupoAcesso != null) || (this.idgrupoAcesso != null && !this.idgrupoAcesso.equals(other.idgrupoAcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Grupoacesso[ idgrupoAcesso=" + idgrupoAcesso + " ]";
    }
    
}
