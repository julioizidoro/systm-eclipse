/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;

import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Curso;
import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
public class CursoDao {
    
    private EntityManager manager;
    
    public Curso salvar(Curso curso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        curso = manager.merge(curso);
        tx.commit();
        manager.close();
        return curso;
    }
    
   
    
    public Curso consultarCursos(int idVenda) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Curso c where c.vendas.idvendas=" + idVenda);
        Curso curso = null; 
        if (q.getResultList().size() > 0) {
            curso =  (Curso) q.getResultList().get(0);
        } 
        manager.close();
        return curso;
    }
    
    public void excluir(int idCurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Curso curso = manager.find(Curso.class, idCurso);
        manager.remove(curso);
        tx.commit();
        manager.close();
    }
    
    public Controlecurso salvar(Controlecurso controle) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
    public void excluirControleCurso(Controlecurso controle) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Controlecurso controlecursos = manager.find(Controlecurso.class, controle.getIdcontroleCursos());
        manager.remove(controlecursos);
        tx.commit();
        manager.close();
    }
    
    public Controlecurso consultarControleCursos(int idVendas, int idControle) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controlecurso c where c.vendas.idvendas=" + idVendas + "  and c.idcontroleCursos=" + idControle);
        Controlecurso controlecurso = null;
        if (q.getResultList().size() > 0) {
            controlecurso =  (Controlecurso) q.getResultList().get(0);
        }
        manager.close();
        return controlecurso;
    }
    
    public Controlecurso consultarControleCursos(int idVendas) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controlecurso c where c.vendas.idvendas=" + idVendas);
        Controlecurso controlecurso = null;
        if (q.getResultList().size() > 0) {
            controlecurso =  (Controlecurso) q.getResultList().get(0);
        }
        manager.close();
        return controlecurso;
    }
    
    public List<Controlecurso> listaControle(String sql) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controlecurso> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void gerarSitaucaoEmbarcado() throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        String data = Formatacao.ConvercaoDataSql(new Date());
        String sql = "Update controlecurso set situacao='Embarcado' where situacao='Processo'  and dataEmbarque<='" + data + "'";
        Query q = manager.createNativeQuery(sql);
        q.executeUpdate();
        tx.commit();
        manager.close();
    }
    
    
    public List<Curso> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Curso> lista = q.getResultList();
        manager.close();
        return lista;
    }
}
