/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Filtroorcamentoproduto;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FiltroOrcamentoProdutoDao {
    
    public Filtroorcamentoproduto salvar(Filtroorcamentoproduto filtro) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        filtro = manager.merge(filtro);
        tx.commit();
        manager.close();
        return filtro;
    }
    
    public void excluir(int idFiltro) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Filtroorcamentoproduto filtro = manager.find(Filtroorcamentoproduto.class, idFiltro);
        manager.remove(filtro);
        tx.commit();
        manager.close();
    }
    
    public Filtroorcamentoproduto pesquisar(int idProduto, int idProdutoOrcamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select f from Filtroorcamentoproduto f where f.produtosorcamento.idprodutosOrcamento=" + idProdutoOrcamento  + 
                " and f.produtos.idprodutos= " + idProduto);
        Filtroorcamentoproduto filtro = null;
        if (q.getResultList().size()>0){
            filtro = (Filtroorcamentoproduto) q.getResultList().get(0);
        }
        manager.close();
        return filtro;
    }
    
    public Filtroorcamentoproduto pesquisar(int idProduto, String nome) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select f from Filtroorcamentoproduto f where f.produtos.idprodutos= " + idProduto
        		+" and f.produtosorcamento.descricao='"+nome+"'");
        Filtroorcamentoproduto filtro = null;
        if (q.getResultList().size()>0){
            filtro = (Filtroorcamentoproduto) q.getResultList().get(0);
        }
        manager.close();
        return filtro;
    }
    
    @SuppressWarnings("unchecked")
	public List<Filtroorcamentoproduto> pesquisar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Filtroorcamentoproduto> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    @SuppressWarnings("unchecked")
	public List<Filtroorcamentoproduto> listarProdutosOrcamento(int idProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Filtroorcamentoproduto p  where p.produtos.idprodutos="+ idProduto +" order by p.descricao" );
        List<Filtroorcamentoproduto> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
