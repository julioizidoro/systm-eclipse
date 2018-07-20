package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
 
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.FornecedorFeriasFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.facade.OCursoDescontoFacade;
import br.com.travelmate.facade.OCursoFormaPagamentoFacade;
import br.com.travelmate.facade.OCursoProdutoFacade;
import br.com.travelmate.facade.OcursoSeguroViagemFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoCursoFornecedorCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorferias;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.model.Ocursoseguro;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.model.Promocaocurso;
import br.com.travelmate.model.Promocaocursocidade;
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class OrcamentoCursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private ProdutosOrcamentoBean produtosOrcamentoBean;
	private boolean seguroSelecionado;
	private boolean obrigatorioSelecionado = true;
	private Seguroviagem seguroviagem;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private float valorTotal;
	private float valorTotalRS;
	private float valorTotalAdicionais;
	private float valorTotalAdicionaisRS;
	private Valoresseguro valorSeguro;
	private String acomodacaoHabiliada;
	private String style = "font-weight:bold;font-size: 13px;margin-left:0%;color:#fff";
	private String styleDados = "font-weight:bold;font-size: 13px;margin-left:0%;color:#fff";
	private String campoPromocaoImg;
	private String campoPromocao;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<Valoresseguro> listaValoresSeguro;
	private Cambio cambioSeguro; 
	private String pacoteAcomodacao;
	private String pacoteTransfer;
	private String escoderOpcionais;
	private String habilitarOpcaoPacote;
	private String numeroSemanas;
	private float valorUtilitarioRS = 0.0f;
	private float valorTotalSeguroDola = 0.0f;
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;
	private boolean segurocancelamento=false;
	private String numero="3";
	private List<ProdutosOrcamentoBean> listaOpcionais;
	private boolean desabilitarbtnOpcional = false;
	private float valorOriginalMulta = 0f;
	private String pinCambio;
	private boolean habilitarPin = false;

	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		session.removeAttribute("resultadoOrcamentoBean"); 
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = aplicacaoMB.getParametrosprodutos().getSeguroPrivado();
		List<Paisproduto> listaPais = paisProdutoFacade.listar(idProduto);
		if ((listaPais.size() > 0) && (listaPais != null)) {
			listaFornecedorCidade = listaPais.get(0).getProdutos().getFornecedorcidadeList();
		} else listaFornecedorCidade = new ArrayList<Fornecedorcidade>(); 
		if (seguroviagem == null) {
			seguroviagem = new Seguroviagem();
			fornecedorcidade = new Fornecedorcidade();
			seguroplanos = new Seguroplanos(); 
			valorSeguro = new Valoresseguro();
		} else {
			fornecedorcidade = seguroviagem.getValoresseguro().getFornecedorcidade();
			listarPlanosSeguro();
			seguroplanos = seguroviagem.getValoresseguro().getSeguroplanos();
			listarValoresSeguro();
			valorSeguro = seguroviagem.getValoresseguro();
		}
		if(resultadoOrcamentoBean.isSeguroSelecionado()){
			seguroSelecionado = true;
			seguroviagem = resultadoOrcamentoBean.getSeguroviagem();
			fornecedorcidade = resultadoOrcamentoBean.getSeguroviagem().getValoresseguro().getFornecedorcidade();
			listarPlanosSeguro();
			seguroplanos = resultadoOrcamentoBean.getSeguroviagem().getValoresseguro().getSeguroplanos();
			listarValoresSeguro();
			valorSeguro = resultadoOrcamentoBean.getSeguroviagem().getValoresseguro(); 
			convertendoValoresSeguro();
			verificarSeguroCancelamento();
			if (cambioSeguro == null) {
				CambioFacade cambioFacade = new CambioFacade();
				cambioSeguro = cambioFacade.consultarCambioMoeda(
						Formatacao.ConvercaoDataSql(aplicacaoMB.getListaCambio().get(0).getData()),
						valorSeguro.getMoedas().getIdmoedas());
			}
		}   
		if(resultadoOrcamentoBean.getListaOutrosProdutos()==null){
			resultadoOrcamentoBean.setListaOutrosProdutos(new ArrayList<ProdutosExtrasBean>());
		}
		if(resultadoOrcamentoBean.getListaAcomodacoes()==null){
			resultadoOrcamentoBean.setListaAcomodacoes(new ArrayList<ProdutosOrcamentoBean>());
		}
		if(resultadoOrcamentoBean.getListaTransfer()==null){
			resultadoOrcamentoBean.setListaTransfer(new ArrayList<ProdutosOrcamentoBean>());
		}
		if(resultadoOrcamentoBean.getListaAcOpcional()==null){
			resultadoOrcamentoBean.setListaAcOpcional(new ArrayList<ProdutosOrcamentoBean>());
		}
		calcularTotais();
		carregarCampoPromocao();
		verificarPacote();
		verificarPacoteAcomodacao();
		verificarPacoteTransfer();
		verificarNumeroSemanas();  
		listaOpcionais = resultadoOrcamentoBean.getListaOpcionais();
		resultadoOrcamentoBean.setListaOpcionais(new ArrayList<ProdutosOrcamentoBean>());
		if (listaOpcionais == null || listaOpcionais.size() <= 0) {
			desabilitarbtnOpcional = true;
		}
	}

	public String getCampoPromocaoImg() {
		return campoPromocaoImg;
	}

	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}

	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}

	public void setCampoPromocaoImg(String campoPromocaoImg) {
		this.campoPromocaoImg = campoPromocaoImg;
	}

	public String getCampoPromocao() {
		return campoPromocao;
	}

	public void setCampoPromocao(String campoPromocao) {
		this.campoPromocao = campoPromocao;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isSeguroSelecionado() {
		return seguroSelecionado;
	}

	public void setSeguroSelecionado(boolean seguroSelecionado) {
		this.seguroSelecionado = seguroSelecionado;

	}

	public boolean isObrigatorioSelecionado() {
		return obrigatorioSelecionado;
	}

	public void setObrigatorioSelecionado(boolean obrigatorioSelecionado) {
		this.obrigatorioSelecionado = obrigatorioSelecionado;
	}

	public Seguroviagem getSeguroviagem() {
		return seguroviagem;
	}

	public void setSeguroviagem(Seguroviagem seguroviagem) {
		this.seguroviagem = seguroviagem;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public float getValorTotalRS() {
		return valorTotalRS;
	}

	public void setValorTotalRS(float valorTotalRS) {
		this.valorTotalRS = valorTotalRS;
	}

	public Valoresseguro getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(Valoresseguro valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public String getAcomodacaoHabiliada() {
		return acomodacaoHabiliada;
	}

	public void setAcomodacaoHabiliada(String acomodacaoHabiliada) {
		this.acomodacaoHabiliada = acomodacaoHabiliada;
	}

	public ProdutosOrcamentoBean getProdutosOrcamentoBean() {
		return produtosOrcamentoBean;
	}

	public void setProdutosOrcamentoBean(ProdutosOrcamentoBean produtosOrcamentoBean) {
		this.produtosOrcamentoBean = produtosOrcamentoBean;
	}

	public String getStyleDados() {
		return styleDados;
	}

	public void setStyleDados(String styleDados) {
		this.styleDados = styleDados;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Cambio getCambioSeguro() {
		return cambioSeguro;
	}

	public void setCambioSeguro(Cambio cambioSeguro) {
		this.cambioSeguro = cambioSeguro;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}
 
	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}

	public float getValorTotalAdicionais() {
		return valorTotalAdicionais;
	}

	public void setValorTotalAdicionais(float valorTotalAdicionais) {
		this.valorTotalAdicionais = valorTotalAdicionais;
	}

	public float getValorTotalAdicionaisRS() {
		return valorTotalAdicionaisRS;
	}

	public void setValorTotalAdicionaisRS(float valorTotalAdicionaisRS) {
		this.valorTotalAdicionaisRS = valorTotalAdicionaisRS;
	}

	public String getPacoteAcomodacao() {
		return pacoteAcomodacao;
	}

	public void setPacoteAcomodacao(String pacoteAcomodacao) {
		this.pacoteAcomodacao = pacoteAcomodacao;
	}

	public String getPacoteTransfer() {
		return pacoteTransfer;
	}

	public void setPacoteTransfer(String pacoteTransfer) {
		this.pacoteTransfer = pacoteTransfer;
	}

	public String getEscoderOpcionais() {
		return escoderOpcionais;
	}

	public void setEscoderOpcionais(String escoderOpcionais) {
		this.escoderOpcionais = escoderOpcionais;
	}

	public String getHabilitarOpcaoPacote() {
		return habilitarOpcaoPacote;
	}

	public void setHabilitarOpcaoPacote(String habilitarOpcaoPacote) {
		this.habilitarOpcaoPacote = habilitarOpcaoPacote;
	}

	public String getNumeroSemanas() {
		return numeroSemanas;
	}

	public void setNumeroSemanas(String numeroSemanas) {
		this.numeroSemanas = numeroSemanas;
	}

	public float getValorUtilitarioRS() {
		return valorUtilitarioRS;
	}

	public void setValorUtilitarioRS(float valorUtilitarioRS) {
		this.valorUtilitarioRS = valorUtilitarioRS;
	}

	public float getValorTotalSeguroDola() {
		return valorTotalSeguroDola;
	}

	public void setValorTotalSeguroDola(float valorTotalSeguroDola) {
		this.valorTotalSeguroDola = valorTotalSeguroDola;
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

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<ProdutosOrcamentoBean> getListaOpcionais() {
		return listaOpcionais;
	}

	public void setListaOpcionais(List<ProdutosOrcamentoBean> listaOpcionais) {
		this.listaOpcionais = listaOpcionais;
	}

	public boolean isDesabilitarbtnOpcional() {
		return desabilitarbtnOpcional;
	}

	public void setDesabilitarbtnOpcional(boolean desabilitarbtnOpcional) {
		this.desabilitarbtnOpcional = desabilitarbtnOpcional;
	}

	public float getValorOriginalMulta() {
		return valorOriginalMulta;
	}

	public void setValorOriginalMulta(float valorOriginalMulta) {
		this.valorOriginalMulta = valorOriginalMulta;
	}

	public String getPinCambio() {
		return pinCambio;
	}

	public void setPinCambio(String pinCambio) {
		this.pinCambio = pinCambio;
	}

	public boolean isHabilitarPin() {
		return habilitarPin;
	}

	public void setHabilitarPin(boolean habilitarPin) {
		this.habilitarPin = habilitarPin;
	}

	public String habilitarSeguro() {
		if (seguroSelecionado) {
			return acomodacaoHabiliada = "false";
		}
		return acomodacaoHabiliada = "true";
	}

	public String srcLogo(Fornecedor fornecedor) {
		String logo = "";
		if (fornecedor != null) {
			logo = "../../resources/imgLogoEscola/" + fornecedor.getLogo();
		}
		return logo;
	}

	public String retornarValorString(Float valor, String sigla) {
		String svalor = "";
		if (valor != null) {
			if (sigla == null || sigla.equalsIgnoreCase("")) {
				sigla = "R$";
			}
			svalor = sigla + " " + Formatacao.formatarFloatString(valor);
		}
		return svalor;
	}

	public void selecionarSeguro() {
		if (seguroSelecionado) {
			seguroviagem = new Seguroviagem();
			try {
				seguroviagem.setDataInicio(
						Formatacao.SomarDiasDatas(resultadoOrcamentoBean.getOcurso().getDatainicio(), -7));
				seguroviagem.setDataTermino(
						Formatacao.SomarDiasDatas(resultadoOrcamentoBean.getOcurso().getDatatermino(), 7));
			} catch (Exception e) {
				e.printStackTrace();
			}
			seguroviagem.setNumeroSemanas(
					Formatacao.subtrairDatas(seguroviagem.getDataInicio(), seguroviagem.getDataTermino()));
			seguroviagem.setNumeroSemanas(seguroviagem.getNumeroSemanas() + 1);
		} else {
			seguroviagem = new Seguroviagem();
			calcularTotais();
		}
		habilitarSeguro();
	}

	public void calcularTotais() {
		valorTotal = 0.0f;
		valorTotalRS = 0.0f;
		valorTotalAdicionais = 0.0f;
		valorTotalAdicionaisRS = 0.0f;
		if (seguroviagem != null && seguroviagem.getValorSeguro()!=null) {
			if (seguroviagem.getValorSeguro() != null && !seguroviagem.isSomarvalortotal()) {;;
				valorTotalAdicionaisRS = valorTotalAdicionaisRS + seguroviagem.getValorSeguro();
				if(seguroviagem.isSegurocancelamento()) {
					float rsSeguro = seguroviagem.getValoresseguro().getValorsegurocancelamento()*cambioSeguro.getValor();
					valorTotalAdicionaisRS = valorTotalAdicionaisRS + rsSeguro;
					valorTotalAdicionais = valorTotalAdicionais
							+ (rsSeguro / resultadoOrcamentoBean.getOcurso().getValorcambio());
				}
				valorTotalAdicionais = valorTotalAdicionais
						+ (seguroviagem.getValorSeguro() / resultadoOrcamentoBean.getOcurso().getValorcambio());
			} else if (seguroviagem.getValorSeguro() != null && seguroviagem.isSomarvalortotal()) {
				valorTotalRS = valorTotalRS + seguroviagem.getValorSeguro();
				if(seguroviagem.isSegurocancelamento()) {
					float rsSeguro = seguroviagem.getValoresseguro().getValorsegurocancelamento()*cambioSeguro.getValor();
					valorTotalRS = valorTotalRS + rsSeguro;
					valorTotal = valorTotal
							+ (rsSeguro / resultadoOrcamentoBean.getOcurso().getValorcambio());
				}
				valorTotal = valorTotal
						+ (seguroviagem.getValorSeguro() / resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
		for (int i = 0; i < resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().size(); i++) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i)
					.getValorPromocional() != null
					&& resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i)
							.getValorPromocional() > 0) {
				valorTotal = valorTotal + resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal()
						.get(i).getValorPromocional();
				valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal()
						.get(i).getValorPromocionalRS();
			} else {
				valorTotal = valorTotal + 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i).getValorOrigianl();
				valorTotalRS = valorTotalRS + 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i).getValorOriginalRS();
			}
		}
		for (int i = 0; i < resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size(); i++) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).isPromocao()) {
				valorTotal = valorTotal + 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).getValorPromocional();
				valorTotalRS = valorTotalRS + 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).getValorPromocionalRS();
			} else {
				valorTotal = valorTotal + 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).getValorOrigianl();
				valorTotalRS = valorTotalRS + 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).getValorOriginalRS();
			}
		}
		for (int i = 0; i < resultadoOrcamentoBean.getListaOpcionais().size(); i++) {
			if (resultadoOrcamentoBean.getListaOpcionais().get(i).isSelecionadoOpcional()
					&& resultadoOrcamentoBean.getListaOpcionais().get(i).isSomarvalortotal()) {
				valorTotal = valorTotal + resultadoOrcamentoBean.getListaOpcionais().get(i).getValorOrigianl();
				valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getListaOpcionais().get(i).getValorOriginalRS();
			} else if (resultadoOrcamentoBean.getListaOpcionais().get(i).isSelecionadoOpcional()
					&& !resultadoOrcamentoBean.getListaOpcionais().get(i).isSomarvalortotal()) {
				valorTotalAdicionais = valorTotalAdicionais + resultadoOrcamentoBean.getListaOpcionais().get(i).getValorOrigianl();
				valorTotalAdicionaisRS = valorTotalAdicionaisRS + resultadoOrcamentoBean.getListaOpcionais().get(i).getValorOriginalRS();
			}
		}
		for (int i = 0; i < resultadoOrcamentoBean.getListaTransfer().size(); i++) {
			if (resultadoOrcamentoBean.getListaTransfer().get(i).isSelecionado()
					&& resultadoOrcamentoBean.getListaTransfer().get(i).isSomarvalortotal()) {
				valorTotal = valorTotal + resultadoOrcamentoBean.getListaTransfer().get(i).getValorOrigianl();
				valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getListaTransfer().get(i).getValorOrigianl()
					* resultadoOrcamentoBean.getOcurso().getValorcambio();
			} else if (resultadoOrcamentoBean.getListaTransfer().get(i).isSelecionado()
					&& !resultadoOrcamentoBean.getListaTransfer().get(i).isSomarvalortotal()) {
				valorTotalAdicionais = valorTotalAdicionais + resultadoOrcamentoBean.getListaTransfer().get(i).getValorOrigianl();
				valorTotalAdicionaisRS = valorTotalAdicionaisRS
					+ resultadoOrcamentoBean.getListaTransfer().get(i).getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio();
			}
		} 
		for (int i = 0; i < resultadoOrcamentoBean.getListaAcomodacoes().size(); i++) {
			if (resultadoOrcamentoBean.getListaAcomodacoes().get(i).isSelecionado()) {
				if (resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocional() != null
						&& resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocional() > 0) {
					valorTotal = valorTotal + resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocional();
					valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocionalRS();
				} else {
					valorTotal = valorTotal + resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorOrigianl();
					valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorOriginalRS();
				}
			}
		} 
		for (int i = 0; i < resultadoOrcamentoBean.getListaAcOpcional().size(); i++) {
			if (resultadoOrcamentoBean.getListaAcOpcional().get(i).isSelecionado()
					&& resultadoOrcamentoBean.getListaAcOpcional().get(i).isSomarvalortotal()) {
				valorTotal = valorTotal + resultadoOrcamentoBean.getListaAcOpcional().get(i).getValorOrigianl();
				valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getListaAcOpcional().get(i).getValorOriginalRS();
			} else if (resultadoOrcamentoBean.getListaAcOpcional().get(i).isSelecionado()
					&& !resultadoOrcamentoBean.getListaAcOpcional().get(i).isSomarvalortotal()) {
				valorTotalAdicionais = valorTotalAdicionais + resultadoOrcamentoBean.getListaAcOpcional().get(i).getValorOriginalAcOpcional();
				valorTotalAdicionaisRS = valorTotalAdicionaisRS + resultadoOrcamentoBean.getListaAcOpcional().get(i).getValorRSacOpcional();
			}
		} 
		for (int i = 0; i < resultadoOrcamentoBean.getOcurso().getOcursodescontoList().size(); i++) {
			if (resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).isSelecionado()) {
				resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).setValormoedaestrangeira(
					resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedanacional()
						/ resultadoOrcamentoBean.getOcurso().getValorcambio());
				valorTotal = valorTotal - resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedaestrangeira();
				valorTotalRS = valorTotalRS - resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedanacional();
			}
		}
		if (resultadoOrcamentoBean.getListaOutrosProdutos() != null) {
			for (int i = 0; i < resultadoOrcamentoBean.getListaOutrosProdutos().size(); i++) {
				if (!resultadoOrcamentoBean.getListaOutrosProdutos().get(i).isSomarvalortotal()) {
					valorTotalAdicionais = valorTotalAdicionais + resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorOrigianl();
					valorTotalAdicionaisRS = valorTotalAdicionaisRS + resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorOriginalRS();
				} else {
					valorTotal = valorTotal + resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorOrigianl();
					valorTotalRS = valorTotalRS + resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorOriginalRS();
				}
			}
		}
	} 

	public void calcularDataTermino() {
		seguroviagem.setValoresseguro(valorSeguro);
		if ((seguroviagem.getDataInicio() != null) && (seguroviagem.getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			cambioSeguro = cambioFacade.consultarCambioMoeda(
					Formatacao.ConvercaoDataSql(aplicacaoMB.getListaCambio().get(0).getData()),
					valorSeguro.getMoedas().getIdmoedas());
			if (cambioSeguro != null) {
				if (seguroviagem.getValoresseguro().getCobranca().equalsIgnoreCase("semana")) {
					seguroviagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroviagem.getDataInicio(),
							seguroviagem.getNumeroSemanas()));
				} else if (seguroviagem.getValoresseguro().getCobranca().equalsIgnoreCase("diaria")) {
					seguroviagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroviagem.getDataInicio(),
							seguroviagem.getNumeroSemanas()));
				}
				float valornacional = seguroviagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				seguroviagem.setValorSeguro(valornacional * seguroviagem.getNumeroSemanas());
				calcularTotais();
				valorSeguroMoedaEstrangeira();
			}
		}
	}

	public String voltar() {
		if(resultadoOrcamentoBean.getOcurso().getIdocurso()!=null){
			return "consultaorcamentocurso";
		}else return "resultadoFiltroOrcamento";
	}

	public void verificarAcomodacaoSelecionada(ProdutosOrcamentoBean produtosOrcamentoBean) {
		boolean buscarTaxa = true;
		if (produtosOrcamentoBean.isSelecionado()) {
			List<ProdutosOrcamentoBean> listaAcomodacao = resultadoOrcamentoBean.getListaAcomodacoes();
			for (int i = 0; i < listaAcomodacao.size(); i++) {
				if (listaAcomodacao.get(i).isSelecionado()) {
					if (!listaAcomodacao.get(i).equals(produtosOrcamentoBean)) {
						produtosOrcamentoBean.setSelecionado(false);
						buscarTaxa = false;
					}
				}
			}
			if (produtosOrcamentoBean.getNumeroSemanas() == 0) {
				if (resultadoOrcamentoBean.getOcurso().getNumerosemanastotal() == 0) {
					resultadoOrcamentoBean.getOcurso()
							.setNumerosemanastotal(resultadoOrcamentoBean.getOcurso().getNumerosemanas()
									- resultadoOrcamentoBean.getOcurso().getNumerodiasferiado());
				}
				produtosOrcamentoBean.setNumeroSemanas(resultadoOrcamentoBean.getOcurso().getNumerosemanastotal());
			}
			gerarPromocaoAcomodacao(produtosOrcamentoBean);
			if (resultadoOrcamentoBean.getOcurso().getCliente().getDataNascimento() != null) {
				if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().isSuplementemenoridade()) {
					int idadeVinculados = 18;
					if(produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdade()>0){
						idadeVinculados = produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdade();
					} 
					int idade = Formatacao
							.calcularIdade(resultadoOrcamentoBean.getOcurso().getCliente().getDataNascimento());
					if (idade < idadeVinculados) {
						suplementoMenorIdadeAcomodacao(produtosOrcamentoBean);
					}
				}
			}
			if (buscarTaxa) {
				produtosOrcamentoBean.setNumeroSemanas(resultadoOrcamentoBean.getOcurso().getNumerosemanastotal());
				String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
						+ produtosOrcamentoBean.getIdproduto();
				GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
				List<Grupoobrigatorio> listaGrupoObrigatorio;
				listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sql);
				if (listaGrupoObrigatorio != null && listaGrupoObrigatorio.size() > 0) {
					for (int i = 0; i < listaGrupoObrigatorio.size(); i++) {
						boolean calcular = true;
						if (listaGrupoObrigatorio.get(i).isMenorobrigatorio()) {
							int idadeCliente = Formatacao.calcularIdade(resultadoOrcamentoBean.getOcurso().getCliente().getDataNascimento());
							if (idadeCliente < resultadoOrcamentoBean.getFornecedorcidadeidioma().getMaioridade()) {
								calcular = true;
							}else{
								calcular = false;
							}
						}
						if (calcular) {
							ProdutosOrcamentoBean po = consultarValores("DI",
									listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
									resultadoOrcamentoBean.getOcurso(), resultadoOrcamentoBean.getDataConsulta(),
									"Obrigatorio");
							if (po != null) {
								resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
								produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
										resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size()
												- 1);
							} else {
								po = consultarValores("DM", listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
										resultadoOrcamentoBean.getOcurso(), new Date(), "Obrigatorio");
								if (po != null) {
									resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
									produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
											resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size()
													- 1);
								} else {
									po = consultarValores("DS", listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
											resultadoOrcamentoBean.getOcurso(), resultadoOrcamentoBean.getDataConsulta(), "Obrigatorio");
									if (po != null) {
										resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
										produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(resultadoOrcamentoBean
												.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
									} else {
										produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(-1);
									}
								}
							}	
						} else {
							produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(-1);
						}
					}
				}
			}
			ProdutosOrcamentoBean po = consultarValoresSuplementoAcomodacqo(produtosOrcamentoBean,
					produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos(),
					resultadoOrcamentoBean.getOcurso(), resultadoOrcamentoBean.getDataConsulta());
			if (po != null) {
				po.getValorcoprodutos().getCoprodutos()
						.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento()+ " - Acomodação");
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
				produtosOrcamentoBean.setLinhaSuplementoAcomodacao(
						resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
			}

			calcularValorAcomodacao(produtosOrcamentoBean); 
			gerarPromocaoCurso(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal());
			gerarPromocaoTaxas(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios(),resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal());
			gerarPromocaoBrindes(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios(), 
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0));
		} else {
			calcularValorAcomodacao(produtosOrcamentoBean);
			if (produtosOrcamentoBean.getLinhaSuplementoAcomodacao() >= 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaSuplementoAcomodacao());
			}
			if (produtosOrcamentoBean.getLinhaObrigatorioAcomodacao() >= 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaObrigatorioAcomodacao());
			}
			
		}
	}

	public void suplementoMenorIdadeAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ resultadoOrcamentoBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and c.produtosorcamento.idprodutosOrcamento="
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		Coprodutos coprodutos = new Coprodutos();
		CoProdutosFacade produtosFacade = new CoProdutosFacade();
		coprodutos = produtosFacade.consultar(sqlSuplementoIdade);
		if (coprodutos != null) {
			ProdutosOrcamentoBean po = consultarValores("DI", coprodutos.getIdcoprodutos(),
					resultadoOrcamentoBean.getOcurso(), resultadoOrcamentoBean.getDataConsulta(), "Obrigatorio");
			if (po != null) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
				produtosOrcamentoBean.setLinhaSuplementoAcomodacao(
						resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
			} else {
				po = consultarValores("DM", coprodutos.getIdcoprodutos(), resultadoOrcamentoBean.getOcurso(),
						new Date(), "Obrigatorio");
				if (po != null) {
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
					produtosOrcamentoBean.setLinhaSuplementoAcomodacao(
							resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
				} else {
					po = consultarValores("DS", coprodutos.getIdcoprodutos(), resultadoOrcamentoBean.getOcurso(),
							resultadoOrcamentoBean.getDataConsulta(), "Obrigatorio");
					if (po != null) {
						resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
						produtosOrcamentoBean.setLinhaSuplementoAcomodacao(
								resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
					} else {
						produtosOrcamentoBean.setLinhaSuplementoAcomodacao(-1);
					}
				}
			}
		}
	}

	public void calcularValorAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isSelecionado()) {
			if (produtosOrcamentoBean.getNumeroSemanas() != resultadoOrcamentoBean.getOcurso().getNumerosemanas()) {
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = null;
				Date dataconsulta = resultadoOrcamentoBean.getDataConsulta();
				if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.isAcomodacaoindependente()) {
					dataconsulta = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
							produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
									.getFornecedorcidade().getFornecedor());
				}
				String sql = "Select v from  Valorcoprodutos v where v.numerosemanainicial<="
						+ produtosOrcamentoBean.getNumeroSemanas()
						+ " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas()
						+ " and v.coprodutos.idcoprodutos="
						+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
				if(produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DI")) {
					sql = sql + " AND v.datainicial<='" 
							+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='" 
							+ Formatacao.ConvercaoDataSql(dataconsulta) + "'";
				}else if(produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DS")) {
					sql = sql + " AND v.datainicial<='" 
							+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='" 
							+ Formatacao.ConvercaoDataSql(dataconsulta) + "'";
				}if(produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DM")) {
					sql = sql + " AND v.datainicial<='" 
							+ Formatacao.ConvercaoDataSql(new Date()) + "' and v.datafinal>='" 
							+ Formatacao.ConvercaoDataSql(new Date()) + "'";
				}
				List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
				if (listaValorcoprodutoses != null) {
					for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
						if (valorcoprodutos == null) {
							valorcoprodutos = new Valorcoprodutos();
							valorcoprodutos = listaValorcoprodutoses.get(n);
						} else {
							valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
						}
					}
				}
				if (valorcoprodutos != null) {
					produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
				}
			}
			int multiplicador = 1;
			if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
			} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
			}
			produtosOrcamentoBean
					.setValorOrigianl(multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
			produtosOrcamentoBean.setValorOriginalRS(
					produtosOrcamentoBean.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
			gerarPromocaoAcomodacao(produtosOrcamentoBean);
			calcularTotais();
		} else {
			produtosOrcamentoBean.setValorOrigianl(0.0f);
			produtosOrcamentoBean.setValorOriginalRS(0.0f);
			produtosOrcamentoBean.setNumeroSemanas(0);
			calcularTotais();
		}
	}

	public void mudarNumeroSemanaAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isSelecionado()) {
			calcularValorAcomodacao(produtosOrcamentoBean);
			if (produtosOrcamentoBean.getLinhaSuplementoAcomodacao() >= 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaSuplementoAcomodacao());
			}
			if (produtosOrcamentoBean.getLinhaObrigatorioAcomodacao() >= 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaObrigatorioAcomodacao());
			}
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			Valorcoprodutos valorcoprodutos = null;
			Date dataconsulta = resultadoOrcamentoBean.getDataConsulta();
			if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
					.isAcomodacaoindependente()) {
				dataconsulta = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
						produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
								.getFornecedorcidade().getFornecedor());
			}
			String sql = "Select v from  Valorcoprodutos v where v.numerosemanainicial<="
					+ produtosOrcamentoBean.getNumeroSemanas()
					+ " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas()
					+ " and v.coprodutos.idcoprodutos="
					+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
			if(produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DI")) {
				sql = sql + " AND v.datainicial<='" 
						+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='" 
						+ Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}else if(produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DS")) {
				sql = sql + " AND v.datainicial<='" 
						+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='" 
						+ Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}if(produtosOrcamentoBean.getValorcoprodutos().getTipodata().equalsIgnoreCase("DM")) {
				sql = sql + " AND v.datainicial<='" 
						+ Formatacao.ConvercaoDataSql(new Date()) + "' and v.datafinal>='" 
						+ Formatacao.ConvercaoDataSql(new Date()) + "'";
			}
			List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
			if (listaValorcoprodutoses != null) {
				for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
					if (valorcoprodutos == null) {
						valorcoprodutos = new Valorcoprodutos();
						valorcoprodutos = listaValorcoprodutoses.get(n);
					} else {
						valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
					}
				}
			}
			if (valorcoprodutos != null) {
				produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
				int multiplicador = 1;
				if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
					multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
				} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
					multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
				}
				produtosOrcamentoBean
						.setValorOrigianl(multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
				produtosOrcamentoBean.setValorOriginalRS(
						produtosOrcamentoBean.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
				gerarPromocaoAcomodacao(produtosOrcamentoBean);
				String sqlObrigatorio = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
						+ produtosOrcamentoBean.getIdproduto();
				GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
				List<Grupoobrigatorio> listaGrupoObrigatorio;
				listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sqlObrigatorio);
				if (listaGrupoObrigatorio != null && listaGrupoObrigatorio.size() > 0) {
					for (int i = 0; i < listaGrupoObrigatorio.size(); i++) {
						boolean calcular = true;
						if (listaGrupoObrigatorio.get(i).isMenorobrigatorio()) {
							int idadeCliente = Formatacao.calcularIdade(resultadoOrcamentoBean.getOcurso().getCliente().getDataNascimento());
							if (idadeCliente < resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getMaioridade()) {
								calcular = true;
							}else{
								calcular = false;
							}
						}
						if (calcular) {
							ProdutosOrcamentoBean po = consultarValores("DI",
									listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
									resultadoOrcamentoBean.getOcurso(), dataconsulta,
									"Obrigatorio");
							if (po != null) {
								resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
								produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
										resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
							} else {
								po = consultarValores("DM", listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
										resultadoOrcamentoBean.getOcurso(), new Date(),
										"Obrigatorio");
								if (po != null) {
									resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
									produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
											resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size()
													- 1);
								} else {
									po = consultarValores("DS", listaGrupoObrigatorio.get(i).getProduto().getIdcoprodutos(),
											resultadoOrcamentoBean.getOcurso(), dataconsulta, "Obrigatorio");
									if (po != null) {
										resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
										produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(
												resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size()
														- 1);
									} else {
										produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(-1);
									}
								}
							}
						} else {
							produtosOrcamentoBean.setLinhaObrigatorioAcomodacao(-1);
						}
					} 
				}
				ProdutosOrcamentoBean po = consultarValoresSuplementoAcomodacqo(produtosOrcamentoBean,
						produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos(),
						resultadoOrcamentoBean.getOcurso(), dataconsulta);
				if (po != null) {
					po.getValorcoprodutos().getCoprodutos()
							.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento());
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(po);
					produtosOrcamentoBean.setLinhaSuplementoAcomodacao(
							resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size() - 1);
				}
				calcularTotais();
				gerarPromocaoCurso(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal());
				gerarPromocaoTaxas(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios(),resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal());
				gerarPromocaoBrindes(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios(), 
						resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0));
				if(produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
					gerarPromocaoTaxasAcomodacaoIndependente(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios(),
							resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal(),
							produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma());
				}
			}else{ 
				produtosOrcamentoBean.setValorOrigianl(0.0f);
				produtosOrcamentoBean.setValorOriginalRS(0.0f);
				produtosOrcamentoBean.setNumeroSemanas(0);
				gerarPromocaoCurso(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal());
				calcularTotais();
			} 
		} else {
			produtosOrcamentoBean.setValorOrigianl(0.0f);
			produtosOrcamentoBean.setValorOriginalRS(0.0f);
			produtosOrcamentoBean.setNumeroSemanas(0);
			gerarPromocaoCurso(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal());
			if (produtosOrcamentoBean.getLinhaSuplementoAcomodacao() >= 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaSuplementoAcomodacao());
			}
			if (produtosOrcamentoBean.getLinhaObrigatorioAcomodacao() >= 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
						.remove(produtosOrcamentoBean.getLinhaObrigatorioAcomodacao());
			}
			for (int i = 0; i < resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size(); i++) {
				if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i)
						.getValorcoprodutos().getCoprodutos().getDescricao().equalsIgnoreCase("Taxa de Acomodação")) {
						resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
							.remove(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i));
						i = resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size();
				}
			}
			calcularTotais();
		}
	}

	public void calcularValorAcOpcional(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isSelecionado()) {
			Double numeroSemanas = produtosOrcamentoBean.getNumeroSemanasAcOpcional();
			if (produtosOrcamentoBean.getNumeroSemanasAcOpcional() != resultadoOrcamentoBean.getOcurso()
					.getNumerosemanas()) {
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = null;
				String sql = "Select v from  Valorcoprodutos v where v.datainicial<='"
						+ Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getDataConsulta()) + "' and v.datafinal>='"
						+ Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getDataConsulta())
						+ "' and v.numerosemanainicial<=" + numeroSemanas + " and v.numerosemanafinal>=" + numeroSemanas
						+ " and v.coprodutos.idcoprodutos="
						+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
				List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
				if (listaValorcoprodutoses != null) {
					for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
						if (valorcoprodutos == null) {
							valorcoprodutos = new Valorcoprodutos();
							valorcoprodutos = listaValorcoprodutoses.get(n);
						} else {
							valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
						}
					}
				}
				if (valorcoprodutos != null) {
					produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
				}
			}
			produtosOrcamentoBean.setValorOriginalAcOpcional(
					numeroSemanas.floatValue() * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
			produtosOrcamentoBean.setValorRSacOpcional(
					numeroSemanas.floatValue() * (produtosOrcamentoBean.getValorcoprodutos().getValororiginal()
							* resultadoOrcamentoBean.getOcurso().getValorcambio()));
			calcularTotais();
		} else {
			produtosOrcamentoBean.setValorOriginalAcOpcional(0.0f);
			produtosOrcamentoBean.setValorRSacOpcional(0.0f);
			produtosOrcamentoBean.setNumeroSemanasAcOpcional(0);
			calcularTotais();
		}
	}

	public String finalizarOrcamentoCurso() {
		if(verificarPaisProduto()){
			verificarEdicao();
			gerarListaProdutosSelecionados();
			return "finalizarOrcamentoCurso";
		}
		return "";
	}
	
	public boolean verificarPaisProduto(){
		if(resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma()
				.getFornecedorcidade().getCidade().getPais().getIdpais()==4){
			if(resultadoOrcamentoBean.getOcurso().getNumerosemanas()>=14){
				if(resultadoOrcamentoBean.getListaOutrosProdutos()!=null 
						&& resultadoOrcamentoBean.getListaOutrosProdutos().size()>0){
					boolean ok = false;
					for (int i = 0; i < resultadoOrcamentoBean.getListaOutrosProdutos().size(); i++) {
						if (resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getProdutosorcamento().getDescricao().equalsIgnoreCase("Seguro OSHC")) {
							ok=true;
						}
					}
					if(ok){
						return true;
					}else{
						Mensagem.lancarMensagemErro("Atenção!", "Incluir Seguro OSHC na aba 'Outros Produtos'.");
						return false;
					}
				}else{
					Mensagem.lancarMensagemErro("Atenção!", "Incluir Seguro OSHC na aba 'Outros Produtos'.");
					return false;
				}
			}
		}
		return true;
	}

	public void gerarListaProdutosSelecionados() {
		if(resultadoOrcamentoBean.getOcurso()!=null && resultadoOrcamentoBean.getOcurso().getIdocurso()!=null) {
			OCursoProdutoFacade oCursoProdutoFacade = new OCursoProdutoFacade();
			List<Ocrusoprodutos> listaProdutosExcluir = oCursoProdutoFacade.listar(
					resultadoOrcamentoBean.getOcurso().getIdocurso());
			if(listaProdutosExcluir!=null) {
				for (int i = 0; i < listaProdutosExcluir.size(); i++) {
					oCursoProdutoFacade.excluir(listaProdutosExcluir.get(i).getIdocrusoprodutos());
				}
			}
		}
		List<Ocrusoprodutos> listaProdutos = new ArrayList<Ocrusoprodutos>();
		List<ProdutosOrcamentoBean> listaObrigaroerios = resultadoOrcamentoBean.getProdutoFornecedorBean()
				.getListaObrigaroerios();
		List<ProdutosOrcamentoBean> listaCurso = resultadoOrcamentoBean.getProdutoFornecedorBean()
				.getListaCursoPrincipal();
		for (int i = 0; i < listaCurso.size(); i++) {
			Ocrusoprodutos produto = new Ocrusoprodutos();
			produto.setNumerosemanas(resultadoOrcamentoBean.getOcurso().getNumerosemanas().doubleValue());
			produto.setValorcoprodutos(listaCurso.get(i).getValorcoprodutos());
			produto.setValororiginal(listaCurso.get(i).getValorOrigianl());
			produto.setValorpromocional(listaCurso.get(i).getValorPromocional());
			produto.setNome(listaCurso.get(i).getValorcoprodutos().getCoprodutos().getNome());
			produto.setSomavalortotal(listaCurso.get(i).isSomarvalortotal());
			produto.setDescricao(
					listaCurso.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso().getTurno() + " "
							+ listaCurso.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso()
									.getCargahoraria()
							+ " " + listaCurso.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso()
									.getTipocargahoraria());
			produto.setTipo(1);
			produto.setNomegrupo("Curso");
			if(resultadoOrcamentoBean.getCodigo()!=null){
				produto.setCodigo(resultadoOrcamentoBean.getCodigo());
			}
			if (listaCurso.get(i).isPromocao()) {
				produto.setPossuipromocao(true);
			} else
				produto.setPossuipromocao(false);
			listaProdutos.add(produto);
			resultadoOrcamentoBean.getOcurso().setCargahoraria(
					produto.getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria() + " "
							+ produto.getValorcoprodutos().getCoprodutos().getComplementocurso().getTipocargahoraria());
			resultadoOrcamentoBean.getOcurso()
					.setTurno(produto.getValorcoprodutos().getCoprodutos().getComplementocurso().getTurno());
			if (listaCurso.get(i).getDescricaobrinde() != null && listaCurso.get(i).getDescricaobrinde().length() > 2) {
				ProdutosExtrasBean promocao = new ProdutosExtrasBean();
				promocao.setDescricao(listaCurso.get(i).getDescricaobrinde());
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtosorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getPromocaoescola());
				promocao.setProdutosorcamento(produtosorcamento);
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
				promocao.setValorcoprodutos(valorcoprodutos);
				promocao.setValorOrigianl(0.0f);
				promocao.setValorOriginalRS(0.0f);
				resultadoOrcamentoBean.getListaOutrosProdutos().add(promocao);
			}
		}

		for (int i = 0; i < listaObrigaroerios.size(); i++) {
			Ocrusoprodutos produto = new Ocrusoprodutos();
			produto.setNumerosemanas(listaObrigaroerios.get(i).getNumeroSemanas());
			produto.setValorcoprodutos(listaObrigaroerios.get(i).getValorcoprodutos());
			produto.setValororiginal(listaObrigaroerios.get(i).getValorOrigianl());
			produto.setValorpromocional(listaObrigaroerios.get(i).getValorPromocional());
			produto.setNome(listaObrigaroerios.get(i).getValorcoprodutos().getCoprodutos().getNome());
			produto.setDescricao(listaObrigaroerios.get(i).getValorcoprodutos().getCoprodutos().getDescricao());
			produto.setTipo(1);
			produto.setNomegrupo("Curso");
			produto.setSomavalortotal(listaObrigaroerios.get(i).isSomarvalortotal());
			if (listaObrigaroerios.get(i).isPromocao()) {
				produto.setPossuipromocao(true);
			} else
				produto.setPossuipromocao(false);
			listaProdutos.add(produto);
		}

		List<ProdutosOrcamentoBean> listaOpcionais = resultadoOrcamentoBean.getListaOpcionais();
		for (int i = 0; i < listaOpcionais.size(); i++) {
			if (listaOpcionais.get(i).isSelecionadoOpcional()) {
				Ocrusoprodutos produto = new Ocrusoprodutos();
				produto.setNumerosemanas(listaOpcionais.get(i).getNumeroSemanas());
				produto.setValorcoprodutos(listaOpcionais.get(i).getValorcoprodutos());
				produto.setValororiginal(listaOpcionais.get(i).getValorOrigianl());
				produto.setValorpromocional(listaOpcionais.get(i).getValorPromocional());
				produto.setNome(listaOpcionais.get(i).getValorcoprodutos().getCoprodutos().getNome());
				produto.setDescricao(listaOpcionais.get(i).getValorcoprodutos().getCoprodutos().getDescricao());
				produto.setTipo(2);
				produto.setSomavalortotal(listaOpcionais.get(i).isSomarvalortotal());
				if (resultadoOrcamentoBean.getListaOpcionais().get(i).isSomarvalortotal()) {
					produto.setNomegrupo("Adicionais");
				} else {
					produto.setNomegrupo("CustosExtras");
				}
				if (listaOpcionais.get(i).isPromocao()) {
					produto.setPossuipromocao(true);
				} else
					produto.setPossuipromocao(false);
				listaProdutos.add(produto);
			}
		}
		List<ProdutosOrcamentoBean> listaAcomodacoes = resultadoOrcamentoBean.getListaAcomodacoes();
		for (int i = 0; i < listaAcomodacoes.size(); i++) {
			if (listaAcomodacoes.get(i).isSelecionado()) {
				Ocrusoprodutos produto = new Ocrusoprodutos();
				produto.setNumerosemanas(listaAcomodacoes.get(i).getNumeroSemanas());
				produto.setValorcoprodutos(listaAcomodacoes.get(i).getValorcoprodutos());
				produto.setValororiginal(listaAcomodacoes.get(i).getValorOrigianl());
				produto.setValorpromocional(listaAcomodacoes.get(i).getValorPromocional());
				produto.setNome(listaAcomodacoes.get(i).getValorcoprodutos().getCoprodutos().getNome());
				produto.setSomavalortotal(listaAcomodacoes.get(i).isSomarvalortotal());
				produto.setDescricao(listaAcomodacoes.get(i).getValorcoprodutos().getCoprodutos()
						.getComplementoacomodacao().getTipoacomodacao()
						+ ", "
						+ listaAcomodacoes.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTipoquarto()
						+ ", "
						+ listaAcomodacoes.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTiporefeicao()
						+ ", " + listaAcomodacoes.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao()
								.getTipobanheiro());
				produto.setTipo(3);
				produto.setNomegrupo("Acomodação");
				if (listaAcomodacoes.get(i).isPromocao()) {
					produto.setPossuipromocao(true);
				} else
					produto.setPossuipromocao(false);
				listaProdutos.add(produto);
				if (listaAcomodacoes.get(i).getDescricaobrinde() != null
						&& listaAcomodacoes.get(i).getDescricaobrinde().length() > 2) {
					ProdutosExtrasBean promocao = new ProdutosExtrasBean();
					promocao.setDescricao(listaAcomodacoes.get(i).getDescricaobrinde());
					ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
					Produtosorcamento produtosorcamento = produtoOrcamentoFacade
							.consultar(aplicacaoMB.getParametrosprodutos().getPromocaoescolaacomodacao());
					promocao.setProdutosorcamento(produtosorcamento);
					ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
					Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
							.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
					promocao.setValorcoprodutos(valorcoprodutos);
					promocao.setValorOrigianl(0.0f);
					promocao.setValorOriginalRS(0.0f);
					resultadoOrcamentoBean.getListaOutrosProdutos().add(promocao);
				}
			}
		}
		List<ProdutosOrcamentoBean> listaAcOpcional = resultadoOrcamentoBean.getListaAcOpcional();
		for (int i = 0; i < listaAcOpcional.size(); i++) {
			if (listaAcOpcional.get(i).isSelecionado()) {
				Ocrusoprodutos produto = new Ocrusoprodutos();
				produto.setNumerosemanas(listaAcOpcional.get(i).getNumeroSemanasAcOpcional());
				produto.setValorcoprodutos(listaAcOpcional.get(i).getValorcoprodutos());
				produto.setValororiginal(listaAcOpcional.get(i).getValorOriginalAcOpcional());
				produto.setValorpromocional(listaAcOpcional.get(i).getValorPromocional());
				produto.setNome(listaAcOpcional.get(i).getValorcoprodutos().getCoprodutos().getNome());
				if(listaAcOpcional.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
					produto.setDescricao(listaAcOpcional.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome()
							+" "+listaAcOpcional.get(i).getValorcoprodutos().getCoprodutos().getDescricao() + " - "
							+ listaAcOpcional.get(i).getNumeroSemanas());
				}else{
					produto.setDescricao(listaAcOpcional.get(i).getValorcoprodutos().getCoprodutos().getDescricao() + " - "
							+ listaAcOpcional.get(i).getNumeroSemanas());
				}
				produto.setTipo(4);
				produto.setSomavalortotal(listaAcOpcional.get(i).isSomarvalortotal());
				if (resultadoOrcamentoBean.getListaAcOpcional().get(i).isSomarvalortotal()) {
					produto.setNomegrupo("Adicionais");
				} else {
					produto.setNomegrupo("CustosExtras");
				}
				if (listaAcOpcional.get(i).isPromocao()) {
					produto.setPossuipromocao(true);
				} else
					produto.setPossuipromocao(false);
				listaProdutos.add(produto);
			}
		}
		List<ProdutosOrcamentoBean> listaTransfer = resultadoOrcamentoBean.getListaTransfer();
		for (int i = 0; i < listaTransfer.size(); i++) {
			if (listaTransfer.get(i).isSelecionado()) {
				Ocrusoprodutos produto = new Ocrusoprodutos();
				produto.setNumerosemanas(listaTransfer.get(i).getNumeroSemanas());
				produto.setValorcoprodutos(listaTransfer.get(i).getValorcoprodutos());
				produto.setValororiginal(listaTransfer.get(i).getValorOrigianl());
				produto.setValorpromocional(listaTransfer.get(i).getValorPromocional());
				produto.setNome(listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getNome());
				if(listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
					produto.setDescricao(listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome()
							+" "+listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getDescricao()
									+ " - " + listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getDescricao());
				}else{
					produto.setDescricao(
							listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getDescricao()
									+ " - " + listaTransfer.get(i).getValorcoprodutos().getCoprodutos().getDescricao());
				}
				produto.setTipo(5);
				produto.setSomavalortotal(listaTransfer.get(i).isSomarvalortotal());
				if (resultadoOrcamentoBean.getListaTransfer().get(i).isSomarvalortotal()) {
					produto.setNomegrupo("Adicionais");
				} else {
					produto.setNomegrupo("Transfer");
				}
				if (listaTransfer.get(i).isPromocao()) {
					produto.setPossuipromocao(true);
				} else
					produto.setPossuipromocao(false);
				listaProdutos.add(produto);
			}
		}
		resultadoOrcamentoBean.getOcurso().setTotalmoedaestrangeira(valorTotal);
		resultadoOrcamentoBean.getOcurso().setTotalmoedanacional(valorTotalRS);
		resultadoOrcamentoBean.setSeguroSelecionado(seguroSelecionado);
		if (seguroSelecionado) {
			resultadoOrcamentoBean.setFornecedorcidade(fornecedorcidade);
			resultadoOrcamentoBean.setValoresSeguro(valorSeguro);
			resultadoOrcamentoBean.setSeguroviagem(seguroviagem);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaProdutos", listaProdutos);
		Ocurso ocurso = new Ocurso();
		ocurso = resultadoOrcamentoBean.getOcurso();
		ocurso.setUsuario(usuarioLogadoMB.getUsuario());
		ocurso.setIdioma(resultadoOrcamentoBean.getOcurso().getIdioma());
		session.setAttribute("ocurso", ocurso);
		session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
	}

	public String corDescricaoSeguro() {
		if (seguroSelecionado) {
			style = "font-weight:bold;font-size: 13px;margin-left:0%;color: #1F8871";
		} else {
			style = "font-weight:bold;font-size: 13px;margin-left:0%;color: #fff";
		}

		return style;
	}

	public void valorSeguroMoedaEstrangeira() {
		if (seguroviagem.getValorSeguro() != null) {
			seguroviagem.setValorMoedaEstrangeira(seguroviagem.getValorSeguro() / cambioSeguro.getValor());
		}
	}

	public String corDescricaoDadosSeguro() {
		if (seguroSelecionado) {
			styleDados = "font-weight:bold;font-size: 15px;margin-left:0%;color: #1F8871";
		} else {
			style = "font-weight:bold;font-size: 13px;margin-left:0%;color: #fff";
			styleDados = "font-weight:bold;font-size: 15px;margin-left:0%;color: #fff";
		}

		return style;
	}

	public void valorDescontoEstrageiro() {
		for (int i = 0; i < resultadoOrcamentoBean.getOcurso().getOcursodescontoList().size(); i++) {
			if (resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedanacional() > 0) {
				resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).setValormoedaestrangeira(
						resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedanacional()
								/ resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
	}

	public void valorDescontoReal() {
		for (int i = 0; i < resultadoOrcamentoBean.getOcurso().getOcursodescontoList().size(); i++) {
			if (resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedaestrangeira() > 0) {
				resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).setValormoedanacional(
						resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedaestrangeira()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
	}

	public void carregarCampoPromocao() {
		if (resultadoOrcamentoBean.getCopromocao() != null) {
			if (resultadoOrcamentoBean.getCopromocao().getDatavalidade().after(new Date())) {
				campoPromocaoImg = "";
				campoPromocao = "color:#1F8871;font-size:24px;font-family: 'Bree Serif', serif;";
			} else {
				campoPromocaoImg = "0.1%";
				campoPromocao = "font-size:1px;color:#fff;";
			}
		} else {
			campoPromocaoImg = "0.1%";
			campoPromocao = "font-size:1px;color:#fff;";
		}
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Ocurso ocurso, Date dataConsulta,
			String tipo) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.numerosemanainicial<="
				+ resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " and v.numerosemanafinal>="
				+ resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " and v.tipodata='" + tipoData
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;

		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
				}

			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = ocurso.getNumerosemanas();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = ocurso.getNumerosemanas() * 7;
			}
			po.setValorOrigianl(valorcoprodutos.getValororiginal() * multiplicador);
			po.setValorOriginalRS(po.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
			if (tipo.equalsIgnoreCase("Acomodacao")) {
				po.setValorOrigianl(0.0f);
				po.setValorOriginalRS(0.0f);
			}
			return po;
		}
		return null;
	}

	public Valorcoprodutos compararValores(Valorcoprodutos valorNovo, Valorcoprodutos valorAtual) {
		if (valorNovo.getPromocional()) {
			return valorNovo;
		} else
			return valorAtual;
	}

	public float calcularValorRealTransfer(ProdutosOrcamentoBean produtosOrcamentoBean) {
		Float valorReal=0.0f;
		if(produtosOrcamentoBean!=null && produtosOrcamentoBean.getValorcoprodutos()!=null){
			valorReal = produtosOrcamentoBean.getValorcoprodutos().getValororiginal()
				* resultadoOrcamentoBean.getOcurso().getValorcambio();
		}
		return valorReal;
	} 

	public void listarPlanosSeguro() {
		if (fornecedorcidade != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorcidade != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorcidade.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}

	public ProdutosOrcamentoBean consultarValoresSuplementoAcomodacqo(ProdutosOrcamentoBean produtosOrcamentoBean,
			int idCoProdutos, Ocurso ocurso, Date dataConsulta) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		/*String sql = "Select v from  Valorcoprodutos v where v.datainicial>='"
				+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor().getAnotarifario()
				+ "-01-01' and v.numerosemanainicial<=" + produtosOrcamentoBean.getNumeroSemanas()
				+ " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas()
				+ " and v.coprodutos.idcoprodutos=" + idCoProdutos + " and v.produtosuplemento='Acomodação'";*/
//		Date dataInicial = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
//				resultadoOrcamentoBean.getFornecedorcidadeidioma());
		
		String sql = "Select v from  Valorcoprodutos v where v.datainicial<='" + Formatacao.ConvercaoDataSql(dataConsulta) + "' "
				+ " and v.datafinal>='"+ Formatacao.ConvercaoDataSql(dataConsulta) + "'  and v.numerosemanainicial<="
					+  produtosOrcamentoBean.getNumeroSemanas() + " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas() + 
					" and v.coprodutos.idcoprodutos=" + idCoProdutos + " and v.produtosuplemento='Acomodação' Order By v.idvalorcoprodutos DESC";

		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses == null) {
			sql = "Select v from  Valorcoprodutos v where v.datainicial>='" + Formatacao.ConvercaoDataSql(dataConsulta) + "' "
					+ " and v.datainicial<='" + Formatacao.ConvercaoDataSql(ocurso.getDatatermino()) + "'"
					+ " and v.datafinal>='"+ Formatacao.ConvercaoDataSql(dataConsulta) + "'  and v.numerosemanainicial<="
						+  produtosOrcamentoBean.getNumeroSemanas() + " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas() + 
						" and v.coprodutos.idcoprodutos=" + idCoProdutos + " and v.produtosuplemento='Acomodação' Order By v.idvalorcoprodutos DESC";
			listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		}
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
				}

			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
			}
			float valorSuplemento = calcularValorFracionadoSuplemento(produtosOrcamentoBean, multiplicador,
					resultadoOrcamentoBean.getFornecedorcidadeidioma(), po);
			if(valorSuplemento==0){
				po=null;
			}else{
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
			return po;
		}
		return null;
	}

	public float calcularValorFracionadoSuplemento(ProdutosOrcamentoBean produtosOrcamentoBean, int multiplicador,
			Fornecedorcidadeidioma fornecedorcidadeidioma, ProdutosOrcamentoBean po) {
		float valorSuplemento = 0.0f;
		Date dataInical = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
				fornecedorcidadeidioma);
		int nSemana = (int) produtosOrcamentoBean.getNumeroSemanas();
		Date dataTermino = calcularDataTerminoCurso(dataInical, nSemana);
		int numeroDias = 0;  
		boolean calcular = true;
//		if (po.getValorcoprodutos().getDatainicial().after(dataInical) && po.getValorcoprodutos().getDatainicial().after(dataTermino)  ||
//				(po.getValorcoprodutos().getDatafinal().before(dataInical) && po.getValorcoprodutos().getDatafinal().before(dataTermino))){
//			calcular = false;
//		}
//		if (calcular){
		if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
				|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;

		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;

		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino))) {

			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(),
					po.getValorcoprodutos().getDatafinal());
		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);

		} else if ((po.getValorcoprodutos().getDatainicial().before(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			if (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical))) {
				numeroDias = 1;
			}else {
				numeroDias = Formatacao.subtrairDatas(dataInical, po.getValorcoprodutos().getDatafinal());
			}
		} else if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
				|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);

		}else {
			valorSuplemento = -1;
			numeroDias=0;
		}
		if ((valorSuplemento == 0) && (numeroDias > 0)) {
			if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				int resto = (numeroDias % 7);
				numeroDias = numeroDias / 7;
				if (resto > 0) {
					numeroDias = numeroDias + 1;
				}
			}
			valorSuplemento = po.getValorcoprodutos().getValororiginal();
			valorSuplemento = valorSuplemento * numeroDias;
		}
		if (valorSuplemento<0){
			valorSuplemento=0;
		}   
		return valorSuplemento;
	}

	public Date retornarDataConsultaOrcamento(Date dataInicio, Fornecedorcidadeidioma fornecedorcidadeidioma) {
		int anoFornecedor = fornecedorcidadeidioma.getFornecedorcidade().getFornecedor().getAnotarifario();
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicio);
		int ano = Formatacao.getAnoData(dataInicio);
		if (anoFornecedor >= ano) {
			return dataInicio;
		}
		else if (anoFornecedor < ano) {
			String sData = Formatacao.ConvercaoDataPadrao(dataInicio);
			String dia = sData.substring(0, 2);
			String mes = sData.substring(3, 5);
			sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
			return Formatacao.ConvercaoStringDataBrasil(sData);
		}    
		return dataInicio;
	}

	public Date calcularDataTerminoCurso(Date dataInical, int numeroSemanas) {
		if ((dataInical != null) && (numeroSemanas > 0)) {
			if (numeroSemanas > 0) {
				Date data = Formatacao.calcularDataFinal(dataInical, numeroSemanas);
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.OrcamentoCursoMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				String sql = "Select f from Fornecedorferias f where f.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataInical) + "' and f.datafinal<='"
						+ Formatacao.ConvercaoDataSql(data) + "'";
				FornecedorFeriasFacade fornecedorFeriasFacade = new FornecedorFeriasFacade();
				List<Fornecedorferias> listaFornecedorferias = fornecedorFeriasFacade.listar(sql);
				if (listaFornecedorferias == null) {
					listaFornecedorferias = new ArrayList<Fornecedorferias>();
				}
				if (listaFornecedorferias.size() > 0) {
					try {
						data = Formatacao.SomarDiasDatas(data, listaFornecedorferias.get(0).getNumerodias());
					} catch (Exception ex) {
						Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.OrcamentoCursoMB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
				return data;
			}
		}
		return null;
	}

	public boolean renderedValorPromocionalAparecendo(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean renderedValorPromocionalEscondido(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return false;
		} else {
			return true;
		}
	}

	public void gerarPromocaoAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sql = "";
		if (produtosOrcamentoBean.getFornecedorcidadeidiomaAcomodacao() != null) {
			 sql = "select p From Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'  and p.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
					+ produtosOrcamentoBean.getFornecedorcidadeidiomaAcomodacao().getFornecedorcidade().getCidade().getIdcidade()
					
					+ " group by p.promocaoacomodacao.idpromoacaoacomodacao";
		}else{
			 sql = "select p From Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'  and p.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
					+ " group by p.promocaoacomodacao.idpromoacaoacomodacao";
		}
		PromocaoAcomodacaoCidadeFacade cidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		List<Promocaoacomodacaocidade> promocaoacomodacaocidade = cidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getComplementoacomodacao() != null) {
			valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
		}
		if (promocaoacomodacaocidade != null && valorcoprodutos != null) {
			for (int i = 0; i < promocaoacomodacaocidade.size(); i++) {
				boolean tempromocao = verificarPromocaoAcomodacaoValida(promocaoacomodacaocidade.get(i).getPromocaoacomodacao(),
						valorcoprodutos, produtosOrcamentoBean);
				if (tempromocao) {
					float valordesconto = 0.0f;
					if (resultadoOrcamentoBean.getOcurso().getDatavalidade() == null) {
						resultadoOrcamentoBean.getOcurso()
								.setDatavalidade(promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getDatavalidadefinal());
					} else if (resultadoOrcamentoBean.getOcurso().getDatavalidade() != null
							&& resultadoOrcamentoBean.getOcurso().getDatavalidade()
									.after(promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getDatavalidadefinal())) {
						resultadoOrcamentoBean.getOcurso()
								.setDatavalidade(promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getDatavalidadefinal());
					}
					if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("P")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = valorcoprodutos.getValororiginal()
									* (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() / 100);
							valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = (float) (valorcoprodutos.getValororiginal()
									* produtosOrcamentoBean.getNumeroSemanas());
							valordesconto = valordesconto
									* (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() / 100);
						}
					} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("V")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = (float) (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana()
									* produtosOrcamentoBean.getNumeroSemanas());
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal();
						}
					} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("T")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = valorcoprodutos.getValororiginal()
									- promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana();
							if(valorcoprodutos.getCobranca().equalsIgnoreCase("S")){
								valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
							}
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = (float) (valorcoprodutos.getValororiginal()
									* produtosOrcamentoBean.getNumeroSemanas());
							valordesconto = valordesconto
									- promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal();
						}
					}
					if ((promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto() != null)
							&& (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto() > 0)) {
						if (valordesconto > promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto()) {
							valordesconto = promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto();
						}
					}
					if (valordesconto > 0) {
						produtosOrcamentoBean.setDescricaopromocao(
								descricaoPromocaoAcomodacao(promocaoacomodacaocidade.get(i).getPromocaoacomodacao()));
						float valorOriginal = (float) (valorcoprodutos.getValororiginal()
								* produtosOrcamentoBean.getNumeroSemanas());
						produtosOrcamentoBean.setValorOrigianl(valorOriginal);
						produtosOrcamentoBean.setValorOriginalRS(produtosOrcamentoBean.getValorOrigianl()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
						produtosOrcamentoBean.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - valordesconto);
						produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
						produtosOrcamentoBean.setPromocao(true);
						i=10000;
					}
				} else {
					produtosOrcamentoBean.setValorPromocional(0.0f);
					produtosOrcamentoBean.setValorPromocionalRS(0.0f);
					produtosOrcamentoBean.setPromocao(false);
				}
			} 
		} else {
			produtosOrcamentoBean.setValorPromocional(0.0f);
			produtosOrcamentoBean.setValorPromocionalRS(0.0f);
			produtosOrcamentoBean.setPromocao(false);
		}
	}

	public boolean verificarPromocaoAcomodacaoValida(Promocaoacomodacao promocao, Valorcoprodutos valorcoprodutos,
			ProdutosOrcamentoBean produtosOrcamentoBean) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioiniciocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioiniciocurso()))
					&& (resultadoOrcamentoBean.getOcurso().getDatainicio().before(promocao.getDatainicioterminiocurso())
							|| resultadoOrcamentoBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioacomodacao())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioacomodacao()))
					&& (resultadoOrcamentoBean.getOcurso().getDatatermino()
							.before(promocao.getDataterminioacodomodacao())
							|| resultadoOrcamentoBean.getOcurso().getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataterminocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}

		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (produtosOrcamentoBean.getNumeroSemanas() >= promocao.getNumerosemanainicio()
					&& produtosOrcamentoBean.getNumeroSemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoacomodacao() != null && promocao.getTipoacomodacao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoacomodacao()
					.equalsIgnoreCase(promocao.getTipoacomodacao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoquarto() != null && promocao.getTipoquarto().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoquarto()
					.equalsIgnoreCase(promocao.getTipoquarto())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTiporefeicao() != null && promocao.getTiporefeicao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTiporefeicao()
					.equalsIgnoreCase(promocao.getTiporefeicao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipobanheiro() != null && promocao.getTipobanheiro().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipobanheiro()
					.equalsIgnoreCase(promocao.getTipobanheiro())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}

	public String descricaoPromocaoAcomodacao(Promocaoacomodacao promocaoacomodacao) {
		String descricao = "";
		if (promocaoacomodacao.getAcomodacaoescola()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaoacomodacao.getDatainicioacomodacao() != null
				&& promocaoacomodacao.getDataterminioacodomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioacomodacao()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDataterminioacodomodacao()) + " | ";
		}
		if (promocaoacomodacao.getDatainicioiniciocurso() != null
				&& promocaoacomodacao.getDatainicioterminiocurso() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioiniciocurso()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioterminiocurso()) + " | ";
		}
		if (promocaoacomodacao.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatamatricula()) + " | ";
		}
		if (promocaoacomodacao.getDatavalidadeinicial() != null && promocaoacomodacao.getDatavalidadefinal() != null) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadeinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadefinal()) + " | ";
		}
		if (promocaoacomodacao.getNumerosemanafinal() != null && promocaoacomodacao.getNumerosemanainicio() != null
				&& promocaoacomodacao.getNumerosemanainicio() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaoacomodacao.getNumerosemanainicio() + " até "
					+ promocaoacomodacao.getNumerosemanafinal() + " | ";
		}
		if (promocaoacomodacao.getTipoacomodacao() != null && promocaoacomodacao.getTipoacomodacao().length() > 2) {
			descricao = descricao + "Tipo de Acomodação: " + promocaoacomodacao.getTipoacomodacao() + " | ";
		}
		if (promocaoacomodacao.getTipoquarto() != null && promocaoacomodacao.getTipoquarto().length() > 2) {
			descricao = descricao + "Tipo de Quarto: " + promocaoacomodacao.getTipoquarto() + " | ";
		}
		if (promocaoacomodacao.getTiporefeicao() != null && promocaoacomodacao.getTiporefeicao().length() > 2) {
			descricao = descricao + "Tipo de Refeição: " + promocaoacomodacao.getTiporefeicao() + " | ";
		}
		if (promocaoacomodacao.getTipobanheiro() != null && promocaoacomodacao.getTipobanheiro().length() > 2) {
			descricao = descricao + "Tipo de Banheiro: " + promocaoacomodacao.getTipobanheiro() + " | ";
		}
		if (promocaoacomodacao.getValormaximodesconto() != null && promocaoacomodacao.getValormaximodesconto() > 0) {
			descricao = descricao + "Valor máximo de desconto: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValormaximodesconto()) + " | ";
		}
		if (promocaoacomodacao.getValorsemanaacima() != null && promocaoacomodacao.getValorsemanaacima() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemanaacima()) + " | ";
		}
		if (promocaoacomodacao.getValorsemanaigual() != null && promocaoacomodacao.getValorsemanaigual() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemanaigual()) + " | ";
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValorsemana() != null
				&& promocaoacomodacao.getValorsemana() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + "% | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + " | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + " | ";
			}
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValortotal() != null
				&& promocaoacomodacao.getValortotal() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + "% | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + " | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional da acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + " | ";
			}
		}
		return descricao;
	}

	public void gerarPromocaoTaxas(List<ProdutosOrcamentoBean> listaObrigatorios, List<ProdutosOrcamentoBean> curso) {
		String sql = "select p From Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaotaxacurso.idpromocaotaxacurso";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaPromocaotaxacidade = promocaoTaxaCidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (listaPromocaotaxacidade != null) {
			for (int j = 0; j < listaPromocaotaxacidade.size(); j++) {
				int idproduto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getProdutosorcamento()
						.getIdprodutosOrcamento();
				int posicao = 0;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
							.getIdprodutosOrcamento() == idproduto) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1005;
					}
				}
				if (listaPromocaotaxacidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoTaxasValida(
							listaPromocaotaxacidade.get(j).getPromocaotaxacurso(), valorcoprodutos,curso);
					if (tempromocao) {
						float valordesconto = 0.0f;
						if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = (valorcoprodutos.getValororiginal() * multiplicador)
										* (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
												/ 100);
							}
						} else if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
										* multiplicador;
							}
						}
						if (valordesconto > 0) {
							listaObrigatorios.get(posicao).setDescricaopromocao(
									descricaoPromocaoTaxas(listaPromocaotaxacidade.get(j).getPromocaotaxacurso()));
							listaObrigatorios.get(posicao).setValorPromocional(
									listaObrigatorios.get(posicao).getValorOrigianl() - valordesconto);
							listaObrigatorios.get(posicao)
									.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional()
											* resultadoOrcamentoBean.getOcurso().getValorcambio());
							listaObrigatorios.get(posicao).setPromocao(true);
							calcularTotais();
						}
					}
				}
			}
		}
	}
	
	public void gerarPromocaoTaxasAcomodacaoIndependente(List<ProdutosOrcamentoBean> listaObrigatorios, List<ProdutosOrcamentoBean> curso, Fornecedorcidadeidioma fornecedorcidadeidioma) {
		String sql = "select p From Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ fornecedorcidadeidioma.getIdfornecedorcidadeidioma();
		if(!fornecedorcidadeidioma.isAcomodacaoindependente()) {
			sql=sql	+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento();
		}
		sql=sql	+ " group by p.promocaotaxacurso.idpromocaotaxacurso";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaPromocaotaxacidade = promocaoTaxaCidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (listaPromocaotaxacidade != null) {
			for (int j = 0; j < listaPromocaotaxacidade.size(); j++) {
				int idproduto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getProdutosorcamento()
						.getIdprodutosOrcamento();
				int posicao = 0;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
							.getIdprodutosOrcamento() == idproduto) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1005;
					}
				}
				if (listaPromocaotaxacidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoTaxasValida(
							listaPromocaotaxacidade.get(j).getPromocaotaxacurso(), valorcoprodutos,curso);
					if (tempromocao) {
						float valordesconto = 0.0f;
						if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = (valorcoprodutos.getValororiginal() * multiplicador)
										* (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
												/ 100);
							}
						} else if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
										* multiplicador;
							}
						}
						if (valordesconto > 0) {
							listaObrigatorios.get(posicao).setDescricaopromocao(
									descricaoPromocaoTaxas(listaPromocaotaxacidade.get(j).getPromocaotaxacurso()));
							listaObrigatorios.get(posicao).setValorPromocional(
									listaObrigatorios.get(posicao).getValorOrigianl() - valordesconto);
							listaObrigatorios.get(posicao)
									.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional()
											* resultadoOrcamentoBean.getOcurso().getValorcambio());
							listaObrigatorios.get(posicao).setPromocao(true);
							calcularTotais();
						}
					}
				}
			}
		}
	}

	public boolean verificarPromocaoTaxasValida(Promocaotaxacurso promocao, Valorcoprodutos valorcoprodutos,List<ProdutosOrcamentoBean> curso) {
		Boolean tempromocao = false;
		if (promocao.getDatainiciocursoinicial() != null && promocao.getDatainiciocursofinal() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainiciocursoinicial())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainiciocursoinicial()))
					&& (resultadoOrcamentoBean.getOcurso().getDatainicio().before(promocao.getDatainiciocursofinal())
							|| resultadoOrcamentoBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainiciocursofinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDatafinalacomodacao() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDataacomodacaoinicial())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDataacomodacaoinicial()))
					&& (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDatafinalacomodacao())
							|| resultadoOrcamentoBean.getOcurso().getDatatermino()
									.equals(promocao.getDatafinalacomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getAcomodacaoselecionada()) {
			return true;
		}
		if (promocao.getDatamatriculaenciadaate() != null) {
			if (new Date().before(promocao.getDatamatriculaenciadaate())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminio() != null) {
			if (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataterminio())
					|| resultadoOrcamentoBean.getOcurso().getDatatermino().equals(promocao.getDataterminio())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanasinicial() != null && promocao.getNumerosemanasinicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (resultadoOrcamentoBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanasinicial()
					&& resultadoOrcamentoBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (resultadoOrcamentoBean.getOcurso().getTurno().equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer
					.parseInt(curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso()
					.getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;

	}

	public String descricaoPromocaoTaxas(Promocaotaxacurso promocaotaxacurso) {
		String descricao = "";
		if (promocaotaxacurso.getAcomodacaoselecionada()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaotaxacurso.getCargahoraria() != null && promocaotaxacurso.getCargahoraria() > 0
				&& promocaotaxacurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaotaxacurso.getCargahoraria() + " "
					+ promocaotaxacurso.getTipocargahoraria() + " | ";
		}
		if (promocaotaxacurso.getDataacomodacaoinicial() != null
				&& promocaotaxacurso.getDatafinalacomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatafinalacomodacao()) + " | ";
		}
		if (promocaotaxacurso.getDatainiciocursoinicial() != null
				&& promocaotaxacurso.getDatainiciocursofinal() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursofinal()) + " | ";
		}
		if (promocaotaxacurso.getDatamatriculaenciadaate() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatamatriculaenciadaate()) + " | ";
		}
		if (promocaotaxacurso.getDataterminio() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataterminio()) + " | ";
		}
		if ((promocaotaxacurso.getDatavalidadefinal() != null)
				&& (promocaotaxacurso.getDatavalidadeinicial() != null)) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadeinicial()) + " a "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaotaxacurso.getNumerosemanafinal() != null && promocaotaxacurso.getNumerosemanasinicial() != null
				&& promocaotaxacurso.getNumerosemanasinicial() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaotaxacurso.getNumerosemanasinicial() + " até "
					+ promocaotaxacurso.getNumerosemanafinal() + " | ";
		}
		if (promocaotaxacurso.getTurno() != null && promocaotaxacurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaotaxacurso.getTurno() + " | ";
		}
		if (promocaotaxacurso.getValorporsemana() != null && promocaotaxacurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaotaxacurso.getValorporsemana()) + " | ";
		}

		if (promocaotaxacurso.getTipodesconto() != null) {
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + "% | ";
			}
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + " | ";
			}
		}
		return descricao;
	}

	public void gerarPromocaoBrindes(List<ProdutosOrcamentoBean> listaObrigatorio, ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sql = "select p From Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaobrindecurso.idpromocaobrindecurso";  
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listaPromocaoBrindeCursoCidade = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listaPromocaoBrindeCursoCidade != null) {
			for (int j = 0; j < listaPromocaoBrindeCursoCidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
				if (listaPromocaoBrindeCursoCidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoBrindesValido(
							listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso(), valorcoprodutos,
							produtosOrcamentoBean);
					if (tempromocao) {

						float valordesconto = 0.0f;
						if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getGanhasemana() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getGanhasemana() > 0) {
							if (produtosOrcamentoBean.getDescricaobrinde() == null) {
								int numeroSemana = resultadoOrcamentoBean.getOcurso().getNumerosemanas()
										+ listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhasemana();
								produtosOrcamentoBean
										.setDescricaobrinde("Matricule-se até o dia "
												+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
														.getPromocaobrindecurso().getDatamatricula())
												+ " pague " + resultadoOrcamentoBean.getOcurso().getNumerosemanas()
												+ " semanas e curse " + numeroSemana + ".");
								produtosOrcamentoBean.setPromocao(true);
								resultadoOrcamentoBean.getOcurso().setNumerosemanas(numeroSemana);
								int idtaxatm = aplicacaoMB.getParametrosprodutos().getTaxatmorcamento();
								for (int i = 0; i < listaObrigatorio.size(); i++) {
									if (idtaxatm != listaObrigatorio.get(i).getValorcoprodutos().getCoprodutos()
											.getProdutosorcamento().getIdprodutosOrcamento()) {
										ProdutosOrcamentoBean po = listaObrigatorio.get(i);
										int multiplicador = 1;
										if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
											multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
										} else if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
											multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
										}
										po.setValorOrigianl(po.getValorcoprodutos().getValororiginal() * multiplicador);
										po.setValorOriginalRS(po.getValorOrigianl()
												* resultadoOrcamentoBean.getOcurso().getValorcambio());
									}
								}
							}
						} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
								.getGanhadescontosemana() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getGanhadescontosemana() > 0) { 
								valordesconto = valorcoprodutos.getValororiginal() * listaPromocaoBrindeCursoCidade
										.get(j).getPromocaobrindecurso().getGanhadescontosemana(); 
								produtosOrcamentoBean.setPromocao(true); 
						} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
								.getGanhadescontosemanaacomodacao() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getGanhadescontosemanaacomodacao() > 0) {
							valordesconto = valorcoprodutos.getValororiginal() * listaPromocaoBrindeCursoCidade.get(j)
									.getPromocaobrindecurso().getGanhadescontosemanaacomodacao();
							int numeroSemanas = resultadoOrcamentoBean.getOcurso().getNumerosemanas()
									- listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemanaacomodacao();
							produtosOrcamentoBean.setDescricaobrinde("Matricule-se até o dia "
									+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
											.getPromocaobrindecurso().getDatamatricula())
									+ " pague " + numeroSemanas + " e ganhe mais "
									+ Formatacao.formatarDouble(produtosOrcamentoBean.getNumeroSemanas())
									+ " semana(s) de acomodação.");
							produtosOrcamentoBean.setPromocao(true);
						} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
								.getGanhadescricao() != null
								&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getGanhadescricao()
										.length() > 2) {
							if (produtosOrcamentoBean.getDescricaobrinde() == null) {
								produtosOrcamentoBean.setDescricaobrinde(listaPromocaoBrindeCursoCidade.get(j)
										.getPromocaobrindecurso().getGanhadescricao());
								produtosOrcamentoBean.setPromocao(true);
							}
						} 
						if (valordesconto > 0) { 
							if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescontosemana() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemana() > 0) {
								if (produtosOrcamentoBean.getValorPromocional() == null
										|| produtosOrcamentoBean.getValorPromocional() == 0) {
									produtosOrcamentoBean
											.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - valordesconto);
									produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
											* resultadoOrcamentoBean.getOcurso().getValorcambio());
								} else {
									produtosOrcamentoBean.setValorPromocional(
											produtosOrcamentoBean.getValorPromocional() - valordesconto);
									produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
											* resultadoOrcamentoBean.getOcurso().getValorcambio());
								}
							} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescontosemanaacomodacao() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemanaacomodacao() > 0) {
								if (produtosOrcamentoBean.getValorPromocional() == null
										|| produtosOrcamentoBean.getValorPromocional() == 0) {
									produtosOrcamentoBean.setValorPromocional(
											produtosOrcamentoBean.getValorOrigianl() - valordesconto);
									produtosOrcamentoBean
											.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
													* resultadoOrcamentoBean.getOcurso().getValorcambio());
								} else {
									produtosOrcamentoBean.setValorPromocional(
											produtosOrcamentoBean.getValorPromocional() - valordesconto);
									produtosOrcamentoBean
											.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
													* resultadoOrcamentoBean.getOcurso().getValorcambio());
								}
							}
							calcularTotais();
						}
					}
				}
			}
		}
	}

	public boolean verificarPromocaoBrindesValido(Promocaobrindecurso promocao, Valorcoprodutos valorcoprodutos,
			ProdutosOrcamentoBean produtosOrcamentoBean) {
		Boolean tempromocao = false;
		if (promocao.getDatainicio() != null && promocao.getDatafinal() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicio())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicio()))
					&& (resultadoOrcamentoBean.getOcurso().getDatainicio().before(promocao.getDatafinal())
							|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatafinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDataacomodacaofinal() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDataacomodacaoinicial())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDataacomodacaoinicial()))
					&& (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataacomodacaofinal())
							|| resultadoOrcamentoBean.getOcurso().getDatatermino()
									.equals(promocao.getDataacomodacaofinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicial() != null && promocao.getNumerosemanainicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (resultadoOrcamentoBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanainicial()
					&& resultadoOrcamentoBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanacurso() != null && promocao.getNumerosemanacurso() > 0) {
			int nSemana = 0;
			for (int i = resultadoOrcamentoBean.getOcurso().getNumerosemanas(); i <= promocao
					.getNumerosemanacurso(); i++) {
				nSemana = nSemana + promocao.getNumerosemanacurso();
			} 
			tempromocao = true;
		}
		if (promocao.getNumerosemanaacomodacao() != null && promocao.getNumerosemanaacomodacao() > 0) {
			int nSemana = 0;
			for (int i = (int) produtosOrcamentoBean.getNumeroSemanas(); i <= promocao
					.getNumerosemanaacomodacao(); i++) {
				nSemana = nSemana + promocao.getNumerosemanaacomodacao();
			} 
			tempromocao = true;
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (resultadoOrcamentoBean.getOcurso().getTurno().equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer
					.parseInt(valorcoprodutos.getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && valorcoprodutos.getCoprodutos().getComplementocurso()
					.getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;

	}

	public String descricaoPromocaoBrinde(Promocaobrindecurso promocaobrindecurso) {
		String descricao = "";
		if (promocaobrindecurso.getDatavalidadeinicial() != null
				&& promocaobrindecurso.getDatavalidadeinicial() != null) {
			descricao = descricao + "Período de validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadeinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaobrindecurso.getNumerosemanainicial() != null && promocaobrindecurso.getNumerosemanafinal() != null
				&& promocaobrindecurso.getNumerosemanainicial() > 0 && promocaobrindecurso.getNumerosemanafinal() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaobrindecurso.getNumerosemanainicial() + " até "
					+ promocaobrindecurso.getNumerosemanafinal() + " | ";
		}
		if (promocaobrindecurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatamatricula()) + " | ";
		}
		if ((promocaobrindecurso.getDataacomodacaoinicial() != null)
				&& (promocaobrindecurso.getDataacomodacaofinal() != null)) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaofinal()) + " | ";
		}
		if ((promocaobrindecurso.getDatainicio() != null) && (promocaobrindecurso.getDatafinal() != null)) {
			descricao = descricao + "Data início do curso entre: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatainicio()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatafinal()) + " | ";
		}
		if (promocaobrindecurso.getValorporsemana() != null && promocaobrindecurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaobrindecurso.getValorporsemana()) + " | ";
		}
		if (promocaobrindecurso.getTurno() != null && promocaobrindecurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaobrindecurso.getTurno() + " | ";
		}
		if (promocaobrindecurso.getNumerosemanacurso() != null && promocaobrindecurso.getNumerosemanacurso() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanacurso() + " semana(s) curso | ";
		}
		if (promocaobrindecurso.getNumerosemanaacomodacao() != null
				&& promocaobrindecurso.getNumerosemanaacomodacao() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanaacomodacao()
					+ " semana(s) acomodação  | ";
		}
		if (promocaobrindecurso.getGanhasemana() != null && promocaobrindecurso.getGanhasemana() > 0) {
			descricao = descricao + "Ganha: " + promocaobrindecurso.getGanhasemana() + " semana(s) de curso ";
		}
		if (promocaobrindecurso.getGanhadescontosemana() != null && promocaobrindecurso.getGanhadescontosemana() > 0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemana()
					+ " semana(s) de curso ";
		}
		if (promocaobrindecurso.getGanhadescontosemanaacomodacao() != null
				&& promocaobrindecurso.getGanhadescontosemanaacomodacao() > 0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemanaacomodacao()
					+ " semana(s) de acomodação ";
		}
		if (promocaobrindecurso.getGanhadescricao() != null && promocaobrindecurso.getGanhadescricao().length() > 2) {
			descricao = descricao + "Brinde: " + promocaobrindecurso.getGanhadescricao();
		}
		return descricao;
	}

	public void alterarCambio() {
		for (int i = 0; i < resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().size(); i++) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i)
					.getValorPromocional() != null
					&& resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i)
							.getValorPromocional() > 0) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i).setValorPromocionalRS(
						resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i)
								.getValorPromocional() * resultadoOrcamentoBean.getOcurso().getValorcambio());
			} else {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(i)
						.setValorOriginalRS(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal()
								.get(i).getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
		for (int i = 0; i < resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().size(); i++) {
			int idtaxatm = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).getValorcoprodutos()
					.getCoprodutos().getProdutosorcamento().getIdprodutosOrcamento() != idtaxatm) {
				if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).isPromocao()) {
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i)
							.setValorPromocionalRS(resultadoOrcamentoBean.getProdutoFornecedorBean()
									.getListaObrigaroerios().get(i).getValorPromocional()
									* resultadoOrcamentoBean.getOcurso().getValorcambio());
				} else {
					resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i).setValorOriginalRS(
							resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i)
									.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
				}
			} else {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().get(i)
						.setValorOrigianl(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios()
								.get(i).getValorOriginalRS() / resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
		for (int i = 0; i < resultadoOrcamentoBean.getListaOpcionais().size(); i++) {
			resultadoOrcamentoBean.getListaOpcionais().get(i)
					.setValorOriginalRS(resultadoOrcamentoBean.getListaOpcionais().get(i).getValorOrigianl()
							* resultadoOrcamentoBean.getOcurso().getValorcambio());
		}
		for (int i = 0; i < resultadoOrcamentoBean.getListaTransfer().size(); i++) {
			if (resultadoOrcamentoBean.getListaTransfer().get(i).isSelecionado()) {
				valorTotalAdicionais = valorTotalAdicionais
						+ resultadoOrcamentoBean.getListaTransfer().get(i).getValorOrigianl();
				resultadoOrcamentoBean.getListaTransfer().get(i)
						.setValorOriginalRS(resultadoOrcamentoBean.getListaTransfer().get(i).getValorOrigianl()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}

		for (int i = 0; i < resultadoOrcamentoBean.getListaAcomodacoes().size(); i++) {
			if (resultadoOrcamentoBean.getListaAcomodacoes().get(i).isSelecionado()) {
				if (resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocional() != null
						&& resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocional() > 0) {
					resultadoOrcamentoBean.getListaAcomodacoes().get(i).setValorPromocionalRS(
							resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorPromocional()
									* resultadoOrcamentoBean.getOcurso().getValorcambio());
				} else {
					resultadoOrcamentoBean.getListaAcomodacoes().get(i)
							.setValorOriginalRS(resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorOrigianl()
									* resultadoOrcamentoBean.getOcurso().getValorcambio());
				}
			}
		}

		for (int i = 0; i < resultadoOrcamentoBean.getListaAcOpcional().size(); i++) {
			if (resultadoOrcamentoBean.getListaAcOpcional().get(i).isSelecionado()) {
				resultadoOrcamentoBean.getListaAcOpcional().get(i)
						.setValorOriginalRS(resultadoOrcamentoBean.getListaAcOpcional().get(i).getValorOrigianl()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}

		for (int i = 0; i < resultadoOrcamentoBean.getOcurso().getOcursodescontoList().size(); i++) {
			if (resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).isSelecionado()) {
				resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).setValormoedaestrangeira(
						resultadoOrcamentoBean.getOcurso().getOcursodescontoList().get(i).getValormoedanacional()
								/ resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
		if (resultadoOrcamentoBean.getListaOutrosProdutos() != null) {
			for (int i = 0; i < resultadoOrcamentoBean.getListaOutrosProdutos().size(); i++) {
				resultadoOrcamentoBean.getListaOutrosProdutos().get(i)
						.setValorOriginalRS(resultadoOrcamentoBean.getListaOutrosProdutos().get(i).getValorOrigianl()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
			}
		}
		if (seguroSelecionado) {
			calcularDataTermino();
		}
		calcularTotais();
	}

	public String gerarDataTerminoPagina() {
		String dataTermino = Formatacao.ConvercaoDataPadrao(resultadoOrcamentoBean.getOcurso().getDatatermino());
		if (resultadoOrcamentoBean.getOcurso().getNumerodiasferiado() > 0) {
			dataTermino = dataTermino + " (" + resultadoOrcamentoBean.getOcurso().getNumerodiasferiado()
					+ " dias de intervalo.)";
		}
		return dataTermino;
	}

	public void verificarPacote() {
		if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal() != null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().size() > 0) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0).getValorcoprodutos()
					.getCoprodutos().isPacote()) {
				habilitarOpcaoPacote = "true";
			} else {
				habilitarOpcaoPacote = "false";
			}
		} else {
			habilitarOpcaoPacote = "false";
		}
	}

	public void esconderOpcionais() {
		if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal() != null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().size() > 0) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0).getValorcoprodutos()
					.getCoprodutos().isPacote()) {
				escoderOpcionais = "false";
			} else {
				escoderOpcionais = "true";
			}
		} else {
			escoderOpcionais = "true";
		}
	}

	public void verificarPacoteAcomodacao() {
		if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal() != null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().size() > 0) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0).getValorcoprodutos()
					.getCoprodutos().isPacote()
					&& resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
							.getValorcoprodutos().getCoprodutos().isAcomodacao()) {
				pacoteAcomodacao = "false";
			} else {
				pacoteAcomodacao = "true";
			}
		} else {
			pacoteAcomodacao = "true";
		}
	}

	public void verificarPacoteTransfer() {
		if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal() != null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().size() > 0) {
			if (resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0).getValorcoprodutos()
					.getCoprodutos().isPacote()
					&& resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
							.getValorcoprodutos().getCoprodutos().isTransfer()) {
				pacoteTransfer = "false";
			} else {
				pacoteTransfer = "true";
			}
		} else {
			pacoteTransfer = "true";
		}
			
	}

	public void verificarNumeroSemanas() {
		if (resultadoOrcamentoBean.getOcurso().getNumerosemanasbrinde() > 0) {
			numeroSemanas = resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " semanas + "
					+ resultadoOrcamentoBean.getOcurso().getNumerosemanasbrinde() + " semanas FREE";
		} else {
			numeroSemanas = resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " semanas";
		}
	}

	public void convertendoValoresSeguro() {
		valorTotalSeguroDola = seguroviagem.getNumeroSemanas() * seguroviagem.getValoresseguro().getValorgross();
		valorUtilitarioRS = seguroviagem.getValoresseguro().getValorgross()
				* resultadoOrcamentoBean.getOcurso().getValorcambio();
	}
	
	public void verificarEdicao(){
		if(resultadoOrcamentoBean.getOcurso()!=null 
				&& resultadoOrcamentoBean.getOcurso().getIdocurso()!=null){
			//ocurso produtos
			OCursoProdutoFacade oCursoProdutoFacade = new OCursoProdutoFacade();
			List<Ocrusoprodutos> listaprodutos = oCursoProdutoFacade.listar(resultadoOrcamentoBean.getOcurso().getIdocurso());
			if(listaprodutos!=null){
				for (int i = 0; i < listaprodutos.size(); i++) {
					oCursoProdutoFacade.excluir(listaprodutos.get(i).getIdocrusoprodutos());
				}
			}
			//seguro viagem
			OcursoSeguroViagemFacade ocursoSeguroViagemFacade = new OcursoSeguroViagemFacade();
			Ocursoseguro seguro = ocursoSeguroViagemFacade.consultar(resultadoOrcamentoBean.getOcurso().getIdocurso());
			if(seguro!=null){ 
				ocursoSeguroViagemFacade.excluir(seguro.getIdocursoseguro()); 
			}
			//remover desconto
			OCursoDescontoFacade cursoDescontoFacade = new OCursoDescontoFacade();
			List<Ocursodesconto> listaDesconto = cursoDescontoFacade.listar(resultadoOrcamentoBean.getOcurso().getIdocurso());
			if(listaDesconto!=null){
				for (int i = 0; i < listaDesconto.size(); i++) {
					cursoDescontoFacade.excluir(listaDesconto.get(i).getIdocursodesconto());
				}   
			}
			//remover forma pagamento
			OCursoFormaPagamentoFacade ocursoFormaPagamentoFacade = new OCursoFormaPagamentoFacade();
			List<Ocursoformapagamento> listaFormaPagamento = ocursoFormaPagamentoFacade.listar(resultadoOrcamentoBean.getOcurso().getIdocurso());
			if(listaFormaPagamento!=null){
				for (int i = 0; i < listaFormaPagamento.size(); i++) {
					ocursoFormaPagamentoFacade.excluir(listaFormaPagamento.get(i).getIdocursoformapagamento());
				}   
			}
		}
	}
	
	
	public String adicionarProdutosExtra() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("valorCambio", resultadoOrcamentoBean.getOcurso().getValorcambio());
		session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("produtosExtra", options, null);
		return "";
	}

	public void retornoDialogoProdutosExtra(SelectEvent event) {
		ProdutosExtrasBean feb = (ProdutosExtrasBean) event.getObject();
		feb.setValorOrigianlString(resultadoOrcamentoBean.getCambio().getMoedas().getSigla() + " "
				+ Formatacao.formatarFloatString(feb.getValorOrigianl()));
		feb.setValorOriginalRSString("R$ " + Formatacao.formatarFloatString(feb.getValorOriginalRS()));
		resultadoOrcamentoBean.getListaOutrosProdutos().add(feb);
		calcularTotais();
	}

	public void excluirProdutoExtra(ProdutosExtrasBean produtosExtrasBean) { 
		valorTotalAdicionaisRS = valorTotalAdicionaisRS - produtosExtrasBean.getValorOriginalRS();
		valorTotalAdicionais = valorTotalAdicionais - produtosExtrasBean.getValorOrigianl();
		resultadoOrcamentoBean.getListaOutrosProdutos().remove(produtosExtrasBean);
		calcularTotais();
	}
	
	public String adicionarTransfer() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean); 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("adicionarTransfer", options, null);
		return "";
	}

	public void retornoDialogoTransfer(SelectEvent event) {
		ProdutosOrcamentoBean po = (ProdutosOrcamentoBean) event.getObject(); 
		if(po!=null){
			resultadoOrcamentoBean.getListaTransfer().add(po);
			calcularTotais();
		}
	}

	public void excluirTransfer(ProdutosOrcamentoBean po) {  
		po.setSelecionado(false);
		resultadoOrcamentoBean.getListaTransfer().remove(po);
		calcularTotais();
	}
	
	public String adicionarOpcionalAcomodacao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("adicionarOpcionalAcomodacao", options, null);
		return "";
	}

	public void retornoDialogoOpcionalAcomodacao(SelectEvent event) {
		ProdutosOrcamentoBean po = (ProdutosOrcamentoBean) event.getObject(); 
		if(po!=null){
			resultadoOrcamentoBean.getListaAcOpcional().add(po);
			calcularTotais();
		}
	}

	public void excluirOpcionalAcomodacao(ProdutosOrcamentoBean po) {  
		po.setSelecionado(false);
		resultadoOrcamentoBean.getListaAcOpcional().remove(po);
		calcularTotais();
	}
	
	public String adicionarAcomodacao() { 
		if (resultadoOrcamentoBean.getFornecedorcidadeidioma().isAcomodacao() || resultadoOrcamentoBean.getFornecedorcidadeidioma().isAcomodacaoindependente()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 750);
			RequestContext.getCurrentInstance().openDialog("adicionarAcomodacao", options, null);
		}else {
			Mensagem.lancarMensagemInfo("Sem Acomodação", "");
		}
		return "";
	}

	public void retornoDialogoAcomodacao(SelectEvent event) {
		ProdutosOrcamentoBean po = (ProdutosOrcamentoBean) event.getObject(); 
		if(po!=null){
			resultadoOrcamentoBean.getListaAcomodacoes().add(po);
			mudarNumeroSemanaAcomodacao(po); 
		}
	}

	public void excluirAcomodacao(ProdutosOrcamentoBean po) {  
		po.setSelecionado(false);
		mudarNumeroSemanaAcomodacao(po); 
		resultadoOrcamentoBean.getListaAcomodacoes().remove(po); 
	}
	
	public boolean desabilitarNovaAcomodacao(){
		if(resultadoOrcamentoBean.getListaAcomodacoes().size()>0){
			return true;
		}else return false;
	}
	
	public String adicionarTaxasOpcionais() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
		session.setAttribute("listaOpcionais", listaOpcionais);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("adicionarTaxasOpcional", options, null);
		return "";
	}
	
	
	public void retornoDialogoOpcional(SelectEvent event) {
		ProdutosOrcamentoBean po = (ProdutosOrcamentoBean) event.getObject(); 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaOpcionais = (List<ProdutosOrcamentoBean>) session.getAttribute("listaOpcionais");
		session.removeAttribute("listaOpcionais");
		if (listaOpcionais.size() > 0) {
			desabilitarbtnOpcional = false;
		}else{
			desabilitarbtnOpcional = true;
		}
		if(po!=null){
			resultadoOrcamentoBean.getListaOpcionais().add(po);
			calcularTotais();
		}
	}
	
	public void excluirTaxaOpcional(ProdutosOrcamentoBean po) {  
		po.setSelecionado(false); 
		resultadoOrcamentoBean.getListaOpcionais().remove(po);
		listaOpcionais.add(po);
		calcularTotais();
	}
	
	public void gerarPromocaoCurso(List<ProdutosOrcamentoBean> listaObrigatorios) {
		String sql = "select p From Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ resultadoOrcamentoBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promoacaocurso.idpromoacaocurso";
		PromocaoCursoFornecedorCidadeFacade cidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		List<Promocaocursocidade> listaPromocaocursocidade = cidadeFacade.listar(sql);
		if (listaPromocaocursocidade != null) { 
			int posicao = 0;
			float desconto = 0.0f; 
			String descricao ="";
			for (int j = 0; j < listaPromocaocursocidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = null; 
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso() != null) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1000;
					}
				} 
				if (listaPromocaocursocidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoValida(listaPromocaocursocidade.get(j).getPromoacaocurso(),
							valorcoprodutos);
					if (tempromocao) { 
						float valordesconto = 0.0f;
						resultadoOrcamentoBean.setCodigo(listaPromocaocursocidade.get(j).getPromoacaocurso().getCodigo()); 
						if (resultadoOrcamentoBean.getOcurso().getDatavalidade() == null) {
							resultadoOrcamentoBean.getOcurso().setDatavalidade(
									listaPromocaocursocidade.get(j).getPromoacaocurso().getDatavalidadefinal());
						} else if (resultadoOrcamentoBean.getOcurso().getDatavalidade() != null
								&& resultadoOrcamentoBean.getOcurso().getDatavalidade().after(
										listaPromocaocursocidade.get(j).getPromoacaocurso().getDatavalidadefinal())) {
							resultadoOrcamentoBean.getOcurso().setDatavalidade(
									listaPromocaocursocidade.get(j).getPromoacaocurso().getDatavalidadefinal());
						}
						if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								valordesconto = valorcoprodutos.getValororiginal()
										* (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() / 100);
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = valordesconto * resultadoOrcamentoBean.getOcurso().getNumerosemanas();
								}
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								int multiplicador = 1;  
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
								}
								float valorcurso = valorcoprodutos.getValororiginal() * multiplicador;
								if(listaPromocaocursocidade.get(j).getPromoacaocurso().isAcumulapromocao()){
									valorcurso = (valorcoprodutos.getValororiginal() * multiplicador)-desconto;
								} 
								valordesconto = valorcurso
										* (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() / 100); 
							}
						} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana()
										* resultadoOrcamentoBean.getOcurso().getNumerosemanas();
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal();
							}
						} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("T")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = (valorcoprodutos.getValororiginal()
											* resultadoOrcamentoBean.getOcurso().getNumerosemanas());
								} else {
									valordesconto = (valorcoprodutos.getValororiginal());
								}
								valordesconto = valordesconto
										- (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana()
												* resultadoOrcamentoBean.getOcurso().getNumerosemanas());
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = (valorcoprodutos.getValororiginal()
											* resultadoOrcamentoBean.getOcurso().getNumerosemanas());
								} else {
									valordesconto = (valorcoprodutos.getValororiginal());
								}
								valordesconto = valordesconto
										- listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal();
							}
						}
						if ((listaPromocaocursocidade.get(j).getPromoacaocurso().getValormaximodesconto() != null)
								&& (listaPromocaocursocidade.get(j).getPromoacaocurso().getValormaximodesconto() > 0)) {
							if (valordesconto > listaPromocaocursocidade.get(j).getPromoacaocurso()
									.getValormaximodesconto()) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso()
										.getValormaximodesconto();
							}
						} 
						if(valordesconto>0){
							desconto = desconto + valordesconto;
						}
						if (valorcoprodutos.getCoprodutos().isPacote()) {
							desconto = 0f;
						}
					} 
				}
			}
			if (desconto > 0) {
				listaObrigatorios.get(posicao).setDescricaopromocao(descricao);
				listaObrigatorios.get(posicao).setValorPromocional(
						listaObrigatorios.get(posicao).getValorOrigianl() - desconto);
				listaObrigatorios.get(posicao)
						.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional()
								* resultadoOrcamentoBean.getOcurso().getCambio().getValor());
				listaObrigatorios.get(posicao).setPromocao(true);
			}
		}
	}

	public boolean verificarPromocaoValida(Promocaocurso promocao, Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioiniciocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioiniciocurso()))
					&& (resultadoOrcamentoBean.getOcurso().getDatainicio().before(promocao.getDatainicioterminiocurso())
							|| resultadoOrcamentoBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if(promocao.getAcomodacaoescola()==true && (resultadoOrcamentoBean.getListaAcomodacoes()==null
				|| resultadoOrcamentoBean.getListaAcomodacoes().size()==0)){
			return false;
		} else if(promocao.getAcomodacaoescola()==true && resultadoOrcamentoBean.getListaAcomodacoes()!=null
				&& resultadoOrcamentoBean.getListaAcomodacoes().size()>0){
			tempromocao = true;
		} 
		if(promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioacomodacao())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioacomodacao()))
					&& (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataterminioacodomodacao())
							|| resultadoOrcamentoBean.getOcurso().getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataterminocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (resultadoOrcamentoBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanainicio()
					&& resultadoOrcamentoBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementocurso().getTurno()
					.equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer
					.parseInt(valorcoprodutos.getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && valorcoprodutos.getCoprodutos().getComplementocurso()
					.getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}
	
	public boolean mostrarInformacaoCurso() {
		if(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0).getValorcoprodutos().getCoprodutos().getAdvertencia()!=null
				&& resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0).getValorcoprodutos().getCoprodutos().getAdvertencia().length()>1) {
			return true;
		}else return false;
	}
	
	public void seguroCancelamento() {
		if(seguroviagem.isSegurocancelamento() && seguroviagem.getValoresseguro().isSegurocancelamento()) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getOcurso().getCambio().getData()),
					seguroviagem.getValoresseguro().getMoedas().getIdmoedas()); 
			float valorsegurocancelamento = seguroviagem.getValoresseguro().getValorsegurocancelamento()
					* cambioSeguro.getValor();
			seguroviagem.setValorSeguro(seguroviagem.getValorSeguro());
		} 
	}
	
	public void selecionarSeguroCancelamento() {
		if(seguroviagem.isSegurocancelamento() && seguroviagem.getValoresseguro().isSegurocancelamento()) { 
			calcularTotais();
		} else if(seguroviagem.getValoresseguro().isSegurocancelamento()) { 
			calcularTotais();
		} 
	}
	
	public void verificarSeguroCancelamento() {
		if (seguroviagem != null) {
			if(seguroviagem.getValoresseguro().isSegurocancelamento()) {
				segurocancelamento = true;
				numero="4";
			} else {
				segurocancelamento = false;
				numero="3";
			}
		}
	} 
	
	public Date retornarDataConsultaOrcamento(Date dataInicio, Fornecedor fornecedor) {
		int anoFornecedor = fornecedor.getAnotarifario();
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicio);
		int ano = Formatacao.getAnoData(dataInicio);
		if (anoFornecedor >= ano) {
			return dataInicio;
		} else if (anoFornecedor < ano) {
			String sData = Formatacao.ConvercaoDataPadrao(dataInicio);
			String dia = sData.substring(0, 2);
			String mes = sData.substring(3, 5);
			sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
			return Formatacao.ConvercaoStringDataBrasil(sData);
		}
		return dataInicio;
	}
}
