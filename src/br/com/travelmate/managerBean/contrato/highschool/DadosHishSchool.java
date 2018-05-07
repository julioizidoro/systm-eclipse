package br.com.travelmate.managerBean.contrato.highschool;

import br.com.travelmate.model.Highschool;

public class DadosHishSchool {

	
private Highschool highschool;
	
	
	public DadosHishSchool(Highschool highschool) {
		this.highschool = highschool;
	}
	
	
	public String dadosCurso(){
		String dados = "<div style=\"border: 2px solid;width:100%;border-color:#9C9C9C;\">"+
			"<p style=\"margin: 1%;\">"+
				"<b style=\"color:green;\">1. OBJETO</b> <br /> O presente contrato tem como objeto a intermediação da prestação de serviços educacionais de intercâmbio cultural pela\n" + 
				"TRAVELMATE  (intermediadora) e seu parceiro internacional,"+ highschool.getEscolaIntercambio() +
				", em favor do PARTICIPANTE\n" + 
				"visando sua inscrição no programa de intercâmbio High School,  com duração de " + highschool.getDuracaoIntercambio() +
				"letivo(s)/escolar(es), em " + highschool.getPaisIntercambio() + 
				" , na cidade de "+ highschool.getCidadeEscola() + 
				", com início previsto para "+ highschool.getValoreshighschool().getInicio() + " - " + highschool.getValoreshighschool().getAnoinicio() + 
				", incluindo todas as atividades descritas neste instrumento. " +
			"</p><br/>"
			+"</div><br/>";
		return dados;
	}
}
