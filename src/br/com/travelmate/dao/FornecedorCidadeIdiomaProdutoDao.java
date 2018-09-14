package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;

public class FornecedorCidadeIdiomaProdutoDao {
	
	public Fornecedorcidadeidiomaproduto salvar(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedorcidadeidiomaproduto = manager.merge(fornecedorcidadeidiomaproduto);
        tx.commit();
        manager.close();
        return fornecedorcidadeidiomaproduto;
    }
	
	
	@SuppressWarnings("unchecked")
	public List<Fornecedorcidadeidiomaproduto> listar(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
       Query q = manager.createQuery(sql);
       List<Fornecedorcidadeidiomaproduto> lista = null;
       if (q.getResultList().size()>0){
           lista =  q.getResultList();
       }
       manager.close();
       return lista;
   }
    

	public void excluir(int idFornecedorcidadeidiomaproduto) throws SQLException {
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("select f from Fornecedorcidadeidiomaproduto f where f.idfornecedorcidadeidiomaproduto=" + idFornecedorcidadeidiomaproduto);
        if (q.getResultList().size()>0){
        	Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto = (Fornecedorcidadeidiomaproduto) q.getResultList().get(0);
            manager.remove(fornecedorcidadeidiomaproduto);
        }
        tx.commit();
        manager.close();
    }
	
	public Fornecedorcidadeidiomaproduto consultar(String sql) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto = null;
        if (q.getResultList().size() > 0) {
        	fornecedorcidadeidiomaproduto = (Fornecedorcidadeidiomaproduto) q.getResultList().get(0);
        } 
        manager.close();
        return fornecedorcidadeidiomaproduto;
     }
	
	
	public Fornecedorcidadeidiomaproduto consultar(int idFornecedorcidadeidiomaproduto) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto = manager.find(Fornecedorcidadeidiomaproduto.class, idFornecedorcidadeidiomaproduto);
        manager.close();
        return fornecedorcidadeidiomaproduto;
    }
    
}
