package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadeidiomaprodutodata;

public class FornecedorCidadeIdiomaProdutoDataDao {
	
	public Fornecedorcidadeidiomaprodutodata salvar(Fornecedorcidadeidiomaprodutodata fornecedorcidadeidiomaprodutodata) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedorcidadeidiomaprodutodata = manager.merge(fornecedorcidadeidiomaprodutodata);
        tx.commit();
        manager.close();
        return fornecedorcidadeidiomaprodutodata;
    }
	
	
	@SuppressWarnings("unchecked")
	public List<Fornecedorcidadeidiomaprodutodata> listar(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
       Query q = manager.createQuery(sql);
       List<Fornecedorcidadeidiomaprodutodata> lista = null;
       if (q.getResultList().size()>0){
           lista =  q.getResultList();
       }
       manager.close();
       return lista;
   }
    

	public void excluir(int idFornecedorcidadeidiomaprodutodata) throws SQLException {
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorcidadeidiomaprodutodata fornecedorcidadeidiomaprodutodata = manager.find(Fornecedorcidadeidiomaprodutodata.class, idFornecedorcidadeidiomaprodutodata);
        manager.remove(fornecedorcidadeidiomaprodutodata);
        tx.commit();
        manager.close();
    }

}
