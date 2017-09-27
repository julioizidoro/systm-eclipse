package br.com.travelmate.bean;

import java.util.ArrayList;
import java.util.List;

import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Produtosorcamentoindice;

public class DestinoParceirosBean {

	public String retornarSqlPaisProduto(Departamento departamento) {
		String sql = "SELECT p FROM Paisproduto p";
		if (departamento != null && departamento.getDepartamentoprodutoList() != null
				&& departamento.getDepartamentoprodutoList().size() > 0) {
			sql = sql + " WHERE (p.produtos.idprodutos="
					+ departamento.getDepartamentoprodutoList().get(0).getProdutos().getIdprodutos();
			for (int i = 1; i < departamento.getDepartamentoprodutoList().size(); i++) {
				sql = sql + " OR p.produtos.idprodutos="
						+ departamento.getDepartamentoprodutoList().get(i).getProdutos().getIdprodutos();
			}
			sql = sql + ")";
		}
		sql = sql + " GROUP BY p.pais.idpais ORDER BY p.pais.nome";
		return sql;
	}

	public String retornarSqlCidadePaisProduto(Cidade cidade, Departamento departamento) {
		String sql = "SELECT c FROM Cidadepaisproduto c WHERE " + "c.cidade.idcidade=" + cidade.getIdcidade();
		if (departamento != null && departamento.getDepartamentoprodutoList() != null
				&& departamento.getDepartamentoprodutoList().size() > 0) {
			sql = sql + " AND (c.paisproduto.produtos.idprodutos="
					+ departamento.getDepartamentoprodutoList().get(0).getProdutos().getIdprodutos();
			for (int j = 1; j < departamento.getDepartamentoprodutoList().size(); j++) {
				sql = sql + " OR c.paisproduto.produtos.idprodutos="
						+ departamento.getDepartamentoprodutoList().get(j).getProdutos().getIdprodutos();
			}
			sql = sql + ")";
		} 
		return sql;
	}

	public String retornarSqlFornecedorCidadeIdiomaProduto(Fornecedor fornecedor,
			Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto, List<Produtosorcamento> listaprodutosorcamento,
			Produtosorcamentoindice produtosorcamentoindice, Departamento departamento, EstrelasBean estrela,
			Paisproduto pais, Cidadepaisproduto cidadepaisproduto, Fornecedorcidade fornecedorcidade) {
		String sql = "SELECT f FROM Fornecedorcidadeidiomaproduto f WHERE"
				+ " f.fornecedorcidadeidioma.fornecedorcidade.ativo=1";
		if(fornecedorcidade!=null && fornecedorcidade.getIdfornecedorcidade()!=null) {
			sql = sql + " AND f.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorcidade.getIdfornecedorcidade();
		}
		if (fornecedor != null && fornecedor.getIdfornecedor() != null) {
			sql = sql + " AND f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor();
		}
		if(cidadepaisproduto!=null && cidadepaisproduto.getIdcidadepaisproduto()!=null) {
			sql = sql + " AND f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
					+ cidadepaisproduto.getCidade().getIdcidade();
		}
		if (fornecedorcidadeidiomaproduto != null
				&& fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto() != null) {
			sql = sql + " AND f.produtosorcamento.idprodutosOrcamento="
					+ fornecedorcidadeidiomaproduto.getProdutosorcamento().getIdprodutosOrcamento();
		} else if (listaprodutosorcamento != null && listaprodutosorcamento.size() > 0) {
			sql = sql + " AND (f.produtosorcamento.idprodutosOrcamento="
					+ listaprodutosorcamento.get(0).getIdprodutosOrcamento();
			for (int i = 1; i < listaprodutosorcamento.size(); i++) {
				sql = sql + " OR f.produtosorcamento.idprodutosOrcamento="
						+ listaprodutosorcamento.get(i).getIdprodutosOrcamento();
			}
			sql = sql + ")";
		} else if (produtosorcamentoindice != null && produtosorcamentoindice.getIdprodutosorcamentoindice() != null) {
			sql = sql
					+ " AND f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
					+ produtosorcamentoindice.getIdprodutosorcamentoindice();
		}
		if (departamento != null && departamento.getDepartamentoprodutoList() != null
				&& departamento.getDepartamentoprodutoList().size() > 0) {
			sql = sql + " AND (f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="
					+ departamento.getDepartamentoprodutoList().get(0).getProdutos().getIdprodutos();
			for (int i = 0; i < departamento.getDepartamentoprodutoList().size(); i++) {
				sql = sql + " OR f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="
						+ departamento.getDepartamentoprodutoList().get(i).getProdutos().getIdprodutos();
			}
			sql = sql + ")";
		}
		if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
			if (estrela.isToptmstar()) {
				sql = sql + " AND f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
			}
			sql = sql + " AND f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
		}
		if (pais != null && pais.getIdpaisproduto() != null) {
			sql = sql + " AND f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ pais.getPais().getIdpais();
		}
		if (departamento != null && departamento.getDepartamentoprodutoList() != null
				&& departamento.getDepartamentoprodutoList().size() > 0) {
			sql = sql + " AND (f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="
					+ departamento.getDepartamentoprodutoList().get(0).getProdutos().getIdprodutos();
			for (int i = 0; i < departamento.getDepartamentoprodutoList().size(); i++) {
				sql = sql + " OR f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="
						+ departamento.getDepartamentoprodutoList().get(i).getProdutos().getIdprodutos();
			}
			sql = sql + ")";
		} 
		return sql;
	}

	public String retonarSqlFornecedorCidade(Fornecedor fornecedor, Departamento departamento, EstrelasBean estrela,
			Paisproduto pais, Cidadepaisproduto cidadepaisproduto) {
		String sql = "SELECT f FROM Fornecedorcidade f WHERE f.ativo=1";
		if(cidadepaisproduto!=null && cidadepaisproduto.getIdcidadepaisproduto()!=null) {
			sql = sql + " AND f.cidade.idcidade=" + cidadepaisproduto.getCidade().getIdcidade();
		}else if(pais!=null && pais.getIdpaisproduto()!=null) {
			sql = sql + " AND f.cidade.pais.idpais=" + pais.getPais().getIdpais();
		}
		if (fornecedor != null) {
			sql = sql + " AND f.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
		}
		if (departamento != null && departamento.getDepartamentoprodutoList() != null
				&& departamento.getDepartamentoprodutoList().size() > 0) {
			sql = sql + " AND (f.produtos.idprodutos="
					+ departamento.getDepartamentoprodutoList().get(0).getProdutos().getIdprodutos();
			for (int i = 0; i < departamento.getDepartamentoprodutoList().size(); i++) {
				sql = sql + " OR f.produtos.idprodutos="
						+ departamento.getDepartamentoprodutoList().get(i).getProdutos().getIdprodutos();
			}
			sql = sql + ")";
		}
		if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
			if (estrela.isToptmstar()) {
				sql = sql + " AND f.toptmstar=true";
			}
			sql = sql + " AND f.numestrelas=" + estrela.getNumero(); 
		} 
		return sql;
	}

	public List<EstrelasBean> gerarEstrelaBean() {
		List<EstrelasBean> listaEstrela = new ArrayList<EstrelasBean>();
		EstrelasBean estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(0);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("115");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelacinza.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(2);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("40");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela2.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(3);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("65");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela3.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(3);
		estrelasBean.setToptmstar(true);
		estrelasBean.setTamanho("65");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelatop3.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(4);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("90");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela4.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(4);
		estrelasBean.setToptmstar(true);
		estrelasBean.setTamanho("90");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelatop4.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(5);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("115");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela5.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(5);
		estrelasBean.setToptmstar(true);
		estrelasBean.setTamanho("115");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelatop5.png");
		listaEstrela.add(estrelasBean);
		return listaEstrela;
	}

}
