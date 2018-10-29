package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.VendasEmbarqueDao;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Vendasembarque;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AtualizarControleCursoMB implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasEmbarqueDao vendasEmbarqueDao;
	private Controlecurso controle;
	private Curso curso;
	private int idade;
	private String tipoacomodacao;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controle = (Controlecurso) session.getAttribute("controle");
        session.removeAttribute("controle");
        consultarCurso();
        idadeCliente();
        if (curso.getTipoAcomodacao() != null && curso.getTipoAcomodacao().length() > 0 && !curso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			tipoacomodacao = curso.getTipoAcomodacao();
		}else if(curso.getAcomodacaocurso() != null) {
			tipoacomodacao = curso.getAcomodacaocurso().getAcomodacao().getTipoacomodacao();
		}else {
			tipoacomodacao = "Sem acomodação";
		}
        if (controle.getVendas().getVendasembarque() == null) {
			controle.getVendas().setVendasembarque(new Vendasembarque());
			controle.getVendas().getVendasembarque().setVendas(controle.getVendas());
		}
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



	public String getTipoacomodacao() {
		return tipoacomodacao;
	}



	public void setTipoacomodacao(String tipoacomodacao) {
		this.tipoacomodacao = tipoacomodacao;
	}



	public void consultarCurso(){
		CursoFacade cursoFacade = new CursoFacade();
		curso = cursoFacade.consultarCursos(controle.getVendas().getIdvendas());
	}
	
	
	public String salvar(){
		CursoFacade cursoFacade = new CursoFacade();
		controle = cursoFacade.salvar(controle);
		curso = cursoFacade.salvar(curso);
		if (controle.getVendas().getVendasembarque().getIdvendasembarque() != null) {
			vendasEmbarqueDao.salvar(controle.getVendas().getVendasembarque());
		}
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
