/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import br.com.travelmate.managerBean.fornecedor.FornecedorMB; 
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
	private VerificarLogin verificarLogin;
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

	public VerificarLogin getVerificarLogin() {
		return verificarLogin;
	}

	public void setVerificarLogin(VerificarLogin verificarLogin) {
		this.verificarLogin = verificarLogin;
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
		atualizaTempoLogado();
		return "comissaovenda";
	}

	public String contasPagar() {
		atualizaTempoLogado();
		return "consultacontaspagar";
	}

	public String planoConta() {
		atualizaTempoLogado();
		return "consplanoconta";
	}

	public String vendas() {
		atualizaTempoLogado();
		return "vendas";
	}

	public String cancelamento() {
		atualizaTempoLogado();
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
		atualizaTempoLogado();
		return "consultacontasreceber";
	}

	public String passagem() {
		atualizaTempoLogado();
		return "passagem";
	}

	public String pacoteso() {
		atualizaTempoLogado();
		return "consultapperadora";
	}

	public String pacotesa() {
		if(usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isPacote()) {
			atualizaTempoLogado();
			return "consultapacotesagencia";
		}return "";
	}

	public String cliente() {
		atualizaTempoLogado();
		return "consultacliente";
	}

	public String fornecedor() {
		atualizaTempoLogado();
		return "consultafornecedor";
	}

	public String produtos() {
		atualizaTempoLogado();
		return "consultaprodutos";
	}

	public String curso() {
		atualizaTempoLogado();
		return "consultafichacurso";
	}

	public String faturafranquia() {
		atualizaTempoLogado();
		return "faturafranquia";
	}

	public String highSchool() {
		atualizaTempoLogado();
		return "consultaHighSchool";
	}

	public String ladies() {
		atualizaTempoLogado();
		return "consultaLadies";
	}

	public String cursosTeens() {
		atualizaTempoLogado();
		return "cursosTeens";
	}

	public String produtoCurso() {
		atualizaTempoLogado();
		return "orcamentocurso";
	}

	public String orcamentoCurso() {
		atualizaTempoLogado();
		return "consultaorcamentocurso";
	}

	public String orcamentoManual(String tipo) {
		atualizaTempoLogado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipoocamento", tipo);
		return "orcamentoManual";
	}

	public String modeloOrcamentoManual() {
		atualizaTempoLogado();
		return "modeloOrcamentoManual";
	}

	public String seguroViagem() {
		atualizaTempoLogado();
		return "consultaSeguro";
	}

	public String visto() {
		atualizaTempoLogado();
		return "consultaVistos";
	}

	public String auPair() {
		atualizaTempoLogado();
		return "consultaAuPair";
	}

	public String demiPair() {
		atualizaTempoLogado();
		return "consultaDemiPair";
	}

	public String trainee() {
		atualizaTempoLogado();
		return "consultaTrainee";
	}

	public String voluntariado() {
		atualizaTempoLogado();
		return "consultaVoluntariado";
	}

	public String workAndTravel() {
		atualizaTempoLogado();
		return "consultaWorkandTravel";
	}

	public String conciliacaoBancaria() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 490);
		RequestContext.getCurrentInstance().openDialog("conciliacaobancaria", options, null);
		return "";
	}

	public String pagamentos() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("pagamentos", options, null);

		return "";
	}

	public String relatorioVendas() {
		atualizaTempoLogado();
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
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("comissaoconsultor", options, null);
		return "";
	}

	public String comissaogerente() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("comissaogerente", options, null);
		return "";
	}

	public String relatoriosContasReceber() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("filtrarcontasreceber", options, null);
		return "";
	}

	public String comissaoterceiros() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 490);
		RequestContext.getCurrentInstance().openDialog("comissaoterceiros", options, null);
		return "";
	}

	public String produtoremessa() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 490);
		RequestContext.getCurrentInstance().openDialog("produtoremessa", options, null);
		return "";
	}

	public String calculoFormaPagamento() {
		atualizaTempoLogado();
		return "calculoFormaPagamento";
	}

	public String midia(String tipoMidais) {
		atualizaTempoLogado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", tipoMidais);
		return "consultamidia";
	}

	public String consGuias(String tipoMidais) {
		atualizaTempoLogado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", tipoMidais);
		return "consGuias";
	}

	public String consVideos(String tipoMidais) {
		atualizaTempoLogado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", tipoMidais);
		return "consVideos";
	}

	public String flyers() {
		atualizaTempoLogado();
		return "flyers";
	}

	public String invoicesCurso() {
		atualizaTempoLogado();
		return "consultainvoice";
	}

	public String dadosVenda() {
		atualizaTempoLogado();
		return "dadosVenda";
	}

	public void atualizaTempoLogado() {
		if (usuarioLogadoMB != null) {
			usuarioLogadoMB.atualizaTempoLogado();
		}
	}

	public String promocao() {
		return "conspromocao";
	}

	public String terceiros() {
		atualizaTempoLogado();
		return "terceiros";
	}

	public String consVincularTerceiros() {
		atualizaTempoLogado();
		return "consVincularTerceiros";
	}

	public String controleCurso() {
		atualizaTempoLogado();
		return "controleCurso";
	}

	public String controleCursosTeens() {
		atualizaTempoLogado();
		return "controleCursosTeens";
	}

	public String controleHighSchool() {
		atualizaTempoLogado();
		return "controleHighSchool";
	}

	public String controleAuPair() {
		atualizaTempoLogado();
		return "controleAupair";
	}

	public String controleTrainee() {
		atualizaTempoLogado();
		return "controleTrainee";
	}

	public String controleVoluntariado() {
		atualizaTempoLogado();
		return "controleVoluntariado";
	}

	public String controleWorkAndTravel() {
		atualizaTempoLogado();
		return "controleWorkAndTravel";
	}

	public String controleSeguroViagem() {
		atualizaTempoLogado();
		return "controleSeguroViagem";
	}

	public String controleVisto() {
		atualizaTempoLogado();
		return "controleVisto";
	}

	public String controlePassagem() {
		atualizaTempoLogado();
		return "controlePassagem";
	}

	public String analiticoVendas() {
		atualizaTempoLogado();
		return "analiticoVendas";
	}

	public String escolas() {
		atualizaTempoLogado();
		return "consEscolasCadastradas";
	}

	public String valoresTrainee() {
		atualizaTempoLogado();
		return "valoresTrainee";
	}

	public String valoresSeguro() {
		atualizaTempoLogado();
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
		atualizaTempoLogado();
		return "autorizacaodebito";
	}

	public String relatoriosComissaoParceiros() {  
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("relatoriosComissaoParceiros", options, null); 
		return "";
	}

	public String valoresWorkAndTravel() {
		atualizaTempoLogado();
		return "valoresWork";
	}

	public String valoresAupair() {
		atualizaTempoLogado();
		return "valoresAupair";
	}

	public String controleDemiPair() {
		atualizaTempoLogado();
		return "controleDemipair";
	}

	public String avisos() {
		atualizaTempoLogado();
		return "avisos";
	}

	public String midias() {
		atualizaTempoLogado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipomidias", "");
		return "consultamidia";
	}

	public String controleTeens() {
		atualizaTempoLogado();
		return "controleTeens";
	}

	public String valoresTeens() {
		atualizaTempoLogado();
		return "valoresTeens";
	}

	public String controleAlteracoes() {
		return "controleAlteracoes";
	}

	public String departamentos() {
		return "consDepartamentos";
	}

	public String reciboAvulso() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("reciboAvulso", options, null);
		return "";
	}

	public String relatoriosCobranca() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("relatoriosCobranca", options, null);
		return "";
	}

	public String mapaVendas() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("mapaVendas", options, null);
		return "";
	}

	public String gerarPIN() {
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("gerarPIN", options, null);
		return "";
	}

	public String alteracaofinanceiro() {
		return "alteracaofinanceiro";
	}

	public String relatorioCancelamento() {
		atualizaTempoLogado(); 
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

	public String relatorioEscolasTarifario() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/fornecedor/reporttarifario.jasper");
		Map parameters = new HashMap();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Fornecedores-Cadastrados.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(FornecedorMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(FornecedorMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
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
		session.setAttribute("funcao", "hoje");
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		Map parameters = new HashMap();
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
		return "consquestionarioHe";
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
		atualizaTempoLogado();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 370);
		RequestContext.getCurrentInstance().openDialog("relatorioConferenciaCartaoCredito", options, null);
		return "";
	}

	public String consPastasVideos() {
		return "consPastasVideos";
	}

	public String relatorioRemessaRetorno(String tipo) {
		atualizaTempoLogado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		session.setAttribute("tipo", tipo);
		RequestContext.getCurrentInstance().openDialog("relatorioRemessaRetorno", options, null);
		return "";
	}

	public String imprimirFatura() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
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
		return "consControleVendas";
	}

	public String cursosPacotes() {
		return "consPacotesAtivos";
	}

	public String produtoOrcamentoGrupo() {
		return "consProdutoOrcamentoGrupo";
	}

	public String relatorioDocsFornecedor() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
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
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioMediaMatch", options, null);
		return "";
	}

	public String parceiroTurismo() {
		atualizaTempoLogado();
		return "consFornecedorTurismo";
	}

	public String pacotesFornecedor() {
		atualizaTempoLogado();
		return "consPacotesFornecedor";
	}

	public String relatorioGeralHS() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioGeralHS", options, null);
		return "";
	}

	public String relatorioVistoHS() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioVistoHS", options, null);
		return "";
	}

	public String relatorioPassagensHS() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioPassagensHS", options, null);
		return "";
	}

	public String calculadoraMargem() { 
		return "calcularMargem";
	}

	public String leadsEncaminhados() {
		atualizaTempoLogado();
		return "consLeadEncaminhado";
	}

	public String revisaoFinanceiro() {
		atualizaTempoLogado();
		return "consVendasRevisaoFinanceiro";
	}

	public String pacotes() {
		return "cursospacotes";
	}

	public String consultarMotivoPendencia() {
		atualizaTempoLogado();
		return "consVendaMotivoPendencia";
	}

	public String followUpCobranca() {
		atualizaTempoLogado();
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
		atualizaTempoLogado();
		return "consTraducao";
	}
	
	public String consWorkSponsor() {
		atualizaTempoLogado();
		return "consWorkSponsor";
	}
	
	public String consWorkEmpregador() {
		atualizaTempoLogado();
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
		atualizaTempoLogado();
		return "consUtil";
	}
	
	public String relatorioCancelamentoFatura() {
		atualizaTempoLogado();
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
		atualizaTempoLogado();
		return "consAgenda";
	}
	
	
	public String vendasCurso() {
		atualizaTempoLogado();
		return "consVendaCursos";
	}
	
	public String vendasCliente() {
		atualizaTempoLogado();
		return "consVendasClientes";
	}
	
	public String relatorioLead() {
		atualizaTempoLogado();
		return "relatorioLeads";
	}
	
	
	public String relatorioHSAnual() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("relatorioHighSchoolAnual", options, null);
		return "";
	}
	
	public String relatorioHSMes() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
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
	
}
