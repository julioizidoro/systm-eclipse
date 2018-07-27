package br.com.travelmate.bean;

import java.util.Date;

import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadsituacao;
import br.com.travelmate.util.Formatacao;

public class LeadSituacaoBean {

	private Leadsituacao leadsituacao;
	private Lead lead;
	
	
	public LeadSituacaoBean(Lead lead, Integer situacaoAnterior, Integer situacaoAtual, LeadSituacaoDao leadSituacaoDao) {
		leadsituacao = new Leadsituacao();
		this.lead = lead;
		this.leadsituacao.setSituacaoanterior(situacaoAnterior);
		this.leadsituacao.setSituacaoatual(situacaoAtual);
		salvarLeadSituacao(leadSituacaoDao);
	}
	
	
	private void salvarLeadSituacao(LeadSituacaoDao leadSituacaoDao){
		leadsituacao.setData(new Date());
		leadsituacao.setLead(lead);
		leadsituacao.setHora(Formatacao.foramtarHoraString());
		leadSituacaoDao.salvar(leadsituacao);
	}

}
