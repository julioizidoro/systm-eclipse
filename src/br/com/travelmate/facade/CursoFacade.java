/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import br.com.travelmate.dao.CursoDao;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Curso;

/**
 *
 * @author Wolverine
 */
public class CursoFacade {
    
    CursoDao cursoDao;
    
    public Curso salvar(Curso curso){
    	cursoDao = new CursoDao();
        try {
            return cursoDao.salvar(curso);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cursos " + ex);
            return null;
        }
    }
    
    
    
    public Curso consultarCursos(int idVenda) {
    	cursoDao = new CursoDao();
        try {
            return cursoDao.consultarCursos(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar Curso " + ex);
            return null;
        }
    }
    
    public void excluir(int idCurso) {
    	cursoDao = new CursoDao();
        try {
        	cursoDao.excluir(idCurso);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Curso " + ex);
        }
    }
    
    public Controlecurso salvar(Controlecurso controle){
    	cursoDao = new CursoDao();
        try {
            return cursoDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle Curso " + ex);
            return null;
        }
    }
    
    public Controlecurso consultarControleCursos(int idVendas, int idControle)  {
    	cursoDao= new CursoDao();
        try {
            return cursoDao.consultarControleCursos(idVendas, idControle);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Controle Curso " + ex);
            return null;
        }
    }
    
    public Controlecurso consultarControleCursos(int idVendas)  {
    	cursoDao= new CursoDao();
        try {
            return cursoDao.consultarControleCursos(idVendas);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Controle Curso " + ex);
            return null;
        }
    }
    
    public List<Controlecurso> listaControle(String sql)  {
    	cursoDao= new CursoDao();
        try {
            return cursoDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Listar Controle Curso " + ex);
            return null;
        }
    }
    
  
    
    public void excluirControleCurso(Controlecurso controle) {
    	cursoDao = new CursoDao();
        try {
        	cursoDao.excluirControleCurso(controle);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Controle de Curso " + ex);
        }
    }
    
    public void gerarSitaucaoEmbarcado() {
    	cursoDao = new CursoDao();
        try {
        	cursoDao.gerarSitaucaoEmbarcado();
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Erro Gerar Embarcados " +  ex);
        }
    }
    
    public List<Curso> lista(String sql) {
    	cursoDao = new CursoDao();
        try {
            return cursoDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
