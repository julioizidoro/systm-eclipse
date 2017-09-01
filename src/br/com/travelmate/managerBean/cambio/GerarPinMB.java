package br.com.travelmate.managerBean.cambio;


import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Pincambio;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class GerarPinMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private List<Usuario> listaConsultor;
    private Usuario usuario;
    private String numPin="0";
    
    @PostConstruct()
    public void init(){
    	if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
	    	unidadenegocio = new Unidadenegocio();
	    	usuario = new Usuario();
	        gerarListaUnidadeNegocio();
    	}
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
    
    public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNumPin() {
		return numPin;
	}

	public void setNumPin(String numPin) {
		this.numPin = numPin;
	}

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
	public void gerarListaConsultor(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        listaConsultor = usuarioFacade.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio()+" order by u.nome");
        if (listaConsultor==null){
            listaConsultor = new ArrayList<Usuario>();
        }
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    public void gerarPIN(){
    	boolean gerar =  true;
    	if (unidadenegocio==null){
            gerar = false;
        }
        if (usuario==null){
            gerar = false;
        }
        if (gerar){
            Random random = new Random();
            int x = random.nextInt(1000);
            x = x + 1;
            int numPin = usuario.getIdusuario() + 10 + unidadenegocio.getIdunidadeNegocio() + 15 + x;
            this.numPin = String.valueOf(numPin);
            Pincambio pinCamio = new Pincambio();
            pinCamio.setConsultor(usuario.getIdusuario());
            pinCamio.setDataEmissao(new Date());
            pinCamio.setUtilizado("N");
            pinCamio.setNumero(this.numPin);  
            pinCamio.setUsuario(usuarioLogadoMB.getUsuario());
            UsuarioFacade usuarioFacade = new UsuarioFacade();
            pinCamio = usuarioFacade.salvar(pinCamio);
        }
    }
}
