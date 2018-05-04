/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.RetornoArquivoFacade;
import br.com.travelmate.facade.RetornoContasFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RetornoBean;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Retornoarquivo;
import br.com.travelmate.model.Retornocontas;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
public class LerRetornoItauBean {
	
	private List<RetornoBean> listaRetorno;
	private Usuario usuario;
	private String nomeArquivo; 
	
	public LerRetornoItauBean(BufferedReader retorno, String nomeArquivo, Usuario usuario) { 
		this.nomeArquivo = nomeArquivo;
		this.usuario = usuario;
        try {
        	listaRetorno = new ArrayList<RetornoBean>();
            lerArquivo(retorno);
        } catch (Exception ex) {
            Logger.getLogger(LerRetornoItauBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public List<RetornoBean> getListaRetorno() {
		return listaRetorno;
	}



	public void setListaRetorno(List<RetornoBean> listaRetorno) {
		this.listaRetorno = listaRetorno;
	}



	private  void lerArquivo(BufferedReader retorno) throws Exception{
        String linha ="";
        while (linha != null) {
            linha = retorno.readLine();
           	if (registarDados(linha)){
            	registarRelatorio(linha);
            }
		}
    }
    
    private boolean registarDados(String linha) {
        if (linha != null) {
            String registro = linha.substring(0, 1);
            if (registro.equalsIgnoreCase("1")) {
                String nossoNumero = linha.substring(62, 71);
                nossoNumero = nossoNumero.trim();
                String codigoOcorrencia = linha.substring(108,110);
                codigoOcorrencia.trim();
                String dataPagamento = linha.substring(110, 116);
                dataPagamento.trim();
                String juros = linha.substring(266, 279);
                juros.trim();
                juros = Formatacao.colcoarVirgulaValor(juros);
                String valorPago = linha.substring(153, 165);
                valorPago.trim();
                valorPago = Formatacao.colcoarVirgulaValor(valorPago);
                return registarRecebimento(nossoNumero, dataPagamento, valorPago, juros, codigoOcorrencia);
            }
        }
        return false;
    }
    
    private void registarRelatorio(String linha) {
        if (linha != null) {
            String registro = linha.substring(0, 1);
            if (registro.equalsIgnoreCase("1")) {
            	String inscricao = linha.substring(3, 17);
                String nossoNumero = linha.substring(62, 71);
                nossoNumero = nossoNumero.trim();
                String codigoOcorrencia = linha.substring(108,111);
                codigoOcorrencia.trim();
                String dataPagamento = linha.substring(110, 116);
                dataPagamento.trim();
                String juros = linha.substring(266, 279);
                juros.trim();
                juros = Formatacao.colcoarVirgulaValor(juros);
                String valorPago = linha.substring(153, 165);
                valorPago.trim();
                valorPago = Formatacao.colcoarVirgulaValor(valorPago);
                String nomePagador =  linha.substring(324, 355);
                RetornoBean retornoBean = new RetornoBean();
                Date data = null;
				try {
					data = converterData(dataPagamento);
				} catch (Exception e) {
					e.printStackTrace();
				}
                retornoBean.setDataPagamento(Formatacao.ConvercaoDataPadrao(data));
                retornoBean.setInscricao(inscricao);
                retornoBean.setNomePagador(nomePagador);
                retornoBean.setNossoNumero(nossoNumero);
                retornoBean.setValorJuros(Formatacao.formatarStringfloat(juros));
                retornoBean.setValorTitulo(Formatacao.formatarStringfloat(valorPago));
                retornoBean.setCodigoOcorrencia(codigoOcorrencia);
                listaRetorno.add(retornoBean);
            }
        }
    }
    
	public boolean registarRecebimento(String nossoNumero, String dataPagamento, String valorPago, String juros,
			String ocorrencia) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		RetornoArquivoFacade retornoArquivoFacade = new RetornoArquivoFacade();
		RetornoContasFacade retornoContasFacade = new RetornoContasFacade();
		Retornoarquivo retornoarquivo = new Retornoarquivo();
		Retornocontas retornocontas = new Retornocontas();
		String sql = "Select c from Contasreceber c where c.nossonumero='" + nossoNumero
				+ "' and c.valorpago=0 and c.boletocancelado=0 and c.situacao<>'cc'" ;
		Contasreceber conta = contasReceberFacade.consultar(sql);
		if (conta != null) {
			if (ocorrencia.equalsIgnoreCase("06")) {
				Float vJuros = Formatacao.formatarStringfloat(juros);
				if (vJuros > 0) {
					conta.setJuros(vJuros);
				}
				conta.setValorpago(Formatacao.formatarStringfloat(valorPago) + vJuros);
				try {
					conta.setDataRetorno(new Date());
					conta.setDatapagamento(converterData(dataPagamento));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (ocorrencia.equalsIgnoreCase("02")) {
				conta.setSituacao("vd");
			}
			conta.setDataRetorno(new Date());
			conta.setCodigoocorrencia(ocorrencia);
			conta = contasReceberFacade.salvar(conta);
			CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
			crmCobrancaBean.baixar(conta, usuario);
			retornoarquivo.setNomeaquivo(nomeArquivo);
			retornoarquivo.setUsuario(usuario);
			retornoarquivo.setDataretorno(new Date());
			retornoArquivoFacade.salvar(retornoarquivo);
			retornocontas.setRetornoarquivo(retornoarquivo);
			retornocontas.setContasreceber(conta);
			retornocontas.setCodigoocorrencia(ocorrencia);
			retornoContasFacade.salvar(retornocontas);
			registrarOutrosLancamentos(conta);
			return true;
		}
		return false;
	}
	
	public void registrarOutrosLancamentos(Contasreceber conta){
		Contaspagar contaspagar = new Contaspagar();
		conta.setBanco(conta.getBanco());
		contaspagar.setCompetencia(Formatacao.gerarCopetencia(conta.getDatavencimento()));
		contaspagar.setDatacompensacao(conta.getDatapagamento());
		contaspagar.setDataEmissao(conta.getDataEmissao());
		contaspagar.setDatavencimento(conta.getDatavencimento());
		contaspagar.setDescricao("Recebimento - " + conta.getTipodocumento() +" " + conta.getNumeroparcelas() +  " - "  + conta.getVendas().getCliente().getNome() + "(" + conta.getNumerodocumento() + ")");  
		contaspagar.setIdcontasreceber(conta.getIdcontasreceber());
		contaspagar.setPlanoconta(conta.getPlanoconta());
		if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			Unidadenegocio un = unidadeNegocioFacade.consultar(6);
			contaspagar.setUnidadenegocio(un);
		}else {
			contaspagar.setUnidadenegocio(conta.getVendas().getUnidadenegocio());
		}
		contaspagar.setValorentrada(conta.getValorpago());
		contaspagar.setValorsaida(0.0f);
		contaspagar.setBanco(conta.getVendas().getUnidadenegocio().getBanco());
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		contasPagarFacade.salvar(contaspagar);
		EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Recebimento pelo SysTM", conta, usuario);
	}
    
    
    
    public Date converterData(String sData) throws Exception{
        String dataPagamento = sData.substring(0, 2) + "/" +  sData.substring(2, 4) + "/20" + sData.substring(4, 6);
        Date data= Formatacao.ConvercaoStringData(dataPagamento);
        int diaSemana = Formatacao.diaSemana(data);
        if (diaSemana==5){
        	data = Formatacao.SomarDiasDatas(data,2);
        }else if (diaSemana==6){
        	data = Formatacao.SomarDiasDatas(data,1);
        }
        return data;
    }
    
    
    
   
   
    
}
