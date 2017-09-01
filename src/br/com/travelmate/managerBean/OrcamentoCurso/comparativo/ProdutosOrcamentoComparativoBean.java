package br.com.travelmate.managerBean.OrcamentoCurso.comparativo;

import java.util.List;

public class ProdutosOrcamentoComparativoBean {

	private String tituloLista; 
	private String totalrslista;
	private String totalmelista; 
	private int idgrupo;
	private List<ListaProdutosOrcamentoComparativoBean> listaProdutosOrcamento;

	public ProdutosOrcamentoComparativoBean() {
		
	}

	public String getTituloLista() {
		return tituloLista;
	}

	public void setTituloLista(String tituloLista) {
		this.tituloLista = tituloLista;
	}  

	public String getTotalrslista() {
		return totalrslista;
	}

	public void setTotalrslista(String totalrslista) {
		this.totalrslista = totalrslista;
	}

	public String getTotalmelista() {
		return totalmelista;
	}

	public void setTotalmelista(String totalmelista) {
		this.totalmelista = totalmelista;
	}  
	public int getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(int idgrupo) {
		this.idgrupo = idgrupo;
	}

	public List<ListaProdutosOrcamentoComparativoBean> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<ListaProdutosOrcamentoComparativoBean> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

}
