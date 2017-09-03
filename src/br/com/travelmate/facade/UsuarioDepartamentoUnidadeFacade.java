package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.UsuarioDepartamentoUnidadeDao;
import br.com.travelmate.model.Usuariodepartamentounidade; 

public class UsuarioDepartamentoUnidadeFacade {

	UsuarioDepartamentoUnidadeDao usuarioDepartamentoUnidadeDao;
	
	public Usuariodepartamentounidade salvar(Usuariodepartamentounidade Usuariodepartamentounidade) {
		usuarioDepartamentoUnidadeDao = new UsuarioDepartamentoUnidadeDao();
        try {
            return usuarioDepartamentoUnidadeDao.salvar(Usuariodepartamentounidade);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDepartamentoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
	public Usuariodepartamentounidade consultar(String sql) {
		usuarioDepartamentoUnidadeDao = new UsuarioDepartamentoUnidadeDao();
    	try {
			return usuarioDepartamentoUnidadeDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
     
    public List<Usuariodepartamentounidade> listar(String sql){
    	usuarioDepartamentoUnidadeDao = new UsuarioDepartamentoUnidadeDao();
        try {
            return usuarioDepartamentoUnidadeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDepartamentoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idUsuariodepartamentounidade) {
    	usuarioDepartamentoUnidadeDao = new UsuarioDepartamentoUnidadeDao();
        try {
        	usuarioDepartamentoUnidadeDao.excluir(idUsuariodepartamentounidade);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDepartamentoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
