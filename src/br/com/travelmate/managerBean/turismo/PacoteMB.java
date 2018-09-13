/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo;

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.PacoteCircuitoFacade;
import br.com.travelmate.facade.PacoteCruzeiroFacade;
import br.com.travelmate.facade.PacoteTransferFacade;
import br.com.travelmate.facade.PacoteTremFacade;
import br.com.travelmate.facade.PacotesCarroFacade;
import br.com.travelmate.facade.PacotesHotelFacade;
import br.com.travelmate.facade.PacotesPassagemFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Contasreceber; 
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotecircuito;
import br.com.travelmate.model.Pacotecruzeiro;
import br.com.travelmate.model.Pacotehotel;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacotetransfer;
import br.com.travelmate.model.Pacotetrem;
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

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PacoteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	private List<Pacotes> listaPacotes;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Pacotes> listaPacotesAgencia;
	private Pacotes pacotes;
	private String cliente = "";
	private Date dataInicio;
	private Date dataFinal;
	private Unidadenegocio unidadenegocio;
	private int idvenda = 0;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaPacotesAgencia = (List<Pacotes>) session.getAttribute("listaPacotesAgencia");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("listaPacotesAgencia");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Pacote")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			dataFinal = (Date) session.getAttribute("dataFinal");
			dataInicio = (Date) session.getAttribute("dataIni");
			unidadenegocio = (Unidadenegocio) session.getAttribute("unidade");
			cliente = (String) session.getAttribute("nomeClientePacote");
			session.removeAttribute("dataFinal");
			session.removeAttribute("dataIni");
			session.removeAttribute("unidade");
			session.removeAttribute("nomeClientePacote");
			idvenda = 0;
			if (cliente == null) {
				cliente = "";
			}
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				gerarListaInicial();
			}
			gerarListaUnidadeNegocio();
		}
	}

	public List<Pacotes> getListaPacotes() {
		return listaPacotes;
	}

	public void setListaPacotes(List<Pacotes> listaPacotes) {
		this.listaPacotes = listaPacotes;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Pacotes> getListaPacotesAgencia() {
		return listaPacotesAgencia;
	}

	public void setListaPacotesAgencia(List<Pacotes> listaPacotesAgencia) {
		this.listaPacotesAgencia = listaPacotesAgencia;
	}

	public Pacotes getPacotes() {
		return pacotes;
	}

	public void setPacotes(Pacotes pacotes) {
		this.pacotes = pacotes;
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

	public String novoPacotes() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("unidade", unidadenegocio);
		session.setAttribute("nomeClientePacote", cliente);
		session.setAttribute("dataIni", dataInicio);
		session.setAttribute("dataFinal", dataFinal);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadpacote";
	}

	public String editarPacote(Pacotes pacote) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pacote", pacote); 
		session.setAttribute("idlead", pacote.getVendas().getIdlead());
		return "cadPacoteOperadora";
	}

	public String editarPacoteAgencia(Pacotes pacote) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pacote", pacote);
		session.setAttribute("unidade", unidadenegocio);
		session.setAttribute("nomeClientePacote", cliente);
		session.setAttribute("dataIni", dataInicio);
		session.setAttribute("dataFinal", dataFinal);
		session.setAttribute("idlead", pacote.getVendas().getIdlead());
		return "cadPacote";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void gerarListaInicial() {
		String sql = null;
		sql = "Select p from Pacotes p where  p.operacao='agencia' and p.controle='Concluido'";
		Date dataconsulta = new Date();
		try {
			dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql = sql + " and p.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
		sql = sql + "  order by p.vendas.dataVenda desc";
		listaPacotesAgencia = GerarListas.listarPacotes(sql);
	}

	public void pesquisar() {
		boolean pesquisaSemParametro = true;
		String sql = null;
		sql = "Select p from Pacotes p where  p.operacao='agencia' and p.controle='Concluido'"
				+ " and p.vendas.cliente.nome like '%" + cliente + "%'";
		if (idvenda > 0) {
			sql = sql + " and p.vendas.idvendas=" + idvenda;
			pesquisaSemParametro = false;
		}
		if ((dataInicio != null) && (dataFinal != null)) {
			sql = sql + " and p.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and p.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			pesquisaSemParametro = false;
		}
		if ((unidadenegocio != null) && (unidadenegocio.getIdunidadeNegocio() != null)) {
			sql = sql + " and p.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			if ((dataInicio == null) && (dataFinal == null)) {
				Date dataconsulta = new Date();
				try {
					dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
				} catch (Exception e) {
					e.printStackTrace();
				}
				sql = sql + " and p.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
			pesquisaSemParametro = false;
		}
		if (pesquisaSemParametro){
			if ((dataInicio == null) && (dataFinal == null)) {
				Date dataconsulta = new Date();
				try {
					dataconsulta = Formatacao.SomarDiasDatas(dataconsulta, -30);
				} catch (Exception e) {
					e.printStackTrace();
				}
				sql = sql + " and p.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataconsulta) + "'";
			}
		}
		sql = sql + "  order by p.vendas.dataVenda desc";
		listaPacotesAgencia = GerarListas.listarPacotes(sql);
		pesquisar = "Sim";
	}

	public void limparPesquisa() {
		String sql = "Select p from Pacotes p where p.operacao='agencia' and p.controle='Concluido' ";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + "and p.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by p.vendas.dataVenda desc";
		listaPacotesAgencia = GerarListas.listarPacotes(sql);
		cliente = "";
		dataFinal = null;
		dataInicio = null;
		unidadenegocio = null;
		idvenda = 0;
		pesquisar = "Nao";
	}

	public void imprimirRecibo(Pacotes pacotes) {
		float valorRecibo = 0.0f;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/recibo/reciboSeguro.jasper");
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = formaPagamentoFacade.consultar(pacotes.getVendas().getIdvendas());
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
				parameters.put("idvendas", pacotes.getVendas().getIdvendas());
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
				gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reciboPagamento-Pacote.pdf",
						null);
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

	public String gerarSql(Pacotes pacotes) {
		String sql = "Select distinct vendas.dataVenda, vendas.valor as valorVenda, vendas.idvendas, pacotes.idpacotes,"
				+ "pacotes.datainicio, pacotes.datetermino, pacotes.valorgross, pacotes.descricao,"
				+ "pacotes.vendas_idvendas, unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia,"
				+ " unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, "
				+ "unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio,"
				+ " unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, "
				+ "unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, "
				+ "unidadeNegocio.cnpj as cnpjunidadeNegocio,cliente.nome, cliente.dataNascimento, cliente.paisnascimento, "
				+ "cliente.cidadenascimento, cliente.rg,cliente.bairro as clientebairro,"
				+ "cliente.sexo, cliente.numeropassaporte, cliente.dataExpedicaoPassaporte,cliente.validadePassaporte, "
				+ "cliente.tipologradouro as clientetipologradouro, cliente.logradouro as clientelogradouro,"
				+ " cliente.numero as clientenumero,cliente.fonecelular, cliente.fonecomercial, cliente.profissao,cliente.foneresidencial,"
				+ "cliente.email as emailcliente,cliente.escolaridade, cliente.nomePai, cliente.profissaopai, cliente.fonepai, cliente.nomemae,"
				+ "cliente.profissaomae, cliente.fonemae, usuario.nome as nomeUsuario, cliente.cidade as clientecidade, cliente.estado as clienteestado, cliente.pais as clientepais,"
				+ "unidadeNegocio.nomeFantasia,cliente.cep as clientecep,cliente.cpf as clientecpf, pacotetrecho.descritivo";
		boolean passageiro = false;
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				if (passageiro == false) {
					if ((pacotes.getPacotetrechoList().get(i).getPacotepassagemList() != null)
							&& (pacotes.getPacotetrechoList().get(i).getPacotepassagemList().size() > 0)) {
						sql = sql
								+ ",pacotepassagem.idpacotepassagem, pacotepassagempassageiro.nome as nomepassageiro, pacotepassagempassageiro.datanascimento as datapassageiro,"
								+ "pacotepassagempassageiro.categoria as categoriapassageiro";
						passageiro = true;
					}
				}
			}
		}
		sql = sql + " from vendas join pacotes on vendas.idvendas = pacotes.vendas_idvendas"
				+ " join pacotetrecho on pacotes.idpacotes = pacotetrecho.pacotes_idpacotes"
				+ " join usuario on pacotes.usuario_idusuario = usuario.idusuario"
				+ " join cliente on vendas.cliente_idcliente = cliente.idcliente"
				+ " join unidadeNegocio on pacotes.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio";
		boolean joinpassageiro = false;
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				if (joinpassageiro == false) {
					if ((pacotes.getPacotetrechoList().get(i).getPacotepassagemList() != null)
							&& (pacotes.getPacotetrechoList().get(i).getPacotepassagemList().size() > 0)) {
						sql = sql
								+ " join pacotepassagem on pacotetrecho.idpacotetrecho =  pacotepassagem.pacotetrecho_idpacotetrecho"
								+ " join pacotepassagempassageiro on pacotepassagem.idpacotepassagem = pacotepassagempassageiro.pacotepassagem_idpacotepassagem";

						joinpassageiro = true;
					}
				}
			}
		}
		sql = sql + " where vendas.idvendas =" + pacotes.getVendas().getIdvendas();
		return sql;
	}

	public String gerarSql2(Pacotes pacotes) {
		String sql2 = "Select distinct  vendas.dataVenda, vendas.valor as valorVenda,"
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, "
				+ "unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, "
				+ "unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, "
				+ "unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio,"
				+ " unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,usuario.nome as nomeUsuario,"
				+ "unidadeNegocio.nomeFantasia, formapagamento.condicao, formapagamento.valortotal,"
				+ "formapagamento.valorjuros,parcelamentopagamento.idparcemlamentoPagamento,parcelamentopagamento.formaPagamento,"
				+ "parcelamentopagamento.tipoParcelmaneto,parcelamentopagamento.valorParcelamento,parcelamentopagamento.diaVencimento,"
				+ "	parcelamentopagamento.numeroParcelas,parcelamentopagamento.valorParcela,parcelamentopagamento.formaPagamento_idformaPagamento"
				+ "	from vendas join usuario on vendas.usuario_idusuario = usuario.idusuario"
				+ "     join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio"
				+ " join formaPagamento on vendas.idvendas = formapagamento.vendas_idvendas"
				+ "  join parcelamentopagamento on formapagamento.idformapagamento = parcelamentopagamento.formapagamento_idformaPagamento"
				+ " where vendas.idvendas =" + pacotes.getVendas().getIdvendas();
		return sql2;
	}

	public String gerarRelatorioFicha(Pacotes pacotes) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		boolean passagem = false;
		for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
			if (passagem == false) {
				if ((pacotes.getPacotetrechoList().get(i).getPacotepassagemList() != null)
						&& (pacotes.getPacotetrechoList().get(i).getPacotepassagemList().size() > 0)) {
					caminhoRelatorio = "/reports/turismo/FichaTurismoPassagemPagina01.jasper";
					passagem = true;
				} else {
					caminhoRelatorio = "/reports/turismo/FichaTurismoPagina01.jasper";
				}
			}
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sql", gerarSql(pacotes));
		parameters.put("sql2", gerarSql2(pacotes));
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//turismo//"));
		parameters.put("itensTrecho", gerarDescricaoTrecho(pacotes));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fichaturismo.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(PacoteMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(PacoteMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarDescricaoTrecho(Pacotes pacotes) {
		String itensTrecho = "";
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
				Pacotecarro pacotecarro = pacotesCarroFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecarro != null) {
					itensTrecho = itensTrecho + "Carro: " + pacotecarro.getDescritivo() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotecarro.getDataretirada()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecarro.getDatadevolucao()) + "\n\n";
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
				Pacotepassagem pacotepassagem = pacotesPassagemFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotepassagem != null) {
					itensTrecho = itensTrecho + "Passagem: " + pacotepassagem.getCiaAerea() + "  Intinerario: "
							+ pacotepassagem.getIntinerario() + "   Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotepassagem.getDataEmbarque()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotepassagem.getDataVolta()) + "\n\n";
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteCruzeiroFacade pacotesCruzeiroFacade = new PacoteCruzeiroFacade();
				Pacotecruzeiro pacotecruzeiro = pacotesCruzeiroFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecruzeiro != null) {
					itensTrecho = itensTrecho + "Cruzeiro: " + pacotecruzeiro.getCategoria() + "  Intinerario: "
							+ pacotecruzeiro.getIntinerario() + "  " + pacotecruzeiro.getTipocabine() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotecruzeiro.getDatasaida()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecruzeiro.getDatechegada()) + "\n\n";
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesHotelFacade pacoteHotelFacade = new PacotesHotelFacade();
				Pacotehotel pacotehotel = pacoteHotelFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotehotel != null) {
					itensTrecho = itensTrecho + "Hotel: " + pacotehotel.getDescritivo() + " " + "     Categoria: "
							+ pacotehotel.getCategoria() + "    Regime: " + pacotehotel.getRegime()
							+ "    Tipo de quarto: " + pacotehotel.getTipoquarto() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotehotel.getDatacheckin()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotehotel.getDatacheckout()) + "\n\n";
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
				Pacotetransfer pacotetransfer = pacoteTransferFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotetransfer != null) {
					itensTrecho = itensTrecho + "Transfer : " + pacotetransfer.getDe() + " / "
							+ pacotetransfer.getPara() + "  " + pacotetransfer.getTipo() + "\n\n";
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteTremFacade pacoteTremFacade = new PacoteTremFacade();
				Pacotetrem pacotetrem = pacoteTremFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotetrem != null) {
					itensTrecho = itensTrecho + "Trem/Ônibus: " + pacotetrem.getDe() + " / " + pacotetrem.getPara()
							+ "    Período: " + pacotetrem.getHorachegada() + " Até " + pacotetrem.getHorasaida()
							+ "\n\n";
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteCircuitoFacade pacoteCircuitoFacade = new PacoteCircuitoFacade();
				Pacotecircuito pacotecircuito = pacoteCircuitoFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecircuito != null) {
					itensTrecho = itensTrecho + "Circuito: " + pacotecircuito.getDe() + " / " + pacotecircuito.getPara()
							+ "    Período: " + Formatacao.ConvercaoDataPadrao(pacotecircuito.getDatainicio()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecircuito.getDatatertminio()) + "\n\n";
					;
				}
			}
		}
		return itensTrecho;
	}

	public String gerarRelatorioContrato(Pacotes pacotes) throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/turismo/contratoTurismoPagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idvendas", pacotes.getVendas().getIdvendas());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//turismo//"));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "contratoturismo.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(PacoteMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(PacoteMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

//	public String cancelarVenda(Pacotes pacotes) {
//		if (!pacotes.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", pacotes.getVendas());
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else {
//			
//			pacotes.getVendas().setSituacao("CANCELADA");
//			vendasDao.salvar(pacotes.getVendas());
//			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
//			String sql = "Select p from Pacotes p where p.operacao='agencia' and p.controle='Concluido' ";
//			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
//				sql = sql + "and p.unidadenegocio.idunidadeNegocio="
//						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
//			}
//			sql = sql + " and p.vendas.dataVenda>='" + dataConsulta + "' order by p.vendas.dataVenda desc";
//			listaPacotesAgencia = GerarListas.listarPacotes(sql);
//		}
//		return "";
//	}
	
	
	public String cancelarVenda(Pacotes pacotes) {
		if (pacotes.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
				|| pacotes.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", pacotes.getVendas());
			session.setAttribute("voltar", "consultapacotesagencia");
			return "emissaocancelamento";
		}  else if (pacotes.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			Vendas vendas = pacotes.getVendas();
			vendas.setSituacao("CANCELADA");
			vendasDao.salvar(vendas);
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
			String sql = "Select p from Pacotes p where p.operacao='agencia' and p.controle='Concluido' ";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + "and p.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}
			sql = sql + " and p.vendas.dataVenda>='" + dataConsulta + "' order by p.vendas.dataVenda desc";
			listaPacotesAgencia = GerarListas.listarPacotes(sql);
		}
		return "";
	}   

	public String corNome(Pacotes pacotes) {
		if (pacotes.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Pacotes pacotes) {
		if (pacotes.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Pacotes pacotes) {
		if (pacotes.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String boletos(Pacotes pacotes) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(pacotes.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + pacotes.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(pacotes.getVendas().getIdvendas()));
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
	
	
	public String informacoes(Pacotes pacotes) {
		if (pacotes.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";  
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", pacotes.getVendas());
			String voltar = "consultapacotesagencia";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
	
	

	
	
	public String fichaPacotes(Pacotes pacotes){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pacotes", pacotes);
		session.setAttribute("listaPacotesAgencia", listaPacotesAgencia);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Pacote");
		session.setAttribute("chamadaTela", "Pacote");
		return "fichasPacotes";
	}
	
	public String contrato(Pacotes pacotes){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pacotes", pacotes);
		session.setAttribute("listaPacotesAgencia", listaPacotesAgencia);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "Pacote");
		session.setAttribute("chamadaTela", "Pacote");
		return "contratoPacotes";
	}
}
