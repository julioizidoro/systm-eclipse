package br.com.travelmate.managerBean.crm;

import java.util.Date;

import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;

public class PesquisaBean {

	private String nomeCliente;
	private Date dataProxInicio;
	private Date dataProxFinal;
	private Date dataUltInicio;
	private Date dataUltFinal;
	private Usuario usuario;
	private Usuario usuarioPara;
	private Unidadenegocio unidadenegocio;
	private String situacao;
	private Produtos programas;
	private Tipocontato tipocontato;
	private String status;
	private Date dataInseridoInicial;
	private Date dataInseridoFinal;

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getDataProxInicio() {
		return dataProxInicio;
	}

	public void setDataProxInicio(Date dataProxInicio) {
		this.dataProxInicio = dataProxInicio;
	}

	public Date getDataProxFinal() {
		return dataProxFinal;
	}

	public void setDataProxFinal(Date dataProxFinal) {
		this.dataProxFinal = dataProxFinal;
	}

	public Date getDataUltInicio() {
		return dataUltInicio;
	}

	public void setDataUltInicio(Date dataUltInicio) {
		this.dataUltInicio = dataUltInicio;
	}

	public Date getDataUltFinal() {
		return dataUltFinal;
	}

	public void setDataUltFinal(Date dataUltFinal) {
		this.dataUltFinal = dataUltFinal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioPara() {
		return usuarioPara;
	}

	public void setUsuarioPara(Usuario usuarioPara) {
		this.usuarioPara = usuarioPara;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Produtos getProgramas() {
		return programas;
	}

	public void setProgramas(Produtos programas) {
		this.programas = programas;
	}

	public Tipocontato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(Tipocontato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataInseridoInicial() {
		return dataInseridoInicial;
	}

	public void setDataInseridoInicial(Date dataInseridoInicial) {
		this.dataInseridoInicial = dataInseridoInicial;
	}

	public Date getDataInseridoFinal() {
		return dataInseridoFinal;
	}

	public void setDataInseridoFinal(Date dataInseridoFinal) {
		this.dataInseridoFinal = dataInseridoFinal;
	}

}
