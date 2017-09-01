/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FiltroOrcamentoProdutoDao;
import br.com.travelmate.dao.TipoAcomodacaoDao;
import br.com.travelmate.model.Tipoacomodacao;

/**
 *
 * @author Wolverine
 */
public class TipoAcomodacaoFacade {
    
    TipoAcomodacaoDao tipoAcomodacaoDao;
    
    public Tipoacomodacao salvar(Tipoacomodacao tipoacomodacao){
    	tipoAcomodacaoDao = new TipoAcomodacaoDao();
        try {
            return tipoAcomodacaoDao.salvar(tipoacomodacao);
        } catch (SQLException ex) {
            Logger.getLogger(TipoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public List<Tipoacomodacao> listar(String sql) {
    	tipoAcomodacaoDao = new TipoAcomodacaoDao();
        try {
            return tipoAcomodacaoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TipoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     
    
    public Tipoacomodacao consultar(String sql) {
    	tipoAcomodacaoDao = new TipoAcomodacaoDao();
        try {
            return tipoAcomodacaoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TipoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int id) {
    	tipoAcomodacaoDao = new TipoAcomodacaoDao();
        try {
        	tipoAcomodacaoDao.excluir(id);
        } catch (SQLException ex) {
            Logger.getLogger(TipoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
