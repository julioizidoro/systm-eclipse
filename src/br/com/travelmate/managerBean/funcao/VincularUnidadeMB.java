package br.com.travelmate.managerBean.funcao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.UsuarioDepartamentoUnidadeFacade;
import br.com.travelmate.model.Funcao;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Usuariodepartamentounidade;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class VincularUnidadeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private List<Unidadenegocio> listaUnidade;
	private List<Unidadenegocio> listaUnidadeSelecionada;
	private String tipo;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("usuario");
		gerarListaUnidades();
		tipo = "T";
		int idgerente= usuario.getDepartamento().getUsuario().getIdusuario();
		if(usuario.getIdusuario()==idgerente){
			gerarUnidadesGerente();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Unidadenegocio> getListaUnidadeSelecionada() {
		return listaUnidadeSelecionada;
	}

	public void setListaUnidadeSelecionada(List<Unidadenegocio> listaUnidadeSelecionada) {
		this.listaUnidadeSelecionada = listaUnidadeSelecionada;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void gerarListaUnidades() {
		listaUnidade = GerarListas.listarUnidade(); 
		listaUnidadeSelecionada = new ArrayList<Unidadenegocio>();
		String sql = "select u From Usuariodepartamentounidade u where u.usuario.idusuario=" + usuario.getIdusuario()
				+ " and u.departamento.iddepartamento=" + usuario.getDepartamento().getIddepartamento();
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> lista = usuarioDepartamentoUnidadeFacade.listar(sql);
		if (lista != null && lista.size() > 0) {
			tipo = lista.get(0).getTipo();
			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).getUnidadenegocio().setTiponotificacao(lista.get(i).getTipo());
				listaUnidadeSelecionada.add(lista.get(i).getUnidadenegocio());
				listaUnidade.remove(lista.get(i).getUnidadenegocio());
			}
		}
	}
	
	public void gerarUnidadesGerente() {
		listaUnidade = GerarListas.listarUnidade(); 
		listaUnidadeSelecionada = new ArrayList<Unidadenegocio>();
		String sql = "select u From Usuariodepartamentounidade u where u.usuario.idusuario=" + usuario.getIdusuario()
				+ " and u.departamento.iddepartamento=" + usuario.getDepartamento().getIddepartamento();
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> lista = usuarioDepartamentoUnidadeFacade.listar(sql);
		if (lista != null && lista.size() > 0) {
			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).getUnidadenegocio().setTiponotificacao("T");
				listaUnidadeSelecionada.add(lista.get(i).getUnidadenegocio());
				listaUnidade.remove(lista.get(i).getUnidadenegocio());
			}
		}else{
			lista = new ArrayList<>();
			for (int i = 0; i < listaUnidade.size(); i++) {
				Usuariodepartamentounidade usuariodepartamentounidade = new Usuariodepartamentounidade();
				usuariodepartamentounidade.setDepartamento(usuario.getDepartamento());
				usuariodepartamentounidade.setUsuario(usuario);
				usuariodepartamentounidade.setTipo("T");
				usuariodepartamentounidade.setUnidadenegocio(listaUnidade.get(i));
				usuarioDepartamentoUnidadeFacade.salvar(usuariodepartamentounidade);
				listaUnidade.get(i).setTiponotificacao(usuariodepartamentounidade.getTipo());
				listaUnidadeSelecionada.add(listaUnidade.get(i));
			}
			for (int i = 0; i < listaUnidadeSelecionada.size(); i++) {
				listaUnidade.remove(listaUnidadeSelecionada.get(i)); 
			}
		}
	}
	
	public void selecionarUnidade(Unidadenegocio unidadenegocio){
		Usuariodepartamentounidade usuariodepartamentounidade = new Usuariodepartamentounidade();
		usuariodepartamentounidade.setDepartamento(usuario.getDepartamento());
		usuariodepartamentounidade.setUsuario(usuario);
		usuariodepartamentounidade.setTipo(tipo);
		usuariodepartamentounidade.setUnidadenegocio(unidadenegocio);
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		usuarioDepartamentoUnidadeFacade.salvar(usuariodepartamentounidade);
		unidadenegocio.setTiponotificacao(usuariodepartamentounidade.getTipo());
		listaUnidadeSelecionada.add(unidadenegocio);
		listaUnidade.remove(unidadenegocio); 
	}
	
	public void removerUnidade(Unidadenegocio unidadenegocio){
		String sql = "select u From Usuariodepartamentounidade u where u.usuario.idusuario=" + usuario.getIdusuario()
		+ " and u.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		Usuariodepartamentounidade usuariodepartamentounidade = usuarioDepartamentoUnidadeFacade.consultar(sql);
		usuarioDepartamentoUnidadeFacade.excluir(usuariodepartamentounidade.getIdusuariodepartamentounidade());
		listaUnidadeSelecionada.remove(unidadenegocio);
		listaUnidade.add(unidadenegocio);
		Mensagem.lancarMensagemInfo(unidadenegocio.getNomerelatorio()+" removida da lista.", "");
	}
	
	public void fechar(){
		RequestContext.getCurrentInstance().closeDialog(new Funcao());
	}

}
