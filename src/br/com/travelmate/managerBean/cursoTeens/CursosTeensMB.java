/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.cursoTeens;
 
import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.LerArquivoTxt;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

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

@Named
@ViewScoped
public class CursosTeensMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	private List<Programasteens> listaCursosTeens;
	private Programasteens programasteens;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Cambio> listaCambio;
	private String numeroFichas;
	private String numerosCurso;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String obsTM;
	private boolean habilitarUnidade = true;
	private String linkIdVenda;
	private String linkIdVendaFranquias;
	private int idVenda;
	private String voltar;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private Integer nFichasFinanceiro;
	private List<Programasteens> listaVendasFinalizada;
	private List<Programasteens> listaVendasAndamento;
	private List<Programasteens> listaVendasCancelada;
	private List<Programasteens> listaVendasProcesso;
	private List<Programasteens> listaVendasFinanceiro;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendasFinalizada = (List<Programasteens>) session.getAttribute("listaVendasFinalizada");
		listaVendasAndamento = (List<Programasteens>) session.getAttribute("listaVendasAndamento");
		listaVendasProcesso = (List<Programasteens>) session.getAttribute("listaVendasProcesso");
		listaVendasFinanceiro = (List<Programasteens>) session.getAttribute("listaVendasFinanceiro");
		listaVendasCancelada = (List<Programasteens>) session.getAttribute("listaVendasCancelada");
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
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Teens")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				carregarListaVendas();
			}
			listarFornecedores();
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

	public List<Programasteens> getListaCursosTeens() {
		return listaCursosTeens;
	}

	public void setListaCursosTeens(List<Programasteens> listaCursosTeens) {
		this.listaCursosTeens = listaCursosTeens;
	}

	public Programasteens getProgramasteens() {
		return programasteens;
	}

	public void setProgramasteens(Programasteens programasteens) {
		this.programasteens = programasteens;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String getNumerosCurso() {
		return numerosCurso;
	}

	public void setNumerosCurso(String numerosCurso) {
		this.numerosCurso = numerosCurso;
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

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	} 

	public List<Programasteens> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Programasteens> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Programasteens> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Programasteens> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Programasteens> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Programasteens> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Programasteens> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Programasteens> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Programasteens> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Programasteens> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
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
	
	

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public void carregarListaVendas() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		String sql = "Select p from Programasteens p where p.vendas.situacao<>'Cancelada' and p.vendas.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getProgramasTeens();
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " and  p.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and p.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + " and p.vendas.dataVenda>='" + dataConsulta + "' order by p.vendas.dataVenda desc";
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		listaCursosTeens = programasTeensFacede.listar(sql);
		if (listaCursosTeens == null) {
			listaCursosTeens = new ArrayList<Programasteens>();
		}
		numeroFichas = "" + String.valueOf(listaCursosTeens.size());
		gerarQuantidadesFichas();
	}

	public String cadastrarFicha() {
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("idlead", idlead);
			return "cadCursosTeens";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}

	public void retornoDialogoEditar() {
		carregarListaVendas();
	}

	public void pesquisarListaVendas() {
		String sql = null;
		sql = "Select p from Programasteens p where p.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and p.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and p.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and p.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and p.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and p.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and p.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and p.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and p.vendas.situacao='" + situacao + "'";
		}
		if (fornecedor != null) {
			sql = sql + " and p.vendas.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
		}
		sql = sql + " order by p.vendas.dataVenda, p.vendas.cliente.nome";
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		listaCursosTeens = programasTeensFacede.listar(sql);
		if (listaCursosTeens == null) {
			listaCursosTeens = new ArrayList<Programasteens>();
		}
		numeroFichas = "" + String.valueOf(listaCursosTeens.size());
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
		carregarListaVendas();
	}

	public String buscarObsTM(Programasteens programasteens) {
		obsTM = programasteens.getVendas().getObstm();
		return obsTM;
	}

	public String gerarRelatorioFicha(Programasteens programasteens) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/cursosTeens/FichaProgramasTeensPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));
		parameters.put("idvendas", programasteens.getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaCursoTeens-" + programasteens.getVendas().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContratoCurso(Programasteens programasteens) throws SQLException, IOException {
		this.programasteens = programasteens;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = ("/reports/cursosTeens/contratoTeensPagina01.jasper");
		Map parameters = new HashMap();
		parameters.put("idvendas", programasteens.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		try {
			gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"contratoCursosTeens-" + programasteens.getVendas().getIdvendas() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Programasteens programasteens) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", programasteens.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioRecibo(Programasteens programasteens) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(programasteens.getVendas().getIdvendas());
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
			parameters.put("idvendas", programasteens.getVendas().getIdvendas());
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
				Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(CursosTeensMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public void buscarCursosTeens(Programasteens programasteens) {
		this.programasteens = programasteens;
	}

	public String editarCursosTeens(Programasteens programasteens) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("programasTeens", programasteens); 
		session.setAttribute("idlead", programasteens.getVendas().getIdlead());
		return "cadCursosTeens";
	}

	public boolean imagemSituacaoUsuario(Programasteens programasteens){
        if (programasteens.getVendas().getSituacao().equals("FINALIZADA")) {
        	programasteens.setHabilitarImagemGerencial(false);
        	programasteens.setHabilitarImagemFranquia(true);
            programasteens.setImagem("../../resources/img/finalizadoFicha.png");
            programasteens.setTituloFicha("FICHA FINALIZADA");
        }else if (programasteens.getVendas().getSituacao().equals("ANDAMENTO")
        		 && !programasteens.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5) {
				programasteens.setHabilitarImagemGerencial(true);
				programasteens.setHabilitarImagemFranquia(false);
			} else {
				programasteens.setHabilitarImagemGerencial(false);
				programasteens.setHabilitarImagemFranquia(true);
			}
			programasteens.setImagem("../../resources/img/ficharestricao.png");
			programasteens.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else if (programasteens.getVendas().getSituacao().equals("ANDAMENTO")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5) {
				programasteens.setHabilitarImagemGerencial(true);
				programasteens.setHabilitarImagemFranquia(false);
			} else {
				programasteens.setHabilitarImagemGerencial(false);
				programasteens.setHabilitarImagemFranquia(true);
			}
			programasteens.setImagem("../../resources/img/amarelaFicha.png");
			programasteens.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		}else if(programasteens.getVendas().getSituacao().equals("CANCELADA")){
        	programasteens.setHabilitarImagemGerencial(false);
        	programasteens.setHabilitarImagemFranquia(true);
            programasteens.setImagem("../../resources/img/fichaCancelada.png");
            programasteens.setTituloFicha("FICHA CANCELADA");
        }else if ((programasteens.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) && (programasteens.getVendas().isRestricaoparcelamento())){
        	if (usuarioLogadoMB.isFinanceiro()){
        		programasteens.setHabilitarImagemGerencial(true);
        		programasteens.setHabilitarImagemFranquia(false);
        	}else {
        		programasteens.setHabilitarImagemGerencial(false);
        		programasteens.setHabilitarImagemFranquia(true);
        	}
        	programasteens.setImagem("../../resources/img/ficharestricao.png");
        	programasteens.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
        }else {
        	programasteens.setHabilitarImagemGerencial(false);
        	programasteens.setHabilitarImagemFranquia(true);
        	programasteens.setImagem("../../resources/img/processoFicha.png");
        	programasteens.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
        }
        return programasteens.isHabilitarImagemGerencial();
    }

	public String documentacao(Programasteens programasteens) {
		boolean validar = true;
		if (programasteens.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = programasteens.getVendas().getDatavalidade();
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
			session.setAttribute("vendas", programasteens.getVendas());
			voltar = "cursosTeens";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
	}

//	public String cancelarVenda(Vendas venda){
//		if (!venda.getSituacao().equalsIgnoreCase("PROCESSO")){
//			Map<String, Object> options = new HashMap<String, Object>();
//	    	options.put("contentWidth", 400);
//	    	FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", venda);
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		}else {
//			
//			venda.setSituacao("CANCELADA");
//			vendasDao.salvar(venda);
//		}
//		return "";
//	}
	
	
	public String cancelarVenda(Vendas venda) {
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA")
				|| venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", venda);
			session.setAttribute("voltar", "cursosTeens");
			return "emissaocancelamento";
		} else if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			venda.setSituacao("CANCELADA");
			vendasDao.salvar(venda);
			carregarListaVendas();
		}
		return "";
	}    
	
	
	public String visualizarContasReceber(Vendas venda){
        if ((venda.getOrcamento()!=null)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("venda", venda);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 620);
            RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
        }
        else{
            FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }
	
	public void liberarFicha(){
    	if ((programasteens.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) && (programasteens.getVendas().isRestricaoparcelamento())){
    		if (usuarioLogadoMB.isFinanceiro()){
    			Vendas venda = programasteens.getVendas();
    			venda.setRestricaoparcelamento(false);
    			
    			venda = vendasDao.salvar(venda);
    			programasteens.setVendas(venda);
    			Formapagamento forma = programasteens.getVendas().getFormapagamento();
        		if (forma!=null){
        			if(forma.getParcelamentopagamentoList()!=null){
        				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
        				for(int i=0;i<forma.getParcelamentopagamentoList().size();i++){
        					forma.getParcelamentopagamentoList().get(i).setVerificarParcelamento(false);
        					forma.getParcelamentopagamentoList().set(i, parcelamentoPagamentoFacade.salvar(forma.getParcelamentopagamentoList().get(i)));    				
        				}
        			}
        		}
    		}
    	}
    }
	
	
	public String boletos(Programasteens programasteens) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(programasteens.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas="
					+ programasteens.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(programasteens.getVendas().getIdvendas()));
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
	
	public String informacoes(Programasteens programasteens) {
		if (programasteens.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";  
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", programasteens.getVendas());
			voltar = "cursosTeens";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
	
	
	public void gerarQuantidadesFichas(){
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
		for (int i = 0; i < listaCursosTeens.size(); i++) {
			if (listaCursosTeens.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaCursosTeens.get(i));
			}else if(listaCursosTeens.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaCursosTeens.get(i));
			}else if(listaCursosTeens.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaCursosTeens.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaCursosTeens.get(i));
			}else if(listaCursosTeens.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaCursosTeens.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaCursosTeens.get(i));
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
	
	
	
	public void listarFornecedores(){
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade.listar("Select f From Fornecedor f Join Fornecedorcidade fc on f.idfornecedor=fc.fornecedor.idfornecedor "
				+ " Where fc.produtos.idprodutos=5 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}
	
	
	public String retornarIconeObsTM(Programasteens programasteens){
		if (programasteens.getObstm() != null && programasteens.getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	
	public String visualizarContasReceber(Programasteens programasteens) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", programasteens.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	public String contrato(Programasteens programasteens){
		this.programasteens = programasteens;
		LerArquivoTxt lerArquivoTxt = new LerArquivoTxt(programasteens.getVendas(), "Teens");
		try {
			String texto = lerArquivoTxt.ler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("http://systm.com.br:82/ftproot/systm/arquivos/Contrato" + programasteens.getVendas().getUnidadenegocio().getIdunidadeNegocio() + 
					programasteens.getVendas().getUsuario().getIdusuario() + programasteens.getVendas().getIdvendas() + ".html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String fichaCursosTeens(Programasteens programasteens){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("programasteens", programasteens);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Teens");
		session.setAttribute("chamadaTela", "Teens");
		return "fichaCursosTeens";
	}
	
	
	public String contratoCursosTeens(Programasteens programasteens){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("programasteens", programasteens);
		session.setAttribute("listaVendasFinalizada", listaVendasFinalizada);
		session.setAttribute("listaVendasAndamento", listaVendasAndamento);
		session.setAttribute("listaVendasCancelada", listaVendasCancelada);
		session.setAttribute("listaVendasProcesso", listaVendasProcesso);
		session.setAttribute("listaVendasFinanceiro", listaVendasFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Teens");
		session.setAttribute("chamadaTela", "Teens");
		return "contratoTeens";
	}
	
	
	public void verificarIdCredito(Programasteens programasteens) {
		if (programasteens.getVendas().getCancelamento() != null) {
			if (programasteens.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (programasteens.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = programasteens.getVendas().getCancelamento().getCancelamentocredito().getCredito();
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
	
	public String relatorioTermoQuitacao(Programasteens programasteens) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(programasteens.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}

}
