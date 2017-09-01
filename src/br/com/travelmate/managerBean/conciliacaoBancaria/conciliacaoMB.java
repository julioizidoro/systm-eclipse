package br.com.travelmate.managerBean.conciliacaoBancaria;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class conciliacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Banco banco;
	private List<TransacaoBean> listaTransacao;
	private Date dataInicial;
	private Date dataFinal;
	private UploadedFile arquivo;
	private Unidadenegocio unidadenegocio;
	private List<Contaspagar> listaLacamentos;
	private List<ConciliacaoBean> listaConciliacaoBancaria;
	private String nomeBotaoConciliar = "Conciliar";
	private List<TransacaoBean> listaTransacaoNaoConciliado;
	private List<Contaspagar> listaOutrosNaoConciliado;
	private Integer sizeConciliada;
	private TransacaoBean transacaoBean;
	private Contaspagar outroslancamentos;
	
	
	
	
	public TransacaoBean getTransacaoBean() {
		return transacaoBean;
	}
	public void setTransacaoBean(TransacaoBean transacaoBean) {
		this.transacaoBean = transacaoBean;
	}
	public Contaspagar getOutroslancamentos() {
		return outroslancamentos;
	}
	public void setOutroslancamentos(Contaspagar outroslancamentos) {
		this.outroslancamentos = outroslancamentos;
	}
	public Integer getSizeConciliada() {
		return sizeConciliada;
	}
	public void setSizeConciliada(Integer sizeConciliada) {
		this.sizeConciliada = sizeConciliada;
	}
	public List<Contaspagar> getListaOutrosNaoConciliado() {
		return listaOutrosNaoConciliado;
	}
	public void setListaOutrosNaoConciliado(List<Contaspagar> listaOutrosNaoConciliado) {
		this.listaOutrosNaoConciliado = listaOutrosNaoConciliado;
	}
	public List<TransacaoBean> getListaTransacaoNaoConciliado() {
		return listaTransacaoNaoConciliado;
	}
	public void setListaTransacaoNaoConciliado(List<TransacaoBean> listaTransacaoNaoConciliado) {
		this.listaTransacaoNaoConciliado = listaTransacaoNaoConciliado;
	}
	public String getNomeBotaoConciliar() {
		return nomeBotaoConciliar;
	}
	public void setNomeBotaoConciliar(String nomeBotaoConciliar) {
		this.nomeBotaoConciliar = nomeBotaoConciliar;
	}
	public List<ConciliacaoBean> getListaConciliacaoBancaria() {
		return listaConciliacaoBancaria;
	}
	public void setListaConciliacaoBancaria(List<ConciliacaoBean> listaConciliacaoBancaria) {
		this.listaConciliacaoBancaria = listaConciliacaoBancaria;
	}
	public List<Contaspagar> getListaLacamentos() {
		return listaLacamentos;
	}
	public void setListaLacamentos(List<Contaspagar> listaLacamentos) {
		this.listaLacamentos = listaLacamentos;
	}
	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}
	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}
	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public UploadedFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
	
	public List<TransacaoBean> getListaTransacao() {
		return listaTransacao;
	}
	public void setListaTransacao(List<TransacaoBean> listaTransacao) {
		this.listaTransacao = listaTransacao;
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
	
	
	public void carregarArquivo(FileUploadEvent e){
		listaConciliacaoBancaria = null;
		arquivo = e.getFile();
		File arq = new File(arquivo.getFileName());
		FileInputStream file = null; 
		try {
			file = (FileInputStream) arquivo.getInputstream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
   	 	LerOFXBean ler = new LerOFXBean();
   	 	ler.iniciarLeitura(file);
   	 	listaTransacao = ler.getListaTransacao();
   	 	if ((ler.getAgencia()!=null) && (ler.getConta()!=null)){
   	 		consultarBanco(ler.getAgencia(), ler.getConta());
   	 	}
   	 	if (banco!=null){
   	 		dataInicial = listaTransacao.get(0).getData();
   	 		dataFinal = listaTransacao.get(listaTransacao.size()-1).getData();
   	 		carregarOutrosLancamentos();
   	 		if (listaLacamentos!=null){
  	 			conciliar();
   	 		}
   	 	}
   	 	
   	 	
   	 	
	}
	
	public void consultarBanco(String agencia , String conta){
		BancoFacade bancoFacade = new BancoFacade();
   	 	List<Banco> listaBanco = bancoFacade.listar();
   	 	if (listaBanco!=null){
   	 		if (listaBanco.size()>0){
   	 			banco = listaBanco.get(0);
   	 		}
   	 	}
	}
	
	public void carregarOutrosLancamentos(){
		ContasPagarFacade outrosLancamentosFacade = new ContasPagarFacade();
		String sql = "SELECT o FROM Contaspagar o where o.datacompensacao>='" +
		Formatacao.ConvercaoDataSql(dataInicial) + "' and o.datacompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' and o.banco.idbanco=" + banco.getIdbanco() + " order by o.datacompensacao";
		listaLacamentos = outrosLancamentosFacade.listar(sql);
		unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
	}
	
	
	public void conciliar(){ 
		for (int i = 0; i < listaTransacao.size(); i++) {
			verficarLancamentos(listaTransacao.get(i));
		}
		sizeConciliada = listaConciliacaoBancaria.size();
		for (int i = 0; i < listaTransacao.size(); i++) {
			gerarListaTransacaoNaoConciliada(listaTransacao.get(i));
		}
		gerarListaOutrosLancamentosNaoConciliado();
		for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
			if (listaConciliacaoBancaria.get(i).getOutroslancamentos() == null) {
				listaConciliacaoBancaria.get(i).setOutroslancamentos(new Contaspagar());
				listaConciliacaoBancaria.get(i).getOutroslancamentos().setSelecionado(false);
			}
		}
	}
	 
	public void verficarLancamentos(TransacaoBean transacao){
		ConciliacaoBean cb = new ConciliacaoBean();
		if (listaConciliacaoBancaria == null) {
			listaConciliacaoBancaria = new ArrayList<ConciliacaoBean>();
		} 
		for (int j = 0; j < listaLacamentos.size(); j++) {
			Float valor;
			String dataTransacao = Formatacao.ConvercaoDataPadrao(transacao.getData());
			String dataLancamento = Formatacao.ConvercaoDataPadrao(listaLacamentos.get(j).getDatacompensacao());
			if (transacao.getTipo().equalsIgnoreCase("DEBIT")){ 
				valor =  transacao.getValorSaida();
				if ((listaLacamentos.get(j).getValorsaida().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))){
					listaLacamentos.get(j).setConciliacao(transacao.getId());
					listaLacamentos.get(j).setConciliada(true);
					transacao.setConciliada(true);
					cb = new ConciliacaoBean();
					cb.setOutroslancamentos(listaLacamentos.get(j));
					cb.setTransacao(transacao);
					listaConciliacaoBancaria.add(cb);
				}
			}else if (transacao.getTipo().equalsIgnoreCase("CREDIT")){
				valor =  transacao.getValorEntrada();
				if ((listaLacamentos.get(j).getValorentrada().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))){
					listaLacamentos.get(j).setConciliacao(transacao.getId());
					listaLacamentos.get(j).setConciliada(true);
					transacao.setConciliada(true);
					cb = new ConciliacaoBean();
					cb.setOutroslancamentos(listaLacamentos.get(j));
					cb.setTransacao(transacao);
					listaConciliacaoBancaria.add(cb);
				}
			}
			
		}
		
		
	} 
	
	
	public String estiloDaTabelaTransacao(TransacaoBean transacao){
		String color;
		if (transacao.getConciliada() == true) {
			color = "color:black;";
		}else{
			color = "color:red;font-weight:bold;";
		}
		return color;
	}
	
	public String estiloDaTabelaOutrosLancamentos(Contaspagar outros){
		String color;
		if (outros.getConciliada() == true) {
			color = "color:gren";
		}else{
			color = "color:red;font-weight:bold;";
		}
		return color;
	}
	
	
	public void gerarListaTransacaoNaoConciliada(TransacaoBean transacao){
		ConciliacaoBean conciliacaoBean = new ConciliacaoBean();
		if (listaTransacaoNaoConciliado == null) {
			listaTransacaoNaoConciliado = new ArrayList<TransacaoBean>();
		}
			if (transacao.getConciliada() == false) {
				listaTransacaoNaoConciliado.add(transacao);
				conciliacaoBean.setTransacao(transacao);
				listaConciliacaoBancaria.add(conciliacaoBean);
			}
		
	}
	
	public void gerarListaOutrosLancamentosNaoConciliado(){
		ConciliacaoBean conciliacaoBean = new ConciliacaoBean();
		if (listaOutrosNaoConciliado == null) {
			listaOutrosNaoConciliado = new ArrayList<Contaspagar>();
		} 
		for (int i = 0; i < listaLacamentos.size(); i++) {
			if (listaLacamentos.get(i).getConciliada() == false) {
				listaOutrosNaoConciliado.add(listaLacamentos.get(i));
				conciliacaoBean.setOutroslancamentos(listaLacamentos.get(i));
				listaConciliacaoBancaria.get(sizeConciliada +i).setOutroslancamentos(conciliacaoBean.getOutroslancamentos());
				
			}
		}
	}
	
	
	public String novaConciliacao(ConciliacaoBean conciliacao) {
		TransacaoBean transacao = null;
		Contaspagar outros = null;
		for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
			if (listaConciliacaoBancaria.get(i).getTransacao().isSelecionado()) {
				transacao = listaConciliacaoBancaria.get(i).getTransacao();
				i = listaConciliacaoBancaria.size();
			}
		}
		for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
			if (listaConciliacaoBancaria.get(i).getOutroslancamentos() != null) {
				if (listaConciliacaoBancaria.get(i).getOutroslancamentos().isSelecionado()) {
					outros = listaConciliacaoBancaria.get(i).getOutroslancamentos();
					i = listaConciliacaoBancaria.size();
				}				
			}
		}
		if(transacao != null && outros != null) {
			ContasPagarFacade outrosFacade = new ContasPagarFacade();
			if (transacao.getValorEntrada() > 0f) {
				outros.setValorentrada(transacao.getValorEntrada());
				outrosFacade.salvar(outros);
			}else{
				outros.setValorsaida(transacao.getValorSaida());
				outrosFacade.salvar(outros);
			}
			listaConciliacaoBancaria = null;
	        carregarOutrosLancamentos();
	        conciliar();
			return "";
		}else{
			transacaoBean = conciliacao.getTransacao();
			outroslancamentos = conciliacao.getOutroslancamentos();
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("banco", banco);
			session.setAttribute("transacaoBean", transacaoBean);
			session.setAttribute("outroslancamentos", outroslancamentos);
			session.setAttribute("unidadenegocio", unidadenegocio);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("cadConciliacao"); 
			return "";			
		}
    } 
	
	
	public void retornoDialogConciliado(SelectEvent e) {
        listaConciliacaoBancaria = null;
        carregarOutrosLancamentos();
        conciliar();
	}

}
