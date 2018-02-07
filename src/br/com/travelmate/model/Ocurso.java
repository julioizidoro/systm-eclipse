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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "ocurso")
public class Ocurso implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idocurso")
	private Integer idocurso;
	@Size(max = 30)
	@Column(name = "nivelidioma")
	private String nivelidioma;
	@Column(name = "datainicio")
	@Temporal(TemporalType.DATE)
	private Date datainicio;
	@Column(name = "datatermino")
	@Temporal(TemporalType.DATE)
	private Date datatermino;
	@Column(name = "numerosemanas")
	private Integer numerosemanas;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "totalmoedaestrangeira")
	private Float totalmoedaestrangeira;
	@Column(name = "totalmoedanacional")
	private Float totalmoedanacional;
	@Column(name = "desconto")
	private Float desconto;
	@Column(name = "valorpassagem")
	private String valorpassagem;
	@Column(name = "valorvisto")
	private String valorvisto;
	@Column(name = "valorcambio")
	private Float valorcambio;
	@Column(name = "dataorcamento")
	@Temporal(TemporalType.DATE)
	private Date dataorcamento;
	@JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
	@ManyToOne(optional = false)
	private Cambio cambio;
	@JoinColumn(name = "fornecedorcidadeidioma_idfornecedorcidadeidioma", referencedColumnName = "idfornecedorcidadeidioma")
	@ManyToOne(optional = false)
	private Fornecedorcidadeidioma fornecedorcidadeidioma;
	@JoinColumn(name = "idioma_ididioma", referencedColumnName = "ididioma")
	@ManyToOne(optional = false)
	private Idioma idioma;
	@JoinColumn(name = "produtosOrcamento_idprodutosOrcamento", referencedColumnName = "idprodutosOrcamento")
	@ManyToOne(optional = false)
	private Produtosorcamento produtosorcamento;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "ocurso")
	private List<Ocursodesconto> ocursodescontoList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ocurso", fetch=FetchType.EAGER)
	private List<Ocrusoprodutos> OcrusoprodutosList;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "ocurso")
	private List<Ocursoseguro> OcursoseguroList;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "ocurso")
	private List<Ocursoformapagamento> OcursoformapagamentoList;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "ocurso")
	private Ocursopacote ocursopacote;
	@Lob
	@Column(name = "Observacao")
	private String observacao;
	@Column(name = "turno")
	private String turno;
	@Column(name = "cargahoraria")
	private String cargahoraria;
	@Column(name = "numerosemanastotal")
	private int numerosemanastotal;
	@Column(name = "numerodiasferiado")
	private int numerodiasferiado;
	@Column(name = "valoroutros")
	private String valoroutros;
	@Column(name = "numerosemanasbrinde")
	private int numerosemanasbrinde;
	@JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
	@ManyToOne(optional = false)
	private Cliente cliente;
	@Column(name = "occliente_idoccliente")
	private int occliente; 
	@Column(name = "modelo")
	private boolean modelo; 
	@Column(name = "enviadoemail")
	private boolean enviadoemail;
	@Column(name = "datavalidade")
	@Temporal(TemporalType.DATE)
	private Date datavalidade;
	@Transient
	private String nomecliente;
	@Transient
	private boolean selecionado;
	@Column(name = "valoravista")
	private Float valoravista;
	

	public Ocurso() {
		setOccliente(12);
	}

	public Ocurso(Integer idocurso) {
		this.idocurso = idocurso;
	}

	public Integer getIdocurso() {
		return idocurso;
	}

	public void setIdocurso(Integer idocurso) {
		this.idocurso = idocurso;
	}

	public String getNivelidioma() {
		return nivelidioma;
	}

	public void setNivelidioma(String nivelidioma) {
		this.nivelidioma = nivelidioma;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatatermino() {
		return datatermino;
	}

	public void setDatatermino(Date datatermino) {
		this.datatermino = datatermino;
	}

	public Integer getNumerosemanas() {
		return numerosemanas;
	}

	public void setNumerosemanas(Integer numerosemanas) {
		this.numerosemanas = numerosemanas;
	}

	public Float getTotalmoedaestrangeira() {
		return totalmoedaestrangeira;
	}

	public void setTotalmoedaestrangeira(Float totalmoedaestrangeira) {
		this.totalmoedaestrangeira = totalmoedaestrangeira;
	}

	public Float getTotalmoedanacional() {
		return totalmoedanacional;
	}

	public void setTotalmoedanacional(Float totalmoedanacional) {
		this.totalmoedanacional = totalmoedanacional;
	}

	public Float getDesconto() {
		return desconto;
	}

	public void setDesconto(Float desconto) {
		this.desconto = desconto;
	}

	public Date getDataorcamento() {
		return dataorcamento;
	}

	public void setDataorcamento(Date dataorcamento) {
		this.dataorcamento = dataorcamento;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Float getValorcambio() {
		return valorcambio;
	}

	public void setValorcambio(Float valorcambio) {
		this.valorcambio = valorcambio;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}

	public String getValorpassagem() {
		return valorpassagem;
	}

	public void setValorpassagem(String valorpassagem) {
		this.valorpassagem = valorpassagem;
	}

	public String getValorvisto() {
		return valorvisto;
	}

	public void setValorvisto(String valorvisto) {
		this.valorvisto = valorvisto;
	}

	public List<Ocursoformapagamento> getOcursoformapagamentoList() {
		return OcursoformapagamentoList;
	}

	public void setOcursoformapagamentoList(List<Ocursoformapagamento> ocursoformapagamentoList) {
		OcursoformapagamentoList = ocursoformapagamentoList;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Ocrusoprodutos> getOcrusoprodutosList() {
		return OcrusoprodutosList;
	}

	public void setOcrusoprodutosList(List<Ocrusoprodutos> ocrusoprodutosList) {
		OcrusoprodutosList = ocrusoprodutosList;
	}

	public List<Ocursoseguro> getOcursoseguroList() {
		return OcursoseguroList;
	}

	public void setOcursoseguroList(List<Ocursoseguro> ocursoseguroList) {
		OcursoseguroList = ocursoseguroList;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCargahoraria() {
		return cargahoraria;
	}

	public void setCargahoraria(String cargahoraria) {
		this.cargahoraria = cargahoraria;
	}

	public int getNumerosemanastotal() {
		return numerosemanastotal;
	}

	public void setNumerosemanastotal(int numerosemanastotal) {
		this.numerosemanastotal = numerosemanastotal;
	}

	public int getNumerodiasferiado() {
		return numerodiasferiado;
	}

	public void setNumerodiasferiado(int numerodiasferiado) {
		this.numerodiasferiado = numerodiasferiado;
	}

	public String getValoroutros() {
		return valoroutros;
	}

	public void setValoroutros(String valoroutros) {
		this.valoroutros = valoroutros;
	}

	public int getNumerosemanasbrinde() {
		return numerosemanasbrinde;
	}

	public void setNumerosemanasbrinde(int numerosemanasbrinde) {
		this.numerosemanasbrinde = numerosemanasbrinde;
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

	public Date getDatavalidade() {
		return datavalidade;
	}

	public void setDatavalidade(Date datavalidade) {
		this.datavalidade = datavalidade;
	}

	public Float getValoravista() {
		return valoravista;
	}

	public void setValoravista(Float valoravista) {
		this.valoravista = valoravista;
	}

	public boolean isModelo() {
		return modelo;
	}

	public void setModelo(boolean modelo) {
		this.modelo = modelo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idocurso != null ? idocurso.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Ocurso)) {
			return false;
		}
		Ocurso other = (Ocurso) object;
		if ((this.idocurso == null && other.idocurso != null)
				|| (this.idocurso != null && !this.idocurso.equals(other.idocurso))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Ocurso[ idocurso=" + idocurso + " ]";
	}

	public List<Ocursodesconto> getOcursodescontoList() {
		return ocursodescontoList;
	}

	public void setOcursodescontoList(List<Ocursodesconto> ocursodescontoList) {
		this.ocursodescontoList = ocursodescontoList;
	}

}
