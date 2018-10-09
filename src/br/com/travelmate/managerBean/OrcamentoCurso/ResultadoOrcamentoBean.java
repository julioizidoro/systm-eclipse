package br.com.travelmate.managerBean.OrcamentoCurso;

import java.util.Date;
import java.util.List;

import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Copromocao;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;

public class ResultadoOrcamentoBean {

	private Fornecedorcidadeidioma fornecedorcidadeidioma;
	private ProdutoFornecedorBean produtoFornecedorBean;
	private Copromocao copromocao;
	private Ocurso ocurso;
	private Cambio cambio;
	private List<ProdutosOrcamentoBean> listaOpcionais;
	private List<ProdutosOrcamentoBean> listaAcomodacoes;
	private List<ProdutosOrcamentoBean> listaAcOpcional;
	private List<ProdutosOrcamentoBean> listaTransfer;
	private List<ProdutosExtrasBean> listaOutrosProdutos;
	private List<FornecedorProdutosBean> listaFornecedorProdutosBean;
	private Fornecedorcidade fornecedorcidade;
	private Valoresseguro valoresSeguro;
	private Seguroviagem seguroviagem;
	private boolean seguroSelecionado;
	private String valorPassagemAerea;
	private String valorVistoConsular;
	private String valorOutros;
	private ProdutosExtrasBean produtosExtrasBean;
	private Date dataConsulta;
	private boolean clientelead;
	private String codigo;
	private Lead lead;
	private List<ProdutosOrcamentoBean> listaOpcionaisSelecionado;

	public boolean isSeguroSelecionado() {
		return seguroSelecionado;
	}

	public void setSeguroSelecionado(boolean seguroSelecionado) {
		this.seguroSelecionado = seguroSelecionado;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public Valoresseguro getValoresSeguro() {
		return valoresSeguro;
	}

	public void setValoresSeguro(Valoresseguro valoresSeguro) {
		this.valoresSeguro = valoresSeguro;
	}

	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}

	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}

	public ProdutoFornecedorBean getProdutoFornecedorBean() {
		return produtoFornecedorBean;
	}

	public void setProdutoFornecedorBean(ProdutoFornecedorBean produtoFornecedorBean) {
		this.produtoFornecedorBean = produtoFornecedorBean;
	}

	public Copromocao getCopromocao() {
		return copromocao;
	}

	public void setCopromocao(Copromocao copromocao) {
		this.copromocao = copromocao;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public List<ProdutosOrcamentoBean> getListaOpcionais() {
		return listaOpcionais;
	}

	public void setListaOpcionais(List<ProdutosOrcamentoBean> listaOpcionais) {
		this.listaOpcionais = listaOpcionais;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoes() {
		return listaAcomodacoes;
	}

	public void setListaAcomodacoes(List<ProdutosOrcamentoBean> listaAcomodacoes) {
		this.listaAcomodacoes = listaAcomodacoes;
	}

	public List<ProdutosOrcamentoBean> getListaTransfer() {
		return listaTransfer;
	}

	public void setListaTransfer(List<ProdutosOrcamentoBean> listaTransfer) {
		this.listaTransfer = listaTransfer;
	}

	public List<ProdutosOrcamentoBean> getListaAcOpcional() {
		return listaAcOpcional;
	}

	public void setListaAcOpcional(List<ProdutosOrcamentoBean> listaAcOpcional) {
		this.listaAcOpcional = listaAcOpcional;
	}

	public String getValorPassagemAerea() {
		return valorPassagemAerea;
	}

	public void setValorPassagemAerea(String valorPassagemAerea) {
		this.valorPassagemAerea = valorPassagemAerea;
	}

	public String getValorVistoConsular() {
		return valorVistoConsular;
	}

	public void setValorVistoConsular(String valorVistoConsular) {
		this.valorVistoConsular = valorVistoConsular;
	}

	public List<FornecedorProdutosBean> getListaFornecedorProdutosBean() {
		return listaFornecedorProdutosBean;
	}

	public void setListaFornecedorProdutosBean(List<FornecedorProdutosBean> listaFornecedorProdutosBean) {
		this.listaFornecedorProdutosBean = listaFornecedorProdutosBean;
	}

	public List<ProdutosExtrasBean> getListaOutrosProdutos() {
		return listaOutrosProdutos;
	}

	public void setListaOutrosProdutos(List<ProdutosExtrasBean> listaOutrosProdutos) {
		this.listaOutrosProdutos = listaOutrosProdutos;
	}

	public ProdutosExtrasBean getProdutosExtrasBean() {
		return produtosExtrasBean;
	}

	public void setProdutosExtrasBean(ProdutosExtrasBean produtosExtrasBean) {
		this.produtosExtrasBean = produtosExtrasBean;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public String getValorOutros() {
		return valorOutros;
	}

	public void setValorOutros(String valorOutros) {
		this.valorOutros = valorOutros;
	}

	public boolean isClientelead() {
		return clientelead;
	}

	public void setClientelead(boolean clientelead) {
		this.clientelead = clientelead;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public List<ProdutosOrcamentoBean> getListaOpcionaisSelecionado() {
		return listaOpcionaisSelecionado;
	}

	public void setListaOpcionaisSelecionado(List<ProdutosOrcamentoBean> listaOpcionaisSelecionado) {
		this.listaOpcionaisSelecionado = listaOpcionaisSelecionado;
	}

}
