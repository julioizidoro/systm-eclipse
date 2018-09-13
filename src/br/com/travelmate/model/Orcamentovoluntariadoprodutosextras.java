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
@Table(name = "orcamentovoluntariadoprodutosextras")
public class Orcamentovoluntariadoprodutosextras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentovoluntariadoprodutosextras")
    private Integer idorcamentovoluntariadoprodutosextras;
    @Column(name = "nome")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Column(name = "valorrs")
    private Float valorrs;
    @Column(name = "somarvalortotal")
    private Boolean somarvalortotal;
    @JoinColumn(name = "orcamentoprojetovoluntariado_idorcamentoprojetovoluntariado", referencedColumnName = "idorcamentoprojetovoluntariado")
    @ManyToOne(optional = false)
    private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado;
    @JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Orcamentovoluntariadoprodutosextras() {
    }

    public Orcamentovoluntariadoprodutosextras(Integer idorcamentovoluntariadoprodutosextras) {
        this.idorcamentovoluntariadoprodutosextras = idorcamentovoluntariadoprodutosextras;
    }

    public Integer getIdorcamentovoluntariadoprodutosextras() {
        return idorcamentovoluntariadoprodutosextras;
    }

    public void setIdorcamentovoluntariadoprodutosextras(Integer idorcamentovoluntariadoprodutosextras) {
        this.idorcamentovoluntariadoprodutosextras = idorcamentovoluntariadoprodutosextras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getValorrs() {
        return valorrs;
    }

    public void setValorrs(Float valorrs) {
        this.valorrs = valorrs;
    }

    public Boolean getSomarvalortotal() {
        return somarvalortotal;
    }

    public void setSomarvalortotal(Boolean somarvalortotal) {
        this.somarvalortotal = somarvalortotal;
    }

    public Orcamentoprojetovoluntariado getOrcamentoprojetovoluntariado() {
        return orcamentoprojetovoluntariado;
    }

    public void setOrcamentoprojetovoluntariado(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
        this.orcamentoprojetovoluntariado = orcamentoprojetovoluntariado;
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
        hash += (idorcamentovoluntariadoprodutosextras != null ? idorcamentovoluntariadoprodutosextras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentovoluntariadoprodutosextras)) {
            return false;
        }
        Orcamentovoluntariadoprodutosextras other = (Orcamentovoluntariadoprodutosextras) object;
        if ((this.idorcamentovoluntariadoprodutosextras == null && other.idorcamentovoluntariadoprodutosextras != null) || (this.idorcamentovoluntariadoprodutosextras != null && !this.idorcamentovoluntariadoprodutosextras.equals(other.idorcamentovoluntariadoprodutosextras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Orcamentovoluntariadoprodutosextras[ idorcamentovoluntariadoprodutosextras=" + idorcamentovoluntariadoprodutosextras + " ]";
    }
    
}

