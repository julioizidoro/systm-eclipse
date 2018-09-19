/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.passagemAerea;

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.PassagemFacade;
import br.com.travelmate.facade.PassagemPassageiroFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB; 
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Passagemaerea;
import br.com.travelmate.model.Passagempassageiro;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
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

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PassagemMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Passagemaerea> listaPassagem;
	private Passagemaerea passagem;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidadeNegocio;
	private boolean habilitarUnidade = true;
	private Date dataInicio;
	private Date dataFinal;
	private String vendaMatriz;
	private int idVenda;
	private String nome;

	@PostConstruct()
	public void init() {
		carregarListaPassagem();
		listarUnidade();
		unidadeNegocio = new Unidadenegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			habilitarUnidade = false;
		} else {
			habilitarUnidade = true;
			unidadeNegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
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

	public List<Passagemaerea> getListaPassagem() {
		return listaPassagem;
	}

	public void setListaPassagem(List<Passagemaerea> listaPassagem) {
		this.listaPassagem = listaPassagem;
	}

	public Passagemaerea getPassagem() {
		return passagem;
	}

	public void setPassagem(Passagemaerea passagem) {
		this.passagem = passagem;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public Unidadenegocio getUnidadeNegocio() {
		return unidadeNegocio;
	}

	public void setUnidadeNegocio(Unidadenegocio unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public String getVendaMatriz() {
		return vendaMatriz;
	}

	public void setVendaMatriz(String vendaMatriz) {
		this.vendaMatriz = vendaMatriz;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String novaPassagem() {
		int idlead=0; 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("idlead", idlead);
		return "cadPassagem";
	}

	public String pacotenovo() {
		return "cadPacoteOperadora";
	}

	public String pacotenovos() {
		return "cadPacote";
	}

	public void carregarListaPassagem() {
		dataFinal = null;
		dataInicio = null;
		nome = "";
		idVenda = 0;
		unidadeNegocio = null;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		String sql;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = "Select p from Passagemaerea p where p.vendas.situacao<>'Cancelada' and p.vendas.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getPassagem() + " and p.vendas.dataVenda>='" + dataConsulta
					+ "' order by p.vendas.dataVenda desc";
		} else {
			sql = "Select p from Passagemaerea p where p.vendas.usuario.idusuario="
					+ usuarioLogadoMB.getUsuario().getIdusuario()
					+ " and p.vendas.situacao<>'Cancelada' and p.vendas.produtos.idprodutos="
					+ aplicacaoMB.getParametrosprodutos().getPassagem() + " and p.vendas.dataVenda>='" + dataConsulta
					+ "' order by p.vendas.dataVenda desc";
		}
		PassagemFacade passagemFacade = new PassagemFacade();
		listaPassagem = passagemFacade.lista(sql);
		if (listaPassagem == null) {
			listaPassagem = new ArrayList<Passagemaerea>();
		}
	}

	public String editar(Passagemaerea passagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("passagemaerea", passagem); 
		session.setAttribute("idlead", passagem.getVendas().getIdlead());
		return "cadPassagem";
	}

	public String gerarRelatorioRecibo(Passagemaerea passagemaerea) throws SQLException, IOException {
		this.passagem = passagemaerea;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/turismo/reciboPagamentoPassagem.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(passagemaerea.getVendas().getIdvendas());
		ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
		List<Parcelamentopagamento> listaParcelamento = parcelamentoPagamentoFacade.listar(forma.getIdformaPagamento());
		if (listaParcelamento != null) {
			for (int i = 0; i < listaParcelamento.size(); i++) {
				valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
			}
		}
		if (valorRecibo > 0.0f) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idvendas", passagemaerea.getVendas().getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			float totalTaxa = 0;
			float totalTarifa = 0;
			PassagemPassageiroFacade passagemPassageiroFacade = new PassagemPassageiroFacade();
			List<Passagempassageiro> lista;
			String sql;
			sql = "SELECT p From Passagempassageiro p where p.passagemaerea.idpassagemAerea="
					+ passagemaerea.getIdpassagemAerea();
			lista = passagemPassageiroFacade.lista(sql);
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getCategoria().equalsIgnoreCase("ADT")) {
					totalTarifa = totalTarifa + passagemaerea.getAdttarifa();
					totalTaxa = totalTaxa + passagemaerea.getAdttaxas() + passagemaerea.getAdttaxaemissao();
				} else if (lista.get(i).getCategoria().equalsIgnoreCase("CHD")) {
					totalTarifa = totalTarifa + passagemaerea.getChdtarifa();
					totalTaxa = totalTaxa + passagemaerea.getChdtaxas() + passagemaerea.getChdtaxaemissao();
				} else {
					totalTarifa = totalTarifa + passagemaerea.getInftarifa();
					totalTaxa = totalTaxa + passagemaerea.getInftaxas() + passagemaerea.getInftaxaemissao();
				}
			}
			parameters.put("totalTaxa", Formatacao.formatarFloatString(totalTaxa));
			parameters.put("totalTarifa", Formatacao.formatarFloatString(totalTarifa));
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reciboPagamento-passagem.pdf",
						null);
			} catch (JRException ex1) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sem recibo para imprimir", ""));
		}
		return "";
	}

	public void pesquisar() {
		String sql;
		sql = "select  p from Passagemaerea p where p.vendas.cliente.nome like '%" + nome + "%' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadeNegocio != null) {
				sql = sql + " and p.vendas.unidadenegocio.idunidadeNegocio=" + unidadeNegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and p.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		if (idVenda > 0) {
			sql = sql + " and p.vendas.idvendas=" + idVenda;
		}
		if ((dataInicio != null) && (dataFinal != null)) {
			sql = sql + " and p.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and p.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and p.vendas.dataVenda>='" + dataConsulta + "'";
			}
		}
		sql = sql + " order by p.vendas.dataVenda, p.vendas.cliente.nome";
		PassagemFacade passagemFacade = new PassagemFacade();
		listaPassagem = passagemFacade.lista(sql);
		if (listaPassagem == null) {
			listaPassagem = new ArrayList<Passagemaerea>();
		}
	}

	public void listarUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
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
//			carregarListaPassagem();
//		}
//		return "";
//	}
	
	public String cancelarVenda(Vendas vendas) {
		if (vendas.getSituacao().equalsIgnoreCase("FINALIZADA")
				|| vendas.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas);
			session.setAttribute("voltar", "passagem");
			return "emissaocancelamento";
		} else if (vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			vendas.setSituacao("CANCELADA");
			vendasDao.salvar(vendas);
		}
		return "";
	}  

	public String corNome(Passagemaerea passagemaerea) {
		if (passagemaerea.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(Passagemaerea passagemaerea) {
		if (passagemaerea.getVendas().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public String botoesHabilitados(Passagemaerea passagemaerea) {
		if (passagemaerea.getVendas().getSituacao().equals("CANCELADA")) {
			return "true";
		}
		return "false";
	}

	public String boletos(Passagemaerea passagemaerea) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(passagemaerea.getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas="
					+ passagemaerea.getVendas().getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas,
							String.valueOf(passagemaerea.getVendas().getIdvendas()), true);
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
}
