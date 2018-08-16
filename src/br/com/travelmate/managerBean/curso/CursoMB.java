package br.com.travelmate.managerBean.curso;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.LerArquivoTxt;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ConsultaOrcamentoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB; 
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class CursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private List<Cambio> listaCambio;
	private String numeroFichas;
	private List<Curso> listaVendasCurso;
	private String numerosCurso;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String obsTM;
	private Curso curso;
	private boolean habilitarUnidade = true;
	private String linkIdVenda;
	private String linkIdVendaFranquias;
	private String motivoCancelamento;
	private int idVenda;
	private String voltar;
	private List<Fornecedor> listaFornecedor;
	private Fornecedor fornecedor;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private Integer nFichaFinanceiro;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private List<Curso> listaVendasCursoFinalizada;
	private List<Curso> listaVendasCursoAndamento;
	private List<Curso> listaVendasCursoCancelada;
	private List<Curso> listaVendasCursoProcesso;
	private List<Curso> listaVendasCursoFinanceiro;
	private boolean segurocancelamento = false;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendasCursoFinalizada = (List<Curso>) session.getAttribute("listaVendasCursoFinalizada");
		listaVendasCursoAndamento = (List<Curso>) session.getAttribute("listaVendasCursoAndamento");
		listaVendasCursoProcesso = (List<Curso>) session.getAttribute("listaVendasCursoProcesso");
		listaVendasCursoFinanceiro = (List<Curso>) session.getAttribute("listaVendasCursoFinanceiro");
		listaVendasCursoCancelada = (List<Curso>) session.getAttribute("listaVendasCursoCancelada");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("listaVendasCursoFinalizada");
		session.removeAttribute("listaVendasCursoAndamento");
		session.removeAttribute("listaVendasCursoProcesso");
		session.removeAttribute("listaVendasCursoFinanceiro");
		session.removeAttribute("listaVendasCursoCancelada");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Curso")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				carregarListaVendasCursos();
			}else {
				nFichasFinalizadas = listaVendasCursoFinalizada.size();
				nFichasAndamento = listaVendasCursoAndamento.size();
				nFichaCancelada = listaVendasCursoCancelada.size();
				nFichasProcesso = listaVendasCursoProcesso.size();
				nFichaFinanceiro = listaVendasCursoFinanceiro.size();
			}
			listaUnidadeNegocio = GerarListas.listarUnidade();
			gerarListaFornecedor();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
				linkIdVenda = "true";
				linkIdVendaFranquias = "false";
			} else if (usuarioLogadoMB.isFinanceiro()) {
				linkIdVenda = "true";
				linkIdVendaFranquias = "false";
			} else {
				linkIdVenda = "false";
				linkIdVendaFranquias = "true";
			}
			idVenda = 0;
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

	public List<Cambio> getListaCambio() {
		return listaCambio;
	}

	public void setListaCambio(List<Cambio> listaCambio) {
		this.listaCambio = listaCambio;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public String getLinkIdVenda() {
		return linkIdVenda;
	}

	public String getLinkIdVendaFranquias() {
		return linkIdVendaFranquias;
	}

	public void setLinkIdVendaFranquias(String linkIdVendaFranquias) {
		this.linkIdVendaFranquias = linkIdVendaFranquias;
	}

	public void setLinkIdVenda(String linkIdVenda) {
		this.linkIdVenda = linkIdVenda;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public List<Curso> getListaVendasCurso() {
		return listaVendasCurso;
	}

	public void setListaVendasCurso(List<Curso> listaVendasCurso) {
		this.listaVendasCurso = listaVendasCurso;
	}

	public String getNumerosCurso() {
		return numerosCurso;
	}

	public void setNumerosCurso(String numerosCurso) {
		this.numerosCurso = numerosCurso;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
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

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public boolean isExpandirOpcoes() {
		return expandirOpcoes;
	}

	public void setExpandirOpcoes(boolean expandirOpcoes) {
		this.expandirOpcoes = expandirOpcoes;
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

	public boolean isEsconderFicha() {
		return esconderFicha;
	}

	public void setEsconderFicha(boolean esconderFicha) {
		this.esconderFicha = esconderFicha;
	}

	public List<Curso> getListaVendasCursoFinalizada() {
		return listaVendasCursoFinalizada;
	}

	public void setListaVendasCursoFinalizada(List<Curso> listaVendasCursoFinalizada) {
		this.listaVendasCursoFinalizada = listaVendasCursoFinalizada;
	}

	public List<Curso> getListaVendasCursoAndamento() {
		return listaVendasCursoAndamento;
	}

	public void setListaVendasCursoAndamento(List<Curso> listaVendasCursoAndamento) {
		this.listaVendasCursoAndamento = listaVendasCursoAndamento;
	}

	public List<Curso> getListaVendasCursoCancelada() {
		return listaVendasCursoCancelada;
	}

	public void setListaVendasCursoCancelada(List<Curso> listaVendasCursoCancelada) {
		this.listaVendasCursoCancelada = listaVendasCursoCancelada;
	}

	public List<Curso> getListaVendasCursoProcesso() {
		return listaVendasCursoProcesso;
	}

	public void setListaVendasCursoProcesso(List<Curso> listaVendasCursoProcesso) {
		this.listaVendasCursoProcesso = listaVendasCursoProcesso;
	}

	public Integer getnFichaFinanceiro() {
		return nFichaFinanceiro;
	}

	public void setnFichaFinanceiro(Integer nFichaFinanceiro) {
		this.nFichaFinanceiro = nFichaFinanceiro;
	}

	public List<Curso> getListaVendasCursoFinanceiro() {
		return listaVendasCursoFinanceiro;
	}

	public void setListaVendasCursoFinanceiro(List<Curso> listaVendasCursoFinanceiro) {
		this.listaVendasCursoFinanceiro = listaVendasCursoFinanceiro;
	}

	public String getPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(String pesquisar) {
		this.pesquisar = pesquisar;
	}

	public void carregarListaVendasCursos() {
		if (usuarioLogadoMB.getUsuario() != null || usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			String sql = "Select c from Curso c where c.vendas.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getCursos();
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sql = sql + " and c.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}
			sql = sql + " and c.vendas.dataVenda>='" + dataConsulta
					+ "' order by c.vendas.dataVenda desc, c.vendas.cliente.nome";
			CursoFacade cursoFacade = new CursoFacade();
			listaVendasCurso = cursoFacade.lista(sql);
			if (listaVendasCurso == null) {
				listaVendasCurso = new ArrayList<Curso>();
			}
			numeroFichas = "" + String.valueOf(listaVendasCurso.size());
		} else {
			if (listaVendasCurso == null) {
				listaVendasCurso = new ArrayList<Curso>();
			}
			numeroFichas = "" + String.valueOf(listaVendasCurso.size());
		}
		gerarQuantidadesFichas();
	}

	public String cadastrarFicha() {
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("idlead", idlead);
			return "cadFichaCurso";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}

	public void numerosCursos() {
		if (listaVendasCurso != null) {
			int numeroSemanas = 0;
			for (int i = 0; listaVendasCurso.size() > i; i++) {
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = new Curso();
				curso = cursoFacade.consultarCursos(listaVendasCurso.get(i).getVendas().getIdvendas());
				numeroSemanas = numeroSemanas + curso.getNumeroSenamas();
			}
			numerosCurso = "No. Semanas Vendidas = " + numeroSemanas;
		}
	}

	public String enviarEmail(Curso curso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("curso", curso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
		return "";
	}

	public String boletos(Curso curso) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(curso.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + curso.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(curso.getVendas().getIdvendas()));
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
		} else {
			FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Dados do cliente não converefe " + validarCliente.getMsg());
		}

		return "";
	}

	public void retornoDialogoEditar() {
		carregarListaVendasCursos();
	}

	public void pesquisarListaVendasCursos() {
		String sql = null;
		sql = "Select c from Curso c where c.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and c.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and c.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and c.dataInscricao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and c.dataInscricao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else if (idVenda==0){
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and c.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and c.vendas.situacao='" + situacao + "'";
		}
		if (fornecedor != null && fornecedor.getIdfornecedor() != null) {
			sql = sql + " and c.vendas.fornecedorcidade.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
		}
		sql = sql + " order by c.vendas.dataVenda desc, c.vendas.cliente.nome";
		CursoFacade cursoFacade = new CursoFacade();
		listaVendasCurso = cursoFacade.lista(sql);
		if (listaVendasCurso == null) {
			listaVendasCurso = new ArrayList<Curso>();
		}
		numeroFichas = "" + String.valueOf(listaVendasCurso.size());
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda = 0;
		fornecedor = null;
		pesquisar = "Nao";
		carregarListaVendasCursos();
	}

	public String obsTM(Curso curso) {
		obsTM = curso.getVendas().getObstm();
		return obsTM;
	}

	public void imprimirFicha(Curso curso) {
		this.curso = curso;
		try {
			gerarRelatorioFicha();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String gerarRelatorioFicha() throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
			if (curso.isDadospais()) {
				caminhoRelatorio = "/reports/curso/FichaCursoDadosPaisPagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/curso/FichaCursoPagina01.jasper";
			}
		} else {
			if (curso.isDadospais()) {
				caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
			}
		}
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
		parameters.put("idvendas", curso.getVendas().getIdvendas());
		parameters.put("sqlpagina2", gerarSqlSeguroViagems());
		if (segurocancelamento) {
			parameters.put("segurocancelamento", "Sim");
		}else {
			parameters.put("segurocancelamento", "Não");
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaCurso-" + curso.getIdcursos() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public void dialogTipoRelatorio(Curso curso) {
		this.curso = curso;
	}

	public String gerarRelatorioContratoCurso(Curso curso) throws SQLException, IOException {
		this.curso = curso;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
			caminhoRelatorio = ("/reports/curso/contratoCursoPagina01.jasper");
		} else
			caminhoRelatorio = ("/reports/curso/contratoCurso2Pagina01.jasper");
		Map parameters = new HashMap();
		parameters.put("idvendas", curso.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		try {
			gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"contratoCurso-" + curso.getVendas().getIdvendas() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Curso curso) throws SQLException, IOException {
		this.curso = curso;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", curso.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioRecibo(Curso curso) throws SQLException, IOException {
		this.curso = curso;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(curso.getVendas().getIdvendas());
		ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
		List<Parcelamentopagamento> listaParcelamento = parcelamentoPagamentoFacade.listar(forma.getIdformaPagamento());
		if (listaParcelamento != null) {
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Dinheiro")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("cheque")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("depósito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
			}
		}
		if (valorRecibo > 0.0f) {
			Map parameters = new HashMap();
			parameters.put("idvendas", curso.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reciboPagamento.pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public String editarCurso(Curso curso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("curso", curso); 
		session.setAttribute("idlead", curso.getVendas().getIdlead());
		return "cadFichaCurso";
	}

	public boolean imagemSituacaoUsuario(Curso curso) {
		if (curso.getVendas().getSituacao().equals("FINALIZADA")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
				curso.setHabilitarImagemGerencial(true);
				curso.setHabilitarImagemFranquia(false);
			} else {
				curso.setHabilitarImagemGerencial(false);
				curso.setHabilitarImagemFranquia(true);
			}
			curso.setImagem("../../resources/img/finalizadoFicha.png");
			curso.setTituloFicha("FICHA FINALIZADA");
		} else if (curso.getVendas().getSituacao().equals("ANDAMENTO")
				&& !curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
				curso.setHabilitarImagemGerencial(true);
				curso.setHabilitarImagemFranquia(false);
			} else {
				curso.setHabilitarImagemGerencial(false);
				curso.setHabilitarImagemFranquia(true);
			}
			curso.setImagem("../../resources/img/ficharestricao.png");
			if (curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				curso.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				curso.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else if (curso.getVendas().getSituacao().equals("ANDAMENTO")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
				curso.setHabilitarImagemGerencial(true);
				curso.setHabilitarImagemFranquia(false);
			} else {
				curso.setHabilitarImagemGerencial(false);
				curso.setHabilitarImagemFranquia(true);
			}
			curso.setImagem("../../resources/img/amarelaFicha.png");
			curso.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		}else if (curso.getVendas().getSituacao().equals("CANCELADA")) {
			curso.setHabilitarImagemGerencial(false);
			curso.setHabilitarImagemFranquia(true);
			curso.setImagem("../../resources/img/fichaCancelada.png");
			curso.setTituloFicha("FICHA CANCELADA");
		} else if ((curso.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (curso.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				curso.setHabilitarImagemGerencial(true);
				curso.setHabilitarImagemFranquia(false);
			} else {
				curso.setHabilitarImagemGerencial(false);
				curso.setHabilitarImagemFranquia(true);
			}
			curso.setImagem("../../resources/img/ficharestricao.png");
			curso.setImagem("../../resources/img/ficharestricao.png");
			if (curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				curso.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				curso.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else {
			curso.setHabilitarImagemGerencial(false);
			curso.setHabilitarImagemFranquia(true);
			curso.setImagem("../../resources/img/processoFicha.png");
			curso.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return curso.isHabilitarImagemGerencial();
	}

	public String corNome(Curso curso) {
		if (curso.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Curso curso) {
		if (curso.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}
 

	public String gerarSqlSeguroViagems() {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(curso.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(curso.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			segurocancelamento = seguro.isSegurocancelamento();
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct vendas.dataVenda, vendas.valor as valorVenda, cursos.nomeCurso, cursos.escola,"
				+ "cursos.cidade, cursos.pais, cursos.aulassemana, cursos.numerosenamas, cursos.dataInicio, "
				+ "cursos.dataTermino, cursos.tipoAcomodacao, cursos.numeroSemanasAcamodacao, cursos.tipoquarto,"
				+ "cursos.refeicoes, cursos.adicionais, cursos.datachegada, cursos.dataSaida, cursos.familiacomcrianca,"
				+ "cursos.familiacomanimais, cursos.fumante, cursos.vegetariano, cursos.hobbies, cursos.possuiAlergia,"
				+ "cursos.quaisalergias, cursos.solicitacoesespeciais, cursos.caratovtm, cursos.numerocartaovtm,"
				+ "cursos.moedacartaovtm, cursos.transferin, cursos.transferouto, cursos.passagemaerea, cursos.observacaopassagemaerea,"
				+ "cursos.vistoconsular, cursos.dataEntregadocumentosvistos, cursos.observacaovisto, cursos.nomecontatoemergencia,"
				+ "cursos.fonecontatoemergencia, cursos.emalcontatoemergencia, cursos.relacaocontatoemergencia, cursos.datainscricao as dataInscricaCurso, cursos.idioma, cursos.nivelIdioma, cursos.possuiSeguroGovernamental, cursos.numeroMeses as numeromesesgovernamental, cursos.seguradoraGovernamental,"
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "usuario.nome as nomeUsuario,unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "orcamentoprodutosorcamento.descricao as descricaoprodutosOrcamento,seguroviagem.idseguroViagem,seguroviagem.seguradora,seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor,orcamentoprodutosorcamento.idorcamentoprodutosorcamento"
				+ " from " + "vendas join cursos on vendas.idvendas = cursos.vendas_idvendas "
				+ "join usuario on vendas.usuario_idusuario = usuario.idusuario "
				+ "join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio "
				+ "join orcamento on vendas.idvendas = orcamento.vendas_idvendas "
				+ "join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento "
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento ";
		sql = sql + sqlseguro;
		sql = sql + " where " + " vendas.idvendas = " + curso.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento ";
		return sql;

	}

	public void dialogSalvarControle(Curso curso) {
		this.curso = curso;
	}

	public void salvarControle() throws SQLException {
		Controlecurso controlecurso = new Controlecurso();
		CursoFacade cursoFacade = new CursoFacade();
		controlecurso = cursoFacade.consultarControleCursos(curso.getVendas().getIdvendas());
		if (controlecurso == null) {
			ControlerBean controlerBean = new ControlerBean();
			float valorPrevisto = 0.0f;
			if (curso.getVendas().getVendascomissao() != null) {
				valorPrevisto = curso.getVendas().getVendascomissao().getValorfornecedor();
			}
			controlerBean.salvarControleCurso(curso.getVendas(), curso, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
		}
	}

	public void buscarCurso(Curso curso) {
		this.curso = curso;
	}

	public void buscarCursoApplication(Curso curso) {
		this.curso = curso;
		CursoFacade cursoFacade = new CursoFacade();
		curso.setApplication(true);
		curso = cursoFacade.salvar(curso);
	}

	public void liberarFicha() {
		if ((curso.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (curso.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				Vendas venda = curso.getVendas();
				venda.setRestricaoparcelamento(false);
				
				venda = vendasDao.salvar(venda);
				curso.setVendas(venda);
				Formapagamento forma = curso.getVendas().getFormapagamento();
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
		} else if ((curso.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (!curso.getVendas().isRestricaoparcelamento())) {
			curso.getVendas().getCliente().setLiberarficha(true);
			ClienteFacade clienteFacade = new ClienteFacade();
			curso.getVendas().setCliente(clienteFacade.salvar(curso.getVendas().getCliente()));
		}
	}

	public String documentacao(Curso curso) {
		boolean validar = true;
		if (curso.getVendas().getSituacao().equalsIgnoreCase("PROCESSO") && usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = curso.getVendas().getDatavalidade();
			if (dataValidade != null) {
				if (!dataAtual.after(dataValidade)) {
					validar = true;
				} else {
					validar = false;
				}
			}
		}
		if (!validar) {
			Mensagem.lancarMensagemInfo("Favor atualizar o câmbio desta ficha",
					"está ficha ultrapassou os 3 dias de validade");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", curso.getVendas());
			voltar = "consultafichacurso";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
	}
	
//	public String documentacao(Curso curso) {
//		if (curso.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
//			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
//			return "";
//		} else {
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("vendas", curso.getVendas());
//			voltar = "consultafichacurso";
//			session.setAttribute("voltar", voltar);
//			return "consArquivo";
//		}
//	}

//	public String cancelarVenda(Curso curso) {
//		if (curso.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
//				|| curso.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", curso.getVendas());
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else if (curso.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
//			
//			curso.getVendas().setSituacao("CANCELADA");
//			vendasDao.salvar(curso.getVendas());
//			carregarListaVendasCursos();
//		}
//		return "";
//	}

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

	public void gerarListaFornecedor() {
		FornecedorFacade forncedorFacade = new FornecedorFacade();
		listaFornecedor = forncedorFacade
				.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public String informacoes(Curso curso) {
		if (curso.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", curso.getVendas());
			voltar = "consultafichacurso";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}

	public boolean habilitarBookingApplication() {
		int iddepartamento = usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento();
		if (iddepartamento == 1 || iddepartamento == 2) {
			return true;
		} else {
			return false;
		}
	}

	


	public String gerarRelatorioBooking(Curso curso) throws SQLException, IOException {
		//traduzirCamposCurso(curso);
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/reportbooking.jasper");
		Map parameters = new HashMap();
		parameters.put("idvendas", curso.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;

		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(ConsultaOrcamentoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		ftp.conectar();
		InputStream is = ftp.receberArquivo("", curso.getVendas().getFornecedor().getLogo(), "/systm/logoescola/");
		Image imgPais = null;
		try {
			imgPais = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("logoescola", imgPais);
		ftp.desconectar();
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reportaplicationec.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	} 

	public String retornarCssBotao() {
		String css = "display: inline-block;position: relative;padding: 0;margin-right: .1em;text-decoration: none !important;"
				+ "cursor: pointer;text-align: center;zoom: 1;overflow: visible;background: #54A515;"
				+ "font-weight: bold;color: #ffffff;font-family: segoe ui, Arial, sans-serif;text-transform: none;margin: 0px;"
				+ "border-radius: 5px;";
		return css;
	} 
	
	public void gerarQuantidadesFichas(){
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0;
		nFichasProcesso = 0;
		nFichaFinanceiro = 0;
		listaVendasCursoFinalizada = new ArrayList<>();
		listaVendasCursoAndamento = new ArrayList<>();
		listaVendasCursoCancelada = new ArrayList<>();
		listaVendasCursoProcesso = new ArrayList<>();
		listaVendasCursoFinanceiro = new ArrayList<>();
		for (int i = 0; i < listaVendasCurso.size(); i++) {
			if (listaVendasCurso.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasCursoFinalizada.add(listaVendasCurso.get(i));
			}else if(listaVendasCurso.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasCursoProcesso.add(listaVendasCurso.get(i));
			}else if(listaVendasCurso.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaVendasCurso.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichaFinanceiro = nFichaFinanceiro + 1;
				listaVendasCursoFinanceiro.add(listaVendasCurso.get(i));
			}else if(listaVendasCurso.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& listaVendasCurso.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasCursoAndamento.add(listaVendasCurso.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCursoCancelada.add(listaVendasCurso.get(i));
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
	
	public String retornarImgOpcoes(){
		if(expandirOpcoes){ 
			return "../../resources/img/esconderOpcoes.png";
		}else return "../../resources/img/expandirOpcoes.png";
	} 
	
	public String retornarTitleOpcoes(){
		if(expandirOpcoes){ 
			return "Esconder Opções";     
		}else return "Expandir Opções";
	} 
	
	public String retornarIconeObsTM(Curso curso){
		if (curso.getVendas().getObstm() != null && curso.getVendas().getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	
	public String visualizarContasReceber(Curso curso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", curso.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	
	public String cancelarVenda(Curso curso) {
		if (curso.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
				|| curso.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", curso.getVendas());
			session.setAttribute("voltar", "consultafichacurso");
			return "emissaocancelamento";
		} else if (curso.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			curso.getVendas().setSituacao("CANCELADA");
			vendasDao.salvar(curso.getVendas());
			carregarListaVendasCursos();
		}
		return "";
	}    
	
	public String contrato(Curso curso){
		this.curso = curso;
		LerArquivoTxt lerArquivoTxt = new LerArquivoTxt(curso.getVendas(), "Curso");
		try {
			String texto = lerArquivoTxt.ler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("http://systm.com.br:82/systm/arquivos/Contrato" + curso.getVendas().getUnidadenegocio().getIdunidadeNegocio() + 
					curso.getVendas().getUsuario().getIdusuario() + curso.getVendas().getIdvendas() + ".html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String fichaCurso(Curso curso){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("curso", curso);
		session.setAttribute("listaVendasCursoAndamento", listaVendasCursoAndamento);
		session.setAttribute("listaVendasCursoCancelada", listaVendasCursoCancelada);
		session.setAttribute("listaVendasCursoFinalizada", listaVendasCursoFinalizada);
		session.setAttribute("listaVendasCursoFinanceiro", listaVendasCursoFinanceiro);
		session.setAttribute("listaVendasCursoProcesso", listaVendasCursoProcesso);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Curso");
		session.setAttribute("chamadaTela", "Curso");
		return "fichaCurso";
	}
	
	
	public String relatorioTermoQuitacao(Curso cursos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(cursos.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}
	
	
	public void verificarIdCredito(Curso cursos) {
		if (cursos.getVendas().getCancelamento() != null) {
			if (cursos.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (cursos.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = cursos.getVendas().getCancelamento().getCancelamentocredito().getCredito();
					FacesContext fc = FacesContext.getCurrentInstance();
					HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
					session.setAttribute("credito", credito);
					Map<String, Object> options = new HashMap<String, Object>();
					options.put("contentWidth", 150);
					RequestContext.getCurrentInstance().openDialog("visualizarIdCredito", options, null);
				}else {
					Mensagem.lancarMensagemFatal("Não há crédito para está venda", "");
				}
			}else {
				Mensagem.lancarMensagemFatal("Não há crédito para está venda", "");
			}
		}else {
			Mensagem.lancarMensagemFatal("Não há crédito para está venda", "");
		}
	}
	
	
	
	public String contratoCurso(Curso curso){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("curso", curso);
		session.setAttribute("listaVendasCursoAndamento", listaVendasCursoAndamento);
		session.setAttribute("listaVendasCursoCancelada", listaVendasCursoCancelada);
		session.setAttribute("listaVendasCursoFinalizada", listaVendasCursoFinalizada);
		session.setAttribute("listaVendasCursoFinanceiro", listaVendasCursoFinanceiro);
		session.setAttribute("listaVendasCursoProcesso", listaVendasCursoProcesso);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Curso");
		session.setAttribute("chamadaTela", "Curso");
		return "contratoCurso";
	}
	
	
	
	
	
}
