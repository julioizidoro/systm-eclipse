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
 * @author wolverine
 */
@Entity
@Table(name = "faturabanco")
public class Faturabanco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfaturabanco")
    private Integer idfaturabanco;
    @Column(name = "banco")
    private String banco;
    @Column(name = "agencia")
    private String agencia;
    @Column(name = "conta")
    private String conta;
    @JoinColumn(name = "fatura_idfatura", referencedColumnName = "idfatura")
    @ManyToOne(optional = false)
    private Fatura fatura;

    public Faturabanco() {
    }

    public Faturabanco(Integer idfaturabanco) {
        this.idfaturabanco = idfaturabanco;
    }

    public Integer getIdfaturabanco() {
        return idfaturabanco;
    }

    public void setIdfaturabanco(Integer idfaturabanco) {
        this.idfaturabanco = idfaturabanco;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfaturabanco != null ? idfaturabanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faturabanco)) {
            return false;
        }
        Faturabanco other = (Faturabanco) object;
        if ((this.idfaturabanco == null && other.idfaturabanco != null) || (this.idfaturabanco != null && !this.idfaturabanco.equals(other.idfaturabanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Faturabanco[ idfaturabanco=" + idfaturabanco + " ]";
    }
    
}

