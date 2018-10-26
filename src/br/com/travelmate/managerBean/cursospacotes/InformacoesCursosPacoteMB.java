package br.com.travelmate.managerBean.cursospacotes;
  
import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.util.Formatacao; 

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class InformacoesCursosPacoteMB implements Serializable {
	
	@Inject
	private CambioDao cambioDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private static final long serialVersionUID = 1L;  
	private Cursospacote cursospacote;    
	private Float valorPacote = 0f;

	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
        cursospacote = (Cursospacote) session.getAttribute("cursospacote");
        session.removeAttribute("cursospacote");  
        int idproduto = cursospacote.getProdutos().getIdprodutos();
		if(idproduto == aplicacaoMB.getParametrosprodutos().getCursos()) {
			cursospacote.setCursos(true);
		}else if(idproduto == aplicacaoMB.getParametrosprodutos().getAupair()) {
			cursospacote.setAupair(true);
		}else if(idproduto == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
			cursospacote.setHighschool(true);
		}else if(idproduto == aplicacaoMB.getParametrosprodutos().getWork()) {
			cursospacote.setWorktravel(true);
		}else if(idproduto == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
			cursospacote.setVoluntariado(true);
		}
		valorPacote = calcularValorCambioAtual();
	}

	 
	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}
 
	public Float getValorPacote() {
		return valorPacote;
	}


	public void setValorPacote(Float valorPacote) {
		this.valorPacote = valorPacote;
	}


	public String cancelar() { 
		RequestContext.getCurrentInstance().closeDialog(null); 
		return "";
	} 
	
	public String gerarOrçamento(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("cursospacote", cursospacote);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("gerarOrcamentoPacote", options, null);
		return "";
	}   
	
	public String acomodacaoInclusa(){ 
		if(cursospacote.getValorcoprodutos_acomodacao()!=null){
			return "Incluída "+Formatacao.formatarFloatStringNumero(cursospacote.getNumerosemanaacomodacao())+" semana(s)";
		}else return "Não incluso";  
	}
	 
	
	public String retornarCargaHoraria(){ 
		return cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso().getCargahoraria()
				+" "+cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso().getTipocargahoraria();  
	}  
	
	public String retornarCambio(){  
		if(cursospacote.getValorcambio()>0){
			return cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas().getSigla()
					+" "+Formatacao.formatarFloatString(cursospacote.getValorcambio());  
		}else{
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), 
					cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas(),
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			return cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas().getSigla()
					+" "+Formatacao.formatarFloatString(cambio.getValor())
					+" referente o dia "+Formatacao.ConvercaoDataPadrao(new Date())+".";  
		} 
	}  
	
	
	public String retornarMesAnoInicio(){
		int mes = Formatacao.getMesData(cursospacote.getDatainiciocurso());
		int ano = Formatacao.getAnoData(cursospacote.getDatainiciocurso());
		String retorno = Formatacao.nomeMes(mes+1)+"/"+ano;
		return retorno;
	}
	
	public String retornarAnoInicio(){
		int ano = Formatacao.getAnoData(cursospacote.getDatainiciocurso());
		String retorno = ""+ano;
		return retorno;
	}
	
	public Float calcularValorCambioAtual() {
		Float valorInicial = cursospacote.getValoravista();
		if (cursospacote.getValorcambio()==0) {
			Cambio cambio = cambioDao.consultarCambioMoedaPais(Formatacao.ConvercaoDataSql(Formatacao.ConvercaoStringData(aplicacaoMB.retornarDataCambio())),
					 usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getIdmoedas(), 
						usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			if (cambio!=null) {
				valorInicial = cursospacote.getValormoedaestrangeira() * cambio.getValor();
			}
			
		}
		return valorInicial;
	}
	 
}
