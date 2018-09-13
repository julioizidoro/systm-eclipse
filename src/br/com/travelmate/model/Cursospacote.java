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
 * @author wolverine
 */
@Entity
@Table(name = "cursospacote")
public class Cursospacote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcursospacote")
    private Integer idcursospacote;
    @Column(name = "numerosemanacurso")
    private Integer numerosemanacurso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valortotalcurso")
    private Float valortotalcurso;
    @Column(name = "numerosemanaacomodacao")
    private Float numerosemanaacomodacao;
    @Column(name = "valortotalpacote")
    private float valortotalpacote;
    @Lob    
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "datainicovenda")
    @Temporal(TemporalType.DATE)
    private Date datainicovenda;
    @Column(name = "dataterminovenda")
    @Temporal(TemporalType.DATE)
    private Date dataterminovenda;
    @Column(name = "datainiciocurso")
    @Temporal(TemporalType.DATE)
    private Date datainiciocurso;
    @Column(name = "dataterminocurso")
    @Temporal(TemporalType.DATE)
    private Date dataterminocurso;
    @Column(name = "valortotalacomodacao")
    private Float valortotalacomodacao;
    @JoinColumn(name = "valorcoprodutos_curso", referencedColumnName = "idvalorcoprodutos")
    @ManyToOne(optional = false)
    private Valorcoprodutos valorcoprodutos_curso;
    @JoinColumn(name = "valorcoprodutos_acomodacao", referencedColumnName = "idvalorcoprodutos")
    @ManyToOne(optional = false)
    private Valorcoprodutos valorcoprodutos_acomodacao;
    @JoinColumn(name = "fornecedorcidadeidioma_idfornecedorcidadeidioma", referencedColumnName = "idfornecedorcidadeidioma")
    @ManyToOne(optional = false)
    private Fornecedorcidadeidioma fornecedorcidadeidioma;
    @Column(name = "promocaoescola")
    private Float promocaoescola; 
    @Column(name = "valorcambio")
    private Float valorcambio;
    @Column(name = "valoravista")
	private Float valoravista;
    @Column(name = "comissaopremium")
	private Float comissaopremium;
    @Column(name = "comissaoexpress")
	private Float comissaoexpress;
    @Column(name = "descritivoacomodacao")
	private String descritivoacomodacao; 
    @Column(name = "taxaacomodacao")
   	private String taxaacomodacao; 
    @Column(name = "taxamatricula")
   	private String taxamatricula; 
    @Column(name = "taxamaterial")
   	private String taxamaterial; 
    @Column(name = "transfer")
   	private String transfer; 
    @Column(name = "outras")
   	private String outras; 
    @Column(name = "comissao")
   	private String comissao; 
    @Column(name = "pagamentoboleto")
   	private String pagamentoboleto; 
    @Column(name = "pagamentocartao")
   	private String pagamentocartao; 
    @Column(name = "pagamentofinanciamento")
   	private String pagamentofinanciamento; 
    @Column(name = "idademinina")
   	private String idademinina; 
    @JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
    @ManyToOne(optional = false)
    private Produtos produtos;
    @Column(name = "provincia")
   	private String provincia; 
    @Column(name = "duracao")
   	private String duracao; 
    @Column(name = "passagemaerea")
   	private String passagemaerea; 
    @Column(name = "modalidadework")
   	private String modalidade; 
    @Column(name = "mostrarescola")
   	private boolean mostrarescola; 
    @Transient
    private boolean cursos;
    @Transient
    private boolean aupair;
    @Transient
    private boolean worktravel;
    @Transient
    private boolean highschool;
    @Transient
    private boolean voluntariado;
    @Column(name = "descontotm1")
    private Float descontotm1;
    @Column(name = "descontotm2")
    private Float descontotm2;
    @Column(name = "ano1")
    private int ano1;
    @Column(name = "ano2")
    private int ano2;
    @Column(name = "anotarifario")
    private int anotarifario;
    @Column(name = "projetovoluntariado")
    private String projetovoluntariado;
    @Column(name = "especial")
    private boolean especial;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "cursospacote")
    private List<Voluntariadopacote> voluntariadopacoteList;
    @Column(name = "valormoedaestrangeira")
    private float valormoedaestrangeira;
    @Column(name = "descontotmreal1")
    private float descontotmreal1;
    @Column(name = "descontotmreal2")
    private float descontotmreal2;
    @JoinColumn(name = "pais_idpais", referencedColumnName = "idpais")
    @ManyToOne(optional = false)
    private Pais pais;
    
    
    
    
    public Cursospacote() {
    		setValortotalacomodacao(0.0f);
    		setValortotalcurso(0.0f);
    		setPromocaoescola(0.0f);
    		setDescontotm1(0.0f);
    		setDescontotm2(0.0f);
    		setValorcambio(0.0f);
    }

    public Cursospacote(Integer idcursospacote) {
        this.idcursospacote = idcursospacote;
    }

    public Integer getIdcursospacote() {
        return idcursospacote;
    }

    public void setIdcursospacote(Integer idcursospacote) {
        this.idcursospacote = idcursospacote;
    }

    public Integer getNumerosemanacurso() {
        return numerosemanacurso;
    }

    public void setNumerosemanacurso(Integer numerosemanacurso) {
        this.numerosemanacurso = numerosemanacurso;
    }

    public Float getValortotalcurso() {
        return valortotalcurso;
    }

    public void setValortotalcurso(Float valortotalcurso) {
        this.valortotalcurso = valortotalcurso;
    }

    public Float getNumerosemanaacomodacao() {
        return numerosemanaacomodacao;
    }

    public void setNumerosemanaacomodacao(Float numerosemanaacomodacao) {
        this.numerosemanaacomodacao = numerosemanaacomodacao;
    } 

	public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDatainicovenda() {
        return datainicovenda;
    }

    public void setDatainicovenda(Date datainicovenda) {
        this.datainicovenda = datainicovenda;
    }

    public Date getDataterminovenda() {
        return dataterminovenda;
    }

    public void setDataterminovenda(Date dataterminovenda) {
        this.dataterminovenda = dataterminovenda;
    }

    public Date getDatainiciocurso() {
        return datainiciocurso;
    }

    public void setDatainiciocurso(Date datainiciocurso) {
        this.datainiciocurso = datainiciocurso;
    }

    public Date getDataterminocurso() {
        return dataterminocurso;
    }

    public void setDataterminocurso(Date dataterminocurso) {
        this.dataterminocurso = dataterminocurso;
    }

    public Float getValortotalacomodacao() {
        return valortotalacomodacao;
    }

    public void setValortotalacomodacao(Float valortotalacomodacao) {
        this.valortotalacomodacao = valortotalacomodacao;
    }
  
    public Valorcoprodutos getValorcoprodutos_curso() {
		return valorcoprodutos_curso;
	}

	public void setValorcoprodutos_curso(Valorcoprodutos valorcoprodutos_curso) {
		this.valorcoprodutos_curso = valorcoprodutos_curso;
	}

	public Valorcoprodutos getValorcoprodutos_acomodacao() {
		return valorcoprodutos_acomodacao;
	}

	public void setValorcoprodutos_acomodacao(Valorcoprodutos valorcoprodutos_acomodacao) {
		this.valorcoprodutos_acomodacao = valorcoprodutos_acomodacao;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
        return fornecedorcidadeidioma;
    }

    public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
        this.fornecedorcidadeidioma = fornecedorcidadeidioma;
    }

    public Float getPromocaoescola() {
		return promocaoescola;
	}

	public void setPromocaoescola(Float promocaoescola) {
		this.promocaoescola = promocaoescola;
	} 

	public Float getDescontotm1() {
		return descontotm1;
	}

	public void setDescontotm1(Float descontotm1) {
		this.descontotm1 = descontotm1;
	}

	public Float getDescontotm2() {
		return descontotm2;
	}

	public void setDescontotm2(Float descontotm2) {
		this.descontotm2 = descontotm2;
	}

	public int getAno1() {
		return ano1;
	}

	public void setAno1(int ano1) {
		this.ano1 = ano1;
	}

	public int getAno2() {
		return ano2;
	}

	public void setAno2(int ano2) {
		this.ano2 = ano2;
	}

	public Float getValorcambio() {
		return valorcambio;
	}

	public void setValorcambio(Float valorcambio) {
		this.valorcambio = valorcambio;
	}

	public Float getValoravista() {
		return valoravista;
	}

	public void setValoravista(Float valoravista) {
		this.valoravista = valoravista;
	}

	public Float getComissaopremium() {
		return comissaopremium;
	}

	public void setComissaopremium(Float comissaopremium) {
		this.comissaopremium = comissaopremium;
	}

	public Float getComissaoexpress() {
		return comissaoexpress;
	}

	public void setComissaoexpress(Float comissaoexpress) {
		this.comissaoexpress = comissaoexpress;
	}

	public String getDescritivoacomodacao() {
		return descritivoacomodacao;
	}

	public void setDescritivoacomodacao(String descritivoacomodacao) {
		this.descritivoacomodacao = descritivoacomodacao;
	}

	public String getTaxaacomodacao() {
		return taxaacomodacao;
	}

	public void setTaxaacomodacao(String taxaacomodacao) {
		this.taxaacomodacao = taxaacomodacao;
	}

	public String getTaxamatricula() {
		return taxamatricula;
	}

	public void setTaxamatricula(String taxamatricula) {
		this.taxamatricula = taxamatricula;
	}

	public String getTaxamaterial() {
		return taxamaterial;
	}

	public void setTaxamaterial(String taxamaterial) {
		this.taxamaterial = taxamaterial;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getOutras() {
		return outras;
	}

	public void setOutras(String outras) {
		this.outras = outras;
	}

	public String getComissao() {
		return comissao;
	}

	public void setComissao(String comissao) {
		this.comissao = comissao;
	}

	public String getPagamentoboleto() {
		return pagamentoboleto;
	}

	public void setPagamentoboleto(String pagamentoboleto) {
		this.pagamentoboleto = pagamentoboleto;
	}

	public String getPagamentocartao() {
		return pagamentocartao;
	}

	public void setPagamentocartao(String pagamentocartao) {
		this.pagamentocartao = pagamentocartao;
	}

	public String getPagamentofinanciamento() {
		return pagamentofinanciamento;
	}

	public void setPagamentofinanciamento(String pagamentofinanciamento) {
		this.pagamentofinanciamento = pagamentofinanciamento;
	}

	public String getIdademinina() {
		return idademinina;
	}

	public void setIdademinina(String idademinina) {
		this.idademinina = idademinina;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getPassagemaerea() {
		return passagemaerea;
	}

	public void setPassagemaerea(String passagemaerea) {
		this.passagemaerea = passagemaerea;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public boolean isCursos() {
		return cursos;
	}

	public void setCursos(boolean cursos) {
		this.cursos = cursos;
	}

	public boolean isAupair() {
		return aupair;
	}

	public void setAupair(boolean aupair) {
		this.aupair = aupair;
	}

	public boolean isWorktravel() {
		return worktravel;
	}

	public void setWorktravel(boolean worktravel) {
		this.worktravel = worktravel;
	}

	public boolean isHighschool() {
		return highschool;
	}

	public void setHighschool(boolean highschool) {
		this.highschool = highschool;
	} 

	public boolean isMostrarescola() {
		return mostrarescola;
	}

	public void setMostrarescola(boolean mostrarescola) {
		this.mostrarescola = mostrarescola;
	}

	public int getAnotarifario() {
		return anotarifario;
	}

	public void setAnotarifario(int anotarifario) {
		this.anotarifario = anotarifario;
	}

	public String getProjetovoluntariado() {
		return projetovoluntariado;
	}

	public void setProjetovoluntariado(String projetovoluntariado) {
		this.projetovoluntariado = projetovoluntariado;
	}

	public boolean isVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(boolean voluntariado) {
		this.voluntariado = voluntariado;
	}

	public List<Voluntariadopacote> getVoluntariadopacoteList() {
		return voluntariadopacoteList;
	}

	public void setVoluntariadopacoteList(List<Voluntariadopacote> voluntariadopacoteList) {
		this.voluntariadopacoteList = voluntariadopacoteList;
	}

	public boolean isEspecial() {
		return especial;
	}

	public void setEspecial(boolean especial) {
		this.especial = especial;
	}

	public float getValormoedaestrangeira() {
		return valormoedaestrangeira;
	}

	public void setValormoedaestrangeira(float valormoedaestrangeira) {
		this.valormoedaestrangeira = valormoedaestrangeira;
	}

	public float getValortotalpacote() {
		return valortotalpacote;
	}

	public void setValortotalpacote(float valortotalpacote) {
		this.valortotalpacote = valortotalpacote;
	}

	

	public float getDescontotmreal1() {
		return descontotmreal1;
	}

	public void setDescontotmreal1(float descontotmreal1) {
		this.descontotmreal1 = descontotmreal1;
	}

	public float getDescontotmreal2() {
		return descontotmreal2;
	}

	public void setDescontotmreal2(float descontotmreal2) {
		this.descontotmreal2 = descontotmreal2;
	}

	public void setDescontotmreal2(int descontotmreal2) {
		this.descontotmreal2 = descontotmreal2;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcursospacote != null ? idcursospacote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cursospacote)) {
            return false;
        }
        Cursospacote other = (Cursospacote) object;
        if ((this.idcursospacote == null && other.idcursospacote != null) || (this.idcursospacote != null && !this.idcursospacote.equals(other.idcursospacote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Cursospacote[ idcursospacote=" + idcursospacote + " ]";
    }
    
}
