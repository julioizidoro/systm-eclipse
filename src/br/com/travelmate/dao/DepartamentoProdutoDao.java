package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager; 
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Departamentoproduto;

@SuppressWarnings("unchecked")
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
	
	 public List<Departamentoproduto> listar(int iddepartamento) throws SQLException {
		 EntityManager manager  = ConectionFactory.getInstance();
         Query q = manager.createQuery("select d from Departamentoproduto d where d.departamento.iddepartamento=" + iddepartamento);
         List<Departamentoproduto> lista = q.getResultList();
         return lista;    
     }
	
	public List<Departamentoproduto> listar() throws SQLException {
		 EntityManager manager  = ConectionFactory.getInstance();
        Query q = manager.createQuery("select d from Departamentoproduto d order by d.produtos.descricao");
        List<Departamentoproduto> lista = q.getResultList();
        return lista;    
    }

}
