package br.com.travelmate.managerBean.financeiro.relatorios;

public class RetornoBean {
	
	private String inscricao;
	private String nossoNumero;
	private String dataPagamento;
	private String nomePagador;
	private float valorTitulo;
	private float valorJuros;
	private String codigoOcorrencia;
	
	public String getInscricao() {
		return inscricao;
	}
	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}
	public String getNossoNumero() {
		return nossoNumero;
	}
	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}
	public String getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getNomePagador() {
		return nomePagador;
	}
	public void setNomePagador(String nomePagador) {
		this.nomePagador = nomePagador;
	}
	public float getValorTitulo() {
		return valorTitulo;
	}
	public void setValorTitulo(float valorTitulo) {
		this.valorTitulo = valorTitulo;
	}
	public float getValorJuros() {
		return valorJuros;
	}
	public void setValorJuros(float valorJuros) {
		this.valorJuros = valorJuros;
	}
	public String getCodigoOcorrencia() {
		return codigoOcorrencia;
	}
	public void setCodigoOcorrencia(String codigoOcorrencia) {
		this.codigoOcorrencia = codigoOcorrencia;
	}

}
