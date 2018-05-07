package br.com.travelmate.managerBean.contrato.vistos;

import br.com.travelmate.model.Vistos;

public class DadosVistos {
	
private Vistos vistos;
	
	
	public DadosVistos(Vistos vistos) {
		this.vistos = vistos;
	}
	
	
	public String dadosCurso(){
		String dados = "<div style=\"border: 2px solid;width:100%;border-color:#9C9C9C;\">"+
			"<p style=\"margin: 1%;\">"+
				
			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"2\">"+
				"<h:panelGroup><b>Pais:</b>"+ vistos.getPaisDestino() + "</h:panelGroup>"+
				"<h:panelGroup style=\"margin-left:20%;\"><b>Tipo Visto:</b>" + vistos.getCategoria() + "</h:panelGroup> <br/><br/>"+
				
			"</h:panelGrid>"
		+"</div><br/>";
		return dados;
	}

}
