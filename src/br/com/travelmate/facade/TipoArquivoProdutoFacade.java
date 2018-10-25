package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.dao.TipoArquivoProdutoDao;
import br.com.travelmate.model.Tipoarquivoproduto; 

public class TipoArquivoProdutoFacade {

	TipoArquivoProdutoDao tipoArquivoDao;
    
    public Tipoarquivoproduto salvar(Tipoarquivoproduto tipoarquivo) {
    	tipoArquivoDao = new TipoArquivoProdutoDao();
        try {
            return tipoArquivoDao.salvar(tipoarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(TipoArquivoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Tipoarquivoproduto> listar(String sql) throws SQLException{
    	tipoArquivoDao = new TipoArquivoProdutoDao();
        return tipoArquivoDao.listar(sql);
    }
   
    public Tipoarquivoproduto consultar(int idTipoArquivos) throws SQLException{
    	tipoArquivoDao = new TipoArquivoProdutoDao();
        return tipoArquivoDao.consultar(idTipoArquivos);
    }
    
    public Tipoarquivoproduto consultarArquivoProduto(String sql) throws SQLException{
    	tipoArquivoDao = new TipoArquivoProdutoDao();
        return tipoArquivoDao.consultarArquivoProduto(sql);
    }
    
    
    public void excluir(int idTipoArquivos) {
    	tipoArquivoDao = new TipoArquivoProdutoDao();
        try {
        	tipoArquivoDao.excluir(idTipoArquivos);
        } catch (SQLException ex) {
            Logger.getLogger(TipoArquivoProdutoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
