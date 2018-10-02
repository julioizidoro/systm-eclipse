package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.dao.CoProdutosDao;
import br.com.travelmate.dao.OCursoDao;
import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.OrcamentoPDFFactory;
import br.com.travelmate.managerBean.OrcamentoCurso.comparativo.GerarComparativoTarifarioBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.GerarOcamentoPDFBean;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Occliente;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ConsultaOrcamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private CoProdutosDao coProdutosDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Ocurso ocurso;
	private List<Ocurso> listaOcurso;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private Date dataInicio;
	private Date dataTermino;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String nomeCliente;
	private boolean habilitarUnidade;
	private boolean imprimirCapa;
	private Lead lead;
	private String funcao;
	private boolean botaoHistorico = false;
	@Inject
	private OCursoDescontoDao oCursoDescontoDao;
	@Inject
	private OCursoProdutoDao oCursoProdutoDao;
	@Inject
	private OcursoSeguroViagemDao ocursoSeguroViagemDao;
	
	@Inject
	private OCursoDao oCursoDao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		ocurso = (Ocurso) session.getAttribute("ocurso");
		session.removeAttribute("ocurso");
		if (ocurso == null) {
			ocurso = new Ocurso();
		}
		gerarListaOrcamento();
		listaUnidadeNegocio = GerarListas.listarUnidade();
		setNomeCliente("");
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			habilitarUnidade = false;
		} else {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
		}
		imprimirCapa = true;
		lead = (Lead) session.getAttribute("lead");
		funcao = (String) session.getAttribute("funcao");
		session.removeAttribute("lead");
		session.removeAttribute("funcao");
		botaoHistorico = verificarLead();
	}

	public List<Ocurso> getListaOcurso() {
		return listaOcurso;
	}

	public void setListaOcurso(List<Ocurso> listaOcurso) {
		this.listaOcurso = listaOcurso;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public String novo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("filtrarEscolaBean");
		return "filtrarorcamento";
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
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

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public boolean isImprimirCapa() {
		return imprimirCapa;
	}

	public void setImprimirCapa(boolean imprimirCapa) {
		this.imprimirCapa = imprimirCapa;
	}

	public boolean isBotaoHistorico() {
		return botaoHistorico;
	}

	public void setBotaoHistorico(boolean botaoHistorico) {
		this.botaoHistorico = botaoHistorico;
	}

	public void gerarListaOrcamento() {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			String sql = null;
			String data = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				data = Formatacao.SubtarirDatas(new Date(), 7, "yyyy-MM-dd");
			}
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = "Select o from Ocurso o where  o.dataorcamento>='" + data
						+ "' order by o.dataorcamento desc, o.idocurso desc";
			} else {
				sql = "Select o from Ocurso o where o.usuario.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
				if (usuarioLogadoMB.getUsuario().getAcessounidade() != null) {
					if (!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
						sql = sql + " and o.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
				sql = sql + " and o.dataorcamento>='" + data + "' order by o.dataorcamento desc, o.idocurso desc";
			}
			listaOcurso = oCursoDao.listar(sql);
			if (listaOcurso == null) {
				listaOcurso = new ArrayList<Ocurso>();
			} else {
				for (int i = 0; i < listaOcurso.size(); i++) {
					if (listaOcurso.get(i).getCliente() != null && listaOcurso.get(i).getCliente().getIdcliente() > 1) {
						listaOcurso.get(i).setNomecliente(listaOcurso.get(i).getCliente().getNome());
					} else {
						OcClienteFacade ocClienteFacade = new OcClienteFacade();
						Occliente occliente = ocClienteFacade.consultar(listaOcurso.get(i).getOccliente());
						listaOcurso.get(i).setNomecliente(occliente.getNome());
					}
				}
			}
		}
	}

	public void pesquisar() {
		String sql = null;
		String usouAnd = "";
		if (nomeCliente == null) {
			nomeCliente = "";
		}
		sql = "Select o from Ocurso o where";

		if (unidadenegocio != null) {
			sql = sql + usouAnd + " o.usuario.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			usouAnd = " and";
		}
		if (dataInicio != null) {
			sql = sql + usouAnd + " o.dataorcamento>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			usouAnd = " and";
		}
		if (dataTermino != null) {
			sql = sql + usouAnd + " o.dataorcamento<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			usouAnd = " and";
		}
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (usuarioLogadoMB.getUsuario().getAcessounidade() != null) {
				if (!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
					sql = sql + " and o.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + usouAnd + " o.cliente.nome like '%" + nomeCliente + "%' order by o.dataorcamento desc";
		listaOcurso = oCursoDao.listar(sql);
		if (listaOcurso == null) {
			listaOcurso = new ArrayList<Ocurso>();
		} else {
			for (int i = 0; i < listaOcurso.size(); i++) {
				if (listaOcurso.get(i).getCliente() != null && listaOcurso.get(i).getCliente().getIdcliente() > 1) {
					listaOcurso.get(i).setNomecliente(listaOcurso.get(i).getCliente().getNome());
				} else {
					OcClienteFacade ocClienteFacade = new OcClienteFacade();
					Occliente occliente = ocClienteFacade.consultar(listaOcurso.get(i).getOccliente());
					listaOcurso.get(i).setNomecliente(occliente.getNome());
				}
			}
		}

	}

	public void limparPesquisa() {
		nomeCliente = null;
		dataInicio = null;
		dataTermino = null;
		unidadenegocio = new Unidadenegocio();
		gerarListaOrcamento();
	}

	public void mensagem() {
		Mensagem.lancarMensagemInfo("Email enviado com sucesso.", "");
	}

	public void imprimirRelatorioPDF(Ocurso ocurso) {
		try {
			gerarOrcamentoPDF(ocurso, "PDF");
		} catch (IOException e) {
			  
		}
	}

	public String editarDadosCliente(Ocurso ocurso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (ocurso.getValorcambio() == null) {
			ocurso.setValorcambio(ocurso.getCambio().getValor());
		}
		session.setAttribute("ocurso", ocurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 680);
		RequestContext.getCurrentInstance().openDialog("editarDadosCliente", options, null);
		return "";
	}

	public String editar(Ocurso ocurso) {
		if (ocurso.getDatavalidade() != null && ocurso.getDatavalidade().before(new Date())) {
			Mensagem.lancarMensagemErro("Atenção!",
					"Data de validade do orçamento expirada, inície um novo orçamento.");
		} else {
			EditarOrcamentoOcursoBean editarOrcamentoOcurso = new EditarOrcamentoOcursoBean(ocurso, cliente,
					ocurso.getDatainicio(), aplicacaoMB, usuarioLogadoMB, ocurso.getIdocurso(), oCursoDescontoDao, oCursoProdutoDao, ocursoSeguroViagemDao, coProdutosDao);
			ResultadoOrcamentoBean resultadoOrcamentoBean = new ResultadoOrcamentoBean();
			resultadoOrcamentoBean.setOcurso(ocurso);
			resultadoOrcamentoBean.setCambio(editarOrcamentoOcurso.getOcurso().getCambio());
			resultadoOrcamentoBean
					.setFornecedorcidadeidioma(editarOrcamentoOcurso.getOcurso().getFornecedorcidadeidioma());
			resultadoOrcamentoBean
					.setFornecedorcidade(resultadoOrcamentoBean.getFornecedorcidadeidioma().getFornecedorcidade());
			resultadoOrcamentoBean.setDataConsulta(editarOrcamentoOcurso.getDataconsulta());
			resultadoOrcamentoBean.setProdutoFornecedorBean(editarOrcamentoOcurso.retornarFornecedorProdutosBean());
			resultadoOrcamentoBean.setListaOpcionais(editarOrcamentoOcurso.gerarListaValorCoProdutos("Opcional"));
			// opcional selecionado
			for (int i = 0; i < editarOrcamentoOcurso.getListaProdutos().size(); i++) {
				if (editarOrcamentoOcurso.getListaProdutos().get(i).getNomegrupo().equalsIgnoreCase("CustosExtras")) {
					int id = editarOrcamentoOcurso.getListaProdutos().get(i).getValorcoprodutos()
							.getIdvalorcoprodutos();
					if (resultadoOrcamentoBean.getListaOpcionais() != null) {
						for (int j = 0; j < resultadoOrcamentoBean.getListaOpcionais().size(); j++) {
							if (resultadoOrcamentoBean.getListaOpcionais().get(j).getValorcoprodutos()
									.getIdvalorcoprodutos() == id) {
								resultadoOrcamentoBean.getListaOpcionais().get(j).setSelecionadoOpcional(true);
								resultadoOrcamentoBean.getListaOpcionais().get(j).setValorOrigianl(
										editarOrcamentoOcurso.getListaProdutos().get(i).getValororiginal());
								resultadoOrcamentoBean.getListaOpcionais().get(j).setValorOriginalRS(
										editarOrcamentoOcurso.getListaProdutos().get(i).getValororiginal()
												* ocurso.getValorcambio());
								resultadoOrcamentoBean.getListaOpcionais().get(j).setSomarvalortotal(
										editarOrcamentoOcurso.getListaProdutos().get(i).isSomavalortotal());
							}
						}
					}
				}
			}
			resultadoOrcamentoBean.setListaOutrosProdutos(editarOrcamentoOcurso.gerarListaProdutosExtras());
			resultadoOrcamentoBean.getOcurso().setOcursodescontoList(editarOrcamentoOcurso.addOcursoDesconto());
			resultadoOrcamentoBean.setValorPassagemAerea(ocurso.getValorpassagem());
			resultadoOrcamentoBean.setValorVistoConsular(ocurso.getValorvisto());
			resultadoOrcamentoBean.setValorOutros(ocurso.getValoroutros());
			Seguroviagem seguroviagem = editarOrcamentoOcurso.buscarSeguroViagem();
			if (seguroviagem != null) {
				resultadoOrcamentoBean.setSeguroSelecionado(true);
				resultadoOrcamentoBean.setSeguroviagem(seguroviagem);
			}
			if (editarOrcamentoOcurso.getValorAcomodacao() != null) {
				resultadoOrcamentoBean.setListaAcomodacoes(new ArrayList<ProdutosOrcamentoBean>());
				ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
				po.setSelecionado(true);
				po.setValorcoprodutos(editarOrcamentoOcurso.getValorAcomodacao().getValorcoprodutos());
				po.setNumeroSemanas(editarOrcamentoOcurso.getValorAcomodacao().getNumerosemanas());
				po.setValorOrigianl(editarOrcamentoOcurso.getValorAcomodacao().getValororiginal());
				po.setValorOriginalRS(
						editarOrcamentoOcurso.getValorAcomodacao().getValororiginal() * ocurso.getValorcambio());
				po.setOcrusoprodutos(editarOrcamentoOcurso.getValorAcomodacao());
				resultadoOrcamentoBean.getListaAcomodacoes().add(po);
			}
			if (editarOrcamentoOcurso.getListaAcOpcional() != null) {
				resultadoOrcamentoBean.setListaAcOpcional(new ArrayList<ProdutosOrcamentoBean>());
				for (int i = 0; i < editarOrcamentoOcurso.getListaAcOpcional().size(); i++) {
					ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
					po.setSelecionado(true);
					po.setValorcoprodutos(editarOrcamentoOcurso.getListaAcOpcional().get(i).getValorcoprodutos());
					po.setNumeroSemanas(editarOrcamentoOcurso.getListaAcOpcional().get(i).getNumerosemanas());
					po.setValorOrigianl(
							editarOrcamentoOcurso.getListaAcOpcional().get(i).getValorcoprodutos().getValororiginal());
					po.setValorOriginalRS(po.getValorOrigianl() * ocurso.getValorcambio());
					po.setValorOriginalAcOpcional(editarOrcamentoOcurso.getListaAcOpcional().get(i).getValororiginal());
					po.setValorRSacOpcional(po.getValorOriginalAcOpcional() * ocurso.getValorcambio());
					po.setOcrusoprodutos(editarOrcamentoOcurso.getListaAcOpcional().get(i));
					resultadoOrcamentoBean.getListaAcOpcional().add(po);
				}
			}
			if (editarOrcamentoOcurso.getListaTransfer() != null) {
				resultadoOrcamentoBean.setListaTransfer(new ArrayList<ProdutosOrcamentoBean>());
				for (int i = 0; i < editarOrcamentoOcurso.getListaTransfer().size(); i++) {
					ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
					po.setSelecionado(true);
					po.setValorcoprodutos(editarOrcamentoOcurso.getListaTransfer().get(i).getValorcoprodutos());
					po.setNumeroSemanas(editarOrcamentoOcurso.getListaTransfer().get(i).getNumerosemanas());
					po.setValorOrigianl(
							editarOrcamentoOcurso.getListaTransfer().get(i).getValorcoprodutos().getValororiginal());
					po.setValorOriginalRS(po.getValorOrigianl() * ocurso.getValorcambio());
					po.setOcrusoprodutos(editarOrcamentoOcurso.getListaTransfer().get(i));
					resultadoOrcamentoBean.getListaTransfer().add(po);
				}
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
			RequestContext.getCurrentInstance().closeDialog(null);
			return "orcamentoCurso";
		}
		return "";
	}

	public void retornoDialogEditar(SelectEvent event) {
		Ocurso ocurso = (Ocurso) event.getObject();
		if (ocurso.getIdocurso() != null) {
			for (int i = 0; i < listaOcurso.size(); i++) {
				if (listaOcurso.get(i) == ocurso) {
					listaOcurso.set(i, ocurso);
					i = 1000;
				}
			}
		}
	}

	public void gerarOrcamentoPDF(Ocurso ocurso, String tipo) throws IOException {
		if (ocurso.getValorcambio() == null) {
			ocurso.setValorcambio(ocurso.getCambio().getValor());
		}
		GerarOcamentoPDFBean o = new GerarOcamentoPDFBean(ocurso, aplicacaoMB);
		OrcamentoPDFFactory.setLista(o.getLista());

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (imprimirCapa) {
			caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina01.jasper";
		} else {
			caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina02.jasper";
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("/reports/orcamentopdf/"));
		File f = new File(servletContext.getRealPath("/reports/orcamentopdf/mascote.png"));
		BufferedImage mascote = null;
		try {
			mascote = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("mascote", mascote);
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagina01.png"));
		BufferedImage pagina01 = null;
		try {
			pagina01 = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("pagina01", pagina01);

		f = new File(servletContext.getRealPath("/reports/orcamentopdf/logocinza.png"));
		BufferedImage logocinza = null;
		try {
			logocinza = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("logocinza", logocinza);
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/curso.png"));
		BufferedImage curso = null;
		try {
			curso = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("curso", curso);
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/pais.png"));
		BufferedImage pais = null;
		try {
			pais = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("pais", pais);
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/orcamento.png"));
		BufferedImage orcamento = null;
		try {
			orcamento = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("orcamento", orcamento);
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/icon.png"));
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("icon", icon);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/logo.png"));
		BufferedImage logo = null;
		try {
			logo = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("logo", logo);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagamento.png"));
		BufferedImage pagamento = null;
		try {
			pagamento = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("pagamento", pagamento);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/outroscustos.png"));
		BufferedImage adicionais = null;
		try {
			adicionais = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("adicionais", adicionais);

		f = new File(servletContext.getRealPath("/reports/orcamentopdf/observacoes.png"));
		BufferedImage obs = null;
		try {
			obs = ImageIO.read(f);
		} catch (IOException e) {
			  
		}
		parameters.put("obs", obs);

		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;

		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(ConsultaOrcamentoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		ftp.conectar();
		InputStream is = ftp.receberArquivo("",
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais() + ".png",
				"/systm/pais/");
		Image imgPais = null;
		try {
			imgPais = ImageIO.read(is);
			if (imgPais == null) {
				is = ftp.receberArquivo("", "0.png", "/systm/pais/");
				imgPais = ImageIO.read(is);
			}
		} catch (IOException e) {
			  
		}
		ftp.desconectar();
		ftp.conectar();
		is = ftp.receberArquivo("",
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getIdfornecedorcidade() + ".png",
				"/systm/fornecedorcidade/");
		Image imgCidade = null;
		try {
			imgCidade = ImageIO.read(is);
			if (imgCidade == null) {
				is = ftp.receberArquivo("", "0.png", "/systm/fornecedorcidade/");
				imgCidade = ImageIO.read(is);
			}
		} catch (IOException e) {
			  
		}
		ftp.desconectar();

		parameters.put("pais", imgPais);
		parameters.put("fornecedorcidade", imgCidade);
		parameters.put("nometipo", "Tipo de Curso");
		parameters.put("lista", OrcamentoPDFFactory.getLista());

		JRDataSource jrds = new JRBeanCollectionDataSource(OrcamentoPDFFactory.getLista());
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		String nomeArquivo = "TM-" + String.valueOf(ocurso.getIdocurso()) + ".pdf";
		try {
			if (tipo.equalsIgnoreCase("PDF")) {
				gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeArquivo);
			} else
				gerarRelatorio.gerarRelatorioDSFile(caminhoRelatorio, parameters, jrds, nomeArquivo);
		} catch (JRException | SQLException e) {
			  
		}
	}

	public void gerarListaOrcamentoEmail() {
		List<Ocurso> listaOcursoSelecionado = new ArrayList<>();
		List<DadosEscolaEmailBean> listaDadosEscolas = new ArrayList<DadosEscolaEmailBean>();
		for (int i = 0; i < listaOcurso.size(); i++) {
			if (listaOcurso.get(i).isSelecionado()) {
				try {
					gerarOrcamentoPDF(listaOcurso.get(i), "EMAIL");
					String nomeArquivo = "TM-" + String.valueOf(listaOcurso.get(i).getIdocurso()) + ".pdf";
					DadosEscolaEmailBean emailBean = new DadosEscolaEmailBean();
					emailBean.setDataInicio(Formatacao.ConvercaoDataPadrao(listaOcurso.get(i).getDatainicio()));
					emailBean.setDataTermino(Formatacao.ConvercaoDataPadrao(listaOcurso.get(i).getDatatermino()));
					emailBean.setEscola(listaOcurso.get(i).getFornecedorcidadeidioma().getFornecedorcidade()
							.getFornecedor().getNome());
					emailBean.setLocal(listaOcurso.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade()
									.getNome() + ", " + listaOcurso.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade()
									.getPais().getNome());
					emailBean.setNomeArquivo(nomeArquivo);
					emailBean.setTipoCurso(listaOcurso.get(i).getProdutosorcamento().getDescricao());
					emailBean.setTurno(listaOcurso.get(i).getTurno());
					emailBean.setDuracao(listaOcurso.get(i).getNumerosemanas().toString());
					if (listaOcurso.get(i).getCliente() != null && listaOcurso.get(i).getCliente().getIdcliente() > 1) {
						emailBean.setCliente(listaOcurso.get(i).getCliente().getNome());
					} else {
						OcClienteFacade ocClienteFacade = new OcClienteFacade();
						Occliente occliente = ocClienteFacade.consultar(listaOcurso.get(i).getOccliente());
						emailBean.setCliente(occliente.getNome());
					}
					emailBean.setIdpais(""+listaOcurso.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais());
					listaDadosEscolas.add(emailBean);
					Ocurso ocursoSelecionado = listaOcurso.get(i);
					listaOcursoSelecionado.add(ocursoSelecionado);
				} catch (IOException e) {
					  
				}
			}
		}
		if (listaDadosEscolas != null && listaDadosEscolas.size() > 0) {
			enviarEmail(listaDadosEscolas, listaOcursoSelecionado);
			listaOcursoSelecionado = new ArrayList<>();
		}
	}

	public String enviarEmail(List<DadosEscolaEmailBean> listaDadosEscolas, List<Ocurso> listaOcursoSelecionado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaDadosEscolas", listaDadosEscolas);
		session.setAttribute("listaOcurso", listaOcursoSelecionado);
		session.setAttribute("cliente", listaOcursoSelecionado.get(0).getCliente());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
		return "";
	}

	public String iniciarOrcamentoComparativo(String tipo) {
		//
		List<Ocurso> lista = new ArrayList<Ocurso>();
		for (int i = 0; i < listaOcurso.size(); i++) {
			if (listaOcurso.get(i).isSelecionado()) {
				lista.add(listaOcurso.get(i));
				if (lista.size() == 3) {
					i = listaOcurso.size() + 100;
				}
			}
		}
		if (lista.size() == 2 || lista.size() == 3) {
			boolean cliente = verificarMesmoClienteOrcamento(lista);
			if (cliente) {
				GerarComparativoTarifarioBean gerarOrcamentoComparativo = new GerarComparativoTarifarioBean(lista,
						aplicacaoMB);
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("listaOrcamento", gerarOrcamentoComparativo.getLista());
				if (tipo.equalsIgnoreCase("email")) {
					session.setAttribute("listaOcurso", lista);
					session.setAttribute("cliente", lista.get(0).getCliente());
					Map<String, Object> options = new HashMap<String, Object>();
					options.put("contentWidth", 400);
					RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
					return "";
				} else
					return "relatorioOrcamentoComparativo";
			} else
				Mensagem.lancarMensagemErro("Atenção!",
						"É preciso selecionar o mesmo cliente para comparar orçamentos.");
		} else {
			Mensagem.lancarMensagemErro("Atenção!",
					"É preciso selecionar no mínimo 2 e no máximo 3 orçamentos para comparar.");
		}
		return "";
	}

	public boolean verificarMesmoClienteOrcamento(List<Ocurso> lista) {
		int idcliente = lista.get(0).getCliente().getIdcliente();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getCliente().getIdcliente() != idcliente) {
				return false;
			}
		}
		return true;
	}

	public boolean verificarEnvioEmail(Ocurso ocurso) {
		if (ocurso.isEnviadoemail()) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void retornoEnviarEmail(SelectEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		List<Ocurso> listaOcurso = (List<Ocurso>) session.getAttribute("listaOcurso");
		session.removeAttribute("listaOcurso");
		if (listaOcurso != null && listaOcurso.size() > 0) {
			for (int i = 0; i < listaOcurso.size(); i++) {
				listaOcurso.get(i).setEnviadoemail(true);
				oCursoDao.salvar(listaOcurso.get(i));
			}
		}
		gerarListaOrcamento();
	}

	public String retornaHistoricoLead() {
		if (lead != null) {
			if (lead.getIdlead() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("lead", lead);
				session.setAttribute("funcao", funcao);
				session.setAttribute("posicao", 0);
				return "historicoCliente";
			}
		}
		return "";
	}
	
	
	public boolean verificarLead() {
		if (lead != null && lead.getIdlead() != null) {
			return true;
		}
		return false;
	}

	public void copiarLink(Ocurso ocurso) {
		try {
			gerarOrcamentoPDF(ocurso, "email");
		} catch (IOException e) {
			  
		}
		String text = "orcamentos.systm.com.br/TM-" + ocurso.getIdocurso() + ".pdf";
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("texto", text);
		RequestContext.getCurrentInstance().openDialog("linkorcamento", options, null);
	}
}
