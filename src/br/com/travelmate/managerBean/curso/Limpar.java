package br.com.travelmate.managerBean.curso;

import java.util.List;

import br.com.travelmate.dao.CoProdutosDao;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Grupoobrigatorio;

public class Limpar {
	
	
	private CoProdutosDao coProdutosDao;
	
	
	
	
	public Limpar(CoProdutosDao coProdutosDao) {
		this.coProdutosDao = coProdutosDao;
	}




	public void limparAcomodacao(){
		String sql = "Select c from Coprodutos c where c.produtosorcamento.idprodutosOrcamento=4"; 
		List<Coprodutos> listaCoproduto = coProdutosDao.listar(sql);
		if (listaCoproduto!=null){
			GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
			for(int i=0;i<listaCoproduto.size();i++){
				Grupoobrigatorio grupo = new Grupoobrigatorio();
				grupo = grupoObrigatorioFacade.consultarProduto(listaCoproduto.get(i).getIdcoprodutos());
				if (grupo!=null){
					grupoObrigatorioFacade.excluir(grupo);
				}
			}
		}
	}

}
