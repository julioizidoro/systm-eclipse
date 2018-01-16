package br.com.travelmate.managerBean.unidadenegocio;

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

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.MateFaturamentoAnualFacade;
import br.com.travelmate.facade.MetaFaturamentoMensalFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Metafaturamentoanual;
import br.com.travelmate.model.Metasfaturamentomensal;
import br.com.travelmate.model.Unidadenegocio; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadUnidadeNegocioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Unidadenegocio unidadenegocio; 
	private List<Banco> listaBanco;  
	private boolean digitosTelefone;  
	private float valorMetaMensal = 0f;
	private boolean novaUnidade;
	private boolean somenteLeitura = false;
	private Banco banco;
	private boolean habilitarNovoCadastro = true;
	private boolean habilitarEdicao = false;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		unidadenegocio = (Unidadenegocio) session.getAttribute("unidadenegocio");
		session.removeAttribute("unidadenegocio");
		carregarBanco();
		if (unidadenegocio == null) {
			digitosTelefone=false;
			unidadenegocio = new Unidadenegocio();
			novaUnidade = true;
			habilitarNovoCadastro = true;
			habilitarEdicao = false;
		} else{
			novaUnidade = false;
			habilitarNovoCadastro = false;
			habilitarEdicao = true;
			if(unidadenegocio.getFone()!=null && unidadenegocio.getFone().length()>13){
				digitosTelefone=true;
			}else digitosTelefone=false;
			if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isEditarunidade()) {
				somenteLeitura = true;
			}
		}
	}
 

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public List<Banco> getListaBanco() {
		return listaBanco;
	}


	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}


	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}


	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}


	public boolean isDigitosTelefone() {
		return digitosTelefone;
	}


	public void setDigitosTelefone(boolean digitosTelefone) {
		this.digitosTelefone = digitosTelefone;
	}


	public float getValorMetaMensal() {
		return valorMetaMensal;
	}


	public void setValorMetaMensal(float valorMetaMensal) {
		this.valorMetaMensal = valorMetaMensal;
	}


	public boolean isNovaUnidade() {
		return novaUnidade;
	}


	public void setNovaUnidade(boolean novaUnidade) {
		this.novaUnidade = novaUnidade;
	}


	public boolean isSomenteLeitura() {
		return somenteLeitura;
	}


	public void setSomenteLeitura(boolean somenteLeitura) {
		this.somenteLeitura = somenteLeitura;
	}


	public Banco getBanco() {
		return banco;
	}


	public void setBanco(Banco banco) {
		this.banco = banco;
	}


	public boolean isHabilitarNovoCadastro() {
		return habilitarNovoCadastro;
	}


	public void setHabilitarNovoCadastro(boolean habilitarNovoCadastro) {
		this.habilitarNovoCadastro = habilitarNovoCadastro;
	}


	public boolean isHabilitarEdicao() {
		return habilitarEdicao;
	}


	public void setHabilitarEdicao(boolean habilitarEdicao) {
		this.habilitarEdicao = habilitarEdicao;
	}


	public boolean validarDados() {
		if (banco == null || banco.getIdbanco() == null) {
			Mensagem.lancarMensagemInfo("Banco não selecionado.", "");
			return false;
		}  
		if (unidadenegocio.getEmail() == null || unidadenegocio.getEmail().length() < 2) {
			Mensagem.lancarMensagemInfo("Email não informado.", "");
			return false;
		} 
		if (unidadenegocio.getRazaoSocial() == null || unidadenegocio.getRazaoSocial().length() < 2) {
			Mensagem.lancarMensagemInfo("Razão social não informado.", "");
			return false;
		}
		if (unidadenegocio.getNomeFantasia() == null || unidadenegocio.getNomeFantasia().length() < 2) {
			Mensagem.lancarMensagemInfo("Razão social não informado.", "");
			return false;
		}
		if (unidadenegocio.getNomerelatorio() == null || unidadenegocio.getNomerelatorio().length() < 2) {
			Mensagem.lancarMensagemInfo("Nome relatório não informado.", "");
			return false;
		}
		return true;
	}  

	public String salvar() {
		if (validarDados()) {
			unidadenegocio.setBanco(banco);
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			unidadenegocio.setSituacao(true); 
			if(unidadenegocio.getIdunidadeNegocio()==null){
				if(digitosTelefone){
					unidadenegocio.setDigitosTelefone(9);
				}else{
					unidadenegocio.setDigitosTelefone(8);
				}
			}
			unidadenegocio = unidadeNegocioFacade.salvar(unidadenegocio);
			if (novaUnidade) {
				salvarMetaFaturamento();
			}
			if (!novaUnidade) {
				return "consUnidade";
			}
		}   
		return ""; 
	}
	
	
	public String salvarMeta() {
		String msg = "";
		if (msg.length() < 2) {
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			unidadenegocio.setSituacao(true); 
			if(unidadenegocio.getIdunidadeNegocio()==null){
				if(digitosTelefone){
					unidadenegocio.setDigitosTelefone(9);
				}else{
					unidadenegocio.setDigitosTelefone(8);
				}
			}
			unidadenegocio = unidadeNegocioFacade.salvar(unidadenegocio);
			if (!novaUnidade) {
				return "consUnidade";
			}
			return "";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}
 
	public String cancelar() {
		return "consUnidade";
	}
	
	
	public void carregarBanco(){
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco==null){
            listaBanco = new ArrayList<Banco>();
        }
    } 
	
	
	public String validarMascaraFone(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefone);
	}
	
	public String salvarMetaFaturamento(){
		MateFaturamentoAnualFacade mateFaturamentoAnualFacade = new MateFaturamentoAnualFacade();
		MetaFaturamentoMensalFacade metaFaturamentoMensalFacade = new MetaFaturamentoMensalFacade();
		Metafaturamentoanual metafaturamentoanual = new Metafaturamentoanual();
		Metasfaturamentomensal metasfaturamentomensal = new Metasfaturamentomensal();
		Date dataAtual = new Date();
		int numeroMes = Formatacao.getMesData(dataAtual);
		int numeroTotalMes = (12- numeroMes) + 1;
		int ano = Formatacao.getAnoData(dataAtual);
		float valorMetaTotal = valorMetaMensal * numeroMes;
		metafaturamentoanual.setAno(ano);
		metafaturamentoanual.setMes(0);
		metafaturamentoanual.setMetaalcancada(0f);
		metafaturamentoanual.setPercentualalcancado(0f);
		metafaturamentoanual.setUnidadenegocio(unidadenegocio);
		metafaturamentoanual.setValormeta(valorMetaTotal);
		metafaturamentoanual.setUnidadenegocio(unidadenegocio);
		mateFaturamentoAnualFacade.salvar(metafaturamentoanual);
		metasfaturamentomensal.setAno(ano);
		metasfaturamentomensal.setMes(numeroMes);
		metasfaturamentomensal.setValoralcancado(0f);
		metasfaturamentomensal.setValormeta(valorMetaMensal);
		metasfaturamentomensal.setValormetasemana(valorMetaMensal / 4);
		metasfaturamentomensal.setPercentualalcancado(0f);
		metasfaturamentomensal.setUnidadenegocio(unidadenegocio);
		metaFaturamentoMensalFacade.salvar(metasfaturamentomensal);
		for (int i = 0; i < numeroTotalMes; i++) {
			metasfaturamentomensal = new Metasfaturamentomensal();
			metasfaturamentomensal.setAno(ano);
			metasfaturamentomensal.setMes(numeroMes + (i + 1));
			metasfaturamentomensal.setValoralcancado(0f);
			metasfaturamentomensal.setValormeta(valorMetaMensal);
			metasfaturamentomensal.setValormetasemana(valorMetaMensal / 4);
			metasfaturamentomensal.setPercentualalcancado(0f);
			metasfaturamentomensal.setUnidadenegocio(unidadenegocio);
			metaFaturamentoMensalFacade.salvar(metasfaturamentomensal);
		}
		return "consUnidade";
	}
}
