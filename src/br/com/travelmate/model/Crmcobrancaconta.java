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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "crmcobrancaconta")
public class Crmcobrancaconta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcrmcobrancaconta")
    private Integer idcrmcobrancaconta;
    @Column(name = "datainclusao")
    @Temporal(TemporalType.DATE)
    private Date datainclusao;
    @Column(name = "paga")
    private Boolean paga;
    @JoinColumn(name = "contasreceber_idcontasreceber", referencedColumnName = "idcontasreceber")
    @OneToOne(optional = false)
    private Contasreceber contasreceber;
    @JoinColumn(name = "crmcobranca_idcrmcobranca", referencedColumnName = "idcrmcobranca")
    @ManyToOne(optional = false)
    private Crmcobranca crmcobranca;

    public Crmcobrancaconta() {
    }

    public Crmcobrancaconta(Integer idcrmcobrancaconta) {
        this.idcrmcobrancaconta = idcrmcobrancaconta;
    }

    public Integer getIdcrmcobrancaconta() {
        return idcrmcobrancaconta;
    }

    public void setIdcrmcobrancaconta(Integer idcrmcobrancaconta) {
        this.idcrmcobrancaconta = idcrmcobrancaconta;
    }

    public Date getDatainclusao() {
        return datainclusao;
    }

    public void setDatainclusao(Date datainclusao) {
        this.datainclusao = datainclusao;
    }

    public Boolean getPaga() {
        return paga;
    }

    public void setPaga(Boolean paga) {
        this.paga = paga;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public Crmcobranca getCrmcobranca() {
        return crmcobranca;
    }

    public void setCrmcobranca(Crmcobranca crmcobranca) {
        this.crmcobranca = crmcobranca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcrmcobrancaconta != null ? idcrmcobrancaconta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Crmcobrancaconta)) {
            return false;
        }
        Crmcobrancaconta other = (Crmcobrancaconta) object;
        if ((this.idcrmcobrancaconta == null && other.idcrmcobrancaconta != null) || (this.idcrmcobrancaconta != null && !this.idcrmcobrancaconta.equals(other.idcrmcobrancaconta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Crmcobrancaconta[ idcrmcobrancaconta=" + idcrmcobrancaconta + " ]";
    }
    
}

