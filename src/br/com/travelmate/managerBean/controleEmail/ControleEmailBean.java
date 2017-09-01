package br.com.travelmate.managerBean.controleEmail;

import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.ControleEmailFacade;
import br.com.travelmate.model.Controleemail;
import br.com.travelmate.model.Usuario;

public class ControleEmailBean {

	
	public void salvar(Date dataEnvio, String horaEnvio, String assunto, List<String> listaEmails, String corpo, Usuario usuario){
		ControleEmailFacade controleEmailFacade = new ControleEmailFacade();
		Controleemail controleEmail = new Controleemail();
		controleEmail.setAssunto(assunto);
		controleEmail.setCorpo(corpo);
		controleEmail.setData(dataEnvio);
		String lista="";
		for (int i = 0; i < listaEmails.size(); i++) {
			lista = lista + listaEmails.get(i) + "\n";
		} 
		controleEmail.setDestinatarios(lista);
		controleEmail.setHora(horaEnvio);
		controleEmail.setUsuario(usuario);
		controleEmailFacade.salvar(controleEmail);
	}
	
}
