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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "fornecedorarquivotipo")
public class Fornecedorarquivotipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfornecedorarquivotipo")
    private Integer idfornecedorarquivotipo;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Column(name = "numeroarquivos")
    private Integer numeroarquivos;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fornecedorarquivotipo")
    private List<Fornecedorarquivo> fornecedorarquivoList;

    public Fornecedorarquivotipo() {
    }

    public Fornecedorarquivotipo(Integer idfornecedorarquivotipo) {
        this.idfornecedorarquivotipo = idfornecedorarquivotipo;
    }

    public Integer getIdfornecedorarquivotipo() {
        return idfornecedorarquivotipo;
    }

    public void setIdfornecedorarquivotipo(Integer idfornecedorarquivotipo) {
        this.idfornecedorarquivotipo = idfornecedorarquivotipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroarquivos() {
        return numeroarquivos;
    }

    public void setNumeroarquivos(Integer numeroarquivos) {
        this.numeroarquivos = numeroarquivos;
    }

    public List<Fornecedorarquivo> getFornecedorarquivoList() {
        return fornecedorarquivoList;
    }

    public void setFornecedorarquivoList(List<Fornecedorarquivo> fornecedorarquivoList) {
        this.fornecedorarquivoList = fornecedorarquivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorarquivotipo != null ? idfornecedorarquivotipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedorarquivotipo)) {
            return false;
        }
        Fornecedorarquivotipo other = (Fornecedorarquivotipo) object;
        if ((this.idfornecedorarquivotipo == null && other.idfornecedorarquivotipo != null) || (this.idfornecedorarquivotipo != null && !this.idfornecedorarquivotipo.equals(other.idfornecedorarquivotipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Fornecedorarquivotipo[ idfornecedorarquivotipo=" + idfornecedorarquivotipo + " ]";
    }
    
}

