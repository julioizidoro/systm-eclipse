package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FornecedorApplicationFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Fornecedorapplication;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class ApplicationCursoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedorcidadeidioma fornecedorcidadeidioma;
	private List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma;
	private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;
	private List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto;
	private Pais pais;
	private List<Pais> listaPais;
	private Produtosorcamento produtosorcamento;
	private List<Produtosorcamento> listaProdutosOrcamento;
	private Fornecedorapplication fornecedorapplication;
	private List<Fornecedorapplication> listaFornecedor;
	private boolean desabilitarUpload = true;
	
	
	@PostConstruct
	public void init() {
		gerarListaPais();
		listaFornecedor = new ArrayList<Fornecedorapplication>();
	}
	
	
	
	
	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}




	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}




	public List<Fornecedorcidadeidioma> getListaFornecedorCidadeIdioma() {
		return listaFornecedorCidadeIdioma;
	}




	public void setListaFornecedorCidadeIdioma(List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma) {
		this.listaFornecedorCidadeIdioma = listaFornecedorCidadeIdioma;
	}




	public Fornecedorcidadeidiomaproduto getFornecedorcidadeidiomaproduto() {
		return fornecedorcidadeidiomaproduto;
	}




	public void setFornecedorcidadeidiomaproduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
	}




	public List<Fornecedorcidadeidiomaproduto> getListaFornecedorCidadeIdiomaProduto() {
		return listaFornecedorCidadeIdiomaProduto;
	}




	public void setListaFornecedorCidadeIdiomaProduto(
			List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto) {
		this.listaFornecedorCidadeIdiomaProduto = listaFornecedorCidadeIdiomaProduto;
	}




	public Pais getPais() {
		return pais;
	}




	public void setPais(Pais pais) {
		this.pais = pais;
	}




	public List<Pais> getListaPais() {
		return listaPais;
	}




	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}




	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}




	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}




	public List<Produtosorcamento> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}




	public void setListaProdutosOrcamento(List<Produtosorcamento> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}




	public Fornecedorapplication getFornecedorapplication() {
		return fornecedorapplication;
	}




	public void setFornecedorapplication(Fornecedorapplication fornecedorapplication) {
		this.fornecedorapplication = fornecedorapplication;
	}




	public List<Fornecedorapplication> getListaFornecedor() {
		return listaFornecedor;
	}




	public void setListaFornecedor(List<Fornecedorapplication> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}




	public boolean isDesabilitarUpload() {
		return desabilitarUpload;
	}




	public void setDesabilitarUpload(boolean desabilitarUpload) {
		this.desabilitarUpload = desabilitarUpload;
	}




	public void gerarListaFornecedorCidade() {
			String sql = null;
			sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.pais.idpais="
					+ pais.getIdpais()
					+ " and f.fornecedorcidade.fornecedor.habilitarorcamento=true"
					+ " and f.acomodacaoindependente=FALSE"
					+ " and f.habilitada=true and f.fornecedorcidade.produtos.idprodutos=1 group by f.fornecedorcidade.fornecedor.idfornecedor order by f.fornecedorcidade.fornecedor.nome";
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			listaFornecedorCidadeIdioma = fornecedorCidadeIdiomaFacade.listar(sql);
			if (listaFornecedorCidadeIdioma == null) {
				listaFornecedorCidadeIdioma = new ArrayList<Fornecedorcidadeidioma>();
			}
			if (listaFornecedorCidadeIdioma.size() == 0) {
				FacesMessage mensagemAtencao = new FacesMessage("Nenhum escola encontrado com os dados pesquisados.",
						"");
				FacesContext.getCurrentInstance().addMessage("Atenção", mensagemAtencao);
			} else
				gerarListaCursoTodasEscolas();
	}
	
	
	public void gerarListaCursoTodasEscolas() {
		String sql = "SELECT f FROM Fornecedorcidadeidiomaproduto f where f.produtosorcamento.tipoproduto='C' and ";
		for (int i = 0; i < listaFornecedorCidadeIdioma.size(); i++) {
			sql = sql + " f.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ listaFornecedorCidadeIdioma.get(i).getIdfornecedorcidadeidioma();
			if ((i + 1) < listaFornecedorCidadeIdioma.size()) {
				sql = sql + " or ";
			}
		}  
		sql = sql + "  order by f.produtosorcamento.descricao ";
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
		List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
		if (lista == null) {
			lista = new ArrayList<Fornecedorcidadeidiomaproduto>();
		} else {
			listaProdutosOrcamento = new ArrayList<Produtosorcamento>();
			for (int i = 0; i < lista.size(); i++) {
				if (verificarListaProdutosOrcamento(lista.get(i).getProdutosorcamento())) {
					listaProdutosOrcamento.add(lista.get(i).getProdutosorcamento());
				}
			}
		}
	}
	
	public boolean verificarListaProdutosOrcamento(Produtosorcamento produtoOcamento) {
		int idProdutoOrcamento = produtoOcamento.getIdprodutosOrcamento();
		for (int i=0;i<listaProdutosOrcamento.size();i++) {
			if (listaProdutosOrcamento.get(i).getIdprodutosOrcamento()==idProdutoOrcamento) {
				return false;
			}
		}
		return true;
	}
	
	
	public void gerarListaPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listarModelo("SELECT p FROM Pais p");
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}
	
	public void gerarListaCursos() {
		if (fornecedorcidadeidioma != null) {
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
			String sql = "SELECT f FROM Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ fornecedorcidadeidioma.getIdfornecedorcidadeidioma()
					+ " and f.produtosorcamento.tipoproduto='C' " + " order by f.produtosorcamento.descricao";
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
			if (lista == null) {
				lista = new ArrayList<Fornecedorcidadeidiomaproduto>();
			} else {
				listaProdutosOrcamento = new ArrayList<Produtosorcamento>();
				for (int i = 0; i < lista.size(); i++) {
					listaProdutosOrcamento.add(lista.get(i).getProdutosorcamento());
				}
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtosorcamento = produtoOrcamentoFacade.consultar(2);
				listaProdutosOrcamento.add(produtosorcamento);
				produtosorcamento = produtoOrcamentoFacade.consultar(17);
				listaProdutosOrcamento.add(produtosorcamento);
			}
		}
	}
	
	public String novaApplication() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedor", fornecedorcidadeidioma.getFornecedorcidade().getFornecedor());
		session.setAttribute("pais", pais);
		session.setAttribute("produtosorcamento", produtosorcamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("uploadApplication", options, null);
		return "";
	}
	
	
	public void gerarFornecedorApplication() {
		String sql = "SELECT f FROM Fornecedorapplication f WHERE f.nomearquivo like '%%'";
		if (pais != null && pais.getIdpais() != null) {
			sql = sql + " AND f.pais.idpais=" + pais.getIdpais();
		}
		
		if (fornecedorcidadeidioma != null && fornecedorcidadeidioma.getIdfornecedorcidadeidioma() != null) {
			sql = sql + " AND f.fornecedor.idfornecedor=" + fornecedorcidadeidioma.getFornecedorcidade().getFornecedor().getIdfornecedor();
		}
		
		if (produtosorcamento != null && produtosorcamento.getIdprodutosOrcamento() != null) {
			sql = sql + " AND f.produtosorcamento.idprodutosOrcamento=" + produtosorcamento.getIdprodutosOrcamento();
		}
		
		if ((pais != null && pais.getIdpais() != null) && (fornecedorcidadeidioma != null && fornecedorcidadeidioma.getIdfornecedorcidadeidioma() != null)
				&& (produtosorcamento != null && produtosorcamento.getIdprodutosOrcamento() != null)) {
			desabilitarUpload = false;
		}else {
			desabilitarUpload = true;
		}
		
		FornecedorApplicationFacade fornecedorApplicationFacade = new FornecedorApplicationFacade();
		listaFornecedor = fornecedorApplicationFacade.listar(sql);
		
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedorapplication>();
		}
		
		
	}
	
	
	public void excluirArquivo(Fornecedorapplication fornecedorapplication) {
		excluirArquivoFTP(fornecedorapplication);
		verificarCursos(fornecedorapplication);
		FornecedorApplicationFacade fornecedorApplicationFacade = new FornecedorApplicationFacade();
		fornecedorApplicationFacade.excluir(fornecedorapplication.getIdfornecedorapplication());
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
		listaFornecedor.remove(fornecedorapplication);
	}
	
	
	public void verificarCursos(Fornecedorapplication fornecedorapplication) {
		CursoFacade cursoFacade = new CursoFacade();
		List<Curso> listaCurso = cursoFacade.lista("SELECT c FROM Curso c WHERE c.idfornecedorapplication=" + fornecedorapplication.getIdfornecedorapplication());
		if (listaCurso != null && !listaCurso.isEmpty()) {
			for (int i = 0; i < listaCurso.size(); i++) {
				listaCurso.get(i).setUploadapplication(false);
				listaCurso.get(i).setIdfornecedorapplication(0);
				cursoFacade.salvar(listaCurso.get(i));
			}
		}
	}
	
	public boolean excluirArquivoFTP(Fornecedorapplication fornecedorapplication) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("local", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey("application/" + fornecedorapplication.getNomearquivo());
			if(s3.delete(objectSummary)) {
				Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
				return true;
			}else {
				Mensagem.lancarMensagemInfo("Falha ao excluir", "");
				return false;
			}
	}
	
	public void retornoDialogUpload(SelectEvent event) {
		Fornecedorapplication fornecedorapplication = (Fornecedorapplication) event.getObject();
		if (fornecedorapplication.getIdfornecedorapplication() != null) {
			if (listaFornecedor == null) {
				listaFornecedor = new ArrayList<Fornecedorapplication>();
			}
			listaFornecedor.add(fornecedorapplication);
		}
	}
	
	
	

}
