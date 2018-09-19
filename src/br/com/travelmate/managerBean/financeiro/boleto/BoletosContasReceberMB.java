package br.com.travelmate.managerBean.financeiro.boleto;

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

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class BoletosContasReceberMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Curso curso;
	private List<Contasreceber> listaContas;
	
	@PostConstruct()
    public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	    curso = (Curso) session.getAttribute("curso");
	    carregarListaContasReceber(curso.getVendas().getIdvendas());
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public List<Contasreceber> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}
	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	
	
	public void carregarListaContasReceber(int idVendas){
        ContasReceberFacade contasReceberController = new ContasReceberFacade();
        String sql = "Select c From Contasreceber c where c.vendas.idvendas=" + idVendas + " and c.tipodocumento='Boleto' and c.numeroparcelas=1 and c.valorpago=0";
        listaContas = contasReceberController.listar(sql);
        if (listaContas==null){
            listaContas = new ArrayList<Contasreceber>();
        }
    }

	public void gerarBoleto1(Contasreceber contasreceber){
		gerarClasseBoleto(contasreceber);
	}
	
	
	public void gerarClasseBoleto(Contasreceber conta) {
        ClienteFacade clienteFacade = new ClienteFacade();
        Cliente cliente = clienteFacade.consultar(conta.getVendas().getCliente().getIdcliente());
        DadosBoletoBean dadosBoletoBean = new DadosBoletoBean();
        dadosBoletoBean.setAgencias(usuarioLogadoMB.getUsuario().getUnidadenegocio().getBanco().getAgencia());
        dadosBoletoBean.setCarteiras(usuarioLogadoMB.getUsuario().getUnidadenegocio().getBanco().getCarteira());
        dadosBoletoBean.setCnpjCedente(usuarioLogadoMB.getUsuario().getUnidadenegocio().getCnpj());
        dadosBoletoBean.setCodigoVenda(conta.getVendas().getIdvendas());
        dadosBoletoBean.setDataDocumento(new Date());
        dadosBoletoBean.setDigitoAgencias(usuarioLogadoMB.getUsuario().getUnidadenegocio().getBanco().getDigioagencia());
        dadosBoletoBean.setDigitoContas(usuarioLogadoMB.getUsuario().getUnidadenegocio().getBanco().getDigitoconta());
        dadosBoletoBean.setDataVencimento(conta.getDatavencimento());
        dadosBoletoBean.setNomeCedente(usuarioLogadoMB.getUsuario().getUnidadenegocio().getRazaoSocial());
        dadosBoletoBean.setNomeSacado(cliente.getNome());
        dadosBoletoBean.setNumeroContas(usuarioLogadoMB.getUsuario().getUnidadenegocio().getBanco().getConta());
        dadosBoletoBean.setNumeroDocumentos(Formatacao.gerarNumeroDocumentoBoleto(String.valueOf(conta.getIdcontasreceber())));
        dadosBoletoBean.setValor(Formatacao.converterFloatBigDecimal(conta.getValorparcela()));
        dadosBoletoBean.setNossoNumeros(dadosBoletoBean.getNumeroDocumentos());
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        conta.setNossonumero(dadosBoletoBean.getNossoNumeros());
        conta.setDataEmissao(new Date());
        conta.setHoraemissao(Formatacao.foramtarHoraString());
        conta.setSituacao("am");
        contasReceberFacade.salvar(conta);
    }
	
}
