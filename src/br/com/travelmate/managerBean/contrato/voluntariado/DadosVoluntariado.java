package br.com.travelmate.managerBean.contrato.voluntariado;

import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.util.Formatacao;

public class DadosVoluntariado {
	
private Voluntariado voluntariado;
	
	
	public DadosVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
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
				"<h:panelGroup><b>Curso 01:</b>"+ voluntariado.getCurso() + "</h:panelGroup>"+
				"<h:panelGroup style=\"margin-left:20%;\"><b>Escola/Instituição:</b>" + voluntariado.getProjetoVoluntariado() + "</h:panelGroup> <br/><br/>"+
				"<h:panelGroup></h:panelGroup>"+
				"<h:panelGroup style=\"margin:1%;\">"+
					"<b>Local:</b>" + voluntariado.getVendas().getFornecedorcidade().getCidade().getNome() + ", " + voluntariado.getVendas().getFornecedorcidade().getCidade().getPais().getNome() +
				"</h:panelGroup>" +
			"</h:panelGrid>"+

			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"3\">"+
				"<h:panelGroup style=\"margin-left:8%;\"></h:panelGroup>"+
				"<h:panelGroup style=\"margin:1%;\">"+
					"<b>Data Inicio: </b>"+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataInicio()) + 
				"</h:panelGroup>"+
				"<h:panelGroup style=\"margin-left:8%;\"></h:panelGroup> "+
				"<h:panelGroup>" +
					"<b>Data Término: </b>" + Formatacao.ConvercaoDataPadrao(voluntariado.getDataTermino()) +
				"</h:panelGroup> "+
				"<h:panelGroup><b>Acomodação: </b>" +  voluntariado.getTipoAcomodacao() + "</h:panelGroup> <br/><br/>"+
				"<h:panelGroup style=\"margin:1%;\"><b>Data Chegada: </b>"+ Formatacao.ConvercaoDataPadrao(voluntariado.getDataChegadaAcomodacao()) + 
				"</h:panelGroup>" +
				"<h:panelGroup><b>Data Partida: </b>" + Formatacao.ConvercaoDataPadrao(voluntariado.getDataPartidaAcomodacao()) +
				"</h:panelGroup><br/> " +
				"<h:panelGroup><b>Extras: </b>" + voluntariado.getAdicionais() + "</h:panelGroup>" +
			"</h:panelGrid>"
		+"</div><br/>";
		return dados;
	}

}
