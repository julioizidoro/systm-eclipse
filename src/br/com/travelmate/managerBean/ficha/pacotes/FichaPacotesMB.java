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
		dataExtanso = Formatacao.getSemana(diaSemana) + " " + dia + Formatacao.getMes() + " " + Formatacao.getAnoData(dataHoje); 
		itensTrecho = gerarDescricaoTrecho(pacotes);
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



	public String gerarDescricaoTrecho(Pacotes pacotes) {
		String itensTrecho = "";
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
				Pacotecarro pacotecarro = pacotesCarroFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotecarro != null) {
					itensTrecho = itensTrecho + "Carro: " + pacotecarro.getDescritivo() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotecarro.getDataretirada()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecarro.getDatadevolucao()) + "\n\n";
				}
			}
		}
		if (pacotes != null) {
			for (int i = 0; i < pacotes.getPacotetrechoList().size(); i++) {
				PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
				Pacotepassagem pacotepassagem = pacotesPassagemFacade
						.consultar(pacotes.getPacotetrechoList().get(i).getIdpacotetrecho());
				if (pacotepassagem != null) {
					itensTrecho = itensTrecho + "Passagem: " + pacotepassagem.getCiaAerea() + "  Intinerario: "
							+ pacotepassagem.getIntinerario() + "   Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotepassagem.getDataEmbarque()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotepassagem.getDataVolta()) + "\n\n";
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
					itensTrecho = itensTrecho + "Cruzeiro: " + pacotecruzeiro.getCategoria() + "  Intinerario: "
							+ pacotecruzeiro.getIntinerario() + "  " + pacotecruzeiro.getTipocabine() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotecruzeiro.getDatasaida()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecruzeiro.getDatechegada()) + "\n\n";
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
					itensTrecho = itensTrecho + "Hotel: " + pacotehotel.getDescritivo() + " " + "     Categoria: "
							+ pacotehotel.getCategoria() + "    Regime: " + pacotehotel.getRegime()
							+ "    Tipo de quarto: " + pacotehotel.getTipoquarto() + "    Período: "
							+ Formatacao.ConvercaoDataPadrao(pacotehotel.getDatacheckin()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotehotel.getDatacheckout()) + "\n\n";
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
					itensTrecho = itensTrecho + "Transfer : " + pacotetransfer.getDe() + " / "
							+ pacotetransfer.getPara() + "  " + pacotetransfer.getTipo() + "\n\n";
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
					itensTrecho = itensTrecho + "Trem/Ônibus: " + pacotetrem.getDe() + " / " + pacotetrem.getPara()
							+ "    Período: " + pacotetrem.getHorachegada() + " Até " + pacotetrem.getHorasaida()
							+ "\n\n";
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
					itensTrecho = itensTrecho + "Circuito: " + pacotecircuito.getDe() + " / " + pacotecircuito.getPara()
							+ "    Período: " + Formatacao.ConvercaoDataPadrao(pacotecircuito.getDatainicio()) + " Até "
							+ Formatacao.ConvercaoDataPadrao(pacotecircuito.getDatatertminio()) + "\n\n";
					;
				}
			}
		}
		return itensTrecho;
	}

}
