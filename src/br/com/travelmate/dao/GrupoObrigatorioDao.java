package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Grupoobrigatorio;

public class GrupoObrigatorioDao {
    
    public Grupoobrigatorio salvar(Grupoobrigatorio grupoobrigatorio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        grupoobrigatorio = manager.merge(grupoobrigatorio);
        tx.commit();
        manager.close();
        return grupoobrigatorio;
    }
    
    
    public List<Grupoobrigatorio> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Grupoobrigatorio> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Grupoobrigatorio consultar(int idcoprodutos) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("SELECT g FROM Grupoobrigatorio g where g.coprodutos.idcoprodutos=" + idcoprodutos);
        Grupoobrigatorio grupo = null;
        if (q.getResultList().size() > 0) {
            grupo =   (Grupoobrigatorio) q.getResultList().get(0);
        }
        manager.close();
        return grupo;
    }
    
    public Grupoobrigatorio consultarProduto(int idcoprodutos) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("SELECT g FROM Grupoobrigatorio g where g.produto.idcoprodutos=" + idcoprodutos);
        Grupoobrigatorio grupo = null;
        if (q.getResultList().size() > 0) {
            grupo =   (Grupoobrigatorio) q.getResultList().get(0);
        }
        manager.close();
        return grupo;
    }
    
    public void excluir(Grupoobrigatorio grupoobrigatorio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        grupoobrigatorio = manager.find(Grupoobrigatorio.class, grupoobrigatorio.getIdgrupoobrigatorio());
        manager.remove(grupoobrigatorio);
        tx.commit();
        manager.close();
    }
    
    public void excluir(int idgrupo)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
       Query q = manager.createNativeQuery("Delete from grupoobrigatorio where coprodutos_produto=" + idgrupo);
       if (q.getResultList().size()>0){
       		Grupoobrigatorio grupoobrigatorio = (Grupoobrigatorio) q.getResultList().get(0);
           manager.remove(grupoobrigatorio);
       }
       tx.commit();
       manager.close();
   }
    
    

}
