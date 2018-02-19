package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.travelmate.bean.TmRaceBean;
import br.com.travelmate.facade.CorridaProdutoAnoFacade;
import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Corridaprodutoano;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class TmRaceMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Corridaprodutomes> listaCorrida;
	private List<TmRaceBean> listaGold;
	private List<TmRaceBean> listaSinze;
	private List<TmRaceBean> listaBronze;
	
	
	@PostConstruct
	public void init(){
		gerarListaGold();
		gerarListaSinze();
		gerarListaBronze();
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
	
	  
	
	
	public void gerarListaGold(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
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
			listaCorrida = corridaProdutoMesFacade.listar("SELECT c FROM Corridaprodutomes c WHERE c.ano=" + ano + " and c.mes="+ mes +" and c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio());
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
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
					listaGold.get(0).setPorcentagem(100f);
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
		}
	}
	
	public void gerarListaSinze(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
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
			listaCorrida = corridaProdutoMesFacade.listar("SELECT c FROM Corridaprodutomes c WHERE c.ano=" + ano + " and c.mes="+ mes +" and c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio());
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
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
				listaSinze.get(0).setPorcentagem(100f);
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
		}
	}
	
	public void gerarListaBronze(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
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
			listaCorrida = corridaProdutoMesFacade.listar("SELECT c FROM Corridaprodutomes c WHERE c.ano=" + ano + " and c.mes="+ mes +" and c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio());
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
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
				listaSinze.get(0).setPorcentagem(100f);
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
		}
	}
	

	
	public String getNomeUnidade(int posicao){
		for (int i = 0; i < listaGold.size(); i++) {
			if (listaGold.get(i).getPosicao() == posicao) {
				return listaGold.get(i).getNomeUnidade();
			}
		}
		return "";
	}
	
	public int getPontos(int posicao){
		for (int i = 0; i < listaGold.size(); i++) {
			if (listaGold.get(i).getPosicao() == posicao) {
				return listaGold.get(i).getPontos();
			}
		}
		return 0;
	}
	
	public String getNomeUnidadeSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				return listaSinze.get(i).getNomeUnidade();
			}
		}
		return "";
	}
	
	public int getPontosSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				return listaSinze.get(i).getPontos();
			}
		}
		return 0;
	}
	

	
	
	public String getNomeUnidadeBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				return listaBronze.get(i).getNomeUnidade();
			}
		}
		return "";
	}
	
	public int getPontosBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				return listaBronze.get(i).getPontos();
			}
		}
		return 0;
	}
	

	
	public float getPorcentagem(int posicao){
		for (int i = 0; i < listaGold.size(); i++) {
			if (listaGold.get(i).getPosicao() == posicao) {
				return listaGold.get(i).getPorcentagem();
			}
		}
		return 0;
	}
	
	public float getPorcentagemSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				return listaSinze.get(i).getPorcentagem();
			} 
		}
		return 0;
	}
	
	public float getPorcentagemBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				return listaBronze.get(i).getPorcentagem();
			}
		}
		return 0;
	}
	

}
