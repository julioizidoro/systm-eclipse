package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the demipair database table.
 * 
 */
@Entity
@Table(name = "demipair")
@NamedQueries({ @NamedQuery(name = "Demipair.findAll", query = "SELECT d FROM Demipair d") })
public class Demipair implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "iddemipair")
	private Integer iddemipair;
	@Column(name = "cartaoVTM")
	private String cartaoVTM;
	@Column(name = "controle")
	private String controle;
	@Column(name = "cursandoPeriodo")
	private String cursandoPeriodo;
	@Column(name = "curso")
	private String curso;
	@Column(name = "dataEntregaDocumentosVistos")
	@Temporal(TemporalType.DATE)
	private Date dataEntregaDocumentosVistos;

	@Column(name = "datainicio")
	@Temporal(TemporalType.DATE)
	private Date datainicio;

	@Column(name = "dataInscricao")
	@Temporal(TemporalType.DATE)
	private Date dataInscricao;

	@Column(name = "duracaoCurso")
	private String duracaoCurso;
	@Column(name = "emailContatoEmergencia")
	private String emailContatoEmergencia;
	@Column(name = "endercoAmigo")
	private String endercoAmigo;
	@Column(name = "foneAmigo")
	private String foneAmigo;
	@Column(name = "foneContatoEmergencia")
	private String foneContatoEmergencia;
	@Column(name = "idioma01")
	private String idioma01;
	@Column(name = "idioma02")
	private String idioma02;
	@Column(name = "idioma03")
	private String idioma03;
	@Column(name = "inituicaoEstuda")
	private String inituicaoEstuda;
	@Column(name = "moedaCartao")
	private String moedaCartao;
	@Column(name = "nivelEstudo")
	private String nivelEstudo;
	@Column(name = "nivelIdioma01")
	private String nivelIdioma01;
	@Column(name = "nivelIdioma02")
	private String nivelIdioma02;
	@Column(name = "nivelIdioma03")
	private String nivelIdioma03;
	@Column(name = "nomeAmigo")
	private String nomeAmigo;
	@Column(name = "nomeContatoEmergencia")
	private String nomeContatoEmergencia;
	@Column(name = "numeroCartao")
	private String numeroCartao;
	@Column(name = "observacaoPassagem")
	private String observacaoPassagem;
	@Column(name = "observacaoVisto")
	private String observacaoVisto;
	@Lob
	@Column(name = "obstm")
	private String obstm;
	@Column(name = "ocupacao")
	private String ocupacao;
	@Column(name = "paisinteresse")
	private String paisinteresse;
	@Column(name = "possuiAmigosPais")
	private String possuiAmigosPais;
	@Column(name = "possuicnh")
	private String possuicnh;
	@Column(name = "relacaoAmigo")
	private String relacaoAmigo;
	@Column(name = "relacaoContatoEmergencia")
	private String relacaoContatoEmergencia;
	@Column(name = "tempocnh")
	private String tempocnh;
	@Column(name = "tipoPassagem")
	private String tipoPassagem;
	@Column(name = "tipoSolicitacaoVisto")
	private String tipoSolicitacaoVisto;
	@Column(name = "numerosemanas")
	private int numerosemanas;
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

	public Demipair() {
	}

	public Integer getIddemipair() {
		return iddemipair;
	}

	public String getCartaoVTM() {
		return this.cartaoVTM;
	}

	public void setCartaoVTM(String cartaoVTM) {
		this.cartaoVTM = cartaoVTM;
	}

	public String getControle() {
		return this.controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public String getCursandoPeriodo() {
		return this.cursandoPeriodo;
	}

	public void setCursandoPeriodo(String cursandoPeriodo) {
		this.cursandoPeriodo = cursandoPeriodo;
	}

	public String getCurso() {
		return this.curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Date getDataEntregaDocumentosVistos() {
		return this.dataEntregaDocumentosVistos;
	}

	public void setDataEntregaDocumentosVistos(Date dataEntregaDocumentosVistos) {
		this.dataEntregaDocumentosVistos = dataEntregaDocumentosVistos;
	}

	public Date getDataInscricao() {
		return this.dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public String getDuracaoCurso() {
		return this.duracaoCurso;
	}

	public void setDuracaoCurso(String duracaoCurso) {
		this.duracaoCurso = duracaoCurso;
	}

	public String getEmailContatoEmergencia() {
		return this.emailContatoEmergencia;
	}

	public void setEmailContatoEmergencia(String emailContatoEmergencia) {
		this.emailContatoEmergencia = emailContatoEmergencia;
	}

	public String getEndercoAmigo() {
		return this.endercoAmigo;
	}

	public void setEndercoAmigo(String endercoAmigo) {
		this.endercoAmigo = endercoAmigo;
	}

	public String getFoneAmigo() {
		return this.foneAmigo;
	}

	public void setFoneAmigo(String foneAmigo) {
		this.foneAmigo = foneAmigo;
	}

	public String getFoneContatoEmergencia() {
		return this.foneContatoEmergencia;
	}

	public void setFoneContatoEmergencia(String foneContatoEmergencia) {
		this.foneContatoEmergencia = foneContatoEmergencia;
	}

	public String getIdioma01() {
		return this.idioma01;
	}

	public void setIdioma01(String idioma01) {
		this.idioma01 = idioma01;
	}

	public String getIdioma02() {
		return this.idioma02;
	}

	public void setIdioma02(String idioma02) {
		this.idioma02 = idioma02;
	}

	public String getIdioma03() {
		return this.idioma03;
	}

	public void setIdioma03(String idioma03) {
		this.idioma03 = idioma03;
	}

	public String getInituicaoEstuda() {
		return this.inituicaoEstuda;
	}

	public void setInituicaoEstuda(String inituicaoEstuda) {
		this.inituicaoEstuda = inituicaoEstuda;
	}

	public String getMoedaCartao() {
		return this.moedaCartao;
	}

	public void setMoedaCartao(String moedaCartao) {
		this.moedaCartao = moedaCartao;
	}

	public String getNivelEstudo() {
		return this.nivelEstudo;
	}

	public void setNivelEstudo(String nivelEstudo) {
		this.nivelEstudo = nivelEstudo;
	}

	public String getNivelIdioma01() {
		return this.nivelIdioma01;
	}

	public void setNivelIdioma01(String nivelIdioma01) {
		this.nivelIdioma01 = nivelIdioma01;
	}

	public String getNivelIdioma02() {
		return this.nivelIdioma02;
	}

	public void setNivelIdioma02(String nivelIdioma02) {
		this.nivelIdioma02 = nivelIdioma02;
	}

	public String getNivelIdioma03() {
		return this.nivelIdioma03;
	}

	public void setNivelIdioma03(String nivelIdioma03) {
		this.nivelIdioma03 = nivelIdioma03;
	}

	public String getNomeAmigo() {
		return this.nomeAmigo;
	}

	public void setNomeAmigo(String nomeAmigo) {
		this.nomeAmigo = nomeAmigo;
	}

	public String getNomeContatoEmergencia() {
		return this.nomeContatoEmergencia;
	}

	public void setNomeContatoEmergencia(String nomeContatoEmergencia) {
		this.nomeContatoEmergencia = nomeContatoEmergencia;
	}

	public String getNumeroCartao() {
		return this.numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getObservacaoPassagem() {
		return this.observacaoPassagem;
	}

	public void setObservacaoPassagem(String observacaoPassagem) {
		this.observacaoPassagem = observacaoPassagem;
	}

	public String getObservacaoVisto() {
		return this.observacaoVisto;
	}

	public void setObservacaoVisto(String observacaoVisto) {
		this.observacaoVisto = observacaoVisto;
	}

	public String getObstm() {
		return this.obstm;
	}

	public void setObstm(String obstm) {
		this.obstm = obstm;
	}

	public String getOcupacao() {
		return this.ocupacao;
	}

	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}

	public String getPaisinteresse() {
		return this.paisinteresse;
	}

	public void setPaisinteresse(String paisinteresse) {
		this.paisinteresse = paisinteresse;
	}

	public String getPossuiAmigosPais() {
		return this.possuiAmigosPais;
	}

	public void setPossuiAmigosPais(String possuiAmigosPais) {
		this.possuiAmigosPais = possuiAmigosPais;
	}

	public String getPossuicnh() {
		return this.possuicnh;
	}

	public void setPossuicnh(String possuicnh) {
		this.possuicnh = possuicnh;
	}

	public String getRelacaoAmigo() {
		return this.relacaoAmigo;
	}

	public void setRelacaoAmigo(String relacaoAmigo) {
		this.relacaoAmigo = relacaoAmigo;
	}

	public String getRelacaoContatoEmergencia() {
		return this.relacaoContatoEmergencia;
	}

	public void setRelacaoContatoEmergencia(String relacaoContatoEmergencia) {
		this.relacaoContatoEmergencia = relacaoContatoEmergencia;
	}

	public String getTempocnh() {
		return this.tempocnh;
	}

	public void setTempocnh(String tempocnh) {
		this.tempocnh = tempocnh;
	}

	public String getTipoPassagem() {
		return this.tipoPassagem;
	}

	public void setTipoPassagem(String tipoPassagem) {
		this.tipoPassagem = tipoPassagem;
	}

	public String getTipoSolicitacaoVisto() {
		return this.tipoSolicitacaoVisto;
	}

	public void setTipoSolicitacaoVisto(String tipoSolicitacaoVisto) {
		this.tipoSolicitacaoVisto = tipoSolicitacaoVisto;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public void setIddemipair(Integer iddemipair) {
		this.iddemipair = iddemipair;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public int getNumerosemanas() {
		return numerosemanas;
	}

	public void setNumerosemanas(int numerosemanas) {
		this.numerosemanas = numerosemanas;
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (iddemipair != null ? iddemipair.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Curso)) {
			return false;
		}
		Demipair other = (Demipair) object;
		if ((this.iddemipair == null && other.iddemipair != null)
				|| (this.iddemipair != null && !this.iddemipair.equals(other.iddemipair))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Demipair[ iddemipair=" + iddemipair + " ]";
	}

}