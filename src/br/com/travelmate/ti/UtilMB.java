package br.com.travelmate.ti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct; 
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.bean.comissao.CalcularComissaoBean;
import br.com.travelmate.dao.HeControleDao;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Hecontrole;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadposvenda;
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;
 

@Named
@ViewScoped
public class UtilMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private HeControleDao heControleDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private LeadPosVendaDao leadPosVendaDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Tipocontato tipoContato;
	private Motivocancelamento motivoCancelamento;
	 
	@PostConstruct
	public void init() {  
		
	}
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public void recalcularDashboard() {
		TiBean tiBean = new TiBean(aplicacaoMB.getParametrosprodutos());
		tiBean.listarVendas();
	}
	
	public String lancarPosVenda() {
		gerarDadosComplementaresLead();
		CursoFacade cursoFacade = new CursoFacade();
		List<Controlecurso> lista = cursoFacade.listaControle("SELECT c FROM Controlecurso c where c.dataEmbarque>='2017-12-01'");
		if (lista!=null) {
			for(int i=0;i<lista.size();i++) {
				if (lista.get(i).getVendas().getIdlead()==0) {
					consultarLead(lista.get(i));
				}
			}
		}
		return "";
	}
	
	public void gerarDadosComplementaresLead() {
		MotivoCancelamentoFacade motivoCancelamentoFacade = new MotivoCancelamentoFacade();
		motivoCancelamento = motivoCancelamentoFacade.consultar("Select c From motivocancelamento c where c.idmotivocancelamento=1");
		TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
		tipoContato = tipoContatoFacade.consultar(1);
	}
	
	
	public void consultarLead(Controlecurso controle) {
		Lead lead = leadDao.consultar("Select l From Lead l where cliente.idcliente=" + controle.getVendas().getCliente().getIdcliente());
		if (lead==null) {
			lead = new Lead();
			lead.setCliente(controle.getVendas().getCliente());
			lead.setDataenvio(new Date());
			lead.setDataproximocontato(new Date());
			lead.setDatarecebimento(new Date());
			lead.setDataultimocontato(new Date());
			lead.setIdcontrole(0);
			lead.setJaecliente(true);
			lead.setMotivocancelamento1(motivoCancelamento);
			lead.setUsuario(controle.getVendas().getUsuario());
			lead.setNotas("Lancado pelo SysTM para gerar PosVenda");
			lead.setPais(controle.getVendas().getFornecedorcidade().getCidade().getPais());
			lead.setProdutos(controle.getVendas().getProdutos());
			lead.setPublicidade(controle.getVendas().getCliente().getPublicidade());
			lead.setSituacao(6);
			lead.setTipocontato(tipoContato);
			lead.setUnidadenegocio(controle.getVendas().getUnidadenegocio());
			lead = leadDao.salvar(lead);
		}
		Leadposvenda leadposvenda = new Leadposvenda();
		try {
			leadposvenda.setDatachegada(Formatacao.SomarDiasDatas(controle.getCurso().getDataTermino(), 2));
		} catch (Exception e) {
			  
		}
		leadposvenda.setDataembarque(controle.getDataEmbarque());
		leadposvenda.setLead(lead);
		leadposvenda.setVendas(controle.getVendas());
		leadposvenda = leadPosVendaDao.salvar(leadposvenda);
	}
	
	public void recalcularBO() {
		
		List<Vendas> listaVendas = vendasDao.lista("Select v FROM Vendas v");
		CalcularComissaoBean cc = new CalcularComissaoBean();
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		for (int i=0;i<listaVendas.size();i++) {
			if (listaVendas.get(i).getVendascomissao()!=null) {
				Vendascomissao vendasComissao = listaVendas.get(i).getVendascomissao();
				vendasComissao.setLiquidovendas(cc.calcularTotalComissao(vendasComissao));
				vendasComissaoFacade.salvar(vendasComissao);
			}
		}
		Mensagem.lancarMensagemInfo("Recalcular","Terminou");
	}
	
	public void gerarControleHE() {
		HeFacade heFacade = new HeFacade();
		List<He> lista = heFacade.listar("SELECT h FROM He h ");
		for (int i = 0; i < lista.size(); i++) {
			Hecontrole controle = new Hecontrole();
			if (lista.get(i).getVendas().getSituacao().equalsIgnoreCase("CANCELADA")) {
				controle.setSituacaoaplicacao("Cancelado");
			}else {
				controle.setSituacaoaplicacao("Processo");
			}
			
			controle.setDatafichafinalizada(new Date());
			controle.setImpresso(false);
			controle.setVistoemitido(false);
			controle.setValorcomissao(0.0f);
			controle.setHe(lista.get(i));
			heControleDao.salvar(controle);
		}
		Mensagem.lancarMensagemInfo("He Controler","Terminou");
	}

}
