/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Coeficientejuros;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class CoeficienteJurosDao {
    
    public Coeficientejuros salvar(Coeficientejuros coeficientejuros) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        coeficientejuros = manager.merge(coeficientejuros);
        tx.commit();
        manager.close();
        return coeficientejuros;
    }
    
    public Coeficientejuros consultar(int numeroParcelas, String tipo) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Coeficientejuros c where c.numeroParcelas=" + numeroParcelas + 
        		" and c.tipo='" + tipo + "'");
        Coeficientejuros coeficientejuros = null;
        if (q.getResultList().size()>0){
        		coeficientejuros = (Coeficientejuros) q.getSingleResult();
        }
        manager.close();
        return coeficientejuros;
    }
    
    @SuppressWarnings("unchecked")
	public List<Coeficientejuros> listar() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Coeficientejuros c order by c.numeroParcelas");
        List<Coeficientejuros> lista = null;
        if (q.getResultList().size()>0){
        		lista = q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    @SuppressWarnings("unchecked")
	public List<Coeficientejuros> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        List<Coeficientejuros> lista = null;
        Query q = manager.createQuery(sql);
        if (q.getResultList().size()>0){
        		lista = q.getResultList();
        }
        manager.close();
        return lista;
    }
}
