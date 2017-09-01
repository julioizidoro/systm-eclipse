/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.aupair.controle;

import java.util.Date;

/**
 *
 * @author Kamila
 */
public class MediaMatchBean {

	private int idvenda;
	private String nomeCliente;
	private Date dataOnline;
	private Date dataMatch;
	private String numerosemanas;

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getDataOnline() {
		return dataOnline;
	}

	public void setDataOnline(Date dataOnline) {
		this.dataOnline = dataOnline;
	}

	public Date getDataMatch() {
		return dataMatch;
	}

	public void setDataMatch(Date dataMatch) {
		this.dataMatch = dataMatch;
	}

	public String getNumerosemanas() {
		return numerosemanas;
	}

	public void setNumerosemanas(String numerosemanas) {
		this.numerosemanas = numerosemanas;
	}

}
