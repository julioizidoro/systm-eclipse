package br.com.travelmate.bean;

import java.util.Date;

import br.com.travelmate.facade.LeadSituacaoFacade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadsituacao;
import br.com.travelmate.util.Formatacao;

public class LeadSituacaoBean {
	
	private Leadsituacao leadsituacao;
	private Lead lead;
	
	
	public LeadSituacaoBean(Lead lead, Integer situacaoAnterior, Integer situacaoAtual) {
		leadsituacao = new Leadsituacao();
		this.lead = lead;
		this.leadsituacao.setSituacaoanterior(situacaoAnterior);
		this.leadsituacao.setSituacaoatual(situacaoAtual);
		salvarLeadSituacao();
	}
	
	
	private void salvarLeadSituacao(){
		LeadSituacaoFacade leadSituacaoFacade = new LeadSituacaoFacade();
		leadsituacao.setData(new Date());
		leadsituacao.setLead(lead);
		leadsituacao.setHora(Formatacao.foramtarHoraString());
		leadSituacaoFacade.salvar(leadsituacao);
	}

}
