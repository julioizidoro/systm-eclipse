package br.com.travelmate.managerBean.crm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadResponsavelDao;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadresponsavel;
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;



@Named
@ViewScoped
public class CadLeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AvisosDao avisosDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private LeadResponsavelDao leadResponsavelDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private String nomeCliente;
	private List<Cliente> listaCliente;
	private List<Usuario> listaConsultor;
	private Usuario consultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private Cliente cliente;
	private Publicidade publicidade;
	private List<Publicidade> listaPublicidades;
	private Lead lead;
	private String mensagem;
	private Produtos produto;
    private List<Produtos> listaProdutos;
    private boolean desabilitarUnidade=true;
   // private List<Paisproduto> listaPais;
    private boolean pesquisanome=true;
    private boolean pesquisatelefone=false;
    private boolean desabilitarConfirmar = false;
    private List<Pais> listaPais;
    private String email;
    private boolean mascara = true;
    private boolean semmascara = false;

	@PostConstruct()
	public void init() {
		unidadenegocio = new Unidadenegocio();
		cliente = new Cliente();
		lead = new Lead();
		gerarListaUnidadeNegocio();
		if(usuarioLogadoMB.getUsuario().isPertencematriz()){
			desabilitarUnidade=false;
		}else{
			desabilitarUnidade = true;
			unidadenegocio=usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaConsultor();
			consultor = usuarioLogadoMB.getUsuario();
		}
		desabilitarConfirmar = true;
		gerarListaPublicidade();
		listaProdutos = GerarListas.listarProdutos("");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar("");
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
		verificarPaisUnidade();
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public Usuario getConsultor() {
		return consultor;
	}

	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Publicidade getPublicidade() {
		return publicidade;
	}

	public void setPublicidade(Publicidade publicidade) {
		this.publicidade = publicidade;
	}

	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}

	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public boolean isDesabilitarUnidade() {
		return desabilitarUnidade;
	}

	public void setDesabilitarUnidade(boolean desabilitarUnidade) {
		this.desabilitarUnidade = desabilitarUnidade;
	}

	

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isPesquisanome() {
		return pesquisanome;
	}

	public void setPesquisanome(boolean pesquisanome) {
		this.pesquisanome = pesquisanome;
	}

	public boolean isPesquisatelefone() {
		return pesquisatelefone;
	}

	public void setPesquisatelefone(boolean pesquisatelefone) {
		this.pesquisatelefone = pesquisatelefone;
	}

	public boolean isDesabilitarConfirmar() {
		return desabilitarConfirmar;
	}

	public void setDesabilitarConfirmar(boolean desabilitarConfirmar) {
		this.desabilitarConfirmar = desabilitarConfirmar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMascara() {
		return mascara;
	}

	public void setMascara(boolean mascara) {
		this.mascara = mascara;
	}

	public boolean isSemmascara() {
		return semmascara;
	}

	public void setSemmascara(boolean semmascara) {
		this.semmascara = semmascara;
	}

	public void buscarCliente() {
		ClienteFacade clienteFacade = new ClienteFacade();
		String sql = "select c from Cliente c where (c.nome like '%" + nomeCliente + "%' or c.email like '%"+nomeCliente+"%')";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + "  and c.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by c.nome";
		listaCliente = clienteFacade.listar(sql);
		if (listaCliente == null) {
			listaCliente = new ArrayList<Cliente>();
		}
	}
	
	public void buscarTelefone() {
		ClienteFacade clienteFacade = new ClienteFacade();
		String sql = "select c from Cliente c where c.foneCelular like '%" + nomeCliente + "%'";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + "  and c.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by c.nome";
		listaCliente = clienteFacade.listar(sql);
		if (listaCliente == null) {
			listaCliente = new ArrayList<Cliente>();
		}
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public void gerarListaConsultor() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaConsultor = usuarioFacade
				.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		if (listaConsultor == null) {
			listaConsultor = new ArrayList<Usuario>();
		}
	}
	
	public void selecionarCliente(Cliente cliente) {
		this.cliente = cliente;
		unidadenegocio = cliente.getUnidadenegocio();
		gerarListaConsultor();
		publicidade = cliente.getPublicidade();
		String sql = "select l from Lead l where l.cliente.idcliente=" + cliente.getIdcliente();
		Lead lead = leadDao.consultar(sql);
		email = cliente.getEmail();
		if (lead != null && lead.getUsuario().getIdusuario() != usuarioLogadoMB.getUsuario().getIdusuario()) {
			this.lead.setJaecliente(false);
			if (lead.getSituacao() != 6 && usuarioLogadoMB.getUsuario().getUnidadenegocio()
					.getIdunidadeNegocio() != lead.getUnidadenegocio().getIdunidadeNegocio()) {
				this.lead.setJaecliente(true);
				mensagem = "Atenção! Este cliente já esta sendo atendido pelo consultor: "
						+ lead.getUsuario().getNome();
				consultor = lead.getUsuario();
				desabilitarConfirmar = true;
			} else {
				this.lead.setJaecliente(false);
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				gerarListaConsultor();
				consultor = usuarioLogadoMB.getUsuario();
				desabilitarConfirmar = false;
			}
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaConsultor();
			consultor = usuarioLogadoMB.getUsuario();
			desabilitarConfirmar = false;
		} else {
			this.lead.setJaecliente(false);
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaConsultor();
			consultor = usuarioLogadoMB.getUsuario();
			desabilitarConfirmar = false;
		}
	}
	
	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade(); 
		try {
			listaPublicidades = publicidadeFacade.listar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (listaPublicidades == null) {
			listaPublicidades = new ArrayList<Publicidade>();
		} 
	}
	
	public String salvar(){
		if(validarDados()){
			ClienteFacade clienteFacade = new ClienteFacade();
			if(cliente.getIdcliente()==null){   
				lead.setJaecliente(false);
			}
			cliente.setPublicidade(publicidade);
			cliente.setUnidadenegocio(unidadenegocio);
			cliente = clienteFacade.salvar(cliente);
			lead.setCliente(cliente); 
			TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
			Tipocontato tipocontato = tipoContatoFacade.consultar(1);
			lead.setTipocontato(tipocontato);
			lead.setSituacao(1);
			lead.setUnidadenegocio(unidadenegocio);
			lead.setUsuario(consultor);
			lead.setProdutos(produto); 
			lead.setDataenvio(new Date()); 
			lead.setHoraenvio(Formatacao.foramtarHoraString()); 
			lead.setNomeunidade(unidadenegocio.getNomerelatorio());
			lead.setDataproximocontato(null); 
			lead.setPublicidade(publicidade);
			MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
			Motivocancelamento motivo = motivoCancelamentoFacade.consultar("select m from Motivocancelamento m where m.idmotivocancelamento=1");
			lead.setMotivocancelamento1(motivo);
			lead = leadDao.salvar(lead);
			Mensagem.lancarMensagemInfo("", "Lead salvo com sucesso!");
			
			if (lead.getUsuario().getIdusuario() == usuarioLogadoMB.getUsuario().getIdusuario()) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("lead", lead);
			}

			if(consultor.getIdusuario()!=usuarioLogadoMB.getUsuario().getIdusuario()){
				Avisos avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Você recebeu uma nova lead - " + lead.getCliente().getNome() + ". Incluida por " + usuarioLogadoMB.getUsuario().getNome() 
						+ ". No dia:" + Formatacao.ConvercaoDataPadrao(new Date()));
				avisos.setIdunidade(0); 
				List<Avisousuario> lista = new ArrayList<Avisousuario>();
				Avisousuario avisousuario = new Avisousuario();  
				avisousuario.setAvisos(avisos);
				avisousuario.setUsuario(consultor);
				avisousuario.setVisto(false); 
				lista.add(avisousuario);
				avisos.setAvisousuarioList(lista);
				avisos = avisosDao.salvar(avisos);
			}
			RequestContext.getCurrentInstance().closeDialog(lead);
		}else{
			Mensagem.lancarMensagemErro("Atenção", "Campos Obrigatorio não preenchidos!");
		}
		return "";
	}
	
	public boolean validarDados(){
		if(cliente==null || cliente.getNome()==null || cliente.getFoneCelular()==null || email==null){
			return false;
		}
		if(publicidade==null || publicidade.getIdpublicidade()==null){
			return false;
		}
		if(unidadenegocio==null || unidadenegocio.getIdunidadeNegocio()==null){
			return false;
		}
		if(consultor==null || consultor.getIdusuario()==null){
			return false;
		}
		if (produto==null){
			return false;
		}
		if (lead.getPais()==null){
			return false;
		}
		if(!Formatacao.validarEmail(email)){
			return false;
		}
		
		if(cliente.getFoneCelular().length()>0){
			for (int i = 0; i < cliente.getFoneCelular().length(); i++) {
				if(i==4){ 
					char numero='9';
					if(cliente.getFoneCelular().charAt(i)!=numero){
						Mensagem.lancarMensagemErro("Telefone inválido!", "");
						return false; 
					}
				}
			}
		}  
		return true;
	}

	public String fechar(){
	    RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	  
	
	public void validarEmail() {
		if (Formatacao.validarEmail(email)) {
			email = email.replaceAll(" ", "");
			ClienteFacade clienteFacade = new ClienteFacade();
			String sql = "select c from Cliente c where (c.email like '%" + email + "%')";

			Cliente c = clienteFacade.consultarEmailSql(sql);
			if (c != null && c.getIdcliente() != null) {
				int idunidade = usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
					selecionarCliente(c);
					email = c.getEmail();
				} else if (idunidade == c.getUnidadenegocio().getIdunidadeNegocio()) {
					selecionarCliente(c);
					email = c.getEmail();
				} else {
					this.mensagem = "Atenção! Este cliente já esta cadastrado na unidade "
							+ c.getUnidadenegocio().getNomerelatorio();
				}

			} else {
				cliente = new Cliente();
				cliente.setEmail(email);
				desabilitarConfirmar = false;
				unidadenegocio = null;
				publicidade = null;
				lead.setPais(null);
				cliente.setNome("");
				cliente.setFoneCelular("");
				lead.setNotas("");
				lead.setJaecliente(false);
				if (usuarioLogadoMB.getUsuario().isPertencematriz()) {
					desabilitarUnidade = false;
				} else {
					unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
					desabilitarUnidade = true;
				}
			}
		}
	}
	
	public void mudarPesquisa(){
		if(pesquisanome){
			pesquisanome=false;
			pesquisatelefone=true;
		}else{
			pesquisanome=true;
			pesquisatelefone=false;
		}
	}
	
	public boolean retornarResponsavelUnidade() {
		int idusuario = usuarioLogadoMB.getUsuario().getIdusuario();
		List<Leadresponsavel> lista = leadResponsavelDao
				.lista(usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getUsuario().getIdusuario() == idusuario) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void verificarPaisUnidade() {
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getNome().equalsIgnoreCase("Paraguai")) {
			mascara = false;
			semmascara = true;
		}else {
			mascara = true;
			semmascara = false;
		}
	}
}
