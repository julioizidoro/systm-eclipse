package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
  
import br.com.travelmate.dao.TreinamentoPerguntasDao; 
import br.com.travelmate.model.Treinamentoperguntas; 

public class TreinamentoPerguntasFacade {

	TreinamentoPerguntasDao treinamentoPerguntasDao;
	
	public Treinamentoperguntas salvar(Treinamentoperguntas treinamentoperguntas) {
		treinamentoPerguntasDao = new TreinamentoPerguntasDao();
        try {
            return treinamentoPerguntasDao.salvar(treinamentoperguntas);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoPerguntasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Treinamentoperguntas> listar(String sql){
    	treinamentoPerguntasDao = new TreinamentoPerguntasDao();
        try {
            return treinamentoPerguntasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoPerguntasFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Treinamentoperguntas consultar(String sql) {
    	treinamentoPerguntasDao = new TreinamentoPerguntasDao();
    	try {
			return treinamentoPerguntasDao.consultar(sql);
		} catch (SQLException e) { 
			  
			return null;
		}  
    }
     
}
