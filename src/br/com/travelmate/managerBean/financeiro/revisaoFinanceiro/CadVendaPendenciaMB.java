package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VendaMotivoPendenciaFacade;
import br.com.travelmate.facade.VendaPendenciaFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.model.Vendamotivopendencia;
import br.com.travelmate.model.Vendapendencia;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVendaPendenciaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vendas venda;
	private Vendapendencia vendapendencia;
	private Vendamotivopendencia vendamotivopendencia;
	private List<Vendamotivopendencia> listaVendaMotivoPencencia;
    private List<Vendas> listaVendaPendente;
    private List<Vendas> listaVendaNova;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		listaVendaPendente = (List<Vendas>) session.getAttribute("listaVendaPendente");
		listaVendaNova = (List<Vendas>) session.getAttribute("listaVendaNova");
		session.removeAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
		session.removeAttribute("venda");
		if (vendapendencia == null) {
			vendapendencia = new Vendapendencia();
		}
		gerarListaMotivoPendencia();
	}
	
	
	
	
	public Vendas getVenda() {
		return venda;
	}




	public void setVenda(Vendas venda) {
		this.venda = venda;
	}




	public Vendapendencia getVendapendencia() {
		return vendapendencia;
	}




	public void setVendapendencia(Vendapendencia vendapendencia) {
		this.vendapendencia = vendapendencia;
	}




	public Vendamotivopendencia getVendamotivopendencia() {
		return vendamotivopendencia;
	}




	public void setVendamotivopendencia(Vendamotivopendencia vendamotivopendencia) {
		this.vendamotivopendencia = vendamotivopendencia;
	}




	public List<Vendamotivopendencia> getListaVendaMotivoPencencia() {
		return listaVendaMotivoPencencia;
	}




	public void setListaVendaMotivoPencencia(List<Vendamotivopendencia> listaVendaMotivoPencencia) {
		this.listaVendaMotivoPencencia = listaVendaMotivoPencencia;
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




	public void gerarListaMotivoPendencia(){
		VendaMotivoPendenciaFacade vendaMotivoPendenciaFacade = new VendaMotivoPendenciaFacade();
		listaVendaMotivoPencencia = vendaMotivoPendenciaFacade.lista("Select v From Vendamotivopendencia v");
		if (listaVendaMotivoPencencia == null) {
			listaVendaMotivoPencencia = new ArrayList<>();
		}
		
	}
	
	
	public void salvar(){
		VendaPendenciaFacade vendaPendenciaFacade = new VendaPendenciaFacade();
		VendasFacade vendasFacade = new VendasFacade();
		if (validarDados()) {
			vendapendencia.setVendamotivopendencia(vendamotivopendencia);
			vendapendencia.setVendas(venda);
			vendapendencia = vendaPendenciaFacade.salvar(vendapendencia);
			listaVendaNova.remove(venda);
			venda.setSituacaofinanceiro("P");
			venda = vendasFacade.salvar(venda);
			listaVendaPendente.add(venda);  
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaVendaNova", listaVendaNova);
			session.setAttribute("listaVendaPendente", listaVendaPendente);
			RequestContext.getCurrentInstance().closeDialog(vendapendencia);
		}
	}
	
	
	public boolean validarDados(){
		if (vendamotivopendencia == null) {
			Mensagem.lancarMensagemInfo("Informe o motivo da pendência \r\n", "");
			return false;
		}
		return true;
	}
	
	
	public void cancelar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaVendaNova", listaVendaNova);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		session.setAttribute("venda", venda);
		RequestContext.getCurrentInstance().closeDialog(new Vendapendencia());
	}

}
