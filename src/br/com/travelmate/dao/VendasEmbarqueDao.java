package br.com.travelmate.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.travelmate.model.Vendasembarque;

public class VendasEmbarqueDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	public Vendasembarque salvar(Vendasembarque vendasEmbarque) {
		vendasEmbarque = manager.merge(vendasEmbarque);
		return vendasEmbarque;
	}
	

	

}
