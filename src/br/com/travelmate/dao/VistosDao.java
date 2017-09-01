package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoresvistos;
import br.com.travelmate.model.Vistos;

public class VistosDao {
    public Vistos salvar(Vistos visto) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        visto = manager.merge(visto);
        tx.commit();
        manager.close();
        return visto;
    }
    
    public Vistos consultarVistos(int idVenda) throws SQLException{
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("select v from Vistos v where v.vendas.idvendas=" + idVenda);
       Vistos visto = null;
        if (q.getResultList().size() > 0) {
            visto =  (Vistos) q.getResultList().get(0);
        }
        manager.close();
        return visto;
    }
    
    public List<Vistos> listar(String sql) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Vistos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idVisto) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Vistos visto = manager.find(Vistos.class, idVisto);
        manager.remove(visto);
        tx.commit();
        manager.close();
    }
    
        
    public Valoresvistos salvar(Valoresvistos valoresVisto) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        valoresVisto = manager.merge(valoresVisto);
        tx.commit();
        manager.close();
        return valoresVisto;
    }
    
    public List<Valoresvistos> listar()throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select v from Valoresvistos v where v.situacao='ATIVO'");
        List<Valoresvistos> lista = q.getResultList();
         manager.close();
         return lista;
    }
    
    public Valoresvistos consultar(int idvaloresVistos) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Valoresvistos valoresVistos = manager.find(Valoresvistos.class, idvaloresVistos);
        manager.close();
        return valoresVistos;
    }
    
}

