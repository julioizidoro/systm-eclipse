package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.OrcamentoVoluntariadoDescontoDao;
import br.com.travelmate.model.Orcamentovoluntariadodesconto;

public class OrcamentoVoluntariadoDescontoFacade {
	
	OrcamentoVoluntariadoDescontoDao orcamentoVoluntariadoDescontoDao;
	
	public Orcamentovoluntariadodesconto salvar(Orcamentovoluntariadodesconto orcamentovoluntariadodesconto) {
		orcamentoVoluntariadoDescontoDao = new OrcamentoVoluntariadoDescontoDao();
        try {
            return orcamentoVoluntariadoDescontoDao.salvar(orcamentovoluntariadodesconto);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Orcamentovoluntariadodesconto> listar(String sql){
    	orcamentoVoluntariadoDescontoDao = new OrcamentoVoluntariadoDescontoDao();
        try {
            return orcamentoVoluntariadoDescontoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idorcamento) {
    	orcamentoVoluntariadoDescontoDao = new OrcamentoVoluntariadoDescontoDao();
        try {
        	orcamentoVoluntariadoDescontoDao.excluir(idorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
