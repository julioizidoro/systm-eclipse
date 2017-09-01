package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "crmcobranca")
public class Crmcobranca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcrmcobranca")
    private Integer idcrmcobranca;
    @Size(max = 2)
    @Column(name = "prioridade")
    private String prioridade;
    @Column(name = "proximocontato")
    @Temporal(TemporalType.DATE)
    private Date proximocontato;
    @Size(max = 10)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "datafinalizada")
    @Temporal(TemporalType.DATE)
    private Date datafinalizada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crmcobranca")
    private List<Crmcobrancaconta> crmcobrancacontaList;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Lob
    @Size(max = 16777215)
    @Column(name = "nota")
    private String nota;

    public Crmcobranca() {
    }

    public Crmcobranca(Integer idcrmcobranca) {
        this.idcrmcobranca = idcrmcobranca;
    }

    public Integer getIdcrmcobranca() {
        return idcrmcobranca;
    }

    public void setIdcrmcobranca(Integer idcrmcobranca) {
        this.idcrmcobranca = idcrmcobranca;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public Date getProximocontato() {
        return proximocontato;
    }

    public void setProximocontato(Date proximocontato) {
        this.proximocontato = proximocontato;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDatafinalizada() {
        return datafinalizada;
    }

    public void setDatafinalizada(Date datafinalizada) {
        this.datafinalizada = datafinalizada;
    }

    public List<Crmcobrancaconta> getCrmcobrancacontaList() {
        return crmcobrancacontaList;
    }

    public void setCrmcobrancacontaList(List<Crmcobrancaconta> crmcobrancacontaList) {
        this.crmcobrancacontaList = crmcobrancacontaList;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcrmcobranca != null ? idcrmcobranca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crmcobranca)) {
            return false;
        }
        Crmcobranca other = (Crmcobranca) object;
        if ((this.idcrmcobranca == null && other.idcrmcobranca != null) || (this.idcrmcobranca != null && !this.idcrmcobranca.equals(other.idcrmcobranca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Crmcobranca[ idcrmcobranca=" + idcrmcobranca + " ]";
    }
    
}

