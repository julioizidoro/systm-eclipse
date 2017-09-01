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
@Table(name = "modeloprodutoorcamentocurso")
public class Modeloprodutoorcamentocurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutoOrcamentoCurso")
    private Integer idprodutoOrcamentoCurso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorMoedaEstrangeira")
    private Float valorMoedaEstrangeira;
    @Column(name = "valorMoedaNacional")
    private Float valorMoedaNacional;
    @JoinColumn(name = "modeloOrcamentoCurso_idorcamentoCurso", referencedColumnName = "idorcamentoCurso")
    @ManyToOne(optional = false)
    private Modeloorcamentocurso modeloorcamentocurso;
    @JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Modeloprodutoorcamentocurso() {
    }

    public Modeloprodutoorcamentocurso(Integer idprodutoOrcamentoCurso) {
        this.idprodutoOrcamentoCurso = idprodutoOrcamentoCurso;
    }

    public Integer getIdprodutoOrcamentoCurso() {
        return idprodutoOrcamentoCurso;
    }

    public void setIdprodutoOrcamentoCurso(Integer idprodutoOrcamentoCurso) {
        this.idprodutoOrcamentoCurso = idprodutoOrcamentoCurso;
    }

    public Float getValorMoedaEstrangeira() {
        return valorMoedaEstrangeira;
    }

    public void setValorMoedaEstrangeira(Float valorMoedaEstrangeira) {
        this.valorMoedaEstrangeira = valorMoedaEstrangeira;
    }

    public Float getValorMoedaNacional() {
        return valorMoedaNacional;
    }

    public void setValorMoedaNacional(Float valorMoedaNacional) {
        this.valorMoedaNacional = valorMoedaNacional;
    }

    public Modeloorcamentocurso getModeloorcamentocurso() {
        return modeloorcamentocurso;
    }

    public void setModeloorcamentocurso(Modeloorcamentocurso modeloorcamentocurso) {
        this.modeloorcamentocurso = modeloorcamentocurso;
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
        hash += (idprodutoOrcamentoCurso != null ? idprodutoOrcamentoCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Modeloprodutoorcamentocurso)) {
            return false;
        }
        Modeloprodutoorcamentocurso other = (Modeloprodutoorcamentocurso) object;
        if ((this.idprodutoOrcamentoCurso == null && other.idprodutoOrcamentoCurso != null) || (this.idprodutoOrcamentoCurso != null && !this.idprodutoOrcamentoCurso.equals(other.idprodutoOrcamentoCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Modeloprodutoorcamentocurso[ idprodutoOrcamentoCurso=" + idprodutoOrcamentoCurso + " ]";
    }
    
}
