package br.com.travelmate.managerBean.turismo.passagemAerea;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;


@Named
@ViewScoped
public class CalcularComissaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float tarifa;
	private float valorComissao;
	private double percentualComissao;
	
	@PostConstruct
    public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        tarifa = (float) session.getAttribute("tarifa");
        session.removeAttribute("tarifa");
	}
	
	
	public float getTarifa() {
		return tarifa;
	}
	public void setTarifa(float tarifa) {
		this.tarifa = tarifa;
	}
	public float getValorComissao() {
		return valorComissao;
	}
	public void setValorComissao(float valorComissao) {
		this.valorComissao = valorComissao;
	}
	public double getPercentualComissao() {
		return percentualComissao;
	}
	public void setPercentualComissao(double percentualComissao) {
		this.percentualComissao = percentualComissao;
	}
	
	public String valorComissao(){
		percentualComissao = percentualComissao /100;
        valorComissao = (float) (tarifa * percentualComissao);
        RequestContext.getCurrentInstance().closeDialog(valorComissao);
        return "";
	}

	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
        return "";
	}
}
