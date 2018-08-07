package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.NotificacaoFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.UsuarioDepartamentoUnidadeFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Notificacao;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuariodepartamentounidade;
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
	@Inject
	private VendasDao vendasDao;
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
	private boolean acessoGerencial = true;
	private boolean acessoUnidade = false;
	
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaQuestionario = (List<Questionariohe>) session.getAttribute("listaQuestionario");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		session.removeAttribute("listaQuestionario");
		listaUnidade = GerarListas.listarUnidade();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			habilitarUnidade = true;
			acessoUnidade = true;
			acessoGerencial = false;
		}
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("QuestionarioHe")) {
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



	public boolean isAcessoGerencial() {
		return acessoGerencial;
	}



	public void setAcessoGerencial(boolean acessoGerencial) {
		this.acessoGerencial = acessoGerencial;
	}



	public boolean isAcessoUnidade() {
		return acessoUnidade;
	}



	public void setAcessoUnidade(boolean acessoUnidade) {
		this.acessoUnidade = acessoUnidade;
	}



	public void gerarListaQuestionario() {
		
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 90, "yyyy-MM-dd");
		listaQuestionario = null;
		String sqlQuestionario = "Select q From Questionariohe q where q.dataenvio>='" + dataConsulta + "'";
				
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sqlQuestionario = sqlQuestionario + " and q.usuario.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}else {
			sqlQuestionario = sqlQuestionario + " and q.situacao<>'PROCESSO' ";
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
		
		session.setAttribute("vendas", vendasDao.consultarVendas(3723));
		session.setAttribute("cliente", questionariohe.getCliente());
		session.setAttribute("pesquisar", "Sim");
		session.setAttribute("nomePrograma", "QuestionarioHe");
		session.setAttribute("chamadaTela", "QuestionarioHe");
		session.setAttribute("listaQuestionario", listaQuestionario);
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
		pesquisar = "Nao";
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
		pesquisar = "Sim";
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
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					String msg = validarDados(questionariohe);
					if (msg == null || msg.length() == 0) {
						questionariohe.setSituacao(situacaoLista);
						QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
						questionarioHeFacade.salvar(questionariohe);
						if (situacaoLista.equalsIgnoreCase("Em Análise") && (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 7 || 
								usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1)) {
							notificarDepartamento(questionariohe);
						}
					}else {
						 Mensagem.lancarMensagemInfo("Preencha informações obrigatórias", msg);
					}
				}else {
					questionariohe.setSituacao(situacaoLista);
					QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
					questionarioHeFacade.salvar(questionariohe);
					if (situacaoLista.equalsIgnoreCase("Processo")) {
						listaQuestionario.remove(questionariohe);
					}
				}
			}
			situacaoLista = "";
		}
	}
	
	public void notificarDepartamento(Questionariohe questionariohe) {
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> listaResponsaveis = usuarioDepartamentoUnidadeFacade.listar("SELECT u FROM Usuariodepartamentounidade u WHERE "
				+ " u.departamento.iddepartamento=7 and u.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		for (int i = 0; i < listaResponsaveis.size(); i++) {
			Notificacao notificacao = new Notificacao();
			NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
			notificacao.setTitulo("Novo Questionario para análise - " + questionariohe.getIdquestionariohe());
			notificacao.setUnidade(usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomeFantasia());
			notificacao.setCliente(questionariohe.getCliente().getNome());
			notificacao.setFornecedor("");
			notificacao.setDatainicio(null);
			notificacao.setConsultor(usuarioLogadoMB.getUsuario().getNome());
			notificacao.setTipovenda("");
			notificacao.setValorvenda(0.0f);
			notificacao.setCambio(0.0f);
			notificacao.setMoeda("");
			notificacao.setLimpar(false);
			notificacao.setData(new Date());
			notificacao.setImagem("inserido");
			notificacao.setUsuario(listaResponsaveis.get(i).getUsuario());
			String hora = Formatacao.foramtarHoraString();
			notificacao.setHora(hora);
			notificacaoFacade.salvar(notificacao);
		}
	}
	
	public String validarDados(Questionariohe questionarioHe) {
		String msg = "";
		if (questionarioHe.getDiplomas() == null || questionarioHe.getDiplomas().length() <= 0) {
			msg = msg + "Informe o nome do curso; \n \n";
		}
		
		if (questionarioHe.getNivelcetificado() == null || questionarioHe.getNivelcetificado().length() <= 0) {
			msg = msg + "Informe o Nivel mais alto de escolaridade no Brasil; \n \n";
		}
		
		if (questionarioHe.getOntuacaotoefl() == null || questionarioHe.getOntuacaotoefl().length() <= 0) {
			msg = msg + "Informe a Pontuação no teste de proficiência ou teste online; \n \n";
		}
		
		if (questionarioHe.getOcupacao1() == null || questionarioHe.getOcupacao1().length() <= 0) {
			msg = msg + "Informe Descreva suas duas última principais ocupações profissionais; \n \n";
		}
		
		
		if (questionarioHe.getPrograma() == null || questionarioHe.getPrograma().length() <= 0) {
			msg = msg + "Informe Programa / Área de interesse; \n \n";
		}
		
		if (questionarioHe.getNivelcertificadointeresse() == null || questionarioHe.getNivelcertificadointeresse().length() <= 0) {
			msg = msg + "Informe Nível de Certificação de interesse; \n \n";
		}
		
		
		if (questionarioHe.getPais1() == null || questionarioHe.getPais1().length() <=0) {
			msg = msg + "Informe o Destino em que prefere estudar; \n \n";
		}
		
		
		if (questionarioHe.getDataprograma() == null) {
			msg = msg + "Informe Data aproximada do Programa; \n \n";
		}
		
		if (questionarioHe.getPrecisatrabalahar() == null || questionarioHe.getPrecisatrabalahar().length() <= 0) {
			msg = msg + "Informe Preciso trabalhar durante meu curso; \n \n";
		}
		
		
		if (questionarioHe.getInteresseemimigrar() == null || questionarioHe.getInteresseemimigrar().length() <= 0) {
			msg = msg + "Informe Tenho interesse em imigrar; \n \n";
		}
		
		
		if (questionarioHe.getObservacao() == null || questionarioHe.getObservacao().length() <= 0) {
			msg = msg + "Informe a Observações e parecer do consultor; \n \n";
		}
		
		if (questionarioHe.getPrecisatrabalahar() == null || questionarioHe.getPrecisatrabalahar().length() <= 0) {
			msg = msg + "Informe 'Preciso trabalhar durante meu curso?'; \n \n";
		}
		
		if (questionarioHe.getInteresseemimigrar() == null || questionarioHe.getInteresseemimigrar().length() <= 0) {
			msg = msg + "Informe 'Tenho interesse m Imigrar?'; \n \n";
		}
		
		if (questionarioHe.getVistotrabalho() == null || questionarioHe.getVistotrabalho().length() <= 0) {
			msg = msg + "Informe 'Tenho interesse em visto de trabalho após o curso?'; \n \n";
		}
		return msg;
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
	
	
	public boolean verificarAcessoUnidade(Questionariohe questionariohe) {
		boolean resultado = false;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			resultado = true;
			if (questionariohe.getSituacao().equalsIgnoreCase("PROCESSO")) {
				resultado = true;
			}else {
				resultado = false;
			}
		}
		return resultado;
	}
	
	
	
	public boolean verificarAcessoGerencial(Questionariohe questionariohe) {
		boolean resultado = true;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			resultado = false;
		}
		return resultado;
	}
	
	
	

}
