/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Copromocao;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursoferiado;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Wolverine
 */
public class FornecedorProdutosBean {
    
        private Fornecedorcidadeidioma fornecedorcidadeidioma;
        private List<ProdutoFornecedorBean> listaProdutoFornecedor;
        private String descricaoPromocao;
		private Ocurso ocurso;
        private Cambio cambio;
        private List<ProdutosOrcamentoBean> listaOpcionais;
        private List<ProdutosOrcamentoBean> listaAcomodacoes;
        private List<ProdutosOrcamentoBean> listaTransfer;
        private List<ProdutosOrcamentoBean> listaAcOpcional;
        private List<Ocursoferiado> listaOcrusoFeriado;
        private Date dataConsulta;
        private int linhaFornecedor;

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
        return fornecedorcidadeidioma;
    }

    public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
        this.fornecedorcidadeidioma = fornecedorcidadeidioma;
    }
    
    public Ocurso getOcurso() {
        return ocurso;
    }

    public void setOcurso(Ocurso ocurso) {
        this.ocurso = ocurso;
    }

    
    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

	public List<ProdutoFornecedorBean> getListaProdutoFornecedor() {
		return listaProdutoFornecedor;
	}

	public void setListaProdutoFornecedor(List<ProdutoFornecedorBean> listaProdutoFornecedor) {
		this.listaProdutoFornecedor = listaProdutoFornecedor;
	}

	

	public String getDescricaoPromocao() {
		return descricaoPromocao;
	}

	public void setDescricaoPromocao(String descricaoPromocao) {
		this.descricaoPromocao = descricaoPromocao;
	}

	public List<ProdutosOrcamentoBean> getListaOpcionais() {
		return listaOpcionais;
	}

	public void setListaOpcionais(List<ProdutosOrcamentoBean> listaOpcionais) {
		this.listaOpcionais = listaOpcionais;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoes() {
		return listaAcomodacoes;
	}

	public void setListaAcomodacoes(List<ProdutosOrcamentoBean> listaAcomodacoes) {
		this.listaAcomodacoes = listaAcomodacoes;
	}

	public List<ProdutosOrcamentoBean> getListaTransfer() {
		return listaTransfer;
	}

	public void setListaTransfer(List<ProdutosOrcamentoBean> listaTransfer) {
		this.listaTransfer = listaTransfer;
	}

	public List<ProdutosOrcamentoBean> getListaAcOpcional() {
		return listaAcOpcional;
	}

	public void setListaAcOpcional(List<ProdutosOrcamentoBean> listaAcOpcional) {
		this.listaAcOpcional = listaAcOpcional;
	}

	public List<Ocursoferiado> getListaOcrusoFeriado() {
		return listaOcrusoFeriado;
	}

	public void setListaOcrusoFeriado(List<Ocursoferiado> listaOcrusoFeriado) {
		this.listaOcrusoFeriado = listaOcrusoFeriado;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public int getLinhaFornecedor() {
		return linhaFornecedor;
	}

	public void setLinhaFornecedor(int linhaFornecedor) {
		this.linhaFornecedor = linhaFornecedor;
	}

	
    
}
