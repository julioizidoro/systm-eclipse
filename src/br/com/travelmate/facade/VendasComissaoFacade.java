package br.com.travelmate.facade;

import br.com.travelmate.dao.VendasComissaoDao;
import br.com.travelmate.model.Vendascomissao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolverine
 */
public class VendasComissaoFacade {
    
    VendasComissaoDao vendasComissaoDao;
    
    public Vendascomissao salvar(Vendascomissao vendasComissao) {
        vendasComissaoDao = new VendasComissaoDao();
        try {
            return vendasComissaoDao.salvar(vendasComissao);
        } catch (SQLException ex) {
            Logger.getLogger(VendasComissaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vendascomissao getVendaComissao(int idVenda, int idProdutos){
        try {
            vendasComissaoDao = new VendasComissaoDao();
            return vendasComissaoDao.getVendaComissao(idVenda, idProdutos);
        } catch (SQLException ex) {
            Logger.getLogger(VendasComissaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vendascomissao consultar(int idVenda){
        try {
            vendasComissaoDao = new VendasComissaoDao();
            return vendasComissaoDao.consultar(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(VendasComissaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idVendaComissao) {
        vendasComissaoDao = new VendasComissaoDao();
        try {
            vendasComissaoDao.excluir(idVendaComissao);
        } catch (SQLException ex) {
            Logger.getLogger(VendasComissaoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Vendascomissao> listar(String sql){
        vendasComissaoDao = new VendasComissaoDao();
        try {
            return vendasComissaoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VendasComissaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
