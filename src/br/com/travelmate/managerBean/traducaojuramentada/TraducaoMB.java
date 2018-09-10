package br.com.travelmate.managerBean.traducaojuramentada;

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

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.TraducaoJuramentadaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Traducaojuramentada;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class TraducaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Traducaojuramentada> listaTraducao; 
	private int idVenda;
	private String nomeCliente;
	private Date dataInicio;
	private Date dataFinal;
	private Unidadenegocio unidade;
	private Usuario usuario;
	private List<Unidadenegocio> listaUnidade;
	private List<Usuario> listaUsuario;
	private boolean liberarComboUsuairo = true; 
	private boolean habilitarUnidade = true;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;  
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private Integer nFichasFinanceiro;
	private List<Traducaojuramentada> listaVendasFinalizada;
	private List<Traducaojuramentada> listaVendasAndamento;
	private List<Traducaojuramentada> listaVendasCancelada;
	private List<Traducaojuramentada> listaVendasProcesso;
	private List<Traducaojuramentada> listaVendasFinanceiro;
	private String numeroFichas;


	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			setNomeCliente(""); 
			listarUnidade();
			gerarListaTraducao();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidade = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				liberarComboUsuario();
				listarUsuario();
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
 
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Unidadenegocio getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
 
	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	} 
	
	
	public List<Traducaojuramentada> getListaTraducao() {
		return listaTraducao;
	}

	public void setListaTraducao(List<Traducaojuramentada> listaTraducao) {
		this.listaTraducao = listaTraducao;
	}

	public boolean isLiberarComboUsuairo() {
		return liberarComboUsuairo;
	}

	public void setLiberarComboUsuairo(boolean liberarComboUsuairo) {
		this.liberarComboUsuairo = liberarComboUsuairo;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public List<Traducaojuramentada> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Traducaojuramentada> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Traducaojuramentada> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Traducaojuramentada> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Traducaojuramentada> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Traducaojuramentada> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Traducaojuramentada> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Traducaojuramentada> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Traducaojuramentada> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Traducaojuramentada> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String editar(Traducaojuramentada traducaojuramentada) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("traducaojuramentada", traducaojuramentada);  
		return "cadTraducao";
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

	public String calculaJuros() {
		RequestContext.getCurrentInstance().openDialog("calculaJuros");
		return "";
	}

	public void pesquisar() {
		String sql;
		sql = "select t from Traducaojuramentada t where t.vendas.cliente.nome like '%" +nomeCliente+ "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidade != null) {
				sql = sql + " and t.vendas.unidadenegocio.idunidadeNegocio=" + unidade.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and t.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and t.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and t.vendas.idvendas=" + idVenda;
		}
		if (dataInicio != null && dataFinal != null) {
			sql = sql + " and t.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and t.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		} 
		if (usuario != null) {
			sql = sql + " and t.vendas.usuario.idusuario=" + usuario.getIdusuario() + " ";
		} 
		sql = sql + " order by t.vendas.idvendas desc, t.vendas.dataVenda desc, t.vendas.cliente.nome"; 
		TraducaoJuramentadaFacade traducaoJuramentadaFacade = new TraducaoJuramentadaFacade();
		listaTraducao = traducaoJuramentadaFacade.lista(sql);
		if (listaTraducao == null) {
			listaTraducao = new ArrayList<>();
		}
		numeroFichas = "" + String.valueOf(listaTraducao.size());
		gerarQuantidadesFichas();
	}

	public void listarUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
	}

	public void listarUsuario() {
		if (unidade != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listaUsuarioUnidade(unidade.getIdunidadeNegocio());
		} else {
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void liberarComboUsuario() {
		if (unidade != null) {
			liberarComboUsuairo = false;
			listarUsuario();
		} else {
			liberarComboUsuairo = true;
			listaUsuario = new ArrayList<Usuario>();
		}
	}  

	public void limparPesquisa() {
		unidade = new Unidadenegocio();
		usuario = new Usuario();
		nomeCliente = "";
		dataInicio = null;
		dataFinal = null;  
		gerarListaTraducao();
	}
 
	public String vendaNaoMatriz() {
		String vendaMatriz = "N";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadTraducao";
	}

	public String vendaMatriz() {
		String vendaMatriz = "S";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadTraducao";
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
			
			venda.setSituacao("CANCELADA");
			vendasDao.salvar(venda); 
			gerarListaTraducao();
		}
		return "";
	}
 
	public String botoesHabilitados(Traducaojuramentada traducaojuramentada) {
		if (traducaojuramentada.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	} 

	public String boletos(Traducaojuramentada traducaojuramentada) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(traducaojuramentada.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas="
					+ traducaojuramentada.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(traducaojuramentada.getVendas().getIdvendas()));
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
	
	public String informacoes(Traducaojuramentada traducaojuramentada) {
		if (traducaojuramentada.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";  
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", traducaojuramentada.getVendas());
			String voltar = "consTraducao";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
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
	
	
	public String vendaMatriz(Traducaojuramentada traducaojuramentada){
		if(traducaojuramentada.getVendas().getVendasMatriz().equalsIgnoreCase("S")){
			return "Matriz";
		}else{
			return "Não Matriz";
		}
	}
	
	public void gerarListaTraducao() {
		if (usuarioLogadoMB.getUsuario() != null || usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			String sql = "Select t from Traducaojuramentada t where t.vendas.dataVenda>='" + dataConsulta;
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + " and t.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sql = sql + " and t.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}
			sql = sql +"' order by t.vendas.dataVenda desc, t.vendas.cliente.nome";
			TraducaoJuramentadaFacade traducaoJuramentadaFacade = new TraducaoJuramentadaFacade();
			listaTraducao = traducaoJuramentadaFacade.lista(sql);
			if (listaTraducao == null) {
				listaTraducao = new ArrayList<>();
			} 
			numeroFichas = "" + String.valueOf(listaTraducao.size());
			gerarQuantidadesFichas();
		}  
	}
	
	public void imprimirRecibo(Traducaojuramentada traducaojuramentada) {
		float valorRecibo = 0.0f;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/recibo/reciboSeguro.jasper");
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = formaPagamentoFacade.consultar(traducaojuramentada.getVendas().getIdvendas());
		List<Parcelamentopagamento> listaParcelamento = forma.getParcelamentopagamentoList();
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
			Map<String, Object> parameters = new HashMap<String, Object>();
			try {
				parameters.put("idvendas", traducaojuramentada.getVendas().getIdvendas());
				File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
				BufferedImage logo = ImageIO.read(f);
				parameters.put("logo", logo);
				String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
				parameters.put("valorExtenso", valorExtenso);
				parameters.put("valorRecibo", valorRecibo);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
			try {
				gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"reciboPagamento-Traducao-" + traducaojuramentada.getIdtraducaojuramentada() + ".pdf", null);
			} catch (JRException | IOException | SQLException e) {
				e.printStackTrace();
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
	}
	
	public String corNome(Traducaojuramentada traducaojuramentada) {
		if (traducaojuramentada.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}
	
	
	public boolean imagemSituacaoUsuario(Traducaojuramentada traducaojuramentada) {
		if (traducaojuramentada.getVendas().getSituacao().equals("FINALIZADA")) {
			traducaojuramentada.setImagem("../../resources/img/finalizadoFicha.png");
			traducaojuramentada.setTituloFicha("FICHA FINALIZADA");
		} else if (traducaojuramentada.getVendas().getSituacao().equals("ANDAMENTO")
				&& !traducaojuramentada.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			traducaojuramentada.setImagem("../../resources/img/ficharestricao.png");
			if (traducaojuramentada.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				traducaojuramentada.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				traducaojuramentada.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else if (traducaojuramentada.getVendas().getSituacao().equals("ANDAMENTO")) {
			traducaojuramentada.setImagem("../../resources/img/amarelaFicha.png");
			traducaojuramentada.setTituloFicha("ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)");
		} else if (traducaojuramentada.getVendas().getSituacao().equals("CANCELADA")) {
			traducaojuramentada.setImagem("../../resources/img/fichaCancelada.png");
			traducaojuramentada.setTituloFicha("FICHA CANCELADA");
		} else if ((traducaojuramentada.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (traducaojuramentada.getVendas().isRestricaoparcelamento())) {
			traducaojuramentada.setImagem("../../resources/img/ficharestricao.png");
			traducaojuramentada.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else {
			traducaojuramentada.setImagem("../../resources/img/processoFicha.png");
			traducaojuramentada.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
		}
		return true;
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
		for (int i = 0; i < listaTraducao.size(); i++) {
			if (listaTraducao.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaTraducao.get(i));
			}else if(listaTraducao.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaTraducao.get(i));
			}else if(listaTraducao.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaTraducao.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaTraducao.get(i));
			}else if(listaTraducao.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaTraducao.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaTraducao.get(i));
			}
		}
	}
}
