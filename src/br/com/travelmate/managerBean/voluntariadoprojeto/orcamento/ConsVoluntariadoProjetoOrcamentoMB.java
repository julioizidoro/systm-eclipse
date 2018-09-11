package br.com.travelmate.managerBean.voluntariadoprojeto.orcamento;
   
import br.com.travelmate.facade.CambioFacade; 
import br.com.travelmate.facade.FornecedorPaisFacade;
import br.com.travelmate.facade.FtpDadosFacade; 
import br.com.travelmate.facade.OrcamentoProjetoVoluntariadoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoDescontoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoFormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoProdutosExtrasFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoSeguroFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ConsultaOrcamentoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.DadosEscolaEmailBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosExtrasBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.OrcamentoPDFFactory; 
import br.com.travelmate.model.Cambio; 
import br.com.travelmate.model.Fornecedorpais;
import br.com.travelmate.model.Ftpdados; 
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Orcamentovoluntariadodesconto;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;
import br.com.travelmate.model.Orcamentovoluntariadoprodutosextras;
import br.com.travelmate.model.Orcamentovoluntariadoseguro;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
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
public class ConsVoluntariadoProjetoOrcamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private Date dataInicio;
	private Date dataTermino;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String nomeCliente;
	private boolean habilitarUnidade;
	private List<Orcamentoprojetovoluntariado> listaOrcamento;

	@PostConstruct
	public void init() { 
		listaUnidadeNegocio = GerarListas.listarUnidade();
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			habilitarUnidade = false;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}  
		gerarListaOrcamento();
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public List<Orcamentoprojetovoluntariado> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<Orcamentoprojetovoluntariado> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	} 
	
	public void gerarListaOrcamento(){
		String data = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			data = Formatacao.SubtarirDatas(new Date(), 15, "yyyy-MM-dd");
		}
		OrcamentoProjetoVoluntariadoFacade orcamentoProjetoVoluntariadoFacade = new OrcamentoProjetoVoluntariadoFacade();
		String sql = "select o from Orcamentoprojetovoluntariado o where o.dataorcamento>='"+data+"'";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql +" and o.usuario.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
					sql = sql + " and o.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sql = sql + " order by o.dataorcamento desc, o.idorcamentoprojetovoluntariado desc";
		listaOrcamento = orcamentoProjetoVoluntariadoFacade.listar(sql);
		if(listaOrcamento==null){
			listaOrcamento = new ArrayList<>();
		}
	}
	
	public void pesquisar(){ 
		OrcamentoProjetoVoluntariadoFacade orcamentoProjetoVoluntariadoFacade = new OrcamentoProjetoVoluntariadoFacade();
		String sql = "select o from Orcamentoprojetovoluntariado o where o.cliente.idcliente like '%"+nomeCliente+"%'";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql +" and o.usuario.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
					sql = sql + " and o.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}else{
			if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
				sql = sql +" and o.usuario.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio();
			}
		}
		if(dataInicio!=null && dataTermino!=null){
			sql = sql +" and o.dataorcamento>='"+dataInicio+"' and o.dataorcamento>='"+dataTermino+"'";
		} 
		sql = sql + " order by o.dataorcamento desc, o.idorcamentoprojetovoluntariado";
		listaOrcamento = orcamentoProjetoVoluntariadoFacade.listar(sql);
		if(listaOrcamento==null){
			listaOrcamento = new ArrayList<>();
		}
	}
	

	public void gerarOrcamentoPDF(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado, String tipo) throws IOException { 
		GerarOrcamentoVoluntariadoPDFBean o = new GerarOrcamentoVoluntariadoPDFBean(orcamentoprojetovoluntariado, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla());
		OrcamentoPDFFactory.setLista(o.getLista());

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/orcamentovoluntariadopdf/orcamentoPagina01.jasper";
		 
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("/reports/orcamentovoluntariadopdf/"));

		File f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/mascote.png"));
		BufferedImage mascote = null;
		try {
			mascote = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("mascote", mascote);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pagina01.png"));
		BufferedImage pagina01 = null;
		try {
			pagina01 = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("pagina01", pagina01);

		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/logocinza.png"));
		BufferedImage logocinza = null;
		try {
			logocinza = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("logocinza", logocinza);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/curso.png"));
		BufferedImage curso = null;
		try {
			curso = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("curso", curso);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pais.png"));
		BufferedImage pais = null;
		try {
			pais = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("pais", pais);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/orcamento.png"));
		BufferedImage orcamento = null;
		try {
			orcamento = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("orcamento", orcamento);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/icon.png"));
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("icon", icon);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/logo.png"));
		BufferedImage logo = null;
		try {
			logo = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("logo", logo);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pagamento.png"));
		BufferedImage pagamento = null;
		try {
			pagamento = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("pagamento", pagamento);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/outroscustos.png"));
		BufferedImage adicionais = null;
		try {
			adicionais = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("adicionais", adicionais);

		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/observacoes.png"));
		BufferedImage obs = null;
		try {
			obs = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
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
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getCidade().getPais().getIdpais() + ".png",
				"/systm/pais/");
		Image imgPais = null;
		try {
			imgPais = ImageIO.read(is);
			if (imgPais == null) {
				is = ftp.receberArquivo("", "0.png", "/systm/pais/");
				imgPais = ImageIO.read(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.desconectar();
		ftp.conectar();
		is = ftp.receberArquivo("",
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getIdfornecedorcidade() + ".png",
				"/systm/fornecedorcidade/");
		Image imgCidade = null;
		try {
			imgCidade = ImageIO.read(is);
			if (imgCidade == null) {
				is = ftp.receberArquivo("", "0.png", "/systm/fornecedorcidade/");
				imgCidade = ImageIO.read(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.desconectar();

		parameters.put("pais", imgPais);
		parameters.put("fornecedorcidade", imgCidade);
		parameters.put("nometipo", "Tipo de Projeto");
		parameters.put("lista", OrcamentoPDFFactory.getLista());

		JRDataSource jrds = new JRBeanCollectionDataSource(OrcamentoPDFFactory.getLista());
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		String nomeArquivo = "TM-" + String.valueOf("vol"+orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado()) + ".pdf";
		try {
			if (tipo.equalsIgnoreCase("PDF")) {
				gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeArquivo);
			} else
				gerarRelatorio.gerarRelatorioDSFile(caminhoRelatorio, parameters, jrds, nomeArquivo);
		} catch (JRException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void copiarLink(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) { 
		try {
			gerarOrcamentoPDF(orcamentoprojetovoluntariado, "email");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null; 
		try {
			dadosFTP = ftpDadosFacade.getFTPDados(); 
			String text = dadosFTP.getHostlink() + ":82/systm/orcamento/TM-" + "vol"+ orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado() + ".pdf"; 
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("texto", text);
			RequestContext.getCurrentInstance().openDialog("linkorcamento", options, null); 
		} catch (SQLException ex) {
			Logger.getLogger(ConsVoluntariadoProjetoOrcamentoMB.class.getName()).log(Level.SEVERE, null, ex);
		} 
	}
	
	
	public void gerarListaOrcamentoEmail() {
		List<Orcamentoprojetovoluntariado> lista = new ArrayList<>();
		List<DadosEscolaEmailBean> listaDadosEscolas = new ArrayList<DadosEscolaEmailBean>();
		for (int i = 0; i < listaOrcamento.size(); i++) {
			if (listaOrcamento.get(i).isSelecionado()) {
				try {
					gerarOrcamentoPDF(listaOrcamento.get(i), "EMAIL");
					String nomeArquivo = "TM-" + String.valueOf("vol"+listaOrcamento.get(i).getIdorcamentoprojetovoluntariado()) + ".pdf";
					DadosEscolaEmailBean emailBean = new DadosEscolaEmailBean();
					emailBean.setDataInicio(Formatacao.ConvercaoDataPadrao(listaOrcamento.get(i).getDatainicial()));
					emailBean.setDataTermino(Formatacao.ConvercaoDataPadrao(listaOrcamento.get(i).getDatafinal()));
					emailBean.setEscola(listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade()
							.getFornecedor().getNome());
					emailBean.setLocal(listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getCidade()
							.getPais().getNome() + ", "
							+ listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getCidade()
									.getNome());
					emailBean.setNomeArquivo(nomeArquivo);
					if(listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetocursoList()!=null &&
							listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetocursoList().size()>0){
						emailBean.setTipoCurso(listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetocursoList().get(0).getProdutosorcamento().getDescricao()); 
					}
					emailBean.setDuracao(listaOrcamento.get(i).getNsemanas()); 
					emailBean.setCliente(listaOrcamento.get(i).getCliente().getNome()); 
					emailBean.setIdpais(""+listaOrcamento.get(i).getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getCidade().getPais().getIdpais());
					listaDadosEscolas.add(emailBean);
					Orcamentoprojetovoluntariado orcamentoprojetovoluntariado = listaOrcamento.get(i);
					lista.add(orcamentoprojetovoluntariado);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (listaDadosEscolas != null && listaDadosEscolas.size() > 0) {
			enviarEmail(listaDadosEscolas, lista);
			lista = new ArrayList<>();
		}
	}

	public String enviarEmail(List<DadosEscolaEmailBean> listaDadosEscolas, List<Orcamentoprojetovoluntariado> listaSelecionado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaDadosEscolas", listaDadosEscolas);
		session.setAttribute("listaOrcamentoVoluntariado", listaSelecionado);
		session.setAttribute("cliente", listaSelecionado.get(0).getCliente());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
		return "";
	}

	public String editar(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado){
		OrcamentoVoluntariadoBean orcamento = new OrcamentoVoluntariadoBean();
		orcamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
		orcamento.setCliente(orcamentoprojetovoluntariado.getCliente());
		orcamento.setVoluntariadoprojetovalor(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor());
		orcamento.setValor(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getValor());
		Cambio cambio = consultarCambio(orcamentoprojetovoluntariado);
		orcamento.setValorRS(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getValor() * cambio.getValor());
		orcamento.setNumeroSemanasAdicionais(orcamentoprojetovoluntariado.getNsemanaadicional());
		orcamento.setValorSemanaAdc(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getValorsemanaadicional() * orcamentoprojetovoluntariado.getNsemanaadicional());
		orcamento.setValorSemanaAdcRS(orcamento.getValorSemanaAdc() * cambio.getValor());
		orcamento.setDatainicial(orcamentoprojetovoluntariado.getDatainicial());
		orcamento.setDatatermino(orcamentoprojetovoluntariado.getDatafinal());
		orcamento.setCambio(cambio);
		orcamento.setValorcambio(cambio.getValor()); 
		orcamento.setTotalnumerosemanas(orcamentoprojetovoluntariado.getNsemanas()); 
		if(orcamento.getNumeroSemanasAdicionais()>0){
			orcamento.setPossuiSemanaAdicional(true);
		} 
		// curso
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getVoluntariadoprojetocursoList() != null
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getVoluntariadoprojetocursoList().size() > 0) {
			orcamento.setPossuiCurso(true);
			orcamento.setVoluntariadoprojetocurso(
					orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getVoluntariadoprojetocursoList().get(0));
		} else {
			orcamento.setPossuiCurso(false);
		}
		// acomodação
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList() != null
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList()
						.size() > 0) {
			orcamento.setPossuiAcomodacao(true);
			orcamento.setVoluntariadoprojetoacomodacao(
					orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList().get(0));
		} else {
			orcamento.setPossuiAcomodacao(false);
		}
		//transfer
		if(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getTransfer()!=null 
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getTransfer().length()>0){
			orcamento.setPossuiTransfer(true);
		}
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento taxatm = produtoOrcamentoFacade.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamento.setTaxatm(taxatm);
		orcamento.setValortaxatmRS(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		orcamento.setValortaxatm(orcamento.getValortaxatmRS()/orcamento.getValorcambio());
		OrcamentoVoluntariadoFormaPagamentoFacade formaPagamentoFacade = new OrcamentoVoluntariadoFormaPagamentoFacade();
		String sql = "select o from Orcamentovoluntariadoformapagamento o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoformapagamento> lista = formaPagamentoFacade.listar(sql);
		if(lista!=null && lista.size()>0){
			orcamento.setOrcamentovoluntariadoformapagamento(lista.get(0));
		}
		consultarSeguro(orcamentoprojetovoluntariado, orcamento);
		consultarProdutosExtras(orcamento);
		orcamento.setListaDesconto(consultarOcursoDesconto(orcamento));
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("orcamento", orcamento);
		return "resultadoOrcamentoVoluntariado";
	}
	
	public List<Ocursodesconto> consultarOcursoDesconto(OrcamentoVoluntariadoBean orcamento) {
		OrcamentoVoluntariadoDescontoFacade descontoFacade = new OrcamentoVoluntariadoDescontoFacade();
		String sql = "select o from Orcamentovoluntariadodesconto o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadodesconto> lista = descontoFacade.listar(sql);
		Ocursodesconto ocursodesconto;
		Produtosorcamento produtosorcamento;   
		orcamento.setListaDesconto(new ArrayList<Ocursodesconto>());
		ocursodesconto = new Ocursodesconto();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		produtosorcamento = new Produtosorcamento();
		produtosorcamento = produtoOrcamentoFacade.consultar(9); 
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		orcamento.getListaDesconto().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(33); 
		ocursodesconto.setProdutosorcamento(produtosorcamento); 
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f); 
		orcamento.getListaDesconto().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(267); 
		ocursodesconto.setProdutosorcamento(produtosorcamento); 
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f); 
		orcamento.getListaDesconto().add(ocursodesconto);
		
		//lista desconto salvo
		if(lista!=null && lista.size()>0){
			for (int i = 0; i < lista.size(); i++) { 
				//lista desconto
				if(orcamento.getListaDesconto()!=null && orcamento.getListaDesconto().size()>0){
					for (int j = 0; j < orcamento.getListaDesconto().size(); j++) {
						int idproduto = orcamento.getListaDesconto().get(j).getProdutosorcamento().getIdprodutosOrcamento();
						if(lista.get(i).getProdutosorcamento().getIdprodutosOrcamento()==idproduto){ 
							orcamento.getListaDesconto().get(j).setProdutosorcamento(lista.get(i).getProdutosorcamento());
							orcamento.getListaDesconto().get(j).setValormoedaestrangeira(lista.get(i).getValor());
							orcamento.getListaDesconto().get(j).setValormoedanacional(lista.get(i).getValorrs());
							if (lista.get(i).getValor() > 0) {
								orcamento.getListaDesconto().get(j).setSelecionado(true);
							} 
						}
					}
				}
			}
		}
		return orcamento.getListaDesconto();
	}
	
	public Cambio consultarCambio(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
		CambioFacade cambioFacade = new CambioFacade();
		Cambio cambio = null;
		Date data = new Date();
		Fornecedorpais fornecedorPais = buscarFornecedorPais(
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
					.getFornecedorcidade().getFornecedor().getIdfornecedor(),
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
					.getFornecedorcidade().getCidade().getPais().getIdpais());
		int idMoeda = orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getCidade().getPais().getMoedasVolutariado().getIdmoedas();
		if (fornecedorPais != null) {
			idMoeda = fornecedorPais.getMoedas().getIdmoedas();
		}
		while (cambio == null) {
			cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(data), idMoeda);
			try {
				data = Formatacao.SomarDiasDatas(data, -1);
			} catch (Exception ex) {
				Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
		return cambio;
	}
	
	
	public Fornecedorpais buscarFornecedorPais(int idFornecedor, int idPais) {
		String sql = "SELECT f FROM Fornecedorpais f where f.fornecedor.idfornecedor=" + idFornecedor
				+ " and f.pais.idpais=" + idPais;
		FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
		return fornecedorPaisFacade.buscar(sql);
	}
	

	public void retornoEnviarEmail(SelectEvent event) {
		OrcamentoProjetoVoluntariadoFacade oFacade = new OrcamentoProjetoVoluntariadoFacade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		List<Orcamentoprojetovoluntariado> lista = (List<Orcamentoprojetovoluntariado>) session.getAttribute("listaOrcamentoVoluntariado");
		session.removeAttribute("listaOrcamentoVoluntariado");
		if (lista != null && lista.size() > 0) {
			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).setEnviadoemail(true); 
				lista.get(i).setSelecionado(false);
				oFacade.salvar(lista.get(i)); 
			}
		}
		gerarListaOrcamento();
	}
	
	public void consultarProdutosExtras(OrcamentoVoluntariadoBean orcamento){
		OrcamentoVoluntariadoProdutosExtrasFacade extrasFacade = new OrcamentoVoluntariadoProdutosExtrasFacade();
		String sql = "select o from Orcamentovoluntariadoprodutosextras o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoprodutosextras> listaExtras = extrasFacade.listar(sql);
		orcamento.setListaOutrosProdutos(new ArrayList<ProdutosExtrasBean>());
		if (listaExtras != null) {
			for (int i = 0; i < listaExtras.size(); i++) {
				if (listaExtras.get(i).getProdutosorcamento().getIdprodutosOrcamento() != 8) {
					ProdutosExtrasBean e = new ProdutosExtrasBean();
					e.setDescricao(listaExtras.get(i).getProdutosorcamento().getDescricao());
					e.setProdutosorcamento(listaExtras.get(i).getProdutosorcamento());
					e.setSomarvalortotal(listaExtras.get(i).getSomarvalortotal());
					e.setValorOrigianl(listaExtras.get(i).getValor());
					e.setValorOriginalRS(listaExtras.get(i).getValorrs());
					orcamento.getListaOutrosProdutos().add(e);
				}
			}
		}
	}

	public void consultarSeguro(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado,OrcamentoVoluntariadoBean orcamento){
		OrcamentoVoluntariadoSeguroFacade seguroFacade = new OrcamentoVoluntariadoSeguroFacade();
		String sql = "select o from Orcamentovoluntariadoseguro o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoseguro> lista = seguroFacade.listar(sql);
		if(lista!=null && lista.size()>0){
			Orcamentovoluntariadoseguro seguro = lista.get(0);
			Seguroviagem seguroviagem = new Seguroviagem(); 
			seguroviagem.setDataInicio(seguro.getDatainicial());
			seguroviagem.setDataTermino(seguro.getDatafinal());
			seguroviagem.setNumeroSemanas(seguro.getNumerodias());
			seguroviagem.setSomarvalortotal(seguro.getSomarvalortotal());
			seguroviagem.setValorMoedaEstrangeira(seguro.getValor());
			seguroviagem.setValorSeguro(seguro.getValorrs()); 
			seguroviagem.setPlano(seguro.getValoresseguro().getPlano()); 
			seguroviagem.setSeguradora(seguro.getValoresseguro().getFornecedorcidade().getFornecedor().getNome());
			seguroviagem.setValoresseguro(seguro.getValoresseguro());
			orcamento.setValorSeguro(seguro.getValoresseguro()); 
			orcamento.setPossuiSeguroViagem(true);
			orcamento.setSeguroviagem(seguroviagem);
		}else orcamento.setSeguroviagem(new Seguroviagem());
	}
}
