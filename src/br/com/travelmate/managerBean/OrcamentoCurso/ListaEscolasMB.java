/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.FornecedorFeriasFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorferias;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class ListaEscolasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Parametrosprodutos parametrosprodutos;
	private String possuiPromocao;
	private FiltrarEscolaBean FiltrarEscolaBean; 
	private List<ProdutosOrcamentoBean> listaSuplemento;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		FiltrarEscolaBean = (FiltrarEscolaBean) session.getAttribute("filtrarEscolaBean");
		for (int i=0;i<FiltrarEscolaBean.getListaFornecedorProdutosBean().size();i++) {
			if (FiltrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().size()==0) {
				FiltrarEscolaBean.getListaFornecedorProdutosBean().remove(i);
			}else {
				System.out.println(i);
			}
		}
	}

	public FiltrarEscolaBean getFiltrarEscolaBean() {
		return FiltrarEscolaBean;
	}

	public void setFiltrarEscolaBean(FiltrarEscolaBean filtrarEscolaBean) {
		FiltrarEscolaBean = filtrarEscolaBean;
	}

	public Parametrosprodutos getParametrosprodutos() {
		return parametrosprodutos;
	}

	public void setParametrosprodutos(Parametrosprodutos parametrosprodutos) {
		this.parametrosprodutos = parametrosprodutos;
	}

	public String getPossuiPromocao() {
		return possuiPromocao;
	}

	public void setPossuiPromocao(String possuiPromocao) {
		this.possuiPromocao = possuiPromocao;
	}
	
	public List<Coprodutos> verificarCursoPossuiValor(Coprodutos listaCoProdutos,
			FornecedorProdutosBean fornecedorProdutosBean) {
		boolean encontrou = false;
		Date dataInicioTarifario = fornecedorProdutosBean.getDataConsulta();
		List<Coprodutos> novaLista = new ArrayList<Coprodutos>();
		//for (int i = 0; i < listaCoProdutos.size(); i++) {
			encontrou = false;
			String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
					+ listaCoProdutos.getIdcoprodutos() + " and g.produto.idcoprodutos="
					+ listaCoProdutos.getIdcoprodutos();
			GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
			FiltrarEscolaBean.setListaGrupoObrigatorio(grupoObrigatorioFacade.listar(sql));
			if (FiltrarEscolaBean.getListaGrupoObrigatorio() != null) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.getIdcoprodutos(),
						fornecedorProdutosBean, dataInicioTarifario, "Obrigatorio");
				if (po != null) {
					encontrou = true;
				} else {
					po = consultarValores("DM", listaCoProdutos.getIdcoprodutos(), fornecedorProdutosBean,
							new Date(), "Obrigatorio");
					if (po != null) {
						encontrou = true;
					} else {
						po = consultarValores("DS", listaCoProdutos.getIdcoprodutos(), fornecedorProdutosBean,
								dataInicioTarifario, "Obrigatorio");
						if (po != null) {
							encontrou = true;
						}
					}
				}
			}
			if (encontrou) {
				novaLista.add(listaCoProdutos);
			}
		//}
		return novaLista;

	}
	
	
	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos,
			FornecedorProdutosBean fornecedorProdutosBean, Date dataConsulta, String tipo) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		String sqlSuplemento = "Select v from  Valorcoprodutos v where v.datainicial<='" + Formatacao.ConvercaoDataSql(dataConsulta)  + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataConsulta)
				+ "' and v.numerosemanainicial<=" + FiltrarEscolaBean.getOcurso().getNumerosemanas()
				+ " and v.numerosemanafinal>=" + FiltrarEscolaBean.getOcurso().getNumerosemanas() + " and v.tipodata='"
				+ tipoData + "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
		List<Valorcoprodutos> listaValorcoprodutosSuplemento = valorCoProdutosFacade.listar(sqlSuplemento);
		if (listaValorcoprodutosSuplemento == null) {
			Date dataTermino = calcularDataTerminoCurso(dataConsulta, FiltrarEscolaBean.getOcurso().getNumerosemanas());
			sqlSuplemento = "Select v from  Valorcoprodutos v where v.datainicial>='"
					+ Formatacao.ConvercaoDataSql(dataConsulta) + "'" + " and v.datainicial<='"
					+ Formatacao.ConvercaoDataSql(dataTermino) + "'" + " and v.datafinal>='"
					+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.numerosemanainicial<="
					+ FiltrarEscolaBean.getOcurso().getNumerosemanas() + " and v.numerosemanafinal>="
					+ FiltrarEscolaBean.getOcurso().getNumerosemanas() + " and v.tipodata='" + tipoData
					+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
			listaValorcoprodutosSuplemento = valorCoProdutosFacade.listar(sqlSuplemento);
		}
		if (listaValorcoprodutosSuplemento != null) {
			for (int n = 0; n < listaValorcoprodutosSuplemento.size(); n++) {
				if (listaValorcoprodutosSuplemento.get(n).getProdutosuplemento().equalsIgnoreCase("Curso")) {
					listaValorcoprodutosSuplemento.get(n).getCoprodutos().getProdutosorcamento().setDescricao(
							listaValorcoprodutosSuplemento.get(n).getCoprodutos().getProdutosorcamento().getDescricao()+" - Curso");
					Valorcoprodutos valorSuplemente = listaValorcoprodutosSuplemento.get(n);
					listarValores(valorSuplemente, fornecedorProdutosBean, tipo);
				}
			}  
		}
		return null;
	}
	
	
	public void listarValores(Valorcoprodutos valorcoprodutos, FornecedorProdutosBean fornecedorProdutosBean,
			String tipo) {
		if (listaSuplemento == null) {
			if (valorcoprodutos != null) {
				ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
				po.setValorcoprodutos(valorcoprodutos);
				po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
				int multiplicador = 1;
				if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
					multiplicador = fornecedorProdutosBean.getOcurso().getNumerosemanas();
				} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
					multiplicador = fornecedorProdutosBean.getOcurso().getNumerosemanas() * 7;
				} 
				float valorSuplemento = calcularValorFracionadoSuplemento
					(multiplicador, fornecedorProdutosBean, po, FiltrarEscolaBean.getOcurso().getDatainicio());
				if(valorSuplemento<=0){
					valorSuplemento = calcularValorFracionadoSuplemento
							(multiplicador, fornecedorProdutosBean, po, fornecedorProdutosBean.getDataConsulta());
				}
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * fornecedorProdutosBean.getCambio().getValor());
				if (listaSuplemento == null) {
					listaSuplemento = new ArrayList<ProdutosOrcamentoBean>();
				}
				po.getValorcoprodutos().getCoprodutos()
						.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento() + " - Curso");
				if(valorSuplemento>0){
					listaSuplemento.add(po);
				}
			}
		}
	}
	
	
	public float calcularValorFracionadoSuplemento(int multiplicador, FornecedorProdutosBean fornecedorProdutosBean,
			ProdutosOrcamentoBean po, Date dataInical) {
		float valorSuplemento = 0.0f; 
		Date dataTermino = calcularDataTerminoCurso(dataInical, FiltrarEscolaBean.getOcurso().getNumerosemanas());
		int numeroDias = 0;
		boolean calcular = true;
//		if ((po.getValorcoprodutos().getDatainicial().after(dataInical) && po.getValorcoprodutos().getDatainicial().after(dataTermino)) ||
//				(po.getValorcoprodutos().getDatafinal().before(dataInical) && po.getValorcoprodutos().getDatafinal().before(dataTermino))){
//			calcular = false;
//		}   
//		if (calcular) {
		if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
				|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;

		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;

		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino))) {

			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), po.getValorcoprodutos().getDatafinal());
		} else if ((po.getValorcoprodutos().getDatainicial().before(dataTermino))
				&& (po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);

		} else if ((po.getValorcoprodutos().getDatainicial().before(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			if (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical))) {
				numeroDias = 1;
			}else {
				numeroDias = Formatacao.subtrairDatas(dataInical, po.getValorcoprodutos().getDatafinal());
			}
		}else if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);
		
		}else{
			valorSuplemento = -1;   
			numeroDias = 0;
		}
		if ((valorSuplemento == 0) && (numeroDias > 0)) {
			if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				int resto = (numeroDias % 7);
				numeroDias = numeroDias / 7;
				if (resto > 0) {
					numeroDias = numeroDias + 1;
				}
			}
			valorSuplemento = po.getValorcoprodutos().getValororiginal();
			valorSuplemento = valorSuplemento * numeroDias;
		}
		if (valorSuplemento < 0) {
			valorSuplemento = 0;
		}
		return valorSuplemento;
	}
	
	public List<ProdutosOrcamentoBean> gerarListaValorCoProdutos(
			String tipo) {
		List<ProdutosOrcamentoBean> listaRetorno = new ArrayList<ProdutosOrcamentoBean>();
		if (tipo.equalsIgnoreCase("obrigatorio")) {
			if (listaSuplemento != null) {
				for (int i = 0; i < listaSuplemento.size(); i++) {
					listaRetorno.add(listaSuplemento.get(i));
				}
				listaSuplemento = null;
			}
		}
		return listaRetorno;
	}

	public String orcamentoResultado(ProdutoFornecedorBean produtoFornecedorBean) {
		if (produtoFornecedorBean != null) {
			int ano = Formatacao.getAnoData(new Date());
			if(ano<=produtoFornecedorBean.getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getAnotarifario()){ 
				int index = produtoFornecedorBean.getLinhaFornecedor();
//				if (produtoFornecedorBean.getCoprodutos().isPacote() == false) {
//					gerarPromocaoTaxas(produtoFornecedorBean.getListaCursoPrincipal(),
//							produtoFornecedorBean.getListaObrigaroerios());
//					int nsemanas = FiltrarEscolaBean.getOcurso().getNumerosemanas();
//					gerarPromocaoBrindes(produtoFornecedorBean.getListaCursoPrincipal(),
//							produtoFornecedorBean.getListaObrigaroerios(), nsemanas, produtoFornecedorBean.get);
//				}
				Integer linhaFornecedorBean = produtoFornecedorBean.getLinhaFornecedorProdutoBean();
				verificarCursoPossuiValor(produtoFornecedorBean.getCoprodutos(), FiltrarEscolaBean.getListaFornecedorProdutosBean().get(linhaFornecedorBean));
				List<ProdutosOrcamentoBean> listaValoresObriatorio = new ArrayList<ProdutosOrcamentoBean>();
				listaValoresObriatorio = produtoFornecedorBean.getListaObriSalvo();
				produtoFornecedorBean.setListaObrigaroerios(gerarListaValorCoProdutos("Obrigatorio"));
				if (listaValoresObriatorio != null && listaValoresObriatorio.size() > 0 && produtoFornecedorBean.getListaObrigaroerios() != null) {
					ProdutosOrcamentoBean produtosOrcamentoBean = new ProdutosOrcamentoBean();
					for (int i = 0; i < listaValoresObriatorio.size(); i++) {
						produtosOrcamentoBean = listaValoresObriatorio.get(i);
						produtoFornecedorBean.getListaObrigaroerios().add(produtosOrcamentoBean);
					}
				}
				ResultadoOrcamentoBean resultadoOrcamentoBean = new ResultadoOrcamentoBean();
				resultadoOrcamentoBean.setCambio(FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getCambio());
				resultadoOrcamentoBean.setFornecedorcidadeidioma(
						FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getFornecedorcidadeidioma());
				resultadoOrcamentoBean.setListaAcomodacoes(
						FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getListaAcomodacoes());
				resultadoOrcamentoBean.setListaAcOpcional(
						FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getListaAcOpcional());
				resultadoOrcamentoBean.setListaOpcionais(
						FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getListaOpcionais());
				resultadoOrcamentoBean
						.setListaTransfer(FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getListaTransfer());
				resultadoOrcamentoBean.setOcurso(FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getOcurso());
				resultadoOrcamentoBean.setProdutoFornecedorBean(produtoFornecedorBean);
				resultadoOrcamentoBean.setListaFornecedorProdutosBean(FiltrarEscolaBean.getListaFornecedorProdutosBean());
				resultadoOrcamentoBean.getOcurso().setValorcambio(resultadoOrcamentoBean.getCambio().getValor());
				resultadoOrcamentoBean.setDataConsulta(FiltrarEscolaBean.getListaFornecedorProdutosBean().get(index).getDataConsulta());
				resultadoOrcamentoBean.setClientelead(FiltrarEscolaBean.isClientelead());
				resultadoOrcamentoBean.setLead(FiltrarEscolaBean.getLead());
				resultadoOrcamentoBean.setCodigo(FiltrarEscolaBean.getCodigo());
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
				return "orcamentoCurso"; 
			}else{
				Map<String, Object> options = new HashMap<String, Object>();
				options.put("contentWidth", 230);
				RequestContext.getCurrentInstance().openDialog("mensagemerro", options, null); 
			}
		} else {
			Mensagem.lancarMensagemErro("Erro!", "Fornecedor não encontrado."); 
		}
		return ""; 
	}
	
	public void gerarPromocaoTaxas(List<ProdutosOrcamentoBean> curso, List<ProdutosOrcamentoBean> listaObrigatorios) {
		String sql = "select p From Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ FiltrarEscolaBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ FiltrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaotaxacurso.idpromocaotaxacurso";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaPromocaotaxacidade = promocaoTaxaCidadeFacade.listar(sql);
		if (listaPromocaotaxacidade != null) {
			for (int j = 0; j < listaPromocaotaxacidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = null;
				int idproduto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getProdutosorcamento()
						.getIdprodutosOrcamento();
				int posicao = 0;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
							.getIdprodutosOrcamento() == idproduto) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1005;
					}
				}
				if (listaPromocaotaxacidade.get(j) != null && valorcoprodutos != null) {
					FiltrarEscolaBean.getOcurso().setTurno(
							curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getTurno());
					boolean tempromocao = verificarPromocaoTaxasValida(curso,
							listaPromocaotaxacidade.get(j).getPromocaotaxacurso(), valorcoprodutos);
					if (tempromocao) {
						FiltrarEscolaBean.setCodigo(listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getCodigo());
						if (FiltrarEscolaBean.getOcurso().getDatavalidade() == null) {
							FiltrarEscolaBean.getOcurso().setDatavalidade(
									listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getDatavalidadefinal());
						} else if (FiltrarEscolaBean.getOcurso().getDatavalidade() != null
								&& FiltrarEscolaBean.getOcurso().getDatavalidade().after(
										listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getDatavalidadefinal())) {
							FiltrarEscolaBean.getOcurso().setDatavalidade(
									listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getDatavalidadefinal());
						}
						float valordesconto = 0.0f;
						if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = FiltrarEscolaBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = FiltrarEscolaBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = (valorcoprodutos.getValororiginal() * multiplicador)
										* (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
												/ 100);
							}
						} else if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = FiltrarEscolaBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = FiltrarEscolaBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
										* multiplicador;
							}
						}
						if (valordesconto > 0) {
							listaObrigatorios.get(posicao).setDescricaopromocao(
									descricaoPromocaoTaxas(listaPromocaotaxacidade.get(j).getPromocaotaxacurso()));
							listaObrigatorios.get(posicao).setValorPromocional(
									listaObrigatorios.get(posicao).getValorOrigianl() - valordesconto);
							listaObrigatorios.get(posicao)
									.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional()
											* FiltrarEscolaBean.getOcurso().getCambio().getValor());
							listaObrigatorios.get(posicao).setPromocao(true);
						}
					}
				}
			}
		}
	}
	
	public String descricaoPromocaoTaxas(Promocaotaxacurso promocaotaxacurso) {
		String descricao = "";
		if (promocaotaxacurso.getAcomodacaoselecionada()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaotaxacurso.getCargahoraria() != null && promocaotaxacurso.getCargahoraria() > 0
				&& promocaotaxacurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaotaxacurso.getCargahoraria() + " "
					+ promocaotaxacurso.getTipocargahoraria() + " | ";
		}
		if (promocaotaxacurso.getDataacomodacaoinicial() != null
				&& promocaotaxacurso.getDatafinalacomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatafinalacomodacao()) + " | ";
		}
		if (promocaotaxacurso.getDatainiciocursoinicial() != null
				&& promocaotaxacurso.getDatainiciocursofinal() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursofinal()) + " | ";
		}
		if (promocaotaxacurso.getDatamatriculaenciadaate() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatamatriculaenciadaate()) + " | ";
		}
		if (promocaotaxacurso.getDataterminio() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataterminio()) + " | ";
		}
		if ((promocaotaxacurso.getDatavalidadefinal() != null)
				&& (promocaotaxacurso.getDatavalidadeinicial() != null)) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadeinicial()) + " a "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaotaxacurso.getNumerosemanafinal() != null && promocaotaxacurso.getNumerosemanasinicial() != null
				&& promocaotaxacurso.getNumerosemanasinicial() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaotaxacurso.getNumerosemanasinicial() + " até "
					+ promocaotaxacurso.getNumerosemanafinal() + " | ";
		}
		if (promocaotaxacurso.getTurno() != null && promocaotaxacurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaotaxacurso.getTurno() + " | ";
		}
		if (promocaotaxacurso.getValorporsemana() != null && promocaotaxacurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaotaxacurso.getValorporsemana()) + " | ";
		}

		if (promocaotaxacurso.getTipodesconto() != null) {
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + "% | ";
			}
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + " | ";
			}
		}
		return descricao;
	}
	
	public boolean verificarPromocaoTaxasValida(List<ProdutosOrcamentoBean> curso, Promocaotaxacurso promocao,
			Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainiciocursoinicial() != null && promocao.getDatainiciocursofinal() != null) {
			if ((FiltrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDatainiciocursoinicial())
					|| FiltrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatainiciocursoinicial()))
					&& (FiltrarEscolaBean.getOcurso().getDatainicio().before(promocao.getDatainiciocursofinal())
							|| FiltrarEscolaBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainiciocursofinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDatafinalacomodacao() != null) {
			if ((FiltrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDataacomodacaoinicial())
					|| FiltrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDataacomodacaoinicial()))
					&& (FiltrarEscolaBean.getOcurso().getDatatermino().before(promocao.getDatafinalacomodacao())
							|| FiltrarEscolaBean.getOcurso().getDatatermino()
									.equals(promocao.getDatafinalacomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getAcomodacaoselecionada()) {
			return false;
		}
		if (promocao.getDatamatriculaenciadaate() != null) {
			String datatexto = Formatacao.ConvercaoDataPadrao(new Date());
			Date data = Formatacao.ConvercaoStringData(datatexto);
			if (data.before(promocao.getDatamatriculaenciadaate())
					|| data.equals(promocao.getDatamatriculaenciadaate())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminio() != null) {
			if (FiltrarEscolaBean.getOcurso().getDatatermino().before(promocao.getDataterminio())
					|| FiltrarEscolaBean.getOcurso().getDatatermino().equals(promocao.getDataterminio())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanasinicial() != null && promocao.getNumerosemanasinicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (FiltrarEscolaBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanasinicial()
					&& FiltrarEscolaBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (FiltrarEscolaBean.getOcurso().getTurno().equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer.parseInt(
					curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && curso.get(0).getValorcoprodutos().getCoprodutos()
					.getComplementocurso().getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;

	}
	

	
	public void gerarPromocaoBrindes(List<ProdutosOrcamentoBean> listaCurso,
			List<ProdutosOrcamentoBean> listaObrigatorio, int numeroSemanasCurso, FornecedorProdutosBean fpb) {
		if (listaCurso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso()!= null) {
			FiltrarEscolaBean.getOcurso()
				.setTurno(listaCurso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getTurno());
		}
		String sql = "select p From Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ FiltrarEscolaBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ FiltrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaobrindecurso.idpromocaobrindecurso";  
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listaPromocaoBrindeCursoCidade = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listaPromocaoBrindeCursoCidade != null) {
			for (int j = 0; j < listaPromocaoBrindeCursoCidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = listaCurso.get(0).getValorcoprodutos();
				if (listaPromocaoBrindeCursoCidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoBrindesValido(listaCurso,
							listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso(), valorcoprodutos);
					if (tempromocao) {
						FiltrarEscolaBean.setCodigo(listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getCodigo());
						if (FiltrarEscolaBean.getOcurso().getDatavalidade() == null) {
							FiltrarEscolaBean.getOcurso().setDatavalidade(listaPromocaoBrindeCursoCidade.get(j)
									.getPromocaobrindecurso().getDatavalidadefinal());
						} else if (FiltrarEscolaBean.getOcurso().getDatavalidade() != null
								&& FiltrarEscolaBean.getOcurso().getDatavalidade().after(listaPromocaoBrindeCursoCidade
										.get(j).getPromocaobrindecurso().getDatavalidadefinal())) {
							FiltrarEscolaBean.getOcurso().setDatavalidade(listaPromocaoBrindeCursoCidade.get(j)
									.getPromocaobrindecurso().getDatavalidadefinal());
						}
						if (listaCurso.get(0).getDescricaobrinde() == null) {
							float valordesconto = 0.0f;
							if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getGanhasemana() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhasemana() > 0) {
								int numeroSemana = 0;
								if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getNumerosemanacurso() > 0) {
									if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getNumerosemanacurso() == numeroSemanasCurso) {
										numeroSemana = listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhasemana();
									} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getNumerosemanacurso() < numeroSemanasCurso) {
										numeroSemana = (int) numeroSemanasCurso / listaPromocaoBrindeCursoCidade.get(j)
												.getPromocaobrindecurso().getNumerosemanacurso();
										if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhasemana() >= 1) {
											numeroSemana = numeroSemana * listaPromocaoBrindeCursoCidade.get(j)
													.getPromocaobrindecurso().getGanhasemana();
										}
									}
								}
								listaCurso.get(0).setDescricaobrinde("Matricule-se até o dia "
										+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
												.getPromocaobrindecurso().getDatamatricula())
										+ " compre " + numeroSemanasCurso + " semanas e ganhe " + numeroSemana
										+ " semanas FREE.");
								listaCurso.get(0).setPromocao(true);
								fpb.getOcurso().setNumerosemanasbrinde(numeroSemana);
								int nsemanastotal=numeroSemanasCurso+numeroSemana;
								Date datatermino = calcularDataTerminoCurso
										(fpb.getOcurso().getDatainicio(), nsemanastotal);
								fpb.getOcurso().setDatatermino(datatermino);
								listaCurso.get(0).setPromocao(true);
								int idtaxatm = FiltrarEscolaBean.getTaxaTM().getIdproduto();
								for (int i = 0; i < listaObrigatorio.size(); i++) {
									if (idtaxatm != listaObrigatorio.get(i).getIdproduto()) {
										ProdutosOrcamentoBean po = listaObrigatorio.get(i);
										int multiplicador = 1;
										if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
											multiplicador = FiltrarEscolaBean.getOcurso().getNumerosemanas();
										} else if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
											multiplicador = FiltrarEscolaBean.getOcurso().getNumerosemanas() * 7;
										}
										po.setValorOrigianl(po.getValorcoprodutos().getValororiginal() * multiplicador);
										po.setValorOriginalRS(po.getValorOrigianl()
												* FiltrarEscolaBean.getOcurso().getCambio().getValor());
									}
								}

							} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescontosemana() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemana() > 0) {
								valordesconto = valorcoprodutos.getValororiginal() * listaPromocaoBrindeCursoCidade
										.get(j).getPromocaobrindecurso().getGanhadescontosemana();
								int numeroSemanas = FiltrarEscolaBean.getOcurso().getNumerosemanas()
										- listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhadescontosemana();
								listaCurso.get(0)
										.setDescricaobrinde("Matricule-se até o dia "
												+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
														.getPromocaobrindecurso().getDatamatricula())
												+ " pague " + numeroSemanas + " semanas e curse "
												+ FiltrarEscolaBean.getOcurso().getNumerosemanas() + ".");
								listaCurso.get(0).setPromocao(true);
							} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescricao() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescricao().length() > 2) {
								listaCurso.get(0).setDescricaobrinde(listaPromocaoBrindeCursoCidade.get(j)
										.getPromocaobrindecurso().getGanhadescricao());
								listaCurso.get(0).setPromocao(true);
							}
							listaCurso.get(0).setDescricaopromocao(
									listaCurso.get(0).getDescricaopromocao() + "  " + descricaoPromocaoBrinde(
											listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()));
							if (listaCurso.get(0).getValorPromocional() == null
									|| listaCurso.get(0).getValorPromocional() == 0) {
								listaCurso.get(0)
										.setValorPromocional(listaCurso.get(0).getValorOrigianl() - valordesconto);
								listaCurso.get(0).setValorPromocionalRS(listaCurso.get(0).getValorPromocional()
										* FiltrarEscolaBean.getOcurso().getCambio().getValor());
							} else {
								listaCurso.get(0)
										.setValorPromocional(listaCurso.get(0).getValorPromocional() - valordesconto);
								listaCurso.get(0).setValorPromocionalRS(listaCurso.get(0).getValorPromocional()
										* FiltrarEscolaBean.getOcurso().getCambio().getValor());
							}
						}
					}
				}
			}
		}

	}
	
	
	public boolean verificarPromocaoBrindesValido(List<ProdutosOrcamentoBean> listaCurso, Promocaobrindecurso promocao,
			Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainicio() != null && promocao.getDatafinal() != null) {
			if ((FiltrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDatainicio())
					|| FiltrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatainicio()))
					&& (FiltrarEscolaBean.getOcurso().getDatainicio().before(promocao.getDatafinal())
							|| FiltrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatafinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDataacomodacaofinal() != null) { 
			return false; 
		}
		if (promocao.getDatamatricula() != null) {
			String datatexto = Formatacao.ConvercaoDataPadrao(new Date());
			Date data = Formatacao.ConvercaoStringData(datatexto);
			if (data.before(promocao.getDatamatricula())
					|| data.equals(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicial() != null && promocao.getNumerosemanainicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (FiltrarEscolaBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanainicial()
					&& FiltrarEscolaBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanacurso() != null && promocao.getNumerosemanacurso() > 0) {
			tempromocao = true;
		}
		if (promocao.getNumerosemanaacomodacao() != null && promocao.getNumerosemanaacomodacao() > 0) {
			tempromocao = true;
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (FiltrarEscolaBean.getOcurso().getTurno().equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer.parseInt(
					listaCurso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && listaCurso.get(0).getValorcoprodutos().getCoprodutos()
					.getComplementocurso().getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;

	}
	
	
	public Date calcularDataTerminoCurso(Date dataInical, int numeroSemanas) {
		if ((dataInical != null) && (numeroSemanas > 0)) {
			if (numeroSemanas > 0) {
				Date data = Formatacao.calcularDataFinal(dataInical, numeroSemanas);
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				String sql = "Select f from Fornecedorferias f where f.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataInical) + "' and f.datafinal<='"
						+ Formatacao.ConvercaoDataSql(data) + "'";
				FornecedorFeriasFacade fornecedorFeriasFacade = new FornecedorFeriasFacade();
				List<Fornecedorferias> listaFornecedorferias = fornecedorFeriasFacade.listar(sql);
				if (listaFornecedorferias == null) {
					listaFornecedorferias = new ArrayList<Fornecedorferias>();
				}
				if (listaFornecedorferias.size() > 0) {
					try {
						data = Formatacao.SomarDiasDatas(data, listaFornecedorferias.get(0).getNumerodias());
					} catch (Exception ex) {
						Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
				return data;
			}
		}
		return null;
	}
	
	public String descricaoPromocaoBrinde(Promocaobrindecurso promocaobrindecurso) {
		String descricao = "";
		if (promocaobrindecurso.getDatavalidadeinicial() != null
				&& promocaobrindecurso.getDatavalidadeinicial() != null) {
			descricao = descricao + "Período de validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadeinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaobrindecurso.getNumerosemanainicial() != null && promocaobrindecurso.getNumerosemanafinal() != null
				&& promocaobrindecurso.getNumerosemanainicial() > 0 && promocaobrindecurso.getNumerosemanafinal() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaobrindecurso.getNumerosemanainicial() + " até "
					+ promocaobrindecurso.getNumerosemanafinal() + " | ";
		}
		if (promocaobrindecurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatamatricula()) + " | ";
		}
		if ((promocaobrindecurso.getDataacomodacaoinicial() != null)
				&& (promocaobrindecurso.getDataacomodacaofinal() != null)) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaofinal()) + " | ";
		}
		if ((promocaobrindecurso.getDatainicio() != null) && (promocaobrindecurso.getDatafinal() != null)) {
			descricao = descricao + "Data início do curso entre: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatainicio()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatafinal()) + " | ";
		}
		if (promocaobrindecurso.getValorporsemana() != null && promocaobrindecurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaobrindecurso.getValorporsemana()) + " | ";
		}
		if (promocaobrindecurso.getTurno() != null && promocaobrindecurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaobrindecurso.getTurno() + " | ";
		}
		if (promocaobrindecurso.getNumerosemanacurso() != null && promocaobrindecurso.getNumerosemanacurso() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanacurso() + " semana(s) curso | ";
		}
		if (promocaobrindecurso.getNumerosemanaacomodacao() != null
				&& promocaobrindecurso.getNumerosemanaacomodacao() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanaacomodacao()
					+ " semana(s) acomodação  | ";
		}
		if (promocaobrindecurso.getGanhasemana() != null && promocaobrindecurso.getGanhasemana() > 0) {
			descricao = descricao + "Ganha: " + promocaobrindecurso.getGanhasemana() + " semana(s) de curso ";
		}
		if (promocaobrindecurso.getGanhadescontosemana() != null && promocaobrindecurso.getGanhadescontosemana() > 0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemana()
					+ " semana(s) de curso ";
		}
		if (promocaobrindecurso.getGanhadescontosemanaacomodacao() != null
				&& promocaobrindecurso.getGanhadescontosemanaacomodacao() > 0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemanaacomodacao()
					+ " semana(s) de acomodação ";
		}
		if (promocaobrindecurso.getGanhadescricao() != null && promocaobrindecurso.getGanhadescricao().length() > 2) {
			descricao = descricao + "Brinde: " + promocaobrindecurso.getGanhadescricao();
		}
		return descricao;
	}

	public String voltar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", FiltrarEscolaBean.getLead());
		session.setAttribute("cliente", FiltrarEscolaBean.getOcurso().getCliente());
		return "filtrarOrcamentoCurso";
	}

	public String possuiPromocao(ProdutoFornecedorBean produtoFornecedorBean) {
		String retorno = "";
		if (produtoFornecedorBean != null && produtoFornecedorBean.getCoprodutos()!=null 
				&& produtoFornecedorBean.getListaCursoPrincipal()!=null && produtoFornecedorBean.getListaCursoPrincipal().size()>0) {
			if (produtoFornecedorBean.getListaCursoPrincipal().get(0).getValorPromocional() != null) {
				if (produtoFornecedorBean.getListaCursoPrincipal().get(0).getValorPromocional() > 0) {
					retorno = "Possui Promoção!";
				}
			}
		}
		possuiPromocao = retorno;
		return possuiPromocao;
	}
	
	
	public boolean renderedPromocao(ProdutoFornecedorBean produtoFornecedorBean) {
		if (produtoFornecedorBean != null && produtoFornecedorBean.getCoprodutos()!=null 
				&& produtoFornecedorBean.getListaCursoPrincipal()!=null && produtoFornecedorBean.getListaCursoPrincipal().size()>0) {
			if (produtoFornecedorBean.getListaCursoPrincipal().get(0).getValorPromocional() != null) {
				if (produtoFornecedorBean.getListaCursoPrincipal().get(0).getValorPromocional() > 0) {
					return true;
				}
			}
		}
		return false;
	}
	 
	public String descricaopromocao(ProdutoFornecedorBean produtoFornecedorBean) {
		if (produtoFornecedorBean != null && produtoFornecedorBean.getCoprodutos()!=null 
				&& produtoFornecedorBean.getListaCursoPrincipal()!=null && produtoFornecedorBean.getListaCursoPrincipal().size()>0) {
			if (produtoFornecedorBean.getListaCursoPrincipal().get(0).getDescricaopromocao() != null) {
				return produtoFornecedorBean.getListaCursoPrincipal().get(0).getDescricaopromocao();
			}
		}
		return "";
	}

}
