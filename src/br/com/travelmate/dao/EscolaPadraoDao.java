package br.com.travelmate.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Escolapadrao;

public class EscolaPadraoDao {
	
	public Escolapadrao pesquisar(){
		EntityManager manager;
		manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select e from Escolapadrao e");
        Escolapadrao escolaPadrao = (Escolapadrao) q.getSingleResult();
        manager.close();
        return escolaPadrao;
	}

}
