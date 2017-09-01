package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
 
import br.com.travelmate.dao.GrupoAcessoDao;
import br.com.travelmate.model.Acesso; 
import br.com.travelmate.model.Grupoacesso;


public class GrupoAcessoFacade {


	GrupoAcessoDao grupoAcessoDao;
    
    public List<Grupoacesso> listar(String sql){
    	grupoAcessoDao = new GrupoAcessoDao();
        try { 
            return grupoAcessoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(GrupoAcessoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Grupoacesso salvar(Grupoacesso grupoacesso){
    	grupoAcessoDao = new GrupoAcessoDao();
        try {
            return grupoAcessoDao.salvar(grupoacesso);
        } catch (SQLException ex) {
            Logger.getLogger(GrupoAcessoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cursos " + ex);
            return null;
        }
    }
    
    
    public Acesso salvar(Acesso acesso){
    	grupoAcessoDao = new GrupoAcessoDao();
        try {
            return grupoAcessoDao.salvar(acesso);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar acesso " + ex);
            return null;
        }
    }
    
}
