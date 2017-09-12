package br.com.travelmate.managerBean.worktravel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.AcessoUnidadeDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class WorkTravelMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private AcessoUnidadeDao acessoUnidadeDao;
	private List<Worktravel> listaWork;
	private String numeroFichas;
	private String obsTM;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean habilitarUnidade = true;
	private String motivoCancelamento;
	private Worktravel work;
	private int idVenda;
	private String linkIdVenda;
	private String linkIdVendaFranquias;
	private String voltar;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			carregarListaVendasWork();
			listaUnidadeNegocio = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
				linkIdVenda = "true";
				linkIdVendaFranquias = "false";
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				linkIdVenda = "false";
				linkIdVendaFranquias = "true";
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Worktravel> getListaWork() {
		return listaWork;
	}

	public void setListaWork(List<Worktravel> listaWork) {
		this.listaWork = listaWork;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNome() {
		return nome;
	}

	public String getLinkIdVenda() {
		return linkIdVenda;
	}

	public void setLinkIdVenda(String linkIdVenda) {
		this.linkIdVenda = linkIdVenda;
	}

	public String getLinkIdVendaFranquias() {
		return linkIdVendaFranquias;
	}

	public void setLinkIdVendaFranquias(String linkIdVendaFranquias) {
		this.linkIdVendaFranquias = linkIdVendaFranquias;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Worktravel getWork() {
		return work;
	}

	public void setWork(Worktravel work) {
		this.work = work;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}
	
	

	public Integer getnFichasFinalizadas() {
		return nFichasFinalizadas;
	}

	public void setnFichasFinalizadas(Integer nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}

	public Integer getnFichasProcesso() {
		return nFichasProcesso;
	}

	public void setnFichasProcesso(Integer nFichasProcesso) {
		this.nFichasProcesso = nFichasProcesso;
	}

	public Integer getnFichasAndamento() {
		return nFichasAndamento;
	}

	public void setnFichasAndamento(Integer nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}

	public Integer getnFichaCancelada() {
		return nFichaCancelada;
	}

	public void setnFichaCancelada(Integer nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	} 
	
	public boolean isExpandirOpcoes() {
		return expandirOpcoes;
	}

	public void setExpandirOpcoes(boolean expandirOpcoes) {
		this.expandirOpcoes = expandirOpcoes;
	}

	public boolean isEsconderFicha() {
		return esconderFicha;
	}

	public void setEsconderFicha(boolean esconderFicha) {
		this.esconderFicha = esconderFicha;
	}

	public String cadastrarFicha() {
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("idlead", idlead);
			return "cadWorkandTravel";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}

	public boolean imagemSituacaoUsuario(Worktravel work) {
		if (work.getVendas().getSituacao().equals("FINALIZADA")) {
			work.setHabilitarImagemGerencial(false);
			work.setHabilitarImagemFranquia(true);
			work.setImagem("../../resources/img/finalizadoFicha.png");
		} else if (work.getVendas().getSituacao().equals("ANDAMENTO")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				work.setHabilitarImagemGerencial(true);
				work.setHabilitarImagemFranquia(false);
			} else {
				work.setHabilitarImagemGerencial(false);
				work.setHabilitarImagemFranquia(true);
			}
			work.setImagem("../../resources/img/amarelaFicha.png");
		} else if (work.getVendas().getSituacao().equals("CANCELADA")) {
			work.setHabilitarImagemGerencial(false);
			work.setHabilitarImagemFranquia(true);
			work.setImagem("../../resources/img/fichaCancelada.png");
		} else if ((work.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (work.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				work.setHabilitarImagemGerencial(true);
				work.setHabilitarImagemFranquia(false);
			} else {
				work.setHabilitarImagemGerencial(false);
				work.setHabilitarImagemFranquia(true);
			}
			work.setImagem("../../resources/img/ficharestricao.png");
		} else {
			work.setHabilitarImagemGerencial(false);
			work.setHabilitarImagemFranquia(true);
			work.setImagem("../../resources/img/processoFicha.png");
		}
		return work.isHabilitarImagemGerencial();
	}

	public String corNome(Worktravel work) {
		if (work.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Worktravel work) {
		if (work.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Worktravel work) {
		if (work.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String obsTMselecionar(Worktravel work) {
		obsTM = work.getVendas().getObstm();
		return obsTM;
	}

	public String gerarRelatorioFicha(Worktravel worktravel) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/worktravel/FichaWorkPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("idvendas", worktravel.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaworktravel-" + worktravel.getIdworkTravel() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(WorkTravelMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(WorkTravelMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(Worktravel worktravel) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (worktravel.getTipo().equalsIgnoreCase("Premium")) {
			caminhoRelatorio = "/reports/worktravel/contratoWorkPremiumPagina01.jasper";
		} else if (worktravel.getTipo().equalsIgnoreCase("France")) {
			caminhoRelatorio = "/reports/worktravel/contratoWorkFrancePagina01.jasper";
		} else {
			caminhoRelatorio = "/reports/worktravel/contratoWorkIndependentPagina01.jasper";
		}
		Map parameters = new HashMap();
		parameters.put("idvendas", worktravel.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"contratoWork-" + worktravel.getIdworkTravel() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(WorkTravelMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(WorkTravelMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public void carregarListaVendasWork() {
		String sql = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		sql = "Select w from Worktravel w where w.vendas.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getWork();
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " and w.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
					+usuarioLogadoMB.getUsuario().getIdusuario());
			if(acessounidade!=null) {
				if(!acessounidade.isEmissaoconsulta()) {
					sql = sql + " and w.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + " and w.vendas.dataVenda>='" + dataConsulta + "' order by w.dataInscricao desc";
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		listaWork = workTravelFacade.lista(sql);
		if (listaWork == null) {
			listaWork = new ArrayList<Worktravel>();
		}
		numeroFichas = "" + String.valueOf(listaWork.size());
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		carregarListaVendasWork();
	}

	public void pesquisarListaWork() {
		String sql = null;
		sql = "Select w from Worktravel w where w.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and w.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and w.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
					+usuarioLogadoMB.getUsuario().getIdusuario());
			if(acessounidade!=null) {
				if(!acessounidade.isEmissaoconsulta()) {
					sql = sql + " and w.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and w.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and w.dataInscricao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and w.dataInscricao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and w.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and w.vendas.situacao='" + situacao + "'";
		}
		sql = sql + " order by w.dataInscricao, w.vendas.cliente.nome";
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		listaWork = workTravelFacade.lista(sql);
		if (listaWork == null) {
			listaWork = new ArrayList<Worktravel>();
		}
		numeroFichas = "" + String.valueOf(listaWork.size());
		gerarQuantidadesFichas();
	}

	public String gerarRelatorioRecibo(Worktravel worktravel) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(worktravel.getVendas().getIdvendas());
		ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
		List<Parcelamentopagamento> listaParcelamento = parcelamentoPagamentoFacade.listar(forma.getIdformaPagamento());
		if (listaParcelamento != null) {
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Dinheiro")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cheque")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Depósito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
			}
		}
		if (valorRecibo > 0.0f) {
			Map parameters = new HashMap();
			parameters.put("idvendas", worktravel.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"reciboPagamento-workAndTravel-" + worktravel.getIdworkTravel() + ".pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(WorkTravelMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(WorkTravelMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Worktravel worktravel) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", worktravel.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"TermoVisto-workAndTravel-" + worktravel.getIdworkTravel() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String editar(Worktravel work) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("work", work);
		session.setAttribute("idlead", work.getVendas().getIdlead());
		return "cadWorkandTravel";
	}

	public void cancelar(Worktravel work) {
		this.work = work;
	}

	public String cancelarVenda(Vendas venda) {
		if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
		} else {
			VendasFacade vendasFacade = new VendasFacade();
			venda.setSituacao("CANCELADA");
			vendasFacade.salvar(venda);
			carregarListaVendasWork();
		}
		return "";
	}

	public void salvarControle() throws SQLException {
		Controlework controlework = new Controlework();
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		controlework = workTravelFacade.consultarControle(work.getVendas().getIdvendas());
		if (controlework == null) {
			ControlerBean controlerBean = new ControlerBean();
			float valorPrevisto = 0.0f;
			if (work.getVendas().getVendascomissao() != null) {
				valorPrevisto = work.getVendas().getVendascomissao().getValorfornecedor();
			}
			controlerBean.salvarControlWork(work.getVendas(), work, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
		}
	}

	public void dialogSalvarControle(Worktravel work) {
		this.work = work;
	}

	public String documentacao(Worktravel work) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", work.getVendas());
		voltar = "consultaWorkandTravel";
		session.setAttribute("voltar", voltar);
		return "consArquivos";
	}

	public String visualizarContasReceber(Vendas venda) {
		if ((venda.getOrcamento() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 620);
			RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public void liberarFicha() {
		if ((work.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (work.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				Vendas venda = work.getVendas();
				venda.setRestricaoparcelamento(false);
				VendasFacade vendasFacade = new VendasFacade();
				venda = vendasFacade.salvar(venda);
				work.setVendas(venda);
				Formapagamento forma = work.getVendas().getFormapagamento();
				if (forma != null) {
					if (forma.getParcelamentopagamentoList() != null) {
						ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
						for (int i = 0; i < forma.getParcelamentopagamentoList().size(); i++) {
							forma.getParcelamentopagamentoList().get(i).setVerificarParcelamento(false);
							forma.getParcelamentopagamentoList().set(i,
									parcelamentoPagamentoFacade.salvar(forma.getParcelamentopagamentoList().get(i)));
						}
					}
				}
			}
		}
	}

	public String boletos(Worktravel worktravel) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + worktravel.getVendas().getIdvendas()
				+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
				+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
		List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
		if (listaContas != null) {
			if (listaContas.size() > 0) {
				GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
				gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(worktravel.getVendas().getIdvendas()));
			} else {
				FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
				relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
			}
		} else {
			FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
		}

		return "";
	}

	public String informacoes(Worktravel worktravel) {
		if (worktravel.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", worktravel.getVendas());
			voltar = "consultaWorkandTravel";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
	
	
	public void gerarQuantidadesFichas(){
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0;
		nFichasProcesso = 0;
		for (int i = 0; i < listaWork.size(); i++) {
			if (listaWork.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
			}else if(listaWork.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
			}else if(listaWork.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
			}else{
				nFichaCancelada = nFichaCancelada + 1;
			}
		}
	}
	
	public void expandirOpcoes(){
		if(expandirOpcoes){
			expandirOpcoes=false;
			esconderFicha=true;
		}else {
			expandirOpcoes=true;
			esconderFicha=false;
		}
	}

	public String retornarImgOpcoes() {
		if (expandirOpcoes) {
			return "../../resources/img/esconderOpcoes.png";
		} else
			return "../../resources/img/expandirOpcoes.png";
	}

	public String retornarTitleOpcoes() {
		if (expandirOpcoes) {
			return "Esconder Opções";
		} else
			return "Expandir Opções";
	}
	
	public String retornarIconeObsTM(Worktravel worktravel){
		if (worktravel.getObstm() != null && worktravel.getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	  
	public String buscarObsTM(Worktravel worktravel) {
		obsTM = worktravel.getObstm();
		return obsTM;
	}
}
