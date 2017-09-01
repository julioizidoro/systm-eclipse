package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.AvisoDocsDao;
import br.com.travelmate.model.Avisodocs;


public class AvisosDocsFacade {
	
	private AvisoDocsDao avisoDocsDao = new AvisoDocsDao();
	
	 public List<Avisodocs> lista(String sql) {
		 avisoDocsDao = new AvisoDocsDao();
       try {
           return avisoDocsDao.listar(sql);
       } catch (SQLException ex) {
           Logger.getLogger(AvisosDocsFacade.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
	
	public Avisodocs salvar(Avisodocs avisos){
		avisoDocsDao = new AvisoDocsDao();
       try {
           return avisoDocsDao.salvar(avisos);
       } catch (SQLException ex) {
           Logger.getLogger(AvisosDocsFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Salvar aviso " + ex);
           return null;
       }
   }
	
	public void excluir(Avisodocs avisos) {
		avisoDocsDao = new AvisoDocsDao();
       try {
    	   avisoDocsDao.excluir(avisos);
       } catch (SQLException ex) {
           Logger.getLogger(AvisosDocsFacade.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro Excluir aviso " + ex);
       }
   }

}
