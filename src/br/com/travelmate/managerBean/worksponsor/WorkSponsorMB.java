package br.com.travelmate.managerBean.worksponsor;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.WorkSponsorFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Worksponsor;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class WorkSponsorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Paisproduto> listaPais;
	private Paisproduto pais;
	private Cidadepaisproduto cidadeproduto;
	private List<Cidadepaisproduto> listaCidade;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<Worksponsor> listaSponsor;

	@PostConstruct
	public void init() {
		gerarListaPais();
		gerarListaSponsor();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public List<Worksponsor> getListaSponsor() {
		return listaSponsor;
	}

	public void setListaSponsor(List<Worksponsor> listaSponsor) {
		this.listaSponsor = listaSponsor;
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
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(aplicacaoMB.getParametrosprodutos().getWork(),
					cidadeproduto.getCidade().getIdcidade());
		}
	}

	public void gerarListaSponsor() {
		WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
		listaSponsor = workSponsorFacade
				.listar("SELECT w FROM Worksponsor w ORDER BY w.fornecedorcidade.cidade.pais.nome,"
						+ " w.fornecedorcidade.cidade.nome, w.fornecedorcidade.fornecedor.nome");
	}

	public String novo() {
		return "cadWorkSponsor";
	}

	public String editar(Worksponsor worksponsor) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("worksponsor", worksponsor);
		return "cadWorkSponsor";
	}

	public void pesquisar() {
		WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
		String sql = "SELECT w FROM Worksponsor w";
		if (fornecedorCidade != null && fornecedorCidade.getIdfornecedorcidade() != null) {
			sql = sql + " WHERE w.fornecedorcidade.idfornecedorcidade=" + fornecedorCidade.getIdfornecedorcidade();
		} else if (cidadeproduto != null && cidadeproduto.getCidade().getIdcidade() != null) {
			sql = sql + " WHERE w.fornecedorcidade.cidade.idcidade=" + cidadeproduto.getCidade().getIdcidade();
		} else if (pais != null && pais.getPais().getIdpais() != null) {
			sql = sql + " WHERE w.fornecedorcidade.cidade.pais.idpais=" + pais.getPais().getIdpais();
		}
		sql = sql + " ORDER BY w.fornecedorcidade.cidade.pais.nome, w.fornecedorcidade.cidade.nome,"
				+ " w.fornecedorcidade.fornecedor.nome";
		listaSponsor = workSponsorFacade.listar(sql);
	}

	public void limpar() {
		pais = new Paisproduto();
		cidadeproduto = new Cidadepaisproduto();
		fornecedorCidade = new Fornecedorcidade();
		gerarListaSponsor();
	}

	public void gerarSponsor() {
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		String sql = "SELECT f FROM Fornecedorcidade f WHERE f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getWork() + " and f.ativo=TRUE";
		List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
		Worksponsor worksponsor;
		WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
		for (int i = 0; i < lista.size(); i++) {
			worksponsor = new Worksponsor();
			worksponsor.setFornecedorcidade(lista.get(i));
			workSponsorFacade.salvar(worksponsor);
		}
		gerarListaSponsor();
	}

	public String consPais() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String voltar = "consWorkSponsor";
		session.setAttribute("voltar", voltar);
		return "consPais";
	}

}
