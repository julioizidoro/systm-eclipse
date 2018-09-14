/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Notificacaoemail;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
@SuppressWarnings("unchecked")
public class NotificacaoEmailDao {
    
    public Notificacaoemail salvar(Notificacaoemail notificacaoemail) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        notificacaoemail = manager.merge(notificacaoemail);
        tx.commit();
        manager.close();
        return notificacaoemail;
    }
    
    public List<Notificacaoemail> listarPorProduto(int idProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select n from Notificacaoemail n where n.produtos.idprodutos=" + idProduto + " order by n.usuario.nome");
        List<Notificacaoemail> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Notificacaoemail> listarPorProdutoTipo(int idProduto, String tipoAviso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select n from Notificacaoemail n where n.produtos.idprodutos=" + idProduto + " and n.tipoAviso<>'" 
                + tipoAviso + "' order by n.usuario.nome" );
        List<Notificacaoemail> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
    public void excluir(int idNotificacaoemail) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Notificacaoemail notificacaoemail = manager.find(Notificacaoemail.class, idNotificacaoemail);
        manager.remove(notificacaoemail);
        tx.commit();
        manager.close();
    }
}
