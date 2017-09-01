package br.com.travelmate.managerBean.docsControle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.DocsFacade;
import br.com.travelmate.model.Docs;
import br.com.travelmate.model.Vendas;

@Named
@ViewScoped
public class DocsControleMB  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Docs docs;
	private Vendas vendas;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        session.removeAttribute("vendas");
        consultarDocs();
	}

	public Docs getDocs() {
		return docs;
	}

	public void setDocs(Docs docs) {
		this.docs = docs;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public String salvar(){
		boolean docsCompletos;
		DocsFacade docsFacade = new DocsFacade();
		docs = docsFacade.salvar(docs);
		if(docs.getLao().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getBooking().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getCoe().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getCor().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getI20().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getCaq().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getFamilia().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getTransferin().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getTansferout().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getSegurogovernamental().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getPassagem().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getVisto().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getCartacustodia().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getPagamento().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else if(docs.getPassaporte().equalsIgnoreCase("Não")){
			docsCompletos=false;
		}else{
			docsCompletos=true;
		}
		String documentos;
		if(docsCompletos){
			documentos="VD";
		}else documentos = "VM";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", vendas);
		RequestContext.getCurrentInstance().closeDialog(documentos);
		return "";
	}
	
	public void consultarDocs(){
		DocsFacade docsFacade = new DocsFacade();
		docs = docsFacade.consultarVenda(vendas.getIdvendas());
		if(docs==null){
			docs = new Docs();
			docs.setVendas(vendas);
		}
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog("VM");
		return "";
	}
}
