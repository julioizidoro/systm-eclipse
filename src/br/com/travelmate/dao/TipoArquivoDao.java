package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Tipoarquivo;

public class TipoArquivoDao {
 	
	public Tipoarquivo salvar(Tipoarquivo tipoarquivo) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		tipoarquivo = manager.merge(tipoarquivo);
		tx.commit();
		manager.close();
		return tipoarquivo;
	}
	    
	public Tipoarquivo consultar(int idTipoArquivos) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Tipoarquivo tipoarquivo = manager.find(Tipoarquivo.class, idTipoArquivos);
        manager.close();
        return tipoarquivo;
    }
	    
	public void excluir(int idTipoArquivos) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Tipoarquivo tipoarquivo = manager.find(Tipoarquivo.class, idTipoArquivos);
		manager.remove(tipoarquivo);
		tx.commit();
		manager.close();
	}
	    
	public List<Tipoarquivo> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Tipoarquivo> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
}
