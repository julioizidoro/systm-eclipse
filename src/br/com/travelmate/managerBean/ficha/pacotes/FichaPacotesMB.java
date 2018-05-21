package br.com.travelmate.managerBean.ficha.pacotes;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.PacoteCircuitoFacade;
import br.com.travelmate.facade.PacoteCruzeiroFacade;
import br.com.travelmate.facade.PacoteTransferFacade;
import br.com.travelmate.facade.PacoteTremFacade;
import br.com.travelmate.facade.PacotesCarroFacade;
import br.com.travelmate.facade.PacotesHotelFacade;
import br.com.travelmate.facade.PacotesPassagemFacade;
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotecircuito;
import br.com.travelmate.model.Pacotecruzeiro;
import br.com.travelmate.model.Pacotehotel;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacotetransfer;
import br.com.travelmate.model.Pacotetrem;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;


@Named
@ViewScoped
public class FichaPacotesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contrato;
	private Pacotes pacotes;
	private Vendas vendas;
	private Date dataHoje;
	private String dataExtanso;
	private int diaSemana;
	private float valorTotalMoeda = 0.0f;
	private boolean habilitarSegundoCurso = false;
	private String itensTrecho = "";
	private float totalPagamento = 0.0f;
	private String itensCarro = "";
	private String itensPassagem = "";
	private String  itensCruzeiro = "";
	private String itensHotel = "";
	private String itensTransfer = "";
	private String itensTreem = "";
	private String itensCircuito = "";
	private boolean carro = false;
	private boolean passagem = false;
	private boolean cruzeiro = false;
	private boolean hotel = false;
	private boolean transfer = false;
	private boolean treem = false;
	private boolean circuito = false;

	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pacotes = (Pacotes) session.getAttribute("pacotes");
		
		if (pacotes != null) {
			vendas = pacotes.getVendas();
		}
		dataHoje = new Date();
		diaSemana = Formatacao.diaSemana(dataHoje) - 1;
		int dia = Formatacao.getDiaData(dataHoje); 
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + " de "+ Formatacao.getMes() + " de " + Formatacao.getAnoData(dataHoje);
		itensTrecho = gerarDescricaoTrecho(pacotes);
		if (vendas.getFormapagamento() != null) {
			totalPagamento = vendas.getFormapagamento().getValorOrcamento() / vendas.getValorcambio();
		}
	}



	public String getContrato() {
		return contrato;
	}



	public void setContrato(String contrato) {
		this.contrato = contrato;
	}






	public Pacotes getPacotes() {
		return pacotes;
	}



	public void setPacotes(Pacotes pacotes) {
		this.pacotes = pacotes;
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}



	public Date getDataHoje() {
		return dataHoje;
	}



	public void setDataHoje(Date dataHoje) {
		this.dataHoje = dataHoje;
	}



	public String getDataExtanso() {
		return dataExtanso;
	}



	public void setDataExtanso(String dataExtanso) {
		this.dataExtanso = dataExtanso;
	}



	public float getValorTotalMoeda() {
		return valorTotalMoeda;
	}



	public void setValorTotalMoeda(float valorTotalMoeda) {
		this.valorTotalMoeda = valorTotalMoeda;
	}



	public boolean isHabilitarSegundoCurso() {
		return habilitarSegundoCurso;
	}



	public void setHabilitarSegundoCurso(boolean habilitarSegundoCurso) {
		this.habilitarSegundoCurso = habilitarSegundoCurso;
	}
	
	
	public String getItensTrecho() {
		return itensTrecho;
	}



	public void setItensTrecho(String itensTrecho) {
		this.itensTrecho = itensTrecho;
	}



	public int getDiaSemana() {
		return diaSemana;
	}



	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}



	public String getItensCarro() {
		return itensCarro;
	}



	public void setItensCarro(String itensCarro) {
		this.itensCarro = itensCarro;
	}



	public String getItensPassagem() {
		return itensPassagem;
	}



	public void setItensPassagem(String itensPassagem) {
		this.itensPassagem = itensPassagem;
	}



	public String getItensCruzeiro() {
		return itensCruzeiro;
	}



	public void setItensCruzeiro(String itensCruzeiro) {
		this.itensCruzeiro = itensCruzeiro;
	}



	public String getItensHotel() {
		return itensHotel;
	}



	public void setItensHotel(String itensHotel) {
		this.itensHotel = itensHotel;
	}



	public String getItensTransfer() {
		return itensTransfer;
	}



	public void setItensTransfer(String itensTransfer) {
		this.itensTransfer = itensTransfer;
	}



	public String getItensTreem() {
		return itensTreem;
	}



	public void setItensTreem(String itensTreem) {
		this.itensTreem = itensTreem;
	}



	public String getItensCircuito() {
		return itensCircuito;
	}



	public void setItensCircuito(String itensCircuito) {
		this.itensCircuito = itensCircuito;
	}



	public boolean isCarro() {
		return carro;
	}



	public void setCarro(boolean carro) {
		this.carro = carro;
	}



	public boolean isPassagem() {
		return passagem;
	}



	public void setPassagem(boolean passagem) {
		this.passagem = passagem;
	}



	public boolean isCruzeiro() {
		return cruzeiro;
	}



	public void setCruzeiro(boolean cruzeiro) {
		this.cruzeiro = cruzeiro;
	}



	public boolean isHotel() {
		return hotel;
	}



	public void setHotel(boolean hotel) {
		this.hotel = hotel;
	}



	public boolean isTransfer() {
		return transfer;
	}



	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}



	public boolean isTreem() {
		return treem;
	}



	public void setTreem(boolean treem) {
		this.treem = treem;
	}



	public boolean isCircuito() {
		return circuito;
	}



	public void setCircuito(boolean circuito) {
		this.circuito = circuito;
	}



	public String gerarDescricaoTrecho(Pacotes pacotes) {
		String itensTrecho = "";
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
				Pacotecarro pacotecarro = pacotesCarroFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecarro != null) {
					carro = true;
					itensCarro =  " " + pacotecarro.getDescritivo() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotecarro.getDataretirada()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecarro.getDatadevolucao());
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
				Pacotepassagem pacotepassagem = pacotesPassagemFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotepassagem != null) {
					passagem = true;
					itensPassagem = " " + pacotepassagem.getCiaAerea() + "  Intinerario: "
							+ pacotepassagem.getIntinerario() + "   Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotepassagem.getDataEmbarque()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotepassagem.getDataVolta());
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteCruzeiroFacade pacotesCruzeiroFacade = new PacoteCruzeiroFacade();
				Pacotecruzeiro pacotecruzeiro = pacotesCruzeiroFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecruzeiro != null) {
					cruzeiro = true;
					itensCruzeiro =  " " + pacotecruzeiro.getCategoria() + "  Intinerario: "
							+ pacotecruzeiro.getIntinerario() + "  " + pacotecruzeiro.getTipocabine() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotecruzeiro.getDatechegada()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecruzeiro.getDatasaida());
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesHotelFacade pacoteHotelFacade = new PacotesHotelFacade();
				Pacotehotel pacotehotel = pacoteHotelFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotehotel != null) {
					hotel = true;
					itensHotel =  " " + pacotehotel.getDescritivo() + " " + "     Categoria: "
							+ pacotehotel.getCategoria() + "    Regime: " + pacotehotel.getRegime()
							+ "    Tipo de quarto: " + pacotehotel.getTipoquarto() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotehotel.getDatacheckin()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotehotel.getDatacheckout());
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
				Pacotetransfer pacotetransfer = pacoteTransferFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotetransfer != null) {
					transfer = true;
					itensTransfer =  " " + pacotetransfer.getDe() + " / "
							+ pacotetransfer.getPara() + "  " + pacotetransfer.getTipo();
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteTremFacade pacoteTremFacade = new PacoteTremFacade();
				Pacotetrem pacotetrem = pacoteTremFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotetrem != null) {
					treem = true;
					itensTreem =  " " + pacotetrem.getDe() + " / " + pacotetrem.getPara()
							+ "    Período: " + pacotetrem.getHorachegada() + " Até " + pacotetrem.getHorasaida()
							;
					;
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacoteCircuitoFacade pacoteCircuitoFacade = new PacoteCircuitoFacade();
				Pacotecircuito pacotecircuito = pacoteCircuitoFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecircuito != null) {
					circuito = true;
					itensCircuito =  " " + pacotecircuito.getDe() + " / " + pacotecircuito.getPara()
							+ "    Período: " + Formatacao.ConvercaoDataPadrao(pacotecircuito.getDatainicio()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecircuito.getDatatertminio());
					;
				}
			}
		}
		return itensTrecho;
	}



	public float getTotalPagamento() {
		return totalPagamento;
	}



	public void setTotalPagamento(float totalPagamento) {
		this.totalPagamento = totalPagamento;
	}

}
