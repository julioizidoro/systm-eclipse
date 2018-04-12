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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "publicidade")
@NamedQueries({
    @NamedQuery(name = "Publicidade.findREFRESH", query = "SELECT p FROM Publicidade p")})
public class Publicidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpublicidade")
    private Integer idpublicidade;
    @Size(max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "publicidade")
    private List<Cliente> clienteList;
    @Column(name = "situacao")
    private Boolean situacao;
    @Column(name = "mostrar")
    private Boolean mostrar;
    

    public Publicidade() {
    }

    public Publicidade(Integer idpublicidade) {
        this.idpublicidade = idpublicidade;
    }

    public Integer getIdpublicidade() {
        return idpublicidade;
    }

    public void setIdpublicidade(Integer idpublicidade) {
        this.idpublicidade = idpublicidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
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
        hash += (idpublicidade != null ? idpublicidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Publicidade)) {
            return false;
        }
        Publicidade other = (Publicidade) object;
        if ((this.idpublicidade == null && other.idpublicidade != null) || (this.idpublicidade != null && !this.idpublicidade.equals(other.idpublicidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Publicidade[ idpublicidade=" + idpublicidade + " ]";
    }
    
}
