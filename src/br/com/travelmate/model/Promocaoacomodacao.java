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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "promocaoacomodacao")
public class Promocaoacomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromoacaoacomodacao")
    private Integer idpromoacaoacomodacao;
    @Column(name = "datavalidadeinicial")
    @Temporal(TemporalType.DATE)
    private Date datavalidadeinicial;
    @Column(name = "datavalidadefinal")
    @Temporal(TemporalType.DATE)
    private Date datavalidadefinal;
    @Column(name = "datainicioiniciocurso")
    @Temporal(TemporalType.DATE)
    private Date datainicioiniciocurso;
    @Column(name = "datainicioterminiocurso")
    @Temporal(TemporalType.DATE)
    private Date datainicioterminiocurso;
    @Column(name = "datainicioacomodacao")
    @Temporal(TemporalType.DATE)
    private Date datainicioacomodacao;
    @Column(name = "dataterminioacodomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataterminioacodomodacao;
    @Column(name = "datamatricula")
    @Temporal(TemporalType.DATE)
    private Date datamatricula;
    @Column(name = "acomodacaoescola")
    private Boolean acomodacaoescola;
    @Size(max = 50)
    @Column(name = "tipoacomodacao")
    private String tipoacomodacao;
    @Column(name = "numerosemanainicio")
    private Integer numerosemanainicio;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorsemanaacima")
    private Float valorsemanaacima;
    @Column(name = "valorsemanaigual")
    private Float valorsemanaigual;
    @Column(name = "valormaximodesconto")
    private Float valormaximodesconto;
    @Size(max = 1)
    @Column(name = "tipodesconto")
    private String tipodesconto;
    @Column(name = "valorsemana")
    private Float valorsemana;
    @Column(name = "valortotal")
    private Float valortotal;
    @Size(max = 50)
    @Column(name = "tipoquarto")
    private String tipoquarto;
    @Size(max = 50)
    @Column(name = "tiporefeicao")
    private String tiporefeicao;
    @Size(max = 50)
    @Column(name = "tipobanheiro")
    private String tipobanheiro;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "promocaoacomodacao")
    private List<Promocaoacomodacaocidade> promocaoacomodacaocidadeList;
    @Column(name = "dataterminocurso")
    @Temporal(TemporalType.DATE)
    private Date dataterminocurso;

    public Promocaoacomodacao() {
    	setAcomodacaoescola(false);
    }

    public Promocaoacomodacao(Integer idpromoacaoacomodacao) {
        this.idpromoacaoacomodacao = idpromoacaoacomodacao;
    }

    public Integer getIdpromoacaoacomodacao() {
        return idpromoacaoacomodacao;
    }

    public void setIdpromoacaoacomodacao(Integer idpromoacaoacomodacao) {
        this.idpromoacaoacomodacao = idpromoacaoacomodacao;
    }

    public Date getDatavalidadeinicial() {
        return datavalidadeinicial;
    }

    public void setDatavalidadeinicial(Date datavalidadeinicial) {
        this.datavalidadeinicial = datavalidadeinicial;
    }

    public Date getDatavalidadefinal() {
        return datavalidadefinal;
    }

    public void setDatavalidadefinal(Date datavalidadefinal) {
        this.datavalidadefinal = datavalidadefinal;
    }

    public Date getDatainicioiniciocurso() {
        return datainicioiniciocurso;
    }

    public void setDatainicioiniciocurso(Date datainicioiniciocurso) {
        this.datainicioiniciocurso = datainicioiniciocurso;
    }

    public Date getDatainicioterminiocurso() {
        return datainicioterminiocurso;
    }

    public void setDatainicioterminiocurso(Date datainicioterminiocurso) {
        this.datainicioterminiocurso = datainicioterminiocurso;
    }

    public Date getDatainicioacomodacao() {
        return datainicioacomodacao;
    }

    public void setDatainicioacomodacao(Date datainicioacomodacao) {
        this.datainicioacomodacao = datainicioacomodacao;
    }

    public Date getDataterminioacodomodacao() {
        return dataterminioacodomodacao;
    }

    public void setDataterminioacodomodacao(Date dataterminioacodomodacao) {
        this.dataterminioacodomodacao = dataterminioacodomodacao;
    }

    public Date getDatamatricula() {
        return datamatricula;
    }

    public void setDatamatricula(Date datamatricula) {
        this.datamatricula = datamatricula;
    }

    public Boolean getAcomodacaoescola() {
        return acomodacaoescola;
    }

    public void setAcomodacaoescola(Boolean acomodacaoescola) {
        this.acomodacaoescola = acomodacaoescola;
    }

    public String getTipoacomodacao() {
        return tipoacomodacao;
    }

    public void setTipoacomodacao(String tipoacomodacao) {
        this.tipoacomodacao = tipoacomodacao;
    }

    public Integer getNumerosemanainicio() {
        return numerosemanainicio;
    }

    public void setNumerosemanainicio(Integer numerosemanainicio) {
        this.numerosemanainicio = numerosemanainicio;
    }

    public Integer getNumerosemanafinal() {
        return numerosemanafinal;
    }

    public void setNumerosemanafinal(Integer numerosemanafinal) {
        this.numerosemanafinal = numerosemanafinal;
    }

    public Float getValorsemanaacima() {
        return valorsemanaacima;
    }

    public void setValorsemanaacima(Float valorsemanaacima) {
        this.valorsemanaacima = valorsemanaacima;
    }

    public Float getValorsemanaigual() {
        return valorsemanaigual;
    }

    public void setValorsemanaigual(Float valorsemanaigual) {
        this.valorsemanaigual = valorsemanaigual;
    }

    public Float getValormaximodesconto() {
        return valormaximodesconto;
    }

    public void setValormaximodesconto(Float valormaximodesconto) {
        this.valormaximodesconto = valormaximodesconto;
    }

    public String getTipodesconto() {
        return tipodesconto;
    }

    public void setTipodesconto(String tipodesconto) {
        this.tipodesconto = tipodesconto;
    }

    public Float getValorsemana() {
        return valorsemana;
    }

    public void setValorsemana(Float valorsemana) {
        this.valorsemana = valorsemana;
    }

    public Float getValortotal() {
        return valortotal;
    }

    public void setValortotal(Float valortotal) {
        this.valortotal = valortotal;
    }

    public List<Promocaoacomodacaocidade> getPromocaoacomodacaocidadeList() {
        return promocaoacomodacaocidadeList;
    }

    public void setPromocaoacomodacaocidadeList(List<Promocaoacomodacaocidade> promocaoacomodacaocidadeList) {
        this.promocaoacomodacaocidadeList = promocaoacomodacaocidadeList;
    }

    public String getTipoquarto() {
		return tipoquarto;
	}

	public void setTipoquarto(String tipoquarto) {
		this.tipoquarto = tipoquarto;
	}

	public String getTiporefeicao() {
		return tiporefeicao;
	}

	public void setTiporefeicao(String tiporefeicao) {
		this.tiporefeicao = tiporefeicao;
	}

	public String getTipobanheiro() {
		return tipobanheiro;
	}

	public void setTipobanheiro(String tipobanheiro) {
		this.tipobanheiro = tipobanheiro;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getDataterminocurso() {
		return dataterminocurso;
	}

	public void setDataterminocurso(Date dataterminocurso) {
		this.dataterminocurso = dataterminocurso;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromoacaoacomodacao != null ? idpromoacaoacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaoacomodacao)) {
            return false;
        }
        Promocaoacomodacao other = (Promocaoacomodacao) object;
        if ((this.idpromoacaoacomodacao == null && other.idpromoacaoacomodacao != null) || (this.idpromoacaoacomodacao != null && !this.idpromoacaoacomodacao.equals(other.idpromoacaoacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaoacomodacao[ idpromoacaoacomodacao=" + idpromoacaoacomodacao + " ]";
    }
    
}
