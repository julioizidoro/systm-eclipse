package br.com.travelmate.managerBean.higherEducation.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.HeControleDao;
import br.com.travelmate.dao.VendasEmbarqueDao;
import br.com.travelmate.model.Hecontrole;
import br.com.travelmate.model.Vendasembarque;

@Named
@ViewScoped
public class AtualizarControleHeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private VendasEmbarqueDao vendasEmbarqueDao;
	@Inject
	private HeControleDao heControleDao;
	private Hecontrole hecontrole;
	private String sql;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        hecontrole = (Hecontrole) session.getAttribute("hecontrole");
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        session.removeAttribute("hecontrole");
        if (hecontrole.getHe().getVendas().getVendasembarque() == null) {
			hecontrole.getHe().getVendas().setVendasembarque(new Vendasembarque());
			hecontrole.getHe().getVendas().getVendasembarque().setVendas(hecontrole.getHe().getVendas());
		}
	}


	public Hecontrole getHecontrole() {
		return hecontrole;
	}






	public void setHecontrole(Hecontrole hecontrole) {
		this.hecontrole = hecontrole;
	}

	
	
	public String salvar(){
		hecontrole.getHe().getVendas().setVendasembarque(vendasEmbarqueDao.salvar(hecontrole.getHe().getVendas().getVendasembarque()));
		hecontrole = heControleDao.salvar(hecontrole);
		RequestContext.getCurrentInstance().closeDialog(sql);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(sql);
		return "";
	}

}
