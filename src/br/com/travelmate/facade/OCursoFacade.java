/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.OCursoDao;
import br.com.travelmate.model.Ocurso;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class OCursoFacade {
    
    private OCursoDao oCursoDao;
    
    public Ocurso salvar(Ocurso  ocurso) throws SQLException{
        oCursoDao = new OCursoDao();
        return oCursoDao.salvar(ocurso);
    }
    
    public List<Ocurso> listar(String sql){
        oCursoDao = new OCursoDao();
        try {
            return oCursoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Ocurso consultar(int idOcurso) {
    	oCursoDao = new OCursoDao();
        try {
            return oCursoDao.consultar(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
