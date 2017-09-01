package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.TreinamentoAcessoDao; 
import br.com.travelmate.model.Treinamentoacesso;

public class TreinamentoAcessoFacade {

	TreinamentoAcessoDao treinamentoAcessoDao;
	
	public Treinamentoacesso salvar(Treinamentoacesso treinamentoacesso) {
		treinamentoAcessoDao = new TreinamentoAcessoDao();
        try {
            return treinamentoAcessoDao.salvar(treinamentoacesso);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoAcessoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Treinamentoacesso> listar(String sql){
    	treinamentoAcessoDao = new TreinamentoAcessoDao();
        try {
            return treinamentoAcessoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoAcessoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Treinamentoacesso consultar(String sql) {
    	treinamentoAcessoDao = new TreinamentoAcessoDao();
    	try {
			return treinamentoAcessoDao.consultar(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
			return null;
		}  
    }
     
}
