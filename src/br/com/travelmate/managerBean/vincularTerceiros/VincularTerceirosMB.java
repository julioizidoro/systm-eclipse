package br.com.travelmate.managerBean.vincularTerceiros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.VendasComissaoFacade;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class VincularTerceirosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataInicial;
	private Date dataFinal;
	private List<Vendas> listaVendas;
	private Vendas vendas;
	private String nomeCliente;
	private String sql;
	private int idvenda;
	
	
	@PostConstruct
	public void init(){
		gerarListaVendas();
	}


	public int getIdvenda() {
		return idvenda;
	}


	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
	
	
	public List<Vendas> getListaVendas() {
		return listaVendas;
	}


	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}


	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public void gerarListaVendas(){
        
        if ((sql==null) || (sql.length()<=0)){
            String sData = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
            sql = "Select v From Vendas v where (v.situacao='FINALIZADA' OR v.situacao='ANDAMENTO') and v.vendasMatriz='S' and  v.dataVenda>='" + sData + "' ";
            if(!usuarioLogadoMB.isFinanceiro()){
            	sql=sql+" and v.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
            }
            sql=sql	+ " order by v.dataVenda DESC, v.idvendas DESC";
        }
        listaVendas = vendasDao.lista(sql);
        if (listaVendas==null){
            listaVendas = new ArrayList<Vendas>();
        }
    }
	
	public void gerarPesquisa(){
		sql = "Select v From Vendas v where (v.situacao='FINALIZADA' OR v.situacao='ANDAMENTO') and v.vendasMatriz='S' " +
	    " and v.cliente.nome like '%" + nomeCliente + "%'"; 
        if ((dataInicial!=null) && (dataFinal!=null)){
            sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) +
                    "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        if (idvenda>0){ 
        	sql = sql + " and v.idvendas=" + idvenda;   
        }
        sql = sql + " order by v.dataVenda DESC, v.idvendas DESC";
        
        listaVendas = vendasDao.lista(sql);
        if (listaVendas==null){
            listaVendas = new ArrayList<Vendas>();
        }
	}
	
	public void limparGerarPesquisa(){
    	sql = null;
        gerarListaVendas();
        nomeCliente="";
        dataFinal=null;
        dataInicial=null;
    }
	
	
	public String cadastroVinculoTerceiro(Vendas venda){
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		Vendascomissao vendascomissao;
		vendascomissao = vendasComissaoFacade.consultar(venda.getIdvendas());
		if (vendascomissao == null) {
			Mensagem.lancarMensagemInfo("Não autorizado", "Essa venda não tem comissão");
			return "";
		}
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("venda", venda);
        session.setAttribute("vendacomissao", vendascomissao);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("cadVincularTerceiros", options, null);
    	return "";
    }
	
	
	public void retornoDialogTerceiros(SelectEvent event){
		Vendas vendas = (Vendas) event.getObject();
		if (vendas.getIdvendas() != null) {
			gerarListaVendas();
			Mensagem.lancarMensagemInfo("Vinculado", "Terceiro vinculado com sucesso");
		}
	}

}
