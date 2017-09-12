package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.AcessoUnidadeDao;
import br.com.travelmate.model.Acessounidade; 

public class AcessoUnidadeFacade {
	
	AcessoUnidadeDao acessoUnidadeDao;
	
	 public Acessounidade salvar(Acessounidade acessounidade){
		 acessoUnidadeDao = new AcessoUnidadeDao();
        try {
            return acessoUnidadeDao.salvar(acessounidade);
        } catch (SQLException ex) {
            Logger.getLogger(AcessoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Cursos " + ex);
            return null;
        }
    } 
    
    public Acessounidade consultar(String sql) {
    		acessoUnidadeDao = new AcessoUnidadeDao();
        try {
            return acessoUnidadeDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AcessoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro consultar acesso " + ex);
            return null;
        }
    }
    
    public void excluir(int idAcessounidade) {
    		acessoUnidadeDao = new AcessoUnidadeDao();
        try {
        	acessoUnidadeDao.excluir(idAcessounidade);
        } catch (SQLException ex) {
            Logger.getLogger(AcessoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Excluir Acesso da unidade " + ex);
        }
    }
   
    
    public List<Acessounidade> lista(String sql) {
    		acessoUnidadeDao = new AcessoUnidadeDao();
        try {
            return acessoUnidadeDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AcessoUnidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
