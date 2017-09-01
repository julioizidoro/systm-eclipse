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

import br.com.travelmate.model.Voluntariadoprojetovalor; 

/**
 *
 * @author Kamila
 */
@FacesConverter(value="VoluntariadoProjetoConverter")
public class VoluntariadoProjetoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Voluntariadoprojetovalor> lista = (List<Voluntariadoprojetovalor>) component.getAttributes().get("listaVoluntariadoValor");
        if (lista != null) {
            for (Voluntariadoprojetovalor voluntariadoprojeto : lista) {
                if (voluntariadoprojeto.getVoluntariadoprojeto().getDescricao().equals(value)) {
                    return voluntariadoprojeto;
                }
            }
        } else {
            return new Voluntariadoprojetovalor();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Projeto";
        }else {
            if (value instanceof Voluntariadoprojetovalor){
            	Voluntariadoprojetovalor voluntariadoprojeto = (Voluntariadoprojetovalor) value;
                if (voluntariadoprojeto.getIdvoluntariadoprojetovalor()==null){
                    return "";
                }else return voluntariadoprojeto.getVoluntariadoprojeto().getDescricao();
            }else return "";
        }
    }
    
}
