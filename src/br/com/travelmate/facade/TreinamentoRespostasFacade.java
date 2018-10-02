package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
  
import br.com.travelmate.dao.TreinamentoRespostasDao; 
import br.com.travelmate.model.Treinamentorespostas; 

public class TreinamentoRespostasFacade {

	TreinamentoRespostasDao treinamentoRespostasDao;
	
	public Treinamentorespostas salvar(Treinamentorespostas treinamentorespostas) {
		treinamentoRespostasDao = new TreinamentoRespostasDao();
        try {
            return treinamentoRespostasDao.salvar(treinamentorespostas);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoRespostasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Treinamentorespostas> listar(String sql){
    	treinamentoRespostasDao = new TreinamentoRespostasDao();
        try {
            return treinamentoRespostasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoRespostasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Treinamentorespostas consultar(String sql) {
    	treinamentoRespostasDao = new TreinamentoRespostasDao();
    	try {
			return treinamentoRespostasDao.consultar(sql);
		} catch (SQLException e) { 
			  
			return null;
		}  
    }
     
}
