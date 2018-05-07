package br.com.travelmate.managerBean.contrato.demipair;

import br.com.travelmate.model.Demipair;
import br.com.travelmate.util.Formatacao;

public class DadosDemiPair {

	
private Demipair demipair;
	
	
	public DadosDemiPair(Demipair demipair) {
		this.demipair = demipair;
	}
	
	
	public String dadosCurso(){
		String dados = "<div style=\"border: 2px solid;width:100%;border-color:#9C9C9C;\">"+
			"<p style=\"margin: 1%;\">"+
				"<b style=\"color:green;\">1. OBJETO</b> <br /> 1.1 O presente contrato tem como objeto a intermediação"+
				"da prestação de serviços educacionais de intercâmbio cultural pela"+
				"TRAVELMATE (intermediadora) e seu parceiro internacional,a"+
				"instituição mencionada abaixo,em favor do(a) PARTICIPANTE , visando"+
				"sua inscrição no seguinte programa, visando a participação deste no programa de Demi Pair\n" + 
				"com duração de "+ demipair.getNumerosemanas() + " semanas e início previsto para " + Formatacao.ConvercaoDataPadrao(demipair.getDatainicio())    +
			"</p><br/>"+
			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"2\">"+
				"<h:panelGroup><b>Curso 01:</b>"+ demipair.getCurso() + "</h:panelGroup>"+
				"<h:panelGroup style=\"margin-left:20%;\"><b>Instituição:</b>" + demipair.getInituicaoEstuda() + "</h:panelGroup> <br/><br/>"+
				"<h:panelGroup></h:panelGroup>"+
			"</h:panelGrid>"

			
		+"</div><br/>";
		return dados;
	}
}
