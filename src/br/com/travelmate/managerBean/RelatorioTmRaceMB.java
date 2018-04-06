package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.CategoriaTmRaceBean;
import br.com.travelmate.bean.TmRaceBean;
import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Unidadenegocio;

@Named
@ViewScoped
public class RelatorioTmRaceMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Corridaprodutomes> listaCorrida;
	private List<TmRaceBean> listaGold;
	private List<TmRaceBean> listaSinze;
	private List<TmRaceBean> listaBronze;
	private int mes;
	private int ano;
	private List<CategoriaTmRaceBean> listaCategoriaBean;
	
	
	@PostConstruct
	public void init(){
	}

  
	


	public List<Corridaprodutomes> getListaCorrida() {
		return listaCorrida;
	}





	public void setListaCorrida(List<Corridaprodutomes> listaCorrida) {
		this.listaCorrida = listaCorrida;
	}





	public List<TmRaceBean> getListaGold() {
		return listaGold;
	}


	public void setListaGold(List<TmRaceBean> listaGold) {
		this.listaGold = listaGold;
	}


	public List<TmRaceBean> getListaSinze() {
		return listaSinze;
	}


	public void setListaSinze(List<TmRaceBean> listaSinze) {
		this.listaSinze = listaSinze;
	}


	public List<TmRaceBean> getListaBronze() {
		return listaBronze;
	}


	public void setListaBronze(List<TmRaceBean> listaBronze) {
		this.listaBronze = listaBronze;
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





	public void gerarListaGold(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<TmRaceBean> listaposicao = null;
		listaGold = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Gold%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutomes>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			String sql ="SELECT c FROM Corridaprodutomes c WHERE c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio();
			if (mes > 0) {
				sql = sql + " and c.mes=" + mes;
			}
			if (ano > 0) {
				sql = sql + " and c.ano=" + ano;
			}
			listaCorrida = corridaProdutoMesFacade.listar(sql);
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomeFantasia());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaGold.size() < 3) {
				listaGold.add(tmRaceBean);
			}else if(listaCorrida.size() > 0 && listaGold.size() >= 3){
					if ((listaGold.get(0).getPontos() <= tmRaceBean.getPontos()) && (listaGold.get(0).getPontos() <= listaGold.get(1).getPontos()) &&
							(listaGold.get(0).getPontos() <= listaGold.get(2).getPontos())) {
						listaposicao.add(listaGold.get(0));
						listaGold.add(tmRaceBean);
					}else if ((listaGold.get(1).getPontos() <= tmRaceBean.getPontos()) && (listaGold.get(1).getPontos() <= listaGold.get(0).getPontos()) &&
							(listaGold.get(1).getPontos() <= listaGold.get(2).getPontos())) {
						listaposicao.add(listaGold.get(1));
						listaGold.add(tmRaceBean);
					} else if ((listaGold.get(2).getPontos() <= tmRaceBean.getPontos()) && (listaGold.get(2).getPontos() <= listaGold.get(1).getPontos()) &&
							(listaGold.get(2).getPontos() <= listaGold.get(0).getPontos())) {
						listaposicao.add(listaGold.get(2));
						listaGold.add(tmRaceBean);
					}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaGold.remove(listaposicao.get(j));
				}
			}
		}
		if (listaGold != null) {
			if (listaGold.size() == 1) {
				listaGold.get(0).setPosicao(1);
				listaGold.get(0).setPorcentagem(100f);
			}else if (listaGold.size() == 2){
				if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos()) {
					listaGold.get(0).setPosicao(1);
					listaGold.get(0).setPorcentagem(100f);
					listaGold.get(1).setPosicao(2);
					listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(0).getPontos());
				}else{
					listaGold.get(0).setPosicao(2);
					listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(1).getPontos());
					listaGold.get(1).setPosicao(1);
					listaGold.get(1).setPorcentagem(100f);
				}
			}else if(listaGold.size() == 3){
				if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos() && listaGold.get(0).getPontos() >= listaGold.get(2).getPontos()) {
					listaGold.get(0).setPosicao(1);
					listaGold.get(1).setPorcentagem(100f);
					if (listaGold.get(1).getPontos() >= listaGold.get(2).getPontos()) {
						listaGold.get(1).setPosicao(2);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(0).getPontos());
						listaGold.get(2).setPosicao(3);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(0).getPontos());
					}else{
						listaGold.get(1).setPosicao(3);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(0).getPontos());
						listaGold.get(2).setPosicao(2);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(0).getPontos());
					}
				}else if(listaGold.get(1).getPontos() >= listaGold.get(0).getPontos() && listaGold.get(1).getPontos() >= listaGold.get(2).getPontos()){
					listaGold.get(1).setPosicao(1);
					listaGold.get(1).setPorcentagem(100f);
					if (listaGold.get(0).getPontos() >= listaGold.get(2).getPontos()) {
						listaGold.get(0).setPosicao(2);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(1).getPontos());
						listaGold.get(2).setPosicao(3);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(1).getPontos());
					}else{
						listaGold.get(0).setPosicao(3);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(1).getPontos());
						listaGold.get(2).setPosicao(2);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(1).getPontos());
					}
				}else if(listaGold.get(2).getPontos() >= listaGold.get(0).getPontos() && listaGold.get(2).getPontos() >= listaGold.get(1).getPontos()){
					listaGold.get(2).setPosicao(1);
					listaGold.get(2).setPorcentagem(100f);
					if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos()) {
						listaGold.get(0).setPosicao(2);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(2).getPontos());
						listaGold.get(1).setPosicao(3);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(2).getPontos());
					}else{
						listaGold.get(0).setPosicao(3);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(2).getPontos());
						listaGold.get(1).setPosicao(2);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(2).getPontos());
					}
				}
			}
			CategoriaTmRaceBean categoriaTmRaceBean = new CategoriaTmRaceBean();
			categoriaTmRaceBean.setCategoria("Gold");
			categoriaTmRaceBean.setListaUnidade(listaGold);
			if (listaCategoriaBean == null) {
				listaCategoriaBean = new ArrayList<CategoriaTmRaceBean>();
			}
			listaCategoriaBean.add(categoriaTmRaceBean);
		}
	}
	
	public void gerarListaSinze(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<TmRaceBean> listaposicao = null;
		listaSinze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Silver%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutomes>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			String sql ="SELECT c FROM Corridaprodutomes c WHERE c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio();
			if (mes > 0) {
				sql = sql + " and c.mes=" + mes;
			}
			if (ano > 0) {
				sql = sql + " and c.ano=" + ano;
			}
			listaCorrida = corridaProdutoMesFacade.listar(sql);
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomeFantasia());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaSinze.size() < 3) {
				listaSinze.add(tmRaceBean);
			}else if (listaCorrida.size() > 0 && listaSinze.size() >= 3){
				if ((listaSinze.get(0).getPontos() <= tmRaceBean.getPontos()) && (listaSinze.get(0).getPontos() <= listaSinze.get(1).getPontos()) &&
						(listaSinze.get(0).getPontos() <= listaSinze.get(2).getPontos())) {
					listaposicao.add(listaSinze.get(0));
					listaSinze.add(tmRaceBean);
				}else if ((listaSinze.get(1).getPontos() <= tmRaceBean.getPontos()) && (listaSinze.get(1).getPontos() <= listaSinze.get(0).getPontos()) &&
						(listaSinze.get(1).getPontos() <= listaSinze.get(2).getPontos())) {
					listaposicao.add(listaSinze.get(1));
					listaSinze.add(tmRaceBean);
				} else if ((listaSinze.get(2).getPontos() <= tmRaceBean.getPontos()) && (listaSinze.get(2).getPontos() <= listaSinze.get(1).getPontos()) &&
						(listaSinze.get(2).getPontos() <= listaSinze.get(0).getPontos())) {
					listaposicao.add(listaSinze.get(2));
					listaSinze.add(tmRaceBean);
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaSinze.remove(listaposicao.get(j));
				}
			}
		}
		if (listaSinze != null) {
			if (listaSinze.size() == 1) {
				listaSinze.get(0).setPosicao(1);
				listaSinze.get(1).setPorcentagem(100f);
			}else if (listaSinze.size() == 2){
				if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos()) {
					listaSinze.get(0).setPosicao(1);
					listaSinze.get(0).setPorcentagem(100f);
					listaSinze.get(1).setPosicao(2);
					listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(0).getPontos());
				}else{
					listaSinze.get(0).setPosicao(2);
					listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(1).getPontos());
					listaSinze.get(1).setPosicao(1);
					listaSinze.get(1).setPorcentagem(100f);
				}
			}else if(listaSinze.size() == 3){
				if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos() && listaSinze.get(0).getPontos() >= listaSinze.get(2).getPontos()) {
					listaSinze.get(0).setPosicao(1);
					listaSinze.get(0).setPorcentagem(100f);
					if (listaSinze.get(1).getPontos() >= listaSinze.get(2).getPontos()) {
						listaSinze.get(1).setPosicao(2);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(0).getPontos());
						listaSinze.get(2).setPosicao(3);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(0).getPontos());
					}else{
						listaSinze.get(1).setPosicao(3);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(0).getPontos());
						listaSinze.get(2).setPosicao(2);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(0).getPontos());
					}
				}else if(listaSinze.get(1).getPontos() >= listaSinze.get(0).getPontos() && listaSinze.get(1).getPontos() >= listaSinze.get(2).getPontos()){
					listaSinze.get(1).setPosicao(1);
					listaSinze.get(1).setPorcentagem(100f);
					if (listaSinze.get(0).getPontos() >= listaSinze.get(2).getPontos()) {
						listaSinze.get(0).setPosicao(2);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(1).getPontos());
						listaSinze.get(2).setPosicao(3);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(1).getPontos());
					}else{
						listaSinze.get(0).setPosicao(3);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(1).getPontos());
						listaSinze.get(2).setPosicao(2);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(1).getPontos());
					}
				}else if(listaSinze.get(2).getPontos() >= listaSinze.get(0).getPontos() && listaSinze.get(2).getPontos() >= listaSinze.get(1).getPontos()){
					listaSinze.get(2).setPosicao(1);
					listaSinze.get(2).setPorcentagem(100f);
					if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos()) {
						listaSinze.get(0).setPosicao(2);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(2).getPontos());
						listaSinze.get(1).setPosicao(3);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(2).getPontos());
					}else{
						listaSinze.get(0).setPosicao(3);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(2).getPontos());
						listaSinze.get(1).setPosicao(2);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(2).getPontos());
					}
				}
			}
			CategoriaTmRaceBean categoriaTmRaceBean = new CategoriaTmRaceBean();
			categoriaTmRaceBean.setCategoria("Silver");
			categoriaTmRaceBean.setListaUnidade(listaSinze);
			if (listaCategoriaBean == null) {
				listaCategoriaBean = new ArrayList<CategoriaTmRaceBean>();
			}
			listaCategoriaBean.add(categoriaTmRaceBean);
		}
	}
	
	public void gerarListaBronze(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<TmRaceBean> listaposicao = null;
		listaBronze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Bronze%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutomes>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			String sql ="SELECT c FROM Corridaprodutomes c WHERE c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio();
			if (mes > 0) {
				sql = sql + " and c.mes=" + mes;
			}
			if (ano > 0) {
				sql = sql + " and c.ano=" + ano;
			}
			listaCorrida = corridaProdutoMesFacade.listar(sql);
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomeFantasia());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaBronze.size() < 3) {
				listaBronze.add(tmRaceBean);
			}else if (listaCorrida.size() > 0 && listaBronze.size() >= 3){
				if ((listaBronze.get(0).getPontos() <= tmRaceBean.getPontos()) && (listaBronze.get(0).getPontos() <= listaBronze.get(1).getPontos()) &&
						(listaBronze.get(0).getPontos() <= listaBronze.get(2).getPontos())) {
					listaposicao.add(listaBronze.get(0));
					listaBronze.add(tmRaceBean);
				}else if ((listaBronze.get(1).getPontos() <= tmRaceBean.getPontos()) && (listaBronze.get(1).getPontos() <= listaBronze.get(0).getPontos()) &&
						(listaBronze.get(1).getPontos() <= listaBronze.get(2).getPontos())) {
					listaposicao.add(listaBronze.get(1));
					listaBronze.add(tmRaceBean);
				} else if ((listaBronze.get(2).getPontos() <= tmRaceBean.getPontos()) && (listaBronze.get(2).getPontos() <= listaBronze.get(1).getPontos()) &&
						(listaBronze.get(2).getPontos() <= listaBronze.get(0).getPontos())) {
					listaposicao.add(listaBronze.get(2));
					listaBronze.add(tmRaceBean);
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaBronze.remove(listaposicao.get(j));
				}
			}
		}
		if (listaBronze != null) {
			if (listaBronze.size() == 1) {
				listaBronze.get(0).setPosicao(1);
				listaBronze.get(0).setPorcentagem(100f);
			}else if (listaBronze.size() == 2){
				if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos()) {
					listaBronze.get(0).setPosicao(1);
					listaBronze.get(0).setPorcentagem(100f);
					listaBronze.get(1).setPosicao(2);
					listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(0).getPontos());
				}else{
					listaBronze.get(0).setPosicao(2);
					listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(1).getPontos());
					listaBronze.get(1).setPosicao(1);
					listaBronze.get(1).setPorcentagem(100f);
				}
			}else if(listaBronze.size() == 3){
				if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos() && listaBronze.get(0).getPontos() >= listaBronze.get(2).getPontos()) {
					listaBronze.get(0).setPosicao(1);
					listaBronze.get(0).setPorcentagem(100f);
					if (listaBronze.get(1).getPontos() >= listaBronze.get(2).getPontos()) {
						listaBronze.get(1).setPosicao(2);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(0).getPontos());
						listaBronze.get(2).setPosicao(3);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(0).getPontos());
					}else{
						listaBronze.get(1).setPosicao(3);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(0).getPontos());
						listaBronze.get(2).setPosicao(2);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(0).getPontos());
					}
				}else if(listaBronze.get(1).getPontos() >= listaBronze.get(0).getPontos() && listaBronze.get(1).getPontos() >= listaBronze.get(2).getPontos()){
					listaBronze.get(1).setPosicao(1);
					listaBronze.get(1).setPorcentagem(100f);
					if (listaBronze.get(0).getPontos() >= listaBronze.get(2).getPontos()) {
						listaBronze.get(0).setPosicao(2);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(1).getPontos());
						listaBronze.get(2).setPosicao(3);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(1).getPontos());
					}else{  
						listaBronze.get(0).setPosicao(3);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(1).getPontos());
						listaBronze.get(2).setPosicao(2);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(1).getPontos());
					}
				}else if(listaBronze.get(2).getPontos() >= listaBronze.get(0).getPontos() && listaBronze.get(2).getPontos() >= listaBronze.get(1).getPontos()){
					listaBronze.get(2).setPosicao(1);
					listaBronze.get(2).setPorcentagem(100f);
					if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos()) {
						listaBronze.get(0).setPosicao(2);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(2).getPontos());
						listaBronze.get(1).setPosicao(3);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(2).getPontos());
					}else{
						listaBronze.get(0).setPosicao(3);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(2).getPontos());
						listaBronze.get(1).setPosicao(2);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(2).getPontos());
					}
				}
			}
			CategoriaTmRaceBean categoriaTmRaceBean = new CategoriaTmRaceBean();
			categoriaTmRaceBean.setCategoria("Bronze");
			categoriaTmRaceBean.setListaUnidade(listaBronze);
			if (listaCategoriaBean == null) {
				listaCategoriaBean = new ArrayList<CategoriaTmRaceBean>();
			}
			listaCategoriaBean.add(categoriaTmRaceBean);
		}
	}
	

	public String gerarRelatorio(){
		gerarListaGold();
		gerarListaBronze();
		gerarListaSinze();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaCategoriaBean", listaCategoriaBean);
		session.setAttribute("mes", mes);
		session.setAttribute("ano", ano);
		return "resultadoTMRace";
	}  
	
	
	public void fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	

	
	

}
