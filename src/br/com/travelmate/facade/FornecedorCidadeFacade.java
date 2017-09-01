/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FornecedorCidadeDao;
import br.com.travelmate.model.Fornecedorcidade;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class FornecedorCidadeFacade {
    
    FornecedorCidadeDao fornecedorCidadeDao;
    
    public Fornecedorcidade salvar(Fornecedorcidade fornecedorcidade) {
        fornecedorCidadeDao = new FornecedorCidadeDao();
        try {
            return fornecedorCidadeDao.salvar(fornecedorcidade);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorcidade> listar(String sql) {
        fornecedorCidadeDao = new FornecedorCidadeDao();
        try {
            return fornecedorCidadeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorcidade getFonecedorCidade(int idFornecedor, int idCidade){
        fornecedorCidadeDao = new FornecedorCidadeDao();
        try {
            return fornecedorCidadeDao.getFonecedorCidade(idFornecedor, idCidade);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorcidade getFornecedorCidade(int idFornecedorCidade) {
        fornecedorCidadeDao = new FornecedorCidadeDao();
        try {
            return fornecedorCidadeDao.getFornecedorCidade(idFornecedorCidade);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Fornecedorcidade getFonecedorCidade(int idCodigo) {
        fornecedorCidadeDao = new FornecedorCidadeDao();
        try {
            return fornecedorCidadeDao.getFornecedorCidade(1);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
