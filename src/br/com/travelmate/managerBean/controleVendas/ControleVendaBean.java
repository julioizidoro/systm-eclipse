package br.com.travelmate.managerBean.controleVendas;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

public class ControleVendaBean {

	
	private Vendas vendas;
	private Date dataInicio;
	private Date dataInscricao;
	
	
	public Vendas getVendas() {
		return vendas;
	}
	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataInscricao() {
		return dataInscricao;
	}
	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	
	
	
	
	
}
