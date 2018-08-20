package br.com.travelmate.managerBean.curso.controle;
 
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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CursoFacade; 
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Curso; 
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao; 
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped   
public class ControleCursoMB implements Serializable {

	/**    
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controlecurso controlecurso;
	private List<Controlecurso> listaControle;
	private String nomeCliente;
	private Date iniDataEmbarque;
	private Date finalDataEmbarque;
	private String motivoCancelamento;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String docs;
	private String situacao;
	private String idvenda;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private String voltar;
	private int nFichasFinalizadas;
	private int nFichasAndamento;
	private int nFichaCancelada;
	private Integer nFichaFinanceiro;
	private String numeroFichas;
	private Date datainivenda;
	private Date datafimvenda;
	private String situacaoFicha; 
	private String sql;
	private String obsTM;
	private List<Controlecurso> listaVendasCursoFinalizada;
	private List<Controlecurso> listaVendasCursoAndamento;
	private List<Controlecurso> listaVendasCursoCancelada; 
	private List<Controlecurso> listaVendasCursoFinanceiro;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendasCursoFinalizada = (List<Controlecurso>) session.getAttribute("listaVendasCursoFinalizada");
		listaVendasCursoAndamento = (List<Controlecurso>) session.getAttribute("listaVendasCursoAndamento");
		listaVendasCursoFinanceiro = (List<Controlecurso>) session.getAttribute("listaVendasCursoFinanceiro");
		listaVendasCursoCancelada = (List<Controlecurso>) session.getAttribute("listaVendasCursoCancelada");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("listaVendasCursoFinalizada");
		session.removeAttribute("listaVendasCursoAndamento");
		session.removeAttribute("listaVendasCursoProcesso");
		session.removeAttribute("listaVendasCursoFinanceiro");
		session.removeAttribute("listaVendasCursoCancelada");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Controlecurso")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
	        	listaControle =  (List<Controlecurso>) session.getAttribute("listaControle");
		        session.removeAttribute("listaControle");
		        if (listaControle==null){
		        	listarControle(sql);
		        }else{
		        	gerarQuantidadesFichas();
		        }
			}else {
				nFichasFinalizadas = listaVendasCursoFinalizada.size();
				nFichasAndamento = listaVendasCursoAndamento.size();
				nFichaCancelada = listaVendasCursoCancelada.size();
				nFichaFinanceiro = listaVendasCursoFinanceiro.size();
			}
			gerarListaUnidadeNegocio();
			controlecurso= new Controlecurso();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Controlecurso getControlecurso() {
		return controlecurso;
	}

	public void setControlecurso(Controlecurso controlecurso) {
		this.controlecurso = controlecurso;
	}

	public List<Controlecurso> getListaControle() {
		return listaControle;
	}

	public void setListaControle(List<Controlecurso> listaControle) {
		this.listaControle = listaControle;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getIniDataEmbarque() {
		return iniDataEmbarque;
	}

	public void setIniDataEmbarque(Date iniDataEmbarque) {
		this.iniDataEmbarque = iniDataEmbarque;
	}

	public Date getFinalDataEmbarque() {
		return finalDataEmbarque;
	}

	public void setFinalDataEmbarque(Date finalDataEmbarque) {
		this.finalDataEmbarque = finalDataEmbarque;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public String getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(String idvenda) {
		this.idvenda = idvenda;
	} 

	public Integer getnFichasFinalizadas() {
		return nFichasFinalizadas;
	}

	public void setnFichasFinalizadas(Integer nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}

	public Integer getnFichasAndamento() {
		return nFichasAndamento;
	}

	public void setnFichasAndamento(Integer nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public Date getDatainivenda() {
		return datainivenda;
	}

	public void setDatainivenda(Date datainivenda) {
		this.datainivenda = datainivenda;
	}

	public Date getDatafimvenda() {
		return datafimvenda;
	}

	public void setDatafimvenda(Date datafimvenda) {
		this.datafimvenda = datafimvenda;
	}

	public Integer getnFichaCancelada() {
		return nFichaCancelada;
	}

	public void setnFichaCancelada(Integer nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}

	public String getSituacaoFicha() {
		return situacaoFicha;
	}

	public void setSituacaoFicha(String situacaoFicha) {
		this.situacaoFicha = situacaoFicha;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public List<Controlecurso> getListaVendasCursoFinalizada() {
		return listaVendasCursoFinalizada;
	}

	public void setListaVendasCursoFinalizada(List<Controlecurso> listaVendasCursoFinalizada) {
		this.listaVendasCursoFinalizada = listaVendasCursoFinalizada;
	}

	public List<Controlecurso> getListaVendasCursoAndamento() {
		return listaVendasCursoAndamento;
	}

	public void setListaVendasCursoAndamento(List<Controlecurso> listaVendasCursoAndamento) {
		this.listaVendasCursoAndamento = listaVendasCursoAndamento;
	}

	public List<Controlecurso> getListaVendasCursoCancelada() {
		return listaVendasCursoCancelada;
	}

	public void setListaVendasCursoCancelada(List<Controlecurso> listaVendasCursoCancelada) {
		this.listaVendasCursoCancelada = listaVendasCursoCancelada;
	}

	public void setnFichasFinalizadas(int nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}

	public void setnFichasAndamento(int nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}

	public void setnFichaCancelada(int nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}

	public Integer getnFichaFinanceiro() {
		return nFichaFinanceiro;
	}

	public void setnFichaFinanceiro(Integer nFichaFinanceiro) {
		this.nFichaFinanceiro = nFichaFinanceiro;
	}

	public List<Controlecurso> getListaVendasCursoFinanceiro() {
		return listaVendasCursoFinanceiro;
	}

	public void setListaVendasCursoFinanceiro(List<Controlecurso> listaVendasCursoFinanceiro) {
		this.listaVendasCursoFinanceiro = listaVendasCursoFinanceiro;
	}

	public void listarControle(String sql) {
		CursoFacade cursoFacade = new CursoFacade();
		if (sql==null){
			String data = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
			sql = "select c from Controlecurso c where c.situacao='Processo' and c.vendas.dataVenda>='" + data + "' order by c.vendas.dataVenda desc";
		}
		listaControle = cursoFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controlecurso>();
		}
		gerarQuantidadesFichas();
	}
	
	public void gerarQuantidadesFichas(){ 
		numeroFichas =  "" + String.valueOf(listaControle.size());
		nFichasAndamento = 0;
		nFichasFinalizadas = 0; 
		nFichaCancelada=0;
		nFichaFinanceiro = 0;
		listaVendasCursoFinalizada = new ArrayList<>();
		listaVendasCursoAndamento = new ArrayList<>();
		listaVendasCursoCancelada = new ArrayList<>(); 
		listaVendasCursoFinanceiro = new ArrayList<>(); 
		for (int i = 0; i < listaControle.size(); i++) {
			if (listaControle.get(i).getCurso() != null) {
				if (listaControle.get(i).getCurso().getTipoimportacaoorcamento().equalsIgnoreCase("T")) {
					listaControle.get(i).setTipoOrcamento("Tarifário");
				} else if (listaControle.get(i).getCurso().getTipoimportacaoorcamento().equalsIgnoreCase("M")) {
					listaControle.get(i).setTipoOrcamento("Manual");
				} else {
					listaControle.get(i).setTipoOrcamento("Nenhum");
				}
			}else {
				System.out.println("1");
			}
			if (listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasCursoFinalizada.add(listaControle.get(i));
			}else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaControle.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichaFinanceiro = nFichaFinanceiro + 1;
				listaVendasCursoFinanceiro.add(listaControle.get(i));
			}else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& listaControle.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasCursoAndamento.add(listaControle.get(i));
			}else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("CANCELADA")){
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCursoCancelada.add(listaControle.get(i));
			}   
		}
	}

	public String invoice(Controlecurso controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			session.setAttribute("listaControle", listaControle);
			return "consultaInvoice";
		}
		return "";
	}

	public void pesquisar() {
		CursoFacade cursoFacade = new CursoFacade();
		sql = "select c from Controlecurso c where c.vendas.cliente.nome like '%" + nomeCliente + "%'  ";
		if ((unidadenegocio != null) && (unidadenegocio.getIdunidadeNegocio()!=null)) {
			sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if ((idvenda != null) && (idvenda.length() > 0)) {
			sql = sql + " and c.vendas.idvendas=" + idvenda;   
		}
		if ((usuario != null) && (usuario.getIdusuario()!=null)) {
			sql = sql + " and  c.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((iniDataEmbarque != null) && (finalDataEmbarque != null)) {
			sql = sql + " and c.dataEmbarque>='" + Formatacao.ConvercaoDataSql(iniDataEmbarque) + "'";
			sql = sql + " and c.dataEmbarque<='" + Formatacao.ConvercaoDataSql(finalDataEmbarque) + "'";
		}
		if ((datainivenda != null) && (datafimvenda != null)) {
			sql = sql + " and c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(datainivenda) + "'";
			sql = sql + " and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(datafimvenda) + "'";
		}
		if (!situacao.equalsIgnoreCase("TODOS")) {
			sql = sql + " and c.situacao='" + situacao + "'";
		}
		if (!situacaoFicha.equalsIgnoreCase("TODOS")) {
			sql = sql + " and c.vendas.situacao='" + situacaoFicha + "'";
		} 
		sql = sql + " order by c.vendas.dataVenda desc, c.idcontroleCursos desc";
		listaControle = cursoFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controlecurso>();
		}
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public void limpar() {
		iniDataEmbarque = null;
		finalDataEmbarque = null;
		nomeCliente = "";
		situacao="TODOS";
		situacaoFicha="TODOS";
		docs="TODOS";
		unidadenegocio=null;
		usuario=null;
		idvenda=null;
		datainivenda = null;
		datafimvenda = null;
		pesquisar = "Nao";
		listarControle(null);
	}

	public void cancelar() {
		CursoFacade cursoFacade = new CursoFacade();
		for (int i = 0; i < listaControle.size(); i++) {
			if (listaControle.get(i).isSelecionado()) {
				listaControle.get(i).setSituacao("CANCELADA");
				cursoFacade.salvar(listaControle.get(i));
				Vendas vendas = new Vendas();
				vendas = listaControle.get(i).getVendas();
				vendas.setSituacao("CANCELADA");
				vendas.setObsCancelar(motivoCancelamento);
				vendas.setDatacancelamento(new Date());
				vendas.setUsuariocancelamento(usuarioLogadoMB.getUsuario().getIdusuario());
				
				vendas = vendasDao.salvar(vendas);
			}
		}
		listarControle(sql);
	}

	public void finalizar(Controlecurso controle) {
		CursoFacade cursoFacade = new CursoFacade();
		controle.setSituacao("FINALIZADA");
		cursoFacade.salvar(controle);
		listarControle(sql);
	}

	public String documentacao(Controlecurso controle) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		voltar = "controleCurso";
		session.setAttribute("voltar", voltar);
		session.setAttribute("vendas", controle.getVendas());
		session.setAttribute("listaControle", listaControle);
		return "consArquivos";
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaConsultor = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
			if (listaConsultor == null) {
				listaConsultor = new ArrayList<Usuario>();
			}
		}
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public String atualizarInformacoes(Controlecurso controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleCursos", options, null);
		}
		return "";
	}

	public String idadeCliente(Controlecurso controle) {
		String retorno = "";
		if(controle.getVendas().getCliente().getDataNascimento()!=null){
			int idade = Formatacao.calcularIdade(controle.getVendas().getCliente().getDataNascimento());
			retorno = idade+" anos";
			return retorno;
		}
		return retorno;
	}
	
	
	public String situacaoControle(Controlecurso controle){
		if(controle.getSituacao().equalsIgnoreCase("Processo")){
			return "../../resources/img/bolaAzul.png";
		}else if(controle.getSituacao().equalsIgnoreCase("Embarcado")){
			return "../../resources/img/bolaVerde.png";
		}else if(controle.getSituacao().equalsIgnoreCase("Cancelado")){
			return "../../resources/img/bolaVermelha.png";
		}else if(controle.getSituacao().equalsIgnoreCase("Standby")){
			return "../../resources/img/bolaAmarela.png";
		}else{
			return null;
		}
	}
	
	
	public void editarDocs(int linha){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", listaControle.get((linha)).getVendas());
		session.setAttribute("linhacontrole", linha);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("docsControle", options, null);
	}
	
	public void retornoDialogDocs(SelectEvent event) {
		String docs;
		docs = (String) event.getObject();
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Vendas vendas = (Vendas) session.getAttribute("vendas");
        int linha = (int) session.getAttribute("linhacontrole");
        session.removeAttribute("vendas");
        session.removeAttribute("linhacontrole");
        CursoFacade cursoFacade= new CursoFacade();
        controlecurso = listaControle.get((linha));
		controlecurso.setDocs(docs);
		controlecurso = cursoFacade.salvar(controlecurso);
		listaControle.get((linha)).setDocs(docs);
	}
	 
	public void salvarSituacao(Controlecurso controlecurso){
		CursoFacade cursoFacade = new CursoFacade();
		controlecurso = cursoFacade.salvar(controlecurso);
	}
	
	
	
	public String docsControle(Controlecurso controle){
		if(controle.getDocs().equalsIgnoreCase("VD")){
			return "../../resources/img/obsFicha.png";
		}else if(controle.getDocs().equalsIgnoreCase("VM")){
			return "../../resources/img/obsVermelha.png";
		}else{
			return null;
		}
	}
	
	
	public String application(Controlecurso controlecurso) {  
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("controle", controlecurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("dadosApplication", options, null); 
		return "";
	}
	   
	public void retornoDialogApplication(SelectEvent event) {
		Controlecurso controlecurso;
		controlecurso = (Controlecurso) event.getObject();
		if(controlecurso!=null){ 
	        CursoFacade cursoFacade= new CursoFacade(); 
			controlecurso = cursoFacade.salvar(controlecurso);
			controlecurso.getCurso().setApplication(true); 
			cursoFacade.salvar(controlecurso.getCurso());
			listarControle(null);
		}
	}
	
	public String retornarIconeApplication(Controlecurso controlecurso) {
		if (controlecurso.getCurso() != null) {
			if (controlecurso.getCurso().isApplication()) {
				return "../../resources/img/iconeSApp.png";
			} else {
				return "../../resources/img/iconeCheck.png";  
			}	
		}
		return "";
	}
	
	
	public String informacoes(Curso curso) {
		if (curso.getVendas().getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", curso.getVendas());
			voltar = "controleCurso";
			session.setAttribute("voltar", voltar);
			session.setAttribute("listaControle", listaControle);
			return "consLogVenda";
		}
	}

	public String imagemSituacao(Curso curso) {
		if (curso != null) {
			if (curso.getVendas().getSituacao().equals("FINALIZADA")) {
				return "../../resources/img/finalizadoFicha.png";
			} else if (curso.getVendas().getSituacao().equals("ANDAMENTO")
					&& !curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
				return "../../resources/img/ficharestricao.png";
			} else if (curso.getVendas().getSituacao().equals("ANDAMENTO")
					&& curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
				return "../../resources/img/amarelaFicha.png";
			} else {
				return "../../resources/img/fichaCancelada.png";
			}
		} else {
			return "../../resources/img/finalizadoFicha.png";
		}
	}
	
	public String gerarRelatorioFicha(Curso curso) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
			if (curso.isDadospais()) {
				caminhoRelatorio = "/reports/curso/FichaCursoDadosPaisPagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/curso/FichaCursoPagina01.jasper";
			}
		} else {
			if (curso.isDadospais()) {
				caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
			}
		}
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
		parameters.put("idvendas", curso.getVendas().getIdvendas());
		parameters.put("sqlpagina2", gerarSqlSeguroViagems(curso));
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"fichaCurso-" + curso.getIdcursos() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	public String gerarSqlSeguroViagems(Curso curso) {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(curso.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(curso.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct vendas.dataVenda, vendas.valor as valorVenda, cursos.nomeCurso, cursos.escola,"
				+ "cursos.cidade, cursos.pais, cursos.aulassemana, cursos.numerosenamas, cursos.dataInicio, "
				+ "cursos.dataTermino, cursos.tipoAcomodacao, cursos.numeroSemanasAcamodacao, cursos.tipoquarto,"
				+ "cursos.refeicoes, cursos.adicionais, cursos.datachegada, cursos.dataSaida, cursos.familiacomcrianca,"
				+ "cursos.familiacomanimais, cursos.fumante, cursos.vegetariano, cursos.hobbies, cursos.possuiAlergia,"
				+ "cursos.quaisalergias, cursos.solicitacoesespeciais, cursos.caratovtm, cursos.numerocartaovtm,"
				+ "cursos.moedacartaovtm, cursos.transferin, cursos.transferouto, cursos.passagemaerea, cursos.observacaopassagemaerea,"
				+ "cursos.vistoconsular, cursos.dataEntregadocumentosvistos, cursos.observacaovisto, cursos.nomecontatoemergencia,"
				+ "cursos.fonecontatoemergencia, cursos.emalcontatoemergencia, cursos.relacaocontatoemergencia, cursos.datainscricao as dataInscricaCurso, cursos.idioma, cursos.nivelIdioma, cursos.possuiSeguroGovernamental, cursos.numeroMeses as numeromesesgovernamental, cursos.seguradoraGovernamental,"
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "usuario.nome as nomeUsuario,unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento,seguroviagem.idseguroViagem,seguroviagem.seguradora,seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor,orcamentoprodutosorcamento.idorcamentoprodutosorcamento"
				+ " from " + "vendas join cursos on vendas.idvendas = cursos.vendas_idvendas "
				+ "join usuario on vendas.usuario_idusuario = usuario.idusuario "
				+ "join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio "
				+ "join orcamento on vendas.idvendas = orcamento.vendas_idvendas "
				+ "join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento "
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento ";
		sql = sql + sqlseguro;
		sql = sql + " where " + " vendas.idvendas = " + curso.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento ";
		return sql;

	}
	
	public void receberControle(Controlecurso controlecurso){
		this.controlecurso=controlecurso;
	}
	
	public void salvarPrioridade(){
		CursoFacade cursoFacade = new CursoFacade();
		controlecurso = cursoFacade.salvar(controlecurso);
		Mensagem.lancarMensagemInfo("Prioridade salva!", "");
	}
	
	public String retornarCorPrioridade(Controlecurso controlecurso){
		if(controlecurso.getPrioridade().equalsIgnoreCase("Urgente")){
			return "color:#B22222;";
		}else if(controlecurso.getPrioridade().equalsIgnoreCase("Alta")){
			return "color:#7D26CD;";
		}else if(controlecurso.getPrioridade().equalsIgnoreCase("Média")){
			return "color:#008B00;";
		}else{
			return "";
		}
	}
	
	
	public String obsTM(Controlecurso controlecurso) {
		obsTM = controlecurso.getCurso().getVendas().getObstm();
		return obsTM;
	}
	
	
	public String retornarIconeObsTM(Controlecurso controlecurso){
		if (controlecurso.getCurso() != null) {
			if (controlecurso.getCurso().getVendas().getObstm() != null
					&& controlecurso.getCurso().getVendas().getObstm().length() > 0) {
				return "../../resources/img/obsVermelha.png";
			}
		}
		return "../../resources/img/obsFicha.png";
	}
	
	
	public String nomeSituacao(Curso curso) {
		if (curso != null) {
			if (curso.getVendas().getSituacao().equals("FINALIZADA")) {
				return "FICHA FINALIZADA";
			} else if (curso.getVendas().getSituacao().equals("ANDAMENTO")
					&& !curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
				return "FINANCEIRO (FICHA EM ANÁLISE NO DEPARTAMENTO FINANCEIRO)";
			} else if (curso.getVendas().getSituacao().equals("ANDAMENTO")
					&& curso.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
				return "ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)";
			} else {
				return "FICHA CANCELADA";
			}
		} else {
			return "FICHA FINALIZADA";
		}
	}
	
	
	public String fichaCurso(Curso curso){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("curso", curso);
		session.setAttribute("listaVendasCursoAndamento", listaVendasCursoAndamento);
		session.setAttribute("listaVendasCursoCancelada", listaVendasCursoCancelada);
		session.setAttribute("listaVendasCursoFinalizada", listaVendasCursoFinalizada);
		session.setAttribute("listaVendasCursoFinanceiro", listaVendasCursoFinanceiro);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "ControleCurso");
		session.setAttribute("chamadaTela", "ControleCurso");
		return "fichaCurso";
	}
	
	
}
