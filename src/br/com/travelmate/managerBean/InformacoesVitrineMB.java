package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class InformacoesVitrineMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Ocurso ocurso;    

	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
        ocurso = (Ocurso) session.getAttribute("ocurso");
        session.removeAttribute("ocurso");  
	}

	 
	
 
	public Ocurso getOcurso() {
		return ocurso;
	}




	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}




	public String cancelar() { 
		RequestContext.getCurrentInstance().closeDialog(null); 
		return "";
	} 
	
	public String gerarOrçamento(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("ocurso", ocurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("gerarOrcamentoPacote", options, null);
		return "";
	}   
	
	public String acomodacaoInclusa(){ 
		return "Não incluso";  
	}
	 
	
	public String retornarCargaHoraria(){ 
		return ocurso.getCargahoraria();  
	}  
	
	public String retornarCambio(){  
		if(ocurso.getValorcambio()>0){
			return ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas().getSigla()
					+" "+Formatacao.formatarFloatString(ocurso.getValorcambio());  
		}else{
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), 
					ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			return ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas().getSigla()
					+" "+Formatacao.formatarFloatString(cambio.getValor())
					+" referente o dia "+Formatacao.ConvercaoDataPadrao(new Date())+".";  
		} 
	}  
	
	
	public String retornarMesAnoInicio(){
		int mes = Formatacao.getMesData(ocurso.getDatainicio());
		int ano = Formatacao.getAnoData(ocurso.getDatainicio());
		String retorno = Formatacao.nomeMes(mes+1)+"/"+ano;
		return retorno;
	}
	
	public String retornarAnoInicio(){
		int ano = Formatacao.getAnoData(ocurso.getDatainicio());
		String retorno = ""+ano;
		return retorno;
	}

}
