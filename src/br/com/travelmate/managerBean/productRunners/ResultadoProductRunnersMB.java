package br.com.travelmate.managerBean.productRunners;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.bean.ProductRunnersBean;

@Named
@ViewScoped
public class ResultadoProductRunnersMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ProductRunnersBean> listaProductRunnersBean;
	private int mes;
	private int ano;

    @SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaProductRunnersBean = (List<ProductRunnersBean>) session.getAttribute("listaProductRunnersBean");
		mes = (int) session.getAttribute("mes");
		ano = (int) session.getAttribute("ano");
		session.removeAttribute("listaProductRunnersBean");
		session.removeAttribute("mes");
		session.removeAttribute("ano");
	}
	
	




	public List<ProductRunnersBean> getListaProductRunnersBean() {
		return listaProductRunnersBean;
	}






	public void setListaProductRunnersBean(List<ProductRunnersBean> listaProductRunnersBean) {
		this.listaProductRunnersBean = listaProductRunnersBean;
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
