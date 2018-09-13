/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.contasReceber;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.PlanoContaFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Planoconta;
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
public class CadContasReceberMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject AplicacaoMB aplicacaoMB;
    private Contasreceber conta;
    private List<Banco> listaBanco;
    private String idVendas;
    private String nomeCliente;
    private Vendas vendas;
    private Date dataVencimentoOriginal;
   
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conta = (Contasreceber) session.getAttribute("contareceber");
        session.removeAttribute("contareceber");
        if (conta==null){
            conta = new Contasreceber();
        }else {
            idVendas = String.valueOf(conta.getVendas().getIdvendas());
            vendas = conta.getVendas();
            nomeCliente = vendas.getCliente().getNome();
            dataVencimentoOriginal = conta.getDatavencimento();
        }
    }

    public Contasreceber getConta() {
        return conta;
    }

    public void setConta(Contasreceber conta) {
        this.conta = conta;
    }

    public List<Banco> getListaBanco() {
        if (listaBanco == null){
            gerarListaBanco();
        }
        
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public String getIdVendas() {
        return idVendas;
    }

    public void setIdVendas(String idVendas) {
        this.idVendas = idVendas;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

	public Date getDataVencimentoOriginal() {
		return dataVencimentoOriginal;
	}

	public void setDataVencimentoOriginal(Date dataVencimentoOriginal) {
		this.dataVencimentoOriginal = dataVencimentoOriginal;
	}

	public String salvar(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        String operacao ="Alteração de contas a receber";
        if (conta.getIdcontasreceber() == null) {
            conta.setBoletocancelado(Boolean.FALSE);
            conta.setBoletoenviado(Boolean.FALSE);
            conta.setDataEmissao(new Date());
            conta.setSituacao("vm");
            conta.setDataalterada(false);
            conta.setDesagio(0.0f);
            conta.setJuros(0.0f);
            conta.setValorpago(0.0f);
            conta.setSelecionado(false);
            PlanoContaFacade planoCoontaFacade = new PlanoContaFacade();
            Planoconta plano = planoCoontaFacade.consultar(aplicacaoMB.getParametrosprodutos().getIdplanocontas());
            conta.setPlanoconta(plano);
            operacao = "Contas a receber criada pelo usuário";
        }
        conta.setVendas(vendas);
        if (conta.getIdcontasreceber() != null) {
	        if (conta.getCrmcobrancaconta() != null) {
	        	if (dataVencimentoOriginal.before(conta.getDatavencimento())) {
	    			CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
	    			crmCobrancaBean.baixar(conta, usuarioLogadoMB.getUsuario());
				}
			}
		}
        conta = contasReceberFacade.salvar(conta);
        new EventoContasReceberBean(operacao, conta, usuarioLogadoMB.getUsuario());
        RequestContext.getCurrentInstance().closeDialog(conta);
        return "";
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public void gerarListaBanco() {
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco == null) {
            listaBanco = new ArrayList<Banco>();
        }
    }

    public String buscarPoIdVenda() {
        if (idVendas.length() > 0) {
            
            this.vendas = vendasDao.consultarVendas(Integer.parseInt(idVendas));
            if (vendas == null) {
                Mensagem.lancarMensagemInfo("Erro! ", "Venda não localizada.");
            } else {
                nomeCliente = vendas.getCliente().getNome();
                conta.setNumerodocumento(String.valueOf(idVendas));
            }
        }
        return "";
        
    }
    
    public String botaovendas(){
        if(idVendas==null){
            return "false";
        }else return "true";
    }
}
