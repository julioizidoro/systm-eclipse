package br.com.travelmate.managerBean.coprodutos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Valorcoprodutos;

@Named
@ViewScoped
public class CadCoSuplementosMB implements Serializable{
	
	 private Coprodutos coprodutos;
	 private Valorcoprodutos valorcoprodutos;
	 private List<Coprodutos> listaSelecao;
	

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	@PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        coprodutos = (Coprodutos) session.getAttribute("coprodutos");
        listaSelecao =  (List<Coprodutos>) session.getAttribute("listaSelecao");
        session.removeAttribute("coprodutos");
        setValorcoprodutos(new Valorcoprodutos());
     }
	
	public Coprodutos getCoprodutos() {
		return coprodutos;
	}
	public void setCoprodutos(Coprodutos coprodutos) {
		this.coprodutos = coprodutos;
	}
	
	public Valorcoprodutos getValorcoprodutos() {
		return valorcoprodutos;
	}

	public void setValorcoprodutos(Valorcoprodutos valorcoprodutos) {
		this.valorcoprodutos = valorcoprodutos;
	}
	
	public String salvarCoSuplemento() {
		if (valorcoprodutos.getTiposuplemento() != null && valorcoprodutos.getProdutosuplemento() != null
				&& valorcoprodutos.getValororiginal() != null && valorcoprodutos.getDatainicial() != null
				&& valorcoprodutos.getDatafinal() != null && valorcoprodutos.getNumerosemanainicial() != null
				&& valorcoprodutos.getNumerosemanafinal() != null) { 
        	valorcoprodutos.setValorpromocional(0.0f);
        	valorcoprodutos.setPromocional(false);
			if (coprodutos != null) {
				valorcoprodutos.setAno(coprodutos.getFornecedorcidadeidioma().getAno());
				salvarCoValorSuplemento(coprodutos);
			} else {
				for(int i=0;i<listaSelecao.size();i++){
					valorcoprodutos.setAno(listaSelecao.get(i).getFornecedorcidadeidioma().getAno());
					salvarCoValorSuplemento(listaSelecao.get(i));
				}
			}
			RequestContext.getCurrentInstance().closeDialog(valorcoprodutos);
		} else {
			FacesMessage mensagem = new FacesMessage("Atencao! ", "Campos obrigatorios nao preenchidos.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}
	 

	public void salvarCoValorSuplemento(Coprodutos coprodutos) {
		Valorcoprodutos valor = new Valorcoprodutos();
		valor.setAno(valorcoprodutos.getAno());
		valor.setCobranca(valorcoprodutos.getCobranca());
		valor.setCoprodutos(coprodutos);
		valor.setDatafinal(valorcoprodutos.getDatafinal());
		valor.setDatainicial(valorcoprodutos.getDatainicial());
		valor.setNumerosemanafinal(valorcoprodutos.getNumerosemanafinal());
		valor.setNumerosemanainicial(valorcoprodutos.getNumerosemanainicial());
		valor.setProdutosuplemento(valorcoprodutos.getProdutosuplemento());
		valor.setPromocional(valorcoprodutos.getPromocional());
		valor.setTipodata(valorcoprodutos.getTipodata());
		valor.setTiposuplemento(valorcoprodutos.getTiposuplemento());
		valor.setValororiginal(valorcoprodutos.getValororiginal());
		valor.setValorpromocional(valorcoprodutos.getValorpromocional());
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		valorCoProdutosFacade.salvar(valor);
	}
	
	 public String cancelarCoSuplemento(){
        RequestContext.getCurrentInstance().closeDialog(null);
        valorcoprodutos = new Valorcoprodutos();
        return "";
    }

	
	
}
