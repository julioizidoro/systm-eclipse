package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

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
@ApplicationScoped
public class ProductRunnersMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Produtos> listaProdutos;
	
	
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

	
	
	public String getFoto(Produtos produtos) {
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		String caminho = null;
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		String sql = "SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + " and c.produtos.idprodutos=" + produtos.getIdprodutos()
						+ " ORDER BY c.pontos DESC";
		try {
			listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (listaCorridaMes.size() > 0) {
			if (listaCorridaMes.get(0).getUsuario().isFoto()) {
				caminho = caminho + "/usuario/" + listaCorridaMes.get(0).getUsuario().getIdusuario() + ".jpg";
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
		try {
			listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		ProdutoFacade produtoFacade = new ProdutoFacade();
		listaProdutos = produtoFacade.listarProdutosSql("SELECT p From Produtos p WHERE p.produtorunners=true");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtos>();
		}
		for (int i = 0; i < listaProdutos.size(); i++) {
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
		}
	}
	
	
	public void calcularPontuacao(Vendas vendas, int pontos, boolean cancelamento){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		Corridaprodutomes corridaprodutomes;
		Corridaprodutoano corridaprodutoano;
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		if (cancelamento) {
			corridaprodutomes = corridaProdutoMesFacade.consultar("SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutomes == null) {
				corridaprodutomes = new Corridaprodutomes();
				corridaprodutomes.setAno(ano);
				corridaprodutomes.setMes(mes);
				corridaprodutomes.setPontos(pontos);
				corridaprodutomes.setProdutos(vendas.getProdutos());
				corridaprodutomes.setUsuario(vendas.getUsuario());
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}else{
				corridaprodutomes.setPontos(corridaprodutomes.getPontos() - pontos);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}
			corridaprodutoano = corridaProdutoAnoFacade.consultar("SELECT c FROM Corridaprodutoano c WHERE  c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutoano == null) {
				corridaprodutoano = new Corridaprodutoano();
				corridaprodutoano.setAno(ano);
				corridaprodutoano.setPontos(pontos);
				corridaprodutoano.setProdutos(vendas.getProdutos());
				corridaprodutoano.setUsuario(vendas.getUsuario());
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}else{
				corridaprodutoano.setPontos(corridaprodutoano.getPontos() - pontos);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}
		}else{
			corridaprodutomes = corridaProdutoMesFacade.consultar("SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutomes == null) {
				corridaprodutomes = new Corridaprodutomes();
				corridaprodutomes.setAno(ano);
				corridaprodutomes.setMes(mes);
				corridaprodutomes.setPontos(pontos);
				corridaprodutomes.setProdutos(vendas.getProdutos());
				corridaprodutomes.setUsuario(vendas.getUsuario());
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}else{
				corridaprodutomes.setPontos(corridaprodutomes.getPontos() + pontos);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}
			corridaprodutoano = corridaProdutoAnoFacade.consultar("SELECT c FROM Corridaprodutoano c WHERE  c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutoano == null) {
				corridaprodutoano = new Corridaprodutoano();
				corridaprodutoano.setAno(ano);
				corridaprodutoano.setPontos(pontos);
				corridaprodutoano.setProdutos(vendas.getProdutos());
				corridaprodutoano.setUsuario(vendas.getUsuario());
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}else{
				corridaprodutoano.setPontos(corridaprodutoano.getPontos() + pontos);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}
		}
	}
	
	
	
	
	
	
}
