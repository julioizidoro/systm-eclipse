package br.com.travelmate.facade;


import br.com.travelmate.dao.FornecedorFeriasDao;
import br.com.travelmate.model.Fornecedorferias;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila Rodrigues
 */
public class FornecedorFeriasFacade {
    
    private FornecedorFeriasDao fornecedorFeriasDao;
    
    public Fornecedorferias salvar(Fornecedorferias fornecedorferias){
        fornecedorFeriasDao = new FornecedorFeriasDao();
        try {
            return fornecedorFeriasDao.salvar(fornecedorferias);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Fornecedorferias> listar(String sql){
        fornecedorFeriasDao = new FornecedorFeriasDao();
        try {
            return fornecedorFeriasDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FornecedorCidadeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
