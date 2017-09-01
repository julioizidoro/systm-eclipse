package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Questionariohe;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class QuestionarioHeDao {
    
    public Questionariohe salvar(Questionariohe questionariohe) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		questionariohe = manager.merge(questionariohe);
        tx.commit();
        manager.close();
        return questionariohe;
    }
    
    public List<Questionariohe> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Questionariohe> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Questionariohe consultar(int idhe) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select q from Questionariohe q where q.idquestionariohe=" + idhe);
        Questionariohe questionariohe = null;
        if (q.getResultList().size()>0){
        	questionariohe = (Questionariohe) q.getResultList().get(0);
        }
        manager.close();
        return questionariohe;
    } 
    
    
    public Questionariohe consultarVenda(int idvenda) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select q from Questionariohe q where q.vendas_idvendas=" + idvenda);
        Questionariohe questionariohe = null;
        if (q.getResultList().size()>0){
        	questionariohe = (Questionariohe) q.getResultList().get(0);
        }
        manager.close();
        return questionariohe;
    } 
}
