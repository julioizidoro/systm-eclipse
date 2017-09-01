package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.PacotePassagemPassageiroDao;
import br.com.travelmate.model.Pacotepassagempassageiro;

public class PacotePassagemPassageiroFacade {
	
	PacotePassagemPassageiroDao pacotepassagemPassageirosDao;
	
	public Pacotepassagempassageiro salvar(Pacotepassagempassageiro passagempassageiro) {
		pacotepassagemPassageirosDao = new PacotePassagemPassageiroDao();
        try {
            return pacotepassagemPassageirosDao.salvar(passagempassageiro);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemPassageiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPassageiro) {
    	pacotepassagemPassageirosDao = new PacotePassagemPassageiroDao();
        try {
        	pacotepassagemPassageirosDao.excluir(idPassageiro);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemPassageiroFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Pacotepassagempassageiro> lista(String sql) {
    	pacotepassagemPassageirosDao = new PacotePassagemPassageiroDao();
        try {
            return pacotepassagemPassageirosDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemPassageiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
