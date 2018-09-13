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
@Table(name = "orcamentovoluntariadodesconto")
public class Orcamentovoluntariadodesconto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentovoluntariadodesconto")
    private Integer idorcamentovoluntariadodesconto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Column(name = "valorrs")
    private Float valorrs;
    @JoinColumn(name = "orcamentoprojetovoluntariado_idorcamentoprojetovoluntariado", referencedColumnName = "idorcamentoprojetovoluntariado")
    @ManyToOne(optional = false)
    private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado;
    @JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Orcamentovoluntariadodesconto() {
    }

    public Orcamentovoluntariadodesconto(Integer idorcamentovoluntariadodesconto) {
        this.idorcamentovoluntariadodesconto = idorcamentovoluntariadodesconto;
    }

    public Integer getIdorcamentovoluntariadodesconto() {
        return idorcamentovoluntariadodesconto;
    }

    public void setIdorcamentovoluntariadodesconto(Integer idorcamentovoluntariadodesconto) {
        this.idorcamentovoluntariadodesconto = idorcamentovoluntariadodesconto;
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
        hash += (idorcamentovoluntariadodesconto != null ? idorcamentovoluntariadodesconto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentovoluntariadodesconto)) {
            return false;
        }
        Orcamentovoluntariadodesconto other = (Orcamentovoluntariadodesconto) object;
        if ((this.idorcamentovoluntariadodesconto == null && other.idorcamentovoluntariadodesconto != null) || (this.idorcamentovoluntariadodesconto != null && !this.idorcamentovoluntariadodesconto.equals(other.idorcamentovoluntariadodesconto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentovoluntariadodesconto[ idorcamentovoluntariadodesconto=" + idorcamentovoluntariadodesconto + " ]";
    }
    
}

