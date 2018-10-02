package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.FornecedorArquivoTipoDao;
import br.com.travelmate.model.Fornecedorarquivotipo;


public class FornecedorArquivoTipoFacade {

	FornecedorArquivoTipoDao fornecedorArquivoTipoDao;
    
    public Fornecedorarquivotipo salvar(Fornecedorarquivotipo tipoarquivo) {
    	fornecedorArquivoTipoDao = new FornecedorArquivoTipoDao();
        try {
            return fornecedorArquivoTipoDao.salvar(tipoarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorArquivoTipoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Fornecedorarquivotipo> listar(String sql) {
    	fornecedorArquivoTipoDao = new FornecedorArquivoTipoDao();
        try {
			return fornecedorArquivoTipoDao.listar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
    }
   
    public Fornecedorarquivotipo consultar(int idTipoArquivos) {
    	fornecedorArquivoTipoDao = new FornecedorArquivoTipoDao();
        try {
			return fornecedorArquivoTipoDao.consultar(idTipoArquivos);
		} catch (SQLException e) {
			  
			return null;
		}
    }
    
    public void excluir(int idTipoArquivos) {
    	fornecedorArquivoTipoDao = new FornecedorArquivoTipoDao();
        try {
        	fornecedorArquivoTipoDao.excluir(idTipoArquivos);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorArquivoTipoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
