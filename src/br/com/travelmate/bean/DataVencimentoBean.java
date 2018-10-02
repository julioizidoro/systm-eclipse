package br.com.travelmate.bean;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

public class DataVencimentoBean {
	
	private Date dataPagamento;
	
	
	public DataVencimentoBean(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
		String dataPagamentoString = Formatacao.ConvercaoDataPadrao(dataPagamento);
		this.dataPagamento = Formatacao.ConvercaoStringData(dataPagamentoString);
	}
	
	
	
	
	public Date validarDataVencimento() {
		boolean horarioExcedido = false;
		int numeroAdicionar = 0;
		int diaSemana = Formatacao.diaSemana(new Date());
		String horaAtual = Formatacao.foramtarHoraString();
		String horaMaxima = "16:00:00";
		Time horatime = null;
		Time horaMaxTime = null;
		try {
			horatime = Formatacao.converterStringHora(horaAtual);
			horaMaxTime = Formatacao.converterStringHora(horaMaxima);
		} catch (ParseException e) {
			  
		}
		String dataString = Formatacao.ConvercaoDataPadrao(new Date());
		Date dataHoje = Formatacao.ConvercaoStringData(dataString);
		int numeroDias = Formatacao.subtrairDatas(dataHoje, dataPagamento);
		try {
			if (horatime.after(horaMaxTime) && numeroDias == 0 && diaSemana == 6) {
				numeroAdicionar = 4;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (numeroDias == 0 && diaSemana == 6) {
				numeroAdicionar = 3;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (horatime.after(horaMaxTime) && numeroDias == 0 && diaSemana == 5) {
				numeroAdicionar = 4;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (numeroDias == 0 && diaSemana == 5) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (numeroDias == 0 && diaSemana == 7) {
				numeroAdicionar = 3;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (numeroDias == 0 && diaSemana == 1) {
				numeroAdicionar = 2;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}  else if (horatime.after(horaMaxTime) && numeroDias == 1 && diaSemana == 6) {
				numeroAdicionar = 3;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (horatime.after(horaMaxTime) && numeroDias == 1 && diaSemana == 5) {
				numeroAdicionar = 3;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (numeroDias == 1 && diaSemana == 7) {
				numeroAdicionar = 2;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (numeroDias == 1 && diaSemana == 1) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (horatime.after(horaMaxTime) && numeroDias == 2 && diaSemana == 6) {
				numeroAdicionar = 2;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (horatime.after(horaMaxTime) && numeroDias == 2 && diaSemana == 5) {
				numeroAdicionar = 2;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (numeroDias == 2 && diaSemana == 7) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (horatime.after(horaMaxTime) && numeroDias == 3 && diaSemana == 6) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (horatime.after(horaMaxTime) && numeroDias == 3 && diaSemana == 5) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			} else if (horatime.after(horaMaxTime) && numeroDias == 1) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (horatime.after(horaMaxTime) && numeroDias == 0) {
				numeroAdicionar = 2;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}else if (numeroDias == 0) {
				numeroAdicionar = 1;
				horarioExcedido = true;
				dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
			}
			if (!horarioExcedido) {
				int diaSemanaParcela = Formatacao.diaSemana(dataPagamento);
				if (diaSemanaParcela == 1) {
					numeroAdicionar = 1;
					horarioExcedido = true;
					dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
				} else if (diaSemanaParcela == 7) {
					numeroAdicionar = 2;
					horarioExcedido = true;
					dataPagamento = Formatacao.SomarDiasDatas(dataPagamento, numeroAdicionar);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (horarioExcedido) {
			Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
		}
		return dataPagamento;
	}

}
