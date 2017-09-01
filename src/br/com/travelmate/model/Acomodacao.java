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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "acomodacao")
public class Acomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idacomodacao")
    private Integer idacomodacao;
    @Column(name = "datainicial")
    @Temporal(TemporalType.DATE)
    private Date datainicial;
    @Column(name = "numerosemana")
    private Integer numerosemana;
    @Column(name = "datadinal")
    @Temporal(TemporalType.DATE)
    private Date datadinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valormoedaestrangeira")
    private Float valormoedaestrangeira;
    @Column(name = "valormoedanacional")
    private Float valormoedanacional;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "valoresacomodacao_idvaloresacomodacao", referencedColumnName = "idvaloresacomodacao")
    @ManyToOne(optional = false)
    private Valoresacomodacao valoresacomodacao;

    public Acomodacao() {
    }

    public Acomodacao(Integer idacomodacao) {
        this.idacomodacao = idacomodacao;
    }

    public Integer getIdacomodacao() {
        return idacomodacao;
    }

    public void setIdacomodacao(Integer idacomodacao) {
        this.idacomodacao = idacomodacao;
    }

    public Date getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(Date datainicial) {
        this.datainicial = datainicial;
    }

    public Integer getNumerosemana() {
        return numerosemana;
    }

    public void setNumerosemana(Integer numerosemana) {
        this.numerosemana = numerosemana;
    }

    public Date getDatadinal() {
        return datadinal;
    }

    public void setDatadinal(Date datadinal) {
        this.datadinal = datadinal;
    }

    public Float getValormoedaestrangeira() {
        return valormoedaestrangeira;
    }

    public void setValormoedaestrangeira(Float valormoedaestrangeira) {
        this.valormoedaestrangeira = valormoedaestrangeira;
    }

    public Float getValormoedanacional() {
        return valormoedanacional;
    }

    public void setValormoedanacional(Float valormoedanacional) {
        this.valormoedanacional = valormoedanacional;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Valoresacomodacao getValoresacomodacao() {
        return valoresacomodacao;
    }

    public void setValoresacomodacao(Valoresacomodacao valoresacomodacao) {
        this.valoresacomodacao = valoresacomodacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idacomodacao != null ? idacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Acomodacao)) {
            return false;
        }
        Acomodacao other = (Acomodacao) object;
        if ((this.idacomodacao == null && other.idacomodacao != null) || (this.idacomodacao != null && !this.idacomodacao.equals(other.idacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Acomodacao[ idacomodacao=" + idacomodacao + " ]";
    }
    
}
