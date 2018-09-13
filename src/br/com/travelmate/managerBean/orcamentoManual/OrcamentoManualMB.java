package br.com.travelmate.managerBean.orcamentoManual;

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
 
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.facade.OrcamentoCursoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ConsultaOrcamentoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.DadosEscolaEmailBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.GerarOcamentoManualPDFBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.OrcamentoPDFFactory; 
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Occliente;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class OrcamentoManualMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<Orcamentocurso> listaOrcamento;
	private boolean habilitarUnidade;
	private String nomeCliente = "";
	private Date dataInicio;
	private Date dataTermino;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean imprimirCapa;
	private Lead lead;
	private String funcao;
	private String tipo;

	@PostConstruct
	public void init() { 
		listaUnidadeNegocio = GerarListas.listarUnidade();
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			imprimirCapa = true;
			lead = (Lead) session.getAttribute("lead");
			funcao = (String) session.getAttribute("funcao");
			tipo = (String) session.getAttribute("tipoocamento"); 
			session.removeAttribute("tipoocamento");
			session.removeAttribute("lead");
			session.removeAttribute("funcao");
			gerarListaOrcamento();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Orcamentocurso> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<Orcamentocurso> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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

	public boolean isImprimirCapa() {
		return imprimirCapa;
	}

	public void setImprimirCapa(boolean imprimirCapa) {
		this.imprimirCapa = imprimirCapa;
	}

	public void gerarListaOrcamento() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		String sql = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy-MM-dd");
			sql = "Select o from Orcamentocurso o where o.cliente.nome like '%" + nomeCliente
					+ "%' and o.situacao='Processo' and o.data>='" + dataConsulta + "'"
					+ " and o.tipoorcamento='"+tipo+"' order by o.data desc";
		} else {
			sql = "Select o from Orcamentocurso o where o.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
					+ " and o.cliente.nome like '%" + nomeCliente + "%' and o.situacao='Processo' and o.data>='"
					+ dataConsulta + "'"; 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
					sql = sql + " and o.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
			sql = sql + " and o.tipoorcamento='"+tipo+"' order by o.data desc, o.idorcamentoCurso desc";
		}
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		listaOrcamento = orcamentoCursoFacade.listarOrcamento(sql);
		if (listaOrcamento == null) {
			listaOrcamento = new ArrayList<Orcamentocurso>();
		} else {
			for (int i = 0; i < listaOrcamento.size(); i++) {
				if (listaOrcamento.get(i).getCliente() != null
						&& listaOrcamento.get(i).getCliente().getIdcliente() > 1) {
					listaOrcamento.get(i).setNomecliente(listaOrcamento.get(i).getCliente().getNome());
				} else {
					OcClienteFacade ocClienteFacade = new OcClienteFacade();
					Occliente occliente = ocClienteFacade.consultar(listaOrcamento.get(i).getOccliente());
					listaOrcamento.get(i).setNomecliente(occliente.getNome());
				}
			}
		}
	}

	public void pesquisar() {
		String sql = null;
		sql = "Select o from Orcamentocurso o where o.cliente.nome like '%" + nomeCliente + "%'";
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and o.usuario.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (dataInicio != null && dataTermino != null) {
			sql = sql + " and o.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and o.data<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} 
		if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
			if(!usuarioLogadoMB.getUsuario().getAcessounidade().isConsultaorcamento()) {
				sql = sql + " and o.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
			}
		} 
		sql = sql + " order by o.data desc";
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		listaOrcamento = orcamentoCursoFacade.listarOrcamento(sql);
		if (listaOrcamento == null) {
			listaOrcamento = new ArrayList<Orcamentocurso>();
		} else {
			for (int i = 0; i < listaOrcamento.size(); i++) {
				if (listaOrcamento.get(i).getCliente() != null
						&& listaOrcamento.get(i).getCliente().getIdcliente() > 1) {
					listaOrcamento.get(i).setNomecliente(listaOrcamento.get(i).getCliente().getNome());
				} else {
					OcClienteFacade ocClienteFacade = new OcClienteFacade();
					Occliente occliente = ocClienteFacade.consultar(listaOrcamento.get(i).getOccliente());
					listaOrcamento.get(i).setNomecliente(occliente.getNome());
				}
			}
		}
	}

	public void limparPesquisa() {
		// carregarOCliente();
		nomeCliente = "";
		dataInicio = null;
		dataTermino = null;
		unidadenegocio = null;
		gerarListaOrcamento();
	}

	public String novo() {
		return "cadOrcamentoManual";
	}

	public String editar(Orcamentocurso orcamentocurso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("orcamentoManual", orcamentocurso);
		session.setAttribute("tipoorcamento", orcamentocurso.getTipoorcamento());
		return "cadOrcamentoManual";
	}

	public String iniciarRelatorioOrcamento(Orcamentocurso orcamentocurso) {
		try {
			gerarOrcamentoPDF(orcamentocurso, "PDF");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void gerarOrcamentoPDF(Orcamentocurso orcamentocurso, String tipo) throws IOException {
		GerarOcamentoManualPDFBean o = new GerarOcamentoManualPDFBean(orcamentocurso);
		OrcamentoPDFFactory.setLista(o.getLista());

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		boolean teste = false;
		if (teste) {
			if (imprimirCapa) {
				caminhoRelatorio = "/reports/orcamentovoluntariadopdf/orcamentoPagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/orcamentovoluntariadopdf/orcamentoPagina02.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("/reports/orcamentovoluntariadopdf/"));
			File f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pagina01.png"));
			BufferedImage pagina01 = null;
			try {
				pagina01 = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pagina01", pagina01);
			f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/mascote.png"));
			BufferedImage mascote = null;
			try {
				mascote = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("mascote", mascote);
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
			parameters.put("nometipo", "Projeto de Voluntariado");
		} else {
			if (imprimirCapa) {
				caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina02.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//orcamentopdf//"));
			File f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagina01.png"));
			BufferedImage pagina01 = null;
			try {
				pagina01 = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pagina01", pagina01);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/mascote.png"));
			BufferedImage mascote = null;
			try {
				mascote = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("mascote", mascote);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/logocinza.png"));
			BufferedImage logocinza = null;
			try {
				logocinza = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("logocinza", logocinza);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/curso.png"));
			BufferedImage curso = null;
			try {
				curso = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("curso", curso);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/pais.png"));
			BufferedImage pais = null;
			try {
				pais = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pais", pais);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/orcamento.png"));
			BufferedImage orcamento = null;
			try {
				orcamento = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("orcamento", orcamento);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/icon.png"));
			BufferedImage icon = null;
			try {
				icon = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("icon", icon);
			//
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/logo.png"));
			BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("logo", logo);
			//
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagamento.png"));
			BufferedImage pagamento = null;
			try {
				pagamento = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pagamento", pagamento);
			//
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/outroscustos.png"));
			BufferedImage adicionais = null;
			try {
				adicionais = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("adicionais", adicionais);

			f = new File(servletContext.getRealPath("/reports/orcamentopdf/observacoes.png"));
			
			parameters.put("nometipo", "Tipo de Curso");
		}
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
				orcamentocurso.getFornecedorcidade().getCidade().getPais().getIdpais() + ".png", "/systm/pais/");
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
		is = ftp.receberArquivo("", orcamentocurso.getFornecedorcidade().getIdfornecedorcidade() + ".png",
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
		parameters.put("lista", OrcamentoPDFFactory.getLista());

		JRDataSource jrds = new JRBeanCollectionDataSource(OrcamentoPDFFactory.getLista());
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		String nomeArquivo = "TM-" + String.valueOf(orcamentocurso.getIdorcamentoCurso()) + ".pdf";
		try {
			if (tipo.equalsIgnoreCase("PDF")) {
				gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeArquivo);
			} else
				gerarRelatorio.gerarRelatorioDSFile(caminhoRelatorio, parameters, jrds, nomeArquivo);
		} catch (JRException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void gerarListaOrcamentoEmail() {
		List<Orcamentocurso> listaOrcamentoCursoSelecionado = new ArrayList<>();
		List<DadosEscolaEmailBean> listaDadosEscolas = new ArrayList<DadosEscolaEmailBean>();
		for (int i = 0; i < listaOrcamento.size(); i++) {
			if (listaOrcamento.get(i).isSelecionado()) {
				try {
					gerarOrcamentoPDF(listaOrcamento.get(i), "EMAIL");
					String nomeArquivo = "TM-" + String.valueOf(listaOrcamento.get(i).getIdorcamentoCurso()) + ".pdf";
					DadosEscolaEmailBean emailBean = new DadosEscolaEmailBean();
					emailBean.setDataInicio(Formatacao.ConvercaoDataPadrao(listaOrcamento.get(i).getDataInicio()));
					emailBean.setDataTermino(Formatacao.ConvercaoDataPadrao(listaOrcamento.get(i).getDataTermino()));
					emailBean.setEscola(listaOrcamento.get(i).getFornecedorcidade().getFornecedor().getNome());
					emailBean.setLocal(listaOrcamento.get(i).getFornecedorcidade().getCidade().getPais().getNome() + ", "
							+ listaOrcamento.get(i).getFornecedorcidade().getCidade().getNome());
					emailBean.setNomeArquivo(nomeArquivo);
					List<Produtoorcamentocurso> lista = getListaProdutoOrcanentoCurso("C");
					emailBean.setTipoCurso(lista.get(0).getProdutosOrcamento().getDescricao());
					emailBean.setTurno("Indefinido");
					emailBean.setDuracao(String.valueOf(listaOrcamento.get(i).getAulasSemana()) + " "
							+ listaOrcamento.get(i).getTipoDuracao());
					if (listaOrcamento.get(i).getCliente() != null
							&& listaOrcamento.get(i).getCliente().getIdcliente() > 1) {
						emailBean.setCliente(listaOrcamento.get(i).getCliente().getNome());
					} else {
						OcClienteFacade ocClienteFacade = new OcClienteFacade();
						Occliente occliente = ocClienteFacade.consultar(listaOrcamento.get(i).getOccliente());
						emailBean.setCliente(occliente.getNome());
					}
					emailBean.setIdpais(""+listaOrcamento.get(i).getFornecedorcidade().getCidade().getPais().getIdpais());
					listaDadosEscolas.add(emailBean);
					Orcamentocurso orcamentocurso = listaOrcamento.get(i);
					listaOrcamentoCursoSelecionado.add(orcamentocurso);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (listaDadosEscolas != null && listaDadosEscolas.size() > 0) {
			enviarEmail(listaDadosEscolas, listaOrcamentoCursoSelecionado);
		}
	}

	public String enviarEmail(List<DadosEscolaEmailBean> listaDadosEscolas,
			List<Orcamentocurso> listaOrcamentoCursoSelecionado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaDadosEscolas", listaDadosEscolas);
		session.setAttribute("listaOrcamentoCurso", listaOrcamentoCursoSelecionado);
		session.setAttribute("cliente", listaOrcamentoCursoSelecionado.get(0).getCliente());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
		return "";
	}

	public List<Produtoorcamentocurso> getListaProdutoOrcanentoCurso(String tipoOrcamento) {
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		String sql = "SELECT o FROM Produtoorcamentocurso o where o.produtosOrcamento.tipoorcamento='" + tipoOrcamento
				+ "' order by o.produtosOrcamento.descricao";
		return orcamentoCursoFacade.listarProdutoOrcamentoCurso(sql);
	}

	public boolean verificarEnvioEmail(Orcamentocurso orcamentocurso) {
		if (orcamentocurso.isEnviadoemail()) {
			return true;
		} else {
			return false;
		}
	}

	public void retornoEnviarEmail(SelectEvent event) {
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		List<Orcamentocurso> listaOrcamentoCurso = (List<Orcamentocurso>) session.getAttribute("listaOrcamentoCurso");
		session.removeAttribute("listaOrcamentoCurso");
		for (int i = 0; i < listaOrcamentoCurso.size(); i++) {
			listaOrcamentoCurso.get(i).setEnviadoemail(true);
			orcamentoCursoFacade.salvar(listaOrcamentoCurso.get(i));
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

	public void copiarLink(Orcamentocurso orcamentocurso) {
		try {
			gerarOrcamentoPDF(orcamentocurso, "email");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
			String text = dadosFTP.getHostlink() + ":82/systm/orcamento/TM-" + orcamentocurso.getIdorcamentoCurso()
					+ ".pdf";
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("texto", text);
			RequestContext.getCurrentInstance().openDialog("linkorcamento", options, null);
		} catch (SQLException ex) {
			Logger.getLogger(ConsultaOrcamentoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
