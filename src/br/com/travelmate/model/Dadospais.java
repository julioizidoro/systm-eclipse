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
import javax.validation.constraints.Size;

@Entity
@Table(name = "dadospais")
public class Dadospais implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddadospais")
    private Integer iddadospais;
    @Size(max = 100)
    @Column(name = "nomepai")
    private String nomepai;
    @Column(name = "datanascimentopai")
    @Temporal(TemporalType.DATE)
    private Date datanascimentopai;
    @Size(max = 20)
    @Column(name = "telefonepai")
    private String telefonepai;
    @Size(max = 100)
    @Column(name = "emailpai")
    private String emailpai;
    @Size(max = 30)
    @Column(name = "tipologradouropai")
    private String tipologradouropai;
    @Size(max = 100)
    @Column(name = "logradouropai")
    private String logradouropai;
    @Size(max = 30)
    @Column(name = "numeropai")
    private String numeropai;
    @Size(max = 50)
    @Column(name = "complementopai")
    private String complementopai;
    @Size(max = 50)
    @Column(name = "bairropai")
    private String bairropai;
    @Size(max = 50)
    @Column(name = "cidadepai")
    private String cidadepai;
    @Size(max = 9)
    @Column(name = "ceppai")
    private String ceppai;
    @Size(max = 2)
    @Column(name = "estadopai")
    private String estadopai;
    @Size(max = 100)
    @Column(name = "nomemae")
    private String nomemae;
    @Column(name = "datanascimentomae")
    @Temporal(TemporalType.DATE)
    private Date datanascimentomae;
    @Size(max = 20)
    @Column(name = "telefonemae")
    private String telefonemae;
    @Size(max = 100)
    @Column(name = "emailmae")
    private String emailmae;
    @Size(max = 30)
    @Column(name = "tipologradouromae")
    private String tipologradouromae;
    @Size(max = 100)
    @Column(name = "logradouromae")
    private String logradouromae;
    @Size(max = 30)
    @Column(name = "numeromae")
    private String numerromae;
    @Size(max = 50)
    @Column(name = "complementomae")
    private String complementomae;
    @Size(max = 50)
    @Column(name = "bairromae")
    private String bairromae;
    @Size(max = 50)
    @Column(name = "cidademae")
    private String cidademae;
    @Size(max = 9)
    @Column(name = "cepmae")
    private String cepmae;
    @Size(max = 2)
    @Column(name = "estadomae")
    private String estadomae;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;

    public Dadospais() {
    }

    public Dadospais(Integer iddadospais) {
        this.iddadospais = iddadospais;
    }

    public Integer getIddadospais() {
        return iddadospais;
    }

    public void setIddadospais(Integer iddadospais) {
        this.iddadospais = iddadospais;
    }

    public String getNomepai() {
        return nomepai;
    }

    public void setNomepai(String nomepai) {
        this.nomepai = nomepai;
    }

    public Date getDatanascimentopai() {
        return datanascimentopai;
    }

    public void setDatanascimentopai(Date datanascimentopai) {
        this.datanascimentopai = datanascimentopai;
    }

    public String getTelefonepai() {
        return telefonepai;
    }

    public void setTelefonepai(String telefonepai) {
        this.telefonepai = telefonepai;
    }

    public String getEmailpai() {
        return emailpai;
    }

    public void setEmailpai(String emailpai) {
        this.emailpai = emailpai;
    }

    public String getTipologradouropai() {
        return tipologradouropai;
    }

    public void setTipologradouropai(String tipologradouropai) {
        this.tipologradouropai = tipologradouropai;
    }

    public String getLogradouropai() {
        return logradouropai;
    }

    public void setLogradouropai(String logradouropai) {
        this.logradouropai = logradouropai;
    }

    public String getNumeropai() {
        return numeropai;
    }

    public void setNumeropai(String numeropai) {
        this.numeropai = numeropai;
    }

    public String getComplementopai() {
        return complementopai;
    }

    public void setComplementopai(String complementopai) {
        this.complementopai = complementopai;
    }

    public String getBairropai() {
        return bairropai;
    }

    public void setBairropai(String bairropai) {
        this.bairropai = bairropai;
    }

    public String getCidadepai() {
        return cidadepai;
    }

    public void setCidadepai(String cidadepai) {
        this.cidadepai = cidadepai;
    }

    public String getCeppai() {
        return ceppai;
    }

    public void setCeppai(String ceppai) {
        this.ceppai = ceppai;
    }

    public String getEstadopai() {
        return estadopai;
    }

    public void setEstadopai(String estadopai) {
        this.estadopai = estadopai;
    }

    public String getNomemae() {
        return nomemae;
    }

    public void setNomemae(String nomemae) {
        this.nomemae = nomemae;
    }

    public Date getDatanascimentomae() {
        return datanascimentomae;
    }

    public void setDatanascimentomae(Date datanascimentomae) {
        this.datanascimentomae = datanascimentomae;
    }

    public String getTelefonemae() {
        return telefonemae;
    }

    public void setTelefonemae(String telefonemae) {
        this.telefonemae = telefonemae;
    }

    public String getEmailmae() {
        return emailmae;
    }

    public void setEmailmae(String emailmae) {
        this.emailmae = emailmae;
    }

    public String getTipologradouromae() {
        return tipologradouromae;
    }

    public void setTipologradouromae(String tipologradouromae) {
        this.tipologradouromae = tipologradouromae;
    }

    public String getLogradouromae() {
        return logradouromae;
    }

    public void setLogradouromae(String logradouromae) {
        this.logradouromae = logradouromae;
    }

    public String getNumerromae() {
        return numerromae;
    }

    public void setNumerromae(String numerromae) {
        this.numerromae = numerromae;
    }

    public String getComplementomae() {
        return complementomae;
    }

    public void setComplementomae(String complementomae) {
        this.complementomae = complementomae;
    }

    public String getBairromae() {
        return bairromae;
    }

    public void setBairromae(String bairromae) {
        this.bairromae = bairromae;
    }

    public String getCidademae() {
        return cidademae;
    }

    public void setCidademae(String cidademae) {
        this.cidademae = cidademae;
    }

    public String getCepmae() {
        return cepmae;
    }

    public void setCepmae(String cepmae) {
        this.cepmae = cepmae;
    }

    public String getEstadomae() {
        return estadomae;
    }

    public void setEstadomae(String estadomae) {
        this.estadomae = estadomae;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddadospais != null ? iddadospais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dadospais)) {
            return false;
        }
        Dadospais other = (Dadospais) object;
        if ((this.iddadospais == null && other.iddadospais != null) || (this.iddadospais != null && !this.iddadospais.equals(other.iddadospais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Dadospais[ iddadospais=" + iddadospais + " ]";
    }
    
}