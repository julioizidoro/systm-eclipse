package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.bean.CategoriaTmRaceBean;

@Named
@ViewScoped
public class ResultadoTmRaceMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mes;
	private int ano;
	private List<CategoriaTmRaceBean> listaCategoriaBean;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaCategoriaBean = (List<CategoriaTmRaceBean>) session.getAttribute("listaCategoriaBean");
		mes = (int) session.getAttribute("mes");
		ano = (int) session.getAttribute("ano");
		session.removeAttribute("listaCategoriaBean");
		session.removeAttribute("mes");
		session.removeAttribute("ano");
	}
	
	





	public List<CategoriaTmRaceBean> getListaCategoriaBean() {
		return listaCategoriaBean;
	}







	public void setListaCategoriaBean(List<CategoriaTmRaceBean> listaCategoriaBean) {
		this.listaCategoriaBean = listaCategoriaBean;
	}







	public int getMes() {
		return mes;
	}






	public void setMes(int mes) {
		this.mes = mes;
	}






	public int getAno() {
		return ano;
	}






	public void setAno(int ano) {
		this.ano = ano;
	}

}
