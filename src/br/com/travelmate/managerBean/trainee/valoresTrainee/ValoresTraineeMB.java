package br.com.travelmate.managerBean.trainee.valoresTrainee;

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

import br.com.travelmate.facade.ValoresTraineeFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Valorestrainee;

@Named
@ViewScoped
public class ValoresTraineeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Valorestrainee> listaValores;
	private String periodo="";
	private String status;
	private String tipo;
	
	@PostConstruct()
	public void init() {
		carregarValores();
	}
	
	public List<Valorestrainee> getListaValores() {
		return listaValores;
	}
	public void setListaValores(List<Valorestrainee> listaValores) {
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
		String sql = "select v from Valorestrainee v where v.situacao='Ativo'";
		ValoresTraineeFacade valoresTraineeFacade = new ValoresTraineeFacade();
		listaValores = valoresTraineeFacade.listar(sql);
	}
	
	public void desativar(Valorestrainee valorestrainee){
		if (valorestrainee.getSituacao().equalsIgnoreCase("Ativo")){
			valorestrainee.setSituacao("Inativo");
			ValoresTraineeFacade valoresTraineeFacade =new ValoresTraineeFacade();
			valorestrainee = valoresTraineeFacade.salvar(valorestrainee);
			carregarValores();
		}
	}
	
	
	public String novo(){
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadValoresTrainee", options, null);
    	return "";
    }
	
	public void retornoDialog(){
		carregarValores();
	}

	public String produtosTrainee(Valorestrainee valorestrainee){
		if(valorestrainee.getTipotrainee().equalsIgnoreCase("Australia")){
			FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("valorestrainee", valorestrainee);
			Map<String,Object> options = new HashMap<String, Object>();
	        options.put("contentWidth", 600);
	        RequestContext.getCurrentInstance().openDialog("produtosTrainee", options, null);
		}
		return "";
	}
	
	
	public void pesquisar() {
		String sql = "select v from Valorestrainee v where v.descricao like '%" + periodo + "%' ";
		if(!status.equalsIgnoreCase("Todos")){
			sql = sql + " and v.situacao='"+status+"'";
		}
		if(!tipo.equalsIgnoreCase("Todos")){
			sql = sql + " and v.tipotrainee='"+tipo+"'";
		}
		ValoresTraineeFacade valoresTraineeFacade = new ValoresTraineeFacade();
		listaValores = valoresTraineeFacade.listar(sql);
	}
	
	public void limpar(){
		tipo="Todos";
		status="Todos";
		periodo="";
		carregarValores();
	}
	
	public String editarValores(Valorestrainee valor) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("valor", valor);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadValoresTrainee", options, null);
		return "";
	}
	
	public String bolaStatus(String situacao){
		 if (situacao.equalsIgnoreCase("Ativo")){
			 return "../../resources/img/bolaVerde.png";
		 }else return "../../resources/img/bolaVermelha.png";
	 }
}
