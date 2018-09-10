package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Questionariohe;

@Named
@ViewScoped
public class ImportarQuestionarioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Questionariohe> listaQuestionario;
	private Questionariohe questionariohe;
	private Cliente cliente;
	
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cliente = (Cliente) session.getAttribute("cliente");
		session.removeAttribute("cliente");
		gerarListaQuestionario();
	}



	public List<Questionariohe> getListaQuestionario() {
		return listaQuestionario;
	}



	public void setListaQuestionario(List<Questionariohe> listaQuestionario) {
		this.listaQuestionario = listaQuestionario;
	}



	public Questionariohe getQuestionariohe() {
		return questionariohe;
	}



	public void setQuestionariohe(Questionariohe questionariohe) {
		this.questionariohe = questionariohe;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	public void gerarListaQuestionario() {
		if (cliente != null) {
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaQuestionario = questionarioHeFacade.listar("SELECT q FROM Questionariohe q WHERE q.cliente.idcliente=" + cliente.getIdcliente());
			if (listaQuestionario == null) {
				listaQuestionario = new ArrayList<Questionariohe>();
			}
		}
	}
	
	
	public void selecionarQuestionario(Questionariohe questionariohe) {
		RequestContext.getCurrentInstance().closeDialog(questionariohe);
	}
	
	
}
