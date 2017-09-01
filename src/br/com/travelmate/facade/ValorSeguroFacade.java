package br.com.travelmate.facade;

import br.com.travelmate.dao.ValorSeguroDao;
import br.com.travelmate.model.Valoresseguro;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ValorSeguroFacade {
    private ValorSeguroDao valorSeguroDao;

    
    public Valoresseguro salvar(Valoresseguro valor) {
    	valorSeguroDao = new ValorSeguroDao();
        try {
            return valorSeguroDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValorSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valoresseguro consultar(int idvalor)  {
    	valorSeguroDao = new ValorSeguroDao();
        try {
            return valorSeguroDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValorSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Valoresseguro consultar(String sql)  {
    	valorSeguroDao = new ValorSeguroDao();
        try {
            return valorSeguroDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValorSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoresseguro> listar(String sql) {
    	valorSeguroDao = new ValorSeguroDao();
        try {
            return valorSeguroDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValorSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valoresseguro> listar(int idFornecedor, String situacao){
    	valorSeguroDao = new ValorSeguroDao();
        try {
            return valorSeguroDao.listar(idFornecedor, situacao);
        } catch (SQLException ex) {
            Logger.getLogger(ValorSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
