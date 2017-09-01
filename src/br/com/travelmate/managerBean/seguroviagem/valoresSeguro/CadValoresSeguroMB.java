package br.com.travelmate.managerBean.seguroviagem.valoresSeguro;

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
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class CadValoresSeguroMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valoresseguro valoresseguro;
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
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        valoresseguro= (Valoresseguro) session.getAttribute("valor");
        session.removeAttribute("valor");
		int idProduto = 0;
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		idProduto = aplicacaoMB.getParametrosprodutos().getSeguroPrivado();
		listaPais = paisProdutoFacade.listar(idProduto);
		carregarComboMoedas();
		carregarListaProdutos(); 
		if (valoresseguro==null){
			valoresseguro = new Valoresseguro();
			seguroplanos = new Seguroplanos();
		}else {
			pais = valoresseguro.getFornecedorcidade().getCidade().getPais();
			cidade = valoresseguro.getFornecedorcidade().getCidade();
			listarFornecedorCidade();
			fornecedorcidade = valoresseguro.getFornecedorcidade();  
			listarPlanosSeguro();
			seguroplanos = valoresseguro.getSeguroplanos();
			moedas = valoresseguro.getMoedas();
			produtos = valoresseguro.getProdutosorcamento();  
		}
	} 

	public Valoresseguro getValoresseguro() {
		return valoresseguro;
	}

	public void setValoresseguro(Valoresseguro valoresseguro) {
		this.valoresseguro = valoresseguro;
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

	public List<Seguroplanos> getListaSeguroPlanos() {
		return listaSeguroPlanos;
	}


	public void setListaSeguroPlanos(List<Seguroplanos> listaSeguroPlanos) {
		this.listaSeguroPlanos = listaSeguroPlanos;
	}


	public Seguroplanos getSeguroplanos() {
		return seguroplanos;
	}


	public void setSeguroplanos(Seguroplanos seguroplanos) {
		this.seguroplanos = seguroplanos;
	}


	public void listarFornecedorCidade() {
		int idProduto = aplicacaoMB.getParametrosprodutos().getSeguroPrivado();
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
		if(seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			valoresseguro.setSituacao("Ativo");
			valoresseguro.setSeguroplanos(seguroplanos);
			valoresseguro.setFornecedorcidade(fornecedorcidade);
			valoresseguro.setMoedas(moedas);
			valoresseguro.setProdutosorcamento(produtos);
			ValorSeguroFacade ValorSeguroFacade = new ValorSeguroFacade();
			valoresseguro=ValorSeguroFacade.salvar(valoresseguro);
			RequestContext.getCurrentInstance().closeDialog(valoresseguro);
		}
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void listarPlanosSeguro() {
		if (fornecedorcidade != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}
}
