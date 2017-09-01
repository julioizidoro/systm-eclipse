/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "notificacaoemail")
@NamedQueries({
    @NamedQuery(name = "Notificacaoemail.findAll", query = "SELECT n FROM Notificacaoemail n")})
public class Notificacaoemail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotificacaoEmail")
    private Integer idnotificacaoEmail;
    @Size(max = 1)
    @Column(name = "tipoAviso")
    private String tipoAviso;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;

    public Notificacaoemail() {
    }

    public Notificacaoemail(Integer idnotificacaoEmail) {
        this.idnotificacaoEmail = idnotificacaoEmail;
    }

    public Integer getIdnotificacaoEmail() {
        return idnotificacaoEmail;
    }

    public void setIdnotificacaoEmail(Integer idnotificacaoEmail) {
        this.idnotificacaoEmail = idnotificacaoEmail;
    }

    public String getTipoAviso() {
        return tipoAviso;
    }

    public void setTipoAviso(String tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotificacaoEmail != null ? idnotificacaoEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notificacaoemail)) {
            return false;
        }
        Notificacaoemail other = (Notificacaoemail) object;
        if ((this.idnotificacaoEmail == null && other.idnotificacaoEmail != null) || (this.idnotificacaoEmail != null && !this.idnotificacaoEmail.equals(other.idnotificacaoEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Notificacaoemail[ idnotificacaoEmail=" + idnotificacaoEmail + " ]";
    }
    
}
