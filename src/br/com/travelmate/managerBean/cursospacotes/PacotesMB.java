package br.com.travelmate.managerBean.cursospacotes;

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
import org.primefaces.event.SelectEvent;

import br.com.travelmate.dao.CoProdutosDao;
import br.com.travelmate.dao.OCursoDao;
import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.EditarOrcamentoOcursoBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ResultadoOrcamentoBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Pacotesinicial;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class PacotesMB implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	@Inject
	private CoProdutosDao coProdutosDao;
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
	private Lead lead;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataInicio;
	private Ocurso pacote;
	private boolean habilitarVoltaBtn = true;
	@Inject
	private OCursoDescontoDao oCursoDescontoDao;
	@Inject
	private OCursoProdutoDao oCursoProdutoDao;
	@Inject
	private OcursoSeguroViagemDao ocursoSeguroViagemDao;
	
	@Inject 
	private OCursoDao oCursoDao;
	private String moedaNacional;

	@PostConstruct
	public void init() {
		gerarListaPais();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead = (Lead) session.getAttribute("lead");
		//listarPacotesEspecial();
		//listarCursosPacotes();
		getAplicacaoMB(); 
		getUsuarioLogadoMB();
		if (lead == null) {
			habilitarVoltaBtn = false;
		} else {
			habilitarVoltaBtn = true;
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	} 
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Ocurso getPacote() {
		return pacote;
	}

	public void setPacote(Ocurso pacote) {
		this.pacote = pacote;
	}

	public boolean isHabilitarVoltaBtn() {
		return habilitarVoltaBtn;
	}

	public void setHabilitarVoltaBtn(boolean habilitarVoltaBtn) {
		this.habilitarVoltaBtn = habilitarVoltaBtn;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void consultarListarCursosPacotes(Pais pais){ 
		String sql = "SELECT c FROM Ocurso c WHERE c.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="+ pais.getIdpais() +
				" and c.modelo=true ORDER BY  c.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais";
		listaOCursos = oCursoDao.listar(sql);  
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
		String retorno = Formatacao.ConvercaoDataPadrao(ocurso.getDatainicio());
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
			externalContext.redirect("http://local.systm.com.br/informativospacotes/"
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
	
	public String formatarTotalMoedaNacional(Ocurso ocurso) {
		Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), ocurso.getCambio().getMoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		return moedaNacional + " " + Formatacao.formatarFloatString(ocurso.getTotalmoedaestrangeira() * cambio.getValor());
	}

	public String formatarTotalMoedaEstrangeira(Ocurso ocurso) {
		String valor = ocurso.getCambio().getMoedas().getSigla();
		valor = valor + " " + Formatacao.formatarFloatString(ocurso.getTotalmoedaestrangeira()); 
		return valor;
	}
	
	public String gerarAcomodacao(Ocurso ocurso) {
		List<Ocrusoprodutos> listaAcomodacao = new ArrayList<Ocrusoprodutos>();
		List<Ocrusoprodutos> lista = ocurso.getOcrusoprodutosList();
		if (lista!=null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getNomegrupo().equalsIgnoreCase("Acomodação")) {
					listaAcomodacao.add(lista.get(i));
				}
			}
		}
		if (listaAcomodacao.size()>0) {
			Integer numero = Formatacao.formatarDouble(listaAcomodacao.get(0).getNumerosemanas());
			return "Acomodação: " + String.valueOf(numero)  + " semanas";
		}
		return "Acomodação: Sem acomodação";
	}
	
	public boolean habilitarBotaoOrcamento() {
		if (lead!=null) {
			return false;
		}
		return true;
	}
	
	public String gerarOrcamento() throws Exception {
		Ocurso ocurso = new Ocurso();
		Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), pacote.getCambio().getMoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		ocurso.setCliente(lead.getCliente());
		ocurso.setCambio(cambio);
		ocurso.setCargahoraria(pacote.getCargahoraria());
		ocurso.setDatainicio(pacote.getDatainicio());   
		ocurso.setDataorcamento(new Date());
		ocurso.setDatatermino(Formatacao.SomarDiasDatas(ocurso.getDatainicio(), (pacote.getNumerosemanas().intValue() * 7)));
		ocurso.setDatavalidade(Formatacao.SomarDiasDatas(ocurso.getDataorcamento(), 5));
		ocurso.setDesconto(pacote.getDesconto());
		ocurso.setEnviadoemail(false);
		ocurso.setFornecedorcidadeidioma(pacote.getFornecedorcidadeidioma());
		ocurso.setIdioma(pacote.getIdioma());
		ocurso.setModelo(false);
		ocurso.setNivelidioma(pacote.getNivelidioma());
		ocurso.setNomecliente(ocurso.getCliente().getNome());
		ocurso.setNumerodiasferiado(pacote.getNumerodiasferiado());
		ocurso.setNumerosemanas(pacote.getNumerosemanas());
		ocurso.setNumerosemanasbrinde(pacote.getNumerosemanasbrinde());
		ocurso.setNumerosemanastotal(pacote.getNumerosemanastotal());
		ocurso.setTotalmoedaestrangeira(pacote.getTotalmoedaestrangeira());
		ocurso.setTotalmoedanacional(pacote.getTotalmoedanacional());
		ocurso.setTurno(pacote.getTurno());
		ocurso.setObservacao(pacote.getObservacao());
		ocurso.setOccliente(1);
		ocurso.setOcrusoprodutosList(pacote.getOcrusoprodutosList());
		ocurso.setOcursodescontoList(pacote.getOcursodescontoList());
		ocurso.setOcursoformapagamentoList(pacote.getOcursoformapagamentoList());
		ocurso.setOcursoseguroList(pacote.getOcursoseguroList());
		ocurso.setProdutosorcamento(pacote.getProdutosorcamento());
		ocurso.setSelecionado(pacote.isSelecionado());
		ocurso.setUsuario(usuarioLogadoMB.getUsuario());
		ocurso.setValoravista(ocurso.getValoravista());
		ocurso.setValorcambio(cambio.getValor());
		ocurso.setValoroutros(pacote.getValoroutros());
		ocurso.setValorpassagem(pacote.getValorpassagem());
		ocurso.setValorvisto(pacote.getValorvisto());
		
		EditarOrcamentoOcursoBean editarOrcamentoOcurso = new EditarOrcamentoOcursoBean(ocurso, lead.getCliente(),
				ocurso.getDatainicio(), aplicacaoMB, usuarioLogadoMB, pacote.getIdocurso(), oCursoDescontoDao, oCursoProdutoDao, ocursoSeguroViagemDao, coProdutosDao);
		ResultadoOrcamentoBean resultadoOrcamentoBean = new ResultadoOrcamentoBean();

		resultadoOrcamentoBean.setOcurso(ocurso);
		resultadoOrcamentoBean.setCambio(cambio);
		resultadoOrcamentoBean.setFornecedorcidadeidioma(editarOrcamentoOcurso.getOcurso().getFornecedorcidadeidioma());
		resultadoOrcamentoBean
				.setFornecedorcidade(resultadoOrcamentoBean.getFornecedorcidadeidioma().getFornecedorcidade());
		resultadoOrcamentoBean.setDataConsulta(editarOrcamentoOcurso.getDataconsulta());
		resultadoOrcamentoBean.setProdutoFornecedorBean(editarOrcamentoOcurso.retornarFornecedorProdutosBean());
		resultadoOrcamentoBean.setListaOpcionais(editarOrcamentoOcurso.gerarListaValorCoProdutos("Opcional"));
		// opcional selecionado
		for (int i = 0; i < editarOrcamentoOcurso.getListaProdutos().size(); i++) {
			if (editarOrcamentoOcurso.getListaProdutos().get(i).getNomegrupo().equalsIgnoreCase("CustosExtras")) {
				int id = editarOrcamentoOcurso.getListaProdutos().get(i).getValorcoprodutos().getIdvalorcoprodutos();
				if (resultadoOrcamentoBean.getListaOpcionais() != null) {
					for (int j = 0; j < resultadoOrcamentoBean.getListaOpcionais().size(); j++) {
						if (resultadoOrcamentoBean.getListaOpcionais().get(j).getValorcoprodutos()
								.getIdvalorcoprodutos() == id) {
							resultadoOrcamentoBean.getListaOpcionais().get(j).setSelecionadoOpcional(true);
							resultadoOrcamentoBean.getListaOpcionais().get(j).setValorOrigianl(
									editarOrcamentoOcurso.getListaProdutos().get(i).getValororiginal());
							resultadoOrcamentoBean.getListaOpcionais().get(j).setValorOriginalRS(
									editarOrcamentoOcurso.getListaProdutos().get(i).getValororiginal()
											* cambio.getValor());
							resultadoOrcamentoBean.getListaOpcionais().get(j).setSomarvalortotal(
									editarOrcamentoOcurso.getListaProdutos().get(i).isSomavalortotal());
						}
					}
				}
			}
		}
		resultadoOrcamentoBean.setListaOutrosProdutos(editarOrcamentoOcurso.gerarListaProdutosExtras());
		resultadoOrcamentoBean.getOcurso().setOcursodescontoList(editarOrcamentoOcurso.addOcursoDesconto());
		resultadoOrcamentoBean.setValorPassagemAerea(ocurso.getValorpassagem());
		resultadoOrcamentoBean.setValorVistoConsular(ocurso.getValorvisto());
		resultadoOrcamentoBean.setValorOutros(ocurso.getValoroutros());
		Seguroviagem seguroviagem = editarOrcamentoOcurso.buscarSeguroViagem();
		if (seguroviagem != null) {
			resultadoOrcamentoBean.setSeguroSelecionado(true);
			resultadoOrcamentoBean.setSeguroviagem(seguroviagem);
		}
		if (editarOrcamentoOcurso.getValorAcomodacao() != null) {
			resultadoOrcamentoBean.setListaAcomodacoes(new ArrayList<ProdutosOrcamentoBean>());
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setSelecionado(true);
			po.setValorcoprodutos(editarOrcamentoOcurso.getValorAcomodacao().getValorcoprodutos());
			po.setNumeroSemanas(editarOrcamentoOcurso.getValorAcomodacao().getNumerosemanas());
			po.setValorOrigianl(editarOrcamentoOcurso.getValorAcomodacao().getValororiginal());
			po.setValorOriginalRS(editarOrcamentoOcurso.getValorAcomodacao().getValororiginal() * cambio.getValor());
			resultadoOrcamentoBean.getListaAcomodacoes().add(po);
		}
		if (editarOrcamentoOcurso.getListaAcOpcional() != null) {
			resultadoOrcamentoBean.setListaAcOpcional(new ArrayList<ProdutosOrcamentoBean>());
			for (int i = 0; i < editarOrcamentoOcurso.getListaAcOpcional().size(); i++) {
				ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
				po.setSelecionado(true);
				po.setValorcoprodutos(editarOrcamentoOcurso.getListaAcOpcional().get(i).getValorcoprodutos());
				po.setNumeroSemanas(editarOrcamentoOcurso.getListaAcOpcional().get(i).getNumerosemanas());
				po.setValorOrigianl(
						editarOrcamentoOcurso.getListaAcOpcional().get(i).getValorcoprodutos().getValororiginal());
				po.setValorOriginalRS(po.getValorOrigianl() * cambio.getValor());
				po.setValorOriginalAcOpcional(editarOrcamentoOcurso.getListaAcOpcional().get(i).getValororiginal());
				po.setValorRSacOpcional(po.getValorOriginalAcOpcional() * cambio.getValor());
				resultadoOrcamentoBean.getListaAcOpcional().add(po);
			}
		}
		if (editarOrcamentoOcurso.getListaTransfer() != null) {
			resultadoOrcamentoBean.setListaTransfer(new ArrayList<ProdutosOrcamentoBean>());
			for (int i = 0; i < editarOrcamentoOcurso.getListaTransfer().size(); i++) {
				ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
				po.setSelecionado(true);
				po.setValorcoprodutos(editarOrcamentoOcurso.getListaTransfer().get(i).getValorcoprodutos());
				po.setNumeroSemanas(editarOrcamentoOcurso.getListaTransfer().get(i).getNumerosemanas());
				po.setValorOrigianl(
						editarOrcamentoOcurso.getListaTransfer().get(i).getValorcoprodutos().getValororiginal());
				po.setValorOriginalRS(po.getValorOrigianl() * cambio.getValor());
				resultadoOrcamentoBean.getListaTransfer().add(po);
			}
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
		// RequestContext.getCurrentInstance().closeDialog(null);
		return "orcamentocursotarifario";
	}
	
	public String retornaHistoricoLead() {
		if (lead != null) {
			if (lead.getIdlead() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("lead", lead);
				session.setAttribute("posicao", 0);
				return "historicoCliente";
			}
		}
		return "";
	}
	
	public boolean verificarCarimbo(Ocurso ocurso){
		if (ocurso.getNomecarimbo() != null && ocurso.getNomecarimbo().length() > 0) {
			return true;
		}
		return false;
	}
	
	public void setarPacote(Ocurso pacote){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 350);
		session.setAttribute("lead", lead);
		session.setAttribute("pacote", pacote);
		RequestContext.getCurrentInstance().openDialog("inserirDataInicio", options, null);
	}
	
	
	public String retornoDialogData(SelectEvent event){
		Date dataInicio = (Date) event.getObject();
		if (dataInicio != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			lead = (Lead) session.getAttribute("lead");
			pacote = (Ocurso) session.getAttribute("pacote");
			session.removeAttribute("pacote");
			session.removeAttribute("lead");
			pacote.setDatainicio(dataInicio);
			try {   
				gerarOrcamento();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/orcamento/orcamentoCurso.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	
	
	
}
