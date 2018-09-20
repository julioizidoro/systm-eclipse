package br.com.travelmate.managerBean.OrcamentoCurso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutoFornecedorBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean; 
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoseguro;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

public class EditarOrcamentoOcursoBean {

	private Ocurso ocurso;
	private List<ProdutosOrcamentoBean> listaSuplemento;
	private Date dataconsulta;
	private Cliente cliente;
	private Date datainicio;
	private AplicacaoMB aplicacaoMB;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Float valortotal;
	private List<Ocrusoprodutos> listaProdutos;
	private Ocrusoprodutos valorAcomodacao;
	private List<Ocrusoprodutos> listaTransfer;
	private List<Ocrusoprodutos> listaAcOpcional;
	private int idOcruso;
	private OCursoDescontoDao oCursoDescontoDao;
	private OCursoProdutoDao oCursoProdutoDao;
	private OcursoSeguroViagemDao ocursoSeguroViagemDao;

	public EditarOrcamentoOcursoBean(Ocurso ocurso, Cliente cliente, Date datainicio, AplicacaoMB aplicacaoMB,
			UsuarioLogadoMB usuarioLogadoMB, int idOcurso, OCursoDescontoDao orCursoDescontoDao, OCursoProdutoDao oCursoProdutoDao, OcursoSeguroViagemDao ocursoSeguroViagemDao) {
		this.cliente = cliente;
		this.datainicio = datainicio;
		this.aplicacaoMB = aplicacaoMB;
		this.usuarioLogadoMB = usuarioLogadoMB;
		this.ocurso = ocurso;
		this.idOcruso = idOcurso;
		this.oCursoDescontoDao = orCursoDescontoDao;
		this.oCursoProdutoDao = oCursoProdutoDao;
		this.ocursoSeguroViagemDao = ocursoSeguroViagemDao;
		dataconsulta = retornarDataConsultaOrcamento(); 
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public List<ProdutosOrcamentoBean> getListaSuplemento() {
		return listaSuplemento;
	}

	public void setListaSuplemento(List<ProdutosOrcamentoBean> listaSuplemento) {
		this.listaSuplemento = listaSuplemento;
	}

	public Date getDataconsulta() {
		return dataconsulta;
	}

	public void setDataconsulta(Date dataconsulta) {
		this.dataconsulta = dataconsulta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Float getValortotal() {
		return valortotal;
	}

	public void setValortotal(Float valortotal) {
		this.valortotal = valortotal;
	}

	public List<Ocrusoprodutos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Ocrusoprodutos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Ocrusoprodutos getValorAcomodacao() {
		return valorAcomodacao;
	}

	public void setValorAcomodacao(Ocrusoprodutos valorAcomodacao) {
		this.valorAcomodacao = valorAcomodacao;
	}

	public List<Ocrusoprodutos> getListaTransfer() {
		return listaTransfer;
	}   

	public void setListaTransfer(List<Ocrusoprodutos> listaTransfer) {
		this.listaTransfer = listaTransfer;
	}

	public List<Ocrusoprodutos> getListaAcOpcional() {
		return listaAcOpcional;
	}

	public void setListaAcOpcional(List<Ocrusoprodutos> listaAcOpcional) {
		this.listaAcOpcional = listaAcOpcional;
	}

	public ProdutoFornecedorBean retornarFornecedorProdutosBean() {
		ProdutoFornecedorBean produtoFornecedorBean = new ProdutoFornecedorBean();
		produtoFornecedorBean.setListaCursoPrincipal(addProdutosCurso());
		produtoFornecedorBean.setListaObrigaroerios(addProdutosObrigatorios());
		consultarAcomodacao();
		consultarTransfer();
		consultarAcOpcional();
		return produtoFornecedorBean;
	}

	public List<ProdutosOrcamentoBean> addProdutosCurso() {
		listaProdutos = oCursoProdutoDao.listar(idOcruso);
		List<ProdutosOrcamentoBean> listaCursoPrincipal = new ArrayList<>();
		for (int i = 0; i < listaProdutos.size(); i++) {
			if (listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso() != null
					&& listaProdutos.get(i).getValorcoprodutos().getProdutosuplemento().equalsIgnoreCase("valor")) {
				float valor = 0.0f;
				if (listaProdutos.get(i).getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
					if (ocurso.getNumerosemanastotal() > 0) {
						valor = ocurso.getNumerosemanastotal()
								* listaProdutos.get(i).getValorcoprodutos().getValororiginal();
					} else {
						valor = ocurso.getNumerosemanas()
								* listaProdutos.get(i).getValorcoprodutos().getValororiginal();
					}
				} else if (listaProdutos.get(i).getValorcoprodutos().getCobranca().equalsIgnoreCase("F")) {
					valor = listaProdutos.get(i).getValorcoprodutos().getValororiginal();
				} else if (listaProdutos.get(i).getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
					int multiplicador = 0;
					if (ocurso.getNumerosemanastotal() > 0) {
						multiplicador = (int) (ocurso.getNumerosemanastotal() * 7);
					} else {
						multiplicador = (int) (ocurso.getNumerosemanas() * 7);
					}
					valor = multiplicador * listaProdutos.get(i).getValorcoprodutos().getValororiginal();
				}
				ProdutosOrcamentoBean produtosOrcamentoBean = adicionarProduto(ocurso.getNumerosemanas(),
						listaProdutos.get(i).getValorcoprodutos(), valor,
						listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getNome(),
						listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", listaProdutos.get(i));
				produtosOrcamentoBean.setValorPromocional(listaProdutos.get(i).getValorpromocional());
				if (produtosOrcamentoBean.getValorPromocional() != null
						&& produtosOrcamentoBean.getValorPromocional() > 0) {
					produtosOrcamentoBean.setValorPromocionalRS(
							listaProdutos.get(i).getValorpromocional() * ocurso.getValorcambio());
					produtosOrcamentoBean.setPromocao(true);
				}
				listaCursoPrincipal.add(produtosOrcamentoBean);
				i = 1000;
			}
		}
		return listaCursoPrincipal;
	}

	public List<ProdutosOrcamentoBean> addProdutosObrigatorios() {
		List<ProdutosOrcamentoBean> listaProdutosObrigatorios = new ArrayList<>();
		for (int i = 0; i < listaProdutos.size(); i++) {
			int taxatm = aplicacaoMB.getParametrosprodutos().getTaxatmorcamento();
			if (listaProdutos.get(i).getValorcoprodutos().getIdvalorcoprodutos() == taxatm) {
				ProdutosOrcamentoBean produto = new ProdutosOrcamentoBean();
				produto.setNumeroSemanas(ocurso.getNumerosemanas());
				produto.setValorcoprodutos(listaProdutos.get(i).getValorcoprodutos());
				produto.setValorOriginalRS(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
				produto.setValorOrigianl(produto.getValorOriginalRS() / ocurso.getValorcambio());
				produto.setOcrusoprodutos(listaProdutos.get(i));
				listaProdutosObrigatorios.add(produto);
			} else if ((listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso() == null
					|| listaProdutos.get(i).getValorcoprodutos().getProdutosuplemento().equalsIgnoreCase("Curso"))
					&& listaProdutos.get(i).getNomegrupo().equalsIgnoreCase("Curso")) { 
				ProdutosOrcamentoBean produtosOrcamentoBean = adicionarProduto(ocurso.getNumerosemanas(),
						listaProdutos.get(i).getValorcoprodutos(), listaProdutos.get(i).getValororiginal(),
						listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getNome(),
						listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", listaProdutos.get(i));
				produtosOrcamentoBean.setValorPromocional(listaProdutos.get(i).getValorpromocional());
				boolean promocao = listaProdutos.get(i).isPossuipromocao();
				if (promocao) {
					produtosOrcamentoBean.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - listaProdutos.get(i).getValorpromocional());
				}else {
					produtosOrcamentoBean.setValorPromocional(listaProdutos.get(i).getValorpromocional());
				}
				if (produtosOrcamentoBean.getValorPromocional() != null
						&& produtosOrcamentoBean.getValorPromocional() > 0) {
					produtosOrcamentoBean.setValorPromocionalRS(
							listaProdutos.get(i).getValorpromocional() * ocurso.getValorcambio());
					produtosOrcamentoBean.setValorPromocional(listaProdutos.get(i).getValorpromocional());
					produtosOrcamentoBean.setPromocao(true);
				}
				produtosOrcamentoBean.setOcrusoprodutos(listaProdutos.get(i));
				listaProdutosObrigatorios.add(produtosOrcamentoBean);
			}
		}
		return listaProdutosObrigatorios;
	}
 

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataConsulta, String tipo) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(datainicio) + "' and v.datafinal>='" + Formatacao.ConvercaoDataSql(datainicio);
		if (ocurso.getNumerosemanastotal() > 0) {
			sql = sql + "' and v.numerosemanainicial<=" + ocurso.getNumerosemanastotal() + " and v.numerosemanafinal>="
					+ ocurso.getNumerosemanastotal();
		} else {
			sql = sql + "' and v.numerosemanainicial<=" + ocurso.getNumerosemanas() + " and v.numerosemanafinal>="
					+ ocurso.getNumerosemanas();
		}
		sql = sql + " and v.tipodata='" + tipoData + "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
 		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql); 
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				}
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = ocurso.getNumerosemanastotal();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = ocurso.getNumerosemanastotal() * 7;
			}
			po.setValorOrigianl(valorcoprodutos.getValororiginal() * multiplicador);
			po.setValorOriginalRS(po.getValorOrigianl() * ocurso.getValorcambio()); 
			return po;
		}
		return null;
	}

	public Date retornarDataConsultaOrcamento() {
		int anoFornecedor = ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getAnotarifario();
		Calendar c = new GregorianCalendar();
		c.setTime(datainicio);
		int ano = Formatacao.getAnoData(datainicio);
		if (anoFornecedor >= ano) {
			return datainicio;
		} else if (anoFornecedor < ano) {
			String sData = Formatacao.ConvercaoDataPadrao(datainicio);
			String dia = sData.substring(0, 2);
			String mes = sData.substring(3, 5);
			sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
			return Formatacao.ConvercaoStringDataBrasil(sData);
		}
		return datainicio;
	} 

	public ProdutosOrcamentoBean adicionarProduto(double numerosemana, Valorcoprodutos valorcoprodutos,
			float valororiginal, String nome, String descricao, int tipo, String nomegrupo, Ocrusoprodutos ocrusoprodutos) {
		ProdutosOrcamentoBean produto = new ProdutosOrcamentoBean();
		produto.setOcrusoprodutos(ocrusoprodutos);
		produto.setNumeroSemanas(numerosemana);
		produto.setValorcoprodutos(valorcoprodutos);
		produto.setValorOrigianl(valororiginal);
		produto.setValorOriginalRS(valororiginal * ocurso.getValorcambio());
		if(valorcoprodutos.getProdutosuplemento().equalsIgnoreCase("Acomodação") || valorcoprodutos.getProdutosuplemento().equalsIgnoreCase("Curso")){
			produto.getValorcoprodutos().getCoprodutos()
				.setDescricao("Suplemento de " + valorcoprodutos.getTiposuplemento() + " - "+valorcoprodutos.getProdutosuplemento());
		}
		return produto;
	}

	public List<ProdutosOrcamentoBean> gerarListaValorCoProdutos(String tipo) {
		List<ProdutosOrcamentoBean> listaRetorno = new ArrayList<ProdutosOrcamentoBean>();
		List<Coprodutos> listaCoProdutos;
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ ocurso.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma() + " and c.tipo='" + tipo
				+ "' and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) { 
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						dataconsulta, tipo);
				if (po == null) { 
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date(), tipo);
					if (po == null) { 
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta, tipo); 
					}
				}
				if(po!=null){
					for (int j = 0; j < listaProdutos.size(); j++) {
						int id = listaProdutos.get(j).getValorcoprodutos().getIdvalorcoprodutos();
						if (po.getValorcoprodutos().getIdvalorcoprodutos() == id) {
							po.setSelecionado(true);
							po.setValorOrigianl(listaProdutos.get(j).getValororiginal());
							po.setValorOriginalRS(po.getValorOrigianl() * ocurso.getValorcambio());
							po.setSomarvalortotal(listaProdutos.get(j).isSomavalortotal());
							boolean promocao = listaProdutos.get(j).isPossuipromocao();
							if (promocao) {
								po.setValorPromocional(po.getValorOrigianl() - listaProdutos.get(j).getValorpromocional());
							}else {
								po.setValorPromocional(listaProdutos.get(j).getValorpromocional());
							}
							if (po.getValorPromocional() != null && po.getValorPromocional() > 0) {
								po.setValorPromocionalRS(
										listaProdutos.get(j).getValorpromocional() * ocurso.getValorcambio());
								po.setValorPromocional(listaProdutos.get(j).getValorpromocional());
								po.setPromocao(true);
							}
						}
					}
					listaRetorno.add(po);
				} 
			}
		} 
		return listaRetorno;
	}

	public List<Ocursodesconto> addOcursoDesconto() {
		List<Ocursodesconto> lista = oCursoDescontoDao.listar(idOcruso);
		ocurso.setOcursodescontoList(new ArrayList<Ocursodesconto>());
		for (int i = 0; i < lista.size(); i++) {
			Ocursodesconto ocursodesconto;
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			Produtosorcamento produtosorcamento = new Produtosorcamento();
			if (lista.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 9) {
				ocursodesconto = new Ocursodesconto();
				produtosorcamento = produtoOrcamentoFacade.consultar(9);
				ocursodesconto.setOcurso(ocurso);
				ocursodesconto.setProdutosorcamento(produtosorcamento);
				ocursodesconto.setValormoedaestrangeira(lista.get(i).getValormoedaestrangeira());
				ocursodesconto.setValormoedanacional(lista.get(i).getValormoedanacional());
				if (lista.get(i).getValormoedaestrangeira() > 0) {
					ocursodesconto.setSelecionado(true);
				}
				ocurso.getOcursodescontoList().add(ocursodesconto);
			} else if (lista.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 33) {
				ocursodesconto = new Ocursodesconto();
				produtosorcamento = produtoOrcamentoFacade.consultar(33);
				ocursodesconto.setOcurso(ocurso);
				ocursodesconto.setProdutosorcamento(produtosorcamento);
				ocursodesconto.setValormoedaestrangeira(lista.get(i).getValormoedaestrangeira());
				ocursodesconto.setValormoedanacional(lista.get(i).getValormoedanacional());
				if (lista.get(i).getValormoedaestrangeira() > 0) {
					ocursodesconto.setSelecionado(true);
				}
				ocurso.getOcursodescontoList().add(ocursodesconto);
			} else if (lista.get(i).getProdutosorcamento().getIdprodutosOrcamento() == 267) {
				ocursodesconto = new Ocursodesconto();
				produtosorcamento = produtoOrcamentoFacade.consultar(267);
				ocursodesconto.setOcurso(ocurso);
				ocursodesconto.setProdutosorcamento(produtosorcamento);
				ocursodesconto.setValormoedaestrangeira(lista.get(i).getValormoedaestrangeira());
				ocursodesconto.setValormoedanacional(lista.get(i).getValormoedanacional());
				if (lista.get(i).getValormoedaestrangeira() > 0) {
					ocursodesconto.setSelecionado(true);
				}
				ocurso.getOcursodescontoList().add(ocursodesconto);
			}
		}
		return ocurso.getOcursodescontoList();
	}

	public void consultarAcomodacao() {
		for (int i = 0; i < listaProdutos.size(); i++) {
			if (listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao() != null) {
				if (listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipo()
						.equalsIgnoreCase("A")) {
					valorAcomodacao = listaProdutos.get(i);
				}
			}
		}
	}
	
	public void consultarAcOpcional() {
		listaAcOpcional = new ArrayList<>();
		for (int i = 0; i < listaProdutos.size(); i++) {
			if (listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getComplementoacomodacao() != null) {
				if (listaProdutos.get(i).getNomegrupo().equalsIgnoreCase("Adicionais")
						&& listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getTipo()
						.equalsIgnoreCase("AcOpcional")) {
					listaAcOpcional.add(listaProdutos.get(i));
				}
			}
		}
	}
	
	public void consultarTransfer() {
		listaTransfer = new ArrayList<>();
		for (int i = 0; i < listaProdutos.size(); i++) { 
			if (listaProdutos.get(i).getNomegrupo().equalsIgnoreCase("Transfer")) {
				listaTransfer.add(listaProdutos.get(i));
			} 
		}
	}

	public Seguroviagem buscarSeguroViagem() {
		Ocursoseguro ocursoseguro = ocursoSeguroViagemDao.consultar(idOcruso);
		Seguroviagem seguroviagem = null;
		if (ocursoseguro != null && ocursoseguro.getIdocursoseguro() != null) {
			seguroviagem = new Seguroviagem();
			seguroviagem.setValoresseguro(ocursoseguro.getValoresseguro());
			seguroviagem.setSeguradora(ocursoseguro.getSeguradora());
			seguroviagem.setDataInicio(ocursoseguro.getDatainicial());
			seguroviagem.setDataTermino(ocursoseguro.getDatafinal());
			seguroviagem.setNumeroSemanas(ocursoseguro.getNumerodias());
			seguroviagem.setValorSeguro(ocursoseguro.getValor());
			seguroviagem.setSomarvalortotal(ocursoseguro.isSomarvalortotal());
			seguroviagem.setPlano(ocursoseguro.getValoresseguro().getPlano());
			seguroviagem.setSegurocancelamento(ocursoseguro.isSegurocancelamento());
		}
		return seguroviagem;
	}

	public List<ProdutosExtrasBean> gerarListaProdutosExtras() {
		List<ProdutosExtrasBean> lista = new ArrayList<>(); 
		if (listaProdutos != null) {
			for (int i = 0; i < listaProdutos.size(); i++) {
				if(listaProdutos.get(i).getNomegrupo().equalsIgnoreCase("Adicionais")
					&& listaProdutos.get(i).getValorcoprodutos().getIdvalorcoprodutos()==aplicacaoMB.getParametrosprodutos().getProdutoextra()){
					Ocrusoprodutos produto = listaProdutos.get(i);
					ProdutosExtrasBean po = new ProdutosExtrasBean();
					po.setProdutosorcamento(produto.getValorcoprodutos().getCoprodutos().getProdutosorcamento());
					po.setDescricao(produto.getDescricao());
					po.setValorcoprodutos(produto.getValorcoprodutos());
					po.setValorOrigianl(produto.getValororiginal());
					po.setValorOriginalRS(po.getValorOrigianl() * ocurso.getValorcambio());
					po.setValorOrigianlString(ocurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(po.getValorOrigianl()));
					po.setValorOriginalRSString("R$ " + Formatacao.formatarFloatString(po.getValorOriginalRS()));
					po.setSomarvalortotal(produto.isSomavalortotal());
					lista.add(po);
				}
			}
		}
		return lista;
	}
}
