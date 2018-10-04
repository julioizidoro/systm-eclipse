package br.com.travelmate.managerBean.workAndTravel.valoresWork;

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
import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;

import br.com.travelmate.facade.ValoresWorkFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoreswork;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class CadValoresWorkMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisDao paisDao;
	@Inject
	private CambioDao cambioDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valoreswork valoresWork;
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
	private String programa = "";
	

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        valoresWork = (Valoreswork) session.getAttribute("valor");
		session.removeAttribute("valor");
		
		listaPais = paisDao.listar();
		carregarComboMoedas();
		carregarListaProdutos();
		if (valoresWork==null){
			valoresWork =new Valoreswork();
		}else {
			pais = valoresWork.getFornecedorcidade().getCidade().getPais();
			fornecedorcidade = valoresWork.getFornecedorcidade();
			cidade = valoresWork.getFornecedorcidade().getCidade();
			moedas = valoresWork.getMoedas();
			moedas1 = valoresWork.getMoedas1();
			produtos = valoresWork.getProdutosorcamento();
			programa = valoresWork.getPrograma();
			listarFornecedorCidade();
		}
	}

	public Valoreswork getValoresWork() {
		return valoresWork;
	}

	public void setValoresWork(Valoreswork valoresWork) {
		this.valoresWork = valoresWork;
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
	
	

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public void listarFornecedorCidade() {
		int idProduto = aplicacaoMB.getParametrosprodutos().getWork();
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
		valoresWork.setPrograma(programa);
		valoresWork.setSituacao("Ativo");
		valoresWork.setFornecedorcidade(fornecedorcidade);
		valoresWork.setMoedas(moedas);
		valoresWork.setMoedas1(moedas1);
		valoresWork.setFornecedor(fornecedorcidade.getFornecedor());
		valoresWork.setProdutosorcamento(produtos);
		ValoresWorkFacade valoresWorkFacade = new ValoresWorkFacade();
		valoresWork=valoresWorkFacade.salvar(valoresWork);
		RequestContext.getCurrentInstance().closeDialog(valoresWork);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
