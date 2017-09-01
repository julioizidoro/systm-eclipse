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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "passagemaerea")
public class Passagemaerea implements Serializable {
    
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "passagemaerea")
    private List<Passagempassageiro> passagempassageiroList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpassagemAerea")
    private Integer idpassagemAerea;
    @Size(max = 50)
    @Column(name = "localizador")
    private String localizador;
    @Size(max = 100)
    @Column(name = "ciaAerea")
    private String ciaAerea;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacoes")
    private String observacoes;
    @Size(max = 50)
    @Column(name = "atendente")
    private String atendente;
    @Column(name = "comissaoloja")
    private Float comissaoloja;
    @Column(name = "dataviagem")
    @Temporal(TemporalType.DATE)
    private Date dataviagem;
    @Column(name = "datavolta")
    @Temporal(TemporalType.DATE)
    private Date datavolta;
    @Lob
    @Size(max = 16777215)
    @Column(name = "trecho")
    private String trecho;
    @Lob
    @Size(max = 16777215)
    @Column(name = "regras")
    private String regras;
    @Column(name = "adttarifa")
    private Float adttarifa;
    @Column(name = "adttaxas")
    private Float adttaxas;
    @Column(name = "adttaxaemissao")
    private Float adttaxaemissao;
    @Column(name = "adtcomissao")
    private Float adtcomissao;
    @Column(name = "chdtarifa")
    private Float chdtarifa;
    @Column(name = "chdtaxas")
    private Float chdtaxas;
    @Column(name = "chdtaxaemissao")
    private Float chdtaxaemissao;
    @Column(name = "chdcomissao")
    private Float chdcomissao;
    @Column(name = "inftarifa")
    private Float inftarifa;
    @Column(name = "inftaxas")
    private Float inftaxas;
    @Column(name = "inftaxaemissao")
    private Float inftaxaemissao;
    @Column(name = "infcomissao")
    private Float infcomissao;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Transient
    private float comissao;
    @Transient
    private float taxaemissao;

    public Passagemaerea() {
    }

    public Passagemaerea(Integer idpassagemAerea) {
        this.idpassagemAerea = idpassagemAerea;
    }

    public Integer getIdpassagemAerea() {
        return idpassagemAerea;
    }

    public void setIdpassagemAerea(Integer idpassagemAerea) {
        this.idpassagemAerea = idpassagemAerea;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public String getCiaAerea() {
        return ciaAerea;
    }

    public void setCiaAerea(String ciaAerea) {
        this.ciaAerea = ciaAerea;
    }

    public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getAtendente() {
		return atendente;
	}

	public void setAtendente(String atendente) {
		this.atendente = atendente;
	}
	public Float getComissaoloja() {
        return comissaoloja;
    }

    public void setComissaoloja(Float comissaoloja) {
        this.comissaoloja = comissaoloja;
    }


    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Date getDataviagem() {
        return dataviagem;
    }

    public void setDataviagem(Date dataviagem) {
        this.dataviagem = dataviagem;
    }

    public Date getDatavolta() {
        return datavolta;
    }

    public void setDatavolta(Date datavolta) {
        this.datavolta = datavolta;
    }

    public String getTrecho() {
        return trecho;
    }

    public void setTrecho(String trecho) {
        this.trecho = trecho;
    }

    public String getRegras() {
        return regras;
    }

    public void setRegras(String regras) {
        this.regras = regras;
    }

    public Float getAdttarifa() {
        return adttarifa;
    }

    public void setAdttarifa(Float adttarifa) {
        this.adttarifa = adttarifa;
    }

    public Float getAdttaxas() {
        return adttaxas;
    }

    public void setAdttaxas(Float adttaxas) {
        this.adttaxas = adttaxas;
    }

    public Float getAdttaxaemissao() {
        return adttaxaemissao;
    }

    public void setAdttaxaemissao(Float adttaxaemissao) {
        this.adttaxaemissao = adttaxaemissao;
    }

    public Float getAdtcomissao() {
        return adtcomissao;
    }

    public void setAdtcomissao(Float adtcomissao) {
        this.adtcomissao = adtcomissao;
    }

    public Float getChdtarifa() {
        return chdtarifa;
    }

    public void setChdtarifa(Float chdtarifa) {
        this.chdtarifa = chdtarifa;
    }

    public Float getChdtaxas() {
        return chdtaxas;
    }

    public void setChdtaxas(Float chdtaxas) {
        this.chdtaxas = chdtaxas;
    }

    public Float getChdtaxaemissao() {
        return chdtaxaemissao;
    }

    public void setChdtaxaemissao(Float chdtaxaemissao) {
        this.chdtaxaemissao = chdtaxaemissao;
    }

    public Float getChdcomissao() {
        return chdcomissao;
    }

    public void setChdcomissao(Float chdcomissao) {
        this.chdcomissao = chdcomissao;
    }

    public Float getInftarifa() {
        return inftarifa;
    }

    public void setInftarifa(Float inftarifa) {
        this.inftarifa = inftarifa;
    }

    public Float getInftaxas() {
        return inftaxas;
    }

    public void setInftaxas(Float inftaxas) {
        this.inftaxas = inftaxas;
    }

    public Float getInftaxaemissao() {
        return inftaxaemissao;
    }

    public void setInftaxaemissao(Float inftaxaemissao) {
        this.inftaxaemissao = inftaxaemissao;
    }

    public Float getInfcomissao() {
        return infcomissao;
    }

    public void setInfcomissao(Float infcomissao) {
        this.infcomissao = infcomissao;
    }

    public List<Passagempassageiro> getPassagempassageiroList() {
        return passagempassageiroList;
    }

    public void setPassagempassageiroList(List<Passagempassageiro> passagempassageiroList) {
        this.passagempassageiroList = passagempassageiroList;
    }
    
    public float getComissao() {
		return comissao;
	}

	public void setComissao(float comissao) {
		this.comissao = comissao;
	}

	public float getTaxaemissao() {
		return taxaemissao;
	}

	public void setTaxaemissao(float taxaemissao) {
		this.taxaemissao = taxaemissao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpassagemAerea != null ? idpassagemAerea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Passagemaerea)) {
            return false;
        }
        Passagemaerea other = (Passagemaerea) object;
        if ((this.idpassagemAerea == null && other.idpassagemAerea != null) || (this.idpassagemAerea != null && !this.idpassagemAerea.equals(other.idpassagemAerea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Passagemaerea[ idpassagemAerea=" + idpassagemAerea + " ]";
    }

    
}
