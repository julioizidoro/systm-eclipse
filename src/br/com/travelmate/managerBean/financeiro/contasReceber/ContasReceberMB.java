/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.contasReceber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.bean.BolinhasBean;
import br.com.travelmate.bean.LerRetornoItauBean;
import br.com.travelmate.bean.RelatorioErroBean; 
import br.com.travelmate.facade.CobrancaFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HistoricoCobrancaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.boleto.DadosBoletoBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RetornoBean;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Historicocobranca;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Wolverine
 */

@Named
@ViewScoped
public class ContasReceberMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Contasreceber> listaContas;
	private Contasreceber conta;
	private Float contasVencidas;
	private Float contasVencer;
	private Float contasVencendo;
	private Vendas vendas;
	private Date dataAnterior;
	private int linha;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidadenegocio;
	private int idVenda;
	private Date dataInicial;
	private Date dataFinal;
	private String status;
	private String nomeCliente;
	private List<BolinhasBean> listaBolinhas;
	private String situacao;
	private String nDocumento;
	private String sql = "";
	private String tipoDocumento;
	private String statusCobranca;
	private String funcaoBotaoBoleto;
	private boolean comboBoleto = false;
	private boolean habilitarComboBoleto = true;
	private boolean btnEnviarBoleto = false;
	private boolean btnGerarSegundaVia = false;
	private boolean btnGerarBoleto = false;
	private boolean btnReenviarRemessa = false;
	private Unidadenegocio unidadeMatriz;
	private boolean selecionarTodos;
	private List<RetornoBean> listaRetorno;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			conta = (Contasreceber) session.getAttribute("contarecebe");
			sql = (String) session.getAttribute("sqlContaReceber");
			gerarListaUnidadeNegocio();
			session.removeAttribute("contarecebe");
			if (sql == null || sql.length() < 5) {
				sqlInicial();
			}
			carregarContasReceber();
			conta = new Contasreceber();
			tipoDocumento = "Selecione";
			gerarBolinhasBean();
			selecionarTodos = false;
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Contasreceber> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}

	public Contasreceber getConta() {
		return conta;
	}

	public void setConta(Contasreceber conta) {
		this.conta = conta;
	}

	public Float getContasVencidas() {
		return contasVencidas;
	}

	public void setContasVencidas(Float contasVencidas) {
		this.contasVencidas = contasVencidas;
	}

	public Float getContasVencer() {
		return contasVencer;
	}

	public void setContasVencer(Float contasVencer) {
		this.contasVencer = contasVencer;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getStatusCobranca() {
		return statusCobranca;
	}

	public void setStatusCobranca(String statusCobranca) {
		this.statusCobranca = statusCobranca;
	}

	public Float getContasVencendo() {
		return contasVencendo;
	}

	public void setContasVencendo(Float contasVencendo) {
		this.contasVencendo = contasVencendo;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getDataAnterior() {
		return dataAnterior;
	}

	public void setDataAnterior(Date dataAnterior) {
		this.dataAnterior = dataAnterior;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public List<Unidadenegocio> getListUnidade() {
		return listaUnidade;
	}

	public void setListUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BolinhasBean> getListaBolinhas() {
		return listaBolinhas;
	}

	public void setListaBolinhas(List<BolinhasBean> listaBolinhas) {
		this.listaBolinhas = listaBolinhas;
	}

	public String getnDocumento() {
		return nDocumento;
	}

	public void setnDocumento(String nDocumento) {
		this.nDocumento = nDocumento;
	}

	public boolean isComboBoleto() {
		return comboBoleto;
	}

	public void setComboBoleto(boolean comboBoleto) {
		this.comboBoleto = comboBoleto;
	}

	public boolean isHabilitarComboBoleto() {
		return habilitarComboBoleto;
	}

	public void setHabilitarComboBoleto(boolean habilitarComboBoleto) {
		this.habilitarComboBoleto = habilitarComboBoleto;
	}

	public boolean isBtnEnviarBoleto() {
		return btnEnviarBoleto;
	}

	public void setBtnEnviarBoleto(boolean btnEnviarBoleto) {
		this.btnEnviarBoleto = btnEnviarBoleto;
	}

	public boolean isBtnGerarSegundaVia() {
		return btnGerarSegundaVia;
	}

	public void setBtnGerarSegundaVia(boolean btnGerarSegundaVia) {
		this.btnGerarSegundaVia = btnGerarSegundaVia;
	}

	public boolean isBtnGerarBoleto() {
		return btnGerarBoleto;
	}

	public void setBtnGerarBoleto(boolean btnGerarBoleto) {
		this.btnGerarBoleto = btnGerarBoleto;
	}

	public String getFuncaoBotaoBoleto() {
		return funcaoBotaoBoleto;
	}

	public void setFuncaoBotaoBoleto(String funcaoBotaoBoleto) {
		this.funcaoBotaoBoleto = funcaoBotaoBoleto;
	}

	public boolean isSelecionarTodos() {
		return selecionarTodos;
	}

	public void setSelecionarTodos(boolean selecionarTodos) {
		this.selecionarTodos = selecionarTodos;
	}

	public boolean isBtnReenviarRemessa() {
		return btnReenviarRemessa;
	}

	public void setBtnReenviarRemessa(boolean btnReenviarRemessa) {
		this.btnReenviarRemessa = btnReenviarRemessa;
	}

	public List<RetornoBean> getListaRetorno() {
		return listaRetorno;
	}

	public void setListaRetorno(List<RetornoBean> listaRetorno) {
		this.listaRetorno = listaRetorno;
	} 

	public void carregarContasReceber() {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		listaContas = contasReceberFacade.listar(sql);
		if (listaContas == null) {
			listaContas = new ArrayList<Contasreceber>();
		} else {
			for (int i = 0; i < listaContas.size(); i++) {
				listaContas.get(i).setBolinhas(verStatus(listaContas.get(i)));
				retornarStatusConta(listaContas.get(i));
			}
		}
	}

	public String iniciarCobranca(Contasreceber conta) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("venda", conta.getVendas());
		session.setAttribute("sqlContaReceber", sql);
		return "cobranca";
	}

	public String recebimento() {
		List<Contasreceber> lista = new ArrayList<Contasreceber>();
		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).isSelecionado()) {
				lista.add(listaContas.get(i));
			}
		}
		if (lista.size() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("listaContas", lista);
			session.setAttribute("novoCartao", "Nao");
			return "recebimento";
		}
		return " ";
	}

	public void calcularTotais() {
		contasVencendo = 0.0f;
		contasVencer = 0.0f;
		contasVencidas = 0.0f;
		Date data = new Date();
		String sData = Formatacao.ConvercaoDataPadrao(data);
		data = Formatacao.ConvercaoStringDataBrasil(sData);
		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).getDatavencimento().equals(data)) {
				contasVencendo = contasVencendo + listaContas.get(i).getValorparcela();
			} else if (listaContas.get(i).getDatavencimento().before(data)) {
				contasVencidas = contasVencidas + listaContas.get(i).getValorparcela();
			} else if (listaContas.get(i).getDatavencimento().after(new Date())) {
				contasVencer = contasVencer + listaContas.get(i).getValorparcela();
			}
		}
	}

	public String visualizar(Vendas vendas) {
		this.vendas = vendas;
		return null;
	}

	public String voltar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "consContasReceber";
	}

	public String voltarRecimento() {
		return "consContasReceber";
	}

	public String editar(Contasreceber conta) {
		if (this.conta != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("contareceber", conta);
			RequestContext.getCurrentInstance().openDialog("adicionarContasReceber");
		}
		return "";
	}

	public String cobranca() {
		return "cobranca";
	}

	public String dialogBoletos() {
		List<Contasreceber> lista = new ArrayList<>();
		for (int i = 0; i < listaContas.size(); i++) {
			if ((listaContas.get(i).getTipodocumento().equalsIgnoreCase("Boleto")
					&& listaContas.get(i).isSelecionado())) {
				if ((listaContas.get(i).getDataalterada() && !listaContas.get(i).getBoletoenviado())
						|| (listaContas.get(i).getBoletocancelado() && !listaContas.get(i).getBoletoenviado())
						|| (!listaContas.get(i).getBoletoenviado())) {
					lista.add(listaContas.get(i));
				}
			}
		}
		if (lista != null && lista.size() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("listaContas", lista);
			RequestContext.getCurrentInstance().openDialog("boletos");
		} else {
			Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
		}
		return "";
	}

	public String retornarStatusConta(Contasreceber conta) {
		String retorno;
		Date data = Formatacao.formatarDataAgora();
		boolean dataVenvida = conta.getDatavencimento().before(data);
		if (conta.getSituacao().equalsIgnoreCase("cc")) {
			retorno = "../../resources/img/bolaAzul.png";
		} else if (conta.getDatapagamento() != null) {
			retorno = "../../resources/img/bolaVerde.png";
		} else if (dataVenvida) {
			retorno = "../../resources/img/bolaVermelha.png";
		} else {
			return "../../resources/img/bolaAmarela.png";
		}
		return retorno;
	}

	public String retornarTitleStatusConta(Contasreceber conta) {
		String retorno;
		Date data = Formatacao.formatarDataAgora();
		boolean dataVenvida = conta.getDatavencimento().before(data);
		if (conta.getSituacao().equalsIgnoreCase("cc")) {
			retorno = "Cancelado";
		} else if (conta.getDatapagamento() != null) {
			retorno = "Já recebido";
		} else if (dataVenvida) {
			retorno = "Em atraso";
		} else {
			return "Dentro do prazo";
		}
		return retorno;
	}

	public String retornarTipoDocumento(Contasreceber conta) {
		String retorno;
		if (conta.getTipodocumento().equalsIgnoreCase("Dinheiro")) {
			retorno = "../../resources/img/dinheiros.png";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Boleto")) {
			retorno = "../../resources/img/boleto.png";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cartão de Crédito") 
				|| conta.getTipodocumento().equalsIgnoreCase("Credito")
				|| conta.getTipodocumento().equalsIgnoreCase("Cartao Credito")) {
			retorno = "../../resources/img/credito.png";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cartão de Crédito Autorizado")) {
			retorno = "../../resources/img/creditoautorizado.png";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cartão de Débito")
				|| conta.getTipodocumento().equalsIgnoreCase("Cartão débito")) {
			retorno = "../../resources/img/debito.png";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cheque")) {
			retorno = "../../resources/img/holerite.png";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Depósito")
				|| conta.getTipodocumento().equalsIgnoreCase("Déposito")
				|| conta.getTipodocumento().equalsIgnoreCase("Deposito")) {
			retorno = "../../resources/img/deposito.png";
		} else {
			retorno = "../../resources/img/financiamento.png";
		}
		return retorno;
	}

	public String titleTipoDocumento(Contasreceber conta) {
		String retorno;
		if (conta.getTipodocumento().equalsIgnoreCase("Dinheiro")) {
			retorno = "Dinheiro";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Boleto")) {
			retorno = "Boleto";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cartão de Crédito") 
				|| conta.getTipodocumento().equalsIgnoreCase("Credito")
				|| conta.getTipodocumento().equalsIgnoreCase("Cartao Credito")) {
			retorno = "Cartão de Crédito";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cartão de Crédito Autorizado")) {
			retorno = "Cartão de Crédito Autorizado";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cartão de Débito")
				|| conta.getTipodocumento().equalsIgnoreCase("Cartão débito")) {
			retorno = "Cartão de Débito";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Cheque")) {
			retorno = "Cheque";
		} else if (conta.getTipodocumento().equalsIgnoreCase("Depósito")
				|| conta.getTipodocumento().equalsIgnoreCase("Déposito")
				|| conta.getTipodocumento().equalsIgnoreCase("Deposito")) {
			retorno = "Deposito";
		} else {
			retorno = "Financiamento Banco";
		}
		return retorno;
	}

	public void uploadRetorno(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Sucesso! ", event.getFile().getFileName() + " carregado");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		UploadedFile uFile = event.getFile();
		lerRetorno(uFile);
	}

	public String lerRetorno(UploadedFile retorno) {
		try {
			LerRetornoItauBean lerRetornoItauBean = new LerRetornoItauBean(
					Formatacao.converterUploadedFileToFile(retorno), retorno.getFileName(), usuarioLogadoMB.getUsuario());
			listaRetorno = lerRetornoItauBean.getListaRetorno();
			if(listaRetorno==null){
				listaRetorno = new ArrayList<>();
			} 
		} catch (Exception ex) {
			Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	

	public String confirmaAlterarDataVencimento() {
		if (conta.getBoletoenviado()) {
			conta.setDataalterada(Boolean.TRUE);
			conta.setBoletoenviado(Boolean.FALSE);
		}
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		conta = contasReceberFacade.salvar(conta);
		EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Alteração data de Vencimento", conta, usuarioLogadoMB.getUsuario());
		listaContas.remove(linha);
		listaContas.add(linha, conta);
		String sql = "Select c from Cobranca c where c.vendas.idvendas=" + conta.getVendas().getIdvendas();
		CobrancaFacade cobrancaFacade = new CobrancaFacade();
		Cobranca cobranca = cobrancaFacade.consultar(sql);
		if (cobranca == null) {
			cobranca = new Cobranca();
			cobranca.setVendas(conta.getVendas());
			cobranca = cobrancaFacade.salvar(cobranca);
		}
		Historicocobranca historicocobranca = new Historicocobranca();
		historicocobranca.setAssunto("Data de vencimento alterada de " + Formatacao.ConvercaoDataPadrao(dataAnterior)
				+ " por " + usuarioLogadoMB.getUsuario().getNome());
		historicocobranca.setCobranca(cobranca);
		historicocobranca.setContato("Sistema");
		historicocobranca.setData(new Date());
		historicocobranca.setUsuario(usuarioLogadoMB.getUsuario());
		HistoricoCobrancaFacade historicocobrancaFacade = new HistoricoCobrancaFacade();
		historicocobrancaFacade.salvar(historicocobranca);
		FacesMessage msg = new FacesMessage("Sucesso! ", "Data de Vencimento Alterada.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		for (int i = 0; i < listaContas.size(); i++) {
			listaContas.get(i).setBolinhas(verStatus(listaContas.get(i)));
		}
		return "";
	}

	public String openDialogAlterarData(String linha) {
		this.linha = Integer.parseInt(linha);
		conta = listaContas.get(this.linha);
		dataAnterior = conta.getDatavencimento();
		return null;
	}

	public String adicionarContasReceber() {
		RequestContext.getCurrentInstance().openDialog("adicionarContasReceber");
		return "";
	}

	public void retornoDialogoNovo(SelectEvent event) {
		Contasreceber novaConta = (Contasreceber) event.getObject();
		listaContas.add(novaConta);
	}

	public void retornoDialogoEditar(SelectEvent event) {
		carregarContasReceber();
	}

	public String relatoriosContasReceber() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("filtrarcontasreceber", options, null);
		return "";
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	private void sqlInicial() {
		sql = "Select c from Contasreceber c where c.valorpago=0 and c.datapagamento is NULL and ";
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==3) {
			sql = sql + " c.datavencimento<='" + Formatacao.ConvercaoDataSql(new Date()) + "'"; 
		}else {
			sql = sql + "c.datavencimento>='" + Formatacao.SubtarirDatas(new Date(), 15, "YYYY/MM/dd") +
					"' and c.vendas.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql  + " and c.situacao<>'cc' order by c.datavencimento, c.vendas.cliente.nome";
	}

	public String limparPesquisa() {
		sqlInicial();
		carregarContasReceber();
		return "";
	}

	public String executarPesquisa() {
		gerarPesquisa();
		carregarContasReceber();
		return "";
	}

	public void gerarPesquisa() {
		if (funcaoBotaoBoleto ==null){
			funcaoBotaoBoleto="";
		}
		btnEnviarBoleto = false;
		btnGerarSegundaVia = false;
		btnGerarBoleto = false;
		btnReenviarRemessa = false;
		if (nomeCliente == null) {
			nomeCliente = "";
		}
		sql = "Select c From Contasreceber c where c.vendas.cliente.nome like '%" + nomeCliente + "%' ";
		if (unidadenegocio != null) {
			sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (funcaoBotaoBoleto.isEmpty()){
			if (dataInicial != null && dataFinal != null) {
				sql = sql + " and c.datavencimento>='" + Formatacao.ConvercaoDataSql(dataInicial)
						+ "' and c.datavencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			}
		}
		if (idVenda > 0) {
			sql = sql + " and c.vendas.idvendas=" + idVenda;
		}
		if ((nDocumento != null) && (nDocumento.length() > 0)) {
			sql = sql + " and c.numerodocumento like '%" + nDocumento + "%' ";
		}
		if (status.equalsIgnoreCase("vencida")) {
			sql = sql + " and c.situacao<>'cc' and c.datavencimento<='" + Formatacao.ConvercaoDataSql(new Date())
					+ "' and c.valorpago=0";
		} else if (status.equalsIgnoreCase("vencer")) {
			sql = sql + " and c.situacao<>'cc' and c.datavencimento>'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
		} else if (status.equalsIgnoreCase("recebidas")) {
			sql = sql + " and c.situacao<>'cc' and c.valorpago>0";
		} else if (status.equalsIgnoreCase("canceladas")) {
			sql = sql + " and c.situacao='cc'";
		} else {
			sql = sql + " and c.situacao<>'cc'";
		}

		if (!situacao.equalsIgnoreCase("0")) {
			sql = sql + " and c.situacao='" + situacao + "'";
		}
		if (!statusCobranca.equalsIgnoreCase("0")) {
			sql = sql + " and c.vendas.statuscobranca='" + statusCobranca + "'";
		}
		if (!tipoDocumento.equalsIgnoreCase("Selecione")) {
			sql = sql + " and c.tipodocumento='" + tipoDocumento + "'";
			if (tipoDocumento.equalsIgnoreCase("Boleto")) {
				if (funcaoBotaoBoleto == null || funcaoBotaoBoleto.equalsIgnoreCase("Selecione")) {
					Mensagem.lancarMensagemInfo("Atenção", "Selecione a função desejada.");
				} else {
					if (funcaoBotaoBoleto.equalsIgnoreCase("Gerar")) {
						sql = sql
								+ " and c.nossonumero is null and c.valorpago=0 and c.boletoenviado=false and c.datavencimento>='"
								+ Formatacao.ConvercaoDataSql(new Date()) + "'";
						btnGerarBoleto = true;
					} else if (funcaoBotaoBoleto.equalsIgnoreCase("2º Via")) {
						sql = sql + " and c.boletoenviado=true and c.valorpago=0 and c.datavencimento>='"
								+ Formatacao.ConvercaoDataSql(new Date()) + "'";
						btnGerarSegundaVia = true;
					} else if (funcaoBotaoBoleto.equalsIgnoreCase("Enviar")) {
						sql = sql
								+ " and c.nossonumero<>'0' and c.boletoenviado=false and c.valorpago=0 and c.datapagamento is null and c.datavencimento>='"
								+ Formatacao.ConvercaoDataSql(new Date()) + "' and c.boletogerado='SIM' ";
						btnEnviarBoleto = true;
					} else if (funcaoBotaoBoleto.equalsIgnoreCase("Reenviar")) {
						sql = sql
								+ " and c.nossonumero<>'0' and c.valorpago=0 and c.datapagamento is null and c.situacao<>'cc'";
						if(dataInicial!=null && dataFinal!=null){
							sql = sql+ " and c.dataenvio>='"+ Formatacao.ConvercaoDataSql(dataInicial) 
									 + "' and c.dataenvio<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'  and c.boletogerado='SIM' and c.boletoenviado=true";
						}
						btnReenviarRemessa = true;
					}
				}
			}
		}
		sql = sql + " order by c.datavencimento, c.vendas.cliente.nome";
	}

	public BolinhasBean verStatus(Contasreceber contasreceber) {
		BolinhasBean bolinhasBean = new BolinhasBean();
		if (contasreceber.getSituacao().equalsIgnoreCase("vd")) {
			bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
			bolinhasBean.setCor("Verde");
			return bolinhasBean;
		} else {
			if (contasreceber.getSituacao().equalsIgnoreCase("vm")) {
				bolinhasBean.setCaminho("../../resources/img/bolaVermelha.png");
				bolinhasBean.setCor("Vermelha");
				return bolinhasBean;
			} else {
				if (contasreceber.getSituacao().equalsIgnoreCase("am")) {
					bolinhasBean.setCaminho("../../resources/img/bolaAmarela.png");
					bolinhasBean.setCor("Amarela");
					return bolinhasBean;
				}
			}
		}
		bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
		bolinhasBean.setCor("Verde");
		return bolinhasBean;
	}

	public String cancelar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		List<Contasreceber> lista = new ArrayList<Contasreceber>();
		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).isSelecionado()) {
				lista.add(listaContas.get(i));
			}
		}
		session.setAttribute("contareceber", lista);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cancelarContasReceber", options, null);
		return "";
	}

	public void gerarBolinhasBean() {
		listaBolinhas = new ArrayList<BolinhasBean>();
		BolinhasBean bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Verde");
		bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
		listaBolinhas.add(bolinhasBean);
		bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Amarela");
		bolinhasBean.setCaminho("../../resources/img/bolaAmarela.png");
		listaBolinhas.add(bolinhasBean);
		bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Vermelha");
		bolinhasBean.setCaminho("../../resources/img/bolaVermelha.png");
		listaBolinhas.add(bolinhasBean);
	}

	public void alterarStatus(String linha) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("linhaSituacao");
		if (linha != null) {
			BolinhasBean bolinhas = listaContas.get(Integer.valueOf(linha)).getBolinhas();
			if (bolinhas.getCor().equalsIgnoreCase("Vermelha")) {
				listaContas.get(Integer.valueOf(linha)).setSituacao("vm");
			} else if (bolinhas.getCor().equalsIgnoreCase("Verde")) {
				listaContas.get(Integer.valueOf(linha)).setSituacao("vd");
			} else {
				listaContas.get(Integer.valueOf(linha)).setSituacao("am");
			}
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			contasReceberFacade.salvar(listaContas.get(Integer.valueOf(linha)));
		}
	}

	public String enderecoCliente(Cliente cliente) {
		if (cliente != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("cliente", cliente);
			RequestContext.getCurrentInstance().openDialog("enderecoCliente");
		} else {
			Mensagem.lancarMensagemInfo("Informação", "Cliente não localizado");
		}
		return "";
	}

	public String recebimentoCartão(String tipo) {
		List<Contasreceber> lista = new ArrayList<Contasreceber>();
		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).isSelecionado()) {
				if ((listaContas.get(i).getTipodocumento().equalsIgnoreCase("Cartão de Crédito"))
						|| (listaContas.get(i).getTipodocumento().equalsIgnoreCase("Cartão de Crédito Autorizado"))) {
					lista.add(listaContas.get(i));
				}
			}
		}
		if (lista.size() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("listaContas", lista);
			session.setAttribute("novoCartao", tipo);
			return "recebimentoCartao";
		}
		return " ";
	}

	public String produtoVendas(Vendas venda) {
		if ((venda.getOrcamento() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 570);
			RequestContext.getCurrentInstance().openDialog("produtoVendas", options, null);
		} else {
			Mensagem.lancarMensagemInfo("Venda não Possui produtos! ", " ");
		}
		return "";
	}

	public String imagemCobranca(Vendas venda) {
		if (venda.getStatuscobranca().equalsIgnoreCase("u")) {
			return "../../resources/img/cobrancaVermelha.png";
		} else if (venda.getStatuscobranca().equalsIgnoreCase("a")) {
			return "../../resources/img/cobrancaAmarela.png";
		} else if (venda.getStatuscobranca().equalsIgnoreCase("p")) {
			return "../../resources/img/cobrancaVerde.png";
		} else
			return "";
	}

	public String situacaoTipoDoc() {
		List<Contasreceber> lista = new ArrayList<Contasreceber>();
		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).isSelecionado()) {
				lista.add(listaContas.get(i));
			}
		}
		if (lista.size() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaContas", lista);
			session.setAttribute("sqlContaReceber", sql);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 250);
			RequestContext.getCurrentInstance().openDialog("editarSituacaoTipoDoc", options, null);
		} else {
			Mensagem.lancarMensagemInfo("Atenção! ", "Você precisa selecionar contas a receber.");
		}
		return "";
	}

	public void retornoSituacaoTipo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		sql = (String) session.getAttribute("sqlContaReceber");
		session.removeAttribute("sqlContaReceber");
		carregarContasReceber();
	}

	public String gerarBoleto() {
		List<Contasreceber> listaSelecionada = new ArrayList<>();
		for (int i = 0; i < listaContas.size(); i++) {
			if ((listaContas.get(i).isSelecionado())
					&& (listaContas.get(i).getTipodocumento().equalsIgnoreCase("Boleto"))) {
				if ((listaContas.get(i).getDataalterada()) && (!listaContas.get(i).getBoletoenviado())
						|| (listaContas.get(i).getBoletocancelado()) && (!listaContas.get(i).getBoletoenviado())
						|| (!listaContas.get(i).getBoletoenviado())) {
					listaSelecionada.add(listaContas.get(i));
				}
			}
		}
		if (listaSelecionada != null && listaSelecionada.size() > 0) {
			List<Boleto> listaBoletos = new ArrayList<Boleto>();
			if (listaSelecionada != null) {
				for (int i = 0; i < listaSelecionada.size(); i++) {
					if (listaSelecionada.get(i).getTipodocumento().equalsIgnoreCase("Boleto")) {
						listaBoletos.add(gerarClasseBoleto(listaSelecionada.get(i)));
					}

				}
			}
			if (listaBoletos.size() > 0) {
				DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
				dadosBoletoBean.gerarPDFS(listaBoletos, "0");
			}
		} else {
			Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Selecione uma conta a receber.");
		}
		return "";
	}

	public void gerarBoletoSegundaVia() {
		List<Contasreceber> listaSelecionada = new ArrayList<>();
		for (int i = 0; i < listaContas.size(); i++) {
			if ((listaContas.get(i).isSelecionado())
					&& (listaContas.get(i).getTipodocumento().equalsIgnoreCase("Boleto"))) {
				listaSelecionada.add(listaContas.get(i));
			}
		}
		if (listaSelecionada != null && listaSelecionada.size() > 0) {
			List<Boleto> listaBoletos = new ArrayList<Boleto>();
			for (int i = 0; i < listaSelecionada.size(); i++) {
				listaBoletos.add(gerarClasseBoletoSegundaVia(listaSelecionada.get(i)));
			}
			if (listaBoletos.size() > 0) {
				DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
				dadosBoletoBean.gerarPDFS(listaBoletos, "0");
			}
		} else {
			Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Selecione uma conta a receber.");
		}
	}

	public Boleto gerarClasseBoletoSegundaVia(Contasreceber conta) {
		DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
		dadosBoletoBean.setAgencias(conta.getVendas().getUnidadenegocio().getBanco().getAgencia());
		dadosBoletoBean.setCarteiras(conta.getVendas().getUnidadenegocio().getBanco().getCarteira());
		dadosBoletoBean.setCodigoVenda(conta.getVendas().getIdvendas());
		dadosBoletoBean.setDataDocumento(conta.getDataEmissao());
		dadosBoletoBean.setDigitoAgencias(conta.getVendas().getUnidadenegocio().getBanco().getDigioagencia());
		dadosBoletoBean.setDigitoContas(conta.getVendas().getUnidadenegocio().getBanco().getDigitoconta());
		dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
		if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()<=2){
			dadosBoletoBean.setNomeCedente(conta.getVendas().getUnidadenegocio().getRazaoSocial());
			dadosBoletoBean.setCnpjCedente(conta.getVendas().getUnidadenegocio().getCnpj());
		}else {
			if (unidadeMatriz==null){
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
				unidadeMatriz = unidadeNegocioFacade.consultar(6);
			}
			dadosBoletoBean.setCnpjCedente(unidadeMatriz.getCnpj());
			dadosBoletoBean.setNomeCedente(unidadeMatriz.getRazaoSocial());
		}
		dadosBoletoBean.setNomeSacado(conta.getVendas().getCliente().getNome());
		dadosBoletoBean.setNumeroContas(conta.getVendas().getUnidadenegocio().getBanco().getConta());
		dadosBoletoBean.setNumeroDocumentos(conta.getNossonumero());
		dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorparcela()));
		dadosBoletoBean.setValorJuros(calcularMultaJuros(conta.getValorparcela(),
				conta.getVendas().getUnidadenegocio().getBanco().getValorjuros()));
		dadosBoletoBean.setValorMulta(calcularMultaJuros(conta.getValorparcela(),
				conta.getVendas().getUnidadenegocio().getBanco().getValormulta()));
		dadosBoletoBean.setNossoNumeros(conta.getNossonumero());
		dadosBoletoBean.setEnderecoSacado(new Endereco());
		dadosBoletoBean.getEnderecoSacado().setBairro(conta.getVendas().getCliente().getBairro());
		dadosBoletoBean.getEnderecoSacado().setCep(conta.getVendas().getCliente().getCep());
		dadosBoletoBean.getEnderecoSacado().setComplemento(conta.getVendas().getCliente().getComplemento());
		dadosBoletoBean.getEnderecoSacado().setLocalidade(conta.getVendas().getCliente().getCidade());
		dadosBoletoBean.getEnderecoSacado().setLogradouro(conta.getVendas().getCliente().getTipologradouro() + " "
				+ conta.getVendas().getCliente().getLogradouro());
		dadosBoletoBean.getEnderecoSacado().setNumero(conta.getVendas().getCliente().getNumero());
		dadosBoletoBean.getEnderecoSacado()
				.setUF(UnidadeFederativa.valueOfSigla(conta.getVendas().getCliente().getEstado()));
		dadosBoletoBean.setValorJuros(calcularMultaJuros(conta.getValorparcela(),
				conta.getVendas().getUnidadenegocio().getBanco().getValorjuros()));
		dadosBoletoBean.setValorMulta(calcularMultaJuros(conta.getValorparcela(),
				conta.getVendas().getUnidadenegocio().getBanco().getValormulta()));

		dadosBoletoBean.criarBoleto();
		return dadosBoletoBean.getBoleto();
	}

	public String calcularMultaJuros(float valor, float percentual) {
		Float calculo = valor * (percentual / 100);
		String scalculo = Formatacao.formatarFloatString(calculo);
		return scalculo;
	}
	

	public Boleto gerarClasseBoleto(Contasreceber conta) {
		DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
		dadosBoletoBean.setAgencias(conta.getVendas().getUnidadenegocio().getBanco().getAgencia());
		dadosBoletoBean.setCarteiras(conta.getVendas().getUnidadenegocio().getBanco().getCarteira());
		dadosBoletoBean.setCodigoVenda(conta.getVendas().getIdvendas());
		dadosBoletoBean.setDataDocumento(new Date());
		dadosBoletoBean.setDigitoAgencias(conta.getVendas().getUnidadenegocio().getBanco().getDigioagencia());
		dadosBoletoBean.setDigitoContas(conta.getVendas().getUnidadenegocio().getBanco().getDigitoconta());
		dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
		if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()<=2){
			dadosBoletoBean.setNomeCedente(conta.getVendas().getUnidadenegocio().getRazaoSocial());
			dadosBoletoBean.setCnpjCedente(conta.getVendas().getUnidadenegocio().getCnpj());
		}else {
			if (unidadeMatriz==null){
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
				unidadeMatriz = unidadeNegocioFacade.consultar(6);
			}
			dadosBoletoBean.setCnpjCedente(unidadeMatriz.getCnpj());
			dadosBoletoBean.setNomeCedente(unidadeMatriz.getRazaoSocial());
		}
		dadosBoletoBean.setNomeSacado(conta.getVendas().getCliente().getNome());
		dadosBoletoBean.setNumeroContas(conta.getVendas().getUnidadenegocio().getBanco().getConta());
		dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(conta.getNumerodocumento(),
				String.valueOf(conta.getParcelaboleto()), conta.getControlenossonumero()));
		dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorparcela()));
		dadosBoletoBean.setValorJuros(calcularMultaJuros(conta.getValorparcela(),
				conta.getVendas().getUnidadenegocio().getBanco().getValorjuros()));
		dadosBoletoBean.setValorMulta(calcularMultaJuros(conta.getValorparcela(),
				conta.getVendas().getUnidadenegocio().getBanco().getValormulta()));
		dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
		dadosBoletoBean.setEnderecoSacado(new Endereco());
		dadosBoletoBean.getEnderecoSacado().setBairro(conta.getVendas().getCliente().getBairro());
		dadosBoletoBean.getEnderecoSacado().setCep(conta.getVendas().getCliente().getCep());
		dadosBoletoBean.getEnderecoSacado().setComplemento(conta.getVendas().getCliente().getComplemento());
		dadosBoletoBean.getEnderecoSacado().setLocalidade(conta.getVendas().getCliente().getCidade());
		dadosBoletoBean.getEnderecoSacado().setLogradouro(conta.getVendas().getCliente().getTipologradouro() + " "
				+ conta.getVendas().getCliente().getLogradouro());
		dadosBoletoBean.getEnderecoSacado().setNumero(conta.getVendas().getCliente().getNumero());
		if(conta.getVendas().getCliente().getEstado()!=null){
			dadosBoletoBean.getEnderecoSacado()
					.setUF(UnidadeFederativa.valueOfSigla(conta.getVendas().getCliente().getEstado()));
		}else{
			dadosBoletoBean.getEnderecoSacado()
			.setUF(UnidadeFederativa.valueOfSigla(conta.getVendas().getUnidadenegocio().getEstado()));
		}
		dadosBoletoBean.criarBoleto();
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
		conta.setDataEmissao(new Date());
		conta.setSituacao("am");
		contasReceberFacade.salvar(conta);
		return dadosBoletoBean.getBoleto();
	}

	public void dialogListarBoletosNaoEnviados() {
		List<Contasreceber> lista = new ArrayList<>();
		for (int i = 0; i < listaContas.size(); i++) {
			if ((listaContas.get(i).getTipodocumento().equalsIgnoreCase("Boleto"))) {
				if ((listaContas.get(i).getDataalterada() && !listaContas.get(i).getBoletoenviado())
						|| (listaContas.get(i).getBoletocancelado() && !listaContas.get(i).getBoletoenviado())
						|| (!listaContas.get(i).getBoletoenviado())) {
					lista.add(listaContas.get(i));
				}
			}
		}
		if (lista != null && lista.size() > 0) {
			listaContas = lista;
		} else {
			Mensagem.lancarMensagemInfo("Atenção!", "Todos os boletos enviados.");
		}
	}

	public void verificarComboBoleto() {
		if (tipoDocumento != null && tipoDocumento.equalsIgnoreCase("Boleto")) {
			comboBoleto = true;
			habilitarComboBoleto = false;
		} else {
			habilitarComboBoleto = true;
			comboBoleto = false;
		}
	}
	
	public void selecionarTodos(){
		if (listaContas!=null){
			boolean nossoNumeroNull=false;
			for(int i=0;i<listaContas.size();i++){
				if ((listaContas.get(0).getTipodocumento().equalsIgnoreCase("Boleto")) && (listaContas.get(i).getNossonumero()==null)){
					listaContas.get(i).setSelecionado(false);
					nossoNumeroNull = true;
				}else {
					listaContas.get(i).setSelecionado(selecionarTodos);
				}
			}
			if (nossoNumeroNull){
				Mensagem.lancarMensagemInfo("ERRO CONTAS", "Existem boletos com nosso numerro NULO");
			}
		}
	}
	
	public String dialogReenviarBoletos() {
		List<Contasreceber> lista = new ArrayList<>();
		for (int i = 0; i < listaContas.size(); i++) {
			if ((listaContas.get(i).getTipodocumento().equalsIgnoreCase("Boleto")
					&& listaContas.get(i).isSelecionado())) { 
				lista.add(listaContas.get(i)); 
			}
		}
		if (lista != null && lista.size() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("listaContas", lista);
			RequestContext.getCurrentInstance().openDialog("boletos");
		} else {
			Mensagem.lancarMensagemInfo("Atenção!", "Selecione uma conta a receber.");
		}
		return "";
	}
	
	public String imprimirListaBoletosItau(){
		if(listaRetorno!=null && listaRetorno.size()>0){
			String caminhoRelatorio = "/reports/financeiro/reportboletositau.jasper";  
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
	        BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}  
	        parameters.put("logo", logo); 
	        parameters.put("titulo", "Títulos Baixados");
	        parameters.put("tituloColunaData", "Data Pagam.");
	        JRDataSource jrds = new JRBeanCollectionDataSource(listaRetorno);
	        GerarRelatorio gerarRelatorio = new GerarRelatorio();
	        try {
	            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "BoletosItau.pdf");
	        } catch (JRException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IOException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        listaRetorno = null; 
		} else {
			FacesMessage msg = new FacesMessage("Lista de retorno vazia. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Lista de retorno vazia.");
		}
        return "";
	} 
	
	public boolean btnImprimirRetorno(){
		if(listaRetorno!=null && listaRetorno.size()>0){
			return true;
		}else return false;
	}
}
