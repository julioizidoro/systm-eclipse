/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
   

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.WorkSponsorArquivoDao;
import br.com.travelmate.model.Worksponsorarquivos;

/**
 *
 * @author Kamila
 */
public class WorkSponsorArquivoFacade {
    
    WorkSponsorArquivoDao workSponsorDao;
    
    public Worksponsorarquivos salvar(Worksponsorarquivos worksponsor) {
    		workSponsorDao = new WorkSponsorArquivoDao();
        try {
            return workSponsorDao.salvar(worksponsor);
        } catch (SQLException ex) {
            Logger.getLogger(WorkSponsorArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Worksponsorarquivos> listar(String sql) {
    		workSponsorDao = new WorkSponsorArquivoDao();
        try {
            return workSponsorDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkSponsorArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Worksponsorarquivos consultar(String sql){
    		workSponsorDao = new WorkSponsorArquivoDao();
        try {
            return workSponsorDao.consulta(sql);
        } catch (SQLException ex) {
            Logger.getLogger(WorkSponsorArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    public void excluir(int idArquivos) {
    		workSponsorDao = new WorkSponsorArquivoDao();
        try {
        	workSponsorDao.excluir(idArquivos);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
