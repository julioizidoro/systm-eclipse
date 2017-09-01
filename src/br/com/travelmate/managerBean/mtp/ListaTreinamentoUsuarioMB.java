package br.com.travelmate.managerBean.mtp;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.dao.TreinamentoRespostasCertaDao;
import br.com.travelmate.facade.TreinamentoRespostaUsuarioFacade; 
import br.com.travelmate.model.Treinamentorespostacerta;
import br.com.travelmate.model.Treinamentorespostas;
import br.com.travelmate.model.Treinamentorespostausuario;
import br.com.travelmate.model.Usuario; 

@Named
@ViewScoped
public class ListaTreinamentoUsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private Treinamentorespostas treinamentorespostas;
	private Treinamentorespostacerta treinamentorespostacerta;
	private List<Treinamentorespostausuario> listarespostausuario;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		treinamentorespostas = (Treinamentorespostas) session.getAttribute("treinamentorespostas");
		session.removeAttribute("treinamentorespostas");
		TreinamentoRespostasCertaDao treinamentoRespostasCertaDao = new TreinamentoRespostasCertaDao();
		try {
			treinamentorespostacerta = treinamentoRespostasCertaDao
					.consultar("select t From Treinamentorespostacerta t"
							+ " where t.treinamentorespostas.idtreinamentorespostas="
							+ treinamentorespostas.getIdtreinamentorespostas());
		} catch (SQLException e) {
			e.printStackTrace();  
		}
		gerarListaTreinamentoRespostaUsuario();
	}
 

	public Treinamentorespostas getTreinamentorespostas() {
		return treinamentorespostas;
	}

	public void setTreinamentorespostas(Treinamentorespostas treinamentorespostas) {
		this.treinamentorespostas = treinamentorespostas;
	}

	public List<Treinamentorespostausuario> getListarespostausuario() {
		return listarespostausuario;
	}

	public void setListarespostausuario(List<Treinamentorespostausuario> listarespostausuario) {
		this.listarespostausuario = listarespostausuario;
	}

	public String fechar() {
		return "consMtp";
	}

	public void gerarListaTreinamentoRespostaUsuario() {
		TreinamentoRespostaUsuarioFacade treinamentoRespostaUsuarioFacade = new TreinamentoRespostaUsuarioFacade();
		listarespostausuario = treinamentoRespostaUsuarioFacade.listar(
				"select t From Treinamentorespostausuario t" + " where t.treinamentorespostas.idtreinamentorespostas="
						+ treinamentorespostas.getIdtreinamentorespostas());
		if (listarespostausuario == null) {
			listarespostausuario = new ArrayList<>();
		}
	}

	public String visualizarRespostas(Treinamentorespostas treinamentorespostas, Usuario usuario) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("treinamentorespostas", treinamentorespostas);
		session.setAttribute("usuario", usuario);
		session.setAttribute("voltar", true);
		return "resultadoQuestionario";
	}
	
	public int verificarPontuacao(Treinamentorespostausuario treinamentorespostausuario){
		int pontuacao=0;
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
		return pontuacao;
	}
}
