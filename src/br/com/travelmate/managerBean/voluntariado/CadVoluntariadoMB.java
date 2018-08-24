package br.com.travelmate.managerBean.voluntariado;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import br.com.travelmate.bean.comissao.ComissaoVoluntariadoBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.ValorSeguroFacade;

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
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVoluntariadoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
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
	
	
	private Voluntariado voluntariadoAlterado;
	private Voluntariado voluntariado;
	private Vendas venda;
	private Formapagamento formaPagamento;
	private Parcelamentopagamento parcelamentopagamento;
	private Orcamento orcamento;
	private Cambio cambio;
	private Date dataCambio;
	private Fornecedorcidade fornecedorCidade;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Cliente cliente;
	private Produtos produto;
	private List<Moedas> listaMoedas;
	private Orcamentoprodutosorcamento orcamentoprodutosorcamento;
	private Produtosorcamento produtosorcamento;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private boolean enviarFicha;
	private boolean novaFicha = false;
	private String cambioAlterado = "Não";
	private String dadosAlterado;
	private Vendas vendaAlterada;
	private float valorVendaAlterar = 0.0f;
	private String camposSeguroViagem = "true";
	private List<Fornecedorcidade> listaFornecedorCidadeSeguro;
	private List<Valoresseguro> listaValoresSeguro;
	private Seguroviagem seguroViagem;
	private Seguroviagem seguroViagemAlterado;
	private Boolean CheckBoxSeguroViagem;
	private Fornecedorcidade fornecedorSeguro;
	private Fornecedorcomissaocurso fornecedorComissao;
	private String camposAcomodacao = "true";
	private String camposAcomodacaoCasaFamilia = "true";
	private String camposCartaoVtm = "true";
	private String camposVisto = "true";
	private String camposPassagem = "true";
	private Moedas moeda;
	private float valorMoedaReal = 0;
	private float valorMoedaEstrangeira = 0;
	private float valorSaldoParcelar = 0;
	private List<String> listaTipoParcelamento;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoOriginal;
	private List<Parcelamentopagamento> listaParcelamentoPagamentoAntiga;
	private boolean digitosFoneContatoEmergencia;
	private Cancelamento cancelamento;
	private Lead lead;
	private String voltarControleVendas = "";
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;
	private boolean segurocancelamento=false;
	private boolean desabilitarCamposCurso = true;
	private float valorSeguroAntigo = 0.0f;
	private boolean habilitarAvisoCambio = false;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		voluntariado = (Voluntariado) session.getAttribute("voluntariado");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");
		session.removeAttribute("voluntariado");
		iniciarListaFornecedorSeguro();
		gerarListaProdutos();
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		carregarComboMoedas();
		if (voluntariado == null) {
			iniciarNovoVoluntariado();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			iniciarAlteracaoVoluntariado();
			if ((venda.getSituacao().equalsIgnoreCase("PROCESSO")) && (venda.isRestricaoparcelamento())) {
				verificarAlteracaoListaParcelamento();
			}
		}
		parcelamentopagamento.setFormaPagamento("sn");
		gerarListaTipoParcelamento();
		digitosFoneContatoEmergencia = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				voluntariado.getFoneContatoEmergencia());
	}

	public float getValorMoedaReal() {
		return valorMoedaReal;
	}

	public void setValorMoedaReal(float valorMoedaReal) {
		this.valorMoedaReal = valorMoedaReal;
	}

	public float getValorMoedaEstrangeira() {
		return valorMoedaEstrangeira;
	}

	public void setValorMoedaEstrangeira(float valorMoedaEstrangeira) {
		this.valorMoedaEstrangeira = valorMoedaEstrangeira;
	}

	public float getValorSaldoParcelar() {
		return valorSaldoParcelar;
	}

	public void setValorSaldoParcelar(float valorSaldoParcelar) {
		this.valorSaldoParcelar = valorSaldoParcelar;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
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

	public boolean isEnviarFicha() {
		return enviarFicha;
	}

	public void setEnviarFicha(boolean enviarFicha) {
		this.enviarFicha = enviarFicha;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public String getCamposCartaoVtm() {
		return camposCartaoVtm;
	}

	public void setCamposCartaoVtm(String camposCartaoVtm) {
		this.camposCartaoVtm = camposCartaoVtm;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public String getCamposVisto() {
		return camposVisto;
	}

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	public void setCamposVisto(String camposVisto) {
		this.camposVisto = camposVisto;
	}

	public String getCamposPassagem() {
		return camposPassagem;
	}

	public void setCamposPassagem(String camposPassagem) {
		this.camposPassagem = camposPassagem;
	}

	public Fornecedorcidade getFornecedorSeguro() {
		return fornecedorSeguro;
	}

	public void setFornecedorSeguro(Fornecedorcidade fornecedorSeguro) {
		this.fornecedorSeguro = fornecedorSeguro;
	}

	public Fornecedorcomissaocurso getFornecedorComissao() {
		return fornecedorComissao;
	}

	public void setFornecedorComissao(Fornecedorcomissaocurso fornecedorComissao) {
		this.fornecedorComissao = fornecedorComissao;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getCamposAcomodacao() {
		return camposAcomodacao;
	}

	public void setCamposAcomodacao(String camposAcomodacao) {
		this.camposAcomodacao = camposAcomodacao;
	}

	public String getCamposAcomodacaoCasaFamilia() {
		return camposAcomodacaoCasaFamilia;
	}

	public void setCamposAcomodacaoCasaFamilia(String camposAcomodacaoCasaFamilia) {
		this.camposAcomodacaoCasaFamilia = camposAcomodacaoCasaFamilia;
	}

	public String getCambioAlterado() {
		return cambioAlterado;
	}

	public void setCambioAlterado(String cambioAlterado) {
		this.cambioAlterado = cambioAlterado;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public String getDadosAlterado() {
		return dadosAlterado;
	}

	public void setDadosAlterado(String dadosAlterado) {
		this.dadosAlterado = dadosAlterado;
	}

	public float getValorVendaAlterar() {
		return valorVendaAlterar;
	}

	public void setValorVendaAlterar(float valorVendaAlterar) {
		this.valorVendaAlterar = valorVendaAlterar;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public Voluntariado getVoluntariadoAlterado() {
		return voluntariadoAlterado;
	}

	public void setVoluntariadoAlterado(Voluntariado voluntariadoAlterado) {
		this.voluntariadoAlterado = voluntariadoAlterado;
	}

	public Voluntariado getVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}

	public Parcelamentopagamento getParcelamentopagamento() {
		return parcelamentopagamento;
	}

	public void setParcelamentopagamento(Parcelamentopagamento parcelamentopagamento) {
		this.parcelamentopagamento = parcelamentopagamento;
	}

	public Orcamentoprodutosorcamento getOrcamentoprodutosorcamento() {
		return orcamentoprodutosorcamento;
	}

	public void setOrcamentoprodutosorcamento(Orcamentoprodutosorcamento orcamentoprodutosorcamento) {
		this.orcamentoprodutosorcamento = orcamentoprodutosorcamento;
	}

	public String getCamposSeguroViagem() {
		return camposSeguroViagem;
	}

	public void setCamposSeguroViagem(String camposSeguroViagem) {
		this.camposSeguroViagem = camposSeguroViagem;
	}

	public List<Fornecedorcidade> getListaFornecedorCidadeSeguro() {
		return listaFornecedorCidadeSeguro;
	}

	public void setListaFornecedorCidadeSeguro(List<Fornecedorcidade> listaFornecedorCidadeSeguro) {
		this.listaFornecedorCidadeSeguro = listaFornecedorCidadeSeguro;
	}

	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}

	public Seguroviagem getSeguroViagem() {
		return seguroViagem;
	}

	public void setSeguroViagem(Seguroviagem seguroViagem) {
		this.seguroViagem = seguroViagem;
	}

	public Seguroviagem getSeguroViagemAlterado() {
		return seguroViagemAlterado;
	}

	public void setSeguroViagemAlterado(Seguroviagem seguroViagemAlterado) {
		this.seguroViagemAlterado = seguroViagemAlterado;
	}

	public Boolean getCheckBoxSeguroViagem() {
		return CheckBoxSeguroViagem;
	}

	public void setCheckBoxSeguroViagem(Boolean checkBoxSeguroViagem) {
		CheckBoxSeguroViagem = checkBoxSeguroViagem;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isDigitosFoneContatoEmergencia() {
		return digitosFoneContatoEmergencia;
	}

	public void setDigitosFoneContatoEmergencia(boolean digitosFoneContatoEmergencia) {
		this.digitosFoneContatoEmergencia = digitosFoneContatoEmergencia;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
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

	public List<Seguroplanos> getListaSeguroPlanos() {
		return listaSeguroPlanos;
	}

	public void setListaSeguroPlanos(List<Seguroplanos> listaSeguroPlanos) {
		this.listaSeguroPlanos = listaSeguroPlanos;
	}

	public Seguroplanos getSeguroplanos() {
		return seguroplanos;
	}

	public void setSeguroplanos(Seguroplanos seguroplanos) {
		this.seguroplanos = seguroplanos;
	}

	public boolean isDesabilitarCamposCurso() {
		return desabilitarCamposCurso;
	}

	public void setDesabilitarCamposCurso(boolean desabilitarCamposCurso) {
		this.desabilitarCamposCurso = desabilitarCamposCurso;
	}

	public float getValorSeguroAntigo() {
		return valorSeguroAntigo;
	}

	public void setValorSeguroAntigo(float valorSeguroAntigo) {
		this.valorSeguroAntigo = valorSeguroAntigo;
	}

	public boolean isHabilitarAvisoCambio() {
		return habilitarAvisoCambio;
	}

	public void setHabilitarAvisoCambio(boolean habilitarAvisoCambio) {
		this.habilitarAvisoCambio = habilitarAvisoCambio;
	}

	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}

	public void carregarVoluntariadoAlteracao() {
		voluntariadoAlterado = new Voluntariado();
		voluntariadoAlterado.setAdicionais(voluntariado.getAdicionais());
		voluntariadoAlterado.setAulasporSemana(voluntariado.getAulasporSemana());
		voluntariadoAlterado.setCargo(voluntariado.getCargo());
		voluntariadoAlterado.setCartaoVTM(voluntariado.getCartaoVTM());
		voluntariadoAlterado.setCiaerea(voluntariado.getCiaerea());
		voluntariadoAlterado.setCurso(voluntariado.getCurso());
		voluntariadoAlterado.setCursoEstuda(voluntariado.getCursoEstuda());
		voluntariadoAlterado.setDataChegadaAcomodacao(voluntariado.getDataChegadaAcomodacao());
		voluntariadoAlterado.setDataChegadaTransfer(voluntariado.getDataChegadaTransfer());
		voluntariadoAlterado.setDataEntregaDocumentoVisto(voluntariado.getDataEntregaDocumentoVisto());
		voluntariadoAlterado.setDataEstimadaTerminoCursoEstuda(voluntariado.getDataEstimadaTerminoCursoEstuda());
		voluntariadoAlterado.setDataInicio(voluntariado.getDataInicio());
		voluntariadoAlterado.setDataInicioSeguro(voluntariado.getDataInicioSeguro());
		voluntariadoAlterado.setDataInicioVoluntariado(voluntariado.getDataInicioVoluntariado());
		voluntariadoAlterado.setDataMatriculaCursoEstuda(voluntariado.getDataMatriculaCursoEstuda());
		voluntariadoAlterado.setDataPartidaAcomodacao(voluntariado.getDataPartidaAcomodacao());
		voluntariadoAlterado.setDataTermino(voluntariado.getDataTermino());
		voluntariadoAlterado.setDataTerminoSeguro(voluntariado.getDataTerminoSeguro());
		voluntariadoAlterado.setDataTerminoVoluntariado(voluntariado.getDataTerminoVoluntariado());
		voluntariadoAlterado.setDeficienciaFisica(voluntariado.getDeficienciaFisica());
		voluntariadoAlterado.setDescricaoCargo(voluntariado.getDescricaoCargo());
		voluntariadoAlterado.setDescricaoCirurgia(voluntariado.getDescricaoCirurgia());
		voluntariadoAlterado.setDescricaoDrogas(voluntariado.getDescricaoDrogas());
		voluntariadoAlterado.setDescricaoMedico(voluntariado.getDescricaoMedico());
		voluntariadoAlterado.setDescricaoProblemaSaude(voluntariado.getDescricaoProblemaSaude());
		voluntariadoAlterado.setDietaEspecifica(voluntariado.getDietaEspecifica());
		voluntariadoAlterado.setDuracaoCursoEstuda(voluntariado.getDuracaoCursoEstuda());
		voluntariadoAlterado.setEmailContatoEmergencia(voluntariado.getEmailContatoEmergencia());
		voluntariadoAlterado.setExperienciaVoluntariado(voluntariado.getExperienciaVoluntariado());
		voluntariadoAlterado.setFamiliaAnimais(voluntariado.getFamiliaAnimais());
		voluntariadoAlterado.setFamiliaCrianca(voluntariado.getFamiliaCrianca());
		voluntariadoAlterado.setFezCirurgia(voluntariado.getFezCirurgia());
		voluntariadoAlterado.setFoneContatoEmergencia(voluntariado.getFoneContatoEmergencia());
		voluntariadoAlterado.setFumante(voluntariado.getFumante());
		voluntariadoAlterado.setHobbies(voluntariado.getHobbies());
		voluntariadoAlterado.setHorario(voluntariado.getHorario());
		voluntariadoAlterado.setIdiomaEstudar(voluntariado.getIdiomaEstudar());
		voluntariadoAlterado.setInstituicaoEstuda(voluntariado.getInstituicaoEstuda());
		voluntariadoAlterado.setMeodaCartaoVTM(voluntariado.getMeodaCartaoVTM());
		voluntariadoAlterado.setMotivoVoluntariado(voluntariado.getMotivoVoluntariado());
		voluntariadoAlterado.setNivelFitness(voluntariado.getNivelFitness());
		voluntariadoAlterado.setNivelIdiomaEstudar(voluntariado.getNivelIdiomaEstudar());
		voluntariadoAlterado.setNomeContatoEmergencia(voluntariado.getNomeContatoEmergencia());
		voluntariadoAlterado.setNumerocartaoVTM(voluntariado.getNumerocartaoVTM());
		voluntariadoAlterado.setNumeroSemanas(voluntariado.getNumeroSemanas());
		voluntariadoAlterado.setNumeroSemanasAcomodacao(voluntariado.getNumeroSemanasAcomodacao());
		voluntariadoAlterado.setNumeroSemanasSeguro(voluntariado.getNumeroSemanasSeguro());
		voluntariadoAlterado.setObservacaoPassagem(voluntariado.getObservacaoPassagem());
		voluntariadoAlterado.setObservacaoVistoConsultar(voluntariado.getObservacaoVistoConsultar());
		voluntariadoAlterado.setPassagemAerea(voluntariado.getPassagemAerea());
		voluntariadoAlterado.setPeriodoCursoEstuda(voluntariado.getPeriodoCursoEstuda());
		voluntariadoAlterado.setPlanoSeguro(voluntariado.getPlanoSeguro());
		voluntariadoAlterado.setPossuiAlergia(voluntariado.getPossuiAlergia());
		voluntariadoAlterado.setPossuiProblemaSaude(voluntariado.getPossuiProblemaSaude());
		voluntariadoAlterado.setProfissao(voluntariado.getProfissao());
		voluntariadoAlterado.setProjetoVoluntariado(voluntariado.getProjetoVoluntariado());
		voluntariadoAlterado.setQuaisAlergias(voluntariado.getQuaisAlergias());
		voluntariadoAlterado.setRefeicoes(voluntariado.getRefeicoes());
		voluntariadoAlterado.setRelacaoContatoEmergencia(voluntariado.getRelacaoContatoEmergencia());
		voluntariadoAlterado.setSeguradora(voluntariado.getSeguradora());
		voluntariadoAlterado.setSeguroViagem(voluntariado.getSeguroViagem());
		voluntariadoAlterado.setSolicitacoesEspeciais(voluntariado.getSolicitacoesEspeciais());
		voluntariadoAlterado.setTipoAcomodacao(voluntariado.getTipoAcomodacao());
		voluntariadoAlterado.setTipoQuarto(voluntariado.getTipoQuarto());
		voluntariadoAlterado.setTransferin(voluntariado.getTransferin());
		voluntariadoAlterado.setTransferout(voluntariado.getTransferout());
		voluntariadoAlterado.setTratamentoDrogas(voluntariado.getTratamentoDrogas());
		voluntariadoAlterado.setTratamentoMedico(voluntariado.getTratamentoMedico());
		voluntariadoAlterado.setVegetariano(voluntariado.getVegetariano());
		voluntariadoAlterado.setVistoConsular(voluntariado.getVistoConsular());
		voluntariadoAlterado.setVoo(voluntariado.getVoo());
	}

	public void carregarSeguro() {
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			CheckBoxSeguroViagem = true;
			fornecedorSeguro = seguroViagem.getValoresseguro().getFornecedorcidade();
		} else {
			CheckBoxSeguroViagem = false;
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
		if (voluntariado.getIdvoluntariado() != null) {
			valorSaldoParcelar = 0.0f;
			parcelamentopagamento = new Parcelamentopagamento();
		}
		valorSaldoParcelar = venda.getValor() - valorParcelado;
		parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
	}

	public void carregarValorCambio() {
		cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), cambio.getMoedas());
		orcamento.setValorCambio(cambio.getValor());
		atualizarValoresProduto();
	}

	public void adicionarProdutos() {
		if (orcamento.getValorCambio() > 0) {
			int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			if (produtosorcamento.getIdprodutosOrcamento() != idProdTx) {
				if (produtosorcamento != null) {
					orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
					orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
					if ((valorMoedaEstrangeira > 0) && (orcamento.getValorCambio() > 0)) {
						valorMoedaReal = valorMoedaEstrangeira * orcamento.getValorCambio();
					} else {
						if ((valorMoedaReal > 0) && (orcamento.getValorCambio() > 0)) {
							valorMoedaEstrangeira = valorMoedaReal / orcamento.getValorCambio();
						}
					}
					boolean excluirDescontoTM = true;
					if (produtosorcamento.getValormaximo()==0) {
						orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
						orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
						orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					}else if (produtosorcamento.getValormaximo()>=valorMoedaReal){
						orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
						orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
						orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
						calcularValorTotalOrcamento();
						orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
					}else {
						FacesContext fc = FacesContext.getCurrentInstance();
				        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				        Map<String, Object> options = new HashMap<String, Object>();
						options.put("contentWidth", 230);
				        session.setAttribute("valorOriginal", 0f);
				        session.setAttribute("novoValor", 0f);
						RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
						//Mensagem.lancarMensagemErro("", "Valor máximo permitudo R$ "+ Formatacao.formatarFloatString(produtosorcamento.getValormaximo()));
						excluirDescontoTM = false;
					}
					if (excluirDescontoTM) {
						if (produtosorcamento.getIdprodutosOrcamento() == 33) {
							Filtroorcamentoproduto filtro = null;
							for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
								if (listaProdutosOrcamento.get(i).getProdutos().getIdprodutos()==aplicacaoMB.getParametrosprodutos().getCursos()) {
									if (listaProdutosOrcamento.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 33) {
										filtro = listaProdutosOrcamento.get(i);
									}
								}
							}
							listaProdutosOrcamento.remove(filtro);
						}
					}
					produtosorcamento = null;
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Taxa TM já esta inclusa", ""));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio não selecionado", ""));
		}
	}
	
	public void retornoDialogProdutoOrcamento() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String adicionar = (String) session.getAttribute("adicionar");
        session.removeAttribute("adicionar");
        if (adicionar != null) {
			if (adicionar.equalsIgnoreCase("sim")) {
				FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
				Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(aplicacaoMB.getParametrosprodutos().getCursos(), 33);
				Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
				orcamentoprodutosorcamento.setDescricao(filtroorcamentoproduto.getProdutosorcamento().getDescricao());
				orcamentoprodutosorcamento.setProdutosorcamento(filtroorcamentoproduto.getProdutosorcamento());
				if ((valorMoedaEstrangeira > 0) && (orcamento.getValorCambio() > 0)) {
					valorMoedaReal = valorMoedaEstrangeira * orcamento.getValorCambio();
				} else {
					if ((valorMoedaReal > 0) && (orcamento.getValorCambio() > 0)) {
						valorMoedaEstrangeira = valorMoedaReal / orcamento.getValorCambio();
					}
				}
				orcamentoprodutosorcamento . setValorMoedaEstrangeira (valorMoedaEstrangeira);
				orcamentoprodutosorcamento . setValorMoedaNacional (valorMoedaReal);
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				calcularValorTotalOrcamento();
				if (filtroorcamentoproduto.getProdutosorcamento().getIdprodutosOrcamento() == 33) {
					Filtroorcamentoproduto filtro = null;
					for (int i = 0; i < listaProdutosOrcamento.size(); i++) {
						if (listaProdutosOrcamento.get(i).getProdutos().getIdprodutos()==aplicacaoMB.getParametrosprodutos().getCursos()) {
							if (listaProdutosOrcamento.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 33) {
								filtro = listaProdutosOrcamento.get(i);
							}
						}
					}
					listaProdutosOrcamento.remove(filtro);
				}
				produtosorcamento = null;
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
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getDescontomatriz()) {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = orcamento.getOrcamentoprodutosorcamentoList().get(i)
							.getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == aplicacaoMB.getParametrosprodutos().getPromocaoescola()) {
					valorMoedaReal = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1;
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
			valorSaldoParcelar = formaPagamento.getValorTotal();
			valorMoedaEstrangeira = 0.0f;
			valorMoedaReal = 0.0f;
			calcularParcelamentoPagamento();
		}
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
				.getIdprodutosOrcamento() == tx) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Taxa TM não pode ser Excluída.", ""));
		} else {
			if (ilinha >= 0) {
				int idproduto = orcamento.getOrcamentoprodutosorcamentoList().get(ilinha).getProdutosorcamento()
						.getIdprodutosOrcamento();
				if (idproduto == aplicacaoMB.getParametrosprodutos().getSeguroOrcamento()) {
					seguroViagem.setPossuiSeguro("Não");
					carregarCamposSeguroPrivado();
				}
				if (idproduto == aplicacaoMB.getParametrosprodutos().getVistoOrcamento()) {
					voluntariado.setVistoConsular("clienteprovidencia");
				}
				FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
				if (idproduto == 33) {
					Filtroorcamentoproduto filtroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(aplicacaoMB.getParametrosprodutos().getCursos(), 33);
					if (listaProdutosOrcamento != null) {
						listaProdutosOrcamento.add(filtroorcamentoproduto);
					}
				}
				if (orcamento.getOrcamentoprodutosorcamentoList().get(ilinha)
						.getIdorcamentoProdutosOrcamento() != null) {
					OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
					orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento.getOrcamentoprodutosorcamentoList()
							.get(ilinha).getIdorcamentoProdutosOrcamento());
				}
				orcamento.getOrcamentoprodutosorcamentoList().remove(ilinha);
				calcularValorTotalOrcamento();
			}
		}
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
			formaPagamento.getParcelamentopagamentoList().remove(linha);
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
			calcularParcelamentoPagamento();
			parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
			parcelamentopagamento.setValorParcela(0.0f);
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
				if (venda.getIdvendas() != null) {
					if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamentopagamento = contasReceberBean.gerarParcelasIndividuais(parcelamentopagamento,
								formaPagamento.getParcelamentopagamentoList().size(), venda, usuarioLogadoMB, voluntariado.getDataInicio());
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamentopagamento);
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")
						|| (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("cheque"))) {
					parcelamentopagamento.setVerificarParcelamento(
							Formatacao.calcularDataParcelamento(parcelamentopagamento.getDiaVencimento(),
									parcelamentopagamento.getNumeroParcelas(), voluntariado.getDataInicioVoluntariado()));
				} else
					parcelamentopagamento.setVerificarParcelamento(false);
				if (parcelamentopagamento.isVerificarParcelamento()) {
					Mensagem.lancarMensagemWarn("Data Vencimento", "Data da ultima parcela dos "
							+ parcelamentopagamento.getFormaPagamento() + " é maior que data início do programa.");
				}
				if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
					DataVencimentoBean dataVencimentoBean = new DataVencimentoBean(parcelamentopagamento.getDiaVencimento());
					parcelamentopagamento.setDiaVencimento(dataVencimentoBean.validarDataVencimento());
				}
				parcelamentopagamento = new Parcelamentopagamento();
				calcularParcelamentoPagamento();
				parcelamentopagamento.setValorParcelamento(valorSaldoParcelar);
				parcelamentopagamento.setNumeroParcelas(01);
				parcelamentopagamento.setValorParcela(parcelamentopagamento.getValorParcelamento());
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
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

		if (parcelamentopagamento.getFormaPagamento().equalsIgnoreCase("sn")) {
			msg = msg + "Forma de pagamento não selecionada";
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
		if (parcelamentopagamento.getValorParcela() <= 0) {
			msg = msg + "Valor da parcela não pode ser 0";
		}
		if (parcelamentopagamento.getValorParcelamento() > (valorSaldoParcelar + 0.01)) {
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
		}
		return msg;
	}

	public void atualizarValoresProduto() {
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produto = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				int idSeguro = produto.getIdprodutosOrcamento();
				if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
						.getIdprodutosOrcamento() != idProdTx) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento() != idSeguro) {
						if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira() > 0) {
							orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
									orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaEstrangeira()
											* orcamento.getValorCambio());
						}
					}
				}
				calcularValorTotalOrcamento();
			}
		}
	}

	public String naoEnviarficha() throws SQLException {
		enviarFicha = false;
		if (confirmarFichaVoluntariado()) {
			return "consVoluntariado";
		}
		return "";
	}

	public String finalizarficha() throws SQLException {
		enviarFicha = true;
		if (confirmarFichaVoluntariado()) {
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consVoluntariado";
		}
		return "";
	}

	public boolean confirmarFichaVoluntariado() throws SQLException {
		boolean salvarOK = false;
		String msg = validarDados();
		String nsituacao = "";
		if (venda.getIdvendas() != null) {
			nsituacao = venda.getSituacao();
		}
		if (msg.length() < 5) {
			if (Formatacao.validarEmail(voluntariado.getEmailContatoEmergencia())) {
				if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
					if (enviarFicha) {
						novaFicha = true;
					}
				} else {
					enviarFicha = true;
				}
				if (enviarFicha) {
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
				} else {
					if (nsituacao.equalsIgnoreCase("")) {
						nsituacao = "PROCESSO";
					}
				}
				if (venda.getIdvendas() == null) {
					nsituacao = "PROCESSO";
				}
				ProgramasBean programasBean = new ProgramasBean();
				this.produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getVoluntariado());
				venda = programasBean.salvarVendas(venda, usuarioLogadoMB, nsituacao, cliente,
						formaPagamento.getValorTotal(), produto, fornecedorCidade, cambio, orcamento.getValorCambio(),
						lead, voluntariado.getDataInicio(), voluntariado.getDataTermino(), vendasDao, leadPosVendaDao, leadDao, leadSituacaoDao);
				CadVoluntariadoBean cadVoluntariadoBean = new CadVoluntariadoBean(venda, formaPagamento, orcamento,
						usuarioLogadoMB, valorSeguroAntigo);
				if (!novaFicha) {
					if (enviarFicha) {
						cadVoluntariadoBean.SalvarAlteracaoFinanceiro(listaParcelamentoPagamentoAntiga,
								listaParcelamentoPagamentoOriginal);
					}
				}
				voluntariado.setControle("Processo");
				voluntariado.setVendas(venda);
				if (fornecedorSeguro != null) {
					voluntariado.setDataInicioSeguro(seguroViagem.getDataInicio());
					voluntariado.setDataTerminoSeguro(seguroViagem.getDataTermino());
					voluntariado.setSeguroViagem(seguroViagem.getPossuiSeguro());
					if (fornecedorSeguro.getFornecedor() == null) {
						voluntariado.setSeguradora("");
					}else {
						voluntariado.setSeguradora(fornecedorSeguro.getFornecedor().getNome());
					}
					if (seguroViagem.getValoresseguro().getSeguroplanos() == null) {
						voluntariado.setPlanoSeguro("");
					}else {
						voluntariado.setPlanoSeguro(seguroViagem.getValoresseguro().getSeguroplanos().getNome());
					}
					voluntariado.setNumeroSemanasSeguro(seguroViagem.getNumeroSemanas());
				}
				voluntariado = cadVoluntariadoBean.salvarVoluntariado(voluntariado, vendaAlterada);
				cadVoluntariadoBean.salvarSeguroViagem(seguroViagem, aplicacaoMB, vendasDao);
				orcamento = cadVoluntariadoBean.salvarOrcamento(cambio, orcamento.getTotalMoedaNacional(),
						orcamento.getTotalMoedaEstrangeira(), orcamento.getValorCambio(), cambioAlterado);
				formaPagamento = cadVoluntariadoBean.salvarFormaPagamento(cancelamento);
				cliente = cadVoluntariadoBean.salvarCliente(cliente,
						Formatacao.ConvercaoDataPadrao(voluntariado.getDataInicioVoluntariado()),
						voluntariado.getDataTerminoVoluntariado(), null);
				if (!novaFicha)  {
					cadVoluntariadoBean.verificarDadosAlterado(voluntariado, voluntariadoAlterado, fornecedorCidade,
							vendaAlterada, valorVendaAlterar, seguroViagem, seguroViagemAlterado);
				}
				if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")) {
					int mes = Formatacao.getMesData(new Date()) + 1;
					int mesVenda = Formatacao.getMesData(venda.getDataVenda()) + 1;
					if (enviarFicha) {
						if (mes == mesVenda) {
							
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularMetaMensal(venda, valorVendaAlterar, false);
							dashBoardBean.calcularMetaAnual(venda, valorVendaAlterar, false);
							int numeroSemana = 0;
							if (voluntariado.isHabilitarCurso()) {
								numeroSemana = voluntariado.getNumeroSemanas();
							}
							int[] pontos = dashBoardBean.calcularPontuacao(venda, numeroSemana, "",
									false, venda.getUsuario());
							int pontoremover = vendaAlterada.getPonto();
							ProductRunnersCalculosBean productRunnersCalculosBean = new ProductRunnersCalculosBean();
							productRunnersCalculosBean.calcularPontuacao(venda, pontos[0], pontoremover, false, venda.getUsuario());
							venda.setPonto(pontos[0]);
							venda.setPontoescola(pontos[1]);
							
							venda = vendasDao.salvar(venda);
							
						}
						String titulo = "";
						String operacao = "";
						String imagemNotificacao = "";
						if (novaFicha) {
							titulo = "Nova Ficha de Voluntariado. " + venda.getIdvendas();
							operacao = "A";
							imagemNotificacao = "inserido";
						} else {
							titulo = "Ficha de Voluntariado Alterado. " + venda.getIdvendas();
							operacao = "I";
							imagemNotificacao = "alterado";
						}
						String vm = "Venda pela Matriz";
						if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
							vm = "Venda pela Loja";
						}
						DepartamentoFacade departamentoFacade = new DepartamentoFacade();
						List<Departamento> departamento = departamentoFacade
								.listar("select d From Departamento d where d.usuario.idusuario="
										+ venda.getProdutos().getIdgerente());
						if (departamento != null && departamento.size() > 0) {
							Formatacao.gravarNotificacaoVendas(titulo, venda.getUnidadenegocio(), cliente.getNome(),
									venda.getFornecedorcidade().getFornecedor().getNome(),
									Formatacao.ConvercaoDataPadrao(voluntariado.getDataInicioVoluntariado()),
									venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(),
									venda.getCambio().getMoedas().getSigla(), operacao, departamento.get(0),
									imagemNotificacao, "A");
						}
						// }
						// }.start();
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Voluntariado Salvo com Sucesso", ""));
				salvarOK = true;
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
		}
		return salvarOK;
	}
	
	

	public String validarDados() {

		if (fornecedorSeguro != null) {
			seguroViagem.setSeguradora(fornecedorSeguro.getFornecedor().getNome());
		}
		String msg = "";
		if (fornecedorCidade == null) {
			msg = msg + "Campo escola não selecionado     ";
		}
		if (cambio == null) {
			msg = msg + "Selecione câmbio da ficha     ";
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
		if (voluntariado.isHabilitarCurso()) {
			if (voluntariado.getCurso() == null) {
				msg = msg + "Curso não informado\r\n";
			}
			if (voluntariado.getAulasporSemana() == null) {
				msg = msg + "Aulas por semana não informada\r\n";
			}
			if (voluntariado.getNumeroSemanas() == null) {
				msg = msg + "Numero de semanas não informado\r\n";
			}
		}
		if (voluntariado.getDataInicioVoluntariado() == null) {
			msg = msg + "Data início inválida\r\n";
		}
		if (voluntariado.getDataTerminoVoluntariado() == null) {
			msg = msg + "Data términio inválida\r\n";
		}
		if (!voluntariado.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			if (voluntariado.getDataChegadaAcomodacao() == null) {
				msg = msg + "Data chegada na acomodação inválida\r\n";
			}
			if (voluntariado.getDataPartidaAcomodacao() == null) {
				msg = msg + "Data partida acomodação inválida\r\n";
			}
			if (voluntariado.getNumeroSemanasAcomodacao() == null) {
				msg = msg + "Numero de semanas de acomodação não informado\r\n";
			}
		}
		if (voluntariado.getCartaoVTM().equalsIgnoreCase("Sim")) {
			if (voluntariado.getNumerocartaoVTM() == null) {
				msg = msg + "Nº Cartão VTM não informado\r\n";
			}
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			if (seguroViagem.getSeguradora() == null) {
				msg = msg + "Seguradora não informada\r\n";
			}
			if (seguroViagem.getValorSeguro() == 0) {
				msg = msg + "Valor do seguro não informado\r\n";
			}
			if (seguroViagem.getValoresseguro() == null) {
				msg = msg + "Plano do seguro não informado\r\n";
			}
			if (seguroViagem.getDataInicio() == null) {
				msg = msg + "Data início seguro inválida\r\n";
			}
			if (seguroViagem.getDataTermino() == null) {
				msg = msg + "Data término seguro inválida\r\n";
			}
			if (this.fornecedorCidade == null) {
				msg = msg + "Escola inválida\r\n";
			}
		}
		if (voluntariado.getNomeContatoEmergencia() == null) {
			msg = msg + "Nome do contato de emergência não informado\r\n";
		}
		if (voluntariado.getFoneContatoEmergencia() == null) {
			msg = msg + "Nº telefone  do contato de emergência não informado\r\n";
		}
		if (voluntariado.getRelacaoContatoEmergencia() == null) {
			msg = msg + "Relação do contato de emergência não informado\r\n";
		}
		if (formaPagamento.getParcelamentopagamentoList() == null) {
			msg = msg + "Forma de Pagamento com erro\r\n";
		} else {
			if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			}
		}

		double saldoParcelar = this.valorSaldoParcelar;
		if (saldoParcelar > 0.01f) {
			msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
		}
		
		if (saldoParcelar < -1f) {
			msg = msg + "Saldo a parcelar negativo";
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			if (seguroViagem.getNumeroSemanas() == 0) {
				msg = msg + "Nº de semanas no seguro é obrigatório\r\n";
			}
		}
		return msg;
	}

	public Float calcularComissao() throws SQLException {
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		fornecedorComissao = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
		if (fornecedorComissao != null) {
			Vendascomissao vendasComissao = venda.getVendascomissao();
			if (vendasComissao == null) {
				vendasComissao = new Vendascomissao();
				vendasComissao.setVendas(venda);
				vendasComissao.setPaga("Não");
			}
			float valorJuros = 0.0f;
			if (venda.getFormapagamento() != null) {
				valorJuros = venda.getFormapagamento().getValorJuros();
			}
			if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
				ComissaoVoluntariadoBean cc = new ComissaoVoluntariadoBean(aplicacaoMB, venda,
						orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao,
						formaPagamento.getParcelamentopagamentoList(), voluntariado.getDataInicioVoluntariado(), vendasComissao,
						valorJuros, true);
				return cc.getVendasComissao().getValorfornecedor();
			}
		}
		return 0.0f;
	}

	public String cancelarCadastro() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consVoluntariado";
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade()
					+ " and f.produtos.idprodutos=" + aplicacaoMB.getParametrosprodutos().getVoluntariado()+
					" and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}

	public void iniciarListaFornecedorSeguro() {
		int idProduto = (int) aplicacaoMB.getParametrosprodutos().getCartao01(); 
		listaFornecedorCidadeSeguro = GerarListas.listarFornecedorSeguro(idProduto);
	}

	public void gerarListaProdutos() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getVoluntariado()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void calcularDataTermino() {
		boolean idade70 = false;
		boolean idade40 = false;
		if (cliente != null) {
			int idadeCliente = Formatacao.calcularIdade(cliente.getDataNascimento());
			if (seguroViagem.getValoresseguro().isAdiconal70()) {
				if (idadeCliente >= 70) {
					idade70 = true;
					dataTermino70anos();
					Mensagem.lancarMensagemInfo("Atenção",
							"aplica-se aumento de 50 % no valor do seguro para clientes acima de 70 anos!");
				} else {
					dataTermino();
				}
			} else {
				if (idadeCliente >= 40) {
					idade40 = true;
					Mensagem.lancarMensagemErro("Atenção", "Cliente acima da idade permitida!");
				} else {
					dataTermino();
				}
			}
		} else
			Mensagem.lancarMensagemErro("Atenção", "Cliente não selecionado");
	}

	public void dataTermino() {
		if ((seguroViagem.getDataInicio() != null) && (seguroViagem.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				seguroViagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroViagem.getDataInicio(),
						seguroViagem.getNumeroSemanas()));
				float valornacional = seguroViagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				if (seguroViagem.getValoresseguro().getCobranca().equalsIgnoreCase("Fixo")) {
					seguroViagem.setValorSeguro(valornacional);
				} else
					seguroViagem.setValorSeguro(valornacional * seguroViagem.getNumeroSemanas());
				calcularValorSeguroPrivadoListaProdutos();
			}
		}
	}

	public void dataTermino70anos() {
		if ((seguroViagem.getDataInicio() != null) && (seguroViagem.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				seguroViagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroViagem.getDataInicio(),
						seguroViagem.getNumeroSemanas()));
				float valornacional = seguroViagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				valornacional = (float) (valornacional * 1.5);
				if (seguroViagem.getValoresseguro().getCobranca().equalsIgnoreCase("Fixo")) {
					seguroViagem.setValorSeguro(valornacional);
				} else
					seguroViagem.setValorSeguro(valornacional * seguroViagem.getNumeroSemanas());
				calcularValorSeguroPrivadoListaProdutos();
			}
		}
	}

	public void carregarCobrancaSeguro() {
		seguroViagem.getValoresseguro().setCobranca(seguroViagem.getValoresseguro().getCobranca());
		verificarSeguroCancelamento();
	}

	public void calcularValorParcelas() {
		if (parcelamentopagamento.getValorParcelamento() > 0) {
			parcelamentopagamento.setValorParcela(
					parcelamentopagamento.getValorParcelamento() / parcelamentopagamento.getNumeroParcelas());
		}
	}

	public void carregarCamposAcomodacao() {
		if (voluntariado.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			camposAcomodacaoCasaFamilia = "true";
			voluntariado.setTipoQuarto("Sem quarto");
			voluntariado.setRefeicoes("Sem Refeição");
			voluntariado.setDataChegadaAcomodacao(null);
			voluntariado.setNumeroSemanasAcomodacao(null);
			voluntariado.setDataPartidaAcomodacao(null);
		} else {
			camposAcomodacao = "false";
			camposAcomodacaoCasaFamilia = "true";
		}
		if (voluntariado.getTipoAcomodacao().equalsIgnoreCase("Casa de família")) {
			camposAcomodacaoCasaFamilia = "false";
			camposAcomodacao = "false";
		}
	}

	public void carregarCamposSeguroPrivado() {
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			camposSeguroViagem = "false";
			if (seguroViagem.getValoresseguro() == null) {
				seguroViagem.setValoresseguro(new Valoresseguro());
			}
		} else {
			seguroViagem.setValorSeguro(0.0f);
			seguroViagem.setPossuiSeguro("Não");
			seguroViagem.setDataInicio(null);
			seguroViagem.setDataTermino(null);
			seguroViagem.setNumeroSemanas(0);
			seguroViagem.setPlano(" ");
			seguroViagem.setSeguradora("");
			seguroViagem.setValorSeguro(0.0f);
			seguroViagem.setFoneContatoEmergencia("");
			seguroViagem.setNomeContatoEmergencia("");
			seguroViagem.setPaisDestino("");
			camposSeguroViagem = "true";
			seguroViagem.setValoresseguro(null);
			fornecedorSeguro = null;
			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
				int idseguroViagem = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
					int idProdutoOrcamento = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento();
					if (idseguroViagem == idProdutoOrcamento) {
						if (orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getIdorcamentoProdutosOrcamento() != null) {
							OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
							orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento
									.getOrcamentoprodutosorcamentoList().get(i).getIdorcamentoProdutosOrcamento());
						}
						orcamento.getOrcamentoprodutosorcamentoList().remove(i);
						calcularValorTotalOrcamento();
						calcularParcelamentoPagamento();
						i = 1000;
					}
				}
			}
		}
	}

	public void calcularDataTerminoCurso() {
		if ((voluntariado.getDataInicio() != null) && (voluntariado.getNumeroSemanas() != null)) {
			if (voluntariado.getNumeroSemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(voluntariado.getDataInicio(), voluntariado.getNumeroSemanas());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
				}
				voluntariado.setDataTermino(data);
			}
		}
	}

	public void calcularDataTerminoAcomodacao() {
		if ((voluntariado.getDataChegadaAcomodacao() != null) && (voluntariado.getNumeroSemanasAcomodacao() != null)) {
			if (voluntariado.getNumeroSemanasAcomodacao() > 0) {
				Date data = Formatacao.calcularDataFinal(voluntariado.getDataChegadaAcomodacao(),
						voluntariado.getNumeroSemanasAcomodacao());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 6) {
						data = Formatacao.SomarDiasDatas(data, 2);
					}
					voluntariado.setDataPartidaAcomodacao(data);
				} catch (Exception ex) {

				}
			}
		}
	}

	public void carregarCamposCartaoVTM() {
		if (voluntariado.getCartaoVTM().equalsIgnoreCase("Sim")) {
			camposCartaoVtm = "false";
		} else {
			camposCartaoVtm = "true";
			voluntariado.setNumerocartaoVTM(null);
		}
	}

	public void iniciarNovoVoluntariado() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		fornecedorComissao = new Fornecedorcomissaocurso();
		produtosorcamento = new Produtosorcamento();
		produto = new Produtos();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		cambio.setMoedas(new Moedas());
		voluntariado = new Voluntariado();
		orcamento = new Orcamento();
		orcamento.setValorCambio(0.0f);
		orcamento.setTotalMoedaEstrangeira(0.0f);
		orcamento.setTotalMoedaNacional(0.0f);
		orcamento.setOrcamentoprodutosorcamentoList(new ArrayList<Orcamentoprodutosorcamento>());
		venda = new Vendas();
		formaPagamento = new Formapagamento();
		formaPagamento.setValorJuros(0.0f);
		formaPagamento.setValorOrcamento(0.0f);
		formaPagamento.setValorTotal(0.0f);
		formaPagamento.setPossuiJuros("Não");
		produto = new Produtos();
		cidade = new Cidade();
		seguroViagem = new Seguroviagem();
		seguroViagem.setValoresseguro(new Valoresseguro());
		seguroplanos = new Seguroplanos();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = produtoOrcamentoFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setProdutosorcamento(produtosorcamento);
		orcamentoprodutosorcamento.setDescricao(produtosorcamento.getDescricao());
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
		orcamentoprodutosorcamento.setValorMoedaNacional(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
		parcelamentopagamento = new Parcelamentopagamento();
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		voluntariado.setCartaoVTM("Não");
		voluntariado.setPassagemAerea("Cliente Providenciará");
		voluntariado.setVistoConsular("Cliente Providenciará");
		seguroViagem.setPossuiSeguro("Não");
		venda.setSituacao("PROCESSO");
		venda.setDataVenda(new Date());
		novaFicha = true;
	}

	public void iniciarAlteracaoVoluntariado() {
		// Vendas
		this.venda = voluntariado.getVendas();
		valorVendaAlterar = venda.getValor();
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			vendaAlterada = venda;
		}
		this.cliente = venda.getCliente();
		carregarVoluntariadoAlteracao();
		// acomodacao
		if (voluntariado.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			camposAcomodacaoCasaFamilia = "true";
		} else {
			camposAcomodacao = "false";
			camposAcomodacaoCasaFamilia = "true";
		}
		if (voluntariado.getTipoAcomodacao().equalsIgnoreCase("Casa de família")) {
			camposAcomodacaoCasaFamilia = "false";
			camposAcomodacao = "false";
		}
		// cartao vtm
		if (voluntariado.getCartaoVTM().equalsIgnoreCase("Sim")) {
			camposCartaoVtm = "false";
		} else {
			camposCartaoVtm = "true";
		}

		fornecedorCidade = venda.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		listarFornecedorCidade();
		fornecedorCidade = venda.getFornecedorcidade();
		fornecedorCidadeAlterado = fornecedorCidade;

		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		fornecedorComissao = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		this.seguroViagem = seguroViagemController.consultarSeguroCurso(venda.getIdvendas());
		if (seguroViagem == null) {
			this.seguroViagem = seguroViagemController.consultar(venda.getIdvendas());
		}
		if (seguroViagem != null) {
			seguroViagemAlterado = seguroViagem;
			fornecedorSeguro = seguroViagem.getValoresseguro().getFornecedorcidade();
			fornecedorSeguro = seguroViagem.getValoresseguro().getFornecedorcidade();
			listarPlanosSeguro();
			seguroplanos = seguroViagem.getValoresseguro().getSeguroplanos();
			listarValoresSeguro();
			valorSeguroAntigo = seguroViagem.getValorSeguro();
			segurocancelamento = seguroViagem.isSegurocancelamento();
			verificarSeguroCancelamento();
		} else {
			seguroViagem = new Seguroviagem();
			seguroViagem.setPossuiSeguro("Não");
			seguroViagem.setValoresseguro(null);
		}
		if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
			carregarSeguro();
			camposSeguroViagem = "false";
		} else {
			camposSeguroViagem = "true";
		}
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		this.formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
		if (formaPagamento != null) {
			carregarCamposFormaPagamento();
			listaParcelamentoPagamentoAntiga = new ArrayList<>();
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
				listaParcelamentoPagamentoAntiga.add(parcelamentopagamento);
			}
		}
		OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
		orcamento = orcamentoFacade.consultar(venda.getIdvendas());
		if (orcamento != null) {
			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
					if (orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() < 0) {
						orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaNacional(
								orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional() * -1);
					}
				}
			}

			dataCambio = orcamento.getCambio().getData();
			cambio = orcamento.getCambio();
			moeda = orcamento.getCambio().getMoedas();
			carregarCambio();
			calcularValorTotalOrcamento();
			carregarCamposAcomodacao();
		}
	}

	public void carregarCambio() {
		CambioFacade cambioFacade = new CambioFacade();
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(venda.getDatavalidade(), new Date());
			if (dias > 3) {
				int idMoedaVenda = venda.getCambio().getMoedas().getIdmoedas();
				for (int i = 0; i < aplicacaoMB.getListaCambio().size(); i++) {
					int idUltimaMoeda = aplicacaoMB.getListaCambio().get(i).getMoedas().getIdmoedas();
					if (idMoedaVenda == idUltimaMoeda) {
						cambio = aplicacaoMB.getListaCambio().get(i);
						i = 1000000;
					}
				}
				if (cambio != null) {
					habilitarAvisoCambio = true;
					orcamento.setValorCambio(cambio.getValor());
					cambioAlterado = "Não";
					atualizarValoresProduto();
				}
			} else {
				cambioAlterado = orcamento.getCambioAlterado();
			}
		} else {
			cambioAlterado = orcamento.getCambioAlterado();
		}
	}
	

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getVoluntariado());
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

	public void calcularValorSeguroPrivadoListaProdutos() {
		int codSeguroPrivado = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
		List<Orcamentoprodutosorcamento> listaSeguro = new ArrayList<Orcamentoprodutosorcamento>();
		for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
			int codigoLista = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
					.getIdprodutosOrcamento();
			if (codSeguroPrivado == codigoLista) {
				listaSeguro.add(orcamento.getOrcamentoprodutosorcamentoList().get(i));
			}
		}
		for (int i = 0; i < listaSeguro.size(); i++) {
			orcamento.getOrcamentoprodutosorcamentoList().remove(listaSeguro.get(i));
			if (listaSeguro.get(i).getIdorcamentoProdutosOrcamento() != null) {
				OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
				orcamentoFacade.excluirOrcamentoProdutoOrcamento(listaSeguro.get(i).getIdorcamentoProdutosOrcamento());
			}
		}
		float valorEstrangeira = 0.0f;
		float valorReal = 0.0f;
		Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		if (seguroViagem != null) {
			if (seguroViagem.getPossuiSeguro().equalsIgnoreCase("Sim")) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produto = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				orcamentoprodutosorcamento.setProdutosorcamento(produto);
				orcamentoprodutosorcamento.setDescricao(produto.getDescricao());
				if (seguroViagem.getValorSeguro() > 0) {
					valorReal = seguroViagem.getValorSeguro();
					orcamentoprodutosorcamento.setValorMoedaNacional(valorReal);
					orcamentoprodutosorcamento.setValorMoedaEstrangeira(valorReal / cambio.getValor());
				} else {
					orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
					valorReal = 0;
					valorEstrangeira = 0;
				}
				orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
				calcularValorTotalOrcamento();
				calcularParcelamentoPagamento();
			}
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

	public void retornoDialogEditarCambio(SelectEvent event) {
		float valorCambio = (float) event.getObject();
		orcamento.setValorCambio(valorCambio);
		atualizarValoresProduto();
	}

	public void carregarFornecedorComissao() {
		if (fornecedorCidade != null) {
			FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
			fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
					fornecedorCidade.getFornecedor().getIdfornecedor(),
					fornecedorCidade.getCidade().getPais().getIdpais());
			if (fornecedorComissao == null) {
				pais = null;
				cidade = null;
				fornecedorCidade = null;
			}
		}
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

	public void listarPlanosSeguro() {
		if (fornecedorSeguro != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorSeguro.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorSeguro != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorSeguro.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}

	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	public void consultarCambio() {
		if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			int dias = Formatacao.subtrairDatas(new Date(), venda.getDataVenda());
			if (dias > 3) {
				Mensagem.lancarMensagemInfo("", "Cambio alterado para o dia atual");
			}
			CambioFacade cambioFacade = new CambioFacade();
			cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio), moeda.getIdmoedas());
			if (cambio != null) {
				orcamento.setValorCambio(cambio.getValor());
				atualizarValoresProduto();
			}
		} else {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha já finalizada!");
		}
	}

	public void habilitarVisto() {
		if (voluntariado.getVistoConsular().equalsIgnoreCase("Através da TravelMate")) {
			camposVisto = "false";
		} else
			camposVisto = "true";
			if (voluntariado != null) {
				if (voluntariado.getDataEntregaDocumentoVisto() != null) {
					voluntariado.setDataEntregaDocumentoVisto(null);
				}
				if (voluntariado.getObservacaoVistoConsultar() != null) {
					voluntariado.setObservacaoVistoConsultar("");
				}
			}
	}

	public void habilitarPassagem() {
		if (voluntariado.getPassagemAerea().equalsIgnoreCase("Através da TravelMate")) {
			camposPassagem = "false";
		} else
			camposPassagem = "true";
	}

	public void gerarListaTipoParcelamento() {
		listaTipoParcelamento = Formatacao.gerarListaTipoParcelamento(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().isParcelamentojoja(),
				parcelamentopagamento.getFormaPagamento(),
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
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

	public void verificarAlteracaoListaParcelamento() {
		if (formaPagamento != null) {
			if (formaPagamento.getParcelamentopagamentoList() != null) {
				for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
					formaPagamento.getParcelamentopagamentoList().get(i)
							.setVerificarParcelamento(Formatacao.calcularDataParcelamento(
									formaPagamento.getParcelamentopagamentoList().get(i).getDiaVencimento(),
									formaPagamento.getParcelamentopagamentoList().get(i).getNumeroParcelas(),
									voluntariado.getDataInicio()));
				}
			}
		}
	}

	public String validarMascaraFoneContatoEmergencia() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneContatoEmergencia);
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

	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			consultarCambio();
		}
		int idTaxaTm = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
			for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
				int idProdutoOrcamento =orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento().getIdprodutosOrcamento();
				if (idProdutoOrcamento == idTaxaTm) {
					float valorNacional = orcamento.getOrcamentoprodutosorcamentoList().get(i).getValorMoedaNacional();
					orcamento.getOrcamentoprodutosorcamentoList().get(i).setValorMoedaEstrangeira(valorNacional/cambio.getValor());
					i = 10000;
				}
			}
		}
	}
	
	public void adicionarSeguroCancelamento() {
		if (seguroViagem.isSegurocancelamento() && seguroViagem.getValoresseguro().isSegurocancelamento()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento(); 
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			Produtosorcamento produto = produtoOrcamentoFacade
					.consultar(aplicacaoMB.getParametrosprodutos().getSegurocancelamentoid());
			orcamentoprodutosorcamento.setProdutosorcamento(produto);
			orcamentoprodutosorcamento.setDescricao(produto.getDescricao());
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas()); 
			orcamentoprodutosorcamento.setValorMoedaNacional(
					seguroViagem.getValoresseguro().getValorsegurocancelamento()*cambioSeguro.getValor()); 
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(orcamentoprodutosorcamento.getValorMoedaNacional()/cambio.getValor());
			orcamento.getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			calcularValorTotalOrcamento();
			calcularParcelamentoPagamento();
		} else {
			seguroViagem.setSegurocancelamento(false);
			if (orcamento.getOrcamentoprodutosorcamentoList() != null) {
				int idseguroCancelamento = aplicacaoMB.getParametrosprodutos().getSegurocancelamentoid();
				for (int i = 0; i < orcamento.getOrcamentoprodutosorcamentoList().size(); i++) {
					int idProdutoOrcamento = orcamento.getOrcamentoprodutosorcamentoList().get(i).getProdutosorcamento()
							.getIdprodutosOrcamento();
					if (idseguroCancelamento == idProdutoOrcamento) {
						if (orcamento.getOrcamentoprodutosorcamentoList().get(i)
								.getIdorcamentoProdutosOrcamento() != null) {
							OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
							orcamentoFacade.excluirOrcamentoProdutoOrcamento(orcamento
									.getOrcamentoprodutosorcamentoList().get(i).getIdorcamentoProdutosOrcamento());
						}
						orcamento.getOrcamentoprodutosorcamentoList().remove(i);
						calcularValorTotalOrcamento();
						calcularParcelamentoPagamento();
						i = 1000;
					}
				}
			}
		}
	}
	
	public boolean habilitarTrocaCliente() {
		if(novaFicha) {
			return false;
		}else return true;
	}
	
	public void verificarSeguroCancelamento() {
		if(seguroViagem.getValoresseguro().isSegurocancelamento()) {
			segurocancelamento = true; 
		} else {
			segurocancelamento = false; 
		}
	} 
	
	
	public void desabilitarCamposCurso(){
		if (voluntariado.isHabilitarCurso()) {
			desabilitarCamposCurso = false;
		}else{
			desabilitarCamposCurso = true;
		}
	}
	
	
	public void fecharNotificacao() {
		habilitarAvisoCambio = false;
	}
	
}