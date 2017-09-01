/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.util.Formatacao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Julio
 */
public class ContasPagarDao {
    
    public Contaspagar salvar(Contaspagar conta) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        conta = manager.merge(conta);
        tx.commit();
        manager.close();
        return conta;
    }
    
    public List<Contaspagar> listar(String sql)throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Contaspagar> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idContasPagar) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Contaspagar c where c.idcontaspagar=" + idContasPagar);
        if (q.getResultList().size()>0){
            Contaspagar contaspagar = (Contaspagar) q.getResultList().get(0);
            manager.remove(contaspagar);
        }
        tx.commit();
        manager.close();
    }
    
    public float gerarSaldoInicial(Date dataInicio, int idbanco)throws SQLException {
    	EntityManager manager;
    	float saldo= 0.0f;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createNativeQuery("SELECT distinct sum(valorsaida) as totalsaida FROM contaspagar" +
        									" where datacompensacao<'" + Formatacao.ConvercaoDataSql(dataInicio) + "' and banco_idbanco=" + idbanco);
        Double totalsaida;
        totalsaida=0.0;
        if (q.getResultList().size()>0){
        	totalsaida =  (Double) q.getSingleResult();
        	if(totalsaida==null){
        		totalsaida=0.0;
        	}
        }
        q = manager.createNativeQuery("SELECT distinct sum(valorentrada) as totalentrada FROM contaspagar" +
				" where datacompensacao<'" + Formatacao.ConvercaoDataSql(dataInicio) + "' and banco_idbanco=" + idbanco);
        Double totalentrada;
        totalentrada=0.0;
        if (q.getResultList().size()>0){
        	totalentrada = (Double) q.getSingleResult();
        	if(totalentrada==null){
        		totalentrada=0.0;
        	}
        }
        saldo = totalentrada.floatValue() - totalsaida.floatValue();
        manager.close();
    	return saldo;
    }
}
