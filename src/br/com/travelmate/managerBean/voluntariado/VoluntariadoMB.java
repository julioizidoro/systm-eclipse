package br.com.travelmate.managerBean.voluntariado;

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
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;

import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.LerArquivoTxt;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.managerBean.trainee.TraineeMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class VoluntariadoMB implements Serializable {

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
	private List<Voluntariado> listaVoluntariado;
	private String numFichas;
	private String obsTM;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean habilitarUnidade = true;
	private String motivoCancelamento;
	private Voluntariado voluntariado;
	private int idVenda;
	private String linkIdVenda;
	private String linkIdVendaFranquias;
	private String voltar;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private boolean expandirOpcoes;
	private boolean esconderFicha = true;
	private List<Voluntariado> listaVendasFinalizada;
	private List<Voluntariado> listaVendasAndamento;
	private List<Voluntariado> listaVendasCancelada;
	private List<Voluntariado> listaVendasProcesso;
	private List<Voluntariado> listaVendasFinanceiro;
	private int nFichasFinanceiro;
	private String pesquisar  = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendasFinalizada = (List<Voluntariado>) session.getAttribute("listaVendasFinalizada");
		listaVendasAndamento = (List<Voluntariado>) session.getAttribute("listaVendasAndamento");
		listaVendasProcesso = (List<Voluntariado>) session.getAttribute("listaVendasProcesso");
		listaVendasFinanceiro = (List<Voluntariado>) session.getAttribute("listaVendasFinanceiro");
		listaVendasCancelada = (List<Voluntariado>) session.getAttribute("listaVendasCancelada");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("listaVendasFinalizada");
		session.removeAttribute("listaVendasAndamento");
		session.removeAttribute("listaVendasProcesso");
		session.removeAttribute("listaVendasFinanceiro");
		session.removeAttribute("listaVendasCancelada");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Voluntariado")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				carregarLista();
			}
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

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Voluntariado> getListaVoluntariado() {
		return listaVoluntariado;
	}

	public void setListaVoluntariado(List<Voluntariado> listaVoluntariado) {
		this.listaVoluntariado = listaVoluntariado;
	}

	public String getNumFichas() {
		return numFichas;
	}

	public void setNumFichas(String numFichas) {
		this.numFichas = numFichas;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public String getNome() {
		return nome;
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

	public Voluntariado getVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
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

	public List<Voluntariado> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Voluntariado> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Voluntariado> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Voluntariado> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Voluntariado> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Voluntariado> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Voluntariado> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Voluntariado> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Voluntariado> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Voluntariado> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public int getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(int nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public String novo() {
		int idlead = 0;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("idlead", idlead);
		return "cadVoluntariado";
	}

	public void carregarLista() {
		String sql = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		sql = "Select v from Voluntariado v where v.vendas.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getVoluntariado();
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " and  v.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and v.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + " and v.vendas.dataVenda>='" + dataConsulta + "' order by v.vendas.dataVenda desc";
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		listaVoluntariado = voluntariadoFacade.lista(sql);
		if (listaVoluntariado == null) {
			listaVoluntariado = new ArrayList<Voluntariado>();
		}
		numFichas = "" + String.valueOf(listaVoluntariado.size());
		gerarQuantidadesFichas();
	}


	public boolean imagemSituacaoUsuario(Voluntariado voluntariado) {
		if (voluntariado.getVendas().getSituacao().equals("FINALIZADA")) {
			voluntariado.setHabilitarImagemGerencial(false);
			voluntariado.setHabilitarImagemFranquia(true);
			voluntariado.setImagem("../../resources/img/finalizadoFicha.png");
			voluntariado.setTituloFicha("FICHA FINALIZADA");
		} else if (voluntariado.getVendas().getSituacao().equals("ANDAMENTO")
				&& !voluntariado.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				voluntariado.setHabilitarImagemGerencial(true);
				voluntariado.setHabilitarImagemFranquia(false);
			} else {
				voluntariado.setHabilitarImagemGerencial(false);
				voluntariado.setHabilitarImagemFranquia(true);
			}
			voluntariado.setImagem("../../resources/img/ficharestricao.png");
			if (voluntariado.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				voluntariado.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			} else {
				voluntariado.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else if (voluntariado.getVendas().getSituacao().equals("ANDAMENTO")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				voluntariado.setHabilitarImagemGerencial(true);
				voluntariado.setHabilitarImagemFranquia(false);
			} else {
				voluntariado.setHabilitarImagemGerencial(false);
				voluntariado.setHabilitarImagemFranquia(true);
			}
			voluntariado.setImagem("../../resources/img/amarelaFicha.png");
			voluntariado.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		} else if (voluntariado.getVendas().getSituacao().equals("CANCELADA")) {
			voluntariado.setHabilitarImagemGerencial(false);
			voluntariado.setHabilitarImagemFranquia(true);
			voluntariado.setImagem("../../resources/img/fichaCancelada.png");
			voluntariado.setTituloFicha("FICHA CANCELADA");
		} else if ((voluntariado.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (voluntariado.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				voluntariado.setHabilitarImagemGerencial(true);
				voluntariado.setHabilitarImagemFranquia(false);
			} else {
				voluntariado.setHabilitarImagemGerencial(false);
				voluntariado.setHabilitarImagemFranquia(true);
			}
			voluntariado.setImagem("../../resources/img/ficharestricao.png");
			voluntariado.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else {
			voluntariado.setHabilitarImagemGerencial(false);
			voluntariado.setHabilitarImagemFranquia(true);
			voluntariado.setImagem("../../resources/img/processoFicha.png");
			voluntariado.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return voluntariado.isHabilitarImagemGerencial();
	}

	public String corNome(Voluntariado voluntariado) {
		if (voluntariado.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Voluntariado voluntariado) {
		if (voluntariado.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Voluntariado voluntariado) {
		if (voluntariado.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String obsTM(Voluntariado voluntariado) {
		obsTM = voluntariado.getVendas().getObstm();
		return obsTM;
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda = 0;
		pesquisar = "Nao";
		carregarLista();
	}

	public void pesquisar() {
		String sql = null;
		sql = "Select v from Voluntariado v where v.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and v.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and v.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and v.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and v.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and v.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and v.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and v.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and v.vendas.situacao='" + situacao + "'";
		}
		sql = sql + " order by v.vendas.dataVenda, v.vendas.cliente.nome";
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		listaVoluntariado = voluntariadoFacade.lista(sql);
		if (listaVoluntariado == null) {
			listaVoluntariado = new ArrayList<Voluntariado>();
		}
		numFichas = "" + String.valueOf(listaVoluntariado.size());
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public String editar(Voluntariado voluntariado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariado", voluntariado);
		session.setAttribute("idlead", voluntariado.getVendas().getIdlead());
		return "cadVoluntariado";
	}

	public String gerarRelatorioFicha(Voluntariado voluntariado) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/voluntariado/FichaVoluntariadoPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
		parameters.put("idvendas", voluntariado.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("sqlpagina2", gerarSqlPagina1(voluntariado));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"FichaVoluntariado-" + voluntariado.getIdvoluntariado() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(VoluntariadoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(VoluntariadoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarSqlPagina1(Voluntariado voluntariado) {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(voluntariado.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(voluntariado.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct " + "vendas.dataVenda, vendas.valor as valorVenda,vendas.idvendas,"
				+ "voluntariado.idvoluntariado,voluntariado.idiomaEstudar, voluntariado.nivelIdiomaEstudar,"
				+ "voluntariado.nomeContatoEmergencia,voluntariado.foneContatoEmergencia, voluntariado.emailContatoEmergencia,"
				+ "voluntariado.relacaoContatoEmergencia,voluntariado.instituicaoEstuda,voluntariado.cursoEstuda,"
				+ "voluntariado.duracaoCursoEstuda,voluntariado.periodoCursoEstuda,voluntariado.dataMatriculaCursoEstuda,voluntariado.dataEstimadaTerminoCursoEstuda,"
				+ "voluntariado.profissao,voluntariado.cargo,voluntariado.descricaoCargo,voluntariado.outrasHabilidade,"
				+ "voluntariado.curso,voluntariado.aulasporSemana,voluntariado.numeroSemanas,voluntariado.dataInicio,"
				+ "voluntariado.dataTermino,voluntariado.tipoAcomodacao,voluntariado.numeroSemanasAcomodacao,voluntariado.tipoQuarto,"
				+ "voluntariado.refeicoes,voluntariado.adicionais,voluntariado.dataChegadaAcomodacao,voluntariado.dataPartidaAcomodacao,"
				+ "voluntariado.familiaCrianca,voluntariado.familiaAnimais,voluntariado.fumante,voluntariado.vegetariano,"
				+ "voluntariado.hobbies,voluntariado.possuiAlergia,voluntariado.quaisAlergias,voluntariado.solicitacoesEspeciais,voluntariado.transferin,"
				+ "voluntariado.transferout,voluntariado.dataChegadaTransfer,"
				+ "voluntariado.voo,voluntariado.ciaerea,voluntariado.horario,voluntariado.projetoVoluntariado,"
				+ "voluntariado.dataInicioVoluntariado,voluntariado.dataTerminoVoluntariado,voluntariado.experienciaVoluntariado,voluntariado.motivoVoluntariado,"
				+ "voluntariado.deficienciaFisica,voluntariado.possuiProblemaSaude,voluntariado.descricaoProblemaSaude,voluntariado.tratamentoMedico,"
				+ "voluntariado.descricaoMedico,voluntariado.tratamentoDrogas,voluntariado.descricaoDrogas,"
				+ "voluntariado.fezCirurgia,voluntariado.descricaoCirurgia,voluntariado.dietaEspecifica,voluntariado.cartaoVTM,"
				+ "voluntariado.numerocartaoVTM,voluntariado.meodaCartaoVTM,voluntariado.seguroViagem,voluntariado.seguradora,voluntariado.planoSeguro,"
				+ "voluntariado.dataInicioSeguro,voluntariado.dataTerminoSeguro,voluntariado.numeroSemanasSeguro,voluntariado.passagemAerea,"
				+ "voluntariado.observacaoPassagem,voluntariado.vistoConsular,voluntariado.observacaoVistoConsultar,voluntariado.vendas_idvendas,"
				+ "voluntariado.dataEntregaDocumentoVisto,voluntariado.nivelFitness,voluntariado.controle, "
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "cliente.nome, cliente.dataNascimento, cliente.paisnascimento, cliente.cidadenascimento, cliente.rg,"
				+ "cliente.sexo, cliente.numeropassaporte, cliente.dataExpedicaoPassaporte,"
				+ "cliente.validadePassaporte, cliente.tipologradouro as clientetipologradouro, cliente.logradouro as clientelogradouro, cliente.numero as clientenumero,"
				+ "cliente.bairro as clientebairro, cliente.cidade as clientecidade, cliente.estado as clienteestado, cliente.cep as clientecep, cliente.cpf as clientecpf, cliente.pais as clientepais, cliente.foneresidencial,"
				+ "cliente.fonecelular, cliente.fonecomercial, cliente.profissao,"
				+ "cliente.email as emailcliente,cliente.escolaridade, cliente.nomePai, cliente.profissaopai, cliente.fonepai, cliente.nomemae,"
				+ "cliente.profissaomae, cliente.fonemae,"
				+ "usuario.nome as nomeUsuario, usuario.email as usuarioemail,"
				+ "unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, formapagamento.condicao, formapagamento.valortotal,"
				+ "formapagamento.valorjuros, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento, moedas.descricao as descricaoMoedas, cambio.idcambio, moedas.sigla,parcelamentopagamento.idparcemlamentoPagamento,"
				+ "parcelamentopagamento.formaPagamento," + "parcelamentopagamento.tipoParcelmaneto,"
				+ "parcelamentopagamento.valorParcelamento," + "parcelamentopagamento.diaVencimento,"
				+ "parcelamentopagamento.numeroParcelas," + "parcelamentopagamento.valorParcela,"
				+ "parcelamentopagamento.formaPagamento_idformaPagamento,orcamentoprodutosorcamento.idorcamentoprodutosorcamento,"
				+ "fornecedor.idfornecedor,seguroviagem.idseguroViagem,seguroviagem.seguradora,"
				+ "seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,"
				+ "seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,"
				+ "seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor, "
				+ "fornecedor.nome as nomeFornecedor, cidade.nome as nomeCidade, pais.nome nomePais" + " from "
				+ "  vendas join voluntariado on vendas.idvendas = voluntariado.vendas_idvendas"
				+ "  join usuario on vendas.usuario_idusuario = usuario.idusuario"
				+ "  join cliente on vendas.cliente_idcliente = cliente.idcliente"
				+ "  join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio"
				+ " join orcamento on vendas.idvendas = orcamento.vendas_idvendas"
				+ "  join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento"
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento"
				+ " join formaPagamento on vendas.idvendas = formapagamento.vendas_idvendas"
				+ " join cambio on orcamento.cambio_idcambio = cambio.idcambio"
				+ "  join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
				+ " join parcelamentopagamento on formapagamento.idformapagamento = parcelamentopagamento.formapagamento_idformaPagamento"
				+ " join fornecedorCidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
				+ "   join fornecedor on fornecedorcidade.fornecedor_idfornecedor = fornecedor.idfornecedor"
				+ "  join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade"
				+ "  join pais on cidade.pais_idpais = pais.idpais";
		sql = sql + sqlseguro;
		sql = sql + " where " + "vendas.idvendas =" + voluntariado.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento";
		return sql;
	}

	public String gerarRelatorioContrato(Voluntariado voluntariado) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/voluntariado/contratoVoluntariadoPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
		parameters.put("idvendas", voluntariado.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"ContratoVoluntariado-" + voluntariado.getIdvoluntariado() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(TraineeMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(TraineeMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioRecibo(Voluntariado voluntariado) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(voluntariado.getVendas().getIdvendas());
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
			parameters.put("idvendas", voluntariado.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"reciboPagamento-Voluntariado-" + voluntariado.getIdvoluntariado() + ".pdf", null);
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

	public String gerarRelatorioTermoVisto(Voluntariado voluntariado) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", voluntariado.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"TermoVisto-Voluntariado-" + voluntariado.getIdvoluntariado() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public void cancelar(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}

	public void salvarControle() throws SQLException {
		Controlevoluntariado controlevoluntariado = new Controlevoluntariado();
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		controlevoluntariado = voluntariadoFacade.consultarControle(voluntariado.getVendas().getIdvendas());
		if (controlevoluntariado == null) {
			ControlerBean controlerBean = new ControlerBean();
			float valorPrevisto = 0.0f;
			if (voluntariado.getVendas().getVendascomissao() != null) {
				valorPrevisto = voluntariado.getVendas().getVendascomissao().getValorfornecedor();
			}
			controlerBean.salvarControleVoluntariado(voluntariado.getVendas(), voluntariado, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
		}
	}

	public void dialogSalvarControle(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}

	public String documentacao(Voluntariado voluntariado) {
		boolean validar = true;
		if (voluntariado.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = voluntariado.getVendas().getDatavalidade();
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
			session.setAttribute("vendas", voluntariado.getVendas());
			voltar = "consultaVoluntariado";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
	}

//	public String cancelarVenda(Vendas venda) {
//		if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", venda);
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else {
//			
//			venda.setSituacao("CANCELADA");
//			vendasDao.salvar(venda);
//			carregarLista();
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

	public void liberarFicha() {
		if ((voluntariado.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (voluntariado.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				Vendas venda = voluntariado.getVendas();
				venda.setRestricaoparcelamento(false);
				
				venda = vendasDao.salvar(venda);
				voluntariado.setVendas(venda);
				Formapagamento forma = voluntariado.getVendas().getFormapagamento();
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

	public String boletos(Voluntariado voluntariado) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(voluntariado.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas="
					+ voluntariado.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(voluntariado.getVendas().getIdvendas()));
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

	public String informacoes(Voluntariado voluntariado) {
		if (voluntariado.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", voluntariado.getVendas());
			voltar = "consultaVoluntariado";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}

	public void gerarQuantidadesFichas() {
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0;
		nFichasProcesso = 0;
		nFichasFinanceiro = 0;
		listaVendasFinalizada = new ArrayList<>();
		listaVendasAndamento = new ArrayList<>();
		listaVendasCancelada = new ArrayList<>();
		listaVendasProcesso = new ArrayList<>();
		listaVendasFinanceiro = new ArrayList<>();
		for (int i = 0; i < listaVoluntariado.size(); i++) {
			if (listaVoluntariado.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaVoluntariado.get(i));
			} else if (listaVoluntariado.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaVoluntariado.get(i));
			}else if(listaVoluntariado.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaVoluntariado.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaVoluntariado.get(i));
			} else if (listaVoluntariado.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaVoluntariado.get(i));
			} else {
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaVoluntariado.get(i));
			}
		}
	}

	public void expandirOpcoes() {
		if (expandirOpcoes) {
			expandirOpcoes = false;
			esconderFicha = true;
		} else {
			expandirOpcoes = true;
			esconderFicha = false;
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
	
	
	public String retornarIconeObsTM(Voluntariado voluntariado){
		if (voluntariado.getVendas().getObstm() != null && voluntariado.getVendas().getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	
	
	public String visualizarContasReceber(Voluntariado voluntariado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", voluntariado.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	
	public String cancelarVenda(Vendas vendas) {
		if (vendas.getSituacao().equalsIgnoreCase("FINALIZADA")
				|| vendas.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas);
			session.setAttribute("voltar", "consultaVoluntariado");
			return "emissaocancelamento";
		} else if (vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			vendas.setSituacao("CANCELADA");
			vendasDao.salvar(vendas);
		}
		return "";
	}  
	
	

	
	
	public void verificarIdCredito(Voluntariado voluntariado) {
		if (voluntariado.getVendas().getCancelamento() != null) {
			if (voluntariado.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (voluntariado.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = voluntariado.getVendas().getCancelamento().getCancelamentocredito().getCredito();
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
	
	public String relatorioTermoQuitacao(Voluntariado voluntariado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(voluntariado.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}
	
	public String fichaVoluntariado(Voluntariado voluntariado){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariado", voluntariado);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Voluntariado");
		session.setAttribute("chamadaTela", "Voluntariado");
		return "fichaVoluntariado";
	}
	
	
	public String contrato(Voluntariado voluntariado){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariado", voluntariado);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Voluntariado");
		session.setAttribute("chamadaTela", "Voluntariado");
		return "contratoVoluntariado";
	}
}
