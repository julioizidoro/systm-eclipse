/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package br.com.travelmate.converter;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import br.com.travelmate.model.Departamentoproduto;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="DepartamentoProdutoConverter")
public class DepartamentoProdutoConverter  implements Converter{
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Departamentoproduto> listaDepartamento = (List<Departamentoproduto>) component.getAttributes().get("listaDepartamentoProduto");
        if (listaDepartamento != null) {
	        for (Departamentoproduto departamento : listaDepartamento) {
                if (departamento.getProdutos().getDescricao().equalsIgnoreCase(value)) {
                    return departamento;
                }
            }
	    } else {
	        return new Departamentoproduto();
	    }
	    return null;
	}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        		Departamentoproduto departamento = (Departamentoproduto) value;
            return departamento.getProdutos().getDescricao();
        }
    }
}
    
    