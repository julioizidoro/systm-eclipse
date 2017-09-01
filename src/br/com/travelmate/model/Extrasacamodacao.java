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
 * @author Wolverine
 */
@Entity
@Table(name = "extrasacamodacao")
public class Extrasacamodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idextrasacamodacao")
    private Integer idextrasacamodacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Size(max = 1)
    @Column(name = "formapagamento")
    private String formapagamento;
    @JoinColumn(name = "valoresacomodacao_idvaloresacomodacao", referencedColumnName = "idvaloresacomodacao")
    @ManyToOne(optional = false)
    private Valoresacomodacao valoresacomodacao;
    @JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento;

    public Extrasacamodacao() {
    }

    public Extrasacamodacao(Integer idextrasacamodacao) {
        this.idextrasacamodacao = idextrasacamodacao;
    }

    public Integer getIdextrasacamodacao() {
        return idextrasacamodacao;
    }

    public void setIdextrasacamodacao(Integer idextrasacamodacao) {
        this.idextrasacamodacao = idextrasacamodacao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }

    public Valoresacomodacao getValoresacomodacao() {
        return valoresacomodacao;
    }

    public void setValoresacomodacao(Valoresacomodacao valoresacomodacao) {
        this.valoresacomodacao = valoresacomodacao;
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
        hash += (idextrasacamodacao != null ? idextrasacamodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Extrasacamodacao)) {
            return false;
        }
        Extrasacamodacao other = (Extrasacamodacao) object;
        if ((this.idextrasacamodacao == null && other.idextrasacamodacao != null) || (this.idextrasacamodacao != null && !this.idextrasacamodacao.equals(other.idextrasacamodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Extrasacamodacao[ idextrasacamodacao=" + idextrasacamodacao + " ]";
    }
    
}

