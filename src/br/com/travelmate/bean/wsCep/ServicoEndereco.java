package br.com.travelmate.bean.wsCep;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServicoEndereco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EnderecoBean buscarEnderecoPor(String urlJson){
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.create();
		EnderecoBean retornoEndereco = gson.fromJson(urlJson, EnderecoBean.class);
		return retornoEndereco;
	}

}
