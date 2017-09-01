package br.com.travelmate.managerBean.cursosTeens.valores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ValoresProgramasTeensFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoresaupair;
import br.com.travelmate.model.Valoresprogramasteens;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class CadValoresCursosTeensMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valoresprogramasteens valoresprogramasteens;
	private Fornecedorcidade fornecedorcidade;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private List<Moedas> listaMoedas;
	private Moedas moedas;
	private Moedas moedas1;
	private Produtosorcamento produtos;
	private List<Filtroorcamentoproduto> listaProdutos;
	

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		valoresprogramasteens =  (Valoresprogramasteens) session.getAttribute("valor");
		session.removeAttribute("valor"); 
		int idProduto = 0;
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		idProduto = aplicacaoMB.getParametrosprodutos().getProgramasTeens();
		listaPais = paisProdutoFacade.listar(idProduto);
		carregarComboMoedas();
		carregarListaProdutos();
		if (valoresprogramasteens==null){
			valoresprogramasteens =new Valoresprogramasteens();
		}else {
			pais = valoresprogramasteens.getFornecedorcidade().getCidade().getPais();
			fornecedorcidade = valoresprogramasteens.getFornecedorcidade();
			cidade = valoresprogramasteens.getFornecedorcidade().getCidade();
			moedas = valoresprogramasteens.getMoedas();
			produtos = valoresprogramasteens.getProdutosorcamento();
			listarFornecedorCidade();
		}
	}

	
	public Valoresprogramasteens getvaloresprogramasteens() {
		return valoresprogramasteens;
	}

	public void setvaloresprogramasteens(Valoresprogramasteens valoresprogramasteens) {
		this.valoresprogramasteens = valoresprogramasteens;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public Moedas getMoedas() {
		return moedas;
	}

	public void setMoedas(Moedas moedas) {
		this.moedas = moedas;
	}

	public Produtosorcamento getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtosorcamento produtos) {
		this.produtos = produtos;
	}

	public List<Filtroorcamentoproduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Filtroorcamentoproduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}


	public Moedas getMoedas1() {
		return moedas1;
	}

	public void setMoedas1(Moedas moedas1) {
		this.moedas1 = moedas1;
	}

	public void listarFornecedorCidade() {
		int idProduto = aplicacaoMB.getParametrosprodutos().getProgramasTeens();
		if ((idProduto > 0) && (cidade != null)) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
		}
	}

	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}
	
	public void carregarListaProdutos(){
        FiltroOrcamentoProdutoFacade filtroOrcamentoFacade = new FiltroOrcamentoProdutoFacade();
        listaProdutos = filtroOrcamentoFacade.pesquisar("select f from Filtroorcamentoproduto f where f.listar='S' and f.produtosorcamento.tipo='D' and f.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getProgramasTeens()+" order by f.produtosorcamento.descricao");
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Filtroorcamentoproduto>();
        }
    }
	
	public String salvar(){
		valoresprogramasteens.setSituacao("Ativo");
		valoresprogramasteens.setFornecedorcidade(fornecedorcidade);
		valoresprogramasteens.setMoedas(moedas);
		valoresprogramasteens.setProdutosorcamento(produtos);
		ValoresProgramasTeensFacade valoresprogramasteensFacade = new ValoresProgramasTeensFacade();
		valoresprogramasteens=valoresprogramasteensFacade.salvar(valoresprogramasteens);
		RequestContext.getCurrentInstance().closeDialog(valoresprogramasteens);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
