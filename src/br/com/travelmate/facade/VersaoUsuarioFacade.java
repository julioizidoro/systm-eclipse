package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VersaoUsuarioDao;
import br.com.travelmate.model.Versaousuario;

public class  VersaoUsuarioFacade{

	private VersaoUsuarioDao versaoUsuarioDao;
	
	public Versaousuario salvar(Versaousuario versoes) {
		versaoUsuarioDao = new VersaoUsuarioDao();
        try {
            return versaoUsuarioDao.salvar(versoes);
        } catch (SQLException ex) {
            Logger.getLogger(VersaoUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Versaousuario consultar(int idversao)  {
    	versaoUsuarioDao = new VersaoUsuarioDao();
        try {
            return versaoUsuarioDao.consultar(idversao);
        } catch (SQLException ex) {
            Logger.getLogger(VersaoUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Versaousuario> listar(String sql) {
    	versaoUsuarioDao = new VersaoUsuarioDao();
        try {
            return versaoUsuarioDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VersaoUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
