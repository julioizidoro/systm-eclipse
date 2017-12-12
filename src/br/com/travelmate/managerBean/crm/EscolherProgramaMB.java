package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class EscolherProgramaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Produtos produto;
	private List<Produtos> listaProdutos;
	private Lead lead;
	private String tipo;
	private  boolean renderizarPos = false;
	private boolean renderizarTrainee = true;
	private String tipoVenda = "";
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		lead = (Lead) session.getAttribute("lead");
		tipoVenda = (String) session.getAttribute("tipoVenda");
		session.removeAttribute("lead");
		session.removeAttribute("tipoVenda");
		listaProdutos = GerarListas.listarProdutos("");
		if (tipoVenda.equalsIgnoreCase("trainee")) {
			renderizarTrainee = true;
			renderizarPos = false;
		}else{
			renderizarPos = true;
			renderizarTrainee = false;
		}
	}



	public Produtos getProduto() {
		return produto;
	}



	public void setProduto(Produtos produto) {
		this.produto = produto;
	}



	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}



	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	
	
	public Lead getLead() {
		return lead;
	}



	public void setLead(Lead lead) {
		this.lead = lead;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public boolean isRenderizarPos() {
		return renderizarPos;
	}



	public void setRenderizarPos(boolean renderizarPos) {
		this.renderizarPos = renderizarPos;
	}





	public boolean isRenderizarTrainee() {
		return renderizarTrainee;
	}



	public void setRenderizarTrainee(boolean renderizarTrainee) {
		this.renderizarTrainee = renderizarTrainee;
	}



	public String getTipoVenda() {
		return tipoVenda;
	}



	public void setTipoVenda(String tipoVenda) {
		this.tipoVenda = tipoVenda;
	}



	public String emitirVendaPos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		int idprodutos = lead.getProdutos().getIdprodutos();
		if (idprodutos != 13) {
			session.setAttribute("cliente", lead.getCliente());
			session.setAttribute("lead", lead); 
			if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getAupair()) {
				return "cadAuPair";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getCursos()) {
				return "cadFichaCurso";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getDemipair()) {
				return "cadDemiPair";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
				return "cadCursosTeens";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
				return "cadHighSchool";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getTrainee()) {
				session.setAttribute("tipo", tipo);
				return "cadTrainee";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
				return "cadVoluntariado";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getWork()) {
				return "cadWorkandTravel";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPacotes()) {
				return "cadpacote";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPassagem()) {
				return "cadPassagem";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVisto()) {
				return "cadVistos";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getSeguroPrivado()) {
				return "fichaSeguroViagem";
			}else if(produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighereducation()){
				return "questionarioHe";
			}
		}
		return "";
	} 
	
	
	public void selecionarLead(Lead lead){
		this.lead = lead;
	}
	
	
	public void emitirVendaTrainee(String tipo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		session.setAttribute("tipo", tipo);
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	

}
