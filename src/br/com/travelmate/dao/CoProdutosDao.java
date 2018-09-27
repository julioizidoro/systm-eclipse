package br.com.travelmate.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Coprodutos;


public class CoProdutosDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
    @Transactional
    public Coprodutos salvar(Coprodutos coObrigatorio) {
        coObrigatorio = manager.merge(coObrigatorio); 
        return coObrigatorio;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Coprodutos> listar(String sql){
        Query q = manager.createQuery(sql);
        List<Coprodutos> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        
        return lista;
    }
    
    
    public Coprodutos consultar(String sql) {
        Query q = manager.createQuery(sql);
        Coprodutos coprodutos = null;
        if(q.getResultList().size()>0){
            coprodutos =  (Coprodutos) q.getResultList().get(0);
        }
        return coprodutos;
    }
    
    @Transactional
    public void excluir(int id) {
		Coprodutos coprodutos = manager.find(Coprodutos.class, id);
        manager.remove(coprodutos);
    }
}
