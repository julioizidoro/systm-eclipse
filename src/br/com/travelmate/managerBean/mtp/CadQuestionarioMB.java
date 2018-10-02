package br.com.travelmate.managerBean.mtp;

import java.io.Serializable;
import java.sql.SQLException; 
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession; 

import br.com.travelmate.dao.TreinamentoRespostasCertaDao;
import br.com.travelmate.facade.TreinamentoPerguntasFacade;
import br.com.travelmate.facade.TreinamentoRespostaCertaFacade;
import br.com.travelmate.facade.TreinamentoRespostasFacade;
import br.com.travelmate.model.Mtp;
import br.com.travelmate.model.Treinamentoperguntas;
import br.com.travelmate.model.Treinamentorespostacerta;
import br.com.travelmate.model.Treinamentorespostas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadQuestionarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mtp mtp;
	private Treinamentoperguntas treinamentoperguntas;
	private Treinamentorespostas treinamentorespostas;
	private Treinamentorespostacerta treinamentorespostacerta;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		mtp = (Mtp) session.getAttribute("mtp");
		session.removeAttribute("mtp");
		iniciarQuestionario();
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

	public Treinamentorespostacerta getTreinamentorespostacerta() {
		return treinamentorespostacerta;
	}

	public void setTreinamentorespostacerta(Treinamentorespostacerta treinamentorespostacerta) {
		this.treinamentorespostacerta = treinamentorespostacerta;
	}

	public String cancelar() {
		return "consMtp";
	}

	public void iniciarQuestionario() {
		TreinamentoPerguntasFacade treinamentoPerguntasFacade = new TreinamentoPerguntasFacade();
		treinamentoperguntas = treinamentoPerguntasFacade
				.consultar("Select t From Treinamentoperguntas t" + " where t.mtp.idmtp=" + mtp.getIdmtp());
		if (treinamentoperguntas == null || treinamentoperguntas.getIdtreinamentoperguntas() == null) {
			treinamentoperguntas = new Treinamentoperguntas();
			treinamentorespostas = new Treinamentorespostas();
			treinamentorespostacerta = new Treinamentorespostacerta();
		} else {
			TreinamentoRespostasFacade treinamentoRespostasFacade = new TreinamentoRespostasFacade();
			treinamentorespostas = treinamentoRespostasFacade.consultar(
					"select t From Treinamentorespostas t" + " where t.treinamentoperguntas.idtreinamentoperguntas="
							+ treinamentoperguntas.getIdtreinamentoperguntas());
			if (treinamentorespostas == null) {
				treinamentorespostas = new Treinamentorespostas();
				treinamentorespostacerta = new Treinamentorespostacerta();
			} else {
				TreinamentoRespostasCertaDao treinamentoRespostasCertaDao = new TreinamentoRespostasCertaDao();
				try {
					treinamentorespostacerta = treinamentoRespostasCertaDao
							.consultar("select t From Treinamentorespostacerta t"
									+ " where t.treinamentorespostas.idtreinamentorespostas="
									+ treinamentorespostas.getIdtreinamentorespostas());
				} catch (SQLException e) {
					    
				}
				if (treinamentorespostacerta == null) {
					treinamentorespostacerta = new Treinamentorespostacerta();
				}
			}
		}
	}

	public String salvar() {
		if (validarDadosPerguntas()) {
			if (validarDadosRespostas()) {
				if (validarDadosRespostaCerta()) {
					treinamentoperguntas.setMtp(mtp);
					TreinamentoPerguntasFacade treinamentoPerguntasFacade = new TreinamentoPerguntasFacade();
					treinamentoperguntas = treinamentoPerguntasFacade.salvar(treinamentoperguntas);
					treinamentorespostas.setTreinamentoperguntas(treinamentoperguntas);
					TreinamentoRespostasFacade treinamentoRespostasFacade = new TreinamentoRespostasFacade();
					treinamentorespostas = treinamentoRespostasFacade.salvar(treinamentorespostas);
					treinamentorespostacerta.setTreinamentorespostas(treinamentorespostas);
					TreinamentoRespostaCertaFacade treinamentoRespostaCertaFacade = new TreinamentoRespostaCertaFacade();
					treinamentorespostacerta = treinamentoRespostaCertaFacade.salvar(treinamentorespostacerta);
					Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
					return "consMtp";
				}
			}
		}
		return "";
	}

	public boolean validarDadosPerguntas() {
		// perguntas
		if (treinamentoperguntas.getPergunta1() == null || treinamentoperguntas.getPergunta1().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Pergunta nº 1 não preenchida.");
			return false;
		} else if (treinamentoperguntas.getPergunta2() == null || treinamentoperguntas.getPergunta2().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Pergunta nº 2 não preenchida.");
			return false;
		} else if (treinamentoperguntas.getPergunta3() == null || treinamentoperguntas.getPergunta3().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Pergunta nº 3 não preenchida.");
			return false;
		} else if (treinamentoperguntas.getPergunta4() == null || treinamentoperguntas.getPergunta4().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Pergunta nº 4 não preenchida.");
			return false;
		} else if (treinamentoperguntas.getPergunta5() == null || treinamentoperguntas.getPergunta5().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Pergunta nº 5 não preenchida.");
			return false;
		} else if (treinamentoperguntas.getPergunta6() == null || treinamentoperguntas.getPergunta6().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Pergunta nº 6 não preenchida.");
			return false;
		}
		return true;
	}

	public boolean validarDadosRespostas() {
		// respostas
		// 1
		if (treinamentorespostas.getResposta11() == null || treinamentorespostas.getResposta11().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 1 - Pergunta nº 1 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta12() == null || treinamentorespostas.getResposta12().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 2 - Pergunta nº 1 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta13() == null || treinamentorespostas.getResposta13().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 3 - Pergunta nº 1 não preenchida.");
			return false;
		}
		// 2
		if (treinamentorespostas.getResposta21() == null || treinamentorespostas.getResposta21().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 1 - Pergunta nº 2 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta22() == null || treinamentorespostas.getResposta22().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 2 - Pergunta nº 2 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta23() == null || treinamentorespostas.getResposta23().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 3 - Pergunta nº 2 não preenchida.");
			return false;
		}
		// 3
		if (treinamentorespostas.getResposta31() == null || treinamentorespostas.getResposta31().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 1 - Pergunta nº 3 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta32() == null || treinamentorespostas.getResposta32().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 2 - Pergunta nº 3 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta33() == null || treinamentorespostas.getResposta33().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 3 - Pergunta nº 3 não preenchida.");
			return false;
		}
		// 4
		if (treinamentorespostas.getResposta41() == null || treinamentorespostas.getResposta41().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 1 - Pergunta nº 4 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta42() == null || treinamentorespostas.getResposta42().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 2 - Pergunta nº 4 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta43() == null || treinamentorespostas.getResposta43().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 3 - Pergunta nº 4 não preenchida.");
			return false;
		}
		// 5
		if (treinamentorespostas.getResposta51() == null || treinamentorespostas.getResposta51().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 1 - Pergunta nº 5 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta52() == null || treinamentorespostas.getResposta52().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 2 - Pergunta nº 5 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta53() == null || treinamentorespostas.getResposta53().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 3 - Pergunta nº 5 não preenchida.");
			return false;
		}
		// 6
		if (treinamentorespostas.getResposta61() == null || treinamentorespostas.getResposta61().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 1 - Pergunta nº 6 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta62() == null || treinamentorespostas.getResposta62().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 2 - Pergunta nº 6 não preenchida.");
			return false;
		} else if (treinamentorespostas.getResposta63() == null || treinamentorespostas.getResposta63().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Resposta nº 3 - Pergunta nº 6 não preenchida.");
			return false;
		}
		return true;
	}
	
	public boolean validarDadosRespostaCerta() {
		// perguntas
		if (treinamentorespostacerta.getRespcerta1() == null || treinamentorespostacerta.getRespcerta1().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta correta da questão nº 1.");
			return false;
		} else if (treinamentorespostacerta.getRespcerta2() == null || treinamentorespostacerta.getRespcerta2().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta correta da questão nº 2.");
			return false;
		} else if (treinamentorespostacerta.getRespcerta3() == null || treinamentorespostacerta.getRespcerta3().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta correta da questão nº 3.");
			return false;
		} else if (treinamentorespostacerta.getRespcerta4() == null || treinamentorespostacerta.getRespcerta4().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta correta da questão nº 4.");
			return false;
		} else if (treinamentorespostacerta.getRespcerta5() == null || treinamentorespostacerta.getRespcerta5().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta correta da questão nº 5.");
			return false;
		} else if (treinamentorespostacerta.getRespcerta6() == null || treinamentorespostacerta.getRespcerta6().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Seleciona a resposta correta da questão nº 6.");
			return false;
		} 
		return true;
	}
}
