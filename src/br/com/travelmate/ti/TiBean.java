package br.com.travelmate.ti;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.travelmate.dao.CrmCobrancaContaDao;
import br.com.travelmate.dao.CrmCobrancaDao;
import br.com.travelmate.dao.CrmCobrancaHistoricoDao;
import br.com.travelmate.facade.CobrancaFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HistoricoCobrancaFacade;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Crmcobranca;
import br.com.travelmate.model.Crmcobrancaconta;
import br.com.travelmate.model.Crmcobrancahistorico;
import br.com.travelmate.model.Historicocobranca;
import br.com.travelmate.util.Formatacao;

public class TiBean {
	
	@Inject
	private CrmCobrancaDao crmCobrancaDao;
	@Inject
	private CrmCobrancaHistoricoDao crmCobrancaHistoricoDao;
	@Inject CrmCobrancaContaDao crmCobrancaContaDao;
	
	/*public void gerarCobrancaNova() {
		CobrancaFacade cobrancaFacade = new CobrancaFacade();
		List<Cobranca> lista = cobrancaFacade.listar("SELECT c FROM Cobranca c");
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getVendas() != null) {
					String dataVencimento = Formatacao.SubtarirDatas(new Date(), 5, "yyyy-MM-dd");
					String sql = "SELECT c FROM Contasreceber c where c.datavencimento<='" + dataVencimento
							+ "' and c.situacao<>'cc' and c.datapagamento is NULL and c.valorpago=0 and c.vendas.idvendas="
							+ lista.get(i).getVendas().getIdvendas();
					List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
					if (listaContas != null) {
						if (listaContas.size() > 0) {
							Crmcobranca crmCobranca = new Crmcobranca();
							crmCobranca.setPrioridade("1");
							crmCobranca.setNota("Inserido pelo SysTM");
							crmCobranca.setProximocontato(new Date());
							crmCobranca.setSituacao("COBRANCA");
							crmCobranca.setVendas(lista.get(i).getVendas());
							crmCobranca=crmCobrancaDao.salvar(crmCobranca);
							CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
							for (int c=0;c<listaContas.size();c++) {
								crmCobrancaBean.criar(listaContas.get(c));
							}
							
							HistoricoCobrancaFacade hitoricoCobrancaFacade = new HistoricoCobrancaFacade();
							List<Historicocobranca> listaHistorico = hitoricoCobrancaFacade
									.listar("SELECT h FROM Historicocobranca h where h.cobranca.idcobranca="
											+ lista.get(i).getIdcobranca());
							if (listaHistorico != null) {
								for (int h = 0; h < listaHistorico.size(); h++) {
									Crmcobrancahistorico crmCobrancaHistoricio = new Crmcobrancahistorico();
									crmCobrancaHistoricio.setCliente(lista.get(i).getVendas().getCliente());
									crmCobrancaHistoricio.setData(listaHistorico.get(h).getData());
									crmCobrancaHistoricio.setHistorico(listaHistorico.get(h).getAssunto());
									crmCobrancaHistoricio.setProximocontato(new Date());
									crmCobrancaHistoricio.setTipocontato("Telefone");
									crmCobrancaHistoricio.setUsuario(listaHistorico.get(h).getUsuario());
									crmCobrancaHistoricoDao.salvar(crmCobrancaHistoricio);
								}
							}
						}
					}
				}
			}
		}

	}*/
	
	
	
	
}
