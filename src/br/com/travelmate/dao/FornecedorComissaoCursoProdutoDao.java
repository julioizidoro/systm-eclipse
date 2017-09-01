/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcomissaocursoproduto;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
/**
 *
 * @author Wolverine
 */
public class FornecedorComissaoCursoProdutoDao {
    
    public Fornecedorcomissaocursoproduto salvar(Fornecedorcomissaocursoproduto fornecedor) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedor = manager.merge(fornecedor);
        tx.commit();
        return fornecedor;
    }
    
    public List<Fornecedorcomissaocursoproduto> listar(int idFornecedorcomissaocurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select f from Fornecedorcomissaocursoproduto f where f.fornecedorcomissaocurso.idfornecedorcomissao=" + idFornecedorcomissaocurso + " order by f.produtosorcamento.descricao");
        List<Fornecedorcomissaocursoproduto> listaFornecedor = q.getResultList();
        return listaFornecedor;
    }
    
    public List<Fornecedorcomissaocursoproduto> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Fornecedorcomissaocursoproduto> listaFornecedor = q.getResultList();
        return listaFornecedor;
    }
    
    public void excluir(int idFornecedorcomissaocursoproduto) throws SQLException {
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("select f from Fornecedorcomissaocursoproduto f where f.idfornecedorcomissaocursoproduto=" + idFornecedorcomissaocursoproduto);
        if (q.getResultList().size()>0){
        	Fornecedorcomissaocursoproduto fornecedorcomissaocursoproduto = (Fornecedorcomissaocursoproduto) q.getResultList().get(0);
            manager.remove(fornecedorcomissaocursoproduto);
        }
        tx.commit();
    }
    
    public Fornecedorcomissaocursoproduto pesquisar(int idfornecedorcomissaocurso, int idprodutoOrcamento) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select f from Fornecedorcomissaocursoproduto f where f.fornecedorcomissaocurso.idfornecedorcomissao=" + idfornecedorcomissaocurso  
        		+ " and f.produtosorcamento.idprodutosOrcamento=" +idprodutoOrcamento);
        Fornecedorcomissaocursoproduto fornecedorproduto = null;
        if (q.getResultList().size()>0){
        	fornecedorproduto = (Fornecedorcomissaocursoproduto) q.getResultList().get(0);
        }
        return fornecedorproduto;
    }
    
    
}
