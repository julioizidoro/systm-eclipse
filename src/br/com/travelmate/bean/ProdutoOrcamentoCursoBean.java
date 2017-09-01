/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

/**
 *
 * @author Wolverine
 */
public class ProdutoOrcamentoCursoBean {
    
    private int idProdutoOrcamento;
    private String descricaoProdutoOrcamento;
    private float valorMoedaEstrangeira;
    private float valorMoedaReal;
    private boolean apagar;
    private boolean novo;
    private int idProdutoOrcamentoCurso;
    private boolean somarvalortotal;

    public int getIdProdutoOrcamento() {
        return idProdutoOrcamento;
    }

    public void setIdProdutoOrcamento(int idProdutoOrcamento) {
        this.idProdutoOrcamento = idProdutoOrcamento;
    }

    public String getDescricaoProdutoOrcamento() {
        return descricaoProdutoOrcamento;
    }

    public void setDescricaoProdutoOrcamento(String descricaoProdutoOrcamento) {
        this.descricaoProdutoOrcamento = descricaoProdutoOrcamento;
    }

    public float getValorMoedaEstrangeira() {
        return valorMoedaEstrangeira;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public int getIdProdutoOrcamentoCurso() {
        return idProdutoOrcamentoCurso;
    }

    public void setIdProdutoOrcamentoCurso(int idProdutoOrcamentoCurso) {
        this.idProdutoOrcamentoCurso = idProdutoOrcamentoCurso;
    }

  
    public void setValorMoedaEstrangeira(float valorMoedaEstrangeira) {
        this.valorMoedaEstrangeira = valorMoedaEstrangeira;
    }

    public float getValorMoedaReal() {
        return valorMoedaReal;
    }

    public void setValorMoedaReal(float valorMoedaReal) {
        this.valorMoedaReal = valorMoedaReal;
    }

    public boolean isApagar() {
        return apagar;
    }

    public void setApagar(boolean apagar) {
        this.apagar = apagar;
    }

	public boolean isSomarvalortotal() {
		return somarvalortotal;
	}

	public void setSomarvalortotal(boolean somarvalortotal) {
		this.somarvalortotal = somarvalortotal;
	}
    
    
    
    
}
