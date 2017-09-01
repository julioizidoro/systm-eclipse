package br.com.travelmate.managerBean.cursospacotes;

import br.com.travelmate.facade.CursosPacotesFacade;  
import br.com.travelmate.facade.PacoteInicialFacade; 
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Cursospacote; 
import br.com.travelmate.model.Pacotesinicial; 
import br.com.travelmate.util.Formatacao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext; 

@Named
@ViewScoped
public class PacotesAtivosMB implements Serializable {
 
	private static final long serialVersionUID = 1L; 
	private List<Pacotesinicial> listaCursosPacotes; 
	private List<Pacotesinicial> listaTrabalhoPacotes; 
	private List<Pacotesinicial> listaTeensPacotes;  
	private boolean curso;
	private boolean teens;
	private boolean trabalho; 
	@Inject
	private AplicacaoMB aplicacaoMB; 

	@PostConstruct
	public void init() {
		listarCursosPacotes(); 
		getAplicacaoMB(); 
	} 
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	public List<Pacotesinicial> getListaCursosPacotes() {
		return listaCursosPacotes;
	}

	public void setListaCursosPacotes(List<Pacotesinicial> listaCursosPacotes) {
		this.listaCursosPacotes = listaCursosPacotes;
	} 

	public List<Pacotesinicial> getListaTrabalhoPacotes() {
		return listaTrabalhoPacotes;
	}

	public void setListaTrabalhoPacotes(List<Pacotesinicial> listaTrabalhoPacotes) {
		this.listaTrabalhoPacotes = listaTrabalhoPacotes;
	}

	public List<Pacotesinicial> getListaTeensPacotes() {
		return listaTeensPacotes;
	}

	public void setListaTeensPacotes(List<Pacotesinicial> listaTeensPacotes) {
		this.listaTeensPacotes = listaTeensPacotes;
	}

	public boolean isTeens() {
		return teens;
	}

	public void setTeens(boolean teens) {
		this.teens = teens;
	}

	public boolean isTrabalho() {
		return trabalho;
	}

	public void setTrabalho(boolean trabalho) {
		this.trabalho = trabalho;
	}

	public boolean isCurso() {
		return curso;
	}

	public void setCurso(boolean curso) {
		this.curso = curso;
	} 

	public void listarCursosPacotes(){ 
		PacoteInicialFacade pacoteInicialFacade = new PacoteInicialFacade();
		String sql = "SELECT c FROM Pacotesinicial c ORDER BY c.idproduto";
		List<Pacotesinicial> lista = pacoteInicialFacade.listar(sql);  
		listaTrabalhoPacotes = new ArrayList<>();
		listaCursosPacotes =  new ArrayList<>();
		listaTeensPacotes =  new ArrayList<>(); 
		if(lista!=null && lista.size()>0) {
			for (int i = 0; i < lista.size(); i++) {
				int idproduto = lista.get(i).getIdproduto();
				if(idproduto == aplicacaoMB.getParametrosprodutos().getCursos()) {
					lista.get(i).setCursos(true);
					listaCursosPacotes.add(lista.get(i));
					curso = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getAupair()) {
					lista.get(i).setAupair(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
					lista.get(i).setHighschool(true);
					listaTeensPacotes.add(lista.get(i));
					teens = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getWork()) {
					lista.get(i).setWorktravel(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}
			}
		}
	}  
	
	public String retornarImagemPais(Pacotesinicial pacotesinicial){
		return aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/bandeirapais/"
				+pacotesinicial.getIdpais()+".png"; 
	}
	
	public String retornarImagemEscola(Pacotesinicial pacotesinicial){ 
		return aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/logoescola/"
				+pacotesinicial.getLogo(); 
	}
	
	public String acomodacaoInclusa(Pacotesinicial pacotesinicial){ 
		if(pacotesinicial.isCursos()) {
			if(pacotesinicial.getNumerosemanaacomodacao()!=null && pacotesinicial.getNumerosemanaacomodacao()>0){
				return "Incluída "+Formatacao.formatarFloatStringNumero(pacotesinicial.getNumerosemanaacomodacao())+" semana(s)";
			}else return "Não incluso";  
		}else {
			if(pacotesinicial.getNumerosemanaacomodacao()!=null && pacotesinicial.getNumerosemanaacomodacao()>0){
				return "Incluída "+Formatacao.formatarFloatStringNumero(pacotesinicial.getNumerosemanaacomodacao())+" semana(s)";
			}else return "Não incluso"; 
		}
	}
	
	public String saibaMais(int idpacote){  
		CursosPacotesFacade cursosPacotesFacade = new CursosPacotesFacade();
		Cursospacote cursospacote = cursosPacotesFacade.consultar
				("SELECT c FROM Cursospacote c WHERE c.idcursospacote="+idpacote); 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cursospacote", cursospacote);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("informacoesCursosPacote", options, null);
		return ""; 
	}
	
	public String gerarOrçamento(int idpacote){  
		CursosPacotesFacade cursosPacotesFacade = new CursosPacotesFacade();
		Cursospacote cursospacote = cursosPacotesFacade.consultar
				("SELECT c FROM Cursospacote c WHERE c.idcursospacote="+idpacote);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("cursospacote", cursospacote);    
		return "gerarOrcamentoPacote";
	}  
	
	public String retornoDialog(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String voltar = (String) session.getAttribute("voltar");
        session.removeAttribute("voltar");
        if (voltar!=null){
        	if (voltar.equalsIgnoreCase("orcamento")){
        		try {
					fc.getExternalContext().redirect("/inicio/pages/orcamento/orcamentoCurso.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return "";  
	} 
	
	public String retornarDataInicio(Pacotesinicial pacotesinicial){
		String retorno = Formatacao.ConvercaoDataPadrao(pacotesinicial.getDatainiciocurso())
				+" até "+Formatacao.ConvercaoDataPadrao(pacotesinicial.getDataterminocurso());
		return retorno;
	}
	
	public String retornarDataInicioHS(Pacotesinicial pacotesinicial){
		int mes = Formatacao.getMesData(pacotesinicial.getDatainiciocurso());
		int ano = Formatacao.getAnoData(pacotesinicial.getDatainiciocurso());
		String retorno = Formatacao.nomeMes(mes+1)+"/"+ano;
		return retorno;
	}
	
	public String retornarDataInicioAupair(Pacotesinicial pacotesinicial){
		int ano = Formatacao.getAnoData(pacotesinicial.getDatainiciocurso());
		String retorno = ""+ano;
		return retorno;
	}
	
	public String visualizarInformativo(int idpacote) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/informativospacotes/"
					+idpacote+".pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
