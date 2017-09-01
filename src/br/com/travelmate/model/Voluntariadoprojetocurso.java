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
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "voluntariadoprojetocurso")

public class Voluntariadoprojetocurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvoluntariadoprojetocurso")
    private Integer idvoluntariadoprojetocurso;
    @Size(max = 5)
    @Column(name = "cargahoraria")
    private String cargahoraria;
    @Size(max = 50)
    @Column(name = "tipocargahoraria")
    private String tipocargahoraria;
    @JoinColumn(name = "voluntariadoprojeto_idvoluntariadoprojeto", referencedColumnName = "idvoluntariadoprojeto")
    @ManyToOne(optional = false)
    private Voluntariadoprojeto voluntariadoprojeto;
    @JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Voluntariadoprojetocurso() {
    }

    public Voluntariadoprojetocurso(Integer idvoluntariadoprojetocurso) {
        this.idvoluntariadoprojetocurso = idvoluntariadoprojetocurso;
    }

    public Integer getIdvoluntariadoprojetocurso() {
        return idvoluntariadoprojetocurso;
    }

    public void setIdvoluntariadoprojetocurso(Integer idvoluntariadoprojetocurso) {
        this.idvoluntariadoprojetocurso = idvoluntariadoprojetocurso;
    }

    public String getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(String cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public String getTipocargahoraria() {
        return tipocargahoraria;
    }

    public void setTipocargahoraria(String tipocargahoraria) {
        this.tipocargahoraria = tipocargahoraria;
    }

    public Voluntariadoprojeto getVoluntariadoprojeto() {
        return voluntariadoprojeto;
    }

    public void setVoluntariadoprojeto(Voluntariadoprojeto voluntariadoprojeto) {
        this.voluntariadoprojeto = voluntariadoprojeto;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoluntariadoprojetocurso != null ? idvoluntariadoprojetocurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Voluntariadoprojetocurso)) {
            return false;
        }
        Voluntariadoprojetocurso other = (Voluntariadoprojetocurso) object;
        if ((this.idvoluntariadoprojetocurso == null && other.idvoluntariadoprojetocurso != null) || (this.idvoluntariadoprojetocurso != null && !this.idvoluntariadoprojetocurso.equals(other.idvoluntariadoprojetocurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Voluntariadoprojetocurso[ idvoluntariadoprojetocurso=" + idvoluntariadoprojetocurso + " ]";
    }
    
}

