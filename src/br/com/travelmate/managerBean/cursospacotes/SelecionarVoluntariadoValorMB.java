package br.com.travelmate.managerBean.cursospacotes;
   
import br.com.travelmate.facade.VoluntariadoProjetoValorFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;  
import br.com.travelmate.model.Voluntariadopacote;
import br.com.travelmate.model.Voluntariadoprojetovalor;
import br.com.travelmate.util.Formatacao; 

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class SelecionarVoluntariadoValorMB implements Serializable {
 
	private static final long serialVersionUID = 1L; 
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Voluntariadopacote voluntariadopacote;   
	private List<Voluntariadoprojetovalor> listaProdutos; 
	private String nome;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) { 
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
	        voluntariadopacote = (Voluntariadopacote) session.getAttribute("voluntariadopacote");  
	        session.removeAttribute("voluntariadopacote");  
	        gerarListaProdutos();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}   
	
	public Voluntariadopacote getVoluntariadopacote() {
		return voluntariadopacote;
	}

	public void setVoluntariadopacote(Voluntariadopacote voluntariadopacote) {
		this.voluntariadopacote = voluntariadopacote;
	}

	public List<Voluntariadoprojetovalor> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Voluntariadoprojetovalor> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String cancelar() { 
		RequestContext.getCurrentInstance().closeDialog(null); 
		return "";
	} 
	
	public String selecionar(Voluntariadoprojetovalor voluntariadoprojetovalor){ 
		voluntariadopacote.setVoluntariadoprojetovalor(voluntariadoprojetovalor);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("voluntariadopacote", voluntariadopacote);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}          
	
	public void gerarListaProdutos(){
		VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
		String sql = "Select v From Voluntariadoprojetovalor v Where v.voluntariadoprojeto.fornecedorcidade.idfornecedorcidade="
					+voluntariadopacote.getCursospacote().getFornecedorcidadeidioma().getFornecedorcidade().getIdfornecedorcidade()
					+" and v.datafinal>='"+Formatacao.ConvercaoDataSql(new Date()) +"'"; 
		if(nome!=null && nome.length()>0){
			sql = sql + " and v.voluntariadoprojeto.descricao like '%"+nome+"%'";
		}
		sql = sql + " order by v.voluntariadoprojeto.descricao";
		listaProdutos = voluntariadoProjetoValorFacade.listar(sql);
	}
}
