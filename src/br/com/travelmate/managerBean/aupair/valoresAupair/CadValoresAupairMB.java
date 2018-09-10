package br.com.travelmate.managerBean.aupair.valoresAupair;

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
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ValorAupairFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoresaupair;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class CadValoresAupairMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valoresaupair valoresAupair;
	private Fornecedorcidade fornecedorcidade;
	private List<Pais> listaPais;
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
		valoresAupair = (Valoresaupair) session.getAttribute("valor");
		session.removeAttribute("valor");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		carregarComboMoedas();
		carregarListaProdutos();
		if (valoresAupair==null){
			valoresAupair =new Valoresaupair();
		}else {
			pais = valoresAupair.getFornecedorcidade().getCidade().getPais();
			fornecedorcidade = valoresAupair.getFornecedorcidade();
			cidade = valoresAupair.getFornecedorcidade().getCidade();
			moedas = valoresAupair.getMoedas();
			moedas1 = valoresAupair.getMoedas1();
			produtos = valoresAupair.getProdutosorcamento();
			listarFornecedorCidade();
		}
	}

	
	public Valoresaupair getValoresAupair() {
		return valoresAupair;
	}

	public void setValoresAupair(Valoresaupair valoresAupair) {
		this.valoresAupair = valoresAupair;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
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
		int idProduto = aplicacaoMB.getParametrosprodutos().getAupair();
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
        listaProdutos = filtroOrcamentoFacade.pesquisar("select f from Filtroorcamentoproduto f where f.listar='S' and f.produtosorcamento.tipo='D' order by f.produtosorcamento.descricao");
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Filtroorcamentoproduto>();
        }
    }
	
	public String salvar(){
		valoresAupair.setSituacao("Ativo");
		valoresAupair.setFornecedorcidade(fornecedorcidade);
		valoresAupair.setMoedas(moedas);
		valoresAupair.setMoedas1(moedas1);
		valoresAupair.setFornecedor(fornecedorcidade.getFornecedor());
		valoresAupair.setProdutosorcamento(produtos);
		ValorAupairFacade valorAupairFacade = new ValorAupairFacade();
		valoresAupair=valorAupairFacade.salvar(valoresAupair);
		RequestContext.getCurrentInstance().closeDialog(valoresAupair);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
