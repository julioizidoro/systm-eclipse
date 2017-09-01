package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ProdutosOrcamentoIndiceDao;
import br.com.travelmate.model.Produtosorcamentoindice;

public class ProdutoOrcamentoIndiceFacade {
	
	ProdutosOrcamentoIndiceDao prOrcamentoIndiceDao;
	
	public Produtosorcamentoindice salvar(Produtosorcamentoindice produtosorcamentoindice) {
		prOrcamentoIndiceDao = new ProdutosOrcamentoIndiceDao();
        try {
            return prOrcamentoIndiceDao.salvar(produtosorcamentoindice);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoIndiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Produtosorcamentoindice> listar(String sql){
    	prOrcamentoIndiceDao = new ProdutosOrcamentoIndiceDao();
        try {
            return prOrcamentoIndiceDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoIndiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idprOrcamentoIndice) {
    	prOrcamentoIndiceDao = new ProdutosOrcamentoIndiceDao();
        try {
        	prOrcamentoIndiceDao.excluir(idprOrcamentoIndice);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoIndiceFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
