package br.com.travelmate.managerBean.voluntariado;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.travelmate.bean.ConsultaBean;
import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoSeguroBean;
import br.com.travelmate.bean.comissao.ComissaoVoluntariadoBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.ValorSeguroFacade;

import br.com.travelmate.facade.VoluntariadoFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controleseguro; 
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Voluntariado; 
import br.com.travelmate.util.Formatacao;

public class CadVoluntariadoBean {
	
	private ProgramasBean programasBean;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Orcamento orcamento;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Voluntariado voluntariado;
	private float valorSeguroAntigo;
	

	public CadVoluntariadoBean(Vendas venda, Formapagamento formaPagamento, Orcamento orcamento, UsuarioLogadoMB usuarioLogadoMB, float valorSeguroAntigo) {
		this.programasBean =  new ProgramasBean();
		this.venda = venda;
		this.formaPagamento= formaPagamento;
		this.orcamento = orcamento;
		this.usuarioLogadoMB = usuarioLogadoMB;
		this.valorSeguroAntigo = valorSeguroAntigo;
	}
	
	public void SalvarAlteracaoFinanceiro(List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga,
			List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal){ 
		if (listaParcelamentoPagamentoOriginal != null) {
			programasBean.salvarAlteracaoFinanceiro(venda,listaParcelamentoPagamentoAntiga, listaParcelamentoPagamentoOriginal,
				venda.getUsuario());
		} 
	}
	
	
	public Voluntariado salvarVoluntariado(Voluntariado voluntariado, Vendas vendaAlterada) {
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		voluntariado = voluntariadoFacade.salvar(voluntariado);
		this.voluntariado=voluntariado;
		return voluntariado;
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
					formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true, voluntariado.getDataInicio());
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
				vendasComissao.setDatainicioprograma(voluntariado.getDataInicioVoluntariado());
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				float valorJuros = 0.0f;
				if (venda.getFormapagamento() != null) {
					valorJuros = venda.getFormapagamento().getValorJuros();
				}
				ComissaoVoluntariadoBean cc = new ComissaoVoluntariadoBean(aplicacaoMB, venda,
						orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao,
						formaPagamento.getParcelamentopagamentoList(), voluntariado.getDataInicioVoluntariado(), vendasComissao,
						valorJuros, true);
				valorPrevisto = cc.getVendasComissao().getValorfornecedor();
			}
		}
		ControlerBean controlerBean = new ControlerBean();
		controlerBean.salvarControleVoluntariado(venda, voluntariado, valorPrevisto);
	}
	
	public String verificarDadosAlterado(Voluntariado voluntariado, Voluntariado voluntariadoAlterado, Fornecedorcidade fornecedorCidade, Vendas vendaAlterada, float valorVendaAlterada, Seguroviagem seguroViagem, Seguroviagem seguroViagemAlterado) {
		String dadosAlterado = "";
		if (voluntariado.getIdiomaEstudar() != null) {
			if (!voluntariado.getIdiomaEstudar().equalsIgnoreCase(voluntariadoAlterado.getIdiomaEstudar())) {
				dadosAlterado = "Idioma que irá Estudar : " + voluntariado.getIdiomaEstudar() + " | "
						+ voluntariadoAlterado.getIdiomaEstudar() + "<br></br>";
			}
		}
		if (voluntariado.getNivelIdiomaEstudar() != null) {
			if (!voluntariado.getNivelIdiomaEstudar().equalsIgnoreCase(voluntariadoAlterado.getNivelIdiomaEstudar())) {
				dadosAlterado = dadosAlterado + "Nível Conhecimento : " + voluntariado.getNivelIdiomaEstudar() + " | "
						+ voluntariadoAlterado.getNivelIdiomaEstudar() + "<br></br>";
			}
		}
		if (voluntariado.getCurso() != null) {
			if (!voluntariado.getCurso().equalsIgnoreCase(voluntariadoAlterado.getCurso())) {
				dadosAlterado = dadosAlterado + "Curso : " + voluntariado.getCurso() + " | "
						+ voluntariadoAlterado.getCurso() + "<br></br>";
			}
		}

		if (voluntariado.getDataInicio() != null) {
			if (!voluntariado.getDataInicio().equals(voluntariadoAlterado.getDataInicio())) {
				dadosAlterado = dadosAlterado + "Data Inicio : "
						+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataInicio()) + " | "
						+ Formatacao.ConvercaoDataPadrao(voluntariadoAlterado.getDataInicio()) + "<br></br>";
			}
		}
		if (voluntariado.getNumeroSemanas() != null) {
			if (voluntariado.getNumeroSemanas() != voluntariadoAlterado.getNumeroSemanas()) {
				dadosAlterado = dadosAlterado + "Nº Semanas : " + String.valueOf(voluntariado.getNumeroSemanas())
						+ " | " + String.valueOf(voluntariadoAlterado.getNumeroSemanas()) + "<br></br>";
			}
		}
		if (voluntariado.getDataTermino() != null) {
			if (!voluntariado.getDataTermino().equals(voluntariadoAlterado.getDataTermino())) {
				dadosAlterado = dadosAlterado + "Data Término : "
						+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataTermino()) + " | "
						+ Formatacao.ConvercaoDataPadrao(voluntariadoAlterado.getDataTermino()) + "<br></br>";
			}
		}

		if (voluntariado.getAulasporSemana() != null) {
			if (!voluntariado.getAulasporSemana().equals(voluntariadoAlterado.getAulasporSemana())) {
				dadosAlterado = dadosAlterado + "Aula por Semana : " + voluntariado.getAulasporSemana() + " | "
						+ voluntariadoAlterado.getAulasporSemana() + "<br></br>";
			}
		}
		if (voluntariado.getTipoAcomodacao() != null) {
			if (!voluntariado.getTipoAcomodacao().equalsIgnoreCase(voluntariadoAlterado.getTipoAcomodacao())) {
				dadosAlterado = dadosAlterado + "Tipo Acomodação: " + voluntariado.getTipoAcomodacao() + " | "
						+ voluntariadoAlterado.getTipoAcomodacao() + "<br></br>";
			}
		}
		if (voluntariado.getNumeroSemanasAcomodacao() != null) {
			if (voluntariado.getNumeroSemanasAcomodacao() != voluntariadoAlterado.getNumeroSemanasAcomodacao()) {
				dadosAlterado = dadosAlterado + "Nº Semanas Acomodação : "
						+ String.valueOf(voluntariado.getNumeroSemanasAcomodacao()) + " | "
						+ String.valueOf(voluntariadoAlterado.getNumeroSemanasAcomodacao()) + "<br></br>";
			}
		}
		if (voluntariado.getTipoQuarto() != null) {
			if (!voluntariado.getTipoQuarto().equalsIgnoreCase(voluntariadoAlterado.getTipoQuarto())) {
				dadosAlterado = dadosAlterado + "Quarto : " + voluntariado.getTipoQuarto() + " | "
						+ voluntariadoAlterado.getTipoQuarto() + "<br></br>";
			}
		}
		if (voluntariado.getRefeicoes() != null) {
			if (!voluntariado.getRefeicoes().equalsIgnoreCase(voluntariadoAlterado.getRefeicoes())) {
				dadosAlterado = dadosAlterado + "Refeição : " + voluntariado.getRefeicoes() + " | "
						+ voluntariadoAlterado.getRefeicoes() + "<br></br>";
			}
		}
		if (voluntariado.getDataChegadaAcomodacao() != null) {
			if (!voluntariado.getDataChegadaAcomodacao().equals(voluntariadoAlterado.getDataChegadaAcomodacao())) {
				dadosAlterado = dadosAlterado + "Data Chegada : "
						+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataChegadaAcomodacao()) + " | "
						+ Formatacao.ConvercaoDataPadrao(voluntariadoAlterado.getDataChegadaAcomodacao()) + "<br></br>";
			}
		}
		if (voluntariado.getDataPartidaAcomodacao() != null) {
			if (!voluntariado.getDataPartidaAcomodacao().equals(voluntariadoAlterado.getDataPartidaAcomodacao())) {
				dadosAlterado = dadosAlterado + "Data Partida : "
						+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataPartidaAcomodacao()) + " |  "
						+ Formatacao.ConvercaoDataPadrao(voluntariadoAlterado.getDataPartidaAcomodacao()) + "<br></br>";
			}
		}
		if (voluntariado.getAdicionais() != null) {
			if (!voluntariado.getAdicionais().equalsIgnoreCase(voluntariadoAlterado.getAdicionais())) {
				dadosAlterado = dadosAlterado + "Adicionais :  " + voluntariado.getAdicionais() + " | "
						+ voluntariadoAlterado.getAdicionais() + "<br></br>";
			}
		}
		if (voluntariado.getFamiliaCrianca() != null) {
			if (!voluntariado.getFamiliaCrianca().equalsIgnoreCase(voluntariadoAlterado.getFamiliaCrianca())) {
				dadosAlterado = dadosAlterado + "Prefere familia com criança : " + voluntariado.getFamiliaCrianca()
						+ " | " + voluntariadoAlterado.getFamiliaCrianca() + "<br></br>";
			}
		}
		if (voluntariado.getFamiliaAnimais() != null) {
			if (!voluntariado.getFamiliaAnimais().equalsIgnoreCase(voluntariadoAlterado.getFamiliaAnimais())) {
				dadosAlterado = dadosAlterado + "Com animais de estimação : " + voluntariado.getFamiliaAnimais() + " | "
						+ voluntariadoAlterado.getFamiliaAnimais() + "<br></br>";
			}
		}
		if (voluntariado.getFumante() != null) {
			if (!voluntariado.getFumante().equalsIgnoreCase(voluntariadoAlterado.getFumante())) {
				dadosAlterado = dadosAlterado + "Você é fumante : " + voluntariado.getFumante() + " | "
						+ voluntariadoAlterado.getFumante() + "<br></br>";
			}
		}
		if (voluntariado.getVegetariano() != null) {
			if (!voluntariado.getVegetariano().equalsIgnoreCase(voluntariadoAlterado.getVegetariano())) {
				dadosAlterado = dadosAlterado + "Você é vegetariano : " + voluntariado.getVegetariano() + " | "
						+ voluntariadoAlterado.getVegetariano() + "<br></br>";
			}
		}
		if (voluntariado.getHobbies() != null) {
			if (!voluntariado.getHobbies().equalsIgnoreCase(voluntariadoAlterado.getHobbies())) {
				dadosAlterado = dadosAlterado + "Quais seus interesses e  hobbies : " + voluntariado.getHobbies()
						+ " | " + voluntariadoAlterado.getHobbies() + "<br></br>";
			}
		}
		if (voluntariado.getPossuiAlergia() != null) {
			if (!voluntariado.getPossuiAlergia().equalsIgnoreCase(voluntariadoAlterado.getPossuiAlergia())) {
				dadosAlterado = dadosAlterado + "Tem alergia a algum medicamento ou alimento? Se sim, especifique  : "
						+ voluntariado.getPossuiAlergia() + " | " + voluntariadoAlterado.getPossuiAlergia()
						+ "<br></br>";
			}
		}
		if (voluntariado.getSolicitacoesEspeciais() != null) {
			if (!voluntariado.getSolicitacoesEspeciais()
					.equalsIgnoreCase(voluntariadoAlterado.getSolicitacoesEspeciais())) {
				dadosAlterado = dadosAlterado + "Solicitações Especiais : " + voluntariado.getSolicitacoesEspeciais()
						+ " | " + voluntariadoAlterado.getSolicitacoesEspeciais() + "<br></br>";
			}
		}
		if (voluntariado.getTransferin() != null) {
			if (!voluntariado.getTransferin().equalsIgnoreCase(voluntariadoAlterado.getTransferin())) {
				dadosAlterado = dadosAlterado + "Transfer in (Chegada) : " + voluntariado.getTransferin() + " | "
						+ voluntariadoAlterado.getTransferin() + "<br></br>";
			}
		}
		if (voluntariado.getTransferout() != null) {
			if (!voluntariado.getTransferout().equalsIgnoreCase(voluntariadoAlterado.getTransferout())) {
				dadosAlterado = dadosAlterado + "Transfer out (Partida) : " + voluntariado.getTransferout() + " | "
						+ voluntariadoAlterado.getTransferout() + "<br></br>";
			}
		}
		if (voluntariado.getPassagemAerea() != null) {
			if (!voluntariado.getPassagemAerea().equalsIgnoreCase(voluntariadoAlterado.getPassagemAerea())) {
				dadosAlterado = dadosAlterado + "Passagem Aérea quando não inclusa no programa : "
						+ voluntariado.getPassagemAerea() + " | " + voluntariadoAlterado.getPassagemAerea()
						+ "<br></br>";
			}
		}
		if (voluntariado.getObservacaoPassagem() != null) {
			if (!voluntariado.getObservacaoPassagem().equalsIgnoreCase(voluntariadoAlterado.getObservacaoPassagem())) {
				dadosAlterado = dadosAlterado + "Observação Passagem : " + voluntariado.getObservacaoPassagem() + " | "
						+ voluntariadoAlterado.getObservacaoPassagem() + "<br></br>";
			}
		}
		if (!seguroViagem.getPossuiSeguro().equalsIgnoreCase(seguroViagemAlterado.getPossuiSeguro())) {
			dadosAlterado = dadosAlterado + "Seguro Viagem : " + seguroViagem.getPossuiSeguro() + " | "
					+ seguroViagemAlterado.getPossuiSeguro() + "<br></br>";
		}

		if (seguroViagem.getSeguradora() != null) {
			if (!seguroViagem.getSeguradora().equalsIgnoreCase(seguroViagemAlterado.getSeguradora())) {
				dadosAlterado = dadosAlterado + "Fornecedor Seguro : " + seguroViagem.getSeguradora() + " | "
						+ seguroViagemAlterado.getSeguradora() + "<br></br>";
			}
		}
		if (seguroViagem.getValorSeguro() != null) {
			if (seguroViagem.getValorSeguro() != seguroViagemAlterado.getValorSeguro()) {
				dadosAlterado = dadosAlterado + "Valor Seguro : "
						+ Formatacao.formatarFloatString(seguroViagem.getValorSeguro())
						+ Formatacao.formatarFloatString(seguroViagemAlterado.getValorSeguro()) + "<br></br>";
			}
		}
		if (seguroViagem.getPlano() != null) {
			if (!seguroViagem.getPlano().equalsIgnoreCase(seguroViagemAlterado.getPlano())) {
				dadosAlterado = dadosAlterado + "Plano : " + seguroViagem.getPlano() + " | "
						+ seguroViagemAlterado.getPlano() + "<br></br>";
			}
		}
		if (seguroViagem.getDataInicio() != null) {
			if (!seguroViagem.getDataInicio().equals(seguroViagemAlterado.getDataInicio())) {
				dadosAlterado = dadosAlterado + "Data Início : " + seguroViagem.getDataInicio() + " | "
						+ seguroViagemAlterado.getDataInicio() + "<br></br>";
			}
		}
		if (seguroViagem.getNumeroSemanas() != null) {
			if (seguroViagem.getNumeroSemanas() != seguroViagemAlterado.getNumeroSemanas()) {
				dadosAlterado = dadosAlterado + "No. Semanas : " + String.valueOf(seguroViagem.getNumeroSemanas())
						+ " | " + String.valueOf(seguroViagemAlterado.getNumeroSemanas()) + "<br></br>";
			}
		}
		if (seguroViagem.getDataTermino() != null) {
			if (!seguroViagem.getDataTermino().equals(seguroViagemAlterado.getDataTermino())) {
				dadosAlterado = dadosAlterado + "Data Término : "
						+ Formatacao.ConvercaoDataPadrao(seguroViagem.getDataTermino()) + "| "
						+ Formatacao.ConvercaoDataPadrao(seguroViagemAlterado.getDataTermino()) + "<br></br>";
			}
		}
		if (voluntariado.getVistoConsular() != null) {
			if (!voluntariado.getVistoConsular().equalsIgnoreCase(voluntariadoAlterado.getVistoConsular())) {
				dadosAlterado = dadosAlterado + "Visto Consular : " + voluntariado.getVistoConsular() + " | "
						+ voluntariadoAlterado.getVistoConsular() + "<br></br>";
			}
		}
		if (voluntariado.getDataEntregaDocumentoVisto() != null) {
			if (!voluntariado.getDataEntregaDocumentoVisto()
					.equals(voluntariadoAlterado.getDataEntregaDocumentoVisto())) {
				dadosAlterado = dadosAlterado + "Data Entrega Documentos : "
						+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataEntregaDocumentoVisto()) + " | "
						+ Formatacao.ConvercaoDataPadrao(voluntariadoAlterado.getDataEntregaDocumentoVisto())
						+ "<br></br>";
			}
		}
		if (voluntariado.getObservacaoVistoConsultar() != null) {
			if (!voluntariado.getObservacaoVistoConsultar()
					.equalsIgnoreCase(voluntariado.getObservacaoVistoConsultar())) {
				dadosAlterado = dadosAlterado + "Observações Visto : " + voluntariado.getObservacaoVistoConsultar()
						+ " | " + voluntariadoAlterado.getObservacaoVistoConsultar() + "<br></br>";
			}
		}
		return dadosAlterado;
	}
	
	
	public void salvarSeguroViagem(Seguroviagem seguroViagem, AplicacaoMB aplicacaoMB, VendasDao vendasDao) {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		if (seguroViagem.getIdseguroViagem() == null) {
			seguroViagem.setControle("Voluntariado");
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			seguroViagem.setFornecedor(seguroViagem.getValoresseguro().getFornecedorcidade().getFornecedor());
			seguroViagem.setPlano(seguroViagem.getValoresseguro().getPlano());
			seguroViagem.setVendas(salvarVendaSeguroViagem(seguroViagem, aplicacaoMB, vendasDao));
		} else {
			if (seguroViagem.getIdvendacurso() > 0) {
				Vendas vendasSeguro = new Vendas();
				
				vendasSeguro = seguroViagem.getVendas();
				vendasSeguro.setSituacao("CANCELADA");
				vendasDao.salvar(vendasSeguro);
			} else {
				seguroViagem.setVendas(venda);
			}
			seguroViagem.setVendas(venda);
			Valoresseguro valoresSeguro = new Valoresseguro();
			FornecedorFacade fornecedorFacade = new FornecedorFacade();
			Fornecedor fornecedor = new Fornecedor();
			fornecedor = fornecedorFacade.consultar(5);
			seguroViagem.setFornecedor(fornecedor);
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			valoresSeguro = valorSeguroFacade.consultar(1);
			seguroViagem.setValoresseguro(valoresSeguro);
			seguroViagem.setIdvendacurso(0);
		}
		seguroViagem = seguroViagemFacade.salvar(seguroViagem);
		float valorJuros = 0.0f;
		if (venda.getFormapagamento() != null) {
			valorJuros = venda.getFormapagamento().getValorJuros();
		}
		if(seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")){ 
			ComissaoSeguroBean cc = new ComissaoSeguroBean(aplicacaoMB, seguroViagem.getVendas(),
					new ArrayList<Parcelamentopagamento>(), new Vendascomissao(), seguroViagem.getDescontoloja(),
					seguroViagem.getDescontomatriz(), valorJuros, false, formaPagamento,seguroViagem);
			salvarControleSeguro(seguroViagem);
		}
	}

	public Vendas salvarVendaSeguroViagem(Seguroviagem seguroViagem, AplicacaoMB aplicacaoMB, VendasDao vendasDao) {
		Vendas vendaSeguro = null;
		if ((seguroViagem != null) && (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim"))) {
			if ((seguroViagem.getVendas() == null) || (seguroViagem.getIdvendacurso() == 0)) {
				vendaSeguro = new Vendas();
				vendaSeguro
						.setProdutos(ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getSeguroPrivado()));
				vendaSeguro.setDataVenda(venda.getDataVenda());
				vendaSeguro.setCliente(venda.getCliente());
				vendaSeguro.setUnidadenegocio(venda.getUnidadenegocio());
				vendaSeguro.setUsuario(venda.getUsuario());
				if (vendaSeguro.getSituacao().equalsIgnoreCase("PROCESSO")) {
					vendaSeguro.setSituacaogerencia("P");
				}else {
					vendaSeguro.setSituacaogerencia("F");
				}
			} else
				vendaSeguro = seguroViagem.getVendas();
			vendaSeguro.setCambio(venda.getCambio());
			vendaSeguro.setFormapagamento(venda.getFormapagamento());
			vendaSeguro.setFornecedorcidade(seguroViagem.getValoresseguro().getFornecedorcidade());
			vendaSeguro.setFornecedor(venda.getFornecedorcidade().getFornecedor());
			vendaSeguro.setOrcamento(venda.getOrcamento());
			vendaSeguro.setSituacao(venda.getSituacao());
			vendaSeguro.setValor(seguroViagem.getValorSeguro());
			vendaSeguro.setVendasMatriz(venda.getVendasMatriz());
			vendaSeguro = vendasDao.salvar(vendaSeguro);
			float novaValorVenda = venda.getValor() - seguroViagem.getValorSeguro();
			venda.setValor(novaValorVenda);
			
			seguroViagem.setIdvendacurso(venda.getIdvendas());
		}
		DashBoardBean dashBoardBean = new DashBoardBean();
		dashBoardBean.calcularNumeroVendasProdutos(vendaSeguro, false);
		dashBoardBean.calcularMetaMensal(vendaSeguro, valorSeguroAntigo, false);
		dashBoardBean.calcularMetaAnual(vendaSeguro, valorSeguroAntigo, false);
		int nNumeroSemana = 0;
		if (voluntariado.isHabilitarCurso()) {
			nNumeroSemana = voluntariado.getNumeroSemanas();
		}
		int[] pontos = dashBoardBean.calcularPontuacao(venda, nNumeroSemana, "",
				false, venda.getUsuario());
		venda.setPonto(pontos[0]);
		venda.setPontoescola(pontos[1]);
		
		venda = vendasDao.salvar(venda);
		return vendaSeguro;
	}

	public void salvarControleSeguro(Seguroviagem seguroViagem) {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Controleseguro controle = seguroViagem.getControleseguro();
		if (controle == null) {
			controle = new Controleseguro();
			controle.setSeguroviagem(seguroViagem);
			controle.setEnvioVoucher("Não");
			controle.setObservacao(" ");
			controle.setFinalizado("Processo");
			controle.setSituacao("Processo");
			controle = seguroViagemFacade.salvarControle(controle);
		}
	}

}
