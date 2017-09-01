package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.OcursoFeriadoDao; 
import br.com.travelmate.model.Ocursoferiado;

public class OcursoFeriadoFacade {
	
	private OcursoFeriadoDao ocrusoFeriadoDao;
	
	public Ocursoferiado salvar(Ocursoferiado  ocursoferiado) {
		ocrusoFeriadoDao = new OcursoFeriadoDao();
		try {
			return ocrusoFeriadoDao.salvar(ocursoferiado);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Ocursoferiado> listar(String sql){
		ocrusoFeriadoDao = new OcursoFeriadoDao();
		try {
			return ocrusoFeriadoDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void excluir(int id) {
		ocrusoFeriadoDao = new OcursoFeriadoDao();
		try {
			ocrusoFeriadoDao.excluir(id);
		} catch (SQLException ex) {
			Logger.getLogger(OcursoFeriadoFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	

}
