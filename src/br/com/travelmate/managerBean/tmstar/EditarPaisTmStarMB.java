package br.com.travelmate.managerBean.tmstar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.TmStarFacade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Tmstar;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EditarPaisTmStarMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pais pais;
	private List<Pais> listaPais;
	private Tmstar tmstar;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tmstar = (Tmstar) session.getAttribute("tmstar");
		session.removeAttribute("tmstar");
		gerarListaPais();
		if (tmstar == null) {
			pais = new Pais();
		}else{
			pais = tmstar.getPais();
		}
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public List<Pais> getListaPais() {
		return listaPais;
	}


	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	

	public Tmstar getTmstar() {
		return tmstar;
	}


	public void setTmstar(Tmstar tmstar) {
		this.tmstar = tmstar;
	}


	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Tmstar());
	}
	
	
	public void salvar(){
		TmStarFacade tmStarFacade  = new TmStarFacade();
		if (pais != null) {
			tmstar.setPais(pais);
			tmstar = tmStarFacade.salvar(tmstar);
			RequestContext.getCurrentInstance().closeDialog(tmstar);
		}else{
			Mensagem.lancarMensagemInfo("Atenção", " você não escolheu nenhum pais");
		}
	}
	
	
	public void gerarListaPais(){
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		if (listaPais == null || listaPais.isEmpty()) {
			listaPais = new ArrayList<Pais>();
		}
	}

}
