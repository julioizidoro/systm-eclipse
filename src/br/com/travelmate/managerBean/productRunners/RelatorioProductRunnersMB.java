package br.com.travelmate.managerBean.productRunners;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.ProductRunnersBean;
import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;


@Named
@ViewScoped
public class RelatorioProductRunnersMB  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<ProductRunnersBean> listaProductRunnersBean;
	private int mes;
	private int ano;
	
	@PostConstruct
	public void init(){
	}
	  
	




	public List<ProductRunnersBean> getListaProductRunnersBean() {
		return listaProductRunnersBean;
	}






	public void setListaProductRunnersBean(List<ProductRunnersBean> listaProductRunnersBean) {
		this.listaProductRunnersBean = listaProductRunnersBean;
	}






	public int getMes() {
		return mes;
	}






	public void setMes(int mes) {
		this.mes = mes;
	}






	public int getAno() {
		return ano;
	}






	public void setAno(int ano) {
		this.ano = ano;
	}






	public void gerarListaProdutos(){
		listaProductRunnersBean = new ArrayList<ProductRunnersBean>();
		ProductRunnersBean productRunnersBean;
		ProdutoFacade produtoFacade = new ProdutoFacade();
		List<Produtos> listaProdutos;
		listaProdutos = produtoFacade.listarProdutosSql("SELECT p From Produtos p WHERE p.produtorunners=true Order By p.ordem ");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtos>();
		}
		for (int i = 0; i < listaProdutos.size(); i++) {
			productRunnersBean = new ProductRunnersBean();
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
			productRunnersBean.setProduto(listaProdutos.get(i));
			productRunnersBean.setListaCorridaUsuario(pegarUsuarios(listaProdutos.get(i)));
			listaProductRunnersBean.add(productRunnersBean);
		}
	}
	
	
	public List<Corridaprodutomes> pegarUsuarios(Produtos produtos) {
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		String sql = "SELECT c FROM Corridaprodutomes c WHERE  c.produtos.idprodutos=" + produtos.getIdprodutos();
		if (mes > 0) {
			sql = sql + " and c.mes=" + mes;
		}
		if (ano > 0) {
			sql = sql + " and c.ano=" + ano;
		}
		sql = sql + " ORDER BY c.pontos DESC";
		listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		
		return listaCorridaMes;
	}
	
	
	public String getFoto(Usuario usuario) {
		String caminho = null;
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
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
	
	
	public String gerarRelatorio(){
		gerarListaProdutos();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaProductRunnersBean", listaProductRunnersBean);
		session.setAttribute("mes", mes);
		session.setAttribute("ano", ano);
		return "resultadoProductRunners";
	}
	
	
	public void fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}

}
