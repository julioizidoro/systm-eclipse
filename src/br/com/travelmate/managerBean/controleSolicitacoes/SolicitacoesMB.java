package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.TiSolicitacoesFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class SolicitacoesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Tisolicitacoes> listaSolicitacoes;
	private Departamento departamento;
	private List<Departamento> listaDepartamento;
	private Date dataInicial;
	private Date dataFinal;
	private String descricao;
	private boolean acessoTI;
	private String situacao;
	private boolean desabilitarDepartamento;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaSolicitacoes = (List<Tisolicitacoes>) session.getAttribute("listaSolicitacoes");
		session.removeAttribute("listaSolicitacoes");
		gerarListaDepartamentos();
		if (listaSolicitacoes == null) {
			gerarListaSolicitacoes();
		}
		if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes() && !usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessoliberadassolicitacoes()){
			departamento = usuarioLogadoMB.getUsuario().getDepartamento();
			desabilitarDepartamento = true;
		}
		habilitarCamposTI();
	}



	public List<Tisolicitacoes> getListaSolicitacoes() {
		return listaSolicitacoes;
	}



	public void setListaSolicitacoes(List<Tisolicitacoes> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	}
	

	public Departamento getDepartamento() {
		return departamento;
	}



	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}



	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}



	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}



	public Date getDataInicial() {
		return dataInicial;
	}



	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}



	public Date getDataFinal() {
		return dataFinal;
	}



	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public boolean isAcessoTI() {
		return acessoTI;
	}



	public void setAcessoTI(boolean acessoTI) {
		this.acessoTI = acessoTI;
	}



	public String getSituacao() {
		return situacao;
	}



	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}



	public boolean isDesabilitarDepartamento() {
		return desabilitarDepartamento;
	}



	public void setDesabilitarDepartamento(boolean desabilitarDepartamento) {
		this.desabilitarDepartamento = desabilitarDepartamento;
	}



	public void gerarListaSolicitacoes(){
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		String sql = "SELECT t FROM Tisolicitacoes t ";
		if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes() && usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessoliberadassolicitacoes()) {
			sql = sql + " WHERE t.liberar=true ";
		}else if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes() && !usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessoliberadassolicitacoes()){
			sql = sql + " WHERE t.departamento.iddepartamento=" + usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() + " and t.liberar=true ";
		}
		listaSolicitacoes = tiSolicitacoesFacade.listar(sql);
		if (listaSolicitacoes == null) {
			listaSolicitacoes = new ArrayList<>();
		}
	}
	
	
	public void editar(RowEditEvent event) {
		Tisolicitacoes tisolicitacoes = ((Tisolicitacoes) event.getObject());
		if (tisolicitacoes != null) {
			TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
			tiSolicitacoesFacade.salvar(tisolicitacoes);
			Mensagem.lancarMensagemInfo("Editado com sucesso!", "");
		}
	}
	
	
	public void cancelarEdicao(RowEditEvent event) {
		Mensagem.lancarMensagemInfo("Operação cancelada!", "");
	}
	
	
	public void retornoDialogNova(SelectEvent event){
		Tisolicitacoes tisolicitacoes = (Tisolicitacoes) event.getObject();
		if (tisolicitacoes.getIdtisolicitacoes() != null) {
			Mensagem.lancarMensagemInfo("Solicitação enviada com sucesso", "");
			if (listaSolicitacoes == null) {
				listaSolicitacoes = new ArrayList<>();
			}
			listaSolicitacoes.add(tisolicitacoes);
		}
	}
	
	
	public void cadastrarSolicitacao(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("cadSolicitacoes", options, null);
	}
	
	
	public void gerarListaDepartamentos(){
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("SELECT d FROM Departamento d WHERE d.lista=true");
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<>();
		}
	}
	
	
	public String historicoSolicitacoes(Tisolicitacoes tisolicitacoes){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaSolicitacoes", listaSolicitacoes);
		session.setAttribute("tisolicitacoes", tisolicitacoes);
		return "consSolicitacoesHistorico";
	}
	
	
	public void habilitarCamposTI(){
		if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes()) {
			acessoTI = true;
		}else{
			acessoTI = false;
		}
	}
	
	public void liberarSolicitacao(Tisolicitacoes tisolicitacoes){
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		if (tisolicitacoes.getLiberar().booleanValue()) {
			tisolicitacoes.setLiberar(true);
			Mensagem.lancarMensagemInfo("Solicitação Liberada", "");
		}else{
			tisolicitacoes.setLiberar(false);
			Mensagem.lancarMensagemInfo("Solicitação Não Liberada", "");
		}
		tiSolicitacoesFacade.salvar(tisolicitacoes);
	}
	
	
	public void concluirSolicitacoes(Tisolicitacoes tisolicitacoes){
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		tisolicitacoes.setConcluido(true);
		tisolicitacoes.setDataconclusao(new Date());
		tisolicitacoes.setSituacao("Concluida");
		tiSolicitacoesFacade.salvar(tisolicitacoes);
		Mensagem.lancarMensagemInfo("Concluido com sucesso", "");
		listaSolicitacoes.remove(tisolicitacoes);
	}
	
	
	public String retornarCorTabela(Tisolicitacoes tisolicitacoes) {
		if (tisolicitacoes.getConcluido().booleanValue()) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "";
	}
	
	
	public boolean retornarAcessoConcluir(Tisolicitacoes tisolicitacoes) {
		if (tisolicitacoes.getConcluido().booleanValue()) {
			return true;
		}
		return false;
	}
	
	public boolean retornarEditar(Tisolicitacoes tisolicitacoes) {
		if (tisolicitacoes.getConcluido().booleanValue()) {
			return false;
		}
		return true;
	}
	
	
	public void pesquisar(){
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		String sql = "SELECT t FROM Tisolicitacoes t WHERE t.descricao like '%"+ descricao +"%'";
		if (departamento != null) {
			sql = sql + " AND t.departamento.iddepartamento=" + departamento.getIddepartamento();
		}
		
		if (dataInicial != null) {
			sql = sql + " AND t.datasolicitacao>='" + Formatacao.ConvercaoDataSql(dataInicial) + "'";
		}
		if (dataFinal != null) {
			sql = sql + " AND t.datasolicitacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		if (situacao != null && !situacao.equalsIgnoreCase("")) {
			sql = sql + " AND t.situacao='" + situacao + "'";
		}
		if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes() && usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessoliberadassolicitacoes()) {
			sql = sql + " AND t.liberar=true ";
		}else if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessogerencialsolicitacoes() 
					&& !usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAcessoliberadassolicitacoes()){
			sql = sql + " AND t.departamento.iddepartamento=" + usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento();
		}
		listaSolicitacoes = tiSolicitacoesFacade.listar(sql);
		if (listaSolicitacoes == null) {
			listaSolicitacoes = new ArrayList<>();
		}
	}
	
	
	
	
	
}
