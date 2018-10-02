package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.TreinamentoRespostasCertaDao; 
import br.com.travelmate.model.Treinamentorespostacerta;

public class TreinamentoRespostaCertaFacade {

	TreinamentoRespostasCertaDao treinamentoRespostasCertaDao;
	
	public Treinamentorespostacerta salvar(Treinamentorespostacerta treinamentorespostacerta) {
		treinamentoRespostasCertaDao = new TreinamentoRespostasCertaDao();
        try {
            return treinamentoRespostasCertaDao.salvar(treinamentorespostacerta);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoRespostaCertaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Treinamentorespostacerta> listar(String sql){
    	treinamentoRespostasCertaDao = new TreinamentoRespostasCertaDao();
        try {
            return treinamentoRespostasCertaDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoRespostaCertaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Treinamentorespostacerta consultar(String sql) {
    	treinamentoRespostasCertaDao = new TreinamentoRespostasCertaDao();
    	try {
			return treinamentoRespostasCertaDao.consultar(sql);
		} catch (SQLException e) { 
			  
			return null;
		}  
    }
     
}
