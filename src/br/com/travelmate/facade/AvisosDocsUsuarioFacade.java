package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.AvisoDocsUsuarioDao;
import br.com.travelmate.model.Avisodocsusuario;


public class AvisosDocsUsuarioFacade {
	
	private AvisoDocsUsuarioDao avisoDocsUsuarioDao = new AvisoDocsUsuarioDao();
	
	 public List<Avisodocsusuario> lista(String sql) {
		 avisoDocsUsuarioDao = new AvisoDocsUsuarioDao();
      try {
          return avisoDocsUsuarioDao.listar(sql);
      } catch (SQLException ex) {
          Logger.getLogger(AvisosDocsUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
          return null;
      }
  }
	
	public Avisodocsusuario salvar(Avisodocsusuario avisos){
		avisoDocsUsuarioDao = new AvisoDocsUsuarioDao();
      try {
          return avisoDocsUsuarioDao.salvar(avisos);
      } catch (SQLException ex) {
          Logger.getLogger(AvisosDocsUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
          JOptionPane.showMessageDialog(null, "Erro Salvar aviso " + ex);
          return null;
      }
  }
	
	public void excluir(Avisodocsusuario avisos) {
		avisoDocsUsuarioDao = new AvisoDocsUsuarioDao();
      try {
    	  avisoDocsUsuarioDao.excluir(avisos);
      } catch (SQLException ex) {
          Logger.getLogger(AvisosDocsUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
          JOptionPane.showMessageDialog(null, "Erro Excluir aviso " + ex);
      }
  }

}
