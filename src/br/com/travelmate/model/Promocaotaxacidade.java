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
@Table(name = "promocaotaxacidade")
public class Promocaotaxacidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocaotaxacidade")
    private Integer idpromocaotaxacidade;
    @JoinColumn(name = "promocaotaxacurso_idpromocaotaxacurso", referencedColumnName = "idpromocaotaxacurso", updatable=true)
    @ManyToOne(optional = false)
    private Promocaotaxacurso promocaotaxacurso;
    @JoinColumn(name = "fornecedorcidadeidiomaproduto_idfornecedorcidadeidiomaproduto", referencedColumnName = "idfornecedorcidadeidiomaproduto")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;

    public Promocaotaxacidade() {
    }

    public Promocaotaxacidade(Integer idpromocaotaxacidade) {
        this.idpromocaotaxacidade = idpromocaotaxacidade;
    }

    public Integer getIdpromocaotaxacidade() {
        return idpromocaotaxacidade;
    }

    public void setIdpromocaotaxacidade(Integer idpromocaotaxacidade) {
        this.idpromocaotaxacidade = idpromocaotaxacidade;
    }

    public Promocaotaxacurso getPromocaotaxacurso() {
        return promocaotaxacurso;
    }

    public void setPromocaotaxacurso(Promocaotaxacurso promocaotaxacurso) {
        this.promocaotaxacurso = promocaotaxacurso;
    }
    
    public Fornecedorcidadeidiomaproduto getFornecedorcidadeidiomaproduto() {
		return fornecedorcidadeidiomaproduto;
	}

	public void setFornecedorcidadeidiomaproduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromocaotaxacidade != null ? idpromocaotaxacidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaotaxacidade)) {
            return false;
        }
        Promocaotaxacidade other = (Promocaotaxacidade) object;
        if ((this.idpromocaotaxacidade == null && other.idpromocaotaxacidade != null) || (this.idpromocaotaxacidade != null && !this.idpromocaotaxacidade.equals(other.idpromocaotaxacidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaotaxacidade[ idpromocaotaxacidade=" + idpromocaotaxacidade + " ]";
    }
    
}
