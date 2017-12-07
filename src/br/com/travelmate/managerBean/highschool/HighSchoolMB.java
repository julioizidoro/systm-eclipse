package br.com.travelmate.managerBean.highschool;

import br.com.travelmate.bean.ControlerBean;
import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;  
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controlehighschool; 
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parcelamentopagamento; 
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
public class HighSchoolMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Highschool> listaHighSchool;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private String numeroFichas;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String obsTM;
	private int idVenda;
	private String linkIdGerente;
	private String linkIdFranquias;
	private boolean habilitarUnidade = true;
	private Highschool highSchool;
	private String voltar;
	private Fornecedorcidade fornecedorcidade;
	private String dataInicioPrograma;
	private Pais pais;
	private List<Pais> listaPais;
	private List<Fornecedorcidade> listaFornecedorProduto;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private Integer nFichasFinanceiro;
	private List<Highschool> listaVendasFinalizada;
	private List<Highschool> listaVendasAndamento;
	private List<Highschool> listaVendasCancelada;
	private List<Highschool> listaVendasProcesso;
	private List<Highschool> listaVendasFinanceiro;

	@PostConstruct()
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			gerarListaHighSchool();
			listaUnidadeNegocio = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				linkIdGerente = "true";
				linkIdFranquias = "false";
			} else {
				linkIdGerente = "false";
				linkIdFranquias = "true";
			}
			gerarlistaPais();
			gerarlistaFornecedor();
		}
	}

	public List<Highschool> getListaHighSchool() {
		return listaHighSchool;
	}

	public void setListaHighSchool(List<Highschool> listaHighSchool) {
		this.listaHighSchool = listaHighSchool;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
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

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getLinkIdGerente() {
		return linkIdGerente;
	}

	public void setLinkIdGerente(String linkIdGerente) {
		this.linkIdGerente = linkIdGerente;
	}

	public String getLinkIdFranquias() {
		return linkIdFranquias;
	}

	public void setLinkIdFranquias(String linkIdFranquias) {
		this.linkIdFranquias = linkIdFranquias;
	}

	public Highschool getHighSchool() {
		return highSchool;
	}

	public void setHighSchool(Highschool highSchool) {
		this.highSchool = highSchool;
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

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public String getDataInicioPrograma() {
		return dataInicioPrograma;
	}

	public void setDataInicioPrograma(String dataInicioPrograma) {
		this.dataInicioPrograma = dataInicioPrograma;
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

	public List<Fornecedorcidade> getListaFornecedorProduto() {
		return listaFornecedorProduto;
	}

	public void setListaFornecedorProduto(List<Fornecedorcidade> listaFornecedorProduto) {
		this.listaFornecedorProduto = listaFornecedorProduto;
	}

	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public List<Highschool> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Highschool> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Highschool> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Highschool> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Highschool> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Highschool> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Highschool> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Highschool> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Highschool> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Highschool> listaVendasFinanceiro) {
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

	public String cadastrarFicha() {
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("idlead", idlead);
			return "cadHighSchool";
		} else {
			Mensagem.lancarMensagemWarn("Cambio do dia ainda não liberado", "");
		}
		return "";
	}

	public String editar(Highschool highSchool) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("highschool", highSchool); 
		session.setAttribute("idlead", highSchool.getVendas().getIdlead());
		return "cadHighSchool";
	}

	public void gerarListaHighSchool() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		String sql = "select h from Highschool h where ";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() + " and ";
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario()+" and ";
				}
			}
		}
		sql = sql + " h.vendas.dataVenda>='" + dataConsulta
				+ "' order by h.vendas.dataVenda desc, h.vendas.idvendas desc";
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		listaHighSchool = highSchoolFacade.listar(sql);
		if (listaHighSchool == null) {
			listaHighSchool = new ArrayList<Highschool>();
		}
		numeroFichas = "" + String.valueOf(listaHighSchool.size());
		gerarQuantidadesFichas();
	}

	public void pesquisar() {
		String sql = null;
		sql = "select h from Highschool h where h.vendas.cliente.nome like '%" + nome + "%'";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and h.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and h.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and h.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and h.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and h.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and h.vendas.situacao='" + situacao + "'";
		}
		if (pais != null && pais.getIdpais() != null) {
			sql = sql + " and h.vendas.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
		}
		if (fornecedorcidade != null && fornecedorcidade.getIdfornecedorcidade() != null) {
			sql = sql + " and h.vendas.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor();
		}
		if (dataInicioPrograma != null && dataInicioPrograma.length() > 0) {
			sql = sql + " and h.dataInicio like '" + dataInicioPrograma + "%'";
		}
		sql = sql + " order by h.vendas.dataVenda, h.vendas.cliente.nome";
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		listaHighSchool = highSchoolFacade.listar(sql);
		if (listaHighSchool == null) {
			listaHighSchool = new ArrayList<Highschool>();
		}
		numeroFichas = "" + String.valueOf(listaHighSchool.size());
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda = 0;
		gerarListaHighSchool();
	}

	public boolean imagemSituacaoUsuario(Highschool highschool) {
		if (highschool.getVendas().getSituacao().equals("FINALIZADA")) {
			highschool.setHabilitarImagemGerencial(false);
			highschool.setHabilitarImagemFranquia(true);
			highschool.setImagem("../../resources/img/finalizadoFicha.png");
			highschool.setTituloFicha("FICHA FINALIZADA");
		} else if (highschool.getVendas().getSituacao().equals("ANDAMENTO")
				&& !highschool.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5) {
				highschool.setHabilitarImagemGerencial(true);
				highschool.setHabilitarImagemFranquia(false);
			} else {
				highschool.setHabilitarImagemGerencial(false);
				highschool.setHabilitarImagemFranquia(true);
			}
			highschool.setImagem("../../resources/img/ficharestricao.png");
			highschool.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		}  else if (highschool.getVendas().getSituacao().equals("ANDAMENTO")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5) {
				highschool.setHabilitarImagemGerencial(true);
				highschool.setHabilitarImagemFranquia(false);
			} else {
				highschool.setHabilitarImagemGerencial(false);
				highschool.setHabilitarImagemFranquia(true);
			}
			highschool.setImagem("../../resources/img/amarelaFicha.png");
			highschool.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		} else if (highschool.getVendas().getSituacao().equals("CANCELADA")) {
			highschool.setHabilitarImagemGerencial(false);
			highschool.setHabilitarImagemFranquia(true);
			highschool.setImagem("../../resources/img/fichaCancelada.png");
			highschool.setTituloFicha("FICHA CANCELADA");
		} else if ((highschool.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (highschool.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				highschool.setHabilitarImagemGerencial(true);
				highschool.setHabilitarImagemFranquia(false);
			} else {
				highschool.setHabilitarImagemGerencial(false);
				highschool.setHabilitarImagemFranquia(true);
			}
			highschool.setImagem("../../resources/img/ficharestricao.png");
			highschool.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else {
			highschool.setHabilitarImagemGerencial(false);
			highschool.setHabilitarImagemFranquia(true);
			highschool.setImagem("../../resources/img/processoFicha.png");
			highschool.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return highschool.isHabilitarImagemGerencial();
	}

	public String corNome(Highschool highschool) {
		if (highschool.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Highschool highschool) {
		if (highschool.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Highschool highschool) {
		if (highschool.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public void buscarHighSchool(Highschool highschool) {
		this.highSchool = highschool;
	}

	public void salvarControle() throws SQLException {
		Controlehighschool controlehighschool = new Controlehighschool();
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		controlehighschool = highSchoolFacade.consultarControle(highSchool.getVendas().getIdvendas());
		if (controlehighschool == null) {
			ControlerBean controlerBean = new ControlerBean();
			float valorPrevisto = 0.0f;
			if (highSchool.getVendas().getVendascomissao() != null) {
				valorPrevisto = highSchool.getVendas().getVendascomissao().getValorfornecedor();
			}
			controlerBean.salvarControleHighSchool(highSchool.getVendas(), highSchool, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
		}
	}

	public String documentacao(Highschool highschool) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", highschool.getVendas());
		voltar = "consultaHighSchool";
		session.setAttribute("voltar", voltar);
		return "consArquivos";
	}

	public String gerarRelatorioRecibo(Highschool highschool) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(highschool.getVendas().getIdvendas());
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
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito autorizado")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
			}
		}
		if (valorRecibo > 0.0f) {
			Map parameters = new HashMap();
			parameters.put("idvendas", highschool.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"reciboPagamento-" + highschool.getVendas().getIdvendas() + ".pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Highschool highschool) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", highschool.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioFicha(Highschool highschool) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/highSchool/FichaHighSchoolPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("idvendas", highschool.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaHighSchool" + highschool.getVendas().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(Highschool highschool) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/highSchool/contratoHighSchoolPagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("idvendas", highschool.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"contratoHighSchool" + highschool.getVendas().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String cancelarVenda(Vendas venda) {
		if (venda.getSituacao().equalsIgnoreCase("FINALIZADA") || venda.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
		} else if (venda.getSituacao().equalsIgnoreCase("PROCESSO")) {
			VendasFacade vendasFacade = new VendasFacade();
			venda.setSituacao("CANCELADA");
			vendasFacade.salvar(venda);
			gerarListaHighSchool();
		}
		return "";
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
		if ((highSchool.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (highSchool.getVendas().isRestricaoparcelamento())) {
			if (usuarioLogadoMB.isFinanceiro()) {
				Vendas venda = highSchool.getVendas();
				venda.setRestricaoparcelamento(false);
				VendasFacade vendasFacade = new VendasFacade();
				venda = vendasFacade.salvar(venda);
				highSchool.setVendas(venda);
				Formapagamento forma = highSchool.getVendas().getFormapagamento();
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

	public String boletos(Highschool highschool) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(highschool.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + highschool.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(highschool.getVendas().getIdvendas()));
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

	public String informacoes(Highschool highschool) {
		if (highschool.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", highschool.getVendas());
			voltar = "consultaHighSchool";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}

	public void gerarlistaPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}

	public void gerarlistaFornecedor() {
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedorProduto = fornecedorCidadeFacade
				.listar("select f from Fornecedorcidade f where f.produtos.idprodutos="
						+ aplicacaoMB.getParametrosprodutos().getHighSchool()
						+ " and f.ativo=1 group by f.fornecedor.idfornecedor order by f.fornecedor.nome");
		if (listaFornecedorProduto == null) {
			listaFornecedorProduto = new ArrayList<Fornecedorcidade>();
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
		for (int i = 0; i < listaHighSchool.size(); i++) {
			if (!listaHighSchool.get(i).getValoreshighschool().getAnoinicio().equalsIgnoreCase("0000") && listaHighSchool.get(i).getValoreshighschool().getAnoinicio().length() ==4) {
				listaHighSchool.get(i).setInicioAnoInicio(listaHighSchool.get(i).getValoreshighschool().getInicio() + " - " + listaHighSchool.get(i).getValoreshighschool().getAnoinicio());
			}
			if (listaHighSchool.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaHighSchool.get(i));
			}else if(listaHighSchool.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaHighSchool.get(i));
			}else if(listaHighSchool.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaHighSchool.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaHighSchool.get(i));
			}else if(listaHighSchool.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaHighSchool.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;   
				listaVendasCancelada.add(listaHighSchool.get(i));
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
	
	public String retornarIconeObsTM(Highschool highschool){
		if (highschool.getObstm() != null && highschool.getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	public String visualizarContasReceber(Highschool highschool) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", highschool.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
}
