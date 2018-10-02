/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.ModeloOrcamentoCursoDao;
import br.com.travelmate.model.Modeloorcamentocurso;
import br.com.travelmate.model.Modeloorcamentocursoforma;
import br.com.travelmate.model.Modeloprodutoorcamentocurso;

/**
 *
 * @author Wolverine
 */
public class ModeloOrcamentoCursoFacade {
    
    ModeloOrcamentoCursoDao modeloOrcamentoCursoDao;
    
    public Modeloorcamentocurso salvar(Modeloorcamentocurso orcamento) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.salvar(orcamento);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public Modeloorcamentocurso consultar(int idOrcamento) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.consultar(idOrcamento);
        } catch (SQLException e) {
			  
			return null;
		}
    }
    
    public Modeloprodutoorcamentocurso salvar(Modeloprodutoorcamentocurso modeloProdutoOrcamentoCurso) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.salvar(modeloProdutoOrcamentoCurso);
        } catch (SQLException e) {
			  
			return null;
		}
    }
    
    public List<Modeloprodutoorcamentocurso> listarProdutoOrcamentoCurso(int idOrcamento) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.listarProdutoOrcamentoCurso(idOrcamento);
        } catch (SQLException e) {
			  
			return null;
		}
    }
    
    public void excluirModleoProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			modeloOrcamentoCursoDao.excluirModleoProdutoOrcamentoCurso(idProdutoOrcamentoCurso);
        } catch (SQLException e) {
			  
		}
    }
    
    
    public Modeloprodutoorcamentocurso consultarProdutoOrcamentoCuros(int idProdutoOrcamentoCurso) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.consultarProdutoOrcamentoCuros(idProdutoOrcamentoCurso);
        } catch (SQLException e) {
			  
			return null;
		}
    }
    
    public Modeloorcamentocursoforma salvar(Modeloorcamentocursoforma modeloForma) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.salvar(modeloForma);
        } catch (SQLException e) {
			  
			return null;
		}
    }
     
     public Modeloorcamentocursoforma consultarFormaPagamento(int idOrcamento) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.consultarFormaPagamento(idOrcamento);
        } catch (SQLException e) {
			  
			return null;
		}
    }
     
    
    public List<Modeloorcamentocurso> listarModeloOrcamentoCurso(String sql) {
        modeloOrcamentoCursoDao = new ModeloOrcamentoCursoDao();
        try {
			return modeloOrcamentoCursoDao.listarModeloOrcamentoCurso(sql);
        } catch (SQLException e) {
			  
			return null;
		}
    }
    
}
