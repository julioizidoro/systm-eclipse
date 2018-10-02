package br.com.travelmate.managerBean.acomodacao;

import java.io.Serializable;
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

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AcomodacaoCursoFacade;
import br.com.travelmate.facade.AcomodacaoFacade;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Acomodacao;
import br.com.travelmate.model.Acomodacaocurso;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class AcomodacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Acomodacao acomodacao;
	private String cliente = "";
	private Date dataInicio;
	private Date dataFinal;
	private Unidadenegocio unidadenegocio;
	private int idvenda = 0;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private List<Acomodacao> listaAcomodacao;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichaCancelada;
	private Integer nFichaFinanceiro;
	private List<Acomodacao> listaVendasFinalizada;
	private List<Acomodacao> listaVendasCancelada;
	private List<Acomodacao> listaVendasProcesso;
	private List<Acomodacao> listaVendasFinanceiro;
	private String obsTM;
	private int numeroFichas;
	
	
	@PostConstruct
	public void init() {
		gerarListaUnidadeNegocio();
		carregarListaVendasAcomodacao();
	}


	public Acomodacao getAcomodacao() {
		return acomodacao;
	}


	public void setAcomodacao(Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
		this.cliente = cliente;
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


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public int getIdvenda() {
		return idvenda;
	}


	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}


	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}


	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}
	
	
	
	
	public List<Acomodacao> getListaAcomodacao() {
		return listaAcomodacao;
	}


	public void setListaAcomodacao(List<Acomodacao> listaAcomodacao) {
		this.listaAcomodacao = listaAcomodacao;
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


	public Integer getnFichaCancelada() {
		return nFichaCancelada;
	}


	public void setnFichaCancelada(Integer nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}


	public Integer getnFichaFinanceiro() {
		return nFichaFinanceiro;
	}


	public void setnFichaFinanceiro(Integer nFichaFinanceiro) {
		this.nFichaFinanceiro = nFichaFinanceiro;
	}


	public List<Acomodacao> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}


	public void setListaVendasFinalizada(List<Acomodacao> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}


	public List<Acomodacao> getListaVendasCancelada() {
		return listaVendasCancelada;
	}


	public void setListaVendasCancelada(List<Acomodacao> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}


	public List<Acomodacao> getListaVendasProcesso() {
		return listaVendasProcesso;
	}


	public void setListaVendasProcesso(List<Acomodacao> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}


	public List<Acomodacao> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}


	public void setListaVendasFinanceiro(List<Acomodacao> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}


	public String getObsTM() {
		return obsTM;
	}


	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}


	public int getNumeroFichas() {
		return numeroFichas;
	}


	public void setNumeroFichas(int numeroFichas) {
		this.numeroFichas = numeroFichas;
	}


	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	public void carregarListaVendasAcomodacao() {
		if (usuarioLogadoMB.getUsuario() != null || usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			String sql = "Select a from Acomodacao a where a.vendas.dataVenda>='" + dataConsulta  + 
					"'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sql = sql + " and a.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}
			sql = sql + " order by a.vendas.dataVenda desc, a.vendas.cliente.nome";
			AcomodacaoFacade acomodacaoFacade = new AcomodacaoFacade();
			listaAcomodacao = acomodacaoFacade.lista(sql);
			if (listaAcomodacao == null) {
				listaAcomodacao = new ArrayList<Acomodacao>();
			}
		} else {
			if (listaAcomodacao == null) {
				listaAcomodacao = new ArrayList<Acomodacao>();
			}
		}
		numeroFichas = listaAcomodacao.size();
		gerarQuantidadesFichas();
	}
	
	public String editar(Acomodacao acomodacao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("acomodacao", acomodacao);
		session.setAttribute("cliente", acomodacao.getVendas().getCliente());
		session.setAttribute("lead", leadDao.consultar("SELECT l FROM Lead l WHERE l.cliente.idcliente=" + acomodacao.getVendas().getCliente().getIdcliente()));
		return "cadAcomodacao";
	}
	
	
	public void pesquisar() {
		boolean pesquisaSemParametro = true;
		String sql = null;
		sql = "Select a from Acomodacao a where "
				+ " a.vendas.cliente.nome like '%" + cliente + "%'";
		if (idvenda > 0) {
			sql = sql + " and a.vendas.idvendas=" + idvenda;
			pesquisaSemParametro = false;
		}
		if ((dataInicio != null) && (dataFinal != null)) {
			sql = sql + " and a.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and a.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			pesquisaSemParametro = false;
		}
		if ((unidadenegocio != null) && (unidadenegocio.getIdunidadeNegocio() != null)) {
			sql = sql + " and a.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			if ((dataInicio == null) && (dataFinal == null)) {
				Date dataconsulta = new Date();
				try {
					dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
				} catch (Exception e) {
					  
				}
				sql = sql + " and a.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
			pesquisaSemParametro = false;
		}
		if (pesquisaSemParametro){
			if ((dataInicio == null) && (dataFinal == null)) {
				Date dataconsulta = new Date();
				try {
					dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
				} catch (Exception e) {
					  
				}
				sql = sql + " and a.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
		}
		sql = sql + "  order by a.vendas.dataVenda desc";
		AcomodacaoFacade acomodacaoFacade = new AcomodacaoFacade();
		listaAcomodacao = acomodacaoFacade.lista(sql);
		if (listaAcomodacao == null) {
			listaAcomodacao = new ArrayList<Acomodacao>();
		}
		numeroFichas = listaAcomodacao.size();
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		carregarListaVendasAcomodacao();
		cliente = "";
		dataFinal = null;
		dataInicio = null;
		unidadenegocio = null;
		idvenda = 0;
	}
	
	
	public void gerarQuantidadesFichas(){
		nFichaCancelada = 0;
		nFichasFinalizadas = 0;
		nFichasProcesso = 0;
		nFichaFinanceiro = 0;
		listaVendasFinalizada = new ArrayList<>();
		listaVendasCancelada = new ArrayList<>();
		listaVendasProcesso = new ArrayList<>();
		listaVendasFinanceiro = new ArrayList<>();
		for (int i = 0; i < listaAcomodacao.size(); i++) {
			if (listaAcomodacao.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaAcomodacao.get(i));
			}else if(listaAcomodacao.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaAcomodacao.get(i));
			}else if(listaAcomodacao.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaAcomodacao.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichaFinanceiro = nFichaFinanceiro + 1;
				listaVendasFinanceiro.add(listaAcomodacao.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaAcomodacao.get(i));
			}
		}
	}
	
	


	public String corNome(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}
	
	
	public void retornoDialogoEditar() {
		carregarListaVendasAcomodacao();
	}
	
	
	public String cancelarVenda(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
				|| acomodacao.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", acomodacao.getVendas());
			session.setAttribute("voltar", "consAcomodacao");
			return "emissaocancelamento";
		} else if (acomodacao.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			acomodacao.getVendas().setSituacao("CANCELADA");
			vendasDao.salvar(acomodacao.getVendas());
			carregarListaVendasAcomodacao();
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

	public String informacoes(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", acomodacao.getVendas());
			String voltar = "consAcomodacao";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
	
	
	public String documentacao(Acomodacao acomodacao) {
		boolean validar = true;
		if (acomodacao.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = acomodacao.getVendas().getDatavalidade();
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
			session.setAttribute("vendas", acomodacao.getVendas());
			String voltar = "consAcomdacao";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
	}
	
	
	public String retornarIconeObsTM(Acomodacao acomodacao){
		if (acomodacao.getVendas().getObstm() != null && acomodacao.getVendas().getObstm().length() > 0) {
			return "../../resources/img/obsVermelha.png";
		}
		return "../../resources/img/obsFicha.png";
	}

	public String obsTM(Acomodacao acomodacao) {
		obsTM = acomodacao.getVendas().getObstm();
		return obsTM;
	}
	
	
	public String boletos(Acomodacao acomodacao) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(acomodacao.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + acomodacao.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(acomodacao.getVendas().getIdvendas()), true);
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
	
	
	public void verificarIdCredito(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getCancelamento() != null) {
			if (acomodacao.getVendas().getCancelamento().getCancelamentocredito() != null) {
				if (acomodacao.getVendas().getCancelamento().getCancelamentocredito().getCredito().getTipocredito().equalsIgnoreCase("Crédito")) {
					Credito credito = acomodacao.getVendas().getCancelamento().getCancelamentocredito().getCredito();
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
	
	
	public String relatorioTermoQuitacao(Acomodacao acomodacao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		Cancelamento cancelamento = cancelamentoFacade.consultar(acomodacao.getVendas().getIdvendas());
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}
	
	
	public String imagemSituacaoUsuario(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getSituacao().equals("FINALIZADA")) {
			
			return "../../resources/img/finalizadoFicha.png";
		} else if (acomodacao.getVendas().getSituacao().equals("ANDAMENTO")
				&& !acomodacao.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			return "../../resources/img/ficharestricao.png";
		} else if (acomodacao.getVendas().getSituacao().equals("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		}else if (acomodacao.getVendas().getSituacao().equals("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		} else if ((acomodacao.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (acomodacao.getVendas().isRestricaoparcelamento())) {
			return "../../resources/img/ficharestricao.png";
		} else {
			return "../../resources/img/processoFicha.png";
		}
	}
	
	
	public boolean retornarAcomodacao(Acomodacao acomodacao) {
		AcomodacaoCursoFacade acomodacaoCursoFacade = new AcomodacaoCursoFacade();
		Acomodacaocurso acomodacaocurso = acomodacaoCursoFacade.consultar("SELECT a FROM Acomodacaocurso a WHERE a.acomodacao.idacomodacao=" + acomodacao.getIdacomodacao());
		if (acomodacaocurso == null || acomodacaocurso.getIdacomodacaocurso() == null) {
			return false;
		}
		return true;
	}

	
	public void dadosCancelamento(Acomodacao acomodacao) {
		if (acomodacao.getVendas().getSituacao().equalsIgnoreCase("CANCELADA") && acomodacao.getVendas().getCancelamento() != null) {
			Cancelamento cancelamento = acomodacao.getVendas().getCancelamento();
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
