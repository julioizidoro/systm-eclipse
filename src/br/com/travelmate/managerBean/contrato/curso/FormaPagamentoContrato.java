package br.com.travelmate.managerBean.contrato.curso;

import java.util.Date;

import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

public class FormaPagamentoContrato {
	
	private Vendas vendas;
	private Date dataHoje;
	private String dataExtanso;
	private int diaSemana;
	
	public FormaPagamentoContrato(Vendas vendas) {
		this.vendas = vendas;
	}
	
	
	
	
	public String pegarFormaPagamento(){
		String dados = "<div align=\"left\">"+
				"<img src=\"http://systm.com.br:82/ftproot/systm/img/opcoesPagamento.PNG\" width=\"47%\" />"+
			"</div> <br/>";
		 dados = dados + "<div style=\"border: 2px solid;border-color:#9C9C9C;width:100%;\">"+ 
				"<div style=\"width:100%;background-color: #CFCFCF;\">"+
					"<table border=\"0\" style=\"width:100%;\">"+
						"<tr>"
							+ "<td>"+
								"<h:panelGroup style=\"margin-left:2%;\">VALOR TOTAL</h:panelGroup>" 
					   		+ "</td>"
					   		+ " <td style=\"float:right;width:65%;\"> "+
						   		"<h:panelGroup>"+
									vendas.getCambio().getMoedas().getSigla() + ": " + Formatacao.formatarFloatString(vendas.getFormapagamento().getValorOrcamento()/vendas.getValorcambio()) +
								"</h:panelGroup>"+
								"<h:panelGroup style=\"margin-left:20%;\">"+
									"R$: " +  Formatacao.formatarFloatString(vendas.getFormapagamento().getValorOrcamento()) +
								"</h:panelGroup>"
					   		+ "</td>"
					   	+ "</tr>"+
					"</table>"+
				"</div>"+
				"<hr style=\"margin:1%;\"/>"+
			"<br/>"+
			"<h:panelGrid style=\"width:100%;margin:1%;\" columns=\"1\">"+
				"<h:panelGroup>Moeda: " + vendas.getCambio().getMoedas().getSigla()  + "</h:panelGroup>" +
				" <h:panelGroup style=\"margin-left:5%;\"> | Câmbio do dia: "+ Formatacao.formatarFloatString(vendas.getCambio().getValor())  + "</h:panelGroup>" +
					 "<h:panelGroup style=\"margin-left:5%;\"> | Valor Juros: R$ "+ Formatacao.formatarFloatString(vendas.getFormapagamento().getValorJuros()) + "</h:panelGroup>" +
					"<h:panelGroup style=\"margin-left:5%;\"> | Valor Final: R$ " +  Formatacao.formatarFloatString(vendas.getFormapagamento().getValorTotal()) + "</h:panelGroup>" +
					
				"</h:panelGroup>" +  
		
			"</h:panelGrid>"+
			
				"<h:panelGroup>"+
					
				"</h:panelGroup>"+
		"<br /><br/>";
		for (int i = 0; i < vendas.getFormapagamento().getParcelamentopagamentoList().size(); i++) {
				dados = dados + "<h:panelGroup style=\"margin-left: 1%;\"> Forma de Pagamento: "+ vendas.getFormapagamento().getParcelamentopagamentoList()
						.get(i).getFormaPagamento() + " </h:panelGroup>"+
				"<hr style=\"margin:1%;\"/>" +
				"<table border=\"0\" style=\"width:100%;\">"+
					"<tr>"
						+ "<td>"+
							"<h:panelGroup style=\"margin-left:2%;\">Saldo Parcelar: R$ "+  Formatacao.formatarFloatString(vendas.getFormapagamento().getParcelamentopagamentoList()
									.get(i).getValorParcelamento()) +
									"</h:panelGroup>"
				   		+ "</td>"
				   		+ " <td > "+
						   		"<h:panelGroup >"+
									" Vencimento: " + Formatacao.ConvercaoDataPadrao(vendas.getFormapagamento().getParcelamentopagamentoList()
											.get(i).getDiaVencimento()) + 
								"</h:panelGroup>"
				   		+ "</td>"
				   		+ " <td > "+
						   		"<h:panelGroup >" +
									" Parcelas: "+ vendas.getFormapagamento().getParcelamentopagamentoList()
										.get(i).getNumeroParcelas() +
								"</h:panelGroup>"
						+ "</td>"
						+ " <td > "+
								"<h:panelGroup>"+
									" Valor: R$ " +  Formatacao.formatarFloatString(vendas.getFormapagamento().getParcelamentopagamentoList()
											.get(i).getValorParcela()) +
								"</h:panelGroup>"
						+ "</td>"
				   	+ "</tr>"+
				 "</table>"+
			"<br/><br/>";	
		}
		dados = dados + " </div><br/>";
		dados = pegarAssinatura(dados);
		return dados;
	}
	
	
	public String pegarAssinatura(String dados){
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + Formatacao.getMes() + " " + Formatacao.getAnoData(dataHoje); 
		dados = dados + "<div align=\"left\">"+
				"<img src=\"http://systm.com.br:82/ftproot/systm/img/assinaturaContrato.PNG\" width=\"40%\" />"+
			"</div> <br/>";
		dados = dados + "<div style=\"border: 2px solid;border-color:#9C9C9C;width:100%;\">"+ 
				"<p style=\"margin:1%;\"> Sendo que o(a) PARTICIPANTE e seus representantes legais"+
				"afirmam estarem cientes dos termos deste contrato, concordando com as"+
				"regras e condições para prestação do serviço tal como proposto pela"+
				"TRAVELMATE , celebram o presente contrato em 02 vias de igual teor e"+
				"forma e na presença de 2 (duas) testemunhas.As partes elegem o foro da Comarca de "+ 
				vendas.getUnidadenegocio().getCidade() +  " - " +
				vendas.getUnidadenegocio().getEstado() + " para " +
				"dirimir quaisquer problemas provenientes do presente contrato, " +
				"renunciando expressamente a qualquer outro por mais privilegiado que " +
				"seja. </p> </div><br/>" +
				"<div align=\"left\" style=\"color:green;\"><b>" + vendas.getUnidadenegocio().getCidade() + ", " + dataExtanso + " </b></div> "+
				"<br/><br/><br/><br/>"+
				"<hr width=\"80%\"  align=\"left\"/>"+
				"<p>Assinatura do(a) " +   vendas.getCliente().getNome() + " - CPF: " + vendas.getCliente().getCpf() + "</p>"+
				"<br/><br/><br/><br/>"+
				"<hr width=\"80%\"  align=\"left\"/>"+
				"<p>Assinatura do responsável (aquele que responde pelo participante no Brasil)</p>"+
				"<br/><br/><br/><br/>"+
				"<hr width=\"80%\"  align=\"left\"/>"+
				"<p>" + vendas.getUnidadenegocio().getRazaoSocial() + "<br/> CNPJ/MF n° " + vendas.getUnidadenegocio().getCnpj() + "</p>";
		return dados;
	}
	
	

}
