/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;


import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class CambioDao {
    
    public Cambio salvar(Cambio cambio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        cambio = manager.merge(cambio);
        tx.commit();
        manager.close();
        return cambio;
    }
    
    public void excluir(Cambio cambio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        cambio = manager.find(Cambio.class, cambio.getIdcambio());
        manager.remove(cambio);
        tx.commit();
        manager.close();
    }
    
    public Cambio consultar(int idCambio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Cambio cambio = manager.find(Cambio.class, idCambio);
        manager.close();
        return cambio;
    }
    
    public Cambio consultarSigla(String data,String sigla) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cambio c where c.moedas.sigla='" + sigla + "' and c.data='" + data + "'");
        Cambio cambio = null;
        if (q.getResultList().size()>0){
            cambio =  (Cambio) q.getResultList().get(0);
        }
        manager.close();
        return cambio;
    }
    
    public List<Moedas> listaMoedas() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select m from Moedas m");
        List<Moedas> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Cambio consultarCambioMoeda(String data, int idMoeda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cambio c where c.moedas.idmoedas=" + idMoeda + " and c.data='" + data + "'");
        Cambio cambio = null;
        if (q.getResultList().size()>0){
            cambio =  (Cambio) q.getResultList().get(0);
        }
        manager.close();
        return cambio;
    }
    
    public Moedas consultarMoeda(int idMoeda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Moedas moeda = manager.find(Moedas.class, idMoeda);
        manager.close();
        return moeda;
    }
    
    public List<Cambio> listar(String data) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cambio c where c.data='" + data + "'");
        List<Cambio> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public List<Cambio> listarCambioPais(String data, Pais pais) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cambio c where c.data='" + data + "' and c.pais.idpais=" + pais.getIdpais());
        List<Cambio> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
