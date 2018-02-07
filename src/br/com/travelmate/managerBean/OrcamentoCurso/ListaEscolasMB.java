/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct; 
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

	public String orcamentoResultado(ProdutoFornecedorBean produtoFornecedorBean) {
		if (produtoFornecedorBean != null) {
			int ano = Formatacao.getAnoData(new Date());
			if(ano<=produtoFornecedorBean.getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getAnotarifario()){ 
				int index = produtoFornecedorBean.getLinhaFornecedor();
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
