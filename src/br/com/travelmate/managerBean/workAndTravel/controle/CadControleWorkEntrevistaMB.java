package br.com.travelmate.managerBean.workAndTravel.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.ControleWorkEmpregadorFacade;
import br.com.travelmate.facade.ControleWorkEntrevistaFacade;
import br.com.travelmate.facade.ControleWorkSponsorFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.WorkEmpregadorFacade;
import br.com.travelmate.facade.WorkSponsorFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Controleworkempregaor;
import br.com.travelmate.model.Controleworkentrevista;
import br.com.travelmate.model.Controleworksponsor;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Workempregador;
import br.com.travelmate.model.Worksponsor;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadControleWorkEntrevistaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Controlework controlework;
	private Controleworkentrevista controleworkentrevista;
	private Worksponsor worksponsor;
	private List<Worksponsor> listaWorkSponsor;
	private Workempregador workempregador;
	private List<Workempregador> listaWorkEmpregador;
	private Paisproduto paisproduto;
	private List<Paisproduto> listaPais;
	private Cidadepaisproduto cidadepaisproduto;
	private List<Cidadepaisproduto> listaCidade;
	private String colocacao;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controlework = (Controlework) session.getAttribute("controlework");
        controleworkentrevista = (Controleworkentrevista) session.getAttribute("controleworkentrevista");
        session.removeAttribute("controleworkentrevista"); 
        session.removeAttribute("controlework"); 
        listaPais = GerarListas.listarPaises(aplicacaoMB.getParametrosprodutos().getWork());
        if(controleworkentrevista==null) {
        	controleworkentrevista = new Controleworkentrevista();
        }else {
        	PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
        	paisproduto = paisProdutoFacade.consultar("SELECT p FROM Paisproduto p WHERE p.pais.idpais="
					+ controleworkentrevista.getWorksponsor().getFornecedorcidade().getCidade().getPais().getIdpais()
					+ " and p.produtos.idprodutos=" + aplicacaoMB.getParametrosprodutos().getWork());
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			listaCidade = GerarListas.listarCidade(paisproduto);
			cidadepaisproduto = cidadePaisProdutosFacade
					.consultar("SELECT c FROM Cidadepaisproduto c WHERE c.cidade.idcidade="
							+ controleworkentrevista.getWorksponsor().getFornecedorcidade().getCidade().getIdcidade()
							+ " and c.paisproduto.produtos.idprodutos="
							+ aplicacaoMB.getParametrosprodutos().getWork());
			listaSponsor();
			worksponsor = controleworkentrevista.getWorksponsor();
			listarEmpregador();
			workempregador = controleworkentrevista.getWorkempregador();
        } 
	}

	public Controlework getControlework() {
		return controlework;
	}

	public void setControlework(Controlework controlework) {
		this.controlework = controlework;
	} 
	
	public Controleworkentrevista getControleworkentrevista() {
		return controleworkentrevista;
	}

	public void setControleworkentrevista(Controleworkentrevista controleworkentrevista) {
		this.controleworkentrevista = controleworkentrevista;
	}
 
	public Worksponsor getWorksponsor() {
		return worksponsor;
	}

	public void setWorksponsor(Worksponsor worksponsor) {
		this.worksponsor = worksponsor;
	}

	public List<Worksponsor> getListaWorkSponsor() {
		return listaWorkSponsor;
	}

	public void setListaWorkSponsor(List<Worksponsor> listaWorkSponsor) {
		this.listaWorkSponsor = listaWorkSponsor;
	}

	public Workempregador getWorkempregador() {
		return workempregador;
	}

	public void setWorkempregador(Workempregador workempregador) {
		this.workempregador = workempregador;
	}

	public List<Workempregador> getListaWorkEmpregador() {
		return listaWorkEmpregador;
	}

	public void setListaWorkEmpregador(List<Workempregador> listaWorkEmpregador) {
		this.listaWorkEmpregador = listaWorkEmpregador;
	}
	 
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Paisproduto getPaisproduto() {
		return paisproduto;
	}

	public void setPaisproduto(Paisproduto paisproduto) {
		this.paisproduto = paisproduto;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidadepaisproduto getCidadepaisproduto() {
		return cidadepaisproduto;
	}

	public void setCidadepaisproduto(Cidadepaisproduto cidadepaisproduto) {
		this.cidadepaisproduto = cidadepaisproduto;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String getColocacao() {
		return colocacao;
	}

	public void setColocacao(String colocacao) {
		this.colocacao = colocacao;
	}

	public void listaSponsor() {
		if(cidadepaisproduto!=null && cidadepaisproduto.getIdcidadepaisproduto()!=null) {
			WorkSponsorFacade workSponsorFacade = new WorkSponsorFacade();
			listaWorkSponsor = workSponsorFacade
					.listar("SELECT w FROM Worksponsor w WHERE w.fornecedorcidade.cidade.idcidade="+cidadepaisproduto.getCidade().getIdcidade()
							+ " ORDER BY w.fornecedorcidade.cidade.pais.nome,"
							+ " w.fornecedorcidade.cidade.nome, w.fornecedorcidade.fornecedor.nome");
		}
	}
	
	public void listarEmpregador() {
		WorkEmpregadorFacade workEmpregadorFacade = new WorkEmpregadorFacade();
		String sql = "SELECT w FROM Workempregador w"
		+ " ORDER BY w.cidadepaisproduto.paisproduto.pais.nome, w.cidadepaisproduto.cidade.nome,"
				+ " w.worksponsor.fornecedorcidade.fornecedor.nome";
		listaWorkEmpregador = workEmpregadorFacade.listar(sql);
	}
	
	public String salvar() {
		if(worksponsor!=null && worksponsor.getIdworksponsor()!=null &&
				workempregador!=null && workempregador.getIdworkempregador()!=null) { 
			ControleWorkEntrevistaFacade controleWorkEntrevistaFacade = new ControleWorkEntrevistaFacade();
			controleworkentrevista.setControlework(controlework);
			controleworkentrevista.setWorkempregador(workempregador);
			controleworkentrevista.setWorksponsor(worksponsor);
			controleworkentrevista = controleWorkEntrevistaFacade.salvar(controleworkentrevista);
			WorkTravelFacade workTravelFacade = new WorkTravelFacade();
			if(controleworkentrevista.getSituacaoempreagador().equalsIgnoreCase("Contratado")
					&& controleworkentrevista.getSituacaosponsor().equalsIgnoreCase("Contratado")) {
				controlework.setStatusprocesso("Contratado"); 
				ControleWorkEmpregadorFacade controleWorkEmpregadorFacade = new ControleWorkEmpregadorFacade();
				Controleworkempregaor controleworkempregaor = new Controleworkempregaor();
				controleworkempregaor.setControlework(controlework);
				controleworkempregaor.setWorkempregador(workempregador);
				controleworkempregaor.setColocacao(colocacao);
				controleWorkEmpregadorFacade.salvar(controleworkempregaor);
				ControleWorkSponsorFacade controleWorkSponsorFacade = new ControleWorkSponsorFacade();
				Controleworksponsor controleworksponsor = new Controleworksponsor();
				controleworksponsor.setWorksponsor(worksponsor);
				controleworksponsor.setControlework(controlework);
				controleWorkSponsorFacade.salvar(controleworksponsor);
			}else {
				controlework.setStatusprocesso("Entrevista");
			}
			controlework = workTravelFacade.salvar(controlework);
			Mensagem.lancarMensagemInfo("Salvo com Sucesso!", "");
			RequestContext.getCurrentInstance().closeDialog(null);
		}else {
			Mensagem.lancarMensagemErro("Atenção!", "Informações obrigatórias não preenchidas.");
		}
		return "";
	}
	
	public void listarCidade() {
		if(paisproduto!=null && paisproduto.getIdpaisproduto()!=null) {
			listaCidade = GerarListas.listarCidade(paisproduto);
		}
	}
 
}
