package br.com.travelmate.managerBean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class ValorComissaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float valorComissao=0.0f;
	
	
	public float getValorComissao() {
		return valorComissao;
	}
	public void setValorComissao(float valorComissao) {
		this.valorComissao = valorComissao;
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
    }
	
	public String salvar(){
		if(valorComissao>0){
            RequestContext.getCurrentInstance().closeDialog(valorComissao);
    		return "";
		}
		return "";
	}

}
