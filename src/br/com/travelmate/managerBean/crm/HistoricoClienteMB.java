package br.com.travelmate.managerBean.crm;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.travelmate.bean.LeadSituacaoBean;
import br.com.travelmate.facade.ContasReceberFacade; 
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadHistoricoFacade;
import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.facade.OCursoFacade;
import br.com.travelmate.facade.OrcamentoCursoFacade; 
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ConsultaOrcamentoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.GerarOcamentoManualPDFBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.GerarOcamentoPDFBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.OrcamentoPDFFactory; 
import br.com.travelmate.model.Contasreceber; 
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class HistoricoClienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Lead lead;  
	private List<Leadhistorico> listaHistorico;
	private boolean mostrarHora;
	private boolean esconderHora;
	private String funcao;
	private List<Motivocancelamento> listaMotivoCancelamento;
	private int idvenda;
	private List<Lead> listaLead;
	private int posicao;
	private PesquisaBean pesquisa;
	private boolean camposcurso;
	private boolean camposcursodemipair;
	private boolean camposvoluntariado;
	private String voltar;
	private boolean mostrarLeads;
	private boolean mostrarPosVenda;
	private boolean habilitarDescricao = true;
	private Produtos produto;
	private List<Produtos> listaProduto;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			listaProduto = GerarListas.listarProdutos("");
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			listaLead = (List<Lead>) session.getAttribute("listaLead");
			Object obj = session.getAttribute("posicao");
			if (obj!=null){
				posicao = (int) obj;
			}else posicao=0;
			funcao = (String) session.getAttribute("funcao");
			voltar = (String) session.getAttribute("voltar");
			pesquisa = (PesquisaBean) session.getAttribute("pesquisa");
			session.removeAttribute("pesquisa"); 
			session.removeAttribute("voltar"); 
			session.removeAttribute("funcao"); 
			session.removeAttribute("listaLead"); 
			session.removeAttribute("posicao");
			if (listaLead != null && listaLead.size() > 0) {
				lead = listaLead.get(posicao);
			}else{
				lead = (Lead) session.getAttribute("lead");
				session.removeAttribute("lead");
			}
			gerarListaHistorico(); 
			gerarListaMotivoCancelamento();
			int idcurso = aplicacaoMB.getParametrosprodutos().getCursos();
			int iddemipair = aplicacaoMB.getParametrosprodutos().getDemipair();
			int idvoluntariado = aplicacaoMB.getParametrosprodutos().getVoluntariado();
			camposvoluntariado = false;
			if(idcurso == lead.getProdutos().getIdprodutos()){
				camposcurso=true; 
				camposcursodemipair = true;
			}else if(iddemipair == lead.getProdutos().getIdprodutos()) {
				camposcursodemipair = true;
			}else if(idvoluntariado == lead.getProdutos().getIdprodutos()){
				camposvoluntariado = true;
			}
			if(lead.getLeadposvenda()==null) {
				mostrarLeads = true;
				mostrarPosVenda = false;
			}else {
				mostrarLeads = false;
				mostrarPosVenda = true;
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}
 

	public List<Leadhistorico> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(List<Leadhistorico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}

	public boolean isMostrarHora() {
		return mostrarHora;
	}

	public void setMostrarHora(boolean mostrarHora) {
		this.mostrarHora = mostrarHora;
	}

	public boolean isEsconderHora() {
		return esconderHora;
	}

	public void setEsconderHora(boolean esconderHora) {
		this.esconderHora = esconderHora;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public List<Motivocancelamento> getListaMotivoCancelamento() {
		return listaMotivoCancelamento;
	}

	public void setListaMotivoCancelamento(List<Motivocancelamento> listaMotivoCancelamento) {
		this.listaMotivoCancelamento = listaMotivoCancelamento;
	}

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public List<Lead> getListaLead() {
		return listaLead;
	}

	public void setListaLead(List<Lead> listaLead) {
		this.listaLead = listaLead;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {   
		this.posicao = posicao;
	}
 

	public PesquisaBean getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(PesquisaBean pesquisa) {
		this.pesquisa = pesquisa;
	}

	public boolean isCamposcurso() {
		return camposcurso;
	}

	public void setCamposcurso(boolean camposcurso) {
		this.camposcurso = camposcurso;
	}

	public boolean isCamposvoluntariado() {
		return camposvoluntariado;
	}

	public void setCamposvoluntariado(boolean camposvoluntariado) {
		this.camposvoluntariado = camposvoluntariado;
	}

	public boolean isCamposcursodemipair() {
		return camposcursodemipair;
	}

	public void setCamposcursodemipair(boolean camposcursodemipair) {
		this.camposcursodemipair = camposcursodemipair;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public boolean isMostrarLeads() {
		return mostrarLeads;
	}

	public void setMostrarLeads(boolean mostrarLeads) {
		this.mostrarLeads = mostrarLeads;
	}

	public boolean isMostrarPosVenda() {
		return mostrarPosVenda;
	}

	public void setMostrarPosVenda(boolean mostrarPosVenda) {
		this.mostrarPosVenda = mostrarPosVenda;
	}

	public boolean isHabilitarDescricao() {
		return habilitarDescricao;
	}

	public void setHabilitarDescricao(boolean habilitarDescricao) {
		this.habilitarDescricao = habilitarDescricao;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public List<Produtos> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produtos> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public String followUp() {
		LeadFacade leadFacade = new LeadFacade();
		lead = leadFacade.salvar(lead);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("funcao", funcao); 
		session.setAttribute("pesquisa", pesquisa);
		if(voltar!=null && voltar.length()>0) {
			return voltar;
		}
		return "followUp";
	}
	
	public void salvarNotas() {
		LeadFacade leadFacade = new LeadFacade();
		lead = leadFacade.salvar(lead); 
		Mensagem.lancarMensagemInfo("Salvo com sucesso.", "");
	}
 

	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#2E5495;";
		} else if (numeroSituacao == 3) {
			return "#9ACD32;";
		} else if (numeroSituacao == 4) {
			return "#FF8C00;";
		}else if (numeroSituacao == 5) {
			return "#B22222;";
		} else if (numeroSituacao == 6) {
			return "#228B22;";
		} else if (numeroSituacao == 7) {
			return "#8B8989;";
		} else if (numeroSituacao == 8) {
			return "#9400D3;";
		}
		return"";
	}

	public String retornarCorHistorico(int idHistorico) {
		if (idHistorico % 2 == 0) {
			return "dataScrollerBranco";
		} else
			return "dataScrollerCinza";
	}

	public void mudarSituacao(int situacao) {
		LeadFacade leadFacade = new LeadFacade();
		LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), situacao);
		lead.setSituacao(situacao);
		lead = leadFacade.salvar(lead);
	}

	public void gerarListaHistorico() {
		LeadHistoricoFacade leadHistoricoFacade = new LeadHistoricoFacade();
		String sql = "select l from Leadhistorico l where l.cliente.idcliente=" + lead.getCliente().getIdcliente()
				+ " order by l.idleadhistorico DESC";
		listaHistorico = leadHistoricoFacade.lista(sql);
	}

	public String visualizarVendas() {
		VendasFacade vendasFacade = new VendasFacade();
		String sql = "Select v From Vendas v where (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO') "
				+ " and v.cliente.idcliente=" + lead.getCliente().getIdcliente() + " order by v.idvendas";
		List<Vendas> listaVendas = vendasFacade.lista(sql);
		if (listaVendas != null && listaVendas.size() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaVendas", listaVendas);
			RequestContext.getCurrentInstance().openDialog("visualizarVendas");
		} else
			Mensagem.lancarMensagemInfo("Este cliente não possui vendas.", "");
		return "";
	}

	public String visualizarContasReceber() {
		VendasFacade vendasFacade = new VendasFacade();
		String sql = "Select v From Vendas v where (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO') "
				+ " and v.cliente.idcliente=" + lead.getCliente().getIdcliente() + " order by v.idvendas";
		List<Vendas> listaVendas = vendasFacade.lista(sql);
		if (listaVendas != null && listaVendas.size() > 0) {
			String sqlContas = "Select c from Contasreceber c where (c.vendas.idvendas="
					+ listaVendas.get(0).getIdvendas();
			for (int i = 1; i < listaVendas.size(); i++) {
				sqlContas = sqlContas + " or c.vendas.idvendas=" + listaVendas.get(i).getIdvendas();
			}
			sqlContas = sqlContas + ") and c.situacao<>'cc' order by c.datavencimento";
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			List<Contasreceber> listaContasReceber = contasReceberFacade.listar(sqlContas);
			if (listaContasReceber != null && listaContasReceber.size() > 0) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("listaContasReceber", listaContasReceber);
				RequestContext.getCurrentInstance().openDialog("visualizarContasReceberCliente");
			} else
				Mensagem.lancarMensagemInfo("Este cliente não possui contas a receber.", "");
		} else
			Mensagem.lancarMensagemInfo("Este cliente não possui contas a receber.", "");
		return "";
	}

	public String cancelarLead() {
			if(lead.getMotivocancelamento1()!=null
				&& lead.getMotivocancelamento1().getIdmotivocancelamento()!=1){
				LeadFacade leadFacade = new LeadFacade();
				LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 7);
				lead.setSituacao(7);
				lead = leadFacade.salvar(lead);
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("funcao", funcao); 
				session.setAttribute("pesquisa", pesquisa);
				return "followUp";
			} else
				Mensagem.lancarMensagemInfo("Selecione um motivo de cancelamento.", "");
		return "";
	}   

	public String orcamentoManual(String tipo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead.getCliente().setLead(lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		session.setAttribute("tipoorcamento", tipo);
		return "cadOrcamentoManual";
	}

	public String orcamentoTarifario() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead.getCliente().setLead(lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		return "filtrarorcamento";
	}
	
	public String orcamentoTarifarioModelo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead.getCliente().setLead(lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		return "inicialPacotes";
	}

	public String emitirVenda() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		int idprodutos = lead.getProdutos().getIdprodutos();
		if (idprodutos != 13) {
			session.setAttribute("cliente", lead.getCliente());
			session.setAttribute("lead", lead); 
			if(idprodutos != aplicacaoMB.getParametrosprodutos().getHighereducation()) {
				return "cadCliente";
			}else {
				return "questionarioHe";
			}
		}
		return "";
	}   
	
	public String emitirVendaTrainee(String tipo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		session.setAttribute("tipo", tipo);
		return "cadCliente";
	}  
	
	public void excluir(Leadhistorico leadhistorico){
		LeadHistoricoFacade leadHistoricoFacade = new LeadHistoricoFacade();
		lead.getCliente().getLeadhistoricoList().remove(leadhistorico);
		leadHistoricoFacade.excluir(leadhistorico.getIdleadhistorico());
		gerarListaHistorico();
		Mensagem.lancarMensagemInfo("Histórico excluido com sucesso.", "");
	}
	
	public void editar(Leadhistorico leadhistorico){ 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("leadhistorico", leadhistorico);
		session.setAttribute("lead", lead);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("cadHistorico", options, null);
	}
	
	public void cadHistorico(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		options.put("modal", true);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("lead", lead);
		RequestContext.getCurrentInstance().openDialog("cadHistorico", options, null);
	}
	
	public void gerarListaMotivoCancelamento(){
		MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
		String sql = "select m from Motivocancelamento m order by m.motivo";
		listaMotivoCancelamento = motivoCancelamentoFacade.lista(sql);
		if(listaMotivoCancelamento==null){
			listaMotivoCancelamento = new ArrayList<Motivocancelamento>();
		}
	} 
	
	public boolean rederizarBotaoOrcamento(Leadhistorico leadhistorico){
		if (!leadhistorico.getTipoorcamento().equalsIgnoreCase("s")){
			return true;
		}
		return false;
	}
	
	public void gerarOrcamentoPDF(Leadhistorico leadhistorico){
		if (leadhistorico.getTipoorcamento().equalsIgnoreCase("t")){
			OCursoFacade oCursoFacade = new OCursoFacade();
			Ocurso ocurso = oCursoFacade.consultar(leadhistorico.getIdorcamento());
			if (ocurso!=null){
				try {
					gerarOrcamentoPDFTarifario(ocurso, "PDF");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if (leadhistorico.getTipoorcamento().equalsIgnoreCase("m")){
			OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
			Orcamentocurso orcamentoCurso = orcamentoCursoFacade.consultar(leadhistorico.getIdorcamento());
			if (orcamentoCurso!=null){
				try {
					gerarOrcamentoPDFManual(orcamentoCurso, "PDF");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void gerarOrcamentoPDFTarifario(Ocurso ocurso, String tipo) throws IOException {
		if(ocurso.getValorcambio()==null){
			ocurso.setValorcambio(ocurso.getCambio().getValor());   
		}
		GerarOcamentoPDFBean o = new GerarOcamentoPDFBean(ocurso, aplicacaoMB);
		OrcamentoPDFFactory.setLista(o.getLista());

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
			caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//orcamentopdf//"));
		File f = new File(servletContext.getRealPath("/reports/orcamentopdf/mascote.png"));
		BufferedImage mascote = null;
		try {
			mascote = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("mascote", mascote);
		f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagina01.png"));
		BufferedImage pagina01 = null;
		try {
			pagina01 = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();      
		}
		parameters.put("pagina01", pagina01);

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
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais() + ".png",
				"/systm/pais/"); 
		Image imgPais = null;
		try {
			imgPais = ImageIO.read(is);
			if (imgPais==null){
				is = ftp.receberArquivo("","0.png", "/systm/pais/");
				imgPais = ImageIO.read(is);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.desconectar();
		ftp.conectar();
		is = ftp.receberArquivo("",
				ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getIdfornecedorcidade() + ".png",
				"/systm/fornecedorcidade/"); 
		Image imgCidade = null;
		try {
			imgCidade = ImageIO.read(is);
			if (imgCidade==null){
				is = ftp.receberArquivo("","0.png", "/systm/fornecedorcidade/");
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
		String nomeArquivo = "TM-" + String.valueOf(ocurso.getIdocurso()) + ".pdf";
		try {
			if (tipo.equalsIgnoreCase("PDF")) {
				gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeArquivo);
			} else
				gerarRelatorio.gerarRelatorioDSFile(caminhoRelatorio, parameters, jrds, nomeArquivo);
		} catch (JRException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void gerarOrcamentoPDFManual(Orcamentocurso orcamentocurso, String tipo) throws IOException{
		GerarOcamentoManualPDFBean o = new GerarOcamentoManualPDFBean(orcamentocurso);
		OrcamentoPDFFactory.setLista(o.getLista());

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
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
				orcamentocurso.getFornecedorcidade().getCidade().getPais().getIdpais() + ".png",
				"/systm/pais/"); 
		Image imgPais = null;
		try {
			imgPais = ImageIO.read(is);
			if (imgPais==null){
				is = ftp.receberArquivo("","0.png", "/systm/pais/");
				imgPais = ImageIO.read(is);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.desconectar();
		ftp.conectar();
		is = ftp.receberArquivo("",
				orcamentocurso.getFornecedorcidade().getIdfornecedorcidade() + ".png",
				"/systm/fornecedorcidade/"); 
		Image imgCidade = null;
		try {
			imgCidade = ImageIO.read(is);
			if (imgCidade==null){
				is = ftp.receberArquivo("","0.png", "/systm/fornecedorcidade/");
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
	
	public void salvarVendaLead(){
		if(idvenda>0){ 
			VendasFacade vendasFacade = new VendasFacade();
			Vendas vendas = vendasFacade.consultarVendas(idvenda);
			if(vendas!=null){
				vendas.setIdlead(lead.getIdlead());
				vendasFacade.salvar(vendas);
				LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 6);
				lead.setSituacao(6);
				LeadFacade leadFacade = new LeadFacade();
				lead = leadFacade.salvar(lead);
			}else{
				Mensagem.lancarMensagemErro("Atenção!", "ID da venda inválido.");
			}
		}
	}
	
	public boolean desabilitarBtnAnterior(){
		if(posicao==0){
			return true;
		}
		return false;
	}
	
	public boolean desabilitarBtnProximo(){
		if (listaLead == null) {
			return true;
		}else{
			if((listaLead.size()-1)==posicao){
				return true;
			}
			return false;
		}
	}
	
	public String historicoClienteAnterior(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaLead", listaLead);
		session.setAttribute("posicao", posicao-1);
		session.setAttribute("funcao", funcao);
		return "historicoCliente";
	}
	
	public String historicoProximoCliente(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaLead", listaLead);
		session.setAttribute("posicao", posicao+1);
		session.setAttribute("funcao", funcao);
		return "historicoCliente";
	}
	
	public String novoOrcamentoVoluntariado(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		lead.getCliente().setLead(lead);
		lead.getCliente().setClienteLead(true);
		session.setAttribute("cliente", lead.getCliente());
		return "filtrarVoluntariadoProjetoOrcamento";
	}
	
	
	public String informacoes(Vendas vendas) { 
		if (vendas.getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas); 
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}
	
	public String documentacao(Vendas vendas) { 
		if (vendas.getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas); 
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
	}
	
	public String gerarRelatorioRecibo(Vendas vendas) throws SQLException, IOException {
		FuncoesFichasBean funcoesFichasBean = new FuncoesFichasBean(vendas);
		funcoesFichasBean.gerarRelatorioRecibo();
		return "";
	}
	
	public String gerarRelatorioTermoVisto(Vendas vendas) throws SQLException, IOException {
		FuncoesFichasBean funcoesFichasBean = new FuncoesFichasBean(vendas);
		funcoesFichasBean.gerarRelatorioTermoVisto(usuarioLogadoMB);
		return "";
	}
	
	public String gerarRelatorioContrato(Vendas vendas) throws SQLException, IOException {
		FuncoesFichasBean funcoesFichasBean = new FuncoesFichasBean(vendas);
		funcoesFichasBean.gerarRelatorioContrato();
		return "";
	}
	
	public String boletos(Vendas vendas) {
		FuncoesFichasBean funcoesFichasBean = new FuncoesFichasBean(vendas);
		funcoesFichasBean.boletos();
		return "";
	}
	
	public String imprimirFicha(Vendas vendas) throws IOException {
		FuncoesFichasBean funcoesFichasBean = new FuncoesFichasBean(vendas);
		funcoesFichasBean.gerarRelatorioFicha();
		return ""; 
	}
	
	public void habilitarDescricaoCancelamento(){
		if (lead.getMotivocancelamento1().isObservacao()) {
			habilitarDescricao = false;
		}else{
			habilitarDescricao = true;
		}
	}
	
	
	public String emitirVendaPos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		int idprodutos = lead.getProdutos().getIdprodutos();
		if (idprodutos != 13) {
			session.setAttribute("cliente", lead.getCliente());
			session.setAttribute("lead", lead); 
			if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getAupair()) {
				return "cadAuPair";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getCursos()) {
				return "cadFichaCurso";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getDemipair()) {
				return "cadDemiPair";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
				return "cadCursosTeens";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
				return "cadHighSchool";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getTrainee()) {
				session.setAttribute("tipo", "EUA"); 
				return "cadTrainee";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
				return "cadVoluntariado";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getWork()) {
				return "cadWorkandTravel";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPacotes()) {
				return "cadpacote";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPassagem()) {
				return "cadPassagem";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVisto()) {
				return "cadVistos";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getSeguroPrivado()) {
				return "fichaSeguroViagem";
			}else if(produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighereducation()){
				return "questionarioHe";
			}
		}
		return "";
	} 
	
	public void selecionarLead(Lead lead){
		this.lead = lead;
	}
	
	public boolean habiltiarCampos(){
		boolean resultado = false;
		if (lead.getProdutos().getIdprodutos() == 1) {
			resultado = habilitarCampoCurso();
		}else if(lead.getProdutos().getIdprodutos() == 16){
			resultado = habilitarCampoVoluntariado();
		}
		return resultado;
	}
	
	public boolean habilitarCampoCurso(){
		if (lead.getProdutos().getIdprodutos() == 1) {
			return true;
		}
		return false;
	}
	
	
	public boolean habilitarCampoVoluntariado(){
		if (lead.getProdutos().getIdprodutos() == 16) {
			return true;
		}
		return false;
	}
	
	
	public String orcamentoVitrine(){
		return "paginainicial";
	}  
}
