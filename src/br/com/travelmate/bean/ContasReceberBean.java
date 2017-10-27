/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.ParametrosProdutosFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Eventocontasreceber;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
public class ContasReceberBean {

	private Vendas venda;
	private List<Parcelamentopagamento> listaParcelas;
	private UsuarioLogadoMB usuarioLogadoBean;
	private List<Contasreceber> listaContas;
	private Planoconta planoConta;
	private String numeroDocumento;
	private float valorJaRecebido;

	public ContasReceberBean(Vendas venda, List<Parcelamentopagamento> listaParcelas, UsuarioLogadoMB usuarioLogadoBean,
			String numeroDocumen, boolean apagarConta) {
		this.venda = venda;
		this.listaParcelas = listaParcelas;
		this.usuarioLogadoBean = usuarioLogadoBean;
		if (apagarConta) {
			apagarContasReceber();
		}
		getPlanoConta();
		verificarParcelamento();
	}

	public ContasReceberBean() {

	}
	
	

	public float getValorJaRecebido() {
		return valorJaRecebido;
	}

	public void setValorJaRecebido(float valorJaRecebido) {
		this.valorJaRecebido = valorJaRecebido;
	}

	public void apagarContasReceber() {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> lista = contasReceberFacade
				.listar("SELECT c FROM Contasreceber c where c.vendas.idvendas=" + venda.getIdvendas());
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				excluirEventosContasReceber(lista.get(i));
				contasReceberFacade.excluir(lista.get(i).getIdcontasreceber());
			}
		}
	}

	public void apagarContasReceber(Parcelamentopagamento parcelamento, int idVenda,
			UsuarioLogadoMB usuarioLogadoBean, int idParcelamentoPagamento) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> lista = contasReceberFacade.listar("SELECT c FROM Contasreceber c where c.vendas.idvendas="
				+ idVenda + " and c.tipodocumento='" + parcelamento.getFormaPagamento() + "' and c.idparcelamentopagamento=" + idParcelamentoPagamento);
		if (lista == null) {
			lista = new ArrayList<>();
		}
		valorJaRecebido=0;
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getValorpago() == 0 && lista.get(i).getDatapagamento()==null) {
				EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean(
						"Conta cancelada pelo SysTM", lista.get(i), usuarioLogadoBean.getUsuario());
				lista.get(i).setSituacao("cc");
				lista.get(i).setDatacancelamento(new Date());
				lista.get(i).setMotivocancelamento("Alteração SysTM");
				contasReceberFacade.salvar(lista.get(i));
				if (lista.get(i).getCrmcobrancaconta() != null) {
					if (lista.get(i).getCrmcobrancaconta().getPaga() == false) {
						CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
						crmCobrancaBean.baixar(lista.get(i), usuarioLogadoBean.getUsuario());
					}
				}  
			}else {
				valorJaRecebido = valorJaRecebido + lista.get(i).getValorpago();
			}
		}
	}

	public Parcelamentopagamento gerarParcelasIndividuais(Parcelamentopagamento parcelamento, int numeroParcela, Vendas venda,
			UsuarioLogadoMB usuarioLogadoBean) {
		ParcelamentoPagamentoFacade pagamentoFacade = new ParcelamentoPagamentoFacade();
		this.venda = venda;
		this.usuarioLogadoBean = usuarioLogadoBean;
		if (parcelamento.getTipoParcelmaneto().equalsIgnoreCase("Matriz")) {
			this.listaContas = new ArrayList<Contasreceber>();
			parcelamento = pagamentoFacade.salvar(parcelamento);
			gerarParcelas(parcelamento, numeroParcela);
			if (listaContas.size() > 0) {
				salvarContasReceber();
			}
		}
		return parcelamento;
	}

	public void verificarParcelamento() {
		this.listaContas = new ArrayList<Contasreceber>();
		for (int i = 0; i < listaParcelas.size(); i++) {
			if (listaParcelas.get(i).getTipoParcelmaneto().equalsIgnoreCase("Matriz")) {
				gerarParcelas(listaParcelas.get(i), (i + 1));
			}
		}
		if (listaContas.size() > 0) {
			salvarContasReceber();
		}
	}

	public void gerarParcelas(Parcelamentopagamento parcela, int idlista) {
		String data = Formatacao.ConvercaoDataPadrao(parcela.getDiaVencimento());
		String mes = data.substring(3, 5);
		String ano = data.substring(6, 10);
		String dia = data.substring(0, 2);
		int cmes = Integer.parseInt(mes);
		int cano = Integer.parseInt(ano);
		int numeroParcelas = parcela.getNumeroParcelas();
		Float valorParcela = 0.0f;
		Date cData;
		if (parcela.getFormaPagamento().equalsIgnoreCase("Financiamento banco")) {
			numeroParcelas = 1;
			valorParcela = parcela.getValorParcela();
			cData = new Date();
		} else {
			numeroParcelas = parcela.getNumeroParcelas();
			valorParcela = parcela.getValorParcela();
			cData = parcela.getDiaVencimento();
		}
		String numeroParcelasFormatado = "00";
		if (numeroParcelas >= 10) {
			numeroParcelasFormatado = String.valueOf(numeroParcelas);
		} else {
			numeroParcelasFormatado = "0" + String.valueOf(numeroParcelas);
		}
		for (int i = 0; i < numeroParcelas; i++) {
			Contasreceber conta = new Contasreceber();
			if (numeroDocumento == null) {
				conta.setNumerodocumento(String.valueOf(venda.getIdvendas()));
			} else
				conta.setNumerodocumento(numeroDocumento);

			if (i < 9) {
				conta.setNumeroparcelas("0" + (String.valueOf(i + 1)) + "/" + numeroParcelasFormatado);
			} else {
				conta.setNumeroparcelas((String.valueOf(i + 1)) + "/" + numeroParcelasFormatado);
			}
			conta.setControlenossonumero(idlista);
			conta.setValorparcela(valorParcela);
			conta.setDatavencimento(cData);
			conta.setTipodocumento(parcela.getFormaPagamento());
			conta.setBanco(venda.getUsuario().getUnidadenegocio().getBanco());
			conta.setDesagio(0.0f);
			conta.setJuros(0.0f);
			if (planoConta == null) {
				getPlanoConta();
			}
			conta.setPlanoconta(this.planoConta);
			conta.setValorpago(0.0f);
			conta.setSituacao("vm");
			conta.setBoletocancelado(false);
			conta.setBoletoenviado(false);
			conta.setDataalterada(false);
			if (conta.getTipodocumento().equalsIgnoreCase("Boleto")) {
				conta.setParcelaboleto(i + 1);
			}
			conta.setBoletoenviado(false);
			conta.setVendas(venda);
			conta.setIdparcelamentopagamento(parcela.getIdparcemlamentoPagamento());
			listaContas.add(conta);
			if (cmes == 12) {
				cmes = 1;
				cano = cano + 1;
			} else {
				cmes = cmes + 1;
			}
			if (cmes < 10) {
				data = dia + "/" + "0" + String.valueOf(cmes) + "/" + String.valueOf(cano);
			} else {
				data = dia + "/" + String.valueOf(cmes) + "/" + String.valueOf(cano);
			}
			cData = Formatacao.ConvercaoStringData(data);
		}

	}

	public void salvarContasReceber() {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		for (int i = 0; i < listaContas.size(); i++) {
			Contasreceber contasreceber = contasReceberFacade.salvar(listaContas.get(i));
			EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Conta criada pelo SysTM",
					contasreceber, usuarioLogadoBean.getUsuario());
		}
	}

	public void getPlanoConta() {
		ParametrosProdutosFacade parametrosProdutosFacade = new ParametrosProdutosFacade();
		Parametrosprodutos parametrosprodutos = parametrosProdutosFacade.consultar();
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		planoConta = planoContaFacade.consultar(parametrosprodutos.getIdplanocontas());
	}

	public void excluirEventosContasReceber(Contasreceber contasreceber) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Eventocontasreceber> lista = contasReceberFacade
				.listarEventosContasReceber("Select e From Eventocontasreceber e Where e.contasreceber.idcontasreceber="
						+ contasreceber.getIdcontasreceber());
		if (lista == null) {
			lista = new ArrayList<>();
		}
		for (int i = 0; i < lista.size(); i++) {
			contasReceberFacade.excluirEventosContasReceber(lista.get(i).getIdeventoscontasreceber());
		}
	}

}
