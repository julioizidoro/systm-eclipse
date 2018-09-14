/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.converter;
 
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto; 

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Greici
 */
@FacesConverter(value = "FornecedorCidadeIdiomaProdutoConverter")
public class FornecedorCidadeIdiomaProdutoConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<Fornecedorcidadeidiomaproduto> listaFornecedorcidadeidiomaproduto = (List<Fornecedorcidadeidiomaproduto>) component
				.getAttributes().get("listaFornecedorCidadeIdiomaProduto");
		if (listaFornecedorcidadeidiomaproduto != null) {
			for (Fornecedorcidadeidiomaproduto Fornecedorcidadeidiomaproduto : listaFornecedorcidadeidiomaproduto) {
				if (Fornecedorcidadeidiomaproduto.getProdutosorcamento().getDescricao().equalsIgnoreCase(value)) {
					return Fornecedorcidadeidiomaproduto;
				}
			}
		} else {
			return new Fornecedorcidadeidiomaproduto();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value.toString().equalsIgnoreCase("0")) {
			return "Selecione";
		} else {
			if (value instanceof Fornecedorcidadeidiomaproduto) {
				Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto = (Fornecedorcidadeidiomaproduto) value;
				if (fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto() == null) {
					return "";
				} else
					return fornecedorcidadeidiomaproduto.getProdutosorcamento().getDescricao();
			} else
				return "";
		}
	}

}
