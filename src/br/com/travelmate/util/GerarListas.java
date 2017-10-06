/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.util;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.CargoFacade;
import br.com.travelmate.facade.CidadePaisProdutosFacade;

import java.util.ArrayList;
import java.util.List;

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FornecedorFinanceiroProdutoFacade;
import br.com.travelmate.facade.PacoteTrechoFacade;
import br.com.travelmate.facade.PacotesFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cargo;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorfinanceiroproduto;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;

/**
 *
 * @author Wolverine
 */
public class GerarListas {

	public static List<Unidadenegocio> listarUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		return unidadeNegocioFacade.listar(true);
	}

	public static List<Pacotes> listarPacotes(String sql) {
		PacotesFacade pacotesFacade = new PacotesFacade();
		List<Pacotes> listaPacotes = pacotesFacade.consultar(sql);
		if (listaPacotes == null) {
			listaPacotes = new ArrayList<Pacotes>();
		}
		return listaPacotes;
	}

	public static List<Usuario> listarUsuarios(String sql) {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		List<Usuario> listaUsuario = usuarioFacade.consultar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public static List<Pacotetrecho> listarPacoteTrecho(String sql) {
		PacoteTrechoFacade pacoteTrechoFacade = new PacoteTrechoFacade();
		List<Pacotetrecho> lista = pacoteTrechoFacade.listar(sql);
		if (lista == null) {
			lista = new ArrayList<Pacotetrecho>();
		}
		return lista;
	}

	public static List<Fornecedorcidade> listarFornecedorCidade(int idProduto, int idCidade) {
		String sql = "SELECT f From Fornecedorcidade f where f.produtos.idprodutos=" + idProduto
				+ " and f.cidade.idcidade=" + idCidade + " and f.work=TRUE order by f.fornecedor.nome";
		FornecedorCidadeFacade fornecedorCidadeFacede = new FornecedorCidadeFacade();
		List<Fornecedorcidade> listafornecedor = fornecedorCidadeFacede.listar(sql);
		if (listafornecedor == null) {
			listafornecedor = new ArrayList<Fornecedorcidade>();
		}
		return listafornecedor;
	}

	public static List<Fornecedorcidade> listarFornecedorCidadeWork(int idProduto, int idCidade) {
		String sql = "SELECT f From Fornecedorcidade f where f.produtos.idprodutos=" + idProduto
				+ " and f.cidade.idcidade=" + idCidade + " and f.work=FALSE order by f.fornecedor.nome";
		FornecedorCidadeFacade fornecedorCidadeFacede = new FornecedorCidadeFacade();
		List<Fornecedorcidade> listafornecedor = fornecedorCidadeFacede.listar(sql);
		if (listafornecedor == null) {
			listafornecedor = new ArrayList<Fornecedorcidade>();
		}
		return listafornecedor;
	}

	public static List<Produtos> listarProdutos(String nome) {
		ProdutoFacade produtoFacade = new ProdutoFacade();
		List<Produtos> lista = produtoFacade.listarProdutos(nome);
		if (lista == null) {
			lista = new ArrayList<Produtos>();
		}
		return lista;
	}

	public static List<Banco> listarBancos() {
		BancoFacade bancoFacade = new BancoFacade();
		List<Banco> lista = bancoFacade.listar();
		if (lista == null) {
			lista = new ArrayList<Banco>();
		}
		return lista;
	}

	public static List<Tipocontato> listarTipoContato(String sql) {
		TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
		List<Tipocontato> listaTipocontato = tipoContatoFacade.lista(sql);
		if (listaTipocontato == null) {
			listaTipocontato = new ArrayList<Tipocontato>();
		}
		return listaTipocontato;
	}

	public static List<Fornecedor> listarFornecedor() {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		String sql = "SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome";
		List<Fornecedor> listarFornecedor = fornecedorFacade.listar(sql);
		if (listarFornecedor == null) {
			listarFornecedor = new ArrayList<Fornecedor>();
		}
		return listarFornecedor;
	}

	public static List<Fornecedorcidade> listarFornecedorTurismo(int idProduto, int idCidade, String tipo) {
		List<Fornecedorcidade> listafornecedor = new ArrayList<Fornecedorcidade>();
		List<Fornecedorcidade> listafinal = new ArrayList<Fornecedorcidade>();
		String sql = "SELECT f FROM Fornecedorfinanceiroproduto f WHERE f.produto='" + tipo + "'";
		FornecedorFinanceiroProdutoFacade fornecedorFinanceiroProdutoFacade = new FornecedorFinanceiroProdutoFacade();
		List<Fornecedorfinanceiroproduto> listaProdutos = fornecedorFinanceiroProdutoFacade.listar(sql);
		if (listaProdutos != null && listaProdutos.size() > 0) {
			sql = "SELECT f FROM Fornecedorcidade f WHERE f.produtos.idprodutos=" + idProduto
					+ " AND f.cidade.idcidade=" + idCidade + " ORDER BY f.fornecedor.nome";
			FornecedorCidadeFacade fornecedorCidadeFacede = new FornecedorCidadeFacade();
			listafornecedor = fornecedorCidadeFacede.listar(sql);
			if (listafornecedor == null) {
				listafornecedor = new ArrayList<Fornecedorcidade>();
			} else {
				for (int i = 0; i < listafornecedor.size(); i++) {
					boolean possui = false;
					for (int j = 0; j < listaProdutos.size(); j++) {
						int idfornecedor = listaProdutos.get(j).getFornecedorfinanceiro().getFornecedor()
								.getIdfornecedor();
						if (idfornecedor == listafornecedor.get(i).getFornecedor().getIdfornecedor()) {
							possui = true;
						}
					}
					if (possui) {
						listafinal.add(listafornecedor.get(i));
					}
				}
			}
		}
		return listafinal;
	}

	public static List<Fornecedorcidade> listarFornecedorSeguro(int idproduto) {
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		String sql = "SELECT f From Fornecedorcidade f where f.produtos.idprodutos=" + idproduto
				+ " order by f.fornecedor.nome DESC";
		List<Fornecedorcidade> listarFornecedor = fornecedorCidadeFacade.listar(sql);
		if (listarFornecedor == null) {
			listarFornecedor = new ArrayList<Fornecedorcidade>();
		}
		return listarFornecedor;
	}

	public static List<Cargo> listarCargo() {
		String sql = "SELECT c FROM Cargo c ORDER BY c.nome";
		CargoFacade cargoFacade = new CargoFacade();
		List<Cargo> lista = cargoFacade.lista(sql);
		return lista;
	}

	public static List<Paisproduto> listarPaises(int idproduto) {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		List<Paisproduto> listaPais = paisProdutoFacade.listar(idproduto);
		return listaPais;
	}

	public static List<Cidadepaisproduto> listarCidade(Paisproduto paisproduto) { 
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		List<Cidadepaisproduto> listaCidade = cidadePaisProdutosFacade.listar(
				"SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.idpaisproduto=" + paisproduto.getIdpaisproduto()); 
		return listaCidade;
	}
}
