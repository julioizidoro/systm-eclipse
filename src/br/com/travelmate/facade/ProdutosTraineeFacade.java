package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.travelmate.dao.ProdutosTraineeDao;
import br.com.travelmate.model.Produtostrainee;
public class ProdutosTraineeFacade {

	ProdutosTraineeDao produtoTraineeDao;
	
	public Produtostrainee salvar(Produtostrainee produto) {
		produtoTraineeDao = new ProdutosTraineeDao();
		try {
			return produtoTraineeDao.salvar(produto);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Produtostrainee> listar(String sql) {
		produtoTraineeDao = new ProdutosTraineeDao();
		try {
			return produtoTraineeDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Produtostrainee consultar(String sql) {
		produtoTraineeDao = new ProdutosTraineeDao();
        try {
            return produtoTraineeDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosTraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
