package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.bean.ConsultaBean;
import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.LogVendaFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Logvenda;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadHeInscricaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	@Inject
	private TmRaceMB tmRaceMB;
	private He he;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Parcelamentopagamento parcelamentopagamento;
	private Orcamento orcamento;
	private Cambio cambio;
	private Date dataCambio;
	private Fornecedorcidade fornecedorCidade;
	private Pais pais;
	private Cidade cidade;
	private Cliente cliente;
	private Moedas moeda;
	private List<Moedas> listaMoedas;
	private Orcamentoprodutosorcamento orcamentoprodutosorcamento;
	private Produtosorcamento produtosorcamento;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private Questionariohe questionarioHe;
	private List<Pais> listaPais;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private boolean consultaCambio = false;
	private float valorSaldoParcelar;
	private float valorVendaAlterada;
	private Cancelamento cancelamento;
	private boolean enviarFicha;
	private boolean novaFicha = false;
	private Vendas vendaAlterada;
	private boolean camposPathway;
	private boolean camposHe;
	private Lead lead;
	private String voltarControleVendas = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		questionarioHe = (Questionariohe) session.getAttribute("questionariohe");
		he = (He) session.getAttribute("he");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("he");
		session.removeAttribute("questionariohe");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();    
		cliente = questionarioHe.getCliente();
		carregarComboMoedas();
		gerarListaProdutos();
		if (he != null) {
			iniciarAlteracao();
		} else {
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
			iniciarNovo();
		}
	}

	public He getHe() {
		return he;
	}

	public void setHe(He he) {
		this.he = he;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Questionariohe getQuestionarioHe() {
		return questionarioHe;
	}

	public void setQuestionarioHe(Questionariohe questionarioHe) {
		this.questionarioHe = questionarioHe;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Vendas getVendaAlterada() {
		return vendaAlterada;
	}

	public void setVendaAlterada(Vendas vendaAlterada) {
		this.vendaAlterada = vendaAlterada;
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

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public Orcamentoprodutosorcamento getOrcamentoprodutosorcamento() {
		return orcamentoprodutosorcamento;
	}

	public void setOrcamentoprodutosorcamento(Orcamentoprodutosorcamento orcamentoprodutosorcamento) {
		this.orcamentoprodutosorcamento = orcamentoprodutosorcamento;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
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

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public boolean isConsultaCambio() {
		return consultaCambio;
	}

	public void setConsultaCambio(boolean consultaCambio) {
		this.consultaCambio = consultaCambio;
	}

	public float getValorSaldoParcelar() {
		return valorSaldoParcelar;
	}

	public void setValorSaldoParcelar(float valorSaldoParcelar) {
		this.valorSaldoParcelar = valorSaldoParcelar;
	}

	public boolean isEnviarFicha() {
		return enviarFicha;
	}

	public void setEnviarFicha(boolean enviarFicha) {
		this.enviarFicha = enviarFicha;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public float getValorVendaAlterada() {
		return valorVendaAlterada;
	}

	public void setValorVendaAlterada(float valorVendaAlterada) {
		this.valorVendaAlterada = valorVendaAlterada;
	}

	public boolean isCamposPathway() {
		return camposPathway;
	}

	public void setCamposPathway(boolean camposPathway) {
		this.camposPathway = camposPathway;
	}

	public boolean isCamposHe() {
		return camposHe;
	}

	public void setCamposHe(boolean camposHe) {
		this.camposHe = camposHe;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return metaRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB metaRunnersMB) {
		this.metaRunnersMB = metaRunnersMB;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public void excluirFormaPagamento(String ilinha) {
		gerarListaParcelamentoOriginal();
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			if (formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento() != null) {
				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
				parcelamentoPagamentoFacade.excluir(
						formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
				ContasReceberBean contasReceberBean = new ContasReceberBean();
				if (venda.getIdvendas()!=null){
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha), venda.getIdvendas(), usuarioLogadoMB,
								formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
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
			} else {
				formaPagamento.getParcelamentopagamentoList().remove(linha);
				calcularParcelamentoPagamento();
				parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
				parcelamentopagamento.setValorParcela(0.0f);
			}
		}
	}

	public String cancelar() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consquestionarioHe";
	}

	public void iniciarNovo() {
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		novaFicha =true;
		he = new He();
		orcamento = new Orcamento();
		orcamento.setValorCambio(0.0f);
		orcamento.setTotalMoedaEstrangeira(0.0f);
		orcamento.setTotalMoedaNacional(0.0f);
		orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		venda = questionarioHe.getVendas();
		venda.setDataVenda(new Date());
		venda.setSituacao("PROCESSO");
		formaPagamento = new Formapagamento();
		formaPagamento.setValorJuros(0.0f);
		formaPagamento.setValorOrcamento(0.0f);
		formaPagamento.setValorTotal(0.0f);
		formaPagamento.setPossuiJuros("Não");
		pais = new Pais();
		cidade = new Cidade();
		parcelamentopagamento = new Parcelamentopagamento();
		parcelamentopagamento.setFormaPagamento("sn");
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		consultaCambio = true;
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = produtoOrcamentoFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
		orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
		orcamentoprodutosorcamento.setValorMoedaNacional(0.0f);
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(aplicacaoMB.getParametrosprodutos().getAssessoriatmhe());
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
	}

	public void iniciarAlteracao() {
		this.venda = he.getVendas();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
			valorVendaAlterada = venda.getValor();
		}
		this.cliente = venda.getCliente();
		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		listarFornecedorCidade();
		fornecedorCidade = venda.getFornecedorcidade();
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		parcelamentopagamento = new Parcelamentopagamento();
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		this.formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
		if (formaPagamento != null) {
			carregarCamposFormaPagamento();
		}
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		orcamento = orcamentoFacade.consultar(venda.getIdvendas());
		if (orcamento != null) {
			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento() != aplicacaoMB.getParametrosprodutos().getIdtaxainscricaohe()) {
						if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() < 0) {
							orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
									orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1);
						}
					}
				}
			}

			dataCambio = orcamento.getCambio().getData();
			cambio = orcamento.getCambio();
			moeda = orcamento.getCambio().getMoedas();
			carregarCambio();
			calcularValorTotalOrcamento();
		}

		consultaCambio = true;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		parcelamentopagamento.setFormaPagamento("sn");
		if(he.getDatainicio()!=null){
			camposPathway=true;
		}
		if ((he.getCurso1()!=null && he.getCurso1().length()>0) || 
			(he.getCurso2()!=null && he.getCurso2().length()>0) || 
			(he.getCurso3()!=null && he.getCurso3().length()>0)){
			camposHe=true;   
		}
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getHighereducation() + " and f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
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

	public void carregarCambio() {
		CambioFacade cambioFacade = new CambioFacade();
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(new Date(), venda.getDataVenda());
			if (dias > 3) {
				Mensagem.lancarMensagemErro("Cambio alterado para o dia atual", "");
				cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
						cambio.getMoedas().getIdmoedas());
				if (cambio != null) {
					orcamento.setValorCambio(cambio.getValor());
					atualizarValoresProduto();
				}
			}
		}
	}

	public void calcularValorTotalOrcamento() {
		if (aplicacaoMB.getParametrosprodutos().isRemessaativa()) {
			calcularImpostoRemessa();
		}
		formaPagamento.setValorTotal(0.0f);
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			float valorTotal = 0.0f;
			orcamento.setTotalMoedaEstrangeira(0.0f);
			orcamento.setTotalMoedaNacional(0.0f);
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento() != aplicacaoMB.getParametrosprodutos().getIdtaxainscricaohe()) {
					float valorMoedaReal = 0.0f;
					float valorMoedaEstrangeira = 0.0f;
					int idProdutoOrcamento = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento();
					if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getDescontoloja()) {
						valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional()
								* -1;
						valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getValorMoedaEstrangeira() * -1;
					} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getDescontomatriz()) {
						valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional()
								* -1;
						valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getValorMoedaEstrangeira() * -1;
					} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getPromocaoescola()) {
						valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional()
								* -1;
						valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getValorMoedaEstrangeira() * -1;
					} else {
						valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional();
						valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getValorMoedaEstrangeira();
					}
					valorTotal = valorTotal + valorMoedaReal;
					orcamento.setTotalMoedaEstrangeira(orcamento.getTotalMoedaEstrangeira() + valorMoedaEstrangeira);
					orcamento.setTotalMoedaNacional(orcamento.getTotalMoedaNacional() + valorMoedaReal);
					venda.setValor(valorTotal);
					formaPagamento.setValorOrcamento(valorTotal);
				}
			}
			if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Não")) {
				formaPagamento.setValorJuros(0.0f);
			}
			venda.setValor(venda.getValor() + formaPagamento.getValorJuros());
			formaPagamento.setValorTotal(venda.getValor());
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getHighereducation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		float valorremessa = 0.0f;
		if (listaProdutoRemessa != null) {
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int idProduto = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento();
				for (int n = 0; n < listaProdutoRemessa.size(); n++) {
					int idRemessa = listaProdutoRemessa.get(n).getProdutosorcamento().getIdprodutosOrcamento();
					if (idProduto == idRemessa) {
						valorremessa = valorremessa
								+ orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional();
					}
				}
			}
		}
		if (valorremessa > 0) {
			boolean achou = false;
			valorremessa = valorremessa * (aplicacaoMB.getParametrosprodutos().getPercentualremessa() / 100);
			int idRemessa = aplicacaoMB.getParametrosprodutos().getProdutoremessa();
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int idProduto = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento();
				if (idRemessa == idProduto) {
					orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(valorremessa);
					orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.setValorMoedaEstrangeira(valorremessa / cambio.getValor());
					achou = true;
					i = 10000;
				}
			}
			if (!achou) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtosorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getProdutoremessa());
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
				orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
				orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorremessa / cambio.getValor());
				orcamentoprodutosorcamento.setValorMoedaNacional(valorremessa);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			}
		}
	}

	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}

	public void atualizarValoresProduto() {
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira() > 0) {
					orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
							orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira()
									* orcamento.getValorCambio());
				}
				calcularValorTotalOrcamento();
			}
		}
	}

	public void calcularDataTermino() {
		if ((he.getDatainicio() != null) && (he.getNumerosemanas() != null)) {
			if (he.getNumerosemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(he.getDatainicio(), he.getNumerosemanas());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				he.setDatatermino(data);
			}
		}
	}

	public void consultarCambio() {
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(new Date(), venda.getDataVenda());
			if (dias > 3) {
				Mensagem.lancarMensagemInfo("", "Cambio alterado para o dia atual");
			}
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda);
			orcamento.setValorCambio(cambio.getValor());
			atualizarValoresProduto();
		} else {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha já finalizada!");
		}
	}

	public String editarcambio(Float valorCambio) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("valorCambio", valorCambio);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 230);
		RequestContext.getCurrentInstance().openDialog("editarcambio", options, null);
		return "";
	}

	public String tituloMoedaEstrangeira() {
		if (cambio != null) {
			if (cambio.getMoedas() != null) {
				return "Valor " + cambio.getMoedas().getSigla();
			} else {
				return "Moeda Estrangeira ";
			}
		} else
			return "Moeda Estrangeira";
	}

	public void adicionarProdutos() {
		if (orcamento.getValorCambio() > 0) {
			int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			if (produtosorcamento != null) {
				if (produtosorcamento.getIdprodutosOrcamento() != idProdTx) {
					orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
					orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
					if (orcamentoprodutosorcamento.getValorMoedaEstrangeira() == null) {
						orcamentoprodutosorcamento.setValorMoedaEstrangeira(0f);
					}
					if (orcamentoprodutosorcamento.getValorMoedaNacional() == null) {
						orcamentoprodutosorcamento.setValorMoedaNacional(0f);
					}
					if ((orcamentoprodutosorcamento.getValorMoedaEstrangeira() > 0)
							&& (orcamento.getValorCambio() > 0)) {
						orcamentoprodutosorcamento.setValorMoedaNacional(
								orcamentoprodutosorcamento.getValorMoedaEstrangeira() * orcamento.getValorCambio());
					} else {
						if ((orcamentoprodutosorcamento.getValorMoedaNacional() > 0)
								&& (orcamento.getValorCambio() > 0)) {
							orcamentoprodutosorcamento.setValorMoedaEstrangeira(
									orcamentoprodutosorcamento.getValorMoedaNacional() / orcamento.getValorCambio());
						}
					}
					orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
					calcularValorTotalOrcamento();
					produtosorcamento = null;
					orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				} else
					Mensagem.lancarMensagemErro("Assessoria TM já inclusa.", "");
			} else
				Mensagem.lancarMensagemErro("Produto não selecionado", "");
		} else
			Mensagem.lancarMensagemErro("Cambio não selecionado", "");
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		if (ilinha >= 0) {
			int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
					.getIdprodutosOrcamento() != null) {
				if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
						.getIdprodutosOrcamento() == tx) {
					Mensagem.lancarMensagemErro("Assessoria TM não pode ser Excluída.", "");
				} else {
					if (vendaAlterada != null) {
						OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
						orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento.getOrcamentoprodutosorcamentoList()
								.get(ilinha).getIdorcamentoProdutosOrcamento());
					}
					orcamento.getOrcamentoprodutosorcamentoList().remove(ilinha);
					calcularValorTotalOrcamento();
				}
			}
		}
	}

	public String calcularJuros() {
		if (formaPagamento.getValorOrcamento() != null && formaPagamento.getValorOrcamento() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("total", formaPagamento.getValorOrcamento());
			RequestContext.getCurrentInstance().openDialog("calcularJuros");
		}
		return "";
	}

	public void retornoValorJuros() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		formaPagamento.setValorJuros((float) session.getAttribute("valorJuros"));
		session.removeAttribute("valorJuros");
		calcularValorTotalOrcamento();
	}

	public void gerarListaTipoParcelamento() {
		listaTipoParcelamento = Formatacao.gerarListaTipoParcelamento(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().isParcelamentojoja(),
				parcelamentopagamento.getFormaPagamento(),
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
	}

	public void calcularValorParcelas() {
		if (parcelamentopagamento.getValorParcelamento() > 0) {
			parcelamentopagamento.setValorParcela(
					parcelamentopagamento.getValorParcelamento() / parcelamentopagamento.getNumeroParcelas());
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
				if (venda.getIdvendas()!=null){
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento, formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB);
					}
				}
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
					boolean horarioExcedido = false;
					int numeroAdicionar = 0;
					int diaSemana = Formatacao.diaSemana(parcelamentopagamento.getDiaVencimento());
					String horaAtual = Formatacao.foramtarHoraString();
					String horaMaxima = "16:00:00";
					Time horatime = null;
					Time horaMaxTime = null;
					try {
						horatime = Formatacao.converterStringHora(horaAtual);
						horaMaxTime = Formatacao.converterStringHora(horaMaxima);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (horatime.after(horaMaxTime)) {
						numeroAdicionar = 1;
						horarioExcedido = true;
					}
		
					if (diaSemana == 1) {
						numeroAdicionar = 1;
						horarioExcedido = true;
					}else if(diaSemana == 7) {
						numeroAdicionar = 2;
						horarioExcedido = true;
					}
					if (horarioExcedido) {
						try {
							parcelamentopagamento.setDiaVencimento(Formatacao.SomarDiasDatas(parcelamentopagamento.getDiaVencimento(), numeroAdicionar));
							Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
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
		}else {
			if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")){
				try {
					msg = msg + Formatacao.validarDataBoleto(parcelamentopagamento.getDiaVencimento());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
		}
		if (parcelamentopagamento.getNumeroParcelas() == 0) {
			msg = msg + "Número de parcelas não pode ser 0";
		}
		if (parcelamentopagamento.getValorParcela() <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		if(parcelamentopagamento.getValorParcelamento() > (valorSaldoParcelar+0.01)){
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
		}
		return msg;
	}

	public String selecionarCreditoCancelamento() {
		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Credito")) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			int idcliente = cliente.getIdcliente();
			session.setAttribute("idcliente", String.valueOf(idcliente));
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("utilizarCredito", options, null);
		}
		return "";
	}

	public void retornoSelecionarCancelamento(SelectEvent event) {
		if (event.getObject() != null) {
			cancelamento = (Cancelamento) event.getObject();
			parcelamentopagamento.setDiaVencimento(new Date());
			parcelamentopagamento.setValorParcelamento(cancelamento.getTotalreembolso());
			parcelamentopagamento.setNumeroParcelas(1);
			parcelamentopagamento.setValorParcela(parcelamentopagamento.getValorParcelamento());
			adicionarFormaPagamento();
		}
	}

	public void retornoDialogEditarCambio(SelectEvent event) {
		float valorCambio = (float) event.getObject();
		orcamento.setValorCambio(valorCambio);
		atualizarValoresProduto();
	}

	public void gerarListaProdutos() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getHighereducation()
				+ " and f.listar='S' and f.hefichafinal=FALSE order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		venda.setSituacao("PROCESSO");
		if (confirmarFicha()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consquestionarioHe";
		}
		return "";
	}

	public String enviarficha() throws SQLException {
		enviarFicha = true;
		if (!venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
			venda.setSituacao("ANDAMENTO");
		}
		if (confirmarFicha()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consquestionarioHe";
		}
		return "";
	}

	public String validarDados() {
		String msg = "";
		if (cambio == null && cambio.getIdcambio() != null) {
			msg = msg + "Câmbio não informado\r\n";
		}
		if (cliente == null) {
			msg = msg + "Campo cliente não selecionado\r\n";
		}
		if (fornecedorCidade == null) {
			msg = msg + "Escola/Instituição não informada\r\n";
		}
		if (pais == null) {
			msg = msg + "País não informado\r\n";
		}
		if (cidade == null) {
			msg = msg + "Cidade não informada\r\n";
		}
		if (formaPagamento.getParcelamentopagamentoList() == null) {
			msg = msg + "Forma de Pagamento com erro\r\n";
		} else {
			if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			}
		}
		if (he.getBandeiracartao() == null || he.getBandeiracartao().length() == 0) {
			msg = msg + "Informe bandeira do cartão.\r\n";
		}
		if (he.getNumerocartao() == null || he.getNumerocartao().length() == 0) {
			msg = msg + "Informe Nº do cartão.\r\n";
		}
		if (he.getNometitular() == null || he.getNometitular().length() == 0) {
			msg = msg + "Informe Nome do titular do cartão.\r\n";
		}
		if (he.getDatavlidade() == null || he.getDatavlidade().length() == 0) {
			msg = msg + "Informe Data de Validade do cartão.\r\n";
		}
		if (he.getPrimeiralingua() == null || he.getPrimeiralingua().length() == 0) {
			msg = msg + "Informe sua primeira lingua.\r\n";
		}
		if (he.getPrimeiralingua() == null || he.getPrimeiralingua().length() == 0) {
			msg = msg + "Informe sua primeira lingua.\r\n";
		}
		if (he.getPossuiexame() == null || he.getPossuiexame().length() == 0) {
			msg = msg + "Informe se possui algum exame de proficiência.\r\n";
		}
		if (he.getNomeinstituicaoestudou() == null || he.getNomeinstituicaoestudou().length() == 0) {
			msg = msg + "Informe a última instituição que estudou.\r\n";
		}
		if (he.getMaiorgrauformacao() == null || he.getMaiorgrauformacao().length() == 0) {
			msg = msg + "Informe o maior grau de formação adquirido.\r\n";
		}
		double saldoParcelar = this.valorSaldoParcelar;
		if (saldoParcelar > 0.01f) {
			msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
		}
		return msg;
	}

	public boolean confirmarFicha() {
		boolean salvarOK = false;
		String msg = validarDados();
		if (msg.length() < 5) {
			ProgramasBean programasBean = new ProgramasBean();
			Produtos produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getHighereducation());
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento() == aplicacaoMB.getParametrosprodutos().getIdtaxainscricaohe()) {
					he.setValorinscricao(orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional());
					venda.setValor(venda.getValor()
							+ orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional());
					i = 1000;
				}
			}
			venda = programasBean.salvarVendas(venda, usuarioLogadoMB, venda.getSituacao(), cliente, venda.getValor(),
					produto, fornecedorCidade, cambio, orcamento.getValorCambio(), lead,  he.getDatainicio(), he.getDatatermino());
			LogVendaFacade logVendaFacade = new LogVendaFacade();
			Logvenda logvenda = logVendaFacade.consultar(venda.getIdvendas());
			if (logvenda != null) {
				if(valorVendaAlterada>0){
					logvenda.setOperacao("ALTERAÇÃO FICHA INSCRIÇÃO");
				}else{
					logvenda.setOperacao("NOVA FICHA INSCRIÇÃO");
				}
				logvenda.setSituacao(venda.getSituacao());
				logvenda = logVendaFacade.salvar(logvenda); 
			}
			if(novaFicha){
				VendasFacade vendasFacade = new VendasFacade();
				Vendas vendas = new Vendas();
				ProdutoFacade produtoFacade = new ProdutoFacade();
				Produtos produtos =produtoFacade.consultar(aplicacaoMB.getParametrosprodutos().getHighereducation());
				vendas.setProdutos(produtos);
				vendas.setCliente(cliente);
				vendas.setSituacao("CANCELADA");
				vendas.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
				vendas.setUsuario(usuarioLogadoMB.getUsuario());
				FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
				Fornecedorcidade fornecedorcidade = fornecedorCidadeFacade.getFornecedorCidade(1);
				vendas.setFornecedorcidade(fornecedorcidade);
				vendas.setFornecedor(fornecedorcidade.getFornecedor());
				vendas.setCambio(aplicacaoMB.getListaCambio().get(0));
				if(lead!=null){
					vendas.setIdlead(lead.getIdlead());
				}else{
					vendas.setIdlead(0);
				}
				vendas = vendasFacade.salvar(vendas);
				he.setVendas1(vendas);
			}
			he.setVendas(venda);
			he.setPaisprograma(pais.getNome());
			he.setQuestionariohe(questionarioHe);
			he.setAssessoriatm(aplicacaoMB.getParametrosprodutos().getAssessoriatmhe());
			CadHeBean cadHeBean = new CadHeBean(venda, formaPagamento, orcamento, usuarioLogadoMB);
			orcamento = cadHeBean.salvarOrcamento(cambio, orcamento.getTotalMoedaNacional(), orcamento.getTotalMoedaEstrangeira(),
					cambio.getValor(), venda, "");
			formaPagamento = cadHeBean.salvarFormaPagamento(cancelamento);
			cliente = cadHeBean.salvarCliente(cliente);
			he = cadHeBean.salvarHe(he, aplicacaoMB, "I"); 
			if (novaFicha) {
				if (enviarFicha) {
					dashBoardMB.getMetamensal().setValoralcancado(
							dashBoardMB.getMetamensal().getValoralcancado() + orcamento.getTotalMoedaNacional());
					dashBoardMB.getMetamensal().setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
							/ dashBoardMB.getMetamensal().getValormeta()) * 100);
					dashBoardMB.getMetaAnual().setMetaalcancada(
							dashBoardMB.getMetaAnual().getMetaalcancada() + orcamento.getTotalMoedaNacional());
					dashBoardMB.getMetaAnual().setPercentualalcancado(
							(dashBoardMB.getMetaAnual().getMetaalcancada() / dashBoardMB.getMetaAnual().getValormeta())
									* 100);

					dashBoardMB.setMetaparcialsemana(
							dashBoardMB.getMetaparcialsemana() + orcamento.getTotalMoedaNacional());
					dashBoardMB.setPercsemana(
							(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana())
									* 100);

					float valor = dashBoardMB.getMetamensal().getValoralcancado();
					dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));

					DashBoardBean dashBoardBean = new DashBoardBean();
					dashBoardBean.calcularMetaMensal(venda, 0, false);
					dashBoardBean.calcularMetaAnual(venda, 0, false);
					int[] pontos;
					if(he.getNumerosemanas()!=null && he.getNumerosemanas()>0){
						pontos = dashBoardBean.calcularPontuacao(venda, he.getNumerosemanas(), "Inscrição", false);
					}else{
						pontos = dashBoardBean.calcularPontuacao(venda, 0, "Inscrição", false);
					}
					productRunnersMB.calcularPontuacao(venda, pontos[0], false);
					venda.setPonto(pontos[0]);
					venda.setPontoescola(pontos[1]);
					VendasFacade vendasFacade = new VendasFacade();
					venda = vendasFacade.salvar(venda);
					metaRunnersMB.carregarListaRunners();
					tmRaceMB.gerarListaGold();
					tmRaceMB.gerarListaSinze();
					tmRaceMB.gerarListaBronze();

					ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
							formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false);
					String titulo = "Nova ficha de inscrição Higher Education";
					String operacao = "A";
					String imagemNotificacao = "inserido"; 
					String vm = "Venda pela Matriz";
					if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
						vm = "Venda pela Loja";
					}
					DepartamentoFacade departamentoFacade = new DepartamentoFacade();
					List<Departamento> departamento = departamentoFacade.listar("select d From Departamento d where d.usuario.idusuario="+venda.getProdutos().getIdgerente());
					if(departamento!=null && departamento.size()>0){
						if (he.getDatainicio() != null) {
							Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(),
									cliente.getNome(), venda.getFornecedorcidade().getFornecedor().getNome(),
									Formatacao.ConvercaoDataPadrao(he.getDatainicio()), venda.getUsuario().getNome(), vm,
									venda.getValor(), venda.getValorcambio(), venda.getCambio().getMoedas().getSigla(),
									operacao, departamento.get(0), imagemNotificacao, "I");
						} else {
							Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(),
									cliente.getNome(), venda.getFornecedorcidade().getFornecedor().getNome(),
									he.getMesano1(), venda.getUsuario().getNome(), vm, venda.getValor(),
									venda.getValorcambio(), venda.getCambio().getMoedas().getSigla(), operacao,
									departamento.get(0), imagemNotificacao, "I");
						}
					}
				}
			} else {
				int mes = Formatacao.getMesData(new Date()) + 1;
				int mesVenda = Formatacao.getMesData(venda.getDataVenda()) + 1;
					if (enviarFicha) {
						if (mes == mesVenda) {
							dashBoardMB.getMetamensal().setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado()
									- valorVendaAlterada + orcamento.getTotalMoedaNacional());
							dashBoardMB.getMetamensal()
									.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
											/ dashBoardMB.getMetamensal().getValormeta()) * 100);
	
							dashBoardMB.getMetaAnual().setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada()
									- valorVendaAlterada + orcamento.getTotalMoedaNacional());
							dashBoardMB.getMetaAnual().setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
									/ dashBoardMB.getMetaAnual().getValormeta()) * 100);
	
							dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() - valorVendaAlterada
									+ orcamento.getTotalMoedaNacional());
							dashBoardMB.setPercsemana(
									(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana())
											* 100);
	
							float valor = dashBoardMB.getMetamensal().getValoralcancado();
							dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularMetaMensal(venda, valorVendaAlterada, false);
							dashBoardBean.calcularMetaAnual(venda, valorVendaAlterada, false);
							int[] pontos;
							if(he.getNumerosemanas()!=null && he.getNumerosemanas()>0){
								pontos = dashBoardBean.calcularPontuacao(venda, he.getNumerosemanas(), "Inscrição", false);
							}else{
								pontos = dashBoardBean.calcularPontuacao(venda, 0, "Inscrição", false);
							}
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							VendasFacade vendasFacade = new VendasFacade();
							venda = vendasFacade.salvar(venda);
							productRunnersMB.calcularPontuacao(venda, pontos[0], false);
							metaRunnersMB.carregarListaRunners();
						}
						String titulo = "Ficha de Higher Education Alterada";
						String operacao = "I";
						String imagemNotificacao = "alterado";
						if (Formatacao.validarDataVenda(venda.getDataVenda())) {
							ContasReceberBean contasReceberBean = new ContasReceberBean(venda,
									formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true);
							String vm = "Venda pela Matriz";
							if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
								vm = "Venda pela Loja";
							}
							DepartamentoFacade departamentoFacade = new DepartamentoFacade();
							List<Departamento> departamento = departamentoFacade.listar("select d From Departamento d where d.usuario.idusuario="+venda.getProdutos().getIdgerente());
							if(departamento!=null && departamento.size()>0){
								if (he.getDatainicio() != null) {
									Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(),
											cliente.getNome(), venda.getFornecedorcidade().getFornecedor().getNome(),
											Formatacao.ConvercaoDataPadrao(he.getDatainicio()),
											venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
											venda.getCambio().getMoedas().getSigla(), operacao,
											departamento.get(0), imagemNotificacao, "A");
								} else {
									Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(),
											cliente.getNome(), venda.getFornecedorcidade().getFornecedor().getNome(),
											he.getMesano1(), venda.getUsuario().getNome(), vm, venda.getValor(),
											venda.getValorcambio(), venda.getCambio().getMoedas().getSigla(), operacao,
											departamento.get(0), imagemNotificacao, "A");
								}
							}
						}
					}
			}
			Mensagem.lancarMensagemInfo("Ficha de Inscrição salva com sucesso!", "");
			salvarOK = true;
		} else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
		return salvarOK; 
	}
	
	public boolean habilitarCamposExame(){
		if(he.getPossuiexame()!=null && he.getPossuiexame().equalsIgnoreCase("Sim")){
			return false;
		}
		return true;
	}
	
	public boolean habilitarCamposHe(){
		if(camposHe){
			return false;
		}
		return true;
	}
	
	public boolean habilitarCamposPathway(){
		if(camposPathway){
			return false;
		}
		return true;
	}
	
	
	public void selecionarCambio(){
		if(pais!=null && pais.getIdpais()!=null){
			moeda=pais.getMoedas();
			consultarCambio();
		}
	}
}
