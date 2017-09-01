package br.com.travelmate.facade;

import br.com.travelmate.dao.EscolaPadraoDao;
import br.com.travelmate.model.Escolapadrao;

public class EscolaPadraoFacade {
	
	private EscolaPadraoDao escolaPadraoDao;
	
	public Escolapadrao pesquisar(){
		escolaPadraoDao = new EscolaPadraoDao();
		return escolaPadraoDao.pesquisar();
	}

}
