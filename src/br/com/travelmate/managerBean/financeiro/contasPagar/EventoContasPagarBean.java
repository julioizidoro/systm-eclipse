package br.com.travelmate.managerBean.financeiro.contasPagar;

import java.util.Date;

import javax.inject.Inject;

import br.com.travelmate.facade.EventoContasPagarFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Eventocontaspagar;
import br.com.travelmate.util.Formatacao;

public class EventoContasPagarBean {
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Eventocontaspagar evento;

	public EventoContasPagarBean(String tipoEvento, Contaspagar conta) {
		evento = new Eventocontaspagar();
		evento.setTipoevento(tipoEvento);
		evento.setContaspagar(conta);
	}
	
	
	public String salvar(){
		evento.setData(new Date());
		evento.setHora(Formatacao.foramtarHoraString());
		evento.setUsuario(usuarioLogadoMB.getUsuario());
		EventoContasPagarFacade eventoContasPagarFacade = new EventoContasPagarFacade();
		eventoContasPagarFacade.salvar(evento);
		return "";
	}
	
	public String cancelar(){
		return "";
	}
	
	

}
