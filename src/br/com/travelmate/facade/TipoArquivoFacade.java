package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.TipoArquivoDao;
import br.com.travelmate.model.Tipoarquivo;

public class TipoArquivoFacade {

	TipoArquivoDao tipoArquivoDao;
    
    public Tipoarquivo salvar(Tipoarquivo tipoarquivo) {
    	tipoArquivoDao = new TipoArquivoDao();
        try {
            return tipoArquivoDao.salvar(tipoarquivo);
        } catch (SQLException ex) {
            Logger.getLogger(TipoArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Tipoarquivo> listar(String sql) throws SQLException{
    	tipoArquivoDao = new TipoArquivoDao();
        return tipoArquivoDao.listar(sql);
    }
   
    public Tipoarquivo consultar(int idTipoArquivos) throws SQLException{
    	tipoArquivoDao = new TipoArquivoDao();
        return tipoArquivoDao.consultar(idTipoArquivos);
    }
    
    public void excluir(int idTipoArquivos) {
    	tipoArquivoDao = new TipoArquivoDao();
        try {
        	tipoArquivoDao.excluir(idTipoArquivos);
        } catch (SQLException ex) {
            Logger.getLogger(TipoArquivoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
