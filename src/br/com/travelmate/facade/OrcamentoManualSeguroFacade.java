package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.OrcamentoManualSeguroDao;
import br.com.travelmate.model.Orcamentomanualseguro;

public class OrcamentoManualSeguroFacade {
	
	private OrcamentoManualSeguroDao orcamentoManualSeguroDao;
	
	public Orcamentomanualseguro salvar(Orcamentomanualseguro  orcamentomanualseguro) {
		orcamentoManualSeguroDao = new OrcamentoManualSeguroDao();
		try {
			return orcamentoManualSeguroDao.salvar(orcamentomanualseguro);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Orcamentomanualseguro consultar(int idOrcamento) {
		orcamentoManualSeguroDao = new OrcamentoManualSeguroDao();
		try {
			return orcamentoManualSeguroDao.consultar(idOrcamento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
