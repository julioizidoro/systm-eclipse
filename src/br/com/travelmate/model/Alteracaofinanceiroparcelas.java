package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "alteracaofinanceiroparcelas")
@NamedQueries({
    @NamedQuery(name = "Alteracaofinanceiroparcelas.findAll", query = "SELECT a FROM Alteracaofinanceiroparcelas a")})
public class Alteracaofinanceiroparcelas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalteracaofinanceiroparcelas")
    private Integer idalteracaofinanceiroparcelas;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 100)
    @Column(name = "tipodocumento")
    private String tipodocumento;
    @Size(max = 6)
    @Column(name = "formaparcelamento")
    private String formaparcelamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorparcelado")
    private Float valorparcelado;
    @Column(name = "numeroparcelas")
    private Integer numeroparcelas;
    @Column(name = "valorparcela")
    private Float valorparcela;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
   
    @JoinColumn(name = "alteracaofinanceiro_idalteracaofinanceiro", referencedColumnName = "idalteracaofinanceiro")
    @ManyToOne(optional = false)
    private Alteracaofinanceiro alteracaofinanceiro;

    public Alteracaofinanceiroparcelas() {
    }

    public Alteracaofinanceiroparcelas(Integer idalteracaofinanceiroparcelas) {
        this.idalteracaofinanceiroparcelas = idalteracaofinanceiroparcelas;
    }

    public Integer getIdalteracaofinanceiroparcelas() {
        return idalteracaofinanceiroparcelas;
    }

    public void setIdalteracaofinanceiroparcelas(Integer idalteracaofinanceiroparcelas) {
        this.idalteracaofinanceiroparcelas = idalteracaofinanceiroparcelas;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getFormaparcelamento() {
        return formaparcelamento;
    }

    public void setFormaparcelamento(String formaparcelamento) {
        this.formaparcelamento = formaparcelamento;
    }

    public Float getValorparcelado() {
        return valorparcelado;
    }

    public void setValorparcelado(Float valorparcelado) {
        this.valorparcelado = valorparcelado;
    }

    public Integer getNumeroparcelas() {
        return numeroparcelas;
    }

    public void setNumeroparcelas(Integer numeroparcelas) {
        this.numeroparcelas = numeroparcelas;
    }

    public Float getValorparcela() {
        return valorparcela;
    }

    public void setValorparcela(Float valorparcela) {
        this.valorparcela = valorparcela;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

   

    public Alteracaofinanceiro getAlteracaofinanceiro() {
        return alteracaofinanceiro;
    }

    public void setAlteracaofinanceiro(Alteracaofinanceiro alteracaofinanceiro) {
        this.alteracaofinanceiro = alteracaofinanceiro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalteracaofinanceiroparcelas != null ? idalteracaofinanceiroparcelas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Alteracaofinanceiroparcelas)) {
            return false;
        }
        Alteracaofinanceiroparcelas other = (Alteracaofinanceiroparcelas) object;
        if ((this.idalteracaofinanceiroparcelas == null && other.idalteracaofinanceiroparcelas != null) || (this.idalteracaofinanceiroparcelas != null && !this.idalteracaofinanceiroparcelas.equals(other.idalteracaofinanceiroparcelas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Alteracaofinanceiroparcelas[ idalteracaofinanceiroparcelas=" + idalteracaofinanceiroparcelas + " ]";
    }
    
}

