package br.com.travelmate.bean;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadsituacao;
import br.com.travelmate.util.Formatacao;

public class LeadSituacaoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadSituacaoDao leadSituacaoDao;
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
		leadsituacao.setData(new Date());
		leadsituacao.setLead(lead);
		leadsituacao.setHora(Formatacao.foramtarHoraString());
		leadSituacaoDao.salvar(leadsituacao);
	}

}
