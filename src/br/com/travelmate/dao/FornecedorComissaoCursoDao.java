/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcomissaocurso;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class FornecedorComissaoCursoDao {

	public Fornecedorcomissaocurso salvar(Fornecedorcomissaocurso fornecedorcomissaocurso) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		fornecedorcomissaocurso = manager.merge(fornecedorcomissaocurso);
		tx.commit();
		return fornecedorcomissaocurso;
	}

	public List<Fornecedorcomissaocurso> listar(int idFornecedor, int idPais) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
		Query q = manager.createQuery("Select f from Fornecedorcomissaocurso f where f.fornecedor.idfornecedor="
				+ idFornecedor + " and f.pais.idpais=" + idPais);
		List<Fornecedorcomissaocurso> listaFornecedor = q.getResultList();
		return listaFornecedor;
	}

	public Fornecedorcomissaocurso consultar(int idFornecedor, int idPais) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance(); 
		Query q = manager.createQuery("Select f from Fornecedorcomissaocurso f where f.fornecedor.idfornecedor="
				+ idFornecedor + " and f.pais.idpais=" + idPais);
		Fornecedorcomissaocurso fornecedorComisao = null;
		if (q.getResultList().size() > 0) {
			fornecedorComisao = (Fornecedorcomissaocurso) q.getResultList().get(0);
		}
		return fornecedorComisao;
	}

	public void excluir(int idFornecedorcomissaocurso) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery(
				"Select f from Fornecedorcomissaocurso f where f.idfornecedorcomissao=" + idFornecedorcomissaocurso);
		if (q.getResultList().size() > 0) {
			Fornecedorcomissaocurso fornecedorcomissaocurso = (Fornecedorcomissaocurso) q.getResultList().get(0);
			manager.remove(fornecedorcomissaocurso);
		} 
		tx.commit();
	}

}
