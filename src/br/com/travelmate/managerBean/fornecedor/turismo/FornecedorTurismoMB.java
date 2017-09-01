package br.com.travelmate.managerBean.fornecedor.turismo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade; 
import br.com.travelmate.facade.FornecedorFinanceiroFacade;
import br.com.travelmate.model.Cidade; 
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorfinanceiro;

@Named
@ViewScoped
public class FornecedorTurismoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedorfinanceiro> listaFornecedor;
	
	@PostConstruct
	public void init(){
		gerarListaFornecedor();
	}

	public List<Fornecedorfinanceiro> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedorfinanceiro> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}
	
	public void gerarListaFornecedor(){
		String sql = "SELECT f FROM Fornecedorfinanceiro f WHERE f.fornecedor.idfornecedor>1000 ORDER BY f.fornecedor.nome";
		FornecedorFinanceiroFacade fornecedorFinanceiroFacade = new FornecedorFinanceiroFacade();
		listaFornecedor = fornecedorFinanceiroFacade.listar(sql);
	}
	
	public String novoFornecedor(){
		return "cadFornecedorTurismo";
	}
	
	public String editar(Fornecedorfinanceiro fornecedorfinanceiro){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorfinanceiro", fornecedorfinanceiro);
		return "cadFornecedorTurismo";
	}
	
	public void salvarFinanceiro(){
		FornecedorCidadeFacade fornecedorFacade = new FornecedorCidadeFacade();
		String sql = "SELECT f FROM Fornecedorcidade f WHERE f.fornecedor.idfornecedor>1000"
				+ " and f.produtos.idprodutos=7  and f.ativo=1 GROUP BY f.fornecedor.idfornecedor";
		List<Fornecedorcidade> listaFornecedor = fornecedorFacade.listar(sql);
		if(listaFornecedor!=null){
			for (int i = 0; i < listaFornecedor.size(); i++) {
				Fornecedorfinanceiro fornecedorfinanceiro = new Fornecedorfinanceiro();
				fornecedorfinanceiro.setFornecedor(listaFornecedor.get(i).getFornecedor());
				FornecedorFinanceiroFacade fornecedorFinanceiroFacade = new FornecedorFinanceiroFacade();
				fornecedorFinanceiroFacade.salvar(fornecedorfinanceiro);
			}
		}
	}
	
	public void salvarFornecedorCidadeCuritiba(){
		FornecedorCidadeFacade fornecedorFacade = new FornecedorCidadeFacade();
		String sql = "SELECT f FROM Fornecedorcidade f WHERE f.fornecedor.idfornecedor>1000"
				+ " and f.produtos.idprodutos=7  and f.ativo=1";
		List<Fornecedorcidade> listaFornecedor = fornecedorFacade.listar(sql);
		if(listaFornecedor!=null){
			CidadeFacade cidadeFacade = new CidadeFacade();
			Cidade cidade = cidadeFacade.consultar(33);
			for (int i = 0; i < listaFornecedor.size(); i++) {
				Fornecedorcidade fornecedorcidade = new Fornecedorcidade();
				fornecedorcidade.setCidade(cidade);
				fornecedorcidade.setFornecedor(listaFornecedor.get(i).getFornecedor());
				fornecedorcidade.setProdutos(listaFornecedor.get(i).getProdutos());
				fornecedorFacade.salvar(fornecedorcidade);
			}
		}
	}
	
	public String adicionarProdutos(Fornecedorfinanceiro fornecedorfinanceiro) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorfinanceiro", fornecedorfinanceiro);
		RequestContext.getCurrentInstance().openDialog("cadFornecedorFinanceiroProduto");
		return "";
	}

}
