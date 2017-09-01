package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Invoice;

@Named
@ViewScoped
public class ConsultaInvoiceCursoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Invoice> listaInvoices;
	private String corValorNet;
	private Invoice invoice;
	private int idVendas;
	private Controlecurso controle;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controle = (Controlecurso) session.getAttribute("controle");
        session.removeAttribute("controle");
        idVendas = controle.getVendas().getIdvendas();
		gerarListaInvoices();
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Invoice> getListaInvoices() {
		return listaInvoices;
	}

	public void setListaInvoices(List<Invoice> listaInvoices) {
		this.listaInvoices = listaInvoices;
	}
	
	
	public String getCorValorNet() {
		return corValorNet;
	}

	public void setCorValorNet(String corValorNet) {
		this.corValorNet = corValorNet;
	}
	
	

	public int getIdVendas() {
		return idVendas;
	}

	public void setIdVendas(int idVendas) {
		this.idVendas = idVendas;
	}


	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
	public void gerarListaInvoices(){
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		String sql = "Select i from Invoice i where i.vendas.idvendas="+ idVendas;
		listaInvoices = invoiceFacade.listar(sql);
		if (listaInvoices==null){
			listaInvoices = new ArrayList<Invoice>();
		}
	}
	
	public Float retornarValorNet(Invoice invoice){
		if (invoice.getValorPagamentoInvoice()>0){
			corValorNet = "width:80px;text-align:right";
			return invoice.getValorPagamentoInvoice();
		}else {
			corValorNet = "width:80px;text-align:right;color:red";
			if(invoice.getVendas().getVendascomissao()!=null){
				return invoice.getVendas().getVendascomissao().getValorfornecedor();
			}else return 0.0f;
		}
	}
	
	
	
	public String novo() {
		Invoice invoice = new Invoice();
		invoice = new Invoice();
		invoice.setControle(controle.getIdcontroleCursos());
		invoice.setProdutos(controle.getVendas().getProdutos());
		invoice.setVendas(controle.getVendas());
		FacesContext fc = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	    session.setAttribute("invoice", invoice);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadInvoice", options, null);
		return "";
	}
	
	
	 public String editar(Invoice invoice){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("invoice", invoice);
        Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadInvoice", options, null);
		return "";
    }
	 
	 
	public String voltar(){
		return "consControleCursos";
	}
	
}
