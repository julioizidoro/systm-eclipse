package br.com.travelmate.managerBean.demipair;
 
import java.util.Date;
import java.util.List;
 

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoDemiPairBean; 
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao; 
import br.com.travelmate.util.Formatacao;

public class CadDemiPairBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Demipair demipair;

	public CadDemiPairBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntigo,List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal){
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamentoPagamentoAntigo,listaParcelamentoPagamentoOriginal,
				venda.getUsuario());
		} 
	}
	
	
	public Demipair salvarDemipair(Demipair demipair) { 
		DemipairFacade demipairFacade = new DemipairFacade();
		demipair = demipairFacade.salvar(demipair);
		this.demipair=demipair;
		return this.demipair;
	}
	 
	public Orcamento salvarOrcamento(Cambio cambio, float totalMoedaReal, float totalMoedaEstrangeira, float valorCambio, String cambioAlterado){
		orcamento = programasBean.salvarOrcamento(orcamento, cambio, totalMoedaReal,
				totalMoedaEstrangeira, valorCambio, venda, cambioAlterado);
		venda.setOrcamento(orcamento);
		return orcamento;
	}
	
	public Formapagamento salvarFormaPagamento(Cancelamento cancelamento){		
		formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, venda);
		venda.setFormapagamento(formaPagamento);
		if (cancelamento != null) {
			programasBean.salvarCancelamentoVenda(venda, cancelamento);
		}
		return formaPagamento;
	}
	
	
	public Cliente salvarCliente(Cliente cliente, String data, Date dataInicio, Date dataTermino){
		cliente = programasBean.salvarCliente(cliente, data, dataInicio, dataTermino);
		return cliente;

	} 
	
	public void salvarNovaFichha(AplicacaoMB aplicacaoMB){ 
		if (Formatacao.validarDataVenda(venda.getDataVenda())) {
			ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
					formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true, demipair.getDatainicio());
		}
		float valorPrevisto = 0.0f;
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso  fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				venda.getFornecedorcidade().getFornecedor().getIdfornecedor(),
				venda.getFornecedorcidade().getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = venda.getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(venda);
				vendasComissao.setPaga("Não");
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				float valorJuros = 0.0f;
				if (venda.getFormapagamento() != null) {
					valorJuros = venda.getFormapagamento().getValorJuros();
				}
				ComissaoDemiPairBean cc = new ComissaoDemiPairBean(aplicacaoMB, venda,
						orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao,
						formaPagamento.getParcelamentopagamentoList(), demipair.getDatainicio(), vendasComissao,
						valorJuros);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			}
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleDemipair(venda, demipair, valorPrevisto);
	}
	
	public String verificarDadosAlterado(Demipair demipair, Demipair demiPairAlterado, Fornecedorcidade fornecedorCidade, Vendas vendaAlterada, float valorVendaAlterada) {
		String dadosAlterado = "";
		if (demipair.getCurso() != null) {
			if (!demipair.getCurso().equalsIgnoreCase(demiPairAlterado.getCurso())) {
				dadosAlterado = dadosAlterado + "Curso que estuda : " + demipair.getCurso() + " | "
						+ demiPairAlterado.getCurso() + "<br></br>";
			}
		}
		if (demipair.getEmailContatoEmergencia() != null) {
			if (!demipair.getEmailContatoEmergencia().equalsIgnoreCase(demiPairAlterado.getEmailContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Email contato de emergencia : " + demipair.getEmailContatoEmergencia()
						+ " | " + demiPairAlterado.getEmailContatoEmergencia() + "<br></br>";
			}
		}
		if (demipair.getFoneContatoEmergencia() != null) {
			if (!demipair.getFoneContatoEmergencia().equalsIgnoreCase(demiPairAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Telefone contato de emergencia : "
						+ demipair.getFoneContatoEmergencia() + " | " + demiPairAlterado.getFoneContatoEmergencia()
						+ "<br></br>";
			}
		}

		if (demipair.getNivelIdioma01() != null) {
			if (!demipair.getNivelIdioma01().equalsIgnoreCase(demiPairAlterado.getNivelIdioma01())) {
				dadosAlterado = dadosAlterado + "Nível Idíoma 01 : " + demipair.getNivelIdioma01() + " | "
						+ demiPairAlterado.getNivelIdioma01() + "<br></br>";
			}
		}
		if (demipair.getNivelIdioma02() != null) {
			if (!demipair.getNivelIdioma02().equalsIgnoreCase(demiPairAlterado.getNivelIdioma02())) {
				dadosAlterado = dadosAlterado + "Nível Idíoma 02 : " + demipair.getNivelIdioma02() + " | "
						+ demiPairAlterado.getNivelIdioma02() + "<br></br>";
			}
		}
		if (demipair.getNivelIdioma03() != null) {
			if (!demipair.getNivelIdioma03().equalsIgnoreCase(demiPairAlterado.getNivelIdioma03())) {
				dadosAlterado = dadosAlterado + "Nível Idíoma 03 : " + demipair.getNivelIdioma03() + " | "
						+ demiPairAlterado.getNivelIdioma03() + "<br></br>";
			}
		}
		if (demipair.getNomeContatoEmergencia() != null) {
			if (!demipair.getNomeContatoEmergencia().equalsIgnoreCase(demiPairAlterado.getNomeContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Nome contato de emergencia : " + demipair.getNomeContatoEmergencia()
						+ " | " + demiPairAlterado.getNomeContatoEmergencia() + "<br></br>";
			}
		}

		if (demipair.getTipoPassagem() != null) {
			if (!demipair.getTipoPassagem().equalsIgnoreCase(demiPairAlterado.getTipoPassagem())) {
				dadosAlterado = dadosAlterado + "Passagem área : " + demipair.getTipoPassagem() + " | "
						+ demiPairAlterado.getTipoPassagem() + "<br></br>";
			}
		}
		if (demipair.getDuracaoCurso() != null) {
			if (!demipair.getDuracaoCurso().equalsIgnoreCase(demiPairAlterado.getDuracaoCurso())) {
				dadosAlterado = dadosAlterado + "Duração do curso que estuda : " + demipair.getDuracaoCurso() + " | "
						+ demiPairAlterado.getDuracaoCurso() + "<br></br>";
			}
		}
		if (demipair.getRelacaoContatoEmergencia() != null) {
			if (!demipair.getRelacaoContatoEmergencia()
					.equalsIgnoreCase(demiPairAlterado.getRelacaoContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Relação contato de emergencia : "
						+ demipair.getRelacaoContatoEmergencia() + " | "
						+ demiPairAlterado.getRelacaoContatoEmergencia() + "<br></br>";
			}
		}
		if(vendaAlterada!=null){
			if (fornecedorCidade.getIdfornecedorcidade() != vendaAlterada.getFornecedorcidade().getIdfornecedorcidade()) {
				dadosAlterado = dadosAlterado + "Fornecedor : " + fornecedorCidade.getCidade().getNome() + " | "
						+ vendaAlterada.getFornecedorcidade().getFornecedor().getNome() + "<br></br>";
			} 
		}
		if (demipair.getEmailContatoEmergencia() != null) {
			if (!demipair.getEmailContatoEmergencia().equalsIgnoreCase(demiPairAlterado.getEmailContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Email contato de emergencia : " + demipair.getEmailContatoEmergencia()
						+ " | " + demiPairAlterado.getEmailContatoEmergencia() + "<br></br>";
			}
		}

		if (demipair.getFoneContatoEmergencia() != null) {
			if (!demipair.getFoneContatoEmergencia().equalsIgnoreCase(demiPairAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Fone contato de emergencia : " + demipair.getFoneContatoEmergencia()
						+ " | " + demiPairAlterado.getFoneContatoEmergencia() + "<br></br>";
			}
		}

		if (demipair.getRelacaoContatoEmergencia() != null) {
			if (!demipair.getRelacaoContatoEmergencia()
					.equalsIgnoreCase(demiPairAlterado.getRelacaoContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Relação contato de emergencia : "
						+ demipair.getRelacaoContatoEmergencia() + " | "
						+ demiPairAlterado.getRelacaoContatoEmergencia() + "<br></br>";
			}
		}
		if (demipair.getObservacaoPassagem() != null) {
			if (!demipair.getObservacaoPassagem().equalsIgnoreCase(demiPairAlterado.getObservacaoPassagem())) {
				dadosAlterado = dadosAlterado + "Obs Passagem aérea : " + demipair.getObservacaoPassagem() + " | "
						+ demiPairAlterado.getObservacaoPassagem() + "<br></br>";
			}
		}
		if (demipair.getCartaoVTM() != null) {
			if (!demipair.getCartaoVTM().equalsIgnoreCase(demiPairAlterado.getCartaoVTM())) {
				dadosAlterado = dadosAlterado + "Cartão VTM : " + demipair.getCartaoVTM() + " | "
						+ demiPairAlterado.getCartaoVTM() + "<br></br>";
			}
		}
		if (demipair.getMoedaCartao() != null) {
			if (!demipair.getMoedaCartao().equalsIgnoreCase(demiPairAlterado.getMoedaCartao())) {
				dadosAlterado = dadosAlterado + "Moeda Cartão VTM : " + demipair.getMoedaCartao() + " | "
						+ demiPairAlterado.getMoedaCartao() + "<br></br>";
			}
		}
		if (demipair.getNumeroCartao() != null) {
			if (!demipair.getNumeroCartao().equalsIgnoreCase(demiPairAlterado.getNumeroCartao())) {
				dadosAlterado = dadosAlterado + "Número Cartão VTM : " + demipair.getNumeroCartao() + " | "
						+ demiPairAlterado.getNumeroCartao() + "<br></br>";
			}
		}
		if (demipair.getFoneAmigo() != null) {
			if (!demipair.getFoneAmigo().equalsIgnoreCase(demiPairAlterado.getFoneAmigo())) {
				dadosAlterado = dadosAlterado + "Fone amigo : " + demipair.getFoneAmigo() + " | "
						+ demiPairAlterado.getFoneAmigo() + "<br></br>";
			}
		}
		if (demipair.getEndercoAmigo() != null) {
			if (!demipair.getEndercoAmigo().equalsIgnoreCase(demiPairAlterado.getEndercoAmigo())) {
				dadosAlterado = dadosAlterado + "Endereço Amigo : " + demipair.getEndercoAmigo() + " | "
						+ demiPairAlterado.getEndercoAmigo() + "<br></br>";
			}
		}
		if (demipair.getEndercoAmigo() != null) {
			if (!demipair.getEndercoAmigo().equalsIgnoreCase(demiPairAlterado.getEndercoAmigo())) {
				dadosAlterado = dadosAlterado + "Endereço Amigo : " + demipair.getEndercoAmigo() + " | "
						+ demiPairAlterado.getEndercoAmigo() + "<br></br>";
			}
		}
		if (demipair.getIdioma01() != null) {
			if (!demipair.getIdioma01().equalsIgnoreCase(demiPairAlterado.getIdioma01())) {
				dadosAlterado = dadosAlterado + "Idioma 01 : " + demipair.getIdioma01() + " | "
						+ demiPairAlterado.getIdioma01() + "<br></br>";
			}
		}
		if (demipair.getIdioma02() != null) {
			if (!demipair.getIdioma02().equalsIgnoreCase(demiPairAlterado.getIdioma02())) {
				dadosAlterado = dadosAlterado + "Idioma 02 : " + demipair.getIdioma02() + " | "
						+ demiPairAlterado.getIdioma02() + "<br></br>";
			}
		}
		if (demipair.getIdioma03() != null) {
			if (!demipair.getIdioma03().equalsIgnoreCase(demiPairAlterado.getIdioma03())) {
				dadosAlterado = dadosAlterado + "Idioma 03 : " + demipair.getIdioma03() + " | "
						+ demiPairAlterado.getIdioma03() + "<br></br>";
			}
		}
		if (demipair.getNivelEstudo() != null) {
			if (!demipair.getNivelEstudo().equalsIgnoreCase(demiPairAlterado.getNivelEstudo())) {
				dadosAlterado = dadosAlterado + "Nível estudo : " + demipair.getNivelEstudo() + " | "
						+ demiPairAlterado.getNivelEstudo() + "<br></br>";
			}
		}
		if (demipair.getOcupacao() != null) {
			if (!demipair.getOcupacao().equalsIgnoreCase(demiPairAlterado.getOcupacao())) {
				dadosAlterado = dadosAlterado + "Ocupação : " + demipair.getOcupacao() + " | "
						+ demiPairAlterado.getOcupacao() + "<br></br>";
			}
		}
		if (demipair.getPossuicnh() != null) {
			if (!demipair.getPossuicnh().equalsIgnoreCase(demiPairAlterado.getPossuicnh())) {
				dadosAlterado = dadosAlterado + "Possui CNH : " + demipair.getPossuicnh() + " | "
						+ demiPairAlterado.getPossuicnh() + "<br></br>";
			}
		}
		if (demipair.getTempocnh() != null) {
			if (!demipair.getPossuicnh().equalsIgnoreCase(demiPairAlterado.getTempocnh())) {
				dadosAlterado = dadosAlterado + "Tempo de CNH : " + demipair.getTempocnh() + " | "
						+ demiPairAlterado.getTempocnh() + "<br></br>";
			}
		}
		if (demipair.getDatainicio() != null) {
			if (demipair.getDatainicio()!=demiPairAlterado.getDatainicio()) {
				dadosAlterado = dadosAlterado + "Data inicio pretendida : " + Formatacao.ConvercaoDataPadrao(demipair.getDatainicio()) + " | "
						+ Formatacao.ConvercaoDataPadrao(demiPairAlterado.getDatainicio()) + "<br></br>";
			}
		}
		if (demipair.getNumerosemanas()>0) {
			if (demipair.getNumerosemanas()!=demiPairAlterado.getNumerosemanas()) {
				dadosAlterado = dadosAlterado + "Nº semanas : " + demipair.getNumerosemanas() + " | "
						+ demiPairAlterado.getNumerosemanas() + "<br></br>";
			}
		}
		return dadosAlterado;
	}
	

}
