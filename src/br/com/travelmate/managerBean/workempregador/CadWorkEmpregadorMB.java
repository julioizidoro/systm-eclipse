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
import br.com.travelmate.model.Cidadepaisproduto;  
import br.com.travelmate.model.Paisproduto; 
import br.com.travelmate.model.Workempregador;
import br.com.travelmate.model.Worksponsor; 
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadWorkEmpregadorMB implements Serializable{

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
    private List<Worksponsor> listaSponsor;
    private Worksponsor worksponsor;
    private Workempregador workempregador;
    
    @PostConstruct
    public void init() { 
    		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		workempregador = (Workempregador) session.getAttribute("workempregador");
		session.removeAttribute("workempregador");
		if(workempregador==null) {
			workempregador = new Workempregador();
			worksponsor = new Worksponsor(); 
			pais = new Paisproduto();
			cidadeproduto = new Cidadepaisproduto();
			gerarListaPais();
		}else { 
			pais = new Paisproduto();
			cidadeproduto = new Cidadepaisproduto();
			gerarListaPais(); 
			pais = workempregador.getCidadepaisproduto().getPaisproduto();
			listarCidade();
			cidadeproduto = workempregador.getCidadepaisproduto();
			listaSponsor();
			worksponsor = workempregador.getWorksponsor();
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
 
	public List<Worksponsor> getListaSponsor() {
		return listaSponsor;
	}

	public void setListaSponsor(List<Worksponsor> listaSponsor) {
		this.listaSponsor = listaSponsor;
	}

	public Workempregador getWorkempregador() {
		return workempregador;
	}

	public void setWorkempregador(Workempregador workempregador) {
		this.workempregador = workempregador;
	}

	public Worksponsor getWorksponsor() {
		return worksponsor;
	}

	public void setWorksponsor(Worksponsor worksponsor) {
		this.worksponsor = worksponsor;
	}
    
	public String cancelar() {
		return "consWorkEmpregador";
	}
	
	public String salvar() {
		if(worksponsor!=null && worksponsor.getIdworksponsor()!=null
				&& cidadeproduto!=null && cidadeproduto.getIdcidadepaisproduto()!=null) {
			WorkEmpregadorFacade workEmpregadorFacade = new WorkEmpregadorFacade();
			workempregador.setCidadepaisproduto(cidadeproduto);
			workempregador.setWorksponsor(worksponsor);
			workempregador = workEmpregadorFacade.salvar(workempregador);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			return "consWorkEmpregador";
		}
		return "";
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
		if(cidadeproduto!=null && cidadeproduto.getIdcidadepaisproduto()!=null) {
			WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
			listaSponsor = workSponsorFacade
					.listar("SELECT w FROM Worksponsor w WHERE w.fornecedorcidade.cidade.idcidade="+cidadeproduto.getCidade().getIdcidade()
							+ " ORDER BY w.fornecedorcidade.cidade.pais.nome,"
							+ " w.fornecedorcidade.cidade.nome, w.fornecedorcidade.fornecedor.nome");
		}
	}
}
