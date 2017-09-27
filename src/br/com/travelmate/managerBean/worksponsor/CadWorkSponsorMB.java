package br.com.travelmate.managerBean.worksponsor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.ProdutosTraineeFacade;
import br.com.travelmate.facade.WorkSponsorFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Worksponsor;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadWorkSponsorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Paisproduto> listaPais;
	private Paisproduto pais;
	private Cidadepaisproduto cidadeproduto;
	private List<Cidadepaisproduto> listaCidade;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Worksponsor worksponsor;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		worksponsor = (Worksponsor) session.getAttribute("worksponsor");
		session.removeAttribute("worksponsor");
		if (worksponsor == null) {
			worksponsor = new Worksponsor();
			fornecedorCidade = new Fornecedorcidade();
			pais = new Paisproduto();
			cidadeproduto = new Cidadepaisproduto();
			gerarListaPais();
		} else {
			pais = new Paisproduto();
			cidadeproduto = new Cidadepaisproduto();
			gerarListaPais();
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			pais = paisProdutoFacade.consultar("SELECT p FROM Paisproduto p WHERE p.pais.idpais="
					+ worksponsor.getFornecedorcidade().getCidade().getPais().getIdpais()
					+ " and p.produtos.idprodutos=" + aplicacaoMB.getParametrosprodutos().getWork());
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			listarCidade();
			cidadeproduto = cidadePaisProdutosFacade
					.consultar("SELECT c FROM Cidadepaisproduto c WHERE c.cidade.idcidade="
							+ worksponsor.getFornecedorcidade().getCidade().getIdcidade()
							+ " and c.paisproduto.produtos.idprodutos="
							+ aplicacaoMB.getParametrosprodutos().getWork());
			listarFornecedorCidade();
			fornecedorCidade = worksponsor.getFornecedorcidade();
		}
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Paisproduto getPais() {
		return pais;
	}

	public void setPais(Paisproduto pais) {
		this.pais = pais;
	}

	public Cidadepaisproduto getCidadeproduto() {
		return cidadeproduto;
	}

	public void setCidadeproduto(Cidadepaisproduto cidadeproduto) {
		this.cidadeproduto = cidadeproduto;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Worksponsor getWorksponsor() {
		return worksponsor;
	}

	public void setWorksponsor(Worksponsor worksponsor) {
		this.worksponsor = worksponsor;
	}

	public String cancelar() {
		return "consWorkSponsor";
	}

	public String salvar() {
		if (fornecedorCidade != null) {
			WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
			worksponsor.setFornecedorcidade(fornecedorCidade);
			worksponsor = workSponsorFacade.salvar(worksponsor);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			return "consWorkSponsor";
		}
		return "";
	}

	public String consFornecedor() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (cidadeproduto != null) {
			ProdutoFacade produtoFacade = new ProdutoFacade();
			Produtos produto = produtoFacade.consultar(aplicacaoMB.getParametrosprodutos().getWork());
			Fornecedorcidade fornecedorcidade = new Fornecedorcidade();
			fornecedorcidade.setWork(false);
			session.setAttribute("fornecedorcidade", fornecedorcidade);
			session.setAttribute("produtos", produto);
			session.setAttribute("cidade", cidadeproduto.getCidade());
			RequestContext.getCurrentInstance().openDialog("consFornecedores", options, null);
			return "";
		} else {
			FacesMessage mensagem = new FacesMessage("Atenção! ", "campos obrigatorios não preenchidos.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return "";
		}
	}

	public void gerarListaPais() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		listaPais = paisProdutoFacade.listar(aplicacaoMB.getParametrosprodutos().getWork());
	}

	public void listarCidade() {
		if (pais != null) {
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			listaCidade = cidadePaisProdutosFacade.listar(
					"SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.idpaisproduto=" + pais.getIdpaisproduto());
		}
	}

	public void listarFornecedorCidade() {
		if (cidadeproduto != null) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidadeWork(
					aplicacaoMB.getParametrosprodutos().getWork(), cidadeproduto.getCidade().getIdcidade());
		}
	}
}
