package br.com.travelmate.managerBean.financeiro.invoices;

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

import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.InvoiceRemessaFacade;
import br.com.travelmate.facade.InvoiceRemessaInvoiceFacade;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Invoiceremessa;
import br.com.travelmate.model.Invoiceremessainvoice;
import br.com.travelmate.model.Terceiros;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class ConsultaInvoiceMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Invoice> listaInvoices;
	private String corValorNet;
	private Float valorTotalSelecionados;
	private List<Invoice> listaSelecionadas;
	private Invoice invoice;
	private boolean checkSelecionado;
	private String tituloCheck;
	private String programa;
	private String nomeCliente;
	private String situacao;
	private String fornecedor;
	private String escala;
	private Date dataPrevisaoInicial;
	private Date dataPrevisaoFinal;
	private Date dataPagamentoInicial;
	private Date dataPagamentoFinal;
	private int idVendas;
	private String sql;
	private List<Invoiceremessa> listaRemassa;
	private Invoiceremessa invoiceRemessa;
	private boolean finalizarInvoices = false;
	private boolean selecionarTodos;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			invoice = new Invoice();
			valorTotalSelecionados = 0.0f;
			listaSelecionadas = new ArrayList<Invoice>();
			checkSelecionado = false;
			tituloCheck = "Selecionar tudo";
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 3) {
				gerarListaInvoices();
				gerarListaInvoiceRemessa();
				getDataEmbarque();
			} else {
				gerarListaInvoiceRemessa();
				if (sql == null) {
					sql = "";
				} else {
					pesquisar();
				}
			}
		}
	}

	public boolean isCheckSelecionado() {
		return checkSelecionado;
	}

	public void setCheckSelecionado(boolean checkSelecionado) {
		this.checkSelecionado = checkSelecionado;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Invoice> getListaInvoices() {
		return listaInvoices;
	}

	public void setListaInvoices(List<Invoice> listaInvoices) {
		this.listaInvoices = listaInvoices;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCorValorNet() {
		return corValorNet;
	}

	public void setCorValorNet(String corValorNet) {
		this.corValorNet = corValorNet;
	}

	public int getIdVendas() {
		return idVendas;
	}

	public void setIdVendas(int idVendas) {
		this.idVendas = idVendas;
	}

	public Float getValorTotalSelecionados() {
		return valorTotalSelecionados;
	}

	public List<Invoice> getListaSelecionadas() {
		return listaSelecionadas;
	}

	public void setListaSelecionadas(List<Invoice> listaSelecionadas) {
		this.listaSelecionadas = listaSelecionadas;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public void setValorTotalSelecionados(Float valorTotalSelecionados) {
		this.valorTotalSelecionados = valorTotalSelecionados;
	}

	public String getTituloCheck() {
		return tituloCheck;
	}

	public void setTituloCheck(String tituloCheck) {
		this.tituloCheck = tituloCheck;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getEscala() {
		return escala;
	}

	public void setEscala(String escala) {
		this.escala = escala;
	}

	public Date getDataPrevisaoInicial() {
		return dataPrevisaoInicial;
	}

	public void setDataPrevisaoInicial(Date dataPrevisaoInicial) {
		this.dataPrevisaoInicial = dataPrevisaoInicial;
	}

	public Date getDataPrevisaoFinal() {
		return dataPrevisaoFinal;
	}

	public void setDataPrevisaoFinal(Date dataPrevisaoFinal) {
		this.dataPrevisaoFinal = dataPrevisaoFinal;
	}

	public Date getDataPagamentoInicial() {
		return dataPagamentoInicial;
	}

	public void setDataPagamentoInicial(Date dataPagamentoInicial) {
		this.dataPagamentoInicial = dataPagamentoInicial;
	}

	public Date getDataPagamentoFinal() {
		return dataPagamentoFinal;
	}

	public void setDataPagamentoFinal(Date dataPagamentoFinal) {
		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	public List<Invoiceremessa> getListaRemassa() {
		return listaRemassa;
	}

	public void setListaRemassa(List<Invoiceremessa> listaRemassa) {
		this.listaRemassa = listaRemassa;
	}

	public Invoiceremessa getInvoiceRemessa() {
		return invoiceRemessa;
	}

	public void setInvoiceRemessa(Invoiceremessa invoiceRemessa) {
		this.invoiceRemessa = invoiceRemessa;
	}

	public boolean isFinalizarInvoices() {
		return finalizarInvoices;
	}

	public void setFinalizarInvoices(boolean finalizarInvoices) {
		this.finalizarInvoices = finalizarInvoices;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public boolean isSelecionarTodos() {
		return selecionarTodos;
	}

	public void setSelecionarTodos(boolean selecionarTodos) {
		this.selecionarTodos = selecionarTodos;
	}

	public String gerarProdutoInvoice(Invoice invoice) {
		if (invoice.getTipo().equalsIgnoreCase("programa")) {
			return invoice.getVendas().getProdutos().getDescricao();
		} else
			return invoice.getTipo();
	}

	public void editarEscala(Invoice invoice) {
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		invoice = invoiceFacade.salvar(invoice);
		pesquisar();
	}

	public void gerarListaInvoices() {
		if (nomeCliente == null) {
			nomeCliente = "";
		}
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
			sql = "Select i from Invoice i where i.vendas.cliente.nome like '%" + nomeCliente
					+ "%' and i.produtos.descricao like 'Cursos' and i.datarecebimentocomprovante is NULL and i.vendas.situacao<>'CANCELADA' order by i.escala desc, i.vendas.vendascomissao.previsaopagamento, i.vendas.cliente.nome";
		} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
			sql = "Select i from Invoice i where i.vendas.cliente.nome like '%" + nomeCliente
					+ "%' and i.produtos.descricao like 'Work and Travel' and i.datarecebimentocomprovante is NULL and i.vendas.situacao<>'CANCELADA' order by i.escala desc, i.vendas.vendascomissao.previsaopagamento, i.vendas.cliente.nome";
		} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5) {
			sql = "Select i from Invoice i where i.vendas.cliente.nome like '%" + nomeCliente
					+ "%' and i.produtos.descricao like 'High School' and i.datarecebimentocomprovante is NULL and i.vendas.situacao<>'CANCELADA' order by i.escala desc, i.vendas.vendascomissao.previsaopagamento, i.vendas.cliente.nome";
		} 
		if (sql != null && sql.length() > 1) {
			listaInvoices = invoiceFacade.listar(sql);
		}
		if (listaInvoices == null) { 
			listaInvoices = new ArrayList<Invoice>();
		}
		listaSelecionadas = new ArrayList<Invoice>();
	}

	public Float retornarValorNet(Invoice invoice) {
		if (invoice.getValorPagamentoInvoice() > 0) {
			corValorNet = "width:80px;text-align:right";
			return invoice.getValorPagamentoInvoice();
		} else {
			corValorNet = "width:80px;text-align:right;color:red";
			return invoice.getVendas().getVendascomissao().getValorfornecedor();
		}
	}

	public void calcularValorTotalSelecionados(Invoice invoice) { 
		List<Cambio> listaCambio = aplicacaoMB.getListaCambio();
		Cambio cambio = null;
		for (int i = 0; i < listaCambio.size(); i++) {
			if (listaCambio.get(i).getMoedas().getSigla()
					.equalsIgnoreCase(invoice.getVendas().getCambio().getMoedas().getSigla())) {
				cambio = listaCambio.get(i);
				i = 10000;
			}
		}
		InvoiceRemessaInvoiceFacade invoiceRemessaInvoiceFacade = new InvoiceRemessaInvoiceFacade();
		if (invoice.isSelecionado()) {
			boolean clientedevendo = verificarClientesDevedores(invoice);
			if(clientedevendo){
				invoice.setClientedevedor(clientedevendo);
			}
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
				if (invoice.getInvoiceremessainvoice() == null) {
					Invoiceremessainvoice invoiceRemessaInvoice = new Invoiceremessainvoice();
					invoiceRemessaInvoice.setInvoice(invoice);
					invoiceRemessaInvoice.setInvoiceremessa(invoiceRemessa);
					invoiceRemessaInvoice = invoiceRemessaInvoiceFacade.salvar(invoiceRemessaInvoice);
					invoice.setInvoiceremessainvoice(invoiceRemessaInvoice);
					listaSelecionadas.add(invoice);
					if (invoice.getValorPagamentoInvoice() > 0) {
						valorTotalSelecionados = valorTotalSelecionados
								+ ((invoice.getValorPagamentoInvoice() - invoice.getValorcredito())
										* cambio.getValor());
					} else {
						valorTotalSelecionados = valorTotalSelecionados
								+ ((invoice.getVendas().getVendascomissao().getValorfornecedor()
										- invoice.getValorcredito()) * cambio.getValor());
					}
				} else {
					listaSelecionadas.add(invoice);
					if (invoice.getValorPagamentoInvoice() > 0) {
						valorTotalSelecionados = valorTotalSelecionados
								+ ((invoice.getValorPagamentoInvoice() - invoice.getValorcredito())
										* cambio.getValor());
					} else {
						valorTotalSelecionados = valorTotalSelecionados
								+ ((invoice.getVendas().getVendascomissao().getValorfornecedor()
										- invoice.getValorcredito()) * cambio.getValor());
					}
				}
			}
		} else {
			invoice.setClientedevedor(false);
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
				invoiceRemessaInvoiceFacade.excluir(invoice.getInvoiceremessainvoice());
				invoice.setInvoiceremessainvoice(null);
			}
			listaSelecionadas.remove(invoice);
			if (invoice.getValorPagamentoInvoice() > 0) {
				valorTotalSelecionados = valorTotalSelecionados
						- ((invoice.getValorPagamentoInvoice() - invoice.getValorcredito()) * cambio.getValor());
			} else {
				valorTotalSelecionados = valorTotalSelecionados
						- ((invoice.getVendas().getVendascomissao().getValorfornecedor() - invoice.getValorcredito())
								* cambio.getValor());
			}
		}  
		if (listaSelecionadas.size() > 0) {
			checkSelecionado = true;
			tituloCheck = "Remover seleção";
		} else {
			checkSelecionado = false;
			tituloCheck = "Selecionar tudo";
		}
	}

	public String stringValorTotalSelecionados() {
		return Formatacao.formatarFloatString(valorTotalSelecionados);
	}

	public void gerarRelatorio() {
		if (listaSelecionadas.size() > 0) {
			List<PagamentoInvoiceBean> listaPagamentoInvoiceBean = new ArrayList<PagamentoInvoiceBean>();
			for (int i = 0; i < listaSelecionadas.size(); i++) {
				PagamentoInvoiceBean pagamentoInvoiceBean = new PagamentoInvoiceBean();
				pagamentoInvoiceBean.setEscala(listaSelecionadas.get(i).getEscala());
				pagamentoInvoiceBean
						.setCidade(listaSelecionadas.get(i).getVendas().getFornecedorcidade().getCidade().getNome());
				pagamentoInvoiceBean.setCliente(listaSelecionadas.get(i).getVendas().getCliente().getNome());
				pagamentoInvoiceBean.setCpf(listaSelecionadas.get(i).getVendas().getCliente().getCpf());
				pagamentoInvoiceBean.setCredito(listaSelecionadas.get(i).getValorcredito());
				pagamentoInvoiceBean.setFornecedor(
						listaSelecionadas.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				pagamentoInvoiceBean.setMoeda(listaSelecionadas.get(i).getVendas().getCambio().getMoedas().getSigla());
				pagamentoInvoiceBean.setPais(
						listaSelecionadas.get(i).getVendas().getFornecedorcidade().getCidade().getPais().getNome());
				if (listaSelecionadas.get(i).getValorPagamentoInvoice() > 0) {
					pagamentoInvoiceBean.setValornet(listaSelecionadas.get(i).getValorPagamentoInvoice());
				} else
					pagamentoInvoiceBean
							.setValornet(listaSelecionadas.get(i).getVendas().getVendascomissao().getValorfornecedor());
				pagamentoInvoiceBean.setStatus(verificarContasReceber(listaSelecionadas.get(i)));
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = cursoFacade.consultarCursos(listaSelecionadas.get(i).getVendas().getIdvendas());
				if (curso != null) {
					pagamentoInvoiceBean.setInicioCurso(curso.getDataInicio());
				}
				pagamentoInvoiceBean.setPrevisaoPagamento(listaSelecionadas.get(i).getDataPrevistaPagamento());
				if(invoice.isClientedevedor()){
					pagamentoInvoiceBean.setClientedevedor("Sim");
				} 
				listaPagamentoInvoiceBean.add(pagamentoInvoiceBean);
			}
			try {
				gerarRelatorioPagamentoInvoice(listaPagamentoInvoiceBean);
			} catch (SQLException | IOException e) {
				((Throwable) e).printStackTrace();
				Mensagem.lancarMensagemErro("Erro Invoice", "Erro gerar lista de Invoices para pagamento");
			}
		}
	}

	public void gerarRelatorioPagamentoInvoice(List<PagamentoInvoiceBean> lista) throws SQLException, IOException {
		if ((lista == null) || (lista.size() == 0)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Relatório não possui páginas", ""));
		} else {
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			Map parameters = new HashMap();
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			String caminhoRelatorio = "/reports/invoices/reportPagamentoInvoices.jasper";
			GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
			JRDataSource jrds = new JRBeanCollectionDataSource(lista);
			try {
				gerarRelatorioContrato.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "PagametnoInvoices.pdf");
			} catch (JRException e) {
				e.printStackTrace();
				Mensagem.lancarMensagemErro("Erro Relatório",
						"Erro gerar relatório de invoices para pagamento " + e.getMessage());
			}
		}
	}

	public String verificarContasReceber(Invoice invoice) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> listaContas = contasReceberFacade
				.listar("Select c from Contasreceber c where c.vendas.idvendas=" + invoice.getVendas().getIdvendas()
						+ " and c.valorpago>0 and c.datavencimento<=" + Formatacao.ConvercaoDataSql(new Date()));
		if ((listaContas == null) || (listaContas.size() == 0)) {
			return "OK";
		} else
			return "Verificar";
	}

	public String baixaRecebiveis() {
		if (listaInvoices!=null && listaInvoices.size() > 0) {
			listaSelecionadas = new ArrayList<>();
			for (int i = 0; i < listaInvoices.size(); i++) {
				if(listaInvoices.get(i).isSelecionado()){
					listaSelecionadas.add(listaInvoices.get(i));
				}
			}
			if(listaSelecionadas.size()>0){
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("listaInvoice", listaSelecionadas);
				session.setAttribute("sql", sql);
				return "baixarecebiveis";
			}else {
				Mensagem.lancarMensagemInfo("Informação", "Nenhuma invoice selecionada para pagamento.");
				return "";
			} 
		} else {
			Mensagem.lancarMensagemInfo("Informação", "Nenhuma invoice selecionada para pagamento.");
			return "";
		}

	}

	public String relatorios() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("relatoriosInvoice", options, null);
		return "";
	}

	public void pesquisar() {
		sql = "";
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		if (sql.length() == 0) {
			sql = "Select i from Invoice i where i.vendas.situacao<>'CANCELADA' and i.vendas.cliente.nome like '%"
					+ nomeCliente + "%' ";
			if (idVendas > 0) {
				sql = sql + " and i.vendas.idvendas=" + idVendas;
			}
			if (!escala.equalsIgnoreCase("s")) {
				sql = sql + " and i.escala=" + escala;
			}
			if (programa != null) {
				sql = sql + " and i.produtos.descricao like '%" + programa + "%' ";
			}
			if ((dataPagamentoInicial != null) && (dataPagamentoFinal != null)) {
				sql = sql + " and i.dataPagamentoInvoice>='" + Formatacao.ConvercaoDataSql(dataPagamentoInicial)
						+ "' and i.dataPagamentoInvoice<='" + Formatacao.ConvercaoDataSql(dataPagamentoFinal) + "' ";
			}
			if ((dataPrevisaoInicial != null) && (dataPrevisaoFinal != null)) {
				sql = sql + " and i.dataPrevistaPagamento>='" + Formatacao.ConvercaoDataSql(dataPrevisaoInicial)
						+ "' and i.dataPrevistaPagamento<='" + Formatacao.ConvercaoDataSql(dataPrevisaoFinal) + "' ";
			}
			if (fornecedor != null) {
				sql = sql + " and i.vendas.fornecedor.nome like '%" + fornecedor + "%' ";
			}
			if (!situacao.equalsIgnoreCase("TODAS") && !situacao.equalsIgnoreCase("PAGAS")  && !situacao.equalsIgnoreCase("COMPROVANTE")) {
				sql = sql + " and i.vendas.situacao='" + situacao + "' ";
			}
			if (situacao.equalsIgnoreCase("PAGAS")) {
				sql = sql + " and i.valorPago>0";
			}
			if (situacao.equalsIgnoreCase("COMPROVANTE")){
				sql = sql + " and i.valorPago=0  order by i.escala desc, i.vendas.vendascomissao.previsaopagamento, i.vendas.cliente.nome";
			}else {
				sql = sql + " and i.valorPago=0 and i.datarecebimentocomprovante is NULL order by i.escala desc, i.vendas.vendascomissao.previsaopagamento, i.vendas.cliente.nome";
			}
		}
		listaInvoices = invoiceFacade.listar(sql);
		getDataEmbarque();
	}

	public void limpar() {
		nomeCliente = "";
		fornecedor = null;
		programa = null;
		dataPagamentoInicial = null;
		dataPagamentoFinal = null;
		dataPrevisaoInicial = null;
		dataPrevisaoFinal = null;
		escala = null;
		situacao = null;
		idVendas = 0;
		listaInvoices = new ArrayList<Invoice>();
		sql = "";
		gerarListaInvoices();
	}

	public void gerarBackOfficeInvoices() {
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		sql = "Select i from Invoice i where  i.valorPago=0";
		listaInvoices = invoiceFacade.listar(sql);
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		if (listaInvoices != null) {
			TerceirosFacade terceirosFacade = new TerceirosFacade();
			Terceiros terceiros = terceirosFacade.consultar(1);
			for (int i = 0; i < listaInvoices.size(); i++) {
				Vendascomissao vendascomissao = vendasComissaoFacade
						.consultar(listaInvoices.get(i).getVendas().getIdvendas());
				if (vendascomissao == null) {
					vendascomissao = new Vendascomissao();
					vendascomissao.setPrevisaopagamento(listaInvoices.get(i).getDataPrevistaPagamento());
					vendascomissao.setPaga("Sim");
					vendascomissao.setUsuario(usuarioLogadoMB.getUsuario());
					vendascomissao.setVendas(listaInvoices.get(i).getVendas());
					vendascomissao.setProdutos(listaInvoices.get(i).getVendas().getProdutos());
					vendascomissao.setTerceiros(terceiros);
					vendasComissaoFacade.salvar(vendascomissao);
				}
			}
		}
		System.out.println("Terminou");
	}

	public String selecionarInvoice(Vendas vendas) throws SQLException {
		ArquivosFacade arquivosFacade = new ArquivosFacade();
		List<Arquivos> listarArquivos;
		String sql = "Select a from Arquivos a where a.vendas.idvendas=" + vendas.getIdvendas()
				+ " and a.tipoarquivo.descricao like '%nvoice%'";
		listarArquivos = arquivosFacade.listar(sql);
		if (listarArquivos == null) {
			Mensagem.lancarMensagemInfo("Atenção", "Não possuí invoice anexada.");
		} else if (listarArquivos.size() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listarArquivos", listarArquivos);
			RequestContext.getCurrentInstance().openDialog("downloadInvoice");
			return "";
		}
		return "";
	}

	public void getDataEmbarque() {
		if (listaInvoices != null) {
			for (int i = 0; i < listaInvoices.size(); i++) {
				if (listaInvoices.get(i).getVendas().getControlecursosList() != null) {
					if (listaInvoices.get(i).getVendas().getControlecursosList().size() > 0) {
						if (listaInvoices.get(i).getVendas().getControlecursosList().get(0).getDataEmbarque() != null) {
							listaInvoices.get(i).setDataEmbarque(
									listaInvoices.get(i).getVendas().getControlecursosList().get(0).getDataEmbarque());
						}
					}
				}
			}
		}
	}

	public void gerarListaInvoiceSelecionadasRemessa() {
		if (listaInvoices != null) {
			for (int i = 0; i < listaInvoices.size(); i++) {
				if (listaInvoices.get(i).getInvoiceremessainvoice() != null) {
					listaInvoices.get(i).setSelecionado(true);
					calcularValorTotalInicial(listaInvoices.get(i));
				}
			}
		}
	}

	public void gerarListaInoiceViaRemessa() {
		gerarListaInvoices();
		listaSelecionadas = new ArrayList<Invoice>();
		if (invoiceRemessa == null) {
			finalizarInvoices = false;
			valorTotalSelecionados = 0.0f;
		} else {
			if (invoiceRemessa.getInvoiceremessainvoiceList() != null) {
				int idremessa = invoiceRemessa.getIdinvoiceremessa();
				for (int i = 0; i < listaInvoices.size(); i++) {
					if (listaInvoices.get(i).getInvoiceremessainvoice() != null) {
						if (listaInvoices.get(i).getInvoiceremessainvoice().getInvoiceremessa() != null) {
							int idinvoice = listaInvoices.get(i).getInvoiceremessainvoice().getInvoiceremessa()
									.getIdinvoiceremessa();
							if (idinvoice == idremessa) {
								listaInvoices.get(i).setSelecionado(true);
								calcularValorTotalInicial(listaInvoices.get(i));
							}
						}
					}
				}
				if (listaSelecionadas != null && !invoiceRemessa.isFinalizada()
						&& usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
					finalizarInvoices = false;
				} else if (listaSelecionadas != null && invoiceRemessa.isFinalizada()
						&& usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
					finalizarInvoices = false;
					listaInvoices = new ArrayList<Invoice>();
					for (int i = 0; i < listaSelecionadas.size(); i++) {
						listaInvoices.add(listaSelecionadas.get(i));
					}
				} else if (listaSelecionadas != null && invoiceRemessa.isFinalizada()) {
					finalizarInvoices = false;
					listaInvoices = new ArrayList<Invoice>();
					for (int i = 0; i < listaSelecionadas.size(); i++) {
						listaInvoices.add(listaSelecionadas.get(i));
					}
				}
			}
		}
	}

	public boolean mostrarBtnFinalizar() {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
			return true;
		} else
			return false;
	}

	public void calcularValorTotalInicial(Invoice invoice) {
		List<Cambio> listaCambio = aplicacaoMB.getListaCambio();
		Cambio cambio = null;
		for (int i = 0; i < listaCambio.size(); i++) {
			if (listaCambio.get(i).getMoedas().getSigla()
					.equalsIgnoreCase(invoice.getVendas().getCambio().getMoedas().getSigla())) {
				cambio = listaCambio.get(i);
				i = 10000;
			}
		}
		if (invoice.isSelecionado()) {
			invoice.setSelecionado(true);
			listaSelecionadas.add(invoice);
			if (invoice.getValorPagamentoInvoice() > 0) {
				valorTotalSelecionados = valorTotalSelecionados
						+ ((invoice.getValorPagamentoInvoice() - invoice.getValorcredito()) * cambio.getValor());
			} else {
				valorTotalSelecionados = valorTotalSelecionados
						+ ((invoice.getVendas().getVendascomissao().getValorfornecedor() - invoice.getValorcredito())
								* cambio.getValor());
			}
		}
		if (listaSelecionadas.size() > 0) {
			checkSelecionado = true;
			tituloCheck = "Remover seleção";
		} else {
			checkSelecionado = false;
			tituloCheck = "Selecionar tudo";
		}
	}

	public void gerarListaInvoiceRemessa() {
		InvoiceRemessaFacade invoiceRemessaFacade = new InvoiceRemessaFacade();
		String dataRemssa = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		try {
			String sql = "SELECT i FROM Invoiceremessa i where i.data>='" + dataRemssa + "'";
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 2) {
				sql = sql + " and i.finalizada=1";
			}
			listaRemassa = invoiceRemessaFacade.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (listaRemassa == null) {
			listaRemassa = new ArrayList<Invoiceremessa>();
		}
	}

	public void criarNovaRemessa() {
		invoiceRemessa = new Invoiceremessa();
		invoiceRemessa.setData(new Date());
		invoiceRemessa.setHora(Formatacao.foramtarHoraString());
		invoiceRemessa.setUsuairoFinanceiro(usuarioLogadoMB.getUsuario());
		invoiceRemessa.setUsuarioCurso(usuarioLogadoMB.getUsuario());
		InvoiceRemessaFacade invoiceRemessaFacade = new InvoiceRemessaFacade();
		invoiceRemessa = invoiceRemessaFacade.salvar(invoiceRemessa);
		listaRemassa.add(invoiceRemessa);
		invoiceRemessa = null;
	}

	public String abrirObsInvoices(Invoice invoice) throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("invoice", invoice);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("consObsInvoices", options, null);
		return "";
	}

	public String retornarComboBoxRemessa(Invoiceremessa invoiceremessa) {
		return Formatacao.ConvercaoDataPadrao(invoiceremessa.getData()) + " - " + invoiceremessa.getHora();
	}

	public void finalizarInvoiceRemessa() {
		if (invoiceRemessa != null) {
			InvoiceRemessaFacade invoiceRemessaFacade = new InvoiceRemessaFacade();
			invoiceRemessa.setFinalizada(true);
			invoiceRemessaFacade.salvar(invoiceRemessa);
			invoiceRemessa = null;
			finalizarInvoices = false;
			gerarListaInvoiceRemessa();
			Mensagem.lancarMensagemInfo("Invoice Remessa finalizado", "");
			listaSelecionadas = new ArrayList<Invoice>();
			for (int i = 0; i < listaInvoices.size(); i++) {
				if (listaInvoices.get(i).isSelecionado()) {
					listaInvoices.get(i).setSelecionado(false);
				}
			}
		}
	}

	public String retornoImagemObsInvoice(Invoice invoice) {
		if (invoice.getObservacaodepartamento() != null) {
			return "../../resources/img/iconeObsVermelho.png";
		}

		if (invoice.getObservacaofinanceiro() != null) {
			return "../../resources/img/iconeObsVermelho.png";
		}
		return "../../resources/img/iconeObsVerde.png";
	}

	public void selecionarTodos() {
		if (listaInvoices != null) {
			for (int i = 0; i < listaInvoices.size(); i++) {
				listaInvoices.get(i).setSelecionado(selecionarTodos);
				calcularValorTotalSelecionados(listaInvoices.get(i));
			}
		}
	}

	public boolean verificarClientesDevedores(Invoice invoice){
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 4, "yyyy-MM-dd");
		String sql = "Select c from Contasreceber c where c.valorpago=0 and c.datapagamento is NULL and c.datavencimento<='"
				+ dataConsulta + "' and c.situacao<>'cc' and c.vendas.cliente.idcliente="+invoice.getVendas().getCliente().getIdcliente() 
				+ " order by c.datavencimento, c.vendas.cliente.nome"; 
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
		if(listaContas!=null && listaContas.size()>0){
			Mensagem.lancarMensagemErro("Cliente devedor!", "Cliente "+invoice.getVendas().getCliente().getNome()+", possui contas a receber em aberto!");
			return true;
		}
		return false;
	}
	
	public String retornarCorClienteDevedor(Invoice invoice){
		if(invoice.isClientedevedor()){
			return "color:red;";
		}
		return "";
	}
}
