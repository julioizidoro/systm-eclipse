package br.com.travelmate.bean;

import java.util.List;

import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Produtos;

public class ProductRunnersBean {
	
	private Produtos produto;
	private List<Corridaprodutomes> listaCorridaUsuario;
	
	
	public Produtos getProduto() {
		return produto;
	}
	public void setProduto(Produtos produto) {
		this.produto = produto;
	}
	public List<Corridaprodutomes> getListaCorridaUsuario() {
		return listaCorridaUsuario;
	}
	public void setListaCorridaUsuario(List<Corridaprodutomes> listaCorridaUsuario) {
		this.listaCorridaUsuario = listaCorridaUsuario;
	}
	
	
	
	
	

}
