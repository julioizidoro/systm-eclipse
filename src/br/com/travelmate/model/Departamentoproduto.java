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
@Table(name = "departamentoproduto")
public class Departamentoproduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddepartamentoproduto")
    private Integer iddepartamentoproduto;
    @JoinColumn(name = "departamento_iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;

    public Departamentoproduto() {
    }

    public Departamentoproduto(Integer iddepartamentoproduto) {
        this.iddepartamentoproduto = iddepartamentoproduto;
    }

    public Integer getIddepartamentoproduto() {
        return iddepartamentoproduto;
    }

    public void setIddepartamentoproduto(Integer iddepartamentoproduto) {
        this.iddepartamentoproduto = iddepartamentoproduto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
        hash += (iddepartamentoproduto != null ? iddepartamentoproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Departamentoproduto)) {
            return false;
        }
        Departamentoproduto other = (Departamentoproduto) object;
        if ((this.iddepartamentoproduto == null && other.iddepartamentoproduto != null) || (this.iddepartamentoproduto != null && !this.iddepartamentoproduto.equals(other.iddepartamentoproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Departamentoproduto[ iddepartamentoproduto=" + iddepartamentoproduto + " ]";
    }
    
}

