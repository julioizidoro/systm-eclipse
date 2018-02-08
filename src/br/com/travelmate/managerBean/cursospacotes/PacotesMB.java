package br.com.travelmate.managerBean.cursospacotes;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.travelmate.facade.CursosPacotesFacade;
import br.com.travelmate.facade.OCursoFacade;
import br.com.travelmate.facade.PacoteInicialFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Pacotesinicial;
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class PacotesMB implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	private List<Ocurso> listaOCursos; 
	private boolean curso;
	private boolean teens;
	private boolean trabalho;
	private boolean turismo;
	private boolean especial;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private boolean habilitarPais = true;
	private boolean habilitarPacotes = false;
	private List<Pais> listaPais;
	private Pais paisLogo;

	@PostConstruct
	public void init() {
		gerarListaPais();
		//listarPacotesEspecial();
		//listarCursosPacotes();
		getAplicacaoMB(); 
	} 
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	

	public List<Ocurso> getListaOCursos() {
		return listaOCursos;
	}

	public void setListaOCursos(List<Ocurso> listaOCursos) {
		this.listaOCursos = listaOCursos;
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

	

	public boolean isTurismo() {
		return turismo;
	}

	public void setTurismo(boolean turismo) {
		this.turismo = turismo;
	}



	public boolean isEspecial() {
		return especial;
	}

	public void setEspecial(boolean especial) {
		this.especial = especial;
	}

	public boolean isHabilitarPais() {
		return habilitarPais;
	}

	public void setHabilitarPais(boolean habilitarPais) {
		this.habilitarPais = habilitarPais;
	}

	public boolean isHabilitarPacotes() {
		return habilitarPacotes;
	}

	public void setHabilitarPacotes(boolean habilitarPacotes) {
		this.habilitarPacotes = habilitarPacotes;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}  

	public Pais getPaisLogo() {
		return paisLogo;
	}

	public void setPaisLogo(Pais paisLogo) {
		this.paisLogo = paisLogo;
	}

	public void consultarListarCursosPacotes(Pais pais){ 
		String sql = "SELECT c FROM Ocurso c WHERE c.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="+ pais.getIdpais() +
				" and c.modelo=true ORDER BY  c.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais";
		OCursoFacade oCursoFacade = new OCursoFacade();
		listaOCursos = oCursoFacade.listar(sql);  
		if(listaOCursos==null) {
			listaOCursos = new ArrayList<Ocurso>();
			habilitarPacotes = false;
			habilitarPais = true;
			curso = false;
			paisLogo = null;
			Mensagem.lancarMensagemInfo("Sem Pacotes disponiveis", "");
		}else{
			habilitarPacotes = true;
			habilitarPais = false;
			curso = true;
			paisLogo = pais;
		}   
	}  
	
	   
	
	public String retornarImagemPais(Ocurso ocurso){
		return aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/bandeirapais/"
				+ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais()+".png"; 
	}
	
	public String retornarImagemEscola(Ocurso ocurso){ 
		return aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/logoescola/"
				+ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getLogo(); 
	}
	
	public String acomodacaoInclusa(Pacotesinicial pacotesinicial){ 
		if(pacotesinicial.isCursos()) {
			if(pacotesinicial.getNumerosemanaacomodacao()!=null && pacotesinicial.getNumerosemanaacomodacao()>0){
				return "Incluída "+Formatacao.formatarFloatStringNumero(pacotesinicial.getNumerosemanaacomodacao())+" semana(s)";
			}else return "Não incluso";  
		}else if(pacotesinicial.isTurismo()) {
			if(pacotesinicial.getDescritivoacomodacao()!=null && pacotesinicial.getDescritivoacomodacao().length()>0){
				return pacotesinicial.getDescritivoacomodacao();
			}else return "Não incluso";  
		}else if(pacotesinicial.isVoluntariado()) {
			if(pacotesinicial.getDescritivoacomodacao()!=null && pacotesinicial.getDescritivoacomodacao().length()>0){
				return "Incluída "+Formatacao.formatarFloatStringNumero(pacotesinicial.getNumerosemanaacomodacao())+" semana(s)";
			}else return "Não incluso";  
		}else {
			if(pacotesinicial.getNumerosemanaacomodacao()!=null && pacotesinicial.getNumerosemanaacomodacao()>0){
				return "Incluída "+Formatacao.formatarFloatStringNumero(pacotesinicial.getNumerosemanaacomodacao())+" semana(s)";
			}else return "Não incluso"; 
		}
	}
	
	public String saibaMais(Ocurso ocurso){  
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("ocurso", ocurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("informacoesVitrine", options, null);
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
	
	public String retornarDataInicio(Ocurso ocurso){
		String retorno = Formatacao.ConvercaoDataPadrao(ocurso.getDatainicio())
				+" até "+Formatacao.ConvercaoDataPadrao(ocurso.getDatatermino());
		return retorno;
	}
	
	public String retornarDataTurismo(Pacotesinicial pacotesinicial){
		String retorno = Formatacao.ConvercaoDataPadrao(pacotesinicial.getDatainiciocurso())
				+" a "+Formatacao.ConvercaoDataPadrao(pacotesinicial.getDataterminocurso());
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
	
	
	public void gerarListaPais(){
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listarModelo("SELECT p FROM Pais p WHERE p.modelo>0");
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}
	
	public String retornarIPais(Pais pais){
		return aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/bandeirapais/"
				+pais.getIdpais()+".png"; 
	}
	
	
	public void voltarPais(){
		habilitarPacotes = false;
		habilitarPais = true;
	}  
	
	public String ImgPais(){
		return aplicacaoMB.getParametrosprodutos().getCaminhoimagens()+"/bandeirapais/"
				+paisLogo.getIdpais()+".png"; 
	}

}
