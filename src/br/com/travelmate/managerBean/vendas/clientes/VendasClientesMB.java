package br.com.travelmate.managerBean.vendas.clientes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class VendasClientesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Curso> listaCurso;
	private List<Pais> listaPais;
	private Pais pais;
	private Unidadenegocio unidadenegocio;
	private List<Fornecedor> listaFornecedor;
	private Fornecedor fornecedor;
	private List<Cidade> listaCidade;
	private Cidade cidade;
	private Date dataIniCurso;
	private Date dataFinCurso;   
	private int numeroSemana;
	
	
	@PostConstruct
	public void init(){
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		gerarListaFornecedor();
	}


	public List<Pais> getListaPais() {
		return listaPais;
	}


	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}


	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public List<Cidade> getListaCidade() {
		return listaCidade;
	}


	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}


	public Cidade getCidade() {
		return cidade;
	}


	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}


	public List<Curso> getListaCurso() {
		return listaCurso;
	}


	public void setListaCurso(List<Curso> listaCurso) {
		this.listaCurso = listaCurso;
	}
	
	public Date getDataIniCurso() {
		return dataIniCurso;
	}


	public void setDataIniCurso(Date dataIniCurso) {
		this.dataIniCurso = dataIniCurso;
	}


	public Date getDataFinCurso() {
		return dataFinCurso;
	}


	public void setDataFinCurso(Date dataFinCurso) {
		this.dataFinCurso = dataFinCurso;
	}


	public int getNumeroSemana() {
		return numeroSemana;
	}


	public void setNumeroSemana(int numeroSemana) {
		this.numeroSemana = numeroSemana;
	}


	public void gerarListaCurso(){
		String sql = "SELECT c FROM Curso c WHERE c.vendas.cliente.nome like '%%'";
		if (pais != null) {
			sql = sql + " AND c.pais like '%" + pais.getNome() + "%' ";
		}
		
		if (cidade != null) {
			sql = sql + " AND c.cidade like '%" + cidade.getNome() + "%'";
		}
		
		if (unidadenegocio != null) {
			sql = sql + " AND c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if ((dataIniCurso != null) && (dataFinCurso != null)) {
			sql = sql + " AND c.dataInicio>='" + Formatacao.ConvercaoDataSql(dataIniCurso) + "'";
			sql = sql + " AND c.dataInicio<='" + Formatacao.ConvercaoDataSql(dataFinCurso) + "'";
		}
		
		if (fornecedor != null && fornecedor.getIdfornecedor() != null) {
			sql = sql + " and c.vendas.fornecedorcidade.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
		}
		
		CursoFacade cursoFacade = new CursoFacade();
		listaCurso = cursoFacade.lista(sql);
		if (listaCurso == null) {
			listaCurso = new ArrayList<Curso>();
		}
		for (int i = 0; i < listaCurso.size(); i++) {
			numeroSemana = listaCurso.get(i).getNumeroSenamas();
		}
	}
	
	
	public void gerarListaFornecedor() {
		FornecedorFacade forncedorFacade = new FornecedorFacade();
		listaFornecedor = forncedorFacade
				.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}
	
	public void gerarListaCidade(){
		CidadeFacade cidadeFacade = new CidadeFacade();
		if (pais != null) {
			listaCidade = cidadeFacade.listar(pais.getIdpais());
		}
	}
	
	public void limparPesquisa(){
		dataFinCurso = null;
		dataIniCurso = null;
		listaCurso = new ArrayList<>();
		fornecedor = null;
		cidade = null;
		pais = null;
		numeroSemana = 0;
	}

}
