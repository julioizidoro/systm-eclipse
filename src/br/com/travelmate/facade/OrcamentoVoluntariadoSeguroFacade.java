package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.OrcamentoVoluntariadoSeguroDao;
import br.com.travelmate.model.Orcamentovoluntariadoseguro;

public class OrcamentoVoluntariadoSeguroFacade {
	
	OrcamentoVoluntariadoSeguroDao orcamentoVoluntariadoSeguroDao;

	
	public Orcamentovoluntariadoseguro salvar(Orcamentovoluntariadoseguro orcamentovoluntariadoseguro) {
		orcamentoVoluntariadoSeguroDao = new OrcamentoVoluntariadoSeguroDao();
        try {
            return orcamentoVoluntariadoSeguroDao.salvar(orcamentovoluntariadoseguro);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Orcamentovoluntariadoseguro> listar(String sql){
    	orcamentoVoluntariadoSeguroDao = new OrcamentoVoluntariadoSeguroDao();
        try {
            return orcamentoVoluntariadoSeguroDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idorcamento) {
    	orcamentoVoluntariadoSeguroDao = new OrcamentoVoluntariadoSeguroDao();
        try {
        	orcamentoVoluntariadoSeguroDao.excluir(idorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
