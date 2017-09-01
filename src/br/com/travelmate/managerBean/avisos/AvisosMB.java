package br.com.travelmate.managerBean.avisos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AvisosMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Avisos> listaAviso;
	private Usuario usuario;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private List<Usuario> listaUsuario;
	private Date dataInicial;
	private Date dataFinal;
	private String sql;
	private String situacao="Ativo";
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			gerarListaAvisos();
			gerarListaUnidade();
		}
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public List<Avisos> getListaAviso() {
		return listaAviso;
	}


	public void setListaAviso(List<Avisos> listaAviso) {
		this.listaAviso = listaAviso;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
	
	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}


	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public String gerarSql(){
		sql = "Select a from Avisos a where a.idavisos>0";
		if ((dataInicial!=null) && (dataFinal!=null)){
			sql = sql + " and a.data>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' " 
					+ " and a.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
		}else {
			sql = sql + " and a.data>='" + Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd") + "' " 
					+ " and a.data<='" + Formatacao.SubtarirDatas(new Date(),0, "yyyy-MM-dd") + "' ";
		}
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("unidade")){
			sql = sql + " and a.usuario.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}else {
			if (unidadenegocio!=null){
				sql = sql + " and a.usuario.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		}
		if(situacao.equalsIgnoreCase("Ativo")){
			sql = sql + " and a.liberar='true'";
		}else{
			sql = sql + " and a.liberar='false'";
		}
		if (usuario!=null && usuario.getIdusuario()!=null){
			sql = sql + " and a.usuario.idusuario=" + usuario.getIdusuario();
		}
		sql = sql + " order by a.data desc, a.idavisos desc";
		return sql;
	}
	
	public void gerarListaAvisos(){
		AvisosFacade avisosFacade = new AvisosFacade();
		listaAviso = avisosFacade.lista(gerarSql());
		if (listaAviso==null){
			listaAviso = new ArrayList<Avisos>();
		}
	}
	
	public void gerarListaUsuario(){
		if (unidadenegocio!=null){
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listaUsuarioUnidade(unidadenegocio.getIdunidadeNegocio());
			if (listaUsuario==null){
				listaUsuario = new ArrayList<Usuario>();
			}
		}
	}
	
	public void gerarListaUnidade(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("unidade")){
			listaUnidade = new ArrayList<Unidadenegocio>();
			listaUnidade.add(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		}else {
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			listaUnidade = unidadeNegocioFacade.listar();
			if (listaUnidade==null){
				listaUnidade = new ArrayList<Unidadenegocio>();
			}
		}
	}
	
	public String novo(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadAvisos", options, null);
		return "";
	}
	
	public String editar(Avisos aviso){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("aviso", aviso);
        Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadAvisos", options, null);
		return "";
	}
	
	public String excluir(Avisos aviso){
		AvisosFacade avisosFacade = new AvisosFacade();
		avisosFacade.excluir(aviso);
		listaAviso.remove(aviso);
		return null;
	}
	
	public void liberar(Avisos aviso){
		AvisosFacade avisosFacade = new AvisosFacade();
		if(aviso.isLiberar()==false){
			aviso.setLiberar(true);
		}else aviso.setLiberar(false);
		aviso = avisosFacade.salvar(aviso);
	}
	
	public String pesquisar(){
		gerarSql();
		gerarListaAvisos();
		return "";
	}
	
	public String limpar(){
		usuario = null;
		unidadenegocio = null;
		dataInicial = null;
		dataFinal = null;
		gerarSql();
		gerarListaAvisos();
		situacao="Ativo";
		return "";
	}
	
	public void returnNovo(SelectEvent event){
		Avisos aviso = (Avisos) event.getObject();
		if(aviso!=null){
			gerarListaAvisos();
		}
	}
	
	public void returnEditar(){
		gerarListaAvisos();
	}
	
	public String imgLiberados(Avisos aviso){
		if(aviso.isLiberar()){
			return "../../resources/img/bolaVerde.png";
		}else return "../../resources/img/bolaVermelha.png";
	}
}
