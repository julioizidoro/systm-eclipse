/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.comissao;
 
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;


/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class CalcularComissaoFranquiaMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vendascomissao vendasComissao;
	private Double percentualComissao;
	private float desagio;
    
    @PostConstruct
    private void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendasComissao = (Vendascomissao) session.getAttribute("vendascomissao");
        float percentualComissaoFloat =  (float) session.getAttribute("percentualcomissao"); 
        String percentualString = Formatacao.formatarFloatString(percentualComissaoFloat);
        percentualComissao = Formatacao.formatarStringDouble(percentualString);
        desagio = vendasComissao.getDesagio();
        session.removeAttribute("vendascomissao"); 
        session.removeAttribute("percentualcomissao");
    }
	
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}

	public Double getPercentualComissao() {
		return percentualComissao;
	}

	public void setPercentualComissao(Double percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	public float getDesagio() {
		return desagio;
	}

	public void setDesagio(float desagio) {
		this.desagio = desagio;
	}

	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	} 
}
