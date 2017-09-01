/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Kamila
 */
@Entity
@Table(name = "ladies")
@NamedQueries({
    @NamedQuery(name = "Ladies.findAll", query = "SELECT l FROM Ladies l")})
public class Ladies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idladies")
    private Integer idladies;
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Size(max = 30)
    @Column(name = "idioma")
    private String idioma;
    @Size(max = 30)
    @Column(name = "nivelIdioma")
    private String nivelIdioma;
    @Size(max = 100)
    @Column(name = "curso")
    private String curso;
    @Column(name = "aulasSemana")
    private Integer aulasSemana;
    @Column(name = "numeroSemanas")
    private Integer numeroSemanas;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "dataTermino")
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Size(max = 100)
    @Column(name = "tipoAcomodacao")
    private String tipoAcomodacao;
    @Column(name = "dataChegadaAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataChegadaAcomodacao;
    @Column(name = "dataPartidaAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataPartidaAcomodacao;
    @Column(name = "numeroSemanasAcomodacao")
    private Integer numeroSemanasAcomodacao;
    @Size(max = 50)
    @Column(name = "tipoQuarto")
    private String tipoQuarto;
    @Size(max = 50)
    @Column(name = "tipoRefeicao")
    private String tipoRefeicao;
    @Size(max = 100)
    @Column(name = "adicionaisAcomodacao")
    private String adicionaisAcomodacao;
    @Size(max = 3)
    @Column(name = "cartaoVTM")
    private String cartaoVTM;
    @Size(max = 50)
    @Column(name = "numeroCartao")
    private String numeroCartao;
    @Size(max = 30)
    @Column(name = "moedaCartao")
    private String moedaCartao;
    @Size(max = 50)
    @Column(name = "solicitacaoVisto")
    private String solicitacaoVisto;
    @Size(max = 100)
    @Column(name = "nomeContatoEmergencia")
    private String nomeContatoEmergencia;
    @Size(max = 14)
    @Column(name = "foneContatoEmergencia")
    private String foneContatoEmergencia;
    @Size(max = 100)
    @Column(name = "emailContatoEmergencia")
    private String emailContatoEmergencia;
    @Size(max = 50)
    @Column(name = "relacaoContatoEmergencia")
    private String relacaoContatoEmergencia;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "obstm")
    private String obstm;
    @Size(max = 50)
    @Column(name = "controle")
    private String controle;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;

    public Ladies() {
    }

    public Ladies(Integer idladies) {
        this.idladies = idladies;
    }

    public Integer getIdladies() {
        return idladies;
    }

    public void setIdladies(Integer idladies) {
        this.idladies = idladies;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
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

    public Integer getAulasSemana() {
        return aulasSemana;
    }

    public void setAulasSemana(Integer aulasSemana) {
        this.aulasSemana = aulasSemana;
    }

    public Integer getNumeroSemanas() {
        return numeroSemanas;
    }

    public void setNumeroSemanas(Integer numeroSemanas) {
        this.numeroSemanas = numeroSemanas;
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

    public String getTipoAcomodacao() {
        return tipoAcomodacao;
    }

    public void setTipoAcomodacao(String tipoAcomodacao) {
        this.tipoAcomodacao = tipoAcomodacao;
    }

    public Date getDataChegadaAcomodacao() {
        return dataChegadaAcomodacao;
    }

    public void setDataChegadaAcomodacao(Date dataChegadaAcomodacao) {
        this.dataChegadaAcomodacao = dataChegadaAcomodacao;
    }

    public Date getDataPartidaAcomodacao() {
        return dataPartidaAcomodacao;
    }

    public void setDataPartidaAcomodacao(Date dataPartidaAcomodacao) {
        this.dataPartidaAcomodacao = dataPartidaAcomodacao;
    }

    public Integer getNumeroSemanasAcomodacao() {
        return numeroSemanasAcomodacao;
    }

    public void setNumeroSemanasAcomodacao(Integer numeroSemanasAcomodacao) {
        this.numeroSemanasAcomodacao = numeroSemanasAcomodacao;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(String tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public String getAdicionaisAcomodacao() {
        return adicionaisAcomodacao;
    }

    public void setAdicionaisAcomodacao(String adicionaisAcomodacao) {
        this.adicionaisAcomodacao = adicionaisAcomodacao;
    }

    public String getCartaoVTM() {
        return cartaoVTM;
    }

    public void setCartaoVTM(String cartaoVTM) {
        this.cartaoVTM = cartaoVTM;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getMoedaCartao() {
        return moedaCartao;
    }

    public void setMoedaCartao(String moedaCartao) {
        this.moedaCartao = moedaCartao;
    }

    public String getSolicitacaoVisto() {
        return solicitacaoVisto;
    }

    public void setSolicitacaoVisto(String solicitacaoVisto) {
        this.solicitacaoVisto = solicitacaoVisto;
    }

    public String getNomeContatoEmergencia() {
        return nomeContatoEmergencia;
    }

    public void setNomeContatoEmergencia(String nomeContatoEmergencia) {
        this.nomeContatoEmergencia = nomeContatoEmergencia;
    }

    public String getFoneContatoEmergencia() {
        return foneContatoEmergencia;
    }

    public void setFoneContatoEmergencia(String foneContatoEmergencia) {
        this.foneContatoEmergencia = foneContatoEmergencia;
    }

    public String getEmailContatoEmergencia() {
        return emailContatoEmergencia;
    }

    public void setEmailContatoEmergencia(String emailContatoEmergencia) {
        this.emailContatoEmergencia = emailContatoEmergencia;
    }

    public String getRelacaoContatoEmergencia() {
        return relacaoContatoEmergencia;
    }

    public void setRelacaoContatoEmergencia(String relacaoContatoEmergencia) {
        this.relacaoContatoEmergencia = relacaoContatoEmergencia;
    }

    public String getObstm() {
        return obstm;
    }

    public void setObstm(String obstm) {
        this.obstm = obstm;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idladies != null ? idladies.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ladies)) {
            return false;
        }
        Ladies other = (Ladies) object;
        if ((this.idladies == null && other.idladies != null) || (this.idladies != null && !this.idladies.equals(other.idladies))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Ladies[ idladies=" + idladies + " ]";
    }
    
}
