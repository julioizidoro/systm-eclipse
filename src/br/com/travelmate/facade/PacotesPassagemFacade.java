/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.AupairDao;
import br.com.travelmate.dao.PacotesPassagemDao;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Pacotepassagem;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PacotesPassagemFacade {
    
    PacotesPassagemDao pacotesDao;
    
    public Pacotepassagem salvar(Pacotepassagem pacote) {
        pacotesDao = new PacotesPassagemDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesPassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote){
        pacotesDao = new PacotesPassagemDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesPassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotepassagem consultar(int idPacote)  {
        pacotesDao = new PacotesPassagemDao();
        try {
            return pacotesDao.consultar(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesPassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pacotepassagem> lista(String sql) {
    	pacotesDao = new PacotesPassagemDao();
        try {
            return pacotesDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesPassagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
