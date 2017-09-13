package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.ProdutoOrcamentoFacade; 
import br.com.travelmate.model.Produtosorcamento;

@Named
@ViewScoped
public class BuscarProdutosOrcamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private String nomeProduto;
	private List<Produtosorcamento> listaProdutoOrcamento; 
	
	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		nomeProduto = (String) session.getAttribute("nomeProdutosOrcamento"); 
		buscarProdutos();
	} 
  
	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public List<Produtosorcamento> getListaProdutoOrcamento() {
		return listaProdutoOrcamento;
	}

	public void setListaProdutoOrcamento(List<Produtosorcamento> listaProdutoOrcamento) {
		this.listaProdutoOrcamento = listaProdutoOrcamento;
	}

	public void buscarProdutos(){
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade(); 
        listaProdutoOrcamento = produtoOrcamentoFacade.listarProdutosOrcamento(nomeProduto);
        if (listaProdutoOrcamento==null){
        		listaProdutoOrcamento = new ArrayList<Produtosorcamento>();
        }
	} 
	
	public String pesquisar() {
		List<Produtosorcamento> lista = new ArrayList<>();
		for (int i = 0; i < listaProdutoOrcamento.size(); i++) {
			if (listaProdutoOrcamento.get(i).isSelecionado()) {
				lista.add(listaProdutoOrcamento.get(i));
			}
		}
        RequestContext.getCurrentInstance().closeDialog(lista);
        return "";
    } 
}
