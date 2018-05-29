package br.com.travelmate.managerBean.funcao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FuncaoFacade;
import br.com.travelmate.facade.Pasta1Facade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Funcao;
import br.com.travelmate.model.Usuario;

@Named
@ViewScoped
public class DepartamentoFuncaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Departamento> listaDepartamentos;
	
	
	@PostConstruct
	public void init(){
		gerarListaDepartamento();
	}
	   
	
	
	
	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}




	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}




	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamentos = departamentoFacade.listar("Select d from Departamento d where d.lista=true order by d.nome");
		if (listaDepartamentos == null) {
			listaDepartamentos = new ArrayList<Departamento>();
		}
	}
	
	
	public String visualizarFuncao(Departamento departamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "visualizarFuncao";
	}
	
	
	public Integer gerarTotalUsuario(Departamento departamento) {
		FuncaoFacade funcaoFacade = new FuncaoFacade();
		Integer numeroTotal = 0;
		String sql = "Select u from Funcao u where u.usuario.departamento.iddepartamento=" + departamento.getIddepartamento() + " and u.usuario.situacao='Ativo'";
		List<Funcao> listaUsuario = funcaoFacade.listar(sql);
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<>();
			numeroTotal = listaUsuario.size();
			return numeroTotal;
		} else {
			numeroTotal = listaUsuario.size();
			return numeroTotal;
		}
	}

}
