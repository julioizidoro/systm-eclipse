package br.com.travelmate.managerBean.contrato.aupair;

import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Curso;
import br.com.travelmate.util.Formatacao;

public class DadosAuPair {

	
private Aupair aupair;
	
	
	public DadosAuPair(Aupair aupair) {
		this.aupair = aupair;
	}
	
	
//	public String dadosAupair(){
//		String dados = "<div style=\"border: 2px solid;width:100%;border-color:#9C9C9C;\">"+
//			"<p style=\"margin: 1%;\">"+
//				"<b style=\"color:green;\">1. OBJETO</b> <br /> O presente contrato tem como objeto a intermediação"+
//				"da prestação de serviços educacionais de intercâmbio cultural pela"+
//				"TRAVELMATE (intermediadora) e seu parceiro internacional,a"+
//				"instituição mencionada abaixo,em favor do(a) PARTICIPANTE , visando"+
//				"sua inscrição no seguinte programa:"+
//			"</p><br/>"+
//			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"2\">"+
//				"<h:panelGroup><b>Curso 01:</b>"+ curso.getNomeCurso() + "</h:panelGroup>"+
//				"<h:panelGroup style=\"margin-left:20%;\"><b>Escola/Instituição:</b>" + curso.getEscola() + "</h:panelGroup> <br/><br/>"+
//				"<h:panelGroup></h:panelGroup>"+
//				"<h:panelGroup style=\"margin:1%;\">"+
//					"<b>Local:</b>" + curso.getCidade() + ", " + curso.getPais() +
//				"</h:panelGroup>" +
//			"</h:panelGrid>"+
//
//			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"3\">"+
//				"<h:panelGroup style=\"margin-left:8%;\"><b>Aulas Por Semana:</b>" + Formatacao.formatarDouble(curso.getAulassemana()) + "</h:panelGroup>"+
//				"<h:panelGroup style=\"margin-left:8%;\"><b>N° Semanas:</b>" + curso.getNumeroSenamas() + "</h:panelGroup> <br/><br/>"+
//				"<h:panelGroup style=\"margin:1%;\">"+
//					"<b>Data Inicio: </b>"+ Formatacao.ConvercaoDataPadrao(curso.getDataInicio()) + 
//				"</h:panelGroup>"+
//				"<h:panelGroup>" +
//					"<b>Data Término: </b>" + Formatacao.ConvercaoDataPadrao(curso.getDataTermino()) +
//				"</h:panelGroup> "+
//				"<h:panelGroup><b>Acomodação: </b>" +  curso.getTipoAcomodacao() + "</h:panelGroup> <br/><br/>"+
//				"<h:panelGroup style=\"margin:1%;\"><b>Data Chegada: </b>"+ Formatacao.ConvercaoDataPadrao(curso.getDataChegada()) + 
//				"</h:panelGroup>" +
//				"<h:panelGroup><b>Data Partida: </b>" + Formatacao.ConvercaoDataPadrao(curso.getDataSaida()) +
//				"</h:panelGroup> " +
//				"<h:panelGroup><b>Extras: </b>" + curso.getAdicionais() + "</h:panelGroup>" +
//			"</h:panelGrid>"
//		+"</div><br/>";
//		return dados;
//	}
}
