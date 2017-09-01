package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valorcoprodutos;

public class ProdutosExtrasBean {

	private Float valorOrigianl;
	private Float valorOriginalRS;
	private String valorOrigianlString;
	private String valorOriginalRSString;
	private String descricao;
	private Valorcoprodutos valorcoprodutos;
	private Produtosorcamento produtosorcamento;
	private boolean somarvalortotal;

	public Float getValorOrigianl() {
		return valorOrigianl;
	}

	public void setValorOrigianl(Float valorOrigianl) {
		this.valorOrigianl = valorOrigianl;
	}

	public Float getValorOriginalRS() {
		return valorOriginalRS;
	}

	public void setValorOriginalRS(Float valorOriginalRS) {
		this.valorOriginalRS = valorOriginalRS;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public String getValorOrigianlString() {
		return valorOrigianlString;
	}

	public void setValorOrigianlString(String valorOrigianlString) {
		this.valorOrigianlString = valorOrigianlString;
	}

	public String getValorOriginalRSString() {
		return valorOriginalRSString;
	}

	public void setValorOriginalRSString(String valorOriginalRSString) {
		this.valorOriginalRSString = valorOriginalRSString;
	}

	public Valorcoprodutos getValorcoprodutos() {
		return valorcoprodutos;
	}

	public void setValorcoprodutos(Valorcoprodutos valorcoprodutos) {
		this.valorcoprodutos = valorcoprodutos;
	}

	public boolean isSomarvalortotal() {
		return somarvalortotal;
	}

	public void setSomarvalortotal(boolean somarvalortotal) {
		this.somarvalortotal = somarvalortotal;
	}

}
