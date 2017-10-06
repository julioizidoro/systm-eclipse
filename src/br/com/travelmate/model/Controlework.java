/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controlework")
public class Controlework implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleWork")
    private Integer idcontroleWork;
    @Column(name = "dataIniciojoboffer")
    @Temporal(TemporalType.DATE)
    private Date dataIniciojoboffer;
    @Column(name = "dataTerminojoboffer")
    @Temporal(TemporalType.DATE)
    private Date dataTerminojoboffer;
    @Column(name = "dataretorno")
    @Temporal(TemporalType.DATE)
    private Date dataretorno;
    @Column(name = "documentacao")
    private Boolean documentacao;
    @Column(name = "applicationsponsor")
    private Boolean applicationsponsor;
    @Column(name = "invoicepaga")
    private Boolean invoicepaga;
    @Size(max = 50)
    @Column(name = "statusprocesso")
    private String statusprocesso;
    @Size(max = 15)
    @Column(name = "modalidade")
    private String modalidade;
    @Size(max = 100)
    @Column(name = "skype")
    private String skype;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacoes")
    private String observacoes;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "controlework", fetch=FetchType.LAZY)
    private Controleworkembarque controleworkembarque;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "controlework", fetch=FetchType.LAZY)
    private Controleworksponsor controleworksponsor;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "controlework", fetch=FetchType.LAZY)
    private Controleworkempregaor controleworkempregaor;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas; 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlework")
    @OrderBy("idcontroleworkentrevista DESC") 
    @Size(max=1)
    private List<Controleworkentrevista> controleworkentrevistaList;

    public Controlework() {
    }

    public Controlework(Integer idcontroleWork) {
        this.idcontroleWork = idcontroleWork;
    }

    public Integer getIdcontroleWork() {
        return idcontroleWork;
    }

    public void setIdcontroleWork(Integer idcontroleWork) {
        this.idcontroleWork = idcontroleWork;
    }

    public Date getDataIniciojoboffer() {
        return dataIniciojoboffer;
    }

    public void setDataIniciojoboffer(Date dataIniciojoboffer) {
        this.dataIniciojoboffer = dataIniciojoboffer;
    }

    public Date getDataTerminojoboffer() {
        return dataTerminojoboffer;
    }

    public void setDataTerminojoboffer(Date dataTerminojoboffer) {
        this.dataTerminojoboffer = dataTerminojoboffer;
    }

    public Date getDataretorno() {
        return dataretorno;
    }

    public void setDataretorno(Date dataretorno) {
        this.dataretorno = dataretorno;
    }

    public Boolean getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(Boolean documentacao) {
        this.documentacao = documentacao;
    }

    public Boolean getApplicationsponsor() {
        return applicationsponsor;
    }

    public void setApplicationsponsor(Boolean applicationsponsor) {
        this.applicationsponsor = applicationsponsor;
    }

    public Boolean getInvoicepaga() {
        return invoicepaga;
    }

    public void setInvoicepaga(Boolean invoicepaga) {
        this.invoicepaga = invoicepaga;
    }

    public String getStatusprocesso() {
        return statusprocesso;
    }

    public void setStatusprocesso(String statusprocesso) {
        this.statusprocesso = statusprocesso;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    

    public Controleworkembarque getControleworkembarque() {
		return controleworkembarque;
	}

	public void setControleworkembarque(Controleworkembarque controleworkembarque) {
		this.controleworkembarque = controleworkembarque;
	}

	public Controleworksponsor getControleworksponsor() {
		return controleworksponsor;
	}

	public void setControleworksponsor(Controleworksponsor controleworksponsor) {
		this.controleworksponsor = controleworksponsor;
	}

	public Controleworkempregaor getControleworkempregaor() {
		return controleworkempregaor;
	}

	public void setControleworkempregaor(Controleworkempregaor controleworkempregaor) {
		this.controleworkempregaor = controleworkempregaor;
	}

	public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public List<Controleworkentrevista> getControleworkentrevistaList() {
        return controleworkentrevistaList;
    }

    public void setControleworkentrevistaList(List<Controleworkentrevista> controleworkentrevistaList) {
        this.controleworkentrevistaList = controleworkentrevistaList;
    }

    public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleWork != null ? idcontroleWork.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Controlework)) {
            return false;
        }
        Controlework other = (Controlework) object;
        if ((this.idcontroleWork == null && other.idcontroleWork != null) || (this.idcontroleWork != null && !this.idcontroleWork.equals(other.idcontroleWork))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controlework[ idcontroleWork=" + idcontroleWork + " ]";
    }
    
}
