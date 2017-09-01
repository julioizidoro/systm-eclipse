/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.ParcelamentoPagamentoDao;
import br.com.travelmate.model.Parcelamentopagamento;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila
 */
public class ParcelamentoPagamentoFacade {
    
    private ParcelamentoPagamentoDao parcelamentoPagamentoDao;
    
     public Parcelamentopagamento salvar(Parcelamentopagamento parcelamentopagamento) {
        parcelamentoPagamentoDao = new ParcelamentoPagamentoDao();
        try {
            return parcelamentoPagamentoDao.salvar(parcelamentopagamento);
        } catch (SQLException ex) {
            Logger.getLogger(ParcelamentoPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idPassagem) {
        parcelamentoPagamentoDao = new ParcelamentoPagamentoDao();
        try {
            parcelamentoPagamentoDao.excluir(idPassagem);
        } catch (SQLException ex) {
            Logger.getLogger(ParcelamentoPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Parcelamentopagamento> listar(int idFormaPagamento) {
       parcelamentoPagamentoDao = new ParcelamentoPagamentoDao();
        try {
            return parcelamentoPagamentoDao.listar(idFormaPagamento);
        } catch (SQLException ex) {
            Logger.getLogger(ParcelamentoPagamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
