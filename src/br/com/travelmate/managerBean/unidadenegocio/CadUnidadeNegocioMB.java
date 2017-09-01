package br.com.travelmate.managerBean.unidadenegocio;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Banco; 
import br.com.travelmate.model.Unidadenegocio; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadUnidadeNegocioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	private Unidadenegocio unidadenegocio; 
	private List<Banco> listaBanco;  
	private boolean digitosTelefone;  

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		unidadenegocio = (Unidadenegocio) session.getAttribute("unidadenegocio");
		session.removeAttribute("unidadenegocio");
		carregarBanco();
		if (unidadenegocio == null) {
			digitosTelefone=false;
			unidadenegocio = new Unidadenegocio(); 
		} else{
			if(unidadenegocio.getFone()!=null && unidadenegocio.getFone().length()>13){
				digitosTelefone=true;
			}else digitosTelefone=false;
		}
	}
 

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public List<Banco> getListaBanco() {
		return listaBanco;
	}


	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}


	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}


	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}


	public boolean isDigitosTelefone() {
		return digitosTelefone;
	}


	public void setDigitosTelefone(boolean digitosTelefone) {
		this.digitosTelefone = digitosTelefone;
	}


	public void validarDados(String msg) {
		if (unidadenegocio.getBanco() == null && unidadenegocio.getBanco().getIdbanco() == null) {
			msg = "Banco não selecionado.";
		}  
		if (unidadenegocio.getEmail() == null || unidadenegocio.getEmail().length() < 2) {
			msg = msg + "\n" + "Email não informado.";
		} else {
			if (Formatacao.validarEmail(unidadenegocio.getEmail())) {
				msg = msg + "\n" + "Email invalido.";
			}
		}
		if (unidadenegocio.getRazaoSocial() == null || unidadenegocio.getRazaoSocial().length() < 2) {
			msg = msg + "\n" + "Razão social não informado.";
		}
		if (unidadenegocio.getNomeFantasia() == null || unidadenegocio.getNomeFantasia().length() < 2) {
			msg = msg + "\n" + "Nome Fantasia não informado.";
		}
		if (unidadenegocio.getNomerelatorio() == null || unidadenegocio.getNomerelatorio().length() < 2) {
			msg = msg + "\n" + "Nome relatório não informado.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			unidadenegocio.setSituacao(true); 
			if(unidadenegocio.getIdunidadeNegocio()==null){
				if(digitosTelefone){
					unidadenegocio.setDigitosTelefone(9);
				}else{
					unidadenegocio.setDigitosTelefone(8);
				}
			}
			unidadenegocio = unidadeNegocioFacade.salvar(unidadenegocio);
			return "consUnidade";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}
 
	public String cancelar() {
		return "consUnidade";
	}
	
	
	public void carregarBanco(){
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco==null){
            listaBanco = new ArrayList<Banco>();
        }
    } 
	
	
	public String validarMascaraFone(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefone);
	}
}
