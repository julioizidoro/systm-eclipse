package br.com.travelmate.managerBean.highschool.valoresHighSchool;

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
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ValoresHighSchoolFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadValoresHighSchoolMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valoreshighschool valoresHighSchool;
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
		valoresHighSchool = (Valoreshighschool) session.getAttribute("valor");
		session.removeAttribute("valor");
		int idProduto = 0;
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		idProduto = aplicacaoMB.getParametrosprodutos().getHighSchool();
		listaPais = paisProdutoFacade.listar(idProduto);
		carregarComboMoedas();
		carregarListaProdutos();
		if (valoresHighSchool==null){
			valoresHighSchool = new Valoreshighschool();
		}else {
			pais = valoresHighSchool.getFornecedorcidade().getCidade().getPais();
			fornecedorcidade = valoresHighSchool.getFornecedorcidade();
			cidade = valoresHighSchool.getFornecedorcidade().getCidade();
			moedas = valoresHighSchool.getMoedas();
			moedas1 = valoresHighSchool.getMoedas1();
			produtos = valoresHighSchool.getProdutosorcamento();
			listarFornecedorCidade();
		}
	}


	public Valoreshighschool getValoresHighSchool() {
		return valoresHighSchool;
	}

	public void setValoresHighSchool(Valoreshighschool valoresHighSchool) {
		this.valoresHighSchool = valoresHighSchool;
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
		int idProduto = aplicacaoMB.getParametrosprodutos().getHighSchool();
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
		if(valoresHighSchool.getValornet()!=null && valoresHighSchool.getValornet()>0){
			if(valoresHighSchool.getValorgross()!=null && valoresHighSchool.getValorgross()>0){
				if(valoresHighSchool.getDatavalidade()!=null){
					valoresHighSchool.setSituacao("Ativo");
					valoresHighSchool.setFornecedorcidade(fornecedorcidade);
					valoresHighSchool.setMoedas(moedas);
					valoresHighSchool.setMoedas1(moedas1);
					ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
					Produtosorcamento produtosorcamento = produtoOrcamentoFacade.consultar(12);
					valoresHighSchool.setProdutosorcamento(produtosorcamento);
					valoresHighSchool.setFornecedor(fornecedorcidade.getFornecedor());
					ValoresHighSchoolFacade valoresHighSchoolFacade = new ValoresHighSchoolFacade();
					valoresHighSchool=valoresHighSchoolFacade.salvar(valoresHighSchool);
					RequestContext.getCurrentInstance().closeDialog(valoresHighSchool);
				}else Mensagem.lancarMensagemErro("Atenção!", "Campos obrigatórios não preenchidos");
			}else Mensagem.lancarMensagemErro("Atenção!", "Campos obrigatórios não preenchidos");
		} else Mensagem.lancarMensagemErro("Atenção!", "Campos obrigatórios não preenchidos");
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
