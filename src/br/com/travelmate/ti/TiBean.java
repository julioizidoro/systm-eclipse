package br.com.travelmate.ti;

import java.util.List; 

import br.com.travelmate.facade.AcessoUnidadeFacade;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;

public class TiBean {  
	
	public TiBean() { 
		 
	}
	
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


	public void salvarAcessoUnidade() {
		AcessoUnidadeFacade acessoUnidadeFacade = new AcessoUnidadeFacade();
		List<Usuario> listaUsuario = GerarListas.listarUsuarios("SELECT u FROM Usuario u WHERE u.situacao='Ativo' ORDER BY u.nome");
		for (int i = 0; i < listaUsuario.size(); i++) {
			Acessounidade acessounidade = acessoUnidadeFacade.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
					+listaUsuario.get(i).getIdusuario());
			if(acessounidade==null) {
				acessounidade = new Acessounidade();
				acessounidade.setComissaoparceiros(true);
				acessounidade.setConsultaorcamento(true);
				acessounidade.setCrm(true);
				acessounidade.setDashboard(true);
				acessounidade.setEmissaoconsulta(true); 
				acessounidade.setUsuario(listaUsuario.get(i));
				acessounidade = acessoUnidadeFacade.salvar(acessounidade);
			}
		}
	}
	
}
