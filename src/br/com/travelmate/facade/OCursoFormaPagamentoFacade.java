/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.OCursoFormaPagamentoDao;
import br.com.travelmate.model.Ocursoformapagamento;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Wolverine
 */
public class OCursoFormaPagamentoFacade {
    
    private OCursoFormaPagamentoDao oCursoFormaPagamentoDao;
    
    public Ocursoformapagamento salvar(Ocursoformapagamento formaPagamento) throws SQLException{
        oCursoFormaPagamentoDao = new OCursoFormaPagamentoDao();
        return oCursoFormaPagamentoDao.salvar(formaPagamento);
    }
    
    public void excluir(int idCurso) {
    	oCursoFormaPagamentoDao = new OCursoFormaPagamentoDao();
        try {
        	oCursoFormaPagamentoDao.excluir(idCurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoFormaPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Fomra de pagamento " + ex);
        }
    }
    
    public List<Ocursoformapagamento> listar(int idOcurso) {
    	oCursoFormaPagamentoDao = new OCursoFormaPagamentoDao();
        try {
            return oCursoFormaPagamentoDao.lista(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoFormaPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Ocursoformapagamento consultar(int idOcurso) {
    	oCursoFormaPagamentoDao = new OCursoFormaPagamentoDao();
        try {
             return oCursoFormaPagamentoDao.consultar(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoFormaPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
