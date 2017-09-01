package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.GrupoObrigatorioDao;
import br.com.travelmate.model.Grupoobrigatorio;

public class GrupoObrigatorioFacade {
	
	 GrupoObrigatorioDao grupoObrigatorioDao;
	    
	    public Grupoobrigatorio salvar(Grupoobrigatorio grupoobrigatorio) {
	    	grupoObrigatorioDao = new GrupoObrigatorioDao();
	        try {
	            return grupoObrigatorioDao.salvar(grupoobrigatorio);
	        } catch (SQLException ex) {
	            Logger.getLogger(GrupoObrigatorioFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Grupoobrigatorio> listar(String sql) {
	    	grupoObrigatorioDao = new GrupoObrigatorioDao();
	        try {
	            return grupoObrigatorioDao.listar(sql);
	        } catch (SQLException ex) {
	            Logger.getLogger(GrupoObrigatorioFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public Grupoobrigatorio consultar(int idcoprodutos) {
	    	grupoObrigatorioDao = new GrupoObrigatorioDao();
	        try {
	            return grupoObrigatorioDao.consultar(idcoprodutos);
	        } catch (SQLException ex) {
	            Logger.getLogger(GrupoObrigatorioFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public void excluir(Grupoobrigatorio grupoobrigatorio){
	    	grupoObrigatorioDao = new GrupoObrigatorioDao();
	        try {
	            grupoObrigatorioDao.excluir(grupoobrigatorio);
	        } catch (SQLException ex) {
	            Logger.getLogger(GrupoObrigatorioFacade.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    
	    public void excluir(int idgrupo){
	    	grupoObrigatorioDao = new GrupoObrigatorioDao();
	    	try {
				grupoObrigatorioDao.excluir(idgrupo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    
	    public Grupoobrigatorio consultarProduto(int idcoprodutos) {
	    	grupoObrigatorioDao = new GrupoObrigatorioDao();
	        try {
	            return grupoObrigatorioDao.consultarProduto(idcoprodutos);
	        } catch (SQLException ex) {
	            Logger.getLogger(GrupoObrigatorioFacade.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    
}
