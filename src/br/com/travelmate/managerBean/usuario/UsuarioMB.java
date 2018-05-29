package br.com.travelmate.managerBean.usuario;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;

import br.com.travelmate.bean.TokenBean;
import br.com.travelmate.bean.UnidadeBean;
import br.com.travelmate.bean.UsuarioBean;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Criptografia;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class UsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome = "";
	private Unidadenegocio unidadenegocio;
	private Departamento departamento;
	private String situacao;
	private List<Unidadenegocio> listaUnidade;
	private List<Departamento> listaDepartamento;
	private List<Usuario> listaUsuario;
	private String sql;
	

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		sql = (String) session.getAttribute("sql");
		session.removeAttribute("sql");
		unidadenegocio = new Unidadenegocio();
		departamento = new Departamento();
		gerarListaUsuario();
		gerarlistaUnidade();
		gerarListaDepartamento();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public String novoUsuario() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("sql", sql);
		return "cadUsuario";
	}

	public void gerarListaUsuario() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		if(sql==null || sql.length()==0){
			sql = "select u from Usuario u where u.situacao='Ativo' order by u.nome";
		}
		listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
		verificarTmturAtivo();
	}

	public void gerarlistaUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("Select d from Departamento d order by d.nome");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<Departamento>();
		}
	}

	public String editarUsuario(Usuario usuario) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("editarUsuario", usuario);
		session.setAttribute("sql", sql);
		return "cadUsuario";
	}

	public void pesquisar() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		sql = "select u from Usuario u where u.nome like '%" + nome + "%'";
		if (situacao.length() > 1) {
			sql = sql + " and u.situacao='" + situacao + "'";
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and u.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (departamento != null && departamento.getIddepartamento() != null) {
			sql = sql + " and u.departamento.iddepartamento=" + departamento.getIddepartamento();
		}
		sql = sql + " order by u.nome, u.unidadenegocio.nomerelatorio";
		listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
		verificarTmturAtivo();
	}

	public void limpar() {
		unidadenegocio = null;
		departamento = null;
		situacao = null;
		nome = "";
		sql = "select u from Usuario u where u.situacao='Ativo' order by u.nome";
		gerarListaUsuario();
	}

	public void resetarSenhaUsuario(Usuario usuario) {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String senhaResetada;
		try {
			senhaResetada = Criptografia.encript("senha");
			if (usuario != null) {
				usuario.setSenha(senhaResetada);
				usuarioFacade.salvar(usuario);
				Mensagem.lancarMensagemInfo("Senha alterada com sucesso", "");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	public void cadastroUsuarioAPI(Usuario usuario) throws org.eclipse.persistence.exceptions.JAXBException {
        try {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            UsuarioBean usuarioBean = new UsuarioBean();
            UnidadeBean unidade = new UnidadeBean();
            TokenBean token = new TokenBean();
            usuarioBean.setCelular(usuario.getUnidadenegocio().getFone());
            usuarioBean.setCodigosystm(usuario.getIdusuario());
            usuarioBean.setDatanascimento(usuario.getDataaniversario());
            usuarioBean.setEmail(usuario.getEmail());
            usuarioBean.setNome(usuario.getNome());
            usuarioBean.setSenha("");
            unidade.setCodigosystm(usuario.getUnidadenegocio().getIdunidadeNegocio());
            unidade.setUsuario(usuarioBean);
            token.setToken("30%GEO&PORT@uX18>");
            token.setUnidade(unidade);
            String cadastroUsuario = gson.toJson(token);

            try {
                java.net.URL url = new java.net.URL("http://www.tmtur.com.br/systm/cadastrausuario");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream());
                out.write(cadastroUsuario);
                out.close();
                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                    Mensagem.lancarMensagemInfo("Ocorreu algum erro. Codigo de reposta: {0}", ""+ http_status);
                }
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                UsuarioFacade usuarioFacade = new UsuarioFacade();
                usuario.setTmturcadastro(true);
                usuario = usuarioFacade.salvar(usuario);
            } catch (java.io.IOException e) {
                Mensagem.lancarMensagemInfo("", "" + e);
            }
        } catch (Exception e) {
        		Mensagem.lancarMensagemInfo("", "" + e);
        }
	} 
	
	
	public void ativarUsuarioAPI(Usuario usuarioSystm) throws org.eclipse.persistence.exceptions.JAXBException {
        try {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            UsuarioBean usuarioBean = new UsuarioBean();
            UnidadeBean unidadeBean = new UnidadeBean();
            TokenBean token = new TokenBean();
            usuarioBean.setCodigosystm(usuarioSystm.getIdusuario());
            unidadeBean.setCodigosystm(usuarioSystm.getUnidadenegocio().getIdunidadeNegocio());
            unidadeBean.setUsuario(usuarioBean);
            token.setToken("30%GEO&PORT@uX18>");
            token.setUnidade(unidadeBean);
            String usuario = gson.toJson(token);

            try {
                java.net.URL url = new java.net.URL("http://www.tmtur.com.br/systm/ativausuario");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream());
                out.write(usuario);
                out.close();
                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                    Mensagem.lancarMensagemInfo("Ocorreu algum erro. Codigo de reposta: {0}", ""+ http_status);
                }
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                UsuarioFacade usuarioFacade = new UsuarioFacade();
                usuarioSystm.setTmturativo(true);
                usuarioSystm = usuarioFacade.salvar(usuarioSystm);
                verificarTmturAtivo();
            } catch (java.io.IOException e) {
                Mensagem.lancarMensagemInfo("", "" + e);
            }
            
        } catch (Exception e) {
        		Mensagem.lancarMensagemInfo("", "" + e);
        }
	} 
	
	public void desativarUsuarioAPI(Usuario usuarioSystm) throws org.eclipse.persistence.exceptions.JAXBException {
        try {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            UsuarioBean usuarioBean = new UsuarioBean();
            UnidadeBean unidadeBean = new UnidadeBean();
            TokenBean token = new TokenBean();
            usuarioBean.setCodigosystm(usuarioSystm.getIdusuario());
            unidadeBean.setCodigosystm(usuarioSystm.getUnidadenegocio().getIdunidadeNegocio());
            unidadeBean.setUsuario(usuarioBean);
            token.setToken("30%GEO&PORT@uX18>");
            token.setUnidade(unidadeBean);
            String usuario = gson.toJson(token);

            try {
                java.net.URL url = new java.net.URL("http://www.tmtur.com.br/systm/inativausuario");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream());
                out.write(usuario);
                out.close();
                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                    Mensagem.lancarMensagemInfo("Ocorreu algum erro. Codigo de reposta: {0}", ""+ http_status);
                }
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                UsuarioFacade usuarioFacade = new UsuarioFacade();
                usuarioSystm.setTmturativo(false);
                usuarioSystm = usuarioFacade.salvar(usuarioSystm);
                verificarTmturAtivo();
                verificarTmturAtivo();
            } catch (java.io.IOException e) {
                Mensagem.lancarMensagemInfo("", "" + e);
            }
            
        } catch (Exception e) {
        		Mensagem.lancarMensagemInfo("", "" + e);
        }
	} 
	
	
	public boolean habilitarAPI() {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public void verificarTmturAtivo() {
		for (int i = 0; i < listaUsuario.size(); i++) {
			if (listaUsuario.get(i).isTmturativo()) {
				listaUsuario.get(i).setIconeTmtur("../../resources/img/iconeSApp.png");
				listaUsuario.get(i).setTituloTmtur("Desativar usuário na TM Tur");
			}else {
				listaUsuario.get(i).setIconeTmtur("../../resources/img/iconeCheck.png");
				listaUsuario.get(i).setTituloTmtur("Ativar usuário na TM Tur");
			}
		}
	}
	
	
	public void tmTurSituacao(Usuario usuario) {
		if (usuario.isTmturativo()) {
			desativarUsuarioAPI(usuario);
			Mensagem.lancarMensagemInfo("", "Inativado com sucesso");
		}else { 
			ativarUsuarioAPI(usuario);
			Mensagem.lancarMensagemInfo("", "Ativado com sucesso");
		}
	}
	
	
	public void tmTurSituacaoCadastro(Usuario usuario) {
		if (usuario.isTmturcadastro()) {
			Mensagem.lancarMensagemInfo("", "Usuário já cadastrado na Tm Tur");
		}else {
			cadastroUsuarioAPI(usuario);
			Mensagem.lancarMensagemInfo("", "Cadastrado com sucesso");
		}
	}
	
	
}
