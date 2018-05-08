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
@Table(name = "vendas")
public class Vendas implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idvendas")
	private Integer idvendas;
	@Column(name = "dataVenda")
	@Temporal(TemporalType.DATE)
	private Date dataVenda;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "valor")
	private Float valor;
	@Size(max = 20)
	@Column(name = "situacao")
	private String situacao;
	@Lob
	@Size(max = 2147483647)
	@Column(name = "Obstm")
	private String obstm;
	@Size(max = 1)
	@Column(name = "vendasMatriz")
	private String vendasMatriz;
	@Column(name = "vendaimportada")
	private Integer vendaimportada;
	@Size(max = 200)
	@Column(name = "obsCancelar")
	private String obsCancelar;
	@Size(max = 1)
	@Column(name = "statuscobranca")
	private String statuscobranca;
	@Column(name = "datacancelamento")
	@Temporal(TemporalType.DATE)
	private Date datacancelamento;
	@Column(name = "usuariocancelamento")
	private Integer usuariocancelamento;
	@JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
	@ManyToOne(optional = false)
	private Cliente cliente;
	@JoinColumn(name = "fornecedor_idfornecedor", referencedColumnName = "idfornecedor")
	@ManyToOne(optional = false)
	private Fornecedor fornecedor;
	@JoinColumn(name = "fornecedorcidade_idfornecedorcidade", referencedColumnName = "idfornecedorcidade")
	@ManyToOne(optional = false)
	private Fornecedorcidade fornecedorcidade;
	@JoinColumn(name = "produtos_idprodutos", referencedColumnName = "idprodutos")
	@ManyToOne(optional = false)
	private Produtos produtos;
	@JoinColumn(name = "unidadeNegocio_idunidadeNegocio", referencedColumnName = "idunidadeNegocio")
	@ManyToOne(optional = false)
	private Unidadenegocio unidadenegocio;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Usuario usuario;
	@JoinColumn(name = "cambio_idcambio", referencedColumnName = "idcambio")
	@ManyToOne(optional = false)
	private Cambio cambio;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private List<Cobranca> cobrancaList;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private List<Contasreceber> contasreceberList;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private Orcamento orcamento;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private Formapagamento formapagamento;
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "vendas")
	private Vendascomissao vendascomissao;
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private List<Alteracaofinanceiro> alteracaofinanceiroList;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private List<Controlecurso> controlecursosList;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private Vendaspacote vendaspacote;
	@Column(name = "valorcambio")
	private Float valorcambio;
	@Column(name = "restricaoparcelamento")
	private boolean restricaoparcelamento;
	@Column(name = "ponto")
	private int ponto;
	@Column(name = "pontoescola")
	private int pontoescola;
	@Transient
	private String botao;
	@Column(name = "idlead")
	private int idlead;
	@Column(name = "pontoextra")
	private int pontoextra;
	@Column(name = "idregravenda")
	private int idregravenda;
	@Column(name = "situacaogerencia")
	private String situacaogerencia;//P- Processo  A - Andamento  F - Finalizada
	@Column(name = "situacaofinanceiro")
	private String situacaofinanceiro;//N - Nova  P - Pendencia  L - Liberada
	@Column(name = "dataprocesso")
	@Temporal(TemporalType.DATE)
	private Date dataprocesso;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "vendas", fetch=FetchType.LAZY)
    private Arquivoskitviagem arquivoskitviagem;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "vendas", fetch=FetchType.LAZY)
    private Vendapendencia vendapendencia; 
	@Transient
	private boolean selecionado;
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private Leadposvenda leadposvenda; 
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "vendas")
	private Cancelamento cancelamento;

	public Vendas() {
		setStatuscobranca("p");
		setRestricaoparcelamento(false);
		setPonto(0);
		setIdregravenda(0);
		setSituacaofinanceiro("N");
		setSituacaogerencia("P");
		setSituacao("PROCESSO");
	}

	public boolean isRestricaoparcelamento() {
		return restricaoparcelamento;
	}

	public void setRestricaoparcelamento(boolean restricaoparcelamento) {
		this.restricaoparcelamento = restricaoparcelamento;
	}

	public String getBotao() {
		return botao;
	}

	public void setBotao(String botao) {
		this.botao = botao;
	}

	public Vendas(Integer idvendas) {
		this.idvendas = idvendas;
	}

	public Integer getIdvendas() {
		return idvendas;
	}

	public void setIdvendas(Integer idvendas) {
		this.idvendas = idvendas;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getObstm() {
		return obstm;
	}

	public void setObstm(String obstm) {
		this.obstm = obstm;
	}

	public String getVendasMatriz() {
		return vendasMatriz;
	}

	public void setVendasMatriz(String vendasMatriz) {
		this.vendasMatriz = vendasMatriz;
	}

	public Integer getVendaimportada() {
		return vendaimportada;
	}

	public Formapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public void setVendaimportada(Integer vendaimportada) {
		this.vendaimportada = vendaimportada;
	}

	public String getObsCancelar() {
		return obsCancelar;
	}

	public void setObsCancelar(String obsCancelar) {
		this.obsCancelar = obsCancelar;
	}

	public Date getDatacancelamento() {
		return datacancelamento;
	}

	public void setDatacancelamento(Date datacancelamento) {
		this.datacancelamento = datacancelamento;
	}

	public Integer getUsuariocancelamento() {
		return usuariocancelamento;
	}

	public void setUsuariocancelamento(Integer usuariocancelamento) {
		this.usuariocancelamento = usuariocancelamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
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

	public List<Alteracaofinanceiro> getAlteracaofinanceiroList() {
		return alteracaofinanceiroList;
	}

	public void setAlteracaofinanceiroList(List<Alteracaofinanceiro> alteracaofinanceiroList) {
		this.alteracaofinanceiroList = alteracaofinanceiroList;
	}

	public List<Controlecurso> getControlecursosList() {
		return controlecursosList;
	}

	public void setControlecursosList(List<Controlecurso> controlecursosList) {
		this.controlecursosList = controlecursosList;
	}

	public int getPontoescola() {
		return pontoescola;
	}

	public void setPontoescola(int pontoescola) {
		this.pontoescola = pontoescola;
	}

	public int getIdlead() {
		return idlead;
	}

	public void setIdlead(int idlead) {
		this.idlead = idlead;
	}

	public List<Cobranca> getCobrancaList() {
		return cobrancaList;
	}

	public void setCobrancaList(List<Cobranca> cobrancaList) {
		this.cobrancaList = cobrancaList;
	}

	public List<Contasreceber> getContasreceberList() {
		return contasreceberList;
	}

	public void setContasreceberList(List<Contasreceber> contasreceberList) {
		this.contasreceberList = contasreceberList;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Vendascomissao getVendascomissao() {
		return vendascomissao;
	}

	public void setVendascomissao(Vendascomissao vendascomissao) {
		this.vendascomissao = vendascomissao;
	}

	public String getStatuscobranca() {
		return statuscobranca;
	}

	public void setStatuscobranca(String statuscobranca) {
		this.statuscobranca = statuscobranca;
	}

	public int getPonto() {
		return ponto;
	}

	public void setPonto(int ponto) {
		this.ponto = ponto;
	}

	

	public Arquivoskitviagem getArquivoskitviagem() {
		return arquivoskitviagem;
	}

	public void setArquivoskitviagem(Arquivoskitviagem arquivoskitviagem) {
		this.arquivoskitviagem = arquivoskitviagem;
	}

	public Vendaspacote getVendaspacote() {
		return vendaspacote;
	}

	public void setVendaspacote(Vendaspacote vendaspacote) {
		this.vendaspacote = vendaspacote;
	}

	public Date getDataprocesso() {
		return dataprocesso;
	}

	public void setDataprocesso(Date dataprocesso) {
		this.dataprocesso = dataprocesso;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public int getPontoextra() {
		return pontoextra;
	}

	public void setPontoextra(int pontoextra) {
		this.pontoextra = pontoextra;
	}

	public int getIdregravenda() {
		return idregravenda;
	}

	public void setIdregravenda(int idregravenda) {
		this.idregravenda = idregravenda;
	}

	public Vendapendencia getVendapendencia() {
		return vendapendencia;
	}

	public void setVendapendencia(Vendapendencia vendapendencia) {
		this.vendapendencia = vendapendencia;
	}

	public String getSituacaogerencia() {
		return situacaogerencia;
	}

	public void setSituacaogerencia(String situacaogerencia) {
		this.situacaogerencia = situacaogerencia;
	}

	public String getSituacaofinanceiro() {
		return situacaofinanceiro;
	}

	public void setSituacaofinanceiro(String situacaofinanceiro) {
		this.situacaofinanceiro = situacaofinanceiro;
	}
 
	public Leadposvenda getLeadposvenda() {
		return leadposvenda;
	}

	public void setLeadposvenda(Leadposvenda leadposvenda) {
		this.leadposvenda = leadposvenda;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idvendas != null ? idvendas.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Vendas)) {
			return false;
		}
		Vendas other = (Vendas) object;
		if ((this.idvendas == null && other.idvendas != null)
				|| (this.idvendas != null && !this.idvendas.equals(other.idvendas))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Vendas[ idvendas=" + idvendas + " ]";
	}
}
