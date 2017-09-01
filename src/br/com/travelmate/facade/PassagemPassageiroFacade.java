package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.PassagemPassageirosDao;
import br.com.travelmate.model.Passagempassageiro;

public class PassagemPassageiroFacade {
	
	PassagemPassageirosDao passagemPassageirosDao;
	
	public Passagempassageiro salvar(Passagempassageiro passagempassageiro) {
		passagemPassageirosDao = new PassagemPassageirosDao();
        try {
            return passagemPassageirosDao.salvar(passagempassageiro);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemPassageiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPassageiro) {
    	passagemPassageirosDao = new PassagemPassageirosDao();
        try {
        	passagemPassageirosDao.excluir(idPassageiro);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemPassageiroFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Passagempassageiro> lista(String sql) {
    	passagemPassageirosDao = new PassagemPassageirosDao();
        try {
            return passagemPassageirosDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemPassageiroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
