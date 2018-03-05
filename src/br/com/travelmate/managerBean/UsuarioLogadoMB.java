/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean;
   
import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas; 
import br.com.travelmate.util.Criptografia;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
 

/**
 *
 * @author Wolverine
 */
@Named
@SessionScoped
public class UsuarioLogadoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VerificarLogin verificarLogin;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private Usuario usuario;
	private String novaSenha;
	private String senhaAtual;
	private String confirmaNovaSenha;
	private String mensagemOla;
	private List<Avisos> listaAvisos;
	private boolean financeiro;
	private boolean controle;
	private boolean logar;
	private boolean comercial;
	private boolean franquia;
	private String senha;
	private String login; 
	private List<String> imagens;

	@PostConstruct
	public void init() {
		//gerarCidadeProduto();
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

	
	public boolean isControle() {
		return controle;
	}

	public void setControle(boolean controle) {
		this.controle = controle;
	}

	
	public String getMensagemOla() {
		return mensagemOla;
	}

	public void setMensagemOla(String mensagemOla) {
		this.mensagemOla = mensagemOla;
	}

	public List<Avisos> getListaAvisos() {
		return listaAvisos;
	}

	public void setListaAvisos(List<Avisos> listaAvisos) {
		this.listaAvisos = listaAvisos;
	}

	public boolean isFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(boolean financeiro) {
		this.financeiro = financeiro;
	}

	public VerificarLogin getVerificarLogin() {
		return verificarLogin;
	}

	public void setVerificarLogin(VerificarLogin verificarLogin) {
		this.verificarLogin = verificarLogin;
	}

	public List<String> getImagens() {
		return imagens;
	}

	public void setImagens(List<String> imagens) {
		this.imagens = imagens;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	} 

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isComercial() {
		return comercial;
	}

	public void setComercial(boolean comercial) {
		this.comercial = comercial;
	}

	public boolean isFranquia() {
		return franquia;
	}

	public void setFranquia(boolean franquia) {
		this.franquia = franquia;
	}
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	} 

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isLogar() {
		return logar;
	}

	public void setLogar(boolean logar) {
		this.logar = logar;
	}

	public String logar() {
		if (logar) {  
			return "paginainicial";
		} else
			return "";
	}

	public boolean validarUsuario() throws SQLException { 
		usuario = new Usuario();  
		if ((login == null) && (senha == null)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro", "Acesso Negado."));
		} else {
			String senha = "";
			try {
				senha = Criptografia.encript(this.senha);
				this.senha = "";
			} catch (NoSuchAlgorithmException ex) {
				Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
			} 
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			usuario = usuarioFacade.consultar(login, senha);
			if (usuario == null) {
				Mensagem.lancarMensagemInfo("Atenção", "Acesso negado");
			} else {
				if (usuario.getSituacao().equalsIgnoreCase("Inativo")) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage("Erro", "Acesso Negado."));
				} else {
					mensagemOlá();
					if (usuario.getDepartamento().getIddepartamento() == 3
							|| usuario.getDepartamento().getIddepartamento() == 1) {
						financeiro = true;
						franquia = false;
					} else{
						financeiro = false;
						franquia = true;
					}
					if (usuario.getTipo().equalsIgnoreCase("Gerencial")
							|| usuario.getDepartamento().getIddepartamento() == 1) {
						controle = true;
					} else
						controle = false;
					if (usuario.getDepartamento().getIddepartamento() == 8
							|| usuario.getDepartamento().getIddepartamento() == 1) {
						comercial = true;
					} else
						comercial = false; 
					if(usuario.getDataaniversario()==null){
						Map<String, Object> options = new HashMap<String, Object>();
						options.put("contentWidth", 210);
						options.put("closable", false);
						RequestContext.getCurrentInstance().openDialog("inserirDataAniversario", options, null);
					}
					return logar = true;
				}
			}
		}
		usuario = new Usuario();
		return logar = false;
	}

	public void erroLogin(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(mensagem, ""));
	}

	public void validarTrocarSenha() {
		if (usuario == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
		} else {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 280);
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("cadNovaSenha", options, null);
		}
	}

	public String confirmaNovaSenha() {
		String repetirSenhaAtual = "";
		try {
			repetirSenhaAtual = Criptografia.encript(senhaAtual);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (repetirSenhaAtual.equalsIgnoreCase(usuario.getSenha())) {
			if ((novaSenha.length() > 0) && (confirmaNovaSenha.length() > 0)) {
				if (novaSenha.equalsIgnoreCase(confirmaNovaSenha)) {
					String senha = "";
					try {
						senha = Criptografia.encript(novaSenha);
					} catch (NoSuchAlgorithmException ex) {
						Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
					}
					usuario.setSenha(senha);
					UsuarioFacade usuarioFacade = new UsuarioFacade();
					usuario = usuarioFacade.salvar(usuario);
					senhaAtual = "";
					novaSenha = "";
					confirmaNovaSenha = "";
					RequestContext.getCurrentInstance().closeDialog(usuario);
					return "";
				} else {
					novaSenha = "";
					confirmaNovaSenha = "";
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
			}
		} else {
			Mensagem.lancarMensagemInfo("", "Alteração Negada");
			senhaAtual = "";
			novaSenha = "";
			confirmaNovaSenha = "";
		}
		return "";
	}

	public String cancelarTrocaSenha() {
		novaSenha = "";
		confirmaNovaSenha = "";
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	

	public String deslogar() {
		Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		if(usuario!=null && usuario.getIdusuario()!=null) {
			verificarLogin.deslogarUsuarioSessao(usuario.getIdusuario());
		}
		return "index";
	}

	public void mensagemOlá() throws SQLException {
		mensagemOla = "Olá " + usuario.getNome();
		gerarListaImagens();
	}

	public String carregarNotificacao(String tipo, String tipo2) {
		AvisosFacade avisosFacade = new AvisosFacade();
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		int numeroAviso = 0;
		List<Avisousuario> listaAvisos = avisosFacade.listarAvisoUsuario("Select a from Avisousuario a where a.usuario.idusuario=" + usuario.getIdusuario() +
				 "  and (a.avisos.imagem='aviso' or a.avisos.imagem='lead') and a.visto=false "+
				 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc");
		if (listaAvisos == null) {
			listaAvisos = new ArrayList<Avisousuario>();
		}
		numeroAviso = listaAvisos.size();
		if (numeroAviso > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("tipo", tipo);
			session.setAttribute("tipo2", tipo2);
			session.setAttribute("idunidade", usuario.getUnidadenegocio().getIdunidadeNegocio());
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 750);
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("notificacoes", options, null);
			return "";	
		}else{
			return "consNoticias";
		}
	}
	
	public String carregarNotificacaoPromocao(String tipo, String tipo2) {
		AvisosFacade avisosFacade = new AvisosFacade();
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		int numeroAviso = 0;
		List<Avisousuario> listaAvisos = avisosFacade.listarAvisoUsuario("Select a from Avisousuario a where a.usuario.idusuario=" + usuario.getIdusuario() +
				 "  and (a.avisos.imagem='promocao') and a.visto=false "+
				 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc");
		if (listaAvisos == null) {
			listaAvisos = new ArrayList<Avisousuario>();
		}
		numeroAviso = listaAvisos.size();
		if (numeroAviso > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("tipo", tipo);
			session.setAttribute("tipo2", tipo2);
			session.setAttribute("idunidade", usuario.getUnidadenegocio().getIdunidadeNegocio());
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 750);
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("notificacoes", options, null);
			return "";	
		}else{
			return "consNoticias";
		}
	}
	
	public String carregarNotificacaoUpload(String tipo, String tipo2) {
		AvisosFacade avisosFacade = new AvisosFacade();
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		int numeroAviso = 0;
		List<Avisousuario> listaAvisos = avisosFacade.listarAvisoUsuario("Select a from Avisousuario a where a.usuario.idusuario=" + usuario.getIdusuario() +
				 "  and (a.avisos.imagem='Upload') and a.visto=false "+
				 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc");
		if (listaAvisos == null) {
			listaAvisos = new ArrayList<Avisousuario>();
		}
		numeroAviso = listaAvisos.size();
		if (numeroAviso > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("tipo", tipo);
			session.setAttribute("tipo2", tipo2);
			session.setAttribute("idunidade", usuario.getUnidadenegocio().getIdunidadeNegocio());
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 750);
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("notificacoes", options, null);
			return "";	
		}else{
			return "consNoticias";
		}
	}
	
	public void retornoDialogNotificacao(SelectEvent event){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String feed = (String) session.getAttribute("feed");
		session.removeAttribute("feed");
		if (feed != null) {
			if (feed.equalsIgnoreCase("Sim")) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/inicio/pages/noticias/consNoticias.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String carregarNotificacaoAviso() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("notificacoesAviso", options, null);
		return "";
	}

	public String vendaMatriz(Vendas vendas) {
		if (vendas.getVendasMatriz().equalsIgnoreCase("S")) {
			return "Matriz";
		} else {
			return "Franquia";
		}
	}

	
	public String importar() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("importarOrcamento", options, null);
		// Limpar limpar = new Limpar();
		/// limpar.limparAcomodacao();
		return "";
	}

	public void atualizaTempoLogado() {
		if (usuario != null && usuario.getIdusuario() != null) {
			verificarLogin.atualizarTempo(usuario.getIdusuario());
		}
	}

	public void retornoDialogAlteracaoSenha(SelectEvent event) {
		Usuario usuario = (Usuario) event.getObject();
		if (usuario != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
	}

	public void gerarListaImagens() {
		imagens = new ArrayList<String>();
		imagens.add("Irlanda");
		imagens.add("UK");
		imagens.add("Estados Unidos");
		imagens.add("Australia");
		imagens.add("Canada");
	}

	public String pdfAustralia() {
		return "resources/img/TopTMStars/AUSTRALIA_PDF";
	}

	public String getFotoUsuarioLogado(Usuario usuario) {
		String caminho = null;
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if(usuario!=null && usuario.getIdusuario()!=null){
			if (usuario.isFoto()) {
				caminho = caminho + "/usuario/" + usuario.getIdusuario() + ".jpg";
			} else
				caminho = caminho + "/usuario/0.png";
		}else{
			Mensagem.lancarMensagemErro("Atenção!", "Nenhum usuário encontrado, favor logar novamente.");  
			caminho = caminho + "/usuario/0.png";
		}
		return caminho;
	}

	public boolean mostrarCRM(int idusuario){
		if(idusuario==125){
			return true;
		}else return false;
	}
	
	public void salvarUsuario() {
		if (usuario.getDataaniversario()!=null) { 
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			usuario = usuarioFacade.salvar(usuario);
			RequestContext.getCurrentInstance().closeDialog("inicial.jsf"); 
		} else
			Mensagem.lancarMensagemErro("Atenção!", "Data de nascimento não informada."); 
	}
	
	public void retornoDialogData(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("inicial.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void gerarCidadeProduto() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		List<Paisproduto> lista = paisProdutoFacade.listar(1);
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		if (lista!=null) {
			for(int i=0;i<lista.size();i++) {				
				List<Fornecedorcidade> listaFornecedorCidade = fornecedorCidadeFacade.listar("SELECT f FROM Fornecedorcidade f where f.cidade.pais.idpais=" + lista.get(i).getPais().getIdpais());
				if (listaFornecedorCidade!=null) {
					for (int c=0;c<listaFornecedorCidade.size();c++) {
						String sql = "SELECT c FROM Cidadepaisproduto c where c.cidade.idcidade=" + listaFornecedorCidade.get(c).getCidade().getIdcidade() + 
								" and c.paisproduto.produtos.idprodutos=" + lista.get(i).getProdutos().getIdprodutos() + " and c.paisproduto.pais.idpais=" + lista.get(i).getPais().getIdpais();
						List<Cidadepaisproduto> listaCidadePaisProduto = cidadePaisProdutosFacade.listar(sql);
						if (listaCidadePaisProduto==null) {
							Cidadepaisproduto cidadePaisProduto = new Cidadepaisproduto();
							cidadePaisProduto.setCidade(listaFornecedorCidade.get(c).getCidade());
							cidadePaisProduto.setPaisproduto(lista.get(i));
							cidadePaisProdutosFacade.salvar(cidadePaisProduto);
						}
					}
				}
			}
		}
	}
	 
}
