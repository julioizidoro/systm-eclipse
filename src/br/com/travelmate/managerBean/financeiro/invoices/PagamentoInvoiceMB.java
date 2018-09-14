package br.com.travelmate.managerBean.financeiro.invoices;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.PagamentoInvoiceFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Pagamentoinvoice;

@Named
@ViewScoped
public class PagamentoInvoiceMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Invoice> listaInvoice;
	private Pagamentoinvoice pagamentoinvoice;
	private Float valorTotalSelecionado;
	private Float valorTotal;
	private boolean outroBanco;
	private boolean pagtoParcial;
	private String sql;
	
	
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaInvoice = (List<Invoice>) session.getAttribute("listaInvoice");
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("listaInvoice");
        session.removeAttribute("sql");
        outroBanco = true;
        if (listaInvoice!=null){
        	pagamentoinvoice = new Pagamentoinvoice();
        	pagamentoinvoice.setTipopagamento("total");
        	pagamentoinvoice.setDatapagamento(new Date());
        	valorTotal=0.0f;
        	Float totalPagar=0.0f;
        	for (int i = 0; i < listaInvoice.size(); i++) {
				listaInvoice.get(i).setValorPago(listaInvoice.get(i).getValorPagamentoInvoice()-listaInvoice.get(i).getValorcredito());
				valorTotal+= listaInvoice.get(i).getValorPagamentoInvoice();
				totalPagar = totalPagar + (listaInvoice.get(i).getValorPagamentoInvoice()-listaInvoice.get(i).getValorcredito());
			}
        	pagamentoinvoice.setValorpago(totalPagar);
        	pagamentoinvoice.setTaxa(0.0f);
        	pagamentoinvoice.setCambiopagamento(0.0f);
        	pagamentoinvoice.setIof(0.0f);
        	pagamentoinvoice.setGanhoperda(0.0f); 
        }
        pagtoParcial=true;
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Invoice> getListaInvoice() {
		return listaInvoice;
	}


	public void setListaInvoice(List<Invoice> listaInvoice) {
		this.listaInvoice = listaInvoice;
	}
	
	public boolean isOutroBanco() {
		return outroBanco;
	}


	public void setOutroBanco(boolean outroBanco) {
		this.outroBanco = outroBanco;
	}


	public Pagamentoinvoice getPagamentoinvoice() {
		return pagamentoinvoice;
	}


	public void setPagamentoinvoice(Pagamentoinvoice pagamentoinvoice) {
		this.pagamentoinvoice = pagamentoinvoice;
	}


	public Float getValorTotalSelecionado() {
		return valorTotalSelecionado;
	}


	public void setValorTotalSelecionado(Float valorTotalSelecionado) {
		this.valorTotalSelecionado = valorTotalSelecionado;
	}


	public Float getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	

	public boolean isPagtoParcial() {
		return pagtoParcial;
	}


	public void setPagtoParcial(boolean pagtoParcial) {
		this.pagtoParcial = pagtoParcial;
	}
	
	public String validar(){
		String msg = "";
		if (pagamentoinvoice.getCambiopagamento()<=0){
			msg = msg + "Valor do câmbio não informado";
		}
		return msg;
	}


	public String validarInvoice() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		String msg = validar();
		if (msg.length() > 5) {
			context.addMessage(null, new FacesMessage(msg, ""));
		} else {
			if (pagamentoinvoice.getTipopagamento().equalsIgnoreCase("total")) {
				valorTotalSelecionado = pagamentoinvoice.getValorpago();
				if (valorTotal < valorTotalSelecionado) {
					context.addMessage(null, new FacesMessage("Valor total não confere", ""));
				} else {
					salvarPagamentoTotal();
					session.setAttribute("sql", sql);
					return "consultainvoice";
				}
			} else if (pagamentoinvoice.getTipopagamento().equalsIgnoreCase("parcial")) {
				if (valorTotal > valorTotalSelecionado) {
					salvarPagamentoParcial();
					session.setAttribute("sql", sql);
					return "consultainvoice";
				} else {
					context.addMessage(null, new FacesMessage("Valor total menor ou igual a valor pago", ""));
				}
			}
		}
		return "";
	}
	
	public String salvarPagamentoTotal(){
		PagamentoInvoiceFacade pagamentoInvoiceFacade = new PagamentoInvoiceFacade();
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		for (int i = 0; i < listaInvoice.size(); i++) {
			Pagamentoinvoice pi = new Pagamentoinvoice();
			pi.setBanco(pagamentoinvoice.getBanco());
			pi.setCambiopagamento(pagamentoinvoice.getCambiopagamento());
			pi.setDatacomprovante(pagamentoinvoice.getDatacomprovante());
			pi.setDatapagamento(pagamentoinvoice.getDatapagamento());
		
			pi.setInvoice(listaInvoice.get(i));
			pi.setOutros(pagamentoinvoice.getOutros());
			pi.setTaxa(pagamentoinvoice.getTaxa());
			pi.setIof(pagamentoinvoice.getIof());
			pi.setValorpago(listaInvoice.get(i).getValorPago());
			pi.setTipopagamento(pagamentoinvoice.getTipopagamento());
			pi.setValorpago(listaInvoice.get(i).getValorPago());
			pi.setGanhoperda(pagamentoinvoice.getGanhoperda()); 
			listaInvoice.get(i).setDatarecebimentocomprovante(pagamentoinvoice.getDatacomprovante());
			invoiceFacade.salvar(listaInvoice.get(i));
			pagamentoInvoiceFacade.salvar(pi);
		}
		return null;
	}
	
	public String salvarPagamentoParcial(){
		PagamentoInvoiceFacade pagamentoInvoiceFacade = new PagamentoInvoiceFacade();
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		for (int i = 0; i < listaInvoice.size(); i++) {
			Pagamentoinvoice pi = new Pagamentoinvoice();
			pi.setBanco(pagamentoinvoice.getBanco());
			pi.setCambiopagamento(pagamentoinvoice.getCambiopagamento());
			pi.setDatacomprovante(pagamentoinvoice.getDatacomprovante());
			pi.setDatapagamento(pagamentoinvoice.getDatapagamento());
			
			pi.setInvoice(listaInvoice.get(i));
			pi.setOutros(pagamentoinvoice.getOutros());
			pi.setTaxa(pagamentoinvoice.getTaxa());
			pi.setIof(pagamentoinvoice.getIof());
			pi.setValorpago(listaInvoice.get(i).getValorPago()-valorTotalSelecionado);
			listaInvoice.get(i).setValorPagamentoInvoice(listaInvoice.get(i).getValorPagamentoInvoice() - listaInvoice.get(i).getValorPago());
			listaInvoice.get(i).setValorPago(0.0f);
			pi.setTipopagamento(pagamentoinvoice.getTipopagamento());
			listaInvoice.get(i).setDataPagamentoInvoice(pagamentoinvoice.getDatapagamento());
			pi.setGanhoperda(pagamentoinvoice.getGanhoperda());
			invoiceFacade.salvar(listaInvoice.get(i));
			pagamentoInvoiceFacade.salvar(pi);			
		}
		return null;
	}
	
	
	public Float calcularPerdaGanhoCambial(Invoice invoice) {
		float valorFinal = 0.0f;
		float valorPagoVenda = invoice.getValorPago() * invoice.getVendas().getCambio().getValor();
		Float valorPagoPagamento = invoice.getValorPago() * pagamentoinvoice.getCambiopagamento();  
		valorFinal = valorPagoVenda - valorPagoPagamento; 
		valorFinal = valorFinal - (pagamentoinvoice.getIof()+pagamentoinvoice.getTaxa());
		return valorFinal;
	}
		
	public void calcularValoresPagamento(){
		if (pagamentoinvoice.getCambiopagamento()>0){
			Float valorVenda = pagamentoinvoice.getValorpago() * listaInvoice.get(0).getVendas().getCambio().getValor();
			Float valorPago = pagamentoinvoice.getValorpago() * pagamentoinvoice.getCambiopagamento();
			float valorFinal = valorVenda - valorPago;
			valorFinal = valorFinal - (pagamentoinvoice.getTaxa()+pagamentoinvoice.getIof()); 
			pagamentoinvoice.setGanhoperda(valorFinal);
			valorTotalSelecionado =pagamentoinvoice.getValorpago();
		}
	}
	
	public void selecionarparcial(){
		if (pagamentoinvoice.getTipopagamento().equalsIgnoreCase("parcial")){
			pagtoParcial=false;
		}else {
			pagtoParcial=true;
		}
	}
	
	public String cancelarPagamento(){
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.setAttribute("sql", sql);
		return "consultainvoice";
	}
	
	public void calcularTotalParcial(){
		valorTotalSelecionado = 0.0f;
		for(int i=0;i<listaInvoice.size();i++){
			valorTotalSelecionado = valorTotalSelecionado + listaInvoice.get(i).getValorPago();
		}
	}
	
	public String anexarArquivo(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", listaInvoice.get(0).getVendas());
		 RequestContext.getCurrentInstance().openDialog("anexarArquivo");
		return "";
	}
	
	
	public void ajustarCambio(){
		PagamentoInvoiceFacade pagamentoInvoiceFacade = new PagamentoInvoiceFacade();
		List<Pagamentoinvoice> lista = pagamentoInvoiceFacade.listar("Select p from Pagamentoinvoice p");
		if (lista!=null){
			for (int i=0;i<lista.size();i++){
				this.pagamentoinvoice = lista.get(i);
				lista.get(i).setGanhoperda(calcularPerdaGanhoCambial(lista.get(i).getInvoices()));
				pagamentoInvoiceFacade.salvar(lista.get(i));
			}
		}
	}
	
}
