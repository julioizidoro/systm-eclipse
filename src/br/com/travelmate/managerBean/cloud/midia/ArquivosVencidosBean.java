package br.com.travelmate.managerBean.cloud.midia;

import java.util.Date;

public class ArquivosVencidosBean {

	private String nome;
	private String pasta;
	private Date data;
	private Integer nArquivo;
	private String nomeFtp;
	private String diretorio;
	private Integer idarquivoBean;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getnArquivo() {
		return nArquivo;
	}

	public void setnArquivo(Integer nArquivo) {
		this.nArquivo = nArquivo;
	}

	public String getNomeFtp() {
		return nomeFtp;
	}

	public void setNomeFtp(String nomeFtp) {
		this.nomeFtp = nomeFtp;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public Integer getIdarquivoBean() {
		return idarquivoBean;
	}

	public void setIdarquivoBean(Integer idarquivoBean) {
		this.idarquivoBean = idarquivoBean;
	}

}
