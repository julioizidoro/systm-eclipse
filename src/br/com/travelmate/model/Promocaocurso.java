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
@Table(name = "promocaocurso")
public class Promocaocurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromoacaocurso")
    private Integer idpromoacaocurso;
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
    @Size(max = 30)
    @Column(name = "turno")
    private String turno;
    @Column(name = "cargahoraria")
    private Integer cargahoraria;
    @Size(max = 30)
    @Column(name = "tipocargahoraria")
    private String tipocargahoraria;
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
    @Column(name = "dataterminocurso")
    @Temporal(TemporalType.DATE)
    private Date dataterminocurso;
    @Size(max = 1)
    @Column(name = "tipodesconto")
    private String tipodesconto;
    @Column(name = "valorsemana")
    private Float valorsemana;
    @Column(name = "valortotal")
    private Float valortotal;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "acumulapromocao")
    private boolean acumulapromocao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "promoacaocurso")
    private List<Promocaocursocidade> promocaocursocidadeList;

    public Promocaocurso() {
    }

    public Promocaocurso(Integer idpromoacaocurso) {
        this.idpromoacaocurso = idpromoacaocurso;
    }

    public Integer getIdpromoacaocurso() {
        return idpromoacaocurso;
    }

    public void setIdpromoacaocurso(Integer idpromoacaocurso) {
        this.idpromoacaocurso = idpromoacaocurso;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(Integer cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public String getTipocargahoraria() {
        return tipocargahoraria;
    }

    public void setTipocargahoraria(String tipocargahoraria) {
        this.tipocargahoraria = tipocargahoraria;
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

    public List<Promocaocursocidade> getPromocaocursocidadeList() {
        return promocaocursocidadeList;
    }

    public void setPromocaocursocidadeList(List<Promocaocursocidade> promocaocursocidadeList) {
        this.promocaocursocidadeList = promocaocursocidadeList;
    }

    public Date getDataterminocurso() {
		return dataterminocurso;
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

	public void setDataterminocurso(Date dataterminocurso) {
		this.dataterminocurso = dataterminocurso;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isAcumulapromocao() {
		return acumulapromocao;
	}

	public void setAcumulapromocao(boolean acumulapromocao) {
		this.acumulapromocao = acumulapromocao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromoacaocurso != null ? idpromoacaocurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaocurso)) {
            return false;
        }
        Promocaocurso other = (Promocaocurso) object;
        if ((this.idpromoacaocurso == null && other.idpromoacaocurso != null) || (this.idpromoacaocurso != null && !this.idpromoacaocurso.equals(other.idpromoacaocurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promoacaocurso[ idpromoacaocurso=" + idpromoacaocurso + " ]";
    }
    
}

