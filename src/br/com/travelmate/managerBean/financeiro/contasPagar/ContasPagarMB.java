package br.com.travelmate.managerBean.financeiro.contasPagar;

import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


@Named
@ViewScoped
public class ContasPagarMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contaspagar conta;
    private List<Contaspagar> listaContasPagar;
    private List<Unidadenegocio> listaUnidadeNegocio;
    private List<Planoconta> listaPlanoConta;
    private Unidadenegocio unidadeNegocio;
    private Planoconta planoConta;
    private Date dataInicial;
    private Date dataFinal;
    private String descricao;
    private int id;
    private String sql;
    private String competencia;
    private List<Banco> listaBanco;
    private Banco banco;

	@PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conta = (Contaspagar) session.getAttribute("contaspagar");
        session.removeAttribute("contaspagar");
        //carregarContasPagar();
        conta = new Contaspagar();
        carregarPlanoConta();
        carregarUnidadeNegocio();
        listaContasPagar = new ArrayList<Contaspagar>();
        listaBanco = GerarListas.listarBancos();
    }

    public Contaspagar getConta() {
        return conta;
    }

    public void setConta(Contaspagar conta) {
        this.conta = conta;
    }

    public List<Contaspagar> getListaContasPagar() {
        return listaContasPagar;
    }

    public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
        this.listaContasPagar = listaContasPagar;
    }
    
    public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public List<Planoconta> getListaPlanoConta() {
		return listaPlanoConta;
	}

	public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
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
	public Unidadenegocio getUnidadeNegocio() {
		return unidadeNegocio;
	}

	public void setUnidadeNegocio(Unidadenegocio unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}

	public Planoconta getPlanoConta() {
		return planoConta;
	}

	public void setPlanoConta(Planoconta planoConta) {
		this.planoConta = planoConta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public String cadastrarContaPagar(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",570);
        RequestContext.getCurrentInstance().openDialog("confContasPagar", options, null);
        return "";
    }
    
    
    /*public void carregarContasPagar(){
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        String sql = "Select c from Contaspagar c order by c.datavencimento";
        listaContasPagar = contasPagarFacade.listar(sql);
        if (listaContasPagar==null){
            listaContasPagar = new ArrayList<Contaspagar>();
        }
    }*/
     
     public String editar(Contaspagar conta){
        if (conta!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contapagar", conta);       
            Map<String,Object> options = new HashMap<String, Object>();
            options.put("contentWidth",570);
            RequestContext.getCurrentInstance().openDialog("confContasPagar", options, null);
        }
        return "";
    }
    
     
     public String excluir(Contaspagar conta){
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        contasPagarFacade.excluir(conta.getIdcontaspagar());
        listaContasPagar.remove(conta);
        new EventoContasPagarBean("Exclusão", conta);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com Sucesso", ""));
        return "";
     }
     
	public void retornoDialogNovo(SelectEvent event) {
		if (event != null) {
			Contaspagar contaspagar = (Contaspagar) event.getObject();
			if (contaspagar.getIdcontaspagar() != null) {
				listaContasPagar.add(contaspagar);
			}
		}
	}
     
     public void carregarUnidadeNegocio(){
         UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
         listaUnidadeNegocio = unidadeNegocioFacade.listarContasPagar();
         if (listaUnidadeNegocio==null){
             listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
         }
     }
     
     public void carregarPlanoConta(){
         PlanoContaFacade planoContaFacade = new PlanoContaFacade();
         listaPlanoConta = planoContaFacade.listar("");
         if (listaPlanoConta==null){
             listaPlanoConta = new ArrayList<Planoconta>();
         }
     }
     
     public void gerarPesquisa(){
     	if (descricao==null){
     		descricao="";
     	}
	    sql = "Select c from Contaspagar c where c.descricao like '%"  + descricao +"%'";
     	if (unidadeNegocio!=null){
     		sql = sql+" and c.unidadenegocio.idunidadeNegocio=" + unidadeNegocio.getIdunidadeNegocio();
     	}
     	if ((dataInicial!=null) && (dataFinal!=null)){
     		sql = sql +  " and c.datacompensacao>='" + Formatacao.ConvercaoDataSql(dataInicial) 
     	              + "' and c.datacompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal)  
     				  + "'"; 	
     	}
     	if (planoConta!=null){
     		sql = sql +" and c.planoconta.idplanoconta=" + planoConta.getIdplanoconta();
     	}
     	if (id>0){
     		sql = sql +" and c.idcontaspagar=" +id;
     	}
     	if (competencia.length()>0){
     		sql = sql +" and c.competencia='" +competencia +"' "; 
     	}
     	if (banco != null && banco.getIdbanco() != null) {
			sql = sql + " and c.banco.idbanco=" + banco.getIdbanco();
		}
     	sql = sql + " order by c.datacompensacao, c.descricao";
     	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
     	listaContasPagar = contasPagarFacade.listar(sql);
        if (listaContasPagar==null){
            listaContasPagar = new ArrayList<Contaspagar>();
        }
     }
     
     public String limparFiltro(){
    	 listaContasPagar = new ArrayList<Contaspagar>();
    	 return "";
     }
     
     public String novaTransferencia(){
         Map<String,Object> options = new HashMap<String, Object>();
         options.put("contentWidth",500);
         RequestContext.getCurrentInstance().openDialog("cadTransferencia", options, null);
         return "";
     }
     
     public void retornoDialogNovaTransferencia(SelectEvent event){
         
     }
     
     
     
     
     
}
