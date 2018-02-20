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

import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.LeadPosVendaFacade;
import br.com.travelmate.facade.LeadResponsavelFacade;
import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoFacade;
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
import br.com.travelmate.model.Leadposvenda;
import br.com.travelmate.model.Leadresponsavel;
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.model.Paisproduto;
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
public class CadLeadDistribuicaoMB implements Serializable{
	
	private static final long serialVersionUID = 1L;
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
	private boolean desabilitarUnidade=true;
    private boolean pesquisanome=true;
    private boolean pesquisatelefone=false;
    private String telaRetorno;
    private boolean desabilitarConfirmar = false;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		telaRetorno = (String) session.getAttribute("telaRetorno");
		session.removeAttribute("telaRetorno");
		unidadenegocio = new Unidadenegocio();
		cliente = new Cliente();
		lead = new Lead();
		gerarListaUnidadeNegocio();
		boolean responsavelUnidade = retornarResponsavelUnidade();
		if(usuarioLogadoMB.getUsuario().isPertencematriz() || responsavelUnidade){
			desabilitarUnidade=false;
		}else{
			unidadenegocio=usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaConsultor();
			consultor = usuarioLogadoMB.getUsuario();
		}
		gerarListaPublicidade();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = 0;
		idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		try {
			publicidade = publicidadeFacade.consultar(9);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	
	public boolean isDesabilitarUnidade() {
		return desabilitarUnidade;
	}

	public void setDesabilitarUnidade(boolean desabilitarUnidade) {
		this.desabilitarUnidade = desabilitarUnidade;
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
	
	public void selecionarCliente(Cliente cliente){
		this.cliente = cliente;
		unidadenegocio = cliente.getUnidadenegocio(); 
		gerarListaConsultor();
		publicidade = cliente.getPublicidade(); 
		LeadFacade leadFacade = new LeadFacade();
		String sql = "select l from Lead l where l.cliente.idcliente="+cliente.getIdcliente();
		Lead lead = leadFacade.consultar(sql);
		if(lead!=null && lead.getUsuario().getIdusuario()!=usuarioLogadoMB.getUsuario().getIdusuario()){
			LeadPosVendaFacade leadPosVendaFacade = new LeadPosVendaFacade();
			Leadposvenda leadposvenda = leadPosVendaFacade.consultar("SELECT l FROM Leadposvenda l WHERE l.lead.idlead=" + lead.getIdlead());
			if (leadposvenda == null) {
				this.lead.setJaecliente(true);
				if (lead.getSituacao() != 6) {
					mensagem = "Atenção! Este cliente já esta sendo atendido pelo consultor: "+lead.getUsuario().getNome(); 
					consultor = lead.getUsuario();
					desabilitarConfirmar = true;
				}else if(lead.getSituacao() == 6  && usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()==lead.getUnidadenegocio().getIdunidadeNegocio()){
					this.lead.setJaecliente(false); 
					unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
					gerarListaConsultor();
					consultor = usuarioLogadoMB.getUsuario();
					desabilitarConfirmar = false;
				}else{
					this.lead.setJaecliente(false); 
					unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
					gerarListaConsultor();
					consultor = usuarioLogadoMB.getUsuario();
					desabilitarConfirmar = false;
				}
			}else{
				this.lead.setJaecliente(false); 
				if (lead.getSituacao() != 6 && usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()!=lead.getUnidadenegocio().getIdunidadeNegocio()) {
					this.lead.setJaecliente(true);
					mensagem = "Atenção! Este cliente já esta sendo atendido pelo consultor: "+lead.getUsuario().getNome(); 
					consultor = lead.getUsuario();
					desabilitarConfirmar = true;
				}else{
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
			}  
		}else{
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
			LeadResponsavelFacade leadResponsavelFacade = new LeadResponsavelFacade();
			List<Leadresponsavel> listaResponsavel = leadResponsavelFacade.lista("SELECT l FROM Leadresponsavel l where l.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio() +
					" and l.usuario.situacao='Ativo'");
			ClienteFacade clienteFacade = new ClienteFacade();
			if(cliente.getIdcliente()==null){   
				lead.setJaecliente(false);
			}
			cliente.setPublicidade(publicidade);    
			cliente.setUnidadenegocio(unidadenegocio);
			cliente = clienteFacade.salvar(cliente);
			LeadFacade leadFacade = new LeadFacade(); 
			lead.setCliente(cliente); 
			TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
			Tipocontato tipocontato = tipoContatoFacade.consultar(1);
			lead.setTipocontato(tipocontato);
			lead.setSituacao(1);   
			lead.setDatarecebimento(new Date()); 
			lead.setHorarecebimento(Formatacao.foramtarHoraString()); 
			lead.setUnidadenegocio(unidadenegocio);
			if (listaResponsavel != null && listaResponsavel.size() > 0) {
				lead.setUsuario(listaResponsavel.get(0).getUsuario());
			}
			PaisFacade paisFacade = new PaisFacade();
			lead.setPais(paisFacade.consultar(5));
			ProdutoFacade produtoFacade = new ProdutoFacade();
			lead.setProdutos(produtoFacade.consultar(21));
			lead.setPublicidade(publicidade);
			MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
			Motivocancelamento motivo = motivoCancelamentoFacade.consultar("select m from Motivocancelamento m where m.idmotivocancelamento=1");
			lead.setMotivocancelamento1(motivo);
			lead = leadFacade.salvar(lead);
			Mensagem.lancarMensagemInfo("", "Lead salvo com sucesso!");
			

			if(listaResponsavel != null && listaResponsavel.size() >0){
				AvisosFacade avisosFacade = new AvisosFacade();
				Avisos avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Você recebeu uma nova lead.");
				avisos.setIdunidade(0); 
				avisos = avisosFacade.salvar(avisos);
				List<Avisousuario> lista = new ArrayList<Avisousuario>();
				for (int i=0;i<listaResponsavel.size();i++) {
					Avisousuario avisousuario = new Avisousuario();  
					avisousuario.setAvisos(avisos);
					avisousuario.setUsuario(listaResponsavel.get(i).getUsuario());
					avisousuario.setVisto(false); 
					lista.add(avisousuario);
				}
				avisos.setAvisousuarioList(lista);
				avisos = avisosFacade.salvar(avisos);
				RequestContext.getCurrentInstance().closeDialog(lead);
			}
		}else{
			Mensagem.lancarMensagemErro("Atenção", "Campos Obrigatorio não preenchidos!");
		}
		return "";
	}
	
	public boolean validarDados(){
		if(cliente==null || cliente.getNome()==null || cliente.getFoneCelular()==null || cliente.getEmail()==null){
			return false;
		}
		if(unidadenegocio==null || unidadenegocio.getIdunidadeNegocio()==null){
			return false;
		}
		if(!Formatacao.validarEmail(cliente.getEmail())){
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
		if(Formatacao.validarEmail(cliente.getEmail())){ 
			ClienteFacade clienteFacade = new ClienteFacade();
			Cliente c = clienteFacade.consultarEmail(cliente.getEmail());
			if(c!=null && c.getIdcliente()!=null){
				Mensagem.lancarMensagemInfo("Cliente já existente.", "");
				selecionarCliente(c);
				desabilitarConfirmar = true;
			}else {
				String email = cliente.getEmail();
				cliente = new Cliente();
				cliente.setEmail(email);
				desabilitarConfirmar = false;
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
		LeadResponsavelFacade leadResponsavelFacade = new LeadResponsavelFacade();
		List<Leadresponsavel> lista = leadResponsavelFacade
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
}
