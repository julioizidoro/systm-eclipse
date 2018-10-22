package br.com.travelmate.managerBean.higherEducation.controle;

import java.util.Date;

import br.com.travelmate.dao.HeControleDao;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Hecontrole;

public class HeControleBean {

	public HeControleBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void salvar(HeControleDao heControleDao, He he) {
		Hecontrole controle = new Hecontrole();
		controle.setSituacaoaplicacao("Processo");
		controle.setDatafichafinalizada(new Date());
		controle.setImpresso(false);
		controle.setVistoemitido(false);
		controle.setValorcomissao(0.0f);
		controle.setHe(he);
		heControleDao.salvar(controle);
	}
	
	public void cancelar(HeControleDao heControleDao, He he) {
		String sql = "SELECT h FROM Hecontrole h where h.he.idhe=" + he.getIdhe();
		Hecontrole controle = heControleDao.consultar(sql);
		if (controle!=null) {
			controle.setSituacaoaplicacao("Cancelado");
			heControleDao.salvar(controle);
		}
	}

}
