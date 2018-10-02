package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;

@Named
@ViewScoped
public class ProdutoRemessaMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Produtos> listaProdutos;
	private List<Filtroorcamentoproduto> listaProdutoOrcamento;
	private Produtos produto;
	private List<Produtoremessa> listaProdutoRemessa;
	private Produtosorcamento produtoOrcamento;
	
	public ProdutoRemessaMB() {
		ProdutoFacade produtoFacade = new ProdutoFacade();
		listaProdutos = produtoFacade.listarProdutos();
		listaProdutoOrcamento =new ArrayList<Filtroorcamentoproduto>();
		listaProdutoRemessa = new ArrayList<Produtoremessa>();
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Filtroorcamentoproduto> getListaProdutoOrcamento() {
		return listaProdutoOrcamento;
	}

	public void setListaProdutoOrcamento(List<Filtroorcamentoproduto> listaProdutoOrcamento) {
		this.listaProdutoOrcamento = listaProdutoOrcamento;
	}
	
	public List<Produtoremessa> getListaProdutoRemessa() {
		return listaProdutoRemessa;
	}

	public void setListaProdutoRemessa(List<Produtoremessa> listaProdutoRemessa) {
		this.listaProdutoRemessa = listaProdutoRemessa;
	}

	

	public Produtosorcamento getProdutoOrcamento() {
		return produtoOrcamento;
	}

	public void setProdutoOrcamento(Produtosorcamento produtoOrcamento) {
		this.produtoOrcamento = produtoOrcamento;
	}

	public void gerarListaProdutoOrcamento(){
		if (produto!=null){
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			String sql = "Select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + produto.getIdprodutos() + " order by f.produtosorcamento.descricao";
			listaProdutoOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
			if (listaProdutoOrcamento==null){
				listaProdutoOrcamento= new ArrayList<Filtroorcamentoproduto>();
			}
			ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
			try {
				listaProdutoRemessa = produtoRemessaFacade.listar(produto.getIdprodutos());
			} catch (Exception e) {
				  
			}
		}
	}
	
	public void salvarProdutoRemessa(){
		if (produtoOrcamento!=null){
			Produtoremessa produtoremessa = new Produtoremessa();
			produtoremessa.setProdutos(produto);
			produtoremessa.setProdutosorcamento(produtoOrcamento);
			ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
			try {
				produtoremessa = produtoRemessaFacade.salvar(produtoremessa);
			} catch (Exception e) {
				  
			}
			listaProdutoRemessa.add(produtoremessa);
		}
		
	}
	
	public void excluirProdutoRemessa(String linha){
		int nlinha = Integer.parseInt(linha);
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		try {
			produtoRemessaFacade.excluir(listaProdutoRemessa.get(nlinha).getIdprodutoremessa());
			listaProdutoRemessa.remove(nlinha);
		} catch (Exception e) {
			  
		}
	}
	
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	

}
