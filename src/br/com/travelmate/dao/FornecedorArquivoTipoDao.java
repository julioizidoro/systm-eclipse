package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorarquivotipo;

public class FornecedorArquivoTipoDao {
	
	public Fornecedorarquivotipo salvar(Fornecedorarquivotipo tipoarquivo) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		tipoarquivo = manager.merge(tipoarquivo);
		tx.commit();
		manager.close();
		return tipoarquivo;
	}
	    
	public Fornecedorarquivotipo consultar(int idTipoArquivos) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Fornecedorarquivotipo tipoarquivo = manager.find(Fornecedorarquivotipo.class, idTipoArquivos);
        manager.close();
        return tipoarquivo;
    }
	    
	public void excluir(int idTipoArquivos) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorarquivotipo tipoarquivo = manager.find(Fornecedorarquivotipo.class, idTipoArquivos);
		manager.remove(tipoarquivo);
		tx.commit();
		manager.close();
	}
	    
	public List<Fornecedorarquivotipo> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Fornecedorarquivotipo> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
}
