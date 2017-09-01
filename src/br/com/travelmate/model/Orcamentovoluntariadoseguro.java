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
@Table(name = "orcamentovoluntariadoseguro")
public class Orcamentovoluntariadoseguro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentovoluntariadoseguro")
    private Integer idorcamentovoluntariadoseguro;
    @Column(name = "datainicial")
    @Temporal(TemporalType.DATE)
    private Date datainicial;
    @Column(name = "datafinal")
    @Temporal(TemporalType.DATE)
    private Date datafinal;
    @Column(name = "numerodias")
    private Integer numerodias;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Column(name = "valorrs")
    private Float valorrs;
    @Column(name = "somarvalortotal")
    private boolean somarvalortotal;
    @JoinColumn(name = "orcamentoprojetovoluntariado_idorcamentoprojetovoluntariado", referencedColumnName = "idorcamentoprojetovoluntariado")
    @ManyToOne(optional = false)
    private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado;
    @JoinColumn(name = "valoresseguro_idvaloresseguro", referencedColumnName = "idvaloresseguro")
    @ManyToOne(optional = false)
    private Valoresseguro valoresseguro;

    public Orcamentovoluntariadoseguro() {
    }

    public Orcamentovoluntariadoseguro(Integer idorcamentovoluntariadoseguro) {
        this.idorcamentovoluntariadoseguro = idorcamentovoluntariadoseguro;
    }

    public Integer getIdorcamentovoluntariadoseguro() {
        return idorcamentovoluntariadoseguro;
    }

    public void setIdorcamentovoluntariadoseguro(Integer idorcamentovoluntariadoseguro) {
        this.idorcamentovoluntariadoseguro = idorcamentovoluntariadoseguro;
    }

    public Date getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(Date datainicial) {
        this.datainicial = datainicial;
    }

    public Date getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(Date datafinal) {
        this.datafinal = datafinal;
    }

    public Integer getNumerodias() {
        return numerodias;
    }

    public void setNumerodias(Integer numerodias) {
        this.numerodias = numerodias;
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

    public boolean getSomarvalortotal() {
        return somarvalortotal;
    }

    public void setSomarvalortotal(boolean somarvalortotal) {
        this.somarvalortotal = somarvalortotal;
    }

    public Orcamentoprojetovoluntariado getOrcamentoprojetovoluntariado() {
        return orcamentoprojetovoluntariado;
    }

    public void setOrcamentoprojetovoluntariado(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
        this.orcamentoprojetovoluntariado = orcamentoprojetovoluntariado;
    }

    public Valoresseguro getValoresseguro() {
        return valoresseguro;
    }

    public void setValoresseguro(Valoresseguro valoresseguro) {
        this.valoresseguro = valoresseguro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentovoluntariadoseguro != null ? idorcamentovoluntariadoseguro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentovoluntariadoseguro)) {
            return false;
        }
        Orcamentovoluntariadoseguro other = (Orcamentovoluntariadoseguro) object;
        if ((this.idorcamentovoluntariadoseguro == null && other.idorcamentovoluntariadoseguro != null) || (this.idorcamentovoluntariadoseguro != null && !this.idorcamentovoluntariadoseguro.equals(other.idorcamentovoluntariadoseguro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentovoluntariadoseguro[ idorcamentovoluntariadoseguro=" + idorcamentovoluntariadoseguro + " ]";
    }
    
}

