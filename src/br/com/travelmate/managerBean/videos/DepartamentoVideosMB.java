package br.com.travelmate.managerBean.videos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.VideoPasta1Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cloud.midia.ArquivosVencidosBean;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Pastavideo;
import br.com.travelmate.model.Videopasta1;


@Named
@ViewScoped
public class DepartamentoVideosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pastavideo pastavideo;
	private List<Pastavideo> listaPastaVideo;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Departamento departamento;
	private List<Departamento> listaDepartamentos;
	private boolean semDepartamentos;
	private List<Videopasta1> listaVideoPasta1;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private String tituloPagina = "";
	private boolean pesquisar;
	private String nomeArquivo;
	private String tipoArquivo;
	private int pasta;
	
	
	@PostConstruct
	public void init(){
		gerarListaDepartamento();
	}


	public Pastavideo getPastavideo() {
		return pastavideo;
	}


	public void setPastavideo(Pastavideo pastavideo) {
		this.pastavideo = pastavideo;
	}


	public List<Pastavideo> getListaPastaVideo() {
		return listaPastaVideo;
	}


	public void setListaPastaVideo(List<Pastavideo> listaPastaVideo) {
		this.listaPastaVideo = listaPastaVideo;
	}
	
	
	


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}


	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}


	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}
	
	

	
	
	public boolean isSemDepartamentos() {
		return semDepartamentos;
	}
   

	public void setSemDepartamentos(boolean semDepartamentos) {
		this.semDepartamentos = semDepartamentos;
	}


	public List<Videopasta1> getListaVideoPasta1() {
		return listaVideoPasta1;
	}


	public void setListaVideoPasta1(List<Videopasta1> listaVideoPasta1) {
		this.listaVideoPasta1 = listaVideoPasta1;
	}


	public ArquivosVencidosBean getArquivosVencidosBean() {
		return arquivosVencidosBean;
	}


	public void setArquivosVencidosBean(ArquivosVencidosBean arquivosVencidosBean) {
		this.arquivosVencidosBean = arquivosVencidosBean;
	}


	public List<ArquivosVencidosBean> getListaArquivoVencidoBean() {
		return listaArquivoVencidoBean;
	}


	public void setListaArquivoVencidoBean(List<ArquivosVencidosBean> listaArquivoVencidoBean) {
		this.listaArquivoVencidoBean = listaArquivoVencidoBean;
	}


	public String getTituloPagina() {
		return tituloPagina;
	}


	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}


	public boolean isPesquisar() {
		return pesquisar;
	}


	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}


	public String getNomeArquivo() {
		return nomeArquivo;
	}


	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}


	public String getTipoArquivo() {
		return tipoArquivo;
	}


	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}


	public int getPasta() {
		return pasta;
	}


	public void setPasta(int pasta) {
		this.pasta = pasta;
	}


	public String posicaoInteracaoUiRepeat(String posicao){
		String classe = "";
		if (posicao.equalsIgnoreCase("0") || posicao.equalsIgnoreCase("4") || posicao.equalsIgnoreCase("8") || posicao.equalsIgnoreCase("12") || posicao.equalsIgnoreCase("16") || posicao.equalsIgnoreCase("20") || posicao.equalsIgnoreCase("24") || posicao.equalsIgnoreCase("28") || posicao.equalsIgnoreCase("32") || posicao.equalsIgnoreCase("36") || posicao.equalsIgnoreCase("40")  ) {
			classe = "panel-stat3 bg-danger";
			return classe;
		}else if(posicao.equalsIgnoreCase("1") || posicao.equalsIgnoreCase("5") || posicao.equalsIgnoreCase("9") || posicao.equalsIgnoreCase("13") || posicao.equalsIgnoreCase("17") || posicao.equalsIgnoreCase("21") || posicao.equalsIgnoreCase("25") || posicao.equalsIgnoreCase("29") || posicao.equalsIgnoreCase("33") || posicao.equalsIgnoreCase("37") || posicao.equalsIgnoreCase("41") ){
			classe = "panel-stat3 bg-info";
			return classe;
		}else if (posicao.equalsIgnoreCase("2") || posicao.equalsIgnoreCase("6") || posicao.equalsIgnoreCase("10") || posicao.equalsIgnoreCase("14") || posicao.equalsIgnoreCase("18") || posicao.equalsIgnoreCase("22") || posicao.equalsIgnoreCase("26") || posicao.equalsIgnoreCase("30") || posicao.equalsIgnoreCase("34") || posicao.equalsIgnoreCase("38") || posicao.equalsIgnoreCase("42") ){
			classe = "panel-stat3 bg-warning";
			return classe;
		}else{
			classe = "panel-stat3 bg-success";
			return classe;
		}
	}
	
	
	public Integer retornarNumeroVideos(Pastavideo pastavideo){
		Integer nVideos = null;
		if (pastavideo.getVideoList() == null) {
			nVideos = 0;
		}else{
			nVideos = pastavideo.getVideoList().size();
		}
		return nVideos;
	}
	
	
	
	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
			acesso = true;
		}else{ 
			acesso = false;
		}
		return acesso;
	}
	
	
	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamentos = departamentoFacade.listar("Select d from Departamento d where d.pasta=1 order by d.nome");
		if (listaDepartamentos == null) {
			listaDepartamentos = new ArrayList<Departamento>();
		}
	}
	
	public void semConteudo() {
		if (listaDepartamentos == null || listaDepartamentos.isEmpty()) {
			semDepartamentos = true;
		} else {
			semDepartamentos = false;
		}
	}
	
	
	public String pasta1Video(Departamento departamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1Video";
	}
	
	
	public Integer gerarTotalPastas(Departamento departamento) {
		VideoPasta1Facade videoPasta1Facade = new VideoPasta1Facade();
		Integer numeroTotal = 0;
		String sql = "Select v from Videopasta1 v where v.departamento.iddepartamento=" + departamento.getIddepartamento();
		listaVideoPasta1 = videoPasta1Facade.listar(sql);
		if (listaVideoPasta1 == null) {
			listaVideoPasta1 = new ArrayList<>();
			numeroTotal = listaVideoPasta1.size();
			return numeroTotal;
		} else {
			numeroTotal = listaVideoPasta1.size();
			return numeroTotal;
		}
	}
	
	
	public String pesquisar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tituloPagina = "Pesquisar Arquivos";
		pesquisar = true;
		pasta = 1;
		session.setAttribute("palavrachave", nomeArquivo);
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("pasta", pasta);
		return "pesquisaVideos";
	}

	
	 

}
