package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadeidioma;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class FornecedorCidadeIdiomaDao {
    
    public Fornecedorcidadeidioma salvar(Fornecedorcidadeidioma fornecedorcidadeidioma) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedorcidadeidioma = manager.merge(fornecedorcidadeidioma);
        tx.commit();
        manager.close();
        return fornecedorcidadeidioma;
    }
    
    
    public List<Fornecedorcidadeidioma> listar(String sql)throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Fornecedorcidadeidioma> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        return lista;
    }
    
    public void excluir(int idFornecedorcidadeidioma) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Fornecedorcidadeidioma fornecedorcidadeidioma = manager.find(Fornecedorcidadeidioma.class, idFornecedorcidadeidioma);
        manager.remove(fornecedorcidadeidioma);
        tx.commit();
    }
    
    public Fornecedorcidadeidioma consultar(String sql) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Fornecedorcidadeidioma fornecedorcidadeidioma = null;
        if (q.getResultList().size() > 0) {
        	fornecedorcidadeidioma = (Fornecedorcidadeidioma) q.getResultList().get(0);
        } 
        return fornecedorcidadeidioma;
     }
}
