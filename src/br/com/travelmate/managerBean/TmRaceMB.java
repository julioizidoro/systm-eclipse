package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.com.travelmate.bean.TmRaceBean;
import br.com.travelmate.facade.CorridaProdutoAnoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Corridaprodutoano;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

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
		List<TmRaceBean> listaposicao = null;
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
			}else if (listaGold.size() == 2){
				if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos()) {
					listaGold.get(0).setPosicao(1);
					listaGold.get(1).setPosicao(2);
				}else{
					listaGold.get(0).setPosicao(2);
					listaGold.get(1).setPosicao(1);
				}
			}else if(listaGold.size() == 3){
				if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos() && listaGold.get(0).getPontos() >= listaGold.get(2).getPontos()) {
					listaGold.get(0).setPosicao(1);
					if (listaGold.get(1).getPontos() >= listaGold.get(2).getPontos()) {
						listaGold.get(1).setPosicao(2);
						listaGold.get(2).setPosicao(3);
					}else{
						listaGold.get(1).setPosicao(3);
						listaGold.get(2).setPosicao(2);
					}
				}else if(listaGold.get(1).getPontos() >= listaGold.get(0).getPontos() && listaGold.get(1).getPontos() >= listaGold.get(2).getPontos()){
					listaGold.get(1).setPosicao(1);
					if (listaGold.get(0).getPontos() >= listaGold.get(2).getPontos()) {
						listaGold.get(0).setPosicao(2);
						listaGold.get(2).setPosicao(3);
					}else{
						listaGold.get(0).setPosicao(3);
						listaGold.get(2).setPosicao(2);
					}
				}else if(listaGold.get(2).getPontos() >= listaGold.get(0).getPontos() && listaGold.get(2).getPontos() >= listaGold.get(1).getPontos()){
					listaGold.get(2).setPosicao(1);
					if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos()) {
						listaGold.get(0).setPosicao(2);
						listaGold.get(1).setPosicao(3);
					}else{
						listaGold.get(0).setPosicao(3);
						listaGold.get(1).setPosicao(2);
					}
				}
			}
		}
	}
	
	public void gerarListaSinze(){
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		int ano = Formatacao.getAnoData(new Date());
		List<TmRaceBean> listaposicao = null;
		listaSinze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Silver%'");
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
			}else if (listaSinze.size() == 2){
				if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos()) {
					listaSinze.get(0).setPosicao(1);
					listaSinze.get(1).setPosicao(2);
				}else{
					listaSinze.get(0).setPosicao(2);
					listaSinze.get(1).setPosicao(1);
				}
			}else if(listaSinze.size() == 3){
				if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos() && listaSinze.get(0).getPontos() >= listaSinze.get(2).getPontos()) {
					listaSinze.get(0).setPosicao(1);
					if (listaSinze.get(1).getPontos() >= listaSinze.get(2).getPontos()) {
						listaSinze.get(1).setPosicao(2);
						listaSinze.get(2).setPosicao(3);
					}else{
						listaSinze.get(1).setPosicao(3);
						listaSinze.get(2).setPosicao(2);
					}
				}else if(listaSinze.get(1).getPontos() >= listaSinze.get(0).getPontos() && listaSinze.get(1).getPontos() >= listaSinze.get(2).getPontos()){
					listaSinze.get(1).setPosicao(1);
					if (listaSinze.get(0).getPontos() >= listaSinze.get(2).getPontos()) {
						listaSinze.get(0).setPosicao(2);
						listaSinze.get(2).setPosicao(3);
					}else{
						listaSinze.get(0).setPosicao(3);
						listaSinze.get(2).setPosicao(2);
					}
				}else if(listaSinze.get(2).getPontos() >= listaSinze.get(0).getPontos() && listaSinze.get(2).getPontos() >= listaSinze.get(1).getPontos()){
					listaSinze.get(2).setPosicao(1);
					if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos()) {
						listaSinze.get(0).setPosicao(2);
						listaSinze.get(1).setPosicao(3);
					}else{
						listaSinze.get(0).setPosicao(3);
						listaSinze.get(1).setPosicao(2);
					}
				}
			}
		}
	}
	
	public void gerarListaBronze(){
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		int ano = Formatacao.getAnoData(new Date());
		List<TmRaceBean> listaposicao = null;
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
			}else if (listaBronze.size() == 2){
				if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos()) {
					listaBronze.get(0).setPosicao(1);
					listaBronze.get(1).setPosicao(2);
				}else{
					listaBronze.get(0).setPosicao(2);
					listaBronze.get(1).setPosicao(1);
				}
			}else if(listaBronze.size() == 3){
				if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos() && listaBronze.get(0).getPontos() >= listaBronze.get(2).getPontos()) {
					listaBronze.get(0).setPosicao(1);
					if (listaBronze.get(1).getPontos() >= listaBronze.get(2).getPontos()) {
						listaBronze.get(1).setPosicao(2);
						listaBronze.get(2).setPosicao(3);
					}else{
						listaBronze.get(1).setPosicao(3);
						listaBronze.get(2).setPosicao(2);
					}
				}else if(listaBronze.get(1).getPontos() >= listaBronze.get(0).getPontos() && listaBronze.get(1).getPontos() >= listaBronze.get(2).getPontos()){
					listaBronze.get(1).setPosicao(1);
					if (listaBronze.get(0).getPontos() >= listaBronze.get(2).getPontos()) {
						listaBronze.get(0).setPosicao(2);
						listaBronze.get(2).setPosicao(3);
					}else{
						listaBronze.get(0).setPosicao(3);
						listaBronze.get(2).setPosicao(2);
					}
				}else if(listaBronze.get(2).getPontos() >= listaBronze.get(0).getPontos() && listaBronze.get(2).getPontos() >= listaBronze.get(1).getPontos()){
					listaBronze.get(2).setPosicao(1);
					if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos()) {
						listaBronze.get(0).setPosicao(2);
						listaBronze.get(1).setPosicao(3);
					}else{
						listaBronze.get(0).setPosicao(3);
						listaBronze.get(1).setPosicao(2);
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
				float porcentagem = (listaGold.get(i).getPontos() * 100) / listaGold.get(0).getPontos();
				return porcentagem;
			}
		}
		return 0;
	}
	
	public float getPorcentagemSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				float porcentagem = (listaSinze.get(i).getPontos() * 100) / listaSinze.get(0).getPontos();
				return porcentagem;
			}
		}
		return 0;
	}
	
	public float getPorcentagemBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				float porcentagem = (listaBronze.get(i).getPontos() * 100) / listaBronze.get(0).getPontos();
				return porcentagem;
			}
		}
		return 0;
	}
	

}
