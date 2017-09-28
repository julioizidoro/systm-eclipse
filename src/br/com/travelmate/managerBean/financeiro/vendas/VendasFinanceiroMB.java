package br.com.travelmate.managerBean.financeiro.vendas;

 
import br.com.travelmate.bean.comissao.CalcularComissaoManualBean;
import br.com.travelmate.facade.FaturaFranquiasFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Faturafranquias;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendapendencia;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class VendasFinanceiroMB  implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private AplicacaoMB aplicacaoMB;
    private List<Vendas> listaVendas;
    private String id;
    private String nomeCliente;
    private Date dataInical;
    private Date dataFinal;
    private Unidadenegocio unidadenegocio;
    private Produtos produto;
    private List<Unidadenegocio> listaUnidadeNegocio;
    private List<Produtos> listaProdutos;
    private String sql;
    private String situacao;
    private boolean selecionado;
    
    @PostConstruct
    public void inti(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        sql = (String) session.getAttribute("sql");
        session.removeAttribute("sql");
        gerarListaUnidadeNegocio();
        gerarListaProdutos();
    }

    public List<Vendas> getListaVendas() {
        return listaVendas;
    }

    public void setListaVendas(List<Vendas> listaVendas) {
        this.listaVendas = listaVendas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Date getDataInical() {
        return dataInical;
    }

    public void setDataInical(Date dataInical) {
        this.dataInical = dataInical;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public List<Unidadenegocio> getListaUnidadeNegocio() {
        return listaUnidadeNegocio;
    }

    public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
        this.listaUnidadeNegocio = listaUnidadeNegocio;
    }

    public List<Produtos> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
    
    public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public String informacoesAdicionais(Vendas venda){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("venda", venda);
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",570);
        RequestContext.getCurrentInstance().openDialog("informacoesAdicionais", options, null);
        return "";
    }
    
    public String produtoVendas(Vendas venda){
        if ((venda.getOrcamento()!=null)){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("venda", venda);
            Map<String,Object> options = new HashMap<String, Object>();
            options.put("contentWidth",570);
            RequestContext.getCurrentInstance().openDialog("produtoVendas", options, null);
        }
        else{
            FacesMessage msg = new FacesMessage("Venda não Possui produtos! ", " ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }
    
    public String visualizarContasReceber(Vendas venda){
        if ((venda!=null)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("venda", venda);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 720);
            RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
        }
        else{
            FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }
    
	public String editarComissao(Vendas venda) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		session.setAttribute("sql", sql);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 850);
		return "editarComissoes";
	}
    
    
    
    public String fecharEditarInformacoes(){
        
        return "";
    }
    
    public void gerarListaVendas(){
        VendasFacade vendasFacade = new VendasFacade();
        if (sql==null){
            String sData = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
            sql = "Select v From Vendas v where (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO') and v.vendasMatriz='S' and v.dataVenda>='" + sData + "'  order by v.dataVenda DESC";
        }
        listaVendas = vendasFacade.lista(sql);
        if (listaVendas==null){
            listaVendas = new ArrayList<Vendas>();
        }
    }
    
    public String filtarVendas(){
        sql = "Select v From Vendas v where v.valor>0 and v.vendasMatriz='S'";
        if ((id!=null) && (id.length()>0)){
            sql = sql + " and v.idvendas=" + id;
        }
        if ((nomeCliente!=null) && (nomeCliente.length()>0)){
            sql = sql +  " and v.cliente.nome like '%" + nomeCliente + "%'"; 
        }
        if ((dataInical!=null) && (dataFinal!=null)){
            sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInical) +
                    "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        if (unidadenegocio!=null){
            sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        if(situacao!=null && !situacao.equalsIgnoreCase("TODAS")){
        	 sql = sql + " and v.situacao='" + situacao+"'";
        }else{
        	sql = sql + " and (v.situacao='FINALIZADA' or v.situacao='ANDAMENTO')";
        }
        if (produto!=null){
            sql = sql + " and v.produtos.idprodutos=" + produto.getIdprodutos();
        }
        sql = sql + " order by v.dataVenda DESC";
        gerarListaVendas();
        return "";
    }
    
    public String limparFiltroVendas(){
    	sql = null;
    	listaVendas = new ArrayList<>();
        unidadenegocio = null;
        produto = null;
        id="";
        nomeCliente="";
        dataFinal=null;
        dataInical=null;
        return "";
    }
    
    private void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar();
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
    private void gerarListaProdutos(){
        ProdutoFacade produtoFacade = new ProdutoFacade();
        listaProdutos = produtoFacade.listarProdutos("");
        if (listaProdutos==null){
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    public String novaVendaAvulsa(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 730);
        RequestContext.getCurrentInstance().openDialog("vendaAvulsa", options, null);
    	return "";
    }
    
    public void retornoDialogNovo(SelectEvent event){
        Vendas vendas = (Vendas) event.getObject();
        listaVendas.add(vendas);
    }
    
    public String gerarContasReceber(Vendas vendas){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("vendas", vendas);       
            RequestContext.getCurrentInstance().openDialog("gerarContasReceber");
        return "";
    }
    
    public String verificarContasReceber(Vendas venda){
    	String retorno = "background:green;border:none";
    	boolean matriz = false;
    	if (venda.getFormapagamento()!=null){
    	List<Parcelamentopagamento> lista = venda.getFormapagamento().getParcelamentopagamentoList();
    	if (lista!=null){
    		if (lista.size()>0){
    			for(int i=0;i<lista.size();i++){
    				if (lista.get(i).getTipoParcelmaneto().equalsIgnoreCase("Matriz")){
    					matriz = true;
    					i=10000;
    				}
    			}
    		}
    	}
    	if (matriz){
    		if ((venda.getContasreceberList()!=null) && (venda.getContasreceberList().size()>0)){
    			retorno = "background:green;border:none";
    		}else retorno = "background:red;border:none";
    	}else retorno = "background:grey;border:none";
    	}
    	return retorno;
    }
    
    public String veriricarBackOffice(Vendas venda){
    	String retorno = "background:green;border:none";
    	if (venda.getVendascomissao()==null){
    		retorno = "background:red;border:none";
    	}else if (venda.getVendascomissao().getFaturaFranquias()==null){
    		retorno = "background:yellow;border:none";
    	}
    	return retorno;
    }
    
    public boolean desabilitarBtnFatura(Vendas venda){ 
    	if(venda.getVendascomissao()!=null){
	    		if(venda.getVendascomissao().getFaturaFranquias()!=null 
	    			&& venda.getVendascomissao().getFaturaFranquias().isApagarfatura()){
	    		return false;
	    	}else return true;
	    }else return true;
    }
    
    public void ativarFaturaFranquia(Vendas venda){
    	FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
    	venda.getVendascomissao().getFaturaFranquias().setApagarfatura(false);
    	try {
			faturaFranquiasFacade.salvar(venda.getVendascomissao().getFaturaFranquias());
		} catch (SQLException e) { 
			e.printStackTrace();
		}
    	Mensagem.lancarMensagemInfo("Fatura ativa!", ""); 
    }
    
    public void selecionarTodos(){
    	for (int i = 0; i < listaVendas.size(); i++) {
			listaVendas.get(i).setSelecionado(selecionado);
		}
    }
    
    public void recalcular() {
    	CalcularComissaoManualBean ccb = new CalcularComissaoManualBean(aplicacaoMB);
    	for (int i = 0; i < listaVendas.size(); i++) {
			if(listaVendas.get(i).isSelecionado()){
				try {
		    		if (listaVendas.get(i).getVendascomissao()!=null && listaVendas.get(i).getVendascomissao().getIdvendascomissao()!=null){
		    			if (listaVendas.get(i).getVendascomissao().getFaturaFranquias()==null){
		    				gerarFaturaFranquia(listaVendas.get(i).getVendascomissao());
		    			}
		    		}else {  
		    			Vendascomissao vendascomissao= new Vendascomissao();
	                	vendascomissao.setVendas(listaVendas.get(i));
	                	vendascomissao.setComissaoemissor(0.0f);
	                	vendascomissao.setComissaofranquiabruta(0.0f);
	                	vendascomissao.setComissaofraquia(0.0f);
	                	vendascomissao.setComissaoterceiros(0.0f);
	                	vendascomissao.setComissaogerente(0.0f);
	                	vendascomissao.setComissaotm(0.0f);
	                	vendascomissao.setDesagio(0.0f);
	                	vendascomissao.setDescontoloja(0.0f);
	                	vendascomissao.setDescontotm(0.0f);
	                	vendascomissao.setIncentivo(0.0f);
	                	vendascomissao.setLiquidofranquia(0.0f);
	                	vendascomissao.setLiquidovendas(0.0f);
	                	vendascomissao.setPaga("Não");
	                	vendascomissao.setProdutos(listaVendas.get(i).getProdutos());
	                	vendascomissao.setTaxatm(0.0f);
	                	vendascomissao.setUsuario(listaVendas.get(i).getUsuario());
	                	vendascomissao.setValorbruto(0.0f);
	                	vendascomissao.setValorcomissionavel(0.0f);
	                	vendascomissao.setValorfornecedor(0.0f);
	                	vendascomissao.setJuros(0.0f);
	                	if(vendascomissao.getTerceiros()==null){
	    	            	TerceirosFacade terceirosFacade = new TerceirosFacade();
	    	                vendascomissao.setTerceiros(terceirosFacade.consultar(1));
	                	} 
	                	listaVendas.get(i).setVendascomissao(vendascomissao); 
		    		}
					ccb.recalcular(listaVendas.get(i).getVendascomissao());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				listaVendas.get(i).setVendascomissao(ccb.getVendascomissao()); 
				VendasFacade vendasFacade = new VendasFacade();
		    	vendasFacade.salvar(listaVendas.get(i));
			}
		} 
    }
    
    public void gerarFaturaFranquia(Vendascomissao vendascomissao){
    	Faturafranquias fatura = new Faturafranquias();
    	fatura.setAno(0);
    	fatura.setMes(0);
    	fatura.setApagarfatura(false);
    	fatura.setFatura(false);
    	fatura.setLiquidopagar(0.0f);
    	fatura.setPagomatriz(0.0f);
    	fatura.setPercentualcomissao(0.0f);
    	fatura.setSelecionado(false);
    	fatura.setTotalprodutos(0.0f);
    	fatura.setVendascomissao(vendascomissao);
    	FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
    	try {
			fatura = faturaFranquiasFacade.salvar(fatura);
			vendascomissao.setFaturaFranquias(fatura);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    
    public String cadastroPendencia(Vendas venda) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("cadVendaPendencia", options, null);
		return "";
	} 
    
    
    public void retornoDialogPendencia(SelectEvent event){
    	Vendapendencia vendapendencia = (Vendapendencia) event.getObject();
    	if (vendapendencia.getIdvendapendencia() != null) {
    		gerarListaVendas();
			Mensagem.lancarMensagemInfo("Pendência feita com sucesso", "");
		}
    }
    
    
    public String retonarCorVenda(Vendas vendas){
    	if (vendas.getVendaspacote() != null) {
			return "color:red;";
		}
    	return "";
    }
    
    public String retonarCorVendaPendencia(Vendas vendas){
    	if (vendas.getSituacaofinanceiro().equalsIgnoreCase("P")) {
			return "color:red;background:red;border:1px;";
		}
    	return "";
    }
    
    
}
