package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.OrcamentoVoluntariadoFormaPagamentoDao;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;

public class OrcamentoVoluntariadoFormaPagamentoFacade {

	OrcamentoVoluntariadoFormaPagamentoDao orcamentoVoluntariadoFormaPagamentoDao;
	
	public Orcamentovoluntariadoformapagamento salvar(Orcamentovoluntariadoformapagamento orcamentovoluntariadoformapagamento) {
		orcamentoVoluntariadoFormaPagamentoDao = new OrcamentoVoluntariadoFormaPagamentoDao();
        try {
            return orcamentoVoluntariadoFormaPagamentoDao.salvar(orcamentovoluntariadoformapagamento);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Orcamentovoluntariadoformapagamento> listar(String sql){
    	orcamentoVoluntariadoFormaPagamentoDao = new OrcamentoVoluntariadoFormaPagamentoDao();
        try {
            return orcamentoVoluntariadoFormaPagamentoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idorcamento) {
    	orcamentoVoluntariadoFormaPagamentoDao = new OrcamentoVoluntariadoFormaPagamentoDao();
        try {
        	orcamentoVoluntariadoFormaPagamentoDao.excluir(idorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
