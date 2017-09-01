package br.com.travelmate.managerBean.mtp;
 
import java.io.Serializable; 
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession; 

import br.com.travelmate.facade.TreinamentoRespostaUsuarioFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Mtp;
import br.com.travelmate.model.Treinamentoperguntas;

import br.com.travelmate.model.Treinamentorespostas;
import br.com.travelmate.model.Treinamentorespostausuario;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ResponderQuestionarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;
	private Mtp mtp;
	private Treinamentoperguntas treinamentoperguntas;
	private Treinamentorespostas treinamentorespostas; 
	private Treinamentorespostausuario treinamentorespostausuario;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		treinamentorespostas = (Treinamentorespostas) session.getAttribute("treinamentorespostas");
		session.removeAttribute("treinamentorespostas");
		treinamentoperguntas = treinamentorespostas.getTreinamentoperguntas();
		mtp = treinamentoperguntas.getMtp();
		treinamentorespostausuario = new Treinamentorespostausuario();
	}

	public Mtp getMtp() {
		return mtp;
	}

	public void setMtp(Mtp mtp) {
		this.mtp = mtp;
	}

	public Treinamentoperguntas getTreinamentoperguntas() {
		return treinamentoperguntas;
	}

	public void setTreinamentoperguntas(Treinamentoperguntas treinamentoperguntas) {
		this.treinamentoperguntas = treinamentoperguntas;
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

	public String cancelar() {
		return "consMtp";
	} 

	public String salvar() { 
		if (validarDadosRespostaUsuario()) {
			treinamentorespostausuario.setUsuario(usuarioLogadoMB.getUsuario());
			treinamentorespostausuario.setTreinamentorespostas(treinamentorespostas); 
			TreinamentoRespostaUsuarioFacade treinamentoRespostaUsuarioFacade = new TreinamentoRespostaUsuarioFacade();
			treinamentorespostausuario = treinamentoRespostaUsuarioFacade.salvar(treinamentorespostausuario);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("treinamentorespostas", treinamentorespostas); 
			session.setAttribute("usuario", usuarioLogadoMB.getUsuario());
			session.setAttribute("voltar", false);
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
}
