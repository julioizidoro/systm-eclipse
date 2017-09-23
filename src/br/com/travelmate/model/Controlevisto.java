package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the controlevistos database table.
 * 
 */
@Entity
@Table(name="controlevistos")
public class Controlevisto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idcontroleVistos;

	private String categoria;

	private String conferenciaDocumentos;

	private String confirmacaoMatricula;

	@Temporal(TemporalType.DATE)
	private Date dataInicioPrograma;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	private String emailOrientacao;

	private String envioProcessamento;

	private String finalizado;

	@Lob
	private String observacao;

	private String paisVisto;

	private String preenchimentoFormularios;

	private String protocolo;

	private String rascunhoFormularios;

	private String situacao;

	@Column(name="vendas_idvendas")
	private int vendasIdvendas;

	private String vistoLiberado;

	public Controlevisto() {
	}

	public int getIdcontroleVistos() {
		return this.idcontroleVistos;
	}

	public void setIdcontroleVistos(int idcontroleVistos) {
		this.idcontroleVistos = idcontroleVistos;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getConferenciaDocumentos() {
		return this.conferenciaDocumentos;
	}

	public void setConferenciaDocumentos(String conferenciaDocumentos) {
		this.conferenciaDocumentos = conferenciaDocumentos;
	}

	public String getConfirmacaoMatricula() {
		return this.confirmacaoMatricula;
	}

	public void setConfirmacaoMatricula(String confirmacaoMatricula) {
		this.confirmacaoMatricula = confirmacaoMatricula;
	}

	public Date getDataInicioPrograma() {
		return this.dataInicioPrograma;
	}

	public void setDataInicioPrograma(Date dataInicioPrograma) {
		this.dataInicioPrograma = dataInicioPrograma;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmailOrientacao() {
		return this.emailOrientacao;
	}

	public void setEmailOrientacao(String emailOrientacao) {
		this.emailOrientacao = emailOrientacao;
	}

	public String getEnvioProcessamento() {
		return this.envioProcessamento;
	}

	public void setEnvioProcessamento(String envioProcessamento) {
		this.envioProcessamento = envioProcessamento;
	}

	public String getFinalizado() {
		return this.finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getPaisVisto() {
		return this.paisVisto;
	}

	public void setPaisVisto(String paisVisto) {
		this.paisVisto = paisVisto;
	}

	public String getPreenchimentoFormularios() {
		return this.preenchimentoFormularios;
	}

	public void setPreenchimentoFormularios(String preenchimentoFormularios) {
		this.preenchimentoFormularios = preenchimentoFormularios;
	}

	public String getProtocolo() {
		return this.protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getRascunhoFormularios() {
		return this.rascunhoFormularios;
	}

	public void setRascunhoFormularios(String rascunhoFormularios) {
		this.rascunhoFormularios = rascunhoFormularios;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getVendasIdvendas() {
		return this.vendasIdvendas;
	}

	public void setVendasIdvendas(int vendasIdvendas) {
		this.vendasIdvendas = vendasIdvendas;
	}

	public String getVistoLiberado() {
		return this.vistoLiberado;
	}

	public void setVistoLiberado(String vistoLiberado) {
		this.vistoLiberado = vistoLiberado;
	}

}