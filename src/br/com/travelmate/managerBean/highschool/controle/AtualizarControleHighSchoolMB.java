package br.com.travelmate.managerBean.highschool.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.VendasEmbarqueDao;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Vendasembarque;

@Named
@ViewScoped
public class AtualizarControleHighSchoolMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasEmbarqueDao vendasEmbarqueDao;
	private Controlehighschool controlehighschool;
	private String sql;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        controlehighschool = (Controlehighschool) session.getAttribute("controle");
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        session.removeAttribute("controle");
        if (controlehighschool.getVendas().getVendasembarque() == null) {
			controlehighschool.getVendas().setVendasembarque(new Vendasembarque());
			controlehighschool.getVendas().getVendasembarque().setVendas(controlehighschool.getVendas());
		}
	}

	public Controlehighschool getControlehighschool() {
		return controlehighschool;
	}

	public void setControlehighschool(Controlehighschool controlehighschool) {
		this.controlehighschool = controlehighschool;
	}

	public String salvar(){
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		controlehighschool.setHighschool(highSchoolFacade.salvar(controlehighschool.getHighschool()));
			vendasEmbarqueDao.salvar(controlehighschool.getVendas().getVendasembarque());
		controlehighschool = highSchoolFacade.salvar(controlehighschool); 
		RequestContext.getCurrentInstance().closeDialog(sql);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog("");
		return "";
	}

}
