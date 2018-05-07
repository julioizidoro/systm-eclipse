package br.com.travelmate.managerBean.contrato.teens;

import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.util.Formatacao;

public class DadosTeens {
	
private Programasteens programasteens;
	
	
	public DadosTeens(Programasteens programasteens) {
		this.programasteens = programasteens;
	}
	
	
	public String dadosCurso(){
		String dados = "<div style=\"border: 2px solid;width:100%;border-color:#9C9C9C;\">"+
			"<p style=\"margin: 1%;\">"+
				"<b style=\"color:green;\">1. OBJETO</b> <br /> O presente contrato tem como objeto a intermediação"+
				"da prestação de serviços educacionais de intercâmbio cultural pela"+
				"TRAVELMATE (intermediadora) e seu parceiro internacional,a"+
				"instituição mencionada abaixo,em favor do(a) PARTICIPANTE , visando"+
				"sua inscrição no seguinte programa:"+
			"</p><br/>"+
			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"2\">"+
				"<h:panelGroup><b>Curso 01:</b>"+ programasteens.getNomeCurso() + "</h:panelGroup>"+
				"<h:panelGroup style=\"margin-left:20%;\"><b>Escola/Instituição:</b>" + programasteens.getNomeEscola() + "</h:panelGroup> <br/><br/>"+
				"<h:panelGroup></h:panelGroup>"+
				"<h:panelGroup style=\"margin:1%;\">"+
					"<b>Local:</b>" + programasteens.getCidadeCurso() + ", " + programasteens.getPaisCurso() +
				"</h:panelGroup>" +
			"</h:panelGrid>"+

			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"3\">"+
				"<h:panelGroup style=\"margin-left:8%;\"><b>Aulas Por Semana:</b>" + programasteens.getAulasSemana() + "</h:panelGroup>"+
				"<h:panelGroup style=\"margin-left:8%;\"><b>N° Semanas:</b>" + programasteens.getNumeroSemanas() + "</h:panelGroup> <br/><br/>"+
				"<h:panelGroup style=\"margin:1%;\">"+
					"<b>Data Inicio: </b>"+ Formatacao.ConvercaoDataPadrao(programasteens.getDataInicioCurso()) + 
				"</h:panelGroup>"+
				"<h:panelGroup>" +
					"<b>Data Término: </b>" + Formatacao.ConvercaoDataPadrao(programasteens.getDataTerminoCurso()) +
				"</h:panelGroup> "+
				"<h:panelGroup><b>Acomodação: </b>" +  programasteens.getTipoAcomodacao() + "</h:panelGroup> <br/><br/>"+
				"<h:panelGroup style=\"margin:1%;\"><b>Data Chegada: </b>"+ Formatacao.ConvercaoDataPadrao(programasteens.getDataChegadaAcomodacao()) + 
				"</h:panelGroup>" +
				"<h:panelGroup><b>Data Partida: </b>" + Formatacao.ConvercaoDataPadrao(programasteens.getDataPartidaAcomodacao()) +
				"</h:panelGroup> " +
				
			"</h:panelGrid>"
		+"</div><br/>";
		return dados;
	}

}
