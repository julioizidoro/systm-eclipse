package br.com.travelmate.bean;

import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.model.Produtos;

public class ConsultaBean {
	
	public static Produtos getProdtuo(int idProduto){
		ProdutoFacade produtoFacade = new ProdutoFacade();
		Produtos produto = produtoFacade.consultar(idProduto);
		return produto;
	}

}
