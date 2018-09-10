package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ProdutosOrcamentoGrupoDao;
import br.com.travelmate.model.Produtosorcamentogrupo;

public class ProdutoOrcamentoGrupoFacade {
	
	ProdutosOrcamentoGrupoDao prOrcamentoGrupoDao;
	
	public Produtosorcamentogrupo salvar(Produtosorcamentogrupo produtosorcamentogrupo) {
		prOrcamentoGrupoDao = new ProdutosOrcamentoGrupoDao();
        try {
            return prOrcamentoGrupoDao.salvar(produtosorcamentogrupo);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoGrupoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Produtosorcamentogrupo> listar(String sql){
    	prOrcamentoGrupoDao = new ProdutosOrcamentoGrupoDao();
        try {
            return prOrcamentoGrupoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoGrupoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idprOrcamentoGrupo) {
    	prOrcamentoGrupoDao = new ProdutosOrcamentoGrupoDao();
        try {
        	prOrcamentoGrupoDao.excluir(idprOrcamentoGrupo);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoOrcamentoGrupoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
