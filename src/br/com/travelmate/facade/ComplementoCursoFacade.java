package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ComplementoAcomodacaoDao;
import br.com.travelmate.dao.ComplementoCursoDao; 
import br.com.travelmate.model.Complementocurso;


public class ComplementoCursoFacade {
	
private ComplementoCursoDao complementoCursoDao;
	
	public Complementocurso salvar(Complementocurso complemento) {
		complementoCursoDao = new ComplementoCursoDao();
		try {
			return complementoCursoDao.salvar(complemento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Complementocurso consultar(int idComplemento) {
		complementoCursoDao = new  ComplementoCursoDao();
		try {
			return complementoCursoDao.consultar(idComplemento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Complementocurso consultar(String sql) {
		complementoCursoDao = new ComplementoCursoDao();
        try {
            return complementoCursoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ComplementoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

	public void excluir(int id) {
	   complementoCursoDao = new ComplementoCursoDao();
       try {
    	   complementoCursoDao.excluir(id);
       } catch (SQLException ex) {
           Logger.getLogger(ComplementoCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
