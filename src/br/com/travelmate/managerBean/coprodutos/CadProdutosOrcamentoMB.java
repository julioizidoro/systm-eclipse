package br.com.travelmate.managerBean.coprodutos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;

@Named
@ViewScoped
public class CadProdutosOrcamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private AplicacaoMB aplicacaoMB;
	private Produtosorcamento produtosorcamento;
	private Filtroorcamentoproduto filtroorcamentoproduto;
	private String nomeProduto;
	private List<Filtroorcamentoproduto> listaFiltroorcamentoproduto;
	private boolean curso=false;
	
	@PostConstruct
	public void init() {
		gerarListaProdutosOrcamento();
		produtosorcamento = new Produtosorcamento();
		filtroorcamentoproduto = new Filtroorcamentoproduto();
		nomeProduto= "";
	}


	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}


	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;  
	}


	public Filtroorcamentoproduto getFiltroorcamentoproduto() {
		return filtroorcamentoproduto;
	}


	public void setFiltroorcamentoproduto(Filtroorcamentoproduto filtroorcamentoproduto) {
		this.filtroorcamentoproduto = filtroorcamentoproduto;
	}


	public String getNomeProduto() {
		return nomeProduto;
	}


	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	
	public List<Filtroorcamentoproduto> getListaFiltroorcamentoproduto() {
		return listaFiltroorcamentoproduto;
	}


	public void setListaFiltroorcamentoproduto(List<Filtroorcamentoproduto> listaFiltroorcamentoproduto) {
		this.listaFiltroorcamentoproduto = listaFiltroorcamentoproduto;
	}


	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}


	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}


	public boolean isCurso() {
		return curso;
	}


	public void setCurso(boolean curso) {
		this.curso = curso;
	}


	public void gerarListaProdutosOrcamento(){
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
        String sql="";
        sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " order by f.produtosorcamento.descricao";
        listaFiltroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
        if (listaFiltroorcamentoproduto==null){
            listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
        }
	}
	
	public void salvarProdutos(Produtosorcamento produtosorcamento){
		ProdutoOrcamentoFacade orcamentoFacade = new ProdutoOrcamentoFacade(); 
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
		orcamentoFacade.salvar(produtosorcamento);
	}
	
	public void salvar(){
		if(produtosorcamento.getIdprodutosOrcamento()!=null){
			ProdutoOrcamentoFacade orcamentoFacade = new ProdutoOrcamentoFacade(); 
			if(curso){
				produtosorcamento.setTipoproduto("C");
			}
			produtosorcamento = orcamentoFacade.salvar(produtosorcamento);
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
			filtroorcamentoproduto = filtroOrcamentoProdutoFacade.salvar(filtroorcamentoproduto);
			gerarListaProdutosOrcamento();  
			produtosorcamento = new Produtosorcamento();
			filtroorcamentoproduto = new Filtroorcamentoproduto();
		}else{
			ProdutoOrcamentoFacade orcamentoFacade = new ProdutoOrcamentoFacade();
			produtosorcamento.setVincular(true);
			if(curso){
				produtosorcamento.setTipoproduto("C");
			}
			produtosorcamento.setNovo(true);   
			produtosorcamento = orcamentoFacade.salvar(produtosorcamento);
			FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
	        filtroorcamentoproduto.setProdutosorcamento(produtosorcamento);
	        ProdutoFacade produtoFacade = new ProdutoFacade();
	        int idProduto = 0;
			idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			Produtos produtos = new Produtos();
			produtos = produtoFacade.consultar(idProduto);
			filtroorcamentoproduto.setProdutos(produtos);
			filtroorcamentoproduto.setListar("N");
			filtroorcamentoproduto = filtroOrcamentoProdutoFacade.salvar(filtroorcamentoproduto);
			produtosorcamento = new Produtosorcamento();
			filtroorcamentoproduto = new Filtroorcamentoproduto();
			gerarListaProdutosOrcamento();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
		}
	}
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	
	public void pesquisar(){
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
        String sql="";
        sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.descricao like '%"+nomeProduto+"%' order by f.produtosorcamento.descricao";
        listaFiltroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
        if (listaFiltroorcamentoproduto==null){
            listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
        }
	}
	
	
	public String excluir(Filtroorcamentoproduto filtroorcamentoproduto) {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		filtroOrcamentoProdutoFacade.excluir(filtroorcamentoproduto.getIdfiltroOrcamentoProduto());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
		gerarListaProdutosOrcamento();
		return "";
	}

	public void editar(Filtroorcamentoproduto filtroorcamentoproduto){
		produtosorcamento = filtroorcamentoproduto.getProdutosorcamento();
		if(produtosorcamento.getTipoproduto()!=null && produtosorcamento.getTipoproduto().equalsIgnoreCase("C")){
			curso=true;
		}
		this.filtroorcamentoproduto = filtroorcamentoproduto;
	}
}
