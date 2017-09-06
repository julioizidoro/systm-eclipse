package br.com.travelmate.managerBean.produtoOrcamentoGrupo;

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

import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoGrupoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoIndiceFacade;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Produtosorcamentogrupo;
import br.com.travelmate.model.Produtosorcamentoindice;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ProdutoOrcamentoGrupoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtosorcamentoindice produtosorcamentoindice;
	private List<Produtosorcamentoindice> listaProdutosOrcamentoIndice;
	private String descricao;
	private List<Produtosorcamentogrupo> listaProdutosOrcamentoGrupo;
	
	
	
	@PostConstruct
	public void init(){
		gerarListaProdutoOrcanmentoIndice();
	}



	public Produtosorcamentoindice getProdutosorcamentoindice() {
		return produtosorcamentoindice;
	}



	public void setProdutosorcamentoindice(Produtosorcamentoindice produtosorcamentoindice) {
		this.produtosorcamentoindice = produtosorcamentoindice;
	}




	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	   
	
	
	public List<Produtosorcamentoindice> getListaProdutosOrcamentoIndice() {
		return listaProdutosOrcamentoIndice;
	}



	public void setListaProdutosOrcamentoIndice(List<Produtosorcamentoindice> listaProdutosOrcamentoIndice) {
		this.listaProdutosOrcamentoIndice = listaProdutosOrcamentoIndice;
	}



	public List<Produtosorcamentogrupo> getListaProdutosOrcamentoGrupo() {
		return listaProdutosOrcamentoGrupo;
	}



	public void setListaProdutosOrcamentoGrupo(List<Produtosorcamentogrupo> listaProdutosOrcamentoGrupo) {
		this.listaProdutosOrcamentoGrupo = listaProdutosOrcamentoGrupo;
	}



	public void gerarListaProdutoOrcanmentoIndice(){
		ProdutoOrcamentoIndiceFacade prOrcamentoIndiceFacade = new ProdutoOrcamentoIndiceFacade();
		listaProdutosOrcamentoIndice = prOrcamentoIndiceFacade.listar("Select p From Produtosorcamentoindice p");
		if (listaProdutosOrcamentoIndice == null) {
			listaProdutosOrcamentoIndice = new ArrayList<>();
		}
	}
	
	
	public void salvarPrOrcamentoIndice(){
		boolean editar = false;
		ProdutoOrcamentoIndiceFacade prOrcamentoIndiceFacade = new ProdutoOrcamentoIndiceFacade();
		Produtosorcamentoindice produtosorcamentoindice = new Produtosorcamentoindice();
		if (descricao != null  && descricao.length() > 0) {
			if (this.produtosorcamentoindice != null) {
				produtosorcamentoindice = this.produtosorcamentoindice;
				editar = true;
			}
			produtosorcamentoindice.setDescricao(descricao);
			produtosorcamentoindice = prOrcamentoIndiceFacade.salvar(produtosorcamentoindice);
			if (editar) {
				gerarListaProdutoOrcanmentoIndice();
			}else{
				listaProdutosOrcamentoIndice.add(produtosorcamentoindice);
			}
			Mensagem.lancarMensagemInfo("Salvar Com Sucesso", "");
			descricao = "";
		}else{
			Mensagem.lancarMensagemInfo("Por Favor Inserir a Descrição", "");
		}
	}
	
	
	public void excluirPrOrcamentoIndice(Produtosorcamentoindice produtosorcamentoindice){
		ProdutoOrcamentoIndiceFacade prOrcamentoIndiceFacade = new ProdutoOrcamentoIndiceFacade();
		ProdutoOrcamentoGrupoFacade prOrcamentoGrupoFacade = new ProdutoOrcamentoGrupoFacade();
		List<Produtosorcamentogrupo> listaProdutosOrcamentoGrupo = prOrcamentoGrupoFacade.listar("Select p From Produtosorcamentogrupo p "
				+ " Where p.produtosorcamentoindice.idProdutosorcamentoindice=" + produtosorcamentoindice.getIdprodutosorcamentoindice());
		if (listaProdutosOrcamentoGrupo != null) {
			for (int i = 0; i < listaProdutosOrcamentoGrupo.size(); i++) {
				prOrcamentoGrupoFacade.excluir(listaProdutosOrcamentoGrupo.get(i).getIdprodutosorcamentogrupo());
			}
		}   
		prOrcamentoIndiceFacade.excluir(produtosorcamentoindice.getIdprodutosorcamentoindice());
		listaProdutosOrcamentoIndice.remove(produtosorcamentoindice);
		listaProdutosOrcamentoGrupo = new ArrayList<>();
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}  
	
	
	public String vincularProdutoOrcamento(Produtosorcamentoindice produtosorcamentoindice) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		session.setAttribute("produtosorcamentoindice", produtosorcamentoindice);
		RequestContext.getCurrentInstance().openDialog("cadProdutoOrcamentoGrupo", options, null);
		return "";
	}
	
	
	public void gerarListaProdutoOrcanmentoGrupo(Produtosorcamentoindice produtosorcamentoindice){
		ProdutoOrcamentoGrupoFacade prOrcamentoGrupoFacade = new ProdutoOrcamentoGrupoFacade();
		listaProdutosOrcamentoGrupo = prOrcamentoGrupoFacade.listar("Select p From Produtosorcamentogrupo p Where p.produtosorcamentoindice.idProdutosorcamentoindice="
				+ produtosorcamentoindice.getIdprodutosorcamentoindice());
		if (listaProdutosOrcamentoGrupo == null) {
			listaProdutosOrcamentoGrupo = new ArrayList<>();
		}
	}
	
	
	public void excluirProdutoOrcamento(Produtosorcamentogrupo produtosorcamentogrupo){
		ProdutoOrcamentoGrupoFacade prOrcamentoGrupoFacade = new ProdutoOrcamentoGrupoFacade();
		prOrcamentoGrupoFacade.excluir(produtosorcamentogrupo.getIdprodutosorcamentogrupo());
		listaProdutosOrcamentoGrupo.remove(produtosorcamentogrupo);
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}
	
	
	public void retornoDialogVinculo(SelectEvent event){
		boolean salvou = (boolean) event.getObject();
		if (salvou) {
			if (listaProdutosOrcamentoGrupo != null && listaProdutosOrcamentoGrupo.size() > 0) {
				listaProdutosOrcamentoGrupo = new ArrayList<>();
			}
			gerarListaProdutoOrcanmentoIndice();
			Mensagem.lancarMensagemInfo("Vinculado com sucesso", "");
		}
	}
	
	
	public void editar(Produtosorcamentoindice produtosorcamentoindice){
		this.produtosorcamentoindice = produtosorcamentoindice;
		descricao = produtosorcamentoindice.getDescricao();
	}
	
	
	public void editarProdutoOrcamento(Produtosorcamento p) {
       ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
       produtoOrcamentoFacade.salvar(p);
       Mensagem.lancarMensagemInfo("", "Salvo com sucesso");
    }
	
	
	
	
	
	

}
