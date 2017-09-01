package br.com.travelmate.managerBean.cambio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class CambioMB  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Date data;
	private List<Cambio> listaCambio;
	private String gerarcambio;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		data = (Date) session.getAttribute("data");
		session.removeAttribute("data");
	}
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}
	
	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public List<Cambio> getListaCambio() {
		return listaCambio;
	}
	
	public void setListaCambio(List<Cambio> listaCambio) {
		this.listaCambio = listaCambio;
	}
	
	public String getGerarcambio() {
		return gerarcambio;
	}

	public void setGerarcambio(String gerarcambio) {
		this.gerarcambio = gerarcambio;
	}
	
	

	public void pesquisar(){ 
		if(data!=null){
			CambioFacade cambioFacade = new CambioFacade();
			listaCambio = cambioFacade.listar(Formatacao.ConvercaoDataSql(data));
			if(listaCambio==null || listaCambio.size()==0){
				 Map<String,Object> options = new HashMap<String, Object>();
			     options.put("contentWidth",200);
			     FacesContext fc = FacesContext.getCurrentInstance();
			     HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			     session.setAttribute("data", data);
			     RequestContext.getCurrentInstance().openDialog("gerarCambioDia", options, null);
			}
		}
	}
	
	public void pesquisarData(){ 
		if(data!=null){
			CambioFacade cambioFacade = new CambioFacade();
			listaCambio = cambioFacade.listar(Formatacao.ConvercaoDataSql(data));
			if(listaCambio==null){
				 listaCambio = new ArrayList<Cambio>();
			}
		}
	}
	
	
	public void gerarcambiodia(String gerarcambio){
		if(gerarcambio.equalsIgnoreCase("Sim")){
			CambioFacade cambioFacade = new CambioFacade();
			List<Moedas> listamoedas;
			listamoedas = cambioFacade.listaMoedas();
			Cambio cambio;
			for (int i = 0; i < listamoedas.size(); i++) {
				cambio = new Cambio();
				cambio.setData(data);
				cambio.setMoedas(listamoedas.get(i));
				cambio.setValor(0.0f);
				cambio = cambioFacade.salvar(cambio);
			}
			FacesContext fc = FacesContext.getCurrentInstance();
		    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		    session.setAttribute("data", data);
		}
		RequestContext.getCurrentInstance().closeDialog(gerarcambio);
	}
	
	
	public void retornoDialogGerarCambio(SelectEvent event) {
		this.gerarcambio = (String) event.getObject();
		if(gerarcambio.equalsIgnoreCase("Sim")){
			pesquisar();
		}
	}
	
	public void editarCambio(Cambio cambio){
		CambioFacade cambioFacade = new CambioFacade();
		cambio = cambioFacade.salvar(cambio);
	}
	
	
	
}
