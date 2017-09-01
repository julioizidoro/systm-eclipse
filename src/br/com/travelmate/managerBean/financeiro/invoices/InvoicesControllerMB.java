package br.com.travelmate.managerBean.financeiro.invoices;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class InvoicesControllerMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Invoice invoice;
	private String dataPrevista;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        invoice = (Invoice) session.getAttribute("invoice");
        session.removeAttribute("invoice");
        dataPrevista = "";
        if (invoice==null){
        	invoice = new Invoice();
        }else {
        	dataPrevista = Formatacao.ConvercaoDataPadrao(invoice.getDataPrevistaPagamento());
        }
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public String salvar(){
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		invoice = invoiceFacade.salvar(invoice);
		String dataInvoice = Formatacao.ConvercaoDataPadrao(invoice.getDataPrevistaPagamento());
		if (!dataInvoice.equalsIgnoreCase(dataPrevista)){
			VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
			Vendascomissao vendascomissao = vendasComissaoFacade.consultar(invoice.getVendas().getIdvendas());
			if (vendascomissao!=null){
				vendascomissao.setPrevisaopagamento(invoice.getDataPrevistaPagamento());
				vendasComissaoFacade.salvar(vendascomissao);
			}
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
