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

/**
 *
 * @author wolverine
 */
@Entity
@Table(name = "controlehighschool")
public class Controlehighschool implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontroleHighSchool")
    private Integer idcontroleHighSchool;
    @Column(name = "dataEmbarque")
    @Temporal(TemporalType.DATE)
    private Date dataEmbarque;
    @Size(max = 50)
    @Column(name = "aeroportofinal")
    private String aeroportofinal;
    @Column(name = "dataEnvioApp")
    @Temporal(TemporalType.DATE)
    private Date dataEnvioApp;
    @Column(name = "dataRetorno")
    @Temporal(TemporalType.DATE)
    private Date dataRetorno;
    @Size(max = 20)
    @Column(name = "visto")
    private String visto;
    @Size(max = 3)
    @Column(name = "familia")
    private String familia;
    @Size(max = 20)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Size(max = 3)
    @Column(name = "pagamento")
    private String pagamento;
    @Size(max = 3)
    @Column(name = "passagem")
    private String passagem;
    @Size(max = 3)
    @Column(name = "historicochegou")
    private String historicochegou;
    @Size(max = 15)
    @Column(name = "historicotraducao")
    private String historicotraducao;
    @Size(max = 50)
    @Column(name = "historicoentrega")
    private String historicoentrega;
    @Column(name = "datadocumentacaovisto")
    @Temporal(TemporalType.DATE)
    private Date datadocumentacaovisto;
    @Column(name = "datacomprovantepagamento")
    @Temporal(TemporalType.DATE)
    private Date datacomprovantepagamento;
    @Column(name = "dataenviopassagem")
    @Temporal(TemporalType.DATE)
    private Date dataenviopassagem;
    @Column(name = "embarquecidade")
    private String embarquecidade;
    @Column(name = "embarqueciaaerea")
    private String embarqueciaaerea;
    @Column(name = "embarquenumerovoo")
    private String embarquenumerovoo;
    @Column(name = "embarquehorariovoo")
    private String embarquehorariovoo;
    @Column(name = "conexaocidade")
    private String conexaocidade;
    @Column(name = "conexaociaarea")
    private String conexaociaarea;
    @Column(name = "conexaonumerovoo")
    private String conexaonumerovoo;
    @Column(name = "conexaohorariovoo")
    private String conexaohorariovoo;
    @Column(name = "chegadahorario")
    private String chegadahorario;
    @Column(name = "chegadacidade")
    private String chegadacidade;
    @Temporal(TemporalType.DATE)
    @Column(name = "dataaplicacaovisto")  
    private Date dataaplicacaovisto;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "highschool_idhighschool", referencedColumnName = "idhighschool")
    @ManyToOne(optional = false)
    private Highschool highschool;

    public Controlehighschool() {
    }

    public Controlehighschool(Integer idcontroleHighSchool) {
        this.idcontroleHighSchool = idcontroleHighSchool;
    }

    public Integer getIdcontroleHighSchool() {
        return idcontroleHighSchool;
    }

    public void setIdcontroleHighSchool(Integer idcontroleHighSchool) {
        this.idcontroleHighSchool = idcontroleHighSchool;
    }

    public Date getDataEmbarque() {
        return dataEmbarque;
    }

    public void setDataEmbarque(Date dataEmbarque) {
        this.dataEmbarque = dataEmbarque;
    }

    public String getAeroportofinal() {
        return aeroportofinal;
    }

    public void setAeroportofinal(String aeroportofinal) {
        this.aeroportofinal = aeroportofinal;
    }

    public Date getDataEnvioApp() {
        return dataEnvioApp;
    }

    public void setDataEnvioApp(Date dataEnvioApp) {
        this.dataEnvioApp = dataEnvioApp;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getPassagem() {
        return passagem;
    }

    public void setPassagem(String passagem) {
        this.passagem = passagem;
    }

    public String getHistoricochegou() {
        return historicochegou;
    }

    public void setHistoricochegou(String historicochegou) {
        this.historicochegou = historicochegou;
    }

    public String getHistoricotraducao() {
        return historicotraducao;
    }

    public void setHistoricotraducao(String historicotraducao) {
        this.historicotraducao = historicotraducao;
    }

    public String getHistoricoentrega() {
        return historicoentrega;
    }

    public void setHistoricoentrega(String historicoentrega) {
        this.historicoentrega = historicoentrega;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Highschool getHighschool() {
        return highschool;
    }

    public void setHighschool(Highschool highschool) {
        this.highschool = highschool;
    }

    public Date getDatadocumentacaovisto() {
		return datadocumentacaovisto;
	}

	public void setDatadocumentacaovisto(Date datadocumentacaovisto) {
		this.datadocumentacaovisto = datadocumentacaovisto;
	}

	public Date getDatacomprovantepagamento() {
		return datacomprovantepagamento;
	}

	public void setDatacomprovantepagamento(Date datacomprovantepagamento) {
		this.datacomprovantepagamento = datacomprovantepagamento;
	}

	public Date getDataenviopassagem() {
		return dataenviopassagem;
	}

	public void setDataenviopassagem(Date dataenviopassagem) {
		this.dataenviopassagem = dataenviopassagem;
	}

	public String getEmbarquecidade() {
		return embarquecidade;
	}

	public void setEmbarquecidade(String embarquecidade) {
		this.embarquecidade = embarquecidade;
	}

	public String getEmbarqueciaaerea() {
		return embarqueciaaerea;
	}

	public void setEmbarqueciaaerea(String embarqueciaaerea) {
		this.embarqueciaaerea = embarqueciaaerea;
	}

	public String getEmbarquenumerovoo() {
		return embarquenumerovoo;
	}

	public void setEmbarquenumerovoo(String embarquenumerovoo) {
		this.embarquenumerovoo = embarquenumerovoo;
	}

	public String getEmbarquehorariovoo() {
		return embarquehorariovoo;
	}

	public void setEmbarquehorariovoo(String embarquehorariovoo) {
		this.embarquehorariovoo = embarquehorariovoo;
	}

	public String getConexaocidade() {
		return conexaocidade;
	}

	public void setConexaocidade(String conexaocidade) {
		this.conexaocidade = conexaocidade;
	}

	public String getConexaociaarea() {
		return conexaociaarea;
	}

	public void setConexaociaarea(String conexaociaarea) {
		this.conexaociaarea = conexaociaarea;
	}

	public String getConexaonumerovoo() {
		return conexaonumerovoo;
	}

	public void setConexaonumerovoo(String conexaonumerovoo) {
		this.conexaonumerovoo = conexaonumerovoo;
	}

	public String getConexaohorariovoo() {
		return conexaohorariovoo;
	}

	public void setConexaohorariovoo(String conexaohorariovoo) {
		this.conexaohorariovoo = conexaohorariovoo;
	}

	public String getChegadahorario() {
		return chegadahorario;
	}

	public void setChegadahorario(String chegadahorario) {
		this.chegadahorario = chegadahorario;
	}

	public String getChegadacidade() {
		return chegadacidade;
	}

	public void setChegadacidade(String chegadacidade) {
		this.chegadacidade = chegadacidade;
	}

	public Date getDataaplicacaovisto() {
		return dataaplicacaovisto;
	}

	public void setDataaplicacaovisto(Date dataaplicacaovisto) {
		this.dataaplicacaovisto = dataaplicacaovisto;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontroleHighSchool != null ? idcontroleHighSchool.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Controlehighschool)) {
            return false;
        }
        Controlehighschool other = (Controlehighschool) object;
        if ((this.idcontroleHighSchool == null && other.idcontroleHighSchool != null) || (this.idcontroleHighSchool != null && !this.idcontroleHighSchool.equals(other.idcontroleHighSchool))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Controlehighschool[ idcontroleHighSchool=" + idcontroleHighSchool + " ]";
    }
    
}
