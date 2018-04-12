package br.com.travelmate.bean.comissao;

import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.PacotePassagemPassageiroFacade;
import br.com.travelmate.facade.PacotesPassagemFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotepassagempassageiro;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;

public class ComissaoPacotesBean {
	
	private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Parcelamentopagamento> listaParcelamento;
    private Date dataInicioPrograma;
    private Float valorComissaoMatriz;
    private float valorJuros;
    private Pacotes pacote;
    private List<Pacotetrecho> listaTrecho;
    
    public ComissaoPacotesBean(AplicacaoMB aplicacaoMB, Vendas venda,List<Parcelamentopagamento> listaParcelamento, Date dataInicioPrograma, Vendascomissao vendascomissao, float valorJuros, Pacotes pacote, float totalTarifa, List<Pacotetrecho> listaTrecho){
    	this.vendasComissao = vendascomissao;
        this.venda = venda;
        this.aplicacaoMB = aplicacaoMB;
        this.listaParcelamento = listaParcelamento;
        this.dataInicioPrograma = dataInicioPrograma;
        this.valorJuros = valorJuros;
        this.valorComissaoMatriz = totalTarifa;
        this.pacote = pacote;
        this.listaTrecho = listaTrecho;
        boolean gerar=true;
        if (vendascomissao.getFaturaFranquias()!=null){
        	if (vendasComissao.getFaturaFranquias().isFatura()){
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
        vendasComissao.setValorbruto(pacote.getValorgross());
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorcomissionavel(this.valorComissaoMatriz);
        for (int i = 0; i < listaTrecho.size(); i++) {
			if(listaTrecho!=null && listaTrecho.size()>0){
				PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
				List<Pacotepassagem> listaPassagem = pacotesPassagemFacade.lista("select p from Pacotepassagem p where p.pacotetrecho.idpacotetrecho="+listaTrecho.get(i).getIdpacotetrecho());
				for (int j = 0; j < listaPassagem.size(); j++) {
					PacotePassagemPassageiroFacade pacotePassagemPassageiroFacade =  new PacotePassagemPassageiroFacade();
			    	String sql = "SELECT p From Pacotepassagempassageiro p where p.pacotepassagem.idpacotepassagem="+listaPassagem.get(j).getIdpacotepassagem();
			   	 	List<Pacotepassagempassageiro> listaPassageiros =  pacotePassagemPassageiroFacade.lista(sql);
			   	 	if(listaPassageiros!=null && listaPassageiros.size()>0){
			   	 		for (int k = 0; k < listaPassageiros.size(); k++) {
			   	 			if(listaPassageiros.get(k).getCategoria().equalsIgnoreCase("ADT")){
			   	 				pacote.setComissao(pacote.getComissao()+listaPassagem.get(j).getAdtcomissao());
			   	 			pacote.setComissao(pacote.getComissao()+listaPassagem.get(j).getAdttaxaemissao());
			   	 			}else if(listaPassageiros.get(k).getCategoria().equalsIgnoreCase("CHD")){
			   	 				pacote.setComissao(pacote.getComissao()+listaPassagem.get(j).getChdcomissao());
			   	 			pacote.setComissao(pacote.getComissao()+listaPassagem.get(j).getChdtaxaemissao());
			   	 			}else if(listaPassageiros.get(k).getCategoria().equalsIgnoreCase("INF")){
			   	 				pacote.setComissao(pacote.getComissao()+listaPassagem.get(j).getInfcomissao());
			   	 			pacote.setComissao(pacote.getComissao()+listaPassagem.get(j).getInftaxaemissao());
			   	 			}
						} 
			   	 	}
				}
			}
		}
        vendasComissao.setComissaotm(pacote.getComissao());
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
        	vendasComissao.setComissaofranquiabruta(pacote.getComissaoloja());
        	vendasComissao.setComissaofraquia(comissaoBean.calcularComissaoFranquiaTurismo(vendasComissao, 0.0f));
        	vendasComissao.setLiquidofranquia(vendasComissao.getComissaofraquia());
        }
        vendasComissao.setValorfornecedor(pacote.getValornet());
        vendasComissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendasComissao));
        vendasComissao.setComissaoemissor(calcularComissaoEmissor());
        vendasComissao.setDatainicioprograma(dataInicioPrograma);
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        //vendasComissao.setPrevisaopagamento(Formatacao.calcularPrevisaoPagamentoFornecedor(dataInicioPrograma, vendasComissao.getProdutos().getIdprodutos(), aplicacaoMB.getParametrosprodutos().getCursos()));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
    		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
    		boolean cursoPacote = false;
    		if (vendasComissao.getVendas().getVendaspacote()!=null) {
    			cursoPacote = true;
    		}
    		vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento,0.0f, aplicacaoMB, false, formapagamento, cursoPacote);
    }
    
    public Float calcularComissaoEmissor(){
    	float valor = 0.0f;
    	if (venda.getUsuario().getIdusuario()==9){
    		return valor;
    	}else {
    		if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
    			valor = (pacote.getComissao() + vendasComissao.getTaxatm()) - vendasComissao.getDesagio();
    		}else {
    			valor = ((pacote.getComissao() + vendasComissao.getTaxatm()) - (vendasComissao.getDesagio() + vendasComissao.getLiquidofranquia()));
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

	public Float getValorComissaoMatriz() {
		return valorComissaoMatriz;
	}

	public void setValorComissaoMatriz(Float valorComissaoMatriz) {
		this.valorComissaoMatriz = valorComissaoMatriz;
	}

	public float getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(float valorJuros) {
		this.valorJuros = valorJuros;
	}

	public Pacotes getPacote() {
		return pacote;
	}

	public void setPacote(Pacotes pacote) {
		this.pacote = pacote;
	}

}
