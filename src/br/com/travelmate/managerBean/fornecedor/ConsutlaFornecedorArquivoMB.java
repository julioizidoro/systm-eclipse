package br.com.travelmate.managerBean.fornecedor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;



import br.com.travelmate.facade.FornecedorArquivoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedorarquivo;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ConsutlaFornecedorArquivoMB implements Serializable {
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Fornecedorarquivo> listaArquivo;
	private Fornecedorcidade fornecedorcidade;
	
	
	@PostConstruct
	public void init(){
		 FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade");
         session.removeAttribute("fornecedorcidade");
         String sql = "SELECT a FROM Fornecedorarquivo a where a.fornecedorcidade=" + fornecedorcidade.getIdfornecedorcidade();
         if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=2){
        	 sql = sql + " and a.datavalidade>='" + Formatacao.ConvercaoDataSql(new Date()) + "' ";
         }
         sql = sql + " order by a.nomearquivo";
         FornecedorArquivoFacade fornecedorArquivoFacade = new FornecedorArquivoFacade();
         listaArquivo = fornecedorArquivoFacade.listar(sql);
         if (listaArquivo==null){
        	 listaArquivo = new ArrayList<Fornecedorarquivo>();
         }
	}

	public List<Fornecedorarquivo> getListaArquivo() {
		return listaArquivo;
	}

	public void setListaArquivo(List<Fornecedorarquivo> listaArquivo) {
		this.listaArquivo = listaArquivo;
	}
	
}
