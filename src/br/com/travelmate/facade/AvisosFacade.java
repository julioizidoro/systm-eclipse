package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;

public class AvisosFacade {
	
	private AvisosDao avisosDao = new AvisosDao();
	
	 public List<Avisos> lista(String sql) {
		 avisosDao = new AvisosDao();
        try {
            return avisosDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AvisosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	
	public Avisos salvar(Avisos avisos){
		avisosDao = new AvisosDao();
        try {
            return avisosDao.salvar(avisos);
        } catch (SQLException ex) {
            Logger.getLogger(AvisosFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar aviso " + ex);
            return null;
        }
    }
	
	public void excluir(Avisos avisos) {
		avisosDao = new AvisosDao();
        try {
        	avisosDao.excluir(avisos);
        } catch (SQLException ex) {
            Logger.getLogger(AvisosFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir aviso " + ex);
        }
    }
	
	public List<Avisousuario> listarAvisoUsuario(String sql) {
		avisosDao = new AvisosDao();
		try {
			return avisosDao.listarAvisoUsuario(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Avisousuario salvar(Avisousuario avisoUsuario) {
		avisosDao = new AvisosDao();
		try {
			return avisosDao.salvar(avisoUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
