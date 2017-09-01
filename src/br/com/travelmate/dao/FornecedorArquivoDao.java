package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorarquivo;

public class FornecedorArquivoDao {
	 	
	public Fornecedorarquivo salvar(Fornecedorarquivo arquivos) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		arquivos = manager.merge(arquivos);
		tx.commit();
		manager.close();
		return arquivos;
	}
	    
	public Fornecedorarquivo consultar(int idArquivos) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Fornecedorarquivo arquivos = manager.find(Fornecedorarquivo.class, idArquivos);
        manager.close();
        return arquivos;
    }
	    
	public void excluir(int idArquivos) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorarquivo arquivos = manager.find(Fornecedorarquivo.class, idArquivos);
		manager.remove(arquivos);
		tx.commit();
		manager.close();
	}
	    
	public List<Fornecedorarquivo> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Fornecedorarquivo> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
}
