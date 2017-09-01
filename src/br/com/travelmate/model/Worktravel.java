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
@Table(name = "worktravel")
public class Worktravel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idworkTravel")
    private Integer idworkTravel;
    @Size(max = 50)
    @Column(name = "tipo")
    private String tipo;
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
    @Column(name = "intituicaoEstuda")
    private String intituicaoEstuda;
    @Size(max = 50)
    @Column(name = "cursoEstuda")
    private String cursoEstuda;
    @Size(max = 50)
    @Column(name = "duracaoCurso")
    private String duracaoCurso;
    @Size(max = 50)
    @Column(name = "periodoCurso")
    private String periodoCurso;
    @Size(max = 20)
    @Column(name = "peirodoEstuda")
    private String peirodoEstuda;
    @Column(name = "dataMatricula")
    @Temporal(TemporalType.DATE)
    private Date dataMatricula;
    @Column(name = "dataEstimadaTermino")
    @Temporal(TemporalType.DATE)
    private Date dataEstimadaTermino;
    @Size(max = 45)
    @Column(name = "idioma01")
    private String idioma01;
    @Size(max = 45)
    @Column(name = "nivelidioma01")
    private String nivelidioma01;
    @Size(max = 45)
    @Column(name = "tempoidioma01")
    private String tempoidioma01;
    @Size(max = 45)
    @Column(name = "idioma02")
    private String idioma02;
    @Size(max = 45)
    @Column(name = "nivelidioma02")
    private String nivelidioma02;
    @Size(max = 45)
    @Column(name = "tempoidioma02")
    private String tempoidioma02;
    @Size(max = 45)
    @Column(name = "idioma03")
    private String idioma03;
    @Size(max = 45)
    @Column(name = "nivelidioma03")
    private String nivelidioma03;
    @Size(max = 45)
    @Column(name = "tempoidioma03")
    private String tempoidioma03;
    @Column(name = "dataInicioPretendida01")
    @Temporal(TemporalType.DATE)
    private Date dataInicioPretendida01;
    @Column(name = "dataInicioPretendida02")
    @Temporal(TemporalType.DATE)
    private Date dataInicioPretendida02;
    @Column(name = "dataTerminoPretendida01")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoPretendida01;
    @Column(name = "dataTerminoPretendida02")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoPretendida02;
    @Column(name = "camareira")
    private boolean camareira;
    @Column(name = "cozinha")
    private boolean cozinha;
    @Column(name = "salvavidas")
    private boolean salvavidas;
    @Column(name = "recepcao")
    private boolean recepcao;
    @Column(name = "garcon")
    private boolean garcon;
    @Column(name = "trabalhosexternos")
    private boolean trabalhosexternos;
    @Column(name = "lavadeira")
    private boolean lavadeira;
    @Column(name = "indiferenteocupacao")
    private boolean indiferenteocupacao;
    @Column(name = "outraocupacao")
    private boolean outraocupacao;
    @Size(max = 100)
    @Column(name = "especificarOutraOcupacao")
    private String especificarOutraOcupacao;
    @Column(name = "programaidependente")
    private boolean programaidependente;
    @Column(name = "parquetematico")
    private boolean parquetematico;
    @Column(name = "hotel")
    private boolean hotel;
    @Column(name = "restaurante")
    private boolean restaurante;
    @Column(name = "lojas")
    private boolean lojas;
    @Column(name = "cassino")
    private boolean cassino;
    @Column(name = "piscina")
    private boolean piscina;
    @Column(name = "estacaoesqui")
    private boolean estacaoesqui;
    @Column(name = "indiferentelocal")
    private boolean indiferentelocal;
    @Column(name = "outrolocal")
    private boolean outrolocal;
    @Size(max = 100)
    @Column(name = "especificaroutraLocal")
    private String especificaroutraLocal;
    @Column(name = "programaodependente01")
    private boolean programaodependente01;
    @Size(max = 50)
    @Column(name = "clima")
    private String clima;
    @Size(max = 200)
    @Column(name = "adicionais")
    private String adicionais;
    @Size(max = 3)
    @Column(name = "sabernadar")
    private String sabernadar;
    @Size(max = 3)
    @Column(name = "treinamentosalvavidas")
    private String treinamentosalvavidas;
    @Size(max = 3)
    @Column(name = "sabeesquiar")
    private String sabeesquiar;
    @Size(max = 3)
    @Column(name = "carteiraHabilitacao")
    private String carteiraHabilitacao;
    @Size(max = 3)
    @Column(name = "dirigirEua")
    private String dirigirEua;
    @Size(max = 45)
    @Column(name = "nomePais01")
    private String nomePais01;
    @Size(max = 45)
    @Column(name = "motivoEstadia01")
    private String motivoEstadia01;
    @Size(max = 45)
    @Column(name = "periodoEstadia01")
    private String periodoEstadia01;
    @Size(max = 45)
    @Column(name = "nomePais02")
    private String nomePais02;
    @Size(max = 45)
    @Column(name = "motivoEstadia02")
    private String motivoEstadia02;
    @Size(max = 45)
    @Column(name = "periodoEstadia02")
    private String periodoEstadia02;
    @Size(max = 45)
    @Column(name = "nomePais03")
    private String nomePais03;
    @Size(max = 45)
    @Column(name = "motivoEstadia03")
    private String motivoEstadia03;
    @Size(max = 45)
    @Column(name = "peirodoEstadia03")
    private String peirodoEstadia03;
    @Size(max = 100)
    @Column(name = "campanheiroViagem01")
    private String campanheiroViagem01;
    @Size(max = 100)
    @Column(name = "companheiroViagem02")
    private String companheiroViagem02;
    @Size(max = 100)
    @Column(name = "companheiroViagem03")
    private String companheiroViagem03;
    @Size(max = 3)
    @Column(name = "trabalhoVistoJ1")
    private String trabalhoVistoJ1;
    @Size(max = 50)
    @Column(name = "quantoVistoj1")
    private String quantoVistoj1;
    @Size(max = 50)
    @Column(name = "numeroSocialSecurity")
    private String numeroSocialSecurity;
    @Size(max = 50)
    @Column(name = "tipoProgramaEua")
    private String tipoProgramaEua;
    @Size(max = 50)
    @Column(name = "agenciaBrasil")
    private String agenciaBrasil;
    @Size(max = 50)
    @Column(name = "posicaoExcercida")
    private String posicaoExcercida;
    @Size(max = 3)
    @Column(name = "opcaoIndependente")
    private String opcaoIndependente;
    @Size(max = 3)
    @Column(name = "possuiOfertaTrabalho")
    private String possuiOfertaTrabalho;
    @Size(max = 100)
    @Column(name = "nomeEmpregador")
    private String nomeEmpregador;
    @Size(max = 50)
    @Column(name = "possicarqueiraExercer")
    private String possicarqueiraExercer;
    @Size(max = 3)
    @Column(name = "possuiDeficienciaFisica")
    private String possuiDeficienciaFisica;
    @Size(max = 3)
    @Column(name = "problemaSaude")
    private String problemaSaude;
    @Size(max = 100)
    @Column(name = "especifiqueProblemaSaude")
    private String especifiqueProblemaSaude;
    @Size(max = 3)
    @Column(name = "tratamentoMedico")
    private String tratamentoMedico;
    @Size(max = 100)
    @Column(name = "especifiqueTratamentoMedico")
    private String especifiqueTratamentoMedico;
    @Size(max = 3)
    @Column(name = "tratamentoUsoDrogas")
    private String tratamentoUsoDrogas;
    @Size(max = 100)
    @Column(name = "especifiqueTratamentoUsoDrogas")
    private String especifiqueTratamentoUsoDrogas;
    @Size(max = 200)
    @Column(name = "listaAlergias")
    private String listaAlergias;
    @Size(max = 3)
    @Column(name = "cartaoVTM")
    private String cartaoVTM;
    @Size(max = 50)
    @Column(name = "numeroCartaoVTM")
    private String numeroCartaoVTM;
    @Size(max = 20)
    @Column(name = "moedaCartaoVTM")
    private String moedaCartaoVTM;
    @Size(max = 3)
    @Column(name = "passagemAerea")
    private String passagemAerea;
    @Size(max = 30)
    @Column(name = "tipoEmissaoPassagem")
    private String tipoEmissaoPassagem;
    @Size(max = 100)
    @Column(name = "observacaoPassagem")
    private String observacaoPassagem;
    @Size(max = 3)
    @Column(name = "tipoVisto")
    private String tipoVisto;
    @Size(max = 30)
    @Column(name = "tipoEmissaoVisto")
    private String tipoEmissaoVisto;
    @Size(max = 100)
    @Column(name = "observacaoVisto")
    private String observacaoVisto;
    @Column(name = "dataEntragaDocumentosVisto")
    @Temporal(TemporalType.DATE)
    private Date dataEntragaDocumentosVisto;
    @Size(max = 3)
    @Column(name = "fuma")
    private String fuma;
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Size(max = 30)
    @Column(name = "controle")
    private String controle;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "obstm")
    private String obstm;
    @Size(max = 50)
    @Column(name = "inicioPrevisto")
    private String inicioPrevisto;
    @Column(name = "skype")
    private String skype;
    @JoinColumn(name = "valoresWork_idvaloresWork", referencedColumnName = "idvaloresWork")
    @ManyToOne(optional = false)
    private Valoreswork valoreswork;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;
    
    @Transient 
    private boolean habilitarImagemGerencial;
    @Transient 
    private boolean habilitarImagemFranquia;
    @Transient 
    private String imagem;
    

    public Worktravel() {
    }

    public Worktravel(Integer idworkTravel) {
        this.idworkTravel = idworkTravel;
    }

    public Integer getIdworkTravel() {
        return idworkTravel;
    }

    public void setIdworkTravel(Integer idworkTravel) {
        this.idworkTravel = idworkTravel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
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

    public String getIntituicaoEstuda() {
        return intituicaoEstuda;
    }

    public void setIntituicaoEstuda(String intituicaoEstuda) {
        this.intituicaoEstuda = intituicaoEstuda;
    }

    public String getCursoEstuda() {
        return cursoEstuda;
    }

    public void setCursoEstuda(String cursoEstuda) {
        this.cursoEstuda = cursoEstuda;
    }

    public String getDuracaoCurso() {
        return duracaoCurso;
    }

    public void setDuracaoCurso(String duracaoCurso) {
        this.duracaoCurso = duracaoCurso;
    }

    public String getPeriodoCurso() {
        return periodoCurso;
    }

    public void setPeriodoCurso(String periodoCurso) {
        this.periodoCurso = periodoCurso;
    }

    public String getPeirodoEstuda() {
        return peirodoEstuda;
    }

    public void setPeirodoEstuda(String peirodoEstuda) {
        this.peirodoEstuda = peirodoEstuda;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Date getDataEstimadaTermino() {
        return dataEstimadaTermino;
    }

    public void setDataEstimadaTermino(Date dataEstimadaTermino) {
        this.dataEstimadaTermino = dataEstimadaTermino;
    }

    public String getIdioma01() {
        return idioma01;
    }

    public void setIdioma01(String idioma01) {
        this.idioma01 = idioma01;
    }

    public String getNivelidioma01() {
        return nivelidioma01;
    }

    public void setNivelidioma01(String nivelidioma01) {
        this.nivelidioma01 = nivelidioma01;
    }

    public String getTempoidioma01() {
        return tempoidioma01;
    }

    public void setTempoidioma01(String tempoidioma01) {
        this.tempoidioma01 = tempoidioma01;
    }

    public String getIdioma02() {
        return idioma02;
    }

    public void setIdioma02(String idioma02) {
        this.idioma02 = idioma02;
    }

    public String getNivelidioma02() {
        return nivelidioma02;
    }

    public void setNivelidioma02(String nivelidioma02) {
        this.nivelidioma02 = nivelidioma02;
    }

    public String getTempoidioma02() {
        return tempoidioma02;
    }

    public void setTempoidioma02(String tempoidioma02) {
        this.tempoidioma02 = tempoidioma02;
    }

    public String getIdioma03() {
        return idioma03;
    }

    public void setIdioma03(String idioma03) {
        this.idioma03 = idioma03;
    }

    public String getNivelidioma03() {
        return nivelidioma03;
    }

    public void setNivelidioma03(String nivelidioma03) {
        this.nivelidioma03 = nivelidioma03;
    }

    public String getTempoidioma03() {
        return tempoidioma03;
    }

    public void setTempoidioma03(String tempoidioma03) {
        this.tempoidioma03 = tempoidioma03;
    }

    public Date getDataInicioPretendida01() {
        return dataInicioPretendida01;
    }

    public void setDataInicioPretendida01(Date dataInicioPretendida01) {
        this.dataInicioPretendida01 = dataInicioPretendida01;
    }

    public Date getDataInicioPretendida02() {
        return dataInicioPretendida02;
    }

    public void setDataInicioPretendida02(Date dataInicioPretendida02) {
        this.dataInicioPretendida02 = dataInicioPretendida02;
    }

    public Date getDataTerminoPretendida01() {
        return dataTerminoPretendida01;
    }

    public void setDataTerminoPretendida01(Date dataTerminoPretendida01) {
        this.dataTerminoPretendida01 = dataTerminoPretendida01;
    }

    public Date getDataTerminoPretendida02() {
        return dataTerminoPretendida02;
    }

    public void setDataTerminoPretendida02(Date dataTerminoPretendida02) {
        this.dataTerminoPretendida02 = dataTerminoPretendida02;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(String adicionais) {
        this.adicionais = adicionais;
    }

    public String getSabernadar() {
        return sabernadar;
    }

    public void setSabernadar(String sabernadar) {
        this.sabernadar = sabernadar;
    }

    public String getTreinamentosalvavidas() {
        return treinamentosalvavidas;
    }

    public void setTreinamentosalvavidas(String treinamentosalvavidas) {
        this.treinamentosalvavidas = treinamentosalvavidas;
    }

    public String getSabeesquiar() {
        return sabeesquiar;
    }

    public void setSabeesquiar(String sabeesquiar) {
        this.sabeesquiar = sabeesquiar;
    }

    public String getCarteiraHabilitacao() {
        return carteiraHabilitacao;
    }

    public void setCarteiraHabilitacao(String carteiraHabilitacao) {
        this.carteiraHabilitacao = carteiraHabilitacao;
    }

    public String getDirigirEua() {
        return dirigirEua;
    }

    public void setDirigirEua(String dirigirEua) {
        this.dirigirEua = dirigirEua;
    }

    public String getNomePais01() {
        return nomePais01;
    }

    public void setNomePais01(String nomePais01) {
        this.nomePais01 = nomePais01;
    }

    public String getMotivoEstadia01() {
        return motivoEstadia01;
    }

    public void setMotivoEstadia01(String motivoEstadia01) {
        this.motivoEstadia01 = motivoEstadia01;
    }

    public String getPeriodoEstadia01() {
        return periodoEstadia01;
    }

    public void setPeriodoEstadia01(String periodoEstadia01) {
        this.periodoEstadia01 = periodoEstadia01;
    }

    public String getNomePais02() {
        return nomePais02;
    }

    public void setNomePais02(String nomePais02) {
        this.nomePais02 = nomePais02;
    }

    public String getMotivoEstadia02() {
        return motivoEstadia02;
    }

    public void setMotivoEstadia02(String motivoEstadia02) {
        this.motivoEstadia02 = motivoEstadia02;
    }

    public String getPeriodoEstadia02() {
        return periodoEstadia02;
    }

    public void setPeriodoEstadia02(String periodoEstadia02) {
        this.periodoEstadia02 = periodoEstadia02;
    }

    public String getNomePais03() {
        return nomePais03;
    }

    public void setNomePais03(String nomePais03) {
        this.nomePais03 = nomePais03;
    }

    public String getMotivoEstadia03() {
        return motivoEstadia03;
    }

    public void setMotivoEstadia03(String motivoEstadia03) {
        this.motivoEstadia03 = motivoEstadia03;
    }

    public String getPeirodoEstadia03() {
        return peirodoEstadia03;
    }

    public void setPeirodoEstadia03(String peirodoEstadia03) {
        this.peirodoEstadia03 = peirodoEstadia03;
    }

    public String getCampanheiroViagem01() {
        return campanheiroViagem01;
    }

    public void setCampanheiroViagem01(String campanheiroViagem01) {
        this.campanheiroViagem01 = campanheiroViagem01;
    }

    public String getCompanheiroViagem02() {
        return companheiroViagem02;
    }

    public void setCompanheiroViagem02(String companheiroViagem02) {
        this.companheiroViagem02 = companheiroViagem02;
    }

    public String getCompanheiroViagem03() {
        return companheiroViagem03;
    }

    public void setCompanheiroViagem03(String companheiroViagem03) {
        this.companheiroViagem03 = companheiroViagem03;
    }

    public String getTrabalhoVistoJ1() {
        return trabalhoVistoJ1;
    }

    public void setTrabalhoVistoJ1(String trabalhoVistoJ1) {
        this.trabalhoVistoJ1 = trabalhoVistoJ1;
    }

    public String getQuantoVistoj1() {
        return quantoVistoj1;
    }

    public void setQuantoVistoj1(String quantoVistoj1) {
        this.quantoVistoj1 = quantoVistoj1;
    }

    public String getNumeroSocialSecurity() {
        return numeroSocialSecurity;
    }

    public void setNumeroSocialSecurity(String numeroSocialSecurity) {
        this.numeroSocialSecurity = numeroSocialSecurity;
    }

    public String getTipoProgramaEua() {
        return tipoProgramaEua;
    }

    public void setTipoProgramaEua(String tipoProgramaEua) {
        this.tipoProgramaEua = tipoProgramaEua;
    }

    public String getAgenciaBrasil() {
        return agenciaBrasil;
    }

    public void setAgenciaBrasil(String agenciaBrasil) {
        this.agenciaBrasil = agenciaBrasil;
    }

    public String getPosicaoExcercida() {
        return posicaoExcercida;
    }

    public void setPosicaoExcercida(String posicaoExcercida) {
        this.posicaoExcercida = posicaoExcercida;
    }

    public String getOpcaoIndependente() {
        return opcaoIndependente;
    }

    public void setOpcaoIndependente(String opcaoIndependente) {
        this.opcaoIndependente = opcaoIndependente;
    }

    public String getPossuiOfertaTrabalho() {
        return possuiOfertaTrabalho;
    }

    public void setPossuiOfertaTrabalho(String possuiOfertaTrabalho) {
        this.possuiOfertaTrabalho = possuiOfertaTrabalho;
    }

    public String getNomeEmpregador() {
        return nomeEmpregador;
    }

    public void setNomeEmpregador(String nomeEmpregador) {
        this.nomeEmpregador = nomeEmpregador;
    }

    public String getPossicarqueiraExercer() {
        return possicarqueiraExercer;
    }

    public void setPossicarqueiraExercer(String possicarqueiraExercer) {
        this.possicarqueiraExercer = possicarqueiraExercer;
    }

    public String getPossuiDeficienciaFisica() {
        return possuiDeficienciaFisica;
    }

    public void setPossuiDeficienciaFisica(String possuiDeficienciaFisica) {
        this.possuiDeficienciaFisica = possuiDeficienciaFisica;
    }

    public String getProblemaSaude() {
        return problemaSaude;
    }

    public void setProblemaSaude(String problemaSaude) {
        this.problemaSaude = problemaSaude;
    }

    public String getEspecifiqueProblemaSaude() {
        return especifiqueProblemaSaude;
    }

    public void setEspecifiqueProblemaSaude(String especifiqueProblemaSaude) {
        this.especifiqueProblemaSaude = especifiqueProblemaSaude;
    }

    public String getTratamentoMedico() {
        return tratamentoMedico;
    }

    public void setTratamentoMedico(String tratamentoMedico) {
        this.tratamentoMedico = tratamentoMedico;
    }

    public String getEspecifiqueTratamentoMedico() {
        return especifiqueTratamentoMedico;
    }

    public void setEspecifiqueTratamentoMedico(String especifiqueTratamentoMedico) {
        this.especifiqueTratamentoMedico = especifiqueTratamentoMedico;
    }

    public String getTratamentoUsoDrogas() {
        return tratamentoUsoDrogas;
    }

    public void setTratamentoUsoDrogas(String tratamentoUsoDrogas) {
        this.tratamentoUsoDrogas = tratamentoUsoDrogas;
    }

    public String getEspecifiqueTratamentoUsoDrogas() {
        return especifiqueTratamentoUsoDrogas;
    }

    public void setEspecifiqueTratamentoUsoDrogas(String especifiqueTratamentoUsoDrogas) {
        this.especifiqueTratamentoUsoDrogas = especifiqueTratamentoUsoDrogas;
    }

    public String getListaAlergias() {
        return listaAlergias;
    }

    public void setListaAlergias(String listaAlergias) {
        this.listaAlergias = listaAlergias;
    }

    public String getCartaoVTM() {
        return cartaoVTM;
    }

    public void setCartaoVTM(String cartaoVTM) {
        this.cartaoVTM = cartaoVTM;
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

    public String getPassagemAerea() {
        return passagemAerea;
    }

    public void setPassagemAerea(String passagemAerea) {
        this.passagemAerea = passagemAerea;
    }

    public String getTipoEmissaoPassagem() {
        return tipoEmissaoPassagem;
    }

    public void setTipoEmissaoPassagem(String tipoEmissaoPassagem) {
        this.tipoEmissaoPassagem = tipoEmissaoPassagem;
    }

    public String getObservacaoPassagem() {
        return observacaoPassagem;
    }

    public void setObservacaoPassagem(String observacaoPassagem) {
        this.observacaoPassagem = observacaoPassagem;
    }

    public String getTipoVisto() {
        return tipoVisto;
    }

    public void setTipoVisto(String tipoVisto) {
        this.tipoVisto = tipoVisto;
    }

    public String getTipoEmissaoVisto() {
        return tipoEmissaoVisto;
    }

    public void setTipoEmissaoVisto(String tipoEmissaoVisto) {
        this.tipoEmissaoVisto = tipoEmissaoVisto;
    }

    public String getObservacaoVisto() {
        return observacaoVisto;
    }

    public void setObservacaoVisto(String observacaoVisto) {
        this.observacaoVisto = observacaoVisto;
    }

    public Date getDataEntragaDocumentosVisto() {
        return dataEntragaDocumentosVisto;
    }

    public void setDataEntragaDocumentosVisto(Date dataEntragaDocumentosVisto) {
        this.dataEntragaDocumentosVisto = dataEntragaDocumentosVisto;
    }

    public String getFuma() {
        return fuma;
    }

    public void setFuma(String fuma) {
        this.fuma = fuma;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public String getObstm() {
        return obstm;
    }

    public void setObstm(String obstm) {
        this.obstm = obstm;
    }

    public String getInicioPrevisto() {
        return inicioPrevisto;
    }

    public void setInicioPrevisto(String inicioPrevisto) {
        this.inicioPrevisto = inicioPrevisto;
    }

    public Valoreswork getValoreswork() {
        return valoreswork;
    }

    public void setValoreswork(Valoreswork valoreswork) {
        this.valoreswork = valoreswork;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }
    
    

	public boolean isCamareira() {
		return camareira;
	}

	public void setCamareira(boolean camareira) {
		this.camareira = camareira;
	}

	public boolean isCozinha() {
		return cozinha;
	}

	public void setCozinha(boolean cozinha) {
		this.cozinha = cozinha;
	}

	public boolean isSalvavidas() {
		return salvavidas;
	}

	public void setSalvavidas(boolean salvavidas) {
		this.salvavidas = salvavidas;
	}

	public boolean isRecepcao() {
		return recepcao;
	}

	public void setRecepcao(boolean recepcao) {
		this.recepcao = recepcao;
	}

	public boolean isGarcon() {
		return garcon;
	}

	public void setGarcon(boolean garcon) {
		this.garcon = garcon;
	}

	public boolean isTrabalhosexternos() {
		return trabalhosexternos;
	}

	public void setTrabalhosexternos(boolean trabalhosexternos) {
		this.trabalhosexternos = trabalhosexternos;
	}

	public boolean isLavadeira() {
		return lavadeira;
	}

	public void setLavadeira(boolean lavadeira) {
		this.lavadeira = lavadeira;
	}


	public String getEspecificarOutraOcupacao() {
		return especificarOutraOcupacao;
	}

	public void setEspecificarOutraOcupacao(String especificarOutraOcupacao) {
		this.especificarOutraOcupacao = especificarOutraOcupacao;
	}

	public boolean isParquetematico() {
		return parquetematico;
	}

	public void setParquetematico(boolean parquetematico) {
		this.parquetematico = parquetematico;
	}

	public boolean isHotel() {
		return hotel;
	}

	public void setHotel(boolean hotel) {
		this.hotel = hotel;
	}

	public boolean isRestaurante() {
		return restaurante;
	}

	public void setRestaurante(boolean restaurante) {
		this.restaurante = restaurante;
	}

	public boolean isLojas() {
		return lojas;
	}

	public void setLojas(boolean lojas) {
		this.lojas = lojas;
	}

	public boolean isCassino() {
		return cassino;
	}

	public void setCassino(boolean cassino) {
		this.cassino = cassino;
	}

	public boolean isPiscina() {
		return piscina;
	}

	public void setPiscina(boolean piscina) {
		this.piscina = piscina;
	}

	public boolean isEstacaoesqui() {
		return estacaoesqui;
	}

	public void setEstacaoesqui(boolean estacaoesqui) {
		this.estacaoesqui = estacaoesqui;
	}

	public boolean isIndiferentelocal() {
		return indiferentelocal;
	}

	public void setIndiferentelocal(boolean indiferentelocal) {
		this.indiferentelocal = indiferentelocal;
	}

	public boolean isIndiferenteocupacao() {
		return indiferenteocupacao;
	}

	public void setIndiferenteocupacao(boolean indiferenteocupacao) {
		this.indiferenteocupacao = indiferenteocupacao;
	}

	public boolean isOutraocupacao() {
		return outraocupacao;
	}

	public void setOutraocupacao(boolean outraocupacao) {
		this.outraocupacao = outraocupacao;
	}

	public boolean isProgramaidependente() {
		return programaidependente;
	}

	public void setProgramaidependente(boolean programaidependente) {
		this.programaidependente = programaidependente;
	}

	public boolean isOutralocal() {
		return outrolocal;
	}

	public void setOutralocal(boolean outralocal) {
		this.outrolocal = outralocal;
	}

	public String getEspecificaroutraLocal() {
		return especificaroutraLocal;
	}

	public void setEspecificaroutraLocal(String especificaroutraLocal) {
		this.especificaroutraLocal = especificaroutraLocal;
	}

	public boolean isProgramaidependente01() {
		return programaodependente01;
	}

	public void setProgramaidependente01(boolean programaidependente01) {
		this.programaodependente01 = programaidependente01;
	}

	public boolean isOutrolocal() {
		return outrolocal;
	}

	public void setOutrolocal(boolean outrolocal) {
		this.outrolocal = outrolocal;
	}

	public boolean isProgramaodependente01() {
		return programaodependente01;
	}

	public void setProgramaodependente01(boolean programaodependente01) {
		this.programaodependente01 = programaodependente01;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idworkTravel != null ? idworkTravel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Worktravel)) {
            return false;
        }
        Worktravel other = (Worktravel) object;
        if ((this.idworkTravel == null && other.idworkTravel != null) || (this.idworkTravel != null && !this.idworkTravel.equals(other.idworkTravel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.travelmate.model.Worktravel[ idworkTravel=" + idworkTravel + " ]";
        
    }
    
}