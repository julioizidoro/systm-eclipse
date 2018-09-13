package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CorridaProdutoAnoDao;
import br.com.travelmate.model.Corridaprodutoano;

public class CorridaProdutoAnoFacade {

	CorridaProdutoAnoDao corridaProdutoAnoDao;
	
	public Corridaprodutoano salvar(Corridaprodutoano corridaprodutomes) {
		corridaProdutoAnoDao = new CorridaProdutoAnoDao();
	        try {
	            return corridaProdutoAnoDao.salvar(corridaprodutomes);
	        } catch (SQLException ex) {
	            Logger.getLogger(CorridaProdutoAnoFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Corridaprodutoano> listar(String sql) {
	    	corridaProdutoAnoDao = new CorridaProdutoAnoDao();
	        try {
				return corridaProdutoAnoDao.listar(sql);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return null;
	    }
	   
	    public Corridaprodutoano consultar(int idcorrida) throws SQLException{
	    	corridaProdutoAnoDao = new CorridaProdutoAnoDao();
	        return corridaProdutoAnoDao.consultar(idcorrida);
	    }
	    
	    public void excluir(int idArquivos) {
	    	corridaProdutoAnoDao = new CorridaProdutoAnoDao();
	        try {
	        	corridaProdutoAnoDao.excluir(idArquivos);
	        } catch (SQLException ex) {
	            Logger.getLogger(CorridaProdutoAnoFacade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    
	    
	    public Corridaprodutoano consultar(String sql) {
	    	corridaProdutoAnoDao = new CorridaProdutoAnoDao();
	        try {
	            return corridaProdutoAnoDao.consultar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(CorridaProdutoAnoFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
}
