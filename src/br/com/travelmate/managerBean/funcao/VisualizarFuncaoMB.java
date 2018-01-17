package br.com.travelmate.managerBean.funcao;

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

import org.primefaces.component.splitbutton.SplitButton;
import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FuncaoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Funcao;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class VisualizarFuncaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Departamento> listaDepartamento;
	private Departamento departamento;
	private List<Usuario> listaUsuario;
	private Usuario usuario;
	private Funcao funcao;
	private List<UsuarioBean> listaUsuarioBean;
	private UsuarioBean usuarioBean;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		departamento = (Departamento) session.getAttribute("departamento");
		session.removeAttribute("departamento");
		gerarListaUsuarios();
		if (funcao == null) {
			funcao = new Funcao();
		}
		if (listaUsuarioBean == null) {
			listaUsuarioBean = new ArrayList<>();
		}    
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

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<UsuarioBean> getListaUsuarioBean() {
		return listaUsuarioBean;
	}

	public void setListaUsuarioBean(List<UsuarioBean> listaUsuarioBean) {
		this.listaUsuarioBean = listaUsuarioBean;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public void gerarListaUsuarios() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuarioBean = new ArrayList<>();
		String sql = "";
		if (departamento != null) {
			sql = "Select u From Usuario u Where u.departamento.iddepartamento=" + departamento.getIddepartamento()
					+ " and u.situacao='Ativo'";
			listaUsuario = usuarioFacade.listar(sql);
			if (listaUsuario == null) {
				listaUsuario = new ArrayList<>();
			}

			if (listaUsuario.size() == 0) {
				Mensagem.lancarMensagemInfo("Nenhum usuario encontrado neste departamento", "");

			} else {
				for (int i = 0; i < listaUsuario.size(); i++) {
					usuarioBean = new UsuarioBean();
					usuarioBean.setUsuario1(listaUsuario.get(i));
					if ((i + 1) < listaUsuario.size()) {
						usuarioBean.setUsuario2(listaUsuario.get(i + 1));
						i++;
						if ((i + 1) < listaUsuario.size()) {
							usuarioBean.setUsuario3(listaUsuario.get(i + 1));
							i++;
						} else {
							usuarioBean.setUsuario3(null);
						}
					} else {
						usuarioBean.setUsuario2(null);
						usuarioBean.setUsuario3(null);
					}
					listaUsuarioBean.add(usuarioBean);
				}
			}
		}
	}

	public Funcao pegarInformacoesUsuario(Usuario usuario) {
		FuncaoFacade funcaoFacade = new FuncaoFacade();
		funcao = funcaoFacade.consultar("Select f From Funcao f Where f.usuario.idusuario=" + usuario.getIdusuario());
		if (funcao == null) {
			funcao = new Funcao();
		}
		return funcao;
	}

	public String pegarPrimeiroNome(String nome) {
		String primeiroNome = "";
		for (int i = 0; i < nome.length(); i++) {
			if (!nome.substring(i).equalsIgnoreCase(" ")) {
				primeiroNome = primeiroNome + nome.substring(i);
			}
		}
		return primeiroNome;
	}

	public String visualizarFuncao(Usuario usuario) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("usuario", usuario);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("verrFuncao", options, null);
		return "";
	}

	public String getFotoUsuarioLogado(Usuario usuario) {
		String caminho = null;
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (usuario != null && usuario.getIdusuario() != null) {
			if (usuario.isFoto()) {
				caminho = caminho + "/usuario/" + usuario.getIdusuario() + ".jpg";
			} else
				caminho = caminho + "/usuario/0.png";
			return caminho;
		}
		return null;
	}

	public String vincularUnidades(Usuario usuario) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("usuario", usuario);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("vincularUnidade", options, null);
		return "";
	}
	
	public boolean mostrarVincular(Usuario usuario){
		if(usuario!=null){
			int idgerente= usuario.getDepartamento().getUsuario().getIdusuario();
			if(usuarioLogadoMB.getUsuario().getIdusuario()==idgerente
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==1){
				return true;
			}
		}
		return false;
	}
	
	public String voltarConsDepartamento() {
		return "consDepartamentoFuncao";
	}

}
