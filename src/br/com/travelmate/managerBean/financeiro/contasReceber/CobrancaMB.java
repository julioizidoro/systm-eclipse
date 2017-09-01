/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.contasReceber;

import br.com.travelmate.bean.BolinhasBean;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.CobrancaFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HistoricoCobrancaFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Historicocobranca;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class CobrancaMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Vendas venda;
    private Cobranca cobranca;
    private List<Historicocobranca> listaHistorico;
    private Historicocobranca historico;
    private List<Contasreceber> listaContas;
    private String dataInicio;
    private boolean digitosTelefone1;
    private boolean digitosTelefone2;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();  
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);  
        venda = (Vendas) session.getAttribute("venda");
        session.removeAttribute("venda");
        if (venda==null){
            venda = new Vendas();
        }else {
            if (venda.getCobrancaList().size()>0){
                cobranca = venda.getCobrancaList().get(0);
            }
        }
        if (cobranca==null){
            cobranca = new Cobranca();
            listaHistorico = new ArrayList<Historicocobranca>();
            cobranca.setVendas(venda);
            
        }else {
            gerarListaHistorico();
        }
        historico = new Historicocobranca();
        if(cobranca.getFone2()==null){
        	cobranca.setFone2(venda.getCliente().getFoneCelular());
        }
        if(cobranca.getFone()==null){
        	cobranca.setFone(venda.getCliente().getFoneResidencial());
        }
        if(cobranca.getEmail()==null){
        	cobranca.setEmail(venda.getCliente().getEmail());
    	}
        if(cobranca.getVendas().getCliente().getDatainicioprograma()!=null){
        	dataInicio=venda.getCliente().getDatainicioprograma();
        }
        carregarContasReceber();
        digitosTelefone1 = aplicacaoMB.checkBoxTelefone(
        		venda.getUnidadenegocio().getDigitosTelefone(), cobranca.getFone());
        digitosTelefone2 = aplicacaoMB.checkBoxTelefone(
        		venda.getUnidadenegocio().getDigitosTelefone(), cobranca.getFone2());
    }
    
   

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
    }

    public List<Contasreceber> getListaContas() {
		return listaContas;
	}



	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}



	public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public List<Historicocobranca> getListaHistorico() {
        return listaHistorico;
    }

    public void setListaHistorico(List<Historicocobranca> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    public Historicocobranca getHistorico() {
        return historico;
    }

    public void setHistorico(Historicocobranca historico) {
        this.historico = historico;
    }
    
    public String getDataInicio() {
		return dataInicio;
	}
    
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}
	
	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isDigitosTelefone1() {
		return digitosTelefone1;
	}

	public void setDigitosTelefone1(boolean digitosTelefone1) {
		this.digitosTelefone1 = digitosTelefone1;
	}

	public boolean isDigitosTelefone2() {
		return digitosTelefone2;
	}

	public void setDigitosTelefone2(boolean digitosTelefone2) {
		this.digitosTelefone2 = digitosTelefone2;
	}

	public void salvarFoneCobranca(){
    	 VendasFacade vendasFacade =  new VendasFacade();
    	 venda = vendasFacade.salvar(venda);
         CobrancaFacade cobrancaFacade = new CobrancaFacade();
         cobranca.setVendas(venda);
         cobranca = cobrancaFacade.salvar(cobranca);
         ClienteFacade clienteFacade = new ClienteFacade();
         Cliente cliente = venda.getCliente();
         cliente.setDatainicioprograma(dataInicio);
         cliente = clienteFacade.salvar(cliente);
         Mensagem.lancarMensagemInfo("Dados salvo com Sucesso! ", "");
    }
    
     
    public void verHistorico(Historicocobranca historico){
        this.historico = historico;
    }
    
    public String voltar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    public String cancelar(){
        return "consContasReceber";
    }
    
    public void novoHistorico(){
        historico = new Historicocobranca();
        historico.setData(new Date());
    }
    
    public void salvarHitorico(){
        CobrancaFacade cobrancaFacade = new CobrancaFacade();
        if (cobranca.getIdcobranca()==null){
            cobranca.setVendas(venda);
            cobranca = cobrancaFacade.salvar(cobranca);
        }
        historico.setData(new Date());
        historico.setCobranca(cobranca);
        historico.setUsuario(usuarioLogadoMB.getUsuario());
        historico = cobrancaFacade.salvar(historico); 
        Mensagem.lancarMensagemInfo("Salvo com Sucesso! ", "Historico de Cobrança Salvo.");
        gerarListaHistorico();
    }
    
    
    
    public String editarHistorico() { 
        if (historico!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("historico", historico);
            session.setAttribute("cobranca", cobranca);
        }
        return "";
    }
    
    
    public void carregarContasReceber() {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql =  "Select c From Contasreceber c where c.vendas.idvendas=" + venda.getIdvendas()+ " order by c.datavencimento, c.vendas.cliente.nome";
		listaContas = contasReceberFacade.listar(sql);
		if (listaContas == null) {
			listaContas = new ArrayList<Contasreceber>();
		} else {
			for (int i = 0; i < listaContas.size(); i++) {
				listaContas.get(i).setBolinhas(verStatus(listaContas.get(i)));
			}
		}
	}
    
    public BolinhasBean verStatus(Contasreceber contasreceber) {
		BolinhasBean bolinhasBean = new BolinhasBean();
		if (contasreceber.getSituacao().equalsIgnoreCase("vd")) {
			bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
			bolinhasBean.setCor("Verde");
			return bolinhasBean;
		} else {
			if (contasreceber.getSituacao().equalsIgnoreCase("vm")) {
				bolinhasBean.setCaminho("../../resources/img/bolaVermelha.png");
				bolinhasBean.setCor("Vermelha");
				return bolinhasBean;
			} else {
				if (contasreceber.getSituacao().equalsIgnoreCase("am")) {
					bolinhasBean.setCaminho("../../resources/img/bolaAmarela.png");
					bolinhasBean.setCor("Amarela");
					return bolinhasBean;
				}
			}
		}
		bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
		bolinhasBean.setCor("Verde");
		return bolinhasBean;
	}
    
    
    public String validarTelefone1(){
    	return aplicacaoMB.validarMascaraTelefone(digitosTelefone1);
    }
    
    public String validarTelefone2(){
    	return aplicacaoMB.validarMascaraTelefone(digitosTelefone2);
    }
   
    public void gerarListaHistorico(){
    	String sql ="select h From Historicocobranca h where h.cobranca.idcobranca="+cobranca.getIdcobranca();
    	HistoricoCobrancaFacade historicoCobrancaFacade = new HistoricoCobrancaFacade();
    	listaHistorico = historicoCobrancaFacade.listar(sql);
    }
}


