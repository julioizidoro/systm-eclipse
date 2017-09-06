package br.com.travelmate.managerBean.acessounidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.dao.AcessoUnidadeDao;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Acessounidade;

@Named
@ViewScoped
public class AcessoUnidadeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AcessoUnidadeDao acessoUnidadeDao;
	private String nome;
	private List<Acessounidade> listaAcesso;
	
	@PostConstruct
	public void init() {
		gerarListaAcesso();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Acessounidade> getListaAcesso() {
		return listaAcesso;
	}

	public void setListaAcesso(List<Acessounidade> listaAcesso) {
		this.listaAcesso = listaAcesso;
	}
	
	public void gerarListaAcesso() {
		String sql ="SELECT a FROM Acessounidade a WHERE a.usuario.situacao='Ativo'";
		if(nome!=null && nome.length()>0) {
			sql = sql + " AND a.usuario.nome like '%"+nome+"%'";
		}
		if(!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " AND a.usuario.unidadenegocio.idunidadeNegocio="
							+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " ORDER BY a.usuario.nome";
		listaAcesso = acessoUnidadeDao.lista(sql);
		if(listaAcesso==null) {
			listaAcesso = new ArrayList<>();
		}
	}
	
	public String novo() {
		return "cadAcessoUnidade";
	}
	
	public String editar(Acessounidade acessounidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("acessounidade", acessounidade);
		return "cadAcessoUnidade";
	}
	
	public void limpar() {
		nome = "";
		gerarListaAcesso();
	}
	
	public String retornarAcesso(boolean acesso) {
		if(acesso) {
			return "Possui acesso";
		}else return "Acesso restrito";
	}

}
