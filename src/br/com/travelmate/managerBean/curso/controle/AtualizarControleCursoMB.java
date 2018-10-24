package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Curso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AtualizarControleCursoMB implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controlecurso controle;
	private Curso curso;
	private int idade;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controle = (Controlecurso) session.getAttribute("controle");
        session.removeAttribute("controle");
        consultarCurso();
        idadeCliente();
	}

	
	
	public Controlecurso getControle() {
		return controle;
	}
	
	public void setControle(Controlecurso controle) {
		this.controle = controle;
	}


	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public int getIdade() {
		return idade;
	}



	public void setIdade(int idade) {
		this.idade = idade;
	}



	public void consultarCurso(){
		CursoFacade cursoFacade = new CursoFacade();
		curso = cursoFacade.consultarCursos(controle.getVendas().getIdvendas());
	}
	
	
	public String salvar(){
		CursoFacade cursoFacade = new CursoFacade();
		controle = cursoFacade.salvar(controle);
		curso = cursoFacade.salvar(curso);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public void idadeCliente() {
		if(controle.getVendas().getCliente().getDataNascimento()!=null){
			idade = Formatacao.calcularIdade(controle.getVendas().getCliente().getDataNascimento());
		}
	}
	
}
