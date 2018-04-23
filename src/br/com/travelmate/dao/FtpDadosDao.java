/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ftpdados;

/**
 *
 * @author Wolverine
 */
public class FtpDadosDao {
    
    public Ftpdados getFTPDados()throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select f from Ftpdados f");
        Ftpdados ftpDados = (Ftpdados) q.getSingleResult();
        manager.close();
        return ftpDados;
    } 
    
}
