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

import br.com.travelmate.model.Departamento;




/**
 *
 * @author Wolverine
 */
@FacesConverter(value="DepartamentoConverter")
public class DepartamentoConverter  implements Converter{
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Departamento> listaDepartamento = (List<Departamento>) component.getAttributes().get("listaDepartamento");
        if (listaDepartamento != null) {
	        for (Departamento departamento : listaDepartamento) {
	                if (departamento.getNome().equalsIgnoreCase(value)) {
	                    return departamento;
	                }
	            }
	    } else {
	        return new Departamento();
	    }
	    return null;
	}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Departamento departamento = (Departamento) value;
            return departamento.getNome();
        }
    }
}
    
    