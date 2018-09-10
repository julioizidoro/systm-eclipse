package br.com.travelmate.managerBean.financeiro.invoices;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.PagamentoInvoiceFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Pagamentoinvoice;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class RelatorioInvoiceMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	private String tipoRelatorio;
    private Date dataInicial;
    private Date dataFinal;
    
    
	public String getTipoRelatorio() {
		return tipoRelatorio;
	}
	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public String iniciarRelatorio(){
		if (tipoRelatorio.equalsIgnoreCase("cambial")){
				gerarRelatorioGanhoPerdaCambial();
		}else if (tipoRelatorio.equalsIgnoreCase("pagar")){
				gerarRelatorioInvoicesPagar();
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String cancelarRelatorio(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public List<PerdaGanhoCambialBean> gerarListaRelatorioPerdaGanhoCambial(){
		String sql = "Select p from  Pagamentoinvoice p where p.valorpago>0 and " +
				" p.datapagamento>='" + Formatacao.ConvercaoDataSql(dataInicial) +
				"' and p.datapagamento<='" + Formatacao.ConvercaoDataSql(dataFinal) +
				"' order by p.datapagamento, p.invoice.vendas.cliente.nome";
		PagamentoInvoiceFacade pagamentoInvoiceFacade = new PagamentoInvoiceFacade();
		List<Pagamentoinvoice> listaInvoice = pagamentoInvoiceFacade.listar(sql);
		List<PerdaGanhoCambialBean> listaPerdaGanhoCambialBean = new ArrayList<PerdaGanhoCambialBean>();
		if (listaInvoice!=null){
			for (int i = 0; i < listaInvoice.size(); i++) {
				PerdaGanhoCambialBean pgc = new PerdaGanhoCambialBean();
				pgc.setCliente(listaInvoice.get(i).getInvoices().getVendas().getCliente().getNome());
				pgc.setCambioVenda(listaInvoice.get(i).getInvoices().getVendas().getCambio().getValor());
				pgc.setDataPagamento(listaInvoice.get(i).getDatapagamento());
				pgc.setMoeda(listaInvoice.get(i).getInvoices().getVendas().getCambio().getMoedas().getSigla());
				pgc.setValorPago(listaInvoice.get(i).getValorpago());
				pgc.setCambioPagamento(listaInvoice.get(i).getCambiopagamento());
				pgc.setTaxa(listaInvoice.get(i).getTaxa());
				pgc.setIof(listaInvoice.get(i).getIof());
				pgc.setGanhoPerda(listaInvoice.get(i).getGanhoperda());
				listaPerdaGanhoCambialBean.add(pgc);
			}
		}
		return listaPerdaGanhoCambialBean;
	}
    
	public void gerarRelatorioGanhoPerdaCambial(){
		List<PerdaGanhoCambialBean> lista = gerarListaRelatorioPerdaGanhoCambial();
		if ((lista == null) || (lista.size() == 0)) {
			FacesContext context = FacesContext.getCurrentInstance();
        	context.addMessage(null, new FacesMessage("Relatório não possui páginas", ""));
		} else {
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			Map<String, Object> parameters = new HashMap<String, Object>();
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		    BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e1) {
				e1.printStackTrace();
				Mensagem.lancarMensagemFatal("Erro Sistema", "Erro gerar ler logo " + e1.getMessage());
			}  
		    parameters.put("logo", logo);
		    String periodo = Formatacao.ConvercaoDataPadrao(dataInicial) + " - " + Formatacao.ConvercaoDataPadrao(dataFinal);
		    parameters.put("periodo", periodo);
		    String caminhoRelatorio = "/reports/invoices/reportGanhoPerdaCambia.jasper";
		    GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		    JRDataSource jrds = new JRBeanCollectionDataSource(lista);
		    try {
				gerarRelatorioContrato.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "GanhoPerdarCambial.pdf");
			} catch (Exception e) {
				e.printStackTrace();
				Mensagem.lancarMensagemErro("Erro Relatório", "Erro gerar relatório de Ganho/Perda Câmbial " + e.getMessage());
			}
		}
	}
	
	public List<PagamentoInvoiceBean> gerarListaInvoicesPagar(){
		String sql = "Select i from Invoice i where i.dataPagamentoInvoice is NULL and " +
				" i.dataPrevistaPagamento>='" + Formatacao.ConvercaoDataSql(dataInicial) +
				"' and i.dataPrevistaPagamento<='" + Formatacao.ConvercaoDataSql(dataFinal) +
				"' order by i.dataPrevistaPagamento, i.vendas.cliente.nome";
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		List<Invoice> listaInvoice = invoiceFacade.listar(sql);
		List<PagamentoInvoiceBean> listaInvoicePagar = new ArrayList<PagamentoInvoiceBean>();
		if (listaInvoice!=null){
			for (int i = 0; i < listaInvoice.size(); i++) {
				PagamentoInvoiceBean pgc = new PagamentoInvoiceBean();
				pgc.setCliente(listaInvoice.get(i).getVendas().getCliente().getNome());
				pgc.setFornecedor(listaInvoice.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				pgc.setPrevisaoPagamento(listaInvoice.get(i).getVendas().getVendascomissao().getPrevisaopagamento());
				pgc.setMoeda(listaInvoice.get(i).getVendas().getCambio().getMoedas().getSigla());
				if (listaInvoice.get(i).getValorPagamentoInvoice()>0){
					pgc.setValornet(listaInvoice.get(i).getValorPagamentoInvoice());
				}else pgc.setValornet(listaInvoice.get(i).getVendas().getVendascomissao().getValorfornecedor());
				pgc.setCredito(listaInvoice.get(i).getValorcredito());
				pgc.setValorReais(pgc.getValornet() - pgc.getCredito());
				pgc.setValorReais(pgc.getValorReais() * getValorCambio(pgc.getMoeda()));
				listaInvoicePagar.add(pgc);
			}
		}
		return listaInvoicePagar;
	}
	
	public void gerarRelatorioInvoicesPagar(){
		List<PagamentoInvoiceBean> lista = gerarListaInvoicesPagar();
		if ((lista == null) || (lista.size() == 0)) {
			FacesContext context = FacesContext.getCurrentInstance();
        	context.addMessage(null, new FacesMessage("Relatório não possui páginas", ""));
		} else {
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			Map<String, Object> parameters = new HashMap<String, Object>();
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		    BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e1) {
				e1.printStackTrace();
				Mensagem.lancarMensagemFatal("Erro Sistema", "Erro gerar ler logo " + e1.getMessage());
			}  
		    parameters.put("logo", logo);
		    String periodo = Formatacao.ConvercaoDataPadrao(dataInicial) + " - " + Formatacao.ConvercaoDataPadrao(dataFinal);
		    parameters.put("periodo", periodo);
		    String caminhoRelatorio = "/reports/invoices/reportInvoicesPagar.jasper";
		    GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		    JRDataSource jrds = new JRBeanCollectionDataSource(lista);
		    try {
				gerarRelatorioContrato.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "InvoicesPagar.pdf");
			} catch (Exception e) {
				e.printStackTrace();
				Mensagem.lancarMensagemErro("Erro Relatório", "Erro gerar relatório de Ganho/Perda Câmbial " + e.getMessage());
			}
		}
	}
	
	public float getValorCambio(String sigla){
		List<Cambio> listaCambio = aplicacaoMB.getListaCambio();
		if (listaCambio!=null){
			for (int i = 0; i < listaCambio.size(); i++) {
				if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase(sigla)){
					return listaCambio.get(i).getValor();
				}
			}
		}
		return 1.0f;
	}
	

}
