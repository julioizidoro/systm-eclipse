/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.facade;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import br.com.travelmate.dao.DocsDao;
import br.com.travelmate.model.Docs;

/**
 *
 * @author Wolverine
 */
public class DocsFacade {
    
    DocsDao docsDao;
    
    public Docs salvar(Docs docs) {
    	docsDao = new DocsDao();
        try {
            return docsDao.salvar(docs);
        } catch (SQLException ex) {
            Logger.getLogger(DocsFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar docs " +  ex);
            return null;
        }
    }
    
    
    public Docs consultarVenda(int idVenda) {
    	docsDao = new DocsDao();
        try {
            return docsDao.consultarVenda(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(DocsFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Docs " +  ex);
            return null;
        }
    }
    
  
}