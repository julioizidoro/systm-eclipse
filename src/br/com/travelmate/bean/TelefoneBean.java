package br.com.travelmate.bean;

public class TelefoneBean {
	
	
	private String numero;
	private String tiponumero;
	
	
	public TelefoneBean() {
		tiponumero = "Fixo";
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getTiponumero() {
		return tiponumero;
	}


	public void setTiponumero(String tiponumero) {
		this.tiponumero = tiponumero;
	}
	
	
	

}
