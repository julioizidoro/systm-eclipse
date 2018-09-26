package br.com.travelmate.dao;
 
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Acessounidade; 

public class AcessoUnidadeDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	 
	@Transactional
	public Acessounidade salvar(Acessounidade acessounidade){
		acessounidade = manager.merge(acessounidade);
        return acessounidade; 
    } 
    
    public Acessounidade consultar(String sql)    {
		Query q = manager.createQuery(sql); 
        Acessounidade acessounidade = null; 
        if (q.getResultList().size() > 0) {
        		acessounidade =  (Acessounidade) q.getResultList().get(0);
        } 
        return acessounidade;
    }
     
    @Transactional
    public void excluir(int idacessounidade)   {
		Acessounidade acessounidade = manager.find(Acessounidade.class, idacessounidade);
        manager.remove(acessounidade);  
    }
     
    @SuppressWarnings("unchecked")
	public List<Acessounidade> lista(String sql)  {
	    Query q = manager.createQuery(sql);
        List<Acessounidade> lista = q.getResultList();
        return lista; 
    }

}
