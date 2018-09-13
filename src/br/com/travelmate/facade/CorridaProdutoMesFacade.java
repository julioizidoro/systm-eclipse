package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CorridaProdutoMesDao;
import br.com.travelmate.model.Corridaprodutomes;

public class CorridaProdutoMesFacade {

	CorridaProdutoMesDao corridaProdutoMesDao;
	
	 public Corridaprodutomes salvar(Corridaprodutomes corridaprodutomes) {
		 corridaProdutoMesDao = new CorridaProdutoMesDao();
	        try {
	            return corridaProdutoMesDao.salvar(corridaprodutomes);
	        } catch (SQLException ex) {
	            Logger.getLogger(CorridaProdutoMesFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Corridaprodutomes> listar(String sql){
	    	corridaProdutoMesDao = new CorridaProdutoMesDao();
	        try {
				return corridaProdutoMesDao.listar(sql);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return null;
	    }
	   
	    public Corridaprodutomes consultar(int idcorrida) throws SQLException{
	    	corridaProdutoMesDao = new CorridaProdutoMesDao();
	        return corridaProdutoMesDao.consultar(idcorrida);
	    }
	    
	    public void excluir(int idArquivos) {
	    	corridaProdutoMesDao = new CorridaProdutoMesDao();
	        try {
	        	corridaProdutoMesDao.excluir(idArquivos);
	        } catch (SQLException ex) {
	            Logger.getLogger(CorridaProdutoMesFacade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    
	    
	    public Corridaprodutomes consultar(String sql) {
	    	corridaProdutoMesDao = new CorridaProdutoMesDao();
	        try {
	            return corridaProdutoMesDao.consultar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(CorridaProdutoMesFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
}
