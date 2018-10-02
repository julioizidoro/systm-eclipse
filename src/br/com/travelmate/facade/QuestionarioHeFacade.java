package br.com.travelmate.facade;
 
import br.com.travelmate.dao.QuestionarioHeDao;
import br.com.travelmate.model.Questionariohe;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class QuestionarioHeFacade { 
    
	QuestionarioHeDao questionarioHeDao;
    
    public Questionariohe salvar(Questionariohe questionariohe){
    	questionarioHeDao = new QuestionarioHeDao();
        try {
            return questionarioHeDao.salvar(questionariohe);
        } catch (SQLException ex) {
            Logger.getLogger(QuestionarioHeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Questionario he" + ex);
            return null;
        }
        
    }
    
    public List<Questionariohe> listar(String sql) {
    	questionarioHeDao = new QuestionarioHeDao();
        try {
            return questionarioHeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(QuestionarioHeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Questionario he" + ex);
            return null;
        }
    }
    
    public Questionariohe consultar(int id)  {
    	questionarioHeDao = new QuestionarioHeDao();
    	try {
			return questionarioHeDao.consultar(id);
		} catch (SQLException e) {
			  
			return null;
		}
    } 
    
    
    public Questionariohe consultarVenda(int id)  {
    	questionarioHeDao = new QuestionarioHeDao();
    	try {
			return questionarioHeDao.consultar(id);
		} catch (SQLException e) {
			  
			return null;
		}
    } 
}
