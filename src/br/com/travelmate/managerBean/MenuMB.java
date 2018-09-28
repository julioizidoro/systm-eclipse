package br.com.travelmate.managerBean;

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
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
  
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VersaoUsuarioFacade;
import br.com.travelmate.managerBean.financeiro.boleto.DadosBoletoBean;
import br.com.travelmate.model.Versaousuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Wolverine
 */
@Named
@SessionScoped
public class MenuMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;  
	private boolean logado;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			logado = true;
			getUsuarioLogadoMB();
		}
	}

	

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String comissaoVendas() {
		
		return "comissaovenda";
	}

	public String contasPagar() {
		
		return "consultacontaspagar";
	}

	public String planoConta() {
		
		return "consplanoconta";
	}

	public String vendas() {
		
		return "vendas";
	}

	public String cancelamento() {
		
		return "conscancelamento";
	}

	public String contasRecebers() {
		DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
		dadosBoletoBean.emitir("0");
		return "";
	}

	public String contasReceber() {
		// DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
		// dadosBoletoBean.emitir();
		
		return "consultacontasreceber";
	}

	public String passagem() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "passagem";
	}

	public String pacoteso() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultapperadora";
	}

	public String pacotesa() {
		if(usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isPacote()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("chamadaTela", "Menu");
			return "consultapacotesagencia";
		}return "";
	}

	public String cliente() {
		
		return "consultacliente";
	}

	public String fornecedor() {
		
		return "consultafornecedor";
	}

	public String produtos() {
		
		return "consultaprodutos";
	}

	public String curso() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultafichacurso";
	}

	public String faturafranquia() {
		
		return "faturafranquia";
	}

	public String highSchool() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaHighSchool";
	}

	public String ladies() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaLadies";
	}

	public String cursosTeens() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "cursosTeens";
	}

	public String produtoCurso() {
		
		return "orcamentocurso";
	}

	public String orcamentoCurso() {
		
		return "consultaorcamentocurso";
	}

	public String orcamentoManual(String tipo) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipoocamento", tipo);
		return "orcamentoManual";
	}

	public String modeloOrcamentoManual() {
		
		return "modeloOrcamentoManual";
	}

	public String seguroViagem() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaSeguro";
	}

	public String visto() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaVistos";
	}

	public String auPair() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaAuPair";
	}

	public String demiPair() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaDemiPair";
	}

	public String trainee() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaTrainee";
	}

	public String voluntariado() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaVoluntariado";
	}

	public String workAndTravel() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaWorkandTravel";
	}

	public String conciliacaoBancaria() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 490);
		RequestContext.getCurrentInstance().openDialog("conciliacaobancaria", options, null);
		return "";
	}

	public String pagamentos() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("pagamentos", options, null);

		return "";
	}

	public String relatorioVendas() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("filtrarvendas", options, null);
		return "";
	}

	public String relatorioSinteticoVendas() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("relatoriosSinteticoVendas", options, null);
		return "";
	}

	public String comissaoconsultor() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("comissaoconsultor", options, null);
		return "";
	}

	public String comissaogerente() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("comissaogerente", options, null);
		return "";
	}

	public String relatoriosContasReceber() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("filtrarcontasreceber", options, null);
		return "";
	}

	public String comissaoterceiros() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 490);
		RequestContext.getCurrentInstance().openDialog("comissaoterceiros", options, null);
		return "";
	}

	public String produtoremessa() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 490);
		RequestContext.getCurrentInstance().openDialog("produtoremessa", options, null);
		return "";
	}

	public String calculoFormaPagamento() {
		
		return "calculoFormaPagamento";
	}

	public String midia(String tipoMidais) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", tipoMidais);
		return "consultamidia";
	}

	public String consGuias(String tipoMidais) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", tipoMidais);
		return "consGuias";
	}

	public String consVideos(String tipoMidais) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", tipoMidais);
		return "consVideos";
	}

	public String flyers() {
		
		return "flyers";
	}

	public String invoicesCurso() {
		
		return "consultainvoice";
	}

	public String dadosVenda() {
		
		return "dadosVenda";
	}

	

	public String promocao() {
		return "conspromocao";
	}

	public String terceiros() {
		
		return "terceiros";
	}

	public String consVincularTerceiros() {
		
		return "consVincularTerceiros";
	}

	public String controleCurso() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleCurso";
	}

	public String controleCursosTeens() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleCursosTeens";
	}

	public String controleHighSchool() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleHighSchool";
	}

	public String controleAuPair() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleAupair";
	}

	public String controleTrainee() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleTrainee";
	}

	public String controleVoluntariado() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleVoluntariado";
	}

	public String controleWorkAndTravel() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "controleWorkAndTravel";
	}

	public String controleSeguroViagem() {
		return "controleSeguroViagem";
	}

	public String controleVisto() {
		
		return "controleVisto";
	}

	public String controlePassagem() {
		
		return "controlePassagem";
	}

	public String analiticoVendas() {
		
		return "analiticoVendas";
	}

	public String escolas() {
		
		return "consEscolasCadastradas";
	}

	public String valoresTrainee() {
		
		return "valoresTrainee";
	}

	public String valoresSeguro() {
		
		return "valoresSeguro";
	}

	public String orcamentoVoluntariado() {
		return "Voluntariado";
	}

	public String valoresHighSchool() {
		return "valoresHighSchool";
	}

	public String consConciliacaoBancaria() {
		return "consConciliacaoBancaria";
	}

	public String relatoriosAutorizacaoDebito() {
		
		return "autorizacaodebito";
	}

	public String relatoriosComissaoParceiros() {  
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("relatoriosComissaoParceiros", options, null); 
		return "";
	}

	public String valoresWorkAndTravel() {
		
		return "valoresWork";
	}

	public String valoresAupair() {
		
		return "valoresAupair";
	}

	public String controleDemiPair() {
		
		return "controleDemipair";
	}

	public String avisos() {
		
		return "avisos";
	}

	public String midias() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", "");
		return "consultamidia";
	}

	public String controleTeens() {
		
		return "controleTeens";
	}

	public String valoresTeens() {
		
		return "valoresTeens";
	}

	public String controleAlteracoes() {
		return "controleAlteracoes";
	}

	public String departamentos() {
		return "consDepartamentos";
	}

	public String reciboAvulso() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("reciboAvulso", options, null);
		return "";
	}

	public String relatoriosCobranca() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("relatoriosCobranca", options, null);
		return "";
	}

	public String mapaVendas() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("mapaVendas", options, null);
		return "";
	}

	public String gerarPIN() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("gerarPIN", options, null);
		return "";
	}

	public String alteracaofinanceiro() {
		return "alteracaofinanceiro";
	}

	public String relatorioCancelamento() {
		 
		return "relatorioCancelamento";
	}

	public String cambio() {
		return "consCambio";
	}

	public String cambioFranquia() {
		return "consCambioFranquia";
	}

	public String metasFaturamento() {
		return "consMetasFaturamento";
	}

	public String regrasPontuacao() {
		return "consRegras";
	}

	public String relatorioCursoParceiros() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 520);
		RequestContext.getCurrentInstance().openDialog("relatorioParceiros", options, null);
		return "";
	}

	public String relatorioCursoFranquias() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 520);
		RequestContext.getCurrentInstance().openDialog("relatorioVendaFranquias", options, null);
		return "";
	}

	public String relatorioEmbarque() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 520);
		RequestContext.getCurrentInstance().openDialog("relatorioEmbarque", options, null);
		return "";
	}

	public String relatorioVendaInvoice() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioVendaInvoice", options, null);
		return "";
	}

	public String promocoes() {
		return "consPromocoes";
	}

	public String promocoesAcomodacao() {
		return "consPromocoesAcomodacao";
	}

	public String editarImagem() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("editarImagemUsuario", options, null);
		return "";
	}

	public String vendasUsuario() {
		if (usuarioLogadoMB.getUsuario().isVende()) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 610);
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("consVendasUsuario", options, null);
		}
		return "";
	}

	public String promocoesTaxas() {
		return "consPromocoesTaxas";
	}

	public String promocoesBrindes() {
		return "consPromocoesBrindes";
	}

	public String consUsuario() {
		return "consUsuario";
	}

	public String consUnidade() {
		return "consUnidade";
	}

	public String consBanco() {
		return "consBanco";
	}

	public String relatorioEmbarqueUnidade() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 520);
		RequestContext.getCurrentInstance().openDialog("relatorioEmbarqueUnidade", options, null);
		return "";
	}

	public String consAcesso() {
		return "consAcesso";
	}

	public String consCoeficiente() {
		return "consCoeficiente";
	}

	public String valoresAcomodacao() {
		return "consValoresAcomodacao";
	}

	public String intervalosEscola() {
		return "consIntervalos";
	}

	public String pontuacaoescola() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 910);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("pontuacaoescola", options, null);
		return "";
	}

	public String guiaescola() {
		return "consGuiaEscola";
	}

	public String consDepartamentos1Videos() {
		return "consDepartamentos1Videos";
	}

	public String consTmStar() {
		return "consTmStar";
	}

	public String topTmStar() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("topTmStar", options, null);
		return "";
	}

//	public String relatorioEscolasTarifario() throws IOException, SQLException {
//		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
//				.getContext();
//		String caminhoRelatorio = ("/reports/fornecedor/reporttarifario.jasper");
//		Map parameters = new HashMap();
//		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
//		BufferedImage logo = ImageIO.read(f);
//		parameters.put("logo", logo);
//		GerarRelatorio gerarRelatorio = new GerarRelatorio();
//		try {
//			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Fornecedores-Cadastrados.pdf", null);
//		} catch (JRException ex1) {
//			Logger.getLogger(FornecedorMB.class.getName()).log(Level.SEVERE, null, ex1);
//		} catch (IOException ex) {
//			Logger.getLogger(FornecedorMB.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		return "";
//	}
	
	
	public String relatorioEscolasTarifario() {
		return "relatorioFornCidadeIdioma";
	}

	public void relatorioMateRunners() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioMateRunners", options, null);
	}

	public String novoLead() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("telaRetorno", "menu");
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("cadLead", options, null);
		return "";
	}

	public String distribuicaoLeads() {
		return "distribuicaoLeads";
	}

	public String followUp() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("funcao", "todos");
		return "followUp";
	}

	public String followUpTodos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("funcao", "todos");
		return "followUp";
	}

	public String followUpHoje() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("funcao", "hoje");
		return "followUp";
	}

	public String followUpAtrasadas() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("funcao", "atrasados");
		return "followUp";
	}

	public String followUpNovos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("funcao", "novos");
		return "followUp";
	}

	public String escolasVendidas() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 320);
		RequestContext.getCurrentInstance().openDialog("escolasVendidas", options, null);
		return "";
	}

	public String paisesVendidos() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 320);
		RequestContext.getCurrentInstance().openDialog("paisesVendidos", options, null);
		return "";
	}

	public String cidadesVendidas() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 320);
		RequestContext.getCurrentInstance().openDialog("cidadesVendidas", options, null);
		return "";
	}

	public String situacaoContato() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 420);
		RequestContext.getCurrentInstance().openDialog("situacaoContato", options, null);
		return "";
	}

	public String situacaoLead() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 420);
		RequestContext.getCurrentInstance().openDialog("situacaoLead", options, null);
		return "";
	}

	public String motivoCancelamento() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("motivoCancelamento", options, null);
		return "";
	}

	public String origemLeads() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 290);
		RequestContext.getCurrentInstance().openDialog("origemLeads", options, null);
		return "";
	}

	public String modeloContratoCurso() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/modelocontratoCursoPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de curso.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoAuPair() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/aupair/modelocontratoAupairPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Au Pair.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoCaregiver() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/aupair/modelocontratoCaregiverPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Caregiver.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoCursoTeens() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/cursosTeens/modelocontratoTeensPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de curso teens.pdf",
					null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoHighSchool() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/highSchool/modelocontratoHighSchoolPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de High School.pdf",
					null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoWorkIndependent() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/worktravel/modelocontratoWorkIndependentPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Work & Travel.pdf",
					null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoWorkPremium() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/worktravel/modelocontratoWorkPremiumPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Work & Travel.pdf",
					null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoVoluntariado() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/voluntariado/modelocontratoVoluntariadoPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Voluntariado.pdf",
					null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoEstagioAUS() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/trainee/modelocontratoEstagioAustralia01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Trainee.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoTrainee() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/trainee/modelocontratoTraineePagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Trainee.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoDemipair() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/demipair/modelocontratoDemipairPagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Demi Pair.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String modeloContratoHigherEducation() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/higherEducation/modeloContratoHePagina01.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/marcadguamodelo.PNG"));
		BufferedImage modelo = ImageIO.read(f);
		parameters.put("modelo", modelo);
		parameters.put("idunidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Modelo contrato de Higher Education.pdf",
					null);
		} catch (JRException ex1) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(MenuMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String valoresHigherEducation() {
		return "valoresHigherEducation";
	}

	public String liberacaoDepoimentos() {
		return "liberacaoDepoimentos";
	}

	public String questionarioHe() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consultaquestionarioHe";
	}
	
	public String consquestionarioHe() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consquestionarioHe";
	}
	
	
	public String formularioAssessoria() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consFormAssessoria";
	}
	
	public String heFichaFinal() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consHeFichaFinal";
	}

	public String promocoesAtivas() {
		return "consPromocoesAtivas";
	}

	public String mtp() {
		return "consMtp";
	}

	public String consMotivoCancelamento() {
		return "consMotivoCancelamento";
	}

	public String cadastroVersoes() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadVersoes", options, null);
		return "";
	}

	public String consVersoes() {
		Date dataAtual = Formatacao.formatarDataAgora();
		if (usuarioLogadoMB.getUsuario().getDataversao() != null) {
			if (usuarioLogadoMB.getUsuario().getDataversao().before(dataAtual)) {
				VersaoUsuarioFacade versaoUsuarioFacade = new VersaoUsuarioFacade();
				String sql = "SELECT v FROM Versaousuario v where v.versoes.data>='"
						+ Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getDataversao())
						+ "' and v.versoes.data<='" + Formatacao.ConvercaoDataSql(new Date())
						+ "' and v.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
				List<Versaousuario> listaVersoes = versaoUsuarioFacade.listar(sql);
				if (listaVersoes == null) {
					listaVersoes = new ArrayList<>();
				}
				if (listaVersoes.size() > 0) {
					FacesContext fc = FacesContext.getCurrentInstance();
					HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
					session.setAttribute("listaVersoes", listaVersoes);
					UsuarioFacade usuarioFacade = new UsuarioFacade();
					usuarioLogadoMB.getUsuario().setDataversao(new Date());
					usuarioFacade.salvar(usuarioLogadoMB.getUsuario());
					Map<String, Object> options = new HashMap<String, Object>();
					options.put("contentWidth", 600);
					RequestContext.getCurrentInstance().openDialog("consVersoes", options, null);
				}

			}

		}

		return "";
	}

	public String consDepartamento() {
		return "consDepartamento";
	}

	public String consFuncao() {
		return "consFuncao";
	}

	public String visualizarFuncao() {
		return "consDepartamentoFuncao";
	}

	public String consControleVistos() {
		return "consControleVistos";
	}

	public String consRecebimentos() {
		return "consRecebimento";
	}

	public String controleEmail() {
		return "controleEmail";
	}

	public String consCartaoCredito() {
		return "consCartaoCredito";
	}

	public String consLancamentoCartao() {
		return "consLancamentoCartao";
	}

	public String relatorioConferenciaCartao() {
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 370);
		RequestContext.getCurrentInstance().openDialog("relatorioConferenciaCartaoCredito", options, null);
		return "";
	}

	public String consPastasVideos() {
		return "consPastasVideos";
	}

	public String relatorioRemessaRetorno(String tipo) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		session.setAttribute("tipo", tipo);
		RequestContext.getCurrentInstance().openDialog("relatorioRemessaRetorno", options, null);
		return "";
	}

	public String imprimirFatura() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 225);
		RequestContext.getCurrentInstance().openDialog("imprimirRelatorioFatura", options, null);
		return "";
	}

	public String fornecedorDocs() {
		return "fornecedorDocs";
	}

	public String consControleDocsVideos() {
		return "consControleDocsVideos";
	}

	public String consControleVendas() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consControleVendas";
	}

	public String cursosPacotes() {
		return "consPacotesAtivos";
	}

	public String produtoOrcamentoGrupo() {
		return "consProdutoOrcamentoGrupo";
	}

	public String relatorioDocsFornecedor() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioDocumentosFornecedor", options, null);
		return "";
	}

	public String produtosOrcamentoVoluntariado() {
		return "consVoluntariadoProjeto";
	}

	public String voluntariadoProjetoOrcamento() {
		return "consVoluntariadoProjetoOrcamento";
	}

	public String comissaocontrole() {
		return "consComissaoControle";
	}

	public String mediaMatch() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioMediaMatch", options, null);
		return "";
	}

	public String parceiroTurismo() {
		
		return "consFornecedorTurismo";
	}

	public String pacotesFornecedor() {
		
		return "consPacotesFornecedor";
	}

	public String relatorioGeralHS() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioGeralHS", options, null);
		return "";
	}

	public String relatorioVistoHS() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioVistoHS", options, null);
		return "";
	}

	public String relatorioPassagensHS() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioPassagensHS", options, null);
		return "";
	}

	public String calculadoraMargem() { 
		return "calcularMargem";
	}

	public String leadsEncaminhados() {
		
		return "consLeadEncaminhado";
	}

	public String revisaoFinanceiro() {
		
		return "consVendasRevisaoFinanceiro";
	}

	public String pacotes() {
		return "cursospacotes";
	}

	public String consultarMotivoPendencia() {
		
		return "consVendaMotivoPendencia";
	}

	public String followUpCobranca() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "followupCobranca";
	}
 
	public void mensagemCobranca() {
		Mensagem.lancarMensagemInfo("Pesquisando contas", "");
	}
	
	public String consAcessoUnidade() {
		return "consAcessoUnidade";
	}
	
	public boolean mostrarComissaoParceiros() { 
		if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
			if(usuarioLogadoMB.getUsuario().getAcessounidade().isComissaoparceiros()) {
				return true;
			}else return false;
		}else return true;
	}
	
	public String consSolicitacoes() {
		return "consSolicitacoes";
	}
	
	public String traducao() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "consTraducao";
	}
	
	public String consWorkSponsor() {
		
		return "consWorkSponsor";
	}
	
	public String consWorkEmpregador() {
		
		return "consWorkEmpregador";
	}
	
	public String relatoriosComissaoParceirosTeens() {  
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("relatoriosComissaoParceirosTeens", options, null); 
		return "";
	}
	
	public String relatorioSolicitacoes() {  
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 310);
		RequestContext.getCurrentInstance().openDialog("relatorioSolicitacoes", options, null); 
		return "";
	}
	
	public String consUtil() {
		
		return "consUtil";
	}
	
	public String relatorioCancelamentoFatura() {
		
		return "relatorioCancelamentoFatura";
	}
	
	public String pesquisarLancamentosCredito() {  
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("pesquisarCartaoCreditoLancamento", options, null); 
		return "";
	}
	
	public void retornoDialogPesquisaLancamentos(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/inicio/pages/cartaocreditolancamento/consPesquisaLancamentosCredito.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public String consAgenda() {
		
		return "consAgenda";
	}
	
	
	public String vendasCurso() {
		
		return "consVendaCursos";
	}
	
	public String vendasCliente() {
		
		return "consVendasClientes";
	}
	
	public String relatorioLead() {
		
		return "relatorioLeads";
	}
	
	
	public String relatorioHSAnual() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioHighSchoolAnual", options, null);
		return "";
	}
	
	public String relatorioHSMes() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioHighSchoolMensal", options, null);
		return "";
	}
	
	
	public void relatorioProtutoRunners(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioProductRunners", options, null);
	}
	
	public void relatorioTmRace(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioTMRace", options, null);
	}
	    
	public String relatorioDashBoard(){
		return "relatorioDashBoard";
	}
	
	public void filtrarDashBoard(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("filtrarDashBoard", options, null);
	}
	
	
	public String modeloOrcamentoTarifario(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.removeAttribute("lead");
		return "inicialPacotes";
	} 
	
	public String consultarNoticia(){
		return "consNoticias";
	}
	
	public String vendasProduto(){
		return "consVendasProduto";
	}
	
	public String comissaoConsultor() {
		return "comissaoConsultor";
	}
	
	public String consFiltroProdutosOrcamento() {
		return "consFiltroProdutosOrcamento";
	}
	
	public String relatorioCliente() {
		return "relatorioCliente";
	}
	
	public String relatorioVendasSintetico() {
		return "relatorioVendasSintetico";
	}
	
	public String consClienteLead() {
		return "consClienteLead";
	}
	
	
	public String relatorioLeadsDetalhado() {
		return "relatorioLeadsDetalhado";
	}
	
	
	public String orcamentoHE() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("chamadaTela", "Menu");
		return "orcamentoHe";
	}
	
	public String consAcomodacao() {
		return "consAcomodacao";
	}
	
	public boolean verificarAcomodacao() {
		if (usuarioLogadoMB == null || usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getUnidadenegocio() == null) {
			return false;
		}
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() <= 2) {
			return true;
		}
		return false;
	}
	
	public String relatorioVendasGerentes() {
		return "relatorioVendasGerentes";
	}
	
	
	public boolean habilitarModeloContrato() {
		if (usuarioLogadoMB != null && usuarioLogadoMB.getUsuario() != null) {
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
				return true;
			}
		}
		return false;
	}
	
	
	public String relatorioProgramas() {
		return "relatorioProgramas";
	}
	
	
	public String applicationCurso() {
		return "consApplicationCurso";
	}
	
	public String condicaomes() {
		return "http://local.systm.com.br";
	}
	
	
	
}
