package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.com.travelmate.bean.TmRaceBean;
import br.com.travelmate.converter.UnidadeNegocioConverter;
import br.com.travelmate.facade.CorridaProdutoAnoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Corridaprodutoano;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

@Named
@ApplicationScoped
public class TmRaceMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Corridaprodutoano> listaCorrida;
	private List<TmRaceBean> listaGold;
	private List<TmRaceBean> listaSinze;
	private List<TmRaceBean> listaBronze;
	
	
	@PostConstruct
	public void init(){
		gerarListaGold();
		gerarListaSinze();
		gerarListaBronze();
	}


	public List<Corridaprodutoano> getListaCorrida() {
		return listaCorrida;
	}


	public void setListaCorrida(List<Corridaprodutoano> listaCorrida) {
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
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		int ano = Formatacao.getAnoData(new Date());
		List<Integer> listaposicao = null;
		listaGold = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Gold%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutoano>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			listaCorrida = corridaProdutoAnoFacade.listar("SELECT c FROM Corridaprodutoano c WHERE c.ano=" + ano + " and c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio());
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutoano>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaGold.size() <= 3) {
				listaGold.add(tmRaceBean);
			}else{
				for (int l = 0; l < listaGold.size(); l++) {
					if (listaGold.get(l).getPontos() < tmRaceBean.getPontos()) {
						listaposicao.add(l);
						listaGold.add(tmRaceBean);
					}
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaGold.remove(listaposicao.get(j));
				}
			}
		}
	}
	
	public void gerarListaSinze(){
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		int ano = Formatacao.getAnoData(new Date());
		List<Integer> listaposicao = null;
		listaSinze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Sinze%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutoano>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			listaCorrida = corridaProdutoAnoFacade.listar("SELECT c FROM Corridaprodutoano c WHERE c.ano=" + ano + " and c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio());
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutoano>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaSinze.size() <= 3) {
				listaSinze.add(tmRaceBean);
			}else{
				for (int l = 0; l < listaSinze.size(); l++) {
					if (listaSinze.get(l).getPontos() < tmRaceBean.getPontos()) {
						listaposicao.add(l);
						listaSinze.add(tmRaceBean);
					}
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaSinze.remove(listaposicao.get(j));
				}
			}
		}
	}
	
	public void gerarListaBronze(){
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		int ano = Formatacao.getAnoData(new Date());
		List<Integer> listaposicao = null;
		listaBronze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Bronze%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutoano>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			listaCorrida = corridaProdutoAnoFacade.listar("SELECT c FROM Corridaprodutoano c WHERE c.ano=" + ano + " and c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio());
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutoano>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaBronze.size() <= 3) {
				listaSinze.add(tmRaceBean);
			}else{
				for (int l = 0; l < listaBronze.size(); l++) {
					if (listaBronze.get(l).getPontos() < tmRaceBean.getPontos()) {
						listaposicao.add(l);
						listaBronze.add(tmRaceBean);
					}
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaBronze.remove(listaposicao.get(j));
				}
			}
		}
	}
	
	public String getNomeUnidade(int posicao){
		if (listaGold != null && listaGold.size() > 0) {
			if (posicao == 1) {
				if (listaGold.size() == 1) {
						return listaGold.get(0).getNomeUnidade();
				}else if(listaGold.size() == 2){
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos())) {
						return listaGold.get(0).getNomeUnidade();
					}else{
						return listaGold.get(1).getNomeUnidade();
					}
				}else{
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(0).getNomeUnidade();
					}else if ((listaGold.get(1).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(1).getNomeUnidade();
					}else if((listaGold.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() > listaGold.get(1).getPontos())){
						return listaGold.get(2).getNomeUnidade();
					}	
				}
			}else if (posicao == 2){
				if (listaGold.size() == 1) {
					return null;
				}else if(listaGold.size() == 2){
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos())) {
						return listaGold.get(1).getNomeUnidade();
					}else{
						return listaGold.get(0).getNomeUnidade();
					}
				}else{
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(0).getPontos() < listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(0).getNomeUnidade();
					}else if ((listaGold.get(1).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(1).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(1).getNomeUnidade();
					}else if((listaGold.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() < listaGold.get(1).getPontos())
							|| (listaGold.get(2).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() > listaGold.get(1).getPontos())){
						return listaGold.get(2).getNomeUnidade();
					}
				}
			}else if(posicao == 3){
				if (listaGold.size() == 1) {
					return null;
				}else if(listaGold.size() == 2){
					return null;
				}else if (listaGold.size() == 3) {
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(0).getPontos() < listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(0).getNomeUnidade();
					}else if ((listaGold.get(1).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(1).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(1).getNomeUnidade();
					}else if((listaGold.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() < listaGold.get(1).getPontos())
							|| (listaGold.get(2).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() > listaGold.get(1).getPontos())){
						return listaGold.get(2).getNomeUnidade();
					}
				}
			}
		}
		return null;
	}
	
	
	public int getPontos(int posicao){
		if (listaGold != null && listaGold.size() >0) {
			if (posicao == 1) {
				if (listaGold.size() == 1) {
						return listaGold.get(0).getPontos();
				}else if(listaGold.size() == 2){
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos())) {
						return listaGold.get(0).getPontos();
					}else{
						return listaGold.get(1).getPontos();
					}
				}else{
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(0).getPontos();
					}else if ((listaGold.get(1).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(1).getPontos();
					}else if((listaGold.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() > listaGold.get(1).getPontos())){
						return listaGold.get(2).getPontos();
					}	
				}
			}else if (posicao == 2){
				if (listaGold.size() == 1) {
					return 0;
				}else if(listaGold.size() == 2){
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos())) {
						return listaGold.get(1).getPontos();
					}else{
						return listaGold.get(0).getPontos();
					}
				}else{
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(0).getPontos() < listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(0).getPontos();
					}else if ((listaGold.get(1).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(1).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(1).getPontos();
					}else if((listaGold.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() < listaGold.get(1).getPontos())
							|| (listaGold.get(2).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() > listaGold.get(1).getPontos())){
						return listaGold.get(2).getPontos();
					}
				}
			}else if(posicao == 3){
				if (listaGold.size() == 1) {
					return 0;
				}else if(listaGold.size() == 2){
					return 0;
				}else if (listaGold.size() == 3) {
					if ((listaGold.get(0).getPontos() > listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(0).getPontos() < listaGold.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(0).getPontos();
					}else if ((listaGold.get(1).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() < listaGold.get(2).getPontos())
							|| (listaGold.get(1).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(1).getPontos() > listaGold.get(2).getPontos())) {
						return listaGold.get(1).getPontos();
					}else if((listaGold.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() < listaGold.get(1).getPontos())
							|| (listaGold.get(2).getPontos() < listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() > listaGold.get(1).getPontos())){
						return listaGold.get(2).getPontos();
					}
				}
			}
		}
		return 0;
	}
	
	public String getNomeUnidadeSinze(int posicao){
		if (listaSinze != null && listaSinze.size() >0) {
			if (posicao == 1) {
				if (listaSinze.size() == 1) {
						return listaSinze.get(0).getNomeUnidade();
				}else if(listaGold.size() == 2){
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos())) {
						return listaSinze.get(0).getNomeUnidade();
					}else{
						return listaSinze.get(1).getNomeUnidade();
					}
				}else{
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(0).getNomeUnidade();
					}else if ((listaSinze.get(1).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(1).getNomeUnidade();
					}else if((listaSinze.get(2).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() > listaSinze.get(1).getPontos())){
						return listaSinze.get(2).getNomeUnidade();
					}	
				}
			}else if (posicao == 2){
				if (listaSinze.size() == 1) {
					return null;
				}else if(listaSinze.size() == 2){
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos())) {
						return listaSinze.get(1).getNomeUnidade();
					}else{
						return listaSinze.get(0).getNomeUnidade();
					}
				}else{
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(0).getPontos() < listaSinze.get(1).getPontos()) && (listaGold.get(0).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(0).getNomeUnidade();
					}else if ((listaSinze.get(1).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(1).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(1).getNomeUnidade();
					}else if((listaSinze.get(2).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() < listaSinze.get(1).getPontos())
							|| (listaSinze.get(2).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() > listaSinze.get(1).getPontos())){
						return listaSinze.get(2).getNomeUnidade();
					}
				}
			}else if(posicao == 3){
				if (listaSinze.size() == 1) {
					return null;
				}else if(listaSinze.size() == 2){
					return null;
				}else if (listaSinze.size() == 3) {
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(0).getPontos() < listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(0).getNomeUnidade();
					}else if ((listaSinze.get(1).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(1).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(1).getNomeUnidade();
					}else if((listaSinze.get(2).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() < listaSinze.get(1).getPontos())
							|| (listaSinze.get(2).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() > listaSinze.get(1).getPontos())){
						return listaSinze.get(2).getNomeUnidade();
					}
				}
			}
		}
		return null;
	}
	
	
	public int getPontosSinze(int posicao){
		if (listaSinze != null && listaSinze.size() > 0) {
			if (posicao == 1) {
				if (listaSinze.size() == 1) {
						return listaSinze.get(0).getPontos();
				}else if(listaSinze.size() == 2){
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos())) {
						return listaSinze.get(0).getPontos();
					}else{
						return listaSinze.get(1).getPontos();
					}
				}else{
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(0).getPontos();
					}else if ((listaSinze.get(1).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(1).getPontos();
					}else if((listaSinze.get(2).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() > listaSinze.get(1).getPontos())){
						return listaSinze.get(2).getPontos();
					}	
				}
			}else if (posicao == 2){
				if (listaSinze.size() == 1) {
					return 0;
				}else if(listaSinze.size() == 2){
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos())) {
						return listaSinze.get(1).getPontos();
					}else{
						return listaSinze.get(0).getPontos();
					}
				}else{
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(0).getPontos() < listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(0).getPontos();
					}else if ((listaSinze.get(1).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(1).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(1).getPontos();
					}else if((listaSinze.get(2).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() < listaSinze.get(1).getPontos())
							|| (listaSinze.get(2).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() > listaSinze.get(1).getPontos())){
						return listaSinze.get(2).getPontos();
					}
				}
			}else if(posicao == 3){
				if (listaSinze.size() == 1) {
					return 0;
				}else if(listaSinze.size() == 2){
					return 0;
				}else if (listaSinze.size() == 3) {
					if ((listaSinze.get(0).getPontos() > listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(0).getPontos() < listaSinze.get(1).getPontos()) && (listaSinze.get(0).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(0).getPontos();
					}else if ((listaSinze.get(1).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() < listaSinze.get(2).getPontos())
							|| (listaSinze.get(1).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(1).getPontos() > listaSinze.get(2).getPontos())) {
						return listaSinze.get(1).getPontos();
					}else if((listaSinze.get(2).getPontos() > listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() < listaSinze.get(1).getPontos())
							|| (listaSinze.get(2).getPontos() < listaSinze.get(0).getPontos()) && (listaSinze.get(2).getPontos() > listaSinze.get(1).getPontos())){
						return listaSinze.get(2).getPontos();
					}
				}
			}
		}
		return 0;
	}
	
	public String getNomeUnidadeBronze(int posicao){
		if (listaBronze != null && listaBronze.size() > 0) {
			if (posicao == 1) {
				if (listaBronze.size() == 1) {
						return listaBronze.get(0).getNomeUnidade();
				}else if(listaBronze.size() == 2){
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos())) {
						return listaBronze.get(0).getNomeUnidade();
					}else{
						return listaBronze.get(1).getNomeUnidade();
					}
				}else{
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(0).getNomeUnidade();
					}else if ((listaBronze.get(1).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(1).getNomeUnidade();
					}else if((listaBronze.get(2).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() > listaBronze.get(1).getPontos())){
						return listaBronze.get(2).getNomeUnidade();
					}	
				}
			}else if (posicao == 2){
				if (listaBronze.size() == 1) {
					return null;
				}else if(listaBronze.size() == 2){
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos())) {
						return listaBronze.get(1).getNomeUnidade();
					}else{
						return listaBronze.get(0).getNomeUnidade();
					}
				}else{
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(0).getPontos() < listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(0).getNomeUnidade();
					}else if ((listaBronze.get(1).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(1).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(1).getNomeUnidade();
					}else if((listaBronze.get(2).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() < listaBronze.get(1).getPontos())
							|| (listaBronze.get(2).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() > listaBronze.get(1).getPontos())){
						return listaBronze.get(2).getNomeUnidade();
					}
				}
			}else if(posicao == 3){
				if (listaBronze.size() == 1) {
					return null;
				}else if(listaBronze.size() == 2){
					return null;
				}else if (listaBronze.size() == 3) {
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(0).getPontos() < listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(0).getNomeUnidade();
					}else if ((listaBronze.get(1).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(1).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(1).getNomeUnidade();
					}else if((listaBronze.get(2).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() < listaBronze.get(1).getPontos())
							|| (listaBronze.get(2).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() > listaBronze.get(1).getPontos())){
						return listaBronze.get(2).getNomeUnidade();
					}
				}
			}
		}
		return null;
	}
	
	
	public int getPontosBronze(int posicao){
		if (listaBronze != null && listaBronze.size() >0) {
			if (posicao == 1) {
				if (listaBronze.size() == 1) {
						return listaBronze.get(0).getPontos();
				}else if(listaBronze.size() == 2){
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos())) {
						return listaBronze.get(0).getPontos();
					}else{
						return listaBronze.get(1).getPontos();
					}
				}else{
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(0).getPontos();
					}else if ((listaBronze.get(1).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() > listaBronze.get(2).getPontos())) {
						return listaGold.get(1).getPontos();
					}else if((listaBronze.get(2).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() > listaBronze.get(1).getPontos())){
						return listaBronze.get(2).getPontos();
					}	
				}
			}else if (posicao == 2){
				if (listaBronze.size() == 1) {
					return 0;
				}else if(listaBronze.size() == 2){
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos())) {
						return listaBronze.get(1).getPontos();
					}else{
						return listaBronze.get(0).getPontos();
					}
				}else{
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(0).getPontos() < listaGold.get(1).getPontos()) && (listaBronze.get(0).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(0).getPontos();
					}else if ((listaBronze.get(1).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(1).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(1).getPontos();
					}else if((listaBronze.get(2).getPontos() > listaGold.get(0).getPontos()) && (listaGold.get(2).getPontos() < listaGold.get(1).getPontos())
							|| (listaBronze.get(2).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() > listaBronze.get(1).getPontos())){
						return listaBronze.get(2).getPontos();
					}
				}
			}else if(posicao == 3){
				if (listaBronze.size() == 1) {
					return 0;
				}else if(listaBronze.size() == 2){
					return 0;
				}else if (listaBronze.size() == 3) {
					if ((listaBronze.get(0).getPontos() > listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(0).getPontos() < listaBronze.get(1).getPontos()) && (listaBronze.get(0).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(0).getPontos();
					}else if ((listaBronze.get(1).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() < listaBronze.get(2).getPontos())
							|| (listaBronze.get(1).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(1).getPontos() > listaBronze.get(2).getPontos())) {
						return listaBronze.get(1).getPontos();
					}else if((listaBronze.get(2).getPontos() > listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() < listaBronze.get(1).getPontos())
							|| (listaBronze.get(2).getPontos() < listaBronze.get(0).getPontos()) && (listaBronze.get(2).getPontos() > listaBronze.get(1).getPontos())){
						return listaBronze.get(2).getPontos();
					}
				}
			}
		}
		return 0;
	}
	

}
