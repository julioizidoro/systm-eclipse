package br.com.travelmate.managerBean.crm.motivocancelamento;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.model.Motivocancelamento; 

@Named
@ViewScoped
public class MotivoCancelamentosMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Motivocancelamento> listaMotivo;
	
	@PostConstruct()
	public void init() {
		gerarLista();
	} 

	public List<Motivocancelamento> getListaMotivo() {
		return listaMotivo;
	}
 
	public void setListaMotivo(List<Motivocancelamento> listaMotivo) {
		this.listaMotivo = listaMotivo;
	} 

	public String novo(){
	    Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",400); 
	    options.put("closable", false);
	    RequestContext.getCurrentInstance().openDialog("cadMotivoCancelamento", options, null);
        return "";
    } 
	
	public void gerarLista(){
		MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
		String sql = "select m from Motivocancelamento m order by m.motivo";
		listaMotivo = motivoCancelamentoFacade.lista(sql);
	}  

	
	public String editar(Motivocancelamento motivocancelamento){
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",400); 
	    options.put("closable", false);
	    FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("motivocancelamento", motivocancelamento);
	    RequestContext.getCurrentInstance().openDialog("cadMotivoCancelamento", options, null);
        return "";
	}
	
	
}
