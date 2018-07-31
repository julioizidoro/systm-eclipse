package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jfree.data.time.Year;
import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.MetasFaturamentoBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.MateFaturamentoAnualFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendaProdutoFacade;
import br.com.travelmate.facade.VersaoUsuarioFacade;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Metafaturamentoanual;
import br.com.travelmate.model.Metasfaturamentomensal;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendaproduto;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Versaousuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@SessionScoped
public class DashBoardMB implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LeadDao leadDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private String valorFaturamento;
	private float metaparcialsemana;
	private float percsemana;
	private Vendaproduto vendaproduto;
	private Metafaturamentoanual metaAnual;
	private Metasfaturamentomensal metamensal;
	private int novos;
	private int hoje;
	private int atrasadas;
	private boolean acessoResponsavelGerencial = false;
	private String imagem = "dashboard";
	private int posicao = 1;
	private boolean habilitarMateRunners = true;
	private boolean habilitarProductRunners = false;
	private boolean habilitarTmRace = false;
	private Ftpdados ftpdados;
	private boolean responsavel = false;
	private boolean fecharDistribuicao = false;
	private int numeroLeads = 0;
	private Date horaCalculo;
	

	public DashBoardMB() {
		
	}

	@PostConstruct
	public void init() { 
		//gerarDadosDashBoard(); 
		if (ftpdados==null) {
			FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			try {
				ftpdados = ftpDadosFacade.getFTPDados();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		fecharDistribuicao = verificarDistribuicaoLead();
	}
	
	

	public Date getHoraCalculo() {
		return horaCalculo;
	}

	public void setHoraCalculo(Date horaCalculo) {
		this.horaCalculo = horaCalculo;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getValorFaturamento() {
		return valorFaturamento;
	}

	public void setValorFaturamento(String valorFaturamento) {
		this.valorFaturamento = valorFaturamento;
	}

	public float getMetaparcialsemana() {
		return metaparcialsemana;
	}

	public void setMetaparcialsemana(float metaparcialsemana) {
		this.metaparcialsemana = metaparcialsemana;
	}

	public float getPercsemana() {
		return percsemana;
	}

	public void setPercsemana(float percsemana) {
		this.percsemana = percsemana;
	}

	public Vendaproduto getVendaproduto() {
		return vendaproduto;
	}

	public void setVendaproduto(Vendaproduto vendaproduto) {
		this.vendaproduto = vendaproduto;
	}

	public Metafaturamentoanual getMetaAnual() {
		return metaAnual;
	}

	public void setMetaAnual(Metafaturamentoanual metaAnual) {
		this.metaAnual = metaAnual;
	}

	public Metasfaturamentomensal getMetamensal() {
		return metamensal;
	}

	public void setMetamensal(Metasfaturamentomensal metamensal) {
		this.metamensal = metamensal;
	}

	// CAlcular Dados nova venda ou alteração de venda

	public int getNovos() {
		return novos;
	}

	public void setNovos(int novos) {
		this.novos = novos;
	}

	public int getHoje() {
		return hoje;
	}

	public void setHoje(int hoje) {
		this.hoje = hoje;
	}

	public int getAtrasadas() {
		return atrasadas;
	}

	public void setAtrasadas(int atrasadas) {
		this.atrasadas = atrasadas;
	}

	public boolean isAcessoResponsavelGerencial() {
		return acessoResponsavelGerencial;
	}

	public void setAcessoResponsavelGerencial(boolean acessoResponsavelGerencial) {
		this.acessoResponsavelGerencial = acessoResponsavelGerencial;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public boolean isHabilitarMateRunners() {
		return habilitarMateRunners;
	}

	public void setHabilitarMateRunners(boolean habilitarMateRunners) {
		this.habilitarMateRunners = habilitarMateRunners;
	}

	public boolean isHabilitarProductRunners() {
		return habilitarProductRunners;
	}

	public void setHabilitarProductRunners(boolean habilitarProductRunners) {
		this.habilitarProductRunners = habilitarProductRunners;
	}

	public boolean isHabilitarTmRace() {
		return habilitarTmRace;
	}

	public void setHabilitarTmRace(boolean habilitarTmRace) {
		this.habilitarTmRace = habilitarTmRace;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public boolean isResponsavel() {
		return responsavel;
	}

	public void setResponsavel(boolean responsavel) {
		this.responsavel = responsavel;
	}

	public boolean isFecharDistribuicao() {
		return fecharDistribuicao;
	}

	public void setFecharDistribuicao(boolean fecharDistribuicao) {
		this.fecharDistribuicao = fecharDistribuicao;
	}

	public int getNumeroLeads() {
		return numeroLeads;
	}

	public void setNumeroLeads(int numeroLeads) {
		this.numeroLeads = numeroLeads;
	}

	public void CalcularFaturamento(boolean nova, Vendas venda, float valorAnterior, Parametrosprodutos parametros) {
		if (nova) {
			salvarVendaProduto(venda, parametros);
			valorAnterior = 0;
		} else {
			if (venda.getValor() != valorAnterior) {
			} else {
				valorAnterior = 0;
			}
		}
		salvarMetaAnual(venda, valorAnterior);
		salvarMetaMensal(venda, valorAnterior);
		salvarFaturamento(venda, valorAnterior);
	}

	public void salvarFaturamento(Vendas venda, float valorAnterior) {
		float faturamentoValor = Formatacao.formatarStringfloat(getValorFaturamento());
		float novoFaturamentoValor;
		novoFaturamentoValor = faturamentoValor + venda.getValor() - valorAnterior;
		setValorFaturamento(Formatacao.formatarFloatString(novoFaturamentoValor));
	}

	public void salvarVendaProduto(Vendas venda, Parametrosprodutos parametros) {
		int idProduto = venda.getProdutos().getIdprodutos();
		if ((idProduto == parametros.getVisto()) || (idProduto == parametros.getSeguroPrivado())) {
			getVendaproduto().setProduto(getVendaproduto().getProduto() + 1);
		} else if ((idProduto == parametros.getPassagem()) || (idProduto == parametros.getPacotes())) {
			getVendaproduto().setTurismo(getVendaproduto().getTurismo() + 1);
		} else {
			getVendaproduto().setIntercambio(getVendaproduto().getIntercambio() + 1);
		}
		VendaProdutoFacade vendaProdutoFacade = new VendaProdutoFacade();
		setVendaproduto(vendaProdutoFacade.salvar(getVendaproduto()));
	}

	public void salvarMetaAnual(Vendas venda, float valorAnterior) {
		getMetaAnual().setMetaalcancada(getMetaAnual().getMetaalcancada() + venda.getValor() - valorAnterior);
		float meta = getMetaAnual().getValormeta();
		float alcancado = getMetaAnual().getMetaalcancada();
		float perc = (alcancado / meta) * 100;
		getMetaAnual().setPercentualalcancado(perc);
		MateFaturamentoAnualFacade meFacade = new MateFaturamentoAnualFacade();
		setMetaAnual(meFacade.salvar(getMetaAnual()));
	}

	public void salvarMetaMensal(Vendas venda, float valorAnterior) {
		float meta = getMetamensal().getValormeta();
		float alcancado = getMetamensal().getValoralcancado() + venda.getValor() - valorAnterior;
		float perc = (alcancado / meta) * 100;
		getMetamensal().setPercentualalcancado(perc);
		meta = getMetamensal().getValormetasemana();
		alcancado = getMetaparcialsemana() + venda.getValor();
		perc = (alcancado / meta) * 100;
		setPercsemana(perc);
	}

	public String retornarMetaAno() {
		String metaAno = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return metaAno;
		} else if (metaAnual!=null && metaAnual.getValormeta()!=null){
			metaAno = Formatacao.formatarFloatString(metaAnual.getValormeta());
			return metaAno;
		}else{
			return "";
		}
	}

	public String retornarMetaMes() {
		String metaMes = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return metaMes;
		} else {
			metaMes = Formatacao.formatarFloatString(metamensal.getValormeta());
			return metaMes;
		}
	}

	public String retornarMetaSemana() {
		String metaSemana = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return metaSemana;
		} else {
			metaSemana = Formatacao.formatarFloatString(metamensal.getValormetasemana());
			return metaSemana;
		}
	}

	public String retornarPercentualAno() {
		String metaAno = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return metaAno;
		} else {
			metaAno = Formatacao.formatarFloatString(metaAnual.getPercentualalcancado());
			return metaAno;
		}
	}

	public String retornarPercentualMes() {
		String metaMes = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return metaMes;
		} else {
			metaMes = Formatacao.formatarFloatString(metamensal.getPercentualalcancado());
			return metaMes;
		}
	}

	public String retornarPercentualSemana() {
		String metaSemana = "";
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return metaSemana;
		} else {
			metaSemana = Formatacao.formatarFloatString(percsemana);
			return metaSemana;
		}
	}

	public String pegarMes() {
		String mes = "" + Formatacao.getMes();
		return mes;
	}

	public String pegarAno() {
		String ano = "" + new Year();
		return ano;
	}

	public String gerarDadosDashBoard() {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			Mensagem.lancarMensagemInfo("Atenção", "reinicie o sistema");
		} else {
			MetasFaturamentoBean metasFaturamentoBean = new MetasFaturamentoBean();
			metaAnual = metasFaturamentoBean.getMetaAnual(usuarioLogadoMB);
			metamensal = metasFaturamentoBean.getMetaMensal(usuarioLogadoMB);
			metaparcialsemana = metasFaturamentoBean.getFaturamentoSemana(usuarioLogadoMB, vendasDao);
			percsemana = (metaparcialsemana / metamensal.getValormetasemana()) * 100;
			float valor = metasFaturamentoBean.getFaturamentoMensal(usuarioLogadoMB, vendasDao).floatValue();
			valorFaturamento = Formatacao.formatarFloatString(valor);

			VendaProdutoFacade vendaProdutoFacade = new VendaProdutoFacade();
			String mesano = Formatacao.gerarCopetencia(new Date());
			String sql = "SELECT v FROM Vendaproduto v where v.mesano='" + mesano + "'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + " and v.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			} else {
				sql = sql + " and v.unidadenegocio.idunidadeNegocio=6";
			}
			vendaproduto = vendaProdutoFacade.lista(sql);
			if (vendaproduto == null) {
				vendaproduto = new Vendaproduto();
				vendaproduto.setMesano(mesano);
				vendaproduto.setIntercambio(0);
				vendaproduto.setTurismo(0);
				vendaproduto.setProduto(0);
				if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
					vendaproduto.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
				} else {
					UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
					Unidadenegocio unidadenegocio = unidadeNegocioFacade.consultar(6);
					vendaproduto.setUnidadenegocio(unidadenegocio);
				}
			}
			gerarNumerosFollowUp();
		}
		return "";
	}

	public boolean retornarConteudo() { 
		if (usuarioLogadoMB.getUsuario().getDashboard().equalsIgnoreCase("C")) {
			return true;
		} else
			return false;
	}

	public boolean retornarImagem() {
		if (usuarioLogadoMB.getUsuario().getDashboard().equalsIgnoreCase("I")) { 
			return true;
		} else
			return false;
	}

	public void mudarDashboard() { 
		if (usuarioLogadoMB.getUsuario().getDashboard().equalsIgnoreCase("C")) {
			usuarioLogadoMB.getUsuario().setDashboard("I"); 
		} else {
			usuarioLogadoMB.getUsuario().setDashboard("C"); 
			gerarDadosDashBoard();
		}
	}

	public void gerarNumerosFollowUp() {
		if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialcrm()
				&& !usuarioLogadoMB.getUsuario().isVende()
				&& usuarioLogadoMB.getUsuario().getAcessounidade().isDashboard()) {
			acessoResponsavelGerencial = true;
		}
		novos = 0;
		atrasadas = 0;
		hoje = 0;
		Date data = new Date();
		String sql = "select l from Lead l where l.situacao<=5 ";
		if (!acessoResponsavelGerencial) {
			sql = sql + " and l.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			if (!usuarioLogadoMB.getUsuario().getAcessounidade().isCrm()) {
				sql = sql + " and l.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
			}
		}
		sql = sql + " order by l.dataproximocontato";
		List<Lead> listaLead = leadDao.lista(sql);
		if (listaLead == null) {
			listaLead = new ArrayList<Lead>();
		}
		String dataHoje = Formatacao.ConvercaoDataSql(new Date());
		for (int i = 0; i < listaLead.size(); i++) {
			if (listaLead.get(i).getSituacao() == 1) {
				novos = novos + 1;
			} else if ((listaLead.get(i).getDataproximocontato()) != null
					&& (Formatacao.ConvercaoDataSql(listaLead.get(i).getDataproximocontato())
							.equalsIgnoreCase(dataHoje))
					&& (listaLead.get(i).getSituacao() <=5)) {
				hoje = hoje + 1;
			} else if ( listaLead.get(i).getDataproximocontato() != null
					&& listaLead.get(i).getDataproximocontato().before(new Date())
					&& (listaLead.get(i).getSituacao() <=5)) {
				atrasadas = atrasadas + 1;
			}
		}
	}
	
	public void gerarlistaVersoes(){
		Date dataAtual = Formatacao.formatarDataAgora();
		if (usuarioLogadoMB.getUsuario().getDataversao().before(dataAtual)){
			VersaoUsuarioFacade versaoUsuarioFacade = new VersaoUsuarioFacade();
			String sql = "SELECT v FROM Versaousuario v where v.versoes.data>='" +
			Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getDataversao()) +
			"' and v.versoes.data<'" + Formatacao.ConvercaoDataSql(new Date()) + "' ";
			List<Versaousuario> listaVersoes = versaoUsuarioFacade.listar(sql);
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			usuarioLogadoMB.getUsuario().setDataversao(new Date());
			usuarioFacade.salvar(usuarioLogadoMB.getUsuario());
			if (listaVersoes!=null){
				if (listaVersoes.size()>0){
					FacesContext fc = FacesContext.getCurrentInstance();
			        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			        session.setAttribute("listaVersoes", listaVersoes);
					RequestContext.getCurrentInstance().openDialog("consVersoes");
				}
			}
		}
	}
	
	public boolean retornarImagemAniversario() { 
		int dia=0;
		int mes=0;
		if (usuarioLogadoMB.getUsuario().isFecharaniversario() == false) {
			if (usuarioLogadoMB.getUsuario().getDataaniversario() != null) {
				dia = Formatacao.getDiaData(usuarioLogadoMB.getUsuario().getDataaniversario());
				mes = Formatacao.getMesData(usuarioLogadoMB.getUsuario().getDataaniversario());
			}
			if (dia==Formatacao.getDiaData(new Date())
					&& mes==Formatacao.getMesData(new Date())) { 
				imagem = "diaaniversario";
			} else if ((usuarioLogadoMB.getUsuario().getDatacomemorativa() != null)
					&& (Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getDatacomemorativa())
							.equals(Formatacao.ConvercaoDataSql(new Date())))) {
				imagem = "diacomemorativo";
			} else {
				return false;
			}  
			return true;
		}else return false; 
	}
	
	public boolean retornarPacotes() { 
		int dia=0;
		int mes=0;
		if (usuarioLogadoMB.getUsuario().isFecharaniversario() == false) {
			if (usuarioLogadoMB.getUsuario().getDataaniversario() != null) {
				dia = Formatacao.getDiaData(usuarioLogadoMB.getUsuario().getDataaniversario());
				mes = Formatacao.getMesData(usuarioLogadoMB.getUsuario().getDataaniversario());
			}
			if (dia==Formatacao.getDiaData(new Date())
					&& mes==Formatacao.getMesData(new Date())) { 
				imagem = "diaaniversario";
			} else if ((usuarioLogadoMB.getUsuario().getDatacomemorativa() != null)
					&& (Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getDatacomemorativa())
							.equals(Formatacao.ConvercaoDataSql(new Date())))) {
				imagem = "diacomemorativo";
			} else {
				return true;
			}  
			return false;
		}else return true; 
	}
	
	public void fecharImagemAniversario(){
		usuarioLogadoMB.getUsuario().setFecharaniversario(true);
	}
	
	public boolean mostrarDadosUnidade() { 
		if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
			if(usuarioLogadoMB.getUsuario().getAcessounidade().isDashboard()) {
				return true;
			}else return false;
		}else return true;
	}
	
	public boolean privarDadosUnidade() { 
		if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
			if(usuarioLogadoMB.getUsuario().getAcessounidade().isDashboard()) {
				return false;
			}else return true;
		}else return true;
	}
	
	
	
	public void mudarPosicao(boolean tipo){
		if (tipo) {
			posicao = posicao + 1;
		}else{
			posicao = posicao - 1;
		}
		if (posicao <= 0) {
			posicao = 3;
		}else if(posicao > 3){
			posicao = 1;
		}
		if (posicao == 1) {
			habilitarMateRunners = true;
			habilitarProductRunners = false;
			habilitarTmRace = false;
		}else if(posicao == 2){
			habilitarMateRunners = false;
			habilitarProductRunners = true;
			habilitarTmRace = false;
		}else if(posicao == 3){
			habilitarMateRunners = false;
			habilitarProductRunners = false;
			habilitarTmRace = true;
		}
	}
	
	
	public String orcamentoTarifarioModelo() {
		
		return "inicialPacotes";
	}
	
	
	public boolean verificarDistribuicaoLead() {
		List<Lead> listaLead;
		Usuario usuario = usuarioLogadoMB.getUsuario();
		if (usuario.getResponsavel() != null) {
			String sql;
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==1  || usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialdistribuicaoleads()) {
				sql = "select l from Lead l where l.dataenvio is null";
			}else if (usuarioLogadoMB.getUsuario().getIdusuario() == 212) {
				sql = "select l from Lead l where l.dataenvio is null and (l.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
						+ " or  l.unidadenegocio.idunidadeNegocio=6)";
			} else {
				sql = "select l from Lead l where l.dataenvio is null and l.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}
			listaLead = leadDao.lista(sql);
			if (listaLead != null && listaLead.size() > 0) {
				numeroLeads = listaLead.size();
				return true;
			}
		}
		return false;
	}
	
	
	
	public String distribuicao() {
		return "distribuicaoLeads";
	}
	
	
	public void fecharNotificacao() {
		fecharDistribuicao = false;
	}
	
}
