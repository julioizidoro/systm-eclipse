package br.com.travelmate.managerBean.traducaojuramentada;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
 
import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.DepartamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.TraducaoJuramentadaFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controlealteracoes;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Departamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Traducaojuramentada;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadTraducaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private MateRunnersMB mateRunnersMB;
	@Inject
	private DashBoardMB dashBoardMB;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Parcelamentopagamento parcelamentopagamento;
	private Orcamento orcamento;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Paisproduto paisProduto;
	private List<Paisproduto> listaPaisProduto;
	private Cidadepaisproduto cidadepaisproduto;
	private List<Cidadepaisproduto> listaCidade;
	private Cliente cliente;
	private Produtos produto;
	private boolean novaFicha = false;
	private boolean editarFicha = false;
	private float valorSaldoParcelar;
	private Controlealteracoes controlealteracoes;
	private Departamentoproduto depPrograma;
	private Departamento depFinanceiro;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamantoPagamentoAntiga;
	private Cancelamento cancelamento;
	private Lead lead;
	private Traducaojuramentada traducao;
	private String vendaMatriz;
	private float valorVendaAlterar;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		traducao = (Traducaojuramentada) session.getAttribute("traducaojuramentada");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		vendaMatriz = (String) session.getAttribute("vendaMatriz");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("traducaojuramentada");
		session.removeAttribute("vendaMatriz");
		ProdutoFacade produtoFacade = new ProdutoFacade();
		produto = produtoFacade.consultar(aplicacaoMB.getParametrosprodutos().getTraducaojuramentada());
		if (traducao == null) {
			iniciarNovo();
			novaFicha = true;
		} else {
			alterarTraducao();
			editarFicha = true;
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return mateRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB mateRunnersMB) {
		this.mateRunnersMB = mateRunnersMB;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Parcelamentopagamento getParcelamentopagamento() {
		return parcelamentopagamento;
	}

	public void setParcelamentopagamento(Parcelamentopagamento parcelamentopagamento) {
		this.parcelamentopagamento = parcelamentopagamento;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Paisproduto getPaisProduto() {
		return paisProduto;
	}

	public void setPaisProduto(Paisproduto paisProduto) {
		this.paisProduto = paisProduto;
	}

	public List<Paisproduto> getListaPaisProduto() {
		return listaPaisProduto;
	}

	public void setListaPaisProduto(List<Paisproduto> listaPaisProduto) {
		this.listaPaisProduto = listaPaisProduto;
	}

	public Cidadepaisproduto getCidadepaisproduto() {
		return cidadepaisproduto;
	}

	public void setCidadepaisproduto(Cidadepaisproduto cidadepaisproduto) {
		this.cidadepaisproduto = cidadepaisproduto;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public boolean isEditarFicha() {
		return editarFicha;
	}

	public void setEditarFicha(boolean editarFicha) {
		this.editarFicha = editarFicha;
	}

	public float getValorSaldoParcelar() {
		return valorSaldoParcelar;
	}

	public void setValorSaldoParcelar(float valorSaldoParcelar) {
		this.valorSaldoParcelar = valorSaldoParcelar;
	}

	public Controlealteracoes getControlealteracoes() {
		return controlealteracoes;
	}

	public void setControlealteracoes(Controlealteracoes controlealteracoes) {
		this.controlealteracoes = controlealteracoes;
	}

	public MateRunnersMB getMateRunnersMB() {
		return mateRunnersMB;
	}

	public void setMateRunnersMB(MateRunnersMB mateRunnersMB) {
		this.mateRunnersMB = mateRunnersMB;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public Departamentoproduto getDepPrograma() {
		return depPrograma;
	}

	public void setDepPrograma(Departamentoproduto depPrograma) {
		this.depPrograma = depPrograma;
	}

	public Departamento getDepFinanceiro() {
		return depFinanceiro;
	}

	public void setDepFinanceiro(Departamento depFinanceiro) {
		this.depFinanceiro = depFinanceiro;
	}

	public List<String> getListaTipoParcelamento() {
		return listaTipoParcelamento;
	}

	public void setListaTipoParcelamento(List<String> listaTipoParcelamento) {
		this.listaTipoParcelamento = listaTipoParcelamento;
	}

	public List<Parcelamentopagamento> getListaParcelamentoPagamentoOriginal() {
		return listaParcelamentoPagamentoOriginal;
	}

	public void setListaParcelamentoPagamentoOriginal(List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal) {
		this.listaParcelamentoPagamentoOriginal = listaParcelamentoPagamentoOriginal;
	}

	public List<Parcelamentopagamento> getListaParcelamantoPagamentoAntiga() {
		return listaParcelamantoPagamentoAntiga;
	}

	public void setListaParcelamantoPagamentoAntiga(List<Parcelamentopagamento> listaParcelamantoPagamentoAntiga) {
		this.listaParcelamantoPagamentoAntiga = listaParcelamantoPagamentoAntiga;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public Traducaojuramentada getTraducao() {
		return traducao;
	}

	public void setTraducao(Traducaojuramentada traducao) {
		this.traducao = traducao;
	}

	public String getVendaMatriz() {
		return vendaMatriz;
	}

	public void setVendaMatriz(String vendaMatriz) {
		this.vendaMatriz = vendaMatriz;
	}

	public void listarCidade() {
		if (produto != null && paisProduto != null) {
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			listaCidade = cidadePaisProdutosFacade
					.listar("SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.idpaisproduto="
							+ paisProduto.getIdpaisproduto());
		}
	}

	public void listarFornecedorCidade() {
		if (produto != null && cidadepaisproduto != null) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(produto.getIdprodutos(),
					cidadepaisproduto.getCidade().getIdcidade());
		}
	}

	public void alterarTraducao() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		listaPaisProduto = paisProdutoFacade.listar(produto.getIdprodutos());
		venda = traducao.getVendas();
		parcelamentopagamento = new Parcelamentopagamento();
		valorVendaAlterar = venda.getValor();
		paisProduto = paisProdutoFacade.consultar("SELECT p FROM Paisproduto p WHERE p.pais.idpais="
				+ venda.getFornecedorcidade().getCidade().getPais().getIdpais() + " and p.produtos.idprodutos="
				+ produto.getIdprodutos());
		listarCidade();
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		cidadepaisproduto = cidadePaisProdutosFacade.consultar("SELECT c FROM Cidadepaisproduto c WHERE"
				+ " c.paisproduto.idpaisproduto=" + paisProduto.getIdpaisproduto() + " and c.cidade.idcidade="
				+ venda.getFornecedorcidade().getCidade().getIdcidade());
		listarFornecedorCidade();
		fornecedorCidade = venda.getFornecedorcidade();
		cliente = venda.getCliente();
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		orcamento = orcamentoFacade.consultar(venda.getIdvendas());
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		this.formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
		if (formaPagamento != null) {
			carregarCamposFormaPagamento();
			listaParcelamantoPagamentoAntiga = new ArrayList<>();
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				Parcelamentopagamento parcelamentopagamento = new Parcelamentopagamento();
				parcelamentopagamento
						.setDiaVencimento(formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento());
				parcelamentopagamento
						.setFormaPagamento(formaPagamento.getParcelamentopagamentoList().get(i).getFormaPagamento());
				parcelamentopagamento
						.setNumeroParcelas(formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas());
				parcelamentopagamento
						.setValorParcela(formaPagamento.getParcelamentopagamentoList().get(i).getValorParcela());
				parcelamentopagamento.setValorParcelamento(
						formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento());
				parcelamentopagamento.setTipoParcelmaneto(
						formaPagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto());
				listaParcelamantoPagamentoAntiga.add(parcelamentopagamento);
			}
		}
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		parcelamentopagamento.setFormaPagamento("sn");
	}

	public void carregarCamposFormaPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
	}

	public void calcularParcelamentoPagamento() {
		Float valorParcelado = 0.0f;
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				valorParcelado = valorParcelado
						+ formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
			}
		}
		valorSaldoParcelar = venda.getValor() - valorParcelado;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
	}

	public void excluirFormaPagamento(String ilinha) {
		gerarListaParcelamentoOriginal();
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			if (formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento() != null) {
				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
				parcelamentoPagamentoFacade.excluir(
						formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
			}
			ContasReceberBean contasReceberBean = new ContasReceberBean();
			if (venda.getIdvendas() != null) {
				if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha),
							venda.getIdvendas(), usuarioLogadoMB, formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
				}
			}
			if (contasReceberBean.getValorJaRecebido() > 0) {
				Parcelamentopagamento parcelamento = new Parcelamentopagamento();
				parcelamento.setDiaVencimento(new Date());
				parcelamento.setFormaPagamento("Saldo pago");

				parcelamento.setNumeroParcelas(01);
				parcelamento.setTipoParcelmaneto("Matriz");
				parcelamento.setValorParcela(contasReceberBean.getValorJaRecebido());
				parcelamento.setValorParcelamento(contasReceberBean.getValorJaRecebido());
				if (formaPagamento != null) {
					parcelamento.setFormapagamento(formaPagamento);
				}
				if (formaPagamento.getParcelamentopagamentoList() == null) {
					formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamento);
			}
			formaPagamento.getParcelamentopagamentoList().remove(linha);
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
			parcelamentopagamento.setValorParcela(0.0f);
		}
	}

	public void gerarListaParcelamentoOriginal() {
		if (venda.getIdvendas() != null) {
			if (listaParcelamentoPagamentoOriginal == null) {
				if (formaPagamento.getParcelamentopagamentoList() != null) {
					listaParcelamentoPagamentoOriginal = new ArrayList<Parcelamentopagamento>();
					listaParcelamentoPagamentoOriginal = formaPagamento.getParcelamentopagamentoList();
				}
			}
		}
	}

	public void adicionarFormaPagamento() {
		gerarListaParcelamentoOriginal();
		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			if (parcelamentopagamento.getValorParcela() > parcelamentopagamento.getValorParcelamento()) {
				Mensagem.lancarMensagemErro("Atenção", "Valor das parcelas maior que o valor a parcelar");
			} else {
				if (formaPagamento.getParcelamentopagamentoList() == null) {
					formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
				}
				if (formaPagamento != null) {
					parcelamentopagamento.setFormapagamento(formaPagamento);
				}
				parcelamentopagamento.setValorParcelamento(parcelamentopagamento.getValorParcelamento());
				if (venda.getIdvendas() != null) {
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento,
								formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB);
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				parcelamentopagamento = new Parcelamentopagamento();
				calcularParcelamentoPagamento();
				parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
				parcelamentopagamento.setNumeroParcelas(01);
				parcelamentopagamento.setValorParcela(parcelamentopagamento.getValorParcelamento());
			}
		} else
			Mensagem.lancarMensagemErro(msg, "");
	}

	public String validarFormaPagamento() {
		String msg = "";
		if (formaPagamento.getCondicao() == null) {
			msg = msg + "Campo forma de pagamento obrigatório";
		}
		if (formaPagamento.getPossuiJuros().equalsIgnoreCase("sim")) {
			if (formaPagamento.getValorJuros() != null) {
				if (formaPagamento.getValorJuros() == 0) {
					msg = msg + "Informar valor do Juros";
				}
			}
		}
		if (parcelamentopagamento.getDiaVencimento() == null) {
			msg = msg + "Data do 1º Vencimento Obrigatorio";
		}
		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
		}
		if (parcelamentopagamento.getValorParcela() <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		if (parcelamentopagamento.getValorParcelamento() > (valorSaldoParcelar + 0.01)) {
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
		}
		return msg;
	}

	public void iniciarNovo() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		listaPaisProduto = paisProdutoFacade.listar(produto.getIdprodutos());
		traducao = new Traducaojuramentada();
		paisProduto = new Paisproduto();
		cidadepaisproduto = new Cidadepaisproduto();
		fornecedorCidade = new Fornecedorcidade();
		editarFicha = false;
		fornecedorCidade = new Fornecedorcidade();
		venda = new Vendas();
		venda.setDataVenda(new Date());
		venda.setSituacao("PROCESSO");
		formaPagamento = new Formapagamento();
		formaPagamento.setValorJuros(0.0f);
		formaPagamento.setValorOrcamento(0.0f);
		formaPagamento.setValorTotal(0.0f);
		formaPagamento.setPossuiJuros("Não");
		parcelamentopagamento = new Parcelamentopagamento();
		parcelamentopagamento.setFormaPagamento("sn");
		orcamento = new Orcamento();
		orcamento.setValorCambio(0.0f); 
		traducao.setValortraducao(0.0f);
		traducao.setComissaofranquia(0.0f);
		traducao.setAssessoriatm(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		traducao.setValortotal(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
	}

	public void calcularValorTotal() {
		formaPagamento.setValorTotal(0.0f);
		float total = 0.0f;
		if (traducao != null) {
			orcamento.setTotalMoedaEstrangeira(0.0f);
			orcamento.setTotalMoedaNacional(0.0f);
			if (traducao.getAssessoriatm() > 0) {
				total = total + traducao.getAssessoriatm();
			}
			if (traducao.getValortraducao() > 0) {
				total = total + traducao.getValortraducao();
			}
			if (traducao.getComissaofranquia() > 0) {
				total = total + traducao.getComissaofranquia();
			}
			traducao.setValortotal(total);
			venda.setValor(total);
			formaPagamento.setValorOrcamento(total);
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Não")) {
				formaPagamento.setValorJuros(0.0f);
			}
			venda.setValor(venda.getValor() + formaPagamento.getValorJuros());
			formaPagamento.setValorTotal(venda.getValor());
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		}
	}
	
	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}
	
	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}
	
	public void gerarListaTipoParcelamento() {
		listaTipoParcelamento = Formatacao.gerarListaTipoParcelamento(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().isParcelamentojoja(),
				parcelamentopagamento.getFormaPagamento(),
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
	}
	
	public String cancelar() {
		return "consTraducao";
	} 
	
	public boolean validarDados() {
		if(cliente==null || cliente.getIdcliente()==null) {
			Mensagem.lancarMensagemErro("Atenção!", "Cliente não informado.");
			return false;
		}else if (fornecedorCidade== null || fornecedorCidade.getIdfornecedorcidade()==null) {
			Mensagem.lancarMensagemErro("Atenção!", "Parceiro não informado.");
			return false;
		}if (formaPagamento.getParcelamentopagamentoList() == null) {
			Mensagem.lancarMensagemErro("Atenção!", "Forma de pagamento com erro.");
			return false;
		} else {
			if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
				Mensagem.lancarMensagemErro("Atenção!", "Forma de pagamento com erro.");
				return false;
			}
		} 
		double saldoParcelar = this.valorSaldoParcelar;
		if (saldoParcelar > 0.01) {
			Mensagem.lancarMensagemErro("Atenção!", "Forma de Pagamento possui saldo a parcelar em aberto.");
			return false; 
		}
		return true;
	}
	
	public String salvar() {
		if(validarDados()) {
			String nsituacao= "FINALIZADA";
			if (venda.getIdvendas() != null) {
				nsituacao = venda.getSituacao();
			}
			if ((nsituacao.equalsIgnoreCase("")) || (nsituacao.equalsIgnoreCase("PROCESSO"))) {
				boolean verificaParcelamento = false;
				verificaParcelamento = Formatacao
						.veririfcarParcelamento(formaPagamento.getParcelamentopagamentoList());
				venda.setRestricaoparcelamento(verificaParcelamento);
				if (verificaParcelamento) {
					nsituacao = "PROCESSO";
					Mensagem.lancarMensagemWarn("Data Vencimento",
							"As parcelas possuem data de vencimento após o inicio do programa. Entrar em contato com Financeiro");
				}   
			}
			Date dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio), 8);
			ProgramasBean programasBean = new ProgramasBean(); 
			venda.setVendasMatriz(vendaMatriz);
			venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
					formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
					lead);
			traducao.setVendas(venda);
			TraducaoJuramentadaFacade juramentadaFacade = new TraducaoJuramentadaFacade();
			traducao = juramentadaFacade.salvar(traducao);
			orcamento = programasBean.salvarOrcamento(orcamento, cambio, traducao.getValortotal(), 0.0f, 0.0f, venda,
					"N");
			formaPagamento = programasBean.salvarFormaPagamento(formaPagamento, venda);
			salvarCliente();
			if (cancelamento != null) {
				programasBean.salvarCancelamentoVenda(venda, cancelamento);
			}
			if (novaFicha) {
				ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
						formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false);
			}
			if (editarFicha) {
				if (listaParcelamantoPagamentoAntiga != null && formaPagamento.getParcelamentopagamentoList() != null) {
					programasBean.salvarAlteracaoFinanceiro(venda, listaParcelamantoPagamentoAntiga,
							formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB.getUsuario());
				}
			}
			salvarPontuacao();
			DepartamentoProdutoFacade departamentoProdutoFacade = new DepartamentoProdutoFacade();
			depPrograma = departamentoProdutoFacade.consultar(venda.getProdutos().getIdprodutos());
			if (depPrograma != null) {
				String titulo = "Nova Ficha - Tradução Juramentada";
				String operacao = "A";
				String imagemNotificacao = "inserido";
				if(editarFicha) {
					titulo = "Ficha Alterada - Tradução Juramentada";
					operacao = "I";
					imagemNotificacao = "alterado";
				}
				String vm = "Venda pela Matriz";
				if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
					vm = "Venda pela Loja";
				}
				Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(), cliente.getNome(),
						venda.getFornecedorcidade().getFornecedor().getNome(),
						Formatacao.ConvercaoDataPadrao(new Date()),
						venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
						venda.getCambio().getMoedas().getSigla(), operacao, depPrograma.getDepartamento(),
						imagemNotificacao, "I"); 
			}
			Mensagem.lancarMensagemInfo("Tradução Juramentada salvo com Sucesso", "");
			return "consTraducao";
		}
		return "";
	}
	
	public void salvarCliente() {
		cliente.setTipoCliente("Fechado");
		cliente.setDataInscricao(new Date());
		ClienteFacade clienteFacade = new ClienteFacade();
		cliente = clienteFacade.salvar(cliente);
	}
	
	public void salvarPontuacao() {
		if (novaFicha) {  
			if (venda.getVendasMatriz().equalsIgnoreCase("S")) {
				dashBoardMB.getVendaproduto().setProduto(dashBoardMB.getVendaproduto().getProduto() + 1);
				dashBoardMB.getMetamensal().setValoralcancado(
						dashBoardMB.getMetamensal().getValoralcancado() + venda.getValor());
				dashBoardMB.getMetamensal()
						.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
								/ dashBoardMB.getMetamensal().getValormeta()) * 100);

				dashBoardMB.getMetaAnual().setMetaalcancada(
						dashBoardMB.getMetaAnual().getMetaalcancada() + venda.getValor());
				dashBoardMB.getMetaAnual()
						.setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
								/ dashBoardMB.getMetaAnual().getValormeta()) * 100);

				dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() + venda.getValor());
				dashBoardMB.setPercsemana((dashBoardMB.getMetaparcialsemana()
						/ dashBoardMB.getMetamensal().getValormetasemana()) * 100);

				float valor = dashBoardMB.getMetamensal().getValoralcancado();
				dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));

				DashBoardBean dashBoardBean = new DashBoardBean();
				dashBoardBean.calcularNumeroVendasProdutos(venda, false);
				dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
				dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
				int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, "", false);
				venda.setPonto(pontos[0]);
				venda.setPontoescola(pontos[1]);
				VendasFacade vendaFacade = new VendasFacade();
				venda = vendaFacade.salvar(venda);
				mateRunnersMB.carregarListaRunners(); 
			}
		} else if (valorVendaAlterar != venda.getValor()) {
			int mes = Formatacao.getMesData(new Date()) + 1;
			int mesVenda = Formatacao.getMesData(venda.getDataVenda()) + 1;
			if (mes == mesVenda) { 
					dashBoardMB.getMetamensal().setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado()
							- valorVendaAlterar + venda.getValor());
					dashBoardMB.getMetamensal()
							.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
									/ dashBoardMB.getMetamensal().getValormeta()) * 100); 
					dashBoardMB.getMetaAnual().setMetaalcancada(
							dashBoardMB.getMetaAnual().getMetaalcancada() - valorVendaAlterar + venda.getValor());
					dashBoardMB.getMetaAnual().setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
							/ dashBoardMB.getMetaAnual().getValormeta()) * 100);

					dashBoardMB.setMetaparcialsemana(
							dashBoardMB.getMetaparcialsemana() - valorVendaAlterar + venda.getValor());
					dashBoardMB.setPercsemana(
							(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana())
									* 100); 
					float valor = dashBoardMB.getMetamensal().getValoralcancado();
					dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor)); 
					DashBoardBean dashBoardBean = new DashBoardBean();
					dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
					dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
					int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, "", false);
					venda.setPonto(pontos[0]);
					venda.setPontoescola(pontos[1]);
					VendasFacade vendaFacade = new VendasFacade();
					venda = vendaFacade.salvar(venda);
					mateRunnersMB.carregarListaRunners();
				} 
		}
	}
	
	public void calcularValorParcelas() {
		if (parcelamentopagamento.getValorParcelamento() > 0) {
			parcelamentopagamento.setValorParcela(
					parcelamentopagamento.getValorParcelamento() / parcelamentopagamento.getNumeroParcelas());
		}
	}

}
