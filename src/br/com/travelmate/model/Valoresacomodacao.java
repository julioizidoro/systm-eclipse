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
@Table(name = "valoresacomodacao")
public class Valoresacomodacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvaloresacomodacao")
    private Integer idvaloresacomodacao;
    @Size(max = 200)
    @Column(name = "residencia")
    private String residencia;
    @Column(name = "datavalidadeinicial")
    @Temporal(TemporalType.DATE)
    private Date datavalidadeinicial;
    @Column(name = "datavalidadefinal")
    @Temporal(TemporalType.DATE)
    private Date datavalidadefinal;
    @Column(name = "numerosemanainicial")
    private Integer numerosemanainicial;
    @Column(name = "numerosemanafinal")
    private Integer numerosemanafinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Column(name = "menoridadevalor")
    private Float menoridadevalor;
    @Size(max = 100)
    @Column(name = "pagardeposito")
    private String pagardeposito;
    @Column(name = "datasumplementoinicial")
    @Temporal(TemporalType.DATE)
    private Date datasumplementoinicial;
    @Column(name = "datasuplementofinal")
    @Temporal(TemporalType.DATE)
    private Date datasuplementofinal;
    @Column(name = "valorsuplemento")
    private Float valorsuplemento;
    @Column(name = "valortm")
    private Float valortm;
    @Column(name = "valorfranquia")
    private Float valorfranquia;
    @Column(name = "valorparceiro")
    private Float valorparceiro;
    @Column(name = "taxamatricula")
    private Float taxamatricula;
    @Size(max = 50)
    @Column(name = "tiporefeicao")
    private String tiporefeicao;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "valoresacomodacao")
    private List<Acomodacao> acomodacaoList;
    @JoinColumn(name = "moedas_idmoedas", referencedColumnName = "idmoedas")
    @ManyToOne(optional = false)
    private Moedas moedas;
    @JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
    @ManyToOne(optional = false)
    private Fornecedorcidade fornecedorcidade;
    @JoinColumn(name = "tipoacomodacao_idtipoacomodacao", referencedColumnName = "idtipoacomodacao")
    @ManyToOne(optional = false)
    private Tipoacomodacao tipoacomodacao;

    public Valoresacomodacao() {
    }

    public Valoresacomodacao(Integer idvaloresacomodacao) {
        this.idvaloresacomodacao = idvaloresacomodacao;
    }

    public Integer getIdvaloresacomodacao() {
        return idvaloresacomodacao;
    }

    public void setIdvaloresacomodacao(Integer idvaloresacomodacao) {
        this.idvaloresacomodacao = idvaloresacomodacao;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
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

    public Integer getNumerosemanainicial() {
        return numerosemanainicial;
    }

    public void setNumerosemanainicial(Integer numerosemanainicial) {
        this.numerosemanainicial = numerosemanainicial;
    }

    public Integer getNumerosemanafinal() {
        return numerosemanafinal;
    }

    public void setNumerosemanafinal(Integer numerosemanafinal) {
        this.numerosemanafinal = numerosemanafinal;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getMenoridadevalor() {
        return menoridadevalor;
    }

    public void setMenoridadevalor(Float menoridadevalor) {
        this.menoridadevalor = menoridadevalor;
    }

    public String getPagardeposito() {
        return pagardeposito;
    }

    public void setPagardeposito(String pagardeposito) {
        this.pagardeposito = pagardeposito;
    }

    public Date getDatasumplementoinicial() {
        return datasumplementoinicial;
    }

    public void setDatasumplementoinicial(Date datasumplementoinicial) {
        this.datasumplementoinicial = datasumplementoinicial;
    }

    public Date getDatasuplementofinal() {
        return datasuplementofinal;
    }

    public void setDatasuplementofinal(Date datasuplementofinal) {
        this.datasuplementofinal = datasuplementofinal;
    }

    public Float getValorsuplemento() {
        return valorsuplemento;
    }

    public void setValorsuplemento(Float valorsuplemento) {
        this.valorsuplemento = valorsuplemento;
    }

    public Float getValortm() {
        return valortm;
    }

    public void setValortm(Float valortm) {
        this.valortm = valortm;
    }

    public Float getValorfranquia() {
        return valorfranquia;
    }

    public void setValorfranquia(Float valorfranquia) {
        this.valorfranquia = valorfranquia;
    }

    public Float getValorparceiro() {
        return valorparceiro;
    }

    public void setValorparceiro(Float valorparceiro) {
        this.valorparceiro = valorparceiro;
    }

    public Float getTaxamatricula() {
        return taxamatricula;
    }

    public void setTaxamatricula(Float taxamatricula) {
        this.taxamatricula = taxamatricula;
    }

    public String getTiporefeicao() {
        return tiporefeicao;
    }

    public void setTiporefeicao(String tiporefeicao) {
        this.tiporefeicao = tiporefeicao;
    }

    public List<Acomodacao> getAcomodacaoList() {
        return acomodacaoList;
    }

    public void setAcomodacaoList(List<Acomodacao> acomodacaoList) {
        this.acomodacaoList = acomodacaoList;
    }

    public Moedas getMoedas() {
        return moedas;
    }

    public void setMoedas(Moedas moedas) {
        this.moedas = moedas;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public Tipoacomodacao getTipoacomodacao() {
        return tipoacomodacao;
    }

    public void setTipoacomodacao(Tipoacomodacao tipoacomodacao) {
        this.tipoacomodacao = tipoacomodacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvaloresacomodacao != null ? idvaloresacomodacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Valoresacomodacao)) {
            return false;
        }
        Valoresacomodacao other = (Valoresacomodacao) object;
        if ((this.idvaloresacomodacao == null && other.idvaloresacomodacao != null) || (this.idvaloresacomodacao != null && !this.idvaloresacomodacao.equals(other.idvaloresacomodacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Valoresacomodacao[ idvaloresacomodacao=" + idvaloresacomodacao + " ]";
    }
    
}
