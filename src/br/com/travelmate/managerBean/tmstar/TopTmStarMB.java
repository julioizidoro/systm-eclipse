package br.com.travelmate.managerBean.tmstar;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.travelmate.bean.TMStarBean;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.TmStarFacade;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Tmstar;

@Named
@ViewScoped
public class TopTmStarMB implements Serializable{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tmstar tmstar;
	private List<Tmstar> listaTmStar;
	private Ftpdados ftpdados;
	private List<TMStarBean> listaTmStarBean;
	
	@PostConstruct
	public void init(){
		gerarListaTmStar();
		pegarFtpDados();
		gerarBeanTmStar();
	}


	public Tmstar getTmstar() {
		return tmstar;
	}


	public void setTmstar(Tmstar tmstar) {
		this.tmstar = tmstar;
	}


	public List<Tmstar> getListaTmStar() {
		return listaTmStar;
	}


	public void setListaTmStar(List<Tmstar> listaTmStar) {
		this.listaTmStar = listaTmStar;
	}
	 
	public Ftpdados getFtpdados() {
		return ftpdados;
	}
 
	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}
	 
	
	public List<TMStarBean> getListaTmStarBean() {
		return listaTmStarBean;
	}


	public void setListaTmStarBean(List<TMStarBean> listaTmStarBean) {
		this.listaTmStarBean = listaTmStarBean;
	}


	public void pegarFtpDados(){
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public String retornaNomePais(int posicao){
		String nomePais = listaTmStar.get(posicao).getPais().getNome();
		return nomePais;
	}
	
	public int retornarIdTmStar(int posicao){
		int idTmStar = listaTmStar.get(posicao).getIdtmstar();
		return idTmStar;
	}
	
	
	public void gerarListaTmStar(){
		TmStarFacade tmStarFacade = new TmStarFacade();
		listaTmStar = tmStarFacade.lista();
		if (listaTmStar == null || listaTmStar.isEmpty()) {
			listaTmStar = new ArrayList<Tmstar>();
		}
	}
	
	public void gerarBeanTmStar(){
		TMStarBean star;
		listaTmStarBean = new ArrayList<TMStarBean>();
		for (int i = 0; i < listaTmStar.size(); i++) {
			star = new TMStarBean();
			star.setImagem("TMS0"+(i+1)+".png");
			star.setPais(listaTmStar.get(i).getPais().getNome());
			star.setPdf("TMS0"+(i+1)+".pdf");
			listaTmStarBean.add(star);
		} 
	}

}
