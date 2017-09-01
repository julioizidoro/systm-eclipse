/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PassagemDao;
import br.com.travelmate.model.Passagemaerea;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PassagemFacade {
    
    PassagemDao passagemDao;
    
    public Passagemaerea salvar(Passagemaerea passageAerea) {
        passagemDao = new PassagemDao();
        try {
            return passagemDao.salvar(passageAerea);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPassagem) {
        passagemDao = new PassagemDao();
        try {
            passagemDao.excluir(idPassagem);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Passagemaerea consultar(int idVenda) {
        passagemDao = new PassagemDao();
        try {
            return passagemDao.consultar(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Passagemaerea consultarPassagem(int idPassagem) {
        passagemDao = new PassagemDao();
        try {
            return passagemDao.consultarPassagem(idPassagem);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Passagemaerea> lista(String sql) {
    	passagemDao = new PassagemDao();
        try {
            return passagemDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
