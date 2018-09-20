package br.com.travelmate.managerBean.mtp;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.MtpFacade;
import br.com.travelmate.facade.TreinamentoAcessoFacade;
import br.com.travelmate.facade.TreinamentoPerguntasFacade;
import br.com.travelmate.facade.TreinamentoRespostaUsuarioFacade;
import br.com.travelmate.facade.TreinamentoRespostasFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Mtp;
import br.com.travelmate.model.Treinamentoacesso;
import br.com.travelmate.model.Treinamentoperguntas;
import br.com.travelmate.model.Treinamentorespostas;
import br.com.travelmate.model.Treinamentorespostausuario;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class MtpMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mtp mtp;
	private List<Mtp> listaMtp;
	private Date dataInicial;
	private Date dataFinal;
	private List<Usuario> listaUsuario;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean criarQuestionario;
	private Ftpdados ftpdados;
	private String urlArquivo;

	@PostConstruct
	public void init() {
		listarMTp();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			criarQuestionario = true;
		}
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		ftpdados = new Ftpdados();
		if (ftpdados != null) {
			urlArquivo = ftpdados.getProtocolo() + "://" + ftpdados.getHost() + ":" + ftpdados.getWww();
		}
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Mtp getMtp() {
		return mtp;
	}

	public void setMtp(Mtp mtp) {
		this.mtp = mtp;
	}

	public List<Mtp> getListaMtp() {
		return listaMtp;
	}

	public void setListaMtp(List<Mtp> listaMtp) {
		this.listaMtp = listaMtp;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isCriarQuestionario() {
		return criarQuestionario;
	}

	public void setCriarQuestionario(boolean criarQuestionario) {
		this.criarQuestionario = criarQuestionario;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public void listarMTp() {
		MtpFacade mtpFacade = new MtpFacade();
		String data = Formatacao.SubtarirDatas(new Date(), 3, "yyyy-MM-dd");
		listaMtp = mtpFacade.lista("Select m From Mtp m Where m.data>='" + data + "' order by m.data");
		if (listaMtp == null) {
			listaMtp = new ArrayList<Mtp>();
		}
	}

	public void pesquisar() {
		MtpFacade mtpFacade = new MtpFacade();
		String sql = "Select m From Mtp m ";
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " Where m.data>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and m.data<='"
					+ Formatacao.ConvercaoDataSql(dataFinal) + "' order by m.data";
		}
		listaMtp = mtpFacade.lista(sql);
	}

	public void limparPesquisa() {
		dataFinal = null;
		dataInicial = null;
		listarMTp();
	}

	public void cadastrarNovoMtp() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadMtp", options, null);
	}

	public void editarMtp(Mtp mtp) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("mtp", mtp);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadMtp", options, null);
	}

	public void retornoDialogNovo(SelectEvent event) {
		Mtp mtp = (Mtp) event.getObject();
		if (mtp != null && mtp.getIdmtp() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			if (listaMtp == null) {
				listarMTp();
			} else {
				listaMtp.add(mtp);
			}
			gerarAvisos(mtp);
		}
	}

	@SuppressWarnings("unchecked")
	public void retornoDialogEdicao(SelectEvent event) {
		List<AlteracoesMtpBean> listaAlteracao = (List<AlteracoesMtpBean>) event.getObject();
		if (listaAlteracao != null) {
			if (listaAlteracao.size() > 0) {
				Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
				listarMTp();
				gerarAvisosEdicao(listaAlteracao);
			}
		}
	}

	public void filtrarMtpHoje() {
		String sql = "Select m From Mtp m Where m.data='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
		MtpFacade mtpFacade = new MtpFacade();
		listaMtp = mtpFacade.lista(sql);
		if (listaMtp == null) {
			listaMtp = new ArrayList<Mtp>();
		}
	}

	public void filtrarMtpSemanal() {
		Date data = null;
		try {
			data = Formatacao.SomarDiasDatas(new Date(), 7);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "Select m From Mtp m Where m.data='" + Formatacao.ConvercaoDataSql(data) + "'";
		MtpFacade mtpFacade = new MtpFacade();
		listaMtp = mtpFacade.lista(sql);
		if (listaMtp == null) {
			listaMtp = new ArrayList<Mtp>();
		}
	}

	public void filtrarMtpMensal() {
		String mesSql = "";
		int nMes;
		int dia = Formatacao.getDiaData(new Date());
		int ano = Formatacao.getAnoData(new Date());
		Calendar cal = new GregorianCalendar();
		nMes = (cal.get(Calendar.MONDAY) + 2);
		if (nMes < 10) {
			mesSql = "0" + nMes;
		} else {
			mesSql = "" + nMes;
		}
		String sql = "Select m From Mtp m Where m.data>='" + Formatacao.ConvercaoDataSql(new Date()) + "' and"
				+ " m.data <='" + ano + "-" + mesSql + "-" + dia + "'";
		MtpFacade mtpFacade = new MtpFacade();
		listaMtp = mtpFacade.lista(sql);
		if (listaMtp == null) {
			listaMtp = new ArrayList<Mtp>();
		}
	}

	public String retornarHost(Mtp mtp) {
		return mtp.getHost();
	}

	public void gerarAvisos(Mtp mtp) {
		listarUsuario();
		AvisosFacade avisosFacade = new AvisosFacade();
		Avisousuario avisousuario;
		Avisos avisos = new Avisos();
		avisos.setData(new Date());
		avisos.setDepartamento(mtp.getDepartamento().getNome());
		avisos.setIdunidade(0);
		avisos.setLiberar(false);
		avisos.setUsuario(usuarioLogadoMB.getUsuario());
		avisos.setImagem("aviso");
		avisos.setTexto("Teremos um novo treinamento do departamento " + mtp.getDepartamento().getNome()
				+ ", Verifique suas consultas de MTP, " + " data e hora.");
		avisos = avisosFacade.salvar(avisos);
		for (int i = 0; i < listaUsuario.size(); i++) {
			avisousuario = new Avisousuario();
			avisousuario.setAvisos(avisos);
			avisousuario.setUsuario(listaUsuario.get(i));
			avisousuario.setVisto(true);
			avisousuario = avisosFacade.salvar(avisousuario);
		}
	}

	public void gerarAvisosEdicao(List<AlteracoesMtpBean> listaAlteracao) {
		String alteracoesMtp = "";
		String nomeDepartamento = "";
		if (listaAlteracao.size() > 0) {
			nomeDepartamento = listaAlteracao.get(0).getDepartamento();
		}
		for (int i = 0; i < listaAlteracao.size(); i++) {
			alteracoesMtp = alteracoesMtp + listaAlteracao.get(i).getAlteracao();
		}
		listarUsuario();
		AvisosFacade avisosFacade = new AvisosFacade();
		Avisousuario avisousuario;
		Avisos avisos = new Avisos();
		avisos.setData(new Date());
		avisos.setDepartamento(nomeDepartamento);
		avisos.setIdunidade(0);
		avisos.setLiberar(true);
		avisos.setUsuario(usuarioLogadoMB.getUsuario());
		avisos.setImagem("aviso");
		avisos.setTexto("O treinamento do departamento " + nomeDepartamento + " teve algumas alterações como: "
				+ alteracoesMtp);
		avisos = avisosFacade.salvar(avisos);
		for (int i = 0; i < listaUsuario.size(); i++) {
			avisousuario = new Avisousuario();
			avisousuario.setAvisos(avisos);
			avisousuario.setUsuario(listaUsuario.get(i));
			avisousuario.setVisto(false);
			avisousuario = avisosFacade.salvar(avisousuario);
		}
	}

	public void listarUsuario() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.listar("Select u From Usuario u Where u.situacao='Ativo'");
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void acessosMtp(Mtp mtp) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("mtp", mtp);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("listaTreinamentoAcesso", options, null);
	}

	public String cadQuestionario(Mtp mtp) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("mtp", mtp);
		return "cadQuestionario";
	}

	public String responderQuestionario(Mtp mtp) {
		TreinamentoPerguntasFacade treinamentoPerguntasFacade = new TreinamentoPerguntasFacade();
		Treinamentoperguntas treinamentoperguntas = treinamentoPerguntasFacade
				.consultar("Select t From Treinamentoperguntas t" + " where t.mtp.idmtp=" + mtp.getIdmtp());
		if (treinamentoperguntas == null || treinamentoperguntas.getIdtreinamentoperguntas() == null) {
			Mensagem.lancarMensagemErro("Atenção!", "Treinamento não possui questionário aberto.");
		} else {
			TreinamentoRespostasFacade treinamentoRespostasFacade = new TreinamentoRespostasFacade();
			Treinamentorespostas treinamentorespostas = treinamentoRespostasFacade.consultar(
					"select t From Treinamentorespostas t" + " where t.treinamentoperguntas.idtreinamentoperguntas="
							+ treinamentoperguntas.getIdtreinamentoperguntas());
			if (treinamentorespostas == null) {
				Mensagem.lancarMensagemErro("Atenção!", "Treinamento não possui questionário aberto.");
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("treinamentorespostas", treinamentorespostas);
				session.setAttribute("usuario", usuarioLogadoMB.getUsuario());
				session.setAttribute("voltar", false);
				TreinamentoRespostaUsuarioFacade treinamentoRespostaUsuarioFacade = new TreinamentoRespostaUsuarioFacade();
				Treinamentorespostausuario treinamentorespostausuario = treinamentoRespostaUsuarioFacade
						.consultar("select t From Treinamentorespostausuario t"
								+ " where t.treinamentorespostas.idtreinamentorespostas="
								+ treinamentorespostas.getIdtreinamentorespostas() + " and t.usuario.idusuario="
								+ usuarioLogadoMB.getUsuario().getIdusuario());
				if (treinamentorespostausuario == null) {
					treinamentorespostausuario = new Treinamentorespostausuario();
				} else {
					return "resultadoQuestionario";
				}
			}
			return "responderQuestionario";
		}
		return "";
	}

	public String salvarAcesso(Mtp mtp) {
		TreinamentoAcessoFacade treinamentoAcessoFacade = new TreinamentoAcessoFacade();
		Treinamentoacesso acesso = treinamentoAcessoFacade
				.consultar("select t From Treinamentoacesso t where t.mtp.idmtp=" + mtp.getIdmtp()
						+ " and t.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario());
		if (acesso == null) {
			acesso = new Treinamentoacesso();
			acesso.setMtp(mtp);
			acesso.setUsuario(usuarioLogadoMB.getUsuario());
			acesso.setData(new Date());
			acesso.setHora(Formatacao.foramtarHoraString());
			acesso = treinamentoAcessoFacade.salvar(acesso);
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(mtp.getHost());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String mensagemAcesosSala() {
		Mensagem.lancarMensagemInfo("Ferramenta de treinamento disponibilizada pelo parceiro", "");
		return "";
	}

	public String visualizarUsuariosResponderam(Mtp mtp) {
		TreinamentoPerguntasFacade treinamentoPerguntasFacade = new TreinamentoPerguntasFacade();
		Treinamentoperguntas treinamentoperguntas = treinamentoPerguntasFacade
				.consultar("Select t From Treinamentoperguntas t" + " where t.mtp.idmtp=" + mtp.getIdmtp());
		if (treinamentoperguntas == null || treinamentoperguntas.getIdtreinamentoperguntas() == null) {
			Mensagem.lancarMensagemErro("Atenção!", "Treinamento não possui questionário aberto.");
		} else {
			TreinamentoRespostasFacade treinamentoRespostasFacade = new TreinamentoRespostasFacade();
			Treinamentorespostas treinamentorespostas = treinamentoRespostasFacade.consultar(
					"select t From Treinamentorespostas t" + " where t.treinamentoperguntas.idtreinamentoperguntas="
							+ treinamentoperguntas.getIdtreinamentoperguntas());
			if (treinamentorespostas == null) {
				Mensagem.lancarMensagemErro("Atenção!", "Treinamento não possui questionário aberto.");
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("treinamentorespostas", treinamentorespostas);
				return "listaTreinamentoUsuario";
			}
		}
		return "";
	}

	public boolean acessoSala(Mtp mtp) {
		if (mtp.getTipo().equalsIgnoreCase("Gerais")) {
			return false;
		} else {
			return true;
		}
	}

	public boolean acessoSalaParceiro(Mtp mtp) {
		if (mtp.getTipo().equalsIgnoreCase("Gerais")) {
			return true;
		} else {
			return false;
		}
	}

	public String corMtpRealizado(Mtp mtp) {
		if (mtp != null) {
			Date hoje = new Date();
			Date ontem = new Date();
			try {
				ontem = Formatacao.SomarDiasDatas(hoje, -1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (mtp.getData() != null && mtp.getData().before(hoje) && mtp.getData().after(ontem)) {
				return "color:#000000;";
			} else if (mtp.getData() != null && mtp.getData().before(hoje)) {
				return "color:#9C9C9C;";
			} else
				return "color:#000000;";
		}
		return "color:#000000;";
	}

	public boolean mtpRealizado(Mtp mtp) {
		if (mtp != null) {
			Date hoje = new Date();
			Date ontem = new Date();
			try {
				ontem = Formatacao.SomarDiasDatas(hoje, -1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (mtp.getData() != null && mtp.getData().before(hoje) && mtp.getData().after(ontem)) {
				return false;
			} else if (mtp.getData() != null && mtp.getData().before(hoje)) {
				return true;
			} else
				return false;
		}
		return false;
	}

}
