/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentocursoformapagamento;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.model.Produtosorcamento;
/**
 *
 * @author Wolverine
 */
@SuppressWarnings("unchecked")
public class OrcamentoCursoDao {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Orcamentocurso salvar(Orcamentocurso orcamento) {
        orcamento = manager.merge(orcamento);
        return orcamento;
    }
    
    public Orcamentocurso consultar(int idOrcamento) {
        Query q = manager.createQuery("select o from Orcamentocurso o where o.idorcamentoCurso=" + idOrcamento);
        Orcamentocurso orcamento = null;
        if (q.getResultList().size() > 0) {
            orcamento = (Orcamentocurso) q.getResultList().get(0);
        } 
        return orcamento;
    }
    
    public List<Orcamentocurso> listarOrcamento(String sql) {
        Query q = manager.createQuery(sql);
        List<Orcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista = q.getResultList();
        }
        return lista;
    }
    
    @Transactional
    public Produtoorcamentocurso salvar(Produtoorcamentocurso produtoOrcamentoCurso) {
        produtoOrcamentoCurso = manager.merge(produtoOrcamentoCurso);
        return produtoOrcamentoCurso;
    }
    
    public List<Produtoorcamentocurso> listarProdutoOrcamentoCurso(int idOrcamento) {
        Query q = manager.createQuery("select o from Produtoorcamentocurso o where o.orcamentocurso=" + idOrcamento);
        List<Produtoorcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista =  q.getResultList();
        }
        return lista;
    }
    
    public List<Produtoorcamentocurso> listarProdutoOrcamentoCurso(String sql) {
        Query q = manager.createQuery(sql);
        List<Produtoorcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista =  q.getResultList();
        }
        return lista;
    }
    
    public Produtosorcamento consultarProdutoOrcamentoCurso(int idProdutoOrcamentoCurso){
        Query q = manager.createQuery("select p from Produtosorcamento p where p.idprodutosOrcamento=" + idProdutoOrcamentoCurso);
        Produtosorcamento produtoorcamento = null;
        if (q.getResultList().size() > 0) {
            produtoorcamento =  (Produtosorcamento) q.getResultList().get(0);
        }
        return produtoorcamento;
    }
    
    @Transactional
    public void excluirProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) {
        Produtoorcamentocurso produtoorcamentocurso = manager.find(Produtoorcamentocurso.class, idProdutoOrcamentoCurso);
        manager.remove(produtoorcamentocurso);
    }
    
    
    public Orcamentocurso consultarCliente(int idCliente){
        Query q = manager.createQuery("select o from Orcamentocurso o where o.cliente=" + idCliente +
                " and o.idCurso=0");
        Orcamentocurso orcamentocurso=null;
        if (q.getResultList().size() > 0) {
            orcamentocurso=  (Orcamentocurso) q.getResultList().get(0);
        }
        return orcamentocurso;
    }
    
    public Produtoorcamentocurso consultarProdutoOrcamentoCuros(int idProdutoOrcamentoCurso) {
        Produtoorcamentocurso produtoOrcamentoCurso = manager.find(Produtoorcamentocurso.class, idProdutoOrcamentoCurso);
        return produtoOrcamentoCurso;
    }
    
    @Transactional
    public void excluirOrcamentoCuros(int idOrcamento){
        Orcamentocurso orcamento = manager.find(Orcamentocurso.class, idOrcamento);
        manager.remove(orcamento);
    }
    
    public List<Orcamentocurso> listarOrcamentoCurso() {
        Query q = manager.createQuery("Select o from Orcamentocurso o where o.fornecedorcidade.idfornecedorcidade=1");
        List<Orcamentocurso> lista = null;
        if (q.getResultList().size() > 0) {
            lista =  q.getResultList();
        }
        return lista;
    }
    
    @Transactional
    public Orcamentocursoformapagamento salvarFormaPagamento(Orcamentocursoformapagamento orcamento) {
        orcamento = manager.merge(orcamento);
        return orcamento;
    }
     
     public Orcamentocursoformapagamento consultarFormaPagamento(int idOrcamento) {
        Query q = manager.createQuery("select o from Orcamentocursoformapagamento o where o.orcamentocurso.idorcamentoCurso=" + idOrcamento);
        Orcamentocursoformapagamento forma = null;
        if (q.getResultList().size() > 0) {
            forma =  (Orcamentocursoformapagamento) q.getResultList().get(0);
        }
        return forma;
    }
}
