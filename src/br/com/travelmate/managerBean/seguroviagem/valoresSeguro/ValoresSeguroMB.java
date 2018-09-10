package br.com.travelmate.managerBean.seguroviagem.valoresSeguro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Valoresseguro;

@Named
@ViewScoped
public class ValoresSeguroMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Valoresseguro> listaValores;
	private String periodo="";
	private String status;
	private String tipo;
	
	@PostConstruct()
	public void init() {
		carregarValores();
	}
	
	public List<Valoresseguro> getListaValores() {
		return listaValores;
	}
	public void setListaValores(List<Valoresseguro> listaValores) {
		this.listaValores = listaValores;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void carregarValores() {
		String sql = "select v from Valoresseguro v where v.situacao='Ativo'";
		ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
		listaValores = valorSeguroFacade.listar(sql);
	}
	
	public void desativar(Valoresseguro valoresseguro){
		valoresseguro.setSituacao("Inativo");
		ValorSeguroFacade valorSeguroFacade =new ValorSeguroFacade();
		valoresseguro = valorSeguroFacade.salvar(valoresseguro);
		carregarValores();
	}
	
	
	public String novo(){
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadValoresSeguro", options, null);
    	return "";
    }
	
	public void retornoDialog(){
		carregarValores();
	}
	
	public void pesquisar() {
		String sql = "select v from Valoresseguro v where v.descricao like '%" + periodo + "%' ";
		if(!status.equalsIgnoreCase("Todos")){
			sql = sql + " and v.situacao='"+status+"'";
		}
		if(!tipo.equalsIgnoreCase("Todos")){
			sql = sql + " and v.tiposeguro='"+tipo+"'";
		}
		ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
		listaValores = valorSeguroFacade.listar(sql);
	}
	
	public void limpar(){
		tipo="Todos";
		status="Todos";
		periodo="";
		carregarValores();
	}
	
	public String editarValores(Valoresseguro valor) {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("valor", valor);
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadValoresSeguro", options, null);
    	return "";
	}
}
