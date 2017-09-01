package br.com.travelmate.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;

import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.financeiro.boleto.DadosBoletoBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;

public class GerarBoletoConsultorBean {
	

	public String gerarBoleto(List<Contasreceber> listaContas, String idvenda){
        List<Boleto> listaBoletos = new ArrayList<Boleto>();
        if (listaContas!=null){
            for(int i=0;i<listaContas.size();i++){
            	if (listaContas.get(i).getTipodocumento().equalsIgnoreCase("Boleto")){
            		listaBoletos.add(gerarClasseBoleto(listaContas.get(i)));
            	}
                
            }
        }
        if (listaBoletos.size()>0){
            DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
            dadosBoletoBean.gerarPDFS(listaBoletos, idvenda);
            ContasReceberFacade contasReceberFacade = new ContasReceberFacade(); 
        }
        return "";
    }
    
    public Boleto gerarClasseBoleto(Contasreceber conta) {
        DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        Unidadenegocio matriz = unidadeNegocioFacade.consultar(6);
        dadosBoletoBean.setAgencias(conta.getVendas().getUnidadenegocio().getBanco().getAgencia());
        dadosBoletoBean.setCarteiras(conta.getVendas().getUnidadenegocio().getBanco().getCarteira());
        dadosBoletoBean.setCnpjCedente(matriz.getCnpj());
        dadosBoletoBean.setCodigoVenda(conta.getVendas().getIdvendas());
        dadosBoletoBean.setDataDocumento(new Date());
        dadosBoletoBean.setDigitoAgencias(conta.getVendas().getUnidadenegocio().getBanco().getDigioagencia());
        dadosBoletoBean.setDigitoContas(conta.getVendas().getUnidadenegocio().getBanco().getDigitoconta());
        dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
        dadosBoletoBean.setNomeCedente(matriz.getRazaoSocial());
        dadosBoletoBean.setNomeSacado(conta.getVendas().getCliente().getNome());
        dadosBoletoBean.setNumeroContas(conta.getVendas().getUnidadenegocio().getBanco().getConta());
        String numeroParcela = conta.getNumeroparcelas().substring(0,2);
        dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(conta.getNumerodocumento(), String.valueOf(conta.getParcelaboleto()), conta.getControlenossonumero()));
        dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorparcela()));
        dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
        dadosBoletoBean.setEnderecoSacado(new Endereco());
        dadosBoletoBean.getEnderecoSacado().setBairro(conta.getVendas().getCliente().getBairro());
        dadosBoletoBean.getEnderecoSacado().setCep(conta.getVendas().getCliente().getCep());
        dadosBoletoBean.getEnderecoSacado().setComplemento(conta.getVendas().getCliente().getComplemento());
        dadosBoletoBean.getEnderecoSacado().setLocalidade(conta.getVendas().getCliente().getCidade());
        dadosBoletoBean.getEnderecoSacado().setLogradouro(conta.getVendas().getCliente().getTipologradouro() + " " + conta.getVendas().getCliente().getLogradouro());
        dadosBoletoBean.getEnderecoSacado().setNumero(conta.getVendas().getCliente().getNumero());
        dadosBoletoBean.getEnderecoSacado().setUF(UnidadeFederativa.valueOfSigla(conta.getVendas().getCliente().getEstado()));
        dadosBoletoBean.setValorJuros(Formatacao.formatarFloatString(conta.getVendas().getUnidadenegocio().getBanco().getValorjuros()));
        dadosBoletoBean.setValorMulta(Formatacao.formatarFloatString(conta.getVendas().getUnidadenegocio().getBanco().getValormulta()));
        dadosBoletoBean.criarBoleto();
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        if (conta.getDataEmissao()==null){
        	conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
        	conta.setDataEmissao(new Date());
        	conta.setSituacao("am"); 
        	conta.setBoletogerado("SIM");
            contasReceberFacade.salvar(conta);
        }
        return dadosBoletoBean.getBoleto();
    }

}
