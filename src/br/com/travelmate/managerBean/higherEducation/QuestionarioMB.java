package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.bean.ListaHeBean;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class QuestionarioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Questionariohe> listaQuestionario;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidadenegocio;
	private int idvenda;
	private String nomeCliente = "";
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private boolean habilitarUnidade = false;
	private String situacaoLista = "";
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";
	private String sql;
	private int nFichas = 0;
	
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaQuestionario = (List<Questionariohe>) session.getAttribute("listaQuestionario");
		session.removeAttribute("listaQuestionario");
		listaUnidade = GerarListas.listarUnidade();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarUnidade = true;
		}
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Curso")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		sql = (String) session.getAttribute("sql");
		session.removeAttribute("sql");
		if (sql!=null) {
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaQuestionario = questionarioHeFacade.listar(sql);
			if (listaQuestionario == null) {
				listaQuestionario = new ArrayList<Questionariohe>();
			}
			nFichas = listaQuestionario.size();
		}else {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				gerarListaQuestionario();
			}
		}
	}



	public List<Questionariohe> getListaQuestionario() {
		return listaQuestionario;
	}



	public void setListaQuestionario(List<Questionariohe> listaQuestionario) {
		this.listaQuestionario = listaQuestionario;
	}



	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}



	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}



	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}



	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}
	
	
	
	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public int getIdvenda() {
		return idvenda;
	}



	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
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



	public String getSituacao() {
		return situacao;
	}



	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}



	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public String getSituacaoLista() {
		return situacaoLista;
	}



	public void setSituacaoLista(String situacaoLista) {
		this.situacaoLista = situacaoLista;
	}



	public String getPesquisar() {
		return pesquisar;
	}



	public void setPesquisar(String pesquisar) {
		this.pesquisar = pesquisar;
	}



	public String getNomePrograma() {
		return nomePrograma;
	}



	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}



	public String getChamadaTela() {
		return chamadaTela;
	}



	public void setChamadaTela(String chamadaTela) {
		this.chamadaTela = chamadaTela;
	}



	public String getSql() {
		return sql;
	}



	public void setSql(String sql) {
		this.sql = sql;
	}



	public int getnFichas() {
		return nFichas;
	}



	public void setnFichas(int nFichas) {
		this.nFichas = nFichas;
	}



	public void gerarListaQuestionario() {
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date());
		Calendar c = new GregorianCalendar(ano, mes, 1);
		Date data = c.getTime();
		String dataConsulta = Formatacao.ConvercaoDataSql(data);
		listaQuestionario = null;
		String sqlQuestionario = "Select q From Questionariohe q where q.dataenvio>='" + dataConsulta + "'";
				
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sqlQuestionario = sqlQuestionario + " and q.usuario.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sqlQuestionario = sqlQuestionario + " order by q.dataenvio desc";
		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		listaQuestionario = questionarioHeFacade.listar(sqlQuestionario);
		if (listaQuestionario == null) {
			listaQuestionario = new ArrayList<Questionariohe>();
		}
		nFichas = listaQuestionario.size();
	}
	
	
	public String retornarimagem(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "../../resources/img/finalizadoFicha.png";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "../../resources/img/finalizadoFicha.png";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		} else {
			return "../../resources/img/processoFicha.png";   
		}
	}
	
	
	public String retornarTituloFicha(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "FICHA FINALIZADA";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "FICHA FINALIZADA";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "FICHA CANCELADA";
		} else {
			return "PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)";   
		}
	}
	
	public boolean desabilitarBtnFichaInscricao(Questionariohe questionariohe) {
		if (questionariohe != null) {
			return false;
		} else
			return false;
	}
	
	
	public String contrato(Questionariohe questionariohe){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("questionariohe", questionariohe);
		return "contratoHE";
	}
	
	
	
	public String documentacao(Questionariohe questionariohe) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		VendasFacade vendasFacade = new VendasFacade();
		session.setAttribute("vendas", vendasFacade.consultarVendas(3723));
		session.setAttribute("cliente", questionariohe.getCliente());
		session.setAttribute("pesquisar", "Sim");
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("chamadaTela", "fichaHE");
		String voltar = "consquestionarioHe";
		session.setAttribute("voltar", voltar);
		session.setAttribute("sql", sql);
		return "consArquivos"; 
	}
	
	
	public void limpar() {
		dataInicio = null;
		dataTermino = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			unidadenegocio = null;
		} else {
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}
		nomeCliente = "";
		idvenda = 0;
		situacao = "sn";
		gerarListaQuestionario();
	}

	public void pesquisar() {
		listaQuestionario = null;
		sql = "SELECT q From Questionariohe q WHERE q.cliente.nome like '%" + nomeCliente + "%' ";
		if (!situacao.equalsIgnoreCase("sn")) {
			sql = sql + " and q.situacao='" + situacao + "' ";
		}
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sql = sql + " and q.usuario.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and q.usuario.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		if (dataInicio != null && dataTermino != null) {
			sql = sql + " and q.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and q.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(dataTermino) + "'";
		}
		if (idvenda > 0) {
			sql = sql + " and q.idquestionariohe=" + idvenda;
		}

		sql = sql + " order by q.dataenvio desc";
		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		listaQuestionario = questionarioHeFacade.listar(sql);
		if (listaQuestionario == null) {
			listaQuestionario = new ArrayList<Questionariohe>();
		}
		nFichas = listaQuestionario.size();
	}
	
	
	public String editar(Questionariohe questionariohe) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("questionariohe", questionariohe);
		return "questionarioHe";
	}
	
	
	public void atualizarLista(Questionariohe questionariohe) {
		if (questionariohe != null) {
			if ((situacaoLista != null) && (!questionariohe.getSituacao().equalsIgnoreCase(situacaoLista))) {
				questionariohe.setSituacao(situacaoLista);
				QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
				questionarioHeFacade.salvar(questionariohe);
			}
		}
	}
	
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	public String fichaQuestionario(Questionariohe questionariohe){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("questionariohe", questionariohe);
		session.setAttribute("listaQuestionario", listaQuestionario);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "QuestionarioHe");
		session.setAttribute("chamadaTela", "QuestionarioHe");
		return "fichaQuestionarioHe";
	}
	
	public void cancelar(Questionariohe questionariohe) {
		questionariohe.setSituacao("Cancelado");
		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		questionarioHeFacade.salvar(questionariohe);
		Mensagem.lancarMensagemInfo("Cancelado com sucesso", "");
	}

}
