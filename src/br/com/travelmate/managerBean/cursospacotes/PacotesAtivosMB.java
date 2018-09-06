package br.com.travelmate.managerBean.cursospacotes;
 
import br.com.travelmate.facade.CursosPacotesFacade;
import br.com.travelmate.facade.PacoteInicialFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cursospacote; 
import br.com.travelmate.model.Pacotesinicial;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

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

@Named
@ViewScoped
public class PacotesAtivosMB implements Serializable {
 
	private static final long serialVersionUID = 1L;  
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Pacotesinicial> listaCursosPacotes; 
	private List<Pacotesinicial> listaTrabalhoPacotes; 
	private List<Pacotesinicial> listaTeensPacotes;  
	private List<Pacotesinicial> listaTurismoPacotes;  
	private List<Pacotesinicial> listaPacotesEspeciais;  
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
	private String moedaNacional;

	@PostConstruct
	public void init() {
		gerarListaPais();
		listarPacotesEspecial();
		listarCursosPacotes();
		getAplicacaoMB(); 
		moedaNacional =usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
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

	public List<Pacotesinicial> getListaTurismoPacotes() {
		return listaTurismoPacotes;
	}

	public void setListaTurismoPacotes(List<Pacotesinicial> listaTurismoPacotes) {
		this.listaTurismoPacotes = listaTurismoPacotes;
	}

	public boolean isTurismo() {
		return turismo;
	}

	public void setTurismo(boolean turismo) {
		this.turismo = turismo;
	}

	public List<Pacotesinicial> getListaPacotesEspeciais() {
		return listaPacotesEspeciais;
	}

	public void setListaPacotesEspeciais(List<Pacotesinicial> listaPacotesEspeciais) {
		this.listaPacotesEspeciais = listaPacotesEspeciais;
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

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void consultarListarCursosPacotes(Pais pais){ 
		String sql = "SELECT c FROM Pacotesinicial c WHERE c.especial=FALSE and c.idpais="+ pais.getIdpais() +" ORDER BY c.idproduto, c.pais";
		PacoteInicialFacade pacoteInicialFacade = new PacoteInicialFacade();
		List<Pacotesinicial> lista = pacoteInicialFacade.listar(sql);  
		listaTrabalhoPacotes = new ArrayList<Pacotesinicial>();
		listaCursosPacotes =  new ArrayList<Pacotesinicial>();
		listaTeensPacotes =  new ArrayList<Pacotesinicial>(); 
		listaTurismoPacotes =  new ArrayList<Pacotesinicial>(); 
		if(lista!=null && lista.size()>0) {
			for (int i = 0; i < lista.size(); i++) {
				int idproduto = lista.get(i).getIdproduto();
				if(idproduto == aplicacaoMB.getParametrosprodutos().getCursos()) {
					lista.get(i).setCursos(true);
					listaCursosPacotes.add(lista.get(i));
					curso = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
					lista.get(i).setHighschool(true);
					listaTeensPacotes.add(lista.get(i));
					teens = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
					lista.get(i).setHighschool(true);
					listaTeensPacotes.add(lista.get(i));
					teens = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getAupair()) {
					lista.get(i).setAupair(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
					lista.get(i).setVoluntariado(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getWork()) {
					lista.get(i).setWorktravel(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getPacotes()) {
					lista.get(i).setTurismo(true);
					listaTurismoPacotes.add(lista.get(i));
					turismo = true;  
				}
			}
			habilitarPacotes = true;
			habilitarPais = false;
		}else{
			Mensagem.lancarMensagemInfo("Sem Pacotes disponiveis", "");
		}
	}  
	
	public void listarCursosPacotes(){ 
		String sql = "SELECT c FROM Pacotesinicial c WHERE c.especial=FALSE and c.paisunidade="+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getIdpais()+ " ORDER BY c.idproduto, c.pais";
		PacoteInicialFacade pacoteInicialFacade = new PacoteInicialFacade();
		List<Pacotesinicial> lista = pacoteInicialFacade.listar(sql);  
		listaTrabalhoPacotes = new ArrayList<Pacotesinicial>();
		listaCursosPacotes =  new ArrayList<Pacotesinicial>();
		listaTeensPacotes =  new ArrayList<Pacotesinicial>(); 
		listaTurismoPacotes =  new ArrayList<Pacotesinicial>(); 
		if(lista!=null && lista.size()>0) {
			for (int i = 0; i < lista.size(); i++) {
				int idproduto = lista.get(i).getIdproduto();
				if(idproduto == aplicacaoMB.getParametrosprodutos().getCursos()) {
					lista.get(i).setCursos(true);
					listaCursosPacotes.add(lista.get(i));
					curso = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
					lista.get(i).setHighschool(true);
					listaTeensPacotes.add(lista.get(i));
					teens = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
					lista.get(i).setHighschool(true);
					listaTeensPacotes.add(lista.get(i));
					teens = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getAupair()) {
					lista.get(i).setAupair(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
					lista.get(i).setVoluntariado(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getWork()) {
					lista.get(i).setWorktravel(true);
					listaTrabalhoPacotes.add(lista.get(i));
					trabalho = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getPacotes()) {
					lista.get(i).setTurismo(true);
					listaTurismoPacotes.add(lista.get(i));
					turismo = true;  
				}
			}
			habilitarPacotes = true;
			habilitarPais = false;
		}else{
			Mensagem.lancarMensagemInfo("Sem Pacotes disponiveis", "");
		}
	}  
	public void listarPacotesEspecial(){ 
		String sql = "SELECT c FROM Pacotesinicial c WHERE c.especial=TRUE ORDER BY c.idproduto, c.pais";
		PacoteInicialFacade pacoteInicialFacade = new PacoteInicialFacade();
		List<Pacotesinicial> lista = pacoteInicialFacade.listar(sql);  
		listaPacotesEspeciais = new ArrayList<Pacotesinicial>(); 
		if(lista!=null && lista.size()>0) {
			for (int i = 0; i < lista.size(); i++) {
				int idproduto = lista.get(i).getIdproduto();
				if(idproduto == aplicacaoMB.getParametrosprodutos().getCursos()) {
					lista.get(i).setCursos(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
					lista.get(i).setHighschool(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
					lista.get(i).setHighschool(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getAupair()) {
					lista.get(i).setAupair(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
					lista.get(i).setVoluntariado(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getWork()) {
					lista.get(i).setWorktravel(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;
				}else if(idproduto == aplicacaoMB.getParametrosprodutos().getPacotes()) {
					lista.get(i).setTurismo(true);
					listaPacotesEspeciais.add(lista.get(i));
					especial = true;  
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
	
	public float calcularValorCambioAtual(Pacotesinicial pacote) {
		float valorInicial = pacote.getValoravista();
		if (pacote.getValorcambio()==0) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), pacote.getMoeda());
			if (cambio!=null) {
				float valor = pacote.getValormoedaestrangeira() * cambio.getValor();
				valorInicial = valor;
			}
			
		}
		return valorInicial;
	}
	
	
}
