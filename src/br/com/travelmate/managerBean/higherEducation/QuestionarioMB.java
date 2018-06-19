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
import br.com.travelmate.managerBean.UsuarioLogadoMB;
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
	
	
	
	@PostConstruct
	public void init() {
		listaUnidade = GerarListas.listarUnidade();
		gerarListaQuestionario();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarUnidade = true;
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
	
	
	public boolean desabilitarBtnAutorizar(Questionariohe questionariohe) {
		if (questionariohe.isAutorizado()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public String documentacao(Questionariohe questionariohe) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
	//	session.setAttribute("vendas", listaHeBean.getQuestionariohe().getVendas());
//		if(listaHeBean.getHe()!=null && listaHeBean.getHe().getVendas1()!=null){
//			session.setAttribute("vendas1", listaHeBean.getHe().getVendas1());
//		}
		session.setAttribute("pesquisar", "Sim");
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("chamadaTela", "fichaHE");
		String voltar = "consquestionarioHe";
		session.setAttribute("voltar", voltar);
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
		String sql = "SELECT q From Questionariohe q WHERE q.cliente.nome like '%" + nomeCliente + "%' ";
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

}
