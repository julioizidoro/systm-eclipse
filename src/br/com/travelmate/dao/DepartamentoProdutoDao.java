package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Departamentoproduto;

public class DepartamentoProdutoDao {
	
	public Departamentoproduto consultar(int idProduto) throws SQLException {
		EntityManager manager;
        manager = ConectionFactory.getConnection();
         Query q = manager.createQuery("select d from Departamentoproduto d where d.produtos.idprodutos=" + idProduto);
         Departamentoproduto departamentoproduto= null;
         if (q.getResultList().size() > 0) {
        	 departamentoproduto =  (Departamentoproduto) q.getResultList().get(0);
         }
         manager.close();
         return departamentoproduto;
     }

}
