package br.com.travelmate.managerBean.financeiro.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.RelatorioCobrancaBean;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Historicocobranca;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioCobrancaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoDocumento;
	private List<RelatorioCobrancaBean> listaRelatorio;

	
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	

	public List<RelatorioCobrancaBean> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<RelatorioCobrancaBean> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String gerarRelatorio() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		carregarListaContasReceber();
		session.setAttribute("listaRelatorio", listaRelatorio);  
		return "relatorioCobrancaFinal";
	}

	
	
	public void carregarListaContasReceber(){
		listaRelatorio = new ArrayList<RelatorioCobrancaBean>();
		int diaSemana = Formatacao.diaSemana(new Date());
		String data = "";
		if (diaSemana == 1) {
			data = Formatacao.SubtarirDatas(new Date(), 5, "yyyy-MM-dd");
		} else if (diaSemana == 7) {
			data = Formatacao.SubtarirDatas(new Date(), 4, "yyyy-MM-dd");
		}else {
			data = Formatacao.SubtarirDatas(new Date(), 3, "yyyy-MM-dd");
		}
		
    	String sql = "Select c from Contasreceber c where c.datapagamento is NULL and c.valorpago=0 and c.datavencimento<'"
    			+ data + "' and c.situacao<>'cc' ";
    	
    	if (!tipoDocumento.equalsIgnoreCase("Todos")){
    		sql = sql + " and c.tipodocumento='" + tipoDocumento + "' ";
    	}
    	sql = sql + " order by c.vendas.idvendas, c.idcontasreceber, c.datavencimento";
    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
    	List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
    	if (listaContas!=null){
    		carregarCobranca(listaContas);
    	}
    }
    
    public void carregarCobranca(List<Contasreceber> listaContas){
    	List<Contasreceber> listaContasCobranca = new ArrayList<Contasreceber>();
    	Cobranca cobranca = null;
    	float valorTotal=0.0f;
    	int idvenda =0;
    	for(int i=0;i<listaContas.size();i++){
    		if (idvenda==listaContas.get(i).getVendas().getIdvendas()){
    			listaContasCobranca.add(listaContas.get(i));
    			valorTotal = valorTotal + listaContas.get(i).getValorparcela();
    		}else if (idvenda==0){
    			idvenda = listaContas.get(i).getVendas().getIdvendas();
    			if (listaContas.get(i).getVendas().getCobrancaList()!=null){
    				if (listaContas.get(i).getVendas().getCobrancaList().size()>0){
    					cobranca=listaContas.get(i).getVendas().getCobrancaList().get(0);
    				}
    			}
    			if (cobranca==null){
    				cobranca=new Cobranca();
    				cobranca.setVendas(listaContas.get(i).getVendas());
    				cobranca.setHistoricocobrancaList(new ArrayList<Historicocobranca>());
    				cobranca.setEmail("SEM COBRANÇA ATIVA");
    				cobranca.setFone("");
    				cobranca.setFone2("");
    			}
    			listaContasCobranca.add(listaContas.get(i));
    			valorTotal = valorTotal + listaContas.get(i).getValorparcela();
    		}
    		if ((i+1)<listaContas.size()){
    			if (idvenda!=listaContas.get(i+1).getVendas().getIdvendas()){
    				incluirListaRelatorio(cobranca, listaContasCobranca, valorTotal);
    				cobranca = null;
    				listaContasCobranca =  new ArrayList<Contasreceber>();
    				idvenda=0;
    				valorTotal=0.0f;
    			}
    		}
    	}
    }
    
    
    public void incluirListaRelatorio(Cobranca cobranca, List<Contasreceber> listaContas, float valorTotal){
    	RelatorioCobrancaBean relatorio = new RelatorioCobrancaBean();
    	relatorio.setCobranca(cobranca);
    	relatorio.setListaContas(listaContas);
    	relatorio.setValorTotal(valorTotal);
    	listaRelatorio.add(relatorio);
    }
}
