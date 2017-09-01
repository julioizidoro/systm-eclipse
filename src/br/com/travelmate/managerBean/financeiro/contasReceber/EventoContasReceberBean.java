package br.com.travelmate.managerBean.financeiro.contasReceber;

import java.util.Date;

import javax.inject.Inject;

import br.com.travelmate.facade.EventoContasReceberFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Eventocontasreceber;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

public class EventoContasReceberBean {
	
	
	private Eventocontasreceber evento;

	public EventoContasReceberBean(String tipoEvento, Contasreceber conta, Usuario usuario) {
		evento = new Eventocontasreceber();
		evento.setTipoevento(tipoEvento);
		evento.setContasreceber(conta);
		evento.setUsuario(usuario);
		salvar();
	}
	
	
	public void salvar(){
		evento.setData(new Date());
		evento.setHora(Formatacao.foramtarHoraString());
		EventoContasReceberFacade eventoContasReceberFacade= new EventoContasReceberFacade();
		eventoContasReceberFacade.salvar(evento);
	}
	
	

}
