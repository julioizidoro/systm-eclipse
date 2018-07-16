package br.com.travelmate.managerBean.cartaocredito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cartaocredito; 

@Named
@ViewScoped
public class CartaoCreditoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome=""; 
	private List<Cartaocredito> listaCartaoCredito; 
	private String  botaoNovo;
	private String botaoEditar;
	
	@PostConstruct
	public void init() { 
		if ((usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==1) || (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==3)) {
			botaoNovo = "false";
			botaoEditar = "false";
		}else {
			botaoNovo = "true";
			botaoEditar = "true";
		}
		gerarlistaCartaoCredito(); 
	}
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}  

	public List<Cartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	} 

	public void setListaCartaoCredito(List<Cartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	} 

	public String novo(){
        return "cadCartaoCredito";
    }
	
	
 
	public String getBotaoNovo() {
		return botaoNovo;
	}


	public void setBotaoNovo(String botaoNovo) {
		this.botaoNovo = botaoNovo;
	}


	public String getBotaoEditar() {
		return botaoEditar;
	}


	public void setBotaoEditar(String botaoEditar) {
		this.botaoEditar = botaoEditar;
	}


	public void gerarlistaCartaoCredito(){
		CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
		String sql = "select c from Cartaocredito c where c.situacao=1";
		if ((usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=1) && (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=3)
				 && (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=9)) {
			sql = sql + " and c.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by c.nome";
        listaCartaoCredito = cartaoCreditoFacade.listar(sql);
        if (listaCartaoCredito==null){
        	listaCartaoCredito = new ArrayList<Cartaocredito>();
        }
    }
	 
	
	public String editar(Cartaocredito cartaocredito){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cartaocredito", cartaocredito);
        return "cadCartaoCredito";
    }
	
	public void pesquisar(){  
		 CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
		 String sql="select c from Cartaocredito c where c.nome like '%"+nome+"%' order by c.nome";
	     listaCartaoCredito = cartaoCreditoFacade.listar(sql);
	}
	
	public void limpar(){ 
		nome ="";
		gerarlistaCartaoCredito();
	}
}
