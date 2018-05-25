package br.com.travelmate.bean;

import java.util.Date;

public class UsuarioBean {

	private int codigosystm;
	private String nome;
	private String email;
	private String senha;
	private Date datanascimento;
	private String celular;
	public int getCodigosystm() {
		return codigosystm;
	}
	public void setCodigosystm(int codigosystm) {
		this.codigosystm = codigosystm;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDatanascimento() {
		return datanascimento;
	}
	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	
	
	
}
