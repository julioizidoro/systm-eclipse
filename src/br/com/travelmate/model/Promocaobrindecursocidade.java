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

import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "promocaobrindecursocidade")
public class Promocaobrindecursocidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocaobrindecursocidade")
    private Integer idpromocaobrindecursocidade;
    @JoinColumn(name = "promocaobrindecurso_idpromocaobrindecurso", referencedColumnName = "idpromocaobrindecurso", updatable=true)
    @ManyToOne(optional = false)
    private Promocaobrindecurso promocaobrindecurso;
    @JoinColumn(name = "fornecedorcidadeidiomaproduto_idfornecedorcidadeidiomaproduto", referencedColumnName = "idfornecedorcidadeidiomaproduto")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;

    public Promocaobrindecursocidade() {
    }

    public Promocaobrindecursocidade(Integer idpromocaobrindecursocidade) {
        this.idpromocaobrindecursocidade = idpromocaobrindecursocidade;
    }

    public Integer getIdpromocaobrindecursocidade() {
        return idpromocaobrindecursocidade;
    }

    public void setIdpromocaobrindecursocidade(Integer idpromocaobrindecursocidade) {
        this.idpromocaobrindecursocidade = idpromocaobrindecursocidade;
    }

    public Promocaobrindecurso getPromocaobrindecurso() {
        return promocaobrindecurso;
    }

    public void setPromocaobrindecurso(Promocaobrindecurso promocaobrindecurso) {
        this.promocaobrindecurso = promocaobrindecurso;
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
        hash += (idpromocaobrindecursocidade != null ? idpromocaobrindecursocidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaobrindecursocidade)) {
            return false;
        }
        Promocaobrindecursocidade other = (Promocaobrindecursocidade) object;
        if ((this.idpromocaobrindecursocidade == null && other.idpromocaobrindecursocidade != null) || (this.idpromocaobrindecursocidade != null && !this.idpromocaobrindecursocidade.equals(other.idpromocaobrindecursocidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaobrindecursocidade[ idpromocaobrindecursocidade=" + idpromocaobrindecursocidade + " ]";
    }
    
}
