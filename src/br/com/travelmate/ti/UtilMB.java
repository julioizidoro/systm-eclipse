package br.com.travelmate.ti;

import java.io.Serializable; 

import javax.annotation.PostConstruct; 
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.managerBean.AplicacaoMB;
 

@Named
@ViewScoped
public class UtilMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@Inject
	private AplicacaoMB aplicacaoMB;
	 
	@PostConstruct
	public void init() {  
		
	}
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public void recalcularDashboard() {
		TiBean tiBean = new TiBean(aplicacaoMB.getParametrosprodutos());
		tiBean.listarVendas();
	}
	 
}
