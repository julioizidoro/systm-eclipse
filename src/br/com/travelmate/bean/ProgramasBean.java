package br.com.travelmate.bean;

import java.util.Date;
import java.util.List;
 
import javax.inject.Named;

import br.com.travelmate.facade.AlteracaofinanceiroFacade;
import br.com.travelmate.facade.AlteracaofinanceiroparcelasFacade;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.CancelamentoVendaFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadPosVendaFacade;
import br.com.travelmate.facade.LogVendaFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.VendasFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Alteracaofinanceiro;
import br.com.travelmate.model.Alteracaofinanceiroparcelas;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cancelamentovenda;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadposvenda;
import br.com.travelmate.model.Logvenda;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
public class ProgramasBean {
	
	
	public Vendas salvarVendas(Vendas venda) {
		venda.setDataVenda(new Date());
		venda.setSituacaogerencia("F");
		venda.setSituacao("FINALIZADA");
		VendasFacade vendasFacade = new VendasFacade();
		venda = vendasFacade.salvar(venda);
		Logvenda logVenda = new Logvenda();
		logVenda.setOperacao("FINALIZADA");
		logVenda.setSituacao(venda.getSituacao());
		logVenda.setUsuario(venda.getUsuario());
		logVenda.setVendas(venda);
		LogVendaFacade logVendaFacade = new LogVendaFacade();
		logVendaFacade.salvar(logVenda);
		return venda;
	}
	
	public Vendas salvarVendas(Vendas venda, UsuarioLogadoMB usuarioLogadoMB, String situacao, Cliente cliente, Float valorTotal, Produtos produto, Fornecedorcidade fornecedorCidade, Cambio cambio, Float valorCambio, Lead lead, Date datainicio, Date datatermino) {
		Logvenda logVenda = new Logvenda();
		if (venda.getIdvendas() == null) {
			logVenda.setOperacao("NOVA");
			venda.setDataVenda(new Date());
			venda.setDatavalidade(calcularDataValidade());
			venda.setUsuariocancelamento(0);
			venda.setObsCancelar("");
			venda.setUsuario(usuarioLogadoMB.getUsuario());
			if(venda.getUnidadenegocio()==null){
				venda.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
			}
		} else {
			if (venda.getDatavalidade()!=null) {
				if (venda.getDatavalidade().before(new Date())) {
					venda.setDatavalidade(calcularDataValidade());
				}
			}
			if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
				venda.setDataVenda(new Date());
				logVenda.setOperacao("NOVA");
			}else {
				logVenda.setOperacao("ALTERAÇÃO");
			}
		}
		venda.setValorcambio(valorCambio);
		venda.setSituacao(situacao);
		if (!situacao.equalsIgnoreCase("PROCESSO")){
			if (venda.getSituacaogerencia().equalsIgnoreCase("P")){
				venda.setSituacaogerencia("A");
			}
		}
		VendasFacade VendasFacade = new VendasFacade();
		venda.setCliente(cliente);
		if(venda.getVendasMatriz()==null || venda.getVendasMatriz().length()==0){
			venda.setVendasMatriz("S");
		}
		venda.setProdutos(produto); 
		venda.setValor(valorTotal);
		venda.setFornecedor(fornecedorCidade.getFornecedor());
		venda.setFornecedorcidade(fornecedorCidade);
		venda.setCambio(cambio);
		venda.setIdlead(0);
		if ((lead!=null) && (lead.getIdlead()!=null)){
			venda.setIdlead(lead.getIdlead());
			venda = VendasFacade.salvar(venda);
			finalizarLead(lead);
			if(lead.getLeadposvenda()==null) {
				Leadposvenda leadposvenda = new Leadposvenda();
				LeadPosVendaFacade leadPosVendaFacade = new LeadPosVendaFacade();
				leadposvenda.setLead(lead);
				leadposvenda.setVendas(venda);
				Date data = null;
				Date datachegada = null;
				if(datainicio!=null) {
					try {
						data = Formatacao.SomarDiasDatas(datainicio, -2); 
					} catch (Exception e) { 
						e.printStackTrace();
					}
				}
				if(datatermino!=null) {
					try { 
						datachegada = Formatacao.SomarDiasDatas(datatermino, 2);
					} catch (Exception e) { 
						e.printStackTrace();
					}
				}
				leadposvenda.setDatachegada(datachegada);
				leadposvenda.setDataembarque(data);
				leadPosVendaFacade.salvar(leadposvenda);
			}
		}else {
			venda = VendasFacade.salvar(venda);
		}
		if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")){
			logVenda.setSituacao(venda.getSituacao());
			logVenda.setUsuario(usuarioLogadoMB.getUsuario());
			logVenda.setVendas(venda);
			LogVendaFacade logVendaFacade = new LogVendaFacade();
			logVendaFacade.salvar(logVenda);
		}
		   
		return venda;
	}
	
	public boolean verificarSomarJuros(Formapagamento formaPagamento){
		if (formaPagamento!=null){
			if (formaPagamento.getParcelamentopagamentoList()!=null){
				for(int i=0;i<formaPagamento.getParcelamentopagamentoList().size();i++){
					if (formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto().equalsIgnoreCase("Financiamento Banco")){
						return false;
					}	
				}
			}
		}
		return true;
	}
	
	public Orcamento salvarOrcamento(Orcamento orcamento, Cambio cambio, Float totalMoedaNacional, Float totalMoedaEstrangeira, Float valorCambio, Vendas venda, String cambioAlterado) {
		if (orcamento == null) {
			orcamento = new Orcamento();
		}
		orcamento.setDataCambio(cambio.getData());
		orcamento.setValorCambio(valorCambio);
		orcamento.setTotalMoedaEstrangeira(totalMoedaEstrangeira);
		orcamento.setTotalMoedaNacional(totalMoedaNacional);
		orcamento.setVendas(venda);
		orcamento.setCambioAlterado(cambioAlterado);
		orcamento.setCambio(cambio);
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		salvarOrcamentoProdutoOrcamento(orcamento);
		orcamento = orcamentoFacade.salvar(orcamento);
		return orcamento;
	}
	
	public void salvarOrcamentoProdutoOrcamento(Orcamento orcamento) {
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
			for (int i = 0; orcamento.getOrcamentoprodutosorcamentoList().size() > i; i++) {
				orcamento.getOrcamentoprodutosorcamentoList().get(i).setOrcamento(orcamento);
			}
		}
	}
	
	public Formapagamento salvarFormaPagamento(Formapagamento formaPagamento, Vendas venda) {
		if (formaPagamento.getValorJuros() != null) {
			formaPagamento.setValorTotal(formaPagamento.getValorJuros() + formaPagamento.getValorOrcamento());
		}else{
			formaPagamento.setValorTotal(formaPagamento.getValorOrcamento());
		}
		
		formaPagamento.setVendas(venda);
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		formaPagamento = formaPagamentoFacade.salvar(formaPagamento);
		return formaPagamento;
	}
	
	
	public Cliente salvarCliente(Cliente cliente, String dataInicio, Date datainicio, Date dataTermino){
		ClienteFacade clienteFacade = new ClienteFacade();
		cliente.setDatainicioprograma(dataInicio);
		cliente.setDataInicio(datainicio);
		cliente.setDataTermino(dataTermino);
		cliente.setTipoCliente("Fechado");
		return clienteFacade.salvar(cliente);
	}
	
	
	public void salvarAlteracaoFinanceiro(Vendas venda, List<Parcelamentopagamento> listaOriginal, List<Parcelamentopagamento> listaNova, Usuario usuario){
		boolean alterado = true;
		for(int i=0;i<listaOriginal.size();i++){
			for(int n=0;n<listaNova.size();n++){
				if(listaNova.get(n).getFormaPagamento().equalsIgnoreCase(listaOriginal.get(i).getFormaPagamento())
						&& listaNova.get(n).getValorParcela().floatValue()==listaOriginal.get(i).getValorParcela().floatValue()
						&& listaNova.get(n).getNumeroParcelas()==listaOriginal.get(i).getNumeroParcelas()){
					alterado=false;
				}else{   
					alterado = true;
				}
			}   
		}   
		if(alterado){
			AlteracaofinanceiroFacade alteracaofinanceiroFacade = new AlteracaofinanceiroFacade();
			Alteracaofinanceiro alteracao = new Alteracaofinanceiro();
			alteracao.setData(new Date());
			alteracao.setUsuario(usuario);
			alteracao.setVendas(venda);
			alteracao.setSituacao(false);
			alteracao = alteracaofinanceiroFacade.salvar(alteracao);
			AlteracaofinanceiroparcelasFacade alteracaofinanceiroparcelasFacade = new AlteracaofinanceiroparcelasFacade();
			for(int i=0;i<listaOriginal.size();i++){
				Alteracaofinanceiroparcelas parcelas = new Alteracaofinanceiroparcelas();
				parcelas.setAlteracaofinanceiro(alteracao);
				parcelas.setData(listaOriginal.get(i).getDiaVencimento());
				parcelas.setFormaparcelamento(listaOriginal.get(i).getTipoParcelmaneto());
				parcelas.setNumeroparcelas(listaOriginal.get(i).getNumeroParcelas());
				parcelas.setTipo("O");
				parcelas.setTipodocumento(listaOriginal.get(i).getFormaPagamento());
				parcelas.setValorparcela(listaOriginal.get(i).getValorParcela());
				parcelas.setValorparcelado(listaOriginal.get(i).getValorParcelamento());
				parcelas = alteracaofinanceiroparcelasFacade.salvar(parcelas);
			}
			
			for(int i=0;i<listaNova.size();i++){
				Alteracaofinanceiroparcelas parcelas = new Alteracaofinanceiroparcelas();
				parcelas.setAlteracaofinanceiro(alteracao);
				parcelas.setData(listaNova.get(i).getDiaVencimento());
				parcelas.setFormaparcelamento(listaNova.get(i).getTipoParcelmaneto());
				parcelas.setNumeroparcelas(listaNova.get(i).getNumeroParcelas());
				parcelas.setTipo("N");
				parcelas.setTipodocumento(listaNova.get(i).getFormaPagamento());
				parcelas.setValorparcela(listaNova.get(i).getValorParcela());
				parcelas.setValorparcelado(listaNova.get(i).getValorParcelamento());
				parcelas = alteracaofinanceiroparcelasFacade.salvar(parcelas);
			}
		}
	}
	
	public void salvarCancelamentoVenda(Vendas venda, Cancelamento cancelamento){
		if (venda!=null){
			if (cancelamento!=null){
				cancelamento.setIdvendacredito(venda.getIdvendas());
				CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
				cancelamento = cancelamentoFacade.salvar(cancelamento);
				Cancelamentovenda cancelamentovenda = new Cancelamentovenda();
				cancelamentovenda.setCancelamento(cancelamento);
				cancelamentovenda.setVendas(venda);
				CancelamentoVendaFacade cancelamentoVendaFacade = new  CancelamentoVendaFacade();
				cancelamentoVendaFacade.salvar(cancelamentovenda);
				Logvenda logVenda = new Logvenda();
				logVenda.setSituacao(venda.getSituacao());
				logVenda.setOperacao("CANCELAMENTO");
				logVenda.setUsuario(cancelamento.getUsuario());
				logVenda.setVendas(venda);
				LogVendaFacade logVendaFacade = new LogVendaFacade();
				logVendaFacade.salvar(logVenda);
			}
		}
	}
	
	public void finalizarLead(Lead lead){
		LeadFacade leadFacade = new LeadFacade();
		LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 6);
		lead.setSituacao(6);
		leadFacade.salvar(lead);
	}
	
	public Date calcularDataValidade() {
		int dias =0;
		int diaSemana = Formatacao.diaSemana(new Date());
		if (diaSemana==1) {
			dias = 3;
		}else if (diaSemana==2) {
			dias=3;
		}else if (diaSemana==3) {
			dias= 3;
		}else if (diaSemana==4) {
			dias = 5;
		}else if (diaSemana==5) {
			dias =5;
		}else if (diaSemana==6) {
			dias =5;
		}else if (diaSemana==7) {
			dias = 4;
		}
		Date dataValidade = Formatacao.calcularDataFinalPorDias(new Date(), dias);
	    diaSemana = Formatacao.diaSemana(dataValidade);
		return dataValidade;
	}
}
