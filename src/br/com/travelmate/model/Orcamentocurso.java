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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "orcamentocurso")
@NamedQueries({
    @NamedQuery(name = "Orcamentocurso.findAll", query = "SELECT o FROM Orcamentocurso o")})
public class Orcamentocurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentoCurso")
    private Integer idorcamentoCurso;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorCambio")
    private Float valorCambio;
    @Size(max = 3)
    @Column(name = "cambioAlterado")
    private String cambioAlterado;
    @Column(name = "valor")
    private Float valor;
    @Column (name = "totalmoedaestrangeira")
    private Float totalmoedaestrangeira;
    @Size(max = 50)
    @Column(name = "idioma")
    private String idioma;
    @Size(max = 45)
    @Column(name = "nivelIdioma")
    private String nivelIdioma;
    @Size(max = 100)
    @Column(name = "curso")
    private String curso;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "dataTermino")
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Column(name = "numeroSemanas")
    private Integer numeroSemanas;
    @Size(max = 30)
    @Column(name = "tipoDuracao")
    private String tipoDuracao;
    @Column(name = "aulasSemana")
    private Integer aulasSemana;
    @Size(max = 50)
    @Column(name = "tipoAcomodacao")
    private String tipoAcomodacao;
    @Column(name = "duracaoAcomodacao")
    private Integer duracaoAcomodacao;
    @Lob
    @Size(max = 16777215)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "idCurso")
    private Integer idCurso;
    @Size(max = 50)
    @Column(name = "refeicoes")
    private String refeicoes;
    @Size(max = 50)
    @Column(name = "tipoQuarto")
    private String tipoQuarto;
    @Size(max = 100)
    @Column(name = "materialDidatico")
    private String materialDidatico;
    @Size(max = 100)
    @Column(name = "seguroSaude")
    private String seguroSaude;
    @Size(max = 100)
    @Column(name = "transfer")
    private String transfer;
    @Size(max = 100)
    @Column(name = "passagemAerea")
    private String passagemAerea;
    @Size(max = 100)
    @Column(name = "sedexInternacional")
    private String sedexInternacional;
    @Size(max = 100)
    @Column(name = "vistoConsular")
    private String vistoConsular;
    @Size(max = 100)
    @Column(name = "outrasTaxas")
    private String outrasTaxas;
    @Size(max = 30)
    @Column(name = "situacao")
    private String situacao;
    @Size(max = 50)
    @Column(name = "tipobanheiro")
    private String tipobanheiro;
    @Size(max = 50)
    @Column(name = "turno")
    private String turno;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "orcamentocurso")
    private List<Orcamentocursoformapagamento> orcamentocursoformapagamentoList;
    @JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
    @ManyToOne(optional = false)
    private Cambio cambio; 
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @Column(name = "occliente_idoccliente") 
    private int occliente;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "orcamentocurso")
    private Orcamentomanualseguro orcamentomanualseguro;
    @Transient
    private boolean selecionado;
	@Column(name = "enviadoemail")
	private boolean enviadoemail;
	@Column(name = "tipoorcamento")
	private String tipoorcamento;
	@Transient
    private String nomecliente;

    public Orcamentocurso() { 
    	setTipoorcamento("Curso");
    	setOccliente(12);
    }

    public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getOccliente() {
		return occliente;
	}

	public void setOccliente(int occliente) {
		this.occliente = occliente;
	}

	public Orcamentocurso(Integer idorcamentoCurso) {
        this.idorcamentoCurso = idorcamentoCurso;
    }

    public Integer getIdorcamentoCurso() {
        return idorcamentoCurso;
    }

    public void setIdorcamentoCurso(Integer idorcamentoCurso) {
        this.idorcamentoCurso = idorcamentoCurso;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getValorCambio() {
        return valorCambio;
    }

    public void setValorCambio(Float valorCambio) {
        this.valorCambio = valorCambio;
    }

    public String getCambioAlterado() {
        return cambioAlterado;
    }

    public void setCambioAlterado(String cambioAlterado) {
        this.cambioAlterado = cambioAlterado;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNivelIdioma() {
        return nivelIdioma;
    }

    public void setNivelIdioma(String nivelIdioma) {
        this.nivelIdioma = nivelIdioma;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Float getTotalmoedaestrangeira() {
		return totalmoedaestrangeira;
	}

	public void setTotalmoedaestrangeira(Float totalmoedaestrangeira) {
		this.totalmoedaestrangeira = totalmoedaestrangeira;
	}

	public Integer getNumeroSemanas() {
        return numeroSemanas;
    }

    public void setNumeroSemanas(Integer numeroSemanas) {
        this.numeroSemanas = numeroSemanas;
    }

    public String getTipoDuracao() {
        return tipoDuracao;
    }

    public void setTipoDuracao(String tipoDuracao) {
        this.tipoDuracao = tipoDuracao;
    }

    public Integer getAulasSemana() {
        return aulasSemana;
    }

    public void setAulasSemana(Integer aulasSemana) {
        this.aulasSemana = aulasSemana;
    }

    public String getTipoAcomodacao() {
        return tipoAcomodacao;
    }

    public void setTipoAcomodacao(String tipoAcomodacao) {
        this.tipoAcomodacao = tipoAcomodacao;
    }

    public Integer getDuracaoAcomodacao() {
        return duracaoAcomodacao;
    }

    public void setDuracaoAcomodacao(Integer duracaoAcomodacao) {
        this.duracaoAcomodacao = duracaoAcomodacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(String refeicoes) {
        this.refeicoes = refeicoes;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getMaterialDidatico() {
        return materialDidatico;
    }

    public void setMaterialDidatico(String materialDidatico) {
        this.materialDidatico = materialDidatico;
    }

    public String getSeguroSaude() {
        return seguroSaude;
    }

    public void setSeguroSaude(String seguroSaude) {
        this.seguroSaude = seguroSaude;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getPassagemAerea() {
        return passagemAerea;
    }

    public void setPassagemAerea(String passagemAerea) {
        this.passagemAerea = passagemAerea;
    }

    public String getSedexInternacional() {
        return sedexInternacional;
    }

    public void setSedexInternacional(String sedexInternacional) {
        this.sedexInternacional = sedexInternacional;
    }

    public String getVistoConsular() {
        return vistoConsular;
    }

    public void setVistoConsular(String vistoConsular) {
        this.vistoConsular = vistoConsular;
    }

    public String getOutrasTaxas() {
        return outrasTaxas;
    }

    public void setOutrasTaxas(String outrasTaxas) {
        this.outrasTaxas = outrasTaxas;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<Orcamentocursoformapagamento> getOrcamentocursoformapagamentoList() {
        return orcamentocursoformapagamentoList;
    }

    public void setOrcamentocursoformapagamentoList(List<Orcamentocursoformapagamento> orcamentocursoformapagamentoList) {
        this.orcamentocursoformapagamentoList = orcamentocursoformapagamentoList;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }
  
	public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipobanheiro() {
		return tipobanheiro;
	}

	public void setTipobanheiro(String tipobanheiro) {
		this.tipobanheiro = tipobanheiro;
	}

	

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public Orcamentomanualseguro getOrcamentomanualseguro() {
		return orcamentomanualseguro;
	}

	public void setOrcamentomanualseguro(Orcamentomanualseguro orcamentomanualseguro) {
		this.orcamentomanualseguro = orcamentomanualseguro;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	

	public boolean isEnviadoemail() {
		return enviadoemail;
	}

	public void setEnviadoemail(boolean enviadoemail) {
		this.enviadoemail = enviadoemail;
	}

	public String getNomecliente() {
		return nomecliente;
	}

	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}

	public String getTipoorcamento() {
		return tipoorcamento;
	}

	public void setTipoorcamento(String tipoorcamento) {
		this.tipoorcamento = tipoorcamento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentoCurso != null ? idorcamentoCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orcamentocurso)) {
            return false;
        }
        Orcamentocurso other = (Orcamentocurso) object;
        if ((this.idorcamentoCurso == null && other.idorcamentoCurso != null) || (this.idorcamentoCurso != null && !this.idorcamentoCurso.equals(other.idorcamentoCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Orcamentocurso[ idorcamentoCurso=" + idorcamentoCurso + " ]";
    }
    
}
