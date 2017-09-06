package br.com.travelmate.managerBean.produtoOrcamentoGrupo;

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
import br.com.travelmate.facade.ProdutoOrcamentoGrupoFacade;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Produtosorcamentogrupo;
import br.com.travelmate.model.Produtosorcamentoindice;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadProdutoOrcamentoGrupoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtosorcamentogrupo produtosorcamentogrupo;
	private List<Produtosorcamentogrupo> listaProdutosOrcamentoGrupo;
	private Produtosorcamentoindice produtosorcamentoindice;
	private Produtosorcamento produtoorcamento;
	private List<Produtosorcamento> listaProdutosOrcamento;
	private List<Produtosorcamentoindice> listarProdutosOrcamentoIndice;
	private String produtos="";
	private List<Produtosorcamento> listaProdutosOrcamentoNovo;
	private boolean tabelaNova = true;
	private boolean tabelaAntiga = false;
	private boolean habilitarNovo = true;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		produtosorcamentoindice = (Produtosorcamentoindice) session.getAttribute("produtosorcamentoindice");
		session.removeAttribute("produtosorcamentoindice");
		gerarListaProdutosOrcamento();
	}

	public Produtosorcamentogrupo getProdutosorcamentogrupo() {
		return produtosorcamentogrupo;
	}

	public void setProdutosorcamentogrupo(Produtosorcamentogrupo produtosorcamentogrupo) {
		this.produtosorcamentogrupo = produtosorcamentogrupo;
	}

	public List<Produtosorcamentogrupo> getListaProdutosOrcamentoGrupo() {
		return listaProdutosOrcamentoGrupo;
	}

	public void setListaProdutosOrcamentoGrupo(List<Produtosorcamentogrupo> listaProdutosOrcamentoGrupo) {
		this.listaProdutosOrcamentoGrupo = listaProdutosOrcamentoGrupo;
	}

	public Produtosorcamento getProdutoorcamento() {
		return produtoorcamento;
	}

	public void setProdutoorcamento(Produtosorcamento produtoorcamento) {
		this.produtoorcamento = produtoorcamento;
	}

	public List<Produtosorcamento> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Produtosorcamento> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public Produtosorcamentoindice getProdutosorcamentoindice() {
		return produtosorcamentoindice;
	}

	public void setProdutosorcamentoindice(Produtosorcamentoindice produtosorcamentoindice) {
		this.produtosorcamentoindice = produtosorcamentoindice;
	}

	public List<Produtosorcamentoindice> getListarProdutosOrcamentoIndice() {
		return listarProdutosOrcamentoIndice;
	}

	public void setListarProdutosOrcamentoIndice(List<Produtosorcamentoindice> listarProdutosOrcamentoIndice) {
		this.listarProdutosOrcamentoIndice = listarProdutosOrcamentoIndice;
	}

	public String getProdutos() {
		return produtos;
	}

	public void setProdutos(String produtos) {
		this.produtos = produtos;
	}

	public List<Produtosorcamento> getListaProdutosOrcamentoNovo() {
		return listaProdutosOrcamentoNovo;
	}

	public void setListaProdutosOrcamentoNovo(List<Produtosorcamento> listaProdutosOrcamentoNovo) {
		this.listaProdutosOrcamentoNovo = listaProdutosOrcamentoNovo;
	}

	public boolean isTabelaNova() {
		return tabelaNova;
	}

	public void setTabelaNova(boolean tabelaNova) {
		this.tabelaNova = tabelaNova;
	}

	public boolean isTabelaAntiga() {
		return tabelaAntiga;
	}

	public void setTabelaAntiga(boolean tabelaAntiga) {
		this.tabelaAntiga = tabelaAntiga;
	}

	public boolean isHabilitarNovo() {
		return habilitarNovo;
	}

	public void setHabilitarNovo(boolean habilitarNovo) {
		this.habilitarNovo = habilitarNovo;
	}

	public void gerarListaProdutosOrcamento() {
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		ProdutoOrcamentoGrupoFacade prOrcamentoGrupoFacade = new ProdutoOrcamentoGrupoFacade();
		listaProdutosOrcamento = produtoOrcamentoFacade
				.listarProdutosOrcamentoSql("Select p From Produtosorcamento p where p.novo=false order by p.descricao");
		
		listaProdutosOrcamentoNovo = produtoOrcamentoFacade
				.listarProdutosOrcamentoSql("Select p From Produtosorcamento p where p.novo=true order by p.descricao");
		
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<>();
		}
		if (produtosorcamentoindice != null) {
			listaProdutosOrcamentoGrupo = prOrcamentoGrupoFacade
					.listar("Select p From Produtosorcamentogrupo p Where p.produtosorcamentoindice.idprodutosorcamentoindice="
							+ produtosorcamentoindice.getIdprodutosorcamentoindice());

			if (listaProdutosOrcamentoGrupo == null) {
				listaProdutosOrcamentoGrupo = new ArrayList<>();
			}

			for (int i = 0; i < listaProdutosOrcamentoGrupo.size(); i++) {
				listaProdutosOrcamento.remove(listaProdutosOrcamentoGrupo.get(i).getProdutosorcamento());
			}
		}
	}   

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(false);
	}

	public void salvar() {
		ProdutoOrcamentoGrupoFacade prOrcamentoGrupoFacade = new ProdutoOrcamentoGrupoFacade();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamentogrupo produtosorcamentogrupo = new Produtosorcamentogrupo();
		boolean salvou = false;
		for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
			if (listaProdutosOrcamento.get(i).isSelecionado()) {
				produtosorcamentogrupo = new Produtosorcamentogrupo();
				produtosorcamentogrupo.setProdutosorcamento(listaProdutosOrcamento.get(i));
				produtosorcamentogrupo.setProdutosorcamentoindice(produtosorcamentoindice);
				prOrcamentoGrupoFacade.salvar(produtosorcamentogrupo);
				salvou = true;
			}
		}
		for (int i = 0; i < listaProdutosOrcamentoNovo.size(); i++) {
			if (listaProdutosOrcamentoNovo.get(i).isSelecionado()) {
				produtosorcamentogrupo = new Produtosorcamentogrupo();
				produtosorcamentogrupo.setProdutosorcamento(listaProdutosOrcamentoNovo.get(i));
				produtosorcamentogrupo.setProdutosorcamentoindice(produtosorcamentoindice);
				prOrcamentoGrupoFacade.salvar(produtosorcamentogrupo);
				listaProdutosOrcamentoNovo.get(i).setNovo(false);
				produtoOrcamentoFacade.salvar(listaProdutosOrcamentoNovo.get(i));
				salvou = true;
			}
		}
		if (salvou) {
			RequestContext.getCurrentInstance().closeDialog(salvou);
		} else {
			Mensagem.lancarMensagemInfo("Selecione Algum Produto Orçamento", "");
		}
	}
	
	public void pesquisar() {
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		ProdutoOrcamentoGrupoFacade prOrcamentoGrupoFacade = new ProdutoOrcamentoGrupoFacade();
		listaProdutosOrcamento = produtoOrcamentoFacade
				.listarProdutosOrcamentoSql("Select p From Produtosorcamento p where p.descricao like '%"+produtos+"%'"
						+ " and p.novo=false order by p.descricao");
		
		listaProdutosOrcamentoNovo = produtoOrcamentoFacade
				.listarProdutosOrcamentoSql("Select p From Produtosorcamento p where p.descricao like '%"+produtos+"%'"
						+ " and p.novo=true order by p.descricao");
		
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<>();
		}
		if (produtosorcamentoindice != null) {
			listaProdutosOrcamentoGrupo = prOrcamentoGrupoFacade
					.listar("Select p From Produtosorcamentogrupo p Where p.produtosorcamentoindice.idProdutosorcamentoindice="
							+ produtosorcamentoindice.getIdprodutosorcamentoindice());

			if (listaProdutosOrcamentoGrupo == null) {
				listaProdutosOrcamentoGrupo = new ArrayList<>();
			}

			for (int i = 0; i < listaProdutosOrcamentoGrupo.size(); i++) {
				listaProdutosOrcamento.remove(listaProdutosOrcamentoGrupo.get(i).getProdutosorcamento());
			}
		}
	}
	
	public void listagemNova(){
		if (habilitarNovo) {
			tabelaNova = true;
			tabelaAntiga = false;
		}else{
			tabelaNova = false;
			tabelaAntiga = true;
		}
	}

}
