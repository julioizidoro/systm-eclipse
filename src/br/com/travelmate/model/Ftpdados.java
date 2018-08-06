/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "ftpdados")
public class Ftpdados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idftpdados")
    private Integer idftpdados;
    @Size(max = 100)
    @Column(name = "host")
    private String host;
    @Size(max = 50)
    @Column(name = "user")
    private String user;
    @Size(max = 20)
    @Column(name = "password")
    private String password;
    @Column(name = "pasta")
    private String pasta;
    @Column(name = "hostupload")
    private String hostupload;
    @Column(name = "hostlink")
    private String hostlink;

    public Ftpdados() {
    }

    public Ftpdados(Integer idftpdados) {
        this.idftpdados = idftpdados;
    }

    public Integer getIdftpdados() {
        return idftpdados;
    }

    public void setIdftpdados(Integer idftpdados) {
        this.idftpdados = idftpdados;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}
	
	

	public String getHostupload() {
		return hostupload;
	}

	public void setHostupload(String hostupload) {
		this.hostupload = hostupload;
	}

	public String getHostlink() {
		return hostlink;
	}

	public void setHostlink(String hostlink) {
		this.hostlink = hostlink;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idftpdados != null ? idftpdados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ftpdados)) {
            return false;
        }
        Ftpdados other = (Ftpdados) object;
        if ((this.idftpdados == null && other.idftpdados != null) || (this.idftpdados != null && !this.idftpdados.equals(other.idftpdados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Ftpdados[ idftpdados=" + idftpdados + " ]";
    }
    
}
