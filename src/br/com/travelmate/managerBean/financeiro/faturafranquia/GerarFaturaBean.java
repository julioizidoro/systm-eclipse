package br.com.travelmate.managerBean.financeiro.faturafranquia;

import java.util.Date;

import br.com.travelmate.model.Faturaajustes;
import br.com.travelmate.model.Faturafranquias;

/**
 *
 * @author Kamila
 */
public class GerarFaturaBean {

	private Faturafranquias faturafranquias;
	private Faturaajustes faturaajustes;
	private Date dataincio;
	private String cliente;
	private Date datavenda;
	private String programa;
	private String fornecedor;
	private float valortotal;
	private float valorcomissionavel;
	private float percentualcomissao;
	private float comissao;
	private float taxatm;
	private float desagio;
	private float descontomatriz;
	private float descontoloja;
	private float recebidomatiz;
	private float total;
	private boolean fatura;
	private boolean selecionado;
	private int ordenacao;
	private int mes;
	private int ano;
	private boolean somar;

	public Faturafranquias getFaturafranquias() {
		return faturafranquias;
	}

	public void setFaturafranquias(Faturafranquias faturafranquias) {
		this.faturafranquias = faturafranquias;
	}

	public Faturaajustes getFaturaajustes() {
		return faturaajustes;
	}

	public void setFaturaajustes(Faturaajustes faturaajustes) {
		this.faturaajustes = faturaajustes;
	}

	public Date getDataincio() {
		return dataincio;
	}

	public void setDataincio(Date dataincio) {
		this.dataincio = dataincio;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getDatavenda() {
		return datavenda;
	}

	public void setDatavenda(Date datavenda) {
		this.datavenda = datavenda;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public float getValortotal() {
		return valortotal;
	}

	public void setValortotal(float valortotal) {
		this.valortotal = valortotal;
	}

	public float getValorcomissionavel() {
		return valorcomissionavel;
	}

	public void setValorcomissionavel(float valorcomissionavel) {
		this.valorcomissionavel = valorcomissionavel;
	}

	public float getPercentualcomissao() {
		return percentualcomissao;
	}

	public void setPercentualcomissao(float percentualcomissao) {
		this.percentualcomissao = percentualcomissao;
	}

	public float getComissao() {
		return comissao;
	}

	public void setComissao(float comissao) {
		this.comissao = comissao;
	}

	public float getTaxatm() {
		return taxatm;
	}

	public void setTaxatm(float taxatm) {
		this.taxatm = taxatm;
	}

	public float getDesagio() {
		return desagio;
	}

	public void setDesagio(float desagio) {
		this.desagio = desagio;
	}

	public float getDescontomatriz() {
		return descontomatriz;
	}

	public void setDescontomatriz(float descontomatriz) {
		this.descontomatriz = descontomatriz;
	}

	public float getDescontoloja() {
		return descontoloja;
	}

	public void setDescontoloja(float descontoloja) {
		this.descontoloja = descontoloja;
	}

	public float getRecebidomatiz() {
		return recebidomatiz;
	}

	public void setRecebidomatiz(float recebidomatiz) {
		this.recebidomatiz = recebidomatiz;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public boolean isFatura() {
		return fatura;
	}

	public void setFatura(boolean fatura) {
		this.fatura = fatura;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public int getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(int ordenacao) {
		this.ordenacao = ordenacao;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public boolean isSomar() {
		return somar;
	}

	public void setSomar(boolean somar) {
		this.somar = somar;
	}

}
