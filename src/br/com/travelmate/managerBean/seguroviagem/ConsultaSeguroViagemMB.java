package br.com.travelmate.managerBean.seguroviagem;

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
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
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
public class ConsultaSeguroViagemMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Seguroviagem> listaSeguro;
	private Seguroviagem seguroViagem;
	private String nomeCliente;
	private Date dataInicio;
	private Date dataFinal;
	private Unidadenegocio unidade;
	private Usuario usuario;
	private List<Unidadenegocio> listaUnidade;
	private List<Usuario> listaUsuario;
	private boolean librarComboUsuairo = true;
	private int idVenda;
	private boolean habilitarUnidade = true;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private String tipoEmissao;
	private Date dataSeguroInicial;
	private Date dataSeguroFinal;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private Integer nFichasFinanceiro;
	private List<Seguroviagem> listaVendasFinalizada;
	private List<Seguroviagem> listaVendasAndamento;
	private List<Seguroviagem> listaVendasCancelada;
	private List<Seguroviagem> listaVendasProcesso;
	private List<Seguroviagem> listaVendasFinanceiro;
	private String numeroFichas;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			setNomeCliente("");
			carregarListaSeguro();
			listarUnidade();
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

	public List<Seguroviagem> getListaSeguro() {
		return listaSeguro;
	}

	public void setListaSeguro(List<Seguroviagem> listaSeguro) {
		this.listaSeguro = listaSeguro;
	}

	public Seguroviagem getSeguroViagem() {
		return seguroViagem;
	}

	public void setSeguroViagem(Seguroviagem seguroViagem) {
		this.seguroViagem = seguroViagem;
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

	public boolean isLibrarComboUsuairo() {
		return librarComboUsuairo;
	}

	public void setLibrarComboUsuairo(boolean librarComboUsuairo) {
		this.librarComboUsuairo = librarComboUsuairo;
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
	
	

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public String getTipoEmissao() {
		return tipoEmissao;
	}

	public void setTipoEmissao(String tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}

	public Date getDataSeguroInicial() {
		return dataSeguroInicial;
	}

	public void setDataSeguroInicial(Date dataSeguroInicial) {
		this.dataSeguroInicial = dataSeguroInicial;
	}

	public Date getDataSeguroFinal() {
		return dataSeguroFinal;
	}

	public void setDataSeguroFinal(Date dataSeguroFinal) {
		this.dataSeguroFinal = dataSeguroFinal;
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

	public List<Seguroviagem> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Seguroviagem> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Seguroviagem> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Seguroviagem> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Seguroviagem> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Seguroviagem> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Seguroviagem> getListaVendasProcesso() {
		return listaVendasProcesso;
	}

	public void setListaVendasProcesso(List<Seguroviagem> listaVendasProcesso) {
		this.listaVendasProcesso = listaVendasProcesso;
	}

	public List<Seguroviagem> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Seguroviagem> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public void carregarListaSeguro() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		String sql = "select s from Seguroviagem s where s.possuiSeguro='Sim'";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and s.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and s.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + " and s.vendas.dataVenda>='" + dataConsulta
				+ "' order by s.vendas.idvendas desc, s.vendas.dataVenda desc";
		listaSeguro = seguroViagemFacade.listar(sql);
		if (listaSeguro == null) {
			listaSeguro = new ArrayList<Seguroviagem>();
		}
		numeroFichas = "" + String.valueOf(listaSeguro.size());
		gerarQuantidadesFichas();
	}

	public String editar(Seguroviagem seguroviagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("seguro", seguroviagem); 
		session.setAttribute("idlead", seguroviagem.getVendas().getIdlead());
		return "fichaSeguroViagem";
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
		sql = "select s from Seguroviagem s where s.possuiSeguro='Sim' and s.vendas.cliente.nome like '%" + nomeCliente
				+ "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidade != null) {
				sql = sql + " and s.vendas.unidadenegocio.idunidadeNegocio=" + unidade.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and s.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and s.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and (s.vendas.idvendas=" + idVenda+ " or s.idvendacurso=" + idVenda +")";
		}
		if (dataInicio != null && dataFinal != null) {
			sql = sql + " and s.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and s.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		} 
		if (usuario != null) {
			sql = sql + " and s.vendas.usuario.idusuario=" + usuario.getIdusuario() + " ";
		}
		if (dataSeguroInicial != null && dataSeguroFinal != null) {
			sql = sql + " and s.dataInicio>='" + Formatacao.ConvercaoDataSql(dataSeguroInicial) + "'";
			sql = sql + " and s.dataTermino<='" + Formatacao.ConvercaoDataSql(dataSeguroFinal) + "'";
		}
		if (tipoEmissao!=null && !tipoEmissao.equals("Todos")){
			sql = sql + " and s.controle='" + tipoEmissao + "' ";
		}
		sql = sql + " order by s.vendas.idvendas desc, s.vendas.dataVenda desc, s.vendas.cliente.nome";
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		listaSeguro = seguroViagemFacade.listar(sql);
		if (listaSeguro == null) {
			listaSeguro = new ArrayList<Seguroviagem>();
		}
		numeroFichas = "" + String.valueOf(listaSeguro.size());
		gerarQuantidadesFichas();
	}

	public void listarUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar();
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
			librarComboUsuairo = false;
			listarUsuario();
		} else {
			librarComboUsuairo = true;
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void imprimirRecibo(Seguroviagem seguro) {
		float valorRecibo = 0.0f;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/recibo/reciboSeguro.jasper");
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = formaPagamentoFacade.consultar(seguro.getVendas().getIdvendas());
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
			Map parameters = new HashMap();
			try {
				parameters.put("idvendas", seguro.getVendas().getIdvendas());
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
						"reciboPagamento-SeguroViagem-" + seguro.getIdseguroViagem() + ".pdf", null);
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

	public void imprimirFicha(Seguroviagem seguro) {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (seguro.getIdvendacurso() > 0) {
			caminhoRelatorio = ("/reports/seguro/FichaSeguroCursoPagina01.jasper");
		} else {
			caminhoRelatorio = ("/reports/seguro/FichaSeguroPagina01.jasper");
		}
		Map parameters = new HashMap();
		try {
			if(seguro.getIdvendacurso()>0){
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = cursoFacade.consultarCursos(seguro.getIdvendacurso());
				if(curso!=null){
					seguro.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
					seguro.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
					seguro.setPaisDestino(curso.getPais());
					SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
					seguro = seguroViagemFacade.salvar(seguro);
				}
			}
			parameters.put("idvendas", seguro.getVendas().getIdvendas());
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		try {
			gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fichaSeguroViagem", null);
		} catch (JRException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void limparPesquisa() {
		unidade = new Unidadenegocio();
		usuario = new Usuario();
		nomeCliente = "";
		dataInicio = null;
		dataFinal = null;
		tipoEmissao="Todos";
		dataSeguroInicial=null;
		dataSeguroFinal=null;
		carregarListaSeguro();

	}

	public void finalizar(Seguroviagem seguroviagem) {
		seguroviagem.setControle("Finalizado");
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		seguroviagem = seguroViagemFacade.salvar(seguroviagem);
		carregarListaSeguro();
	}

	public String vendaNaoMatriz() {
		String vendaMatriz = "N";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "fichaSeguroViagem";
	}

	public String vendaMatriz() {
		String vendaMatriz = "S";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "fichaSeguroViagem";
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
			carregarListaSeguro();
		}
		return "";
	}

	public String corNome(Seguroviagem seguroviagem) {
		if (seguroviagem.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Seguroviagem seguroviagem) {
		if (seguroviagem.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Seguroviagem seguroviagem) {
		if (seguroviagem.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String tipoSeguro(Seguroviagem seguroviagem) {
		if (seguroviagem.getIdvendacurso() > 0) {
			return "Curso";
		} else {
			return "Avulso";
		}
	}

	public String boletos(Seguroviagem seguroviagem) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + seguroviagem.getVendas().getIdvendas()
				+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
				+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
		List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
		if (listaContas != null) {
			if (listaContas.size() > 0) {
				GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
				gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(seguroviagem.getVendas().getIdvendas()));
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
	
	public String informacoes(Seguroviagem seguroviagem) {
		if (seguroviagem.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";  
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", seguroviagem.getVendas());
			String voltar = "consultaSeguro";
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
	
	
	public String vendaMatriz(Seguroviagem seguroviagem){
		if(seguroviagem.getVendas().getVendasMatriz().equalsIgnoreCase("S")){
			return "Matriz";
		}else{
			return "Não Matriz";
		}
	}
	
	public boolean retornarBotaoContasReceber(Seguroviagem seguroviagem){
		if (seguroviagem.getIdvendacurso() == 0) {
			return true;
		}else{
			return false;
		}
	}
	
	public String visualizarContasReceber(Seguroviagem seguroviagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", seguroviagem.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	
	public boolean imagemSituacaoUsuario(Seguroviagem seguroviagem) {
		if (seguroviagem.getVendas().getSituacao().equals("FINALIZADA")) {
			seguroviagem.setImagem("../../resources/img/finalizadoFicha.png");
			seguroviagem.setTituloFicha("FICHA FINALIZADA");
		} else if (seguroviagem.getVendas().getSituacao().equals("ANDAMENTO")
				&& !seguroviagem.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			seguroviagem.setImagem("../../resources/img/ficharestricao.png");
			if (seguroviagem.getVendas().getSituacaofinanceiro().equalsIgnoreCase("P")) {
				seguroviagem.setTituloFicha("FINANCEIRO - PENDENTE (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}else {
				seguroviagem.setTituloFicha("FINANCEIRO - AGUARDANDO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
			}
		} else if (seguroviagem.getVendas().getSituacao().equals("ANDAMENTO")) {
			seguroviagem.setImagem("../../resources/img/amarelaFicha.png");
			seguroviagem.setTituloFicha("ANDAMENTO (FICHA EM ANDAMENTO)");
		} else if (seguroviagem.getVendas().getSituacao().equals("CANCELADA")) {
			seguroviagem.setImagem("../../resources/img/fichaCancelada.png");
			seguroviagem.setTituloFicha("FICHA CANCELADA");
		} else if ((seguroviagem.getVendas().getSituacao().equalsIgnoreCase("PROCESSO"))
				&& (seguroviagem.getVendas().isRestricaoparcelamento())) {
			seguroviagem.setImagem("../../resources/img/ficharestricao.png");
			seguroviagem.setTituloFicha("FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)");
		} else {
			seguroviagem.setImagem("../../resources/img/processoFicha.png");
			seguroviagem.setTituloFicha("PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)");
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
		for (int i = 0; i < listaSeguro.size(); i++) {
			if (listaSeguro.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaSeguro.get(i));
			}else if(listaSeguro.get(i).getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
				nFichasProcesso = nFichasProcesso + 1;
				listaVendasProcesso.add(listaSeguro.get(i));
			}else if(listaSeguro.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
					&& !listaSeguro.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaSeguro.get(i));
			}else if(listaSeguro.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaSeguro.get(i));
			}else{
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaSeguro.get(i));
			}
		}
	}

}
