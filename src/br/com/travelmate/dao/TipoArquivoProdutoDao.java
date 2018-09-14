package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Tipoarquivoproduto;  

public class TipoArquivoProdutoDao {
 	
	public Tipoarquivoproduto salvar(Tipoarquivoproduto tipoarquivo) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		tipoarquivo = manager.merge(tipoarquivo);
		tx.commit();
		manager.close();
		return tipoarquivo;
	}
	    
	public Tipoarquivoproduto consultar(int idTipoArquivos) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Tipoarquivoproduto tipoarquivo = manager.find(Tipoarquivoproduto.class, idTipoArquivos);
        manager.close();
        return tipoarquivo;
    }
	    
	public void excluir(int idTipoArquivos) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Tipoarquivoproduto tipoarquivo = manager.find(Tipoarquivoproduto.class, idTipoArquivos);
		manager.remove(tipoarquivo);
		tx.commit();
		manager.close();
	}

    @SuppressWarnings("unchecked")
	public List<Tipoarquivoproduto> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Tipoarquivoproduto> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
}
