package br.com.travelmate.bean;

import java.util.List;


import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Contasreceber;



public class RelatorioCobrancaBean {
	
	private Cobranca cobranca;
	private List<Contasreceber> listaContas;
	private float valorTotal;
	
	
	public Cobranca getCobranca() {
		return cobranca;
	}
	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}
	public List<Contasreceber> getListaContas() {
		return listaContas;
	}
	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}
	
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
}
