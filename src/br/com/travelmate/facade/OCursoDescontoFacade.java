package br.com.travelmate.facade;

import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.model.Ocursodesconto;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OCursoDescontoFacade {
    
    OCursoDescontoDao cursoDescontoDao;
    
    public Ocursodesconto salvar(Ocursodesconto ocursodesconto) {
        cursoDescontoDao = new OCursoDescontoDao();
        try {
            return cursoDescontoDao.salvar(ocursodesconto);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoDescontoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Ocursodesconto> listar(String sql){
        cursoDescontoDao = new OCursoDescontoDao();
        try {
            return cursoDescontoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoDescontoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Ocursodesconto> listar(int idOcurso) {
    	cursoDescontoDao = new OCursoDescontoDao();
        try {
            return cursoDescontoDao.lista(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoDescontoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idOcursodesconto) {  
    	cursoDescontoDao = new OCursoDescontoDao();
        try {
        	cursoDescontoDao.excluir(idOcursodesconto);
        } catch (SQLException ex) {
            Logger.getLogger(OCursoDescontoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
