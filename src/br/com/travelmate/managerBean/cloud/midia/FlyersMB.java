package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.travelmate.facade.MidiaFacade;
import br.com.travelmate.model.Midias;

@Named
@ViewScoped
public class FlyersMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Midias> listaMidia;
	private String flyers;
	
	@PostConstruct
    public void init(){
		String tipo = "flyers";
		carregarListaMidias(tipo);
	}
	
	public List<Midias> getListaMidia() {
		return listaMidia;
	}
	public void setListaMidia(List<Midias> listaMidia) {
		this.listaMidia = listaMidia;
	}
	

	public String getFlyers() {
		return flyers;
	}

	public void setFlyers(String flyers) {
		this.flyers = flyers;
	}

	public String carregarListaMidias(String tipo){
		MidiaFacade midiasFacade = new MidiaFacade();
        listaMidia = midiasFacade.listar(tipo);
        if (listaMidia==null){
        	listaMidia = new ArrayList<Midias>();
        }
        return "";
    }
    
	public void carregarImagem(Midias midias){
		flyers = midias.getLink();
	}
}
