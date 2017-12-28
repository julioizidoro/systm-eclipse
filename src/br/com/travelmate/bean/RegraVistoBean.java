package br.com.travelmate.bean;

import java.util.Date;
import br.com.travelmate.facade.RegraVistoFacade;
import br.com.travelmate.model.Regravisto;
import br.com.travelmate.util.Formatacao;

public class RegraVistoBean {
	
	private String msg;
	

	public RegraVistoBean(Date dataInicio, int idPais, String tipoVisto) {
		
		ConsultarRegraVisto(dataInicio, idPais, tipoVisto);
	}
	
	
	
	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	private boolean ConsultarRegraVisto(Date dataInicio, int idPais, String tipoVisto){
		RegraVistoFacade regraVistoFacade = new RegraVistoFacade();
		String sql = "SELECT r FROM Regravisto r where r.pais.idpais=" + idPais +
				" and r.tipovisto='" + tipoVisto + "'";
		Regravisto regra = regraVistoFacade.consultar(sql);
		if (regra==null){
			msg ="ok";
			return true;
		}else{
			return calcularRegraVisto(dataInicio, regra);
		}
	}
		
	public boolean calcularRegraVisto(Date dataInicio, Regravisto regra){
		int dias = Formatacao.subtrairDatas(new Date(), dataInicio);
		if (dias<regra.getNumerodias()){
			msg = "Prazo insuficiente para aplicação do  visto. Necessário " + regra.getNumerodias() + " dias";
			return false;
		}else {
			msg = "ok";
			return true;
		}
	}
	
	

}
