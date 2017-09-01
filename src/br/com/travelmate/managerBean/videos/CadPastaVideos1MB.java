package br.com.travelmate.managerBean.videos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VideoPasta1Facade;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadPastaVideos1MB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Videopasta1 videopasta1;
	
	@PostConstruct  
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta1 = (Videopasta1) session.getAttribute("videopasta1");
		departamento = (Departamento) session.getAttribute("departamento");
		session.removeAttribute("departamento");
		session.removeAttribute("videopasta1");
		if (videopasta1 == null) {
			videopasta1 = new Videopasta1();
		}
	}

	
	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	

	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}


	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}



	

	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Videopasta1());
	}
	
	public void salvar(){
		VideoPasta1Facade videoPasta1Facade = new VideoPasta1Facade();
		videopasta1.setDepartamento(departamento);
		String mensagem = validarDados();
		if (mensagem.length() == 0) { 
			videopasta1 = videoPasta1Facade.salvar(videopasta1);
			RequestContext.getCurrentInstance().closeDialog(videopasta1);
		}else{
			Mensagem.lancarMensagemInfo("", mensagem);
		}
	}
	
	public String validarDados(){
		String msg = "";
		if (videopasta1.getDescricao() == null || videopasta1.getDescricao().length() == 0) {
			msg = msg + " Informe o nome da pasta";
		}
		return msg;
	}
}
