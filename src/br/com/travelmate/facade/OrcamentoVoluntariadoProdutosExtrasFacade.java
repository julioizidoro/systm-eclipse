package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.OrcamentoVoluntariadoProdutosExtrasDao;
import br.com.travelmate.model.Orcamentovoluntariadoprodutosextras;

public class OrcamentoVoluntariadoProdutosExtrasFacade {
	
	OrcamentoVoluntariadoProdutosExtrasDao orcamentoVoluntariadoProdutosExtrasDao;
	
	public Orcamentovoluntariadoprodutosextras salvar(Orcamentovoluntariadoprodutosextras orcamentovoluntariadoprodutosextras) {
		orcamentoVoluntariadoProdutosExtrasDao = new OrcamentoVoluntariadoProdutosExtrasDao();
        try {
            return orcamentoVoluntariadoProdutosExtrasDao.salvar(orcamentovoluntariadoprodutosextras);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Orcamentovoluntariadoprodutosextras> listar(String sql){
    	orcamentoVoluntariadoProdutosExtrasDao = new OrcamentoVoluntariadoProdutosExtrasDao();
        try {
            return orcamentoVoluntariadoProdutosExtrasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idorcamento) {
    	orcamentoVoluntariadoProdutosExtrasDao = new OrcamentoVoluntariadoProdutosExtrasDao();
        try {
        	orcamentoVoluntariadoProdutosExtrasDao.excluir(idorcamento);
        } catch (SQLException ex) {
            Logger.getLogger(Arquivo1Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
