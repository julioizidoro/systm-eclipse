package br.com.travelmate.managerBean.cancelamento;

import java.util.List;

import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Faturafranquias;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Usuario;


public class CalcularMultaCancelamentoBean {

	
	public float calcularMulta(Cancelamento cancelamento){
		int idcondicao = cancelamento.getCondicaocancelamento().getIdcondicaocancelamento();
		if (idcondicao==1){
			return 0.0f;
		}else if ((idcondicao==2) || (idcondicao==3) || (idcondicao==10) || (idcondicao==17)){
			return (float) (cancelamento.getVendas().getValor() * 0.30);
		}else if (idcondicao==4){ 
			return  1500 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==5){
			float valorMulta = 1500 * cancelamento.getVendas().getValorcambio();
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
		}else if(idcondicao==12){
			float valorMulta = (float) (cancelamento.getVendas().getValor() * 0.20);
			valorMulta = valorMulta + cancelamento.getVendas().getVendascomissao().getTaxatm();
			return valorMulta; 
		}else if (idcondicao==13){
			return 0.0f;
		}else if (idcondicao==14){
			return (float) ((cancelamento.getVendas().getValor()*0.60) + cancelamento.getVendas().getVendascomissao().getTaxatm());
		}else if (idcondicao==15){
			return (float) ((cancelamento.getVendas().getValor()*0.10) + cancelamento.getVendas().getVendascomissao().getTaxatm());
		}else if (idcondicao==16){
			return 700 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==18){
			return 0.0f;
		}else if ((idcondicao==19) || (idcondicao==20)){
			return cancelamento.getVendas().getValor();
		}
		return 0.0f;
	}
	
	public Cancelamento calcularTotais(Cancelamento cancelamento){
		float totalRecebimento = cancelamento.getTotalrecebidomatriz() + cancelamento.getTotalrecebidoloja();
		cancelamento.setTotalrecebido(totalRecebimento);
		float totalreembolso = cancelamento.getTotalrecebido() - cancelamento.getMultacliente();
		cancelamento.setTotalreembolso(totalreembolso);
		float saldoCancelamento = cancelamento.getMultacliente() + cancelamento.getEstornoloja();
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
		}else if ((idcondicao==2) || (idcondicao==3) || (idcondicao==12) || (idcondicao==13) || (idcondicao==14)
				 || (idcondicao==15) || (idcondicao==16)){
			return 0.0f;
		}else if ((idcondicao==4) || (idcondicao==5)) {
			HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
			Highschool highschool = highSchoolFacade.consultarHighschool(cancelamento.getVendas().getIdvendas());
			if (highschool!=null){
				if (highschool.getValoreshighschool().getDuracao().equalsIgnoreCase("02 SEMESTRES")){
					return 725 * cancelamento.getVendas().getValorcambio();
				}else return 375 * cancelamento.getVendas().getValorcambio();
			}
			return 375 * cancelamento.getVendas().getValorcambio();
		}else if ((idcondicao==6) || (idcondicao==10)){
			return 300 * cancelamento.getVendas().getValorcambio();
		}else if ((idcondicao==7) || (idcondicao==8) || (idcondicao==9)){
			return 250 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==8){
			return 750 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==9){
			return 850 * cancelamento.getVendas().getValorcambio();
		}else if(idcondicao==11){
			return 672 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==17){
			return 150 * cancelamento.getVendas().getValorcambio();
		}else if (idcondicao==18){
			return cancelamento.getVendas().getVendascomissao().getLiquidofranquia();
		}else if (idcondicao==19){
			return 0.0f;
		}else if (idcondicao==20){
			return 0.0f;
		}
		return 0.0f;
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
	
	public Cancelamento concluirSituacaoCancelamento(Cancelamento cancelamento){
		if ((cancelamento.getValorcredito()==0) && (cancelamento.getValorreembolso()>0)){
			cancelamento.setSituacao("Pago");
		}else {
			cancelamento.setSituacao("Creditado");
		}
		return cancelamento;
	}
	

}
