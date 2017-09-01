package br.com.travelmate.managerBean.OrcamentoCurso;

import java.util.ArrayList;
import java.util.List;

import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.model.Cambio; 
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Publicidade;

public class FiltrarEscolaBean {

	private List<Paisproduto> listaPais;
	private Cidadepaisproduto cidade;
	private List<Cidadepaisproduto> listaCidade;
	private Pais pais;
	private Idioma idioma;
	private List<Idioma> listaIdiomas;
	private List<Produtosorcamento> listaProdutosOrcamento;
	private Ocurso ocurso;
	private List<FornecedorProdutosBean> listaFornecedorProdutosBean;
	private List<Publicidade> listaPublicidades;
	private Cambio cambio;
	private boolean selecionarCliente;
	private String nomeBotao;
	private List<Grupoobrigatorio> listaGrupoObrigatorio;
	private String turno;
	private List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma;
	private Fornecedorcidadeidioma fornecedorcidadeidioma;
	private boolean possuiCursoObrigatorio = false;
	private boolean idiomaescolhido = true;
	private boolean possuifornecedor = true;
	private boolean possuiCurso = false;
	private ProdutosOrcamentoBean taxaTM;
	private boolean clientelead;
	private String codigo;
	private Lead lead;

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	 
	public Cidadepaisproduto getCidade() {
		return cidade;
	}

	public void setCidade(Cidadepaisproduto cidade) {
		this.cidade = cidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public boolean isClientelead() {
		return clientelead;
	}

	public void setClientelead(boolean clientelead) {
		this.clientelead = clientelead;
	}

	public boolean isPossuiCurso() {
		return possuiCurso;
	}

	public void setPossuiCurso(boolean possuiCurso) {
		this.possuiCurso = possuiCurso;
	}

	public List<Produtosorcamento> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Produtosorcamento> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public List<FornecedorProdutosBean> getListaFornecedorProdutosBean() {
		return listaFornecedorProdutosBean;
	}

	public void setListaFornecedorProdutosBean(List<FornecedorProdutosBean> listaFornecedorProdutosBean) {
		this.listaFornecedorProdutosBean = listaFornecedorProdutosBean;
	}

	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}

	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public boolean isSelecionarCliente() {
		return selecionarCliente;
	}

	public void setSelecionarCliente(boolean selecionarCliente) {
		this.selecionarCliente = selecionarCliente;
	}

	public String getNomeBotao() {
		return nomeBotao;
	}

	public void setNomeBotao(String nomeBotao) {
		this.nomeBotao = nomeBotao;
	}

	public List<Grupoobrigatorio> getListaGrupoObrigatorio() {
		return listaGrupoObrigatorio;
	}

	public void setListaGrupoObrigatorio(List<Grupoobrigatorio> listaGrupoObrigatorio) {
		this.listaGrupoObrigatorio = listaGrupoObrigatorio;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public List<Fornecedorcidadeidioma> getListaFornecedorCidadeIdioma() {
		return listaFornecedorCidadeIdioma;
	}

	public void setListaFornecedorCidadeIdioma(List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma) {
		this.listaFornecedorCidadeIdioma = listaFornecedorCidadeIdioma;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}

	public boolean isPossuiCursoObrigatorio() {
		return possuiCursoObrigatorio;
	}

	public void setPossuiCursoObrigatorio(boolean possuiCursoObrigatorio) {
		this.possuiCursoObrigatorio = possuiCursoObrigatorio;
	}

	public boolean isIdiomaescolhido() {
		return idiomaescolhido;
	}

	public void setIdiomaescolhido(boolean idiomaescolhido) {
		this.idiomaescolhido = idiomaescolhido;
	}

	public boolean isPossuifornecedor() {
		return possuifornecedor;
	}

	public void setPossuifornecedor(boolean possuifornecedor) {
		this.possuifornecedor = possuifornecedor;
	}

	public ProdutosOrcamentoBean getTaxaTM() {
		return taxaTM;
	}

	public void setTaxaTM(ProdutosOrcamentoBean taxaTM) {
		this.taxaTM = taxaTM;
	}
 

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public void gerarListaIdioma() {
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		String sql = "Select i from Idioma  i  order by i.descricao";
		listaIdiomas = idiomaFacade.listar(sql);
		if (listaIdiomas == null) {
			listaIdiomas = new ArrayList<Idioma>();
		}
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

}
