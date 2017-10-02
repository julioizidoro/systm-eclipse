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
import javax.persistence.ManyToOne;
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
@Table(name = "voluntariado")
public class Voluntariado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvoluntariado")
    private Integer idvoluntariado;
    @Size(max = 50)
    @Column(name = "idiomaEstudar")
    private String idiomaEstudar;
    @Size(max = 50)
    @Column(name = "nivelIdiomaEstudar")
    private String nivelIdiomaEstudar;
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
    @Size(max = 100)
    @Column(name = "instituicaoEstuda")
    private String instituicaoEstuda;
    @Size(max = 50)
    @Column(name = "cursoEstuda")
    private String cursoEstuda;
    @Size(max = 50)
    @Column(name = "duracaoCursoEstuda")
    private String duracaoCursoEstuda;
    @Size(max = 50)
    @Column(name = "periodoCursoEstuda")
    private String periodoCursoEstuda;
    @Column(name = "dataMatriculaCursoEstuda")
    @Temporal(TemporalType.DATE)
    private Date dataMatriculaCursoEstuda;
    @Column(name = "dataEstimadaTerminoCursoEstuda")
    @Temporal(TemporalType.DATE)
    private Date dataEstimadaTerminoCursoEstuda;
    @Size(max = 50)
    @Column(name = "profissao")
    private String profissao;
    @Size(max = 50)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 200)
    @Column(name = "descricaoCargo")
    private String descricaoCargo;
    @Size(max = 100)
    @Column(name = "outrasHabilidade")
    private String outrasHabilidade;
    @Size(max = 100)
    @Column(name = "curso")
    private String curso;
    @Column(name = "aulasporSemana")
    private Integer aulasporSemana;
    @Column(name = "numeroSemanas")
    private Integer numeroSemanas;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "dataTermino")
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Size(max = 50)
    @Column(name = "tipoAcomodacao")
    private String tipoAcomodacao;
    @Column(name = "numeroSemanasAcomodacao")
    private Integer numeroSemanasAcomodacao;
    @Size(max = 50)
    @Column(name = "tipoQuarto")
    private String tipoQuarto;
    @Size(max = 50)
    @Column(name = "refeicoes")
    private String refeicoes;
    @Size(max = 100)
    @Column(name = "adicionais")
    private String adicionais;
    @Column(name = "dataChegadaAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataChegadaAcomodacao;
    @Column(name = "dataPartidaAcomodacao")
    @Temporal(TemporalType.DATE)
    private Date dataPartidaAcomodacao;
    @Size(max = 15)
    @Column(name = "familiaCrianca")
    private String familiaCrianca;
    @Size(max = 15)
    @Column(name = "familiaAnimais")
    private String familiaAnimais;
    @Size(max = 3)
    @Column(name = "fumante")
    private String fumante;
    @Size(max = 3)
    @Column(name = "vegetariano")
    private String vegetariano;
    @Size(max = 200)
    @Column(name = "hobbies")
    private String hobbies;
    @Size(max = 3)
    @Column(name = "possuiAlergia")
    private String possuiAlergia;
    @Size(max = 200)
    @Column(name = "quaisAlergias")
    private String quaisAlergias;
    @Size(max = 100)
    @Column(name = "solicitacoesEspeciais")
    private String solicitacoesEspeciais;
    @Size(max = 3)
    @Column(name = "transferin")
    private String transferin;
    @Size(max = 3)
    @Column(name = "transferout")
    private String transferout;
    @Column(name = "dataChegadaTransfer")
    @Temporal(TemporalType.DATE)
    private Date dataChegadaTransfer;
    @Size(max = 45)
    @Column(name = "voo")
    private String voo;
    @Size(max = 50)
    @Column(name = "ciaerea")
    private String ciaerea;
    @Size(max = 5)
    @Column(name = "horario")
    private String horario;
    @Size(max = 200)
    @Column(name = "projetoVoluntariado")
    private String projetoVoluntariado;
    @Column(name = "dataInicioVoluntariado")
    @Temporal(TemporalType.DATE)
    private Date dataInicioVoluntariado;
    @Column(name = "dataTerminoVoluntariado")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoVoluntariado;
    @Size(max = 200)
    @Column(name = "experienciaVoluntariado")
    private String experienciaVoluntariado;
    @Size(max = 200)
    @Column(name = "motivoVoluntariado")
    private String motivoVoluntariado;
    @Size(max = 3)
    @Column(name = "deficienciaFisica")
    private String deficienciaFisica;
    @Size(max = 3)
    @Column(name = "possuiProblemaSaude")
    private String possuiProblemaSaude;
    @Size(max = 100)
    @Column(name = "descricaoProblemaSaude")
    private String descricaoProblemaSaude;
    @Size(max = 3)
    @Column(name = "tratamentoMedico")
    private String tratamentoMedico;
    @Size(max = 100)
    @Column(name = "descricaoMedico")
    private String descricaoMedico;
    @Size(max = 3)
    @Column(name = "tratamentoDrogas")
    private String tratamentoDrogas;
    @Size(max = 100)
    @Column(name = "descricaoDrogas")
    private String descricaoDrogas;
    @Size(max = 3)
    @Column(name = "fezCirurgia")
    private String fezCirurgia;
    @Size(max = 100)
    @Column(name = "descricaoCirurgia")
    private String descricaoCirurgia;
    @Size(max = 100)
    @Column(name = "dietaEspecifica")
    private String dietaEspecifica;
    @Size(max = 3)
    @Column(name = "cartaoVTM")
    private String cartaoVTM;
    @Size(max = 45)
    @Column(name = "numerocartaoVTM")
    private String numerocartaoVTM;
    @Size(max = 45)
    @Column(name = "meodaCartaoVTM")
    private String meodaCartaoVTM;
    @Size(max = 3)
    @Column(name = "seguroViagem")
    private String seguroViagem;
    @Size(max = 100)
    @Column(name = "seguradora")
    private String seguradora;
    @Size(max = 50)
    @Column(name = "planoSeguro")
    private String planoSeguro;
    @Column(name = "dataInicioSeguro")
    @Temporal(TemporalType.DATE)
    private Date dataInicioSeguro;
    @Column(name = "dataTerminoSeguro")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoSeguro;
    @Column(name = "numeroSemanasSeguro")
    private Integer numeroSemanasSeguro;
    @Size(max = 50)
    @Column(name = "passagemAerea")
    private String passagemAerea;
    @Size(max = 100)
    @Column(name = "observacaoPassagem")
    private String observacaoPassagem;
    @Size(max = 50)
    @Column(name = "vistoConsular")
    private String vistoConsular;
    @Size(max = 100)
    @Column(name = "observacaoVistoConsultar")
    private String observacaoVistoConsultar;
    @Column(name = "dataEntregaDocumentoVisto")
    @Temporal(TemporalType.DATE)
    private Date dataEntregaDocumentoVisto;
    @Size(max = 100)
    @Column(name = "nivelFitness")
    private String nivelFitness;
    @Size(max = 30)
    @Column(name = "controle")
    private String controle;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    @Transient 
    private boolean habilitarImagemGerencial;
    @Transient 
    private boolean habilitarImagemFranquia;
    @Transient 
    private String imagem;
    @Transient
    private String tituloFicha;
    @Column(name = "habilitarCurso")
    private boolean habilitarCurso;
    
    
    

    public Voluntariado() {
    	habilitarCurso = false;
    }

    public Voluntariado(Integer idvoluntariado) {
        this.idvoluntariado = idvoluntariado;
    }

    public Integer getIdvoluntariado() {
        return idvoluntariado;
    }

    public void setIdvoluntariado(Integer idvoluntariado) {
        this.idvoluntariado = idvoluntariado;
    }

    public String getIdiomaEstudar() {
        return idiomaEstudar;
    }

    public void setIdiomaEstudar(String idiomaEstudar) {
        this.idiomaEstudar = idiomaEstudar;
    }

    public String getNivelIdiomaEstudar() {
        return nivelIdiomaEstudar;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public Date getDataEntregaDocumentoVisto() {
        return dataEntregaDocumentoVisto;
    }

    public void setDataEntregaDocumentoVisto(Date dataEntregaDocumentoVisto) {
        this.dataEntregaDocumentoVisto = dataEntregaDocumentoVisto;
    }

    public String getNivelFitness() {
        return nivelFitness;
    }

    public void setNivelFitness(String nivelFitness) {
        this.nivelFitness = nivelFitness;
    }

    public void setNivelIdiomaEstudar(String nivelIdiomaEstudar) {
        this.nivelIdiomaEstudar = nivelIdiomaEstudar;
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

   
    public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public void setRelacaoContatoEmergencia(String relacaoContatoEmergencia) {
        this.relacaoContatoEmergencia = relacaoContatoEmergencia;
    }

    public String getInstituicaoEstuda() {
        return instituicaoEstuda;
    }

    public void setInstituicaoEstuda(String instituicaoEstuda) {
        this.instituicaoEstuda = instituicaoEstuda;
    }

    public String getCursoEstuda() {
        return cursoEstuda;
    }

    public void setCursoEstuda(String cursoEstuda) {
        this.cursoEstuda = cursoEstuda;
    }

    public String getDuracaoCursoEstuda() {
        return duracaoCursoEstuda;
    }

    public void setDuracaoCursoEstuda(String duracaoCursoEstuda) {
        this.duracaoCursoEstuda = duracaoCursoEstuda;
    }

    public String getPeriodoCursoEstuda() {
        return periodoCursoEstuda;
    }

    public void setPeriodoCursoEstuda(String periodoCursoEstuda) {
        this.periodoCursoEstuda = periodoCursoEstuda;
    }

    public Date getDataMatriculaCursoEstuda() {
        return dataMatriculaCursoEstuda;
    }

    public void setDataMatriculaCursoEstuda(Date dataMatriculaCursoEstuda) {
        this.dataMatriculaCursoEstuda = dataMatriculaCursoEstuda;
    }

    public Date getDataEstimadaTerminoCursoEstuda() {
        return dataEstimadaTerminoCursoEstuda;
    }

    public void setDataEstimadaTerminoCursoEstuda(Date dataEstimadaTerminoCursoEstuda) {
        this.dataEstimadaTerminoCursoEstuda = dataEstimadaTerminoCursoEstuda;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescricaoCargo() {
        return descricaoCargo;
    }

    public void setDescricaoCargo(String descricaoCargo) {
        this.descricaoCargo = descricaoCargo;
    }

    public String getOutrasHabilidade() {
        return outrasHabilidade;
    }

    public void setOutrasHabilidade(String outrasHabilidade) {
        this.outrasHabilidade = outrasHabilidade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getAulasporSemana() {
        return aulasporSemana;
    }

    public void setAulasporSemana(Integer aulasporSemana) {
        this.aulasporSemana = aulasporSemana;
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

    public String getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(String refeicoes) {
        this.refeicoes = refeicoes;
    }

    public String getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(String adicionais) {
        this.adicionais = adicionais;
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

    public String getFamiliaCrianca() {
        return familiaCrianca;
    }

    public void setFamiliaCrianca(String familiaCrianca) {
        this.familiaCrianca = familiaCrianca;
    }

    public String getFamiliaAnimais() {
        return familiaAnimais;
    }

    public void setFamiliaAnimais(String familiaAnimais) {
        this.familiaAnimais = familiaAnimais;
    }

    public String getFumante() {
        return fumante;
    }

    public void setFumante(String fumante) {
        this.fumante = fumante;
    }

    public String getVegetariano() {
        return vegetariano;
    }

    public void setVegetariano(String vegetariano) {
        this.vegetariano = vegetariano;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPossuiAlergia() {
        return possuiAlergia;
    }

    public void setPossuiAlergia(String possuiAlergia) {
        this.possuiAlergia = possuiAlergia;
    }

    public String getQuaisAlergias() {
        return quaisAlergias;
    }

    public void setQuaisAlergias(String quaisAlergias) {
        this.quaisAlergias = quaisAlergias;
    }

    public String getSolicitacoesEspeciais() {
        return solicitacoesEspeciais;
    }

    public void setSolicitacoesEspeciais(String solicitacoesEspeciais) {
        this.solicitacoesEspeciais = solicitacoesEspeciais;
    }

    public String getTransferin() {
        return transferin;
    }

    public void setTransferin(String transferin) {
        this.transferin = transferin;
    }

    public String getTransferout() {
        return transferout;
    }

    public void setTransferout(String transferout) {
        this.transferout = transferout;
    }

    public Date getDataChegadaTransfer() {
        return dataChegadaTransfer;
    }

    public void setDataChegadaTransfer(Date dataChegadaTransfer) {
        this.dataChegadaTransfer = dataChegadaTransfer;
    }

    public String getVoo() {
        return voo;
    }

    public void setVoo(String voo) {
        this.voo = voo;
    }

    public String getCiaerea() {
        return ciaerea;
    }

    public void setCiaerea(String ciaerea) {
        this.ciaerea = ciaerea;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getProjetoVoluntariado() {
        return projetoVoluntariado;
    }

    public void setProjetoVoluntariado(String projetoVoluntariado) {
        this.projetoVoluntariado = projetoVoluntariado;
    }

    public Date getDataInicioVoluntariado() {
        return dataInicioVoluntariado;
    }

    public void setDataInicioVoluntariado(Date dataInicioVoluntariado) {
        this.dataInicioVoluntariado = dataInicioVoluntariado;
    }

    public Date getDataTerminoVoluntariado() {
        return dataTerminoVoluntariado;
    }

    public void setDataTerminoVoluntariado(Date dataTerminoVoluntariado) {
        this.dataTerminoVoluntariado = dataTerminoVoluntariado;
    }

    public String getExperienciaVoluntariado() {
        return experienciaVoluntariado;
    }

    public void setExperienciaVoluntariado(String experienciaVoluntariado) {
        this.experienciaVoluntariado = experienciaVoluntariado;
    }

    public String getMotivoVoluntariado() {
        return motivoVoluntariado;
    }

    public void setMotivoVoluntariado(String motivoVoluntariado) {
        this.motivoVoluntariado = motivoVoluntariado;
    }

    public String getDeficienciaFisica() {
        return deficienciaFisica;
    }

    public void setDeficienciaFisica(String deficienciaFisica) {
        this.deficienciaFisica = deficienciaFisica;
    }

    public String getPossuiProblemaSaude() {
        return possuiProblemaSaude;
    }

    public void setPossuiProblemaSaude(String possuiProblemaSaude) {
        this.possuiProblemaSaude = possuiProblemaSaude;
    }

    public String getDescricaoProblemaSaude() {
        return descricaoProblemaSaude;
    }

    public void setDescricaoProblemaSaude(String descricaoProblemaSaude) {
        this.descricaoProblemaSaude = descricaoProblemaSaude;
    }

    public String getTratamentoMedico() {
        return tratamentoMedico;
    }

    public void setTratamentoMedico(String tratamentoMedico) {
        this.tratamentoMedico = tratamentoMedico;
    }

    public String getDescricaoMedico() {
        return descricaoMedico;
    }

    public void setDescricaoMedico(String descricaoMedico) {
        this.descricaoMedico = descricaoMedico;
    }

    public String getTratamentoDrogas() {
        return tratamentoDrogas;
    }

    public void setTratamentoDrogas(String tratamentoDrogas) {
        this.tratamentoDrogas = tratamentoDrogas;
    }

    public String getDescricaoDrogas() {
        return descricaoDrogas;
    }

    public void setDescricaoDrogas(String descricaoDrogas) {
        this.descricaoDrogas = descricaoDrogas;
    }

    public String getFezCirurgia() {
        return fezCirurgia;
    }

    public void setFezCirurgia(String fezCirurgia) {
        this.fezCirurgia = fezCirurgia;
    }

    public String getDescricaoCirurgia() {
        return descricaoCirurgia;
    }

    public void setDescricaoCirurgia(String descricaoCirurgia) {
        this.descricaoCirurgia = descricaoCirurgia;
    }

    public String getDietaEspecifica() {
        return dietaEspecifica;
    }

    public void setDietaEspecifica(String dietaEspecifica) {
        this.dietaEspecifica = dietaEspecifica;
    }

    public String getCartaoVTM() {
        return cartaoVTM;
    }

    public void setCartaoVTM(String cartaoVTM) {
        this.cartaoVTM = cartaoVTM;
    }

    public String getNumerocartaoVTM() {
        return numerocartaoVTM;
    }

    public void setNumerocartaoVTM(String numerocartaoVTM) {
        this.numerocartaoVTM = numerocartaoVTM;
    }

    public String getMeodaCartaoVTM() {
        return meodaCartaoVTM;
    }

    public void setMeodaCartaoVTM(String meodaCartaoVTM) {
        this.meodaCartaoVTM = meodaCartaoVTM;
    }

    public String getSeguroViagem() {
        return seguroViagem;
    }

    public void setSeguroViagem(String seguroViagem) {
        this.seguroViagem = seguroViagem;
    }

    public String getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public String getPlanoSeguro() {
        return planoSeguro;
    }

    public void setPlanoSeguro(String planoSeguro) {
        this.planoSeguro = planoSeguro;
    }

    public Date getDataInicioSeguro() {
        return dataInicioSeguro;
    }

    public void setDataInicioSeguro(Date dataInicioSeguro) {
        this.dataInicioSeguro = dataInicioSeguro;
    }

    public Date getDataTerminoSeguro() {
        return dataTerminoSeguro;
    }

    public void setDataTerminoSeguro(Date dataTerminoSeguro) {
        this.dataTerminoSeguro = dataTerminoSeguro;
    }

    public Integer getNumeroSemanasSeguro() {
        return numeroSemanasSeguro;
    }

    public void setNumeroSemanasSeguro(Integer numeroSemanasSeguro) {
        this.numeroSemanasSeguro = numeroSemanasSeguro;
    }

    public String getPassagemAerea() {
        return passagemAerea;
    }

    public void setPassagemAerea(String passagemAerea) {
        this.passagemAerea = passagemAerea;
    }

    public String getObservacaoPassagem() {
        return observacaoPassagem;
    }

    public void setObservacaoPassagem(String observacaoPassagem) {
        this.observacaoPassagem = observacaoPassagem;
    }

    public String getVistoConsular() {
        return vistoConsular;
    }

    public void setVistoConsular(String vistoConsular) {
        this.vistoConsular = vistoConsular;
    }

    public String getObservacaoVistoConsultar() {
        return observacaoVistoConsultar;
    }

    public void setObservacaoVistoConsultar(String observacaoVistoConsultar) {
        this.observacaoVistoConsultar = observacaoVistoConsultar;
    }

    public boolean isHabilitarImagemGerencial() {
		return habilitarImagemGerencial;
	}

	public void setHabilitarImagemGerencial(boolean habilitarImagemGerencial) {
		this.habilitarImagemGerencial = habilitarImagemGerencial;
	}

	public boolean isHabilitarImagemFranquia() {
		return habilitarImagemFranquia;
	}

	public void setHabilitarImagemFranquia(boolean habilitarImagemFranquia) {
		this.habilitarImagemFranquia = habilitarImagemFranquia;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTituloFicha() {
		return tituloFicha;
	}

	public void setTituloFicha(String tituloFicha) {
		this.tituloFicha = tituloFicha;
	}

	public boolean isHabilitarCurso() {
		return habilitarCurso;
	}

	public void setHabilitarCurso(boolean habilitarCurso) {
		this.habilitarCurso = habilitarCurso;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoluntariado != null ? idvoluntariado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Voluntariado)) {
            return false;
        }
        Voluntariado other = (Voluntariado) object;
        if ((this.idvoluntariado == null && other.idvoluntariado != null) || (this.idvoluntariado != null && !this.idvoluntariado.equals(other.idvoluntariado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Voluntariado[ idvoluntariado=" + idvoluntariado + " ]";
    }
    
}
