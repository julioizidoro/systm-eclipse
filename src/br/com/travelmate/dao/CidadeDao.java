package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cidade;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class CidadeDao {
    
    public Cidade salvar(Cidade cidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        cidade = manager.merge(cidade);
        tx.commit();
        manager.close();
        return cidade;
    }
    
    public List<Cidade> listar(int idPais) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cidade c where c.pais.idpais=" + idPais + " order by c.nome");
        List<Cidade> listaCidade = q.getResultList();
        manager.close();
        return listaCidade;
    }
    
    
    public Cidade consultar(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Cidade c where c.idcidade="+id );
        Cidade cidade = null;
        if (q.getResultList().size() > 0) {
        	cidade = (Cidade) q.getResultList().get(0);
        } 
        manager.close();
        return cidade;
     }
}
