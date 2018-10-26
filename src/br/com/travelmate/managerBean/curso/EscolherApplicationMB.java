package br.com.travelmate.managerBean.curso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorApplicationFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorapplication;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Produtosorcamento;

@Named
@ViewScoped
public class EscolherApplicationMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Produtosorcamento produtosorcamento;
	private List<Produtosorcamento> listaProdutosOrcamento;
	private List<Fornecedorapplication> listaApplication;
	private boolean desabilitarImpressao = true;
	private String descricao;
	private List<Produtosorcamento> listaProdutosFiltro;
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade");
		session.removeAttribute("fornecedorcidade");
		gerarListaCursos();
	}


	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}


	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}


	public List<Produtosorcamento> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}


	public void setListaProdutosOrcamento(List<Produtosorcamento> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}


	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}


	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}


	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}


	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}
	
	public List<Fornecedorapplication> getListaApplication() {
		return listaApplication;
	}


	public void setListaApplication(List<Fornecedorapplication> listaApplication) {
		this.listaApplication = listaApplication;
	}


	public boolean isDesabilitarImpressao() {
		return desabilitarImpressao;
	}


	public void setDesabilitarImpressao(boolean desabilitarImpressao) {
		this.desabilitarImpressao = desabilitarImpressao;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public List<Produtosorcamento> getListaProdutosFiltro() {
		return listaProdutosFiltro;
	}


	public void setListaProdutosFiltro(List<Produtosorcamento> listaProdutosFiltro) {
		this.listaProdutosFiltro = listaProdutosFiltro;
	}


	public void gerarFornecedorApplication() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getCursos()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		List<Filtroorcamentoproduto> listaFiltro = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaFiltro == null) {
			listaFiltro = new ArrayList<Filtroorcamentoproduto>();
		}
		listaProdutosOrcamento = new ArrayList<Produtosorcamento>();
		for (int i = 0; i < listaFiltro.size(); i++) {
			listaProdutosOrcamento.add(listaFiltro.get(i).getProdutosorcamento());
		}
	}

	public void gerarListaCursos() {
		if (fornecedorcidade != null) {
			String sql = "SELECT f FROM Fornecedorapplication f where f.produtosorcamento.tipoproduto='C' and  f.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor() + " and f.pais.idpais=" + fornecedorcidade.getCidade().getPais().getIdpais();
			sql = sql + " AND (f.nomearquivo is not null or f.nomearquivo='') order by f.produtosorcamento.descricao ";
			if (descricao != null && descricao.length() > 0) {
				sql = sql + " AND f.produtosorcamento.descricao like '%"+ descricao +"%' ";
			}
			FornecedorApplicationFacade fornecedorApplicationFacade = new FornecedorApplicationFacade();
			listaApplication = fornecedorApplicationFacade.listar(sql);
			if (listaApplication == null) {
				listaApplication = new ArrayList<Fornecedorapplication>();
			} else {
				listaProdutosOrcamento = new ArrayList<Produtosorcamento>();
				for (int i = 0; i < listaApplication.size(); i++) {
					if (listaApplication.get(i).getProdutosorcamento().getIdprodutosOrcamento() != 2 && listaApplication.get(i).getProdutosorcamento().getIdprodutosOrcamento() != 17) {
						listaProdutosOrcamento.add(listaApplication.get(i).getProdutosorcamento());
					}
				}
			}
		}
	}
	
	
	public boolean verificarListaProdutosOrcamento(Produtosorcamento produtoOcamento) {
		int idProdutoOrcamento = produtoOcamento.getIdprodutosOrcamento();
		for (int i=0;i<listaProdutosOrcamento.size();i++) {
			if (listaProdutosOrcamento.get(i).getIdprodutosOrcamento()==idProdutoOrcamento) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	public String imprimirApplication(Produtosorcamento produtosorcamento) {
		Fornecedorapplication fornecedorapplication = null;
		if (produtosorcamento != null) {
			for (int i = 0; i < listaApplication.size(); i++) {
				if (listaApplication.get(i).getProdutosorcamento().getIdprodutosOrcamento()==produtosorcamento.getIdprodutosOrcamento()) {
					fornecedorapplication = listaApplication.get(i);
				}
			}
			try {
				String url = "https://local.systm.com.br/application/" + fornecedorapplication.getNomearquivo();
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (IOException e) {
				  
			}
		}
		return "";
	}
	
	
	
	public void verificarImpressao() {
		if (produtosorcamento != null) {
			desabilitarImpressao = false;
		}else {
			desabilitarImpressao = true;
		}
	}
	
	

}
