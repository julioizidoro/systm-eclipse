package br.com.travelmate.facade;

import br.com.travelmate.dao.HighSchoolDao;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Highschool;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class HighSchoolFacade {
    
    HighSchoolDao highSchoolDao;
    
    
    public Highschool salvar(Highschool highschool){
        highSchoolDao = new HighSchoolDao();
        try {
            return highSchoolDao.salvar(highschool);
        } catch (SQLException ex) {
            Logger.getLogger(HighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar High School " + ex);
            return null;
        }
        
    }
    
    public List<Highschool> listar(String sql) {
        highSchoolDao = new HighSchoolDao();
        try {
            return highSchoolDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar High School" + ex);
            return null;
        }
    }
    
    public Highschool consultarHighschool(int idVenda)  {
    	highSchoolDao= new HighSchoolDao();
    	try {
			return highSchoolDao.consultarHighschool(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    
    public Controlehighschool salvar(Controlehighschool controle){
    	highSchoolDao= new HighSchoolDao();
        try {
            return highSchoolDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(HighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle High School " + ex);
            return null;
        }
    }
    
    public Controlehighschool consultarControleCursos(int idVendas, int idControle)  {
    	highSchoolDao= new HighSchoolDao();
        try {
            return highSchoolDao.consultarControle(idVendas, idControle);
        } catch (SQLException ex) {
            Logger.getLogger(HighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Controle High School " + ex);
            return null;
        }
    }
    
    public Controlehighschool consultarControle(int idVendas)  {
    	highSchoolDao= new HighSchoolDao();
        try {
            return highSchoolDao.consultarControle(idVendas);
        } catch (SQLException ex) {
            Logger.getLogger(HighSchoolFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Controle High School " + ex);
            return null;
        }
    }
    
    public List<Controlehighschool> listaControle(String sql)  {
    	highSchoolDao= new HighSchoolDao();
        try {
            return highSchoolDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Listar Controle High School " + ex);
            return null;
        }
    }
    
 
}
