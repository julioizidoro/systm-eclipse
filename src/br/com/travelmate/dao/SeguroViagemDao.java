package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Seguroviagem;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class SeguroViagemDao {
    
    public Seguroviagem salvar(Seguroviagem seguroViagem) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        seguroViagem = manager.merge(seguroViagem);
        tx.commit();
        manager.close();
        return seguroViagem;
    }
    
    public Seguroviagem consultar(int idVenda) throws SQLException{
    		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select s from Seguroviagem s where s.vendas.idvendas=" + idVenda);
        Seguroviagem seguro = null;
        if (q.getResultList().size() > 0) {
            seguro =   (Seguroviagem) q.getResultList().get(0);
        }
        manager.close();
        return seguro;
    }
    
    public Seguroviagem consultarSeguroCurso(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select s from Seguroviagem s where s.idvendacurso=" + idVenda);
        Seguroviagem seguro = null;
        if (q.getResultList().size() > 0) {
            seguro =   (Seguroviagem) q.getResultList().get(0);
        }
        manager.close();
        return seguro;
    }

    @SuppressWarnings("unchecked")
    public List<Seguroviagem> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        List<Seguroviagem> lista = null;
        Query q = manager.createQuery(sql);
        lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idSeguroViagem) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Seguroviagem seguroViagem = manager.find(Seguroviagem.class, idSeguroViagem);
        manager.remove(seguroViagem);
        tx.commit();
        manager.close();
    }
    
  
    public Controleseguro consultarControleSeguro(int idVendas) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controleseguro c where c.vendas.idvendas=" + idVendas);
        Controleseguro controle = null;
        if (q.getResultList().size() > 0) {
            controle =  (Controleseguro) q.getResultList().get(0);
        }
        manager.close();
        return controle;
    }
    
    public Controleseguro salvarControle(Controleseguro controle) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
}
