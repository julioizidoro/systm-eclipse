package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
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
import br.com.travelmate.facade.FornecedorCidadeDepoimentoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadedepoimento;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class FornecedorCidadeDepoimentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Fornecedorcidadedepoimento> listaDepoimento; 
	private Fornecedorcidade fornecedorcidade; 
	private Fornecedorcidadedepoimento depoimento;
	private List<Cliente> listaCliente; 
	private String nome;

	@PostConstruct
	public void init() { 
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade"); 
			session.removeAttribute("fornecedorcidade"); 
			depoimento = new Fornecedorcidadedepoimento();
			gerarListaDepoimentos(); 
		}
	}
 
	public List<Fornecedorcidadedepoimento> getListaDepoimento() {
		return listaDepoimento;
	}
 
	public void setListaDepoimento(List<Fornecedorcidadedepoimento> listaDepoimento) {
		this.listaDepoimento = listaDepoimento;
	}
 
	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}
 
	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}
 
	public Fornecedorcidadedepoimento getDepoimento() {
		return depoimento;
	}
 
	public void setDepoimento(Fornecedorcidadedepoimento depoimento) {
		this.depoimento = depoimento;
	}
 
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void salvarDepoimento(){
		depoimento.setData(new Date());
		depoimento.setFornecedorcidade(fornecedorcidade);
		depoimento.setApovado(false);
		depoimento.setUsuario(usuarioLogadoMB.getUsuario());
		FornecedorCidadeDepoimentoFacade fornecedorCidadeDepoimentoFacade = new FornecedorCidadeDepoimentoFacade();
		if(!usuarioLogadoMB.getUsuario().isDepoimentos()){
			fornecedorCidadeDepoimentoFacade.salvar(depoimento);
			depoimento = new Fornecedorcidadedepoimento();
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "Depoimento aguardando aprovação.");
			String sql = "SELECT u FROM Usuario u where u.situacao='Ativo' and u.depoimentos=true";
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			List<Usuario> lista = usuarioFacade.listar(sql);
			if(lista!=null && lista.size()>0){
				Avisos avisos; 
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setIdunidade(usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setDepartamento("outros");   
				avisos.setTexto("Novo depoimento inserido por "
									+usuarioLogadoMB.getUsuario().getNome()+" aguardando aprovação!");
				AvisosFacade avisosFacade = new AvisosFacade();
				avisos =  avisosFacade.salvar(avisos);
				for (int i = 0; i < lista.size(); i++) {
					Avisousuario avisousuario = new Avisousuario();
					avisousuario.setAvisos(avisos);
					avisousuario.setUsuario(lista.get(i));
					avisousuario.setVisto(false);
					avisosFacade.salvar(avisousuario);
				}
			}
		}else{
			depoimento.setApovado(true);   
			fornecedorCidadeDepoimentoFacade.salvar(depoimento);
			gerarListaDepoimentos();
			depoimento = new Fornecedorcidadedepoimento();
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		}
	}
	
	public void gerarListaDepoimentos(){
		String sql = "select f from Fornecedorcidadedepoimento f where f.apovado=true"
			+ " and f.fornecedorcidade.idfornecedorcidade="+fornecedorcidade.getIdfornecedorcidade();
		FornecedorCidadeDepoimentoFacade fornecedorCidadeDepoimentoFacade = new FornecedorCidadeDepoimentoFacade();
		listaDepoimento = fornecedorCidadeDepoimentoFacade.lista(sql);
		if(listaDepoimento==null){
			listaDepoimento = new ArrayList<Fornecedorcidadedepoimento>();
		}
	}
	
	public void gerarListaCliente() {
		ClienteFacade clienteFacade = new ClienteFacade();
		String sql = "select c from Cliente c where c.nome like '%"+nome+"%'";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = sql + " and c.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by c.nome";
		listaCliente = clienteFacade.listar(sql);
		if (listaCliente == null) {
			listaCliente = new ArrayList<Cliente>();
		}
	}

 
	public boolean mostrarContatos(Fornecedorcidadedepoimento depoimento){
		if(depoimento.isAceitacontato()){
			return true;
		}
		return false;
	}
	
	public void selecionarCliente(Cliente cliente){
		depoimento.setCliente(cliente);
		nome = cliente.getNome();
	}
}
