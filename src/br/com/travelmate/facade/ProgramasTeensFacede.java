/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.ProgramasTeensDao;
import br.com.travelmate.model.Controleprogramasteen;
import br.com.travelmate.model.Programasteens;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Greicieli
 */
public class ProgramasTeensFacede {
    ProgramasTeensDao programasTeensDao;
    
     public Programasteens salvar(Programasteens programasteens){
        programasTeensDao = new ProgramasTeensDao();
        try {
            return programasTeensDao.salvar(programasteens);
        } catch (SQLException ex) {
            Logger.getLogger(ProgramasTeensFacede.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public List<Programasteens> listar(String sql) {
        programasTeensDao = new ProgramasTeensDao();
        try {
            return programasTeensDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProgramasTeensFacede.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Programasteens find(int idVenda) {
    	programasTeensDao = new ProgramasTeensDao();
    	try {
			return programasTeensDao.find(idVenda);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public Controleprogramasteen salvar(Controleprogramasteen controle){
    	programasTeensDao = new ProgramasTeensDao();
        try {
            return programasTeensDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(ProgramasTeensFacede.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle Cursos Teens " + ex);
            return null;
        }
    }
    
    public Controleprogramasteen consultarControleCursos(int idVendas, int idControle)  {
    	programasTeensDao = new ProgramasTeensDao();
        try {
            return programasTeensDao.consultarControle(idVendas, idControle);
        } catch (SQLException ex) {
            Logger.getLogger(ProgramasTeensFacede.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Controle Cursos Teens " + ex);
            return null;
        }
    }
    
    public Controleprogramasteen consultarControle(int idVendas)  {
    	programasTeensDao = new ProgramasTeensDao();
        try {
            return programasTeensDao.consultarControle(idVendas);
        } catch (SQLException ex) {
            Logger.getLogger(ProgramasTeensFacede.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Controle Cursos Teens " + ex);
            return null;
        }
    }
    
    public List<Controleprogramasteen> listaControle(String sql)  {
    	programasTeensDao = new ProgramasTeensDao();
        try {
            return programasTeensDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProgramasTeensFacede.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Listar Controle Cursos Teens " + ex);
            return null;
        }
    }
    
}
