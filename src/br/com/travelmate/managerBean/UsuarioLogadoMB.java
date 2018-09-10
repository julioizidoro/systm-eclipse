/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean;
   
import br.com.travelmate.connection.ConectionFactory;
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
		removerAtributosSessao();
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
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		ConectionFactory.getInstanceClose();
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
					FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/noticias/consNoticias.jsf");
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
	
	public void removerAtributosSessao() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.removeAttribute("acessounidade");
			session.removeAttribute("valoresacomodacao");
			session.removeAttribute("vendas");
			session.removeAttribute("listaArquivos");
			session.removeAttribute("adicionar");
			session.removeAttribute("alteracao");
			session.removeAttribute("alteracaofinanceiro");
			session.removeAttribute("ano");
			session.removeAttribute("arquivo1");
			session.removeAttribute("arquivo2");
			session.removeAttribute("arquivo3");
			session.removeAttribute("arquivo4");
			session.removeAttribute("arquivo5");
			session.removeAttribute("arquivoBean");
			session.removeAttribute("arquivosNovos");
			session.removeAttribute("aupair");
			session.removeAttribute("aviso");
			session.removeAttribute("avisousuario");
			session.removeAttribute("banco");
			session.removeAttribute("campo");
			session.removeAttribute("campoAlteracao");
			session.removeAttribute("cancelamento");
			session.removeAttribute("cartaocredito");
			session.removeAttribute("chamadaTela");
			session.removeAttribute("cliente");
			session.removeAttribute("cobranca");
			session.removeAttribute("coeficientejuros");
			session.removeAttribute("comissaocontrole");
			session.removeAttribute("competencia");
			session.removeAttribute("confirmar");
			session.removeAttribute("contapagar");
			session.removeAttribute("contareceber");
			session.removeAttribute("controle");
			session.removeAttribute("controlecurso");
			session.removeAttribute("controleseguro");
			session.removeAttribute("controlework");
			session.removeAttribute("controleworkentrevista");
			session.removeAttribute("controleworlembarque");
			session.removeAttribute("coprodutos");
			session.removeAttribute("credito");
			session.removeAttribute("crmcobranca");
			session.removeAttribute("crmcobrancahistorico");
			session.removeAttribute("curso");
			session.removeAttribute("cursospacote");
			session.removeAttribute("data");
			session.removeAttribute("dataFinal");
			session.removeAttribute("dataIni");
			session.removeAttribute("demipair");
			session.removeAttribute("departamento");
			session.removeAttribute("editar");
			session.removeAttribute("editarUsuario");
			session.removeAttribute("faseHe");
			session.removeAttribute("fatura");
			session.removeAttribute("feed");
			session.removeAttribute("filtrarEscolaBean");
			session.removeAttribute("filtroorcamentoproduto");
			session.removeAttribute("fornecedor");
			session.removeAttribute("fornecedorcidade");
			session.removeAttribute("fornecedorCidade");
			session.removeAttribute("fornecedorcidadeidioma");
			session.removeAttribute("fornecedorCidadeIdiomaProduto");
			session.removeAttribute("fornecedorcidadeidiomaproduto");
			session.removeAttribute("fornecedordocs");
			session.removeAttribute("fornecedorfinanceiro");
			session.removeAttribute("funcao");
			session.removeAttribute("grupoacesso");
			session.removeAttribute("guiaescola");
			session.removeAttribute("he");
			session.removeAttribute("highschool");
			session.removeAttribute("historico");
			session.removeAttribute("idcliente");
			session.removeAttribute("idioma");
			session.removeAttribute("idlead");
			session.removeAttribute("idunidade");
			session.removeAttribute("indiceLsita");
			session.removeAttribute("invoice");
			session.removeAttribute("lancamento");
			session.removeAttribute("lead");
			session.removeAttribute("linhacontrole");
			session.removeAttribute("listaAndamento");
			session.removeAttribute("listaArquivo");
			session.removeAttribute("listaArquivoVencidoBean");
			session.removeAttribute("listaAvisosArquivosNovos");
			session.removeAttribute("listaCancelada");
			session.removeAttribute("listaCancelamento");
			session.removeAttribute("listaCategoriaBean");
			session.removeAttribute("listaCidade");
			session.removeAttribute("listaCliente");
			session.removeAttribute("listaContas");
			session.removeAttribute("listaContasReceber");
			session.removeAttribute("listaControle");
			session.removeAttribute("listaCrmAtrasado");
			session.removeAttribute("listaCrmCobranca");
			session.removeAttribute("listaCrmCobrancaHoje");
			session.removeAttribute("listaCrmCobrancaNovos");
			session.removeAttribute("listaCrmCobrancaProx7");
			session.removeAttribute("listaCrmCobrancaTodos");
			session.removeAttribute("listaDadosEscolas");
			session.removeAttribute("listaDepartamentos");
			session.removeAttribute("listaDocs");
			session.removeAttribute("listaFinalizar");
			session.removeAttribute("listaFinanceiro");
			session.removeAttribute("listaFornecedorCidade");
			session.removeAttribute("listaHe");
			session.removeAttribute("listaInvoice");
			session.removeAttribute("listaLead");
			session.removeAttribute("listaleadtotal");
			session.removeAttribute("listaOcurso");
			session.removeAttribute("listaOpcionais");
			session.removeAttribute("listaOrcamento");
			session.removeAttribute("listaOrcamentoCurso");
			session.removeAttribute("listaOrcamentoVoluntariado");
			session.removeAttribute("listaPacotes");
			session.removeAttribute("listapais");
			session.removeAttribute("listaposvenda");
			session.removeAttribute("listaProcesso");
			session.removeAttribute("listaProductRunnersBean");
			session.removeAttribute("listaProdutos");
			session.removeAttribute("listarArquivos");
			session.removeAttribute("listaRelatorio");
			session.removeAttribute("listaSelecao");
			session.removeAttribute("listaSolicitacoes");
			session.removeAttribute("listaTabelaCidade");
			session.removeAttribute("listaTabelaPais");
			session.removeAttribute("listatipocontato");
			session.removeAttribute("listausuario");
			session.removeAttribute("listaVendaNova");
			session.removeAttribute("listaVendaPendente");
			session.removeAttribute("listaVendas");
			session.removeAttribute("listaVendasAndamento");
			session.removeAttribute("listaVendasCancelada");
			session.removeAttribute("listaVendasCursoAndamento");
			session.removeAttribute("listaVendasCursoCancelada");
			session.removeAttribute("listaVendasCursoFinalizada");
			session.removeAttribute("listaVendasCursoFinanceiro");
			session.removeAttribute("listaVendasCursoProcesso");
			session.removeAttribute("listaVendasFinalizada");
			session.removeAttribute("listaVendasFinanceiro");
			session.removeAttribute("listaVendasProcesso");
			session.removeAttribute("listaVersoes");
			session.removeAttribute("listaVoluntariadoProjeto");
			session.removeAttribute("mes");
			session.removeAttribute("metasfaturamentomensal");
			session.removeAttribute("modeloOrcamentoCurso");
			session.removeAttribute("motivocancelamento");
			session.removeAttribute("msgBilhete");
			session.removeAttribute("mtp");
			session.removeAttribute("nome");
			session.removeAttribute("nomeClientePacote");
			session.removeAttribute("nomePrograma");
			session.removeAttribute("novoCartao");
			session.removeAttribute("novoValor");
			session.removeAttribute("numero");
			session.removeAttribute("ocCliente");
			session.removeAttribute("ocurso");
			session.removeAttribute("ocursoferiado");
			session.removeAttribute("operacao");
			session.removeAttribute("orcamento");
			session.removeAttribute("orcamentoManual");
			session.removeAttribute("outroslancamentos");
			session.removeAttribute("pacote");
			session.removeAttribute("pacotesfornecedor");
			session.removeAttribute("pacoteTrecho");
			session.removeAttribute("pais");
			session.removeAttribute("palavrachave");
			session.removeAttribute("passagemaerea");
			session.removeAttribute("pasta");
			session.removeAttribute("pasta1");
			session.removeAttribute("pasta2");
			session.removeAttribute("pasta3");
			session.removeAttribute("pasta4");
			session.removeAttribute("pasta5");
			session.removeAttribute("pastavideo");
			session.removeAttribute("percentualcomissao");
			session.removeAttribute("pesquisar");
			session.removeAttribute("planoconta");
			session.removeAttribute("posicao");
			session.removeAttribute("produtoorcamentoindice");
			session.removeAttribute("produtos");
			session.removeAttribute("produtosTrainee");
			session.removeAttribute("programasTeens");
			session.removeAttribute("promocaoacomodacao");
			session.removeAttribute("promocaoAcomodacao");
			session.removeAttribute("promocaobrindecurso");
			session.removeAttribute("promocaocurso");
			session.removeAttribute("promocaotaxacurso");
			session.removeAttribute("questionariohe");
			session.removeAttribute("recinternacional");
			session.removeAttribute("redirecionar");
			session.removeAttribute("regraVenda");
			session.removeAttribute("resultadoOracmentoBean");
			session.removeAttribute("seguro");
			session.removeAttribute("seguroViagem");
			session.removeAttribute("sql");
			session.removeAttribute("sqlContasReceber");
			session.removeAttribute("tarifa");
			session.removeAttribute("telaRetorno");
			session.removeAttribute("terceiros");
			session.removeAttribute("texto");
			session.removeAttribute("tipo");
			session.removeAttribute("tipo2");
			session.removeAttribute("tipomidias");
			session.removeAttribute("tipoorcamento");
			session.removeAttribute("tipoproduto");
			session.removeAttribute("tipoVenda");
			session.removeAttribute("tisolicitacoeshistorico");
			session.removeAttribute("tisolicitaoes");
			session.removeAttribute("tituloPagina");
			session.removeAttribute("tmstar");
			session.removeAttribute("total");
			session.removeAttribute("traducaojuramentada");
			session.removeAttribute("trainee");
			session.removeAttribute("transacaoBean");
			session.removeAttribute("treinamentorespostas");
			session.removeAttribute("turismo");
			session.removeAttribute("unidade");
			session.removeAttribute("urlDocs");
			session.removeAttribute("valor");
			session.removeAttribute("valorCambio");
			session.removeAttribute("valorcampo");
			session.removeAttribute("valorcoprodutos");
			session.removeAttribute("valorEntrada");
			session.removeAttribute("valorestrainee");
			session.removeAttribute("valorJuros");
			session.removeAttribute("valorOriginal");
			session.removeAttribute("venda");
			session.removeAttribute("venda1");
			session.removeAttribute("vendacomissao");
			session.removeAttribute("vendaMatriz");
			session.removeAttribute("vendamotivopendencia");
			session.removeAttribute("vendascomissao");
			session.removeAttribute("video");
			session.removeAttribute("video1");
			session.removeAttribute("videopasta1");
			session.removeAttribute("videopasta2");
			session.removeAttribute("videopasta3");
			session.removeAttribute("videopasta4");
			session.removeAttribute("videopasta5");
			session.removeAttribute("vistos");
			session.removeAttribute("voltar");
			session.removeAttribute("voltarControleVendas");
			session.removeAttribute("voltarPagina");
			session.removeAttribute("voluntariado");
			session.removeAttribute("voluntariadopacote");
			session.removeAttribute("voluntariadoprojeto");
			session.removeAttribute("voluntariadoprojetovalor");
			session.removeAttribute("work");
			session.removeAttribute("workempregador");
			session.removeAttribute("worksponsor");
			session.removeAttribute("worktravel");
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
