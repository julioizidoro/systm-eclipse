/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.facade;

import br.com.travelmate.dao.ContasReceberDao;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Eventocontasreceber;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class ContasReceberFacade {
    
    ContasReceberDao contasReceberDao;
    
    public Contasreceber salvar(Contasreceber conta) {
        contasReceberDao = new ContasReceberDao();
        try {
            return contasReceberDao.salvar(conta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Contasreceber consultar(int idConta) {
        contasReceberDao = new ContasReceberDao();
        try {
            return contasReceberDao.consultar(idConta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idConta) {
        contasReceberDao = new ContasReceberDao();
        try {
            contasReceberDao.excluir(idConta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluirEventosContasReceber(int idConta) {
        contasReceberDao = new ContasReceberDao();
        try {
            contasReceberDao.excluirEventoContasReceber(idConta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Contasreceber> listar(String sql){
        contasReceberDao = new ContasReceberDao();
        try {
            return contasReceberDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<Eventocontasreceber> listarEventosContasReceber(String sql){
        contasReceberDao = new ContasReceberDao();
        try {
            return contasReceberDao.listarEventosContasReceber(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Contasreceber consultar(String sql){
        contasReceberDao = new ContasReceberDao();
        try {
            return contasReceberDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
