package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.MidiaFacade;
import br.com.travelmate.model.Midias;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class VideosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Midias> listaMidia;
	private String titulo;
	private String nomeVideo;
	  
	@PostConstruct
    public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String tipo = (String) session.getAttribute("tipomidias");
        session.removeAttribute("tipomidias");
        if (tipo==null){
        	tipo = "midias";
        }
		carregarListaMidias(tipo);
	}
	
	public List<Midias> getListaMidia() {
		return listaMidia;
	}
	public void setListaMidia(List<Midias> listaMidia) {
		this.listaMidia = listaMidia;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	

	public String getNomeVideo() {
		return nomeVideo;
	}

	public void setNomeVideo(String nomeVideo) {
		this.nomeVideo = nomeVideo;
	}

	public String carregarListaMidias(String tipo){
		if (tipo.equals("midias")){
			titulo = "Vídeos";
			MidiaFacade midiasFacade = new MidiaFacade();
	        listaMidia = midiasFacade.listar(tipo);
	        if (listaMidia==null){
	        	listaMidia = new ArrayList<Midias>();
	        }
		}else{
			titulo= "Clouds";
			MidiaFacade midiasFacade = new MidiaFacade();
	        listaMidia = midiasFacade.listarSql("select m from Midias m where m.situacao='Ativo' and m.tipo<>'midias'");
	        if (listaMidia==null){
	        	listaMidia = new ArrayList<Midias>();
	        }
		}
   
        return "";
    }
	
	
	public void pesquisar(){
		listaMidia = null;
		String sql = "Select m from Midias m where m.situacao='Ativo' and m.tipo='midias' and m.titulo like '%" + nomeVideo + "%'";
		MidiaFacade midiaFacade = new MidiaFacade();
		listaMidia = midiaFacade.listarSql(sql);
		if (listaMidia == null ||listaMidia.isEmpty()) {
			carregarListaMidias("midias");
			Mensagem.lancarMensagemInfo("Atenção", "video não encontrado");
		}else{
			Mensagem.lancarMensagemInfo("Pesquisa", "feita com sucesso");
		}
	}
	
	public void limparPesquisa(){
		nomeVideo = "";
		carregarListaMidias("midias");
	}

}
