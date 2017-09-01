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
 * @author Wolverine
 */
@Entity
@Table(name = "modeloorcamentocurso")
public class Modeloorcamentocurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorcamentoCurso")
    private Integer idorcamentoCurso;
    @Size(max = 200)
    @Column(name = "nome")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorCambio")
    private Float valorCambio;
    @Column(name = "valor")
    private Float valor;
    @Size(max = 100)
    @Column(name = "curso")
    private String curso;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
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
    @Column(name = "dataValidade")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;
    @Size(max = 45)
    @Column(name = "duracao")
    private String duracao;
    @Size(max = 30)
    @Column(name = "situacao")
    private String situacao;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
    @ManyToOne(optional = false)
    private Cambio cambio;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
    @ManyToOne(optional = false)
    private Unidadenegocio unidadenegocio;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "modeloorcamentocurso")
    private List<Modeloorcamentocursoforma> modeloorcamentocursoformaList;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "modeloorcamentocurso")
    private List<Modeloprodutoorcamentocurso> modeloprodutoorcamentocursoList;

    public Modeloorcamentocurso() {
    }

    public Modeloorcamentocurso(Integer idorcamentoCurso) {
        this.idorcamentoCurso = idorcamentoCurso;
    }

    public Integer getIdorcamentoCurso() {
        return idorcamentoCurso;
    }

    public void setIdorcamentoCurso(Integer idorcamentoCurso) {
        this.idorcamentoCurso = idorcamentoCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValorCambio() {
        return valorCambio;
    }

    public void setValorCambio(Float valorCambio) {
        this.valorCambio = valorCambio;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
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

    public List<Modeloorcamentocursoforma> getModeloorcamentocursoformaList() {
        return modeloorcamentocursoformaList;
    }

    public void setModeloorcamentocursoformaList(List<Modeloorcamentocursoforma> modeloorcamentocursoformaList) {
        this.modeloorcamentocursoformaList = modeloorcamentocursoformaList;
    }

    public List<Modeloprodutoorcamentocurso> getModeloprodutoorcamentocursoList() {
        return modeloprodutoorcamentocursoList;
    }

    public void setModeloprodutoorcamentocursoList(List<Modeloprodutoorcamentocurso> modeloprodutoorcamentocursoList) {
        this.modeloprodutoorcamentocursoList = modeloprodutoorcamentocursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorcamentoCurso != null ? idorcamentoCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Modeloorcamentocurso)) {
            return false;
        }
        Modeloorcamentocurso other = (Modeloorcamentocurso) object;
        if ((this.idorcamentoCurso == null && other.idorcamentoCurso != null) || (this.idorcamentoCurso != null && !this.idorcamentoCurso.equals(other.idorcamentoCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Modeloorcamentocurso[ idorcamentoCurso=" + idorcamentoCurso + " ]";
    }
    
}

