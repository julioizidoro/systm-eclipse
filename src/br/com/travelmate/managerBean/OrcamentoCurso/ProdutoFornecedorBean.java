package br.com.travelmate.managerBean.OrcamentoCurso;

import java.util.List;

import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedorcidadeidioma;

public class ProdutoFornecedorBean {
	
	private Coprodutos coprodutos;
	private List<ProdutosOrcamentoBean> listaObrigaroerios;
	private List<ProdutosOrcamentoBean> listaCursoPrincipal;
    private Float valorMoedaEstrangeira;
    private String svalorMoedaEstrangeira;
    private Float valorMoedaNacional;
    private String svalorMoedaNacional;
    private Float valorDesconto;
    private String svalorDesconto;
    private Integer linhaFornecedor;
    private Integer linhaFornecedorProdutoBean;
    private Fornecedorcidadeidioma fornecedorCidadeIdioma;
    private List<ProdutosOrcamentoBean> listaObriSalvo;
	
    public Integer getLinhaFornecedor() {
		return linhaFornecedor;
	}
	public void setLinhaFornecedor(Integer linhaFornecedor) {
		this.linhaFornecedor = linhaFornecedor;
	}
	public Coprodutos getCoprodutos() {
		return coprodutos;
	}
	public void setCoprodutos(Coprodutos coprodutos) {
		this.coprodutos = coprodutos;
	}
	public List<ProdutosOrcamentoBean> getListaObrigaroerios() {
		return listaObrigaroerios;
	}
	public void setListaObrigaroerios(List<ProdutosOrcamentoBean> listaObrigaroerios) {
		this.listaObrigaroerios = listaObrigaroerios;
	}
	
	public Float getValorMoedaEstrangeira() {
		return valorMoedaEstrangeira;
	}
	public void setValorMoedaEstrangeira(Float valorMoedaEstrangeira) {
		this.valorMoedaEstrangeira = valorMoedaEstrangeira;
	}
	public String getSvalorMoedaEstrangeira() {
		return svalorMoedaEstrangeira;
	}
	public void setSvalorMoedaEstrangeira(String svalorMoedaEstrangeira) {
		this.svalorMoedaEstrangeira = svalorMoedaEstrangeira;
	}
	public Float getValorMoedaNacional() {
		return valorMoedaNacional;
	}
	public void setValorMoedaNacional(Float valorMoedaNacional) {
		this.valorMoedaNacional = valorMoedaNacional;
	}
	public String getSvalorMoedaNacional() {
		return svalorMoedaNacional;
	}
	public void setSvalorMoedaNacional(String svalorMoedaNacional) {
		this.svalorMoedaNacional = svalorMoedaNacional;
	}
	public Float getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(Float valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public String getSvalorDesconto() {
		return svalorDesconto;
	}
	public void setSvalorDesconto(String svalorDesconto) {
		this.svalorDesconto = svalorDesconto;
	}
	public List<ProdutosOrcamentoBean> getListaCursoPrincipal() {
		return listaCursoPrincipal;
	}
	public void setListaCursoPrincipal(List<ProdutosOrcamentoBean> listaCursoPrincipal) {
		this.listaCursoPrincipal = listaCursoPrincipal;
	}
	public Integer getLinhaFornecedorProdutoBean() {
		return linhaFornecedorProdutoBean;
	}
	public void setLinhaFornecedorProdutoBean(Integer linhaFornecedorProdutoBean) {
		this.linhaFornecedorProdutoBean = linhaFornecedorProdutoBean;
	}
	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}
	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	}
	public List<ProdutosOrcamentoBean> getListaObriSalvo() {
		return listaObriSalvo;
	}
	public void setListaObriSalvo(List<ProdutosOrcamentoBean> listaObriSalvo) {
		this.listaObriSalvo = listaObriSalvo;
	}
	
	
}
