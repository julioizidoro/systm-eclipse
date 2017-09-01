package br.com.travelmate.managerBean.fornecedor.turismo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.FornecedorFinanceiroProdutoFacade; 
import br.com.travelmate.model.Fornecedorfinanceiro;
import br.com.travelmate.model.Fornecedorfinanceiroproduto; 
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadFornecedorTurismoProdutoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private Fornecedorfinanceiro fornecedorfinanceiro;
	private List<Fornecedorfinanceiroproduto> listaProdutos; 
	private String produto;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        fornecedorfinanceiro = (Fornecedorfinanceiro) session.getAttribute("fornecedorfinanceiro");
        session.removeAttribute("fornecedorfinanceiro"); 
        gerarListas();
	}  
	
	public Fornecedorfinanceiro getFornecedorfinanceiro() {
		return fornecedorfinanceiro;
	} 

	public void setFornecedorfinanceiro(Fornecedorfinanceiro fornecedorfinanceiro) {
		this.fornecedorfinanceiro = fornecedorfinanceiro;
	} 
  
	public List<Fornecedorfinanceiroproduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Fornecedorfinanceiroproduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	} 
	
	public String adicionar(){  
		if(produto!=null && produto.length()>2) {
			  FornecedorFinanceiroProdutoFacade fornecedorFinanceiroProdutoFacade = new FornecedorFinanceiroProdutoFacade();
			  Fornecedorfinanceiroproduto fornecedorfinanceiroproduto = new Fornecedorfinanceiroproduto();
			  fornecedorfinanceiroproduto.setFornecedorfinanceiro(fornecedorfinanceiro);
			  fornecedorfinanceiroproduto.setProduto(produto);
			  fornecedorFinanceiroProdutoFacade.salvar(fornecedorfinanceiroproduto);
			  gerarListas();
		}else Mensagem.lancarMensagemErro("Atenção!", "Produto não informado.");
		return "";
	} 
	
	public void excluir(Fornecedorfinanceiroproduto fornecedorfinanceiroproduto) {
		FornecedorFinanceiroProdutoFacade fornecedorFinanceiroProdutoFacade = new FornecedorFinanceiroProdutoFacade();
		fornecedorFinanceiroProdutoFacade.excluir(fornecedorfinanceiroproduto.getIdfornecedorfinanceiroproduto());
		listaProdutos.remove(fornecedorfinanceiroproduto);
		Mensagem.lancarMensagemInfo("Excluído com sucesso!", "");
	}
	
	public void gerarListas() {
		FornecedorFinanceiroProdutoFacade fornecedorFinanceiroProdutoFacade = new FornecedorFinanceiroProdutoFacade();
        listaProdutos = fornecedorFinanceiroProdutoFacade.listar
        		("SELECT f FROM Fornecedorfinanceiroproduto f WHERE f.fornecedorfinanceiro.idfornecedorfinanceiro="
        				+fornecedorfinanceiro.getIdfornecedorfinanceiro());
        if(listaProdutos==null) {
        		listaProdutos = new ArrayList<>();
        }
	}
}
