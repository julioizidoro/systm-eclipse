package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.Arquivo1Dao;
import br.com.travelmate.dao.OrcamentoProjetoVoluntariadoDao;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;

public class OrcamentoProjetoVoluntariadoFacade {

	OrcamentoProjetoVoluntariadoDao orcamentoProjetoVoluntariadoDao;
	
	public Orcamentoprojetovoluntariado salvar(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
		orcamentoProjetoVoluntariadoDao = new OrcamentoProjetoVoluntariadoDao();
        try {
            return orcamentoProjetoVoluntariadoDao.salvar(orcamentoprojetovoluntariado);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Orcamentoprojetovoluntariado> listar(String sql){
    	orcamentoProjetoVoluntariadoDao = new OrcamentoProjetoVoluntariadoDao();
        try {
            return orcamentoProjetoVoluntariadoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idorcamento) {
    	orcamentoProjetoVoluntariadoDao = new OrcamentoProjetoVoluntariadoDao();
        try {
        	orcamentoProjetoVoluntariadoDao.excluir(idorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
