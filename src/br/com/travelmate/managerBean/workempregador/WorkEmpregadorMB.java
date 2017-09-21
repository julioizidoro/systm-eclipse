package br.com.travelmate.managerBean.workempregador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CidadePaisProdutosFacade; 
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.WorkEmpregadorFacade;
import br.com.travelmate.facade.WorkSponsorFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidadepaisproduto; 
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Workempregador;
import br.com.travelmate.model.Worksponsor; 

@Named
@ViewScoped
public class WorkEmpregadorMB implements Serializable {

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
	private Worksponsor worksponsor; 
	private List<Worksponsor> listaSponsor;
	private List<Workempregador> listaEmpregador;
	private String nome="";

	@PostConstruct
	public void init() {
		gerarListaPais();
		gerarListaEmpregador();
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

	public Worksponsor getWorksponsor() {
		return worksponsor;
	}

	public void setWorksponsor(Worksponsor worksponsor) {
		this.worksponsor = worksponsor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Workempregador> getListaEmpregador() {
		return listaEmpregador;
	}

	public void setListaEmpregador(List<Workempregador> listaEmpregador) {
		this.listaEmpregador = listaEmpregador;
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

	public void listaSponsor() {
		WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
		listaSponsor = workSponsorFacade
				.listar("SELECT w FROM Worksponsor w WHERE w.fornecedorcidade.cidade.idcidade="+cidadeproduto.getCidade().getIdcidade()
						+ " ORDER BY w.fornecedorcidade.cidade.pais.nome,"
						+ " w.fornecedorcidade.cidade.nome, w.fornecedorcidade.fornecedor.nome");
	}

	public String novo() {
		return "cadWorkEmpregador";
	}

	public String editar(Workempregador workempregador) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("workempregador", workempregador);
		return "cadWorkEmpregador";
	}

	public void pesquisar() {
		WorkEmpregadorFacade workEmpregadorFacade = new WorkEmpregadorFacade();
		String sql = "SELECT w FROM Workempregador w WHERE w.nome like '%"+nome+"%'";
		if (worksponsor != null && worksponsor.getIdworksponsor() != null) {
			sql = sql + " AND w.worksponsor.idworksponsor=" + worksponsor.getIdworksponsor();
		}
		if (cidadeproduto != null && cidadeproduto.getIdcidadepaisproduto() != null) {
			sql = sql + " AND w.cidadepaisproduto.idcidadepaisproduto=" + cidadeproduto.getIdcidadepaisproduto();
		} else if (pais != null && pais.getPais().getIdpais() != null) {
			sql = sql + " AND w.cidadepaisproduto.paisproduto.idpaisproduto=" + pais.getIdpaisproduto();
		}
		sql = sql + " ORDER BY w.cidadepaisproduto.paisproduto.pais.nome, w.cidadepaisproduto.cidade.nome,"
				+ " w.worksponsor.fornecedorcidade.fornecedor.nome";
		listaEmpregador = workEmpregadorFacade.listar(sql);
	}
	
	public void gerarListaEmpregador() {
		WorkEmpregadorFacade workEmpregadorFacade = new WorkEmpregadorFacade();
		String sql = "SELECT w FROM Workempregador w WHERE w.nome like '%"+nome+"%'"
		+ " ORDER BY w.cidadepaisproduto.paisproduto.pais.nome, w.cidadepaisproduto.cidade.nome,"
				+ " w.worksponsor.fornecedorcidade.fornecedor.nome";
		listaEmpregador = workEmpregadorFacade.listar(sql);
	}

	public void limpar() {
		pais = new Paisproduto();
		cidadeproduto = new Cidadepaisproduto();
		worksponsor = new Worksponsor();
		nome = "";
		gerarListaEmpregador();
	}  
	
	public String imagemPickup(Workempregador workempregador) {
		if(workempregador.isOferecepickup()) {
			return "bolaVerde.png";
		}else return "bolaVermelha.png";
	}
}
