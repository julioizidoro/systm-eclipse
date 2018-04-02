package br.com.travelmate.managerBean.filtroprodutosorcamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class FiltroProdutosOrcamentoMB implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Filtroorcamentoproduto> listaOrcamentoProduto;
	private Produtos produtos;
	private List<Produtos> listaProdutos;
	
	
	
	
	@PostConstruct
	public void init() {
		gerarListaInicial();
	}




	public List<Filtroorcamentoproduto> getListaOrcamentoProduto() {
		return listaOrcamentoProduto;
	}




	public void setListaOrcamentoProduto(List<Filtroorcamentoproduto> listaOrcamentoProduto) {
		this.listaOrcamentoProduto = listaOrcamentoProduto;
	}




	public Produtos getProdutos() {
		return produtos;
	}




	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}




	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}




	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	
	
	
	public void gerarListaInicial() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "SELECT f FROM Filtroorcamentoproduto f WHERE f.produtosorcamento.idprodutosOrcamento=33";
		listaOrcamentoProduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaOrcamentoProduto == null) {
			listaOrcamentoProduto = new ArrayList<Filtroorcamentoproduto>();
		}
	}
	
	
	public void editar(Filtroorcamentoproduto filtroorcamentoproduto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 250);
		session.setAttribute("filtroorcamentoproduto", filtroorcamentoproduto);
		RequestContext.getCurrentInstance().openDialog("editarFiltroProdutoOrcamento", options, null);
	}
	
	
	public void retornoDialogEditar(SelectEvent event) {
		Filtroorcamentoproduto filtroorcamentoproduto = (Filtroorcamentoproduto) event.getObject();
		if (filtroorcamentoproduto != null) {
			gerarListaInicial();
			Mensagem.lancarMensagemInfo("Valor Alterado com sucesso!", "");
		}
	}
	
	

}
