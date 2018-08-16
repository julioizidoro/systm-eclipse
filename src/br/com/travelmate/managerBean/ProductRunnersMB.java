package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.UsuarioProdutoRunnersBean;
import br.com.travelmate.facade.CorridaProdutoAnoFacade;
import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.model.Corridaprodutoano;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ProductRunnersMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Produtos> listaProdutos;
	private List<UsuarioProdutoRunnersBean> listaCorrida;
	
	
	@PostConstruct
	public void init(){
		gerarListaProdutos();
		
	}   


	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}


	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	
	
	public List<UsuarioProdutoRunnersBean> getListaCorrida() {
		return listaCorrida;
	}


	public void setListaCorrida(List<UsuarioProdutoRunnersBean> listaCorrida) {
		this.listaCorrida = listaCorrida;
	}


	public String getFoto(Usuario usuario) {
		String caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (usuario != null) {
			if (usuario.isFoto()) {
				caminho = caminho + "/usuario/" + usuario.getIdusuario() + ".jpg";
			}else{
				caminho = caminho + "/usuario/0.png";
			}
		}else{
			caminho = caminho + "/usuario/0.png";
		}
		return caminho;
	}
	
	
	public Integer getPontuacao(Produtos produtos) {
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		String sql = "SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + " and c.produtos.idprodutos=" + produtos.getIdprodutos()
						+ " ORDER BY c.pontos DESC";
		listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		if (listaCorridaMes.size() > 0) {
			return listaCorridaMes.get(0).getPontos();
		}
		return 0;
	}
	
	
	public String graficoTop3Mes(Produtos produtos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("produtos", produtos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 380); 
		RequestContext.getCurrentInstance().openDialog("graficoTop3Mes", options,null);
		return "";
	}
	
	
	public String graficoTopAno(Produtos produtos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("produtos", produtos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 580); 
		RequestContext.getCurrentInstance().openDialog("graficoTopAno", options,null);
		return "";
	}   
	
	public void gerarListaProdutos(){
		UsuarioProdutoRunnersBean corrida;
		listaCorrida = new ArrayList<UsuarioProdutoRunnersBean>();
		ProdutoFacade produtoFacade = new ProdutoFacade();
		listaProdutos = produtoFacade.listarProdutosSql("SELECT p From Produtos p WHERE p.produtorunners=true Order By p.ordem ");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtos>();
		}
		for (int i = 0; i < listaProdutos.size(); i++) {
			corrida = new UsuarioProdutoRunnersBean();
			if (listaProdutos.get(i).getIdprodutos() ==1) {
				listaProdutos.get(i).setCorTitulo("#b6c72c;");
			} else if (listaProdutos.get(i).getIdprodutos() ==2) {
				listaProdutos.get(i).setCorTitulo("#c12c2f;");
			}else if (listaProdutos.get(i).getIdprodutos() ==4) {
				listaProdutos.get(i).setCorTitulo("#decf25;");
			}else if (listaProdutos.get(i).getIdprodutos() ==5) {
				listaProdutos.get(i).setCorTitulo("#522c7b;");
			}else if (listaProdutos.get(i).getIdprodutos() ==6) {
				listaProdutos.get(i).setCorTitulo("#66b0ca;");
			}else if (listaProdutos.get(i).getIdprodutos() ==7) {
				listaProdutos.get(i).setCorTitulo("#dfa422;");
			}else if (listaProdutos.get(i).getIdprodutos() ==9) {
				listaProdutos.get(i).setCorTitulo("#be2a73;");
			}else if (listaProdutos.get(i).getIdprodutos() ==10) {
				listaProdutos.get(i).setCorTitulo("#79191d;");
			}else if (listaProdutos.get(i).getIdprodutos() ==16) {   
				listaProdutos.get(i).setCorTitulo("#344d97;");
			}else if (listaProdutos.get(i).getIdprodutos() ==22) {
				listaProdutos.get(i).setCorTitulo("#31436a;");
			}else {
				listaProdutos.get(i).setCorTitulo("#decf25;");
			}
			corrida.setProdutos(listaProdutos.get(i));
			corrida = gerarListaCorrida(corrida);
			listaCorrida.add(corrida);
		}
	}
	
	
	public UsuarioProdutoRunnersBean gerarListaCorrida(UsuarioProdutoRunnersBean corrida){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		String sql = "Select c From Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + " and c.produtos.idprodutos=" + corrida.getProdutos().getIdprodutos()
						+ " ORDER BY c.pontos DESC";
		listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		if (listaCorridaMes.size() > 0) {
			corrida.setFoto(getFoto(listaCorridaMes.get(0).getUsuario()));
			corrida.setPontos(listaCorridaMes.get(0).getPontos());
			corrida.setNomeConsultor(listaCorridaMes.get(0).getUsuario().getNome());
			corrida.setNomeUnidade(listaCorridaMes.get(0).getUsuario().getUnidadenegocio().getNomerelatorio());
		}else{
			corrida.setFoto(getFoto(null));
			corrida.setPontos(0);
			corrida.setNomeConsultor("");
			corrida.setNomeUnidade("");
		}
		return corrida;
	}
	
	
	
	
	
}
