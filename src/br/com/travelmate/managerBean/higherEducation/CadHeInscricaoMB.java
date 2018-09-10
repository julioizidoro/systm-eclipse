package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.sql.SQLException;
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
import br.com.travelmate.bean.DataVencimentoBean;
import br.com.travelmate.bean.ProductRunnersCalculosBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoHEInscricaoBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.HeParceirosFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
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
import br.com.travelmate.model.Heparceiros;
import br.com.travelmate.model.Lead;
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
import br.com.travelmate.model.Vendascomissao;
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
	private LeadSituacaoDao leadSituacaoDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private LeadPosVendaDao leadPosVendaDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
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
	private boolean camposPathway2;
	private boolean camposPathway3;
	private boolean camposHe;
	private boolean camposHe2;
	private boolean camposHe3;
	private Lead lead;
	private String voltarControleVendas = "";
	private boolean camposAcomodacao = true;
	private boolean camposAcomodacaoCasaFamilia = true;
	private boolean desabilitarAlergiaAlimentoCampo = true;
	private boolean digitosTelefoneContatoEmergencia;
	private Heparceiros heparceiros1;
	private Heparceiros heparceiros2;
	private Heparceiros heparceiros3;
	private Fornecedorcidade fornecedorCidade2;
	private Pais pais2;
	private Cidade cidade2;
	private Fornecedorcidade fornecedorCidade3;
	private Pais pais3;
	private Cidade cidade3;
	private List<Pais> listaPais2;
	private List<Fornecedorcidade> listaFornecedorCidade2;
	private List<Pais> listaPais3;
	private List<Fornecedorcidade> listaFornecedorCidade3;
	private String moedaNacional;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cliente = (Cliente) session.getAttribute("cliente");
		he = (He) session.getAttribute("he");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("he");
		session.removeAttribute("cliente");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();    
		carregarComboMoedas();
		gerarListaProdutos();
		if (he != null) {
			iniciarAlteracao();
		} else {
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
			iniciarNovo();
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		carregarCamposAcomodacao();
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


	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	

	public boolean isCamposAcomodacao() {
		return camposAcomodacao;
	}

	public void setCamposAcomodacao(boolean camposAcomodacao) {
		this.camposAcomodacao = camposAcomodacao;
	}

	public boolean isCamposAcomodacaoCasaFamilia() {
		return camposAcomodacaoCasaFamilia;
	}

	public void setCamposAcomodacaoCasaFamilia(boolean camposAcomodacaoCasaFamilia) {
		this.camposAcomodacaoCasaFamilia = camposAcomodacaoCasaFamilia;
	}

	public boolean isDesabilitarAlergiaAlimentoCampo() {
		return desabilitarAlergiaAlimentoCampo;
	}

	public void setDesabilitarAlergiaAlimentoCampo(boolean desabilitarAlergiaAlimentoCampo) {
		this.desabilitarAlergiaAlimentoCampo = desabilitarAlergiaAlimentoCampo;
	}

	public boolean isDigitosTelefoneContatoEmergencia() {
		return digitosTelefoneContatoEmergencia;
	}

	public void setDigitosTelefoneContatoEmergencia(boolean digitosTelefoneContatoEmergencia) {
		this.digitosTelefoneContatoEmergencia = digitosTelefoneContatoEmergencia;
	}

	public boolean isCamposPathway2() {
		return camposPathway2;
	}

	public void setCamposPathway2(boolean camposPathway2) {
		this.camposPathway2 = camposPathway2;
	}

	public boolean isCamposPathway3() {
		return camposPathway3;
	}

	public void setCamposPathway3(boolean camposPathway3) {
		this.camposPathway3 = camposPathway3;
	}

	public Heparceiros getHeparceiros1() {
		return heparceiros1;
	}

	public void setHeparceiros1(Heparceiros heparceiros1) {
		this.heparceiros1 = heparceiros1;
	}

	public Heparceiros getHeparceiros2() {
		return heparceiros2;
	}

	public void setHeparceiros2(Heparceiros heparceiros2) {
		this.heparceiros2 = heparceiros2;
	}

	public Heparceiros getHeparceiros3() {
		return heparceiros3;
	}

	public void setHeparceiros3(Heparceiros heparceiros3) {
		this.heparceiros3 = heparceiros3;
	}

	public boolean isCamposHe2() {
		return camposHe2;
	}

	public void setCamposHe2(boolean camposHe2) {
		this.camposHe2 = camposHe2;
	}

	public boolean isCamposHe3() {
		return camposHe3;
	}

	public void setCamposHe3(boolean camposHe3) {
		this.camposHe3 = camposHe3;
	}

	public Fornecedorcidade getFornecedorCidade2() {
		return fornecedorCidade2;
	}

	public void setFornecedorCidade2(Fornecedorcidade fornecedorCidade2) {
		this.fornecedorCidade2 = fornecedorCidade2;
	}

	public Pais getPais2() {
		return pais2;
	}

	public void setPais2(Pais pais2) {
		this.pais2 = pais2;
	}

	public Cidade getCidade2() {
		return cidade2;
	}

	public void setCidade2(Cidade cidade2) {
		this.cidade2 = cidade2;
	}

	public Fornecedorcidade getFornecedorCidade3() {
		return fornecedorCidade3;
	}

	public void setFornecedorCidade3(Fornecedorcidade fornecedorCidade3) {
		this.fornecedorCidade3 = fornecedorCidade3;
	}

	public Pais getPais3() {
		return pais3;
	}

	public void setPais3(Pais pais3) {
		this.pais3 = pais3;
	}

	public Cidade getCidade3() {
		return cidade3;
	}

	public void setCidade3(Cidade cidade3) {
		this.cidade3 = cidade3;
	}

	public List<Pais> getListaPais2() {
		return listaPais2;
	}

	public void setListaPais2(List<Pais> listaPais2) {
		this.listaPais2 = listaPais2;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade2() {
		return listaFornecedorCidade2;
	}

	public void setListaFornecedorCidade2(List<Fornecedorcidade> listaFornecedorCidade2) {
		this.listaFornecedorCidade2 = listaFornecedorCidade2;
	}

	public List<Pais> getListaPais3() {
		return listaPais3;
	}

	public void setListaPais3(List<Pais> listaPais3) {
		this.listaPais3 = listaPais3;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade3() {
		return listaFornecedorCidade3;
	}

	public void setListaFornecedorCidade3(List<Fornecedorcidade> listaFornecedorCidade3) {
		this.listaFornecedorCidade3 = listaFornecedorCidade3;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
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
		return "consFormAssessoria";
	}

	public void iniciarNovo() {
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		novaFicha =true;
		he = new He();
		he.setBanheiroprivativo("Não");
		he.setVegetariano("");
		he.setFumante("");
		he.setFamiliacomAnimais("");
		he.setFamiliacomCrianca("");
		he.setAdicionais("");
		he.setTipoFicha("Formulario");
		orcamento = new Orcamento();
		orcamento.setValorCambio(0.0f);
		orcamento.setTotalMoedaEstrangeira(0.0f);
		orcamento.setTotalMoedaNacional(0.0f);
		orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		venda = new Vendas();
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
		heparceiros1 = new Heparceiros();
//		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
//		Produtosorcamento produtosorcamento = produtoOrcamentoFacade
//				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
//		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
//		orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
//		orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
//		orcamentoprodutosorcamento.setValorMoedaNacional(0.0f);
//		orcamentoprodutosorcamento.setValorMoedaEstrangeira(aplicacaoMB.getParametrosprodutos().getAssessoriatmhe());
//		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
//		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
	}

	public void iniciarAlteracao() {
		this.venda = he.getVendas();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
			valorVendaAlterada = venda.getValor();
		}
		he.setTipoFicha("Formulario");
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
		buscarHeParceiros();
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
	
	public void listarFornecedorCidade2() {
		if (cidade2 != null) {
			String sql = "select f from Fornecedorcidade f where f.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getHighereducation() + " and f.cidade.idcidade="
					+ cidade2.getIdcidade() + " and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade2 = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade2 == null) {
				listaFornecedorCidade2 = new ArrayList<Fornecedorcidade>();
			}
		}
	}
	
	public void listarFornecedorCidade3() {
		if (cidade2 != null) {
			String sql = "select f from Fornecedorcidade f where f.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getHighereducation() + " and f.cidade.idcidade="
					+ cidade3.getIdcidade() + " and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade3 = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade3 == null) {
				listaFornecedorCidade3 = new ArrayList<Fornecedorcidade>();
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
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
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
			if (produtosorcamento != null) {
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
				Mensagem.lancarMensagemErro("Produto não selecionado", "");
		} else
			Mensagem.lancarMensagemErro("Cambio não selecionado", "");
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		if (ilinha >= 0) {
			if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getIdorcamentoProdutosOrcamento() != null) {

				if (vendaAlterada != null) {
					OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
					orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento.getOrcamentoprodutosorcamentoList()
							.get(ilinha).getIdorcamentoProdutosOrcamento());
				}
			}
			orcamento.getOrcamentoprodutosorcamentoList().remove(ilinha);
			calcularValorTotalOrcamento();
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
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
					DataVencimentoBean dataVencimentoBean = new DataVencimentoBean(parcelamentopagamento.getDiaVencimento());
					parcelamentopagamento.setDiaVencimento(dataVencimentoBean.validarDataVencimento());
				}
				if (venda.getIdvendas()!=null){
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento, formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB, he.getDatainicio());
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
			if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
				String dataAtualString = Formatacao.ConvercaoDataPadrao(new Date());
				Date dataAtual = Formatacao.ConvercaoStringData(dataAtualString);
				if (parcelamentopagamento.getDiaVencimento().before(dataAtual)) {
					msg = msg + "Data deve ser num próximo dia util";
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
		venda.setValorcambio(valorCambio);
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
		if (venda.getIdvendas()==null) {
			venda.setSituacao("PROCESSO");
		}
		if (confirmarFicha()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consFormAssessoria";
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
			return "consFormAssessoria";
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
		
		if (saldoParcelar < 0.00f) {
			msg = msg + "Valor da forma de pagamento maior que o valor da venda\r\n";
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
			float totalMoedaEstrangeira = orcamento.getTotalMoedaEstrangeira();
			float totalMoedaReal = orcamento.getTotalMoedaNacional();
			venda.setValorpais(totalMoedaEstrangeira * cambio.getValor());
			Cambio cambioBrasil = null;
			if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getIdpais() != 5) {
				PaisFacade paisFacade = new PaisFacade();
				Pais pais = paisFacade.consultar(5);
				CambioFacade cambioFacade = new CambioFacade();
				cambioBrasil = cambioFacade.consultarCambioMoedaPais(Formatacao.ConvercaoDataSql(pais.getDatacambio()), cambio.getMoedas().getIdmoedas(), pais);
				totalMoedaReal = totalMoedaEstrangeira * cambioBrasil.getValor();
			}
			venda.setValor(totalMoedaReal);
			venda = programasBean.salvarVendas(venda, usuarioLogadoMB, venda.getSituacao(), cliente, venda.getValor(),
					produto, fornecedorCidade, cambio, orcamento.getValorCambio(), lead,  he.getDatainicio(), he.getDatatermino(),
					vendasDao, leadPosVendaDao, leadDao, leadSituacaoDao);
			
			
			
			he.setPaisprograma(pais.getNome());
			he.setAssessoriatm(aplicacaoMB.getParametrosprodutos().getAssessoriatmhe());
			CadHeBean cadHeBean = new CadHeBean(venda, formaPagamento, orcamento, usuarioLogadoMB);
			float valorCambioBrasil = 0.0f;
			if (cambioBrasil != null) {
				valorCambioBrasil = cambioBrasil.getValor();
			}
			orcamento = cadHeBean.salvarOrcamento(cambio, venda.getValorpais(), totalMoedaEstrangeira,
					cambio.getValor(), venda, "", totalMoedaReal, valorCambioBrasil);
			formaPagamento = cadHeBean.salvarFormaPagamento(cancelamento);
			cliente = cadHeBean.salvarCliente(cliente);
			venda.setOrcamento(orcamento);
			venda.setFormapagamento(formaPagamento);  
			he.setVendas(venda);
			he = cadHeBean.salvarHe(he, aplicacaoMB, "I"); 
			salvarHeParceiros();
			new ComissaoHEInscricaoBean(aplicacaoMB, he.getVendas(),
					orcamento.getOrcamentoprodutosorcamentoList(),
					formaPagamento.getParcelamentopagamentoList(),  new Vendascomissao(),
					 0.0f, true);
			if (novaFicha) {
				if (enviarFicha) {
					DashBoardBean dashBoardBean = new DashBoardBean();
					dashBoardBean.calcularMetaMensal(venda, 0, false);
					dashBoardBean.calcularMetaAnual(venda, 0, false);
					int[] pontos;
					if(he.getNumerosemanas()!=null && he.getNumerosemanas()>0){
						pontos = dashBoardBean.calcularPontuacao(venda, he.getNumerosemanas(), "Inscrição", false, venda.getUsuario());
					}else{
						pontos = dashBoardBean.calcularPontuacao(venda, 0, "Inscrição", false, venda.getUsuario());
					}
					int pontoremover = 0;
					if (vendaAlterada!=null) {
						pontoremover = vendaAlterada.getPonto();
					};
					ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
					productRunnersCalculosBean.calcularPontuacao(venda, pontos[0], pontoremover, false,venda.getUsuario() );
					venda.setPonto(pontos[0]);
					venda.setPontoescola(pontos[1]);
					
					venda = vendasDao.salvar(venda);
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
							
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularMetaMensal(venda, valorVendaAlterada, false);
							dashBoardBean.calcularMetaAnual(venda, valorVendaAlterada, false);
							int[] pontos;
							if(he.getNumerosemanas()!=null && he.getNumerosemanas()>0){
								pontos = dashBoardBean.calcularPontuacao(venda, he.getNumerosemanas(), "Inscrição", false, venda.getUsuario());
							}else{
								pontos = dashBoardBean.calcularPontuacao(venda, 0, "Inscrição", false, venda.getUsuario());
							}
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							
							venda = vendasDao.salvar(venda);
							int pontoremover = 0;
							if (vendaAlterada!=null) {
								pontoremover = vendaAlterada.getPonto();
							}
							ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
							productRunnersCalculosBean.calcularPontuacao(venda, pontos[0], pontoremover, false, venda.getUsuario());
							
						}
						String titulo = "Ficha de Higher Education Alterada";
						String operacao = "I";
						String imagemNotificacao = "alterado";
							new ContasReceberBean(venda,
									formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, true, he.getDatainicio());
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
	
	public boolean habilitarCamposHe2(){
		if(camposHe2){
			return false;
		}
		return true;
	}
	
	public boolean habilitarCamposHe3(){
		if(camposHe3){
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
	
	public boolean habilitarCamposPathway2(){
		if(camposPathway2){
			return false;
		}
		return true;
	}
	
	public boolean habilitarCamposPathway3(){
		if(camposPathway3){
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
	
	
	public void carregarCamposAcomodacao() {
		if (he.getTipoAcomodacao() == null || he.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = true;
			camposAcomodacaoCasaFamilia = true;
			he.setTipoQuarto("Sem quarto");
			he.setRefeicoes("Sem Refeição");
			he.setDataChegada(null);
			he.setNumeroSemanasAcomodacao(null);
			he.setDataSaida(null);
		} else {
			camposAcomodacao = false;
			camposAcomodacaoCasaFamilia = true;
		}
		if (he.getTipoAcomodacao() != null && he.getTipoAcomodacao().equalsIgnoreCase("Casa de família")) {
			camposAcomodacaoCasaFamilia = false;
			camposAcomodacao = false;
		}
	}
	
	public void calcularDataTerminoAcomodacao() {
		if ((he.getDataChegada() != null) && (he.getNumeroSemanasAcomodacao() != null)) {
			if (he.getNumeroSemanasAcomodacao() > 0) {
				int diaSemana = Formatacao.diaSemana(he.getDataChegada()); 
				if(diaSemana!=1) {
					Mensagem.lancarMensagemInfo("Atenção!", "O sistema não irá calcular automaticamente"
							+ " as datas de chegada e partida para acomodações que não iniciam no Domingo.");
					he.setDataSaida(null);
					he.setNumeroSemanasAcomodacao(null);
				} else {
					Date data = Formatacao.calcularDataFinalAcomodacao(he.getDataChegada(), he.getNumeroSemanasAcomodacao());
					he.setDataSaida(data);
				}
			}
		}
	}
	
	
	public void desabilitarAlergiaAlimento(){
		if (he.getPossuiAlergia().equalsIgnoreCase("Sim")) {
			desabilitarAlergiaAlimentoCampo = false;
		}else{
			desabilitarAlergiaAlimentoCampo = true;
		}
	}
	
	public String validarMascaraFoneContatoEmergencia() {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneContatoEmergencia);
	}
	
	
	public String questionario() {
		if (cliente != null) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("cliente", cliente);
			RequestContext.getCurrentInstance().openDialog("importarQuestionario", options, null);
		}
		return "";
	}
	
	public void retornoDialogQuestionario(SelectEvent event) {
		Questionariohe questionariohe = (Questionariohe) event.getObject();
		he.setQuestionario(questionariohe.getIdquestionariohe());
		he.setMaiorgrauformacao(questionariohe.getNivelcetificado());
		he.setNotacienciahumanas(questionariohe.getNotacienciahumanas());
		he.setNotaciencianatureza(questionariohe.getNotaciencianatureza());
		he.setNotalinguagem(questionariohe.getNotalinguagem());
		he.setNotamatematica(questionariohe.getNotamatematica());
		he.setNotaredacao(questionariohe.getNotaredacao());
	}
	
	
	public void gerarHeParceiro2() {
		if (camposHe2) {
			heparceiros2 = new Heparceiros();
			PaisFacade paisFacade = new PaisFacade();
			listaPais2 = paisFacade.listar();    
		}
	}
	
	public void gerarHeParceiro3() {
		if (camposHe3) {
			heparceiros3 = new Heparceiros();
			PaisFacade paisFacade = new PaisFacade();
			listaPais3 = paisFacade.listar();    
		}
	}
	
	
	public void buscarHeParceiros() {
		HeParceirosFacade heParceirosFacade = new HeParceirosFacade();
		List<Heparceiros> listaHeParceiros = heParceirosFacade.listar("Select h From Heparceiros h WHERE h.he.idhe=" + he.getIdhe());
		if (listaHeParceiros == null) {
			listaHeParceiros = new ArrayList<Heparceiros>();
		}
		heparceiros1 = new Heparceiros();
		if (listaHeParceiros.size() > 0) {
			if (listaHeParceiros.size() == 1) {
				heparceiros1 = listaHeParceiros.get(0);
			}else if(listaHeParceiros.size() == 2) {
				heparceiros1 = listaHeParceiros.get(0);
				camposHe = true;
				camposPathway = true;
				heparceiros2 = listaHeParceiros.get(1);
				camposHe2 = true;
				camposPathway2 = true;
				PaisFacade paisFacade = new PaisFacade();
				listaPais2 = paisFacade.listar();    
				pais2 = heparceiros2.getFornecedorcidade().getCidade().getPais();
				cidade2 = heparceiros2.getFornecedorcidade().getCidade();
				listarFornecedorCidade2();
				fornecedorCidade2 = heparceiros2.getFornecedorcidade();
			}else {
				heparceiros1 = listaHeParceiros.get(0);
				camposHe = true;
				camposPathway = heparceiros1.isPathway();
				heparceiros2 = listaHeParceiros.get(1);
				camposHe2 = true;
				camposPathway2 = heparceiros2.isPathway();
				PaisFacade paisFacade = new PaisFacade();
				listaPais2 = paisFacade.listar();    
				pais2 = heparceiros2.getFornecedorcidade().getCidade().getPais();
				cidade2 = heparceiros2.getFornecedorcidade().getCidade();
				listarFornecedorCidade2();
				fornecedorCidade2 = heparceiros2.getFornecedorcidade();
				heparceiros3 = listaHeParceiros.get(2);
				camposHe3 = true;
				camposPathway3 = heparceiros3.isPathway();
				listaPais3 = paisFacade.listar();    
				pais3 = heparceiros3.getFornecedorcidade().getCidade().getPais();
				cidade3 = heparceiros3.getFornecedorcidade().getCidade();
				listarFornecedorCidade3();
				fornecedorCidade3 = heparceiros3.getFornecedorcidade();
			}
		}
	}
	
	
	public void salvarHeParceiros() {
		HeParceirosFacade heParceirosFacade = new HeParceirosFacade();
		heparceiros1.setHe(he);
		heparceiros1.setFornecedorcidade(fornecedorCidade);
		heparceiros1.setPathway(camposPathway);
		heparceiros1 = heParceirosFacade.salvar(heparceiros1);
		if (camposHe2) {
			if (validarDadosParceiros2()) {
				heparceiros2.setHe(he);
				heparceiros2.setPathway(camposPathway2);
				heparceiros2.setFornecedorcidade(fornecedorCidade2);
				heparceiros2 = heParceirosFacade.salvar(heparceiros2);
			}
		}
		
		if (camposHe3) {
			if (validarDadosParceiros3()) {
				heparceiros3.setHe(he);
				heparceiros3.setPathway(camposPathway3);
				heparceiros3.setFornecedorcidade(fornecedorCidade3);
				heparceiros3 = heParceirosFacade.salvar(heparceiros3);
			}
		}
	}
	
	
	public boolean validarDadosParceiros2() {
		if (fornecedorCidade == null) {
			Mensagem.lancarMensagemInfo("Escola/Instituição opção 2 não informada\r\n", "");
			return false;
		}
		if (pais == null) {
			Mensagem.lancarMensagemInfo("País opção 2 não informado\r\n", "");
			return false;
		}
		if (cidade == null) {
			Mensagem.lancarMensagemInfo("Cidade opção 2 não informada\r\n", "");
			return false;
		}
		return true;
	}
	
	public boolean validarDadosParceiros3() {
		if (fornecedorCidade == null) {
			Mensagem.lancarMensagemInfo("Escola/Instituição opção 3 não informada\r\n", "");
			return false;
		}
		if (pais == null) {
			Mensagem.lancarMensagemInfo("País opção 3 não informado\r\n", "");
			return false;
		}
		if (cidade == null) {
			Mensagem.lancarMensagemInfo("Cidade opção 3 não informada\r\n", "");
			return false;
		}
		return true;
	}
	
	public void calcularDataTermino1() {
		if ((heparceiros1.getDatainicio() != null) && (heparceiros1.getNumerosemanas() != null)) {
			if (heparceiros1.getNumerosemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(heparceiros1.getDatainicio(), heparceiros1.getNumerosemanas());
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
				heparceiros1.setDatatermino(data);
			}
		}
	}
		
		public void calcularDataTermino2() {
			if ((heparceiros2.getDatainicio() != null) && (heparceiros2.getNumerosemanas() != null)) {
				if (heparceiros2.getNumerosemanas() > 0) {
					Date data = Formatacao.calcularDataFinal(heparceiros2.getDatainicio(), heparceiros2.getNumerosemanas());
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
					heparceiros2.setDatatermino(data);
				}
			}
		}
	
	public void calcularDataTermino3() {
		if ((heparceiros3.getDatainicio() != null) && (heparceiros3.getNumerosemanas() != null)) {
			if (heparceiros3.getNumerosemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(heparceiros3.getDatainicio(), heparceiros3.getNumerosemanas());
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
				heparceiros3.setDatatermino(data);
			}
		}
	}
	
	
}
