package br.com.travelmate.managerBean.highschool.valoresHighSchool;

import java.io.Serializable;
import java.util.Date;
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

import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ValoresHighSchoolFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais; 
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class valoresHighSchoolMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Valoreshighschool> listaValores;
	private String periodo="";
	private String status;
	private String inicio="";
	private Fornecedorcidade fornecedorcidade;
    private List<Pais> listaPais;
    private Cidade cidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private Pais pais;
    private String sql;
    private String bolaStatus;
	
	@PostConstruct()
	public void init() {
		PaisFacade paisFacade = new PaisFacade();
        listaPais = paisFacade.listar();
		sql = "select v from Valoreshighschool v where (v.situacao='Ativo' or v.situacao='Temporário') and v.datavalidade>='"
				+Formatacao.ConvercaoDataSql(new Date())+"'";
		carregarValores();
	}
	
	public List<Valoreshighschool> getListaValores() {
		return listaValores;
	}
	public void setListaValores(List<Valoreshighschool> listaValores) {
		this.listaValores = listaValores;
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

	
	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	

	public String getBolaStatus() {
		return bolaStatus;
	}

	public void setBolaStatus(String bolaStatus) {
		this.bolaStatus = bolaStatus;
	}

	public void carregarValores() {
		ValoresHighSchoolFacade ValoresHighSchoolFacade = new ValoresHighSchoolFacade();
		listaValores = ValoresHighSchoolFacade.listar(sql);
		for (int i = 0; i < listaValores.size(); i++) {
			if (!listaValores.get(i).getAnoinicio().equalsIgnoreCase("0000") && listaValores.get(i).getAnoinicio().length() ==4) {
				listaValores.get(i).setInicioAnoInicio(listaValores.get(i).getInicio() + " - "  + listaValores.get(i).getAnoinicio());
			}
		}
	}
	
	public void mudarSituacao(Valoreshighschool valores){ 
		ValoresHighSchoolFacade ValoresHighSchoolFacade =new ValoresHighSchoolFacade();
		valores = ValoresHighSchoolFacade.salvar(valores); 
	} 
	
	public String novo(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("sql", sql);
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadValoresHigh", options, null);
    	return "";
    }
	
	public void retornoDialog(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        if ((sql==null) || (sql.length()<=10)){
        	limpar();
        }
		carregarValores();
	}
	
	
	public void pesquisar() {
		sql = "select v from Valoreshighschool v where v.duracao like '%" + periodo + "%' and v.inicio like '%" + inicio + "%' ";
		if(!status.equalsIgnoreCase("Todos")){
			sql = sql + " and v.situacao='"+status+"'";
		}
		if ((fornecedorcidade!=null) && (fornecedorcidade.getIdfornecedorcidade()!=null)){
			sql = sql + " and v.fornecedorcidade.idfornecedorcidade='"+fornecedorcidade.getIdfornecedorcidade()+"'";
		}else{
			if (cidade!=null){
				sql = sql + " and v.fornecedorcidade.cidade.idcidade='"+cidade.getIdcidade()+"'";
			}else if(pais!=null){
				sql = sql + " and v.fornecedorcidade.cidade.pais.idpais='"+pais.getIdpais()+"'";
			}
		}
		carregarValores();
	}
	
	public void limpar(){
		pais=null;
		cidade=null;
		fornecedorcidade=null;
		inicio="";
		status="Todos";
		periodo="";
		sql = "select v from Valoreshighschool v where (v.situacao='Ativo' or v.situacao='Temporário')";
		carregarValores();
	}
	
	
	 public void listarFornecedorCidade(){
	        int idProduto=idProduto = aplicacaoMB.getParametrosprodutos().getHighSchool();
	        if ((idProduto>0) && (cidade!=null)){
	            listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
	        }
	    }
	 
	 public String bolaStatus(String situacao){
		 if (situacao.equalsIgnoreCase("Ativo")){
			 return "../../resources/img/bolaVerde.png";
		 }if (situacao.equalsIgnoreCase("Temporário")){
			 return "../../resources/img/bolaAmarela.png";
		 }else return "../../resources/img/bolaVermelha.png";
	 }
	 
	 public String editarValores(Valoreshighschool valor) {
		 FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        session.setAttribute("sql", sql);
	        session.setAttribute("valor", valor);
			Map<String,Object> options = new HashMap<String, Object>();
	        options.put("contentWidth", 600);
	        RequestContext.getCurrentInstance().openDialog("cadValoresHigh", options, null);
	    	return "";
	 }
	 
	    
}
