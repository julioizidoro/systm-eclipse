package br.com.travelmate.managerBean.mtp;
 
import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.dao.TreinamentoRespostasCertaDao;
import br.com.travelmate.facade.TreinamentoRespostaUsuarioFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Treinamentorespostacerta;
import br.com.travelmate.model.Treinamentorespostas;
import br.com.travelmate.model.Treinamentorespostausuario;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ResultadoQuestionarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB; 
	private Treinamentorespostas treinamentorespostas; 
	private Treinamentorespostacerta treinamentorespostacerta; 
	private Treinamentorespostausuario treinamentorespostausuario;
	private int pontuacao;
	private Usuario usuario;
	private boolean voltar;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		treinamentorespostas = (Treinamentorespostas) session.getAttribute("treinamentorespostas");
		usuario = (Usuario) session.getAttribute("usuario");
		voltar = (boolean) session.getAttribute("voltar");
		session.removeAttribute("voltar");
		session.removeAttribute("usuario");
		session.removeAttribute("treinamentorespostas");
		iniciarResultado();
	}

	 
	public Treinamentorespostas getTreinamentorespostas() {
		return treinamentorespostas;
	}

	public void setTreinamentorespostas(Treinamentorespostas treinamentorespostas) {
		this.treinamentorespostas = treinamentorespostas;
	}

	 
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Treinamentorespostausuario getTreinamentorespostausuario() {
		return treinamentorespostausuario;
	}

	public void setTreinamentorespostausuario(Treinamentorespostausuario treinamentorespostausuario) {
		this.treinamentorespostausuario = treinamentorespostausuario;
	}

	public Treinamentorespostacerta getTreinamentorespostacerta() {
		return treinamentorespostacerta;
	}


	public void setTreinamentorespostacerta(Treinamentorespostacerta treinamentorespostacerta) {
		this.treinamentorespostacerta = treinamentorespostacerta;
	} 

	public Usuario getUsuario() {
		return usuario;
	} 

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public boolean isVoltar() {
		return voltar;
	}


	public void setVoltar(boolean voltar) {
		this.voltar = voltar;
	}


	public int getPontuacao() {
		return pontuacao;
	}


	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}


	public String fechar() {
		return "consMtp";
	}
	
	public String voltarLista() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("treinamentorespostas", treinamentorespostas);
		return "listaTreinamentoUsuario";
	}

	public void iniciarResultado() { 
		TreinamentoRespostaUsuarioFacade treinamentoRespostaUsuarioFacade = new TreinamentoRespostaUsuarioFacade();
		treinamentorespostausuario = treinamentoRespostaUsuarioFacade
				.consultar("select t From Treinamentorespostausuario t"
						+ " where t.treinamentorespostas.idtreinamentorespostas="
						+ treinamentorespostas.getIdtreinamentorespostas()
						+ " and t.usuario.idusuario="+usuario.getIdusuario());  
		TreinamentoRespostasCertaDao treinamentoRespostasCertaDao = new TreinamentoRespostasCertaDao();
		try {
			treinamentorespostacerta = treinamentoRespostasCertaDao
					.consultar("select t From Treinamentorespostacerta t"
							+ " where t.treinamentorespostas.idtreinamentorespostas="
							+ treinamentorespostas.getIdtreinamentorespostas());
		} catch (SQLException e) {
			e.printStackTrace();  
		}
		verificarPontuacao();
	}

	public String salvar() { 
		if (validarDadosRespostaUsuario()) {
			treinamentorespostausuario.setUsuario(usuarioLogadoMB.getUsuario());
			treinamentorespostausuario.setTreinamentorespostas(treinamentorespostas); 
			TreinamentoRespostaUsuarioFacade treinamentoRespostaUsuarioFacade = new TreinamentoRespostaUsuarioFacade();
			treinamentorespostausuario = treinamentoRespostaUsuarioFacade.salvar(treinamentorespostausuario);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			return "resultadoQuestionario";
		} 
		return "";
	}

	 
	public boolean validarDadosRespostaUsuario() { 
		if (treinamentorespostausuario.getRespusuario1() == null
				|| treinamentorespostausuario.getRespusuario1().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta da questão nº 1.");
			return false;
		} else if (treinamentorespostausuario.getRespusuario2() == null
				|| treinamentorespostausuario.getRespusuario2().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta da questão nº 2.");
			return false;
		} else if (treinamentorespostausuario.getRespusuario3() == null
				|| treinamentorespostausuario.getRespusuario3().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta da questão nº 3.");
			return false;
		}else if (treinamentorespostausuario.getRespusuario4() == null
				|| treinamentorespostausuario.getRespusuario4().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta da questão nº 4.");
			return false;
		}else if (treinamentorespostausuario.getRespusuario5() == null
				|| treinamentorespostausuario.getRespusuario5().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta da questão nº 5.");
			return false;
		}else if (treinamentorespostausuario.getRespusuario6() == null
				|| treinamentorespostausuario.getRespusuario6().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta da questão nº 6.");
			return false;
		}
		return true;
	}
	
	public String retornarCor(int pergunta, String resposta){
		String cor ="";
		if(pergunta==1){
			if(resposta.equals(treinamentorespostausuario.getRespusuario1())){
				cor =  "red";
			}
			if(resposta.equals(treinamentorespostacerta.getRespcerta1())){
				cor =  "green";
			}
		}else if(pergunta==2){
			if(resposta.equals(treinamentorespostausuario.getRespusuario2())){
				cor =  "red";
			}
			if(resposta.equals(treinamentorespostacerta.getRespcerta2())){
				cor =  "green";
			}
		}else if(pergunta==3){
			if(resposta.equals(treinamentorespostausuario.getRespusuario3())){
				cor =  "red";
			}
			if(resposta.equals(treinamentorespostacerta.getRespcerta3())){
				cor =  "green";
			}
		}else if(pergunta==4){
			if(resposta.equals(treinamentorespostausuario.getRespusuario4())){
				cor =  "red";
			}
			if(resposta.equals(treinamentorespostacerta.getRespcerta4())){
				cor =  "green";
			}
		}else if(pergunta==5){
			if(resposta.equals(treinamentorespostausuario.getRespusuario5())){
				cor =  "red";
			}
			if(resposta.equals(treinamentorespostacerta.getRespcerta5())){
				cor =  "green";
			}
		}else if(pergunta==6){
			if(resposta.equals(treinamentorespostausuario.getRespusuario6())){
				cor =  "red";
			}
			if(resposta.equals(treinamentorespostacerta.getRespcerta6())){
				cor = "green";
			}
		}
		return cor;
	}
	
	public void verificarPontuacao(){
		pontuacao=0;
		if (treinamentorespostacerta.getRespcerta1().equals(treinamentorespostausuario.getRespusuario1())) {
			pontuacao = pontuacao+1;
		}
		if (treinamentorespostacerta.getRespcerta2().equals(treinamentorespostausuario.getRespusuario2())) {
			pontuacao = pontuacao+1;
		}
		if (treinamentorespostacerta.getRespcerta3().equals(treinamentorespostausuario.getRespusuario3())) {
			pontuacao = pontuacao+1;
		}
		if (treinamentorespostacerta.getRespcerta4().equals(treinamentorespostausuario.getRespusuario4())) {
			pontuacao = pontuacao+1;
		}
		if (treinamentorespostacerta.getRespcerta5().equals(treinamentorespostausuario.getRespusuario5())) {
			pontuacao = pontuacao+1;
		}
		if (treinamentorespostacerta.getRespcerta6().equals(treinamentorespostausuario.getRespusuario6())) {
			pontuacao = pontuacao+1;
		}
	}
}
