package br.com.travelmate.bean.controleAlteracoes;

import java.util.Date;

import br.com.travelmate.facade.ControleAlteracoesFacade;
import br.com.travelmate.facade.DadosAlteracoesFacade;
import br.com.travelmate.model.Controlealteracoes;
import br.com.travelmate.model.Dadosalteracoes;
import br.com.travelmate.model.Departamento;

public class ControleAlteracaoCursoBean {
	
	public Controlealteracoes salvar(Controlealteracoes controleAlteracoes, Departamento departamento, String campo, String dadosAtual, String dadosAnterior){
		if (controleAlteracoes == null || controleAlteracoes.getIdcontrolealteracoes()==null){
			controleAlteracoes.setDataalteracao(new Date());
			controleAlteracoes.setVerificado(false);
			ControleAlteracoesFacade controleAlteracoesFacade = new ControleAlteracoesFacade();
			controleAlteracoes = controleAlteracoesFacade.salvar(controleAlteracoes);
		}
		Dadosalteracoes dados  = new Dadosalteracoes();
		dados.setCampo(campo);
		dados.setControlealteracoes(controleAlteracoes);
		dados.setDadoatual(dadosAtual);
		dados.setDadoanterior(dadosAnterior);
		dados.setVerificado(false);
		dados.setDepartamento(departamento);
		DadosAlteracoesFacade dadosAlteracoesFacade = new DadosAlteracoesFacade();
		dadosAlteracoesFacade.salvar(dados);
		return controleAlteracoes;
	}

}
