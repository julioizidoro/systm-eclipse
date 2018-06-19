package br.com.travelmate.managerBean.cancelamento;

import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;


public class CalcularMultaCancelamentoBean {
	
	public Float valorSeguro;
	
	public Float consultarValorCambio(Date dataVenda, int idMoeda) {
		Float ValorCambio = 0.0f;
		CambioFacade cambioFacade = new CambioFacade();
		String sData = Formatacao.ConvercaoDataSql(dataVenda);
		Cambio cambio = cambioFacade.consultarCambioMoeda(sData, idMoeda);
		return ValorCambio;
	}
	
	public float calcularMulta(Cancelamento cancelamento){
		valorSeguro = 0.0f;
		
		int idcondicao = cancelamento.getCondicaocancelamento().getIdcondicaocancelamento();
		if (idcondicao==1){
			return 0.0f;
		}else if (idcondicao==2){
			if (cancelamento.getVendas().getProdutos().getIdprodutos()==1) {
				valorSeguro = consultarValorSeguro(cancelamento.getVendas().getIdlead());
			}
			return (float) ((cancelamento.getVendas().getValor() + valorSeguro) * 0.30);
		}else if (idcondicao==3){
			return (float) (cancelamento.getVendas().getValor() * 0.30);
		}else if (idcondicao==4){
			Float valorCambio = consultarValorCambio(cancelamento.getVendas().getDataVenda(), 2);
			if (valorCambio<=0) {
				valorCambio = 1f;
			}
			return  1500 * valorCambio;
		}else if (idcondicao==5){
			Float valorCambio = consultarValorCambio(cancelamento.getVendas().getDataVenda(), 2);
			if (valorCambio<=0) {
				valorCambio = 1f;
			}
			float valorMulta = 1500 * valorCambio;
			valorMulta = (float) (valorMulta + (cancelamento.getVendas().getValor() * 0.2));
			return valorMulta;
		}else if (idcondicao==6){
			return 350 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==7){
			return 500 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==8){
			return 750 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==9){
			return 850 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==10){
			return (float) (cancelamento.getVendas().getValor() * 0.30);
		}else if (idcondicao==11){
			return (float) (cancelamento.getVendas().getValor() * 0.30);
		}else if (idcondicao==12){
			return 0.0f;
		}else if (idcondicao==13){
			return (float) (cancelamento.getVendas().getValor() * 0.60);
		}else if (idcondicao==14){
			return (float) (cancelamento.getVendas().getValor() * 0.10);
		}else if (idcondicao==15){
			return 700 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==16){
			return (float) (cancelamento.getVendas().getValor() * 0.30);
		}else if (idcondicao==17){
			return 0.0f;
		}else if (idcondicao==18){
			return 0.0f;
		}else if (idcondicao==19){
			return 0.0f;
		}else if (idcondicao==20){
			return 0.0f;
		}else if (idcondicao==21){
			return 0.0f;
		}else if (idcondicao==22){
			return 0.0f;
		}else if (idcondicao==23){
			return 0.0f;
		}else if (idcondicao==24){
			return 0.0f;
		}else if (idcondicao==25){
			return 0.0f;
		}else if (idcondicao==26){
			return 0.0f;
		}else if (idcondicao==27){
			return 0.0f;
		}else if (idcondicao==28){
			return 0.0f;
		}else if (idcondicao==29){
			return (float) (cancelamento.getVendas().getValor() * 0.30);
		}else if (idcondicao==30){
			return 0.0f;
		}else if (idcondicao==31){
			return 0.0f;
		}else if (idcondicao==32){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.50);
		}else if (idcondicao==33){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.65);
		}else if (idcondicao==34){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else return 0.0f;
		
	}
	
	public Cancelamento calcularTotais(Cancelamento cancelamento){
		if (cancelamento.getTotalrecebidomatriz() == null) {
			cancelamento.setTotalrecebidomatriz(0.0f);
		}
		if (cancelamento.getTotalrecebidoloja() == null) {
			cancelamento.setTotalrecebidoloja(0.0f);
		}
		float totalRecebimento = cancelamento.getTotalrecebidomatriz() + cancelamento.getTotalrecebidoloja();
		cancelamento.setTotalrecebido(totalRecebimento);
		float totalreembolso = cancelamento.getTotalrecebido() - cancelamento.getMultacliente();
		cancelamento.setTotalreembolso(totalreembolso);
		float saldoCancelamento = cancelamento.getMultacliente() + cancelamento.getEstornoloja();
		if (cancelamento.getMultafornecedor() == null) {
			cancelamento.setMultafornecedor(0.0f);
		}
		if (cancelamento.getVendas().getVendascomissao()!=null){
			saldoCancelamento = saldoCancelamento - (cancelamento.getVendas().getVendascomissao().getComissaotm() + cancelamento.getVendas().getVendascomissao().getTaxatm() + (cancelamento.getMultafornecedor()* cancelamento.getVendas().getValorcambio()));
		}
		cancelamento.setSaldocancelamento(saldoCancelamento);
		return cancelamento;
	}
	
	public float calcularEstornoFranquia(Cancelamento cancelamento){
		int idcondicao = cancelamento.getCondicaocancelamento().getIdcondicaocancelamento();
		if (idcondicao==1){
			return 0.0f;
		}else if (idcondicao==2){
			return 0.0f;
		}else if (idcondicao==3){
			return 0.0f;
		}else if (idcondicao==4){
			return (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao()) * 0.75);
		}else if (idcondicao==5){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==6){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==7){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==8){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==9){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==10){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==11){
			return 0.0f;
		}else if (idcondicao==12){
			return calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao());
		}else if (idcondicao==13){
			return 0.0f;
		}else if (idcondicao==14){
			return calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao());
		}else if (idcondicao==15){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.75);
		}else if (idcondicao==16){
			return 0.0f;
		}else if (idcondicao==17){
			return calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao());
		}else if (idcondicao==18){
			return 0.0f;
		}else if (idcondicao==19){
			return 0.0f;
		}else if (idcondicao==20){
			return 0.0f;
		}else if (idcondicao==21){
			return 0.0f;
		}else if (idcondicao==22){
			return 0.0f;
		}else if (idcondicao==23){
			return 0.0f;
		}else if (idcondicao==24){
			return 0.0f;
		}else if (idcondicao==25){
			return 0.0f;
		}else if (idcondicao==26){
			return 0.0f;
		}else if (idcondicao==27){
			return 0.0f;
		}else if (idcondicao==28){
			return 0.0f;
		}else if (idcondicao==29){
			return 0.0f;
		}else if (idcondicao==30){
			return 0.0f;
		}else if (idcondicao==31){
			return 0.0f;
		}else if (idcondicao==32){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.50);
		}else if (idcondicao==33){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.50);
		}else if (idcondicao==34){
			return  (float) (calcularComissaoFranquia(cancelamento.getVendas().getVendascomissao())* 0.25);
		}else return 0.0f;
	}
	
	public float calcularComissaoFranquia(Vendascomissao vendaComissao) {
		float valorComissao = vendaComissao.getLiquidofranquia() + (vendaComissao.getCustofinanceirofranquia()/2);
		return valorComissao;
	}
	
	public boolean verifcarValorCreditoReembolso(Cancelamento cancelamento){
		float total = cancelamento.getValorcredito() + cancelamento.getValorreembolso();
		if (total>(cancelamento.getTotalreembolso() + 0.01)){
			return false;
		}else return true;
	}
	
	public void verifcarSituacaoInvoice(Cancelamento cancelamento){
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		String sql = "SELECT i Invoices i where i.vendas.idvendas=" + cancelamento.getVendas().getIdvendas();
		List<Invoice> listaInvoices = invoiceFacade.listar(sql);
		if (cancelamento.getMultafornecedor()==0){
			for(int i=0;i<listaInvoices.size();i++){
				listaInvoices.get(i).setSituacao("Cancelada");
				invoiceFacade.salvar(listaInvoices.get(i));
			}
		}else {
			for(int i=0;i<listaInvoices.size();i++){
				if (i==0){
					listaInvoices.get(i).setValorPagamentoInvoice(cancelamento.getMultafornecedor());
				}else {
					listaInvoices.get(i).setSituacao("Cancelada");
				}
				invoiceFacade.salvar(listaInvoices.get(i));
			} 
		}
	}
	
	public void cancelarContasReceber(Cancelamento cancelamento, Usuario usuario){
		if ((cancelamento.getValorcredito()==0) && (cancelamento.getValorreembolso()>0)){
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT c FROM Contasreceber c where c.vendas.idvendas=" + cancelamento.getVendas().getIdvendas();
			List<Contasreceber> lista = contasReceberFacade.listar(sql);
			if (lista!=null){
				for(int i=0;i<lista.size();i++){
					if (lista.get(i).getValorpago() == 0 && lista.get(i).getDatapagamento()==null) {
						lista.get(i).setSituacao("cc");
						lista.get(i).setMotivocancelamento("Cancelamento da Venda");
						contasReceberFacade.salvar(lista.get(i));
						if (lista.get(i).getCrmcobrancaconta() != null) {
							if (lista.get(i).getCrmcobrancaconta().getPaga() == false) {
								CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
								crmCobrancaBean.baixar(lista.get(i), usuario);
							}
						}
						EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Cancelada", lista.get(i), usuario);
					}
				}
			}
		}
	}
	
	public Cancelamento concluirSituacaoCancelamento(Cancelamento cancelamento){
		if ((cancelamento.getValorcredito()==0) && (cancelamento.getValorreembolso()>0)){
			cancelamento.setSituacao("Pago");
		}else {
			cancelamento.setSituacao("Creditado");
		}
		return cancelamento;
	}
	
	public Float consultarValorSeguro(int idVendaCurso) {
		Float valorSeguro = 0.0f;
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemFacade.consultarSeguroCurso(idVendaCurso);
		if (seguro!=null) {
			valorSeguro = seguro.getVendas().getValor();
		}
		return valorSeguro;
	}
	

}
