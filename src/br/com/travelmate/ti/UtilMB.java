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
import br.com.travelmate.dao.RelatorioClienteDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.dao.VendasEmbarqueDao;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.MotivoCancelamentoFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Hecontrole;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadposvenda;
import br.com.travelmate.model.Motivocancelamento;
import br.com.travelmate.model.Relatoriocliente;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Vendasembarque;
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
	private RelatorioClienteDao relatorioClienteDao;
	@Inject
	private VendasEmbarqueDao vendasEmbarqueDao;
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
	private List<Relatoriocliente> listaRelatorio;
	 
	@PostConstruct
	public void init() {  
		listaRelatorio = relatorioClienteDao.listar();
	}
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Relatoriocliente> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<Relatoriocliente> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
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
		leadposvenda.setDataembarque(controle.getVendas().getVendasembarque().getDataida());
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
			
			controle.setDatafichafinalizada(lista.get(i).getVendas().getDataVenda());
			controle.setImpresso(false);
			controle.setVistoemitido(false);
			controle.setValorcomissao(0.0f);
			controle.setHe(lista.get(i));
			heControleDao.salvar(controle);
		}
		Mensagem.lancarMensagemInfo("He Controler","Terminou");
	}
	
	public void gerarControleCursoAjustes() {
		CursoFacade cursoFacade = new CursoFacade();
		List<Controlecurso> lista = cursoFacade.listaControle("select c from Controlecurso c");
		if (lista!=null) {
			for (int i=0;i<lista.size();i++) {
				Vendasembarque embarque = new Vendasembarque();
				embarque.setDataida(lista.get(i).getDataEmbarque());
				try {
					embarque.setDatavolta(Formatacao.SomarDiasDatas(lista.get(i).getCurso().getDataTermino(), 2));
				} catch (Exception e) {
					e.printStackTrace();
				}
				embarque.setVendas(lista.get(i).getVendas());
				vendasEmbarqueDao.salvar(embarque);
			}
		}
		Mensagem.lancarMensagemInfo("Vendas Embarque","Terminou");
	}
	
	public String gerarRelatorioCliente() {
		//Gerar cliente sem vendas
		ClienteFacade clienteFacade = new ClienteFacade();
		List<Cliente> listaClientes = clienteFacade.consultarNome("");
		for(int i=0;i<listaClientes.size();i++) {
			Relatoriocliente relatorio = new Relatoriocliente();
			relatorio.setNome(listaClientes.get(i).getNome());
			relatorio.setDataNascimento(listaClientes.get(i).getDataNascimento());
			relatorio.setEmail(listaClientes.get(i).getEmail());
			relatorio.setFoneResidencial(listaClientes.get(i).getFoneResidencial());
			relatorio.setFoneCelular(listaClientes.get(i).getFoneCelular());
			relatorio.setUnidade(listaClientes.get(i).getUnidadenegocio().getNomerelatorio());
			relatorio.setProfissao(listaClientes.get(i).getProfissao());
			relatorio.setEscolaridade(listaClientes.get(i).getEscolaridade());
			if (listaClientes.get(i).getDataNascimento()!=null) {
				relatorio.setIdade(Formatacao.calcularIdade(listaClientes.get(i).getDataNascimento()));
			}
			if ((listaClientes.get(i).getListaVendas()!=null) && (listaClientes.get(i).getListaVendas().size()>0)) {
				relatorio.setPrograma(listaClientes.get(i).getListaVendas().get(0).getProdutos().getDescricao());
				relatorio.setPais(listaClientes.get(i).getListaVendas().get(0).getFornecedorcidade().getCidade().getPais().getNome());
				relatorio.setCidade(listaClientes.get(i).getListaVendas().get(0).getFornecedorcidade().getCidade().getNome());
				if (listaClientes.get(i).getListaVendas().get(0).getVendascomissao()!=null) {
					relatorio.setDatainicio(listaClientes.get(i).getListaVendas().get(0).getVendascomissao().getDatainicioprograma());
				}
				
			}else {
				relatorio.setPrograma("");
				relatorio.setPais("");
				relatorio.setCidade("");
			}
			relatorioClienteDao.salvar(relatorio);
		}
		return null;
	}

}
