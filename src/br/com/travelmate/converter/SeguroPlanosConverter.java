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

import br.com.travelmate.model.Seguroplanos;

/**
 *
 * @author Kamila
 */
@FacesConverter(value="SeguroPlanosConverter")
public class SeguroPlanosConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Seguroplanos> listaSeguroplanos = (List<Seguroplanos>) component.getAttributes().get("listaSeguroPlanos");
        if (listaSeguroplanos != null) {
            for (Seguroplanos seguroplanos : listaSeguroplanos) {
                if (seguroplanos.getNome().equalsIgnoreCase(value)) {
                    return seguroplanos;
                }
            }
        } else {
            return new Seguroplanos();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Fornecdor";
        }else {
            if (value instanceof Seguroplanos){
            		Seguroplanos seguroplanos = (Seguroplanos) value;
                if (seguroplanos.getIdseguroplanos()==null){
                    return "";
                }else return seguroplanos.getNome();
            }else return "";
        }
    }
    
}
