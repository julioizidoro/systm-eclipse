package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.TipoContatoDao; 
import br.com.travelmate.model.Tipocontato; 

public class TipoContatoFacade {

	TipoContatoDao tipoContatoDao;
	
	public List<Tipocontato> lista(String sql) {
		tipoContatoDao = new TipoContatoDao();
        try {
            return tipoContatoDao.lista(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(TipoContatoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Tipocontato consultar(int idTipocontato) {
    	tipoContatoDao = new TipoContatoDao();
        try {
            return tipoContatoDao.consultar(idTipocontato);
        } catch (SQLException ex) {
            Logger.getLogger(TipoContatoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    public Tipocontato consultar(String sql) {
    	tipoContatoDao = new TipoContatoDao();
        try {
            return tipoContatoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TipoContatoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    public Tipocontato salvar(Tipocontato tipocontato) {
    	tipoContatoDao = new TipoContatoDao();
        try {
            return tipoContatoDao.salvar(tipocontato);
        } catch (SQLException ex) {
            Logger.getLogger(TipoContatoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     
}
