package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.LancamentoCartaoCreditoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Lancamentocartaocredito;
import br.com.travelmate.model.Vendas;

@Named
@ViewScoped
public class LancamentoCartaoCreditoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Vendas vendas;
    private List<Vendas> listaVendaPendente;
    private List<Vendas> listaVendaNova;
    private Lancamentocartaocredito lancamentocartaocredito;
    

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init(){
    	LancamentoCartaoCreditoFacade lancamentoCartaoCreditoFacade = new LancamentoCartaoCreditoFacade();
    	FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vendas = (Vendas) session.getAttribute("venda");
		listaVendaPendente = (List<Vendas>) session.getAttribute("listaVendaPendente");
		listaVendaNova = (List<Vendas>) session.getAttribute("listaVendaNova");
		session.removeAttribute("venda");
		session.removeAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
		lancamentocartaocredito = lancamentoCartaoCreditoFacade.consultarVenda(vendas.getIdvendas());
		if (lancamentocartaocredito == null) {
			lancamentocartaocredito = new Lancamentocartaocredito();
			lancamentocartaocredito.setDatainclusao(new Date());
			lancamentocartaocredito.setVendas(vendas);
			lancamentocartaocredito.setUsuario(usuarioLogadoMB.getUsuario());
			lancamentocartaocredito.setLancado(false);
		}
    }


	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}


	public List<Vendas> getListaVendaPendente() {
		return listaVendaPendente;
	}


	public void setListaVendaPendente(List<Vendas> listaVendaPendente) {
		this.listaVendaPendente = listaVendaPendente;
	}


	public List<Vendas> getListaVendaNova() {
		return listaVendaNova;
	}


	public void setListaVendaNova(List<Vendas> listaVendaNova) {
		this.listaVendaNova = listaVendaNova;
	}


	public Lancamentocartaocredito getLancamentocartaocredito() {
		return lancamentocartaocredito;
	}


	public void setLancamentocartaocredito(Lancamentocartaocredito lancamentocartaocredito) {
		this.lancamentocartaocredito = lancamentocartaocredito;
	}
    
	
	
	public void salvar(){
		LancamentoCartaoCreditoFacade lancamentoCartaoCreditoFacade = new LancamentoCartaoCreditoFacade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", vendas);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		session.setAttribute("listaVendaNova", listaVendaNova);
		lancamentocartaocredito = lancamentoCartaoCreditoFacade.salvar(lancamentocartaocredito);
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void cancelar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", vendas);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		session.setAttribute("listaVendaNova", listaVendaNova);
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
    
	

}
