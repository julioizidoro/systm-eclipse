/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.boleto;

import br.com.travelmate.Interface.ArquivoRemessaItau;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import java.io.IOException;
	import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Pattern;




/**
 *
 * @author Wolverine
 */
public class ArquivoRemessaEnviar implements ArquivoRemessaItau{
    
    
    private String branco = "                                        ";
    private String zeros = "000000000000000000000";
    
    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    
    public String gerarHeader(Contasreceber conta, int numeroSequencial, Unidadenegocio unidade, String agencia, String contaBanco, String digitoConta) throws IOException{
        String linha="";
        linha = linha  + ("0");
        linha = linha  + ("1");
        linha = linha  + ("REMESSA");
        linha = linha  + ("01");
        linha = linha  + ("COBRANCA       ");
        linha = linha  + (agencia);
        linha = linha  + ("00");
        linha = linha  + (contaBanco);
        linha = linha  + (digitoConta);
        linha = linha  + (branco.substring(0, 8));
        String nomeEmpresa = deAccent(unidade.getRazaoSocial());
        if (nomeEmpresa == null) {
			nomeEmpresa = "Sem Empresa";
		}
        nomeEmpresa = nomeEmpresa.toUpperCase();
        if (nomeEmpresa.length()<30){
            nomeEmpresa = nomeEmpresa + branco.substring(0, 30 - nomeEmpresa.length());
        }else nomeEmpresa = nomeEmpresa.substring(0,30);
        linha = linha  + (nomeEmpresa);
        linha = linha  + ("341");
        linha = linha  + ("BANCO ITAU S.A.");
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha  + (branco + branco + branco + branco + branco + branco + branco + branco.substring(0,14));
        String ns = "";
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    public String gerarDetalhe(Contasreceber conta, int numeroSequencial, Unidadenegocio unidade, String agencia, String contaBanco, String digitoConta) throws IOException, Exception{
        String linha="";
        linha = linha  + ("1");
        linha = linha  + ("02");
        linha = linha  + (Formatacao.retirarPontos(unidade.getCnpj()));
        linha = linha  + (agencia);
        linha = linha  + ("00");
        linha = linha  + (contaBanco);
        linha = linha  + (digitoConta);
        linha = linha  + (branco.substring(0, 4));
        linha = linha  + ("0000");
        linha = linha  + (branco.substring(0, 25));
        linha = linha  + (conta.getNossonumero());
        linha = linha  + ("0000000000000");
        linha = linha  + ("109");
        linha = linha  + ("000000000000000000000");
        linha = linha  + ("I");
        linha = linha  + ("01");
        linha = linha  + (conta.getNossonumero() + "  ");
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(conta.getDatavencimento()));
        String valor = Formatacao.formatarFloatString(conta.getValorparcela());
        valor = Formatacao.retirarPontos(valor);
        if (valor.length()<13){
            valor = zeros.substring(0, 13 - valor.length()) + valor;
        }
        linha = linha  + (valor);
        linha = linha  + ("341");
        linha = linha  + ("00000");
        linha = linha  + ("08");
        linha = linha  + ("N");
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha  + ("00");
        linha = linha  + ("00");
        linha = linha  + (valorJuros(conta.getValorparcela(), unidade.getBanco().getValorjuros()));
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha  + (zeros.substring(0, 13));
        linha = linha  + (zeros.substring(0, 13));
        linha = linha  + (zeros.substring(0, 13));
        linha = linha  + ("01");
        linha = linha  + (Formatacao.retirarPontos(conta.getVendas().getCliente().getCpf())+ "   ");
        String nomeCliente = deAccent(conta.getVendas().getCliente().getNome());
        if (nomeCliente == null) {
			nomeCliente = "Sem Cliente";
		}
        nomeCliente = nomeCliente.toUpperCase();
        if (nomeCliente.length()<30){
            nomeCliente = nomeCliente + branco.substring(0, 30 -nomeCliente.length());
        }else nomeCliente = nomeCliente.substring(0, 30);
        linha = linha  + (nomeCliente);
        linha = linha  + (branco.substring(0, 10));
        String endereco = "";
        String tipoLogradouro = deAccent(conta.getVendas().getCliente().getTipologradouro());
        String logra = deAccent(conta.getVendas().getCliente().getLogradouro());
        String numero = conta.getVendas().getCliente().getNumero();
        if (tipoLogradouro == null) {
			tipoLogradouro = "";
		}
        if (logra == null) {
			logra = "";
		}
        if (numero == null) {
			numero = "";
		}
        
        endereco = tipoLogradouro + " " + logra + numero;
        if (endereco == null || endereco.length() <= 1) {
			endereco = "Sem endereço";
		}
        String logradouro = endereco;
        
        logradouro = logradouro.toUpperCase();
        if (logradouro.length()<40){
            logradouro = logradouro + branco.substring(0, 40 - logradouro.length());
        }else logradouro = logradouro.substring(0, 40);
        linha = linha  + (logradouro);
        String bairro = deAccent(conta.getVendas().getCliente().getBairro());
        if (bairro == null) {
			bairro = "Sem Bairro";
		}
        bairro = bairro.toUpperCase();
        if (bairro.length()<12){
            bairro = bairro + branco.substring(0, 12 - bairro.length());
        }else bairro = bairro.substring(0,12);
        linha = linha  + (bairro);
        linha = linha  + (Formatacao.retirarPontos(conta.getVendas().getCliente().getCep()));
        String cidade = deAccent(conta.getVendas().getCliente().getCidade());
        if (cidade == null) {
			cidade = "Sem Cidade";
		}
        cidade = cidade.toUpperCase();
        if (cidade.length()<15){
            cidade = cidade + branco.substring(0, 15 - cidade.length());
        }else cidade = cidade.substring(0, 15);
        String estado = conta.getVendas().getCliente().getEstado();
        if (estado == null) {
			estado = "sn";
		}
        linha = linha  + (cidade);
        linha = linha  + (estado.toUpperCase());
        linha = linha  + (branco.substring(0,30));
        linha = linha  + ("    ");
        linha = linha  + (Formatacao.SubtarirDatas(conta.getDatavencimento(), -1, "ddMMyy"));
        linha = linha  + ("00");
        linha = linha  + (" ");
        String ns;
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    public String gerarMulta(Contasreceber conta, int numeroSequencial, Unidadenegocio unidade) throws IOException, Exception{
        String linha="";
        linha = linha  + ("2");
        linha = linha  + ("1");
        linha = linha  + (Formatacao.SubtarirDatas(conta.getDatavencimento(), -1, "ddMMyyyy"));
        linha = linha  + (valorJuros(conta.getValorparcela(), unidade.getBanco().getValormulta()));
        linha = linha  + (branco + branco + branco + branco + branco + branco + branco + branco + branco + branco.substring(0,11));
        String ns;
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    public String gerarTrailer(int numeroSequencial) throws IOException{
        String linha="";
        linha = linha  + ("9");
        linha = linha  + (branco + branco + branco + branco + branco + branco + branco + branco + branco + branco.substring(0,33));
        String ns;
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    private String valorJuros(Float valorConta, float juros){
       // Float valorJuros = valorConta * (juros/100);
       // String valor = Formatacao.retirarPontos(Formatacao.formatarFloatString(valorJuros));
        String valor = "0";
        if (valor.length()<13){
            valor = zeros.substring(0, 13 - valor.length()) + valor;
        }
        return valor;
    }
}
