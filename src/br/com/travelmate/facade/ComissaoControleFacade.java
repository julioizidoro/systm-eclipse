package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ComissaoControleDao;
import br.com.travelmate.dao.ContasReceberDao;
import br.com.travelmate.model.Comissaocontrole;


public class ComissaoControleFacade {
	
	private ComissaoControleDao comissaoControleDao;
	
	public Comissaocontrole salvar(Comissaocontrole comissaocontrole) {
		comissaoControleDao = new ComissaoControleDao();
		try {
			return comissaoControleDao.salvar(comissaocontrole);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Comissaocontrole> listar(String sql){
		comissaoControleDao = new ComissaoControleDao();
		try {
			return comissaoControleDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Comissaocontrole getComissao(String sql){
		comissaoControleDao = new ComissaoControleDao();
		try {
			return comissaoControleDao.getComissao(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    public void excluir(int idComissao) {
    	comissaoControleDao = new ComissaoControleDao();
        try {
        	comissaoControleDao.excluir(idComissao);
        } catch (SQLException ex) {
            Logger.getLogger(ComissaoControleFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
