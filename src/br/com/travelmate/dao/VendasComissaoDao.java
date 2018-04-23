package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Vendascomissao;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class VendasComissaoDao {
    
    public Vendascomissao salvar(Vendascomissao vendasComissao) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        vendasComissao = manager.merge(vendasComissao);
        tx.commit();
        manager.close();
        return vendasComissao;
    }
    
    public Vendascomissao getVendaComissao(int idVenda, int idProdutos)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select v from Vendascomissao v where v.vendas.idvendas=" + idVenda + " and v.produtos.idprodutos=" + idProdutos);
        Vendascomissao vendascomissao = null;
        if (q.getResultList().size()>0){
            vendascomissao = (Vendascomissao) q.getResultList().get(0);
        }
        manager.close();
        return vendascomissao;
    }
    
    public Vendascomissao consultar(int idVenda)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("Select v from Vendascomissao v where v.vendas.idvendas=" + idVenda);
       Vendascomissao vendascomissao = null;
       if (q.getResultList().size()>0){
           vendascomissao = (Vendascomissao) q.getResultList().get(0);
       }
       manager.close();
       return vendascomissao;
   }
    
    public void excluir(int idVendaComissao) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Vendascomissao vendasComissao = manager.find(Vendascomissao.class, idVendaComissao);
        manager.remove(vendasComissao);
        tx.commit();
        manager.close();
    }
    
    public List<Vendascomissao> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Vendascomissao> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
         return lista;
    }
    
}
