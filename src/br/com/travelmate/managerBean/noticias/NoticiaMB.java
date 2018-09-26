package br.com.travelmate.managerBean.noticias;

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

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class NoticiaMB implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AvisosDao avisosDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Avisousuario> listaAvisosUsuario;
	private List<Departamento> listaDepartamento;
	private Departamento departamento;
	private Date dataInicial;
	private Date dataFinal;
	private String tipo = "";
	private List<Usuario> listaUsuario;
	private Usuario usuario;
	private String texto = "";
	private boolean informacao;
	private boolean upload;
	private boolean aviso;
	private boolean lead;
	private boolean promocao;
	private boolean visualizado;
	private List<Avisousuario> listaAvisosTudo;
	private Avisousuario avisousuario;
	
	    
	
	  
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		avisousuario = (Avisousuario) session.getAttribute("avisousuario");
		session.removeAttribute("avisousuario");
		if (avisousuario == null) {
			gerarListaAvisosInicial();
			gerarListaDepartamento();
		}
	}

	  
	
	
	
	public List<Avisousuario> getListaAvisosUsuario() {
		return listaAvisosUsuario;
	}





	public void setListaAvisosUsuario(List<Avisousuario> listaAvisosUsuario) {
		this.listaAvisosUsuario = listaAvisosUsuario;
	}





	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}





	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}





	public Departamento getDepartamento() {
		return departamento;
	}





	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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





	public String getTipo() {
		return tipo;
	}





	public void setTipo(String tipo) {
		this.tipo = tipo;
	}





	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}





	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}





	public Usuario getUsuario() {
		return usuario;
	}





	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}





	public String getTexto() {
		return texto;
	}





	public void setTexto(String texto) {
		this.texto = texto;
	}





	public boolean isInformacao() {
		return informacao;
	}





	public void setInformacao(boolean informacao) {
		this.informacao = informacao;
	}





	public boolean isUpload() {
		return upload;
	}





	public void setUpload(boolean upload) {
		this.upload = upload;
	}





	public boolean isAviso() {
		return aviso;
	}





	public void setAviso(boolean aviso) {
		this.aviso = aviso;
	}





	public boolean isLead() {
		return lead;
	}





	public void setLead(boolean lead) {
		this.lead = lead;
	}





	public boolean isPromocao() {
		return promocao;
	}





	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
	}





	public boolean isVisualizado() {
		return visualizado;
	}





	public void setVisualizado(boolean visualizado) {
		this.visualizado = visualizado;
	}





	public List<Avisousuario> getListaAvisosTudo() {
		return listaAvisosTudo;
	}





	public void setListaAvisosTudo(List<Avisousuario> listaAvisosTudo) {
		this.listaAvisosTudo = listaAvisosTudo;
	}





	public Avisousuario getAvisousuario() {
		return avisousuario;
	}





	public void setAvisousuario(Avisousuario avisousuario) {
		this.avisousuario = avisousuario;
	}





	public void gerarListaAvisosInicial(){
		String  dataString = Formatacao.SubtarirDatas(new Date(), 15, "dd/MM/yyyy");
		Date dataFiltro = Formatacao.ConvercaoStringData(dataString);
		listaAvisosTudo = avisosDao.listarAvisoUsuario("SELECT a FROM Avisousuario a WHERE a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario()
				 + " and a.avisos.data>='" + Formatacao.ConvercaoDataSql(dataFiltro) + "' order by a.avisos.data DESC");
		if (listaAvisosTudo == null) {
			listaAvisosTudo = new ArrayList<Avisousuario>();
		}
		listaAvisosUsuario = new ArrayList<Avisousuario>();
		for (int i = 0; i < listaAvisosTudo.size(); i++) {
			Avisousuario avisousuario = new Avisousuario();
			avisousuario = listaAvisosTudo.get(i);
			listaAvisosUsuario.add(avisousuario);
			
		}
	}
	
	
	public String retornarDataInicio(Date datainicio){
		String retorno = Formatacao.ConvercaoDataPadrao(datainicio);
		return retorno;
	}
	
	
	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("Select d from Departamento d order by d.nome");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<Departamento>();
		}
	}
	
	
	public void pesquisarAvisosUsuario(){
		String sql = "SELECT a FROM Avisousuario a WHERE a.avisos.imagem like '%" + tipo + "%' and a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
		if (departamento != null && departamento.getIddepartamento() != null) {
			sql = sql + " and a.avisos.usuario.departamento.iddepartamento=" + departamento.getIddepartamento();
		}
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " and a.avisos.data>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and a.avisos.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and a.avisos.usuario.idusuario=" + usuario.getIdusuario();
		}
		sql = sql + " order by a.avisos.data DESC";
		listaAvisosTudo = avisosDao.listarAvisoUsuario(sql);
		if (listaAvisosTudo == null) {
			listaAvisosTudo = new ArrayList<Avisousuario>();
		}
		listaAvisosUsuario = new ArrayList<Avisousuario>();
		for (int i = 0; i < listaAvisosTudo.size(); i++) {
			Avisousuario avisousuario = new Avisousuario();
			avisousuario = listaAvisosTudo.get(i);
			listaAvisosUsuario.add(avisousuario);
			
		}
		Mensagem.lancarMensagemInfo("Pesquisa com sucesso!", "");
	}
	
	
	public void limpar(){
		departamento = null;
		tipo = "";
		dataFinal = null;
		dataInicial = null;
		gerarListaAvisosInicial();
	}
	
	
	public boolean verficiarTexto(Avisousuario aviso){
		if (aviso.getAvisos().getTexto().length() > 400) {
			return true;
		}
		return false;
	}
	
	
	public boolean verificarVisto(Avisousuario aviso){
		if (aviso.getVisto()) {
			return false;
		}
		return true;
	}
	
	
	public void limparVisualizacao(Avisousuario avisousuario){
		avisousuario.setVisto(true);
		avisosDao.salvar(avisousuario);
		Mensagem.lancarMensagemInfo("Salvo com sucesso!!", "");
	}
	
	
	
	public String retornarTexto(Avisousuario aviso){
		if (aviso.getAvisos().getTexto().length() > 400) {
			return aviso.getAvisos().getTexto().substring(0, 300) + " ....";
		}
		return aviso.getAvisos().getTexto();
	}
	
	
	public void setarAviso(Avisousuario avisousuario){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("avisousuario", avisousuario);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("visualizarNoticia", options, null);
	}
	
	
	
	public void filtrar(){
		listaAvisosUsuario = new ArrayList<Avisousuario>();
		Avisousuario avisousuario;
		if (visualizado) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				if (listaAvisosTudo.get(i).getVisto()) {
					avisousuario = new Avisousuario();
					avisousuario = listaAvisosTudo.get(i);
					listaAvisosUsuario.add(avisousuario);
				}
			}    
		}
		if (informacao) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				if (listaAvisosTudo.get(i).getAvisos().getImagem().equalsIgnoreCase("informacao")) {
					avisousuario = new Avisousuario();
					avisousuario = listaAvisosTudo.get(i);
					listaAvisosUsuario.add(avisousuario);
				}
			}
		}
		if (upload) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				if (listaAvisosTudo.get(i).getAvisos().getImagem().equalsIgnoreCase("Upload")) {
					avisousuario = new Avisousuario();
					avisousuario = listaAvisosTudo.get(i);
					listaAvisosUsuario.add(avisousuario);
				}
			}
		}
		if (promocao) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				if (listaAvisosTudo.get(i).getAvisos().getImagem().equalsIgnoreCase("promocao")) {
					avisousuario = new Avisousuario();
					avisousuario = listaAvisosTudo.get(i);
					listaAvisosUsuario.add(avisousuario);
				}
			}
		}
		if (lead) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				if (listaAvisosTudo.get(i).getAvisos().getImagem().equalsIgnoreCase("lead")) {
					avisousuario = new Avisousuario();
					avisousuario = listaAvisosTudo.get(i);
					listaAvisosUsuario.add(avisousuario);
				}
			}
		}
		if (aviso) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				if (listaAvisosTudo.get(i).getAvisos().getImagem().equalsIgnoreCase("aviso")) {
					avisousuario = new Avisousuario();
					avisousuario = listaAvisosTudo.get(i);
					listaAvisosUsuario.add(avisousuario);
				}
			}
		}
		if (!visualizado && !promocao && !aviso && !lead && !upload && !informacao) {
			for (int i = 0; i < listaAvisosTudo.size(); i++) {
				avisousuario = new Avisousuario();
				avisousuario = listaAvisosTudo.get(i);
				listaAvisosUsuario.add(avisousuario);
				
			}
		}
	}
	
	
	
	public void gerarListaUsuario(){
		if (departamento != null && departamento.getIddepartamento() != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listar("SELECT u FROM Usuario u WHERE u.departamento.iddepartamento=" + departamento.getIddepartamento());
			if (listaUsuario == null) {
				listaUsuario = new ArrayList<Usuario>();
			}
		}
	}
	
	
	public boolean verificarView(Avisousuario aviso){
		if (aviso.getVisto()) {
			return true;
		}
		return false;
	}

}
