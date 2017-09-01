package br.com.travelmate.managerBean.financeiro.invoices;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.model.Invoice;

@Named
@ViewScoped
public class InvoiceObservacaoMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Invoice invoice;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        invoice = (Invoice) session.getAttribute("invoice");
        session.removeAttribute("invoice");
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public String salvar(){
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		invoiceFacade.salvar(invoice);
		RequestContext.getCurrentInstance().closeDialog(invoice);
		return "";
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	
	

}
