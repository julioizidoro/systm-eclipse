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
@Table(name = "promocaoacomodacaocidade")
public class Promocaoacomodacaocidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocaoacomodacaocidade")
    private Integer idpromocaoacomodacaocidade;
    @JoinColumn(name = "promocaoacomodacao_idpromoacaoacomodacao", referencedColumnName = "idpromoacaoacomodacao", updatable=true)
    @ManyToOne(optional = false)
    private Promocaoacomodacao promocaoacomodacao;
    @JoinColumn(name = "fornecedorcidadeidioma_idfornecedorcidadeidioma", referencedColumnName = "idfornecedorcidadeidioma")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidioma fornecedorcidadeidioma;

    public Promocaoacomodacaocidade() {
    }

    public Promocaoacomodacaocidade(Integer idpromocaoacomodacaocidade) {
        this.idpromocaoacomodacaocidade = idpromocaoacomodacaocidade;
    }

    public Integer getIdpromocaoacomodacaocidade() {
        return idpromocaoacomodacaocidade;
    }

    public void setIdpromocaoacomodacaocidade(Integer idpromocaoacomodacaocidade) {
        this.idpromocaoacomodacaocidade = idpromocaoacomodacaocidade;
    }

    public Promocaoacomodacao getPromocaoacomodacao() {
        return promocaoacomodacao;
    }

    public void setPromocaoacomodacao(Promocaoacomodacao promocaoacomodacao) {
        this.promocaoacomodacao = promocaoacomodacao;
    }

    public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
        return fornecedorcidadeidioma;
    }

    public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
        this.fornecedorcidadeidioma = fornecedorcidadeidioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromocaoacomodacaocidade != null ? idpromocaoacomodacaocidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaoacomodacaocidade)) {
            return false;
        }
        Promocaoacomodacaocidade other = (Promocaoacomodacaocidade) object;
        if ((this.idpromocaoacomodacaocidade == null && other.idpromocaoacomodacaocidade != null) || (this.idpromocaoacomodacaocidade != null && !this.idpromocaoacomodacaocidade.equals(other.idpromocaoacomodacaocidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaoacomodacaocidade[ idpromocaoacomodacaocidade=" + idpromocaoacomodacaocidade + " ]";
    }
    
}

