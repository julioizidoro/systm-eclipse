package br.com.travelmate.bean.comissao;

import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Passagemaerea;
import br.com.travelmate.model.Passagempassageiro;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;

public class ComissaoPassagemBean {
	
	private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Parcelamentopagamento> listaParcelamento;
    private Date dataInicioPrograma;
    private float valorJuros;
    private Passagemaerea passagem;
    private List<Passagempassageiro> listaPassageiro;
    
    public ComissaoPassagemBean(AplicacaoMB aplicacaoMB, Vendas venda,List<Parcelamentopagamento> listaParcelamento, Date dataInicioPrograma, Vendascomissao vendascomissao, float valorJuros, Passagemaerea passagem,List<Passagempassageiro> listaPassageiro){
    		this.vendasComissao = vendascomissao;
        this.venda = venda;
        this.aplicacaoMB = aplicacaoMB;
        this.listaParcelamento = listaParcelamento;
        this.dataInicioPrograma = dataInicioPrograma;
        this.valorJuros = valorJuros;
        this.passagem = passagem;
        this.listaPassageiro=listaPassageiro;
        boolean gerar=true;
        if (this.vendasComissao==null) {
        		this.vendasComissao = new Vendascomissao();
        }
        if (this.vendasComissao.getFaturaFranquias()!=null){
        		if (this.vendasComissao.getFaturaFranquias().isFatura()){
        			gerar=false;
        		}
        }
        if (gerar){
        	IniciarCalculoComissao();
        }
        
    }
    
    public void IniciarCalculoComissao(){
    	CalcularComissaoBean comissaoBean = new CalcularComissaoBean();
    	if (vendasComissao==null){
            vendasComissao = new Vendascomissao();
            vendasComissao.setVendas(venda);
        }else{
        	if (vendasComissao.getVendas()==null){
        		vendasComissao.setVendas(venda);
        	}
        }
    	vendasComissao.setJuros(valorJuros);
        vendasComissao.setDescontotm(0.0f);
        vendasComissao.setDescontoloja(0.0f);
        vendasComissao.setDescontofornecedor(0.0f);
        vendasComissao.setDesagio(comissaoBean.calcularDesagio(listaParcelamento, aplicacaoMB, vendasComissao));
        vendasComissao.setTaxatm(0.0f);
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor());
        vendasComissao.setProdutos(venda.getProdutos());
        float totalOrcamento = passagem.getAdttarifa() + passagem.getChdtarifa() + passagem.getInftarifa();
        float totalComissao = passagem.getAdtcomissao() + passagem.getChdcomissao() + passagem.getInfcomissao();
        
        vendasComissao.setValorcomissionavel(totalOrcamento);
        vendasComissao.setComissaotm(passagem.getChdtaxaemissao() + passagem.getAdttaxaemissao() + passagem.getChdtaxaemissao() + totalComissao);
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
        	vendasComissao.setComissaofranquiabruta(passagem.getComissaoloja());
        	vendasComissao.setComissaofraquia(passagem.getComissaoloja());
            vendasComissao.setLiquidofranquia(vendasComissao.getComissaofraquia());
        }
        vendasComissao.setValorfornecedor(calcularValorNet());
        vendasComissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendasComissao));
        vendasComissao.setComissaoemissor(calcularComissaoEmissor());
        vendasComissao.setDatainicioprograma(dataInicioPrograma);
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        //vendasComissao.setPrevisaopagamento(Formatacao.calcularPrevisaoPagamentoFornecedor(dataInicioPrograma, vendasComissao.getProdutos().getIdprodutos(), aplicacaoMB.getParametrosprodutos().getCursos()));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissaoTurismo(vendasComissao));
        FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
    		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
    		boolean cursoPacote = false;
    		if (vendasComissao.getVendas().getVendaspacote()!=null) {
    			cursoPacote = true;
    		}
    		vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento,0.0f, aplicacaoMB, false,formapagamento, cursoPacote);
    }
    
    public float calcularValorNet(){
    	float valor = passagem.getAdttarifa() + passagem.getAdttaxas() + passagem.getInftarifa() + passagem.getInftaxas() + passagem.getChdtarifa() + passagem.getChdtaxas();
    	valor = valor - (passagem.getAdtcomissao() + passagem.getInfcomissao() + passagem.getChdcomissao());
    	return valor;
    }
    
    public Float calcularComissaoEmissor(){
    	float valor = 0.0f;
    	if (venda.getUsuario().getIdusuario()==9){
    		return valor;
    	}else {
    		if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
    			valor = (vendasComissao.getComissaotm() + vendasComissao.getTaxatm()) - vendasComissao.getDesagio();
    		}else {
    			valor = ((vendasComissao.getComissaotm() + vendasComissao.getTaxatm()) - (vendasComissao.getDesagio() +  vendasComissao.getLiquidofranquia()));
    		}
    		if (venda.getUsuario().getUnidadenegocio().getPerconsultor()>0){
    			valor = valor * (venda.getUsuario().getUnidadenegocio().getPerconsultor().floatValue()/100);
    		}else valor =0.0f;
    		return valor;
    	}
    }

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}


	public List<Parcelamentopagamento> getListaParcelamento() {
		return listaParcelamento;
	}

	public void setListaParcelamento(List<Parcelamentopagamento> listaParcelamento) {
		this.listaParcelamento = listaParcelamento;
	}

	public Date getDataInicioPrograma() {
		return dataInicioPrograma;
	}

	public void setDataInicioPrograma(Date dataInicioPrograma) {
		this.dataInicioPrograma = dataInicioPrograma;
	}

	public float getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(float valorJuros) {
		this.valorJuros = valorJuros;
	}

	public Passagemaerea getPassagem() {
		return passagem;
	}

	public void setPassagem(Passagemaerea passagem) {
		this.passagem = passagem;
	}
    
    

}
