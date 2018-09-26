/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wolverine
 */
@Entity
@Table(name = "acesso")
public class Acesso implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idacesso")
	private Integer idacesso;
	@Column(name = "acesso")
	private boolean acesso;
	@Column(name = "avisos")
	private boolean avisos;
	@Column(name = "banco")
	private boolean banco;
	@Column(name = "cambio")
	private boolean cambio;
	@Column(name = "cliente")
	private boolean cliente;
	@Column(name = "fornecedor")
	private boolean fornecedor;
	@Column(name = "produtos")
	private boolean produtos;
	@Column(name = "usuarios")
	private boolean usuarios;
	@Column(name = "unidades")
	private boolean unidades;
	@Column(name = "promocoes")
	private boolean promocoes;
	@Column(name = "pacote")
	private boolean pacote;
	@Column(name = "passagem")
	private boolean passagem;
	@Column(name = "menugerencial")
	private boolean menugerencial;
	@Column(name = "gerenciasaupair")
	private boolean gerenciasaupair;
	@Column(name = "gerenciascurso")
	private boolean gerenciascurso;
	@Column(name = "gerenciasprogramateens")
	private boolean gerenciasprogramateens;
	@Column(name = "gerenciasdemipair")
	private boolean gerenciasdemipair;
	@Column(name = "gerenciashighschool")
	private boolean gerenciashighschool;
	@Column(name = "gerenciasseguro")
	private boolean gerenciasseguro;
	@Column(name = "gerenciastrainne")
	private boolean gerenciastrainne;
	@Column(name = "gerenciasvoluntariado")
	private boolean gerenciasvoluntariado;
	@Column(name = "gerenciaswork")
	private boolean gerenciaswork;
	@Column(name = "controlealteracoes")
	private boolean controlealteracoes;
	@Column(name = "menucrm")
	private boolean menucrm;
	@Column(name = "distribuicaolead")
	private boolean distribuicaolead;
	@Column(name = "cadlead")
	private boolean cadlead;
	@Column(name = "menufinanceiro")
	private boolean menufinanceiro;
	@Column(name = "menucomercial")
	private boolean menucomercial;
	@Column(name = "crmdiretoria")
	private boolean crmdiretoria;
	@Column(name = "invoice")
	private boolean invoice;
	@Column(name = "gerarpin")
	private boolean gerarpin;
	@Column(name = "coeficiente")
	private boolean coeficiente;
	@Column(name = "contasreceber")
	private boolean contasreceber;
	@Column(name = "outroslancamentos")
	private boolean outroslancamentos;
	@Column(name = "planoconta")
	private boolean planoconta;
	@Column(name = "vendas")
	private boolean vendas;
	@Column(name = "cancelamentofinanceiro")
	private boolean cancelamentofinanceiro;
	@Column(name = "faturafranquia")
	private boolean faturafranquia;
	@Column(name = "terceiros")
	private boolean terceiros;
	@Column(name = "relatoriosfinanceiro")
	private boolean relatoriosfinanceiro;
	@Column(name = "conciliacaobancaria")
	private boolean conciliacaobancaria;
	@Column(name = "controlealteracaofinanceiro")
	private boolean controlealteracaofinanceiro;
	@Column(name = "highereducation")
	private boolean highereducation;
	@Column(name = "aprovardepoimento")
	private boolean aprovardepoimento;
	@Column(name = "aprovarquestionariohe")
	private boolean aprovarquestionariohe;
	@Column(name = "cadmtp")
	private boolean cadmtp;
	@Column(name = "cadmotivocancelamento")
	private boolean cadmotivocancelamento;
	@Column(name = "gerencialcrm")
	private boolean gerencialcrm;
	@Column(name = "gerencialcrmunidade")
	private boolean gerencialcrmunidade;
	@Column(name = "departamento")
	private boolean departamento;
	@Column(name = "funcao")
	private boolean funcao;
	@Column(name = "enviaremail")
	private boolean enviaremail;
	@Column(name = "recebimentos")
	private boolean recebimentos;
	@Column(name = "cartaocredito")
	private boolean cartaocredito;
	@Column(name = "conferenciacartaocredito")
	private boolean conferenciacartaocredito;
	@Column(name = "fatura")
	private boolean fatura;
	@Column(name = "recebimento")
	private boolean recebimento;
	@Column(name = "btncomissaoficha")
	private boolean btncomissaoficha;
	@Column(name = "fornecedordocs")
	private boolean fornecedordocs;
	@Column(name = "controledocumentos")
	private boolean controledocumentos;
	@Column(name = "gerencialdocsfornecedor")
	private boolean gerencialdocsfornecedor;
	@Column(name = "relatoriodocsparceiro")
	private boolean relatoriodocsparceiro;
	@Column(name = "comissaopacotes")
	private boolean comissaopacotes;
	@Column(name = "gerenciasturismo")
	private boolean gerenciasturismo;
	@Column(name = "turismotodasvendas")
	private boolean turismotodasvendas;
	@Column(name = "revisaofinanceiro")
	private boolean revisaofinanceiro;
	@Column(name = "motivopendencia")
	private boolean motivopendencia;
	@Column(name = "menucontas")
	private boolean menucontas;
	@Column(name = "crmcobranca")
	private boolean crmcobranca;
	@Column(name = "acessounidade")
	private boolean acessounidade;
	@Column(name = "menusolicitacoes")
	private boolean menusolicitacoes;
	@Column(name = "acessogerencialsolicitacoes")
	private boolean acessogerencialsolicitacoes;
	@Column(name = "acessoliberadassolicitacoes")
	private boolean acessoliberadassolicitacoes;
	@Column(name = "calculadorafinanceira")
	private boolean calculadorafinanceira;
	@Column(name = "relatoriosolicitacoesti")
	private boolean relatoriosolicitacoesti; 
	@Column(name = "editarusuario")
	private boolean editarusuario;
	@Column(name = "editarunidade")
	private boolean editarunidade;
	@Column(name = "consultausuariounidade")
	private boolean consultausuariounidade;
	@Column(name = "cadastrousuario")
	private boolean cadastrousuario; 
	@Column(name = "cadastrounidade")
	private boolean cadastrounidade; 
	@Column(name = "utilti")
	private boolean utilti; 
	@Column(name = "gerencialdistribuicaoleads")
	private boolean gerencialdistribuicaoleads; 
	@Column(name = "acessogerencialdocs")
	private boolean acessogerencialdocs; 
	@Column(name = "modeloorcamento")
	private boolean modeloorcamento; 
	@Column(name = "relatoriogerencia")
	private boolean relatoriogerencia; 
	@Column(name = "comissaoconsultor")
	private boolean comissaoconsultor; 
	@Column(name = "descontotm")
	private boolean descontotm; 
	@Column(name = "relatorioleads")
	private boolean relatorioleads; 
	@Column(name = "relatoriocliente")
	private boolean relatoriocliente;
	@Column(name = "relatorioleadsdetalhado")
	private boolean relatorioleadsdetalhado;  
	@Column(name = "relatoriocartaocredito")
	private boolean relatoriocartaocredito; 
	@Column(name = "editardocsvideos")
	private boolean editardocsvideos;  
	@Column(name = "excluirdocsvideos")
	private boolean excluirdocsvideos;   
	@Column(name = "eventoscontasreceber")
	private boolean eventoscontasreceber;   
	
	
	
	
	public Integer getIdacesso() {
		return idacesso;
	}

	public void setIdacesso(Integer idacesso) {
		this.idacesso = idacesso;
	}

	public boolean isAcesso() {
		return acesso;
	}

	public void setAcesso(boolean acesso) {
		this.acesso = acesso;
	}

	public boolean isAvisos() {
		return avisos;
	}

	public void setAvisos(boolean avisos) {
		this.avisos = avisos;
	}

	public boolean isBanco() {
		return banco;
	}

	public void setBanco(boolean banco) {
		this.banco = banco;
	}

	public boolean isCambio() {
		return cambio;
	}

	public void setCambio(boolean cambio) {
		this.cambio = cambio;
	}

	public boolean isCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public boolean isFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(boolean fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean isProdutos() {
		return produtos;
	}

	public void setProdutos(boolean produtos) {
		this.produtos = produtos;
	}

	public boolean isUsuarios() {
		return usuarios;
	}

	public void setUsuarios(boolean usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isUnidades() {
		return unidades;
	}

	public void setUnidades(boolean unidades) {
		this.unidades = unidades;
	}

	public boolean isPromocoes() {
		return promocoes;
	}

	public void setPromocoes(boolean promocoes) {
		this.promocoes = promocoes;
	}

	public boolean isPacote() {
		return pacote;
	}

	public void setPacote(boolean pacote) {
		this.pacote = pacote;
	}

	public boolean isPassagem() {
		return passagem;
	}

	public void setPassagem(boolean passagem) {
		this.passagem = passagem;
	}

	public boolean isMenugerencial() {
		return menugerencial;
	}

	public void setMenugerencial(boolean menugerencial) {
		this.menugerencial = menugerencial;
	}

	public boolean isGerenciasaupair() {
		return gerenciasaupair;
	}

	public void setGerenciasaupair(boolean gerenciasaupair) {
		this.gerenciasaupair = gerenciasaupair;
	}

	public boolean isGerenciascurso() {
		return gerenciascurso;
	}

	public void setGerenciascurso(boolean gerenciascurso) {
		this.gerenciascurso = gerenciascurso;
	}

	public boolean isGerenciasprogramateens() {
		return gerenciasprogramateens;
	}

	public void setGerenciasprogramateens(boolean gerenciasprogramateens) {
		this.gerenciasprogramateens = gerenciasprogramateens;
	}

	public boolean isGerenciasdemipair() {
		return gerenciasdemipair;
	}

	public void setGerenciasdemipair(boolean gerenciasdemipair) {
		this.gerenciasdemipair = gerenciasdemipair;
	}

	public boolean isGerenciashighschool() {
		return gerenciashighschool;
	}

	public void setGerenciashighschool(boolean gerenciashighschool) {
		this.gerenciashighschool = gerenciashighschool;
	}

	public boolean isGerenciasseguro() {
		return gerenciasseguro;
	}

	public void setGerenciasseguro(boolean gerenciasseguro) {
		this.gerenciasseguro = gerenciasseguro;
	}

	public boolean isGerenciastrainne() {
		return gerenciastrainne;
	}

	public void setGerenciastrainne(boolean gerenciastrainne) {
		this.gerenciastrainne = gerenciastrainne;
	}

	public boolean isGerenciasvoluntariado() {
		return gerenciasvoluntariado;
	}

	public void setGerenciasvoluntariado(boolean gerenciasvoluntariado) {
		this.gerenciasvoluntariado = gerenciasvoluntariado;
	}

	public boolean isGerenciaswork() {
		return gerenciaswork;
	}

	public void setGerenciaswork(boolean gerenciaswork) {
		this.gerenciaswork = gerenciaswork;
	}

	public boolean isControlealteracoes() {
		return controlealteracoes;
	}

	public void setControlealteracoes(boolean controlealteracoes) {
		this.controlealteracoes = controlealteracoes;
	}

	public boolean isMenucrm() {
		return menucrm;
	}

	public void setMenucrm(boolean menucrm) {
		this.menucrm = menucrm;
	}

	public boolean isDistribuicaolead() {
		return distribuicaolead;
	}

	public void setDistribuicaolead(boolean distribuicaolead) {
		this.distribuicaolead = distribuicaolead;
	}

	public boolean isCadlead() {
		return cadlead;
	}

	public void setCadlead(boolean cadlead) {
		this.cadlead = cadlead;
	}

	public boolean isMenufinanceiro() {
		return menufinanceiro;
	}

	public void setMenufinanceiro(boolean menufinanceiro) {
		this.menufinanceiro = menufinanceiro;
	}

	public boolean isMenucomercial() {
		return menucomercial;
	}

	public void setMenucomercial(boolean menucomercial) {
		this.menucomercial = menucomercial;
	}

	public boolean isCrmdiretoria() {
		return crmdiretoria;
	}

	public void setCrmdiretoria(boolean crmdiretoria) {
		this.crmdiretoria = crmdiretoria;
	}

	public boolean isInvoice() {
		return invoice;
	}

	public void setInvoice(boolean invoice) {
		this.invoice = invoice;
	}

	public boolean isGerarpin() {
		return gerarpin;
	}

	public void setGerarpin(boolean gerarpin) {
		this.gerarpin = gerarpin;
	}

	public boolean isCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(boolean coeficiente) {
		this.coeficiente = coeficiente;
	}

	public boolean isContasreceber() {
		return contasreceber;
	}

	public void setContasreceber(boolean contasreceber) {
		this.contasreceber = contasreceber;
	}

	public boolean isOutroslancamentos() {
		return outroslancamentos;
	}

	public void setOutroslancamentos(boolean outroslancamentos) {
		this.outroslancamentos = outroslancamentos;
	}

	public boolean isPlanoconta() {
		return planoconta;
	}

	public void setPlanoconta(boolean planoconta) {
		this.planoconta = planoconta;
	}

	public boolean isVendas() {
		return vendas;
	}

	public void setVendas(boolean vendas) {
		this.vendas = vendas;
	}

	public boolean isCancelamentofinanceiro() {
		return cancelamentofinanceiro;
	}

	public void setCancelamentofinanceiro(boolean cancelamentofinanceiro) {
		this.cancelamentofinanceiro = cancelamentofinanceiro;
	}

	public boolean isFaturafranquia() {
		return faturafranquia;
	}

	public void setFaturafranquia(boolean faturafranquia) {
		this.faturafranquia = faturafranquia;
	}

	public boolean isTerceiros() {
		return terceiros;
	}

	public void setTerceiros(boolean terceiros) {
		this.terceiros = terceiros;
	}

	public boolean isRelatoriosfinanceiro() {
		return relatoriosfinanceiro;
	}

	public void setRelatoriosfinanceiro(boolean relatoriosfinanceiro) {
		this.relatoriosfinanceiro = relatoriosfinanceiro;
	}

	public boolean isConciliacaobancaria() {
		return conciliacaobancaria;
	}

	public void setConciliacaobancaria(boolean conciliacaobancaria) {
		this.conciliacaobancaria = conciliacaobancaria;
	}

	public boolean isControlealteracaofinanceiro() {
		return controlealteracaofinanceiro;
	}

	public void setControlealteracaofinanceiro(boolean controlealteracaofinanceiro) {
		this.controlealteracaofinanceiro = controlealteracaofinanceiro;
	}

	public boolean isHighereducation() {
		return highereducation;
	}

	public void setHighereducation(boolean highereducation) {
		this.highereducation = highereducation;
	}

	public boolean isAprovardepoimento() {
		return aprovardepoimento;
	}

	public void setAprovardepoimento(boolean aprovardepoimento) {
		this.aprovardepoimento = aprovardepoimento;
	}

	public boolean isAprovarquestionariohe() {
		return aprovarquestionariohe;
	}

	public void setAprovarquestionariohe(boolean aprovarquestionariohe) {
		this.aprovarquestionariohe = aprovarquestionariohe;
	}

	public boolean isCadmtp() {
		return cadmtp;
	}

	public void setCadmtp(boolean cadmtp) {
		this.cadmtp = cadmtp;
	}

	public boolean isCadmotivocancelamento() {
		return cadmotivocancelamento;
	}

	public void setCadmotivocancelamento(boolean cadmotivocancelamento) {
		this.cadmotivocancelamento = cadmotivocancelamento;
	}

	public boolean isGerencialcrm() {
		return gerencialcrm;
	}

	public void setGerencialcrm(boolean gerencialcrm) {
		this.gerencialcrm = gerencialcrm;
	}

	public boolean isGerencialcrmunidade() {
		return gerencialcrmunidade;
	}

	public void setGerencialcrmunidade(boolean gerencialcrmunidade) {
		this.gerencialcrmunidade = gerencialcrmunidade;
	}

	public boolean isDepartamento() {
		return departamento;
	}

	public void setDepartamento(boolean departamento) {
		this.departamento = departamento;
	}

	public boolean isFuncao() {
		return funcao;
	}

	public void setFuncao(boolean funcao) {
		this.funcao = funcao;
	}

	public boolean isEnviaremail() {
		return enviaremail;
	}

	public void setEnviaremail(boolean enviaremail) {
		this.enviaremail = enviaremail;
	}

	public boolean isRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(boolean recebimentos) {
		this.recebimentos = recebimentos;
	}

	public boolean isCartaocredito() {
		return cartaocredito;
	}

	public void setCartaocredito(boolean cartaocredio) {
		this.cartaocredito = cartaocredio;
	}

	public boolean isConferenciacartaocredito() {
		return conferenciacartaocredito;
	}

	public void setConferenciacartaocredito(boolean conferenciacartaocredito) {
		this.conferenciacartaocredito = conferenciacartaocredito;
	}

	public boolean isFatura() {
		return fatura;
	}

	public void setFatura(boolean fatura) {
		this.fatura = fatura;
	}

	public boolean isRecebimento() {
		return recebimento;
	}

	public void setRecebimento(boolean recebimento) {
		this.recebimento = recebimento;
	}

	public boolean isBtncomissaoficha() {
		return btncomissaoficha;
	}

	public void setBtncomissaoficha(boolean btncomissaoficha) {
		this.btncomissaoficha = btncomissaoficha;
	}

	public boolean isFornecedordocs() {
		return fornecedordocs;
	}

	public void setFornecedordocs(boolean fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}

	public boolean isControledocumentos() {
		return controledocumentos;
	}

	public void setControledocumentos(boolean controledocumentos) {
		this.controledocumentos = controledocumentos;
	}

	public boolean isGerencialdocsfornecedor() {
		return gerencialdocsfornecedor;
	}

	public void setGerencialdocsfornecedor(boolean gerencialdocsfornecedor) {
		this.gerencialdocsfornecedor = gerencialdocsfornecedor;
	}

	public boolean isRelatoriodocsparceiro() {
		return relatoriodocsparceiro;
	}

	public void setRelatoriodocsparceiro(boolean relatoriodocsparceiro) {
		this.relatoriodocsparceiro = relatoriodocsparceiro;
	}

	public boolean isComissaopacotes() {
		return comissaopacotes;
	}

	public void setComissaopacotes(boolean comissaopacotes) {
		this.comissaopacotes = comissaopacotes;
	}

	public boolean isGerenciasturismo() {
		return gerenciasturismo;
	}

	public void setGerenciasturismo(boolean gerenciasturismo) {
		this.gerenciasturismo = gerenciasturismo;
	}

	public boolean isTurismotodasvendas() {
		return turismotodasvendas;
	}

	public void setTurismotodasvendas(boolean turismotodasvendas) {
		this.turismotodasvendas = turismotodasvendas;
	}

	public boolean isRevisaofinanceiro() {
		return revisaofinanceiro;
	}

	public void setRevisaofinanceiro(boolean revisaofinanceiro) {
		this.revisaofinanceiro = revisaofinanceiro;
	}

	public boolean isMotivopendencia() {
		return motivopendencia;
	}

	public void setMotivopendencia(boolean motivopendencia) {
		this.motivopendencia = motivopendencia;
	}

	public boolean isMenucontas() {
		return menucontas;
	}

	public void setMenucontas(boolean menucontas) {
		this.menucontas = menucontas;
	}

	public boolean isCrmcobranca() {
		return crmcobranca;
	}

	public void setCrmcobranca(boolean crmcobranca) {
		this.crmcobranca = crmcobranca;
	}

	public boolean isAcessounidade() {
		return acessounidade;
	}

	public void setAcessounidade(boolean acessounidade) {
		this.acessounidade = acessounidade;
	}

	public boolean isMenusolicitacoes() {
		return menusolicitacoes;
	}

	public void setMenusolicitacoes(boolean menusolicitacoes) {
		this.menusolicitacoes = menusolicitacoes;
	}

	public boolean isAcessogerencialsolicitacoes() {
		return acessogerencialsolicitacoes;
	}

	public void setAcessogerencialsolicitacoes(boolean acessogerencialsolicitacoes) {
		this.acessogerencialsolicitacoes = acessogerencialsolicitacoes;
	}

	public boolean isAcessoliberadassolicitacoes() {
		return acessoliberadassolicitacoes;
	}

	public void setAcessoliberadassolicitacoes(boolean acessoliberadassolicitacoes) {
		this.acessoliberadassolicitacoes = acessoliberadassolicitacoes;
	}

	public boolean isCalculadorafinanceira() {
		return calculadorafinanceira;
	}

	public void setCalculadorafinanceira(boolean calculadorafinanceira) {
		this.calculadorafinanceira = calculadorafinanceira;
	}

	public boolean isRelatoriosolicitacoesti() {
		return relatoriosolicitacoesti;
	}

	public void setRelatoriosolicitacoesti(boolean relatoriosolicitacoesti) {
		this.relatoriosolicitacoesti = relatoriosolicitacoesti;
	}
 

	public boolean isEditarusuario() {
		return editarusuario;
	}

	public void setEditarusuario(boolean editarusuario) {
		this.editarusuario = editarusuario;
	}

	public boolean isEditarunidade() {
		return editarunidade;
	}

	public void setEditarunidade(boolean editarunidade) {
		this.editarunidade = editarunidade;
	}

	public boolean isConsultausuariounidade() {
		return consultausuariounidade;
	}

	public void setConsultausuariounidade(boolean consultausuariounidade) {
		this.consultausuariounidade = consultausuariounidade;
	}

	public boolean isCadastrousuario() {
		return cadastrousuario;
	}

	public void setCadastrousuario(boolean cadastrousuario) {
		this.cadastrousuario = cadastrousuario;
	}

	public boolean isCadastrounidade() {
		return cadastrounidade;
	}

	public void setCadastrounidade(boolean cadastrounidade) {
		this.cadastrounidade = cadastrounidade;
	}

	public boolean isUtilti() {
		return utilti;
	}

	public void setUtilti(boolean utilti) {
		this.utilti = utilti;
	}

	public boolean isGerencialdistribuicaoleads() {
		return gerencialdistribuicaoleads;
	}

	public void setGerencialdistribuicaoleads(boolean gerencialdistribuicaoleads) {
		this.gerencialdistribuicaoleads = gerencialdistribuicaoleads;
	}

	public boolean isAcessogerencialdocs() {
		return acessogerencialdocs;
	}

	public void setAcessogerencialdocs(boolean acessogerencialdocs) {
		this.acessogerencialdocs = acessogerencialdocs;
	}

	
	public boolean isModeloorcamento() {
		return modeloorcamento;
	}

	public void setModeloorcamento(boolean modeloorcamento) {
		this.modeloorcamento = modeloorcamento;
	}

	public boolean isRelatoriogerencia() {
		return relatoriogerencia;
	}

	public void setRelatoriogerencia(boolean relatoriogerencia) {
		this.relatoriogerencia = relatoriogerencia;
	}

	public boolean isComissaoconsultor() {
		return comissaoconsultor;
	}

	public void setComissaoconsultor(boolean comissaoconsultor) {
		this.comissaoconsultor = comissaoconsultor;
	}

	public boolean isDescontotm() {
		return descontotm;
	}

	public void setDescontotm(boolean descontotm) {
		this.descontotm = descontotm;
	}

	public boolean isRelatorioleads() {
		return relatorioleads;
	}

	public void setRelatorioleads(boolean relatorioleads) {
		this.relatorioleads = relatorioleads;
	}

	public boolean isRelatoriocliente() {
		return relatoriocliente;
	}

	public void setRelatoriocliente(boolean relatoriocliente) {
		this.relatoriocliente = relatoriocliente;
	}

	public boolean isRelatorioleadsdetalhado() {
		return relatorioleadsdetalhado;
	}

	public void setRelatorioleadsdetalhado(boolean relatorioleadsdetalhado) {
		this.relatorioleadsdetalhado = relatorioleadsdetalhado;
	}

	public boolean isRelatoriocartaocredito() {
		return relatoriocartaocredito;
	}

	public void setRelatoriocartaocredito(boolean relatoriocartaocredito) {
		this.relatoriocartaocredito = relatoriocartaocredito;
	}

	public boolean isEditardocsvideos() {
		return editardocsvideos;
	}

	public void setEditardocsvideos(boolean editardocsvideos) {
		this.editardocsvideos = editardocsvideos;
	}

	public boolean isExcluirdocsvideos() {
		return excluirdocsvideos;
	}

	public void setExcluirdocsvideos(boolean excluirdocsvideos) {
		this.excluirdocsvideos = excluirdocsvideos;
	}

	public boolean isEventoscontasreceber() {
		return eventoscontasreceber;
	}

	public void setEventoscontasreceber(boolean eventoscontasreceber) {
		this.eventoscontasreceber = eventoscontasreceber;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idacesso != null ? idacesso.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Acesso)) {
			return false;
		}
		Acesso other = (Acesso) object;
		if ((this.idacesso == null && other.idacesso != null)
				|| (this.idacesso != null && !this.idacesso.equals(other.idacesso))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.travelmate.model.Acesso[ idacesso=" + idacesso + " ]";
	}

}
