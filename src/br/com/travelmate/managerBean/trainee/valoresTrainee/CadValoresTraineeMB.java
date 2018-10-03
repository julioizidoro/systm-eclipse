package br.com.travelmate.managerBean.trainee.valoresTrainee;

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

import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ValoresTraineeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valorestrainee;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class CadValoresTraineeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private CambioDao cambioDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valorestrainee valorestrainee;
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
        valorestrainee = (Valorestrainee) session.getAttribute("valor");
		session.removeAttribute("valor");
		int idProduto = 0;
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		idProduto = aplicacaoMB.getParametrosprodutos().getTrainee();
		listaPais = paisProdutoFacade.listar(idProduto);
		carregarComboMoedas();
		carregarListaProdutos();
		if (valorestrainee==null){
			valorestrainee = new Valorestrainee();
		}else {
			pais = valorestrainee.getFornecedorcidade().getCidade().getPais();
			fornecedorcidade = valorestrainee.getFornecedorcidade();
			cidade = valorestrainee.getFornecedorcidade().getCidade();
			moedas = valorestrainee.getMoedas();
			moedas1 = valorestrainee.getMoedas1();
			produtos = valorestrainee.getProdutosorcamento();
			listarFornecedorCidade();
		}
	}

	
	public Valorestrainee getValorestrainee() {
		return valorestrainee;
	}

	public void setValorestrainee(Valorestrainee valorestrainee) {
		this.valorestrainee = valorestrainee;
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
		int idProduto = aplicacaoMB.getParametrosprodutos().getTrainee();
		if ((idProduto > 0) && (cidade != null)) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
		}
	}

	public void carregarComboMoedas() {
		listaMoedas = cambioDao.listaMoedas();
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
		valorestrainee.setSituacao("Ativo");
		valorestrainee.setFornecedorcidade(fornecedorcidade);
		valorestrainee.setMoedas(moedas);
		valorestrainee.setProdutosorcamento(produtos);
		valorestrainee.setMoedas1(moedas1);
		ValoresTraineeFacade valoresTraineeFacade = new ValoresTraineeFacade();
		valorestrainee=valoresTraineeFacade.salvar(valorestrainee);
		if(valorestrainee.getTipotrainee().equalsIgnoreCase("Australia")){
			FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("valorestrainee", valorestrainee);
			return "cadProdutosTrainee";
		}else{
			RequestContext.getCurrentInstance().closeDialog(valorestrainee);
			return "";
		}
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
