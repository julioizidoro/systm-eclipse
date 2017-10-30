package br.com.travelmate.managerBean.departamento;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ConsDepartamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private List<Departamento> listaDepartamento;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	
	
	@PostConstruct
	public void init(){
		gerarListaDepartamentos();
	}



	public Departamento getDepartamento() {
		return departamento;
	}



	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}



	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}



	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}
	
	
	
	
	public void cadastrarNovoDepartamento(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
	    options.put("closable", false);
	    RequestContext.getCurrentInstance().openDialog("cadDepartamento", options, null);
	}
	
	
	public void editarDepartamento(Departamento departamento){
		if (departamento != null) {
			Map<String, Object> options = new HashMap<String, Object>();
		    options.put("closable", false);
		    FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        session.setAttribute("departamento", departamento);
		    RequestContext.getCurrentInstance().openDialog("cadDepartamento", options, null);
		}
	}
	
	
	public void retornoDialogNovo(SelectEvent event){
		Departamento departamento = (Departamento) event.getObject();
		if (departamento.getIddepartamento() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			if (listaDepartamento != null) {
				listaDepartamento.add(departamento);
			}
		}
	}
	
	
	public void retornoDialogEdicao(SelectEvent event){
		Departamento departamento = (Departamento) event.getObject();
		if (departamento.getIddepartamento() != null) {
			Mensagem.lancarMensagemInfo("Atualizado com sucesso", "");
			gerarListaDepartamentos();
		}
	}
	
	
	public void gerarListaDepartamentos(){
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		String sql = "Select d From Departamento d";
		//if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
		//	sql = sql + " and d.";
		//}
		listaDepartamento = departamentoFacade.listar(sql);
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<>();
		}
	}
	
	
	public String visualizarFuncaoDepartamento(Departamento departamento){
		int iddepartamento = usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento();
		if(iddepartamento==1 || iddepartamento==departamento.getIddepartamento()) {
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
		    options.put("closable", false);
		    session.setAttribute("departamento", departamento);
		    return "consFuncao";
		}else {
			Mensagem.lancarMensagemInfo("Atenção!", "Acesso negado.");
			return "";
		}
	}

}
