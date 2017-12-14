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
 * @author Kamila
 */
@Entity
@Table(name = "cursos")
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcursos")
	private Integer idcursos;
	@Size(max = 50)
	@Column(name = "indiomaEstudar")
	private String indiomaEstudar;
	@Size(max = 50)
	@Column(name = "nivelIdiomaEstudar")
	private String nivelIdiomaEstudar;
	@Size(max = 50)
	@Column(name = "nivelidiomaoutra")
	private String nivelidiomaoutra;
	@Size(max = 100)
	@Column(name = "nomeCurso")
	private String nomeCurso;
	@Size(max = 100)
	@Column(name = "escola")
	private String escola;
	@Size(max = 50)
	@Column(name = "cidade")
	private String cidade;
	@Size(max = 50)
	@Column(name = "pais")
	private String pais;
	@Size(max = 30)
	@Column(name = "tipoDuracao")
	private String tipoDuracao;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "aulassemana")
	private Double aulassemana;
	@Column(name = "numeroSenamas")
	private Integer numeroSenamas;
	@Column(name = "dataInicio")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	@Column(name = "dataTermino")
	@Temporal(TemporalType.DATE)
	private Date dataTermino;
	@Size(max = 50)
	@Column(name = "tipoAcomodacao")
	private String tipoAcomodacao;
	@Size(max = 50)
	@Column(name = "tipoacomodacaooutra")
	private String tipoacomodacaooutra;
	@Column(name = "numeroSemanasAcamodacao")
	private Integer numeroSemanasAcamodacao;
	@Size(max = 50)
	@Column(name = "tipoQuarto")
	private String tipoQuarto;
	@Size(max = 50)
	@Column(name = "tipoquartooutra")
	private String tipoquartooutra;
	@Size(max = 50)
	@Column(name = "refeicoes")
	private String refeicoes;
	@Size(max = 50)
	@Column(name = "refeicoesoutra")
	private String refeicoesoutra;
	@Size(max = 100)
	@Column(name = "adicionais")
	private String adicionais;
	@Column(name = "dataChegada")
	@Temporal(TemporalType.DATE)
	private Date dataChegada;
	@Column(name = "dataSaida")
	@Temporal(TemporalType.DATE)
	private Date dataSaida;
	@Size(max = 15)
	@Column(name = "familiacomCrianca")
	private String familiacomCrianca;
	@Size(max = 15)
	@Column(name = "familiacomAnimais")
	private String familiacomAnimais;
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
	@Column(name = "caratoVTM")
	private String caratoVTM;
	@Size(max = 45)
	@Column(name = "numeroCartaoVTM")
	private String numeroCartaoVTM;
	@Size(max = 45)
	@Column(name = "moedaCartaoVTM")
	private String moedaCartaoVTM;
	@Size(max = 3)
	@Column(name = "transferin")
	private String transferin;
	@Size(max = 3)
	@Column(name = "transferouto")
	private String transferouto;
	@Size(max = 50)
	@Column(name = "passagemAerea")
	private String passagemAerea;
	@Size(max = 200)
	@Column(name = "observacaoPassagemAerea")
	private String observacaoPassagemAerea;
	@Size(max = 50)
	@Column(name = "vistoConsular")
	private String vistoConsular;
	@Column(name = "dataEntregaDocumentosVistos")
	@Temporal(TemporalType.DATE)
	private Date dataEntregaDocumentosVistos;
	@Size(max = 200)
	@Column(name = "observacaoVisto")
	private String observacaoVisto;
	@Size(max = 100)
	@Column(name = "nomeContatoEmergencia")
	private String nomeContatoEmergencia;
	@Size(max = 20)
	@Column(name = "foneContatoEmergencia")
	private String foneContatoEmergencia;
	@Size(max = 100)
	@Column(name = "emalContatoEmergencia")
	private String emalContatoEmergencia;
	@Size(max = 50)
	@Column(name = "relacaoContatoEmergencia")
	private String relacaoContatoEmergencia;
	@Column(name = "dataInscricao")
	@Temporal(TemporalType.DATE)
	private Date dataInscricao;
	@Size(max = 50)
	@Column(name = "idioma")
	private String idioma;
	@Size(max = 50)
	@Column(name = "nivelIdioma")
	private String nivelIdioma;
	@Size(max = 50)
	@Column(name = "controle")
	private String controle;
	@Size(max = 1)
	@Column(name = "habilitarSegundoCurso")
	private String habilitarSegundoCurso;
	@Size(max = 100)
	@Column(name = "sCurso")
	private String sCurso;
	@Column(name = "sDataInicio")
	@Temporal(TemporalType.DATE)
	private Date sDataInicio;
	@Column(name = "sDataTermino")
	@Temporal(TemporalType.DATE)
	private Date sDataTermino;
	@Column(name = "sNumeroSemanas")
	private Integer sNumeroSemanas;
	@Size(max = 50)
	@Column(name = "sTipoCargaHoraria")
	private String sTipoCargaHoraria;
	@Column(name = "sCargaHoraria")
	private Double sCargaHoraria;
	@Size(max = 100)
	@Column(name = "seguradoraGovernamental")
	private String seguradoraGovernamental;
	@Column(name = "numeroMeses")
	private int numeroMeses;
	@Size(max = 1)
	@Column(name = "possuiSeguroGovernamental")
	private String possuiSeguroGovernamental;
	@Column(name = "valorSeguroGovernamental")
	private Float valorSeguroGovernamental;
	@Size(max = 3)
	@Column(name = "banheiroprivativo")
	private String banheiroprivativo;
	@JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	@ManyToOne(optional = false)
	private Vendas vendas;
	@Column(name = "dadospais")
	private boolean dadospais;
	@Column(name = "application")
	private boolean application;
	@Transient
	private boolean selecionado;
	@Transient
	private boolean habilitarImagemGerencial;
	@Transient
	private boolean habilitarImagemFranquia;
	@Transient
	private String imagem;
	@Size(max = 45)
	@Column(name = "turno1")
	private String turno1;
	@Size(max = 45)
	@Column(name = "turno2")
	private String turno2;
	@Size(max = 20)
	@Column(name = "codigo")
	private String codigo;
	@Column(name = "tipoimportacaoorcamento")
	private String tipoimportacaoorcamento;
	@Transient
	private String tituloFicha;
	@Transient
	private int idade;

	public Curso() {
		setFumante("Não");
		setFamiliacomAnimais("Não");
		setFamiliacomCrianca("Não");
		setVegetariano("Não");
		setTransferin("Não");
		setTransferouto("Não");
		setTipoimportacaoorcamento("N");
	}

	public Curso(Integer idcursos) {
		this.idcursos = idcursos;
	}

	public Integer getIdcursos() {
		return idcursos;
	}

	public void setIdcursos(Integer idcursos) {
		this.idcursos = idcursos;
	}

	public String getIndiomaEstudar() {
		return indiomaEstudar;
	}

	public void setIndiomaEstudar(String indiomaEstudar) {
		this.indiomaEstudar = indiomaEstudar;
	}

	public String getNivelIdiomaEstudar() {
		return nivelIdiomaEstudar;
	}

	public void setNivelIdiomaEstudar(String nivelIdiomaEstudar) {
		this.nivelIdiomaEstudar = nivelIdiomaEstudar;
	}

	public String getNomeCurso() {
		return nomeCurso;
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

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTipoDuracao() {
		return tipoDuracao;
	}

	public void setTipoDuracao(String tipoDuracao) {
		this.tipoDuracao = tipoDuracao;
	}

	public Double getAulassemana() {
		return aulassemana;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public void setAulassemana(Double aulassemana) {
		this.aulassemana = aulassemana;
	}

	public Integer getNumeroSenamas() {
		return numeroSenamas;
	}

	public void setNumeroSenamas(Integer numeroSenamas) {
		this.numeroSenamas = numeroSenamas;
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

	public Integer getNumeroSemanasAcamodacao() {
		return numeroSemanasAcamodacao;
	}

	public void setNumeroSemanasAcamodacao(Integer numeroSemanasAcamodacao) {
		this.numeroSemanasAcamodacao = numeroSemanasAcamodacao;
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

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getFamiliacomCrianca() {
		return familiacomCrianca;
	}

	public void setFamiliacomCrianca(String familiacomCrianca) {
		this.familiacomCrianca = familiacomCrianca;
	}

	public String getFamiliacomAnimais() {
		return familiacomAnimais;
	}

	public void setFamiliacomAnimais(String familiacomAnimais) {
		this.familiacomAnimais = familiacomAnimais;
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

	public String getCaratoVTM() {
		return caratoVTM;
	}

	public void setCaratoVTM(String caratoVTM) {
		this.caratoVTM = caratoVTM;
	}

	public String getNumeroCartaoVTM() {
		return numeroCartaoVTM;
	}

	public void setNumeroCartaoVTM(String numeroCartaoVTM) {
		this.numeroCartaoVTM = numeroCartaoVTM;
	}

	public String getMoedaCartaoVTM() {
		return moedaCartaoVTM;
	}

	public void setMoedaCartaoVTM(String moedaCartaoVTM) {
		this.moedaCartaoVTM = moedaCartaoVTM;
	}

	public String getTransferin() {
		return transferin;
	}

	public void setTransferin(String transferin) {
		this.transferin = transferin;
	}

	public String getTransferouto() {
		return transferouto;
	}

	public void setTransferouto(String transferouto) {
		this.transferouto = transferouto;
	}

	public String getPassagemAerea() {
		return passagemAerea;
	}

	public void setPassagemAerea(String passagemAerea) {
		this.passagemAerea = passagemAerea;
	}

	public String getObservacaoPassagemAerea() {
		return observacaoPassagemAerea;
	}

	public String getsCurso() {
		return sCurso;
	}

	public void setsCurso(String sCurso) {
		this.sCurso = sCurso;
	}

	public Date getsDataInicio() {
		return sDataInicio;
	}

	public void setsDataInicio(Date sDataInicio) {
		this.sDataInicio = sDataInicio;
	}

	public Date getsDataTermino() {
		return sDataTermino;
	}

	public void setsDataTermino(Date sDataTermino) {
		this.sDataTermino = sDataTermino;
	}

	public Integer getsNumeroSemanas() {
		return sNumeroSemanas;
	}

	public void setsNumeroSemanas(Integer sNumeroSemanas) {
		this.sNumeroSemanas = sNumeroSemanas;
	}

	public String getsTipoCargaHoraria() {
		return sTipoCargaHoraria;
	}

	public void setsTipoCargaHoraria(String sTipoCargaHoraria) {
		this.sTipoCargaHoraria = sTipoCargaHoraria;
	}

	public Double getsCargaHoraria() {
		return sCargaHoraria;
	}

	public void setsCargaHoraria(Double sCargaHoraria) {
		this.sCargaHoraria = sCargaHoraria;
	}

	public boolean isDadospais() {
		return dadospais;
	}

	public void setDadospais(boolean dadospais) {
		this.dadospais = dadospais;
	}

	public void setObservacaoPassagemAerea(String observacaoPassagemAerea) {
		this.observacaoPassagemAerea = observacaoPassagemAerea;
	}

	public String getVistoConsular() {
		return vistoConsular;
	}

	public void setVistoConsular(String vistoConsular) {
		this.vistoConsular = vistoConsular;
	}

	public Date getDataEntregaDocumentosVistos() {
		return dataEntregaDocumentosVistos;
	}

	public void setDataEntregaDocumentosVistos(Date dataEntregaDocumentosVistos) {
		this.dataEntregaDocumentosVistos = dataEntregaDocumentosVistos;
	}

	public String getObservacaoVisto() {
		return observacaoVisto;
	}

	public void setObservacaoVisto(String observacaoVisto) {
		this.observacaoVisto = observacaoVisto;
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

	public String getEmalContatoEmergencia() {
		return emalContatoEmergencia;
	}

	public void setEmalContatoEmergencia(String emalContatoEmergencia) {
		this.emalContatoEmergencia = emalContatoEmergencia;
	}

	public String getRelacaoContatoEmergencia() {
		return relacaoContatoEmergencia;
	}

	public void setRelacaoContatoEmergencia(String relacaoContatoEmergencia) {
		this.relacaoContatoEmergencia = relacaoContatoEmergencia;
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

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public String getHabilitarSegundoCurso() {
		return habilitarSegundoCurso;
	}

	public void setHabilitarSegundoCurso(String habilitarSegundoCurso) {
		this.habilitarSegundoCurso = habilitarSegundoCurso;
	}

	public String getSCurso() {
		return sCurso;
	}

	public void setSCurso(String sCurso) {
		this.sCurso = sCurso;
	}

	public Date getSDataInicio() {
		return sDataInicio;
	}

	public void setSDataInicio(Date sDataInicio) {
		this.sDataInicio = sDataInicio;
	}

	public Date getSDataTermino() {
		return sDataTermino;
	}

	public void setSDataTermino(Date sDataTermino) {
		this.sDataTermino = sDataTermino;
	}

	public Integer getSNumeroSemanas() {
		return sNumeroSemanas;
	}

	public void setSNumeroSemanas(Integer sNumeroSemanas) {
		this.sNumeroSemanas = sNumeroSemanas;
	}

	public String getSTipoCargaHoraria() {
		return sTipoCargaHoraria;
	}

	public void setSTipoCargaHoraria(String sTipoCargaHoraria) {
		this.sTipoCargaHoraria = sTipoCargaHoraria;
	}

	public Double getSCargaHoraria() {
		return sCargaHoraria;
	}

	public void setSCargaHoraria(Double sCargaHoraria) {
		this.sCargaHoraria = sCargaHoraria;
	}

	public String getSeguradoraGovernamental() {
		return seguradoraGovernamental;
	}

	public void setSeguradoraGovernamental(String seguradoraGovernamental) {
		this.seguradoraGovernamental = seguradoraGovernamental;
	}

	public int getNumeroMeses() {
		return numeroMeses;
	}

	public void setNumeroMeses(int numeroMeses) {
		this.numeroMeses = numeroMeses;
	}

	public String getPossuiSeguroGovernamental() {
		return possuiSeguroGovernamental;
	}

	public void setPossuiSeguroGovernamental(String possuiSeguroGovernamental) {
		this.possuiSeguroGovernamental = possuiSeguroGovernamental;
	}

	public Float getValorSeguroGovernamental() {
		return valorSeguroGovernamental;
	}

	public void setValorSeguroGovernamental(Float valorSeguroGovernamental) {
		this.valorSeguroGovernamental = valorSeguroGovernamental;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getBanheiroprivativo() {
		return banheiroprivativo;
	}

	public void setBanheiroprivativo(String banheiroprivativo) {
		this.banheiroprivativo = banheiroprivativo;
	}

	public String getNivelidiomaoutra() {
		return nivelidiomaoutra;
	}

	public void setNivelidiomaoutra(String nivelidiomaoutra) {
		this.nivelidiomaoutra = nivelidiomaoutra;
	}

	public String getTipoacomodacaooutra() {
		return tipoacomodacaooutra;
	}

	public void setTipoacomodacaooutra(String tipoacomodacaooutra) {
		this.tipoacomodacaooutra = tipoacomodacaooutra;
	}

	public String getTipoquartooutra() {
		return tipoquartooutra;
	}

	public void setTipoquartooutra(String tipoquartooutra) {
		this.tipoquartooutra = tipoquartooutra;
	}

	public String getRefeicoesoutra() {
		return refeicoesoutra;
	}

	public void setRefeicoesoutra(String refeicoesoutra) {
		this.refeicoesoutra = refeicoesoutra;
	}

	public boolean isApplication() {
		return application;
	}

	public void setApplication(boolean application) {
		this.application = application;
	}

	public String getTurno1() {
		return turno1;
	}

	public void setTurno1(String turno1) {
		this.turno1 = turno1;
	}

	public String getTurno2() {
		return turno2;
	}

	public void setTurno2(String turno2) {
		this.turno2 = turno2;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoimportacaoorcamento() {
		return tipoimportacaoorcamento;
	}

	public void setTipoimportacaoorcamento(String tipoimportacaoorcamento) {
		this.tipoimportacaoorcamento = tipoimportacaoorcamento;
	}
	
	

	public String getTituloFicha() {
		return tituloFicha;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public void setTituloFicha(String tituloFicha) {
		this.tituloFicha = tituloFicha;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcursos != null ? idcursos.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Curso)) {
			return false;
		}
		Curso other = (Curso) object;
		if ((this.idcursos == null && other.idcursos != null)
				|| (this.idcursos != null && !this.idcursos.equals(other.idcursos))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Curso[ idcursos=" + idcursos + " ]";
	}

}
