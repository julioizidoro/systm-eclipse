package br.com.travelmate.managerBean.worktravel;
 
import java.util.Date;
import java.util.List;
 

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.ProgramasBean; 
import br.com.travelmate.bean.comissao.ComissaoWorkBean;  
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade; 
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;

public class CadWorkTravelBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Worktravel work;
	

	public CadWorkTravelBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB,Worktravel worktravel) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
		this.work = worktravel;
	}
	
	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntigo, List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal){ 
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda,listaParcelamentoPagamentoAntigo, listaParcelamentoPagamentoOriginal,
				venda.getUsuario());
		} 
	}
	
	
	public Worktravel salvarWork(Worktravel work, Vendas vendaAlterada) {
		if (work.getIdworkTravel() == null) {
			work.setDataInscricao(new Date());
		} else {
			if (vendaAlterada != null && vendaAlterada.getSituacao().equalsIgnoreCase("PROCESSO")) {
				work.setDataInscricao(new Date());
			}
		}
		work.setControle("Processo");
		work.setVendas(venda);
		WorkTravelFacade workTravelFacade = new WorkTravelFacade(); 
		work = workTravelFacade.salvar(work);
		return work;
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
					formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true, work.getDataInicioPretendida01()); 
		}
		
		Float valorPrevisto= 0.0f;
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
			ComissaoWorkBean cc = new ComissaoWorkBean(aplicacaoMB, venda,
					orcamento.getOrcamentoprodutosorcamentoList(), orcamento.getValorCambio(), work.getValoreswork(),
					formaPagamento.getParcelamentopagamentoList(), vendasComissao, valorJuros, true);
			valorPrevisto = cc.getVendasComissao().getValorfornecedor();
		} 
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControlWork(venda, work, valorPrevisto);
	}
	
	public String verificarDadosAlterado(Worktravel work, Worktravel workAlterado, Fornecedorcidade fornecedorCidade, Vendas vendaAlterada, float valorVendaAlterada) {
		String dadosAlterado = "";
		if (work.getCursoEstuda() != null) {
			if (!work.getCursoEstuda().equalsIgnoreCase(workAlterado.getCursoEstuda())) {
				dadosAlterado = dadosAlterado + "Curso que estuda : " + work.getCursoEstuda() + " | "
						+ workAlterado.getCursoEstuda() + "<br></br>";
			}
		}
		if (work.getEmailContatoEmergencia() != null) {
			if (!work.getEmailContatoEmergencia().equalsIgnoreCase(workAlterado.getEmailContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Email contato de emergencia : " + work.getEmailContatoEmergencia()
						+ " | " + workAlterado.getEmailContatoEmergencia() + "<br></br>";
			}
		}
		if (work.getFoneContatoEmergencia() != null) {
			if (!work.getFoneContatoEmergencia().equalsIgnoreCase(workAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Telefone contato de emergencia : " + work.getFoneContatoEmergencia()
						+ " | " + workAlterado.getFoneContatoEmergencia() + "<br></br>";
			}
		}
		if (work.getFuma() != null) {
			if (!work.getFuma().equalsIgnoreCase(workAlterado.getFuma())) {
				dadosAlterado = dadosAlterado + "Fuma : " + work.getFuma() + " | " + workAlterado.getFuma()
						+ "<br></br>";
			}
		}
		if (work.getNivelidioma01() != null) {
			if (!work.getNivelidioma01().equalsIgnoreCase(workAlterado.getNivelidioma01())) {
				dadosAlterado = dadosAlterado + "Nível Idíoma 01 : " + work.getNivelidioma01() + " | "
						+ workAlterado.getNivelidioma01() + "<br></br>";
			}
		}
		if (work.getNivelidioma02() != null) {
			if (!work.getNivelidioma02().equalsIgnoreCase(workAlterado.getNivelidioma02())) {
				dadosAlterado = dadosAlterado + "Nível Idíoma 02 : " + work.getNivelidioma02() + " | "
						+ workAlterado.getNivelidioma02() + "<br></br>";
			}
		}
		if (work.getNivelidioma03() != null) {
			if (!work.getNivelidioma03().equalsIgnoreCase(workAlterado.getNivelidioma03())) {
				dadosAlterado = dadosAlterado + "Nível Idíoma 03 : " + work.getNivelidioma03() + " | "
						+ workAlterado.getNivelidioma03() + "<br></br>";
			}
		}
		if (work.getNomeContatoEmergencia() != null) {
			if (!work.getNomeContatoEmergencia().equalsIgnoreCase(workAlterado.getNomeContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Nome contato de emergencia : " + work.getNomeContatoEmergencia()
						+ " | " + workAlterado.getNomeContatoEmergencia() + "<br></br>";
			}
		}
		if (work.getNumeroSocialSecurity() != null) {
			if (!work.getNumeroSocialSecurity().equalsIgnoreCase(workAlterado.getNumeroSocialSecurity())) {
				dadosAlterado = dadosAlterado + "Número Social Security : " + work.getNumeroSocialSecurity() + " | "
						+ workAlterado.getNumeroSocialSecurity() + "<br></br>";
			}
		}
		if (work.getPassagemAerea() != null) {
			if (!work.getPassagemAerea().equalsIgnoreCase(workAlterado.getPassagemAerea())) {
				dadosAlterado = dadosAlterado + "Passagem área : " + work.getPassagemAerea() + " | "
						+ workAlterado.getPassagemAerea() + "<br></br>";
			}
		}
		if (work.getDuracaoCurso() != null) {
			if (!work.getDuracaoCurso().equalsIgnoreCase(workAlterado.getDuracaoCurso())) {
				dadosAlterado = dadosAlterado + "Duração do curso que estuda : " + work.getDuracaoCurso() + " | "
						+ workAlterado.getDuracaoCurso() + "<br></br>";
			}
		}
		if (work.getListaAlergias() != null) {
			if (!work.getListaAlergias().equalsIgnoreCase(workAlterado.getListaAlergias())) {
				dadosAlterado = dadosAlterado + "Alergias : " + work.getListaAlergias() + " | "
						+ workAlterado.getListaAlergias() + "<br></br>";
			}
		}
		if (work.getPossuiDeficienciaFisica() != null) {
			if (!work.getPossuiDeficienciaFisica().equalsIgnoreCase(workAlterado.getPossuiDeficienciaFisica())) {
				dadosAlterado = dadosAlterado + "Possui deficiencia : " + work.getPossuiDeficienciaFisica() + " | "
						+ workAlterado.getPossuiDeficienciaFisica() + "<br></br>";
			}
		}
		if (work.getProblemaSaude() != null) {
			if (!work.getProblemaSaude().equalsIgnoreCase(workAlterado.getProblemaSaude())) {
				dadosAlterado = dadosAlterado + "Possui problema de saúde : " + work.getProblemaSaude() + " | "
						+ workAlterado.getProblemaSaude() + "<br></br>";
			}
		}
		if (work.getRelacaoContatoEmergencia() != null) {
			if (!work.getRelacaoContatoEmergencia().equalsIgnoreCase(workAlterado.getRelacaoContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Relação contato de emergencia : " + work.getRelacaoContatoEmergencia()
						+ " | " + workAlterado.getRelacaoContatoEmergencia() + "<br></br>";
			}
		}
		if (work.getTratamentoUsoDrogas() != null) {
			if (!work.getTratamentoUsoDrogas().equalsIgnoreCase(workAlterado.getTratamentoUsoDrogas())) {
				dadosAlterado = dadosAlterado + "Tratamento com uso de drogas : " + work.getTratamentoUsoDrogas()
						+ " | " + workAlterado.getTratamentoUsoDrogas() + "<br></br>";
			}
		}

		if (work.getTrabalhoVistoJ1() != null) {
			if (!work.getTrabalhoVistoJ1().equalsIgnoreCase(workAlterado.getTrabalhoVistoJ1())) {
				dadosAlterado = dadosAlterado + "J1 : " + work.getTrabalhoVistoJ1() + " | "
						+ workAlterado.getTrabalhoVistoJ1() + "<br></br>";
			}
		}
		if (fornecedorCidade.getIdfornecedorcidade() != vendaAlterada.getFornecedorcidade().getIdfornecedorcidade()) {
			dadosAlterado = dadosAlterado + "Fornecedor : " + fornecedorCidade.getCidade().getNome() + " | "
					+ vendaAlterada.getFornecedorcidade().getFornecedor().getNome() + "<br></br>";
		}

		if (valorVendaAlterada != venda.getValor()) {
			dadosAlterado = dadosAlterado + "Valor Total : " + Formatacao.formatarFloatString(venda.getValor()) + " | "
					+ Formatacao.formatarFloatString(valorVendaAlterada) + "<br></br>";
		}
		if (work.getCampanheiroViagem01() != null) {
			if (!work.getCampanheiroViagem01().equalsIgnoreCase(workAlterado.getCampanheiroViagem01())) {
				dadosAlterado = dadosAlterado + "1- Companheiro de viagem : " + work.getCampanheiroViagem01() + " | "
						+ workAlterado.getCampanheiroViagem01() + "<br></br>";
			}
		}
		if (work.getCompanheiroViagem02() != null) {
			if (!work.getCompanheiroViagem02().equalsIgnoreCase(workAlterado.getCompanheiroViagem02())) {
				dadosAlterado = dadosAlterado + "2- Companheiro de viagem : " + work.getCompanheiroViagem02() + " | "
						+ workAlterado.getCompanheiroViagem02() + "<br></br>";
			}
		}
		if (work.getCompanheiroViagem03() != null) {
			if (!work.getCompanheiroViagem03().equalsIgnoreCase(workAlterado.getCompanheiroViagem03())) {
				dadosAlterado = dadosAlterado + "3- Companheiro de viagem : " + work.getCompanheiroViagem03() + " | "
						+ workAlterado.getCompanheiroViagem03() + "<br></br>";
			}
		}
		if (work.getEmailContatoEmergencia() != null) {
			if (!work.getEmailContatoEmergencia().equalsIgnoreCase(workAlterado.getEmailContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Email contato de emergencia : " + work.getEmailContatoEmergencia()
						+ " | " + workAlterado.getEmailContatoEmergencia() + "<br></br>";
			}
		}

		if (work.getFoneContatoEmergencia() != null) {
			if (!work.getFoneContatoEmergencia().equalsIgnoreCase(workAlterado.getFoneContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Fone contato de emergencia : " + work.getFoneContatoEmergencia()
						+ " | " + workAlterado.getFoneContatoEmergencia() + "<br></br>";
			}
		}

		if (work.getRelacaoContatoEmergencia() != null) {
			if (!work.getRelacaoContatoEmergencia().equalsIgnoreCase(workAlterado.getRelacaoContatoEmergencia())) {
				dadosAlterado = dadosAlterado + "Relação contato de emergencia : " + work.getRelacaoContatoEmergencia()
						+ " | " + workAlterado.getRelacaoContatoEmergencia() + "<br></br>";
			}
		}
		if (work.getPassagemAerea() != null) {
			if (!work.getPassagemAerea().equalsIgnoreCase(workAlterado.getPassagemAerea())) {
				dadosAlterado = dadosAlterado + "Passagem aérea : " + work.getPassagemAerea() + " | "
						+ workAlterado.getPassagemAerea() + "<br></br>";
			}
		}
		if (work.getCartaoVTM() != null) {
			if (!work.getCartaoVTM().equalsIgnoreCase(workAlterado.getCartaoVTM())) {
				dadosAlterado = dadosAlterado + "Cartão VTM : " + work.getCartaoVTM() + " | "
						+ workAlterado.getCartaoVTM() + "<br></br>";
			}
		}
		if (work.getMoedaCartaoVTM() != null) {
			if (!work.getMoedaCartaoVTM().equalsIgnoreCase(workAlterado.getMoedaCartaoVTM())) {
				dadosAlterado = dadosAlterado + "Moeda Cartão VTM : " + work.getMoedaCartaoVTM() + " | "
						+ workAlterado.getMoedaCartaoVTM() + "<br></br>";
			}
		}
		if (work.getNumeroCartaoVTM() != null) {
			if (!work.getNumeroCartaoVTM().equalsIgnoreCase(workAlterado.getNumeroCartaoVTM())) {
				dadosAlterado = dadosAlterado + "Número Cartão VTM : " + work.getNumeroCartaoVTM() + " | "
						+ workAlterado.getNumeroCartaoVTM() + "<br></br>";
			}
		}
		if (work.getNomeEmpregador() != null) {
			if (!work.getNomeEmpregador().equalsIgnoreCase(workAlterado.getNomeEmpregador())) {
				dadosAlterado = dadosAlterado + "Nome empregador : " + work.getNomeEmpregador() + " | "
						+ workAlterado.getNomeEmpregador() + "<br></br>";
			}
		}
		if (work.getFuma() != null) {
			if (!work.getFuma().equalsIgnoreCase(workAlterado.getFuma())) {
				dadosAlterado = dadosAlterado + "Fumante : " + work.getFuma() + " | " + workAlterado.getFuma()
						+ "<br></br>";
			}
		}
		return dadosAlterado;
	}
	

}
