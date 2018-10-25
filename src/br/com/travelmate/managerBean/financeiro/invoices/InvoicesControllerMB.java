package br.com.travelmate.managerBean.financeiro.invoices;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.TipoArquivoProdutoFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Tipoarquivoproduto;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class InvoicesControllerMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Invoice invoice;
	private String dataPrevista;
	private Tipoarquivoproduto tipoarquivo;
	private List<Tipoarquivoproduto> listaTipoArquivo;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        invoice = (Invoice) session.getAttribute("invoice");
        session.removeAttribute("invoice");
        dataPrevista = "";
        gerarListaTipoArquivo();
        if (invoice==null){
        	invoice = new Invoice();
        }else {
        	dataPrevista = Formatacao.ConvercaoDataPadrao(invoice.getDataPrevistaPagamento());
        	tipoarquivo = invoice.getTipoarquivoproduto();
        }
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public Tipoarquivoproduto getTipoarquivo() {
		return tipoarquivo;
	}

	public void setTipoarquivo(Tipoarquivoproduto tipoarquivo) {
		this.tipoarquivo = tipoarquivo;
	}

	public List<Tipoarquivoproduto> getListaTipoArquivo() {
		return listaTipoArquivo;
	}

	public void setListaTipoArquivo(List<Tipoarquivoproduto> listaTipoArquivo) {
		this.listaTipoArquivo = listaTipoArquivo;
	}

	public String salvar(){
		if (tipoarquivo != null && tipoarquivo.getIdtipoarquivoproduto() != null) {
			InvoiceFacade invoiceFacade = new InvoiceFacade();
			invoice.setTipoarquivoproduto(tipoarquivo);
			invoice = invoiceFacade.salvar(invoice);
			String dataInvoice = Formatacao.ConvercaoDataPadrao(invoice.getDataPrevistaPagamento());
			if (dataInvoice != null && !dataInvoice.equalsIgnoreCase(dataPrevista)){
				VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
				Vendascomissao vendascomissao = vendasComissaoFacade.consultar(invoice.getVendas().getIdvendas());
				if (vendascomissao!=null){
					vendascomissao.setPrevisaopagamento(invoice.getDataPrevistaPagamento());
					vendasComissaoFacade.salvar(vendascomissao);
				}
			}
			RequestContext.getCurrentInstance().closeDialog(null);
		}else {
			Mensagem.lancarMensagemInfo("Selecione o Tipode Arquivo", "");
		}
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	

	
	public void gerarListaTipoArquivo() {
		TipoArquivoProdutoFacade tipoArquivoFacade = new TipoArquivoProdutoFacade();
		try {
				listaTipoArquivo = tipoArquivoFacade
						.listar("Select t from Tipoarquivoproduto t" + " where t.produtos.idprodutos="
								+ invoice.getVendas().getProdutos().getIdprodutos() + " order by t.tipoarquivo.idtipoArquivo");
			
			if (listaTipoArquivo == null) {
				listaTipoArquivo = new ArrayList<Tipoarquivoproduto>();
			}
		} catch (SQLException e) {
			  
		}
	}

}
