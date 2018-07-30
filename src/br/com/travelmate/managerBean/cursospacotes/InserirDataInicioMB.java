package br.com.travelmate.managerBean.cursospacotes;

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

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoDataFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.DatasBean;
import br.com.travelmate.managerBean.OrcamentoCurso.EditarOrcamentoOcursoBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ResultadoOrcamentoBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Fornecedorcidadeidiomaprodutodata;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class InserirDataInicioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataInicio;
	private Ocurso pacote;
	private Lead lead;
	private List<DatasBean> listaFornecedorCidadeDatas;
	private List<DatasBean> listaDatas;
	private boolean calendario=true;
	private boolean comboDatas=false;
	private DatasBean datasBean;
	@Inject
	private OCursoDescontoDao oCursoDescontoDao;
	@Inject
	private OCursoProdutoDao oCursoProdutoDao;
	@Inject
	private OcursoSeguroViagemDao ocursoSeguroViagemDao;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead = (Lead) session.getAttribute("lead");
		pacote = (Ocurso) session.getAttribute("pacote");
		session.removeAttribute("lead");
		session.removeAttribute("pacote");
		verificarDatas();
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


	public Lead getLead() {
		return lead;
	}


	public void setLead(Lead lead) {
		this.lead = lead;
	}

	
	
	public List<DatasBean> getListaFornecedorCidadeDatas() {
		return listaFornecedorCidadeDatas;
	}


	public void setListaFornecedorCidadeDatas(List<DatasBean> listaFornecedorCidadeDatas) {
		this.listaFornecedorCidadeDatas = listaFornecedorCidadeDatas;
	}


	public List<DatasBean> getListaDatas() {
		return listaDatas;
	}


	public void setListaDatas(List<DatasBean> listaDatas) {
		this.listaDatas = listaDatas;
	}


	public boolean isCalendario() {
		return calendario;
	}


	public void setCalendario(boolean calendario) {
		this.calendario = calendario;
	}


	public boolean isComboDatas() {
		return comboDatas;
	}


	public void setComboDatas(boolean comboDatas) {
		this.comboDatas = comboDatas;
	}


	public DatasBean getDatasBean() {
		return datasBean;
	}


	public void setDatasBean(DatasBean datasBean) {
		this.datasBean = datasBean;
	}


	public String gerarOrcamento() throws Exception {
		Ocurso ocurso = new Ocurso();
		Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), pacote.getCambio().getMoedas());
		ocurso.setCliente(lead.getCliente());
		ocurso.setCambio(cambio);
		ocurso.setCargahoraria(pacote.getCargahoraria());
		if (dataInicio != null) {
			ocurso.setDatainicio(dataInicio);
		}else{
			ocurso.setDatainicio(pacote.getDatainicio());
		}
		ocurso.setDataorcamento(new Date());
		ocurso.setDatatermino(Formatacao.SomarDiasDatas(ocurso.getDatainicio(), pacote.getNumerosemanas().intValue()));
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
				ocurso.getDatainicio(), aplicacaoMB, usuarioLogadoMB, pacote.getIdocurso(), oCursoDescontoDao, oCursoProdutoDao, ocursoSeguroViagemDao);
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
	
	
	public void selecionarData(){
		if (comboDatas) {
			dataInicio = Formatacao.ConvercaoStringData(datasBean.getDescricao());
		}
		if (dataInicio != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacote", pacote);
			session.setAttribute("lead", lead);
			RequestContext.getCurrentInstance().closeDialog(dataInicio);
		}else{
			Mensagem.lancarMensagemInfo("Informe a data de início", "");
		}
	}
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void verificarDatas(){
		if(pacote.getProdutosorcamento()!=null
				&& pacote.getProdutosorcamento().getIdprodutosOrcamento()!=null
				&& pacote.getFornecedorcidadeidioma()!=null
				&& pacote.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()!=null){
			FornecedorCidadeIdiomaProdutoFacade produtoFacade = new FornecedorCidadeIdiomaProdutoFacade();
			Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto = produtoFacade
					.consultar("SELECT f FROM Fornecedorcidadeidiomaproduto f WHERE"
							+ " f.fornecedorcidadeidioma.idfornecedorcidadeidioma="+pacote.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
							+ " AND f.produtosorcamento.idprodutosOrcamento="+pacote.getProdutosorcamento().getIdprodutosOrcamento());
			if(fornecedorcidadeidiomaproduto!=null && fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto()!=null){
				FornecedorCidadeIdiomaProdutoDataFacade dataFacade = new FornecedorCidadeIdiomaProdutoDataFacade();
				List<Fornecedorcidadeidiomaprodutodata> lista =
						dataFacade.listar("SELECT f FROM Fornecedorcidadeidiomaprodutodata f WHERE"
								+ " f.fornecedorcidadeidiomaproduto.idfornecedorcidadeidiomaproduto="
								+fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto() + " and f.datainicio>='" + Formatacao.ConvercaoDataSql(new Date()) + "' "
										+ " order by f.datainicio");
				if(lista!=null){    
					listaFornecedorCidadeDatas= new ArrayList<>();
					listaDatas = new ArrayList<>();
					for (int i = 0; i < lista.size(); i++) { 
						DatasBean datasBean = new DatasBean();
						datasBean.setDescricao(Formatacao.ConvercaoDataPadrao(lista.get(i).getDatainicio()));
						datasBean.setNumerosemanas(lista.get(i).getNumerosemanas());
						listaDatas.add(datasBean);
						listaFornecedorCidadeDatas.add(datasBean);
					}
					calendario = false;
					comboDatas = true;
				}else{
					calendario = true;
					comboDatas = false;
				}
			}else{
				calendario = true;
				comboDatas = false;
			}
		}
	}
}
