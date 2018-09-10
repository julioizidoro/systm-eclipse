package br.com.travelmate.managerBean.orcamentoManual;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ModeloOrcamentoCursoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Modeloorcamentocurso;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ModeloOrcamentoManualMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Modeloorcamentocurso> listaOrcamento;
	private String nomeModelo = "";
	private boolean matriz;
	
	@PostConstruct
	public void init() {
		gerarListaModelos();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			matriz = true;
		} 
	}
	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}
	
	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Modeloorcamentocurso> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<Modeloorcamentocurso> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}

	public String getNomeModelo() {
		return nomeModelo;
	}

	public void setNomeModelo(String nomeModelo) {
		this.nomeModelo = nomeModelo;
	}

	public boolean isMatriz() {
		return matriz;
	}

	public void setMatriz(boolean matriz) {
		this.matriz = matriz;
	}

	public String novo(){
		return "cadModeloOrcamentoManual";
	}
	
	public String editar(Modeloorcamentocurso modeloOrcamentoCurso){
		int idusuario = usuarioLogadoMB.getUsuario().getIdusuario();
		if(modeloOrcamentoCurso.getUsuario().getIdusuario()==idusuario){
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        session.setAttribute("modeloOrcamentoCurso", modeloOrcamentoCurso);
			return "cadModeloOrcamentoManual";
		}else{
			Mensagem.lancarMensagemFatal("Atenção", "Você não possui acesso a este modelo.");
		}
		return "";
	}
	
	public void limparPesquisa() {
		nomeModelo = "";
		gerarListaModelos();
	}
	
	public void gerarListaModelos() {
		String sql = "Select m from Modeloorcamentocurso m where m.nome like '%" + nomeModelo
					+ "%' and m.situacao='Ativo'";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (matriz) {
				sql = sql + " and m.unidadenegocio.idunidadeNegocio=6 or m.unidadenegocio.idunidadeNegocio=2 or m.unidadenegocio.idunidadeNegocio=1 or m.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}else{
				sql = sql +" and m.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}
		}
		sql = sql + " order by m.data desc, m.idorcamentoCurso desc";
		ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
		listaOrcamento = modeloOrcamentoCursoFacade.listarModeloOrcamentoCurso(sql);
		if (listaOrcamento == null) {
			listaOrcamento = new ArrayList<Modeloorcamentocurso>();
		}
	}
	
	public String importar(Modeloorcamentocurso modeloorcamentocurso){
		RequestContext.getCurrentInstance().closeDialog(modeloorcamentocurso);
		return "";
	}

	public void desativar(Modeloorcamentocurso modeloOrcamentoCurso){
		int idusuario = usuarioLogadoMB.getUsuario().getIdusuario();
		if(modeloOrcamentoCurso.getUsuario().getIdusuario()==idusuario){
			modeloOrcamentoCurso.setSituacao("Cancelado");
			ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
			modeloOrcamentoCurso = modeloOrcamentoCursoFacade.salvar(modeloOrcamentoCurso);
			listaOrcamento.remove(modeloOrcamentoCurso);
		}else{
			Mensagem.lancarMensagemFatal("Atenção", "Você não possui acesso a este modelo.");
		}
	}
	
	public String imagemSituacao(Modeloorcamentocurso modeloOrcamentoCurso){
		if(modeloOrcamentoCurso.getSituacao().equalsIgnoreCase("Ativo")){
			return "../../resources/img/bolaVerde.png";
		}else return "../../resources/img/bolaVermelha.png";
	}
}
