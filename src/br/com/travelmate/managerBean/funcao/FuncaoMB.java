package br.com.travelmate.managerBean.funcao;

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
import br.com.travelmate.facade.FuncaoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Funcao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class FuncaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Funcao funcao;
	private Departamento departamento;
	private List<Funcao> listaFuncao;
	private List<Departamento> listaDepartamento;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarDepartamento = false;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			departamento = (Departamento) session.getAttribute("departamento");
			session.removeAttribute("departamento");
			gerarListaDepartamento();
			pesquisar();
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
				habilitarDepartamento = true;
			}
		}
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Funcao> getListaFuncao() {
		return listaFuncao;
	}

	public void setListaFuncao(List<Funcao> listaFuncao) {
		this.listaFuncao = listaFuncao;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public boolean isHabilitarDepartamento() {
		return habilitarDepartamento;
	}

	public void setHabilitarDepartamento(boolean habilitarDepartamento) {
		this.habilitarDepartamento = habilitarDepartamento;
	}

	public String cadastroFuncao() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		options.put("closable", false);
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("departamento", departamento);
		RequestContext.getCurrentInstance().openDialog("cadFuncao", options, null);
		return "";
	}

	public String editarFuncao(Funcao funcao) {
		if (funcao != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			session.setAttribute("funcao", funcao);
			options.put("contentWidth", 600);
			options.put("closable", false);
			RequestContext.getCurrentInstance().openDialog("cadFuncao", options, null);
		}
		return "";
	}

	public void retornoDialogNovo(SelectEvent event) {
		Funcao funcao = (Funcao) event.getObject();
		if (funcao.getIdfuncao() != null) {
			Mensagem.lancarMensagemInfo("Salvo co msucesso", "");
			if (listaFuncao != null) {
				listaFuncao.add(funcao);
			}
		}
	}

	public void retornoDialogEdicao(SelectEvent event) {
		Funcao funcao = (Funcao) event.getObject();
		if (funcao.getIdfuncao() != null) {
			Mensagem.lancarMensagemInfo("Atualizado com sucesso", "");
		}
	}

	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("Select d From Departamento d where d.lista=true");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<>();
		}
	}

	public void pesquisar() {
		FuncaoFacade funcaoFacade = new FuncaoFacade();
		String sql = "";
		if (departamento == null) {
			listaFuncao = new ArrayList<>();
		} else {
			sql = "Select f From Funcao f Where f.usuario.departamento.iddepartamento="
					+ departamento.getIddepartamento();
			listaFuncao = funcaoFacade.listar(sql);
			if (listaFuncao == null) {
				listaFuncao = new ArrayList<>();
			}
		}
	}

	public String visualizarFuncao(Funcao funcao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("funcao", funcao);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("verrFuncao", options, null);
		return "";
	}

	public String voltar() {
		return "consDepartamento";
	}

}
