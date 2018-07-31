package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ConteudoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DashBoardMB dashBoardMB;
	private String dados = "";
	
	
	@PostConstruct
	public void init() {
		getDashBoardMB();
		if (dashBoardMB.getHoraCalculo()==null) {
			dashBoardMB.setHoraCalculo(new Date());
		}else {
			long diferenca = new Date().getTime();
			long calculo =dashBoardMB.getHoraCalculo().getTime();
			diferenca = diferenca - calculo;
			if (diferenca>=600000) {
				dashBoardMB.gerarDadosDashBoard();
				dashBoardMB.setHoraCalculo(new Date());
			}
			
		}
		
	}


	public String getDados() {
		return dados;
	}


	public void setDados(String dados) {
		this.dados = dados;
	}


	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}


	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}
	
	
	
	

}
