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
import javax.persistence.ManyToOne;
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
@Table(name = "promocaotaxacurso")
public class Promocaotaxacurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocaotaxacurso")
    private Integer idpromocaotaxacurso;
    @Column(name = "acomodacaoselecionada")
    private Boolean acomodacaoselecionada;
    @Column(name = "datainiciocursoinicial")
    @Temporal(TemporalType.DATE)
    private Date datainiciocursoinicial;
    @Column(name = "datainiciocursofinal")
    @Temporal(TemporalType.DATE)
    private Date datainiciocursofinal;
    @Column(name = "datamatriculaenciadaate")
    @Temporal(TemporalType.DATE)
    private Date datamatriculaenciadaate;
    @Column(name = "numerosemanasinicial")
    private Integer numerosemanasinicial;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    @Column(name = "dataterminio")
    @Temporal(TemporalType.DATE)
    private Date dataterminio;
    @Size(max = 45)
    @Column(name = "turno")
    private String turno;
    @Column(name = "cargahoraria")
    private Integer cargahoraria;
    @Size(max = 45)
    @Column(name = "tipocargahoraria")
    private String tipocargahoraria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorporsemana")
    private Float valorporsemana;
    @Size(max = 2)
    @Column(name = "tipodesconto")
    private String tipodesconto;
    @Column(name = "valordesconto")
    private Float valordesconto;
    @Column(name = "datavalidadeinicial")
    @Temporal(TemporalType.DATE)
    private Date datavalidadeinicial;
    @Column(name = "datavalidadefinal")
    @Temporal(TemporalType.DATE)
    private Date datavalidadefinal;
    @Column(name = "dataacomodacaoinicial")
    @Temporal(TemporalType.DATE)
    private Date dataacomodacaoinicial;
    @Column(name = "datafinalacomodacao")
    @Temporal(TemporalType.DATE)
    private Date datafinalacomodacao;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "promocaotaxacurso")
    private List<Promocaotaxacidade> promocaotaxacidadeList;
    @JoinColumn(name = "produtosorcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
    @ManyToOne(optional = false)
    private Produtosorcamento produtosorcamento; 

    public Promocaotaxacurso() {
    }

    public Promocaotaxacurso(Integer idpromocaotaxacurso) {
        this.idpromocaotaxacurso = idpromocaotaxacurso;
    }

    public Integer getIdpromocaotaxacurso() {
        return idpromocaotaxacurso;
    }

    public void setIdpromocaotaxacurso(Integer idpromocaotaxacurso) {
        this.idpromocaotaxacurso = idpromocaotaxacurso;
    }

    public Boolean getAcomodacaoselecionada() {
        return acomodacaoselecionada;
    }

    public void setAcomodacaoselecionada(Boolean acomodacaoselecionada) {
        this.acomodacaoselecionada = acomodacaoselecionada;
    }

    public Date getDatainiciocursoinicial() {
        return datainiciocursoinicial;
    }

    public void setDatainiciocursoinicial(Date datainiciocursoinicial) {
        this.datainiciocursoinicial = datainiciocursoinicial;
    }

    public Date getDatainiciocursofinal() {
        return datainiciocursofinal;
    }

    public void setDatainiciocursofinal(Date datainiciocursofinal) {
        this.datainiciocursofinal = datainiciocursofinal;
    }

    public Date getDatamatriculaenciadaate() {
        return datamatriculaenciadaate;
    }

    public void setDatamatriculaenciadaate(Date datamatriculaenciadaate) {
        this.datamatriculaenciadaate = datamatriculaenciadaate;
    }

    public Integer getNumerosemanasinicial() {
        return numerosemanasinicial;
    }

    public void setNumerosemanasinicial(Integer numerosemanasinicial) {
        this.numerosemanasinicial = numerosemanasinicial;
    }

    public Integer getNumerosemanafinal() {
        return numerosemanafinal;
    }

    public void setNumerosemanafinal(Integer numerosemanafinal) {
        this.numerosemanafinal = numerosemanafinal;
    }

    public Date getDataterminio() {
        return dataterminio;
    }

    public void setDataterminio(Date dataterminio) {
        this.dataterminio = dataterminio;
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

    public Float getValorporsemana() {
        return valorporsemana;
    }

    public void setValorporsemana(Float valorporsemana) {
        this.valorporsemana = valorporsemana;
    }

    public String getTipodesconto() {
        return tipodesconto;
    }

    public void setTipodesconto(String tipodesconto) {
        this.tipodesconto = tipodesconto;
    }

    public Float getValordesconto() {
        return valordesconto;
    }

    public void setValordesconto(Float valordesconto) {
        this.valordesconto = valordesconto;
    }

    public List<Promocaotaxacidade> getPromocaotaxacidadeList() {
        return promocaotaxacidadeList;
    }

    public void setPromocaotaxacidadeList(List<Promocaotaxacidade> promocaotaxacidadeList) {
        this.promocaotaxacidadeList = promocaotaxacidadeList;
    }

    public Produtosorcamento getProdutosorcamento() {
        return produtosorcamento;
    }

    public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
        this.produtosorcamento = produtosorcamento;
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

	public Date getDataacomodacaoinicial() {
		return dataacomodacaoinicial;
	}

	public void setDataacomodacaoinicial(Date dataacomodacaoinicial) {
		this.dataacomodacaoinicial = dataacomodacaoinicial;
	}

	public Date getDatafinalacomodacao() {
		return datafinalacomodacao;
	}

	public void setDatafinalacomodacao(Date datafinalacomodacao) {
		this.datafinalacomodacao = datafinalacomodacao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromocaotaxacurso != null ? idpromocaotaxacurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Promocaotaxacurso)) {
            return false;
        }
        Promocaotaxacurso other = (Promocaotaxacurso) object;
        if ((this.idpromocaotaxacurso == null && other.idpromocaotaxacurso != null) || (this.idpromocaotaxacurso != null && !this.idpromocaotaxacurso.equals(other.idpromocaotaxacurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Promocaotaxacurso[ idpromocaotaxacurso=" + idpromocaotaxacurso + " ]";
    }
    
}

