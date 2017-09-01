package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.TreinamentoRespostaUsuarioDao; 
import br.com.travelmate.model.Treinamentorespostausuario;

public class TreinamentoRespostaUsuarioFacade {

	TreinamentoRespostaUsuarioDao treinamentoRespostaUsuarioDao;
	
	public Treinamentorespostausuario salvar(Treinamentorespostausuario treinamentorespostausuario) {
		treinamentoRespostaUsuarioDao = new TreinamentoRespostaUsuarioDao();
        try {
            return treinamentoRespostaUsuarioDao.salvar(treinamentorespostausuario);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoRespostaUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Treinamentorespostausuario> listar(String sql){
    	treinamentoRespostaUsuarioDao = new TreinamentoRespostaUsuarioDao();
        try {
            return treinamentoRespostaUsuarioDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TreinamentoRespostaUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Treinamentorespostausuario consultar(String sql) {
    	treinamentoRespostaUsuarioDao = new TreinamentoRespostaUsuarioDao();
    	try {
			return treinamentoRespostaUsuarioDao.consultar(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
			return null;
		}  
    }
     
}
