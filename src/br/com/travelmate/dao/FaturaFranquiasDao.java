/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Faturafranquias;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FaturaFranquiasDao {
    
    public Faturafranquias salvar(Faturafranquias faturafranquias) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        faturafranquias = manager.merge(faturafranquias);
        tx.commit();
        manager.close();
        return faturafranquias;
    }
    
    public Faturafranquias getFaturaFranquia(int idVendasComissao)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
 	    Query q = manager.createQuery("Select f from Faturafranquias f where f.vendascomissao.idvendascomissao=" + idVendasComissao);
        Faturafranquias fatura = null;
        if (q.getResultList().size()>0){
            fatura = (Faturafranquias) q.getResultList().get(0);
        }
        manager.close();
        return fatura;
    }
    
    public void excluir(int idFaturaFranquias) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Faturafranquias fatura = manager.find(Faturafranquias.class, idFaturaFranquias);
        manager.remove(fatura);
        tx.commit();
        manager.close();
    }
    
    public List<Faturafranquias> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Faturafranquias> fatura = null;
        if (q.getResultList().size()>0){
            fatura =  q.getResultList();
        }
        manager.close();
        return fatura;
    }    
}
