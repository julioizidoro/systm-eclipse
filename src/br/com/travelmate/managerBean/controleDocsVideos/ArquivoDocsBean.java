package br.com.travelmate.managerBean.controleDocsVideos;

import java.util.Date;

public class ArquivoDocsBean {
	
	private String nome;
	private Date datavalidade;
	private String nomeftp;
	private String tipo;
	private Date datainicio;
	private String caminho;
	private Integer idArquivoDocs;
	private String tipoArquivo;
	private boolean selecionado;
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDatavalidade() {
		return datavalidade;
	}
	public void setDatavalidade(Date datavalidade) {
		this.datavalidade = datavalidade;
	}
	public String getNomeftp() {
		return nomeftp;
	}
	public void setNomeftp(String nomeftp) {
		this.nomeftp = nomeftp;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getDatainicio() {
		return datainicio;
	}
	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public Integer getIdArquivoDocs() {
		return idArquivoDocs;
	}
	public void setIdArquivoDocs(Integer idArquivoDocs) {
		this.idArquivoDocs = idArquivoDocs;
	}
	public String getTipoArquivo() {
		return tipoArquivo;
	}
	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}
	public boolean isSelecionado() {
		return selecionado;
	}
	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}
	
	
	

}
