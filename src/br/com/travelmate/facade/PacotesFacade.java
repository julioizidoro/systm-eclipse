/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.PacotesDao;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacotetrecho;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PacotesFacade {
    
    PacotesDao pacotesDao;
    
    public Pacotes salvar(Pacotes pacote) {
        pacotesDao = new PacotesDao();
        try {
            return pacotesDao.salvar(pacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPacote) {
        pacotesDao = new PacotesDao();
        try {
            pacotesDao.excluir(idPacote);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Pacotes consultar(int idVenda)  {
        pacotesDao = new PacotesDao();
        try {
            return pacotesDao.consultar(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Pacotes> consultar(String sql) {
        pacotesDao = new PacotesDao();
        try {
            return pacotesDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PacotesFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
//    public Controlepacotes salvar(Controlepacotes controle) throws SQLException{
//        pacotesDao = new PacotesDao();
//        return pacotesDao.salvar(controle);
//    }
//    
//    public void excluirControlePacotes(Controlepacotes controle) throws SQLException{
//        pacotesDao = new PacotesDao();
//        pacotesDao.excluirControlePacotes(controle);
//    }
}
