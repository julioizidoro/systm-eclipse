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

import br.com.travelmate.model.Cidade;


/**
 *
 * @author Wolverine
 */
@FacesConverter(value="CidadeConverter")
public class CidadeConverter  implements Converter{
    
    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Cidade> listaCidade = (List<Cidade>) component.getAttributes().get("listaCidade");
        if (listaCidade != null) {
            for (Cidade cidade : listaCidade) {
                if (cidade.getNome().equalsIgnoreCase(value)) {
                    return cidade;
                }
            }
        } else {
            return new Cidade();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Cidade){
                Cidade cidade = (Cidade) value;
                 return cidade.getNome();
            }else return "";
        }
    }
    
}
