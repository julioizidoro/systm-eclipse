package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.GrupoObrigatorioDao; 
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Ocursoseguro;

public class OcursoSeguroViagemFacade {

	private OcursoSeguroViagemDao ocursoSeguroViagemDao;
    
    public Ocursoseguro salvar(Ocursoseguro  ocursoseguro) throws SQLException{
    	ocursoSeguroViagemDao = new OcursoSeguroViagemDao();
        return ocursoSeguroViagemDao.salvar(ocursoseguro);
    }
    
    public Ocursoseguro consultar(int idOcurso) {
    	ocursoSeguroViagemDao = new OcursoSeguroViagemDao();
        try {
            return ocursoSeguroViagemDao.consultar(idOcurso);
        } catch (SQLException ex) {
            Logger.getLogger(OcursoSeguroViagemFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idOcursoseguro) {  
    	ocursoSeguroViagemDao = new OcursoSeguroViagemDao();
        try {
        	ocursoSeguroViagemDao.excluir(idOcursoseguro);
        } catch (SQLException ex) {
            Logger.getLogger(OcursoSeguroViagemFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
