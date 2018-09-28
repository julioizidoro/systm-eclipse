
package br.com.travelmate.managerBean.demipair;

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
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Controledemipair;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class DemiPairMB implements Serializable {

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
	private List<Demipair> listaDemipair;
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
	private Demipair demipair;
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
	private Integer nFichasFinanceiro;
	private List<Demipair> listaVendasFinalizada;
	private List<Demipair> listaVendasAndamento;
	private List<Demipair> listaVendasCancelada;
	private List<Demipair> listaVendasProcesso;
	private List<Demipair> listaVendasFinanceiro;
	private String chamadaTela = "";

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaDemipair = (List<Demipair>) session.getAttribute("listaDemipair");
		session.removeAttribute("listaDemipair");
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu")) || listaDemipair == null || listaDemipair.size() == 0) {
				carregarListaVendas();
			}else {
				gerarQuantidadesFichas();
			}
			listaUnidadeNegocio = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
	    		habilitarUnidade = false;
	    		linkIdVenda = "true";
				linkIdVendaFranquias = "false";
	    	}else {
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

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String getObsTM() {
		return obsTM;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
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

	public List<Demipair> getListaDemipair() {
		return listaDemipair;
	}

	public void setListaDemipair(List<Demipair> listaDemipair) {
		this.listaDemipair = listaDemipair;
	}

	public Demipair getDemipair() {
		return demipair;
	}

	public void setDemipair(Demipair demipair) {
		this.demipair = demipair;
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

	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public List<Demipair> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Demipair> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Demipair> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Demipair> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Demipair> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Demipair> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Demipair> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Demipair> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Demipair> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Demipair> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public String cadastrarFicha() {
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("idlead", idlead);
			return "cadDemiPair";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}

	public boolean imagemSituacaoUsuario(Demipair demipair){
        if (demipair.getVendas().getSituacao().equals("FINALIZADA")) {
        	demipair.setHabilitarImagemGerencial(false);
        	demipair.setHabilitarImagemFranquia(true);
        	demipair.setImagem("../../resources/img/finalizadoFicha.png");
        	demipair.setTituloFicha("FICHA FINALIZADA");
        } else if (demipair.getVendas().getSituacao().equals("ANDAMENTO")
				&& !demipair.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
				demipair.setHabilitarImagemGerencial(true);
				demipair.setHabilitarImagemFranquia(false);
			} else {
				demipair.setHabilitarImagemGerencial(false);
				demipair.setHabilitarImagemFranquia(true);
			}
			demipair.setImagem("../../resources/img/ficharestricao.png");
			if (demipair.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				demipair.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				demipair.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else if(demipair.getVendas().getSituacao().equals("CANCELADA")){
        	demipair.setHabilitarImagemGerencial(false);
        	demipair.setHabilitarImagemFranquia(true);
            demipair.setImagem("../../resources/img/fichaCancelada.png");
            demipair.setTituloFicha("FICHA CANCELADA");
        }else {
        	demipair.setHabilitarImagemGerencial(false);
        	demipair.setHabilitarImagemFranquia(true);
        	demipair.setImagem("../../resources/img/processoFicha.png");
        	demipair.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
        }
        return demipair.isHabilitarImagemGerencial();
    }
	
	public String corNome(Demipair demipair){
        if (demipair.getVendas().getSituacao().equals("CANCELADA")) {
        	return "color:#808080;text-decoration: line-through;";
        }return "color:#000000;";
    }
    
    public String cor(Demipair demipair){
        if (demipair.getVendas().getSituacao().equals("CANCELADA")) {
        	return "color:#808080;";
        }return "color:#000000;";
    }
    
    public String botoesHabilitados(Demipair demipair){
        if (demipair.getVendas().getSituacao().equals("CANCELADA")) {
        	return "true";
        }return "false";
    }

	public String obsTMselecionar(Demipair demipair) {
		obsTM = demipair.getObstm();
		return obsTM;
	}

	public String gerarRelatorioFicha(Demipair demipair) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/demipair/FichaDemipairPagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idvendas", demipair.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fichaDemipair"+demipair.getVendas().getIdvendas()+".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(Demipair demipair) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/demipair/contratoDemipairPagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idvendas", demipair.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "contratoDemipair"+demipair.getVendas().getIdvendas()+".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public void carregarListaVendas() {
		String sql = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		sql = "Select d from Demipair d where ";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " d.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() +" and"; 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " d.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario()+" and ";
				}
			}
		}
		sql = sql + " d.vendas.dataVenda>='" + dataConsulta + "' order by d.vendas.dataVenda desc";
		DemipairFacade demipairFacade = new DemipairFacade();
		listaDemipair = demipairFacade.lista(sql);
		if (listaDemipair == null) {
			listaDemipair = new ArrayList<Demipair>();
		}
		numeroFichas = "" + String.valueOf(listaDemipair.size());
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda=0;
		carregarListaVendas();
	}

	public void pesquisarLista() {
		String sql = null;
		sql = "Select d from Demipair d where d.vendas.cliente.nome like '%" + nome + "%' ";
		 if (idVenda>0){
	    	sql = sql + " and d.vendas.idvendas=" + idVenda;
	    }
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and d.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and d.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and d.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}

		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and d.dataInscricao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and d.dataInscricao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
			sql = sql + " and d.vendas.dataVenda>='" + dataConsulta + "'";
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and d.vendas.situacao='" + situacao + "'";
		}
		sql = sql + " order by d.vendas.dataVenda, d.vendas.cliente.nome";
		DemipairFacade demipairFacade = new DemipairFacade();
		listaDemipair = demipairFacade.lista(sql);
		if (listaDemipair == null) {
			listaDemipair = new ArrayList<Demipair>();
		}
		numeroFichas = "" + String.valueOf(listaDemipair.size());
		gerarQuantidadesFichas();
	}

	public String gerarRelatorioRecibo(Demipair demipair) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(demipair.getVendas().getIdvendas());
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
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idvendas", demipair.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla());
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
		    String moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		    parameters.put("moedaNacional", moedaNacional);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reciboPagamento.pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}

	public String gerarRelatorioTermoVisto(Demipair demipair) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idcliente", demipair.getVendas().getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(DemiPairMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String editar(Demipair demipair) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("demipair", demipair); 
		session.setAttribute("idlead", demipair.getVendas().getIdlead());
		return "cadDemiPair";
	}

	public void cancelar(Demipair demipair) {
		this.demipair = demipair;
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
//			carregarListaVendas();
//		}
//		return "";
//	}
	
	public void salvarControle() throws SQLException{
    	Controledemipair controledemipair = new Controledemipair();
    	DemipairFacade demiFacade = new DemipairFacade();
    	controledemipair = demiFacade.consultarControle(demipair.getVendas().getIdvendas());
    	if(controledemipair==null){
    		ControlerBean controlerBean = new ControlerBean();
    		float valorPrevisto = 0.0f;
    		if (demipair.getVendas().getVendascomissao()!=null){
    			valorPrevisto = demipair.getVendas().getVendascomissao().getValorfornecedor();
    		}
			controlerBean.salvarControleDemipair(demipair.getVendas(), demipair, valorPrevisto);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
    	}else{
    		Mensagem.lancarMensagemErro("Atenção", "Controle já existente.");
    	}
    }
    
    public void dialogSalvarControle(Demipair demipair){
    	this.demipair=demipair;
    }
    
	public String documentacao(Demipair demipair) {
		boolean validar = true;
		if (demipair.getVendas().getSituacao().equalsIgnoreCase("PROCESSO") && usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = demipair.getVendas().getDatavalidade();

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
			session.setAttribute("vendas", demipair.getVendas());
			voltar = "consultaDemiPair";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
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
    	if ((demipair.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) && (demipair.getVendas().isRestricaoparcelamento())){
    		if (usuarioLogadoMB.isFinanceiro()){
    			Vendas venda = demipair.getVendas();
    			venda.setRestricaoparcelamento(false);
    			
    			venda = vendasDao.salvar(venda);
    			demipair.setVendas(venda);
    			Formapagamento forma = demipair.getVendas().getFormapagamento();
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
    
    
    
	public String boletos(Demipair demipair) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(demipair.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + demipair.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(demipair.getVendas().getIdvendas()), true);
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
    
    
    public String informacoes(Demipair demipair) {
		if (demipair.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";  
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", demipair.getVendas());
			voltar = "consultaDemiPair";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
    
    
    public void gerarQuantidadesFichas(){
    	if (listaDemipair != null) {
    		numeroFichas = "" + String.valueOf(listaDemipair.size());
		}
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
  		for (int i = 0; i < listaDemipair.size(); i++) {
  			if (listaDemipair.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
  				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaDemipair.get(i));
  			}else if(listaDemipair.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
  				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaDemipair.get(i));
  			}else if(listaDemipair.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaDemipair.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaDemipair.get(i));
			} else if(listaDemipair.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
  				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaDemipair.get(i));
  			}else{
  				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaDemipair.get(i));
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
	
	
	public String retornarIconeObsTM(Demipair demipair){
		if (demipair.getObstm() != null && demipair.getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}
	
	
	public String visualizarContasReceber(Demipair demipair) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", demipair.getVendas());
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
			session.setAttribute("voltar", "consultaDemiPair");
			return "emissaocancelamento";
		} else if (vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			vendas.setSituacao("CANCELADA");
			vendasDao.salvar(vendas);
			carregarListaVendas();
		}
		return "";
	}   
	
	
	
	
	public String fichaDemiPair(Demipair demipair){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("demipair", demipair);
		session.setAttribute("listaDemipair", listaDemipair);
		return "fichasDemiPair";
	}
	
	public String contratoDemiPair(Demipair demipair){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("demipair", demipair);
		session.setAttribute("listaDemipair", listaDemipair);
		return "contratoDemiPair";
	}
	
	
	public void verificarIdCredito(Demipair demipair) {
		if (demipair.getVendas().getCancelamento() != null) {
			if (demipair.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (demipair.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = demipair.getVendas().getCancelamento().getCancelamentocredito().getCredito();
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
	
	public String relatorioTermoQuitacao(Demipair demipair) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(demipair.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}
	
	
	public void dadosCancelamento(Demipair demipair) {
		if (demipair.getVendas().getSituacao().equalsIgnoreCase("CANCELADA") && demipair.getVendas().getCancelamento() != null) {
			Cancelamento cancelamento = demipair.getVendas().getCancelamento();
			if (cancelamento != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("cancelamento", cancelamento);
				Map<String, Object> options = new HashMap<String, Object>();
				options.put("contentWidth", 400);
				RequestContext.getCurrentInstance().openDialog("dadosCancelamento", options, null);
			}else {
				Mensagem.lancarMensagemInfo("Venda sem informações do cancelamento", "");
			}
		}
	}
}
