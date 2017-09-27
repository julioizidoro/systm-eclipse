/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
 
import br.com.travelmate.dao.PaisProdutoDao; 
import br.com.travelmate.model.Paisproduto;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class PaisProdutoFacade {
    
    PaisProdutoDao paisProdutoDao;
    
    public Paisproduto salvar(Paisproduto paisProduto) {
        paisProdutoDao = new PaisProdutoDao();
        try {
            return paisProdutoDao.salvar(paisProduto);
                    } catch (SQLException ex) {
            Logger.getLogger(PaisProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Paisproduto> listar(int idProduto) {
        paisProdutoDao = new PaisProdutoDao();
        try {
            return paisProdutoDao.listar(idProduto);
        } catch (SQLException ex) {
            Logger.getLogger(PaisProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Paisproduto> listar(String sql) {
        paisProdutoDao = new PaisProdutoDao();
        try {
            return paisProdutoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PaisProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Paisproduto> listar() {
        paisProdutoDao = new PaisProdutoDao();
        try {
            return paisProdutoDao.listar();
        } catch (SQLException ex) {
            Logger.getLogger(PaisProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Paisproduto consultar(String sql) {
    	paisProdutoDao = new PaisProdutoDao();
        try {
            return paisProdutoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PaisProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
