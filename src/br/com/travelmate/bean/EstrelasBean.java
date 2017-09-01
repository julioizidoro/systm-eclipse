package br.com.travelmate.bean;

public class EstrelasBean {
	 
	private int numero;
	private String caminho;
	private boolean toptmstar;
	private String tamanho;
	 
	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isToptmstar() {
		return toptmstar;
	}

	public void setToptmstar(boolean toptmstar) {
		this.toptmstar = toptmstar;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
 
}
