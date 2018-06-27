package br.com.travelmate.managerBean.voluntariadoprojeto.orcamento;
 
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorPaisFacade; 
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.VoluntariadoProjetoValorFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosExtrasBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorpais;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Voluntariadoprojetovalor;
import br.com.travelmate.util.Formatacao; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class VoluntariadoProjetoOrcamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Voluntariadoprojetovalor voluntariadoprojeto;
	private List<Voluntariadoprojetovalor> listaVoluntariadoProjeto;
	private Voluntariadoprojetovalor voluntariadoprojetovalor;
	private List<Voluntariadoprojetovalor> listaValor;
	private Pais pais;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Cidade> listaCidade;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listafornecedorcidade;
	private int nsemanaadicional;
	private Date datainicial;
	private Cliente cliente;
	private boolean digitosFoneResidencial;
	private boolean digitosFoneCelular; 
	private List<Voluntariadoprojetovalor> listaVoluntariadoProjetoCidade;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cliente = (Cliente) session.getAttribute("cliente");
		session.removeAttribute("cliente");
		PaisProdutoFacade paisFacade = new PaisProdutoFacade();
		listaPais = paisFacade.listar(aplicacaoMB.getParametrosprodutos().getVoluntariado());
		fornecedorcidade = new Fornecedorcidade();
		pais = new Pais();
		cidade = new Cidade();
		voluntariadoprojeto = new Voluntariadoprojetovalor();
		voluntariadoprojetovalor = new Voluntariadoprojetovalor();
		datainicial = null;
		digitosFoneCelular = aplicacaoMB
				.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), null);
		digitosFoneResidencial = aplicacaoMB
				.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), null);
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Voluntariadoprojetovalor getVoluntariadoprojeto() {
		return voluntariadoprojeto;
	}

	public void setVoluntariadoprojeto(Voluntariadoprojetovalor voluntariadoprojeto) {
		this.voluntariadoprojeto = voluntariadoprojeto;
	}

	public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
		return voluntariadoprojetovalor;
	}

	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}

	public List<Voluntariadoprojetovalor> getListaVoluntariadoProjeto() {
		return listaVoluntariadoProjeto;
	}

	public void setListaVoluntariadoProjeto(List<Voluntariadoprojetovalor> listaVoluntariadoProjeto) {
		this.listaVoluntariadoProjeto = listaVoluntariadoProjeto;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListafornecedorcidade() {
		return listafornecedorcidade;
	}

	public void setListafornecedorcidade(List<Fornecedorcidade> listafornecedorcidade) {
		this.listafornecedorcidade = listafornecedorcidade;
	}

	public List<Voluntariadoprojetovalor> getListaValor() {
		return listaValor;
	}

	public void setListaValor(List<Voluntariadoprojetovalor> listaValor) {
		this.listaValor = listaValor;
	}

	public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public int getNsemanaadicional() {
		return nsemanaadicional;
	}

	public void setNsemanaadicional(int nsemanaadicional) {
		this.nsemanaadicional = nsemanaadicional;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isDigitosFoneResidencial() {
		return digitosFoneResidencial;
	}

	public void setDigitosFoneResidencial(boolean digitosFoneResidencial) {
		this.digitosFoneResidencial = digitosFoneResidencial;
	}

	public boolean isDigitosFoneCelular() {
		return digitosFoneCelular;
	}

	public void setDigitosFoneCelular(boolean digitosFoneCelular) {
		this.digitosFoneCelular = digitosFoneCelular;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Voluntariadoprojetovalor> getListaVoluntariadoProjetoCidade() {
		return listaVoluntariadoProjetoCidade;
	}

	public void setListaVoluntariadoProjetoCidade(List<Voluntariadoprojetovalor> listaVoluntariadoProjetoCidade) {
		this.listaVoluntariadoProjetoCidade = listaVoluntariadoProjetoCidade;
	}

	public void listarVoluntariadoProjeto() {
		if (pais != null && pais.getIdpais()!= null) {
			String sql = "Select Distinct v from Voluntariadoprojetovalor v where " + " v.voluntariadoprojeto.fornecedorcidade.fornecedor.habilitarorcamento=true " 
					+" and v.voluntariadoprojeto.fornecedorcidade.cidade.pais.idpais="+ pais.getIdpais() + " and v.datafinal>='" + Formatacao.ConvercaoDataSql(new Date()) + "'"
					+" group by v.voluntariadoprojeto.idvoluntariadoprojeto, v.voluntariadoprojeto.fornecedorcidade order by v.voluntariadoprojeto.descricao";
			VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
			listaVoluntariadoProjeto = voluntariadoProjetoValorFacade.listar(sql);
			if (listaVoluntariadoProjeto == null) {
				listaVoluntariadoProjeto = new ArrayList<>();
			}
		}
	}
	
	public void listarCidade() {
		if (pais != null && pais.getIdpais()!=null && voluntariadoprojeto!=null) { 
			String sql = "Select v from Voluntariadoprojetovalor v where "
					+"v.idvoluntariadoprojetovalor="+ voluntariadoprojeto.getIdvoluntariadoprojetovalor() + " and v.datafinal>='" + Formatacao.ConvercaoDataSql(new Date()) + "' " 
					+" group by v.voluntariadoprojeto.fornecedorcidade.cidade.idcidade order by v.voluntariadoprojeto.descricao";
			VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
			listaVoluntariadoProjetoCidade = voluntariadoProjetoValorFacade.listar(sql);
			listaCidade = new ArrayList<Cidade>();
			if (listaVoluntariadoProjetoCidade != null) {  
				for (int i = 0; i < listaVoluntariadoProjetoCidade.size(); i++) {
					listaCidade.add(listaVoluntariadoProjetoCidade.get(i).getVoluntariadoprojeto().getFornecedorcidade().getCidade());
				}
			} 
		}
	}

	public void listarFornecedorCidade() {
		if (pais != null && pais.getIdpais()!=null && voluntariadoprojeto!=null) { 
			String sql = "Select v from Voluntariadoprojetovalor v where "
					+"v.idvoluntariadoprojetovalor="+ voluntariadoprojeto.getIdvoluntariadoprojetovalor() + " and v.voluntariadoprojeto.fornecedorcidade.fornecedor.habilitarorcamento=true "
					+" group by v.voluntariadoprojeto.fornecedorcidade.fornecedor.idfornecedor order by v.voluntariadoprojeto.descricao";
			VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
			listaVoluntariadoProjeto = voluntariadoProjetoValorFacade.listar(sql);
			listafornecedorcidade = new ArrayList<Fornecedorcidade>();
			if (listaVoluntariadoProjeto != null) {  
				for (int i = 0; i < listaVoluntariadoProjeto.size(); i++) {
					listafornecedorcidade.add(listaVoluntariadoProjeto.get(i).getVoluntariadoprojeto().getFornecedorcidade());
				}
			} 
		}
	}

	public void gerarListaValores() {
		if (voluntariadoprojeto != null && voluntariadoprojeto.getIdvoluntariadoprojetovalor() != null
				&& datainicial!=null) {
			String sql = "Select v from Voluntariadoprojetovalor v where v.voluntariadoprojeto.idvoluntariadoprojeto="
					+ voluntariadoprojeto.getVoluntariadoprojeto().getIdvoluntariadoprojeto() + " and v.datainicial<='"
					+ Formatacao.ConvercaoDataSql(datainicial) + "' and v.datafinal>='"
					+ Formatacao.ConvercaoDataSql(datainicial) + "' order by v.datainicial";
			VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
			listaValor = voluntariadoProjetoValorFacade.listar(sql);
			if (listaValor == null) {
				listaValor = new ArrayList<>();
			}
		}
	}

	public String retornarDescricao(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		return voluntariadoprojetovalor.getNumerosemanainicial() + " Semana(s)";
	}

	public String validarMascaraFoneResidencial() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneResidencial);
	}

	public String validarMascaraFoneCelular() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneCelular);
	}

	public String gerarOrcamento() {
		OrcamentoVoluntariadoBean orcamento = new OrcamentoVoluntariadoBean();
		orcamento.setCliente(cliente);
		orcamento.setVoluntariadoprojetovalor(voluntariadoprojeto);
		orcamento.setValor(voluntariadoprojeto.getValor());
		fornecedorcidade = voluntariadoprojeto.getVoluntariadoprojeto().getFornecedorcidade();
		Cambio cambio = consultarCambio();
		orcamento.setValorRS(voluntariadoprojeto.getValor() * cambio.getValor());
		orcamento.setNumeroSemanasAdicionais(nsemanaadicional);
		orcamento.setValorSemanaAdc(voluntariadoprojeto.getValorsemanaadicional() * nsemanaadicional);
		orcamento.setValorSemanaAdcRS(orcamento.getValorSemanaAdc() * cambio.getValor());
		orcamento.setDatainicial(datainicial);
		orcamento.setDatatermino(calcularDataTermino());
		orcamento.setCambio(cambio);
		orcamento.setValorcambio(cambio.getValor());
		if (nsemanaadicional > 0) {
			orcamento.setTotalnumerosemanas(voluntariadoprojeto.getNumerosemanainicial() + " Semana(s) + "
					+ nsemanaadicional + " Semanas Adicionais");
			orcamento.setPossuiSemanaAdicional(true);
		} else {
			orcamento.setTotalnumerosemanas(voluntariadoprojeto.getNumerosemanainicial() + " Semana(s)");
		}
		// curso
		if (voluntariadoprojeto.getVoluntariadoprojeto().getVoluntariadoprojetocursoList() != null
				&& voluntariadoprojeto.getVoluntariadoprojeto().getVoluntariadoprojetocursoList().size() > 0) {
			orcamento.setPossuiCurso(true);
			orcamento.setVoluntariadoprojetocurso(
					voluntariadoprojeto.getVoluntariadoprojeto().getVoluntariadoprojetocursoList().get(0));
		} else {
			orcamento.setPossuiCurso(false);
		}
		// acomodação
		if (voluntariadoprojeto.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList() != null
				&& voluntariadoprojeto.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList()
						.size() > 0) {
			orcamento.setPossuiAcomodacao(true);
			orcamento.setVoluntariadoprojetoacomodacao(
					voluntariadoprojeto.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList().get(0));
		} else {
			orcamento.setPossuiAcomodacao(false);
		}
		// transfer
		if (voluntariadoprojeto.getVoluntariadoprojeto().getTransfer() != null
				&& voluntariadoprojeto.getVoluntariadoprojeto().getTransfer().length() > 0) {
			orcamento.setPossuiTransfer(true);
		}
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento taxatm = produtoOrcamentoFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamento.setTaxatm(taxatm);
		orcamento.setValortaxatmRS(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		orcamento.setValortaxatm(orcamento.getValortaxatmRS() / orcamento.getValorcambio());
		orcamento.setSeguroviagem(new Seguroviagem());
		orcamento.setListaOutrosProdutos(new ArrayList<ProdutosExtrasBean>());
		orcamento.setListaDesconto(addOcursoDesconto(orcamento));
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("orcamento", orcamento);
		return "resultadoOrcamentoVoluntariado";
	}

	public Cambio consultarCambio() {
		CambioFacade cambioFacade = new CambioFacade();
		Cambio cambio = null;
		Date data = new Date();
		Fornecedorpais fornecedorPais = buscarFornecedorPais(fornecedorcidade.getFornecedor().getIdfornecedor(),
				fornecedorcidade.getCidade().getPais().getIdpais());
		int idMoeda = fornecedorcidade.getCidade().getPais().getMoedasVolutariado().getIdmoedas();
		if (fornecedorPais != null) {
			idMoeda = fornecedorPais.getPais().getMoedasVolutariado().getIdmoedas();
		}
		while (cambio == null) {
			cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(data), idMoeda);
			try {
				data = Formatacao.SomarDiasDatas(data, -1);
			} catch (Exception ex) {
				Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
		return cambio;
	}

	public Fornecedorpais buscarFornecedorPais(int idFornecedor, int idPais) {
		String sql = "SELECT f FROM Fornecedorpais f where f.fornecedor.idfornecedor=" + idFornecedor
				+ " and f.pais.idpais=" + idPais;
		FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
		return fornecedorPaisFacade.buscar(sql);
	}

	// corrigir data termino
	public Date calcularDataTermino() {
		if ((datainicial != null) && (voluntariadoprojetovalor.getNumerosemanainicial() > 0)) {
			int numerosemanas = voluntariadoprojetovalor.getNumerosemanainicial();
			if (nsemanaadicional > 0) {
				numerosemanas = numerosemanas + nsemanaadicional;
			}
			Date data = Formatacao.calcularDataFinal(datainicial, numerosemanas);
			int diaSemana = Formatacao.diaSemana(data);
			try {
				if (diaSemana == 1) {
					data = Formatacao.SomarDiasDatas(data, -2);
				} else if (diaSemana == 7) {
					data = Formatacao.SomarDiasDatas(data, -1);
				}
			} catch (Exception ex) {

			}
			return data;
		}
		return null;
	} 

	public List<Ocursodesconto> addOcursoDesconto(OrcamentoVoluntariadoBean orcamento) {
		orcamento.setListaDesconto(new ArrayList<Ocursodesconto>());
		Ocursodesconto ocursodesconto = new Ocursodesconto();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = new Produtosorcamento();
		produtosorcamento = produtoOrcamentoFacade.consultar(9);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		orcamento.getListaDesconto().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(33);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		orcamento.getListaDesconto().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(267);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		orcamento.getListaDesconto().add(ocursodesconto);
		return orcamento.getListaDesconto();
	}

	public String habilitarCalendario() {
		if (voluntariadoprojeto != null && voluntariadoprojeto.getIdvoluntariadoprojetovalor() != null) {
			return Formatacao.getSemanaIngles(voluntariadoprojeto.getVoluntariadoprojeto().getDiasemanainicial());
		}
		return "";
	}
}
