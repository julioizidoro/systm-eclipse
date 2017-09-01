package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * The persistent class for the controledemipair database table.
 * 
 */
@Entity
@Table(name = "controledemipair")
@NamedQueries({ @NamedQuery(name="Controledemipair.findAll", query="SELECT c FROM Controledemipair c")})
public class Controledemipair implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idcontroledemipair")
	private Integer idcontroledemipair;
	@Column(name = "booking")
	private String booking;
	@Column(name = "coe")
	private String coe;
	@Column(name = "loa")
	private String loa;
	@Column(name = "fichaoriginal")
	private String fichaoriginal;
	@Column(name = "contratooriginal")
	private String contratooriginal;
	@Column(name = "informativoprorama")
	private String informativoprograma;
	@Column(name = "application")
	private String application;
	@Column(name = "testeingles")
	private String testeingles;
	@Column(name = "arquivofoto")
	private String arquivofoto;
	@Column(name = "copiapptcolorida")
	private String copiapptcolorida;
	@Column(name = "termoassinado")
	private String termoassinado;
	@Column(name = "cartaapresentacao")
	private String cartaapresentacao;
	@Column(name = "curriculum")
	private String curriculum;
	@Column(name = "historicoescolar")
	private String historicoescolar;
	@Column(name = "cartarecomendacao")
	private String cartarecomendacao;
	@Column(name = "atestadomedico")
	private String atestadomedico;
	@Column(name = "antecedentescrimianis")
	private String antecedentescrimianis;
	@Column(name = "copiacnh")
	private String copiacnh;
	@Column(name = "carteiravacinacao")
	private String carteiravacinacao;
	@Column(name = "pagamento")
	private String pagamento;
	@Column(name = "passagem")
	private String passagem;
	@JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	@ManyToOne(optional = false)
	private Vendas vendas;
	@Column(name = "visto")
	private String visto;
	@Size(max = 50)
    @Column(name = "statusprocesso")
    private String statusprocesso;

	public Controledemipair() {
	}

	public Integer getIdcontroledemipair() {
		return idcontroledemipair;
	}

	public void setIdcontroledemipair(Integer idcontroledemipair) {
		this.idcontroledemipair = idcontroledemipair;
	}

	public String getBooking() {
		return booking;
	}

	public void setBooking(String booking) {
		this.booking = booking;
	}

	public String getCoe() {
		return coe;
	}

	public void setCoe(String coe) {
		this.coe = coe;
	}


	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public String getPassagem() {
		return passagem;
	}

	public void setPassagem(String passagem) {
		this.passagem = passagem;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public String getVisto() {
		return visto;
	}

	public void setVisto(String visto) {
		this.visto = visto;
	}

	public String getFichaoriginal() {
		return fichaoriginal;
	}

	public void setFichaoriginal(String fichaoriginal) {
		this.fichaoriginal = fichaoriginal;
	}

	public String getContratooriginal() {
		return contratooriginal;
	}

	public void setContratooriginal(String contratooriginal) {
		this.contratooriginal = contratooriginal;
	}

	public String getStatusprocesso() {
		return statusprocesso;
	}

	public void setStatusprocesso(String statusprocesso) {
		this.statusprocesso = statusprocesso;
	}

	public String getInformativoprograma() {
		return informativoprograma;
	}

	public void setInformativoprograma(String informativoprograma) {
		this.informativoprograma = informativoprograma;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getTesteingles() {
		return testeingles;
	}

	public void setTesteingles(String testeingles) {
		this.testeingles = testeingles;
	}

	public String getArquivofoto() {
		return arquivofoto;
	}

	public void setArquivofoto(String arquivofoto) {
		this.arquivofoto = arquivofoto;
	}

	public String getCopiapptcolorida() {
		return copiapptcolorida;
	}

	public void setCopiapptcolorida(String copiapptcolorida) {
		this.copiapptcolorida = copiapptcolorida;
	}

	public String getTermoassinado() {
		return termoassinado;
	}

	public void setTermoassinado(String termoassinado) {
		this.termoassinado = termoassinado;
	}

	public String getCartaapresentacao() {
		return cartaapresentacao;
	}

	public void setCartaapresentacao(String cartaapresentacao) {
		this.cartaapresentacao = cartaapresentacao;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getHistoricoescolar() {
		return historicoescolar;
	}

	public void setHistoricoescolar(String historicoescolar) {
		this.historicoescolar = historicoescolar;
	}

	public String getCartarecomendacao() {
		return cartarecomendacao;
	}

	public void setCartarecomendacao(String cartarecomendacao) {
		this.cartarecomendacao = cartarecomendacao;
	}

	public String getAtestadomedico() {
		return atestadomedico;
	}

	public void setAtestadomedico(String atestadomedico) {
		this.atestadomedico = atestadomedico;
	}

	public String getAntecedentescrimianis() {
		return antecedentescrimianis;
	}

	public void setAntecedentescrimianis(String antecedentescrimianis) {
		this.antecedentescrimianis = antecedentescrimianis;
	}

	public String getCopiacnh() {
		return copiacnh;
	}

	public void setCopiacnh(String copiacnh) {
		this.copiacnh = copiacnh;
	}

	public String getCarteiravacinacao() {
		return carteiravacinacao;
	}

	public void setCarteiravacinacao(String carteiravacinacao) {
		this.carteiravacinacao = carteiravacinacao;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcontroledemipair != null ? idcontroledemipair.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Controledemipair)) {
			return false;
		}
		Controledemipair other = (Controledemipair) object;
		if ((this.idcontroledemipair == null && other.idcontroledemipair != null)
				|| (this.idcontroledemipair != null && !this.idcontroledemipair.equals(other.idcontroledemipair))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Controledemipair[ idcontroledemipair=" + idcontroledemipair + " ]";
	}
}