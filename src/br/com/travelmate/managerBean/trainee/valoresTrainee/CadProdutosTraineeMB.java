package br.com.travelmate.managerBean.trainee.valoresTrainee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.ProdutosTraineeFacade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Produtostrainee;
import br.com.travelmate.model.Valorestrainee;

@Named
@ViewScoped
public class CadProdutosTraineeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtosorcamento produtoVisto;
	private Produtosorcamento produtoSeguro;
	private Produtosorcamento produtoPrograma;
	private Produtosorcamento produtoApplication;
	private Produtostrainee produtosTrainee;
	private Valorestrainee valorestrainee;
	private List<Filtroorcamentoproduto> listaProdutos;
	
	@PostConstruct
	public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produtosTrainee = (Produtostrainee) session.getAttribute("produtosTrainee");
        valorestrainee = (Valorestrainee) session.getAttribute("valorestrainee");
        session.removeAttribute("produtosTrainee");
        session.removeAttribute("valorestrainee");
        if(produtosTrainee==null){
			produtosTrainee = new Produtostrainee();
			produtosTrainee.setValorapplication(0.0f);
			produtosTrainee.setValorprograma(0.0f);
			produtosTrainee.setValorseguro(0.0f);
			produtosTrainee.setValortotal(0.0f);
			produtosTrainee.setValorvisto(0.0f);
        }else{
        	produtoApplication=produtosTrainee.getProdutoapplication();
        	produtoPrograma=produtosTrainee.getProdutoprograma();
        	produtoSeguro=produtosTrainee.getProdutoseguro();
        	produtoVisto=produtosTrainee.getProdutovisto();
        }
		carregarListaProdutos();
	}

	public Produtosorcamento getProdutoVisto() {
		return produtoVisto;
	}

	public void setProdutoVisto(Produtosorcamento produtoVisto) {
		this.produtoVisto = produtoVisto;
	}

	public Produtosorcamento getProdutoSeguro() {
		return produtoSeguro;
	}

	public void setProdutoSeguro(Produtosorcamento produtoSeguro) {
		this.produtoSeguro = produtoSeguro;
	}

	public Produtosorcamento getProdutoPrograma() {
		return produtoPrograma;
	}

	public void setProdutoPrograma(Produtosorcamento produtoPrograma) {
		this.produtoPrograma = produtoPrograma;
	}

	public Produtosorcamento getProdutoApplication() {
		return produtoApplication;
	}

	public void setProdutoApplication(Produtosorcamento produtoApplication) {
		this.produtoApplication = produtoApplication;
	}

	public Produtostrainee getProdutosTrainee() {
		return produtosTrainee;
	}

	public void setProdutosTrainee(Produtostrainee produtosTrainee) {
		this.produtosTrainee = produtosTrainee;
	}

	public Valorestrainee getValorestrainee() {
		return valorestrainee;
	}

	public void setValorestrainee(Valorestrainee valorestrainee) {
		this.valorestrainee = valorestrainee;
	}

	public List<Filtroorcamentoproduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Filtroorcamentoproduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public void calcularTotal(){
		produtosTrainee.setValortotal(produtosTrainee.getValorapplication()+produtosTrainee.getValorprograma()+produtosTrainee.getValorseguro()+produtosTrainee.getValorvisto());
	}
	
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void carregarListaProdutos(){
        FiltroOrcamentoProdutoFacade filtroOrcamentoFacade = new FiltroOrcamentoProdutoFacade();
        listaProdutos = filtroOrcamentoFacade.pesquisar("select f from Filtroorcamentoproduto f where f.listar='S' and f.produtosorcamento.tipo='D' order by f.produtosorcamento.descricao");
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Filtroorcamentoproduto>();
        }
    }
	
	public String salvar(){
		ProdutosTraineeFacade produtosTraineeFacade= new ProdutosTraineeFacade();
		produtosTrainee.setProdutoapplication(produtoApplication);
		produtosTrainee.setProdutoprograma(produtoPrograma);
		produtosTrainee.setProdutoseguro(produtoSeguro);
		produtosTrainee.setProdutovisto(produtoVisto);
		produtosTrainee.setValorestrainee(valorestrainee);
		produtosTrainee = produtosTraineeFacade.salvar(produtosTrainee);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
