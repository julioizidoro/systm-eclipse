package br.com.travelmate.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Arquivoshitorico;

public class ArquivosHistoricoDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
    public Arquivoshitorico salvar(Arquivoshitorico arquivoshistorico) {
		arquivoshistorico = manager.merge(arquivoshistorico);
        return arquivoshistorico;
    }
    
    public Arquivoshitorico consultar(int idArquivosHistorico) {
        Arquivoshitorico arquivoshistorico = manager.find(Arquivoshitorico.class, idArquivosHistorico);
        return arquivoshistorico;
    }
    
    @Transactional
    public void excluir(int idArquivosHistorico) {
    	Arquivoshitorico arquivoshistorico = manager.find(Arquivoshitorico.class, idArquivosHistorico);
        manager.remove(arquivoshistorico);
    }
    
    public List<Arquivoshitorico> lista(String sql) {
        Query q = manager.createQuery(sql);
        List<Arquivoshitorico> lista = q.getResultList();
        return lista;
    }

}
