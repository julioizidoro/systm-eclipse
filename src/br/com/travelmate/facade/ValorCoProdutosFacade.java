package br.com.travelmate.facade;

import br.com.travelmate.dao.ValorCoProdutoDao;
import br.com.travelmate.model.Valorcoprodutos;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila Rodrigues
 */
public class ValorCoProdutosFacade {
    
    private ValorCoProdutoDao valorCoProdutosDao;
    
    public Valorcoprodutos salvar(Valorcoprodutos valorcoprodutos){
        valorCoProdutosDao = new ValorCoProdutoDao();
        try {
            return valorCoProdutosDao.salvar(valorcoprodutos);
        } catch (SQLException ex) {
            Logger.getLogger(ValorCoProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public List<Valorcoprodutos> listar(String sql){
        valorCoProdutosDao = new ValorCoProdutoDao();
        try {
            return valorCoProdutosDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValorCoProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   public void excluir(int idValorCoProduto) {
	   valorCoProdutosDao = new ValorCoProdutoDao();
       try {
    	   valorCoProdutosDao.excluir(idValorCoProduto);
       } catch (SQLException ex) {
           Logger.getLogger(ValorCoProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public Valorcoprodutos consultar(int idValorCoProduto) {
	   valorCoProdutosDao = new ValorCoProdutoDao();
	   try {
		return valorCoProdutosDao.consultar(idValorCoProduto);
	} catch (SQLException ex) {
		Logger.getLogger(ValorCoProdutosFacade.class.getName()).log(Level.SEVERE, null, ex);
		return null;
	}
   }
}
