package br.com.travelmate.managerBean.financeiro.vendamotivopendencia;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VendaMotivoPendenciaFacade;
import br.com.travelmate.model.Vendamotivopendencia;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVendaMotivoPendenciaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vendamotivopendencia vendamotivopendencia;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vendamotivopendencia = (Vendamotivopendencia) session.getAttribute("vendamotivopendencia");
		session.removeAttribute("vendamotivopendencia");
		if (vendamotivopendencia == null) {
			vendamotivopendencia = new Vendamotivopendencia();
		}
	}

	public Vendamotivopendencia getVendamotivopendencia() {
		return vendamotivopendencia;
	}

	public void setVendamotivopendencia(Vendamotivopendencia vendamotivopendencia) {
		this.vendamotivopendencia = vendamotivopendencia;
	}
	
	

	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Vendamotivopendencia());
	}
	
	
	public void salvar(){
		String descricao = "";
		VendaMotivoPendenciaFacade vendaMotivoPendenciaFacade = new VendaMotivoPendenciaFacade();
		try {
			descricao = new String(vendamotivopendencia.getDescricao().getBytes(Charset.defaultCharset()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			  
		}
		if (descricao != null && descricao.length() > 0) {
			vendamotivopendencia.setDescricao(descricao);
			vendamotivopendencia = vendaMotivoPendenciaFacade.salvar(vendamotivopendencia);
			RequestContext.getCurrentInstance().closeDialog(vendamotivopendencia);
		}else{
			Mensagem.lancarMensagemInfo("Informe uma descrição", "");
		}
	}

}
