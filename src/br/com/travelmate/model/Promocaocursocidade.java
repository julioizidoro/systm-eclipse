package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "promocaocursocidade")
public class Promocaocursocidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocaocursocidade")
    private Integer idpromocaocursocidade;
    @JoinColumn(name = "fornecedorcidadeidiomaproduto_idfornecedorcidadeidiomaproduto", referencedColumnName = "idfornecedorcidadeidiomaproduto")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;
    @JoinColumn(name = "promoacaocurso_idpromoacaocurso", referencedColumnName = "idpromoacaocurso", updatable=true)
    @ManyToOne(optional = false)
    private Promocaocurso promoacaocurso;

    public Promocaocursocidade() {
    }

    public Promocaocursocidade(Integer idpromocaocursocidade) {
        this.idpromocaocursocidade = idpromocaocursocidade;
    }

    public Integer getIdpromocaocursocidade() {
        return idpromocaocursocidade;
    }

    public void setIdpromocaocursocidade(Integer idpromocaocursocidade) {
        this.idpromocaocursocidade = idpromocaocursocidade;
    }
 

    public Fornecedorcidadeidiomaproduto getFornecedorcidadeidiomaproduto() {
		return fornecedorcidadeidiomaproduto;
	}

	public void setFornecedorcidadeidiomaproduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
	}

	public Promocaocurso getPromoacaocurso() {
        return promoacaocurso;
    }

    public void setPromoacaocurso(Promocaocurso promoacaocurso) {
        this.promoacaocurso = promoacaocurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromocaocursocidade != null ? idpromocaocursocidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaocursocidade)) {
            return false;
        }
        Promocaocursocidade other = (Promocaocursocidade) object;
        if ((this.idpromocaocursocidade == null && other.idpromocaocursocidade != null) || (this.idpromocaocursocidade != null && !this.idpromocaocursocidade.equals(other.idpromocaocursocidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaocursocidade[ idpromocaocursocidade=" + idpromocaocursocidade + " ]";
    }
    
}

