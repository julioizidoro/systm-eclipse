package br.com.travelmate.bean;

import br.com.travelmate.model.Produtos;

public class UsuarioProdutoRunnersBean {
	
	private int pontos;
	private Produtos produtos;
	private String nomeConsultor;
	private String nomeUnidade;
	private String foto;
	
	
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public String getNomeUnidade() {
		return nomeUnidade;
	}
	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	public Produtos getProdutos() {
		return produtos;
	}
	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}
	public String getNomeConsultor() {
		return nomeConsultor;
	}
	public void setNomeConsultor(String nomeConsultor) {
		this.nomeConsultor = nomeConsultor;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	
	

	
	
	
	

}
